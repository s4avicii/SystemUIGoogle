package com.airbnb.lottie.parser.moshi;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.hidl.base.V1_0.DebugInfo$$ExternalSyntheticOutline0;
import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import okio.Buffer;
import okio.ByteString;
import okio.Segment;

public abstract class JsonReader implements Closeable {
    public static final String[] REPLACEMENT_CHARS = new String[128];
    public int[] pathIndices = new int[32];
    public String[] pathNames = new String[32];
    public int[] scopes = new int[32];
    public int stackSize;

    public enum Token {
        BEGIN_ARRAY,
        END_ARRAY,
        BEGIN_OBJECT,
        END_OBJECT,
        NAME,
        STRING,
        NUMBER,
        BOOLEAN,
        NULL,
        END_DOCUMENT
    }

    public abstract void beginArray() throws IOException;

    public abstract void beginObject() throws IOException;

    public abstract void endArray() throws IOException;

    public abstract void endObject() throws IOException;

    public abstract boolean hasNext() throws IOException;

    public abstract boolean nextBoolean() throws IOException;

    public abstract double nextDouble() throws IOException;

    public abstract int nextInt() throws IOException;

    public abstract String nextString() throws IOException;

    public abstract Token peek() throws IOException;

    public abstract int selectName(Options options) throws IOException;

    public abstract void skipName() throws IOException;

    public abstract void skipValue() throws IOException;

    public static final class Options {
        public final okio.Options doubleQuoteSuffix;
        public final String[] strings;

        /* renamed from: of */
        public static Options m22of(String... strArr) {
            String str;
            try {
                ByteString[] byteStringArr = new ByteString[strArr.length];
                Buffer buffer = new Buffer();
                for (int i = 0; i < strArr.length; i++) {
                    String str2 = strArr[i];
                    String[] strArr2 = JsonReader.REPLACEMENT_CHARS;
                    Segment writableSegment$external__okio__android_common__okio_lib = buffer.writableSegment$external__okio__android_common__okio_lib(1);
                    byte[] bArr = writableSegment$external__okio__android_common__okio_lib.data;
                    int i2 = writableSegment$external__okio__android_common__okio_lib.limit;
                    writableSegment$external__okio__android_common__okio_lib.limit = i2 + 1;
                    byte b = (byte) 34;
                    bArr[i2] = b;
                    buffer.size++;
                    int length = str2.length();
                    int i3 = 0;
                    for (int i4 = 0; i4 < length; i4++) {
                        char charAt = str2.charAt(i4);
                        if (charAt < 128) {
                            str = strArr2[charAt];
                            if (str == null) {
                            }
                        } else if (charAt == 8232) {
                            str = "\\u2028";
                        } else if (charAt == 8233) {
                            str = "\\u2029";
                        }
                        if (i3 < i4) {
                            buffer.writeUtf8(str2, i3, i4);
                        }
                        buffer.writeUtf8(str, 0, str.length());
                        i3 = i4 + 1;
                    }
                    if (i3 < length) {
                        buffer.writeUtf8(str2, i3, length);
                    }
                    Segment writableSegment$external__okio__android_common__okio_lib2 = buffer.writableSegment$external__okio__android_common__okio_lib(1);
                    byte[] bArr2 = writableSegment$external__okio__android_common__okio_lib2.data;
                    int i5 = writableSegment$external__okio__android_common__okio_lib2.limit;
                    writableSegment$external__okio__android_common__okio_lib2.limit = i5 + 1;
                    bArr2[i5] = b;
                    buffer.size++;
                    buffer.readByte();
                    byteStringArr[i] = buffer.readByteString();
                }
                return new Options((String[]) strArr.clone(), okio.Options.m138of(byteStringArr));
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        public Options(String[] strArr, okio.Options options) {
            this.strings = strArr;
            this.doubleQuoteSuffix = options;
        }
    }

    static {
        for (int i = 0; i <= 31; i++) {
            REPLACEMENT_CHARS[i] = String.format("\\u%04x", new Object[]{Integer.valueOf(i)});
        }
        String[] strArr = REPLACEMENT_CHARS;
        strArr[34] = "\\\"";
        strArr[92] = "\\\\";
        strArr[9] = "\\t";
        strArr[8] = "\\b";
        strArr[10] = "\\n";
        strArr[13] = "\\r";
        strArr[12] = "\\f";
    }

    public final String getPath() {
        int i = this.stackSize;
        int[] iArr = this.scopes;
        String[] strArr = this.pathNames;
        int[] iArr2 = this.pathIndices;
        StringBuilder m = JsonReader$$ExternalSyntheticOutline0.m23m('$');
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = iArr[i2];
            if (i3 == 1 || i3 == 2) {
                m.append('[');
                m.append(iArr2[i2]);
                m.append(']');
            } else if (i3 == 3 || i3 == 4 || i3 == 5) {
                m.append('.');
                if (strArr[i2] != null) {
                    m.append(strArr[i2]);
                }
            }
        }
        return m.toString();
    }

    public final void pushScope(int i) {
        int i2 = this.stackSize;
        int[] iArr = this.scopes;
        if (i2 == iArr.length) {
            if (i2 != 256) {
                this.scopes = Arrays.copyOf(iArr, iArr.length * 2);
                String[] strArr = this.pathNames;
                this.pathNames = (String[]) Arrays.copyOf(strArr, strArr.length * 2);
                int[] iArr2 = this.pathIndices;
                this.pathIndices = Arrays.copyOf(iArr2, iArr2.length * 2);
            } else {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Nesting too deep at ");
                m.append(getPath());
                throw new JsonDataException(m.toString());
            }
        }
        int[] iArr3 = this.scopes;
        int i3 = this.stackSize;
        this.stackSize = i3 + 1;
        iArr3[i3] = i;
    }

    public final JsonEncodingException syntaxError(String str) throws JsonEncodingException {
        StringBuilder m = DebugInfo$$ExternalSyntheticOutline0.m2m(str, " at path ");
        m.append(getPath());
        throw new JsonEncodingException(m.toString());
    }
}
