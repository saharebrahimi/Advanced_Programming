package ir.aut.ceit.logic;

import java.nio.ByteBuffer;

public class TurnMessage extends BaseMessage {
    private String mUsername;

    public TurnMessage(String username) {
        mUsername = username;
        serialize();
    }

    public TurnMessage(byte[] serialized) {
        mSerialized = serialized;
        deserialize();
    }

    @Override
    protected void serialize() {
        int usernameLength = mUsername.getBytes().length;
        int messageLength = 4 + 1 + 1 + 4 + usernameLength ;
        ByteBuffer byteBuffer = ByteBuffer.allocate(messageLength);
        byteBuffer.putInt(messageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.Turn_Message);
        byteBuffer.putInt(usernameLength);
        byteBuffer.put(mUsername.getBytes());

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

    }

    @Override
    public byte getMessageType() {
        return MessageTypes.Turn_Message;
    }

    public String getUsername() {
        return mUsername;
    }


}









