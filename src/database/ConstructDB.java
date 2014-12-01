package database;

import java.sql.*;

public class ConstructDB {

	public static void main(String[] args) {

		//Connect to DB

		Connection conn = null;
        Statement stmt = null;

		try {
			//Class.forName("org.gjt.mm.mysql.Driver").newInstance();

			String DB="jdbc:mysql://stusql.dcs.shef.ac.uk/dbname?user=team016&password=eabb6f40";

			conn = DriverManager.getConnection(DB);
			System.out.println("Connected to database succesfully");

		//Create Tables

		stmt = conn.createStatement();

		//Addresses
		
		String createAddress = 	"CREATE TABLE Address(" +
								"(houseNo INTEGER not NULL, " +
								"postcode VARCHAR[8] not NULL, " +
								"street VARCHAR[30], " +
								"district VARCHAR[30], " +
								"city VARCHAR[30], " + 
								"PRIMARY KEY (houseNo, postcode));";

		//Patients

		String createPatient = 	"CREATE TABLE Patient" +
								"(patientID INTEGER not NULL, " +
								"title VARCHAR[10], " +
								"forename VARCHAR[30], " +
								"surname VARCHAR[30], " +
								"birthDate DATE, " +
								"contactNo VARCHAR[10], " +
								"houseNo INTEGER, " +
								"postcode VARCHAR[8]" + 
								"amountOwed FLOAT, " +
								"PRIMARY KEY (patientID), " +
								"FOREIGN KEY (houseNo, postcode) REFERENCES Address (houseNo, postcode));";

		//Care Plans	

		String createCarePlan = "CREATE TABLE CarePlan " +
								"(planName VARCHAR[30] not NULL, " +
								"checkUps INTEGER, " +
								"hygeineVisits INTEGER, " +
								"repairs INTEGER, " +
								"cost FLOAT, " + 
								"PRIMARY KEY (planName));";

		//Subscriptions - linker between patients and Patient and CarePlan tables

		String createSub =		"CREATE TABLE Subscription" +
								"(patientID INTEGER not NULL, " +
								"planName VARCHAR[30] not NULL, " +
								"PRIMARY KEY (patientID, planName), " +
								"FOREIGN KEY (patientID) REFERENCES Patient (patientID)," +
								"FOREIGN KEY (planName) REFERENCES CarePlan (planName));";

		//Appointments

		String createAppoint = 	"CREATE TABLE Appointment(" +
								"(date DATE not NULL, " +
								"startTime TIME, " +
								"endTime TIME, " +
								"patientID INTEGER, " +
								"partner VARCHAR[30], " +
								"PRIMARY KEY (date, start, partner)," +
								"FOREIGN KEY (patientID) REFERENCES Patient (patientID));";

		//Treatments

		String createTreatment = "CREATE TABLE Treatment " +
								"(treatName VARCHAR[30] not NULL, " +
								"(date DATE not NULL, " +
								"startTime TIME, " +
								"partner VARCHAR[30], " +
								"cost FLOAT, " + 
								"PRIMARY KEY (treatment)," +
								"FOREIGN KEY (date, startTime, partner) REFERENCES Appointment (date, startTime, partner));";

		stmt.executeUpdate(createPatient);
        stmt.executeUpdate(createAddress);
        stmt.executeUpdate(createTreatment);
        stmt.executeUpdate(createSub);
        stmt.executeUpdate(createCarePlan);
        stmt.executeUpdate(createAppoint);


		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
}
