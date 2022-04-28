package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.Iterator;
import java.util.Objects;

public final class Iterators {

    public static class PeekingImpl<E> implements PeekingIterator<E> {
        public boolean hasPeeked;
        public final Iterator<? extends E> iterator;
        public E peekedElement;

        public final boolean hasNext() {
            if (this.hasPeeked || this.iterator.hasNext()) {
                return true;
            }
            return false;
        }

        public final E next() {
            if (!this.hasPeeked) {
                return this.iterator.next();
            }
            E e = this.peekedElement;
            this.hasPeeked = false;
            this.peekedElement = null;
            return e;
        }

        public final void remove() {
            Preconditions.checkState(!this.hasPeeked, "Can't remove after you've peeked at next");
            this.iterator.remove();
        }

        public PeekingImpl(Iterator<? extends E> it) {
            Objects.requireNonNull(it);
            this.iterator = it;
        }
    }

    public static void clear(Iterator<?> it) {
        Objects.requireNonNull(it);
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }

    public static Object getNext(Iterator it) {
        if (it.hasNext()) {
            return it.next();
        }
        return null;
    }
}
