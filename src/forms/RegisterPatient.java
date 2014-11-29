package forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class RegisterPatient extends JPanel{
    
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
        qHand = new QueryHandler("uname", "pwd");

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
                //Grab data values

                //Check whether hNum + pCode exist in db
                String[] adrExRes = qHand.executeQuery(null);
                //Check whether fname+sname+hNum+pCode exists in db
                String[] dupRes = qHand.executeQuery(null);

                //IF address exists and patient doesn't, continue
                if(adrExRes != null && dupRes == null){
                    //Insert new row
                    qHand.executeUpdate(null);
                    JOptionPane.showMessageDialog(parentF, "Patient "+
                            "successfully registered!");
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
        });
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
