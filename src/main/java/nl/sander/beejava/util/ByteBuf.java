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

    public String toString() {
        return toString(StandardCharsets.UTF_8);
    }

    public String toString(Charset charset) {
        data.flip();

        CharsetDecoder decoder = charset.newDecoder(); // decode is not threadsafe, might put it in threadlocal
        // but I don't think this (newDecoder()+config) is expensive

        decoder.onMalformedInput(CodingErrorAction.REPLACE)
                .onUnmappableCharacter(CodingErrorAction.REPLACE);

        try {
            return decoder.decode(data).toString();
        } catch (CharacterCodingException e) {
            throw new RuntimeException(e);
        }
    }

    public void clear() {
        data.clear();
    }

    public void add(final byte c) {
        if (data.remaining() == 0) {
            enlarge(1);
        }
        data.put(c);
    }

    public void add(final byte[] bytes) {
        if (data.remaining() < bytes.length) {
            enlarge(bytes.length);
        }
        data.put(bytes);
    }

    public void add(final int... ints) {
        if (data.remaining() < ints.length) {
            enlarge(ints.length);
        }
        byte[] bytes = new byte[ints.length];
        for (int i = 0; i < ints.length; i++) {
            bytes[i] = (byte) (i & 0xFF);
        }
        add(bytes);
    }

    private void enlarge(final int size) {
        final int length1 = 2 * data.limit();
        final int length2 = data.limit() + size;
        ByteBuffer newData = ByteBuffer.allocate(Math.max(length1, length2));
        data.flip();
        newData.put(data);
        data = newData;
    }

    public int length() {
        return data.limit() - data.remaining();
    }

    public byte[] toBytes() {
        return data.array();
    }
}
