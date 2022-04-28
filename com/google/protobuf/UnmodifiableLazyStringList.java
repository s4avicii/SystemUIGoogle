package com.google.protobuf;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

public final class UnmodifiableLazyStringList extends AbstractList<String> implements LazyStringList, RandomAccess {
    public final LazyStringList list;

    public final LazyStringList getUnmodifiableView() {
        return this;
    }

    public final Object get(int i) {
        return (String) this.list.get(i);
    }

    public final List<?> getUnderlyingElements() {
        return this.list.getUnderlyingElements();
    }

    public final Iterator<String> iterator() {
        return new Iterator<String>(this) {
            public Iterator<String> iter;

            public final boolean hasNext() {
                return this.iter.hasNext();
            }

            public final Object next() {
                return this.iter.next();
            }

            public final void remove() {
                throw new UnsupportedOperationException();
            }

            {
                this.iter = r1.list.iterator();
            }
        };
    }

    public final ListIterator<String> listIterator(int i) {
        return new ListIterator<String>(this, i) {
            public ListIterator<String> iter;

            public final void add(Object obj) {
                String str = (String) obj;
                throw new UnsupportedOperationException();
            }

            public final boolean hasNext() {
                return this.iter.hasNext();
            }

            public final boolean hasPrevious() {
                return this.iter.hasPrevious();
            }

            public final Object next() {
                return this.iter.next();
            }

            public final int nextIndex() {
                return this.iter.nextIndex();
            }

            public final Object previous() {
                return this.iter.previous();
            }

            public final int previousIndex() {
                return this.iter.previousIndex();
            }

            public final void remove() {
                throw new UnsupportedOperationException();
            }

            public final void set(Object obj) {
                String str = (String) obj;
                throw new UnsupportedOperationException();
            }

            {
                this.iter = r1.list.listIterator(r2);
            }
        };
    }

    public final int size() {
        return this.list.size();
    }

    public UnmodifiableLazyStringList(LazyStringList lazyStringList) {
        this.list = lazyStringList;
    }
}
