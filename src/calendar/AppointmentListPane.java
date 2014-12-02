package calendar;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AppointmentListPane extends JPanel{
    public AppointmentListPane(JFrame parentF, int day, int month, int year){
        this.parentF = parentF;
        this.day = day;
        this.month = month;
        this.year = year;

    }

    private JFrame parentF = null;
    private int day, month, year;
    public static final Dimension PREF_DIMS = new Dimension(300, 600);
}
