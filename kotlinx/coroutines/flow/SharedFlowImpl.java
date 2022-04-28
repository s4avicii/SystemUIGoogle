package kotlinx.coroutines.flow;

import androidx.preference.R$color;
import androidx.slice.SliceSpecs;
import java.util.Objects;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.DisposeOnCancel;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: SharedFlow.kt */
public final class SharedFlowImpl<T> extends AbstractSharedFlow<SharedFlowSlot> implements MutableSharedFlow<T>, Flow {
    public Object[] buffer;
    public final int bufferCapacity;
    public int bufferSize;
    public long minCollectorIndex;
    public final BufferOverflow onBufferOverflow;
    public int queueSize;
    public final int replay;
    public long replayIndex;

    /* compiled from: SharedFlow.kt */
    public static final class Emitter implements DisposableHandle {
        public final Continuation<Unit> cont;
        public final SharedFlowImpl<?> flow;
        public long index;
        public final Object value;

        public final void dispose() {
            SharedFlowImpl<?> sharedFlowImpl = this.flow;
            Objects.requireNonNull(sharedFlowImpl);
            synchronized (sharedFlowImpl) {
                if (this.index >= sharedFlowImpl.getHead()) {
                    Object[] objArr = sharedFlowImpl.buffer;
                    Intrinsics.checkNotNull(objArr);
                    int i = (int) this.index;
                    if (objArr[(objArr.length - 1) & i] == this) {
                        objArr[i & (objArr.length - 1)] = SharedFlowKt.NO_VALUE;
                        sharedFlowImpl.cleanupTailLocked();
                    }
                }
            }
        }

        public Emitter(SharedFlowImpl sharedFlowImpl, long j, Object obj, CancellableContinuationImpl cancellableContinuationImpl) {
            this.flow = sharedFlowImpl;
            this.index = j;
            this.value = obj;
            this.cont = cancellableContinuationImpl;
        }
    }

