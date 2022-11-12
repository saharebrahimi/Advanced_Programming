package ir.aut.ceit.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ClientConnection implements ActionListener {//implementando el listener de eventos
    //  JPanel jp1;
    JButton jButton;
    private String name;
    private String ID;
    public void setName(String name){
        this.name=name;
    }
    public void setID(){
        this.ID=ID;
    }
    public ClientConnection() {

        JFrame jfM = new JFrame("Please wait ...");
        jfM.setLayout(null);


        JLabel jLabel1 = new JLabel("Waiting for the host to join ...");
        jLabel1.setBounds(80, 25, 200, 50);
        jfM.add(jLabel1);

        JButton jButton = new JButton("Cancel");
        jButton.setBounds(185, 90, 90, 20);
        jfM.add(jButton);

        jfM.setLocation(100, 50);
        jfM.setResizable(false);
        jfM.setVisible(true);
        jfM.setSize(350, 180);
        jfM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }




    @Override
    public void actionPerformed(ActionEvent e) {


    }
}