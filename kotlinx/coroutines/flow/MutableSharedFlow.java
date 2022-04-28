package kotlinx.coroutines.flow;

import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* compiled from: SharedFlow.kt */
public interface MutableSharedFlow<T> extends Flow, FlowCollector<T> {
    Object emit(T t, Continuation<? super Unit> continuation);

    boolean tryEmit(T t);
}
