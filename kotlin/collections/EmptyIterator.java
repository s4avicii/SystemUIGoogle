package kotlin.collections;

import java.util.ListIterator;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: Collections.kt */
public final class EmptyIterator implements ListIterator, KMappedMarker {
    public static final EmptyIterator INSTANCE = new EmptyIterator();

    public final boolean hasNext() {
        return false;
    }

    public final boolean hasPrevious() {
        return false;
    }

    public final int nextIndex() {
        return 0;
    }

    public final int previousIndex() {
        return -1;
    }

    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public final void add(Object obj) {
        Void voidR = (Void) obj;
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public final Object next() {
        throw new NoSuchElementException();
    }

    public final Object previous() {
        throw new NoSuchElementException();
    }

    public final void set(Object obj) {
        Void voidR = (Void) obj;
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
