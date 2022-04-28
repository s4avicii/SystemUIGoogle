package com.google.protobuf;

public enum WireFormat$FieldType {
    DOUBLE(WireFormat$JavaType.DOUBLE, 1),
    FLOAT(WireFormat$JavaType.FLOAT, 5),
    INT64(r5, 0),
    UINT64(r5, 0),
    INT32(r11, 0),
    FIXED64(r5, 1),
    FIXED32(r11, 5),
    BOOL(WireFormat$JavaType.BOOLEAN, 0),
    UINT32(r11, 0),
    ENUM(WireFormat$JavaType.ENUM, 0),
    SFIXED32(r11, 5),
    SFIXED64(r5, 1),
    SINT32(r11, 0),
    SINT64(r5, 0);
    
    private final WireFormat$JavaType javaType;
    private final int wireType;

    /* access modifiers changed from: public */
    WireFormat$FieldType(WireFormat$JavaType wireFormat$JavaType, int i) {
        this.javaType = wireFormat$JavaType;
        this.wireType = i;
    }

    public final WireFormat$JavaType getJavaType() {
        return this.javaType;
    }
}
