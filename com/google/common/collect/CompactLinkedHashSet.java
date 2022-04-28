package com.google.common.collect;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

class CompactLinkedHashSet<E> extends CompactHashSet<E> {
    public transient int firstEntry;
    public transient int lastEntry;
    public transient int[] predecessor;
    public transient int[] successor;

    public CompactLinkedHashSet() {
    }

    public final void setSucceeds(int i, int i2) {
        if (i == -2) {
            this.firstEntry = i2;
        } else {
            int[] iArr = this.successor;
            Objects.requireNonNull(iArr);
            iArr[i] = i2 + 1;
        }
        if (i2 == -2) {
            this.lastEntry = i;
            return;
        }
        int[] iArr2 = this.predecessor;
        Objects.requireNonNull(iArr2);
        iArr2[i2] = i + 1;
    }

    public final <T> T[] toArray(T[] tArr) {
        int size = size();
        if (tArr.length < size) {
            tArr = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), size);
        }
        Iterator it = iterator();
        int i = 0;
        while (it.hasNext()) {
            tArr[i] = it.next();
            i++;
        }
        if (tArr.length > size) {
            tArr[size] = null;
        }
        return tArr;
    }

    public CompactLinkedHashSet(int i) {
        super(i);
    }

    public final int getSuccessor(int i) {
        int[] iArr = this.successor;
        Objects.requireNonNull(iArr);
        return iArr[i] - 1;
    }

    public final int adjustAfterRemove(int i, int i2) {
        if (i >= size()) {
            return i2;
        }
        return i;
    }

    public final int allocArrays() {
        int allocArrays = super.allocArrays();
        this.predecessor = new int[allocArrays];
        this.successor = new int[allocArrays];
        return allocArrays;
    }

    public final void clear() {
        if (!needsAllocArrays()) {
            this.firstEntry = -2;
            this.lastEntry = -2;
            int[] iArr = this.predecessor;
            if (!(iArr == null || this.successor == null)) {
                Arrays.fill(iArr, 0, size(), 0);
                Arrays.fill(this.successor, 0, size(), 0);
            }
            super.clear();
        }
    }

    @CanIgnoreReturnValue
    public final Set<E> convertToHashFloodingResistantImplementation() {
        Set<E> convertToHashFloodingResistantImplementation = super.convertToHashFloodingResistantImplementation();
        this.predecessor = null;
        this.successor = null;
        return convertToHashFloodingResistantImplementation;
    }

    public final void init(int i) {
        super.init(i);
        this.firstEntry = -2;
        this.lastEntry = -2;
    }

    public final void insertEntry(int i, E e, int i2, int i3) {
        super.insertEntry(i, e, i2, i3);
        setSucceeds(this.lastEntry, i);
        setSucceeds(i, -2);
    }

    public final void moveLastEntry(int i, int i2) {
        int size = size() - 1;
        super.moveLastEntry(i, i2);
        int[] iArr = this.predecessor;
        Objects.requireNonNull(iArr);
        setSucceeds(iArr[i] - 1, getSuccessor(i));
        if (i < size) {
            int[] iArr2 = this.predecessor;
            Objects.requireNonNull(iArr2);
            setSucceeds(iArr2[size] - 1, i);
            setSucceeds(i, getSuccessor(size));
        }
        int[] iArr3 = this.predecessor;
        Objects.requireNonNull(iArr3);
        iArr3[size] = 0;
        int[] iArr4 = this.successor;
        Objects.requireNonNull(iArr4);
        iArr4[size] = 0;
    }

    public final void resizeEntries(int i) {
        super.resizeEntries(i);
        int[] iArr = this.predecessor;
        Objects.requireNonNull(iArr);
        this.predecessor = Arrays.copyOf(iArr, i);
        int[] iArr2 = this.successor;
        Objects.requireNonNull(iArr2);
        this.successor = Arrays.copyOf(iArr2, i);
    }

    public final Object[] toArray() {
        Object[] objArr = new Object[size()];
        int i = 0;
        for (Object obj : this) {
            objArr[i] = obj;
            i++;
        }
        return objArr;
    }

    public final int firstEntryIndex() {
        return this.firstEntry;
    }
}
