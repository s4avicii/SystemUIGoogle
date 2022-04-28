package com.google.android.systemui.elmyra.proto.nano;

import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class ElmyraFilters$LowpassFilter extends MessageNano {
    public float cutoff = 0.0f;
    public float rate = 0.0f;

    public final int computeSerializedSize() {
        int i = 0;
        if (Float.floatToIntBits(this.cutoff) != Float.floatToIntBits(0.0f)) {
            i = 0 + CodedOutputByteBufferNano.computeFloatSize(1);
        }
        if (Float.floatToIntBits(this.rate) != Float.floatToIntBits(0.0f)) {
            return i + CodedOutputByteBufferNano.computeFloatSize(2);
        }
        return i;
    }

    public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        if (Float.floatToIntBits(this.cutoff) != Float.floatToIntBits(0.0f)) {
            codedOutputByteBufferNano.writeFloat(1, this.cutoff);
        }
        if (Float.floatToIntBits(this.rate) != Float.floatToIntBits(0.0f)) {
            codedOutputByteBufferNano.writeFloat(2, this.rate);
        }
    }

    public ElmyraFilters$LowpassFilter() {
        this.cachedSize = -1;
    }

    public final MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                break;
            } else if (readTag == 13) {
                this.cutoff = codedInputByteBufferNano.readFloat();
            } else if (readTag == 21) {
                this.rate = codedInputByteBufferNano.readFloat();
            } else if (!codedInputByteBufferNano.skipField(readTag)) {
                break;
            }
        }
        return this;
    }
}
