package treatments;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import forms.QueryHandler;

public class TreatmentsTable extends JPanel{

    public TreatmentsTable(JFrame parentF){
        this.parentF = parentF;
        qHand = new QueryHandler("team016", "eabb6f40");
        setLayout(new BorderLayout());       
        initTable(); 
        scrollPane = new JScrollPane(treatTable);
        add(scrollPane);
    }

    public void initTable(){
        treatTable = new JTable(data, colNames);   
    }
    
    public void update(String patID){
        try{
        //Get appointments 
        //Get most recent appointment
            String recAppt = "";
            ResultSet rs = qHand.executeQueryRS(recAppt);
        //Extract date+time+partn from it

        //Get treatments matching the date+time+partn
            String relTreats = "";
            rs = qHand.executeQueryRS(relTreats);


        //Create new Object[2][x] table listing treatment name and cost
        }catch(InstantiationException e){
            throw new RuntimeException("Instantiation ex");
        }catch(IllegalAccessException e){
            throw new RuntimeException("Illegal access ex");
        }
        
        //Call call init table with update data[][]

        //Repaint window...
        //call removeAll() to get rid of all comps
        //create new scroll pane
        //put table in pane
        //revalidate
        //repaint
    }

    String[] colNames = {"Treatment", "Cost"};
    Object[][] data = {{"N/A", "N/A"}};

    private JFrame parentF = null;
    private JTable treatTable = null;

    private JScrollPane scrollPane = null;

    private QueryHandler qHand = null;
}
