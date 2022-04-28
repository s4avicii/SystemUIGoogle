package com.airbnb.lottie.parser.moshi;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.C0961QS;
import java.io.EOFException;
import java.io.IOException;
import java.util.Objects;
import kotlin.text.Charsets;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.RealBufferedSource;

public final class JsonUtf8Reader extends JsonReader {
    public static final ByteString DOUBLE_QUOTE_OR_SLASH = ByteString.encodeUtf8("\"\\");
    public static final ByteString LINEFEED_OR_CARRIAGE_RETURN = ByteString.encodeUtf8("\n\r");
    public static final ByteString SINGLE_QUOTE_OR_SLASH = ByteString.encodeUtf8("'\\");
    public static final ByteString UNQUOTED_STRING_TERMINALS = ByteString.encodeUtf8("{}[]:, \n\t\r\f/\\;#=");
    public final Buffer buffer;
    public int peeked = 0;
    public long peekedLong;
    public int peekedNumberLength;
    public String peekedString;
    public final BufferedSource source;

    public final void close() throws IOException {
        this.peeked = 0;
        this.scopes[0] = 8;
        this.stackSize = 1;
        Buffer buffer2 = this.buffer;
        Objects.requireNonNull(buffer2);
        buffer2.skip(buffer2.size);
        this.source.close();
    }

    public final int nextNonWhitespace(boolean z) throws IOException {
        int i = 0;
        while (true) {
            int i2 = i + 1;
            if (this.source.request((long) i2)) {
                byte b = this.buffer.getByte((long) i);
                if (b == 10 || b == 32 || b == 13 || b == 9) {
                    i = i2;
                } else {
                    this.buffer.skip((long) (i2 - 1));
                    if (b == 47) {
                        if (!this.source.request(2)) {
                            return b;
                        }
                        checkLenient();
                        throw null;
                    } else if (b != 35) {
                        return b;
                    } else {
                        checkLenient();
                        throw null;
                    }
                }
            } else if (!z) {
                return -1;
            } else {
                throw new EOFException("End of input");
            }
        }
    }

    public final String nextQuotedValue(ByteString byteString) throws IOException {
        StringBuilder sb = null;
        while (true) {
            long indexOfElement = this.source.indexOfElement(byteString);
            if (indexOfElement == -1) {
                syntaxError("Unterminated string");
                throw null;
            } else if (this.buffer.getByte(indexOfElement) == 92) {
                if (sb == null) {
                    sb = new StringBuilder();
                }
                sb.append(this.buffer.readUtf8(indexOfElement));
                this.buffer.readByte();
                sb.append(readEscapeCharacter());
            } else if (sb == null) {
                String readUtf8 = this.buffer.readUtf8(indexOfElement);
                this.buffer.readByte();
                return readUtf8;
            } else {
                sb.append(this.buffer.readUtf8(indexOfElement));
                this.buffer.readByte();
                return sb.toString();
            }
        }
    }

    public final void skipValue() throws IOException {
        int i = 0;
        do {
            int i2 = this.peeked;
            if (i2 == 0) {
                i2 = doPeek();
            }
            if (i2 == 3) {
                pushScope(1);
            } else if (i2 == 1) {
                pushScope(3);
            } else {
                if (i2 == 4) {
                    i--;
                    if (i >= 0) {
                        this.stackSize--;
                    } else {
                        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Expected a value but was ");
                        m.append(peek());
                        m.append(" at path ");
                        m.append(getPath());
                        throw new JsonDataException(m.toString());
                    }
                } else if (i2 == 2) {
                    i--;
                    if (i >= 0) {
                        this.stackSize--;
                    } else {
                        StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Expected a value but was ");
                        m2.append(peek());
                        m2.append(" at path ");
                        m2.append(getPath());
                        throw new JsonDataException(m2.toString());
                    }
                } else if (i2 == 14 || i2 == 10) {
                    long indexOfElement = this.source.indexOfElement(UNQUOTED_STRING_TERMINALS);
                    Buffer buffer2 = this.buffer;
                    if (indexOfElement == -1) {
                        Objects.requireNonNull(buffer2);
                        indexOfElement = buffer2.size;
                    }
                    buffer2.skip(indexOfElement);
                } else if (i2 == 9 || i2 == 13) {
                    skipQuotedValue(DOUBLE_QUOTE_OR_SLASH);
                } else if (i2 == 8 || i2 == 12) {
                    skipQuotedValue(SINGLE_QUOTE_OR_SLASH);
                } else if (i2 == 17) {
                    this.buffer.skip((long) this.peekedNumberLength);
                } else if (i2 == 18) {
                    StringBuilder m3 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Expected a value but was ");
                    m3.append(peek());
                    m3.append(" at path ");
                    m3.append(getPath());
                    throw new JsonDataException(m3.toString());
                }
                this.peeked = 0;
            }
            i++;
            this.peeked = 0;
        } while (i != 0);
        int[] iArr = this.pathIndices;
        int i3 = this.stackSize;
        int i4 = i3 - 1;
        iArr[i4] = iArr[i4] + 1;
        this.pathNames[i3 - 1] = "null";
    }

