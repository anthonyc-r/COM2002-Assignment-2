package forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import interfaces.DPanel;

public class RegisterPatient extends JPanel implements DPanel{
    
    //Testing
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //NOTE: adding layout=null here screws up JPanel layout
        frame.add(new RegisterPatient(frame));
        frame.setSize(RegisterPatient.PREF_DIMS);
        frame.setResizable(true); 
        frame.setVisible(true);
    }

    public RegisterPatient(JFrame parentF){
		labels = new LinkedHashMap<String, JLabel>();
        fields = new LinkedHashMap<String, JComponent>(); 
        qHand = new QueryHandler("team016", "eabb6f40");

        //Set parent
        this.parentF = parentF;
        //Set layout to 2d grid
        setLayout(new GridLayout(0, 2));
        //Set panel size... 
        initFields();
        addFields();
        setListeners();
        setSize(PANEL_WIDTH, PANEL_HEIGHT);
    }

    public Dimension getPrefDims(){
        return RegisterPatient.PREF_DIMS;
    }

    private void initFields(){		
		labels.put("title", new JLabel("Title:"));
        labels.put("fName", new JLabel("Forename:"));
        labels.put("sName", new JLabel("Surname:"));
        labels.put("bDate", new JLabel("D.O.B.(DDMMYYYY):"));
        labels.put("cNum", new JLabel("Contact No.:"));
		labels.put("hNum", new JLabel("House No.:"));
		labels.put("pCode", new JLabel("Postcode:"));

        //Fields...
        fields.put("title", new JTextField(10));
        fields.put("fName", new JTextField(10));
        fields.put("sName", new JTextField(10));
        //Consists of 3 labels + 3 JTFs for DD MM YYYY
        fields.put("bDate", new DatePanel());
        fields.put("cNum", new JTextField(10));
		fields.put("hNum", new JTextField(10));
		fields.put("pCode", new JTextField(10));

        submitB = new JButton("Submit");
        cancelB = new JButton("Cancel");
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

        //Buttons...
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
                if(validateInput()){
                    insertValues();
                }
            }
        });
    }

    private void insertValues(){
        //Get vars
        String title = ((JTextField) fields.get("title")).getText();
        String fName = ((JTextField) fields.get("fName")).getText();
        String sName = ((JTextField) fields.get("sName")).getText();
        String hNum = ((JTextField) fields.get("hNum")).getText();
        String bDate = ((DatePanel) fields.get("bDate")).getText();
        String cNum = ((JTextField) fields.get("cNum")).getText();
        String pCode = ((JTextField) fields.get("pCode")).getText();
        //QUERIES
        String getAddr = "SELECT * FROM Address WHERE houseNo = '"+
            hNum+"' AND postcode = '"+ pCode+"';";
                
        String dup = "SELECT * FROM Patient WHERE forename = '"+
            fName+
            "' AND surname = '"+
            sName+
            "' AND houseNo = '"+
            hNum+
            "' AND postcode = '"+
            pCode+"';";

        String regPatient = "INSERT INTO Patient"+
            "(title, forename, surname, birthDate, contactNo, "+
            "houseNo, postcode, amountOwed)"+
            " VALUES('"+
            title+"', '"+
            fName+"', '"+
            sName+"', '"+
            bDate+"', '"+
            cNum+"', '"+
            hNum+"', '"+
            pCode+"', '"+
            //A new patient doesn't owe any money.
            "0'"+");";

        //Grab data values
        System.out.println("Got data values, executing query.");

        //Check whether hNum + pCode exist in db
        String[] adrExRes = qHand.executeQuery(getAddr);
        System.out.println("Got possibly null addr");
        //Check whether fname+sname+hNum+pCode exists in db
        String[] dupRes = qHand.executeQuery(dup);
        System.out.println("Got poss null patient entry");

        //IF address exists and patient doesn't, continue
        if(adrExRes != null && dupRes == null){
            //Insert new row
            int status = qHand.executeUpdate(regPatient);
            if(status >= 0){    
                JOptionPane.showMessageDialog(parentF, "Patient "+
                        "successfully registered!");
            }
            parentF.dispose();
        }
        //ELSE
        else{ 
            JOptionPane.showMessageDialog(parentF, "The specified "+
                    "Address does not exist in the database! "+
                    "Please register the new address now.");
            //new Address reg form frame
            JFrame regFrame = new JFrame();
            regFrame.add(new RegisterAddress(regFrame));
            regFrame.setSize(RegisterAddress.PREF_DIMS);
            regFrame.setResizable(false);
            regFrame.setVisible(true);
        }
    }

    private boolean validateInput(){
        return true;
    }

    //Vars
    private JFrame parentF = null;
    private QueryHandler qHand = null;
    

    //Required components
    private LinkedHashMap<String, JLabel> labels = null;
    private LinkedHashMap<String, JComponent> fields = null;
    private JButton submitB = new JButton("Submit");
    private JButton cancelB = new JButton("Cancel");

    //Consts
    public static final int PANEL_WIDTH=230;
    public static final int PANEL_HEIGHT=245;
    public static final Dimension PREF_DIMS = new Dimension(250, 245);
}
