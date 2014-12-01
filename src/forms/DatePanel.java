package forms;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class DatePanel extends JPanel{
     public DatePanel(){
        this.fields = new LinkedHashMap<String, JTextField>();
        fields.put("day", new JTextField(2));
        fields.put("month", new JTextField(2));
        fields.put("year", new JTextField(4));
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(fields.get("day"));
        add(fields.get("month"));
        add(fields.get("year"));
    }
    public String getDay(){
        return fields.get("day").getText();
    }
    public String getMonth(){
        return fields.get("month").getText();
    }
    public String getYear(){
        return fields.get("year").getText();
    }
    public String getText(){
        return getYear()+"-"+getMonth()+"-"+getDay();
    }
      
    private LinkedHashMap<String, JTextField> fields = null;
}
