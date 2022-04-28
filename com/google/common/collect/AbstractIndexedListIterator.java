package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.DoNotCall;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public abstract class AbstractIndexedListIterator<E> extends UnmodifiableIterator<Object> implements ListIterator<Object> {
    public int position;
    public final int size;

    public final /* bridge */ /* synthetic */ void add(Object obj) {
        add$com$google$common$collect$UnmodifiableListIterator(obj);
        throw null;
    }

    public final /* bridge */ /* synthetic */ void set(Object obj) {
        set$com$google$common$collect$UnmodifiableListIterator(obj);
        throw null;
    }

    @DoNotCall("Always throws UnsupportedOperationException")
    @Deprecated
    public final void add$com$google$common$collect$UnmodifiableListIterator(Object obj) {
        throw new UnsupportedOperationException();
    }

    public final boolean hasNext() {
        if (this.position < this.size) {
            return true;
        }
        return false;
    }

    public final boolean hasPrevious() {
        if (this.position > 0) {
            return true;
        }
        return false;
    }

    public final int previousIndex() {
        return this.position - 1;
    }

    @DoNotCall("Always throws UnsupportedOperationException")
    @Deprecated
    public final void set$com$google$common$collect$UnmodifiableListIterator(Object obj) {
        throw new UnsupportedOperationException();
    }

    public AbstractIndexedListIterator(int i, int i2) {
        Preconditions.checkPositionIndex(i2, i);
        this.size = i;
        this.position = i2;
    }

    public final E next() {
        if (hasNext()) {
            int i = this.position;
            this.position = i + 1;
            return ((ImmutableList.Itr) this).list.get(i);
        }
        throw new NoSuchElementException();
    }

    public final E previous() {
        if (hasPrevious()) {
            int i = this.position - 1;
            this.position = i;
            return ((ImmutableList.Itr) this).list.get(i);
        }
        throw new NoSuchElementException();
    }

    public final int nextIndex() {
        return this.position;
    }
}
