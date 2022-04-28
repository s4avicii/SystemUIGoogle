package kotlinx.coroutines.channels;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@DebugMetadata(mo21073c = "kotlinx.coroutines.channels.AbstractChannel", mo21074f = "AbstractChannel.kt", mo21075l = {633}, mo21076m = "receiveCatching-JP2dKIU")
/* compiled from: AbstractChannel.kt */
public final class AbstractChannel$receiveCatching$1 extends ContinuationImpl {
    public int label;
    public /* synthetic */ Object result;
    public final /* synthetic */ AbstractChannel<E> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public AbstractChannel$receiveCatching$1(AbstractChannel<E> abstractChannel, Continuation<? super AbstractChannel$receiveCatching$1> continuation) {
        super(continuation);
        this.this$0 = abstractChannel;
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        Object r1 = this.this$0.m322receiveCatchingJP2dKIU(this);
        if (r1 == CoroutineSingletons.COROUTINE_SUSPENDED) {
            return r1;
        }
        return new ChannelResult(r1);
    }
}
