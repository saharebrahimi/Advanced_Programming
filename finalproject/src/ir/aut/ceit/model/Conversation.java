package ir.aut.ceit.model;

import com.sun.deploy.panel.JSmartTextArea;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Conversation extends JPanel {
    private JPanel panel;

    public Conversation() {
        JFrame frame = new JFrame();
        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        frame.add(panel);
        frame.setVisible(true);


    }

    public void add(String name, JSONObject object) {
        JPanel panel1 = new JPanel();
        JLabel nam = new JLabel("name" + name);
        JButton but = new JButton("history");
        String resulttext = null;
        String finalresult = null;
        String result;
        String resultName = object.getString("name");
        JSONArray jsonArrayType = object.getJSONArray("type");
        for (int i = 0; i < jsonArrayType.length(); i++) {
            if (jsonArrayType.length() > 0) {
                JSONObject jsonObjectWeather = jsonArrayType.getJSONObject(i);
                resulttext = jsonObjectWeather.getString("text");
            }
            finalresult = resulttext + "            " + finalresult;
        }
        result = resultName + "             " + finalresult;

        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                JTextArea show = new JSmartTextArea();
                show.setText(result);
                frame.add(show);
                frame.setVisible(true);


            }
        });
        panel1.add(but);
        panel1.add(nam);

        panel.add(panel1);


    }


}
