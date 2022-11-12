package ir.aut.ceit.logic;

import ir.aut.ceit.view.ServerConnection;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MessageManager implements ServerSocketHandler.IServerSocketHandlerCallback, NetworkHandler.INetworkHandlerCallback {
    private ServerSocketHandler mServerSocketHandler;
    private NetworkHandler mNetworkHandler;
    private static List<NetworkHandler> mNetworkHandlerList;
    private String name;
    private String accept;
    private String message;
    private String ready;
    private String start;
    private static boolean readyForHit;
    public static String index;
    private static String arrayList;
    private static boolean readyClicked;
    private static String whoStart;
    public static String enemyArrayList;
    private static boolean isHost;

    /**
     * Instantiate server socket handler and start it. (Call this constructor in host mode)
     */
    public MessageManager(int port) throws IOException {
        mServerSocketHandler = new ServerSocketHandler(port, this, this);
        mServerSocketHandler.start();
        mNetworkHandlerList = new ArrayList<NetworkHandler>();
    }

    /**
     * Instantiate a network handler and start it. (Call this constructor in guest mode)
     */
    public MessageManager(String ip, int port) throws IOException {
        Socket mSocket = new Socket(ip, port);
        mNetworkHandler = new NetworkHandler(mSocket, this);
        mNetworkHandlerList = new ArrayList<>();
        mNetworkHandlerList.add(mNetworkHandler);
        mNetworkHandler.start();

    }

    static void setMainList(ArrayList<Integer> mainList1) {
        ArrayList<Integer> mainList = mainList1;
    }

    static void setTurnList(ArrayList<Integer> turnArrayList) {
        ArrayList<Integer> turnList = turnArrayList;
    }

    static void settIsHit(boolean hitting) {
        boolean isHit = hitting;
    }

    public static void setHost(boolean Host) {
        isHost = Host;
    }

    public static void setIsReady(boolean ready) {
        boolean isReady = ready;
    }
    //   public static void setEnemyArrayList(String mEnemyArrayList,String starter) {
    //     sendReadyMessage("ready",mEnemyArrayList,starter);
    // }

    public static void setWhoStart(String starter) {
        whoStart = starter;
    }
    //  public static void setIndex(String index) {
    //    sendHitMessage( index);
    // }

    public static void setArrayList(String arrayList) {
        sendReadyMessage("ready", arrayList);
    }

    public static void setReadyClicked(boolean flag) {
        readyClicked = flag;
        if (flag) {
            BattleShip.setIsReady(true);
        }
    }


    public void setName(String name, String ip) {
        sendRequestLogin(name, ip);
    }

    public static void setStart(String start, String starter) {
        sendAcceptMessage(start, starter);
    }

    public static void setMessage(String message) {
        sendChatMessage(message);

    }

    public static void sendAcceptMessage(String message, String starter) {
        AcceptMessage acceptMessage = new AcceptMessage(message, starter);
        mNetworkHandlerList.get(0).sendMessage(acceptMessage);
        BattleShip host = new BattleShip();
    }

    /**
     * IMPORTANT: Request login is an example message and doesn’t relate to this project!
     * Create a RequestLoginMessage object and sent it through the appropriate network handler.
     */
    public void sendRequestLogin(String username, String ip) {
        RequestLoginMessage requestLoginMessage = new RequestLoginMessage(username, ip);
        mNetworkHandler.sendMessage(requestLoginMessage);
    }

    public static void sendChatMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message);
        mNetworkHandlerList.get(0).sendMessage(chatMessage);
        BattleShip.setChat(message);
        //   System.out.println(message);
    }

    public static void sendReadyMessage(String message, String arrayList) {
        ReadyMessage readyMessage = new ReadyMessage(message, arrayList);
        mNetworkHandlerList.get(0).sendMessage(readyMessage);
        // BattleShip.setIsReady(true);
    }

    public static void sendWhoStartMessage(String whoStarter) {
        if (!isHost) {
            WhoStartMessage whoStartMessage = new WhoStartMessage(whoStarter);
            mNetworkHandlerList.get(0).sendMessage(whoStartMessage);
        }
    }

    public static void sendHitMessage(String index) {

        HitMessage hitMessage = new HitMessage(index);
        mNetworkHandlerList.get(0).sendMessage(hitMessage);
        sendTurnMessage("turn");
        //  settIsHit(true);
    }

    public static void sendTurnMessage(String turn)  {
      //  if (isHit) {
   //         Thread.sleep(3000);

            TurnMessage turnMessage = new TurnMessage(turn);
            mNetworkHandlerList.get(0).sendMessage(turnMessage);
          //  BattleShip.setDrawArrayList(mainList);
      //  }
    }

    /**
     * IMPORTANT: Request login is an example message and doesn’t relate to this project!
     * Use the message.
     */
    private void consumeRequestLogin(RequestLoginMessage message) {
        String myMessage = message.getUsername();
        String ip = message.getIp();
        ServerConnection.setNI(myMessage, ip);
    }

    private void consumeAcceptMessage(AcceptMessage message) {
        String accept = message.getUsername();
        String starter = message.getIp();
        setWhoStart(starter);
        if (starter.equals("1"))
            sendWhoStartMessage("0");
        else
            sendWhoStartMessage("1");

        if (accept.equals("0")) {

        } else {
            BattleShip Guest = new BattleShip();
        }

    }

    private void consumeChatMessage(ChatMessage message) {
        String chat = message.getUsername();
        BattleShip.setChat(chat);
    }

    private void consumeReadyMessage(ReadyMessage message) throws InterruptedException {
        String readyMessage = message.getUsername();
        String arrayList = message.getIp();
        if (readyMessage.equals("ready")) {
            setIsReady(true);
            if (whoStart.equals("1") && readyClicked) {
                BattleShip.setWhostart(true, arrayList);
            }


        }
    }

    private void consumeWhoStartMessage(WhoStartMessage message) {
        String starter = message.getUsername();
        setWhoStart(starter);
    }

    private void consumeHitMessage(HitMessage message) throws InterruptedException {
        String index = message.getIndex();
        BattleShip.setIndexHit(index);
        //   System.out.println("wohStart:"+whoStart+"readyClicked:"+readyClicked+"isHit:"+isHit);
        if (whoStart.equals("0") && readyClicked) {
            BattleShip.setWhostart(false, arrayList);
        }


    }

    private void consumeTurnMessage(TurnMessage message) throws InterruptedException {
        String turnMessage = message.getUsername();
        if (turnMessage.equals("turn")) {

            TimeUnit.SECONDS.sleep(3);
            BattleShip.setCheckTurn(true);
        }
    }
    /*private void consumeWhoStartMessage(WhoStartMessage message) {
        String whoStart = message.getUsername();
        if (whoStart.equals("yes")) {
            BattleShip.setWhostart(true, enemyArrayList);
        } else {
            BattleShip.setWhostart(false, enemyArrayList);
        }
    }*/

    /**
     * IMPORTANT: Request login is an example message and doesn’t relate to this project!
     * * According to the message type of baseMessage, call corresponding method to use it.
     */
    @Override
    public void onMessageReceived(BaseMessage baseMessage) throws InterruptedException {
        switch (baseMessage.getMessageType()) {
            case 1:
                consumeRequestLogin((RequestLoginMessage) baseMessage);
                break;
            case 2:
                consumeAcceptMessage((AcceptMessage) baseMessage);
                break;
            case 3:
                consumeChatMessage((ChatMessage) baseMessage);
                break;
            case 4:
                consumeReadyMessage((ReadyMessage) baseMessage);
                break;
            case 5:
                consumeHitMessage((HitMessage) baseMessage);
                break;
            case 6:
                consumeWhoStartMessage((WhoStartMessage) baseMessage);
                break;
            case 7:
                consumeTurnMessage((TurnMessage) baseMessage);
                break;
        }
    }

    @Override
    public void onSocketClosed() {
        try {
            // mSocket.close();
            mNetworkHandler.stopSelf();
            mServerSocketHandler.stopSelf();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add network handler to the list.
     */
    @Override
    public void onNewConnectionReceived(NetworkHandler networkHandler) {
        mNetworkHandlerList.add(networkHandler);
    }

}
