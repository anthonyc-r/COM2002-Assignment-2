package forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class BookAppointment extends JPanel{
    //testing
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new BookAppointment(frame));
        //NOTE: Adding layout=null here screws up the JPanel layout.
        frame.setSize(BookAppointment.PREF_DIMS);
        frame.setResizable(true); 
        frame.setVisible(true);
    }

    public BookAppointment(JFrame parentF){
        labels = new LinkedHashMap<String, JLabel>();
        fields = new LinkedHashMap<String, JComponent>(); 
        this.parentF = parentF;
        qHand = new QueryHandler("uname", "pwd");

        setLayout(new GridLayout(0, 2));
        initFields();
        addFields();
        setListeners();
        setSize(PANEL_WIDTH, PANEL_HEIGHT);
    }

    private void initFields(){
        //Ket the labels done first
        labels.put("start", new JLabel("Start(HHMM):"));
        labels.put("end", new JLabel("End(HHMM)"));
        labels.put("date", new JLabel("date(DDMMYYYY):"));
        labels.put("partn", new JLabel("Partner:"));
        labels.put("patID", new JLabel("Patient ID:"));
        
        //Fields...
        fields.put("start", new TimePanel());
        fields.put("end", new TimePanel());
        fields.put("date", new DatePanel());
        fields.put("partn", new JTextField(10)); 
        fields.put("patID", new JTextField(10));
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
                //Ensure nothing overlaps etc.
                if(treatmentValid()){    
                    //IF empty add new entry with null FK
                    if(((JTextField)fields.get("patID")).getText().equals("")){
                        qHand.executeUpdate(null);
                    }
                    //ELSE check that patientID exists
                    else{
                        //IF exists update table
                        String[] patient = qHand.executeQuery(null);
                        if(patient != null){
                            qHand.executeQuery(null);
                            JOptionPane.showMessageDialog(parentF,
                                    "Successfully booked new appointment.");
                        }
                        //ELSE show warning
                        JOptionPane.showMessageDialog(parentF, "Cannot "+
                                "find patient in database.");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(parentF, "Appointment "+
                            "not valid.");
                }
            }
        });
    }
    
    private boolean treatmentValid(){
        //Ensure nothing overlaps
        return false;
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
