package kotlinx.coroutines;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CancellableContinuationImpl.kt */
public final class CompletedContinuation {
    public final Throwable cancelCause;
    public final CancelHandler cancelHandler;
    public final Object idempotentResume;
    public final Function1<Throwable, Unit> onCancellation;
    public final Object result;

    public CompletedContinuation(Object obj, CancelHandler cancelHandler2, Function1<? super Throwable, Unit> function1, Object obj2, Throwable th) {
        this.result = obj;
        this.cancelHandler = cancelHandler2;
        this.onCancellation = function1;
        this.idempotentResume = obj2;
        this.cancelCause = th;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CompletedContinuation)) {
            return false;
        }
        CompletedContinuation completedContinuation = (CompletedContinuation) obj;
        return Intrinsics.areEqual(this.result, completedContinuation.result) && Intrinsics.areEqual(this.cancelHandler, completedContinuation.cancelHandler) && Intrinsics.areEqual(this.onCancellation, completedContinuation.onCancellation) && Intrinsics.areEqual(this.idempotentResume, completedContinuation.idempotentResume) && Intrinsics.areEqual(this.cancelCause, completedContinuation.cancelCause);
    }

    public final int hashCode() {
        Object obj = this.result;
        int i = 0;
        int hashCode = (obj == null ? 0 : obj.hashCode()) * 31;
        CancelHandler cancelHandler2 = this.cancelHandler;
        int hashCode2 = (hashCode + (cancelHandler2 == null ? 0 : cancelHandler2.hashCode())) * 31;
        Function1<Throwable, Unit> function1 = this.onCancellation;
        int hashCode3 = (hashCode2 + (function1 == null ? 0 : function1.hashCode())) * 31;
        Object obj2 = this.idempotentResume;
        int hashCode4 = (hashCode3 + (obj2 == null ? 0 : obj2.hashCode())) * 31;
        Throwable th = this.cancelCause;
        if (th != null) {
            i = th.hashCode();
        }
        return hashCode4 + i;
    }

    public static CompletedContinuation copy$default(CompletedContinuation completedContinuation, CancelHandler cancelHandler2, CancellationException cancellationException, int i) {
        Object obj;
        Function1<Throwable, Unit> function1;
        Object obj2 = null;
        if ((i & 1) != 0) {
            obj = completedContinuation.result;
        } else {
            obj = null;
        }
        if ((i & 2) != 0) {
            cancelHandler2 = completedContinuation.cancelHandler;
        }
        CancelHandler cancelHandler3 = cancelHandler2;
        if ((i & 4) != 0) {
            function1 = completedContinuation.onCancellation;
        } else {
            function1 = null;
        }
        if ((i & 8) != 0) {
            obj2 = completedContinuation.idempotentResume;
        }
        Object obj3 = obj2;
        Throwable th = cancellationException;
        if ((i & 16) != 0) {
            th = completedContinuation.cancelCause;
        }
        Objects.requireNonNull(completedContinuation);
        return new CompletedContinuation(obj, cancelHandler3, function1, obj3, th);
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("CompletedContinuation(result=");
        m.append(this.result);
        m.append(", cancelHandler=");
        m.append(this.cancelHandler);
        m.append(", onCancellation=");
        m.append(this.onCancellation);
        m.append(", idempotentResume=");
        m.append(this.idempotentResume);
        m.append(", cancelCause=");
        m.append(this.cancelCause);
        m.append(')');
        return m.toString();
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ CompletedContinuation(Object obj, CancelHandler cancelHandler2, Function1 function1, Object obj2, CancellationException cancellationException, int i) {
        this(obj, (i & 2) != 0 ? null : cancelHandler2, (i & 4) != 0 ? null : function1, (i & 8) != 0 ? null : obj2, (i & 16) != 0 ? null : cancellationException);
    }
}
