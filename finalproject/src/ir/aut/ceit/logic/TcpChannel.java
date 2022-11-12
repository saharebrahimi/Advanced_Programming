package ir.aut.ceit.logic;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
//****************************************
public class TcpChannel {
    private Socket mSocket;
    private OutputStream mOutputStream;
    private InputStream mInputStream;

    public TcpChannel(SocketAddress socketAddress, int timeout) throws IOException {
        mSocket = new Socket();
        mSocket.connect(socketAddress);
        mSocket.setSoTimeout(timeout);
        mInputStream = mSocket.getInputStream();
        mOutputStream = mSocket.getOutputStream();
    }


    public TcpChannel(Socket socket, int timeout) throws IOException {
        mSocket = socket;
        mSocket.setSoTimeout(timeout);
        mInputStream = mSocket.getInputStream();
        mOutputStream = mSocket.getOutputStream();
    }

    /**
     * Try to read specific count from input stream.
     */
    public byte[] read(final int count) throws IOException {//age moshkel timeout hazf!!
        byte[] buffer = new byte[count];
        try {
            mInputStream.read(buffer);

        }
        catch (SocketTimeoutException e){
            // System.out.println(e);
        }
        return buffer;
    }

    /**
     * Write bytes on output stream.
     */
    public void write(byte[] data) throws IOException {
        mOutputStream.write(data);
        mOutputStream.flush();
    }

    /**
     * Check socket’s connectivity.
     */
    public boolean isConnected() {
        return mSocket.isConnected();
    }

    /**
     * Try to close socket and input-output streams.
     */
    public void closeChannel() throws IOException {
        mSocket.close();
        mInputStream.close();
        mOutputStream.close();
    }
}