package kotlinx.coroutines;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import java.util.Objects;
import java.util.concurrent.locks.LockSupport;
import kotlin.coroutines.CoroutineContext;
import kotlinx.atomicfu.AtomicBoolean;
import kotlinx.atomicfu.AtomicLong;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.atomicfu.InterceptorKt;
import kotlinx.atomicfu.TraceBase;
import kotlinx.coroutines.internal.ArrayQueue;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;
import kotlinx.coroutines.internal.ThreadSafeHeap;
import kotlinx.coroutines.internal.ThreadSafeHeapNode;

/* compiled from: EventLoop.common.kt */
public abstract class EventLoopImplBase extends EventLoopImplPlatform {
    public final AtomicRef<DelayedTaskQueue> _delayed = new AtomicRef<>((Object) null);
    public final AtomicBoolean _isCompleted = new AtomicBoolean(false);
    public final AtomicRef<Object> _queue = new AtomicRef<>((Object) null);

    /* compiled from: EventLoop.common.kt */
    public static abstract class DelayedTask implements Runnable, Comparable<DelayedTask>, DisposableHandle, ThreadSafeHeapNode {
        public Object _heap;
        public int index;
        public long nanoTime;

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: kotlinx.coroutines.internal.ThreadSafeHeap} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final synchronized void dispose() {
            /*
                r5 = this;
                monitor-enter(r5)
                java.lang.Object r0 = r5._heap     // Catch:{ all -> 0x0033 }
                kotlinx.coroutines.internal.Symbol r1 = kotlinx.coroutines.EventLoop_commonKt.DISPOSED_TASK     // Catch:{ all -> 0x0033 }
                if (r0 != r1) goto L_0x0009
                monitor-exit(r5)
                return
            L_0x0009:
                boolean r2 = r0 instanceof kotlinx.coroutines.EventLoopImplBase.DelayedTaskQueue     // Catch:{ all -> 0x0033 }
                r3 = 0
                if (r2 == 0) goto L_0x0011
                kotlinx.coroutines.EventLoopImplBase$DelayedTaskQueue r0 = (kotlinx.coroutines.EventLoopImplBase.DelayedTaskQueue) r0     // Catch:{ all -> 0x0033 }
                goto L_0x0012
            L_0x0011:
                r0 = r3
            L_0x0012:
                if (r0 != 0) goto L_0x0015
                goto L_0x002d
            L_0x0015:
                monitor-enter(r0)     // Catch:{ all -> 0x0033 }
                java.lang.Object r2 = r5._heap     // Catch:{ all -> 0x0020 }
                boolean r4 = r2 instanceof kotlinx.coroutines.internal.ThreadSafeHeap     // Catch:{ all -> 0x0020 }
                if (r4 == 0) goto L_0x0022
                r3 = r2
                kotlinx.coroutines.internal.ThreadSafeHeap r3 = (kotlinx.coroutines.internal.ThreadSafeHeap) r3     // Catch:{ all -> 0x0020 }
                goto L_0x0022
            L_0x0020:
                r1 = move-exception
                goto L_0x0031
            L_0x0022:
                if (r3 != 0) goto L_0x0025
                goto L_0x002c
            L_0x0025:
                int r2 = r5.index     // Catch:{ all -> 0x0020 }
                boolean r3 = kotlinx.coroutines.DebugKt.DEBUG     // Catch:{ all -> 0x0020 }
                r0.removeAtImpl(r2)     // Catch:{ all -> 0x0020 }
            L_0x002c:
                monitor-exit(r0)     // Catch:{ all -> 0x0033 }
            L_0x002d:
                r5._heap = r1     // Catch:{ all -> 0x0033 }
                monitor-exit(r5)
                return
            L_0x0031:
                monitor-exit(r0)     // Catch:{ all -> 0x0033 }
                throw r1     // Catch:{ all -> 0x0033 }
            L_0x0033:
                r0 = move-exception
                monitor-exit(r5)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.EventLoopImplBase.DelayedTask.dispose():void");
        }

        public final int compareTo(Object obj) {
            int i = ((this.nanoTime - ((DelayedTask) obj).nanoTime) > 0 ? 1 : ((this.nanoTime - ((DelayedTask) obj).nanoTime) == 0 ? 0 : -1));
            if (i > 0) {
                return 1;
            }
            if (i < 0) {
                return -1;
            }
            return 0;
        }

        public final void setHeap(DelayedTaskQueue delayedTaskQueue) {
            boolean z;
            if (this._heap != EventLoop_commonKt.DISPOSED_TASK) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                this._heap = delayedTaskQueue;
                return;
            }
            throw new IllegalArgumentException("Failed requirement.".toString());
        }

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Delayed[nanos=");
            m.append(this.nanoTime);
            m.append(']');
            return m.toString();
        }

        public final void setIndex(int i) {
            this.index = i;
        }
    }

    /* compiled from: EventLoop.common.kt */
    public static final class DelayedTaskQueue extends ThreadSafeHeap<DelayedTask> {
        public long timeNow;

        public DelayedTaskQueue(long j) {
            this.timeNow = j;
        }
    }

    public final boolean enqueueImpl(Runnable runnable) {
        boolean z;
        AtomicRef<Object> atomicRef = this._queue;
        while (true) {
            Objects.requireNonNull(atomicRef);
            T t = atomicRef.value;
            AtomicBoolean atomicBoolean = this._isCompleted;
            Objects.requireNonNull(atomicBoolean);
            if (atomicBoolean._value != 0) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                return false;
            }
            if (t == null) {
                if (this._queue.compareAndSet(null, runnable)) {
                    return true;
                }
            } else if (t instanceof LockFreeTaskQueueCore) {
                LockFreeTaskQueueCore lockFreeTaskQueueCore = (LockFreeTaskQueueCore) t;
                int addLast = lockFreeTaskQueueCore.addLast(runnable);
                if (addLast == 0) {
                    return true;
                }
                if (addLast == 1) {
                    this._queue.compareAndSet(t, lockFreeTaskQueueCore.next());
                } else if (addLast == 2) {
                    return false;
                }
            } else if (t == EventLoop_commonKt.CLOSED_EMPTY) {
                return false;
            } else {
                LockFreeTaskQueueCore lockFreeTaskQueueCore2 = new LockFreeTaskQueueCore(8, true);
                lockFreeTaskQueueCore2.addLast((Runnable) t);
                lockFreeTaskQueueCore2.addLast(runnable);
                if (this._queue.compareAndSet(t, lockFreeTaskQueueCore2)) {
                    return true;
                }
            }
        }
    }

