package calendar;

import enums.Partner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.GregorianCalendar;
import java.util.Calendar;

public class CalendarPane extends JPanel{

    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //NOTE: adding layout=null here screws up JPanel layout
        frame.add(new CalendarPane(frame));
        frame.setSize(CalendarPane.PREF_DIMS);
        frame.setResizable(true); 
        frame.setVisible(true);
    }

    public CalendarPane(JFrame parentF){
        this.parentF = parentF;
        setLayout(new GridLayout(0, 7)); 
        
        //init nonfield values
        cal = new GregorianCalendar();
        //Set default values.
        partner = Partner.DENTIST;
        //defaults to current date.
        month = String.valueOf(cal.MONTH);
        year = String.valueOf(cal.YEAR);

        initComponents();
        addComponents();
    }

    public void setDate(String month, String year){
        this.month = month;
        this.year = year;
    }

    private void initComponents(){
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        
        dayLabels = new JLabel[7];        
        dayLabels[0] = new JLabel("Sunday");
        dayLabels[1] = new JLabel("Monday");
        dayLabels[2] = new JLabel("Tuesday");
        dayLabels[3] = new JLabel("Wednesday");
        dayLabels[4] = new JLabel("Thursday");
        dayLabels[5] = new JLabel("Friday");
        dayLabels[6] = new JLabel("Satuday");
        

        dayButtons = new JButton[daysInMonth];
        //init the buttons
        for(int i=0; i<daysInMonth; i++){
            dayButtons[i] = new JButton(String.valueOf(i+1));
        }
    }

    private void addComponents(){
        cal.set(Calendar.DAY_OF_MONTH, 0);
        int offset = cal.get(Calendar.DAY_OF_WEEK);
        int numButtons = dayButtons.length;
        System.out.println(offset);
        //Add labels.
        for(int i=0; i<7; i++){
            add(dayLabels[i]);
        }

        //pad the first few days 
        for(int i=0; i<offset; i++){
            add(new JComponent(){});
        }
        //proceed to add rest of days
        for(int i=0; i<numButtons; i++){
            add(dayButtons[i]);
        }
    }

    //Can't do it like this...
    /*private void addActionListeners(){
        int days = dayButtons.length;
        for(int i=0; i<days; i++){
            dayButtons[i].addActionListener(new ActionListener(){
                public void actionPerformed(){
                   JFrame listFrame = new JFrame(); 
                   listFrame.add(new AppointmentListPane(parentF, i+1, 
                               Integer.valueOf(month),
                               Integer.valueOf(year)));
                   listFrame.setSize(AppointmentListPane.PREF_DIMS);

                }
            });
        }
    }*/
    
    private String month = null;
    private String year = null;
    private Partner partner = null;

    private GregorianCalendar cal = null;
    
    private JFrame parentF = null;
    private JLabel[] dayLabels = null;
    private JButton[] dayButtons = null;

    public static final Dimension PREF_DIMS = new Dimension(800, 600);

}
