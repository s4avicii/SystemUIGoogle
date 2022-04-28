package com.android.p012wm.shell.nano;

import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

/* renamed from: com.android.wm.shell.nano.WmShellTraceProto */
public final class WmShellTraceProto extends MessageNano {
    public boolean testValue = false;

    public final int computeSerializedSize() {
        if (this.testValue) {
            return 0 + CodedOutputByteBufferNano.computeBoolSize(1);
        }
        return 0;
    }

    public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        boolean z = this.testValue;
        if (z) {
            codedOutputByteBufferNano.writeBool(1, z);
        }
    }

    public WmShellTraceProto() {
        this.cachedSize = -1;
    }

    public final MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                break;
            } else if (readTag == 8) {
                this.testValue = codedInputByteBufferNano.readBool();
            } else if (!codedInputByteBufferNano.skipField(readTag)) {
                break;
            }
        }
        return this;
    }
}
