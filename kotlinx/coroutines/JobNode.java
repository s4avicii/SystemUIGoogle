package kotlinx.coroutines;

/* compiled from: JobSupport.kt */
public abstract class JobNode extends CompletionHandlerBase implements DisposableHandle, Incomplete {
    public JobSupport job;

    public final NodeList getList() {
        return null;
    }

    public final boolean isActive() {
        return true;
    }

    public final JobSupport getJob() {
        JobSupport jobSupport = this.job;
        if (jobSupport != null) {
            return jobSupport;
        }
        return null;
    }

    public final String toString() {
        return DebugStringsKt.getClassSimpleName(this) + '@' + DebugStringsKt.getHexAddress(this) + "[job@" + DebugStringsKt.getHexAddress(getJob()) + ']';
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x001d A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:3:0x000f A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void dispose() {
        /*
            r4 = this;
            kotlinx.coroutines.JobSupport r0 = r4.getJob()
            java.util.Objects.requireNonNull(r0)
        L_0x0007:
            java.lang.Object r1 = r0.mo21281x8adbf455()
            boolean r2 = r1 instanceof kotlinx.coroutines.JobNode
            if (r2 == 0) goto L_0x001d
            if (r1 == r4) goto L_0x0012
            goto L_0x002c
        L_0x0012:
            kotlinx.atomicfu.AtomicRef<java.lang.Object> r2 = r0._state
            kotlinx.coroutines.Empty r3 = kotlinx.coroutines.JobSupportKt.EMPTY_ACTIVE
            boolean r1 = r2.compareAndSet(r1, r3)
            if (r1 == 0) goto L_0x0007
            goto L_0x002c
        L_0x001d:
            boolean r0 = r1 instanceof kotlinx.coroutines.Incomplete
            if (r0 == 0) goto L_0x002c
            kotlinx.coroutines.Incomplete r1 = (kotlinx.coroutines.Incomplete) r1
            kotlinx.coroutines.NodeList r0 = r1.getList()
            if (r0 == 0) goto L_0x002c
            r4.remove()
        L_0x002c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.JobNode.dispose():void");
    }
}
