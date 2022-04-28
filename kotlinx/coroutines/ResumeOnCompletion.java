package kotlinx.coroutines;

import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* compiled from: JobSupport.kt */
public final class ResumeOnCompletion extends JobNode {
    public final Continuation<Unit> continuation;

    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((Throwable) obj);
        return Unit.INSTANCE;
    }

    public final void invoke(Throwable th) {
        this.continuation.resumeWith(Unit.INSTANCE);
    }

    public ResumeOnCompletion(CancellableContinuationImpl cancellableContinuationImpl) {
        this.continuation = cancellableContinuationImpl;
    }
}
