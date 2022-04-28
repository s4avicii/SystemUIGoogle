package org.tensorflow.lite;

import java.nio.MappedByteBuffer;

final class NativeInterpreterWrapperExperimental extends NativeInterpreterWrapper {
    public NativeInterpreterWrapperExperimental(MappedByteBuffer mappedByteBuffer) {
        super(mappedByteBuffer);
    }
}
