package kotlinx.coroutines.channels;

import kotlinx.coroutines.internal.LockFreeLinkedListKt;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.Symbol;

/* renamed from: kotlinx.coroutines.channels.AbstractChannel$enqueueReceiveInternal$$inlined$addLastIfPrevAndIf$1 */
/* compiled from: LockFreeLinkedList.kt */
public final class C2500x75f916ce extends LockFreeLinkedListNode.CondAddOp {
    public final /* synthetic */ AbstractChannel this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C2500x75f916ce(LockFreeLinkedListNode lockFreeLinkedListNode, AbstractChannel abstractChannel) {
        super(lockFreeLinkedListNode);
        this.this$0 = abstractChannel;
    }

    public final Symbol prepare(Object obj) {
        LockFreeLinkedListNode lockFreeLinkedListNode = (LockFreeLinkedListNode) obj;
        if (this.this$0.isBufferEmpty()) {
            return null;
        }
        return LockFreeLinkedListKt.CONDITION_FALSE;
    }
}
