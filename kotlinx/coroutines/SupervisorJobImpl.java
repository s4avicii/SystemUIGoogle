package kotlinx.coroutines;

/* compiled from: Supervisor.kt */
public final class SupervisorJobImpl extends JobImpl {
    public final boolean childCancelled(Throwable th) {
        return false;
    }

    public SupervisorJobImpl(Job job) {
        super(job);
    }
}
