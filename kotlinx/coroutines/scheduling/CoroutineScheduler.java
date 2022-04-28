package kotlinx.coroutines.scheduling;

import androidx.coordinatorlayout.R$styleable;
import androidx.exifinterface.media.C0155xe8491b12;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.LockSupport;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlinx.atomicfu.AtomicBoolean;
import kotlinx.atomicfu.AtomicInt;
import kotlinx.atomicfu.AtomicLong;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.atomicfu.InterceptorKt;
import kotlinx.atomicfu.TraceBase;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: CoroutineScheduler.kt */
public final class CoroutineScheduler implements Executor, Closeable {
    public static final Symbol NOT_IN_STACK = new Symbol("NOT_IN_STACK");
    public final AtomicBoolean _isTerminated;
    public final AtomicLong controlState;
    public final int corePoolSize;
    public final GlobalQueue globalBlockingQueue;
    public final GlobalQueue globalCpuQueue;
    public final long idleWorkerKeepAliveNs;
    public final int maxPoolSize;
    public final AtomicLong parkedWorkersStack;
    public final String schedulerName;
    public final AtomicReferenceArray<Worker> workers;

    /* compiled from: CoroutineScheduler.kt */
    public final class Worker extends Thread {
        public volatile int indexInArray;
        public final WorkQueue localQueue;
        public boolean mayHaveLocalTasks;
        public long minDelayUntilStealableTaskNs;
        public volatile Object nextParkedWorker;
        public int rngState;
        public WorkerState state;
        public long terminationDeadline;
        public final AtomicInt workerCtl;

        public Worker() {
            throw null;
        }

        public Worker(int i) {
            CoroutineScheduler.this = CoroutineScheduler.this;
            setDaemon(true);
            this.localQueue = new WorkQueue();
            this.state = WorkerState.DORMANT;
            this.workerCtl = new AtomicInt();
            this.nextParkedWorker = CoroutineScheduler.NOT_IN_STACK;
            this.rngState = Random.Default.nextInt();
            setIndexInArray(i);
        }

