package DBTEST06;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ModifyOwed extends JPanel{
    //testing
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ModifyOwed(frame));
        //NOTE: Adding layout=null here screws up the JPanel layout.
        frame.setSize(ModifyOwed.PREF_DIMS);
        frame.setResizable(true); 
        frame.setVisible(true);
    }

    public ModifyOwed(JFrame parentF){
        labels = new LinkedHashMap<String, JLabel>();
        fields = new LinkedHashMap<String, JComponent>(); 
        qHand = new QueryHandler("team016", "eabb6f40");
        this.parentF = parentF;

        setLayout(new GridLayout(0, 2));
        initFields();
        addFields();
        setListeners();
        setSize(PANEL_WIDTH, PANEL_HEIGHT);
    }
    
    private void initFields(){
        //Ket the labels done first
        labels.put("patientID", new JLabel("Patient ID:"));
        labels.put("newOwed", new JLabel("New Owed:"));
        
        fields.put("patientID", new JTextField());
        fields.put("newOwed", new JTextField());
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
        add(getOwed);
        add(new JLabel());
        add(submitB);
        add(cancelB);
    }
    
    private void setListeners() {
    	cancelB.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                parentF.dispose();
            }
        });
    	
    	submitB.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			String newOwed = ((JTextField)fields.get("newOwed")).getText();
    			String patID = ((JTextField)fields.get("patientID")).getText();
    			String[] existsID = qHand.executeQuery("SELECT * FROM Patient WHERE patientID = '"+patID+"';");
    			if (existsID!=null) {
    				qHand.executeUpdate("UPDATE Patient SET amountOwed = '"+newOwed+"' WHERE patientID = '"+patID+"';");
    				JOptionPane.showMessageDialog(parentF, "Payment due changed successfully");
    			}
    			//JOptionPane.showMessageDialog(parentF, patID+" "+newOwed);
    		}
    	});
    	getOwed.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			String patID = ((JTextField)fields.get("patientID")).getText();
    			String[] existsID = qHand.executeQuery("SELECT * FROM Patient WHERE patientID = '"+patID+"';");
    			if (existsID!=null) {
    				String owed = qHand.executeQuery("SELECT amountOwed FROM Patient WHERE patientID = '"+patID+"';")[0];
    				JOptionPane.showMessageDialog(parentF, "£"+owed+" owed");
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
    private JButton submitB = new JButton("Submit");
    private JButton cancelB = new JButton("Cancel");
    private JButton getOwed = new JButton("Owed");
    //Constants...
    public static final int PANEL_WIDTH = 230;
    public static final int PANEL_HEIGHT = 245;
    public static final Dimension PREF_DIMS = new Dimension(230, 150);
}
