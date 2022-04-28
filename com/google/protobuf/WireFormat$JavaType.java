package com.google.protobuf;

import java.io.Serializable;

public enum WireFormat$JavaType {
    INT(0),
    LONG(0L),
    FLOAT(Float.valueOf(0.0f)),
    DOUBLE(Double.valueOf(0.0d)),
    BOOLEAN(Boolean.FALSE),
    STRING(""),
    BYTE_STRING(ByteString.EMPTY),
    ENUM((String) null),
    MESSAGE((String) null);
    
    private final Object defaultDefault;

    /* access modifiers changed from: public */
    WireFormat$JavaType(Serializable serializable) {
        this.defaultDefault = serializable;
    }
}
