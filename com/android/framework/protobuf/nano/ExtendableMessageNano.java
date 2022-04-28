package com.android.framework.protobuf.nano;

import com.android.framework.protobuf.nano.ExtendableMessageNano;
import java.io.IOException;

public abstract class ExtendableMessageNano<M extends ExtendableMessageNano<M>> extends MessageNano {
    public final MessageNano clone() throws CloneNotSupportedException {
        ExtendableMessageNano extendableMessageNano = (ExtendableMessageNano) super.clone();
        int i = InternalNano.$r8$clinit;
        return extendableMessageNano;
    }

    public final void computeSerializedSize() {
    }

    public final void writeTo() throws IOException {
    }

    /* renamed from: clone  reason: collision with other method in class */
    public final Object m155clone() throws CloneNotSupportedException {
        ExtendableMessageNano extendableMessageNano = (ExtendableMessageNano) super.clone();
        int i = InternalNano.$r8$clinit;
        return extendableMessageNano;
    }
}
