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
		JFrame frame = null;
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
		
        final JFrame[] frames = {
            new JFrame(),
            new JFrame(),
            new JFrame(),
            new JFrame(),
            new JFrame(),
            new JFrame(),
            new JFrame(),
            new JFrame()
        };
        
        final DPanel [] listeners = {
            new CalendarMain(frames[0]),
            new BookAppointment(frames[1]),
            new SubscribePatient(frames[2]),
            new TreatmentsMain(frames[3]),
            new ModifyOwed(frames[4]),
            new RegisterPatient(frames[5]),
            new RegisterAddress(frames[6]),
            new GetID(frames[7])
		};

		for (int i=0; i<buttonText.length; i++) {
            buttons[i] = new JButton();
			panel.add(buttons[i]);
			buttons[i].setText(buttonText[i]);
			final int j = i;
			buttons[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DPanel child = listeners[j];
					//JFrame frame = new JFrame();
					frames[j].add((JPanel)child);
					frames[j].setSize(((DPanel)child).getPrefDims());
					frames[j].setResizable(true);
					frames[j].setVisible(true);
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
