package org.tensorflow.lite;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

class NativeInterpreterWrapper implements AutoCloseable {
    public final ArrayList delegates = new ArrayList();
    public long errorHandle;
    public Tensor[] inputTensors;
    public long interpreterHandle;
    public HashMap memoryAllocated = new HashMap();
    public ByteBuffer modelByteBuffer;
    public long modelHandle;
    public Tensor[] outputTensors;
    public final ArrayList ownedDelegates = new ArrayList();

    private static native long allocateTensors(long j, long j2, int i);

    private static native void applyDelegate(long j, long j2, long j3);

    private static native long createErrorReporter(int i);

    private static native long createInterpreter(long j, long j2, int i);

    private static native long createModelWithBuffer(ByteBuffer byteBuffer, long j);

    private static native void delete(long j, long j2, long j3);

    private static native long deleteCancellationFlag(long j);

    private static native int getInputCount(long j);

    private static native int getInputTensorIndex(long j, int i);

    private static native int getOutputCount(long j);

    private static native int getOutputTensorIndex(long j, int i);

    private static native String[] getSignatureKeys(long j);

    private static native boolean hasUnresolvedFlexOp(long j);

    private static native boolean resizeInput(long j, long j2, int i, int[] iArr, boolean z, int i2);

    private static native void run(long j, long j2);

