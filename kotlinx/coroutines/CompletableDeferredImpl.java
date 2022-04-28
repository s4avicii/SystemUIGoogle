package kotlinx.coroutines;

/* compiled from: CompletableDeferred.kt */
public final class CompletableDeferredImpl<T> extends JobSupport implements CompletableDeferred<T> {
    public CompletableDeferredImpl(Job job) {
        super(true);
        initParentJob(job);
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x000e A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:4:0x0010  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean complete() {
        /*
            r4 = this;
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
        L_0x0002:
            java.lang.Object r1 = r4.mo21281x8adbf455()
            java.lang.Object r1 = r4.tryMakeCompleting(r1, r0)
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.JobSupportKt.COMPLETING_ALREADY
            if (r1 != r2) goto L_0x0010
            r4 = 0
            goto L_0x001c
        L_0x0010:
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.JobSupportKt.COMPLETING_WAITING_CHILDREN
            r3 = 1
            if (r1 != r2) goto L_0x0016
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.JobSupportKt.COMPLETING_RETRY
            if (r1 != r2) goto L_0x001b
            goto L_0x0002
        L_0x001b:
            r4 = r3
        L_0x001c:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.CompletableDeferredImpl.complete():boolean");
    }
}
