package kotlin.text;

import java.nio.charset.Charset;

/* compiled from: Charsets.kt */
public final class Charsets {
    public static final Charset UTF_16 = Charset.forName("UTF-16");
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    static {
        Charset.forName("UTF-16BE");
        Charset.forName("UTF-16LE");
        Charset.forName("US-ASCII");
        Charset.forName("ISO-8859-1");
    }
}
