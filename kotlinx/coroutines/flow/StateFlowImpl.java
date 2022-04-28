package kotlinx.coroutines.flow;

import androidx.slice.compat.SliceProviderCompat;
import java.util.Objects;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.flow.internal.AbstractSharedFlow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot;

/* compiled from: StateFlow.kt */
public final class StateFlowImpl<T> extends AbstractSharedFlow<StateFlowSlot> implements MutableStateFlow<T>, Flow {
    public final AtomicRef<Object> _state;
    public int sequence;

    public final AbstractSharedFlowSlot[] createSlotArray() {
        return new StateFlowSlot[2];
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x002d, code lost:
        r12 = (kotlinx.coroutines.flow.StateFlowSlot[]) r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x002f, code lost:
        if (r12 != null) goto L_0x0032;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0032, code lost:
        r2 = r12.length;
        r3 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0034, code lost:
        if (r3 >= r2) goto L_0x0069;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0036, code lost:
        r4 = r12[r3];
        r3 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x003a, code lost:
        if (r4 != null) goto L_0x003d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x003d, code lost:
        r5 = r4._state;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x003f, code lost:
        java.util.Objects.requireNonNull(r5);
        r6 = r5.value;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0044, code lost:
        if (r6 != null) goto L_0x0047;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0047, code lost:
        r7 = kotlinx.coroutines.flow.StateFlowKt.PENDING;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0049, code lost:
        if (r6 != r7) goto L_0x004c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x004c, code lost:
        r8 = kotlinx.coroutines.flow.StateFlowKt.NONE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x004e, code lost:
        if (r6 != r8) goto L_0x0059;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0056, code lost:
        if (r4._state.compareAndSet(r6, r7) == false) goto L_0x003f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x005f, code lost:
        if (r4._state.compareAndSet(r6, r8) == false) goto L_0x003f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0061, code lost:
        ((kotlinx.coroutines.CancellableContinuationImpl) r6).resumeWith(kotlin.Unit.INSTANCE);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0069, code lost:
        monitor-enter(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        r12 = r10.sequence;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x006c, code lost:
        if (r12 != r11) goto L_0x0073;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x006e, code lost:
        r10.sequence = r11 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0071, code lost:
        monitor-exit(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0072, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        r11 = r10.slots;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0075, code lost:
        monitor-exit(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0076, code lost:
        r9 = r12;
        r12 = r11;
        r11 = r9;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean updateState(java.lang.Object r11, java.lang.Object r12) {
        /*
            r10 = this;
            monitor-enter(r10)
            kotlinx.atomicfu.AtomicRef<java.lang.Object> r0 = r10._state     // Catch:{ all -> 0x0083 }
            java.util.Objects.requireNonNull(r0)     // Catch:{ all -> 0x0083 }
            T r0 = r0.value     // Catch:{ all -> 0x0083 }
            r1 = 0
            if (r11 == 0) goto L_0x0013
            boolean r11 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r11)     // Catch:{ all -> 0x0083 }
            if (r11 != 0) goto L_0x0013
            monitor-exit(r10)
            return r1
        L_0x0013:
            boolean r11 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r12)     // Catch:{ all -> 0x0083 }
            r0 = 1
            if (r11 == 0) goto L_0x001c
            monitor-exit(r10)
            return r0
        L_0x001c:
            kotlinx.atomicfu.AtomicRef<java.lang.Object> r11 = r10._state     // Catch:{ all -> 0x0083 }
            r11.setValue(r12)     // Catch:{ all -> 0x0083 }
            int r11 = r10.sequence     // Catch:{ all -> 0x0083 }
            r12 = r11 & 1
            if (r12 != 0) goto L_0x007d
            int r11 = r11 + r0
            r10.sequence = r11     // Catch:{ all -> 0x0083 }
            S[] r12 = r10.slots     // Catch:{ all -> 0x0083 }
            monitor-exit(r10)
        L_0x002d:
            kotlinx.coroutines.flow.StateFlowSlot[] r12 = (kotlinx.coroutines.flow.StateFlowSlot[]) r12
            if (r12 != 0) goto L_0x0032
            goto L_0x0069
        L_0x0032:
            int r2 = r12.length
            r3 = r1
        L_0x0034:
            if (r3 >= r2) goto L_0x0069
            r4 = r12[r3]
            int r3 = r3 + 1
            if (r4 != 0) goto L_0x003d
            goto L_0x0034
        L_0x003d:
            kotlinx.atomicfu.AtomicRef<java.lang.Object> r5 = r4._state
        L_0x003f:
            java.util.Objects.requireNonNull(r5)
            T r6 = r5.value
            if (r6 != 0) goto L_0x0047
            goto L_0x0034
        L_0x0047:
            kotlinx.coroutines.internal.Symbol r7 = kotlinx.coroutines.flow.StateFlowKt.PENDING
            if (r6 != r7) goto L_0x004c
            goto L_0x0034
        L_0x004c:
            kotlinx.coroutines.internal.Symbol r8 = kotlinx.coroutines.flow.StateFlowKt.NONE
            if (r6 != r8) goto L_0x0059
            kotlinx.atomicfu.AtomicRef<java.lang.Object> r8 = r4._state
            boolean r6 = r8.compareAndSet(r6, r7)
            if (r6 == 0) goto L_0x003f
            goto L_0x0034
        L_0x0059:
            kotlinx.atomicfu.AtomicRef<java.lang.Object> r7 = r4._state
            boolean r7 = r7.compareAndSet(r6, r8)
            if (r7 == 0) goto L_0x003f
            kotlinx.coroutines.CancellableContinuationImpl r6 = (kotlinx.coroutines.CancellableContinuationImpl) r6
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            r6.resumeWith(r4)
            goto L_0x0034
        L_0x0069:
            monitor-enter(r10)
            int r12 = r10.sequence     // Catch:{ all -> 0x007a }
            if (r12 != r11) goto L_0x0073
            int r11 = r11 + r0
            r10.sequence = r11     // Catch:{ all -> 0x007a }
            monitor-exit(r10)
            return r0
        L_0x0073:
            S[] r11 = r10.slots     // Catch:{ all -> 0x007a }
            monitor-exit(r10)
            r9 = r12
            r12 = r11
            r11 = r9
            goto L_0x002d
        L_0x007a:
            r11 = move-exception
            monitor-exit(r10)
            throw r11
        L_0x007d:
            int r11 = r11 + 2
            r10.sequence = r11     // Catch:{ all -> 0x0083 }
            monitor-exit(r10)
            return r0
        L_0x0083:
            r11 = move-exception
            monitor-exit(r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.StateFlowImpl.updateState(java.lang.Object, java.lang.Object):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x0087  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00c0 A[Catch:{ all -> 0x013b }] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00c1 A[Catch:{ all -> 0x013b }] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00d3 A[Catch:{ all -> 0x013b }] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00d5 A[Catch:{ all -> 0x013b }] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00e8 A[Catch:{ all -> 0x013b }, RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00e9 A[Catch:{ all -> 0x013b }] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0104 A[Catch:{ all -> 0x013b }] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0106 A[Catch:{ all -> 0x013b }] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0109 A[Catch:{ all -> 0x013b }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object collect(kotlinx.coroutines.flow.FlowCollector<? super T> r14, kotlin.coroutines.Continuation<? super kotlin.Unit> r15) {
        /*
            r13 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            boolean r1 = r15 instanceof kotlinx.coroutines.flow.StateFlowImpl$collect$1
            if (r1 == 0) goto L_0x0015
            r1 = r15
            kotlinx.coroutines.flow.StateFlowImpl$collect$1 r1 = (kotlinx.coroutines.flow.StateFlowImpl$collect$1) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0015
            int r2 = r2 - r3
            r1.label = r2
            goto L_0x001a
        L_0x0015:
            kotlinx.coroutines.flow.StateFlowImpl$collect$1 r1 = new kotlinx.coroutines.flow.StateFlowImpl$collect$1
            r1.<init>(r13, r15)
        L_0x001a:
            java.lang.Object r15 = r1.result
            int r2 = r1.label
            r3 = 1
            r4 = 2
            r5 = 3
            r6 = 0
            if (r2 == 0) goto L_0x0087
            if (r2 == r3) goto L_0x006f
            if (r2 == r4) goto L_0x0050
            if (r2 != r5) goto L_0x0048
            java.lang.Object r13 = r1.L$4
            java.lang.Object r14 = r1.L$3
            kotlinx.coroutines.Job r14 = (kotlinx.coroutines.Job) r14
            java.lang.Object r2 = r1.L$2
            kotlinx.coroutines.flow.StateFlowSlot r2 = (kotlinx.coroutines.flow.StateFlowSlot) r2
            java.lang.Object r7 = r1.L$1
            kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7
            java.lang.Object r8 = r1.L$0
            kotlinx.coroutines.flow.StateFlowImpl r8 = (kotlinx.coroutines.flow.StateFlowImpl) r8
            kotlin.ResultKt.throwOnFailure(r15)     // Catch:{ all -> 0x006c }
            r15 = r14
            r14 = r13
            r13 = r8
            r8 = r7
            r7 = r2
        L_0x0044:
            r2 = r1
            r1 = r0
            goto L_0x00b7
        L_0x0048:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r14 = "call to 'resume' before 'invoke' with coroutine"
            r13.<init>(r14)
            throw r13
        L_0x0050:
            java.lang.Object r13 = r1.L$4
            java.lang.Object r14 = r1.L$3
            kotlinx.coroutines.Job r14 = (kotlinx.coroutines.Job) r14
            java.lang.Object r2 = r1.L$2
            kotlinx.coroutines.flow.StateFlowSlot r2 = (kotlinx.coroutines.flow.StateFlowSlot) r2
            java.lang.Object r7 = r1.L$1
            kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7
            java.lang.Object r8 = r1.L$0
            kotlinx.coroutines.flow.StateFlowImpl r8 = (kotlinx.coroutines.flow.StateFlowImpl) r8
            kotlin.ResultKt.throwOnFailure(r15)     // Catch:{ all -> 0x006c }
            r15 = r8
            r8 = r7
            r7 = r2
            r2 = r1
            r1 = r0
            goto L_0x00ec
        L_0x006c:
            r13 = move-exception
            goto L_0x014d
        L_0x006f:
            java.lang.Object r13 = r1.L$2
            kotlinx.coroutines.flow.StateFlowSlot r13 = (kotlinx.coroutines.flow.StateFlowSlot) r13
            java.lang.Object r14 = r1.L$1
            kotlinx.coroutines.flow.FlowCollector r14 = (kotlinx.coroutines.flow.FlowCollector) r14
            java.lang.Object r2 = r1.L$0
            kotlinx.coroutines.flow.StateFlowImpl r2 = (kotlinx.coroutines.flow.StateFlowImpl) r2
            kotlin.ResultKt.throwOnFailure(r15)     // Catch:{ all -> 0x0082 }
            r12 = r2
            r2 = r13
            r13 = r12
            goto L_0x00a7
        L_0x0082:
            r14 = move-exception
            r7 = r13
        L_0x0084:
            r13 = r14
            goto L_0x014b
        L_0x0087:
            kotlin.ResultKt.throwOnFailure(r15)
            kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot r15 = r13.allocateSlot()
            kotlinx.coroutines.flow.StateFlowSlot r15 = (kotlinx.coroutines.flow.StateFlowSlot) r15
            boolean r2 = r14 instanceof kotlinx.coroutines.flow.SubscribedFlowCollector     // Catch:{ all -> 0x0147 }
            if (r2 == 0) goto L_0x00a6
            r2 = r14
            kotlinx.coroutines.flow.SubscribedFlowCollector r2 = (kotlinx.coroutines.flow.SubscribedFlowCollector) r2     // Catch:{ all -> 0x0147 }
            r1.L$0 = r13     // Catch:{ all -> 0x0147 }
            r1.L$1 = r14     // Catch:{ all -> 0x0147 }
            r1.L$2 = r15     // Catch:{ all -> 0x0147 }
            r1.label = r3     // Catch:{ all -> 0x0147 }
            kotlin.Unit r2 = r2.onSubscription(r1)     // Catch:{ all -> 0x0147 }
            if (r2 != r0) goto L_0x00a6
            return r0
        L_0x00a6:
            r2 = r15
        L_0x00a7:
            kotlin.coroutines.CoroutineContext r15 = r1.getContext()     // Catch:{ all -> 0x0142 }
            kotlinx.coroutines.Job$Key r7 = kotlinx.coroutines.Job.Key.$$INSTANCE     // Catch:{ all -> 0x0142 }
            kotlin.coroutines.CoroutineContext$Element r15 = r15.get(r7)     // Catch:{ all -> 0x0142 }
            kotlinx.coroutines.Job r15 = (kotlinx.coroutines.Job) r15     // Catch:{ all -> 0x0142 }
            r8 = r14
            r7 = r2
            r14 = r6
            goto L_0x0044
        L_0x00b7:
            kotlinx.atomicfu.AtomicRef<java.lang.Object> r9 = r13._state     // Catch:{ all -> 0x013b }
            java.util.Objects.requireNonNull(r9)     // Catch:{ all -> 0x013b }
            T r9 = r9.value     // Catch:{ all -> 0x013b }
            if (r15 != 0) goto L_0x00c1
            goto L_0x00c7
        L_0x00c1:
            boolean r10 = r15.isActive()     // Catch:{ all -> 0x013b }
            if (r10 == 0) goto L_0x013d
        L_0x00c7:
            if (r14 == 0) goto L_0x00cf
            boolean r10 = kotlin.jvm.internal.Intrinsics.areEqual(r14, r9)     // Catch:{ all -> 0x013b }
            if (r10 != 0) goto L_0x00f0
        L_0x00cf:
            kotlinx.coroutines.internal.Symbol r14 = androidx.slice.compat.SliceProviderCompat.C03502.NULL     // Catch:{ all -> 0x013b }
            if (r9 != r14) goto L_0x00d5
            r14 = r6
            goto L_0x00d6
        L_0x00d5:
            r14 = r9
        L_0x00d6:
            r2.L$0 = r13     // Catch:{ all -> 0x013b }
            r2.L$1 = r8     // Catch:{ all -> 0x013b }
            r2.L$2 = r7     // Catch:{ all -> 0x013b }
            r2.L$3 = r15     // Catch:{ all -> 0x013b }
            r2.L$4 = r9     // Catch:{ all -> 0x013b }
            r2.label = r4     // Catch:{ all -> 0x013b }
            java.lang.Object r14 = r8.emit(r14, r2)     // Catch:{ all -> 0x013b }
            if (r14 != r0) goto L_0x00e9
            return r0
        L_0x00e9:
            r14 = r15
            r15 = r13
            r13 = r9
        L_0x00ec:
            r12 = r14
            r14 = r13
            r13 = r15
            r15 = r12
        L_0x00f0:
            java.util.Objects.requireNonNull(r7)     // Catch:{ all -> 0x013b }
            kotlinx.atomicfu.AtomicRef<java.lang.Object> r9 = r7._state     // Catch:{ all -> 0x013b }
            kotlinx.coroutines.internal.Symbol r10 = kotlinx.coroutines.flow.StateFlowKt.NONE     // Catch:{ all -> 0x013b }
            java.lang.Object r9 = r9.getAndSet(r10)     // Catch:{ all -> 0x013b }
            kotlin.jvm.internal.Intrinsics.checkNotNull(r9)     // Catch:{ all -> 0x013b }
            boolean r11 = kotlinx.coroutines.DebugKt.DEBUG     // Catch:{ all -> 0x013b }
            kotlinx.coroutines.internal.Symbol r11 = kotlinx.coroutines.flow.StateFlowKt.PENDING     // Catch:{ all -> 0x013b }
            if (r9 != r11) goto L_0x0106
            r9 = r3
            goto L_0x0107
        L_0x0106:
            r9 = 0
        L_0x0107:
            if (r9 != 0) goto L_0x00b7
            r2.L$0 = r13     // Catch:{ all -> 0x013b }
            r2.L$1 = r8     // Catch:{ all -> 0x013b }
            r2.L$2 = r7     // Catch:{ all -> 0x013b }
            r2.L$3 = r15     // Catch:{ all -> 0x013b }
            r2.L$4 = r14     // Catch:{ all -> 0x013b }
            r2.label = r5     // Catch:{ all -> 0x013b }
            kotlinx.coroutines.CancellableContinuationImpl r9 = new kotlinx.coroutines.CancellableContinuationImpl     // Catch:{ all -> 0x013b }
            kotlin.coroutines.Continuation r11 = androidx.preference.R$color.intercepted(r2)     // Catch:{ all -> 0x013b }
            r9.<init>(r11, r3)     // Catch:{ all -> 0x013b }
            r9.initCancellability()     // Catch:{ all -> 0x013b }
            kotlinx.atomicfu.AtomicRef<java.lang.Object> r11 = r7._state     // Catch:{ all -> 0x013b }
            boolean r10 = r11.compareAndSet(r10, r9)     // Catch:{ all -> 0x013b }
            if (r10 == 0) goto L_0x012a
            goto L_0x012f
        L_0x012a:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x013b }
            r9.resumeWith(r10)     // Catch:{ all -> 0x013b }
        L_0x012f:
            java.lang.Object r9 = r9.getResult()     // Catch:{ all -> 0x013b }
            if (r9 != r1) goto L_0x0136
            goto L_0x0138
        L_0x0136:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x013b }
        L_0x0138:
            if (r9 != r0) goto L_0x00b7
            return r0
        L_0x013b:
            r14 = move-exception
            goto L_0x0144
        L_0x013d:
            java.util.concurrent.CancellationException r14 = r15.getCancellationException()     // Catch:{ all -> 0x013b }
            throw r14     // Catch:{ all -> 0x013b }
        L_0x0142:
            r14 = move-exception
            r7 = r2
        L_0x0144:
            r2 = r13
            goto L_0x0084
        L_0x0147:
            r14 = move-exception
            r2 = r13
            r13 = r14
            r7 = r15
        L_0x014b:
            r8 = r2
            r2 = r7
        L_0x014d:
            r8.freeSlot(r2)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.StateFlowImpl.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final AbstractSharedFlowSlot createSlot() {
        return new StateFlowSlot();
    }

    public final T getValue() {
        T t = SliceProviderCompat.C03502.NULL;
        AtomicRef<Object> atomicRef = this._state;
        Objects.requireNonNull(atomicRef);
        T t2 = atomicRef.value;
        if (t2 == t) {
            return null;
        }
        return t2;
    }

    public final void setValue(T t) {
        if (t == null) {
            t = SliceProviderCompat.C03502.NULL;
        }
        updateState((Object) null, t);
    }

    public StateFlowImpl(Object obj) {
        this._state = new AtomicRef<>(obj);
    }

    public final Object emit(T t, Continuation<? super Unit> continuation) {
        setValue(t);
        return Unit.INSTANCE;
    }

    public final boolean tryEmit(T t) {
        setValue(t);
        return true;
    }
}
