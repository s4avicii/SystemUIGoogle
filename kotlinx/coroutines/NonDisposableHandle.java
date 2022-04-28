package kotlinx.coroutines;

/* compiled from: Job.kt */
public final class NonDisposableHandle implements DisposableHandle, ChildHandle {
    public static final NonDisposableHandle INSTANCE = new NonDisposableHandle();

    public final boolean childCancelled(Throwable th) {
        return false;
    }

    public final void dispose() {
    }

    public final Job getParent() {
        return null;
    }

    public final String toString() {
        return "NonDisposableHandle";
    }
}
