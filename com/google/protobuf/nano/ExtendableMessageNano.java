package com.google.protobuf.nano;

import com.google.protobuf.nano.ExtendableMessageNano;
import java.io.IOException;
import java.nio.charset.Charset;

public abstract class ExtendableMessageNano<M extends ExtendableMessageNano<M>> extends MessageNano {
    public final MessageNano clone() throws CloneNotSupportedException {
        ExtendableMessageNano extendableMessageNano = (ExtendableMessageNano) super.clone();
        Charset charset = InternalNano.UTF_8;
        return extendableMessageNano;
    }

    public final int computeSerializedSize() {
        return 0;
    }

    public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
    }

    /* renamed from: clone  reason: collision with other method in class */
    public final Object m319clone() throws CloneNotSupportedException {
        ExtendableMessageNano extendableMessageNano = (ExtendableMessageNano) super.clone();
        Charset charset = InternalNano.UTF_8;
        return extendableMessageNano;
    }
}
