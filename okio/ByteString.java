package okio;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import okio.internal.ByteStringKt;

/* compiled from: ByteString.kt */
public class ByteString implements Serializable, Comparable<ByteString> {
    public static final ByteString EMPTY = new ByteString(new byte[0]);
    private static final long serialVersionUID = 1;
    private final byte[] data;
    public transient int hashCode;
    public transient String utf8;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ByteString) {
            ByteString byteString = (ByteString) obj;
            Objects.requireNonNull(byteString);
            int size$external__okio__android_common__okio_lib = byteString.getSize$external__okio__android_common__okio_lib();
            byte[] bArr = this.data;
            if (size$external__okio__android_common__okio_lib == bArr.length && byteString.rangeEquals(0, bArr, 0, bArr.length)) {
                return true;
            }
        }
        return false;
    }

    public boolean rangeEquals(int i, byte[] bArr, int i2, int i3) {
        boolean z;
        if (i < 0) {
            return false;
        }
        byte[] bArr2 = this.data;
        if (i > bArr2.length - i3 || i2 < 0 || i2 > bArr.length - i3) {
            return false;
        }
        int i4 = 0;
        while (true) {
            if (i4 >= i3) {
                z = true;
                break;
            }
            int i5 = i4 + 1;
            if (bArr2[i4 + i] != bArr[i4 + i2]) {
                z = false;
                break;
            }
            i4 = i5;
        }
        if (z) {
            return true;
        }
        return false;
    }

    public static final ByteString encodeUtf8(String str) {
        ByteString byteString = new ByteString(str.getBytes(Charsets.UTF_8));
        byteString.utf8 = str;
        return byteString;
    }

    private final void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeInt(this.data.length);
        objectOutputStream.write(this.data);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002e, code lost:
        return 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        return -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0024, code lost:
        if (r6 < r7) goto L_0x002c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x002a, code lost:
        if (r0 < r1) goto L_0x002c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int compareTo(java.lang.Object r9) {
        /*
            r8 = this;
            okio.ByteString r9 = (okio.ByteString) r9
            int r0 = r8.getSize$external__okio__android_common__okio_lib()
            int r1 = r9.getSize$external__okio__android_common__okio_lib()
            int r2 = java.lang.Math.min(r0, r1)
            r3 = 0
            r4 = r3
        L_0x0010:
            r5 = -1
            if (r4 >= r2) goto L_0x0027
            byte r6 = r8.internalGet$external__okio__android_common__okio_lib(r4)
            r6 = r6 & 255(0xff, float:3.57E-43)
            byte r7 = r9.internalGet$external__okio__android_common__okio_lib(r4)
            r7 = r7 & 255(0xff, float:3.57E-43)
            if (r6 != r7) goto L_0x0024
            int r4 = r4 + 1
            goto L_0x0010
        L_0x0024:
            if (r6 >= r7) goto L_0x002e
            goto L_0x002c
        L_0x0027:
            if (r0 != r1) goto L_0x002a
            goto L_0x002f
        L_0x002a:
            if (r0 >= r1) goto L_0x002e
        L_0x002c:
            r3 = r5
            goto L_0x002f
        L_0x002e:
            r3 = 1
        L_0x002f:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.ByteString.compareTo(java.lang.Object):int");
    }

    public int getSize$external__okio__android_common__okio_lib() {
        return this.data.length;
    }

    public int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int hashCode2 = Arrays.hashCode(this.data);
        this.hashCode = hashCode2;
        return hashCode2;
    }

    public String hex() {
        byte[] bArr = this.data;
        char[] cArr = new char[(bArr.length * 2)];
        int length = bArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            byte b = bArr[i];
            i++;
            int i3 = i2 + 1;
            char[] cArr2 = ByteStringKt.HEX_DIGIT_CHARS;
            cArr[i2] = cArr2[(b >> 4) & 15];
            i2 = i3 + 1;
            cArr[i3] = cArr2[b & 15];
        }
        return new String(cArr);
    }

    public byte internalGet$external__okio__android_common__okio_lib(int i) {
        return this.data[i];
    }

    /* JADX WARNING: Code restructure failed: missing block: B:101:0x0106, code lost:
        if (r4 == 64) goto L_0x022f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x0119, code lost:
        if (r4 == 64) goto L_0x022f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x0128, code lost:
        if (r4 == 64) goto L_0x022f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x013a, code lost:
        if (r4 == 64) goto L_0x022f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x0147, code lost:
        if (r4 == 64) goto L_0x022f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x018f, code lost:
        if (r4 == 64) goto L_0x022f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:160:0x01a2, code lost:
        if (r4 == 64) goto L_0x022f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:166:0x01b3, code lost:
        if (r4 == 64) goto L_0x022f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:172:0x01c2, code lost:
        if (r4 == 64) goto L_0x022f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:175:0x01d8, code lost:
        if (r4 == 64) goto L_0x022f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:181:0x01e5, code lost:
        if (r4 == 64) goto L_0x022f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:184:0x01ec, code lost:
        if (r4 == 64) goto L_0x022f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:213:0x022b, code lost:
        if (r4 == 64) goto L_0x022f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:214:0x022e, code lost:
        r5 = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x009e, code lost:
        if (r4 == 64) goto L_0x022f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x00af, code lost:
        if (r4 == 64) goto L_0x022f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x00ba, code lost:
        if (r4 == 64) goto L_0x022f;
     */
    /* JADX WARNING: Removed duplicated region for block: B:245:0x022e A[EDGE_INSN: B:245:0x022e->B:214:0x022e ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:252:0x022e A[EDGE_INSN: B:252:0x022e->B:214:0x022e ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:256:0x022e A[EDGE_INSN: B:256:0x022e->B:214:0x022e ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:258:0x022e A[EDGE_INSN: B:258:0x022e->B:214:0x022e ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:270:0x022e A[EDGE_INSN: B:270:0x022e->B:214:0x022e ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String toString() {
        /*
            r17 = this;
            r0 = r17
            byte[] r1 = r0.data
            int r2 = r1.length
            if (r2 != 0) goto L_0x0009
            r2 = 1
            goto L_0x000a
        L_0x0009:
            r2 = 0
        L_0x000a:
            if (r2 == 0) goto L_0x0010
            java.lang.String r0 = "[size=0]"
            goto L_0x032f
        L_0x0010:
            int r2 = r1.length
            r3 = 0
            r4 = 0
            r5 = 0
        L_0x0014:
            r6 = 64
            if (r3 >= r2) goto L_0x022f
            byte r7 = r1[r3]
            r8 = 10
            r9 = 13
            r10 = 127(0x7f, float:1.78E-43)
            r11 = 160(0xa0, float:2.24E-43)
            r12 = 32
            r14 = 65533(0xfffd, float:9.1831E-41)
            r15 = 65536(0x10000, float:9.18355E-41)
            if (r7 < 0) goto L_0x0093
            int r16 = r4 + 1
            if (r4 != r6) goto L_0x0031
            goto L_0x022f
        L_0x0031:
            if (r7 == r8) goto L_0x004d
            if (r7 == r9) goto L_0x004d
            if (r7 < 0) goto L_0x003b
            if (r7 >= r12) goto L_0x003b
            r4 = 1
            goto L_0x003c
        L_0x003b:
            r4 = 0
        L_0x003c:
            if (r4 != 0) goto L_0x004a
            if (r10 > r7) goto L_0x0044
            if (r7 >= r11) goto L_0x0044
            r4 = 1
            goto L_0x0045
        L_0x0044:
            r4 = 0
        L_0x0045:
            if (r4 == 0) goto L_0x0048
            goto L_0x004a
        L_0x0048:
            r4 = 0
            goto L_0x004b
        L_0x004a:
            r4 = 1
        L_0x004b:
            if (r4 != 0) goto L_0x022e
        L_0x004d:
            if (r7 != r14) goto L_0x0051
            goto L_0x022e
        L_0x0051:
            if (r7 >= r15) goto L_0x0055
            r4 = 1
            goto L_0x0056
        L_0x0055:
            r4 = 2
        L_0x0056:
            int r5 = r5 + r4
            int r3 = r3 + 1
        L_0x0059:
            r4 = r16
            if (r3 >= r2) goto L_0x0014
            byte r7 = r1[r3]
            if (r7 < 0) goto L_0x0014
            int r7 = r3 + 1
            byte r3 = r1[r3]
            int r16 = r4 + 1
            if (r4 != r6) goto L_0x006b
            goto L_0x022f
        L_0x006b:
            if (r3 == r8) goto L_0x0087
            if (r3 == r9) goto L_0x0087
            if (r3 < 0) goto L_0x0075
            if (r3 >= r12) goto L_0x0075
            r4 = 1
            goto L_0x0076
        L_0x0075:
            r4 = 0
        L_0x0076:
            if (r4 != 0) goto L_0x0084
            if (r10 > r3) goto L_0x007e
            if (r3 >= r11) goto L_0x007e
            r4 = 1
            goto L_0x007f
        L_0x007e:
            r4 = 0
        L_0x007f:
            if (r4 == 0) goto L_0x0082
            goto L_0x0084
        L_0x0082:
            r4 = 0
            goto L_0x0085
        L_0x0084:
            r4 = 1
        L_0x0085:
            if (r4 != 0) goto L_0x022e
        L_0x0087:
            if (r3 != r14) goto L_0x008b
            goto L_0x022e
        L_0x008b:
            if (r3 >= r15) goto L_0x008f
            r3 = 1
            goto L_0x0090
        L_0x008f:
            r3 = 2
        L_0x0090:
            int r5 = r5 + r3
            r3 = r7
            goto L_0x0059
        L_0x0093:
            int r14 = r7 >> 5
            r15 = -2
            r13 = 128(0x80, float:1.794E-43)
            if (r14 != r15) goto L_0x00f8
            int r7 = r3 + 1
            if (r2 > r7) goto L_0x00a2
            if (r4 != r6) goto L_0x022e
            goto L_0x022f
        L_0x00a2:
            byte r14 = r1[r3]
            byte r7 = r1[r7]
            r15 = r7 & 192(0xc0, float:2.69E-43)
            if (r15 != r13) goto L_0x00ac
            r15 = 1
            goto L_0x00ad
        L_0x00ac:
            r15 = 0
        L_0x00ad:
            if (r15 != 0) goto L_0x00b3
            if (r4 != r6) goto L_0x022e
            goto L_0x022f
        L_0x00b3:
            r7 = r7 ^ 3968(0xf80, float:5.56E-42)
            int r14 = r14 << 6
            r7 = r7 ^ r14
            if (r7 >= r13) goto L_0x00be
            if (r4 != r6) goto L_0x022e
            goto L_0x022f
        L_0x00be:
            int r13 = r4 + 1
            if (r4 != r6) goto L_0x00c4
            goto L_0x022f
        L_0x00c4:
            if (r7 == r8) goto L_0x00e0
            if (r7 == r9) goto L_0x00e0
            if (r7 < 0) goto L_0x00ce
            if (r7 >= r12) goto L_0x00ce
            r4 = 1
            goto L_0x00cf
        L_0x00ce:
            r4 = 0
        L_0x00cf:
            if (r4 != 0) goto L_0x00dd
            if (r10 > r7) goto L_0x00d7
            if (r7 >= r11) goto L_0x00d7
            r4 = 1
            goto L_0x00d8
        L_0x00d7:
            r4 = 0
        L_0x00d8:
            if (r4 == 0) goto L_0x00db
            goto L_0x00dd
        L_0x00db:
            r4 = 0
            goto L_0x00de
        L_0x00dd:
            r4 = 1
        L_0x00de:
            if (r4 != 0) goto L_0x022e
        L_0x00e0:
            r4 = 65533(0xfffd, float:9.1831E-41)
            if (r7 != r4) goto L_0x00e7
            goto L_0x022e
        L_0x00e7:
            r4 = 65536(0x10000, float:9.18355E-41)
            if (r7 >= r4) goto L_0x00ef
            r4 = 1
            r16 = r4
            goto L_0x00f1
        L_0x00ef:
            r16 = 2
        L_0x00f1:
            int r5 = r5 + r16
            int r3 = r3 + 2
            r4 = r13
            goto L_0x0014
        L_0x00f8:
            int r10 = r7 >> 4
            r11 = 57344(0xe000, float:8.0356E-41)
            r12 = 55296(0xd800, float:7.7486E-41)
            if (r10 != r15) goto L_0x0187
            int r7 = r3 + 2
            if (r2 > r7) goto L_0x010a
            if (r4 != r6) goto L_0x022e
            goto L_0x022f
        L_0x010a:
            byte r10 = r1[r3]
            int r14 = r3 + 1
            byte r14 = r1[r14]
            r15 = r14 & 192(0xc0, float:2.69E-43)
            if (r15 != r13) goto L_0x0116
            r15 = 1
            goto L_0x0117
        L_0x0116:
            r15 = 0
        L_0x0117:
            if (r15 != 0) goto L_0x011d
            if (r4 != r6) goto L_0x022e
            goto L_0x022f
        L_0x011d:
            byte r7 = r1[r7]
            r15 = r7 & 192(0xc0, float:2.69E-43)
            if (r15 != r13) goto L_0x0125
            r13 = 1
            goto L_0x0126
        L_0x0125:
            r13 = 0
        L_0x0126:
            if (r13 != 0) goto L_0x012c
            if (r4 != r6) goto L_0x022e
            goto L_0x022f
        L_0x012c:
            r13 = -123008(0xfffffffffffe1f80, float:NaN)
            r7 = r7 ^ r13
            int r13 = r14 << 6
            r7 = r7 ^ r13
            int r10 = r10 << 12
            r7 = r7 ^ r10
            r10 = 2048(0x800, float:2.87E-42)
            if (r7 >= r10) goto L_0x013e
            if (r4 != r6) goto L_0x022e
            goto L_0x022f
        L_0x013e:
            if (r12 > r7) goto L_0x0144
            if (r7 >= r11) goto L_0x0144
            r10 = 1
            goto L_0x0145
        L_0x0144:
            r10 = 0
        L_0x0145:
            if (r10 == 0) goto L_0x014b
            if (r4 != r6) goto L_0x022e
            goto L_0x022f
        L_0x014b:
            int r10 = r4 + 1
            if (r4 != r6) goto L_0x0151
            goto L_0x022f
        L_0x0151:
            if (r7 == r8) goto L_0x0173
            if (r7 == r9) goto L_0x0173
            if (r7 < 0) goto L_0x015d
            r4 = 32
            if (r7 >= r4) goto L_0x015d
            r4 = 1
            goto L_0x015e
        L_0x015d:
            r4 = 0
        L_0x015e:
            if (r4 != 0) goto L_0x0170
            r4 = 127(0x7f, float:1.78E-43)
            if (r4 > r7) goto L_0x016a
            r4 = 160(0xa0, float:2.24E-43)
            if (r7 >= r4) goto L_0x016a
            r4 = 1
            goto L_0x016b
        L_0x016a:
            r4 = 0
        L_0x016b:
            if (r4 == 0) goto L_0x016e
            goto L_0x0170
        L_0x016e:
            r4 = 0
            goto L_0x0171
        L_0x0170:
            r4 = 1
        L_0x0171:
            if (r4 != 0) goto L_0x022e
        L_0x0173:
            r4 = 65533(0xfffd, float:9.1831E-41)
            if (r7 != r4) goto L_0x017a
            goto L_0x022e
        L_0x017a:
            r4 = 65536(0x10000, float:9.18355E-41)
            if (r7 >= r4) goto L_0x0180
            r13 = 1
            goto L_0x0181
        L_0x0180:
            r13 = 2
        L_0x0181:
            int r5 = r5 + r13
            int r3 = r3 + 3
            r4 = r10
            goto L_0x0014
        L_0x0187:
            int r7 = r7 >> 3
            if (r7 != r15) goto L_0x022b
            int r7 = r3 + 3
            if (r2 > r7) goto L_0x0193
            if (r4 != r6) goto L_0x022e
            goto L_0x022f
        L_0x0193:
            byte r9 = r1[r3]
            int r10 = r3 + 1
            byte r10 = r1[r10]
            r14 = r10 & 192(0xc0, float:2.69E-43)
            if (r14 != r13) goto L_0x019f
            r14 = 1
            goto L_0x01a0
        L_0x019f:
            r14 = 0
        L_0x01a0:
            if (r14 != 0) goto L_0x01a6
            if (r4 != r6) goto L_0x022e
            goto L_0x022f
        L_0x01a6:
            int r14 = r3 + 2
            byte r14 = r1[r14]
            r15 = r14 & 192(0xc0, float:2.69E-43)
            if (r15 != r13) goto L_0x01b0
            r15 = 1
            goto L_0x01b1
        L_0x01b0:
            r15 = 0
        L_0x01b1:
            if (r15 != 0) goto L_0x01b7
            if (r4 != r6) goto L_0x022e
            goto L_0x022f
        L_0x01b7:
            byte r7 = r1[r7]
            r15 = r7 & 192(0xc0, float:2.69E-43)
            if (r15 != r13) goto L_0x01bf
            r13 = 1
            goto L_0x01c0
        L_0x01bf:
            r13 = 0
        L_0x01c0:
            if (r13 != 0) goto L_0x01c6
            if (r4 != r6) goto L_0x022e
            goto L_0x022f
        L_0x01c6:
            r13 = 3678080(0x381f80, float:5.154088E-39)
            r7 = r7 ^ r13
            int r13 = r14 << 6
            r7 = r7 ^ r13
            int r10 = r10 << 12
            r7 = r7 ^ r10
            int r9 = r9 << 18
            r7 = r7 ^ r9
            r9 = 1114111(0x10ffff, float:1.561202E-39)
            if (r7 <= r9) goto L_0x01dc
            if (r4 != r6) goto L_0x022e
            goto L_0x022f
        L_0x01dc:
            if (r12 > r7) goto L_0x01e2
            if (r7 >= r11) goto L_0x01e2
            r9 = 1
            goto L_0x01e3
        L_0x01e2:
            r9 = 0
        L_0x01e3:
            if (r9 == 0) goto L_0x01e8
            if (r4 != r6) goto L_0x022e
            goto L_0x022f
        L_0x01e8:
            r9 = 65536(0x10000, float:9.18355E-41)
            if (r7 >= r9) goto L_0x01ef
            if (r4 != r6) goto L_0x022e
            goto L_0x022f
        L_0x01ef:
            int r9 = r4 + 1
            if (r4 != r6) goto L_0x01f4
            goto L_0x022f
        L_0x01f4:
            if (r7 == r8) goto L_0x0218
            r4 = 13
            if (r7 == r4) goto L_0x0218
            if (r7 < 0) goto L_0x0202
            r4 = 32
            if (r7 >= r4) goto L_0x0202
            r4 = 1
            goto L_0x0203
        L_0x0202:
            r4 = 0
        L_0x0203:
            if (r4 != 0) goto L_0x0215
            r4 = 127(0x7f, float:1.78E-43)
            if (r4 > r7) goto L_0x020f
            r4 = 160(0xa0, float:2.24E-43)
            if (r7 >= r4) goto L_0x020f
            r4 = 1
            goto L_0x0210
        L_0x020f:
            r4 = 0
        L_0x0210:
            if (r4 == 0) goto L_0x0213
            goto L_0x0215
        L_0x0213:
            r4 = 0
            goto L_0x0216
        L_0x0215:
            r4 = 1
        L_0x0216:
            if (r4 != 0) goto L_0x022e
        L_0x0218:
            r4 = 65533(0xfffd, float:9.1831E-41)
            if (r7 != r4) goto L_0x021e
            goto L_0x022e
        L_0x021e:
            r4 = 65536(0x10000, float:9.18355E-41)
            if (r7 >= r4) goto L_0x0224
            r13 = 1
            goto L_0x0225
        L_0x0224:
            r13 = 2
        L_0x0225:
            int r5 = r5 + r13
            int r3 = r3 + 4
            r4 = r9
            goto L_0x0014
        L_0x022b:
            if (r4 != r6) goto L_0x022e
            goto L_0x022f
        L_0x022e:
            r5 = -1
        L_0x022f:
            java.lang.String r1 = "…]"
            java.lang.String r2 = "[size="
            r3 = 93
            r4 = -1
            if (r5 != r4) goto L_0x02cc
            byte[] r4 = r0.data
            int r4 = r4.length
            if (r4 > r6) goto L_0x0254
            java.lang.String r1 = "[hex="
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            java.lang.String r0 = r17.hex()
            r1.append(r0)
            r1.append(r3)
            java.lang.String r0 = r1.toString()
            goto L_0x032f
        L_0x0254:
            java.lang.StringBuilder r2 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r2)
            byte[] r3 = r0.data
            int r3 = r3.length
            r2.append(r3)
            java.lang.String r3 = " hex="
            r2.append(r3)
            byte[] r3 = r0.data
            int r4 = r3.length
            if (r6 > r4) goto L_0x026a
            r4 = 1
            goto L_0x026b
        L_0x026a:
            r4 = 0
        L_0x026b:
            if (r4 == 0) goto L_0x02b3
            int r4 = r3.length
            if (r6 != r4) goto L_0x0271
            goto L_0x027e
        L_0x0271:
            okio.ByteString r0 = new okio.ByteString
            int r4 = r3.length
            if (r6 > r4) goto L_0x028e
            r4 = 0
            byte[] r3 = java.util.Arrays.copyOfRange(r3, r4, r6)
            r0.<init>(r3)
        L_0x027e:
            java.lang.String r0 = r0.hex()
            r2.append(r0)
            r2.append(r1)
            java.lang.String r0 = r2.toString()
            goto L_0x032f
        L_0x028e:
            java.lang.IndexOutOfBoundsException r0 = new java.lang.IndexOutOfBoundsException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "toIndex ("
            r1.append(r2)
            r1.append(r6)
            java.lang.String r2 = ") is greater than size ("
            r1.append(r2)
            r1.append(r4)
            java.lang.String r2 = ")."
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x02b3:
            java.lang.String r1 = "endIndex > length("
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            byte[] r0 = r0.data
            int r0 = r0.length
            r2 = 41
            java.lang.String r0 = androidx.core.graphics.Insets$$ExternalSyntheticOutline0.m11m(r1, r0, r2)
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.String r0 = r0.toString()
            r1.<init>(r0)
            throw r1
        L_0x02cc:
            java.lang.String r4 = r0.utf8
            if (r4 != 0) goto L_0x02de
            byte[] r4 = r17.internalArray$external__okio__android_common__okio_lib()
            java.nio.charset.Charset r6 = kotlin.text.Charsets.UTF_8
            java.lang.String r7 = new java.lang.String
            r7.<init>(r4, r6)
            r0.utf8 = r7
            r4 = r7
        L_0x02de:
            r6 = 0
            java.lang.String r6 = r4.substring(r6, r5)
            java.lang.String r7 = "\\"
            java.lang.String r8 = "\\\\"
            java.lang.String r6 = kotlin.text.StringsKt__StringsJVMKt.replace$default(r6, r7, r8)
            java.lang.String r7 = "\n"
            java.lang.String r8 = "\\n"
            java.lang.String r6 = kotlin.text.StringsKt__StringsJVMKt.replace$default(r6, r7, r8)
            java.lang.String r7 = "\r"
            java.lang.String r8 = "\\r"
            java.lang.String r6 = kotlin.text.StringsKt__StringsJVMKt.replace$default(r6, r7, r8)
            int r4 = r4.length()
            if (r5 >= r4) goto L_0x031b
            java.lang.StringBuilder r2 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r2)
            byte[] r0 = r0.data
            int r0 = r0.length
            r2.append(r0)
            java.lang.String r0 = " text="
            r2.append(r0)
            r2.append(r6)
            r2.append(r1)
            java.lang.String r0 = r2.toString()
            goto L_0x032f
        L_0x031b:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "[text="
            r0.append(r1)
            r0.append(r6)
            r0.append(r3)
            java.lang.String r0 = r0.toString()
        L_0x032f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.ByteString.toString():java.lang.String");
    }

    public ByteString(byte[] bArr) {
        this.data = bArr;
    }

    private final void readObject(ObjectInputStream objectInputStream) throws IOException {
        boolean z;
        int readInt = objectInputStream.readInt();
        int i = 0;
        if (readInt >= 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            byte[] bArr = new byte[readInt];
            while (i < readInt) {
                int read = objectInputStream.read(bArr, i, readInt - i);
                if (read != -1) {
                    i += read;
                } else {
                    throw new EOFException();
                }
            }
            ByteString byteString = new ByteString(bArr);
            Field declaredField = ByteString.class.getDeclaredField("data");
            declaredField.setAccessible(true);
            declaredField.set(this, byteString.data);
            return;
        }
        throw new IllegalArgumentException(Intrinsics.stringPlus("byteCount < 0: ", Integer.valueOf(readInt)).toString());
    }

    public boolean rangeEquals(ByteString byteString, int i) {
        return byteString.rangeEquals(0, this.data, 0, i);
    }

    public final byte[] getData$external__okio__android_common__okio_lib() {
        return this.data;
    }

    public byte[] internalArray$external__okio__android_common__okio_lib() {
        return this.data;
    }
}
