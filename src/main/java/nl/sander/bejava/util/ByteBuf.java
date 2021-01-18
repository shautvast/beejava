package nl.sander.bejava.util;

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
        var bytes = new byte[ints.length];
        for (int i = 0; i < ints.length; i++) {
            bytes[i] = (byte) (ints[i] & 0xFF);
        }
        addU8(bytes);
    }

    public void addU16(int u16) {
        addU8((u16 >>> 8) & 0xFF, u16 & 0xFF);
    }

    public void addU32(int u32) {
        addU8((u32 >>> 24) & 0xFF,(u32 >>> 16) & 0xFF, (u32 >>> 8) & 0xFF, u32 & 0xFF);
    }

    private void enlarge(final int size) {
        final var length1 = 2 * data.limit();
        final var length2 = data.limit() + size;
        var newData = ByteBuffer.allocate(Math.max(length1, length2));
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

//    public String toString() {
//        return toString(StandardCharsets.UTF_8);
//    }
//
//    public String toString(Charset charset) {
//        data.flip();
//
//        CharsetDecoder decoder = charset.newDecoder(); // decode is not threadsafe, might put it in threadlocal
//        // but I don't think this (newDecoder()+config) is expensive
//
//        decoder.onMalformedInput(CodingErrorAction.REPLACE)
//                .onUnmappableCharacter(CodingErrorAction.REPLACE);
//
//        try {
//            return decoder.decode(data).toString();
//        } catch (CharacterCodingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//

}
