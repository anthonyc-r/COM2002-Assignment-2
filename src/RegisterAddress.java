package forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class RegisterAddress extends JPanel{
    //testing
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new RegisterAddress(frame));
        //NOTE: Adding layout=null here screws up the JPanel layout.
        frame.setSize(230, 245);
        frame.setResizable(true); 
        frame.setVisible(true);
    }

    public RegisterAddress(JFrame parentF){
        labels = new LinkedHashMap<String, JLabel>();
        fields = new LinkedHashMap<String, JTextField>(); 
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
        labels.put("hNum", new JLabel("House No.:"));
        labels.put("pCode", new JLabel("Postcode:"));
        labels.put("street", new JLabel("Street:"));
        labels.put("dist", new JLabel("District:"));
        labels.put("city", new JLabel("City:"));
        
        //Fields...
        fields.put("hNum", new JTextField(10));
        fields.put("pCode", new JTextField(10));
        fields.put("street", new JTextField(10));
        fields.put("dist", new JTextField(10));
        fields.put("city", new JTextField(10));
    }

    private void addFields(){
        Iterator lit = labels.entrySet().iterator();
        Iterator fit = fields.entrySet().iterator();

        while(lit.hasNext() && fit.hasNext()){
            Map.Entry lPair = (Map.Entry)lit.next();
            Map.Entry fPair = (Map.Entry)fit.next();

            add((JLabel)lPair.getValue());
            add((JTextField)fPair.getValue());
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
                //Check if it already exists...
                String[] res = qHand.executeQuery(null);
                //if not...
                if(res == null){
                    //insert into DB
                    qHand.executeUpdate(null);
                    JOptionPane.showMessageDialog(parentF, "Successfully "+
                            "inserted new address.");
                    parentF.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(parentF, "Address "+
                            "already exists in database!");
                }
            }
        });
    }

    //vars
    private QueryHandler qHand = null;

    //Components...
    private LinkedHashMap<String, JLabel> labels = null;
    private LinkedHashMap<String, JTextField> fields = null;
    private JButton submitB = new JButton("Submit");
    private JButton cancelB = new JButton("Cancel");
    private JFrame parentF = null;

    //Constants...
    public static final int PANEL_WIDTH = 230;
    public static final int PANEL_HEIGHT = 245;
    public static final Dimension PREF_DIMS = new Dimension(230, 180);
}
