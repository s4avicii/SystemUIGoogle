package com.google.android.systemui.elmyra.proto.nano;

import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;
import java.util.Objects;

public final class ElmyraFilters$MedianFilter extends MessageNano {
    public int windowSize = 0;

    public final int computeSerializedSize() {
        int i = this.windowSize;
        if (i == 0) {
            return 0;
        }
        return 0 + CodedOutputByteBufferNano.computeRawVarint32Size(i) + CodedOutputByteBufferNano.computeTagSize(1);
    }

    public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        int i = this.windowSize;
        if (i != 0) {
            Objects.requireNonNull(codedOutputByteBufferNano);
            codedOutputByteBufferNano.writeTag(1, 0);
            codedOutputByteBufferNano.writeRawVarint32(i);
        }
    }

    public ElmyraFilters$MedianFilter() {
        this.cachedSize = -1;
    }

    public final MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                break;
            } else if (readTag == 8) {
                this.windowSize = codedInputByteBufferNano.readRawVarint32();
            } else if (!codedInputByteBufferNano.skipField(readTag)) {
                break;
            }
        }
        return this;
    }
}
