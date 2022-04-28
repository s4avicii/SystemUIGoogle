package com.google.protobuf.nano;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import java.util.Objects;

public final class CodedOutputByteBufferNano {
    public final ByteBuffer buffer;

    public static class OutOfSpaceException extends IOException {
        private static final long serialVersionUID = -6947486886997889499L;

        public OutOfSpaceException(int i, int i2) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space (pos " + i + " limit " + i2 + ").");
        }
    }

    public CodedOutputByteBufferNano(byte[] bArr, int i) {
        ByteBuffer wrap = ByteBuffer.wrap(bArr, 0, i);
        this.buffer = wrap;
        wrap.order(ByteOrder.LITTLE_ENDIAN);
    }

    public static int computeRawVarint32Size(int i) {
        if ((i & -128) == 0) {
            return 1;
        }
        if ((i & -16384) == 0) {
            return 2;
        }
        if ((-2097152 & i) == 0) {
            return 3;
        }
        return (i & -268435456) == 0 ? 4 : 5;
    }

    public static int computeRawVarint64Size(long j) {
        if ((-128 & j) == 0) {
            return 1;
        }
        if ((-16384 & j) == 0) {
            return 2;
        }
        if ((-2097152 & j) == 0) {
            return 3;
        }
        if ((-268435456 & j) == 0) {
            return 4;
        }
        if ((-34359738368L & j) == 0) {
            return 5;
        }
        if ((-4398046511104L & j) == 0) {
            return 6;
        }
        if ((-562949953421312L & j) == 0) {
            return 7;
        }
        if ((-72057594037927936L & j) == 0) {
            return 8;
        }
        return (j & Long.MIN_VALUE) == 0 ? 9 : 10;
    }

    public static void encode(CharSequence charSequence, ByteBuffer byteBuffer) {
        if (byteBuffer.isReadOnly()) {
            throw new ReadOnlyBufferException();
        } else if (byteBuffer.hasArray()) {
            try {
                byteBuffer.position(encode(charSequence, byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining()) - byteBuffer.arrayOffset());
            } catch (ArrayIndexOutOfBoundsException e) {
                BufferOverflowException bufferOverflowException = new BufferOverflowException();
                bufferOverflowException.initCause(e);
                throw bufferOverflowException;
            }
        } else {
            int length = charSequence.length();
            int i = 0;
            while (i < length) {
                char charAt = charSequence.charAt(i);
                if (charAt < 128) {
                    byteBuffer.put((byte) charAt);
                } else if (charAt < 2048) {
                    byteBuffer.put((byte) ((charAt >>> 6) | 960));
                    byteBuffer.put((byte) ((charAt & '?') | 128));
                } else if (charAt < 55296 || 57343 < charAt) {
                    byteBuffer.put((byte) ((charAt >>> 12) | 480));
                    byteBuffer.put((byte) (((charAt >>> 6) & 63) | 128));
                    byteBuffer.put((byte) ((charAt & '?') | 128));
                } else {
                    int i2 = i + 1;
                    if (i2 != charSequence.length()) {
                        char charAt2 = charSequence.charAt(i2);
                        if (Character.isSurrogatePair(charAt, charAt2)) {
                            int codePoint = Character.toCodePoint(charAt, charAt2);
                            byteBuffer.put((byte) ((codePoint >>> 18) | 240));
                            byteBuffer.put((byte) (((codePoint >>> 12) & 63) | 128));
                            byteBuffer.put((byte) (((codePoint >>> 6) & 63) | 128));
                            byteBuffer.put((byte) ((codePoint & 63) | 128));
                            i = i2;
                        } else {
                            i = i2;
                        }
                    }
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Unpaired surrogate at index ");
                    m.append(i - 1);
                    throw new IllegalArgumentException(m.toString());
                }
                i++;
            }
        }
    }

    public final void writeBool(int i, boolean z) throws IOException {
        writeTag(i, 0);
        writeRawByte(z ? 1 : 0);
    }

    public final void writeFixed64(long j) throws IOException {
        writeTag(1, 1);
        if (this.buffer.remaining() >= 8) {
            this.buffer.putLong(j);
            return;
        }
        throw new OutOfSpaceException(this.buffer.position(), this.buffer.limit());
    }

    public final void writeFloat(int i, float f) throws IOException {
        writeTag(i, 5);
        int floatToIntBits = Float.floatToIntBits(f);
        if (this.buffer.remaining() >= 4) {
            this.buffer.putInt(floatToIntBits);
            return;
        }
        throw new OutOfSpaceException(this.buffer.position(), this.buffer.limit());
    }

    public final void writeInt32(int i, int i2) throws IOException {
        writeTag(i, 0);
        if (i2 >= 0) {
            writeRawVarint32(i2);
        } else {
            writeRawVarint64((long) i2);
        }
    }

    public final void writeInt64(int i, long j) throws IOException {
        writeTag(i, 0);
        writeRawVarint64(j);
    }

    public final void writeMessage(int i, MessageNano messageNano) throws IOException {
        writeTag(i, 2);
        Objects.requireNonNull(messageNano);
        if (messageNano.cachedSize < 0) {
            messageNano.getSerializedSize();
        }
        writeRawVarint32(messageNano.cachedSize);
        messageNano.writeTo(this);
    }

    public final void writeRawByte(int i) throws IOException {
        byte b = (byte) i;
        if (this.buffer.hasRemaining()) {
            this.buffer.put(b);
            return;
        }
        throw new OutOfSpaceException(this.buffer.position(), this.buffer.limit());
    }

    public final void writeString(int i, String str) throws IOException {
        writeTag(i, 2);
        try {
            int computeRawVarint32Size = computeRawVarint32Size(str.length());
            if (computeRawVarint32Size == computeRawVarint32Size(str.length() * 3)) {
                int position = this.buffer.position();
                if (this.buffer.remaining() >= computeRawVarint32Size) {
                    this.buffer.position(position + computeRawVarint32Size);
                    encode(str, this.buffer);
                    int position2 = this.buffer.position();
                    this.buffer.position(position);
                    writeRawVarint32((position2 - position) - computeRawVarint32Size);
                    this.buffer.position(position2);
                    return;
                }
                throw new OutOfSpaceException(position + computeRawVarint32Size, this.buffer.limit());
            }
            writeRawVarint32(encodedLength(str));
            encode(str, this.buffer);
        } catch (BufferOverflowException e) {
            OutOfSpaceException outOfSpaceException = new OutOfSpaceException(this.buffer.position(), this.buffer.limit());
            outOfSpaceException.initCause(e);
            throw outOfSpaceException;
        }
    }

    public static int computeTagSize(int i) {
        return computeRawVarint32Size((i << 3) | 0);
    }

    public final void writeRawVarint32(int i) throws IOException {
        while ((i & -128) != 0) {
            writeRawByte((i & 127) | 128);
            i >>>= 7;
        }
        writeRawByte(i);
    }

    public final void writeRawVarint64(long j) throws IOException {
        while ((-128 & j) != 0) {
            writeRawByte((((int) j) & 127) | 128);
            j >>>= 7;
        }
        writeRawByte((int) j);
    }

    public final void writeTag(int i, int i2) throws IOException {
        writeRawVarint32((i << 3) | i2);
    }

    public static int computeBoolSize(int i) {
        return computeTagSize(i) + 1;
    }

    public static int computeFloatSize(int i) {
        return computeTagSize(i) + 4;
    }

    public static int computeInt32Size(int i, int i2) {
        int i3;
        int computeTagSize = computeTagSize(i);
        if (i2 >= 0) {
            i3 = computeRawVarint32Size(i2);
        } else {
            i3 = 10;
        }
        return computeTagSize + i3;
    }

    public static int computeInt64Size(int i, long j) {
        return computeRawVarint64Size(j) + computeTagSize(i);
    }

    public static int computeMessageSize(int i, MessageNano messageNano) {
        int computeTagSize = computeTagSize(i);
        int serializedSize = messageNano.getSerializedSize();
        return computeRawVarint32Size(serializedSize) + serializedSize + computeTagSize;
    }

    public static int computeStringSize(int i, String str) {
        int computeTagSize = computeTagSize(i);
        int encodedLength = encodedLength(str);
        return computeRawVarint32Size(encodedLength) + encodedLength + computeTagSize;
    }

    public static int encodedLength(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        int i2 = 0;
        while (i2 < length && charSequence.charAt(i2) < 128) {
            i2++;
        }
        int i3 = length;
        while (true) {
            if (i2 >= length) {
                break;
            }
            char charAt = charSequence.charAt(i2);
            if (charAt < 2048) {
                i3 += (127 - charAt) >>> 31;
                i2++;
            } else {
                int length2 = charSequence.length();
                while (i2 < length2) {
                    char charAt2 = charSequence.charAt(i2);
                    if (charAt2 < 2048) {
                        i += (127 - charAt2) >>> 31;
                    } else {
                        i += 2;
                        if (55296 <= charAt2 && charAt2 <= 57343) {
                            if (Character.codePointAt(charSequence, i2) >= 65536) {
                                i2++;
                            } else {
                                throw new IllegalArgumentException(VendorAtomValue$$ExternalSyntheticOutline0.m0m("Unpaired surrogate at index ", i2));
                            }
                        }
                    }
                    i2++;
                }
                i3 += i;
            }
        }
        if (i3 >= length) {
            return i3;
        }
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("UTF-8 length does not fit in int: ");
        m.append(((long) i3) + 4294967296L);
        throw new IllegalArgumentException(m.toString());
    }

    public static int encode(CharSequence charSequence, byte[] bArr, int i, int i2) {
        int i3;
        int i4;
        char charAt;
        int length = charSequence.length();
        int i5 = i2 + i;
        int i6 = 0;
        while (i6 < length && (i4 = i6 + i) < i5 && (charAt = charSequence.charAt(i6)) < 128) {
            bArr[i4] = (byte) charAt;
            i6++;
        }
        if (i6 == length) {
            return i + length;
        }
        int i7 = i + i6;
        while (i6 < length) {
            char charAt2 = charSequence.charAt(i6);
            if (charAt2 < 128 && i7 < i5) {
                i3 = i7 + 1;
                bArr[i7] = (byte) charAt2;
            } else if (charAt2 < 2048 && i7 <= i5 - 2) {
                int i8 = i7 + 1;
                bArr[i7] = (byte) ((charAt2 >>> 6) | 960);
                i7 = i8 + 1;
                bArr[i8] = (byte) ((charAt2 & '?') | 128);
                i6++;
            } else if ((charAt2 < 55296 || 57343 < charAt2) && i7 <= i5 - 3) {
                int i9 = i7 + 1;
                bArr[i7] = (byte) ((charAt2 >>> 12) | 480);
                int i10 = i9 + 1;
                bArr[i9] = (byte) (((charAt2 >>> 6) & 63) | 128);
                i3 = i10 + 1;
                bArr[i10] = (byte) ((charAt2 & '?') | 128);
            } else if (i7 <= i5 - 4) {
                int i11 = i6 + 1;
                if (i11 != charSequence.length()) {
                    char charAt3 = charSequence.charAt(i11);
                    if (Character.isSurrogatePair(charAt2, charAt3)) {
                        int codePoint = Character.toCodePoint(charAt2, charAt3);
                        int i12 = i7 + 1;
                        bArr[i7] = (byte) ((codePoint >>> 18) | 240);
                        int i13 = i12 + 1;
                        bArr[i12] = (byte) (((codePoint >>> 12) & 63) | 128);
                        int i14 = i13 + 1;
                        bArr[i13] = (byte) (((codePoint >>> 6) & 63) | 128);
                        i7 = i14 + 1;
                        bArr[i14] = (byte) ((codePoint & 63) | 128);
                        i6 = i11;
                        i6++;
                    } else {
                        i6 = i11;
                    }
                }
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Unpaired surrogate at index ");
                m.append(i6 - 1);
                throw new IllegalArgumentException(m.toString());
            } else {
                throw new ArrayIndexOutOfBoundsException("Failed writing " + charAt2 + " at index " + i7);
            }
            i7 = i3;
            i6++;
        }
        return i7;
    }
}
