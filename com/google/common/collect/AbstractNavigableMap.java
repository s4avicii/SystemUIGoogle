package com.google.common.collect;

import com.google.common.collect.Maps;
import com.google.common.collect.TreeRangeSet$RangesByUpperBound;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;

public abstract class AbstractNavigableMap<K, V> extends Maps.IteratorBasedAbstractMap<K, V> implements NavigableMap<K, V> {

    public final class DescendingMap extends Maps.DescendingMap<K, V> {
        public DescendingMap() {
        }
    }

    public final Map.Entry<K, V> ceilingEntry(K k) {
        return tailMap(k, true).firstEntry();
    }

    public abstract TreeRangeSet$RangesByUpperBound.C24792 descendingEntryIterator();

    public final Map.Entry<K, V> floorEntry(K k) {
        return headMap(k, true).lastEntry();
    }

    public final SortedMap<K, V> headMap(K k) {
        return headMap(k, false);
    }

    public final Map.Entry<K, V> higherEntry(K k) {
        return tailMap(k, false).firstEntry();
    }

    public final Map.Entry<K, V> lowerEntry(K k) {
        return headMap(k, false).lastEntry();
    }

    public final SortedMap<K, V> subMap(K k, K k2) {
        return subMap(k, true, k2, false);
    }

    public final SortedMap<K, V> tailMap(K k) {
        return tailMap(k, true);
    }

    public final NavigableMap<K, V> descendingMap() {
        return new DescendingMap();
    }

    public final NavigableSet<K> navigableKeySet() {
        return new Maps.NavigableKeySet(this);
    }

    public final K ceilingKey(K k) {
        return Maps.keyOrNull(ceilingEntry(k));
    }

    public final NavigableSet<K> descendingKeySet() {
        return descendingMap().navigableKeySet();
    }

    public final Map.Entry<K, V> firstEntry() {
        return (Map.Entry) Iterators.getNext(entryIterator());
    }

    public final K firstKey() {
        Map.Entry firstEntry = firstEntry();
        if (firstEntry != null) {
            return firstEntry.getKey();
        }
        throw new NoSuchElementException();
    }

    public final K floorKey(K k) {
        return Maps.keyOrNull(floorEntry(k));
    }

    public final K higherKey(K k) {
        return Maps.keyOrNull(higherEntry(k));
    }

    public final Set<K> keySet() {
        return navigableKeySet();
    }

    public final Map.Entry<K, V> lastEntry() {
        return (Map.Entry) Iterators.getNext(descendingEntryIterator());
    }

    public final K lastKey() {
        Map.Entry lastEntry = lastEntry();
        if (lastEntry != null) {
            return lastEntry.getKey();
        }
        throw new NoSuchElementException();
    }

    public final K lowerKey(K k) {
        return Maps.keyOrNull(lowerEntry(k));
    }

    public final Map.Entry<K, V> pollFirstEntry() {
        TreeRangeSet$RangesByUpperBound.C24781 entryIterator = entryIterator();
        if (!entryIterator.hasNext()) {
            Map.Entry entry = null;
            return null;
        }
        entryIterator.next();
        entryIterator.remove();
        throw null;
    }

    public final Map.Entry<K, V> pollLastEntry() {
        TreeRangeSet$RangesByUpperBound.C24792 descendingEntryIterator = descendingEntryIterator();
        if (!descendingEntryIterator.hasNext()) {
            Map.Entry entry = null;
            return null;
        }
        descendingEntryIterator.next();
        descendingEntryIterator.remove();
        throw null;
    }
}
