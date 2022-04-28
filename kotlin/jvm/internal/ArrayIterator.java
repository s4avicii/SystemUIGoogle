package kotlin.jvm.internal;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: ArrayIterator.kt */
public final class ArrayIterator<T> implements Iterator<T>, KMappedMarker {
    public final T[] array;
    public int index;

    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public final boolean hasNext() {
        if (this.index < this.array.length) {
            return true;
        }
        return false;
    }

    public final T next() {
        try {
            T[] tArr = this.array;
            int i = this.index;
            this.index = i + 1;
            return tArr[i];
        } catch (ArrayIndexOutOfBoundsException e) {
            this.index--;
            throw new NoSuchElementException(e.getMessage());
        }
    }

    public ArrayIterator(T[] tArr) {
        this.array = tArr;
    }
}
