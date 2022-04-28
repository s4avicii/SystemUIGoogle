package kotlinx.coroutines.internal;

import androidx.slice.view.R$id;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;

/* compiled from: Atomic.kt */
public abstract class AtomicOp<T> extends OpDescriptor {
    public final AtomicRef<Object> _consensus = new AtomicRef<>(R$id.NO_DECISION);

    public abstract Symbol prepare(Object obj);

    public final Object perform(Object obj) {
        boolean z;
        LockFreeLinkedListNode lockFreeLinkedListNode;
        AtomicRef<Object> atomicRef = this._consensus;
        Objects.requireNonNull(atomicRef);
        T t = atomicRef.value;
        T t2 = R$id.NO_DECISION;
        if (t == t2) {
            t = prepare(obj);
            boolean z2 = DebugKt.DEBUG;
            AtomicRef<Object> atomicRef2 = this._consensus;
            Objects.requireNonNull(atomicRef2);
            T t3 = atomicRef2.value;
            if (t3 != t2) {
                t = t3;
            } else if (!this._consensus.compareAndSet(t2, t)) {
                AtomicRef<Object> atomicRef3 = this._consensus;
                Objects.requireNonNull(atomicRef3);
                t = atomicRef3.value;
            }
        }
        LockFreeLinkedListNode.CondAddOp condAddOp = (LockFreeLinkedListNode.CondAddOp) this;
        LockFreeLinkedListNode lockFreeLinkedListNode2 = (LockFreeLinkedListNode) obj;
        if (t == null) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            lockFreeLinkedListNode = condAddOp.newNode;
        } else {
            lockFreeLinkedListNode = condAddOp.oldNext;
        }
        if (lockFreeLinkedListNode != null && lockFreeLinkedListNode2._next.compareAndSet(condAddOp, lockFreeLinkedListNode) && z) {
            LockFreeLinkedListNode lockFreeLinkedListNode3 = condAddOp.newNode;
            LockFreeLinkedListNode lockFreeLinkedListNode4 = condAddOp.oldNext;
            Intrinsics.checkNotNull(lockFreeLinkedListNode4);
            lockFreeLinkedListNode3.finishAdd(lockFreeLinkedListNode4);
        }
        return t;
    }
}