        public final Task pollGlobalQueues() {
            if (nextInt(2) == 0) {
                Task task = (Task) CoroutineScheduler.this.globalCpuQueue.removeFirstOrNull();
                if (task == null) {
                    return (Task) CoroutineScheduler.this.globalBlockingQueue.removeFirstOrNull();
                }
                return task;
            }
            Task task2 = (Task) CoroutineScheduler.this.globalBlockingQueue.removeFirstOrNull();
            if (task2 == null) {
                return (Task) CoroutineScheduler.this.globalCpuQueue.removeFirstOrNull();
            }
            return task2;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v3, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: kotlinx.coroutines.scheduling.Task} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v7, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v10, resolved type: kotlinx.coroutines.scheduling.Task} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* JADX WARNING: Removed duplicated region for block: B:15:0x003a  */
        /* JADX WARNING: Removed duplicated region for block: B:33:0x007d  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final kotlinx.coroutines.scheduling.Task findTask(boolean r11) {
            /*
                r10 = this;
                kotlinx.coroutines.scheduling.CoroutineScheduler$WorkerState r0 = kotlinx.coroutines.scheduling.CoroutineScheduler.WorkerState.CPU_ACQUIRED
                kotlinx.coroutines.scheduling.CoroutineScheduler$WorkerState r1 = r10.state
                r2 = 0
                r3 = 1
                if (r1 != r0) goto L_0x0009
                goto L_0x0034
            L_0x0009:
                kotlinx.coroutines.scheduling.CoroutineScheduler r1 = kotlinx.coroutines.scheduling.CoroutineScheduler.this
                kotlinx.atomicfu.AtomicLong r4 = r1.controlState
            L_0x000d:
                java.util.Objects.requireNonNull(r4)
                long r5 = r4.value
                r7 = 9223367638808264704(0x7ffffc0000000000, double:NaN)
                long r7 = r7 & r5
                r9 = 42
                long r7 = r7 >> r9
                int r7 = (int) r7
                if (r7 != 0) goto L_0x0020
                r1 = r2
                goto L_0x0030
            L_0x0020:
                r7 = 4398046511104(0x40000000000, double:2.1729236899484E-311)
                long r7 = r5 - r7
                kotlinx.atomicfu.AtomicLong r9 = r1.controlState
                boolean r5 = r9.compareAndSet(r5, r7)
                if (r5 == 0) goto L_0x000d
                r1 = r3
            L_0x0030:
                if (r1 == 0) goto L_0x0036
                r10.state = r0
            L_0x0034:
                r0 = r3
                goto L_0x0037
            L_0x0036:
                r0 = r2
            L_0x0037:
                r1 = 0
                if (r0 == 0) goto L_0x007d
                if (r11 == 0) goto L_0x0072
                kotlinx.coroutines.scheduling.CoroutineScheduler r11 = kotlinx.coroutines.scheduling.CoroutineScheduler.this
                int r11 = r11.corePoolSize
                int r11 = r11 * 2
                int r11 = r10.nextInt(r11)
                if (r11 != 0) goto L_0x0049
                goto L_0x004a
            L_0x0049:
                r3 = r2
            L_0x004a:
                if (r3 == 0) goto L_0x0052
                kotlinx.coroutines.scheduling.Task r11 = r10.pollGlobalQueues()
                if (r11 != 0) goto L_0x007c
            L_0x0052:
                kotlinx.coroutines.scheduling.WorkQueue r11 = r10.localQueue
                java.util.Objects.requireNonNull(r11)
                kotlinx.atomicfu.AtomicRef<kotlinx.coroutines.scheduling.Task> r0 = r11.lastScheduledTask
                java.lang.Object r0 = r0.getAndSet(r1)
                kotlinx.coroutines.scheduling.Task r0 = (kotlinx.coroutines.scheduling.Task) r0
                if (r0 != 0) goto L_0x0066
                kotlinx.coroutines.scheduling.Task r11 = r11.pollBuffer()
                goto L_0x0067
            L_0x0066:
                r11 = r0
            L_0x0067:
                if (r11 != 0) goto L_0x007c
                if (r3 != 0) goto L_0x0078
                kotlinx.coroutines.scheduling.Task r11 = r10.pollGlobalQueues()
                if (r11 != 0) goto L_0x007c
                goto L_0x0078
            L_0x0072:
                kotlinx.coroutines.scheduling.Task r11 = r10.pollGlobalQueues()
                if (r11 != 0) goto L_0x007c
            L_0x0078:
                kotlinx.coroutines.scheduling.Task r11 = r10.trySteal(r2)
            L_0x007c:
                return r11
            L_0x007d:
                if (r11 == 0) goto L_0x00a0
                kotlinx.coroutines.scheduling.WorkQueue r11 = r10.localQueue
                java.util.Objects.requireNonNull(r11)
                kotlinx.atomicfu.AtomicRef<kotlinx.coroutines.scheduling.Task> r0 = r11.lastScheduledTask
                java.lang.Object r0 = r0.getAndSet(r1)
                kotlinx.coroutines.scheduling.Task r0 = (kotlinx.coroutines.scheduling.Task) r0
                if (r0 != 0) goto L_0x0092
                kotlinx.coroutines.scheduling.Task r0 = r11.pollBuffer()
            L_0x0092:
                if (r0 != 0) goto L_0x00ab
                kotlinx.coroutines.scheduling.CoroutineScheduler r11 = kotlinx.coroutines.scheduling.CoroutineScheduler.this
                kotlinx.coroutines.scheduling.GlobalQueue r11 = r11.globalBlockingQueue
                java.lang.Object r11 = r11.removeFirstOrNull()
                r0 = r11
                kotlinx.coroutines.scheduling.Task r0 = (kotlinx.coroutines.scheduling.Task) r0
                goto L_0x00ab
            L_0x00a0:
                kotlinx.coroutines.scheduling.CoroutineScheduler r11 = kotlinx.coroutines.scheduling.CoroutineScheduler.this
                kotlinx.coroutines.scheduling.GlobalQueue r11 = r11.globalBlockingQueue
                java.lang.Object r11 = r11.removeFirstOrNull()
                r0 = r11
                kotlinx.coroutines.scheduling.Task r0 = (kotlinx.coroutines.scheduling.Task) r0
            L_0x00ab:
                if (r0 != 0) goto L_0x00b1
                kotlinx.coroutines.scheduling.Task r0 = r10.trySteal(r3)
            L_0x00b1:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.scheduling.CoroutineScheduler.Worker.findTask(boolean):kotlinx.coroutines.scheduling.Task");
        }

        public final int nextInt(int i) {
            int i2 = this.rngState;
            int i3 = i2 ^ (i2 << 13);
            int i4 = i3 ^ (i3 >> 17);
            int i5 = i4 ^ (i4 << 5);
            this.rngState = i5;
            int i6 = i - 1;
            if ((i6 & i) == 0) {
                return i6 & i5;
            }
            return (Integer.MAX_VALUE & i5) % i;
        }

        public final void run() {
            boolean z;
            boolean z2;
            boolean z3;
            boolean z4;
            boolean z5;
            boolean z6;
            boolean z7;
            WorkerState workerState = WorkerState.PARKING;
            WorkerState workerState2 = WorkerState.TERMINATED;
            loop0:
            while (true) {
                boolean z8 = true;
                boolean z9 = false;
                boolean z10 = false;
                while (true) {
                    CoroutineScheduler coroutineScheduler = CoroutineScheduler.this;
                    Objects.requireNonNull(coroutineScheduler);
                    AtomicBoolean atomicBoolean = coroutineScheduler._isTerminated;
                    Objects.requireNonNull(atomicBoolean);
                    if (atomicBoolean._value != 0) {
                        z = z8;
                    } else {
                        z = z9;
                    }
                    if (z || this.state == workerState2) {
                        tryReleaseCpu(workerState2);
                    } else {
                        Task findTask = findTask(this.mayHaveLocalTasks);
                        long j = -2097152;
                        if (findTask != null) {
                            this.minDelayUntilStealableTaskNs = 0;
                            WorkerState workerState3 = WorkerState.BLOCKING;
                            int taskMode = findTask.taskContext.getTaskMode();
                            this.terminationDeadline = 0;
                            if (this.state == workerState) {
                                boolean z11 = DebugKt.DEBUG;
                                this.state = workerState3;
                            }
                            if (taskMode != 0 && tryReleaseCpu(workerState3)) {
                                CoroutineScheduler coroutineScheduler2 = CoroutineScheduler.this;
                                Objects.requireNonNull(coroutineScheduler2);
                                if (!coroutineScheduler2.tryUnpark()) {
                                    AtomicLong atomicLong = coroutineScheduler2.controlState;
                                    Objects.requireNonNull(atomicLong);
                                    if (!coroutineScheduler2.tryCreateWorker(atomicLong.value)) {
                                        coroutineScheduler2.tryUnpark();
                                    }
                                }
                            }
                            Objects.requireNonNull(CoroutineScheduler.this);
                            try {
                                findTask.run();
                            } catch (Throwable th) {
                                Throwable th2 = th;
                                Thread currentThread = Thread.currentThread();
                                currentThread.getUncaughtExceptionHandler().uncaughtException(currentThread, th2);
                            }
                            if (taskMode != 0) {
                                CoroutineScheduler.this.controlState.addAndGet(-2097152);
                                if (this.state != workerState2) {
                                    boolean z12 = DebugKt.DEBUG;
                                    this.state = WorkerState.DORMANT;
                                }
                            }
                        } else {
                            this.mayHaveLocalTasks = z9;
                            if (this.minDelayUntilStealableTaskNs != 0) {
                                if (z10) {
                                    tryReleaseCpu(workerState);
                                    Thread.interrupted();
                                    LockSupport.parkNanos(this.minDelayUntilStealableTaskNs);
                                    this.minDelayUntilStealableTaskNs = 0;
                                    break;
                                }
                                z10 = z8;
                            } else {
                                Object obj = this.nextParkedWorker;
                                Symbol symbol = CoroutineScheduler.NOT_IN_STACK;
                                if (obj != symbol) {
                                    z2 = z8;
                                } else {
                                    z2 = z9;
                                }
                                if (z2) {
                                    z3 = z10;
                                    boolean z13 = DebugKt.DEBUG;
                                    int i = -1;
                                    this.workerCtl.setValue(-1);
                                    while (true) {
                                        if (this.nextParkedWorker != CoroutineScheduler.NOT_IN_STACK) {
                                            z5 = z8;
                                        } else {
                                            z5 = false;
                                        }
                                        if (!z5) {
                                            break;
                                        }
                                        AtomicInt atomicInt = this.workerCtl;
                                        Objects.requireNonNull(atomicInt);
                                        if (atomicInt.value != i) {
                                            break;
                                        }
                                        CoroutineScheduler coroutineScheduler3 = CoroutineScheduler.this;
                                        Objects.requireNonNull(coroutineScheduler3);
                                        AtomicBoolean atomicBoolean2 = coroutineScheduler3._isTerminated;
                                        Objects.requireNonNull(atomicBoolean2);
                                        if (atomicBoolean2._value != 0) {
                                            z6 = z8;
                                        } else {
                                            z6 = false;
                                        }
                                        if (z6 || this.state == workerState2) {
                                            break;
                                        }
                                        tryReleaseCpu(workerState);
                                        Thread.interrupted();
                                        if (this.terminationDeadline == 0) {
                                            this.terminationDeadline = System.nanoTime() + CoroutineScheduler.this.idleWorkerKeepAliveNs;
                                        }
                                        LockSupport.parkNanos(CoroutineScheduler.this.idleWorkerKeepAliveNs);
                                        if (System.nanoTime() - this.terminationDeadline >= 0) {
                                            this.terminationDeadline = 0;
                                            CoroutineScheduler coroutineScheduler4 = CoroutineScheduler.this;
                                            synchronized (coroutineScheduler4.workers) {
                                                AtomicBoolean atomicBoolean3 = coroutineScheduler4._isTerminated;
                                                Objects.requireNonNull(atomicBoolean3);
                                                if (atomicBoolean3._value != 0) {
                                                    z7 = z8;
                                                } else {
                                                    z7 = false;
                                                }
                                                if (!z7) {
                                                    AtomicLong atomicLong2 = coroutineScheduler4.controlState;
                                                    Objects.requireNonNull(atomicLong2);
                                                    if (((int) (atomicLong2.value & 2097151)) > coroutineScheduler4.corePoolSize) {
                                                        if (this.workerCtl.compareAndSet(i, z8 ? 1 : 0)) {
                                                            int i2 = this.indexInArray;
                                                            setIndexInArray(0);
                                                            coroutineScheduler4.parkedWorkersStackTopUpdate(this, i2, 0);
                                                            AtomicLong atomicLong3 = coroutineScheduler4.controlState;
                                                            Objects.requireNonNull(atomicLong3);
                                                            int i3 = InterceptorKt.$r8$clinit;
                                                            long andDecrement = AtomicLong.f154FU.getAndDecrement(atomicLong3);
                                                            TraceBase traceBase = atomicLong3.trace;
                                                            if (traceBase != TraceBase.None.INSTANCE) {
                                                                Intrinsics.stringPlus("getAndDec():", Long.valueOf(andDecrement));
                                                                Objects.requireNonNull(traceBase);
                                                            }
                                                            int i4 = (int) (andDecrement & 2097151);
                                                            if (i4 != i2) {
                                                                Worker worker = coroutineScheduler4.workers.get(i4);
                                                                Intrinsics.checkNotNull(worker);
                                                                Worker worker2 = worker;
                                                                coroutineScheduler4.workers.set(i2, worker2);
                                                                worker2.setIndexInArray(i2);
                                                                coroutineScheduler4.parkedWorkersStackTopUpdate(worker2, i4, i2);
                                                            }
                                                            coroutineScheduler4.workers.set(i4, (Object) null);
                                                            this.state = workerState2;
                                                        }
                                                    }
                                                }
                                            }
                                            z8 = true;
                                            i = -1;
                                        }
                                        z8 = true;
                                        i = -1;
                                    }
                                } else {
                                    CoroutineScheduler coroutineScheduler5 = CoroutineScheduler.this;
                                    Objects.requireNonNull(coroutineScheduler5);
                                    if (this.nextParkedWorker != symbol) {
                                        z4 = z9;
                                        z3 = z10;
                                        z9 = z4;
                                        z10 = z3;
                                        z8 = true;
                                    } else {
                                        AtomicLong atomicLong4 = coroutineScheduler5.parkedWorkersStack;
                                        while (true) {
                                            Objects.requireNonNull(atomicLong4);
                                            long j2 = atomicLong4.value;
                                            long j3 = (2097152 + j2) & j;
                                            int i5 = this.indexInArray;
                                            boolean z14 = DebugKt.DEBUG;
                                            this.nextParkedWorker = coroutineScheduler5.workers.get((int) (j2 & 2097151));
                                            z3 = z10;
                                            if (coroutineScheduler5.parkedWorkersStack.compareAndSet(j2, ((long) i5) | j3)) {
                                                break;
                                            }
                                            z10 = z3;
                                            j = -2097152;
                                        }
                                    }
                                }
                                z4 = false;
                                z9 = z4;
                                z10 = z3;
                                z8 = true;
                            }
                        }
                    }
                }
            }
            tryReleaseCpu(workerState2);
            return;
        }

        public final void setIndexInArray(int i) {
            String str;
            StringBuilder sb = new StringBuilder();
            sb.append(CoroutineScheduler.this.schedulerName);
            sb.append("-worker-");
            if (i == 0) {
                str = "TERMINATED";
            } else {
                str = String.valueOf(i);
            }
            sb.append(str);
            setName(sb.toString());
            this.indexInArray = i;
        }

        public final boolean tryReleaseCpu(WorkerState workerState) {
            boolean z;
            WorkerState workerState2 = this.state;
            if (workerState2 == WorkerState.CPU_ACQUIRED) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                CoroutineScheduler.this.controlState.addAndGet(4398046511104L);
            }
            if (workerState2 != workerState) {
                this.state = workerState;
            }
            return z;
        }

        public final Task trySteal(boolean z) {
            long j;
            boolean z2;
            boolean z3 = DebugKt.DEBUG;
            AtomicLong atomicLong = CoroutineScheduler.this.controlState;
            Objects.requireNonNull(atomicLong);
            int i = (int) (atomicLong.value & 2097151);
            if (i < 2) {
                return null;
            }
            int nextInt = nextInt(i);
            CoroutineScheduler coroutineScheduler = CoroutineScheduler.this;
            int i2 = 0;
            long j2 = Long.MAX_VALUE;
            while (i2 < i) {
                i2++;
                nextInt++;
                if (nextInt > i) {
                    nextInt = 1;
                }
                Worker worker = coroutineScheduler.workers.get(nextInt);
                if (worker != null && worker != this) {
                    boolean z4 = DebugKt.DEBUG;
                    if (z) {
                        WorkQueue workQueue = this.localQueue;
                        WorkQueue workQueue2 = worker.localQueue;
                        Objects.requireNonNull(workQueue);
                        AtomicInt atomicInt = workQueue2.consumerIndex;
                        Objects.requireNonNull(atomicInt);
                        int i3 = atomicInt.value;
                        AtomicInt atomicInt2 = workQueue2.producerIndex;
                        Objects.requireNonNull(atomicInt2);
                        int i4 = atomicInt2.value;
                        AtomicReferenceArray<Task> atomicReferenceArray = workQueue2.buffer;
                        while (true) {
                            if (i3 == i4) {
                                break;
                            }
                            int i5 = i3 & 127;
                            AtomicInt atomicInt3 = workQueue2.blockingTasksInBuffer;
                            Objects.requireNonNull(atomicInt3);
                            if (atomicInt3.value == 0) {
                                break;
                            }
                            Task task = atomicReferenceArray.get(i5);
                            if (task != null) {
                                if (task.taskContext.getTaskMode() == 1) {
                                    z2 = true;
                                } else {
                                    z2 = false;
                                }
                                if (z2 && atomicReferenceArray.compareAndSet(i5, task, (Object) null)) {
                                    workQueue2.blockingTasksInBuffer.decrementAndGet();
                                    workQueue.add(task, false);
                                    j = -1;
                                    break;
                                }
                            }
                            i3++;
                        }
                        j = workQueue.tryStealLastScheduled(workQueue2, true);
                    } else {
                        WorkQueue workQueue3 = this.localQueue;
                        WorkQueue workQueue4 = worker.localQueue;
                        Objects.requireNonNull(workQueue3);
                        Task pollBuffer = workQueue4.pollBuffer();
                        if (pollBuffer != null) {
                            workQueue3.add(pollBuffer, false);
                            j = -1;
                        } else {
                            j = workQueue3.tryStealLastScheduled(workQueue4, false);
                        }
                    }
                    if (j == -1) {
                        WorkQueue workQueue5 = this.localQueue;
                        Objects.requireNonNull(workQueue5);
                        Task andSet = workQueue5.lastScheduledTask.getAndSet(null);
                        if (andSet == null) {
                            return workQueue5.pollBuffer();
                        }
                        return andSet;
                    } else if (j > 0) {
                        j2 = Math.min(j2, j);
                    }
                }
            }
            if (j2 == Long.MAX_VALUE) {
                j2 = 0;
            }
            this.minDelayUntilStealableTaskNs = j2;
            return null;
        }
    }

    /* compiled from: CoroutineScheduler.kt */
    public enum WorkerState {
        CPU_ACQUIRED,
        BLOCKING,
        PARKING,
        DORMANT,
        TERMINATED
    }

