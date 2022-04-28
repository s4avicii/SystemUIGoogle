package kotlin.collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import kotlin.jvm.internal.ArrayIterator;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: Collections.kt */
public final class ArrayAsCollection<T> implements Collection<T>, KMappedMarker {
    public final boolean isVarargs;
    public final T[] values;

    public final boolean add(T t) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public final boolean addAll(Collection<? extends T> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public final void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public final boolean remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public final boolean removeAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public final boolean retainAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public final <T> T[] toArray(T[] tArr) {
        return CollectionToArray.toArray(this, tArr);
    }

    public final boolean contains(Object obj) {
        return ArraysKt___ArraysKt.contains(this.values, obj);
    }

    public final boolean isEmpty() {
        if (this.values.length == 0) {
            return true;
        }
        return false;
    }

    public final Iterator<T> iterator() {
        return new ArrayIterator(this.values);
    }

    public final int size() {
        return this.values.length;
    }

    public final Object[] toArray() {
        T[] tArr = this.values;
        Class<Object[]> cls = Object[].class;
        if (!this.isVarargs || !Intrinsics.areEqual(tArr.getClass(), cls)) {
            return Arrays.copyOf(tArr, tArr.length, cls);
        }
        return tArr;
    }

    public ArrayAsCollection(T[] tArr, boolean z) {
        this.values = tArr;
        this.isVarargs = z;
    }

    public final boolean containsAll(Collection<? extends Object> collection) {
        if (collection.isEmpty()) {
            return true;
        }
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }
}
