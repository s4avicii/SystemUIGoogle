package kotlinx.coroutines;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CancellationException;
import kotlin.ExceptionsKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.atomicfu.AtomicBoolean;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.OpDescriptor;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;

/* compiled from: JobSupport.kt */
public class JobSupport implements Job, ChildJob, ParentJob {
    public final AtomicRef<ChildHandle> _parentHandle;
    public final AtomicRef<Object> _state;

    /* compiled from: JobSupport.kt */
    public static final class AwaitContinuation<T> extends CancellableContinuationImpl<T> {
        public final JobSupport job;

        public AwaitContinuation(Continuation continuation, CompletableDeferredImpl completableDeferredImpl) {
            super(continuation, 1);
            this.job = completableDeferredImpl;
        }

        public final String nameString() {
            return "AwaitContinuation";
        }

        public final Throwable getContinuationCancellationCause(JobSupport jobSupport) {
            Throwable rootCause;
            Object state$external__kotlinx_coroutines__android_common__kotlinx_coroutines = this.job.mo21281x8adbf455();
            if ((state$external__kotlinx_coroutines__android_common__kotlinx_coroutines instanceof Finishing) && (rootCause = ((Finishing) state$external__kotlinx_coroutines__android_common__kotlinx_coroutines).getRootCause()) != null) {
                return rootCause;
            }
            if (state$external__kotlinx_coroutines__android_common__kotlinx_coroutines instanceof CompletedExceptionally) {
                return ((CompletedExceptionally) state$external__kotlinx_coroutines__android_common__kotlinx_coroutines).cause;
            }
            return jobSupport.getCancellationException();
        }
    }

    /* compiled from: JobSupport.kt */
    public static final class ChildCompletion extends JobNode {
        public final ChildHandleNode child;
        public final JobSupport parent;
        public final Object proposedUpdate;
        public final Finishing state;