    public final void close() {
        Worker worker;
        int i;
        Task task;
        boolean z;
        if (this._isTerminated.compareAndSet()) {
            Thread currentThread = Thread.currentThread();
            if (currentThread instanceof Worker) {
                worker = (Worker) currentThread;
            } else {
                worker = null;
            }
            if (worker == null || !Intrinsics.areEqual(CoroutineScheduler.this, this)) {
                worker = null;
            }
            synchronized (this.workers) {
                AtomicLong atomicLong = this.controlState;
                Objects.requireNonNull(atomicLong);
                i = (int) (atomicLong.value & 2097151);
            }
            if (1 <= i) {
                int i2 = 1;
                while (true) {
                    int i3 = i2 + 1;
                    Worker worker2 = this.workers.get(i2);
                    Intrinsics.checkNotNull(worker2);
                    Worker worker3 = worker2;
                    if (worker3 != worker) {
                        while (worker3.isAlive()) {
                            LockSupport.unpark(worker3);
                            worker3.join(10000);
                        }
                        boolean z2 = DebugKt.DEBUG;
                        WorkQueue workQueue = worker3.localQueue;
                        GlobalQueue globalQueue = this.globalBlockingQueue;
                        Objects.requireNonNull(workQueue);
                        Task andSet = workQueue.lastScheduledTask.getAndSet(null);
                        if (andSet != null) {
                            globalQueue.addLast(andSet);
                        }
                        do {
                            Task pollBuffer = workQueue.pollBuffer();
                            if (pollBuffer == null) {
                                z = false;
                                continue;
                            } else {
                                globalQueue.addLast(pollBuffer);
                                z = true;
                                continue;
                            }
                        } while (z);
                    }
                    if (i2 == i) {
                        break;
                    }
                    i2 = i3;
                }
            }
            GlobalQueue globalQueue2 = this.globalBlockingQueue;
            Objects.requireNonNull(globalQueue2);
            AtomicRef<LockFreeTaskQueueCore<Task>> atomicRef = globalQueue2._cur;
            while (true) {
                Objects.requireNonNull(atomicRef);
                LockFreeTaskQueueCore lockFreeTaskQueueCore = (LockFreeTaskQueueCore) atomicRef.value;
                if (lockFreeTaskQueueCore.close()) {
                    break;
                }
                globalQueue2._cur.compareAndSet(lockFreeTaskQueueCore, lockFreeTaskQueueCore.next());
            }
            GlobalQueue globalQueue3 = this.globalCpuQueue;
            Objects.requireNonNull(globalQueue3);
            AtomicRef<LockFreeTaskQueueCore<Task>> atomicRef2 = globalQueue3._cur;
            while (true) {
                Objects.requireNonNull(atomicRef2);
                LockFreeTaskQueueCore lockFreeTaskQueueCore2 = (LockFreeTaskQueueCore) atomicRef2.value;
                if (lockFreeTaskQueueCore2.close()) {
                    break;
                }
                globalQueue3._cur.compareAndSet(lockFreeTaskQueueCore2, lockFreeTaskQueueCore2.next());
            }
            while (true) {
                if (worker == null) {
                    task = null;
                } else {
                    task = worker.findTask(true);
                }
                if (task == null && (task = (Task) this.globalCpuQueue.removeFirstOrNull()) == null && (task = (Task) this.globalBlockingQueue.removeFirstOrNull()) == null) {
                    if (worker != null) {
                        worker.tryReleaseCpu(WorkerState.TERMINATED);
                    }
                    boolean z3 = DebugKt.DEBUG;
                    AtomicLong atomicLong2 = this.parkedWorkersStack;
                    Objects.requireNonNull(atomicLong2);
                    int i4 = InterceptorKt.$r8$clinit;
                    atomicLong2.value = 0;
                    TraceBase traceBase = atomicLong2.trace;
                    TraceBase.None none = TraceBase.None.INSTANCE;
                    if (traceBase != none) {
                        Objects.requireNonNull(traceBase);
                    }
                    AtomicLong atomicLong3 = this.controlState;
                    Objects.requireNonNull(atomicLong3);
                    atomicLong3.value = 0;
                    TraceBase traceBase2 = atomicLong3.trace;
                    if (traceBase2 != none) {
                        Objects.requireNonNull(traceBase2);
                        return;
                    }
                    return;
                }
                try {
                    task.run();
                } catch (Throwable th) {
                    Thread currentThread2 = Thread.currentThread();
                    currentThread2.getUncaughtExceptionHandler().uncaughtException(currentThread2, th);
                }
            }
        }
    }

