package ir.aut.ceit.logic;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

//**********************************************
public class NetworkHandler extends Thread {

    private TcpChannel mTcpChannel;
    private Queue<byte[]> mSendQueue;
    private Queue<byte[]> mReceivedQueue;
    private ReceivedMessageConsumer mConsumerThread;
    private INetworkHandlerCallback iNetworkHandlerCallback;

    public NetworkHandler(SocketAddress socketAddress, INetworkHandlerCallback iNetworkHandlerCallback) throws IOException {
        mTcpChannel = new TcpChannel(socketAddress, 300);
        this.iNetworkHandlerCallback = iNetworkHandlerCallback;
        mSendQueue = new LinkedList<>();
        mReceivedQueue = new LinkedList<>();
        mConsumerThread = new ReceivedMessageConsumer();
    }


    public NetworkHandler(Socket socket, INetworkHandlerCallback iNetworkHandlerCallback) throws IOException {
        mTcpChannel = new TcpChannel(socket, 300);
        this.iNetworkHandlerCallback = iNetworkHandlerCallback;
        mSendQueue = new LinkedList<>();
        mReceivedQueue = new LinkedList<>();
        mConsumerThread = new ReceivedMessageConsumer();
        mConsumerThread.start();
    }


    /**
     * Add serialized bytes of message to the sendQueue.
     */
    public void sendMessage(BaseMessage baseMessage) {

        mSendQueue.add(baseMessage.getSerialized());

    }

    /**
     * While channel is connected and stop is not called:
     * if sendQueue is not empty, then poll a message and send it
     * else if readChannel() is returning bytes, then add it to receivedQueue.
     */
    @Override
    public void run() {
        while (mTcpChannel.isConnected() && !isInterrupted()) {
            if (!mSendQueue.isEmpty()) {
                try {
                    mTcpChannel.write(mSendQueue.poll());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    byte[] bytes = readChannel();
                    if (bytes.length > 0) {
                        mReceivedQueue.add(bytes);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Kill the thread and close the channel.
     */
    public void stopSelf() throws IOException {
        interrupt();
        mTcpChannel.closeChannel();

    }

    /**
     * Try to read some bytes from the channel.
     */
    private byte[] readChannel() throws IOException {
        byte[] lenBytes = mTcpChannel.read(4);
        int len = ByteBuffer.wrap(lenBytes).getInt();
        if (len > 0) {
            byte[] bytes = mTcpChannel.read(len - 4);
            byte[] result = new byte[len];
            System.arraycopy(lenBytes, 0, result, 0, 4);
            System.arraycopy(bytes, 0, result, 4, len - 4);
            return result;
        } else {
            return new byte[0];
        }
    }

    private class ReceivedMessageConsumer extends Thread {
        /**
         * While channel is connected and stop is not called:
         * if there exists message in receivedQueue, then create a message object
         * from appropriate class based on message type byte and pass it through onMessageReceived
         * else if receivedQueue is empty, then sleep 100 ms.
         */
        @Override
        public void run() {
            while (mTcpChannel.isConnected() && !isInterrupted()) {
                if (!mReceivedQueue.isEmpty()) {
                    ByteBuffer byteBuffer = ByteBuffer.wrap(mReceivedQueue.poll());
                    byteBuffer.position(5);
                    byte MessageType = byteBuffer.get();
                    switch (MessageType) {
                        case 1:
                            RequestLoginMessage requestLoginMessage = new RequestLoginMessage(byteBuffer.array());
                            try {
                                iNetworkHandlerCallback.onMessageReceived(requestLoginMessage);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            AcceptMessage acceptMessage = new AcceptMessage(byteBuffer.array());
                            try {
                                iNetworkHandlerCallback.onMessageReceived(acceptMessage);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            ChatMessage chatMessage=new ChatMessage(byteBuffer.array());
                            try {
                                iNetworkHandlerCallback.onMessageReceived(chatMessage);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 4:
                            ReadyMessage readyMessage=new ReadyMessage(byteBuffer.array());
                            try {
                                iNetworkHandlerCallback.onMessageReceived(readyMessage);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 5:
                            HitMessage hitMessage=new HitMessage(byteBuffer.array());
                            try {
                                iNetworkHandlerCallback.onMessageReceived(hitMessage);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 6:
                            WhoStartMessage whoStartMessage=new WhoStartMessage(byteBuffer.array());
                            try {
                                iNetworkHandlerCallback.onMessageReceived(whoStartMessage);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 7:
                            TurnMessage turnMessage=new TurnMessage(byteBuffer.array());
                            try {
                                iNetworkHandlerCallback.onMessageReceived(turnMessage);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                    }


                } else {
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }

                }
            }

        }
    }

    public interface INetworkHandlerCallback {
        void onMessageReceived(BaseMessage baseMessage) throws InterruptedException;

        void onSocketClosed() throws IOException;
    }

}