        public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Throwable) obj);
            return Unit.INSTANCE;
        }

        public final void invoke(Throwable th) {
            JobSupport jobSupport = this.parent;
            Finishing finishing = this.state;
            ChildHandleNode childHandleNode = this.child;
            Object obj = this.proposedUpdate;
            Objects.requireNonNull(jobSupport);
            boolean z = DebugKt.DEBUG;
            ChildHandleNode nextChild = JobSupport.nextChild(childHandleNode);
            if (nextChild == null || !jobSupport.tryWaitForChild(finishing, nextChild, obj)) {
                jobSupport.afterCompletion(jobSupport.finalizeFinishingState(finishing, obj));
            }
        }

        public ChildCompletion(JobSupport jobSupport, Finishing finishing, ChildHandleNode childHandleNode, Object obj) {
            this.parent = jobSupport;
            this.state = finishing;
            this.child = childHandleNode;
            this.proposedUpdate = obj;
        }
    }

    /* compiled from: JobSupport.kt */
    public static final class Finishing implements Incomplete {
        public final AtomicRef<Object> _exceptionsHolder;
        public final AtomicBoolean _isCompleting = new AtomicBoolean(false);
        public final AtomicRef<Throwable> _rootCause;
        public final NodeList list;

        public final Throwable getRootCause() {
            AtomicRef<Throwable> atomicRef = this._rootCause;
            Objects.requireNonNull(atomicRef);
            return (Throwable) atomicRef.value;
        }

        public final ArrayList sealLocked(Throwable th) {
            ArrayList arrayList;
            AtomicRef<Object> atomicRef = this._exceptionsHolder;
            Objects.requireNonNull(atomicRef);
            T t = atomicRef.value;
            if (t == null) {
                arrayList = new ArrayList(4);
            } else if (t instanceof Throwable) {
                ArrayList arrayList2 = new ArrayList(4);
                arrayList2.add(t);
                arrayList = arrayList2;
            } else if (t instanceof ArrayList) {
                arrayList = (ArrayList) t;
            } else {
                throw new IllegalStateException(Intrinsics.stringPlus("State is ", t).toString());
            }
            Throwable rootCause = getRootCause();
            if (rootCause != null) {
                arrayList.add(0, rootCause);
            }
            if (th != null && !Intrinsics.areEqual(th, rootCause)) {
                arrayList.add(th);
            }
            this._exceptionsHolder.setValue(JobSupportKt.SEALED);
            return arrayList;
        }

        public final String toString() {
            boolean z;
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Finishing[cancelling=");
            m.append(isCancelling());
            m.append(", completing=");
            AtomicBoolean atomicBoolean = this._isCompleting;
            Objects.requireNonNull(atomicBoolean);
            if (atomicBoolean._value != 0) {
                z = true;
            } else {
                z = false;
            }
            m.append(z);
            m.append(", rootCause=");
            m.append(getRootCause());
            m.append(", exceptions=");
            AtomicRef<Object> atomicRef = this._exceptionsHolder;
            Objects.requireNonNull(atomicRef);
            m.append(atomicRef.value);
            m.append(", list=");
            m.append(this.list);
            m.append(']');
            return m.toString();
        }

        public Finishing(NodeList nodeList, Throwable th) {
            this.list = nodeList;
            this._rootCause = new AtomicRef<>(th);
            this._exceptionsHolder = new AtomicRef<>((Object) null);
        }

        public final void addExceptionLocked(Throwable th) {
            Throwable rootCause = getRootCause();
            if (rootCause == null) {
                this._rootCause.setValue(th);
            } else if (th != rootCause) {
                AtomicRef<Object> atomicRef = this._exceptionsHolder;
                Objects.requireNonNull(atomicRef);
                T t = atomicRef.value;
                if (t == null) {
                    this._exceptionsHolder.setValue(th);
                } else if (t instanceof Throwable) {
                    if (th != t) {
                        ArrayList arrayList = new ArrayList(4);
                        arrayList.add(t);
                        arrayList.add(th);
                        this._exceptionsHolder.setValue(arrayList);
                    }
                } else if (t instanceof ArrayList) {
                    ((ArrayList) t).add(th);
                } else {
                    throw new IllegalStateException(Intrinsics.stringPlus("State is ", t).toString());
                }
            }
        }

        public final boolean isActive() {
            if (getRootCause() == null) {
                return true;
            }
            return false;
        }

        public final boolean isCancelling() {
            if (getRootCause() != null) {
                return true;
            }
            return false;
        }

        public final NodeList getList() {
            return this.list;
        }
    }

    public void afterCompletion(Object obj) {
    }

    public String cancellationExceptionMessage() {
        return "Job was cancelled";
    }

    /* renamed from: getHandlesException$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public boolean mo21268x3da20e56() {
        return true;
    }

    /* renamed from: getOnCancelComplete$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public boolean mo21269x6aca53c8() {
        return this instanceof CompletableDeferredImpl;
    }

    public boolean handleJobException(Throwable th) {
        return false;
    }

    /* JADX WARNING: type inference failed for: r4v12, types: [kotlinx.coroutines.InactiveNodeList] */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x008a, code lost:
        if (r9 == false) goto L_0x008c;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final kotlinx.coroutines.DisposableHandle invokeOnCompletion(boolean r11, boolean r12, kotlinx.coroutines.JobNode r13) {
        /*
            r10 = this;
            r0 = 0
            if (r11 == 0) goto L_0x0014
            boolean r1 = r13 instanceof kotlinx.coroutines.JobCancellingNode
            if (r1 == 0) goto L_0x000b
            r1 = r13
            kotlinx.coroutines.JobCancellingNode r1 = (kotlinx.coroutines.JobCancellingNode) r1
            goto L_0x000c
        L_0x000b:
            r1 = r0
        L_0x000c:
            if (r1 != 0) goto L_0x0017
            kotlinx.coroutines.InvokeOnCancelling r1 = new kotlinx.coroutines.InvokeOnCancelling
            r1.<init>(r13)
            goto L_0x0017
        L_0x0014:
            boolean r1 = kotlinx.coroutines.DebugKt.DEBUG
            r1 = r13
        L_0x0017:
            r1.job = r10
        L_0x0019:
            java.lang.Object r2 = r10.mo21281x8adbf455()
            boolean r3 = r2 instanceof kotlinx.coroutines.Empty
            if (r3 == 0) goto L_0x004a
            r3 = r2
            kotlinx.coroutines.Empty r3 = (kotlinx.coroutines.Empty) r3
            java.util.Objects.requireNonNull(r3)
            boolean r4 = r3.isActive
            if (r4 == 0) goto L_0x0034
            kotlinx.atomicfu.AtomicRef<java.lang.Object> r3 = r10._state
            boolean r2 = r3.compareAndSet(r2, r1)
            if (r2 == 0) goto L_0x0019
            return r1
        L_0x0034:
            kotlinx.coroutines.NodeList r2 = new kotlinx.coroutines.NodeList
            r2.<init>()
            boolean r4 = r3.isActive
            if (r4 == 0) goto L_0x003e
            goto L_0x0044
        L_0x003e:
            kotlinx.coroutines.InactiveNodeList r4 = new kotlinx.coroutines.InactiveNodeList
            r4.<init>(r2)
            r2 = r4
        L_0x0044:
            kotlinx.atomicfu.AtomicRef<java.lang.Object> r4 = r10._state
            r4.compareAndSet(r3, r2)
            goto L_0x0019
        L_0x004a:
            boolean r3 = r2 instanceof kotlinx.coroutines.Incomplete
            if (r3 == 0) goto L_0x00cf
            r3 = r2
            kotlinx.coroutines.Incomplete r3 = (kotlinx.coroutines.Incomplete) r3
            kotlinx.coroutines.NodeList r3 = r3.getList()
            if (r3 != 0) goto L_0x0062
            java.lang.String r3 = "null cannot be cast to non-null type kotlinx.coroutines.JobNode"
            java.util.Objects.requireNonNull(r2, r3)
            kotlinx.coroutines.JobNode r2 = (kotlinx.coroutines.JobNode) r2
            r10.promoteSingleToNodeList(r2)
            goto L_0x0019
        L_0x0062:
            kotlinx.coroutines.NonDisposableHandle r4 = kotlinx.coroutines.NonDisposableHandle.INSTANCE
            r5 = 2
            r6 = 0
            r7 = 1
            if (r11 == 0) goto L_0x00b0
            boolean r8 = r2 instanceof kotlinx.coroutines.JobSupport.Finishing
            if (r8 == 0) goto L_0x00b0
            monitor-enter(r2)
            r8 = r2
            kotlinx.coroutines.JobSupport$Finishing r8 = (kotlinx.coroutines.JobSupport.Finishing) r8     // Catch:{ all -> 0x00ad }
            java.lang.Throwable r8 = r8.getRootCause()     // Catch:{ all -> 0x00ad }
            if (r8 == 0) goto L_0x008c
            boolean r9 = r13 instanceof kotlinx.coroutines.ChildHandleNode     // Catch:{ all -> 0x00ad }
            if (r9 == 0) goto L_0x00ab
            r9 = r2
            kotlinx.coroutines.JobSupport$Finishing r9 = (kotlinx.coroutines.JobSupport.Finishing) r9     // Catch:{ all -> 0x00ad }
            kotlinx.atomicfu.AtomicBoolean r9 = r9._isCompleting     // Catch:{ all -> 0x00ad }
            java.util.Objects.requireNonNull(r9)     // Catch:{ all -> 0x00ad }
            int r9 = r9._value     // Catch:{ all -> 0x00ad }
            if (r9 == 0) goto L_0x0089
            r9 = r7
            goto L_0x008a
        L_0x0089:
            r9 = r6
        L_0x008a:
            if (r9 != 0) goto L_0x00ab
        L_0x008c:
            kotlinx.coroutines.JobSupport$addLastAtomic$$inlined$addLastIf$1 r4 = new kotlinx.coroutines.JobSupport$addLastAtomic$$inlined$addLastIf$1     // Catch:{ all -> 0x00ad }
            r4.<init>(r1, r10, r2)     // Catch:{ all -> 0x00ad }
        L_0x0091:
            kotlinx.coroutines.internal.LockFreeLinkedListNode r9 = r3.getPrevNode()     // Catch:{ all -> 0x00ad }
            int r9 = r9.tryCondAddNext(r1, r3, r4)     // Catch:{ all -> 0x00ad }
            if (r9 == r7) goto L_0x00a0
            if (r9 == r5) goto L_0x009e
            goto L_0x0091
        L_0x009e:
            r4 = r6
            goto L_0x00a1
        L_0x00a0:
            r4 = r7
        L_0x00a1:
            if (r4 != 0) goto L_0x00a6
            monitor-exit(r2)
            goto L_0x0019
        L_0x00a6:
            if (r8 != 0) goto L_0x00aa
            monitor-exit(r2)
            return r1
        L_0x00aa:
            r4 = r1
        L_0x00ab:
            monitor-exit(r2)
            goto L_0x00b1
        L_0x00ad:
            r10 = move-exception
            monitor-exit(r2)
            throw r10
        L_0x00b0:
            r8 = r0
        L_0x00b1:
            if (r8 == 0) goto L_0x00b9
            if (r12 == 0) goto L_0x00b8
            r13.invoke(r8)
        L_0x00b8:
            return r4
        L_0x00b9:
            kotlinx.coroutines.JobSupport$addLastAtomic$$inlined$addLastIf$1 r4 = new kotlinx.coroutines.JobSupport$addLastAtomic$$inlined$addLastIf$1
            r4.<init>(r1, r10, r2)
        L_0x00be:
            kotlinx.coroutines.internal.LockFreeLinkedListNode r2 = r3.getPrevNode()
            int r2 = r2.tryCondAddNext(r1, r3, r4)
            if (r2 == r7) goto L_0x00cb
            if (r2 == r5) goto L_0x00cc
            goto L_0x00be
        L_0x00cb:
            r6 = r7
        L_0x00cc:
            if (r6 == 0) goto L_0x0019
            return r1
        L_0x00cf:
            if (r12 == 0) goto L_0x00e1
            boolean r10 = r2 instanceof kotlinx.coroutines.CompletedExceptionally
            if (r10 == 0) goto L_0x00d8
            kotlinx.coroutines.CompletedExceptionally r2 = (kotlinx.coroutines.CompletedExceptionally) r2
            goto L_0x00d9
        L_0x00d8:
            r2 = r0
        L_0x00d9:
            if (r2 != 0) goto L_0x00dc
            goto L_0x00de
        L_0x00dc:
            java.lang.Throwable r0 = r2.cause
        L_0x00de:
            r13.invoke(r0)
        L_0x00e1:
            kotlinx.coroutines.NonDisposableHandle r10 = kotlinx.coroutines.NonDisposableHandle.INSTANCE
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.JobSupport.invokeOnCompletion(boolean, boolean, kotlinx.coroutines.JobNode):kotlinx.coroutines.DisposableHandle");
    }

    public boolean isScopedCoroutine() {
        return this instanceof BlockingCoroutine;
    }

    public void onCompletionInternal(Object obj) {
    }

    public void onStart() {
    }

    public static String stateString(Object obj) {
        boolean z;
        if (obj instanceof Finishing) {
            Finishing finishing = (Finishing) obj;
            if (finishing.isCancelling()) {
                return "Cancelling";
            }
            AtomicBoolean atomicBoolean = finishing._isCompleting;
            Objects.requireNonNull(atomicBoolean);
            if (atomicBoolean._value != 0) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                return "Completing";
            }
        } else if (obj instanceof Incomplete) {
            if (((Incomplete) obj).isActive()) {
                return "Active";
            }
            return "New";
        } else if (obj instanceof CompletedExceptionally) {
            return "Cancelled";
        } else {
            return "Completed";
        }
        return "Active";
    }

    public final ChildHandle attachChild(JobSupport jobSupport) {
        return (ChildHandle) Job.DefaultImpls.invokeOnCompletion$default(this, true, new ChildHandleNode(jobSupport), 2);
    }

    public final void cancel(CancellationException cancellationException) {
        if (cancellationException == null) {
            cancellationException = new JobCancellationException(cancellationExceptionMessage(), (Throwable) null, this);
        }
        mo21272x7bd83b56(cancellationException);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x003d, code lost:
        r0 = kotlinx.coroutines.JobSupportKt.COMPLETING_ALREADY;
     */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x00d0 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x004a A[SYNTHETIC] */
    /* renamed from: cancelImpl$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean mo21272x7bd83b56(java.lang.Object r9) {
        /*
            r8 = this;
            kotlinx.coroutines.internal.Symbol r0 = kotlinx.coroutines.JobSupportKt.COMPLETING_ALREADY
            boolean r1 = r8.mo21269x6aca53c8()
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L_0x0044
        L_0x000a:
            java.lang.Object r0 = r8.mo21281x8adbf455()
            boolean r1 = r0 instanceof kotlinx.coroutines.Incomplete
            if (r1 == 0) goto L_0x003d
            boolean r1 = r0 instanceof kotlinx.coroutines.JobSupport.Finishing
            if (r1 == 0) goto L_0x002b
            r1 = r0
            kotlinx.coroutines.JobSupport$Finishing r1 = (kotlinx.coroutines.JobSupport.Finishing) r1
            java.util.Objects.requireNonNull(r1)
            kotlinx.atomicfu.AtomicBoolean r1 = r1._isCompleting
            java.util.Objects.requireNonNull(r1)
            int r1 = r1._value
            if (r1 == 0) goto L_0x0027
            r1 = r2
            goto L_0x0028
        L_0x0027:
            r1 = r3
        L_0x0028:
            if (r1 == 0) goto L_0x002b
            goto L_0x003d
        L_0x002b:
            kotlinx.coroutines.CompletedExceptionally r1 = new kotlinx.coroutines.CompletedExceptionally
            java.lang.Throwable r4 = r8.createCauseException(r9)
            r1.<init>(r4, r3)
            java.lang.Object r0 = r8.tryMakeCompleting(r0, r1)
            kotlinx.coroutines.internal.Symbol r1 = kotlinx.coroutines.JobSupportKt.COMPLETING_RETRY
            if (r0 == r1) goto L_0x000a
            goto L_0x003f
        L_0x003d:
            kotlinx.coroutines.internal.Symbol r0 = kotlinx.coroutines.JobSupportKt.COMPLETING_ALREADY
        L_0x003f:
            kotlinx.coroutines.internal.Symbol r1 = kotlinx.coroutines.JobSupportKt.COMPLETING_WAITING_CHILDREN
            if (r0 != r1) goto L_0x0044
            return r2
        L_0x0044:
            kotlinx.coroutines.internal.Symbol r1 = kotlinx.coroutines.JobSupportKt.COMPLETING_ALREADY
            if (r0 != r1) goto L_0x00fb
            r0 = 0
            r1 = r0
        L_0x004a:
            java.lang.Object r4 = r8.mo21281x8adbf455()
            boolean r5 = r4 instanceof kotlinx.coroutines.JobSupport.Finishing
            if (r5 == 0) goto L_0x009f
            monitor-enter(r4)
            r5 = r4
            kotlinx.coroutines.JobSupport$Finishing r5 = (kotlinx.coroutines.JobSupport.Finishing) r5     // Catch:{ all -> 0x009c }
            kotlinx.atomicfu.AtomicRef<java.lang.Object> r5 = r5._exceptionsHolder     // Catch:{ all -> 0x009c }
            java.util.Objects.requireNonNull(r5)     // Catch:{ all -> 0x009c }
            T r5 = r5.value     // Catch:{ all -> 0x009c }
            kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.JobSupportKt.SEALED     // Catch:{ all -> 0x009c }
            if (r5 != r6) goto L_0x0063
            r5 = r2
            goto L_0x0064
        L_0x0063:
            r5 = r3
        L_0x0064:
            if (r5 == 0) goto L_0x006b
            kotlinx.coroutines.internal.Symbol r9 = kotlinx.coroutines.JobSupportKt.TOO_LATE_TO_CANCEL     // Catch:{ all -> 0x009c }
            monitor-exit(r4)
            goto L_0x00fa
        L_0x006b:
            r5 = r4
            kotlinx.coroutines.JobSupport$Finishing r5 = (kotlinx.coroutines.JobSupport.Finishing) r5     // Catch:{ all -> 0x009c }
            boolean r5 = r5.isCancelling()     // Catch:{ all -> 0x009c }
            if (r9 != 0) goto L_0x0076
            if (r5 != 0) goto L_0x0082
        L_0x0076:
            if (r1 != 0) goto L_0x007c
            java.lang.Throwable r1 = r8.createCauseException(r9)     // Catch:{ all -> 0x009c }
        L_0x007c:
            r9 = r4
            kotlinx.coroutines.JobSupport$Finishing r9 = (kotlinx.coroutines.JobSupport.Finishing) r9     // Catch:{ all -> 0x009c }
            r9.addExceptionLocked(r1)     // Catch:{ all -> 0x009c }
        L_0x0082:
            r9 = r4
            kotlinx.coroutines.JobSupport$Finishing r9 = (kotlinx.coroutines.JobSupport.Finishing) r9     // Catch:{ all -> 0x009c }
            java.lang.Throwable r9 = r9.getRootCause()     // Catch:{ all -> 0x009c }
            r1 = r5 ^ 1
            if (r1 == 0) goto L_0x008e
            r0 = r9
        L_0x008e:
            monitor-exit(r4)
            if (r0 != 0) goto L_0x0092
            goto L_0x0099
        L_0x0092:
            kotlinx.coroutines.JobSupport$Finishing r4 = (kotlinx.coroutines.JobSupport.Finishing) r4
            kotlinx.coroutines.NodeList r9 = r4.list
            r8.notifyCancelling(r9, r0)
        L_0x0099:
            kotlinx.coroutines.internal.Symbol r9 = kotlinx.coroutines.JobSupportKt.COMPLETING_ALREADY
            goto L_0x00fa
        L_0x009c:
            r8 = move-exception
            monitor-exit(r4)
            throw r8
        L_0x009f:
            boolean r5 = r4 instanceof kotlinx.coroutines.Incomplete
            if (r5 == 0) goto L_0x00f8
            if (r1 != 0) goto L_0x00a9
            java.lang.Throwable r1 = r8.createCauseException(r9)
        L_0x00a9:
            r5 = r4
            kotlinx.coroutines.Incomplete r5 = (kotlinx.coroutines.Incomplete) r5
            boolean r6 = r5.isActive()
            if (r6 == 0) goto L_0x00d3
            boolean r4 = kotlinx.coroutines.DebugKt.DEBUG
            kotlinx.coroutines.NodeList r4 = r8.getOrPromoteCancellingList(r5)
            if (r4 != 0) goto L_0x00bb
            goto L_0x00c8
        L_0x00bb:
            kotlinx.coroutines.JobSupport$Finishing r6 = new kotlinx.coroutines.JobSupport$Finishing
            r6.<init>(r4, r1)
            kotlinx.atomicfu.AtomicRef<java.lang.Object> r7 = r8._state
            boolean r5 = r7.compareAndSet(r5, r6)
            if (r5 != 0) goto L_0x00ca
        L_0x00c8:
            r4 = r3
            goto L_0x00ce
        L_0x00ca:
            r8.notifyCancelling(r4, r1)
            r4 = r2
        L_0x00ce:
            if (r4 == 0) goto L_0x004a
            kotlinx.coroutines.internal.Symbol r9 = kotlinx.coroutines.JobSupportKt.COMPLETING_ALREADY
            goto L_0x00fa
        L_0x00d3:
            kotlinx.coroutines.CompletedExceptionally r5 = new kotlinx.coroutines.CompletedExceptionally
            r5.<init>(r1, r3)
            java.lang.Object r5 = r8.tryMakeCompleting(r4, r5)
            kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.JobSupportKt.COMPLETING_ALREADY
            if (r5 == r6) goto L_0x00e8
            kotlinx.coroutines.internal.Symbol r4 = kotlinx.coroutines.JobSupportKt.COMPLETING_RETRY
            if (r5 != r4) goto L_0x00e6
            goto L_0x004a
        L_0x00e6:
            r0 = r5
            goto L_0x00fb
        L_0x00e8:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "Cannot happen in "
            java.lang.String r9 = kotlin.jvm.internal.Intrinsics.stringPlus(r9, r4)
            java.lang.String r9 = r9.toString()
            r8.<init>(r9)
            throw r8
        L_0x00f8:
            kotlinx.coroutines.internal.Symbol r9 = kotlinx.coroutines.JobSupportKt.TOO_LATE_TO_CANCEL
        L_0x00fa:
            r0 = r9
        L_0x00fb:
            kotlinx.coroutines.internal.Symbol r9 = kotlinx.coroutines.JobSupportKt.COMPLETING_ALREADY
            if (r0 != r9) goto L_0x0100
            goto L_0x010e
        L_0x0100:
            kotlinx.coroutines.internal.Symbol r9 = kotlinx.coroutines.JobSupportKt.COMPLETING_WAITING_CHILDREN
            if (r0 != r9) goto L_0x0105
            goto L_0x010e
        L_0x0105:
            kotlinx.coroutines.internal.Symbol r9 = kotlinx.coroutines.JobSupportKt.TOO_LATE_TO_CANCEL
            if (r0 != r9) goto L_0x010b
            r2 = r3
            goto L_0x010e
        L_0x010b:
            r8.afterCompletion(r0)
        L_0x010e:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.JobSupport.mo21272x7bd83b56(java.lang.Object):boolean");
    }

    public boolean childCancelled(Throwable th) {
        if (th instanceof CancellationException) {
            return true;
        }
        if (!mo21272x7bd83b56(th) || !mo21268x3da20e56()) {
            return false;
        }
        return true;
    }

    public final void completeStateFinalization(Incomplete incomplete, Object obj) {
        CompletedExceptionally completedExceptionally;
        Throwable th;
        CompletionHandlerException completionHandlerException;
        AtomicRef<ChildHandle> atomicRef = this._parentHandle;
        Objects.requireNonNull(atomicRef);
        ChildHandle childHandle = (ChildHandle) atomicRef.value;
        if (childHandle != null) {
            childHandle.dispose();
            this._parentHandle.setValue(NonDisposableHandle.INSTANCE);
        }
        if (obj instanceof CompletedExceptionally) {
            completedExceptionally = (CompletedExceptionally) obj;
        } else {
            completedExceptionally = null;
        }
        if (completedExceptionally == null) {
            th = null;
        } else {
            th = completedExceptionally.cause;
        }
        if (incomplete instanceof JobNode) {
            try {
                ((JobNode) incomplete).invoke(th);
            } catch (Throwable th2) {
                mo21178x4a3c0b24(new CompletionHandlerException("Exception in completion handler " + incomplete + " for " + this, th2));
            }
        } else {
            NodeList list = incomplete.getList();
            if (list != null) {
                CompletionHandlerException completionHandlerException2 = null;
                for (LockFreeLinkedListNode lockFreeLinkedListNode = (LockFreeLinkedListNode) list.getNext(); !Intrinsics.areEqual(lockFreeLinkedListNode, list); lockFreeLinkedListNode = lockFreeLinkedListNode.getNextNode()) {
                    if (lockFreeLinkedListNode instanceof JobNode) {
                        JobNode jobNode = (JobNode) lockFreeLinkedListNode;
                        try {
                            jobNode.invoke(th);
                        } catch (Throwable th3) {
                            if (completionHandlerException2 == null) {
                                completionHandlerException = null;
                            } else {
                                ExceptionsKt.addSuppressed(completionHandlerException2, th3);
                                completionHandlerException = completionHandlerException2;
                            }
                            if (completionHandlerException == null) {
                                completionHandlerException2 = new CompletionHandlerException("Exception in completion handler " + jobNode + " for " + this, th3);
                            }
                        }
                    }
                }
                if (completionHandlerException2 != null) {
                    mo21178x4a3c0b24(completionHandlerException2);
                }
            }
        }
    }

    public final Throwable createCauseException(Object obj) {
        boolean z;
        if (obj == null) {
            z = true;
        } else {
            z = obj instanceof Throwable;
        }
        if (z) {
            Throwable th = (Throwable) obj;
            if (th == null) {
                return new JobCancellationException(cancellationExceptionMessage(), (Throwable) null, this);
            }
            return th;
        }
        Objects.requireNonNull(obj, "null cannot be cast to non-null type kotlinx.coroutines.ParentJob");
        return ((ParentJob) obj).getChildJobCancellationCause();
    }

    public final Object finalizeFinishingState(Finishing finishing, Object obj) {
        CompletedExceptionally completedExceptionally;
        Throwable finalRootCause;
        Object obj2;
        boolean z = DebugKt.DEBUG;
        Throwable th = null;
        if (obj instanceof CompletedExceptionally) {
            completedExceptionally = (CompletedExceptionally) obj;
        } else {
            completedExceptionally = null;
        }
        if (completedExceptionally != null) {
            th = completedExceptionally.cause;
        }
        synchronized (finishing) {
            finishing.isCancelling();
            ArrayList sealLocked = finishing.sealLocked(th);
            finalRootCause = getFinalRootCause(finishing, sealLocked);
            if (finalRootCause != null) {
                addSuppressedExceptions(finalRootCause, sealLocked);
            }
        }
        boolean z2 = false;
        if (!(finalRootCause == null || finalRootCause == th)) {
            obj = new CompletedExceptionally(finalRootCause, false);
        }
        if (finalRootCause != null) {
            if (cancelParent(finalRootCause) || handleJobException(finalRootCause)) {
                z2 = true;
            }
            if (z2) {
                Objects.requireNonNull(obj, "null cannot be cast to non-null type kotlinx.coroutines.CompletedExceptionally");
                ((CompletedExceptionally) obj)._handled.compareAndSet();
            }
        }
        onCompletionInternal(obj);
        AtomicRef<Object> atomicRef = this._state;
        if (obj instanceof Incomplete) {
            obj2 = new IncompleteStateBox((Incomplete) obj);
        } else {
            obj2 = obj;
        }
        atomicRef.compareAndSet(finishing, obj2);
        completeStateFinalization(finishing, obj);
        return obj;
    }

    /* renamed from: getState$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public final Object mo21281x8adbf455() {
        AtomicRef<Object> atomicRef = this._state;
        while (true) {
            Objects.requireNonNull(atomicRef);
            T t = atomicRef.value;
            if (!(t instanceof OpDescriptor)) {
                return t;
            }
            ((OpDescriptor) t).perform(this);
        }
    }

    public final void initParentJob(Job job) {
        boolean z = DebugKt.DEBUG;
        if (job == null) {
            this._parentHandle.setValue(NonDisposableHandle.INSTANCE);
            return;
        }
        job.start();
        ChildHandle attachChild = job.attachChild(this);
        this._parentHandle.setValue(attachChild);
        if (!(mo21281x8adbf455() instanceof Incomplete)) {
            attachChild.dispose();
            this._parentHandle.setValue(NonDisposableHandle.INSTANCE);
        }
    }

    public final void promoteSingleToNodeList(JobNode jobNode) {
        NodeList nodeList = new NodeList();
        Objects.requireNonNull(jobNode);
        nodeList._prev.lazySet(jobNode);
        nodeList._next.lazySet(jobNode);
        while (true) {
            if (jobNode.getNext() == jobNode) {
                if (jobNode._next.compareAndSet(jobNode, nodeList)) {
                    nodeList.finishAdd(jobNode);
                    break;
                }
            } else {
                break;
            }
        }
        this._state.compareAndSet(jobNode, jobNode.getNextNode());
    }

    public final int startInternal(Object obj) {
        if (obj instanceof Empty) {
            Empty empty = (Empty) obj;
            Objects.requireNonNull(empty);
            if (empty.isActive) {
                return 0;
            }
            if (!this._state.compareAndSet(obj, JobSupportKt.EMPTY_ACTIVE)) {
                return -1;
            }
            onStart();
            return 1;
        } else if (!(obj instanceof InactiveNodeList)) {
            return 0;
        } else {
            AtomicRef<Object> atomicRef = this._state;
            InactiveNodeList inactiveNodeList = (InactiveNodeList) obj;
            Objects.requireNonNull(inactiveNodeList);
            if (!atomicRef.compareAndSet(obj, inactiveNodeList.list)) {
                return -1;
            }
            onStart();
            return 1;
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(mo21179x29b568d4() + '{' + stateString(mo21281x8adbf455()) + '}');
        sb.append('@');
        sb.append(DebugStringsKt.getHexAddress(this));
        return sb.toString();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:62:0x00b1, code lost:
        if (r5 != null) goto L_0x00b4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00b4, code lost:
        notifyCancelling(r0, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00b9, code lost:
        if ((r7 instanceof kotlinx.coroutines.ChildHandleNode) == false) goto L_0x00bf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x00bb, code lost:
        r0 = (kotlinx.coroutines.ChildHandleNode) r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00bf, code lost:
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x00c0, code lost:
        if (r0 != null) goto L_0x00ce;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x00c2, code lost:
        r7 = r7.getList();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x00c6, code lost:
        if (r7 != null) goto L_0x00c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x00c9, code lost:
        r4 = nextChild(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x00ce, code lost:
        r4 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x00cf, code lost:
        if (r4 == null) goto L_0x00da;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x00d5, code lost:
        if (tryWaitForChild(r3, r4, r8) == false) goto L_0x00da;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:?, code lost:
        return finalizeFinishingState(r3, r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:?, code lost:
        return kotlinx.coroutines.JobSupportKt.COMPLETING_WAITING_CHILDREN;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object tryMakeCompleting(java.lang.Object r7, java.lang.Object r8) {
        /*
            r6 = this;
            boolean r0 = r7 instanceof kotlinx.coroutines.Incomplete
            if (r0 != 0) goto L_0x0007
            kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.JobSupportKt.COMPLETING_ALREADY
            return r6
        L_0x0007:
            boolean r0 = r7 instanceof kotlinx.coroutines.Empty
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L_0x0011
            boolean r0 = r7 instanceof kotlinx.coroutines.JobNode
            if (r0 == 0) goto L_0x0041
        L_0x0011:
            boolean r0 = r7 instanceof kotlinx.coroutines.ChildHandleNode
            if (r0 != 0) goto L_0x0041
            boolean r0 = r8 instanceof kotlinx.coroutines.CompletedExceptionally
            if (r0 != 0) goto L_0x0041
            kotlinx.coroutines.Incomplete r7 = (kotlinx.coroutines.Incomplete) r7
            boolean r0 = kotlinx.coroutines.DebugKt.DEBUG
            kotlinx.atomicfu.AtomicRef<java.lang.Object> r0 = r6._state
            boolean r3 = r8 instanceof kotlinx.coroutines.Incomplete
            if (r3 == 0) goto L_0x002c
            kotlinx.coroutines.IncompleteStateBox r3 = new kotlinx.coroutines.IncompleteStateBox
            r4 = r8
            kotlinx.coroutines.Incomplete r4 = (kotlinx.coroutines.Incomplete) r4
            r3.<init>(r4)
            goto L_0x002d
        L_0x002c:
            r3 = r8
        L_0x002d:
            boolean r0 = r0.compareAndSet(r7, r3)
            if (r0 != 0) goto L_0x0034
            goto L_0x003b
        L_0x0034:
            r6.onCompletionInternal(r8)
            r6.completeStateFinalization(r7, r8)
            r1 = r2
        L_0x003b:
            if (r1 == 0) goto L_0x003e
            return r8
        L_0x003e:
            kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.JobSupportKt.COMPLETING_RETRY
            return r6
        L_0x0041:
            kotlinx.coroutines.Incomplete r7 = (kotlinx.coroutines.Incomplete) r7
            kotlinx.coroutines.NodeList r0 = r6.getOrPromoteCancellingList(r7)
            if (r0 != 0) goto L_0x004d
            kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.JobSupportKt.COMPLETING_RETRY
            goto L_0x00de
        L_0x004d:
            boolean r3 = r7 instanceof kotlinx.coroutines.JobSupport.Finishing
            r4 = 0
            if (r3 == 0) goto L_0x0056
            r3 = r7
            kotlinx.coroutines.JobSupport$Finishing r3 = (kotlinx.coroutines.JobSupport.Finishing) r3
            goto L_0x0057
        L_0x0056:
            r3 = r4
        L_0x0057:
            if (r3 != 0) goto L_0x005e
            kotlinx.coroutines.JobSupport$Finishing r3 = new kotlinx.coroutines.JobSupport$Finishing
            r3.<init>(r0, r4)
        L_0x005e:
            monitor-enter(r3)
            kotlinx.atomicfu.AtomicBoolean r5 = r3._isCompleting     // Catch:{ all -> 0x00df }
            java.util.Objects.requireNonNull(r5)     // Catch:{ all -> 0x00df }
            int r5 = r5._value     // Catch:{ all -> 0x00df }
            if (r5 == 0) goto L_0x0069
            r1 = r2
        L_0x0069:
            if (r1 == 0) goto L_0x0070
            kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.JobSupportKt.COMPLETING_ALREADY     // Catch:{ all -> 0x00df }
            monitor-exit(r3)
            goto L_0x00de
        L_0x0070:
            kotlinx.atomicfu.AtomicBoolean r1 = r3._isCompleting     // Catch:{ all -> 0x00df }
            java.util.Objects.requireNonNull(r1)     // Catch:{ all -> 0x00df }
            int r5 = kotlinx.atomicfu.InterceptorKt.$r8$clinit     // Catch:{ all -> 0x00df }
            r1._value = r2     // Catch:{ all -> 0x00df }
            kotlinx.atomicfu.TraceBase r1 = r1.trace     // Catch:{ all -> 0x00df }
            kotlinx.atomicfu.TraceBase$None r5 = kotlinx.atomicfu.TraceBase.None.INSTANCE     // Catch:{ all -> 0x00df }
            if (r1 == r5) goto L_0x0082
            java.util.Objects.requireNonNull(r1)     // Catch:{ all -> 0x00df }
        L_0x0082:
            if (r3 == r7) goto L_0x0090
            kotlinx.atomicfu.AtomicRef<java.lang.Object> r1 = r6._state     // Catch:{ all -> 0x00df }
            boolean r1 = r1.compareAndSet(r7, r3)     // Catch:{ all -> 0x00df }
            if (r1 != 0) goto L_0x0090
            kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.JobSupportKt.COMPLETING_RETRY     // Catch:{ all -> 0x00df }
            monitor-exit(r3)
            goto L_0x00de
        L_0x0090:
            boolean r1 = kotlinx.coroutines.DebugKt.DEBUG     // Catch:{ all -> 0x00df }
            boolean r1 = r3.isCancelling()     // Catch:{ all -> 0x00df }
            boolean r5 = r8 instanceof kotlinx.coroutines.CompletedExceptionally     // Catch:{ all -> 0x00df }
            if (r5 == 0) goto L_0x009e
            r5 = r8
            kotlinx.coroutines.CompletedExceptionally r5 = (kotlinx.coroutines.CompletedExceptionally) r5     // Catch:{ all -> 0x00df }
            goto L_0x009f
        L_0x009e:
            r5 = r4
        L_0x009f:
            if (r5 != 0) goto L_0x00a2
            goto L_0x00a7
        L_0x00a2:
            java.lang.Throwable r5 = r5.cause     // Catch:{ all -> 0x00df }
            r3.addExceptionLocked(r5)     // Catch:{ all -> 0x00df }
        L_0x00a7:
            java.lang.Throwable r5 = r3.getRootCause()     // Catch:{ all -> 0x00df }
            r1 = r1 ^ r2
            if (r1 == 0) goto L_0x00af
            goto L_0x00b0
        L_0x00af:
            r5 = r4
        L_0x00b0:
            monitor-exit(r3)
            if (r5 != 0) goto L_0x00b4
            goto L_0x00b7
        L_0x00b4:
            r6.notifyCancelling(r0, r5)
        L_0x00b7:
            boolean r0 = r7 instanceof kotlinx.coroutines.ChildHandleNode
            if (r0 == 0) goto L_0x00bf
            r0 = r7
            kotlinx.coroutines.ChildHandleNode r0 = (kotlinx.coroutines.ChildHandleNode) r0
            goto L_0x00c0
        L_0x00bf:
            r0 = r4
        L_0x00c0:
            if (r0 != 0) goto L_0x00ce
            kotlinx.coroutines.NodeList r7 = r7.getList()
            if (r7 != 0) goto L_0x00c9
            goto L_0x00cf
        L_0x00c9:
            kotlinx.coroutines.ChildHandleNode r4 = nextChild(r7)
            goto L_0x00cf
        L_0x00ce:
            r4 = r0
        L_0x00cf:
            if (r4 == 0) goto L_0x00da
            boolean r7 = r6.tryWaitForChild(r3, r4, r8)
            if (r7 == 0) goto L_0x00da
            kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.JobSupportKt.COMPLETING_WAITING_CHILDREN
            goto L_0x00de
        L_0x00da:
            java.lang.Object r6 = r6.finalizeFinishingState(r3, r8)
        L_0x00de:
            return r6
        L_0x00df:
            r6 = move-exception
            monitor-exit(r3)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.JobSupport.tryMakeCompleting(java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public final boolean tryWaitForChild(Finishing finishing, ChildHandleNode childHandleNode, Object obj) {
        while (Job.DefaultImpls.invokeOnCompletion$default(childHandleNode.childJob, false, new ChildCompletion(this, finishing, childHandleNode, obj), 1) == NonDisposableHandle.INSTANCE) {
            childHandleNode = nextChild(childHandleNode);
            if (childHandleNode == null) {
                return false;
            }
        }
        return true;
    }

    public JobSupport(boolean z) {
        Empty empty;
        if (z) {
            empty = JobSupportKt.EMPTY_ACTIVE;
        } else {
            empty = JobSupportKt.EMPTY_NEW;
        }
        this._state = AtomicFU.atomic(empty);
        this._parentHandle = AtomicFU.atomic((Object) null);
    }

    public static void addSuppressedExceptions(Throwable th, ArrayList arrayList) {
        Throwable th2;
        if (arrayList.size() > 1) {
            Set newSetFromMap = Collections.newSetFromMap(new IdentityHashMap(arrayList.size()));
            if (!DebugKt.RECOVER_STACK_TRACES) {
                th2 = th;
            } else {
                th2 = StackTraceRecoveryKt.unwrapImpl(th);
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Throwable th3 = (Throwable) it.next();
                if (DebugKt.RECOVER_STACK_TRACES) {
                    th3 = StackTraceRecoveryKt.unwrapImpl(th3);
                }
                if (th3 != th && th3 != th2 && !(th3 instanceof CancellationException) && newSetFromMap.add(th3)) {
                    ExceptionsKt.addSuppressed(th, th3);
                }
            }
        }
    }

    public static ChildHandleNode nextChild(LockFreeLinkedListNode lockFreeLinkedListNode) {
        while (lockFreeLinkedListNode.isRemoved()) {
            lockFreeLinkedListNode = lockFreeLinkedListNode.getPrevNode();
        }
        while (true) {
            lockFreeLinkedListNode = lockFreeLinkedListNode.getNextNode();
            if (!lockFreeLinkedListNode.isRemoved()) {
                if (lockFreeLinkedListNode instanceof ChildHandleNode) {
                    return (ChildHandleNode) lockFreeLinkedListNode;
                }
                if (lockFreeLinkedListNode instanceof NodeList) {
                    return null;
                }
            }
        }
    }

    public final boolean cancelParent(Throwable th) {
        if (isScopedCoroutine()) {
            return true;
        }
        boolean z = th instanceof CancellationException;
        AtomicRef<ChildHandle> atomicRef = this._parentHandle;
        Objects.requireNonNull(atomicRef);
        ChildHandle childHandle = (ChildHandle) atomicRef.value;
        if (childHandle == null || childHandle == NonDisposableHandle.INSTANCE) {
            return z;
        }
        if (childHandle.childCancelled(th) || z) {
            return true;
        }
        return false;
    }

    public final <R> R fold(R r, Function2<? super R, ? super CoroutineContext.Element, ? extends R> function2) {
        return function2.invoke(r, this);
    }

    public final <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> key) {
        return CoroutineContext.Element.DefaultImpls.get(this, key);
    }

    public final CancellationException getCancellationException() {
        Object state$external__kotlinx_coroutines__android_common__kotlinx_coroutines = mo21281x8adbf455();
        CancellationException cancellationException = null;
        if (state$external__kotlinx_coroutines__android_common__kotlinx_coroutines instanceof Finishing) {
            Throwable rootCause = ((Finishing) state$external__kotlinx_coroutines__android_common__kotlinx_coroutines).getRootCause();
            if (rootCause != null) {
                String stringPlus = Intrinsics.stringPlus(DebugStringsKt.getClassSimpleName(this), " is cancelling");
                if (rootCause instanceof CancellationException) {
                    cancellationException = (CancellationException) rootCause;
                }
                if (cancellationException == null) {
                    if (stringPlus == null) {
                        stringPlus = cancellationExceptionMessage();
                    }
                    cancellationException = new JobCancellationException(stringPlus, rootCause, this);
                }
            }
            if (cancellationException != null) {
                return cancellationException;
            }
            throw new IllegalStateException(Intrinsics.stringPlus("Job is still new or active: ", this).toString());
        } else if (state$external__kotlinx_coroutines__android_common__kotlinx_coroutines instanceof Incomplete) {
            throw new IllegalStateException(Intrinsics.stringPlus("Job is still new or active: ", this).toString());
        } else if (!(state$external__kotlinx_coroutines__android_common__kotlinx_coroutines instanceof CompletedExceptionally)) {
            return new JobCancellationException(Intrinsics.stringPlus(DebugStringsKt.getClassSimpleName(this), " has completed normally"), (Throwable) null, this);
        } else {
            Throwable th = ((CompletedExceptionally) state$external__kotlinx_coroutines__android_common__kotlinx_coroutines).cause;
            if (th instanceof CancellationException) {
                cancellationException = (CancellationException) th;
            }
            if (cancellationException == null) {
                return new JobCancellationException(cancellationExceptionMessage(), th, this);
            }
            return cancellationException;
        }
    }

    public final CancellationException getChildJobCancellationCause() {
        Throwable th;
        Object state$external__kotlinx_coroutines__android_common__kotlinx_coroutines = mo21281x8adbf455();
        CancellationException cancellationException = null;
        if (state$external__kotlinx_coroutines__android_common__kotlinx_coroutines instanceof Finishing) {
            th = ((Finishing) state$external__kotlinx_coroutines__android_common__kotlinx_coroutines).getRootCause();
        } else if (state$external__kotlinx_coroutines__android_common__kotlinx_coroutines instanceof CompletedExceptionally) {
            th = ((CompletedExceptionally) state$external__kotlinx_coroutines__android_common__kotlinx_coroutines).cause;
        } else if (!(state$external__kotlinx_coroutines__android_common__kotlinx_coroutines instanceof Incomplete)) {
            th = null;
        } else {
            throw new IllegalStateException(Intrinsics.stringPlus("Cannot be cancelling child in this state: ", state$external__kotlinx_coroutines__android_common__kotlinx_coroutines).toString());
        }
        if (th instanceof CancellationException) {
            cancellationException = (CancellationException) th;
        }
        if (cancellationException == null) {
            return new JobCancellationException(Intrinsics.stringPlus("Parent job is ", stateString(state$external__kotlinx_coroutines__android_common__kotlinx_coroutines)), th, this);
        }
        return cancellationException;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v0, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v0, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: java.lang.Throwable} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Throwable getFinalRootCause(kotlinx.coroutines.JobSupport.Finishing r5, java.util.ArrayList r6) {
        /*
            r4 = this;
            boolean r0 = r6.isEmpty()
            r1 = 0
            if (r0 == 0) goto L_0x0018
            boolean r5 = r5.isCancelling()
            if (r5 == 0) goto L_0x0017
            kotlinx.coroutines.JobCancellationException r5 = new kotlinx.coroutines.JobCancellationException
            java.lang.String r6 = r4.cancellationExceptionMessage()
            r5.<init>(r6, r1, r4)
            return r5
        L_0x0017:
            return r1
        L_0x0018:
            java.util.Iterator r4 = r6.iterator()
        L_0x001c:
            boolean r5 = r4.hasNext()
            r0 = 1
            if (r5 == 0) goto L_0x0030
            java.lang.Object r5 = r4.next()
            r2 = r5
            java.lang.Throwable r2 = (java.lang.Throwable) r2
            boolean r2 = r2 instanceof java.util.concurrent.CancellationException
            r2 = r2 ^ r0
            if (r2 == 0) goto L_0x001c
            goto L_0x0031
        L_0x0030:
            r5 = r1
        L_0x0031:
            java.lang.Throwable r5 = (java.lang.Throwable) r5
            if (r5 == 0) goto L_0x0036
            return r5
        L_0x0036:
            r4 = 0
            java.lang.Object r5 = r6.get(r4)
            java.lang.Throwable r5 = (java.lang.Throwable) r5
            boolean r2 = r5 instanceof kotlinx.coroutines.TimeoutCancellationException
            if (r2 == 0) goto L_0x0063
            java.util.Iterator r6 = r6.iterator()
        L_0x0045:
            boolean r2 = r6.hasNext()
            if (r2 == 0) goto L_0x005e
            java.lang.Object r2 = r6.next()
            r3 = r2
            java.lang.Throwable r3 = (java.lang.Throwable) r3
            if (r3 == r5) goto L_0x005a
            boolean r3 = r3 instanceof kotlinx.coroutines.TimeoutCancellationException
            if (r3 == 0) goto L_0x005a
            r3 = r0
            goto L_0x005b
        L_0x005a:
            r3 = r4
        L_0x005b:
            if (r3 == 0) goto L_0x0045
            r1 = r2
        L_0x005e:
            java.lang.Throwable r1 = (java.lang.Throwable) r1
            if (r1 == 0) goto L_0x0063
            return r1
        L_0x0063:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.JobSupport.getFinalRootCause(kotlinx.coroutines.JobSupport$Finishing, java.util.ArrayList):java.lang.Throwable");
    }

    public final NodeList getOrPromoteCancellingList(Incomplete incomplete) {
        NodeList list = incomplete.getList();
        if (list != null) {
            return list;
        }
        if (incomplete instanceof Empty) {
            return new NodeList();
        }
        if (incomplete instanceof JobNode) {
            promoteSingleToNodeList((JobNode) incomplete);
            return null;
        }
        throw new IllegalStateException(Intrinsics.stringPlus("State should have list: ", incomplete).toString());
    }

    public boolean isActive() {
        Object state$external__kotlinx_coroutines__android_common__kotlinx_coroutines = mo21281x8adbf455();
        if (!(state$external__kotlinx_coroutines__android_common__kotlinx_coroutines instanceof Incomplete) || !((Incomplete) state$external__kotlinx_coroutines__android_common__kotlinx_coroutines).isActive()) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP_START, MTH_ENTER_BLOCK] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object join(kotlin.coroutines.Continuation<? super kotlin.Unit> r5) {
        /*
            r4 = this;
        L_0x0000:
            java.lang.Object r0 = r4.mo21281x8adbf455()
            boolean r1 = r0 instanceof kotlinx.coroutines.Incomplete
            r2 = 0
            r3 = 1
            if (r1 != 0) goto L_0x000c
            r0 = r2
            goto L_0x0013
        L_0x000c:
            int r0 = r4.startInternal(r0)
            if (r0 < 0) goto L_0x0000
            r0 = r3
        L_0x0013:
            if (r0 != 0) goto L_0x001f
            kotlin.coroutines.CoroutineContext r4 = r5.getContext()
            kotlinx.coroutines.JobKt.ensureActive(r4)
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        L_0x001f:
            kotlinx.coroutines.CancellableContinuationImpl r0 = new kotlinx.coroutines.CancellableContinuationImpl
            kotlin.coroutines.Continuation r5 = androidx.preference.R$color.intercepted(r5)
            r0.<init>(r5, r3)
            r0.initCancellability()
            kotlinx.coroutines.ResumeOnCompletion r5 = new kotlinx.coroutines.ResumeOnCompletion
            r5.<init>(r0)
            kotlinx.coroutines.DisposableHandle r4 = r4.invokeOnCompletion(r2, r3, r5)
            kotlinx.coroutines.DisposeOnCancel r5 = new kotlinx.coroutines.DisposeOnCancel
            r5.<init>(r4)
            r0.invokeOnCancellation(r5)
            java.lang.Object r4 = r0.getResult()
            kotlin.coroutines.intrinsics.CoroutineSingletons r5 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            if (r4 != r5) goto L_0x0045
            goto L_0x0047
        L_0x0045:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
        L_0x0047:
            if (r4 != r5) goto L_0x004a
            return r4
        L_0x004a:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.JobSupport.join(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* renamed from: makeCompletingOnce$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public final Object mo21285xe12a6b8b(Object obj) {
        Object tryMakeCompleting;
        CompletedExceptionally completedExceptionally;
        do {
            tryMakeCompleting = tryMakeCompleting(mo21281x8adbf455(), obj);
            if (tryMakeCompleting == JobSupportKt.COMPLETING_ALREADY) {
                String str = "Job " + this + " is already complete or completing, but is being completed with " + obj;
                Throwable th = null;
                if (obj instanceof CompletedExceptionally) {
                    completedExceptionally = (CompletedExceptionally) obj;
                } else {
                    completedExceptionally = null;
                }
                if (completedExceptionally != null) {
                    th = completedExceptionally.cause;
                }
                throw new IllegalStateException(str, th);
            }
        } while (tryMakeCompleting == JobSupportKt.COMPLETING_RETRY);
        return tryMakeCompleting;
    }

    public final CoroutineContext minusKey(CoroutineContext.Key<?> key) {
        return CoroutineContext.Element.DefaultImpls.minusKey(this, key);
    }

    /* renamed from: nameString$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public String mo21179x29b568d4() {
        return DebugStringsKt.getClassSimpleName(this);
    }

    public final void notifyCancelling(NodeList nodeList, Throwable th) {
        CompletionHandlerException completionHandlerException;
        CompletionHandlerException completionHandlerException2 = null;
        for (LockFreeLinkedListNode lockFreeLinkedListNode = (LockFreeLinkedListNode) nodeList.getNext(); !Intrinsics.areEqual(lockFreeLinkedListNode, nodeList); lockFreeLinkedListNode = lockFreeLinkedListNode.getNextNode()) {
            if (lockFreeLinkedListNode instanceof JobCancellingNode) {
                JobNode jobNode = (JobNode) lockFreeLinkedListNode;
                try {
                    jobNode.invoke(th);
                } catch (Throwable th2) {
                    if (completionHandlerException2 == null) {
                        completionHandlerException = null;
                    } else {
                        ExceptionsKt.addSuppressed(completionHandlerException2, th2);
                        completionHandlerException = completionHandlerException2;
                    }
                    if (completionHandlerException == null) {
                        completionHandlerException2 = new CompletionHandlerException("Exception in completion handler " + jobNode + " for " + this, th2);
                    }
                }
            }
        }
        if (completionHandlerException2 != null) {
            mo21178x4a3c0b24(completionHandlerException2);
        }
        cancelParent(th);
    }

    public final CoroutineContext plus(CoroutineContext coroutineContext) {
        return CoroutineContext.DefaultImpls.plus(this, coroutineContext);
    }

    public final boolean start() {
        int startInternal;
        do {
            startInternal = startInternal(mo21281x8adbf455());
            if (startInternal == 0) {
                return false;
            }
        } while (startInternal != 1);
        return true;
    }

    /* renamed from: handleOnCompletionException$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public void mo21178x4a3c0b24(CompletionHandlerException completionHandlerException) {
        throw completionHandlerException;
    }

    public final void parentCancelled(JobSupport jobSupport) {
        mo21272x7bd83b56(jobSupport);
    }

    public final CoroutineContext.Key<?> getKey() {
        return Job.Key.$$INSTANCE;
    }
}