    public final int createNewWorker() {
        boolean z;
        boolean z2;
        synchronized (this.workers) {
            AtomicBoolean atomicBoolean = this._isTerminated;
            Objects.requireNonNull(atomicBoolean);
            boolean z3 = false;
            if (atomicBoolean._value != 0) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                return -1;
            }
            AtomicLong atomicLong = this.controlState;
            Objects.requireNonNull(atomicLong);
            long j = atomicLong.value;
            int i = (int) (j & 2097151);
            int i2 = i - ((int) ((j & 4398044413952L) >> 21));
            if (i2 < 0) {
                i2 = 0;
            }
            if (i2 >= this.corePoolSize) {
                return 0;
            }
            if (i >= this.maxPoolSize) {
                return 0;
            }
            AtomicLong atomicLong2 = this.controlState;
            Objects.requireNonNull(atomicLong2);
            int i3 = ((int) (atomicLong2.value & 2097151)) + 1;
            if (i3 <= 0 || this.workers.get(i3) != null) {
                z2 = false;
            } else {
                z2 = true;
            }
            if (z2) {
                Worker worker = new Worker(i3);
                this.workers.set(i3, worker);
                AtomicLong atomicLong3 = this.controlState;
                Objects.requireNonNull(atomicLong3);
                int i4 = InterceptorKt.$r8$clinit;
                long incrementAndGet = AtomicLong.f154FU.incrementAndGet(atomicLong3);
                TraceBase traceBase = atomicLong3.trace;
                if (traceBase != TraceBase.None.INSTANCE) {
                    Intrinsics.stringPlus("incAndGet():", Long.valueOf(incrementAndGet));
                    Objects.requireNonNull(traceBase);
                }
                if (i3 == ((int) (2097151 & incrementAndGet))) {
                    z3 = true;
                }
                if (z3) {
                    worker.start();
                    int i5 = i2 + 1;
                    return i5;
                }
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
    }

    public final void dispatch(Runnable runnable, TaskContext taskContext, boolean z) {
        Task task;
        Worker worker;
        Task task2;
        boolean z2;
        boolean z3;
        Objects.requireNonNull(TasksKt.schedulerTimeSource);
        long nanoTime = System.nanoTime();
        if (runnable instanceof Task) {
            task = (Task) runnable;
            task.submissionTime = nanoTime;
            task.taskContext = taskContext;
        } else {
            task = new TaskImpl(runnable, nanoTime, taskContext);
        }
        Thread currentThread = Thread.currentThread();
        Worker worker2 = null;
        if (currentThread instanceof Worker) {
            worker = (Worker) currentThread;
        } else {
            worker = null;
        }
        if (worker != null && Intrinsics.areEqual(CoroutineScheduler.this, this)) {
            worker2 = worker;
        }
        boolean z4 = true;
        if (worker2 == null || worker2.state == WorkerState.TERMINATED || (task.taskContext.getTaskMode() == 0 && worker2.state == WorkerState.BLOCKING)) {
            task2 = task;
        } else {
            worker2.mayHaveLocalTasks = true;
            task2 = worker2.localQueue.add(task, z);
        }
        if (task2 != null) {
            if (task2.taskContext.getTaskMode() == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                z3 = this.globalBlockingQueue.addLast(task2);
            } else {
                z3 = this.globalCpuQueue.addLast(task2);
            }
            if (!z3) {
                throw new RejectedExecutionException(Intrinsics.stringPlus(this.schedulerName, " was terminated"));
            }
        }
        if (!z || worker2 == null) {
            z4 = false;
        }
        if (task.taskContext.getTaskMode() != 0) {
            long addAndGet = this.controlState.addAndGet(2097152);
            if (!z4 && !tryUnpark() && !tryCreateWorker(addAndGet)) {
                tryUnpark();
            }
        } else if (!z4 && !tryUnpark()) {
            AtomicLong atomicLong = this.controlState;
            Objects.requireNonNull(atomicLong);
            if (!tryCreateWorker(atomicLong.value)) {
                tryUnpark();
            }
        }
    }

    public final void execute(Runnable runnable) {
        dispatch(runnable, R$styleable.INSTANCE, false);
    }

    public final void parkedWorkersStackTopUpdate(Worker worker, int i, int i2) {
        AtomicLong atomicLong = this.parkedWorkersStack;
        while (true) {
            Objects.requireNonNull(atomicLong);
            long j = atomicLong.value;
            int i3 = (int) (2097151 & j);
            long j2 = (2097152 + j) & -2097152;
            if (i3 == i) {
                if (i2 == 0) {
                    Objects.requireNonNull(worker);
                    Object obj = worker.nextParkedWorker;
                    while (true) {
                        if (obj == NOT_IN_STACK) {
                            i3 = -1;
                            break;
                        } else if (obj == null) {
                            i3 = 0;
                            break;
                        } else {
                            Worker worker2 = (Worker) obj;
                            int i4 = worker2.indexInArray;
                            if (i4 != 0) {
                                i3 = i4;
                                break;
                            }
                            obj = worker2.nextParkedWorker;
                        }
                    }
                } else {
                    i3 = i2;
                }
            }
            if (i3 >= 0 && this.parkedWorkersStack.compareAndSet(j, j2 | ((long) i3))) {
                return;
            }
        }
    }

    public final String toString() {
        ArrayList arrayList = new ArrayList();
        int length = this.workers.length();
        int i = 0;
        int i2 = 1;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (i2 < length) {
            int i7 = i2 + 1;
            Worker worker = this.workers.get(i2);
            if (worker != null) {
                WorkQueue workQueue = worker.localQueue;
                Objects.requireNonNull(workQueue);
                AtomicRef<Task> atomicRef = workQueue.lastScheduledTask;
                Objects.requireNonNull(atomicRef);
                T t = atomicRef.value;
                int bufferSize$external__kotlinx_coroutines__android_common__kotlinx_coroutines = workQueue.mo21434x84356e59();
                if (t != null) {
                    bufferSize$external__kotlinx_coroutines__android_common__kotlinx_coroutines++;
                }
                int ordinal = worker.state.ordinal();
                if (ordinal == 0) {
                    i++;
                    StringBuilder sb = new StringBuilder();
                    sb.append(bufferSize$external__kotlinx_coroutines__android_common__kotlinx_coroutines);
                    sb.append('c');
                    arrayList.add(sb.toString());
                } else if (ordinal == 1) {
                    i3++;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(bufferSize$external__kotlinx_coroutines__android_common__kotlinx_coroutines);
                    sb2.append('b');
                    arrayList.add(sb2.toString());
                } else if (ordinal == 2) {
                    i4++;
                } else if (ordinal == 3) {
                    i5++;
                    if (bufferSize$external__kotlinx_coroutines__android_common__kotlinx_coroutines > 0) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(bufferSize$external__kotlinx_coroutines__android_common__kotlinx_coroutines);
                        sb3.append('d');
                        arrayList.add(sb3.toString());
                    }
                } else if (ordinal == 4) {
                    i6++;
                }
            }
            i2 = i7;
        }
        AtomicLong atomicLong = this.controlState;
        Objects.requireNonNull(atomicLong);
        long j = atomicLong.value;
        return this.schedulerName + '@' + DebugStringsKt.getHexAddress(this) + "[Pool Size {core = " + this.corePoolSize + ", max = " + this.maxPoolSize + "}, Worker States {CPU = " + i + ", blocking = " + i3 + ", parked = " + i4 + ", dormant = " + i5 + ", terminated = " + i6 + "}, running workers queues = " + arrayList + ", global CPU queue size = " + this.globalCpuQueue.getSize() + ", global blocking queue size = " + this.globalBlockingQueue.getSize() + ", Control State {created workers= " + ((int) (2097151 & j)) + ", blocking tasks = " + ((int) ((4398044413952L & j) >> 21)) + ", CPUs acquired = " + (this.corePoolSize - ((int) ((j & 9223367638808264704L) >> 42))) + "}]";
    }

