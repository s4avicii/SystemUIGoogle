package com.google.common.collect;

import java.io.Serializable;
import java.util.Objects;

final class ReverseOrdering<T> extends Ordering<T> implements Serializable {
    private static final long serialVersionUID = 0;
    public final Ordering<? super T> forwardOrder;

    public final int compare(T t, T t2) {
        return this.forwardOrder.compare(t2, t);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ReverseOrdering) {
            return this.forwardOrder.equals(((ReverseOrdering) obj).forwardOrder);
        }
        return false;
    }

    public final int hashCode() {
        return -this.forwardOrder.hashCode();
    }

    public final String toString() {
        return this.forwardOrder + ".reverse()";
    }

    public ReverseOrdering(Ordering<? super T> ordering) {
        Objects.requireNonNull(ordering);
        this.forwardOrder = ordering;
    }

    public final <S extends T> Ordering<S> reverse() {
        return this.forwardOrder;
    }
}
