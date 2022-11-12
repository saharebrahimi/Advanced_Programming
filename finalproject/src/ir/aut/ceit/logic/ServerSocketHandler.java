package ir.aut.ceit.logic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

//****************************************
public class ServerSocketHandler extends Thread {
    private ServerSocket mServerSocket;
    private int port;
    private NetworkHandler.INetworkHandlerCallback iNetworkHandlerCallback;
    private IServerSocketHandlerCallback iServerSocketHandlerCallback;

    public ServerSocketHandler(int port, NetworkHandler.INetworkHandlerCallback iNetworkHandlerCallback, IServerSocketHandlerCallback iServerSocketHandlerCallback) throws IOException {
        this.port = port;
        this.iNetworkHandlerCallback = iNetworkHandlerCallback;
        this.iServerSocketHandlerCallback = iServerSocketHandlerCallback;
        try {
            mServerSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * While server socket is connected and stop is not called:
     * if a connection receives, then create a network handler and pass it through onNewConnectionReceived
     * else sleep for 100 ms.
     */
    @Override
    public void run() {
        while (!mServerSocket.isClosed() && !isInterrupted()) {
            Socket socket = null;
            try {
                socket = mServerSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (socket != null) {
                NetworkHandler networkHandler = null;
                try {
                    networkHandler = new NetworkHandler(socket, iNetworkHandlerCallback);
                    /////////////////
                    iServerSocketHandlerCallback.onNewConnectionReceived(networkHandler);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (networkHandler != null) {
                    networkHandler.start();
                    iServerSocketHandlerCallback.onNewConnectionReceived(networkHandler);
                }
            }
        }
    }

    /**
     * Kill the thread and close the server socket.
     */
    public void stopSelf() throws IOException {
        interrupt();
        mServerSocket.close();

    }

    public interface IServerSocketHandlerCallback {
        void onNewConnectionReceived(NetworkHandler networkHandler);

    }
}