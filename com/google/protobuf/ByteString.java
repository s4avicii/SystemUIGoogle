package com.google.protobuf;

import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.google.protobuf.Utf8;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public abstract class ByteString implements Iterable<Byte>, Serializable {
    public static final ByteString EMPTY = new LiteralByteString(Internal.EMPTY_BYTE_ARRAY);
    public static final ByteArrayCopier byteArrayCopier;
    private int hash = 0;

    public static abstract class AbstractByteIterator implements Iterator {
        public final Object next() {
            C24851 r2 = (C24851) this;
            int i = r2.position;
            if (i < r2.limit) {
                r2.position = i + 1;
                return Byte.valueOf(ByteString.this.internalByteAt(i));
            }
            throw new NoSuchElementException();
        }

        public final void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static final class ArraysByteArrayCopier implements ByteArrayCopier {
    }

    public interface ByteArrayCopier {
    }

    public static abstract class LeafByteString extends ByteString {
    }

    public static class LiteralByteString extends LeafByteString {
        private static final long serialVersionUID = 1;
        public final byte[] bytes;

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ByteString) || size() != ((ByteString) obj).size()) {
                return false;
            }
            if (size() == 0) {
                return true;
            }
            if (!(obj instanceof LiteralByteString)) {
                return obj.equals(this);
            }
            LiteralByteString literalByteString = (LiteralByteString) obj;
            int peekCachedHashCode = peekCachedHashCode();
            int peekCachedHashCode2 = literalByteString.peekCachedHashCode();
            if (peekCachedHashCode != 0 && peekCachedHashCode2 != 0 && peekCachedHashCode != peekCachedHashCode2) {
                return false;
            }
            int size = size();
            if (size > literalByteString.size()) {
                throw new IllegalArgumentException("Length too large: " + size + size());
            } else if (0 + size <= literalByteString.size()) {
                byte[] bArr = this.bytes;
                byte[] bArr2 = literalByteString.bytes;
                int offsetIntoBytes = getOffsetIntoBytes() + size;
                int offsetIntoBytes2 = getOffsetIntoBytes();
                int offsetIntoBytes3 = literalByteString.getOffsetIntoBytes() + 0;
                while (offsetIntoBytes2 < offsetIntoBytes) {
                    if (bArr[offsetIntoBytes2] != bArr2[offsetIntoBytes3]) {
                        return false;
                    }
                    offsetIntoBytes2++;
                    offsetIntoBytes3++;
                }
                return true;
            } else {
                StringBuilder m = GridLayoutManager$$ExternalSyntheticOutline0.m19m("Ran off end of other: ", 0, ", ", size, ", ");
                m.append(literalByteString.size());
                throw new IllegalArgumentException(m.toString());
            }
        }

        public int getOffsetIntoBytes() {
            return 0;
        }

        public byte byteAt(int i) {
            return this.bytes[i];
        }

        public byte internalByteAt(int i) {
            return this.bytes[i];
        }

        public final int partialHash(int i, int i2) {
            byte[] bArr = this.bytes;
            int offsetIntoBytes = getOffsetIntoBytes() + 0;
            Charset charset = Internal.UTF_8;
            for (int i3 = offsetIntoBytes; i3 < offsetIntoBytes + i2; i3++) {
                i = (i * 31) + bArr[i3];
            }
            return i;
        }

        public int size() {
            return this.bytes.length;
        }

        public final String toStringInternal(Charset charset) {
            return new String(this.bytes, getOffsetIntoBytes(), size(), charset);
        }

        public LiteralByteString(byte[] bArr) {
            Objects.requireNonNull(bArr);
            this.bytes = bArr;
        }

        public final boolean isValidUtf8() {
            int offsetIntoBytes = getOffsetIntoBytes();
            Utf8.Processor processor = Utf8.processor;
            Objects.requireNonNull(processor);
            if (processor.partialIsValidUtf8(this.bytes, offsetIntoBytes, size() + offsetIntoBytes) == 0) {
                return true;
            }
            return false;
        }
    }

    public static final class SystemByteArrayCopier implements ByteArrayCopier {
    }

    public abstract byte byteAt(int i);

    public abstract boolean equals(Object obj);

    public abstract byte internalByteAt(int i);

    public abstract boolean isValidUtf8();

    public abstract int partialHash(int i, int i2);

    public abstract int size();

    public final String toString() {
        return String.format("<ByteString@%s size=%d>", new Object[]{Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(size())});
    }

    public abstract String toStringInternal(Charset charset);

    static {
        boolean z;
        ByteArrayCopier byteArrayCopier2;
        if (Android.MEMORY_CLASS == null || Android.IS_ROBOLECTRIC) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            byteArrayCopier2 = new SystemByteArrayCopier();
        } else {
            byteArrayCopier2 = new ArraysByteArrayCopier();
        }
        byteArrayCopier = byteArrayCopier2;
    }

    public final int hashCode() {
        int i = this.hash;
        if (i == 0) {
            int size = size();
            i = partialHash(size, size);
            if (i == 0) {
                i = 1;
            }
            this.hash = i;
        }
        return i;
    }

    public final Iterator iterator() {
        return new AbstractByteIterator() {
            public final int limit;
            public int position = 0;

            {
                this.limit = ByteString.this.size();
            }

            public final boolean hasNext() {
                if (this.position < this.limit) {
                    return true;
                }
                return false;
            }
        };
    }

    public final int peekCachedHashCode() {
        return this.hash;
    }
}
