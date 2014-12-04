package forms;


import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import treatments.TreatmentsMain;
import calendar.CalendarMain;
import interfaces.DPanel;
import treatments.TreatmentsMain;

public class SecInterface extends JFrame {
	
	public SecInterface () {
		initUI();
	}
	
	private void initUI() {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1, 5, 5));
		String[] buttonText = {
			"View Appointments",
			"Book Appointment",
			"Subscribe Patient",
			"Review Treatments",
			"Modify Owed",
			"Register Patient",
			"Register Address",
            "Get Patient ID"
		};
		JButton[] buttons = new JButton[buttonText.length];
		final DPanel [] listeners = {
            new CalendarMain(frame),
            new BookAppointment(frame),
            new SubscribePatient(frame),
            new TreatmentsMain(frame),
            new ModifyOwed(frame),
            new RegisterPatient(frame),
            new RegisterAddress(frame),
            new GetID(frame)
		};

		for (int i=0; i<buttonText.length; i++) {
			buttons[i] = new JButton();
			panel.add(buttons[i]);
			buttons[i].setText(buttonText[i]);
			final int j = i;
			buttons[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DPanel child = listeners[j];
					JFrame frame = new JFrame();
					frame.add((JPanel)child);
					frame.setSize(((DPanel)child).getPrefDims());
					frame.setResizable(false);
					frame.setVisible(true);
				}
			});
		}
		
		add(panel);
		
		setTitle("Secretary's Interface");
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		JFrame panel = new SecInterface();
		panel.setVisible(true);
	}

}
