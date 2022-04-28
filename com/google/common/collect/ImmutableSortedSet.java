package com.google.common.collect;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.DoNotCall;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.SortedSet;
import okio.Okio__OkioKt;

public abstract class ImmutableSortedSet<E> extends ImmutableSortedSetFauxverideShim<E> implements NavigableSet<E>, SortedIterable<E> {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final transient Comparator<? super E> comparator;
    @LazyInit
    public transient ImmutableSortedSet<E> descendingSet;

    public static class SerializedForm<E> implements Serializable {
        private static final long serialVersionUID = 0;
        public final Comparator<? super E> comparator;
        public final Object[] elements;

        public Object readResolve() {
            RegularImmutableSortedSet<E> regularImmutableSortedSet;
            Builder builder = new Builder(this.comparator);
            Object[] objArr = this.elements;
            if (builder.hashTable != null) {
                for (Object add : objArr) {
                    builder.add(add);
                }
            } else {
                int length = objArr.length;
                ObjectArrays.checkElementsNotNull(objArr, length);
                builder.getReadyToExpandTo(builder.size + length);
                System.arraycopy(objArr, 0, builder.contents, builder.size, length);
                builder.size += length;
            }
            Object[] objArr2 = builder.contents;
            Comparator<? super E> comparator2 = builder.comparator;
            int i = builder.size;
            int i2 = ImmutableSortedSet.$r8$clinit;
            if (i == 0) {
                regularImmutableSortedSet = ImmutableSortedSet.emptySet(comparator2);
            } else {
                ObjectArrays.checkElementsNotNull(objArr2, i);
                Arrays.sort(objArr2, 0, i, comparator2);
                int i3 = 1;
                for (int i4 = 1; i4 < i; i4++) {
                    Object obj = objArr2[i4];
                    if (comparator2.compare(obj, objArr2[i3 - 1]) != 0) {
                        objArr2[i3] = obj;
                        i3++;
                    }
                }
                Arrays.fill(objArr2, i3, i, (Object) null);
                if (i3 < objArr2.length / 2) {
                    objArr2 = Arrays.copyOf(objArr2, i3);
                }
                regularImmutableSortedSet = new RegularImmutableSortedSet<>(ImmutableList.asImmutableList(objArr2, i3), comparator2);
            }
            builder.size = regularImmutableSortedSet.size();
            builder.forceCopy = true;
            return regularImmutableSortedSet;
        }

        public SerializedForm(Comparator<? super E> comparator2, Object[] objArr) {
            this.comparator = comparator2;
            this.elements = objArr;
        }
    }

    public abstract ImmutableSortedSet<E> createDescendingSet();

    public abstract ImmutableList.Itr descendingIterator();

    public final NavigableSet headSet(Object obj, boolean z) {
        Objects.requireNonNull(obj);
        return headSetImpl(obj, z);
    }

    public abstract ImmutableSortedSet<E> headSetImpl(E e, boolean z);

    public final NavigableSet subSet(Object obj, boolean z, Object obj2, boolean z2) {
        Objects.requireNonNull(obj);
        Objects.requireNonNull(obj2);
        if (this.comparator.compare(obj, obj2) <= 0) {
            return subSetImpl(obj, z, obj2, z2);
        }
        throw new IllegalArgumentException();
    }

    public abstract ImmutableSortedSet<E> subSetImpl(E e, boolean z, E e2, boolean z2);

    public final NavigableSet tailSet(Object obj, boolean z) {
        Objects.requireNonNull(obj);
        return tailSetImpl(obj, z);
    }

    public abstract ImmutableSortedSet<E> tailSetImpl(E e, boolean z);

    public static final class Builder<E> extends ImmutableSet.Builder<E> {
        public final Comparator<? super E> comparator;

        public Builder(Comparator<? super E> comparator2) {
            Objects.requireNonNull(comparator2);
            this.comparator = comparator2;
        }

