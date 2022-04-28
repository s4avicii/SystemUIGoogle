package okio;

import java.util.Objects;
import okio.internal.SegmentedByteStringKt;

/* compiled from: SegmentedByteString.kt */
public final class SegmentedByteString extends ByteString {
    public final transient int[] directory;
    public final transient byte[][] segments;

    public final boolean rangeEquals(int i, byte[] bArr, int i2, int i3) {
        int i4;
        boolean z;
        if (i < 0 || i > getSize$external__okio__android_common__okio_lib() - i3 || i2 < 0 || i2 > bArr.length - i3) {
            return false;
        }
        int i5 = i3 + i;
        int segment = SegmentedByteStringKt.segment(this, i);
        while (i < i5) {
            if (segment == 0) {
                i4 = 0;
            } else {
                i4 = this.directory[segment - 1];
            }
            int[] iArr = this.directory;
            int i6 = iArr[this.segments.length + segment];
            int min = Math.min(i5, (iArr[segment] - i4) + i4) - i;
            int i7 = (i - i4) + i6;
            byte[] bArr2 = this.segments[segment];
            int i8 = 0;
            while (true) {
                if (i8 >= min) {
                    z = true;
                    break;
                }
                int i9 = i8 + 1;
                if (bArr2[i8 + i7] != bArr[i8 + i2]) {
                    z = false;
                    break;
                }
                i8 = i9;
            }
            if (!z) {
                return false;
            }
            i2 += min;
            i += min;
            segment++;
        }
        return true;
    }

    public SegmentedByteString(byte[][] bArr, int[] iArr) {
        super(ByteString.EMPTY.getData$external__okio__android_common__okio_lib());
        this.segments = bArr;
        this.directory = iArr;
    }

    private final Object writeReplace() {
        return new ByteString(toByteArray());
    }

    public final boolean equals(Object obj) {
        if (obj != this) {
            if (obj instanceof ByteString) {
                ByteString byteString = (ByteString) obj;
                Objects.requireNonNull(byteString);
                if (byteString.getSize$external__okio__android_common__okio_lib() != getSize$external__okio__android_common__okio_lib() || !rangeEquals(byteString, getSize$external__okio__android_common__okio_lib())) {
                    return false;
                }
            }
            return false;
        }
        return true;
    }

    public final int getSize$external__okio__android_common__okio_lib() {
        return this.directory[this.segments.length - 1];
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int length = this.segments.length;
        int i2 = 0;
        int i3 = 1;
        int i4 = 0;
        while (i2 < length) {
            int[] iArr = this.directory;
            int i5 = iArr[length + i2];
            int i6 = iArr[i2];
            byte[] bArr = this.segments[i2];
            int i7 = (i6 - i4) + i5;
            while (i5 < i7) {
                i3 = (i3 * 31) + bArr[i5];
                i5++;
            }
            i2++;
            i4 = i6;
        }
        this.hashCode = i3;
        return i3;
    }

    public final String hex() {
        return new ByteString(toByteArray()).hex();
    }

    public final byte internalGet$external__okio__android_common__okio_lib(int i) {
        int i2;
        Util.checkOffsetAndCount((long) this.directory[this.segments.length - 1], (long) i, 1);
        int segment = SegmentedByteStringKt.segment(this, i);
        if (segment == 0) {
            i2 = 0;
        } else {
            i2 = this.directory[segment - 1];
        }
        int[] iArr = this.directory;
        byte[][] bArr = this.segments;
        return bArr[segment][(i - i2) + iArr[bArr.length + segment]];
    }

    public final String toString() {
        return new ByteString(toByteArray()).toString();
    }

    public final byte[] internalArray$external__okio__android_common__okio_lib() {
        return toByteArray();
    }

    public final byte[] toByteArray() {
        byte[] bArr = new byte[getSize$external__okio__android_common__okio_lib()];
        int length = this.segments.length;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i < length) {
            int[] iArr = this.directory;
            int i4 = iArr[length + i];
            int i5 = iArr[i];
            int i6 = i5 - i2;
            System.arraycopy(this.segments[i], i4, bArr, i3, (i4 + i6) - i4);
            i3 += i6;
            i++;
            i2 = i5;
        }
        return bArr;
    }

    public final boolean rangeEquals(ByteString byteString, int i) {
        int i2;
        if (getSize$external__okio__android_common__okio_lib() - i < 0) {
            return false;
        }
        int i3 = i + 0;
        int segment = SegmentedByteStringKt.segment(this, 0);
        int i4 = 0;
        int i5 = 0;
        while (i4 < i3) {
            if (segment == 0) {
                i2 = 0;
            } else {
                i2 = this.directory[segment - 1];
            }
            int[] iArr = this.directory;
            int i6 = iArr[this.segments.length + segment];
            int min = Math.min(i3, (iArr[segment] - i2) + i2) - i4;
            if (!byteString.rangeEquals(i5, this.segments[segment], (i4 - i2) + i6, min)) {
                return false;
            }
            i5 += min;
            i4 += min;
            segment++;
        }
        return true;
    }
}