    public final boolean tryUnpark() {
        Worker worker;
        Symbol symbol;
        int i;
        do {
            AtomicLong atomicLong = this.parkedWorkersStack;
            while (true) {
                Objects.requireNonNull(atomicLong);
                long j = atomicLong.value;
                worker = this.workers.get((int) (2097151 & j));
                if (worker == null) {
                    worker = null;
                    break;
                }
                long j2 = (2097152 + j) & -2097152;
                Object obj = worker.nextParkedWorker;
                while (true) {
                    symbol = NOT_IN_STACK;
                    if (obj == symbol) {
                        i = -1;
                        break;
                    } else if (obj == null) {
                        i = 0;
                        break;
                    } else {
                        Worker worker2 = (Worker) obj;
                        i = worker2.indexInArray;
                        if (i != 0) {
                            break;
                        }
                        obj = worker2.nextParkedWorker;
                    }
                }
                if (i >= 0 && this.parkedWorkersStack.compareAndSet(j, j2 | ((long) i))) {
                    worker.nextParkedWorker = symbol;
                    break;
                }
            }
            if (worker == null) {
                return false;
            }
        } while (!worker.workerCtl.compareAndSet(-1, 0));
        LockSupport.unpark(worker);
        return true;
    }

    public CoroutineScheduler(int i, int i2, long j, String str) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        this.corePoolSize = i;
        this.maxPoolSize = i2;
        this.idleWorkerKeepAliveNs = j;
        this.schedulerName = str;
        if (i >= 1) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            if (i2 >= i) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                if (i2 <= 2097150) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (z3) {
                    if (j > 0) {
                        z4 = true;
                    } else {
                        z4 = false;
                    }
                    if (z4) {
                        this.globalCpuQueue = new GlobalQueue();
                        this.globalBlockingQueue = new GlobalQueue();
                        this.parkedWorkersStack = new AtomicLong(0);
                        this.workers = new AtomicReferenceArray<>(i2 + 1);
                        this.controlState = new AtomicLong(((long) i) << 42);
                        this._isTerminated = new AtomicBoolean(false);
                        return;
                    }
                    throw new IllegalArgumentException(("Idle worker keep alive time " + j + " must be positive").toString());
                }
                throw new IllegalArgumentException(C0155xe8491b12.m16m("Max pool size ", i2, " should not exceed maximal supported number of threads 2097150").toString());
            }
            throw new IllegalArgumentException(("Max pool size " + i2 + " should be greater than or equals to core pool size " + i).toString());
        }
        throw new IllegalArgumentException(C0155xe8491b12.m16m("Core pool size ", i, " should be at least 1").toString());
    }

    public final boolean tryCreateWorker(long j) {
        int i = ((int) (2097151 & j)) - ((int) ((j & 4398044413952L) >> 21));
        if (i < 0) {
            i = 0;
        }
        if (i < this.corePoolSize) {
            int createNewWorker = createNewWorker();
            if (createNewWorker == 1 && this.corePoolSize > 1) {
                createNewWorker();
            }
            if (createNewWorker > 0) {
                return true;
            }
        }
        return false;
    }
}
