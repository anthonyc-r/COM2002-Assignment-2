package treatments;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TreatmentsTable extends JPanel{

    public TreatmentsTable(JFrame parentF){
        this.parentF = parentF;
        
        initTable(); 
        scrollPane = new JScrollPane(treatTable);
        add(scrollPane);
    }

    public void initTable(){
        treatTable = new JTable(data, colNames);   
    }
    
    public void update(String patientID){
        //Get appointments 
        //Set new data values
        //Call init table again
        //removeAll()
        //addTable
        //revalidate
        //repaint
    }

    String[] colNames = {"Treatment", "Cost", "Paid"};
    Object[][] data = {{"N/A", "N/A", "N/A"}};

    private JFrame parentF = null;
    private JTable treatTable = null;

    private JScrollPane scrollPane = null;
}
