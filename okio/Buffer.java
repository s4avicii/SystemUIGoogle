package okio;

import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import java.io.Closeable;
import java.io.EOFException;
import java.io.Flushable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import okio.internal.BufferKt;

/* compiled from: Buffer.kt */
public final class Buffer implements BufferedSource, Closeable, Flushable, WritableByteChannel, Cloneable, ByteChannel {
    public Segment head;
    public long size;

    public final void close() {
    }

    public final void flush() {
    }

    public final long indexOfElement(ByteString byteString) {
        return indexOfElement(byteString, 0);
    }

    public final boolean isOpen() {
        return true;
    }

    public final long read(Buffer buffer, long j) {
        int i;
        Segment segment;
        Buffer buffer2 = buffer;
        long j2 = 0;
        if (j >= 0) {
            long j3 = this.size;
            if (j3 == 0) {
                return -1;
            }
            long j4 = j > j3 ? j3 : j;
            if (this != buffer2) {
                Util.checkOffsetAndCount(j3, 0, j4);
                long j5 = j4;
                while (true) {
                    if (j5 <= j2) {
                        break;
                    }
                    Segment segment2 = this.head;
                    Intrinsics.checkNotNull(segment2);
                    int i2 = segment2.limit;
                    Segment segment3 = this.head;
                    Intrinsics.checkNotNull(segment3);
                    if (j5 < ((long) (i2 - segment3.pos))) {
                        Segment segment4 = buffer2.head;
                        Segment segment5 = segment4 != null ? segment4.prev : null;
                        if (segment5 != null && segment5.owner) {
                            if ((((long) segment5.limit) + j5) - ((long) (segment5.shared ? 0 : segment5.pos)) <= 8192) {
                                Segment segment6 = this.head;
                                Intrinsics.checkNotNull(segment6);
                                segment6.writeTo(segment5, (int) j5);
                                this.size -= j5;
                                buffer2.size += j5;
                                break;
                            }
                        }
                        Segment segment7 = this.head;
                        Intrinsics.checkNotNull(segment7);
                        int i3 = (int) j5;
                        if (i3 > 0 && i3 <= segment7.limit - segment7.pos) {
                            if (i3 >= 1024) {
                                segment = segment7.sharedCopy();
                            } else {
                                segment = SegmentPool.take();
                                byte[] bArr = segment7.data;
                                byte[] bArr2 = segment.data;
                                int i4 = segment7.pos;
                                System.arraycopy(bArr, i4, bArr2, 0, (i4 + i3) - i4);
                            }
                            segment.limit = segment.pos + i3;
                            segment7.pos += i3;
                            Segment segment8 = segment7.prev;
                            Intrinsics.checkNotNull(segment8);
                            segment8.push(segment);
                            this.head = segment;
                        } else {
                            throw new IllegalArgumentException("byteCount out of range".toString());
                        }
                    }
                    Segment segment9 = this.head;
                    Intrinsics.checkNotNull(segment9);
                    long j6 = (long) (segment9.limit - segment9.pos);
                    this.head = segment9.pop();
                    Segment segment10 = buffer2.head;
                    if (segment10 == null) {
                        buffer2.head = segment9;
                        segment9.prev = segment9;
                        segment9.next = segment9;
                    } else {
                        Segment segment11 = segment10.prev;
                        Intrinsics.checkNotNull(segment11);
                        segment11.push(segment9);
                        Segment segment12 = segment9.prev;
                        if (segment12 != segment9) {
                            Intrinsics.checkNotNull(segment12);
                            if (segment12.owner) {
                                int i5 = segment9.limit - segment9.pos;
                                Segment segment13 = segment9.prev;
                                Intrinsics.checkNotNull(segment13);
                                int i6 = 8192 - segment13.limit;
                                Segment segment14 = segment9.prev;
                                Intrinsics.checkNotNull(segment14);
                                if (segment14.shared) {
                                    i = 0;
                                } else {
                                    Segment segment15 = segment9.prev;
                                    Intrinsics.checkNotNull(segment15);
                                    i = segment15.pos;
                                }
                                if (i5 <= i6 + i) {
                                    Segment segment16 = segment9.prev;
                                    Intrinsics.checkNotNull(segment16);
                                    segment9.writeTo(segment16, i5);
                                    segment9.pop();
                                    SegmentPool.recycle(segment9);
                                }
                            }
                        } else {
                            throw new IllegalStateException("cannot compact".toString());
                        }
                    }
                    this.size -= j6;
                    buffer2.size += j6;
                    j5 -= j6;
                    j2 = 0;
                }
                return j4;
            }
            throw new IllegalArgumentException("source == this".toString());
        }
        throw new IllegalArgumentException(Intrinsics.stringPlus("byteCount < 0: ", Long.valueOf(j)).toString());
    }

