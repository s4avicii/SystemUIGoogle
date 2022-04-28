package kotlinx.coroutines;

import kotlin.Result;
import kotlin.Unit;
import kotlinx.coroutines.JobSupport;

/* compiled from: JobSupport.kt */
public final class ResumeAwaitOnCompletion<T> extends JobNode {
    public final CancellableContinuationImpl<T> continuation;

    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((Throwable) obj);
        return Unit.INSTANCE;
    }

    public final void invoke(Throwable th) {
        Object state$external__kotlinx_coroutines__android_common__kotlinx_coroutines = getJob().mo21281x8adbf455();
        boolean z = DebugKt.DEBUG;
        if (state$external__kotlinx_coroutines__android_common__kotlinx_coroutines instanceof CompletedExceptionally) {
            this.continuation.resumeWith(new Result.Failure(((CompletedExceptionally) state$external__kotlinx_coroutines__android_common__kotlinx_coroutines).cause));
        } else {
            this.continuation.resumeWith(JobSupportKt.unboxState(state$external__kotlinx_coroutines__android_common__kotlinx_coroutines));
        }
    }

    public ResumeAwaitOnCompletion(JobSupport.AwaitContinuation awaitContinuation) {
        this.continuation = awaitContinuation;
    }
}