    public final boolean isEmpty() {
        boolean z;
        boolean z2;
        ArrayQueue<DispatchedTask<?>> arrayQueue = this.unconfinedQueue;
        if (arrayQueue == null || arrayQueue.head == arrayQueue.tail) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            return false;
        }
        AtomicRef<DelayedTaskQueue> atomicRef = this._delayed;
        Objects.requireNonNull(atomicRef);
        DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) atomicRef.value;
        if (delayedTaskQueue != null) {
            if (delayedTaskQueue.getSize() == 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (!z2) {
                return false;
            }
        }
        AtomicRef<Object> atomicRef2 = this._queue;
        Objects.requireNonNull(atomicRef2);
        T t = atomicRef2.value;
        if (t != null) {
            if (t instanceof LockFreeTaskQueueCore) {
                AtomicLong atomicLong = ((LockFreeTaskQueueCore) t)._state;
                Objects.requireNonNull(atomicLong);
                long j = atomicLong.value;
                if (((int) ((1073741823 & j) >> 0)) != ((int) ((j & 1152921503533105152L) >> 30))) {
                    return false;
                }
            } else if (t != EventLoop_commonKt.CLOSED_EMPTY) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:47:0x0083  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0099  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void schedule(long r13, kotlinx.coroutines.EventLoopImplBase.DelayedTask r15) {
        /*
            r12 = this;
            kotlinx.atomicfu.AtomicBoolean r0 = r12._isCompleted
            java.util.Objects.requireNonNull(r0)
            int r0 = r0._value
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x000d
            r0 = r1
            goto L_0x000e
        L_0x000d:
            r0 = r2
        L_0x000e:
            r3 = 2
            r4 = 0
            if (r0 == 0) goto L_0x0013
            goto L_0x0054
        L_0x0013:
            kotlinx.atomicfu.AtomicRef<kotlinx.coroutines.EventLoopImplBase$DelayedTaskQueue> r0 = r12._delayed
            java.util.Objects.requireNonNull(r0)
            T r0 = r0.value
            kotlinx.coroutines.EventLoopImplBase$DelayedTaskQueue r0 = (kotlinx.coroutines.EventLoopImplBase.DelayedTaskQueue) r0
            if (r0 != 0) goto L_0x0034
            kotlinx.atomicfu.AtomicRef<kotlinx.coroutines.EventLoopImplBase$DelayedTaskQueue> r0 = r12._delayed
            kotlinx.coroutines.EventLoopImplBase$DelayedTaskQueue r5 = new kotlinx.coroutines.EventLoopImplBase$DelayedTaskQueue
            r5.<init>(r13)
            r0.compareAndSet(r4, r5)
            kotlinx.atomicfu.AtomicRef<kotlinx.coroutines.EventLoopImplBase$DelayedTaskQueue> r0 = r12._delayed
            java.util.Objects.requireNonNull(r0)
            T r0 = r0.value
            kotlin.jvm.internal.Intrinsics.checkNotNull(r0)
            kotlinx.coroutines.EventLoopImplBase$DelayedTaskQueue r0 = (kotlinx.coroutines.EventLoopImplBase.DelayedTaskQueue) r0
        L_0x0034:
            monitor-enter(r15)
            java.lang.Object r5 = r15._heap     // Catch:{ all -> 0x00ca }
            kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.EventLoop_commonKt.DISPOSED_TASK     // Catch:{ all -> 0x00ca }
            if (r5 != r6) goto L_0x003e
            monitor-exit(r15)
            r0 = r3
            goto L_0x0081
        L_0x003e:
            monitor-enter(r0)     // Catch:{ all -> 0x00ca }
            T[] r5 = r0.f160a     // Catch:{ all -> 0x00c7 }
            if (r5 != 0) goto L_0x0045
            r5 = r4
            goto L_0x0047
        L_0x0045:
            r5 = r5[r2]     // Catch:{ all -> 0x00c7 }
        L_0x0047:
            kotlinx.coroutines.EventLoopImplBase$DelayedTask r5 = (kotlinx.coroutines.EventLoopImplBase.DelayedTask) r5     // Catch:{ all -> 0x00c7 }
            r6 = r12
            kotlinx.coroutines.DefaultExecutor r6 = (kotlinx.coroutines.DefaultExecutor) r6     // Catch:{ all -> 0x00c7 }
            boolean r6 = access$isCompleted(r6)     // Catch:{ all -> 0x00c7 }
            if (r6 == 0) goto L_0x0056
            monitor-exit(r0)     // Catch:{ all -> 0x00ca }
            monitor-exit(r15)
        L_0x0054:
            r0 = r1
            goto L_0x0081
        L_0x0056:
            r6 = 0
            if (r5 != 0) goto L_0x005d
            r0.timeNow = r13     // Catch:{ all -> 0x00c7 }
            goto L_0x0070
        L_0x005d:
            long r8 = r5.nanoTime     // Catch:{ all -> 0x00c7 }
            long r10 = r8 - r13
            int r5 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
            if (r5 < 0) goto L_0x0066
            r8 = r13
        L_0x0066:
            long r10 = r0.timeNow     // Catch:{ all -> 0x00c7 }
            long r10 = r8 - r10
            int r5 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
            if (r5 <= 0) goto L_0x0070
            r0.timeNow = r8     // Catch:{ all -> 0x00c7 }
        L_0x0070:
            long r8 = r15.nanoTime     // Catch:{ all -> 0x00c7 }
            long r10 = r0.timeNow     // Catch:{ all -> 0x00c7 }
            long r8 = r8 - r10
            int r5 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r5 >= 0) goto L_0x007b
            r15.nanoTime = r10     // Catch:{ all -> 0x00c7 }
        L_0x007b:
            r0.addImpl(r15)     // Catch:{ all -> 0x00c7 }
            monitor-exit(r0)     // Catch:{ all -> 0x00ca }
            monitor-exit(r15)
            r0 = r2
        L_0x0081:
            if (r0 == 0) goto L_0x0099
            if (r0 == r1) goto L_0x0095
            if (r0 != r3) goto L_0x0088
            goto L_0x00c3
        L_0x0088:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "unexpected result"
            java.lang.String r13 = r13.toString()
            r12.<init>(r13)
            throw r12
        L_0x0095:
            kotlinx.coroutines.EventLoopImplPlatform.reschedule(r13, r15)
            goto L_0x00c3
        L_0x0099:
            kotlinx.atomicfu.AtomicRef<kotlinx.coroutines.EventLoopImplBase$DelayedTaskQueue> r13 = r12._delayed
            java.util.Objects.requireNonNull(r13)
            T r13 = r13.value
            kotlinx.coroutines.EventLoopImplBase$DelayedTaskQueue r13 = (kotlinx.coroutines.EventLoopImplBase.DelayedTaskQueue) r13
            if (r13 != 0) goto L_0x00a5
            goto L_0x00b0
        L_0x00a5:
            monitor-enter(r13)
            T[] r14 = r13.f160a     // Catch:{ all -> 0x00c4 }
            if (r14 != 0) goto L_0x00ab
            goto L_0x00ad
        L_0x00ab:
            r4 = r14[r2]     // Catch:{ all -> 0x00c4 }
        L_0x00ad:
            monitor-exit(r13)
            kotlinx.coroutines.EventLoopImplBase$DelayedTask r4 = (kotlinx.coroutines.EventLoopImplBase.DelayedTask) r4
        L_0x00b0:
            if (r4 != r15) goto L_0x00b3
            goto L_0x00b4
        L_0x00b3:
            r1 = r2
        L_0x00b4:
            if (r1 == 0) goto L_0x00c3
            java.lang.Thread r12 = r12.getThread()
            java.lang.Thread r13 = java.lang.Thread.currentThread()
            if (r13 == r12) goto L_0x00c3
            java.util.concurrent.locks.LockSupport.unpark(r12)
        L_0x00c3:
            return
        L_0x00c4:
            r12 = move-exception
            monitor-exit(r13)
            throw r12
        L_0x00c7:
            r12 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00ca }
            throw r12     // Catch:{ all -> 0x00ca }
        L_0x00ca:
            r12 = move-exception
            monitor-exit(r15)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.EventLoopImplBase.schedule(long, kotlinx.coroutines.EventLoopImplBase$DelayedTask):void");
    }

    public final void shutdown() {
        DelayedTask delayedTask;
        ThreadSafeHeapNode threadSafeHeapNode;
        ThreadLocalEventLoop.ref.set((Object) null);
        AtomicBoolean atomicBoolean = this._isCompleted;
        Objects.requireNonNull(atomicBoolean);
        int i = InterceptorKt.$r8$clinit;
        atomicBoolean._value = 1;
        TraceBase traceBase = atomicBoolean.trace;
        if (traceBase != TraceBase.None.INSTANCE) {
            Objects.requireNonNull(traceBase);
        }
        boolean z = DebugKt.DEBUG;
        AtomicRef<Object> atomicRef = this._queue;
        while (true) {
            Objects.requireNonNull(atomicRef);
            T t = atomicRef.value;
            if (t == null) {
                if (this._queue.compareAndSet(null, EventLoop_commonKt.CLOSED_EMPTY)) {
                    break;
                }
            } else if (t instanceof LockFreeTaskQueueCore) {
                ((LockFreeTaskQueueCore) t).close();
                break;
            } else if (t == EventLoop_commonKt.CLOSED_EMPTY) {
                break;
            } else {
                LockFreeTaskQueueCore lockFreeTaskQueueCore = new LockFreeTaskQueueCore(8, true);
                lockFreeTaskQueueCore.addLast((Runnable) t);
                if (this._queue.compareAndSet(t, lockFreeTaskQueueCore)) {
                    break;
                }
            }
        }
        do {
        } while (processNextEvent() <= 0);
        long nanoTime = System.nanoTime();
        while (true) {
            AtomicRef<DelayedTaskQueue> atomicRef2 = this._delayed;
            Objects.requireNonNull(atomicRef2);
            DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) atomicRef2.value;
            if (delayedTaskQueue == null) {
                delayedTask = null;
            } else {
                synchronized (delayedTaskQueue) {
                    if (delayedTaskQueue.getSize() > 0) {
                        threadSafeHeapNode = delayedTaskQueue.removeAtImpl(0);
                    } else {
                        threadSafeHeapNode = null;
                    }
                }
                delayedTask = (DelayedTask) threadSafeHeapNode;
            }
            if (delayedTask != null) {
                EventLoopImplPlatform.reschedule(nanoTime, delayedTask);
            } else {
                return;
            }
        }
    }

    public static final boolean access$isCompleted(DefaultExecutor defaultExecutor) {
        Objects.requireNonNull(defaultExecutor);
        AtomicBoolean atomicBoolean = defaultExecutor._isCompleted;
        Objects.requireNonNull(atomicBoolean);
        if (atomicBoolean._value != 0) {
            return true;
        }
        return false;
    }

    public final void enqueue(Runnable runnable) {
        if (enqueueImpl(runnable)) {
            Thread thread = getThread();
            if (Thread.currentThread() != thread) {
                LockSupport.unpark(thread);
                return;
            }
            return;
        }
        DefaultExecutor.INSTANCE.enqueue(runnable);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0084, code lost:
        r8 = null;
     */
    /* JADX WARNING: Removed duplicated region for block: B:105:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x00b5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final long processNextEvent() {
        /*
            r12 = this;
            boolean r0 = r12.processUnconfinedEvent()
            r1 = 0
            if (r0 == 0) goto L_0x0009
            return r1
        L_0x0009:
            kotlinx.atomicfu.AtomicRef<kotlinx.coroutines.EventLoopImplBase$DelayedTaskQueue> r0 = r12._delayed
            java.util.Objects.requireNonNull(r0)
            T r0 = r0.value
            kotlinx.coroutines.EventLoopImplBase$DelayedTaskQueue r0 = (kotlinx.coroutines.EventLoopImplBase.DelayedTaskQueue) r0
            r3 = 0
            r4 = 1
            r5 = 0
            if (r0 == 0) goto L_0x005a
            int r6 = r0.getSize()
            if (r6 != 0) goto L_0x001f
            r6 = r4
            goto L_0x0020
        L_0x001f:
            r6 = r5
        L_0x0020:
            if (r6 != 0) goto L_0x005a
            long r6 = java.lang.System.nanoTime()
        L_0x0026:
            monitor-enter(r0)
            T[] r8 = r0.f160a     // Catch:{ all -> 0x0057 }
            if (r8 != 0) goto L_0x002d
            r8 = r3
            goto L_0x002f
        L_0x002d:
            r8 = r8[r5]     // Catch:{ all -> 0x0057 }
        L_0x002f:
            if (r8 != 0) goto L_0x0034
            monitor-exit(r0)
            r8 = r3
            goto L_0x0052
        L_0x0034:
            kotlinx.coroutines.EventLoopImplBase$DelayedTask r8 = (kotlinx.coroutines.EventLoopImplBase.DelayedTask) r8     // Catch:{ all -> 0x0057 }
            long r9 = r8.nanoTime     // Catch:{ all -> 0x0057 }
            long r9 = r6 - r9
            int r9 = (r9 > r1 ? 1 : (r9 == r1 ? 0 : -1))
            if (r9 < 0) goto L_0x0040
            r9 = r4
            goto L_0x0041
        L_0x0040:
            r9 = r5
        L_0x0041:
            if (r9 == 0) goto L_0x0048
            boolean r8 = r12.enqueueImpl(r8)     // Catch:{ all -> 0x0057 }
            goto L_0x0049
        L_0x0048:
            r8 = r5
        L_0x0049:
            if (r8 == 0) goto L_0x0050
            kotlinx.coroutines.internal.ThreadSafeHeapNode r8 = r0.removeAtImpl(r5)     // Catch:{ all -> 0x0057 }
            goto L_0x0051
        L_0x0050:
            r8 = r3
        L_0x0051:
            monitor-exit(r0)
        L_0x0052:
            kotlinx.coroutines.EventLoopImplBase$DelayedTask r8 = (kotlinx.coroutines.EventLoopImplBase.DelayedTask) r8
            if (r8 != 0) goto L_0x0026
            goto L_0x005a
        L_0x0057:
            r12 = move-exception
            monitor-exit(r0)
            throw r12
        L_0x005a:
            kotlinx.atomicfu.AtomicRef<java.lang.Object> r0 = r12._queue
        L_0x005c:
            java.util.Objects.requireNonNull(r0)
            T r6 = r0.value
            if (r6 != 0) goto L_0x0064
            goto L_0x0084
        L_0x0064:
            boolean r7 = r6 instanceof kotlinx.coroutines.internal.LockFreeTaskQueueCore
            if (r7 == 0) goto L_0x0080
            r7 = r6
            kotlinx.coroutines.internal.LockFreeTaskQueueCore r7 = (kotlinx.coroutines.internal.LockFreeTaskQueueCore) r7
            java.lang.Object r8 = r7.removeFirstOrNull()
            kotlinx.coroutines.internal.Symbol r9 = kotlinx.coroutines.internal.LockFreeTaskQueueCore.REMOVE_FROZEN
            if (r8 == r9) goto L_0x0076
            java.lang.Runnable r8 = (java.lang.Runnable) r8
            goto L_0x0091
        L_0x0076:
            kotlinx.atomicfu.AtomicRef<java.lang.Object> r8 = r12._queue
            kotlinx.coroutines.internal.LockFreeTaskQueueCore r7 = r7.next()
            r8.compareAndSet(r6, r7)
            goto L_0x005c
        L_0x0080:
            kotlinx.coroutines.internal.Symbol r7 = kotlinx.coroutines.EventLoop_commonKt.CLOSED_EMPTY
            if (r6 != r7) goto L_0x0086
        L_0x0084:
            r8 = r3
            goto L_0x0091
        L_0x0086:
            kotlinx.atomicfu.AtomicRef<java.lang.Object> r7 = r12._queue
            boolean r7 = r7.compareAndSet(r6, r3)
            if (r7 == 0) goto L_0x005c
            r8 = r6
            java.lang.Runnable r8 = (java.lang.Runnable) r8
        L_0x0091:
            if (r8 == 0) goto L_0x0097
            r8.run()
            return r1
        L_0x0097:
            kotlinx.coroutines.internal.ArrayQueue<kotlinx.coroutines.DispatchedTask<?>> r0 = r12.unconfinedQueue
            r6 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            if (r0 != 0) goto L_0x00a1
            goto L_0x00ac
        L_0x00a1:
            int r8 = r0.head
            int r0 = r0.tail
            if (r8 != r0) goto L_0x00a9
            r0 = r4
            goto L_0x00aa
        L_0x00a9:
            r0 = r5
        L_0x00aa:
            if (r0 == 0) goto L_0x00ae
        L_0x00ac:
            r8 = r6
            goto L_0x00af
        L_0x00ae:
            r8 = r1
        L_0x00af:
            int r0 = (r8 > r1 ? 1 : (r8 == r1 ? 0 : -1))
            if (r0 != 0) goto L_0x00b5
            goto L_0x0113
        L_0x00b5:
            kotlinx.atomicfu.AtomicRef<java.lang.Object> r0 = r12._queue
            java.util.Objects.requireNonNull(r0)
            T r0 = r0.value
            if (r0 != 0) goto L_0x00bf
            goto L_0x00e3
        L_0x00bf:
            boolean r8 = r0 instanceof kotlinx.coroutines.internal.LockFreeTaskQueueCore
            if (r8 == 0) goto L_0x010e
            kotlinx.coroutines.internal.LockFreeTaskQueueCore r0 = (kotlinx.coroutines.internal.LockFreeTaskQueueCore) r0
            kotlinx.atomicfu.AtomicLong r0 = r0._state
            java.util.Objects.requireNonNull(r0)
            long r8 = r0.value
            r10 = 1073741823(0x3fffffff, double:5.304989472E-315)
            long r10 = r10 & r8
            long r10 = r10 >> r5
            int r0 = (int) r10
            r10 = 1152921503533105152(0xfffffffc0000000, double:1.2882296003504729E-231)
            long r8 = r8 & r10
            r10 = 30
            long r8 = r8 >> r10
            int r8 = (int) r8
            if (r0 != r8) goto L_0x00df
            goto L_0x00e0
        L_0x00df:
            r4 = r5
        L_0x00e0:
            if (r4 != 0) goto L_0x00e3
            goto L_0x0113
        L_0x00e3:
            kotlinx.atomicfu.AtomicRef<kotlinx.coroutines.EventLoopImplBase$DelayedTaskQueue> r12 = r12._delayed
            java.util.Objects.requireNonNull(r12)
            T r12 = r12.value
            kotlinx.coroutines.EventLoopImplBase$DelayedTaskQueue r12 = (kotlinx.coroutines.EventLoopImplBase.DelayedTaskQueue) r12
            if (r12 != 0) goto L_0x00ef
            goto L_0x00fa
        L_0x00ef:
            monitor-enter(r12)
            T[] r0 = r12.f160a     // Catch:{ all -> 0x010b }
            if (r0 != 0) goto L_0x00f5
            goto L_0x00f7
        L_0x00f5:
            r3 = r0[r5]     // Catch:{ all -> 0x010b }
        L_0x00f7:
            monitor-exit(r12)
            kotlinx.coroutines.EventLoopImplBase$DelayedTask r3 = (kotlinx.coroutines.EventLoopImplBase.DelayedTask) r3
        L_0x00fa:
            if (r3 != 0) goto L_0x00fd
            goto L_0x0112
        L_0x00fd:
            long r3 = r3.nanoTime
            long r5 = java.lang.System.nanoTime()
            long r3 = r3 - r5
            int r12 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r12 >= 0) goto L_0x0109
            goto L_0x0113
        L_0x0109:
            r1 = r3
            goto L_0x0113
        L_0x010b:
            r0 = move-exception
            monitor-exit(r12)
            throw r0
        L_0x010e:
            kotlinx.coroutines.internal.Symbol r12 = kotlinx.coroutines.EventLoop_commonKt.CLOSED_EMPTY
            if (r0 != r12) goto L_0x0113
        L_0x0112:
            r1 = r6
        L_0x0113:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.EventLoopImplBase.processNextEvent():long");
    }

    public final void dispatch(CoroutineContext coroutineContext, Runnable runnable) {
        enqueue(runnable);
    }
}
