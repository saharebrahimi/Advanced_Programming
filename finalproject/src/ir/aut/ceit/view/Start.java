package ir.aut.ceit.view;

import ir.aut.ceit.logic.BattleShip;
import ir.aut.ceit.logic.MessageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Start {
    private MessageManager messageManager;
    // Various GUI components and info
    public static JFrame mainFrame = null;
    public static JTextField ipField = null;
    public static JTextField nameField = null;
    public static JTextField portField = null;
    public static JTextField portField2 = null;
    public static JRadioButton hostOption = null;
    public static JRadioButton guestOption = null;
    public static JButton startButton = null;
    public static JButton exitButton = null;

    // Connection info

    public  boolean isHost = false;
    public  static int mPort;
    public String mIp;
    public static String mName;
    public BattleShip battleShip;

    private  JPanel initOptionsPane() {

        JPanel pane;

        // Create an options pane
        JPanel optionsPane = new JPanel(new GridLayout(7, 1));


        // name address input
        pane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pane.add(new JLabel("Name:"));
        nameField = new JTextField(10);
        //   nameField.setText(hostIP);
        nameField.setEditable(true);
        pane.add(nameField);
        optionsPane.add(pane);

        hostOption = new JRadioButton("Host", false);
        hostOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hostOption.setSelected(true);
                guestOption.setSelected(false);
                isHost = true;

            }
        });

        pane = new JPanel(new GridLayout(1, 1));
        pane.add(hostOption);
        optionsPane.add(pane);


        // Port input
        pane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pane.add(new JLabel("Port:"));
        portField = new JTextField(10);
        portField.setEditable(true);
        //  portField.setText((new Integer(mPort)).toString());
        pane.add(portField);


        optionsPane.add(pane);

        // Host/guest option
        guestOption = new JRadioButton("Guest", false);
        guestOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guestOption.setSelected(true);
                hostOption.setSelected(false);
                isHost = false;

            }
        });

        pane = new JPanel(new GridLayout(1, 1));
        pane.add(guestOption);
        optionsPane.add(pane);


        // IP address input
        pane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pane.add(new JLabel("IP:"));
        ipField = new JTextField(10);
        //  ipField.setText(hostIP);
        ipField.setEditable(true);
        pane.add(ipField);
        optionsPane.add(pane);

        //
        pane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pane.add(new JLabel("Port:"));
        portField2 = new JTextField(10);
        portField2.setEditable(true);
        // portField2.setText((new Integer(mPort)).toString());
        pane.add(portField2);
        optionsPane.add(pane);


        // Connect/disconnect buttons
        JPanel buttonPane = new JPanel(new GridLayout(1, 2));

        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mName = nameField.getText();
                if (isHost) {

                    mPort = Integer.parseInt((portField.getText()));
                    try {

                        messageManager=new MessageManager(mPort);
                        messageManager.setHost(true);

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }


                } else {
                    mPort = Integer.parseInt((portField2.getText()));
                    mIp = ipField.getText();
                    try {
                        messageManager=new MessageManager(mIp,mPort);
                        messageManager.setName(mName,mIp);
                        messageManager.setHost(false);
                        ClientConnection clientConnection=new ClientConnection();

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }

        });

        startButton.setEnabled(true);
        exitButton = new JButton("Exit");
        exitButton.setEnabled(true);
        buttonPane.add(startButton);
        buttonPane.add(exitButton);
        optionsPane.add(buttonPane);


        return optionsPane;
    }

    public void initGUI() {

        JPanel optionsPane = initOptionsPane();



        JPanel mainPane = new JPanel(new FlowLayout());
        optionsPane.setSize(500, 500);
        mainPane.add(optionsPane);

        mainFrame = new JFrame("Select Connection Mode");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setContentPane(mainPane);
        mainFrame.setSize(500, 300);
        // mainFrame.setLocation(300, 300);
        //  mainFrame.pack();

        mainFrame.setVisible(true);
    }


}