    static {
        ByteString.encodeUtf8("*/");
    }

    public final void beginArray() throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 3) {
            pushScope(1);
            this.pathIndices[this.stackSize - 1] = 0;
            this.peeked = 0;
            return;
        }
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Expected BEGIN_ARRAY but was ");
        m.append(peek());
        m.append(" at path ");
        m.append(getPath());
        throw new JsonDataException(m.toString());
    }

    public final void beginObject() throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 1) {
            pushScope(3);
            this.peeked = 0;
            return;
        }
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Expected BEGIN_OBJECT but was ");
        m.append(peek());
        m.append(" at path ");
        m.append(getPath());
        throw new JsonDataException(m.toString());
    }

    public final void checkLenient() throws IOException {
        syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
        throw null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:122:0x01ac, code lost:
        if (isLiteral(r2) != false) goto L_0x020d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x01ae, code lost:
        r2 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x01af, code lost:
        if (r5 != r2) goto L_0x01d5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x01b1, code lost:
        if (r6 == false) goto L_0x01d4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x01b7, code lost:
        if (r7 != Long.MIN_VALUE) goto L_0x01bb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x01b9, code lost:
        if (r9 == false) goto L_0x01d4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:130:0x01bf, code lost:
        if (r7 != 0) goto L_0x01c3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:131:0x01c1, code lost:
        if (r9 != false) goto L_0x01d4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:0x01c3, code lost:
        if (r9 == false) goto L_0x01c6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:0x01c6, code lost:
        r7 = -r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:134:0x01c7, code lost:
        r0.peekedLong = r7;
        r0.buffer.skip((long) r1);
        r14 = 16;
        r0.peeked = 16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:135:0x01d4, code lost:
        r2 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:0x01d5, code lost:
        if (r5 == r2) goto L_0x01dc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x01d7, code lost:
        if (r5 == 4) goto L_0x01dc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:0x01da, code lost:
        if (r5 != 7) goto L_0x020d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x01dc, code lost:
        r0.peekedNumberLength = r1;
        r14 = 17;
        r0.peeked = 17;
     */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x0125 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0126  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int doPeek() throws java.io.IOException {
        /*
            r17 = this;
            r0 = r17
            int[] r1 = r0.scopes
            int r2 = r0.stackSize
            int r3 = r2 + -1
            r3 = r1[r3]
            r5 = 34
            r6 = 93
            r7 = 59
            r8 = 44
            r9 = 3
            r10 = 7
            r11 = 6
            r12 = 5
            r13 = 2
            r14 = 0
            r15 = 4
            r16 = 0
            r4 = 1
            if (r3 != r4) goto L_0x0022
            int r2 = r2 - r4
            r1[r2] = r13
            goto L_0x0082
        L_0x0022:
            if (r3 != r13) goto L_0x0040
            int r1 = r0.nextNonWhitespace(r4)
            okio.Buffer r2 = r0.buffer
            r2.readByte()
            if (r1 == r8) goto L_0x0082
            if (r1 == r7) goto L_0x003c
            if (r1 != r6) goto L_0x0036
            r0.peeked = r15
            return r15
        L_0x0036:
            java.lang.String r1 = "Unterminated array"
            r0.syntaxError(r1)
            throw r16
        L_0x003c:
            r17.checkLenient()
            throw r16
        L_0x0040:
            if (r3 == r9) goto L_0x026a
            if (r3 != r12) goto L_0x0046
            goto L_0x026a
        L_0x0046:
            if (r3 != r15) goto L_0x0066
            int r2 = r2 - r4
            r1[r2] = r12
            int r1 = r0.nextNonWhitespace(r4)
            okio.Buffer r2 = r0.buffer
            r2.readByte()
            r2 = 58
            if (r1 == r2) goto L_0x0082
            r2 = 61
            if (r1 == r2) goto L_0x0062
            java.lang.String r1 = "Expected ':'"
            r0.syntaxError(r1)
            throw r16
        L_0x0062:
            r17.checkLenient()
            throw r16
        L_0x0066:
            if (r3 != r11) goto L_0x006c
            int r2 = r2 - r4
            r1[r2] = r10
            goto L_0x0082
        L_0x006c:
            if (r3 != r10) goto L_0x007e
            int r1 = r0.nextNonWhitespace(r14)
            r2 = -1
            if (r1 != r2) goto L_0x007a
            r1 = 18
            r0.peeked = r1
            return r1
        L_0x007a:
            r17.checkLenient()
            throw r16
        L_0x007e:
            r1 = 8
            if (r3 == r1) goto L_0x0262
        L_0x0082:
            int r1 = r0.nextNonWhitespace(r4)
            if (r1 == r5) goto L_0x0258
            r2 = 39
            if (r1 == r2) goto L_0x0254
            if (r1 == r8) goto L_0x0244
            if (r1 == r7) goto L_0x0244
            r2 = 91
            if (r1 == r2) goto L_0x023b
            if (r1 == r6) goto L_0x0231
            r2 = 123(0x7b, float:1.72E-43)
            if (r1 == r2) goto L_0x0229
            okio.Buffer r1 = r0.buffer
            r2 = 0
            byte r1 = r1.getByte(r2)
            r5 = 116(0x74, float:1.63E-43)
            if (r1 == r5) goto L_0x00cc
            r5 = 84
            if (r1 != r5) goto L_0x00ab
            goto L_0x00cc
        L_0x00ab:
            r5 = 102(0x66, float:1.43E-43)
            if (r1 == r5) goto L_0x00c6
            r5 = 70
            if (r1 != r5) goto L_0x00b4
            goto L_0x00c6
        L_0x00b4:
            r5 = 110(0x6e, float:1.54E-43)
            if (r1 == r5) goto L_0x00c0
            r5 = 78
            if (r1 != r5) goto L_0x00bd
            goto L_0x00c0
        L_0x00bd:
            r6 = r14
            goto L_0x0123
        L_0x00c0:
            java.lang.String r1 = "null"
            java.lang.String r5 = "NULL"
            r6 = r10
            goto L_0x00d2
        L_0x00c6:
            java.lang.String r1 = "false"
            java.lang.String r5 = "FALSE"
            r6 = r11
            goto L_0x00d2
        L_0x00cc:
            java.lang.String r1 = "true"
            java.lang.String r5 = "TRUE"
            r6 = r12
        L_0x00d2:
            int r7 = r1.length()
            r8 = r4
        L_0x00d7:
            if (r8 >= r7) goto L_0x0102
            okio.BufferedSource r14 = r0.source
            int r10 = r8 + 1
            long r11 = (long) r10
            boolean r11 = r14.request(r11)
            if (r11 != 0) goto L_0x00e6
        L_0x00e4:
            r6 = 0
            goto L_0x0123
        L_0x00e6:
            okio.Buffer r11 = r0.buffer
            r14 = r10
            long r9 = (long) r8
            byte r9 = r11.getByte(r9)
            char r10 = r1.charAt(r8)
            if (r9 == r10) goto L_0x00fb
            char r8 = r5.charAt(r8)
            if (r9 == r8) goto L_0x00fb
            goto L_0x00e4
        L_0x00fb:
            r8 = r14
            r9 = 3
            r10 = 7
            r11 = 6
            r12 = 5
            r14 = 0
            goto L_0x00d7
        L_0x0102:
            okio.BufferedSource r1 = r0.source
            int r5 = r7 + 1
            long r8 = (long) r5
            boolean r1 = r1.request(r8)
            if (r1 == 0) goto L_0x011b
            okio.Buffer r1 = r0.buffer
            long r8 = (long) r7
            byte r1 = r1.getByte(r8)
            boolean r1 = r0.isLiteral(r1)
            if (r1 == 0) goto L_0x011b
            goto L_0x00e4
        L_0x011b:
            okio.Buffer r1 = r0.buffer
            long r7 = (long) r7
            r1.skip(r7)
            r0.peeked = r6
        L_0x0123:
            if (r6 == 0) goto L_0x0126
            return r6
        L_0x0126:
            r7 = r2
            r6 = r4
            r1 = 0
            r5 = 0
            r9 = 0
        L_0x012b:
            okio.BufferedSource r10 = r0.source
            int r11 = r1 + 1
            long r2 = (long) r11
            boolean r2 = r10.request(r2)
            if (r2 != 0) goto L_0x0139
            r2 = r13
            goto L_0x01af
        L_0x0139:
            okio.Buffer r2 = r0.buffer
            long r12 = (long) r1
            byte r2 = r2.getByte(r12)
            r12 = 43
            if (r2 == r12) goto L_0x0200
            r12 = 69
            if (r2 == r12) goto L_0x01f6
            r12 = 101(0x65, float:1.42E-43)
            if (r2 == r12) goto L_0x01f6
            r12 = 45
            if (r2 == r12) goto L_0x01ea
            r12 = 46
            if (r2 == r12) goto L_0x01e3
            r12 = 48
            if (r2 < r12) goto L_0x01a8
            r12 = 57
            if (r2 <= r12) goto L_0x015d
            goto L_0x01a8
        L_0x015d:
            if (r5 == r4) goto L_0x019e
            if (r5 != 0) goto L_0x0162
            goto L_0x019e
        L_0x0162:
            r1 = 2
            if (r5 != r1) goto L_0x018c
            r12 = 0
            int r1 = (r7 > r12 ? 1 : (r7 == r12 ? 0 : -1))
            if (r1 != 0) goto L_0x016d
            goto L_0x020d
        L_0x016d:
            r12 = 10
            long r12 = r12 * r7
            int r2 = r2 + -48
            long r1 = (long) r2
            long r12 = r12 - r1
            r1 = -922337203685477580(0xf333333333333334, double:-8.390303882365713E246)
            int r1 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
            if (r1 > 0) goto L_0x0186
            if (r1 != 0) goto L_0x0184
            int r1 = (r12 > r7 ? 1 : (r12 == r7 ? 0 : -1))
            if (r1 >= 0) goto L_0x0184
            goto L_0x0186
        L_0x0184:
            r1 = 0
            goto L_0x0187
        L_0x0186:
            r1 = r4
        L_0x0187:
            r1 = r1 & r6
            r6 = r1
            r7 = r12
            r3 = 6
            goto L_0x01a5
        L_0x018c:
            r1 = 3
            if (r5 != r1) goto L_0x0194
            r5 = r15
            r2 = 7
            r3 = 6
            goto L_0x0207
        L_0x0194:
            r1 = 5
            r3 = 6
            if (r5 == r1) goto L_0x019a
            if (r5 != r3) goto L_0x01a5
        L_0x019a:
            r2 = 7
            r5 = 7
            goto L_0x0207
        L_0x019e:
            r3 = 6
            int r2 = r2 + -48
            int r1 = -r2
            long r1 = (long) r1
            r7 = r1
            r5 = 2
        L_0x01a5:
            r2 = 7
            goto L_0x0207
        L_0x01a8:
            boolean r2 = r0.isLiteral(r2)
            if (r2 != 0) goto L_0x020d
            r2 = 2
        L_0x01af:
            if (r5 != r2) goto L_0x01d5
            if (r6 == 0) goto L_0x01d4
            r2 = -9223372036854775808
            int r2 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r2 != 0) goto L_0x01bb
            if (r9 == 0) goto L_0x01d4
        L_0x01bb:
            r2 = 0
            int r4 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r4 != 0) goto L_0x01c3
            if (r9 != 0) goto L_0x01d4
        L_0x01c3:
            if (r9 == 0) goto L_0x01c6
            goto L_0x01c7
        L_0x01c6:
            long r7 = -r7
        L_0x01c7:
            r0.peekedLong = r7
            okio.Buffer r2 = r0.buffer
            long r3 = (long) r1
            r2.skip(r3)
            r14 = 16
            r0.peeked = r14
            goto L_0x020e
        L_0x01d4:
            r2 = 2
        L_0x01d5:
            if (r5 == r2) goto L_0x01dc
            if (r5 == r15) goto L_0x01dc
            r2 = 7
            if (r5 != r2) goto L_0x020d
        L_0x01dc:
            r0.peekedNumberLength = r1
            r14 = 17
            r0.peeked = r14
            goto L_0x020e
        L_0x01e3:
            r1 = 2
            r2 = 7
            r3 = 6
            if (r5 != r1) goto L_0x020d
            r1 = 3
            goto L_0x0206
        L_0x01ea:
            r1 = 2
            r2 = 7
            r3 = 6
            if (r5 != 0) goto L_0x01f2
            r5 = r4
            r9 = r5
            goto L_0x0207
        L_0x01f2:
            r13 = 5
            if (r5 != r13) goto L_0x020d
            goto L_0x0205
        L_0x01f6:
            r1 = 2
            r2 = 7
            r3 = 6
            r13 = 5
            if (r5 == r1) goto L_0x01fe
            if (r5 != r15) goto L_0x020d
        L_0x01fe:
            r5 = r13
            goto L_0x0207
        L_0x0200:
            r2 = 7
            r3 = 6
            r13 = 5
            if (r5 != r13) goto L_0x020d
        L_0x0205:
            r1 = r3
        L_0x0206:
            r5 = r1
        L_0x0207:
            r1 = r11
            r2 = 0
            r13 = 2
            goto L_0x012b
        L_0x020d:
            r14 = 0
        L_0x020e:
            if (r14 == 0) goto L_0x0211
            return r14
        L_0x0211:
            okio.Buffer r1 = r0.buffer
            r2 = 0
            byte r1 = r1.getByte(r2)
            boolean r1 = r0.isLiteral(r1)
            if (r1 != 0) goto L_0x0225
            java.lang.String r1 = "Expected value"
            r0.syntaxError(r1)
            throw r16
        L_0x0225:
            r17.checkLenient()
            throw r16
        L_0x0229:
            okio.Buffer r1 = r0.buffer
            r1.readByte()
            r0.peeked = r4
            return r4
        L_0x0231:
            if (r3 != r4) goto L_0x0244
            okio.Buffer r1 = r0.buffer
            r1.readByte()
            r0.peeked = r15
            return r15
        L_0x023b:
            okio.Buffer r1 = r0.buffer
            r1.readByte()
            r1 = 3
            r0.peeked = r1
            return r1
        L_0x0244:
            if (r3 == r4) goto L_0x0250
            r1 = 2
            if (r3 != r1) goto L_0x024a
            goto L_0x0250
        L_0x024a:
            java.lang.String r1 = "Unexpected value"
            r0.syntaxError(r1)
            throw r16
        L_0x0250:
            r17.checkLenient()
            throw r16
        L_0x0254:
            r17.checkLenient()
            throw r16
        L_0x0258:
            okio.Buffer r1 = r0.buffer
            r1.readByte()
            r1 = 9
            r0.peeked = r1
            return r1
        L_0x0262:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "JsonReader is closed"
            r0.<init>(r1)
            throw r0
        L_0x026a:
            int r2 = r2 - r4
            r1[r2] = r15
            r1 = 125(0x7d, float:1.75E-43)
            r2 = 5
            if (r3 != r2) goto L_0x028f
            int r2 = r0.nextNonWhitespace(r4)
            okio.Buffer r6 = r0.buffer
            r6.readByte()
            if (r2 == r8) goto L_0x028f
            if (r2 == r7) goto L_0x028b
            if (r2 != r1) goto L_0x0285
            r1 = 2
            r0.peeked = r1
            return r1
        L_0x0285:
            java.lang.String r1 = "Unterminated object"
            r0.syntaxError(r1)
            throw r16
        L_0x028b:
            r17.checkLenient()
            throw r16
        L_0x028f:
            int r2 = r0.nextNonWhitespace(r4)
            if (r2 == r5) goto L_0x02ba
            r4 = 39
            if (r2 == r4) goto L_0x02b1
            if (r2 != r1) goto L_0x02ad
            r1 = 5
            if (r3 == r1) goto L_0x02a7
            okio.Buffer r1 = r0.buffer
            r1.readByte()
            r1 = 2
            r0.peeked = r1
            return r1
        L_0x02a7:
            java.lang.String r1 = "Expected name"
            r0.syntaxError(r1)
            throw r16
        L_0x02ad:
            r17.checkLenient()
            throw r16
        L_0x02b1:
            okio.Buffer r1 = r0.buffer
            r1.readByte()
            r17.checkLenient()
            throw r16
        L_0x02ba:
            okio.Buffer r1 = r0.buffer
            r1.readByte()
            r1 = 13
            r0.peeked = r1
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.parser.moshi.JsonUtf8Reader.doPeek():int");
    }

    public final void endArray() throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 4) {
            int i2 = this.stackSize - 1;
            this.stackSize = i2;
            int[] iArr = this.pathIndices;
            int i3 = i2 - 1;
            iArr[i3] = iArr[i3] + 1;
            this.peeked = 0;
            return;
        }
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Expected END_ARRAY but was ");
        m.append(peek());
        m.append(" at path ");
        m.append(getPath());
        throw new JsonDataException(m.toString());
    }

    public final void endObject() throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 2) {
            int i2 = this.stackSize - 1;
            this.stackSize = i2;
            this.pathNames[i2] = null;
            int[] iArr = this.pathIndices;
            int i3 = i2 - 1;
            iArr[i3] = iArr[i3] + 1;
            this.peeked = 0;
            return;
        }
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Expected END_OBJECT but was ");
        m.append(peek());
        m.append(" at path ");
        m.append(getPath());
        throw new JsonDataException(m.toString());
    }

    public final int findName(String str, JsonReader.Options options) {
        int length = options.strings.length;
        for (int i = 0; i < length; i++) {
            if (str.equals(options.strings[i])) {
                this.peeked = 0;
                this.pathNames[this.stackSize - 1] = str;
                return i;
            }
        }
        return -1;
    }

    public final boolean hasNext() throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 2 || i == 4 || i == 18) {
            return false;
        }
        return true;
    }

    public final boolean isLiteral(int i) throws IOException {
        if (i == 9 || i == 10 || i == 12 || i == 13 || i == 32) {
            return false;
        }
        if (i != 35) {
            if (i == 44) {
                return false;
            }
            if (!(i == 47 || i == 61)) {
                if (i == 123 || i == 125 || i == 58) {
                    return false;
                }
                if (i != 59) {
                    switch (i) {
                        case 91:
                        case 93:
                            return false;
                        case 92:
                            break;
                        default:
                            return true;
                    }
                }
            }
        }
        checkLenient();
        throw null;
    }

    public final boolean nextBoolean() throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 5) {
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i2 = this.stackSize - 1;
            iArr[i2] = iArr[i2] + 1;
            return true;
        } else if (i == 6) {
            this.peeked = 0;
            int[] iArr2 = this.pathIndices;
            int i3 = this.stackSize - 1;
            iArr2[i3] = iArr2[i3] + 1;
            return false;
        } else {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Expected a boolean but was ");
            m.append(peek());
            m.append(" at path ");
            m.append(getPath());
            throw new JsonDataException(m.toString());
        }
    }

    public final double nextDouble() throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 16) {
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i2 = this.stackSize - 1;
            iArr[i2] = iArr[i2] + 1;
            return (double) this.peekedLong;
        }
        if (i == 17) {
            this.peekedString = this.buffer.readUtf8((long) this.peekedNumberLength);
        } else if (i == 9) {
            this.peekedString = nextQuotedValue(DOUBLE_QUOTE_OR_SLASH);
        } else if (i == 8) {
            this.peekedString = nextQuotedValue(SINGLE_QUOTE_OR_SLASH);
        } else if (i == 10) {
            this.peekedString = nextUnquotedValue();
        } else if (i != 11) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Expected a double but was ");
            m.append(peek());
            m.append(" at path ");
            m.append(getPath());
            throw new JsonDataException(m.toString());
        }
        this.peeked = 11;
        try {
            double parseDouble = Double.parseDouble(this.peekedString);
            if (Double.isNaN(parseDouble) || Double.isInfinite(parseDouble)) {
                throw new JsonEncodingException("JSON forbids NaN and infinities: " + parseDouble + " at path " + getPath());
            }
            this.peekedString = null;
            this.peeked = 0;
            int[] iArr2 = this.pathIndices;
            int i3 = this.stackSize - 1;
            iArr2[i3] = iArr2[i3] + 1;
            return parseDouble;
        } catch (NumberFormatException unused) {
            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Expected a double but was ");
            m2.append(this.peekedString);
            m2.append(" at path ");
            m2.append(getPath());
            throw new JsonDataException(m2.toString());
        }
    }

    public final int nextInt() throws IOException {
        String str;
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 16) {
            long j = this.peekedLong;
            int i2 = (int) j;
            if (j == ((long) i2)) {
                this.peeked = 0;
                int[] iArr = this.pathIndices;
                int i3 = this.stackSize - 1;
                iArr[i3] = iArr[i3] + 1;
                return i2;
            }
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Expected an int but was ");
            m.append(this.peekedLong);
            m.append(" at path ");
            m.append(getPath());
            throw new JsonDataException(m.toString());
        }
        if (i == 17) {
            this.peekedString = this.buffer.readUtf8((long) this.peekedNumberLength);
        } else if (i == 9 || i == 8) {
            if (i == 9) {
                str = nextQuotedValue(DOUBLE_QUOTE_OR_SLASH);
            } else {
                str = nextQuotedValue(SINGLE_QUOTE_OR_SLASH);
            }
            this.peekedString = str;
            try {
                int parseInt = Integer.parseInt(str);
                this.peeked = 0;
                int[] iArr2 = this.pathIndices;
                int i4 = this.stackSize - 1;
                iArr2[i4] = iArr2[i4] + 1;
                return parseInt;
            } catch (NumberFormatException unused) {
            }
        } else if (i != 11) {
            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Expected an int but was ");
            m2.append(peek());
            m2.append(" at path ");
            m2.append(getPath());
            throw new JsonDataException(m2.toString());
        }
        this.peeked = 11;
        try {
            double parseDouble = Double.parseDouble(this.peekedString);
            int i5 = (int) parseDouble;
            if (((double) i5) == parseDouble) {
                this.peekedString = null;
                this.peeked = 0;
                int[] iArr3 = this.pathIndices;
                int i6 = this.stackSize - 1;
                iArr3[i6] = iArr3[i6] + 1;
                return i5;
            }
            StringBuilder m3 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Expected an int but was ");
            m3.append(this.peekedString);
            m3.append(" at path ");
            m3.append(getPath());
            throw new JsonDataException(m3.toString());
        } catch (NumberFormatException unused2) {
            StringBuilder m4 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Expected an int but was ");
            m4.append(this.peekedString);
            m4.append(" at path ");
            m4.append(getPath());
            throw new JsonDataException(m4.toString());
        }
    }

    public final String nextName() throws IOException {
        String str;
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 14) {
            str = nextUnquotedValue();
        } else if (i == 13) {
            str = nextQuotedValue(DOUBLE_QUOTE_OR_SLASH);
        } else if (i == 12) {
            str = nextQuotedValue(SINGLE_QUOTE_OR_SLASH);
        } else if (i == 15) {
            str = this.peekedString;
        } else {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Expected a name but was ");
            m.append(peek());
            m.append(" at path ");
            m.append(getPath());
            throw new JsonDataException(m.toString());
        }
        this.peeked = 0;
        this.pathNames[this.stackSize - 1] = str;
        return str;
    }

    public final String nextString() throws IOException {
        String str;
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 10) {
            str = nextUnquotedValue();
        } else if (i == 9) {
            str = nextQuotedValue(DOUBLE_QUOTE_OR_SLASH);
        } else if (i == 8) {
            str = nextQuotedValue(SINGLE_QUOTE_OR_SLASH);
        } else if (i == 11) {
            str = this.peekedString;
            this.peekedString = null;
        } else if (i == 16) {
            str = Long.toString(this.peekedLong);
        } else if (i == 17) {
            str = this.buffer.readUtf8((long) this.peekedNumberLength);
        } else {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Expected a string but was ");
            m.append(peek());
            m.append(" at path ");
            m.append(getPath());
            throw new JsonDataException(m.toString());
        }
        this.peeked = 0;
        int[] iArr = this.pathIndices;
        int i2 = this.stackSize - 1;
        iArr[i2] = iArr[i2] + 1;
        return str;
    }

    public final String nextUnquotedValue() throws IOException {
        long indexOfElement = this.source.indexOfElement(UNQUOTED_STRING_TERMINALS);
        int i = (indexOfElement > -1 ? 1 : (indexOfElement == -1 ? 0 : -1));
        Buffer buffer2 = this.buffer;
        if (i != 0) {
            return buffer2.readUtf8(indexOfElement);
        }
        Objects.requireNonNull(buffer2);
        return buffer2.readString(buffer2.size, Charsets.UTF_8);
    }

    public final JsonReader.Token peek() throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        switch (i) {
            case 1:
                return JsonReader.Token.BEGIN_OBJECT;
            case 2:
                return JsonReader.Token.END_OBJECT;
            case 3:
                return JsonReader.Token.BEGIN_ARRAY;
            case 4:
                return JsonReader.Token.END_ARRAY;
            case 5:
            case FalsingManager.VERSION /*6*/:
                return JsonReader.Token.BOOLEAN;
            case 7:
                return JsonReader.Token.NULL;
            case 8:
            case 9:
            case 10:
            case QSTileImpl.C1034H.STALE /*11*/:
                return JsonReader.Token.STRING;
            case KeyguardSliceProvider.ALARM_VISIBILITY_HOURS /*12*/:
            case C0961QS.VERSION /*13*/:
            case 14:
            case 15:
                return JsonReader.Token.NAME;
            case 16:
            case 17:
                return JsonReader.Token.NUMBER;
            case 18:
                return JsonReader.Token.END_DOCUMENT;
            default:
                throw new AssertionError();
        }
    }

    public final char readEscapeCharacter() throws IOException {
        int i;
        int i2;
        if (this.source.request(1)) {
            byte readByte = this.buffer.readByte();
            if (readByte == 10 || readByte == 34 || readByte == 39 || readByte == 47 || readByte == 92) {
                return (char) readByte;
            }
            if (readByte == 98) {
                return 8;
            }
            if (readByte == 102) {
                return 12;
            }
            if (readByte == 110) {
                return 10;
            }
            if (readByte == 114) {
                return 13;
            }
            if (readByte == 116) {
                return 9;
            }
            if (readByte != 117) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Invalid escape sequence: \\");
                m.append((char) readByte);
                syntaxError(m.toString());
                throw null;
            } else if (this.source.request(4)) {
                char c = 0;
                for (int i3 = 0; i3 < 4; i3++) {
                    byte b = this.buffer.getByte((long) i3);
                    char c2 = (char) (c << 4);
                    if (b < 48 || b > 57) {
                        if (b >= 97 && b <= 102) {
                            i2 = b - 97;
                        } else if (b < 65 || b > 70) {
                            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("\\u");
                            m2.append(this.buffer.readUtf8(4));
                            syntaxError(m2.toString());
                            throw null;
                        } else {
                            i2 = b - 65;
                        }
                        i = i2 + 10;
                    } else {
                        i = b - 48;
                    }
                    c = (char) (i + c2);
                }
                this.buffer.skip(4);
                return c;
            } else {
                StringBuilder m3 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Unterminated escape sequence at path ");
                m3.append(getPath());
                throw new EOFException(m3.toString());
            }
        } else {
            syntaxError("Unterminated escape sequence");
            throw null;
        }
    }

    public final int selectName(JsonReader.Options options) throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i < 12 || i > 15) {
            return -1;
        }
        if (i == 15) {
            return findName(this.peekedString, options);
        }
        int select = this.source.select(options.doubleQuoteSuffix);
        if (select != -1) {
            this.peeked = 0;
            this.pathNames[this.stackSize - 1] = options.strings[select];
            return select;
        }
        String str = this.pathNames[this.stackSize - 1];
        String nextName = nextName();
        int findName = findName(nextName, options);
        if (findName == -1) {
            this.peeked = 15;
            this.peekedString = nextName;
            this.pathNames[this.stackSize - 1] = str;
        }
        return findName;
    }

    public final void skipName() throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 14) {
            long indexOfElement = this.source.indexOfElement(UNQUOTED_STRING_TERMINALS);
            Buffer buffer2 = this.buffer;
            if (indexOfElement == -1) {
                Objects.requireNonNull(buffer2);
                indexOfElement = buffer2.size;
            }
            buffer2.skip(indexOfElement);
        } else if (i == 13) {
            skipQuotedValue(DOUBLE_QUOTE_OR_SLASH);
        } else if (i == 12) {
            skipQuotedValue(SINGLE_QUOTE_OR_SLASH);
        } else if (i != 15) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Expected a name but was ");
            m.append(peek());
            m.append(" at path ");
            m.append(getPath());
            throw new JsonDataException(m.toString());
        }
        this.peeked = 0;
        this.pathNames[this.stackSize - 1] = "null";
    }

    public final void skipQuotedValue(ByteString byteString) throws IOException {
        while (true) {
            long indexOfElement = this.source.indexOfElement(byteString);
            if (indexOfElement == -1) {
                syntaxError("Unterminated string");
                throw null;
            } else if (this.buffer.getByte(indexOfElement) == 92) {
                this.buffer.skip(indexOfElement + 1);
                readEscapeCharacter();
            } else {
                this.buffer.skip(indexOfElement + 1);
                return;
            }
        }
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("JsonReader(");
        m.append(this.source);
        m.append(")");
        return m.toString();
    }

    public JsonUtf8Reader(RealBufferedSource realBufferedSource) {
        this.source = realBufferedSource;
        this.buffer = realBufferedSource.bufferField;
        pushScope(6);
    }
}
