package com.google.android.systemui.elmyra.proto.nano;

import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class ElmyraFilters$Filter extends MessageNano {
    public static volatile ElmyraFilters$Filter[] _emptyArray;
    public int parametersCase_ = 0;
    public MessageNano parameters_ = null;

    public final int computeSerializedSize() {
        int i = 0;
        if (this.parametersCase_ == 1) {
            i = 0 + CodedOutputByteBufferNano.computeMessageSize(1, this.parameters_);
        }
        if (this.parametersCase_ == 2) {
            i += CodedOutputByteBufferNano.computeMessageSize(2, this.parameters_);
        }
        if (this.parametersCase_ == 3) {
            i += CodedOutputByteBufferNano.computeMessageSize(3, this.parameters_);
        }
        if (this.parametersCase_ == 4) {
            return i + CodedOutputByteBufferNano.computeMessageSize(4, this.parameters_);
        }
        return i;
    }

    public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        if (this.parametersCase_ == 1) {
            codedOutputByteBufferNano.writeMessage(1, this.parameters_);
        }
        if (this.parametersCase_ == 2) {
            codedOutputByteBufferNano.writeMessage(2, this.parameters_);
        }
        if (this.parametersCase_ == 3) {
            codedOutputByteBufferNano.writeMessage(3, this.parameters_);
        }
        if (this.parametersCase_ == 4) {
            codedOutputByteBufferNano.writeMessage(4, this.parameters_);
        }
    }

    public ElmyraFilters$Filter() {
        this.cachedSize = -1;
    }

    public final MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                break;
            } else if (readTag == 10) {
                if (this.parametersCase_ != 1) {
                    this.parameters_ = new ElmyraFilters$FIRFilter();
                }
                codedInputByteBufferNano.readMessage(this.parameters_);
                this.parametersCase_ = 1;
            } else if (readTag == 18) {
                if (this.parametersCase_ != 2) {
                    this.parameters_ = new ElmyraFilters$HighpassFilter();
                }
                codedInputByteBufferNano.readMessage(this.parameters_);
                this.parametersCase_ = 2;
            } else if (readTag == 26) {
                if (this.parametersCase_ != 3) {
                    this.parameters_ = new ElmyraFilters$LowpassFilter();
                }
                codedInputByteBufferNano.readMessage(this.parameters_);
                this.parametersCase_ = 3;
            } else if (readTag == 34) {
                if (this.parametersCase_ != 4) {
                    this.parameters_ = new ElmyraFilters$MedianFilter();
                }
                codedInputByteBufferNano.readMessage(this.parameters_);
                this.parametersCase_ = 4;
            } else if (!codedInputByteBufferNano.skipField(readTag)) {
                break;
            }
        }
        return this;
    }
}
