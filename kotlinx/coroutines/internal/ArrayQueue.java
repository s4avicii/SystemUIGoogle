package kotlinx.coroutines.internal;

/* compiled from: ArrayQueue.kt */
public final class ArrayQueue<T> {
    public Object[] elements = new Object[16];
    public int head;
    public int tail;
}
