package forms;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class TimePanel extends JPanel{
     public TimePanel(){
        this.fields = new LinkedHashMap<String, JTextField>();
        fields.put("hours", new JTextField(2));
        fields.put("minutes", new JTextField(2));
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(fields.get("hours"));
        add(fields.get("minutes"));
    }
    public String getHours(){
        return fields.get("hours").getText();
    }
    public String getMinutes(){
        return fields.get("minutes").getText();
    }
      
    private LinkedHashMap<String, JTextField> fields = null;
}
