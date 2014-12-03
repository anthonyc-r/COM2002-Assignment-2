package calendar;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import interfaces.DPanel;

public class CalendarMain extends JPanel implements DPanel{
    
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.add(new CalendarMain(frame));
        frame.setSize(CalendarMain.PREF_DIMS);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
    }
    
    public Dimension getPrefDims(){
        return CalendarMain.PREF_DIMS;
    }

    public CalendarMain(JFrame parentF){
        //Layout needs fixing
        setLayout(null);
        CalendarPane cPane = new CalendarPane(parentF);
        SelectionPane sPane = new SelectionPane(parentF, cPane);
        add(sPane);
        add(cPane);
        sPane.setBounds(10, 10, 790, 40);
        cPane.setBounds(10, 40, 790, 590);
    }

    public static final Dimension PREF_DIMS = new Dimension(840, 680);
}
