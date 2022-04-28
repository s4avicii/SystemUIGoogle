package kotlinx.coroutines.flow;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;

/* compiled from: Builders.kt */
public final class SafeFlow<T> extends AbstractFlow<T> {
    public final Function2<FlowCollector<? super T>, Continuation<? super Unit>, Object> block;

    public SafeFlow(Function2<? super FlowCollector<? super T>, ? super Continuation<? super Unit>, ? extends Object> function2) {
        this.block = function2;
    }
}
