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
        //Get appointments 
        //Get most recent appointment
        String recAppt = "SELECT * FROM Appointment WHERE "+
            "patientID = '"+patID+"' ORDER BY date DESC LIMIT 1;";
        String[] recApptRes = qHand.executeQuery(recAppt);
        
        if(recApptRes == null){
            JOptionPane.showMessageDialog(parentF, "Cannot find patient.");
        }
        else{
            //Extract date+time+partn from it
            //SEE TABLE STRUCT, DATE=COL0, STIME=COL1, PARTN=COL4
            String date = recApptRes[0];
            String startTime = recApptRes[1];
            String partn = recApptRes[4];

            //Get treatments matching the date+time+partn
            String relatedTreats = "SELECT treatName, cost FROM Treatment WHERE "+
                "date = '"+date+"' AND startTime = '"+startTime+"' AND "+
                "partner = '"+partn+"';";
            String[][] treatResults = qHand.executeQueryFull(relatedTreats);
        
            if(treatResults != null){
                //Pass refernce to newly recovered data to data
                data = treatResults;
        
                //Call call init table with update data[][]
                initTable();
                //Repaint window...
                //call removeAll() to get rid of all comps
                removeAll();
                //create new scroll pane
                scrollPane = new JScrollPane(treatTable);
                //put table in pane
                add(scrollPane);
                //revalidate
                revalidate();
                //repaint
                repaint();
            }
            else{
                JOptionPane.showMessageDialog(parentF, "No treatments found.");
            }
        }
    }

    String[] colNames = {"Treatment", "Cost"};
    Object[][] data = {{"N/A", "N/A"}};

    private JFrame parentF = null;
    private JTable treatTable = null;

    private JScrollPane scrollPane = null;

    private QueryHandler qHand = null;
}