    public final void close() {
        int i = 0;
        int i2 = 0;
        while (true) {
            Tensor[] tensorArr = this.inputTensors;
            if (i2 >= tensorArr.length) {
                break;
            }
            if (tensorArr[i2] != null) {
                tensorArr[i2].close();
                this.inputTensors[i2] = null;
            }
            i2++;
        }
        while (true) {
            Tensor[] tensorArr2 = this.outputTensors;
            if (i >= tensorArr2.length) {
                break;
            }
            if (tensorArr2[i] != null) {
                tensorArr2[i].close();
                this.outputTensors[i] = null;
            }
            i++;
        }
        delete(this.errorHandle, this.modelHandle, this.interpreterHandle);
        deleteCancellationFlag(0);
        this.errorHandle = 0;
        this.modelHandle = 0;
        this.interpreterHandle = 0;
        this.modelByteBuffer = null;
        this.memoryAllocated = null;
        this.delegates.clear();
        Iterator it = this.ownedDelegates.iterator();
        while (it.hasNext()) {
            try {
                ((AutoCloseable) it.next()).close();
            } catch (Exception e) {
                System.err.println("Failed to close flex delegate: " + e);
            }
        }
        this.ownedDelegates.clear();
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x0050 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run(java.lang.Object[] r11, java.util.HashMap r12) {
        /*
            r10 = this;
            int r0 = r11.length
            if (r0 == 0) goto L_0x0125
            if (r12 == 0) goto L_0x011d
            r0 = 0
            r9 = r0
        L_0x0007:
            int r1 = r11.length
            if (r9 >= r1) goto L_0x0053
            org.tensorflow.lite.Tensor r1 = r10.getInputTensor(r9)
            r2 = r11[r9]
            r3 = 0
            if (r2 != 0) goto L_0x0014
            goto L_0x0028
        L_0x0014:
            boolean r4 = r2 instanceof java.nio.Buffer
            if (r4 == 0) goto L_0x0019
            goto L_0x0028
        L_0x0019:
            r1.throwIfTypeIsIncompatible(r2)
            int[] r2 = r1.computeShapeOf(r2)
            int[] r1 = r1.shapeCopy
            boolean r1 = java.util.Arrays.equals(r1, r2)
            if (r1 == 0) goto L_0x002a
        L_0x0028:
            r6 = r3
            goto L_0x002b
        L_0x002a:
            r6 = r2
        L_0x002b:
            if (r6 == 0) goto L_0x0050
            long r1 = r10.interpreterHandle
            long r3 = r10.errorHandle
            r8 = 0
            r7 = 0
            r5 = r9
            boolean r1 = resizeInput(r1, r3, r5, r6, r7, r8)
            if (r1 == 0) goto L_0x0050
            java.util.HashMap r1 = r10.memoryAllocated
            java.lang.Integer r2 = java.lang.Integer.valueOf(r0)
            java.lang.Boolean r3 = java.lang.Boolean.FALSE
            r1.put(r2, r3)
            org.tensorflow.lite.Tensor[] r1 = r10.inputTensors
            r2 = r1[r9]
            if (r2 == 0) goto L_0x0050
            r1 = r1[r9]
            r1.refreshShape()
        L_0x0050:
            int r9 = r9 + 1
            goto L_0x0007
        L_0x0053:
            java.util.HashMap r1 = r10.memoryAllocated
            java.lang.Integer r2 = java.lang.Integer.valueOf(r0)
            boolean r1 = r1.containsKey(r2)
            r2 = 1
            if (r1 == 0) goto L_0x0074
            java.util.HashMap r1 = r10.memoryAllocated
            java.lang.Integer r3 = java.lang.Integer.valueOf(r0)
            java.lang.Object r1 = r1.get(r3)
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            if (r1 == 0) goto L_0x0074
            r1 = r0
            goto L_0x0075
        L_0x0074:
            r1 = r2
        L_0x0075:
            if (r1 != 0) goto L_0x0079
            r2 = r0
            goto L_0x009d
        L_0x0079:
            java.util.HashMap r1 = r10.memoryAllocated
            java.lang.Integer r3 = java.lang.Integer.valueOf(r0)
            java.lang.Boolean r4 = java.lang.Boolean.TRUE
            r1.put(r3, r4)
            long r3 = r10.interpreterHandle
            long r5 = r10.errorHandle
            allocateTensors(r3, r5, r0)
            r1 = r0
        L_0x008c:
            org.tensorflow.lite.Tensor[] r3 = r10.outputTensors
            int r4 = r3.length
            if (r1 >= r4) goto L_0x009d
            r4 = r3[r1]
            if (r4 == 0) goto L_0x009a
            r3 = r3[r1]
            r3.refreshShape()
        L_0x009a:
            int r1 = r1 + 1
            goto L_0x008c
        L_0x009d:
            r1 = r0
        L_0x009e:
            int r3 = r11.length
            if (r1 >= r3) goto L_0x00ad
            org.tensorflow.lite.Tensor r3 = r10.getInputTensor(r1)
            r4 = r11[r1]
            r3.setTo(r4)
            int r1 = r1 + 1
            goto L_0x009e
        L_0x00ad:
            java.lang.System.nanoTime()
            long r3 = r10.interpreterHandle
            long r5 = r10.errorHandle
            run((long) r3, (long) r5)
            java.lang.System.nanoTime()
            if (r2 == 0) goto L_0x00cd
        L_0x00bc:
            org.tensorflow.lite.Tensor[] r11 = r10.outputTensors
            int r1 = r11.length
            if (r0 >= r1) goto L_0x00cd
            r1 = r11[r0]
            if (r1 == 0) goto L_0x00ca
            r11 = r11[r0]
            r11.refreshShape()
        L_0x00ca:
            int r0 = r0 + 1
            goto L_0x00bc
        L_0x00cd:
            java.util.Set r11 = r12.entrySet()
            java.util.Iterator r11 = r11.iterator()
        L_0x00d5:
            boolean r12 = r11.hasNext()
            if (r12 == 0) goto L_0x011c
            java.lang.Object r12 = r11.next()
            java.util.Map$Entry r12 = (java.util.Map.Entry) r12
            java.lang.Object r0 = r12.getValue()
            if (r0 == 0) goto L_0x00d5
            java.lang.Object r0 = r12.getKey()
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r0 = r0.intValue()
            if (r0 < 0) goto L_0x0110
            org.tensorflow.lite.Tensor[] r1 = r10.outputTensors
            int r2 = r1.length
            if (r0 >= r2) goto L_0x0110
            r2 = r1[r0]
            if (r2 != 0) goto L_0x0108
            long r2 = r10.interpreterHandle
            int r4 = getOutputTensorIndex(r2, r0)
            org.tensorflow.lite.Tensor r2 = org.tensorflow.lite.Tensor.fromIndex(r2, r4)
            r1[r0] = r2
        L_0x0108:
            java.lang.Object r12 = r12.getValue()
            r2.copyTo(r12)
            goto L_0x00d5
        L_0x0110:
            java.lang.IllegalArgumentException r10 = new java.lang.IllegalArgumentException
            java.lang.String r11 = "Invalid output Tensor index: "
            java.lang.String r11 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0.m0m(r11, r0)
            r10.<init>(r11)
            throw r10
        L_0x011c:
            return
        L_0x011d:
            java.lang.IllegalArgumentException r10 = new java.lang.IllegalArgumentException
            java.lang.String r11 = "Input error: Outputs should not be null."
            r10.<init>(r11)
            throw r10
        L_0x0125:
            java.lang.IllegalArgumentException r10 = new java.lang.IllegalArgumentException
            java.lang.String r11 = "Input error: Inputs should not be null or empty."
            r10.<init>(r11)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: org.tensorflow.lite.NativeInterpreterWrapper.run(java.lang.Object[], java.util.HashMap):void");
    }

    public final Tensor getInputTensor(int i) {
        if (i >= 0) {
            Tensor[] tensorArr = this.inputTensors;
            if (i < tensorArr.length) {
                Tensor tensor = tensorArr[i];
                if (tensor != null) {
                    return tensor;
                }
                long j = this.interpreterHandle;
                Tensor fromIndex = Tensor.fromIndex(j, getInputTensorIndex(j, i));
                tensorArr[i] = fromIndex;
                return fromIndex;
            }
        }
        throw new IllegalArgumentException(VendorAtomValue$$ExternalSyntheticOutline0.m0m("Invalid input Tensor index: ", i));
    }

    public final String[] getSignatureKeys() {
        return getSignatureKeys(this.interpreterHandle);
    }

    public NativeInterpreterWrapper(MappedByteBuffer mappedByteBuffer) {
        TensorFlowLite.init();
        if (mappedByteBuffer != null) {
            this.modelByteBuffer = mappedByteBuffer;
            long createErrorReporter = createErrorReporter(512);
            long createModelWithBuffer = createModelWithBuffer(this.modelByteBuffer, createErrorReporter);
            ArrayList arrayList = new ArrayList();
            this.errorHandle = createErrorReporter;
            this.modelHandle = createModelWithBuffer;
            long createInterpreter = createInterpreter(createModelWithBuffer, createErrorReporter, -1);
            this.interpreterHandle = createInterpreter;
            this.inputTensors = new Tensor[getInputCount(createInterpreter)];
            this.outputTensors = new Tensor[getOutputCount(this.interpreterHandle)];
            if (hasUnresolvedFlexOp(this.interpreterHandle)) {
                Delegate delegate = null;
                try {
                    Class<?> cls = Class.forName("org.tensorflow.lite.flex.FlexDelegate");
                    Iterator it = arrayList.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (cls.isInstance((Delegate) it.next())) {
                                break;
                            }
                        } else {
                            delegate = (Delegate) cls.getConstructor(new Class[0]).newInstance(new Object[0]);
                            break;
                        }
                    }
                } catch (Exception unused) {
                }
                if (delegate != null) {
                    this.ownedDelegates.add((AutoCloseable) delegate);
                    applyDelegate(this.interpreterHandle, this.errorHandle, delegate.getNativeHandle());
                }
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                Delegate delegate2 = (Delegate) it2.next();
                applyDelegate(this.interpreterHandle, this.errorHandle, delegate2.getNativeHandle());
                this.delegates.add(delegate2);
            }
            allocateTensors(this.interpreterHandle, createErrorReporter, 0);
            this.memoryAllocated.put(0, Boolean.TRUE);
            return;
        }
        throw new IllegalArgumentException("Model ByteBuffer should be either a MappedByteBuffer of the model file, or a direct ByteBuffer using ByteOrder.nativeOrder() which contains bytes of model content.");
    }
}
