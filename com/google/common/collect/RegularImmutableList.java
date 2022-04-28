package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.Objects;

class RegularImmutableList<E> extends ImmutableList<E> {
    public static final ImmutableList<Object> EMPTY = new RegularImmutableList(new Object[0], 0);
    public final transient Object[] array;
    public final transient int size;

    public final int internalArrayStart() {
        return 0;
    }

    public final int copyIntoArray(Object[] objArr) {
        System.arraycopy(this.array, 0, objArr, 0, this.size);
        return 0 + this.size;
    }

    public final E get(int i) {
        Preconditions.checkElementIndex(i, this.size);
        E e = this.array[i];
        Objects.requireNonNull(e);
        return e;
    }

    public RegularImmutableList(Object[] objArr, int i) {
        this.array = objArr;
        this.size = i;
    }

    public final Object[] internalArray() {
        return this.array;
    }

    public final int internalArrayEnd() {
        return this.size;
    }

    public final int size() {
        return this.size;
    }
}
