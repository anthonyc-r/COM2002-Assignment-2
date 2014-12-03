package DBTEST06;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GetID extends JPanel{
    //testing
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new GetID(frame));
        //NOTE: Adding layout=null here screws up the JPanel layout.
        frame.setSize(GetID.PREF_DIMS);
        frame.setResizable(true); 
        frame.setVisible(true);
    }

    public GetID(JFrame parentF){
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
		labels.put("title", new JLabel("Title:"));
        labels.put("fName", new JLabel("Forename:"));
        labels.put("sName", new JLabel("Surname:"));
        labels.put("bDate", new JLabel("D.O.B.(DDMMYYYY):"));
		labels.put("hNum", new JLabel("House No.:"));
		labels.put("pCode", new JLabel("Postcode:"));

        //Fields...
        fields.put("title", new JTextField(10));
        fields.put("fName", new JTextField(10));
        fields.put("sName", new JTextField(10));
        //Consists of 3 labels + 3 JTFs for DD MM YYYY
        fields.put("bDate", new DatePanel());
		fields.put("hNum", new JTextField(10));
		fields.put("pCode", new JTextField(10));

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
    			String title = ((JTextField)fields.get("title")).getText();
    			String fName = ((JTextField)fields.get("fName")).getText();
    			String sName = ((JTextField)fields.get("sName")).getText();
    			String bDate = ((DatePanel)fields.get("bDate")).getText();
    			String hNum = ((JTextField)fields.get("hNum")).getText();
    			String pCode = ((JTextField)fields.get("pCode")).getText();
    			
    			String[] patientEx = qHand.executeQuery(
    					"SELECT patientID FROM Patient WHERE "
    					+ "title = '"+title+"' AND "
    					+ "forename = '"+fName+"' AND "
    					+ "surname = '"+sName+"' AND "
    					+ "birthDate = '"+bDate+"' AND "
    					+ "houseNo = "+hNum+" AND "
    					+ "postcode = '"+pCode+"';"
    				);
    			if (patientEx!=null)
    				JOptionPane.showMessageDialog(parentF, "ID: "+patientEx[0]);
    			else 
    				JOptionPane.showMessageDialog(parentF, "No patient found");
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
    public static final Dimension PREF_DIMS = new Dimension(300, 300);
}
