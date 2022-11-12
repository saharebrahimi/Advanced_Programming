package ir.aut.ceit.view;

import ir.aut.ceit.logic.MessageManager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

public class ServerConnection implements ActionListener {

    JButton jButton;
    private String ID;
    private static String ip;
    private static String name;


    public static void setNI(String name,String ip){
        new ServerConnection(name,ip);
    }
    public ServerConnection(String name,String ip) {

        JFrame jfM = new JFrame("Waiting for connection ...");
        jfM.setLayout(null);
        JSeparator separatorV = new JSeparator();
        separatorV.setOrientation(SwingConstants.HORIZONTAL);
        separatorV.setBounds(0, 120, 300, 2);
        jfM.add(separatorV);

        JSeparator separatorV2 = new JSeparator();
        separatorV2.setOrientation(SwingConstants.HORIZONTAL);
        separatorV2.setBounds(0, 25, 300, 2);
        jfM.add(separatorV2);


        JSeparator separatorV3 = new JSeparator();
        separatorV3.setOrientation(SwingConstants.HORIZONTAL);
        separatorV3.setBounds(0, 0, 300, 2);
        jfM.add(separatorV3);

        JSeparator separatorV4 = new JSeparator();
        separatorV4.setOrientation(SwingConstants.VERTICAL);
        separatorV4.setBounds(0, 0, 2, 120);
        jfM.add(separatorV4);

        JSeparator separatorV5 = new JSeparator();
        separatorV5.setOrientation(SwingConstants.VERTICAL);
        separatorV5.setBounds(293, 0, 2, 120);
        jfM.add(separatorV5);

        JLabel jLabel = new JLabel("Received Connection");
        jLabel.setBounds(90, 10, 200, 10);
        jfM.add(jLabel);
        JLabel jLabel1 = new JLabel(" Name : "+name);
        jLabel1.setBounds(30, 20, 200, 50);
        jfM.add(jLabel1);
        JLabel jLabel2 = new JLabel(" IP : "+ip);
        jLabel2.setBounds(30, 50, 200, 30);
        jfM.add(jLabel2);
        // jfM.add(jp1);
        JButton jButton = new JButton("Reject");
        jButton.setBounds(110, 90, 70, 20);
        jfM.add(jButton);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        JButton jButton1=new JButton("Accept");
        jButton1.setBounds(181,90,80,20);
        jfM.add(jButton1);
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random random=new Random();
                int n=random.nextInt(2);
                String starter= String.valueOf(n);
                MessageManager.setStart("1","1");
            }
        });
        jfM.setLocation(100, 50);
        jfM.setResizable(false);
        jfM.setVisible(true);
        jfM.setSize(300, 300);
        jfM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }




    @Override
    public void actionPerformed(ActionEvent e) {


    }
}
