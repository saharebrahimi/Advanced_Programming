package ir.aut.ceit.logic;

import ir.aut.ceit.model.ChatHistory;
import ir.aut.ceit.model.Conversation;
import ir.aut.ceit.view.Start;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class BattleShip extends JFrame {

    private static ArrayList<JButton> myList = new ArrayList<JButton>();
    private int rotatingCounter;

    private JPanel myShipsPanel;
    private static JTextArea chatHistory;
    private JTextArea sendBar;
    private boolean shipPress1 = false;
    private boolean shipPress2 = false;
    private boolean shipPress3 = false;
    private boolean shipPress4 = false;

    private int countShip1 = 0;
    private int countShip2 = 0;
    private int countShip3 = 0;
    private int countShip4 = 0;


    private static JButton reset;
    private static JButton ready;
    private static JButton rotate;
    private static JButton clear;
    private static JButton ship1;
    private static JButton ship2;
    private static JButton ship3;
    private static JButton ship4;
    private static JPanel startPanel;
    public static ArrayList enemyArrayList;
    private static ArrayList<Integer> myArrayList;
    private static String indexHit;
    private static ArrayList<Integer> saveArrayList;
    public static ArrayList<Integer> drawArrayList;
    public static boolean checkTurn;
    private static String mChat;
    public static JButton leave;

    static void setCheckTurn(boolean flag) {
        if (flag) {
            for (JButton aMyList : myList) {
                aMyList.setBackground(Color.black);
            }
        }
    }

    /*public static void setDrawArrayList(ArrayList<Integer> drawList) {
        String tempDraw = String.valueOf(drawList);
        String draw = tempDraw.substring(1, tempDraw.length() - 1);
        char[] drawChars = draw.toCharArray();
        for (int i = 0; i < myList.size(); i++) {
            if (drawChars[3 * i] == '1') {
                myList.get(i).setBackground(Color.blue);
            } else {
                myList.get(i).setBackground(Color.black);
            }
        }

    }*/

    static void setIndexHit(String index) {
        indexHit = index;
    }

    private static void setMyArrayList(ArrayList<Integer> mArrayList) {
        myArrayList = mArrayList;
    }

    public static boolean whostart;

    static void setWhostart(boolean flag, String arrayList) throws InterruptedException {
        if (flag) {
            MessageManager.setMainList(myArrayList);
            saveArrayList = new ArrayList<>();
            String tempArrayList = arrayList.substring(1, arrayList.length() - 1);
            char[] lastArray = tempArrayList.toCharArray();
            for (JButton aMyList : myList) {
                aMyList.setBackground(Color.black);

            }
            for (int i = 0; i < 100; i++) {
                saveArrayList.add(i, 0);
            }
            for (int j = 0; j < myList.size(); j++) {
                int finalJ = j;
                int finalJ1 = j;
                myList.get(j).addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        String index = String.valueOf(finalJ1);
                        MessageManager.sendHitMessage(index);

                        MessageManager.settIsHit(true);


                        if (lastArray[3 * finalJ] == '1') {
                            myList.get(finalJ).setBackground(Color.green);
                            saveArrayList.add(finalJ, 2);
                            try {
                                TimeUnit.SECONDS.sleep(3);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }

                            String tempDraw = String.valueOf(myArrayList);
                            String draw = tempDraw.substring(1, tempDraw.length() - 1);
                            char[] drawChars = draw.toCharArray();
                            for (int i = 0; i < myList.size(); i++) {
                                if (drawChars[3 * i] == '1') {
                                    myList.get(i).setBackground(Color.blue);
                                } else {
                                    myList.get(i).setBackground(Color.black);
                                }
                            }
                            MessageManager.setTurnList(saveArrayList);


                        } else {
                            myList.get(finalJ).setBackground(Color.red);
                            saveArrayList.add(finalJ, 3);
                            try {
                                TimeUnit.SECONDS.sleep(3);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                            String tempDraw = String.valueOf(myArrayList);
                            String draw = tempDraw.substring(1, tempDraw.length() - 1);
                            char[] drawChars = draw.toCharArray();
                            for (int i = 0; i < myList.size(); i++) {
                                if (drawChars[3 * i] == '1') {
                                    myList.get(i).setBackground(Color.blue);
                                } else {
                                    myList.get(i).setBackground(Color.black);
                                }
                            }
                            MessageManager.setTurnList(saveArrayList);
                        }

                    }
                });
            }
        } else {

            String tempArray = String.valueOf(myArrayList);
            String mArray = tempArray.substring(1, tempArray.length() - 1);
            char[] myChars = mArray.toCharArray();
            int nIndex = Integer.parseInt(indexHit);
            if (myChars[3 * nIndex] == '1') {
                myList.get(nIndex).setBackground(Color.red);
            } else {
                myList.get(nIndex).setBackground(Color.green);
            }
        }


    }

    static void setIsReady(boolean isready) {
        if (isready) {
            reset.setVisible(false);
            ready.setVisible(false);
            rotate.setVisible(false);
            clear.setVisible(false);
            ship1.setVisible(false);
            ship2.setVisible(false);
            ship3.setVisible(false);
            ship4.setVisible(false);
            leave = new JButton("Leave");
            startPanel.add(leave);
            ArrayList<Integer> arrayList = new ArrayList<>();
            for (int i = 0; i < myList.size(); i++) {
                if (myList.get(i).getBackground() != Color.black) {
                    arrayList.add(i, 1);
                } else {
                    arrayList.add(i, 0);
                }

            }
            setMyArrayList(arrayList);
            String arrayListEnemy = String.valueOf(arrayList);
            MessageManager.setArrayList(arrayListEnemy);
        }
    }

    static void setChat(String chat) {
        mChat = chat;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH.mm.ss");
        Date date = new Date();
        String time = simpleDateFormat.format(date);
        chatHistory.append(mChat + "\n" + time + "\n");
        ChatHistory chatHistory = new ChatHistory(mChat + "\n" + time + "\n", Start.mName);


    }


    BattleShip() {
        initializeMenu();
        initializeMyBoard();
        initializeChatPanel();
        initializeStartPanel();
        initializeShipPanel();
        initializeComponents();
        setVisible(true);
    }

    private void initializeMenu() {
        JPanel start = new JPanel();
        start.setBounds(0, 0, 100, 50);
        getContentPane().add(start);
        JMenuBar menubar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem history = new JMenuItem("Conversations History");
        JMenu helpMenu = new JMenu("Help");
        fileMenu.add(history);
        history.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChatHistory.finalJson();
                Conversation conversation = new Conversation();
                conversation.add(Start.mName, ChatHistory.finalmessage);


            }
        });
        menubar.add(fileMenu);
        menubar.add(helpMenu);
        start.add(menubar);

    }


    private void initializeMyBoard() {
        myShipsPanel = new JPanel();
        myShipsPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Your ships:", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
        myShipsPanel.setBounds(200, 11, 449, 369);
        getContentPane().add(myShipsPanel);


        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                JButton button = new JButton();
                myList.add(button);

            }
        }
        for (JButton button : myList) {
            button.setText(" ");
            button.setBackground(Color.black);
            button.setEnabled(false);
            myShipsPanel.add(button);
        }


        JList myBoardList = new JList();
        myShipsPanel.add(myBoardList);
        myBoardList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        myBoardList.setVisibleRowCount(11);
        myBoardList.setFixedCellHeight(30);
        myBoardList.setFixedCellWidth(29);


    }

    private void initializeShipPanel() {
        JPanel shipPanel = new JPanel();
        shipPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Ships type", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
        shipPanel.setBounds(10, 400, 350, 200);
        getContentPane().add(shipPanel);
        ship1 = new JButton("1");
        clear = new JButton("Clear");
        ship1.setForeground(Color.black);
        ship1.setBackground(Color.cyan);
        ship2 = new JButton("  2  ");
        ship2.setBackground(Color.blue);
        ship2.setForeground(Color.black);
        ship3 = new JButton("    3     ");
        ship3.setBackground(Color.magenta);
        ship3.setForeground(Color.black);
        ship4 = new JButton("        4        ");
        ship4.setBackground(Color.pink);
        ship4.setForeground(Color.black);
        JLabel space = new JLabel("   ");
        BoxLayout layout = new BoxLayout(shipPanel, BoxLayout.Y_AXIS);
        shipPanel.setLayout(layout);
        shipPanel.add(space);
        shipPanel.add(ship1);
        shipPanel.add(ship2);
        shipPanel.add(ship3);
        shipPanel.add(ship4);
        shipPanel.add(clear);
        ship1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shipPress1 = true;
                shipPress2 = false;
                shipPress3 = false;
                shipPress4 = false;
                for (int i = 0; i < myList.size(); i++) {
                    int finalI = i;

                    myList.get(i).addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (myList.get(finalI).getBackground() == Color.black && shipPress1)
                                countShip1++;
                            if (shipPress1 && (countShip1 < 5))
                                myList.get(finalI).setBackground(Color.cyan);

                        }

                        @Override
                        public void mousePressed(MouseEvent e) {

                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {

                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {

                        }

                        @Override
                        public void mouseExited(MouseEvent e) {

                        }
                    });
                }
            }
        });

        ship2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shipPress2 = true;
                shipPress1 = false;
                shipPress3 = false;
                shipPress4 = false;
                for (int i = 0; i < myList.size(); i++) {
                    int finalI = i;
                    int x = finalI % 10;
                    myList.get(i).addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (rotatingCounter % 2 == 0) {
                                if ((myList.get(finalI).getBackground() == Color.black && myList.get(finalI + 1).getBackground() == Color.black) && shipPress2)
                                    countShip2++;
                                if (x < 9 && shipPress2 && (countShip2 < 4)) {
                                    if (myList.get(finalI).getBackground() == Color.black && myList.get(finalI + 1).getBackground() == Color.black) {
                                        myList.get(finalI).setBackground(Color.blue);
                                        myList.get(finalI + 1).setBackground(Color.blue);
                                    }

                                }
                            } else {
                                if ((myList.get(finalI).getBackground() == Color.black && myList.get(finalI + 10).getBackground() == Color.black) && shipPress2)
                                    countShip2++;
                                int y = finalI / 10;
                                if (y < 9 && shipPress2 && (countShip2 < 4)) {
                                    if (myList.get(finalI).getBackground() == Color.black && myList.get(finalI + 10).getBackground() == Color.black) {
                                        myList.get(finalI).setBackground(Color.blue);
                                        myList.get(finalI + 10).setBackground(Color.blue);
                                    }

                                }
                            }
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {

                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {

                        }

                        @Override

                        public void mouseEntered(MouseEvent e) {

                        }

                        @Override
                        public void mouseExited(MouseEvent e) {

                        }
                    });
                }
            }
        });

        ship3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shipPress3 = true;
                shipPress2 = false;
                shipPress1 = false;
                shipPress4 = false;
                for (int i = 0; i < myList.size(); i++) {
                    int finalI = i;
                    int x = finalI % 10;
                    myList.get(i).addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {

                            if (rotatingCounter % 2 == 0) {
                                if ((myList.get(finalI).getBackground() == Color.black && myList.get(finalI + 1).getBackground() == Color.black &&
                                        myList.get(finalI + 2).getBackground() == Color.black& shipPress3))
                                        countShip3++;
                                if (x < 8 && shipPress3 && (countShip3 < 3)) {
                                    if (myList.get(finalI).getBackground() == Color.black && myList.get(finalI + 1).getBackground() == Color.black &&
                                            myList.get(finalI + 2).getBackground() == Color.black) {

                                        myList.get(finalI).setBackground(Color.magenta);
                                        myList.get(finalI + 1).setBackground(Color.magenta);
                                        myList.get(finalI + 2).setBackground(Color.magenta);
                                    }
                                }
                            } else {
                                if ((myList.get(finalI).getBackground() == Color.black && myList.get(finalI + 10).getBackground() == Color.black &&
                                        myList.get(finalI + 20).getBackground() == Color.black) && shipPress3)
                                    countShip3++;
                                int y = finalI / 10;
                                if (y < 8 && shipPress3 && (countShip3 < 3)) {
                                    if (myList.get(finalI).getBackground() == Color.black && myList.get(finalI + 10).getBackground() == Color.black &&
                                            myList.get(finalI + 20).getBackground() == Color.black) {
                                        myList.get(finalI).setBackground(Color.magenta);
                                        myList.get(finalI + 10).setBackground(Color.magenta);
                                        myList.get(finalI + 20).setBackground(Color.magenta);
                                    }
                                }
                            }
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {

                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {

                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {

                        }

                        @Override
                        public void mouseExited(MouseEvent e) {

                        }
                    });
                }
            }
        });
        ship4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shipPress4 = true;
                shipPress2 = false;
                shipPress3 = false;
                shipPress1 = false;
                for (int i = 0; i < myList.size(); i++) {
                    int finalI = i;
                    myList.get(i).addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {

                            int x = finalI % 10;
                            if (rotatingCounter % 2 == 0) {
                                if ((myList.get(finalI).getBackground() == Color.black && myList.get(finalI + 1).getBackground() == Color.black &&
                                        myList.get(finalI + 2).getBackground() == Color.black && myList.get(finalI + 3).getBackground() == Color.black) && shipPress4) {
                                    countShip4++;
                                }
                                if (x < 7 && shipPress4 && (countShip4 < 2)) {
                                    if (myList.get(finalI).getBackground() == Color.black && myList.get(finalI + 1).getBackground() == Color.black &&
                                            myList.get(finalI + 2).getBackground() == Color.black && myList.get(finalI + 3).getBackground() == Color.black) {
                                        myList.get(finalI).setBackground(Color.pink);
                                        myList.get(finalI + 1).setBackground(Color.pink);
                                        myList.get(finalI + 2).setBackground(Color.pink);
                                        myList.get(finalI + 3).setBackground(Color.pink);
                                    }
                                }
                            } else {
                                if ((myList.get(finalI).getBackground() == Color.black && myList.get(finalI + 10).getBackground() == Color.black &&
                                        myList.get(finalI + 20).getBackground() == Color.black && myList.get(finalI + 30).getBackground() == Color.black) && shipPress4)
                                    countShip4++;
                                int y = finalI / 10;
                                if (y < 7 && shipPress4 && (countShip4 < 2)) {
                                    if (myList.get(finalI).getBackground() == Color.black && myList.get(finalI + 10).getBackground() == Color.black &&
                                            myList.get(finalI + 20).getBackground() == Color.black && myList.get(finalI + 30).getBackground() == Color.black) {
                                        myList.get(finalI).setBackground(Color.pink);
                                        myList.get(finalI + 10).setBackground(Color.pink);
                                        myList.get(finalI + 20).setBackground(Color.pink);
                                        myList.get(finalI + 30).setBackground(Color.pink);
                                    }
                                }
                            }
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {

                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {

                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {

                        }

                        @Override
                        public void mouseExited(MouseEvent e) {

                        }
                    });
                }
            }
        });
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shipPress1 = false;
                shipPress2 = false;
                shipPress3 = false;
                shipPress4 = false;

                for (int i = 0; i < myList.size(); i++) {
                    int finalI = i;
                   // int finalI1 = i;
                    myList.get(i).addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (myList.get(finalI).getBackground() == Color.pink && myList.get(finalI + 1).getBackground() == Color.pink && myList.get(finalI + 2).getBackground() == Color.pink && myList.get(finalI + 3).getBackground() == Color.pink && (rotatingCounter % 2 == 0)) {
                                myList.get(finalI).setBackground(Color.black);
                                myList.get(finalI + 1).setBackground(Color.black);
                                myList.get(finalI + 2).setBackground(Color.black);
                                myList.get(finalI + 3).setBackground(Color.black);
                                countShip4--;
                            } else if (myList.get(finalI).getBackground() == Color.magenta && myList.get(finalI + 1).getBackground() == Color.magenta && myList.get(finalI + 2).getBackground() == Color.magenta && (rotatingCounter % 2 == 0)) {
                                myList.get(finalI).setBackground(Color.black);
                                myList.get(finalI + 1).setBackground(Color.black);
                                myList.get(finalI + 2).setBackground(Color.black);
                                countShip3--;
                            } else if (myList.get(finalI).getBackground() == Color.blue && myList.get(finalI + 1).getBackground() == Color.blue && (rotatingCounter % 2 == 0)) {
                                myList.get(finalI).setBackground(Color.black);
                                myList.get(finalI + 1).setBackground(Color.black);
                                countShip2--;
                            } else if (myList.get(finalI).getBackground() == Color.cyan) {
                                myList.get(finalI).setBackground(Color.black);
                                countShip1--;
                            } else if (myList.get(finalI).getBackground() == Color.pink && myList.get(finalI + 10).getBackground() == Color.pink && myList.get(finalI + 20).getBackground() == Color.pink && myList.get(finalI + 30).getBackground() == Color.pink && (rotatingCounter % 2 != 0)) {
                                myList.get(finalI).setBackground(Color.black);
                                myList.get(finalI + 10).setBackground(Color.black);
                                myList.get(finalI + 20).setBackground(Color.black);
                                myList.get(finalI + 30).setBackground(Color.black);
                                countShip4--;
                            } else if (myList.get(finalI).getBackground() == Color.magenta && myList.get(finalI + 10).getBackground() == Color.magenta && myList.get(finalI + 20).getBackground() == Color.magenta && (rotatingCounter % 2 != 0)) {
                                myList.get(finalI).setBackground(Color.black);
                                myList.get(finalI + 10).setBackground(Color.black);
                                myList.get(finalI + 20).setBackground(Color.black);
                                countShip3--;
                            } else if (myList.get(finalI).getBackground() == Color.blue && myList.get(finalI + 1).getBackground() == Color.blue && (rotatingCounter != 0)) {
                                myList.get(finalI).setBackground(Color.black);
                                myList.get(finalI + 10).setBackground(Color.black);
                                countShip2--;
                            } else if (myList.get(finalI).getBackground() == Color.cyan) {
                                myList.get(finalI).setBackground(Color.black);
                                countShip1--;
                            }

                        }
                    });
                }
            }
        });
    }


    private void initializeStartPanel() {
        startPanel = new JPanel();
        startPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Start", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
        startPanel.setBounds(370, 400, 350, 200);
        getContentPane().add(startPanel);
        reset = new JButton("Reset");
        reset.setForeground(Color.black);
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (JButton button : myList) {
                    button.setText(" ");
                    button.setOpaque(true);
                    button.setBackground(Color.black);
                    button.setEnabled(false);
                    myShipsPanel.add(button);
                }
            }
        });

        ready = new JButton("Ready");
        ready.setForeground(Color.black);
        ready.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random random = new Random();
                int n = random.nextInt(2);
                String starter = String.valueOf(n);
                MessageManager.setReadyClicked(true);
            }
        });
        rotate = new JButton("Rotate");
        rotate.setForeground(Color.black);
        rotate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rotatingCounter++;

            }
        });
        startPanel.add(ready);
        startPanel.add(reset);
        startPanel.add(rotate);
    }


    private void initializeChatPanel() {
        JPanel chatHistoryPanel = new JPanel();
        chatHistoryPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Chat History", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
        chatHistoryPanel.setBounds(739, 11, 245, 507);
        getContentPane().add(chatHistoryPanel);
        chatHistoryPanel.setLayout(null);


        chatHistory = new JTextArea();
        chatHistory.setEditable(true);
        chatHistory.setBounds(739, 11, 245, 507);
        JScrollPane chatScrollPane = new JScrollPane(chatHistory);

        chatHistoryPanel.setLayout(new BorderLayout());
        chatHistoryPanel.add(chatScrollPane, BorderLayout.CENTER);

        JPanel sendMsgPanel = new JPanel();
        sendMsgPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        sendMsgPanel.setBounds(740, 530, 244, 75);
        getContentPane().add(sendMsgPanel);
        sendMsgPanel.setLayout(null);

        this.sendBar = new JTextArea();
        this.sendBar.setEditable(true);
        this.sendBar.setBounds(177, 11, 244, 22);

        JButton btnSend = new JButton("Send");
        btnSend.setEnabled(true);
        btnSend.setBounds(177, 11, 57, 53);
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = Start.mName + ":" + sendBar.getText();

                sendBar.setText("");
                if (message != null) {
                    MessageManager.setMessage(message);

                }
            }
        });

        JScrollPane chatScrollPane2 = new JScrollPane(sendBar);
        sendMsgPanel.setLayout(new BorderLayout());
        sendMsgPanel.add(chatScrollPane2, BorderLayout.CENTER);


        sendMsgPanel.add(btnSend, BorderLayout.SOUTH);
    }

    private void initializeComponents() {
        JSeparator separatorH = new JSeparator();
        separatorH.setBounds(10, 391, 719, 2);
        getContentPane().add(separatorH);

        JSeparator separatorV = new JSeparator();
        separatorV.setOrientation(SwingConstants.VERTICAL);
        separatorV.setBounds(728, 11, 2, 600);
        getContentPane().add(separatorV);

        getContentPane().setLayout(null);

        setTitle("BattleShip");
        setResizable(false);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException ignored) {
        }
        new BattleShip();
    }
}