    public final int select(Options options) {
        int selectPrefix = BufferKt.selectPrefix(this, options, false);
        if (selectPrefix == -1) {
            return -1;
        }
        ByteString byteString = options.byteStrings[selectPrefix];
        Objects.requireNonNull(byteString);
        skip((long) byteString.getSize$external__okio__android_common__okio_lib());
        return selectPrefix;
    }

    public final Buffer writeInt(int i) {
        Segment writableSegment$external__okio__android_common__okio_lib = writableSegment$external__okio__android_common__okio_lib(4);
        byte[] bArr = writableSegment$external__okio__android_common__okio_lib.data;
        int i2 = writableSegment$external__okio__android_common__okio_lib.limit;
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((i >>> 24) & 255);
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((i >>> 16) & 255);
        int i5 = i4 + 1;
        bArr[i4] = (byte) ((i >>> 8) & 255);
        bArr[i5] = (byte) (i & 255);
        writableSegment$external__okio__android_common__okio_lib.limit = i5 + 1;
        this.size += 4;
        return this;
    }

    public final Buffer writeUtf8(String str, int i, int i2) {
        boolean z;
        boolean z2;
        boolean z3;
        char c;
        boolean z4;
        char charAt;
        if (i >= 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            if (i2 >= i) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                if (i2 <= str.length()) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (z3) {
                    while (i < i2) {
                        char charAt2 = str.charAt(i);
                        if (charAt2 < 128) {
                            Segment writableSegment$external__okio__android_common__okio_lib = writableSegment$external__okio__android_common__okio_lib(1);
                            byte[] bArr = writableSegment$external__okio__android_common__okio_lib.data;
                            int i3 = writableSegment$external__okio__android_common__okio_lib.limit - i;
                            int min = Math.min(i2, 8192 - i3);
                            int i4 = i + 1;
                            bArr[i + i3] = (byte) charAt2;
                            while (true) {
                                i = i4;
                                if (i >= min || (charAt = str.charAt(i)) >= 128) {
                                    int i5 = writableSegment$external__okio__android_common__okio_lib.limit;
                                    int i6 = (i3 + i) - i5;
                                    writableSegment$external__okio__android_common__okio_lib.limit = i5 + i6;
                                    this.size += (long) i6;
                                } else {
                                    i4 = i + 1;
                                    bArr[i + i3] = (byte) charAt;
                                }
                            }
                            int i52 = writableSegment$external__okio__android_common__okio_lib.limit;
                            int i62 = (i3 + i) - i52;
                            writableSegment$external__okio__android_common__okio_lib.limit = i52 + i62;
                            this.size += (long) i62;
                        } else {
                            if (charAt2 < 2048) {
                                Segment writableSegment$external__okio__android_common__okio_lib2 = writableSegment$external__okio__android_common__okio_lib(2);
                                byte[] bArr2 = writableSegment$external__okio__android_common__okio_lib2.data;
                                int i7 = writableSegment$external__okio__android_common__okio_lib2.limit;
                                bArr2[i7] = (byte) ((charAt2 >> 6) | 192);
                                bArr2[i7 + 1] = (byte) ((charAt2 & '?') | 128);
                                writableSegment$external__okio__android_common__okio_lib2.limit = i7 + 2;
                                this.size += 2;
                            } else if (charAt2 < 55296 || charAt2 > 57343) {
                                Segment writableSegment$external__okio__android_common__okio_lib3 = writableSegment$external__okio__android_common__okio_lib(3);
                                byte[] bArr3 = writableSegment$external__okio__android_common__okio_lib3.data;
                                int i8 = writableSegment$external__okio__android_common__okio_lib3.limit;
                                bArr3[i8] = (byte) ((charAt2 >> 12) | 224);
                                bArr3[i8 + 1] = (byte) ((63 & (charAt2 >> 6)) | 128);
                                bArr3[i8 + 2] = (byte) ((charAt2 & '?') | 128);
                                writableSegment$external__okio__android_common__okio_lib3.limit = i8 + 3;
                                this.size += 3;
                            } else {
                                int i9 = i + 1;
                                if (i9 < i2) {
                                    c = str.charAt(i9);
                                } else {
                                    c = 0;
                                }
                                if (charAt2 <= 56319) {
                                    if (56320 > c || c >= 57344) {
                                        z4 = false;
                                    } else {
                                        z4 = true;
                                    }
                                    if (z4) {
                                        int i10 = (((charAt2 & 1023) << 10) | (c & 1023)) + 0;
                                        Segment writableSegment$external__okio__android_common__okio_lib4 = writableSegment$external__okio__android_common__okio_lib(4);
                                        byte[] bArr4 = writableSegment$external__okio__android_common__okio_lib4.data;
                                        int i11 = writableSegment$external__okio__android_common__okio_lib4.limit;
                                        bArr4[i11] = (byte) ((i10 >> 18) | 240);
                                        bArr4[i11 + 1] = (byte) (((i10 >> 12) & 63) | 128);
                                        bArr4[i11 + 2] = (byte) (((i10 >> 6) & 63) | 128);
                                        bArr4[i11 + 3] = (byte) ((i10 & 63) | 128);
                                        writableSegment$external__okio__android_common__okio_lib4.limit = i11 + 4;
                                        this.size += 4;
                                        i += 2;
                                    }
                                }
                                Segment writableSegment$external__okio__android_common__okio_lib5 = writableSegment$external__okio__android_common__okio_lib(1);
                                byte[] bArr5 = writableSegment$external__okio__android_common__okio_lib5.data;
                                int i12 = writableSegment$external__okio__android_common__okio_lib5.limit;
                                writableSegment$external__okio__android_common__okio_lib5.limit = i12 + 1;
                                bArr5[i12] = (byte) 63;
                                this.size++;
                                i = i9;
                            }
                            i++;
                        }
                    }
                    return this;
                }
                StringBuilder m = ExifInterface$$ExternalSyntheticOutline0.m13m("endIndex > string.length: ", i2, " > ");
                m.append(str.length());
                throw new IllegalArgumentException(m.toString().toString());
            }
            throw new IllegalArgumentException(("endIndex < beginIndex: " + i2 + " < " + i).toString());
        }
        throw new IllegalArgumentException(Intrinsics.stringPlus("beginIndex < 0: ", Integer.valueOf(i)).toString());
    }

    public final Object clone() {
        Buffer buffer = new Buffer();
        if (this.size != 0) {
            Segment segment = this.head;
            Intrinsics.checkNotNull(segment);
            Segment sharedCopy = segment.sharedCopy();
            buffer.head = sharedCopy;
            sharedCopy.prev = sharedCopy;
            sharedCopy.next = sharedCopy;
            for (Segment segment2 = segment.next; segment2 != segment; segment2 = segment2.next) {
                Segment segment3 = sharedCopy.prev;
                Intrinsics.checkNotNull(segment3);
                Intrinsics.checkNotNull(segment2);
                segment3.push(segment2.sharedCopy());
            }
            buffer.size = this.size;
        }
        return buffer;
    }

    /* JADX WARNING: type inference failed for: r19v0, types: [java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(java.lang.Object r19) {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            r2 = 0
            r3 = 1
            if (r0 != r1) goto L_0x000b
        L_0x0008:
            r2 = r3
            goto L_0x007c
        L_0x000b:
            boolean r4 = r1 instanceof okio.Buffer
            if (r4 != 0) goto L_0x0011
            goto L_0x007c
        L_0x0011:
            long r4 = r0.size
            okio.Buffer r1 = (okio.Buffer) r1
            java.util.Objects.requireNonNull(r1)
            long r6 = r1.size
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 == 0) goto L_0x001f
            goto L_0x007c
        L_0x001f:
            long r4 = r0.size
            r6 = 0
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 != 0) goto L_0x0028
            goto L_0x0008
        L_0x0028:
            okio.Segment r4 = r0.head
            kotlin.jvm.internal.Intrinsics.checkNotNull(r4)
            okio.Segment r1 = r1.head
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1)
            int r5 = r4.pos
            int r8 = r1.pos
            r9 = r6
        L_0x0037:
            long r11 = r0.size
            int r11 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r11 >= 0) goto L_0x0008
            int r11 = r4.limit
            int r11 = r11 - r5
            int r12 = r1.limit
            int r12 = r12 - r8
            int r11 = java.lang.Math.min(r11, r12)
            long r11 = (long) r11
            r13 = r6
        L_0x0049:
            int r15 = (r13 > r11 ? 1 : (r13 == r11 ? 0 : -1))
            if (r15 >= 0) goto L_0x0064
            r15 = 1
            long r13 = r13 + r15
            byte[] r15 = r4.data
            int r16 = r5 + 1
            byte r5 = r15[r5]
            byte[] r15 = r1.data
            int r17 = r8 + 1
            byte r8 = r15[r8]
            if (r5 == r8) goto L_0x005f
            goto L_0x007c
        L_0x005f:
            r5 = r16
            r8 = r17
            goto L_0x0049
        L_0x0064:
            int r13 = r4.limit
            if (r5 != r13) goto L_0x006f
            okio.Segment r4 = r4.next
            kotlin.jvm.internal.Intrinsics.checkNotNull(r4)
            int r5 = r4.pos
        L_0x006f:
            int r13 = r1.limit
            if (r8 != r13) goto L_0x007a
            okio.Segment r1 = r1.next
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1)
            int r8 = r1.pos
        L_0x007a:
            long r9 = r9 + r11
            goto L_0x0037
        L_0x007c:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.Buffer.equals(java.lang.Object):boolean");
    }

    public final byte getByte(long j) {
        Util.checkOffsetAndCount(this.size, j, 1);
        Segment segment = this.head;
        if (segment != null) {
            long j2 = this.size;
            if (j2 - j < j) {
                while (j2 > j) {
                    segment = segment.prev;
                    Intrinsics.checkNotNull(segment);
                    j2 -= (long) (segment.limit - segment.pos);
                }
                return segment.data[(int) ((((long) segment.pos) + j) - j2)];
            }
            long j3 = 0;
            while (true) {
                int i = segment.limit;
                int i2 = segment.pos;
                long j4 = ((long) (i - i2)) + j3;
                if (j4 > j) {
                    return segment.data[(int) ((((long) i2) + j) - j3)];
                }
                segment = segment.next;
                Intrinsics.checkNotNull(segment);
                j3 = j4;
            }
        } else {
            Intrinsics.checkNotNull((Object) null);
            throw null;
        }
    }

    public final int hashCode() {
        Segment segment = this.head;
        if (segment == null) {
            return 0;
        }
        int i = 1;
        do {
            int i2 = segment.limit;
            for (int i3 = segment.pos; i3 < i2; i3++) {
                i = (i * 31) + segment.data[i3];
            }
            segment = segment.next;
            Intrinsics.checkNotNull(segment);
        } while (segment != this.head);
        return i;
    }

    public final long indexOfElement(ByteString byteString, long j) {
        int i;
        int i2;
        int i3;
        int i4;
        long j2 = 0;
        if (j >= 0) {
            Segment segment = this.head;
            if (segment == null) {
                return -1;
            }
            long j3 = this.size;
            if (j3 - j < j) {
                while (j3 > j) {
                    segment = segment.prev;
                    Intrinsics.checkNotNull(segment);
                    j3 -= (long) (segment.limit - segment.pos);
                }
                if (byteString.getSize$external__okio__android_common__okio_lib() == 2) {
                    byte internalGet$external__okio__android_common__okio_lib = byteString.internalGet$external__okio__android_common__okio_lib(0);
                    byte internalGet$external__okio__android_common__okio_lib2 = byteString.internalGet$external__okio__android_common__okio_lib(1);
                    while (j3 < this.size) {
                        byte[] bArr = segment.data;
                        i3 = (int) ((((long) segment.pos) + j) - j3);
                        int i5 = segment.limit;
                        while (i3 < i5) {
                            byte b = bArr[i3];
                            if (b == internalGet$external__okio__android_common__okio_lib || b == internalGet$external__okio__android_common__okio_lib2) {
                                i4 = segment.pos;
                            } else {
                                i3++;
                            }
                        }
                        j3 += (long) (segment.limit - segment.pos);
                        segment = segment.next;
                        Intrinsics.checkNotNull(segment);
                        j = j3;
                    }
                    return -1;
                }
                byte[] internalArray$external__okio__android_common__okio_lib = byteString.internalArray$external__okio__android_common__okio_lib();
                while (j3 < this.size) {
                    byte[] bArr2 = segment.data;
                    int i6 = (int) ((((long) segment.pos) + j) - j3);
                    int i7 = segment.limit;
                    while (i3 < i7) {
                        byte b2 = bArr2[i3];
                        int length = internalArray$external__okio__android_common__okio_lib.length;
                        int i8 = 0;
                        while (i8 < length) {
                            byte b3 = internalArray$external__okio__android_common__okio_lib[i8];
                            i8++;
                            if (b2 == b3) {
                                i4 = segment.pos;
                            }
                        }
                        i6 = i3 + 1;
                    }
                    j3 += (long) (segment.limit - segment.pos);
                    segment = segment.next;
                    Intrinsics.checkNotNull(segment);
                    j = j3;
                }
                return -1;
                return ((long) (i3 - i4)) + j3;
            }
            while (true) {
                long j4 = ((long) (segment.limit - segment.pos)) + j2;
                if (j4 > j) {
                    break;
                }
                segment = segment.next;
                Intrinsics.checkNotNull(segment);
                j2 = j4;
            }
            if (byteString.getSize$external__okio__android_common__okio_lib() == 2) {
                byte internalGet$external__okio__android_common__okio_lib3 = byteString.internalGet$external__okio__android_common__okio_lib(0);
                byte internalGet$external__okio__android_common__okio_lib4 = byteString.internalGet$external__okio__android_common__okio_lib(1);
                while (j2 < this.size) {
                    byte[] bArr3 = segment.data;
                    i = (int) ((((long) segment.pos) + j) - j2);
                    int i9 = segment.limit;
                    while (i < i9) {
                        byte b4 = bArr3[i];
                        if (b4 == internalGet$external__okio__android_common__okio_lib3 || b4 == internalGet$external__okio__android_common__okio_lib4) {
                            i2 = segment.pos;
                        } else {
                            i++;
                        }
                    }
                    j2 += (long) (segment.limit - segment.pos);
                    segment = segment.next;
                    Intrinsics.checkNotNull(segment);
                    j = j2;
                }
                return -1;
            }
            byte[] internalArray$external__okio__android_common__okio_lib2 = byteString.internalArray$external__okio__android_common__okio_lib();
            while (j2 < this.size) {
                byte[] bArr4 = segment.data;
                int i10 = (int) ((((long) segment.pos) + j) - j2);
                int i11 = segment.limit;
                while (i < i11) {
                    byte b5 = bArr4[i];
                    int length2 = internalArray$external__okio__android_common__okio_lib2.length;
                    int i12 = 0;
                    while (i12 < length2) {
                        byte b6 = internalArray$external__okio__android_common__okio_lib2[i12];
                        i12++;
                        if (b5 == b6) {
                            i2 = segment.pos;
                        }
                    }
                    i10 = i + 1;
                }
                j2 += (long) (segment.limit - segment.pos);
                segment = segment.next;
                Intrinsics.checkNotNull(segment);
                j = j2;
            }
            return -1;
            return ((long) (i - i2)) + j2;
        }
        throw new IllegalArgumentException(Intrinsics.stringPlus("fromIndex < 0: ", Long.valueOf(j)).toString());
    }

    public final byte readByte() throws EOFException {
        if (this.size != 0) {
            Segment segment = this.head;
            Intrinsics.checkNotNull(segment);
            int i = segment.pos;
            int i2 = segment.limit;
            int i3 = i + 1;
            byte b = segment.data[i];
            this.size--;
            if (i3 == i2) {
                this.head = segment.pop();
                SegmentPool.recycle(segment);
            } else {
                segment.pos = i3;
            }
            return b;
        }
        throw new EOFException();
    }

    public final byte[] readByteArray(long j) throws EOFException {
        boolean z;
        int i;
        int i2 = 0;
        if (j < 0 || j > 2147483647L) {
            z = false;
        } else {
            z = true;
        }
        if (!z) {
            throw new IllegalArgumentException(Intrinsics.stringPlus("byteCount: ", Long.valueOf(j)).toString());
        } else if (this.size >= j) {
            int i3 = (int) j;
            byte[] bArr = new byte[i3];
            while (i2 < i3) {
                int i4 = i3 - i2;
                Util.checkOffsetAndCount((long) i3, (long) i2, (long) i4);
                Segment segment = this.head;
                if (segment == null) {
                    i = -1;
                } else {
                    i = Math.min(i4, segment.limit - segment.pos);
                    byte[] bArr2 = segment.data;
                    int i5 = segment.pos;
                    System.arraycopy(bArr2, i5, bArr, i2, (i5 + i) - i5);
                    int i6 = segment.pos + i;
                    segment.pos = i6;
                    this.size -= (long) i;
                    if (i6 == segment.limit) {
                        this.head = segment.pop();
                        SegmentPool.recycle(segment);
                    }
                }
                if (i != -1) {
                    i2 += i;
                } else {
                    throw new EOFException();
                }
            }
            return bArr;
        } else {
            throw new EOFException();
        }
    }

    public final ByteString readByteString() {
        boolean z;
        long j = this.size;
        if (j < 0 || j > 2147483647L) {
            z = false;
        } else {
            z = true;
        }
        if (!z) {
            throw new IllegalArgumentException(Intrinsics.stringPlus("byteCount: ", Long.valueOf(j)).toString());
        } else if (j < j) {
            throw new EOFException();
        } else if (j < 4096) {
            return new ByteString(readByteArray(j));
        } else {
            ByteString snapshot = snapshot((int) j);
            skip(j);
            return snapshot;
        }
    }

    public final String readString(long j, Charset charset) throws EOFException {
        boolean z;
        int i = (j > 0 ? 1 : (j == 0 ? 0 : -1));
        if (i < 0 || j > 2147483647L) {
            z = false;
        } else {
            z = true;
        }
        if (!z) {
            throw new IllegalArgumentException(Intrinsics.stringPlus("byteCount: ", Long.valueOf(j)).toString());
        } else if (this.size < j) {
            throw new EOFException();
        } else if (i == 0) {
            return "";
        } else {
            Segment segment = this.head;
            Intrinsics.checkNotNull(segment);
            int i2 = segment.pos;
            if (((long) i2) + j > ((long) segment.limit)) {
                return new String(readByteArray(j), charset);
            }
            int i3 = (int) j;
            String str = new String(segment.data, i2, i3, charset);
            int i4 = segment.pos + i3;
            segment.pos = i4;
            this.size -= j;
            if (i4 == segment.limit) {
                this.head = segment.pop();
                SegmentPool.recycle(segment);
            }
            return str;
        }
    }

    public final String readUtf8(long j) throws EOFException {
        return readString(j, Charsets.UTF_8);
    }

    public final boolean request(long j) {
        if (this.size >= j) {
            return true;
        }
        return false;
    }

    public final void skip(long j) throws EOFException {
        while (j > 0) {
            Segment segment = this.head;
            if (segment != null) {
                int min = (int) Math.min(j, (long) (segment.limit - segment.pos));
                long j2 = (long) min;
                this.size -= j2;
                j -= j2;
                int i = segment.pos + min;
                segment.pos = i;
                if (i == segment.limit) {
                    this.head = segment.pop();
                    SegmentPool.recycle(segment);
                }
            } else {
                throw new EOFException();
            }
        }
    }

    public final ByteString snapshot(int i) {
        if (i == 0) {
            return ByteString.EMPTY;
        }
        Util.checkOffsetAndCount(this.size, 0, (long) i);
        Segment segment = this.head;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i3 < i) {
            Intrinsics.checkNotNull(segment);
            int i5 = segment.limit;
            int i6 = segment.pos;
            if (i5 != i6) {
                i3 += i5 - i6;
                i4++;
                segment = segment.next;
            } else {
                throw new AssertionError("s.limit == s.pos");
            }
        }
        byte[][] bArr = new byte[i4][];
        int[] iArr = new int[(i4 * 2)];
        Segment segment2 = this.head;
        int i7 = 0;
        while (i2 < i) {
            Intrinsics.checkNotNull(segment2);
            bArr[i7] = segment2.data;
            i2 += segment2.limit - segment2.pos;
            iArr[i7] = Math.min(i2, i);
            iArr[i7 + i4] = segment2.pos;
            segment2.shared = true;
            i7++;
            segment2 = segment2.next;
        }
        return new SegmentedByteString(bArr, iArr);
    }

    public final String toString() {
        boolean z;
        long j = this.size;
        if (j <= 2147483647L) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return snapshot((int) j).toString();
        }
        throw new IllegalStateException(Intrinsics.stringPlus("size > Int.MAX_VALUE: ", Long.valueOf(j)).toString());
    }

    public final Segment writableSegment$external__okio__android_common__okio_lib(int i) {
        boolean z = true;
        if (i < 1 || i > 8192) {
            z = false;
        }
        if (z) {
            Segment segment = this.head;
            if (segment == null) {
                Segment take = SegmentPool.take();
                this.head = take;
                take.prev = take;
                take.next = take;
                return take;
            }
            Intrinsics.checkNotNull(segment);
            Segment segment2 = segment.prev;
            Intrinsics.checkNotNull(segment2);
            if (segment2.limit + i <= 8192 && segment2.owner) {
                return segment2;
            }
            Segment take2 = SegmentPool.take();
            segment2.push(take2);
            return take2;
        }
        throw new IllegalArgumentException("unexpected capacity".toString());
    }

    public final int write(ByteBuffer byteBuffer) throws IOException {
        int remaining = byteBuffer.remaining();
        int i = remaining;
        while (i > 0) {
            Segment writableSegment$external__okio__android_common__okio_lib = writableSegment$external__okio__android_common__okio_lib(1);
            int min = Math.min(i, 8192 - writableSegment$external__okio__android_common__okio_lib.limit);
            byteBuffer.get(writableSegment$external__okio__android_common__okio_lib.data, writableSegment$external__okio__android_common__okio_lib.limit, min);
            i -= min;
            writableSegment$external__okio__android_common__okio_lib.limit += min;
        }
        this.size += (long) remaining;
        return remaining;
    }

    public final int read(ByteBuffer byteBuffer) throws IOException {
        Segment segment = this.head;
        if (segment == null) {
            return -1;
        }
        int min = Math.min(byteBuffer.remaining(), segment.limit - segment.pos);
        byteBuffer.put(segment.data, segment.pos, min);
        int i = segment.pos + min;
        segment.pos = i;
        this.size -= (long) min;
        if (i == segment.limit) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
        }
        return min;
    }
}
