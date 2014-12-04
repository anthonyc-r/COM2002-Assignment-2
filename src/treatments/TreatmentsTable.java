package treatments;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.sql.*;
import forms.QueryHandler;

public class TreatmentsTable extends JPanel{

    public TreatmentsTable(JFrame parentF, TreatmentsMain mainPanel){
        this.parentF = parentF;
        this.mainPanel = mainPanel;
        qHand = new QueryHandler("team016", "eabb6f40");
        setLayout(new BorderLayout());       
        initTable(); 
        scrollPane = new JScrollPane(treatTable);
        add(scrollPane);
    }

    public void initTable(){
        treatTable = new JTable(data, colNames);   
    }
    
    public void update(String patID) {
    		String treats = returnTreatmentsForPatient(patID);
        	data  = qHand.executeQueryFull(treats + ";");
        	if (data == null) {
                JOptionPane.showMessageDialog(parentF, "Cannot find patient.");
            }
        	//totalCost
        	String totCost = String.valueOf(returnTreatmentsTotalCost(data));
        	//costDue
        	String costDue = String.valueOf(returnTotalCostWithCarePlan(patID, data));
            //update field values for cost due and total owed
            mainPanel.updateFields(totCost, costDue);
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
    
	public String returnTreatmentsForPatient(String patId) {
    	String appForPatient = ("(SELECT date, startTime, partner FROM Appointment WHERE patientID = " + patId + ")");
    	String treatForPatient = ("SELECT treatName, date, startTime, partner, cost FROM Treatment WHERE (date, startTime, partner) IN " + appForPatient);
    	return treatForPatient;
    	//needs a semi-colon added at the end
    }
    
    //returns the total cost of all treatments under the patient
    private float returnTreatmentsTotalCost(Object[][] costs) {
    	float totalCost = 0;
    	for (int x=0;x<costs.length;x++) {
    	     for (int y=0;y<costs[0].length;y++) {
    	    	 String input = costs[x][y].toString();
    	           if (y==4) {
    	        	   //returns total cost
    	        	   totalCost = totalCost + Float.valueOf(input);
    	           }
    	     } 
    	}
    	return totalCost;
    }

	//applies patient's careplan to the total cost and returns a new value
    private float returnTotalCostWithCarePlan(String patID, Object[][] patTreats) {
    	String carePlan = "(SELECT planName FROM Subscription WHERE patientID = " + patID + ");";
		int checkUps = 0;
		int hygeineVisits = 0;
		int repairs = 0;
		int totalCheckUps = 0;
		int totalHygeine = 0;
		int totalRepairs = 0;
		float totalCost = 0;
		String[] treatNames = new String[patTreats.length];
		String[][] plan = qHand.executeQueryFull(carePlan);
		if (plan == null) {
			return returnTreatmentsTotalCost(patTreats);
		}
    	switch (plan[0][0]) {
    		case "NHS free plan" :
    			totalCheckUps = 2;
    			totalHygeine = 2;
    			totalRepairs = 6;
    			break;
    		case "maintenance plan" :
    			totalCheckUps = 2;
    			totalHygeine = 2;
    			totalRepairs = 0;
    			break;
    		case "oral health plan" :
    			totalCheckUps = 2;
    			totalHygeine = 4;
    			totalRepairs = 0;
    			break;
    		case "dental repair plan" :
    			totalCheckUps = 2;
    			totalHygeine = 2;
    			totalRepairs = 2;
    			break;
    	}
    	for (int x=0;x<patTreats.length;x++) {
			String treatName = patTreats[x][0].toString();
			if ((treatName.equals("check up")) && (checkUps != totalCheckUps)) {
				checkUps++;
				treatNames[x] = "N/A";
			} else if (treatName.equals("hygeine visit") && (hygeineVisits != totalHygeine)) {
				hygeineVisits++;
				treatNames[x] = "N/A";
			} else if (treatName.equals("repair") && (repairs != totalRepairs)) {
				repairs++;
				treatNames[x] = "N/A";
			}
    	}
    	for (int x=0;x<treatNames.length;x++) {
    		if (treatNames[x] != "N/A") {
    			String input = patTreats[x][4].toString();
    			totalCost = totalCost + Float.valueOf(input);
   	     	} 
    	}
    	return totalCost;
    }

    String[] colNames = {"Treatment", "Date"};
    Object[][] data = {{"N/A", "N/A"}};

    private JFrame parentF = null;
    private JTable treatTable = null;

    private JScrollPane scrollPane = null;

    private QueryHandler qHand = null;
    private TreatmentsMain mainPanel = null;
}