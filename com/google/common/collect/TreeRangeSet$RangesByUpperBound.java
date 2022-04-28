package com.google.common.collect;

import androidx.leanback.R$layout;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Cut;
import com.google.common.collect.Iterators;
import java.lang.Comparable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Objects;

final class TreeRangeSet$RangesByUpperBound<C extends Comparable<?>> extends AbstractNavigableMap<Cut<C>, Range<C>> {
    public final NavigableMap<Cut<C>, Range<C>> rangesByLowerBound;
    public final Range<Cut<C>> upperBoundWindow;

    public final NavigableMap subMap(Object obj, boolean z, Object obj2, boolean z2) {
        Cut cut;
        Cut cut2;
        BoundType boundType = BoundType.CLOSED;
        BoundType boundType2 = BoundType.OPEN;
        Cut cut3 = (Cut) obj;
        Cut cut4 = (Cut) obj2;
        BoundType boundType3 = z ? boundType : boundType2;
        if (!z2) {
            boundType = boundType2;
        }
        Range<Comparable> range = Range.ALL;
        if (boundType3 == boundType2) {
            cut = new Cut.AboveValue(cut3);
        } else {
            cut = new Cut.BelowValue(cut3);
        }
        if (boundType == boundType2) {
            cut2 = new Cut.BelowValue(cut4);
        } else {
            cut2 = new Cut.AboveValue(cut4);
        }
        return subMap(new Range(cut, cut2));
    }

