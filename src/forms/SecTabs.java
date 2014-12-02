package DBTEST04;


import javax.swing.*;

public class SecTabs extends JFrame {
	
	public SecTabs () {
		initUI();
	}
	
	private void initUI() {
		JFrame frame = new JFrame();
		JTabbedPane tabbedPane = new JTabbedPane();
		String[] tabText = {
			"View Appointments",
			"Book Appointment",
			"Subscribe Patient",
			"Review Treatments",
			"Modify Owed",
			"Register Patient",
			"Register Address"
		};
		JComponent[] tabComponents = {
			null,
			new BookAppointment(frame),
			new SubscribePatient(frame),
			null,
			null,
			new RegisterPatient(frame),
			new RegisterAddress(frame),
		};
		for (int i=0; i<tabText.length; i++) {
			tabbedPane.addTab(tabText[i], tabComponents[i]);
		}
		
		add(tabbedPane);
		
		setTitle("place-holder");
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		JFrame panel = new SecTabs();
		panel.setVisible(true);
	}

}
