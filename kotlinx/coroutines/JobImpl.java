package kotlinx.coroutines;

import java.util.Objects;
import kotlinx.atomicfu.AtomicRef;

/* compiled from: JobSupport.kt */
public class JobImpl extends JobSupport {
    public final boolean handlesException;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public JobImpl(Job job) {
        super(true);
        ChildHandleNode childHandleNode;
        JobSupport jobSupport;
        ChildHandleNode childHandleNode2;
        JobSupport job2;
        boolean z = true;
        initParentJob(job);
        AtomicRef<ChildHandle> atomicRef = this._parentHandle;
        Objects.requireNonNull(atomicRef);
        ChildHandle childHandle = (ChildHandle) atomicRef.value;
        if (childHandle instanceof ChildHandleNode) {
            childHandleNode = (ChildHandleNode) childHandle;
        } else {
            childHandleNode = null;
        }
        if (childHandleNode == null) {
            jobSupport = null;
        } else {
            jobSupport = childHandleNode.getJob();
        }
        if (jobSupport != null) {
            while (true) {
                if (!jobSupport.mo21268x3da20e56()) {
                    AtomicRef<ChildHandle> atomicRef2 = jobSupport._parentHandle;
                    Objects.requireNonNull(atomicRef2);
                    ChildHandle childHandle2 = (ChildHandle) atomicRef2.value;
                    if (childHandle2 instanceof ChildHandleNode) {
                        childHandleNode2 = (ChildHandleNode) childHandle2;
                    } else {
                        childHandleNode2 = null;
                    }
                    if (childHandleNode2 == null) {
                        job2 = null;
                        continue;
                    } else {
                        job2 = childHandleNode2.getJob();
                        continue;
                    }
                    if (jobSupport == null) {
                        break;
                    }
                } else {
                    break;
                }
            }
        } else {
            z = false;
        }
        this.handlesException = z;
    }

    /* renamed from: getOnCancelComplete$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public final boolean mo21269x6aca53c8() {
        return true;
    }

    /* renamed from: getHandlesException$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public final boolean mo21268x3da20e56() {
        return this.handlesException;
    }
}
