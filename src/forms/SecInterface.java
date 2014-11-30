package DBTEST01;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SecInterface extends JFrame {
	
	public SecInterface () {
		initUI();
	}
	
	private void initUI() {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(7, 1, 5, 5));
		String[] buttonText = {
			"View Appointments",
			"Book Appointment",
			"Subscribe Patient",
			"Review Treatments",
			"Modify Owed",
			"Register Patient",
			"Register Address"
		};
		JButton[] buttons = new JButton[buttonText.length];
		JPanel [] listeners = {
            null,
            new BookAppointment(frame),
            null,
            null,
            null,
            null,
            null,
		};

		for (int i=0; i<buttonText.length; i++) {
			buttons[i] = new JButton();
			panel.add(buttons[i]);
			buttons[i].setText(buttonText[i]);
			final int j = i;
			buttons[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JPanel child = listeners[j];
					JFrame frame = new JFrame();
					frame.add(child);
					frame.setSize(200, 200);
					frame.setResizable(true);
					frame.setVisible(true);
				}
			});
		}
		
		add(panel);
		
		setTitle("place-holder");
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		JFrame panel = new SecInterface();
		panel.setVisible(true);
	}

}