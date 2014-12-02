package forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;

public class RecordTreatment extends JPanel{
    //testing
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new RecordTreatment(frame));
        //NOTE: Adding layout=null here screws up the JPanel layout.
        frame.setSize(RecordTreatment.PREF_DIMS);
        frame.setResizable(true); 
        frame.setVisible(true);
    }

    public RecordTreatment(JFrame parentF){
        labels = new LinkedHashMap<String, JLabel>();
        fields = new LinkedHashMap<String, JComponent>(); 
        this.parentF = parentF;
        qHand = new QueryHandler("team016", "eabb6f40");

        setLayout(new GridLayout(0, 2));
        initFields();
        addFields();
        setListeners();
        setSize(PANEL_WIDTH, PANEL_HEIGHT);
    }

    private void initFields(){
        //Get the labels done first
        labels.put("treat", new JLabel("Treatment:"));
        labels.put("cost", new JLabel("Cost:"));
        labels.put("date", new JLabel("Date(DDMMYYYY):"));
        labels.put("start", new JLabel("Start(HHMM):"));
        labels.put("partn", new JLabel("Partner:"));
        
        //Fields...
        fields.put("treat", new JTextField(10));
        fields.put("cost", new JTextField(10));
        fields.put("date", new DatePanel());
        fields.put("start", new TimePanel()); 
        fields.put("partn", new JTextField(10));
    }

    private void addFields(){
        Iterator lit = labels.entrySet().iterator();
        Iterator fit = fields.entrySet().iterator();

        while(lit.hasNext() && fit.hasNext()){
            Map.Entry lPair = (Map.Entry)lit.next();
            Map.Entry fPair = (Map.Entry)fit.next();

            add((JLabel)lPair.getValue());
            add((JComponent)fPair.getValue());
        }

        //Annd the two buttons...
        add(submitB);
        add(cancelB);
    }

    private void setListeners(){
        cancelB.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                parentF.dispose();
            }
        });

        submitB.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //Check date, start, partner combo exists in Appointment
                recordTreatment();
            }
        });
    }
    
    private void recordTreatment(){
        //Get vars
        String tName = ((JTextField) fields.get("treat")).getText();
        String cost = ((JTextField) fields.get("cost")).getText();
        String date = ((DatePanel) fields.get("date")).getText();
        String sTime = (((TimePanel) fields.get("start")).getHours() + ":" + ((TimePanel) fields.get("start")).getMinutes());
        String partner = ((JTextField) fields.get("partn")).getText();

        //QUERIES
        String checkApp = "SELECT * FROM Appointment WHERE date = '" +
                date + "' AND startTime = '"+ sTime + "' AND partner = '" + partner + "';";
        
        if (qHand.executeQuery(checkApp) != null) {
        	String recTreatment = "INSERT INTO Treatment"+
        			"(treatName, date, startTime, partner, cost)" +
        			" VALUES('"+
        			tName + "', '"+
        			date + "', '"+
        			sTime + "', '"+
        			partner + "', '"+
        			cost + "');";
            JOptionPane.showMessageDialog(parentF, recTreatment);
        	qHand.executeUpdate(recTreatment);
            JOptionPane.showMessageDialog(parentF, "Treatment "+
                    "successfully recorded!");
            parentF.dispose();
        } else {
        	System.out.println("Treatment entered does not correspond with existing appointment with matching date, start time and partner");
        }
    }
    
    //vars
    private JFrame parentF = null;
    private QueryHandler qHand = null;

    //Components...
    private LinkedHashMap<String, JLabel> labels = null;
    private LinkedHashMap<String, JComponent> fields = null;
    private JButton submitB = new JButton("Submit");
    private JButton cancelB = new JButton("Cancel");

    //Constants...
    public static final int PANEL_WIDTH = 230;
    public static final int PANEL_HEIGHT = 245;
    public static final Dimension PREF_DIMS = new Dimension(250, 190);

}
