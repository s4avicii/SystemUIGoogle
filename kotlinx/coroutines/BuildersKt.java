package kotlinx.coroutines;

import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function2;

public final class BuildersKt {
    public static StandaloneCoroutine launch$default(CoroutineScope coroutineScope, MainCoroutineDispatcher mainCoroutineDispatcher, CoroutineStart coroutineStart, Function2 function2, int i) {
        CoroutineContext coroutineContext;
        boolean z;
        StandaloneCoroutine standaloneCoroutine;
        CoroutineContext coroutineContext2 = mainCoroutineDispatcher;
        if ((i & 1) != 0) {
            coroutineContext2 = EmptyCoroutineContext.INSTANCE;
        }
        if ((i & 2) != 0) {
            coroutineStart = CoroutineStart.DEFAULT;
        }
        boolean z2 = CoroutineContextKt.useCoroutinesScheduler;
        CoroutineContext plus = coroutineScope.getCoroutineContext().plus(coroutineContext2);
        if (DebugKt.DEBUG) {
            coroutineContext = plus.plus(new CoroutineId(DebugKt.COROUTINE_ID.incrementAndGet()));
        } else {
            coroutineContext = plus;
        }
        ExecutorCoroutineDispatcher executorCoroutineDispatcher = Dispatchers.Default;
        if (plus != executorCoroutineDispatcher && plus.get(ContinuationInterceptor.Key.$$INSTANCE) == null) {
            coroutineContext = coroutineContext.plus(executorCoroutineDispatcher);
        }
        if (coroutineStart == CoroutineStart.LAZY) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            standaloneCoroutine = new LazyStandaloneCoroutine(coroutineContext, function2);
        } else {
            standaloneCoroutine = new StandaloneCoroutine(coroutineContext, true);
        }
        standaloneCoroutine.start(coroutineStart, standaloneCoroutine, function2);
        return standaloneCoroutine;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: kotlinx.coroutines.CompletedExceptionally} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object runBlocking$default(kotlin.jvm.functions.Function2 r7) throws java.lang.InterruptedException {
        /*
            kotlin.coroutines.EmptyCoroutineContext r0 = kotlin.coroutines.EmptyCoroutineContext.INSTANCE
            java.lang.Thread r1 = java.lang.Thread.currentThread()
            kotlin.coroutines.ContinuationInterceptor$Key r2 = kotlin.coroutines.ContinuationInterceptor.Key.$$INSTANCE
            java.util.Objects.requireNonNull(r0)
            r3 = 0
            kotlinx.coroutines.EventLoop r4 = kotlinx.coroutines.ThreadLocalEventLoop.m129x4695df28()
            java.util.Objects.requireNonNull(r0)
            boolean r0 = kotlinx.coroutines.DebugKt.DEBUG
            if (r0 == 0) goto L_0x0027
            kotlinx.coroutines.CoroutineId r0 = new kotlinx.coroutines.CoroutineId
            java.util.concurrent.atomic.AtomicLong r5 = kotlinx.coroutines.DebugKt.COROUTINE_ID
            long r5 = r5.incrementAndGet()
            r0.<init>(r5)
            kotlin.coroutines.CoroutineContext r0 = r4.plus(r0)
            goto L_0x0028
        L_0x0027:
            r0 = r4
        L_0x0028:
            kotlinx.coroutines.ExecutorCoroutineDispatcher r5 = kotlinx.coroutines.Dispatchers.Default
            if (r4 == r5) goto L_0x0036
            kotlin.coroutines.CoroutineContext$Element r2 = r4.get(r2)
            if (r2 != 0) goto L_0x0036
            kotlin.coroutines.CoroutineContext r0 = r0.plus(r5)
        L_0x0036:
            kotlinx.coroutines.BlockingCoroutine r2 = new kotlinx.coroutines.BlockingCoroutine
            r2.<init>(r0, r1, r4)
            kotlinx.coroutines.CoroutineStart r0 = kotlinx.coroutines.CoroutineStart.DEFAULT
            r2.start(r0, r2, r7)
            kotlinx.coroutines.EventLoop r7 = r2.eventLoop     // Catch:{ all -> 0x00a2 }
            r0 = 0
            if (r7 != 0) goto L_0x0046
            goto L_0x004b
        L_0x0046:
            int r1 = kotlinx.coroutines.EventLoop.$r8$clinit     // Catch:{ all -> 0x00a2 }
            r7.incrementUseCount(r0)     // Catch:{ all -> 0x00a2 }
        L_0x004b:
            boolean r7 = java.lang.Thread.interrupted()     // Catch:{ all -> 0x008c }
            if (r7 != 0) goto L_0x008e
            kotlinx.coroutines.EventLoop r7 = r2.eventLoop     // Catch:{ all -> 0x008c }
            if (r7 != 0) goto L_0x005b
            r4 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            goto L_0x005f
        L_0x005b:
            long r4 = r7.processNextEvent()     // Catch:{ all -> 0x008c }
        L_0x005f:
            java.lang.Object r7 = r2.mo21281x8adbf455()     // Catch:{ all -> 0x008c }
            boolean r7 = r7 instanceof kotlinx.coroutines.Incomplete     // Catch:{ all -> 0x008c }
            r7 = r7 ^ 1
            if (r7 == 0) goto L_0x0088
            kotlinx.coroutines.EventLoop r7 = r2.eventLoop     // Catch:{ all -> 0x00a2 }
            if (r7 != 0) goto L_0x006e
            goto L_0x0073
        L_0x006e:
            int r1 = kotlinx.coroutines.EventLoop.$r8$clinit     // Catch:{ all -> 0x00a2 }
            r7.decrementUseCount(r0)     // Catch:{ all -> 0x00a2 }
        L_0x0073:
            java.lang.Object r7 = r2.mo21281x8adbf455()
            java.lang.Object r7 = kotlinx.coroutines.JobSupportKt.unboxState(r7)
            boolean r0 = r7 instanceof kotlinx.coroutines.CompletedExceptionally
            if (r0 == 0) goto L_0x0082
            r3 = r7
            kotlinx.coroutines.CompletedExceptionally r3 = (kotlinx.coroutines.CompletedExceptionally) r3
        L_0x0082:
            if (r3 != 0) goto L_0x0085
            return r7
        L_0x0085:
            java.lang.Throwable r7 = r3.cause
            throw r7
        L_0x0088:
            java.util.concurrent.locks.LockSupport.parkNanos(r2, r4)     // Catch:{ all -> 0x008c }
            goto L_0x004b
        L_0x008c:
            r7 = move-exception
            goto L_0x0097
        L_0x008e:
            java.lang.InterruptedException r7 = new java.lang.InterruptedException     // Catch:{ all -> 0x008c }
            r7.<init>()     // Catch:{ all -> 0x008c }
            r2.mo21272x7bd83b56(r7)     // Catch:{ all -> 0x008c }
            throw r7     // Catch:{ all -> 0x008c }
        L_0x0097:
            kotlinx.coroutines.EventLoop r1 = r2.eventLoop     // Catch:{ all -> 0x00a2 }
            if (r1 != 0) goto L_0x009c
            goto L_0x00a1
        L_0x009c:
            int r2 = kotlinx.coroutines.EventLoop.$r8$clinit     // Catch:{ all -> 0x00a2 }
            r1.decrementUseCount(r0)     // Catch:{ all -> 0x00a2 }
        L_0x00a1:
            throw r7     // Catch:{ all -> 0x00a2 }
        L_0x00a2:
            r7 = move-exception
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.BuildersKt.runBlocking$default(kotlin.jvm.functions.Function2):java.lang.Object");
    }
}
