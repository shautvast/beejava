package nl.sander.beejava.util;

import java.nio.ByteBuffer;
import java.nio.charset.*;

/**
 * storage like ArrayList, with bytebuffer instead of array
 */
public class ByteBuf {

    private ByteBuffer data;

    public ByteBuf() {
        this(64);
    }

    public ByteBuf(final int initialSize) {
        data = ByteBuffer.allocate(initialSize);
    }

    public void addU8(final byte[] bytes) {
        if (data.remaining() < bytes.length) {
            enlarge(bytes.length);
        }
        data.put(bytes);
    }

    public void addU8(final int... ints) {
        if (data.remaining() < ints.length) {
            enlarge(ints.length);
        }
        byte[] bytes = new byte[ints.length];
        for (int i = 0; i < ints.length; i++) {
            bytes[i] = (byte) (ints[i] & 0xFF);
        }
        addU8(bytes);
    }

    public void addU16(int u16) {
        addU8((u16 >>> 8) & 0xFF, u16 & 0xFF);
    }

    private void enlarge(final int size) {
        final int length1 = 2 * data.limit();
        final int length2 = data.limit() + size;
        ByteBuffer newData = ByteBuffer.allocate(Math.max(length1, length2));
        data.flip();
        newData.put(data);
        data = newData;
    }

    public byte[] toBytes() {
        int length = data.limit() - data.remaining();
        data.rewind();
        byte[] arr = new byte[length];
        data.get(arr);
        return arr;
    }
}
