package androidx.emoji2.text;

import androidx.emoji2.text.flatbuffer.MetadataList;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;

public final class MetadataListReader {

    public static class ByteBufferReader {
        public final ByteBuffer mByteBuffer;

        public final long readUnsignedInt() throws IOException {
            return ((long) this.mByteBuffer.getInt()) & 4294967295L;
        }

        public final void skip(int i) throws IOException {
            ByteBuffer byteBuffer = this.mByteBuffer;
            byteBuffer.position(byteBuffer.position() + i);
        }

        public ByteBufferReader(ByteBuffer byteBuffer) {
            this.mByteBuffer = byteBuffer;
            byteBuffer.order(ByteOrder.BIG_ENDIAN);
        }
    }

    public static MetadataList read(MappedByteBuffer mappedByteBuffer) throws IOException {
        long j;
        ByteBuffer duplicate = mappedByteBuffer.duplicate();
        ByteBufferReader byteBufferReader = new ByteBufferReader(duplicate);
        byteBufferReader.skip(4);
        short s = duplicate.getShort() & 65535;
        if (s <= 100) {
            byteBufferReader.skip(6);
            int i = 0;
            while (true) {
                if (i >= s) {
                    j = -1;
                    break;
                }
                int i2 = byteBufferReader.mByteBuffer.getInt();
                byteBufferReader.skip(4);
                j = byteBufferReader.readUnsignedInt();
                byteBufferReader.skip(4);
                if (1835365473 == i2) {
                    break;
                }
                i++;
            }
            if (j != -1) {
                byteBufferReader.skip((int) (j - ((long) byteBufferReader.mByteBuffer.position())));
                byteBufferReader.skip(12);
                long readUnsignedInt = byteBufferReader.readUnsignedInt();
                for (int i3 = 0; ((long) i3) < readUnsignedInt; i3++) {
                    int i4 = byteBufferReader.mByteBuffer.getInt();
                    long readUnsignedInt2 = byteBufferReader.readUnsignedInt();
                    byteBufferReader.readUnsignedInt();
                    if (1164798569 == i4 || 1701669481 == i4) {
                        duplicate.position((int) (readUnsignedInt2 + j));
                        MetadataList metadataList = new MetadataList();
                        duplicate.order(ByteOrder.LITTLE_ENDIAN);
                        metadataList.__reset(duplicate.position() + duplicate.getInt(duplicate.position()), duplicate);
                        return metadataList;
                    }
                }
            }
            throw new IOException("Cannot read metadata.");
        }
        throw new IOException("Cannot read metadata.");
    }
}
