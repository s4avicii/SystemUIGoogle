package org.tensorflow.lite;

import java.util.HashMap;

public class InterpreterImpl implements AutoCloseable {
    public NativeInterpreterWrapper wrapper;

    public final void close() {
        NativeInterpreterWrapper nativeInterpreterWrapper = this.wrapper;
        if (nativeInterpreterWrapper != null) {
            nativeInterpreterWrapper.close();
            this.wrapper = null;
        }
    }

    public final void runForMultipleInputsOutputs(Object[] objArr, HashMap hashMap) {
        NativeInterpreterWrapper nativeInterpreterWrapper = this.wrapper;
        if (nativeInterpreterWrapper != null) {
            nativeInterpreterWrapper.run(objArr, hashMap);
            return;
        }
        throw new IllegalStateException("Internal error: The Interpreter has already been closed.");
    }

    public InterpreterImpl(NativeInterpreterWrapperExperimental nativeInterpreterWrapperExperimental) {
        this.wrapper = nativeInterpreterWrapperExperimental;
    }

    public final void finalize() throws Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }
}
