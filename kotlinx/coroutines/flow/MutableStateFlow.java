package kotlinx.coroutines.flow;

/* compiled from: StateFlow.kt */
public interface MutableStateFlow<T> extends StateFlow<T>, MutableSharedFlow<T> {
    T getValue();

    void setValue(T t);
}
