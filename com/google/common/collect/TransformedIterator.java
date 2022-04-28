package com.google.common.collect;

import java.util.Iterator;
import java.util.Objects;

public abstract class TransformedIterator<F, T> implements Iterator<T> {
    public final Iterator<? extends F> backingIterator;

    public abstract T transform(F f);

    public final boolean hasNext() {
        return this.backingIterator.hasNext();
    }

    public final T next() {
        return transform(this.backingIterator.next());
    }

    public final void remove() {
        this.backingIterator.remove();
    }

    public TransformedIterator(Iterator<? extends F> it) {
        Objects.requireNonNull(it);
        this.backingIterator = it;
    }
}
