package forms;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import interfaces.DPanel;
import calendar.CalendarMain;

public class PartInterface extends JFrame {
	
	public PartInterface() {
		initUI();
	}
	
	private void initUI() {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1, 5, 5));
		String[] buttonText = {
			"View Appointments",
			"Record Treatment",
		};
		JButton[] buttons = new JButton[buttonText.length];
		final DPanel [] listeners = {
            new CalendarMain(frame),
            new RecordTreatment(frame),
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
					frame.setSize(child.getPrefDims());
					frame.setResizable(true);
					frame.setVisible(true);
				}
			});
		}
		
		add(panel);
		
		setTitle("Partners' Interface");
		setSize(500, 142);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		JFrame panel = new PartInterface();
		panel.setVisible(true);
	}

}
