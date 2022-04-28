package com.google.common.collect;

import java.util.Objects;
import okio.Okio__OkioKt;

final class RegularImmutableSet<E> extends ImmutableSet<E> {
    public static final RegularImmutableSet<Object> EMPTY;
    public static final Object[] EMPTY_ARRAY;
    public final transient Object[] elements;
    public final transient int hashCode;
    public final transient int mask;
    public final transient int size;
    public final transient Object[] table;

    static {
        Object[] objArr = new Object[0];
        EMPTY_ARRAY = objArr;
        EMPTY = new RegularImmutableSet(objArr, 0, objArr, 0, 0);
    }

    public final int internalArrayStart() {
        return 0;
    }

    public final boolean contains(Object obj) {
        Object[] objArr = this.table;
        if (obj == null || objArr.length == 0) {
            return false;
        }
        int smearedHash = Okio__OkioKt.smearedHash(obj);
        while (true) {
            int i = smearedHash & this.mask;
            Object obj2 = objArr[i];
            if (obj2 == null) {
                return false;
            }
            if (obj2.equals(obj)) {
                return true;
            }
            smearedHash = i + 1;
        }
    }

    public final int copyIntoArray(Object[] objArr) {
        System.arraycopy(this.elements, 0, objArr, 0, this.size);
        return 0 + this.size;
    }

    public final ImmutableList<E> createAsList() {
        return ImmutableList.asImmutableList(this.elements, this.size);
    }

    public final UnmodifiableIterator<E> iterator() {
        ImmutableList asList = asList();
        Objects.requireNonNull(asList);
        return asList.listIterator(0);
    }

    public RegularImmutableSet(Object[] objArr, int i, Object[] objArr2, int i2, int i3) {
        this.elements = objArr;
        this.hashCode = i;
        this.table = objArr2;
        this.mask = i2;
        this.size = i3;
    }

    public final int hashCode() {
        return this.hashCode;
    }

    public final Object[] internalArray() {
        return this.elements;
    }

    public final int internalArrayEnd() {
        return this.size;
    }

    public final int size() {
        return this.size;
    }
}
