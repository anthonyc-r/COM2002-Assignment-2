package DBTEST06;

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
        qHand = new QueryHandler("team016", "eabb6f40");
        this.parentF = parentF;

        setLayout(new GridLayout(0, 2));
        box = initFields();
        addFields();
        setListeners();
        setSize(PANEL_WIDTH, PANEL_HEIGHT);
    }
    
    private JComboBox initFields(){
        //Ket the labels done first
        labels.put("patID", new JLabel("PatientID:"));
        labels.put("plan", new JLabel("Plan:"));
        //Get different plan names from db
        String[][] res = qHand.executeQueryFull("SELECT planName FROM CarePlan");
        String[] res1d = new String[res.length];
        if(res != null){
            for (int i=0; i<res.length; i++)
            	res1d[i]=res[i][0];
        }
        //Setup combo box...
        @SuppressWarnings("unchecked")
		JComboBox cmBox = new JComboBox(res1d);
        
        //Fields...
        fields.put("patID", new JTextField(10));
        fields.put("plan", cmBox);
        
        return cmBox;
    }

    private void addFields(){
    	add(unSubBox);
    	add(new JLabel(""));
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
             	String[] dupCheck = qHand.executeQuery("SELECT * FROM Subscription WHERE patientID = '"+patID+"';");
                String[] IDExists = qHand.executeQuery("SELECT * FROM Patient WHERE patientID = '"+patID+"';");
            	if (!unSubBox.isSelected()) {
                    if (dupCheck == null) {
                        //Check plan exists in CarePlan(It should)
                        String plan = (String)box.getSelectedItem(); 
                        String[] pExists = qHand.executeQuery(
                                "SELECT * FROM CarePlan WHERE planName = '"+
                                    plan+"';"
                            );
                        if(IDExists != null && pExists != null){    
                            //Insert into db
                            qHand.executeUpdate(
                                "INSERT INTO Subscription VALUES ('"+
                                        patID+"', '"+
                                        plan+"');"
                                );
                            JOptionPane.showMessageDialog(parentF, "Patient "+
                                "successfully subscribed.");
                            parentF.dispose();
                        } else{
                        JOptionPane.showMessageDialog(parentF, "Patient ID or "+
                                "plan does not exist in the database.");
                        }
                    } else
                        JOptionPane.showMessageDialog(parentF, 
                                "Patient already has plan"
                        );
            	} else {
            		String[] hasSub = qHand.executeQuery("SELECT * FROM Subscription WHERE patientID = '"+patID+"';");
            		if (hasSub!=null) {
            			qHand.executeUpdate("DELETE FROM Subscription WHERE patientID = '"+patID+"';");
            			JOptionPane.showMessageDialog(parentF, "Plan removed");
            		} else 
            			JOptionPane.showMessageDialog(parentF, "Patient does not have plan");
            	}
            }
        });
    }

    //var
    private JFrame parentF = null;
    private QueryHandler qHand;
    //Components...
    private LinkedHashMap<String, JLabel> labels;
    private LinkedHashMap<String, JComponent> fields;
    private JComboBox box;
    private JButton submitB = new JButton("Submit");
    private JButton cancelB = new JButton("Cancel");
    private JCheckBox unSubBox  = new JCheckBox("Unsubscribe", false);
    //Constants...
    public static final int PANEL_WIDTH = 230;
    public static final int PANEL_HEIGHT = 245;
    public static final Dimension PREF_DIMS = new Dimension(230, 150);
}
