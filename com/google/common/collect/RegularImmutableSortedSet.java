package com.google.common.collect;

import androidx.leanback.R$id;
import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

final class RegularImmutableSortedSet<E> extends ImmutableSortedSet<E> {
    public static final RegularImmutableSortedSet<Comparable> NATURAL_EMPTY_SET = new RegularImmutableSortedSet<>(RegularImmutableList.EMPTY, NaturalOrdering.INSTANCE);
    public final transient ImmutableList<E> elements;

    public final E ceiling(E e) {
        int tailIndex = tailIndex(e, true);
        if (tailIndex == size()) {
            return null;
        }
        return this.elements.get(tailIndex);
    }

    public final boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            return Collections.binarySearch(this.elements, obj, this.comparator) >= 0;
        } catch (ClassCastException unused) {
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0037 A[Catch:{ ClassCastException | NoSuchElementException -> 0x004b }] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x004a A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(java.lang.Object r7) {
        /*
            r6 = this;
            r0 = 1
            if (r7 != r6) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r7 instanceof java.util.Set
            r2 = 0
            if (r1 != 0) goto L_0x000a
            return r2
        L_0x000a:
            java.util.Set r7 = (java.util.Set) r7
            int r1 = r6.size()
            int r3 = r7.size()
            if (r1 == r3) goto L_0x0017
            return r2
        L_0x0017:
            boolean r1 = r6.isEmpty()
            if (r1 == 0) goto L_0x001e
            return r0
        L_0x001e:
            java.util.Comparator<? super E> r1 = r6.comparator
            boolean r1 = androidx.leanback.R$id.hasSameComparator(r1, r7)
            if (r1 == 0) goto L_0x004c
            java.util.Iterator r7 = r7.iterator()
            com.google.common.collect.UnmodifiableIterator r1 = r6.iterator()     // Catch:{ ClassCastException | NoSuchElementException -> 0x004b }
        L_0x002e:
            r3 = r1
            com.google.common.collect.AbstractIndexedListIterator r3 = (com.google.common.collect.AbstractIndexedListIterator) r3     // Catch:{ ClassCastException | NoSuchElementException -> 0x004b }
            boolean r4 = r3.hasNext()     // Catch:{ ClassCastException | NoSuchElementException -> 0x004b }
            if (r4 == 0) goto L_0x004a
            java.lang.Object r3 = r3.next()     // Catch:{ ClassCastException | NoSuchElementException -> 0x004b }
            java.lang.Object r4 = r7.next()     // Catch:{ ClassCastException | NoSuchElementException -> 0x004b }
            if (r4 == 0) goto L_0x0049
            java.util.Comparator<? super E> r5 = r6.comparator     // Catch:{ ClassCastException | NoSuchElementException -> 0x004b }
            int r3 = r5.compare(r3, r4)     // Catch:{ ClassCastException | NoSuchElementException -> 0x004b }
            if (r3 == 0) goto L_0x002e
        L_0x0049:
            return r2
        L_0x004a:
            return r0
        L_0x004b:
            return r2
        L_0x004c:
            boolean r6 = r6.containsAll(r7)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.RegularImmutableSortedSet.equals(java.lang.Object):boolean");
    }

    public final E floor(E e) {
        int headIndex = headIndex(e, true) - 1;
        if (headIndex == -1) {
            return null;
        }
        return this.elements.get(headIndex);
    }

    public final E higher(E e) {
        int tailIndex = tailIndex(e, false);
        if (tailIndex == size()) {
            return null;
        }
        return this.elements.get(tailIndex);
    }

    public final E lower(E e) {
        int headIndex = headIndex(e, false) - 1;
        if (headIndex == -1) {
            return null;
        }
        return this.elements.get(headIndex);
    }

    static {
        ImmutableList.Itr itr = ImmutableList.EMPTY_ITR;
    }

    public final boolean containsAll(Collection<?> collection) {
        if (collection instanceof Multiset) {
            collection = ((Multiset) collection).elementSet();
        }
        if (!R$id.hasSameComparator(this.comparator, collection) || collection.size() <= 1) {
            return super.containsAll(collection);
        }
        UnmodifiableIterator it = iterator();
        Iterator<?> it2 = collection.iterator();
        AbstractIndexedListIterator abstractIndexedListIterator = (AbstractIndexedListIterator) it;
        if (!abstractIndexedListIterator.hasNext()) {
            return false;
        }
        Object next = it2.next();
        Object next2 = abstractIndexedListIterator.next();
        while (true) {
            try {
                int compare = this.comparator.compare(next2, next);
                if (compare < 0) {
                    if (!abstractIndexedListIterator.hasNext()) {
                        return false;
                    }
                    next2 = abstractIndexedListIterator.next();
                } else if (compare == 0) {
                    if (!it2.hasNext()) {
                        return true;
                    }
                    next = it2.next();
                } else if (compare > 0) {
                    break;
                }
            } catch (ClassCastException | NullPointerException unused) {
            }
        }
        return false;
    }

    public final int copyIntoArray(Object[] objArr) {
        return this.elements.copyIntoArray(objArr);
    }

    public final ImmutableSortedSet<E> createDescendingSet() {
        Comparator<? super E> reverseOrder = Collections.reverseOrder(this.comparator);
        if (isEmpty()) {
            return ImmutableSortedSet.emptySet(reverseOrder);
        }
        return new RegularImmutableSortedSet(this.elements.reverse(), reverseOrder);
    }

    public final ImmutableList.Itr descendingIterator() {
        ImmutableList<E> reverse = this.elements.reverse();
        Objects.requireNonNull(reverse);
        return reverse.listIterator(0);
    }

    public final RegularImmutableSortedSet<E> getSubSet(int i, int i2) {
        if (i == 0 && i2 == size()) {
            return this;
        }
        if (i < i2) {
            return new RegularImmutableSortedSet<>(this.elements.subList(i, i2), this.comparator);
        }
        return ImmutableSortedSet.emptySet(this.comparator);
    }

    public final int headIndex(E e, boolean z) {
        ImmutableList<E> immutableList = this.elements;
        Objects.requireNonNull(e);
        int binarySearch = Collections.binarySearch(immutableList, e, this.comparator);
        if (binarySearch < 0) {
            return ~binarySearch;
        }
        if (z) {
            return binarySearch + 1;
        }
        return binarySearch;
    }

    public final Object[] internalArray() {
        return this.elements.internalArray();
    }

    public final int internalArrayEnd() {
        return this.elements.internalArrayEnd();
    }

    public final int internalArrayStart() {
        return this.elements.internalArrayStart();
    }

    public final UnmodifiableIterator<E> iterator() {
        ImmutableList<E> immutableList = this.elements;
        Objects.requireNonNull(immutableList);
        return immutableList.listIterator(0);
    }

    public final int size() {
        return this.elements.size();
    }

    public final int tailIndex(E e, boolean z) {
        ImmutableList<E> immutableList = this.elements;
        Objects.requireNonNull(e);
        int binarySearch = Collections.binarySearch(immutableList, e, this.comparator);
        if (binarySearch < 0) {
            return ~binarySearch;
        }
        if (z) {
            return binarySearch;
        }
        return binarySearch + 1;
    }

    public RegularImmutableSortedSet(ImmutableList<E> immutableList, Comparator<? super E> comparator) {
        super(comparator);
        this.elements = immutableList;
    }

    public final E first() {
        if (!isEmpty()) {
            return this.elements.get(0);
        }
        throw new NoSuchElementException();
    }

    public final ImmutableSortedSet<E> headSetImpl(E e, boolean z) {
        return getSubSet(0, headIndex(e, z));
    }

    public final E last() {
        if (!isEmpty()) {
            return this.elements.get(size() - 1);
        }
        throw new NoSuchElementException();
    }

    public final ImmutableSortedSet<E> subSetImpl(E e, boolean z, E e2, boolean z2) {
        RegularImmutableSortedSet regularImmutableSortedSet = (RegularImmutableSortedSet) tailSetImpl(e, z);
        Objects.requireNonNull(regularImmutableSortedSet);
        return regularImmutableSortedSet.getSubSet(0, regularImmutableSortedSet.headIndex(e2, z2));
    }

    public final ImmutableSortedSet<E> tailSetImpl(E e, boolean z) {
        return getSubSet(tailIndex(e, z), size());
    }

    public final ImmutableList<E> asList() {
        return this.elements;
    }
}
