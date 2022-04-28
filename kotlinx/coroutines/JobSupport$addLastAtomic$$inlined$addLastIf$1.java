package kotlinx.coroutines;

import kotlinx.coroutines.internal.LockFreeLinkedListKt;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: LockFreeLinkedList.kt */
public final class JobSupport$addLastAtomic$$inlined$addLastIf$1 extends LockFreeLinkedListNode.CondAddOp {
    public final /* synthetic */ Object $expect$inlined;
    public final /* synthetic */ JobSupport this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public JobSupport$addLastAtomic$$inlined$addLastIf$1(LockFreeLinkedListNode lockFreeLinkedListNode, JobSupport jobSupport, Object obj) {
        super(lockFreeLinkedListNode);
        this.this$0 = jobSupport;
        this.$expect$inlined = obj;
    }

    public final Symbol prepare(Object obj) {
        boolean z;
        LockFreeLinkedListNode lockFreeLinkedListNode = (LockFreeLinkedListNode) obj;
        if (this.this$0.mo21281x8adbf455() == this.$expect$inlined) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return null;
        }
        return LockFreeLinkedListKt.CONDITION_FALSE;
    }
}
