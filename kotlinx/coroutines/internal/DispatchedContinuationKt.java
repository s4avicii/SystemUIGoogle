package kotlinx.coroutines.internal;

/* compiled from: DispatchedContinuation.kt */
public final class DispatchedContinuationKt {
    public static final Symbol REUSABLE_CLAIMED = new Symbol("REUSABLE_CLAIMED");
    public static final Symbol UNDEFINED = new Symbol("UNDEFINED");

    /* JADX WARNING: Code restructure failed: missing block: B:33:0x009b, code lost:
        if (r0.clearThreadContext() != false) goto L_0x009d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> void resumeCancellableWith(kotlin.coroutines.Continuation<? super T> r6, java.lang.Object r7, kotlin.jvm.functions.Function1<? super java.lang.Throwable, kotlin.Unit> r8) {
        /*
            boolean r0 = r6 instanceof kotlinx.coroutines.internal.DispatchedContinuation
            if (r0 == 0) goto L_0x00c1
            kotlinx.coroutines.internal.DispatchedContinuation r6 = (kotlinx.coroutines.internal.DispatchedContinuation) r6
            java.lang.Throwable r0 = kotlin.Result.m320exceptionOrNullimpl(r7)
            r1 = 0
            if (r0 != 0) goto L_0x0017
            if (r8 == 0) goto L_0x0015
            kotlinx.coroutines.CompletedWithCancellation r0 = new kotlinx.coroutines.CompletedWithCancellation
            r0.<init>(r7, r8)
            goto L_0x001d
        L_0x0015:
            r0 = r7
            goto L_0x001d
        L_0x0017:
            kotlinx.coroutines.CompletedExceptionally r8 = new kotlinx.coroutines.CompletedExceptionally
            r8.<init>(r0, r1)
            r0 = r8
        L_0x001d:
            kotlinx.coroutines.CoroutineDispatcher r8 = r6.dispatcher
            r6.getContext()
            boolean r8 = r8.isDispatchNeeded()
            r2 = 1
            if (r8 == 0) goto L_0x0038
            r6._state = r0
            r6.resumeMode = r2
            kotlinx.coroutines.CoroutineDispatcher r7 = r6.dispatcher
            kotlin.coroutines.CoroutineContext r8 = r6.getContext()
            r7.dispatch(r8, r6)
            goto L_0x00c4
        L_0x0038:
            boolean r8 = kotlinx.coroutines.DebugKt.DEBUG
            kotlinx.coroutines.EventLoop r8 = kotlinx.coroutines.ThreadLocalEventLoop.m129x4695df28()
            boolean r3 = r8.isUnconfinedLoopActive()
            if (r3 == 0) goto L_0x004d
            r6._state = r0
            r6.resumeMode = r2
            r8.dispatchUnconfined(r6)
            goto L_0x00c4
        L_0x004d:
            r8.incrementUseCount(r2)
            r3 = 0
            kotlin.coroutines.CoroutineContext r4 = r6.getContext()     // Catch:{ all -> 0x0076 }
            kotlinx.coroutines.Job$Key r5 = kotlinx.coroutines.Job.Key.$$INSTANCE     // Catch:{ all -> 0x0076 }
            kotlin.coroutines.CoroutineContext$Element r4 = r4.get(r5)     // Catch:{ all -> 0x0076 }
            kotlinx.coroutines.Job r4 = (kotlinx.coroutines.Job) r4     // Catch:{ all -> 0x0076 }
            if (r4 == 0) goto L_0x0078
            boolean r5 = r4.isActive()     // Catch:{ all -> 0x0076 }
            if (r5 != 0) goto L_0x0078
            java.util.concurrent.CancellationException r1 = r4.getCancellationException()     // Catch:{ all -> 0x0076 }
            r6.mo21191xd43a9d22(r0, r1)     // Catch:{ all -> 0x0076 }
            kotlin.Result$Failure r0 = new kotlin.Result$Failure     // Catch:{ all -> 0x0076 }
            r0.<init>(r1)     // Catch:{ all -> 0x0076 }
            r6.resumeWith(r0)     // Catch:{ all -> 0x0076 }
            r1 = r2
            goto L_0x0078
        L_0x0076:
            r7 = move-exception
            goto L_0x00b5
        L_0x0078:
            if (r1 != 0) goto L_0x00ae
            kotlin.coroutines.Continuation<T> r0 = r6.continuation     // Catch:{ all -> 0x0076 }
            java.lang.Object r1 = r6.countOrElement     // Catch:{ all -> 0x0076 }
            kotlin.coroutines.CoroutineContext r4 = r0.getContext()     // Catch:{ all -> 0x0076 }
            java.lang.Object r1 = kotlinx.coroutines.internal.ThreadContextKt.updateThreadContext(r4, r1)     // Catch:{ all -> 0x0076 }
            kotlinx.coroutines.internal.Symbol r5 = kotlinx.coroutines.internal.ThreadContextKt.NO_THREAD_ELEMENTS     // Catch:{ all -> 0x0076 }
            if (r1 == r5) goto L_0x008f
            kotlinx.coroutines.UndispatchedCoroutine r0 = kotlinx.coroutines.CoroutineContextKt.updateUndispatchedCompletion(r0, r4, r1)     // Catch:{ all -> 0x0076 }
            goto L_0x0090
        L_0x008f:
            r0 = r3
        L_0x0090:
            kotlin.coroutines.Continuation<T> r5 = r6.continuation     // Catch:{ all -> 0x00a1 }
            r5.resumeWith(r7)     // Catch:{ all -> 0x00a1 }
            if (r0 == 0) goto L_0x009d
            boolean r7 = r0.clearThreadContext()     // Catch:{ all -> 0x0076 }
            if (r7 == 0) goto L_0x00ae
        L_0x009d:
            kotlinx.coroutines.internal.ThreadContextKt.restoreThreadContext(r4, r1)     // Catch:{ all -> 0x0076 }
            goto L_0x00ae
        L_0x00a1:
            r7 = move-exception
            if (r0 == 0) goto L_0x00aa
            boolean r0 = r0.clearThreadContext()     // Catch:{ all -> 0x0076 }
            if (r0 == 0) goto L_0x00ad
        L_0x00aa:
            kotlinx.coroutines.internal.ThreadContextKt.restoreThreadContext(r4, r1)     // Catch:{ all -> 0x0076 }
        L_0x00ad:
            throw r7     // Catch:{ all -> 0x0076 }
        L_0x00ae:
            boolean r7 = r8.processUnconfinedEvent()     // Catch:{ all -> 0x0076 }
            if (r7 != 0) goto L_0x00ae
            goto L_0x00b8
        L_0x00b5:
            r6.handleFatalException(r7, r3)     // Catch:{ all -> 0x00bc }
        L_0x00b8:
            r8.decrementUseCount(r2)
            goto L_0x00c4
        L_0x00bc:
            r6 = move-exception
            r8.decrementUseCount(r2)
            throw r6
        L_0x00c1:
            r6.resumeWith(r7)
        L_0x00c4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.DispatchedContinuationKt.resumeCancellableWith(kotlin.coroutines.Continuation, java.lang.Object, kotlin.jvm.functions.Function1):void");
    }
}
