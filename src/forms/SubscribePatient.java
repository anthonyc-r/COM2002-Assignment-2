package DBTEST05;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SubscribePatient extends JPanel{
    //testing
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new SubscribePatient(frame));
        //NOTE: Adding layout=null here screws up the JPanel layout.
        frame.setSize(SubscribePatient.PREF_DIMS);
        frame.setResizable(true); 
        frame.setVisible(true);
    }

    public SubscribePatient(JFrame parentF){
        labels = new LinkedHashMap<String, JLabel>();
        fields = new LinkedHashMap<String, JComponent>(); 
        qHand = new QueryHandler("uname", "pwd");
        this.parentF = parentF;

        setLayout(new GridLayout(0, 2));
        initFields();
        addFields();
        setListeners();
        setSize(PANEL_WIDTH, PANEL_HEIGHT);
    }
    
    private void initFields(){
        //Ket the labels done first
        labels.put("patID", new JLabel("PatientID:"));
        labels.put("plan", new JLabel("Plan:"));
        //Setup combo box...
        JComboBox cmBox = new JComboBox();
        //Get different plan names from db
        String[] res = qHand.executeQuery(null);
        if(res != null){
            for(String name : res){
                //Add name to cmBox       
            }
        }
        //Fields...
        fields.put("patID", new JTextField(10));
        fields.put("plan", cmBox);
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
                //Get patID + plan
                //Check patID exist in Patient
            	String patID = ((JTextField)fields.get("patID")).getText();
                String[] IDExists = qHand.executeQuery(
                		"SELECT 1 FROM Patient WHERE patientID = '"+
                			patID+"';"
                	);
                //Check plan exists in CarePlan(It should)
                String plan = ((JTextField)fields.get("plan")).getText();
                String[] pExists = qHand.executeQuery(
                		"SELECT 1 FROM CarePlan WHERE planName = '"+
                			plan+"';"
                	);
                if(IDExists != null && pExists != null){    
                    //Insert into db
                    qHand.executeUpdate(
                    		"INSERT INTO Subscription VALUES ('"+
                    			patID+"', "+
                    			plan+"');"
                    	);
                    JOptionPane.showMessageDialog(parentF, "Patient "+
                            "successfully subscribed.");
                    parentF.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(parentF, "Patient ID or "+
                            "plan does not exist in the database.");
                }
            }
        });
    }

    //var
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
    public static final Dimension PREF_DIMS = new Dimension(230, 90);
}
