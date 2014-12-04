package calendar;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import enums.Partner;
import forms.QueryHandler;

public class AppointmentListPane extends JPanel{
    public AppointmentListPane(JFrame parentF, int day, int month, 
            int year, Partner partner){
        this.parentF = parentF;
        this.day = day;
        this.month = month;
        this.year = year;
        this.partner = partner;
        
        setLayout(new BorderLayout());

        qHand = new QueryHandler("team016", "eabb6f40");
        initTable();

        tabScroll = new JScrollPane(appTable);
        add(tabScroll);
    }

    private void initTable(){
        String getAppointments = "SELECT "+
            "patientID, partner, startTime, endTime "+
            "FROM Appointment "+
            "WHERE date = '"+year+"-"+month+"-"+day+"' "+
            "AND partner = '"+partner+"';";
        String[][] appResults = qHand.executeQueryFull(getAppointments);
        //if null no appointments
        if(appResults == null){
            data = new String[1][4];
            data[0][0] = "";
            data[0][1] = "";
            data[0][2] = "";
            data[0][3] = "";
        }
        else{
            data = appResults;
        }

        //make table
        appTable = new JTable(data, tabHead);
    }

    private JFrame parentF = null;
    private JTable appTable = null;
    private JScrollPane tabScroll = null;

    private int day, month, year;
    private Partner partner = null;
    private String[] tabHead = {"PatientID", "Partner", "Start", "End"};
    private String[][] data = null;
    private QueryHandler qHand = null;
    public static final Dimension PREF_DIMS = new Dimension(300, 600);
}
