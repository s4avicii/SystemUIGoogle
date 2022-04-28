package kotlinx.coroutines.channels;

import kotlinx.coroutines.internal.LockFreeLinkedListKt;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: LockFreeLinkedList.kt */
public final class AbstractSendChannel$enqueueSend$$inlined$addLastIfPrevAndIf$1 extends LockFreeLinkedListNode.CondAddOp {
    public final /* synthetic */ AbstractSendChannel this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public AbstractSendChannel$enqueueSend$$inlined$addLastIfPrevAndIf$1(SendElement sendElement, AbstractSendChannel abstractSendChannel) {
        super(sendElement);
        this.this$0 = abstractSendChannel;
    }

    public final Symbol prepare(Object obj) {
        LockFreeLinkedListNode lockFreeLinkedListNode = (LockFreeLinkedListNode) obj;
        if (this.this$0.isBufferFull()) {
            return null;
        }
        return LockFreeLinkedListKt.CONDITION_FALSE;
    }
}
