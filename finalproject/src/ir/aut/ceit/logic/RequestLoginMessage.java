package ir.aut.ceit.logic;

import java.nio.ByteBuffer;

public class RequestLoginMessage extends BaseMessage {
    private String mUsername;
    private String mIp;

    public RequestLoginMessage(String username,String ip) {
        mUsername = username;
        mIp=ip;
        serialize();
    }

    public RequestLoginMessage(byte[] serialized) {
        mSerialized = serialized;
        deserialize();
    }

    @Override
    protected void serialize() {
        int usernameLength = mUsername.getBytes().length;
        int ipLength=mIp.getBytes().length;
        int messageLength = 4 + 1 + 1 + 4 + usernameLength+4+ipLength ;
        ByteBuffer byteBuffer = ByteBuffer.allocate(messageLength);
        byteBuffer.putInt(messageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.REQUEST_LOGIN);
        byteBuffer.putInt(usernameLength);
        byteBuffer.put(mUsername.getBytes());
        byteBuffer.putInt(ipLength);
        byteBuffer.put(mIp.getBytes());
        mSerialized = byteBuffer.array();
    }

    @Override
    protected void deserialize() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(mSerialized);
        int messageLength = byteBuffer.getInt();
        byte protocolVersion = byteBuffer.get();
        byte messageType = byteBuffer.get();

        int usernameLength = byteBuffer.getInt();
        byte[] usernameBytes = new byte[usernameLength];
        byteBuffer.get(usernameBytes);
        mUsername = new String(usernameBytes);

        int ipLength=byteBuffer.getInt();
        byte[] ipBytes=new byte[ipLength];
        byteBuffer.get(ipBytes);
        mIp=new String(ipBytes);


    }

    @Override
    public byte getMessageType() {
        return MessageTypes.REQUEST_LOGIN;
    }

    public String getUsername() {
        return mUsername;
    }
    public String getIp() {
        return mIp;
    }

}



