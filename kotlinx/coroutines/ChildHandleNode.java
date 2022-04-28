package kotlinx.coroutines;

import kotlin.Unit;

/* compiled from: JobSupport.kt */
public final class ChildHandleNode extends JobCancellingNode implements ChildHandle {
    public final ChildJob childJob;

    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((Throwable) obj);
        return Unit.INSTANCE;
    }

    public final void invoke(Throwable th) {
        this.childJob.parentCancelled(getJob());
    }

    public ChildHandleNode(JobSupport jobSupport) {
        this.childJob = jobSupport;
    }

    public final boolean childCancelled(Throwable th) {
        return getJob().childCancelled(th);
    }

    public final Job getParent() {
        return getJob();
    }
}
