package org.tensorflow.lite;

import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.FalsingManager;

public enum DataType {
    FLOAT32(1),
    INT32(2),
    UINT8(3),
    INT64(4),
    STRING(5),
    BOOL(6),
    INT16(7);
    
    public static final DataType[] values = null;
    private final int value;

    /* access modifiers changed from: public */
    static {
        values = values();
    }

    public static DataType fromC(int i) {
        for (DataType dataType : values) {
            if (dataType.value == i) {
                return dataType;
            }
        }
        StringBuilder m = ExifInterface$$ExternalSyntheticOutline0.m13m("DataType error: DataType ", i, " is not recognized in Java (version ");
        m.append(TensorFlowLite.runtimeVersion());
        m.append(")");
        throw new IllegalArgumentException(m.toString());
    }

    /* access modifiers changed from: public */
    DataType(int i) {
        this.value = i;
    }

    public final int byteSize() {
        switch (ordinal()) {
            case 0:
            case 1:
                return 4;
            case 2:
            case 7:
                return 1;
            case 3:
                return 8;
            case 4:
            case 5:
                return -1;
            case FalsingManager.VERSION:
                return 2;
            default:
                throw new IllegalArgumentException("DataType error: DataType " + this + " is not supported yet");
        }
    }

    public final String toStringName() {
        switch (ordinal()) {
            case 0:
                return "float";
            case 1:
                return "int";
            case 2:
            case 7:
                return "byte";
            case 3:
                return "long";
            case 4:
                return "string";
            case 5:
                return "bool";
            case FalsingManager.VERSION:
                return "short";
            default:
                throw new IllegalArgumentException("DataType error: DataType " + this + " is not supported yet");
        }
    }
}
