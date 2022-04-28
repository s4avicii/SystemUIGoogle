package kotlin.collections;

import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: AbstractList.kt */
public abstract class AbstractList<E> extends AbstractCollection<E> implements List<E> {

    /* compiled from: AbstractList.kt */
    public class IteratorImpl implements Iterator<E>, KMappedMarker {
        public int index;

        public final void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        public IteratorImpl() {
        }

        public final boolean hasNext() {
            int i = this.index;
            AbstractList<E> abstractList = AbstractList.this;
            Objects.requireNonNull(abstractList);
            if (i < abstractList.getSize()) {
                return true;
            }
            return false;
        }

        public final E next() {
            if (hasNext()) {
                AbstractList<E> abstractList = AbstractList.this;
                int i = this.index;
                this.index = i + 1;
                return abstractList.get(i);
            }
            throw new NoSuchElementException();
        }
    }

    /* compiled from: AbstractList.kt */
    public class ListIteratorImpl extends AbstractList<E>.IteratorImpl implements ListIterator<E> {
        public final void add(E e) {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        public final void set(E e) {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        public ListIteratorImpl(int i) {
            super();
            Objects.requireNonNull(AbstractList.this);
            int size = AbstractList.this.getSize();
            if (i < 0 || i > size) {
                throw new IndexOutOfBoundsException("index: " + i + ", size: " + size);
            }
            this.index = i;
        }

        public final boolean hasPrevious() {
            if (this.index > 0) {
                return true;
            }
            return false;
        }

        public final int previousIndex() {
            return this.index - 1;
        }

        public final E previous() {
            if (hasPrevious()) {
                AbstractList<E> abstractList = AbstractList.this;
                int i = this.index - 1;
                this.index = i;
                return abstractList.get(i);
            }
            throw new NoSuchElementException();
        }

        public final int nextIndex() {
            return this.index;
        }
    }

    /* compiled from: AbstractList.kt */
    public static final class SubList<E> extends AbstractList<E> implements RandomAccess {
        public int _size;
        public final int fromIndex;
        public final AbstractList<E> list;

        public final E get(int i) {
            int i2 = this._size;
            if (i >= 0 && i < i2) {
                return this.list.get(this.fromIndex + i);
            }
            throw new IndexOutOfBoundsException("index: " + i + ", size: " + i2);
        }

        public SubList(AbstractList<? extends E> abstractList, int i, int i2) {
            this.list = abstractList;
            this.fromIndex = i;
            Objects.requireNonNull(abstractList);
            int size = abstractList.getSize();
            if (i < 0 || i2 > size) {
                StringBuilder m = GridLayoutManager$$ExternalSyntheticOutline0.m19m("fromIndex: ", i, ", toIndex: ", i2, ", size: ");
                m.append(size);
                throw new IndexOutOfBoundsException(m.toString());
            } else if (i <= i2) {
                this._size = i2 - i;
            } else {
                throw new IllegalArgumentException("fromIndex: " + i + " > toIndex: " + i2);
            }
        }

        public final int getSize() {
            return this._size;
        }
    }

    public final void add(int i, E e) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public final boolean addAll(int i, Collection<? extends E> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof List)) {
            return false;
        }
        Collection collection = (Collection) obj;
        if (size() == collection.size()) {
            Iterator it = collection.iterator();
            for (Object areEqual : this) {
                if (!Intrinsics.areEqual(areEqual, it.next())) {
                }
            }
            return true;
        }
        return false;
    }

    public abstract E get(int i);

    public final ListIterator<E> listIterator() {
        return new ListIteratorImpl(0);
    }

    public final E remove(int i) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public final E set(int i, E e) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public final Iterator<E> iterator() {
        return new IteratorImpl();
    }

    public final ListIterator<E> listIterator(int i) {
        return new ListIteratorImpl(i);
    }

    public final List<E> subList(int i, int i2) {
        return new SubList(this, i, i2);
    }

    public final int hashCode() {
        int i;
        int i2 = 1;
        for (Object next : this) {
            int i3 = i2 * 31;
            if (next == null) {
                i = 0;
            } else {
                i = next.hashCode();
            }
            i2 = i3 + i;
        }
        return i2;
    }

    public int indexOf(E e) {
        int i = 0;
        for (Object areEqual : this) {
            if (Intrinsics.areEqual(areEqual, e)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public int lastIndexOf(E e) {
        ListIterator listIterator = listIterator(size());
        while (listIterator.hasPrevious()) {
            if (Intrinsics.areEqual(listIterator.previous(), e)) {
                return listIterator.nextIndex();
            }
        }
        return -1;
    }
}