    public final AbstractSharedFlowSlot[] createSlotArray() {
        return new SharedFlowSlot[2];
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v5, resolved type: kotlin.coroutines.Continuation<kotlin.Unit>[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v7, resolved type: kotlin.coroutines.Continuation<kotlin.Unit>[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v8, resolved type: kotlin.coroutines.Continuation<kotlin.Unit>[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v9, resolved type: kotlin.coroutines.Continuation<kotlin.Unit>[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v10, resolved type: kotlin.coroutines.Continuation<kotlin.Unit>[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v11, resolved type: kotlin.coroutines.Continuation<kotlin.Unit>[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v12, resolved type: kotlin.coroutines.Continuation<kotlin.Unit>[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v13, resolved type: kotlin.coroutines.Continuation<kotlin.Unit>[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v14, resolved type: kotlin.coroutines.Continuation<kotlin.Unit>[]} */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x000d, code lost:
        r11 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x000d, code lost:
        r11 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0015, code lost:
        r4 = r4;
     */
    /* JADX WARNING: Incorrect type for immutable var: ssa=kotlin.coroutines.Continuation<kotlin.Unit>[], code=java.lang.Object[], for r11v0, types: [kotlin.coroutines.Continuation<kotlin.Unit>[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final kotlin.coroutines.Continuation<kotlin.Unit>[] findSlotsToResumeLocked(java.lang.Object[] r11) {
        /*
            r10 = this;
            int r0 = r11.length
            int r1 = r10.nCollectors
            if (r1 != 0) goto L_0x0006
            goto L_0x0041
        L_0x0006:
            S[] r1 = r10.slots
            if (r1 != 0) goto L_0x000b
            goto L_0x0041
        L_0x000b:
            r2 = 0
            int r3 = r1.length
        L_0x000d:
            if (r2 >= r3) goto L_0x0041
            r4 = r1[r2]
            int r2 = r2 + 1
            if (r4 == 0) goto L_0x000d
            kotlinx.coroutines.flow.SharedFlowSlot r4 = (kotlinx.coroutines.flow.SharedFlowSlot) r4
            kotlinx.coroutines.CancellableContinuationImpl r5 = r4.cont
            if (r5 != 0) goto L_0x001c
            goto L_0x000d
        L_0x001c:
            long r6 = r10.tryPeekLocked(r4)
            r8 = 0
            int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r6 >= 0) goto L_0x0027
            goto L_0x000d
        L_0x0027:
            int r6 = r11.length
            if (r0 < r6) goto L_0x0035
            int r6 = r11.length
            r7 = 2
            int r6 = r6 * r7
            int r6 = java.lang.Math.max(r7, r6)
            java.lang.Object[] r11 = java.util.Arrays.copyOf(r11, r6)
        L_0x0035:
            r6 = r11
            kotlin.coroutines.Continuation[] r6 = (kotlin.coroutines.Continuation[]) r6
            int r7 = r0 + 1
            r6[r0] = r5
            r0 = 0
            r4.cont = r0
            r0 = r7
            goto L_0x000d
        L_0x0041:
            kotlin.coroutines.Continuation[] r11 = (kotlin.coroutines.Continuation[]) r11
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.SharedFlowImpl.findSlotsToResumeLocked(kotlin.coroutines.Continuation[]):kotlin.coroutines.Continuation[]");
    }

    public final Object[] growBuffer(Object[] objArr, int i, int i2) {
        boolean z;
        int i3 = 0;
        if (i2 > 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            Object[] objArr2 = new Object[i2];
            this.buffer = objArr2;
            if (objArr == null) {
                return objArr2;
            }
            long head = getHead();
            while (i3 < i) {
                int i4 = i3 + 1;
                int i5 = (int) (((long) i3) + head);
                objArr2[i5 & (i2 - 1)] = objArr[(objArr.length - 1) & i5];
                i3 = i4;
            }
            return objArr2;
        }
        throw new IllegalStateException("Buffer size overflow".toString());
    }

    public final void updateBufferLocked(long j, long j2, long j3, long j4) {
        long j5 = j;
        long j6 = j2;
        long min = Math.min(j6, j);
        boolean z = DebugKt.DEBUG;
        for (long head = getHead(); head < min; head = 1 + head) {
            Object[] objArr = this.buffer;
            Intrinsics.checkNotNull(objArr);
            objArr[((int) head) & (objArr.length - 1)] = null;
        }
        this.replayIndex = j5;
        this.minCollectorIndex = j6;
        this.bufferSize = (int) (j3 - min);
        this.queueSize = (int) (j4 - j3);
        boolean z2 = DebugKt.DEBUG;
    }

    public final Object awaitValue(SharedFlowSlot sharedFlowSlot, Continuation<? super Unit> continuation) {
        Unit unit;
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(R$color.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        synchronized (this) {
            if (tryPeekLocked(sharedFlowSlot) < 0) {
                sharedFlowSlot.cont = cancellableContinuationImpl;
            } else {
                cancellableContinuationImpl.resumeWith(Unit.INSTANCE);
            }
            unit = Unit.INSTANCE;
        }
        Object result = cancellableContinuationImpl.getResult();
        if (result == CoroutineSingletons.COROUTINE_SUSPENDED) {
            return result;
        }
        return unit;
    }

    public final void cleanupTailLocked() {
        if (this.bufferCapacity != 0 || this.queueSize > 1) {
            Object[] objArr = this.buffer;
            Intrinsics.checkNotNull(objArr);
            while (this.queueSize > 0) {
                long head = getHead();
                int i = this.bufferSize;
                int i2 = this.queueSize;
                if (objArr[((int) ((head + ((long) (i + i2))) - 1)) & (objArr.length - 1)] == SharedFlowKt.NO_VALUE) {
                    this.queueSize = i2 - 1;
                    objArr[((int) (getHead() + ((long) (this.bufferSize + this.queueSize)))) & (objArr.length - 1)] = null;
                } else {
                    return;
                }
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v9, resolved type: kotlinx.coroutines.flow.SharedFlowSlot} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0065  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x009e A[Catch:{ all -> 0x0046 }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00bd A[Catch:{ all -> 0x0046 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object collect(kotlinx.coroutines.flow.FlowCollector<? super T> r9, kotlin.coroutines.Continuation<? super kotlin.Unit> r10) {
        /*
            r8 = this;
            boolean r0 = r10 instanceof kotlinx.coroutines.flow.SharedFlowImpl$collect$1
            if (r0 == 0) goto L_0x0013
            r0 = r10
            kotlinx.coroutines.flow.SharedFlowImpl$collect$1 r0 = (kotlinx.coroutines.flow.SharedFlowImpl$collect$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            kotlinx.coroutines.flow.SharedFlowImpl$collect$1 r0 = new kotlinx.coroutines.flow.SharedFlowImpl$collect$1
            r0.<init>(r8, r10)
        L_0x0018:
            java.lang.Object r10 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 3
            r4 = 2
            r5 = 1
            if (r2 == 0) goto L_0x0065
            if (r2 == r5) goto L_0x004c
            if (r2 == r4) goto L_0x0032
            if (r2 != r3) goto L_0x002a
            goto L_0x0032
        L_0x002a:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L_0x0032:
            java.lang.Object r8 = r0.L$3
            kotlinx.coroutines.Job r8 = (kotlinx.coroutines.Job) r8
            java.lang.Object r9 = r0.L$2
            kotlinx.coroutines.flow.SharedFlowSlot r9 = (kotlinx.coroutines.flow.SharedFlowSlot) r9
            java.lang.Object r2 = r0.L$1
            kotlinx.coroutines.flow.FlowCollector r2 = (kotlinx.coroutines.flow.FlowCollector) r2
            java.lang.Object r5 = r0.L$0
            kotlinx.coroutines.flow.SharedFlowImpl r5 = (kotlinx.coroutines.flow.SharedFlowImpl) r5
            kotlin.ResultKt.throwOnFailure(r10)     // Catch:{ all -> 0x0046 }
            goto L_0x0096
        L_0x0046:
            r8 = move-exception
        L_0x0047:
            r10 = r9
            r9 = r8
            r8 = r5
            goto L_0x00d4
        L_0x004c:
            java.lang.Object r8 = r0.L$2
            r9 = r8
            kotlinx.coroutines.flow.SharedFlowSlot r9 = (kotlinx.coroutines.flow.SharedFlowSlot) r9
            java.lang.Object r8 = r0.L$1
            kotlinx.coroutines.flow.FlowCollector r8 = (kotlinx.coroutines.flow.FlowCollector) r8
            java.lang.Object r2 = r0.L$0
            kotlinx.coroutines.flow.SharedFlowImpl r2 = (kotlinx.coroutines.flow.SharedFlowImpl) r2
            kotlin.ResultKt.throwOnFailure(r10)     // Catch:{ all -> 0x005f }
            r10 = r8
            r8 = r2
            goto L_0x0087
        L_0x005f:
            r8 = move-exception
            r10 = r9
            r9 = r8
            r8 = r2
            goto L_0x00d4
        L_0x0065:
            kotlin.ResultKt.throwOnFailure(r10)
            kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot r10 = r8.allocateSlot()
            kotlinx.coroutines.flow.SharedFlowSlot r10 = (kotlinx.coroutines.flow.SharedFlowSlot) r10
            boolean r2 = r9 instanceof kotlinx.coroutines.flow.SubscribedFlowCollector     // Catch:{ all -> 0x00d3 }
            if (r2 == 0) goto L_0x0084
            r2 = r9
            kotlinx.coroutines.flow.SubscribedFlowCollector r2 = (kotlinx.coroutines.flow.SubscribedFlowCollector) r2     // Catch:{ all -> 0x00d3 }
            r0.L$0 = r8     // Catch:{ all -> 0x00d3 }
            r0.L$1 = r9     // Catch:{ all -> 0x00d3 }
            r0.L$2 = r10     // Catch:{ all -> 0x00d3 }
            r0.label = r5     // Catch:{ all -> 0x00d3 }
            kotlin.Unit r2 = r2.onSubscription(r0)     // Catch:{ all -> 0x00d3 }
            if (r2 != r1) goto L_0x0084
            return r1
        L_0x0084:
            r7 = r10
            r10 = r9
            r9 = r7
        L_0x0087:
            kotlin.coroutines.CoroutineContext r2 = r0.getContext()     // Catch:{ all -> 0x00ce }
            kotlinx.coroutines.Job$Key r5 = kotlinx.coroutines.Job.Key.$$INSTANCE     // Catch:{ all -> 0x00ce }
            kotlin.coroutines.CoroutineContext$Element r2 = r2.get(r5)     // Catch:{ all -> 0x00ce }
            kotlinx.coroutines.Job r2 = (kotlinx.coroutines.Job) r2     // Catch:{ all -> 0x00ce }
            r5 = r8
            r8 = r2
            r2 = r10
        L_0x0096:
            java.lang.Object r10 = r5.tryTakeValue(r9)     // Catch:{ all -> 0x0046 }
            kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.flow.SharedFlowKt.NO_VALUE     // Catch:{ all -> 0x0046 }
            if (r10 == r6) goto L_0x00bd
            if (r8 != 0) goto L_0x00a1
            goto L_0x00a7
        L_0x00a1:
            boolean r6 = r8.isActive()     // Catch:{ all -> 0x0046 }
            if (r6 == 0) goto L_0x00b8
        L_0x00a7:
            r0.L$0 = r5     // Catch:{ all -> 0x0046 }
            r0.L$1 = r2     // Catch:{ all -> 0x0046 }
            r0.L$2 = r9     // Catch:{ all -> 0x0046 }
            r0.L$3 = r8     // Catch:{ all -> 0x0046 }
            r0.label = r3     // Catch:{ all -> 0x0046 }
            java.lang.Object r10 = r2.emit(r10, r0)     // Catch:{ all -> 0x0046 }
            if (r10 != r1) goto L_0x0096
            return r1
        L_0x00b8:
            java.util.concurrent.CancellationException r8 = r8.getCancellationException()     // Catch:{ all -> 0x0046 }
            throw r8     // Catch:{ all -> 0x0046 }
        L_0x00bd:
            r0.L$0 = r5     // Catch:{ all -> 0x0046 }
            r0.L$1 = r2     // Catch:{ all -> 0x0046 }
            r0.L$2 = r9     // Catch:{ all -> 0x0046 }
            r0.L$3 = r8     // Catch:{ all -> 0x0046 }
            r0.label = r4     // Catch:{ all -> 0x0046 }
            java.lang.Object r10 = r5.awaitValue(r9, r0)     // Catch:{ all -> 0x0046 }
            if (r10 != r1) goto L_0x0096
            return r1
        L_0x00ce:
            r10 = move-exception
            r5 = r8
            r8 = r10
            goto L_0x0047
        L_0x00d3:
            r9 = move-exception
        L_0x00d4:
            r8.freeSlot(r10)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.SharedFlowImpl.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final AbstractSharedFlowSlot createSlot() {
        return new SharedFlowSlot();
    }

    public final void dropOldestLocked() {
        S[] sArr;
        Object[] objArr = this.buffer;
        Intrinsics.checkNotNull(objArr);
        objArr[((int) getHead()) & (objArr.length - 1)] = null;
        this.bufferSize--;
        long head = getHead() + 1;
        if (this.replayIndex < head) {
            this.replayIndex = head;
        }
        if (this.minCollectorIndex < head) {
            if (!(this.nCollectors == 0 || (sArr = this.slots) == null)) {
                int i = 0;
                int length = sArr.length;
                while (i < length) {
                    S s = sArr[i];
                    i++;
                    if (s != null) {
                        SharedFlowSlot sharedFlowSlot = (SharedFlowSlot) s;
                        long j = sharedFlowSlot.index;
                        if (j >= 0 && j < head) {
                            sharedFlowSlot.index = head;
                        }
                    }
                }
            }
            this.minCollectorIndex = head;
        }
        boolean z = DebugKt.DEBUG;
    }

    public final void enqueueLocked(Object obj) {
        int i = this.bufferSize + this.queueSize;
        Object[] objArr = this.buffer;
        if (objArr == null) {
            objArr = growBuffer((Object[]) null, 0, 2);
        } else if (i >= objArr.length) {
            objArr = growBuffer(objArr, i, objArr.length * 2);
        }
        objArr[((int) (getHead() + ((long) i))) & (objArr.length - 1)] = obj;
    }

    public final long getHead() {
        return Math.min(this.minCollectorIndex, this.replayIndex);
    }

    public final boolean tryEmit(T t) {
        int i;
        boolean z;
        Continuation[] continuationArr = SliceSpecs.EMPTY_RESUMES;
        synchronized (this) {
            i = 0;
            if (tryEmitLocked(t)) {
                continuationArr = findSlotsToResumeLocked(continuationArr);
                z = true;
            } else {
                z = false;
            }
        }
        int length = continuationArr.length;
        while (i < length) {
            Continuation continuation = continuationArr[i];
            i++;
            if (continuation != null) {
                continuation.resumeWith(Unit.INSTANCE);
            }
        }
        return z;
    }

    public final boolean tryEmitLocked(T t) {
        if (this.nCollectors == 0) {
            boolean z = DebugKt.DEBUG;
            if (this.replay != 0) {
                enqueueLocked(t);
                int i = this.bufferSize + 1;
                this.bufferSize = i;
                if (i > this.replay) {
                    dropOldestLocked();
                }
                this.minCollectorIndex = getHead() + ((long) this.bufferSize);
            }
            return true;
        }
        if (this.bufferSize >= this.bufferCapacity && this.minCollectorIndex <= this.replayIndex) {
            int ordinal = this.onBufferOverflow.ordinal();
            if (ordinal == 0) {
                return false;
            }
            if (ordinal == 2) {
                return true;
            }
        }
        enqueueLocked(t);
        int i2 = this.bufferSize + 1;
        this.bufferSize = i2;
        if (i2 > this.bufferCapacity) {
            dropOldestLocked();
        }
        long head = getHead() + ((long) this.bufferSize);
        long j = this.replayIndex;
        if (((int) (head - j)) > this.replay) {
            updateBufferLocked(1 + j, this.minCollectorIndex, getHead() + ((long) this.bufferSize), getHead() + ((long) this.bufferSize) + ((long) this.queueSize));
        }
        return true;
    }

    public final long tryPeekLocked(SharedFlowSlot sharedFlowSlot) {
        long j = sharedFlowSlot.index;
        if (j < getHead() + ((long) this.bufferSize)) {
            return j;
        }
        if (this.bufferCapacity <= 0 && j <= getHead() && this.queueSize != 0) {
            return j;
        }
        return -1;
    }

    public final Object tryTakeValue(SharedFlowSlot sharedFlowSlot) {
        Object obj;
        Continuation[] continuationArr = SliceSpecs.EMPTY_RESUMES;
        synchronized (this) {
            long tryPeekLocked = tryPeekLocked(sharedFlowSlot);
            if (tryPeekLocked < 0) {
                obj = SharedFlowKt.NO_VALUE;
            } else {
                long j = sharedFlowSlot.index;
                Object[] objArr = this.buffer;
                Intrinsics.checkNotNull(objArr);
                Object obj2 = objArr[((int) tryPeekLocked) & (objArr.length - 1)];
                if (obj2 instanceof Emitter) {
                    obj2 = ((Emitter) obj2).value;
                }
                sharedFlowSlot.index = tryPeekLocked + 1;
                Object obj3 = obj2;
                continuationArr = mo21371xffe52cb8(j);
                obj = obj3;
            }
        }
        int i = 0;
        int length = continuationArr.length;
        while (i < length) {
            Continuation continuation = continuationArr[i];
            i++;
            if (continuation != null) {
                continuation.resumeWith(Unit.INSTANCE);
            }
        }
        return obj;
    }

    /* renamed from: updateCollectorIndexLocked$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public final Continuation<Unit>[] mo21371xffe52cb8(long j) {
        int i;
        long j2;
        boolean z;
        S[] sArr;
        boolean z2 = DebugKt.DEBUG;
        if (j > this.minCollectorIndex) {
            return SliceSpecs.EMPTY_RESUMES;
        }
        long head = getHead();
        long j3 = ((long) this.bufferSize) + head;
        long j4 = 1;
        if (this.bufferCapacity == 0 && this.queueSize > 0) {
            j3++;
        }
        if (!(this.nCollectors == 0 || (sArr = this.slots) == null)) {
            int length = sArr.length;
            int i2 = 0;
            while (i2 < length) {
                S s = sArr[i2];
                i2++;
                if (s != null) {
                    long j5 = ((SharedFlowSlot) s).index;
                    if (j5 >= 0 && j5 < j3) {
                        j3 = j5;
                    }
                }
            }
        }
        boolean z3 = DebugKt.DEBUG;
        if (j3 <= this.minCollectorIndex) {
            return SliceSpecs.EMPTY_RESUMES;
        }
        long head2 = getHead() + ((long) this.bufferSize);
        if (this.nCollectors > 0) {
            i = Math.min(this.queueSize, this.bufferCapacity - ((int) (head2 - j3)));
        } else {
            i = this.queueSize;
        }
        Continuation<Unit>[] continuationArr = SliceSpecs.EMPTY_RESUMES;
        long j6 = ((long) this.queueSize) + head2;
        if (i > 0) {
            continuationArr = new Continuation[i];
            Object[] objArr = this.buffer;
            Intrinsics.checkNotNull(objArr);
            long j7 = head2;
            int i3 = 0;
            while (true) {
                if (head2 >= j6) {
                    head2 = j7;
                    break;
                }
                long j8 = head2 + j4;
                int i4 = (int) head2;
                Object obj = objArr[(objArr.length - 1) & i4];
                Symbol symbol = SharedFlowKt.NO_VALUE;
                if (obj != symbol) {
                    Objects.requireNonNull(obj, "null cannot be cast to non-null type kotlinx.coroutines.flow.SharedFlowImpl.Emitter");
                    Emitter emitter = (Emitter) obj;
                    int i5 = i3 + 1;
                    continuationArr[i3] = emitter.cont;
                    objArr[(objArr.length - 1) & i4] = symbol;
                    long j9 = j7;
                    objArr[((int) j9) & (objArr.length - 1)] = emitter.value;
                    long j10 = j9 + 1;
                    if (i5 >= i) {
                        head2 = j10;
                        break;
                    }
                    i3 = i5;
                    j7 = j10;
                    head2 = j8;
                    j4 = 1;
                } else {
                    long j11 = j7;
                    head2 = j8;
                }
            }
        }
        int i6 = (int) (head2 - head);
        if (this.nCollectors == 0) {
            j2 = head2;
        } else {
            j2 = j3;
        }
        long max = Math.max(this.replayIndex, head2 - ((long) Math.min(this.replay, i6)));
        if (this.bufferCapacity == 0 && max < j6) {
            Object[] objArr2 = this.buffer;
            Intrinsics.checkNotNull(objArr2);
            if (Intrinsics.areEqual(objArr2[((int) max) & (objArr2.length - 1)], SharedFlowKt.NO_VALUE)) {
                head2++;
                max++;
            }
        }
        updateBufferLocked(max, j2, head2, j6);
        cleanupTailLocked();
        if (continuationArr.length == 0) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            return findSlotsToResumeLocked(continuationArr);
        }
        return continuationArr;
    }

    public SharedFlowImpl(int i, int i2, BufferOverflow bufferOverflow) {
        this.replay = i;
        this.bufferCapacity = i2;
        this.onBufferOverflow = bufferOverflow;
    }

    public final Object emit(T t, Continuation<? super Unit> continuation) {
        Emitter emitter;
        Continuation[] continuationArr;
        if (tryEmit(t)) {
            return Unit.INSTANCE;
        }
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(R$color.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        Continuation[] continuationArr2 = SliceSpecs.EMPTY_RESUMES;
        synchronized (this) {
            if (tryEmitLocked(t)) {
                cancellableContinuationImpl.resumeWith(Unit.INSTANCE);
                continuationArr = findSlotsToResumeLocked(continuationArr2);
                emitter = null;
            } else {
                Emitter emitter2 = new Emitter(this, ((long) (this.bufferSize + this.queueSize)) + getHead(), t, cancellableContinuationImpl);
                enqueueLocked(emitter2);
                this.queueSize++;
                if (this.bufferCapacity == 0) {
                    continuationArr2 = findSlotsToResumeLocked(continuationArr2);
                }
                continuationArr = continuationArr2;
                emitter = emitter2;
            }
        }
        if (emitter != null) {
            cancellableContinuationImpl.invokeOnCancellation(new DisposeOnCancel(emitter));
        }
        int i = 0;
        int length = continuationArr.length;
        while (i < length) {
            Continuation continuation2 = continuationArr[i];
            i++;
            if (continuation2 != null) {
                continuation2.resumeWith(Unit.INSTANCE);
            }
        }
        Object result = cancellableContinuationImpl.getResult();
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (result != coroutineSingletons) {
            result = Unit.INSTANCE;
        }
        if (result == coroutineSingletons) {
            return result;
        }
        return Unit.INSTANCE;
    }
}
