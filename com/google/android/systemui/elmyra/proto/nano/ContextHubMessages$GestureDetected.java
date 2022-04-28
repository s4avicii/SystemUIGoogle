package com.google.android.systemui.elmyra.proto.nano;

import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class ContextHubMessages$GestureDetected extends MessageNano {
    public boolean hapticConsumed = false;
    public boolean hostSuspended = false;

    public final int computeSerializedSize() {
        int i = 0;
        if (this.hostSuspended) {
            i = 0 + CodedOutputByteBufferNano.computeBoolSize(1);
        }
        if (this.hapticConsumed) {
            return i + CodedOutputByteBufferNano.computeBoolSize(2);
        }
        return i;
    }

    public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        boolean z = this.hostSuspended;
        if (z) {
            codedOutputByteBufferNano.writeBool(1, z);
        }
        boolean z2 = this.hapticConsumed;
        if (z2) {
            codedOutputByteBufferNano.writeBool(2, z2);
        }
    }

    public ContextHubMessages$GestureDetected() {
        this.cachedSize = -1;
    }

    public final MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                break;
            } else if (readTag == 8) {
                this.hostSuspended = codedInputByteBufferNano.readBool();
            } else if (readTag == 16) {
                this.hapticConsumed = codedInputByteBufferNano.readBool();
            } else if (!codedInputByteBufferNano.skipField(readTag)) {
                break;
            }
        }
        return this;
    }
}