        @CanIgnoreReturnValue
        public final ImmutableSet.Builder add(Object obj) {
            Objects.requireNonNull(obj);
            if (this.hashTable != null) {
                int chooseTableSize = ImmutableSet.chooseTableSize(this.size);
                Object[] objArr = this.hashTable;
                if (chooseTableSize <= objArr.length) {
                    int length = objArr.length - 1;
                    int smear = Okio__OkioKt.smear(obj.hashCode());
                    while (true) {
                        int i = smear & length;
                        Object[] objArr2 = this.hashTable;
                        Object obj2 = objArr2[i];
                        if (obj2 == null) {
                            objArr2[i] = obj;
                            getReadyToExpandTo(this.size + 1);
                            Object[] objArr3 = this.contents;
                            int i2 = this.size;
                            this.size = i2 + 1;
                            objArr3[i2] = obj;
                            break;
                        } else if (obj2.equals(obj)) {
                            break;
                        } else {
                            smear = i + 1;
                        }
                    }
                    return this;
                }
            }
            this.hashTable = null;
            getReadyToExpandTo(this.size + 1);
            Object[] objArr4 = this.contents;
            int i3 = this.size;
            this.size = i3 + 1;
            objArr4[i3] = obj;
            return this;
        }
    }

    public static <E> RegularImmutableSortedSet<E> emptySet(Comparator<? super E> comparator2) {
        if (NaturalOrdering.INSTANCE.equals(comparator2)) {
            return RegularImmutableSortedSet.NATURAL_EMPTY_SET;
        }
        return new RegularImmutableSortedSet<>(RegularImmutableList.EMPTY, comparator2);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Use SerializedForm");
    }

    public final NavigableSet descendingSet() {
        ImmutableSortedSet<E> immutableSortedSet = this.descendingSet;
        if (immutableSortedSet != null) {
            return immutableSortedSet;
        }
        ImmutableSortedSet<E> createDescendingSet = createDescendingSet();
        this.descendingSet = createDescendingSet;
        createDescendingSet.descendingSet = this;
        return createDescendingSet;
    }

    @CanIgnoreReturnValue
    @DoNotCall("Always throws UnsupportedOperationException")
    @Deprecated
    public final E pollFirst() {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @DoNotCall("Always throws UnsupportedOperationException")
    @Deprecated
    public final E pollLast() {
        throw new UnsupportedOperationException();
    }

    public Object writeReplace() {
        return new SerializedForm(this.comparator, toArray());
    }

    public ImmutableSortedSet(Comparator<? super E> comparator2) {
        this.comparator = comparator2;
    }

    public E ceiling(E e) {
        Objects.requireNonNull(e);
        return Iterators.getNext(tailSetImpl(e, true).iterator());
    }

    public E first() {
        return iterator().next();
    }

    public E floor(E e) {
        Objects.requireNonNull(e);
        return Iterators.getNext(headSetImpl(e, true).descendingIterator());
    }

    public final SortedSet headSet(Object obj) {
        Objects.requireNonNull(obj);
        return headSetImpl(obj, false);
    }

    public E higher(E e) {
        Objects.requireNonNull(e);
        return Iterators.getNext(tailSetImpl(e, false).iterator());
    }

    public E last() {
        return descendingIterator().next();
    }

    public E lower(E e) {
        Objects.requireNonNull(e);
        return Iterators.getNext(headSetImpl(e, false).descendingIterator());
    }

    public final SortedSet tailSet(Object obj) {
        Objects.requireNonNull(obj);
        return tailSetImpl(obj, true);
    }

    public final SortedSet subSet(Object obj, Object obj2) {
        Objects.requireNonNull(obj);
        Objects.requireNonNull(obj2);
        if (this.comparator.compare(obj, obj2) <= 0) {
            return subSetImpl(obj, true, obj2, false);
        }
        throw new IllegalArgumentException();
    }

    public final Comparator<? super E> comparator() {
        return this.comparator;
    }
}
