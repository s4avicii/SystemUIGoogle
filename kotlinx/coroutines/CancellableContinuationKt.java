package kotlinx.coroutines;

import java.util.Objects;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.DispatchedContinuationKt;

/* compiled from: CancellableContinuation.kt */
public final class CancellableContinuationKt {
    public static final <T> CancellableContinuationImpl<T> getOrCreateCancellableContinuation(Continuation<? super T> continuation) {
        CancellableContinuationImpl<T> cancellableContinuationImpl;
        CancellableContinuationImpl<T> cancellableContinuationImpl2;
        boolean z = true;
        if (!(continuation instanceof DispatchedContinuation)) {
            return new CancellableContinuationImpl<>(continuation, 1);
        }
        DispatchedContinuation dispatchedContinuation = (DispatchedContinuation) continuation;
        AtomicRef<Object> atomicRef = dispatchedContinuation._reusableCancellableContinuation;
        while (true) {
            Objects.requireNonNull(atomicRef);
            T t = atomicRef.value;
            cancellableContinuationImpl = null;
            if (t == null) {
                dispatchedContinuation._reusableCancellableContinuation.setValue(DispatchedContinuationKt.REUSABLE_CLAIMED);
                cancellableContinuationImpl2 = null;
                break;
            } else if (t instanceof CancellableContinuationImpl) {
                if (dispatchedContinuation._reusableCancellableContinuation.compareAndSet(t, DispatchedContinuationKt.REUSABLE_CLAIMED)) {
                    cancellableContinuationImpl2 = (CancellableContinuationImpl) t;
                    break;
                }
            } else if (t != DispatchedContinuationKt.REUSABLE_CLAIMED && !(t instanceof Throwable)) {
                throw new IllegalStateException(Intrinsics.stringPlus("Inconsistent state ", t).toString());
            }
        }
        if (cancellableContinuationImpl2 != null) {
            boolean z2 = DebugKt.DEBUG;
            AtomicRef<Object> atomicRef2 = cancellableContinuationImpl2._state;
            Objects.requireNonNull(atomicRef2);
            T t2 = atomicRef2.value;
            if (!(t2 instanceof CompletedContinuation) || ((CompletedContinuation) t2).idempotentResume == null) {
                cancellableContinuationImpl2._decision.setValue(0);
                cancellableContinuationImpl2._state.setValue(Active.INSTANCE);
            } else {
                cancellableContinuationImpl2.mo21192x2343eba7();
                z = false;
            }
            if (z) {
                cancellableContinuationImpl = cancellableContinuationImpl2;
            }
        }
        if (cancellableContinuationImpl == null) {
            return new CancellableContinuationImpl<>(continuation, 2);
        }
        return cancellableContinuationImpl;
    }
}
