package ir.aut.ceit.logic;

public abstract class BaseMessage {
    byte[] mSerialized;

    /**
     * Fields are stored into serial bytes in this method.
     */
    protected abstract void serialize();

    /**
     * Fields are restored from serial bytes in this method.
     */
    protected abstract void deserialize();

    /**
     * Return message type code.
     */
    public abstract byte getMessageType();

    byte[] getSerialized() {
        return mSerialized;
    }
}