package com.google.protobuf.nano;

import java.nio.charset.Charset;

public final class InternalNano {
    public static final Object LAZY_INIT_LOCK = new Object();
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    static {
        Charset.forName("ISO-8859-1");
    }
}
