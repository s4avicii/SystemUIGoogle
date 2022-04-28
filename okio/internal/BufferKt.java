package okio.internal;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import okio.Buffer;
import okio.Options;
import okio.Segment;

/* compiled from: Buffer.kt */
public final class BufferKt {
    static {
        "0123456789abcdef".getBytes(Charsets.UTF_8);
    }

    public static final int selectPrefix(Buffer buffer, Options options, boolean z) {
        int i;
        int i2;
        boolean z2;
        int i3;
        Segment segment;
        int i4;
        Segment segment2 = buffer.head;
        if (segment2 != null) {
            byte[] bArr = segment2.data;
            int i5 = segment2.pos;
            int i6 = segment2.limit;
            int[] iArr = options.trie;
            Segment segment3 = segment2;
            int i7 = -1;
            int i8 = 0;
            loop0:
            while (true) {
                int i9 = i8 + 1;
                int i10 = iArr[i8];
                int i11 = i9 + 1;
                int i12 = iArr[i9];
                if (i12 != -1) {
                    i7 = i12;
                }
                if (segment3 == null) {
                    break;
                }
                if (i10 < 0) {
                    int i13 = (i10 * -1) + i11;
                    while (true) {
                        int i14 = i5 + 1;
                        int i15 = i11 + 1;
                        if ((bArr[i5] & 255) != iArr[i11]) {
                            return i7;
                        }
                        if (i15 == i13) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        if (i14 == i6) {
                            Intrinsics.checkNotNull(segment3);
                            Segment segment4 = segment3.next;
                            Intrinsics.checkNotNull(segment4);
                            i4 = segment4.pos;
                            byte[] bArr2 = segment4.data;
                            i3 = segment4.limit;
                            if (segment4 != segment2) {
                                byte[] bArr3 = bArr2;
                                segment = segment4;
                                bArr = bArr3;
                            } else if (!z2) {
                                break loop0;
                            } else {
                                bArr = bArr2;
                                segment = null;
                            }
                        } else {
                            Segment segment5 = segment3;
                            i3 = i6;
                            i4 = i14;
                            segment = segment5;
                        }
                        if (z2) {
                            i2 = iArr[i15];
                            i = i4;
                            i6 = i3;
                            segment3 = segment;
                            break;
                        }
                        i5 = i4;
                        i6 = i3;
                        i11 = i15;
                        segment3 = segment;
                    }
                } else {
                    i = i5 + 1;
                    byte b = bArr[i5] & 255;
                    int i16 = i11 + i10;
                    while (i11 != i16) {
                        if (b == iArr[i11]) {
                            i2 = iArr[i11 + i10];
                            if (i == i6) {
                                segment3 = segment3.next;
                                Intrinsics.checkNotNull(segment3);
                                i = segment3.pos;
                                bArr = segment3.data;
                                i6 = segment3.limit;
                                if (segment3 == segment2) {
                                    segment3 = null;
                                }
                            }
                        } else {
                            i11++;
                        }
                    }
                    return i7;
                }
                if (i2 >= 0) {
                    return i2;
                }
                i8 = -i2;
                i5 = i;
            }
            if (z) {
                return -2;
            }
            return i7;
        } else if (z) {
            return -2;
        } else {
            return -1;
        }
    }
}
