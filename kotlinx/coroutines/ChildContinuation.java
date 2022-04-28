package kotlinx.coroutines;

import kotlin.Unit;

/* compiled from: JobSupport.kt */
public final class ChildContinuation extends JobCancellingNode {
    public final CancellableContinuationImpl<?> child;

    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((Throwable) obj);
        return Unit.INSTANCE;
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void invoke(java.lang.Throwable r7) {
        /*
            r6 = this;
            kotlinx.coroutines.CancellableContinuationImpl<?> r7 = r6.child
            kotlinx.coroutines.JobSupport r6 = r6.getJob()
            java.lang.Throwable r6 = r7.getContinuationCancellationCause(r6)
            boolean r0 = r7.isReusable()
            if (r0 != 0) goto L_0x0011
            goto L_0x003f
        L_0x0011:
            kotlin.coroutines.Continuation<T> r0 = r7.delegate
            kotlinx.coroutines.internal.DispatchedContinuation r0 = (kotlinx.coroutines.internal.DispatchedContinuation) r0
            java.util.Objects.requireNonNull(r0)
            kotlinx.atomicfu.AtomicRef<java.lang.Object> r1 = r0._reusableCancellableContinuation
        L_0x001a:
            java.util.Objects.requireNonNull(r1)
            T r2 = r1.value
            kotlinx.coroutines.internal.Symbol r3 = kotlinx.coroutines.internal.DispatchedContinuationKt.REUSABLE_CLAIMED
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r3)
            r5 = 1
            if (r4 == 0) goto L_0x0031
            kotlinx.atomicfu.AtomicRef<java.lang.Object> r2 = r0._reusableCancellableContinuation
            boolean r2 = r2.compareAndSet(r3, r6)
            if (r2 == 0) goto L_0x001a
            goto L_0x0040
        L_0x0031:
            boolean r3 = r2 instanceof java.lang.Throwable
            if (r3 == 0) goto L_0x0036
            goto L_0x0040
        L_0x0036:
            kotlinx.atomicfu.AtomicRef<java.lang.Object> r3 = r0._reusableCancellableContinuation
            r4 = 0
            boolean r2 = r3.compareAndSet(r2, r4)
            if (r2 == 0) goto L_0x001a
        L_0x003f:
            r5 = 0
        L_0x0040:
            if (r5 == 0) goto L_0x0043
            goto L_0x004f
        L_0x0043:
            r7.cancel(r6)
            boolean r6 = r7.isReusable()
            if (r6 != 0) goto L_0x004f
            r7.mo21192x2343eba7()
        L_0x004f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.ChildContinuation.invoke(java.lang.Throwable):void");
    }

    public ChildContinuation(CancellableContinuationImpl<?> cancellableContinuationImpl) {
        this.child = cancellableContinuationImpl;
    }
}
