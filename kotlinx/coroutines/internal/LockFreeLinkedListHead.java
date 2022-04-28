package kotlinx.coroutines.internal;

/* compiled from: LockFreeLinkedList.kt */
public class LockFreeLinkedListHead extends LockFreeLinkedListNode {
    public final boolean isRemoved() {
        return false;
    }

    public final boolean remove() {
        throw new IllegalStateException("head cannot be removed".toString());
    }
}