    public final C24792 descendingEntryIterator() {
        boolean z;
        Collection<Range<C>> collection;
        final Iterators.PeekingImpl peekingImpl;
        Range<Cut<C>> range = this.upperBoundWindow;
        Objects.requireNonNull(range);
        if (range.upperBound != Cut.AboveAll.INSTANCE) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            NavigableMap<Cut<C>, Range<C>> navigableMap = this.rangesByLowerBound;
            Range<Cut<C>> range2 = this.upperBoundWindow;
            Objects.requireNonNull(range2);
            collection = navigableMap.headMap((Cut) range2.upperBound.endpoint(), false).descendingMap().values();
        } else {
            collection = this.rangesByLowerBound.descendingMap().values();
        }
        Iterator<Range<C>> it = collection.iterator();
        if (it instanceof Iterators.PeekingImpl) {
            peekingImpl = (Iterators.PeekingImpl) it;
        } else {
            peekingImpl = new Iterators.PeekingImpl(it);
        }
        if (peekingImpl.hasNext()) {
            Cut<C> cut = this.upperBoundWindow.upperBound;
            if (!peekingImpl.hasPeeked) {
                peekingImpl.peekedElement = peekingImpl.iterator.next();
                peekingImpl.hasPeeked = true;
            }
            if (cut.isLessThan(((Range) peekingImpl.peekedElement).upperBound)) {
                peekingImpl.next();
            }
        }
        return new AbstractIterator<Map.Entry<Cut<Comparable<?>>, Range<Comparable<?>>>>() {
            public final Object computeNext() {
                AbstractIterator.State state = AbstractIterator.State.DONE;
                if (!((Iterators.PeekingImpl) peekingImpl).hasNext()) {
                    this.state = state;
                } else {
                    Range range = (Range) ((Iterators.PeekingImpl) peekingImpl).next();
                    if (TreeRangeSet$RangesByUpperBound.this.upperBoundWindow.lowerBound.isLessThan(range.upperBound)) {
                        return new ImmutableEntry(range.upperBound, range);
                    }
                    this.state = state;
                }
                return null;
            }
        };
    }

    public final C24781 entryIterator() {
        boolean z;
        final Iterator<Range<C>> it;
        Range<Cut<C>> range = this.upperBoundWindow;
        Objects.requireNonNull(range);
        if (range.lowerBound != Cut.BelowAll.INSTANCE) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            it = this.rangesByLowerBound.values().iterator();
        } else {
            NavigableMap<Cut<C>, Range<C>> navigableMap = this.rangesByLowerBound;
            Range<Cut<C>> range2 = this.upperBoundWindow;
            Objects.requireNonNull(range2);
            Map.Entry<Cut<C>, Range<C>> lowerEntry = navigableMap.lowerEntry((Cut) range2.lowerBound.endpoint());
            if (lowerEntry == null) {
                it = this.rangesByLowerBound.values().iterator();
            } else if (this.upperBoundWindow.lowerBound.isLessThan(lowerEntry.getValue().upperBound)) {
                it = this.rangesByLowerBound.tailMap(lowerEntry.getKey(), true).values().iterator();
            } else {
                NavigableMap<Cut<C>, Range<C>> navigableMap2 = this.rangesByLowerBound;
                Range<Cut<C>> range3 = this.upperBoundWindow;
                Objects.requireNonNull(range3);
                it = navigableMap2.tailMap((Cut) range3.lowerBound.endpoint(), true).values().iterator();
            }
        }
        return new AbstractIterator<Map.Entry<Cut<Comparable<?>>, Range<Comparable<?>>>>() {
            public final Object computeNext() {
                AbstractIterator.State state = AbstractIterator.State.DONE;
                if (!it.hasNext()) {
                    this.state = state;
                } else {
                    Range range = (Range) it.next();
                    if (!TreeRangeSet$RangesByUpperBound.this.upperBoundWindow.upperBound.isLessThan(range.upperBound)) {
                        return new ImmutableEntry(range.upperBound, range);
                    }
                    this.state = state;
                }
                return null;
            }
        };
    }

    public final Range<C> get(Object obj) {
        Map.Entry<Cut<C>, Range<C>> lowerEntry;
        if (obj instanceof Cut) {
            try {
                Cut cut = (Cut) obj;
                Range<Cut<C>> range = this.upperBoundWindow;
                Objects.requireNonNull(range);
                Objects.requireNonNull(cut);
                if ((range.lowerBound.isLessThan(cut) && !range.upperBound.isLessThan(cut)) && (lowerEntry = this.rangesByLowerBound.lowerEntry(cut)) != null && lowerEntry.getValue().upperBound.equals(cut)) {
                    return lowerEntry.getValue();
                }
            } catch (ClassCastException unused) {
            }
        }
        return null;
    }

    public final NavigableMap headMap(Object obj, boolean z) {
        BoundType boundType;
        Range range;
        Cut cut = (Cut) obj;
        if (z) {
            boundType = BoundType.CLOSED;
        } else {
            boundType = BoundType.OPEN;
        }
        Range<Comparable> range2 = Range.ALL;
        int ordinal = boundType.ordinal();
        if (ordinal == 0) {
            range = new Range(Cut.BelowAll.INSTANCE, new Cut.BelowValue(cut));
        } else if (ordinal == 1) {
            range = new Range(Cut.BelowAll.INSTANCE, new Cut.AboveValue(cut));
        } else {
            throw new AssertionError();
        }
        return subMap(range);
    }

    public final boolean isEmpty() {
        if (this.upperBoundWindow.equals(Range.ALL)) {
            return this.rangesByLowerBound.isEmpty();
        }
        if (!entryIterator().hasNext()) {
            return true;
        }
        return false;
    }

    public final int size() {
        if (this.upperBoundWindow.equals(Range.ALL)) {
            return this.rangesByLowerBound.size();
        }
        C24781 entryIterator = entryIterator();
        long j = 0;
        while (entryIterator.hasNext()) {
            entryIterator.next();
            j++;
        }
        return R$layout.saturatedCast(j);
    }

    public final NavigableMap tailMap(Object obj, boolean z) {
        BoundType boundType;
        Range range;
        Cut cut = (Cut) obj;
        if (z) {
            boundType = BoundType.CLOSED;
        } else {
            boundType = BoundType.OPEN;
        }
        Range<Comparable> range2 = Range.ALL;
        int ordinal = boundType.ordinal();
        if (ordinal == 0) {
            range = new Range(new Cut.AboveValue(cut), Cut.AboveAll.INSTANCE);
        } else if (ordinal == 1) {
            range = new Range(new Cut.BelowValue(cut), Cut.AboveAll.INSTANCE);
        } else {
            throw new AssertionError();
        }
        return subMap(range);
    }

    public TreeRangeSet$RangesByUpperBound(NavigableMap<Cut<C>, Range<C>> navigableMap, Range<Cut<C>> range) {
        this.rangesByLowerBound = navigableMap;
        this.upperBoundWindow = range;
    }

    public final boolean containsKey(Object obj) {
        if (get(obj) != null) {
            return true;
        }
        return false;
    }

    public final NavigableMap<Cut<C>, Range<C>> subMap(Range<Cut<C>> range) {
        Range<Cut<C>> range2 = this.upperBoundWindow;
        if (!(range.lowerBound.compareTo(range2.upperBound) <= 0 && range2.lowerBound.compareTo(range.upperBound) <= 0)) {
            return ImmutableSortedMap.NATURAL_EMPTY_MAP;
        }
        NavigableMap<Cut<C>, Range<C>> navigableMap = this.rangesByLowerBound;
        Range<Cut<C>> range3 = this.upperBoundWindow;
        int compareTo = range.lowerBound.compareTo(range3.lowerBound);
        int compareTo2 = range.upperBound.compareTo(range3.upperBound);
        if (compareTo < 0 || compareTo2 > 0) {
            if (compareTo > 0 || compareTo2 < 0) {
                range = new Range<>(compareTo >= 0 ? range.lowerBound : range3.lowerBound, compareTo2 <= 0 ? range.upperBound : range3.upperBound);
            } else {
                range = range3;
            }
        }
        return new TreeRangeSet$RangesByUpperBound(navigableMap, range);
    }

    public final Comparator<? super Cut<C>> comparator() {
        return NaturalOrdering.INSTANCE;
    }
}
