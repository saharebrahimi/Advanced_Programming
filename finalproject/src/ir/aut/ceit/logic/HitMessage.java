package ir.aut.ceit.logic;

import java.nio.ByteBuffer;

public class HitMessage extends BaseMessage {
    private String mIndex;

    HitMessage(String index) {
        mIndex = index;
        serialize();
    }

    HitMessage(byte[] serialized) {
        mSerialized = serialized;
        deserialize();
    }

    @Override
    protected void serialize() {
        int indexLength=mIndex.getBytes().length;
        int messageLength = 4 + 1 + 1 + 4 +indexLength ;
        ByteBuffer byteBuffer = ByteBuffer.allocate(messageLength);
        byteBuffer.putInt(messageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.Hit_Message);

        byteBuffer.putInt(indexLength);
        byteBuffer.put(mIndex.getBytes());
        mSerialized = byteBuffer.array();
    }

    @Override
    protected void deserialize() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(mSerialized);
        int messageLength = byteBuffer.getInt();
        byte protocolVersion = byteBuffer.get();
        byte messageType = byteBuffer.get();


        int indexLength=byteBuffer.getInt();
        byte[] indexBytes=new byte[indexLength];
        byteBuffer.get(indexBytes);
        mIndex=new String(indexBytes);


    }

    @Override
    public byte getMessageType() {
        return MessageTypes.Hit_Message;
    }


    String getIndex() {
        return mIndex;
    }

}



