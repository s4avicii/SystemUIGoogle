package com.google.common.collect;

import androidx.recyclerview.R$dimen;
import com.google.common.collect.AbstractMapBasedMultimap;
import com.google.common.collect.AbstractNavigableMap;
import com.google.common.collect.Sets;
import com.google.common.collect.TreeRangeSet$RangesByUpperBound;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

public final class Maps {

    public static abstract class DescendingMap<K, V> extends ForwardingMap<K, V> implements NavigableMap<K, V> {
        public transient Ordering comparator;
        public transient AnonymousClass1EntrySetImpl entrySet;
        public transient NavigableKeySet navigableKeySet;

        public final Object delegate() {
            return AbstractNavigableMap.this;
        }

        public final NavigableMap<K, V> headMap(K k, boolean z) {
            return AbstractNavigableMap.this.tailMap(k, z).descendingMap();
        }

        public final NavigableMap<K, V> subMap(K k, boolean z, K k2, boolean z2) {
            return AbstractNavigableMap.this.subMap(k2, z2, k, z).descendingMap();
        }

        public final NavigableMap<K, V> tailMap(K k, boolean z) {
            return AbstractNavigableMap.this.headMap(k, z).descendingMap();
        }

        public final Map.Entry<K, V> ceilingEntry(K k) {
            return AbstractNavigableMap.this.floorEntry(k);
        }

        public final K ceilingKey(K k) {
            return AbstractNavigableMap.this.floorKey(k);
        }

        public final Comparator<? super K> comparator() {
            Ordering ordering;
            Ordering ordering2 = this.comparator;
            if (ordering2 != null) {
                return ordering2;
            }
            Comparator comparator2 = AbstractNavigableMap.this.comparator();
            if (comparator2 == null) {
                comparator2 = NaturalOrdering.INSTANCE;
            }
            if (comparator2 instanceof Ordering) {
                ordering = (Ordering) comparator2;
            } else {
                ordering = new ComparatorOrdering(comparator2);
            }
            Ordering reverse = ordering.reverse();
            this.comparator = reverse;
            return reverse;
        }

        public final NavigableSet<K> descendingKeySet() {
            return AbstractNavigableMap.this.navigableKeySet();
        }

        public final NavigableMap<K, V> descendingMap() {
            return AbstractNavigableMap.this;
        }

        public final Set<Map.Entry<K, V>> entrySet() {
            AnonymousClass1EntrySetImpl r0 = this.entrySet;
            if (r0 != null) {
                return r0;
            }
            AnonymousClass1EntrySetImpl r02 = new EntrySet<Object, Object>() {
                public final Iterator<Map.Entry<Object, Object>> iterator() {
                    AbstractNavigableMap.DescendingMap descendingMap = (AbstractNavigableMap.DescendingMap) DescendingMap.this;
                    Objects.requireNonNull(descendingMap);
                    return AbstractNavigableMap.this.descendingEntryIterator();
                }

                public final Map<Object, Object> map() {
                    return DescendingMap.this;
                }
            };
            this.entrySet = r02;
            return r02;
        }

        public final Map.Entry<K, V> firstEntry() {
            return AbstractNavigableMap.this.lastEntry();
        }

        public final K firstKey() {
            return AbstractNavigableMap.this.lastKey();
        }

        public final Map.Entry<K, V> floorEntry(K k) {
            return AbstractNavigableMap.this.ceilingEntry(k);
        }

        public final K floorKey(K k) {
            return AbstractNavigableMap.this.ceilingKey(k);
        }

        public final Map.Entry<K, V> higherEntry(K k) {
            return AbstractNavigableMap.this.lowerEntry(k);
        }

        public final K higherKey(K k) {
            return AbstractNavigableMap.this.lowerKey(k);
        }

        public final Map.Entry<K, V> lastEntry() {
            return AbstractNavigableMap.this.firstEntry();
        }

        public final K lastKey() {
            return AbstractNavigableMap.this.firstKey();
        }

        public final Map.Entry<K, V> lowerEntry(K k) {
            return AbstractNavigableMap.this.higherEntry(k);
        }

        public final K lowerKey(K k) {
            return AbstractNavigableMap.this.higherKey(k);
        }

        public final NavigableSet<K> navigableKeySet() {
            NavigableKeySet navigableKeySet2 = this.navigableKeySet;
            if (navigableKeySet2 != null) {
                return navigableKeySet2;
            }
            NavigableKeySet navigableKeySet3 = new NavigableKeySet(this);
            this.navigableKeySet = navigableKeySet3;
            return navigableKeySet3;
        }

        public final Map.Entry<K, V> pollFirstEntry() {
            return AbstractNavigableMap.this.pollLastEntry();
        }

        public final Map.Entry<K, V> pollLastEntry() {
            return AbstractNavigableMap.this.pollFirstEntry();
        }

        public final Collection<V> values() {
            return new Values(this);
        }

        /* renamed from: delegate  reason: collision with other method in class */
        public final Map<K, V> m318delegate() {
            return AbstractNavigableMap.this;
        }

        public final Set<K> keySet() {
            return navigableKeySet();
        }

        public final String toString() {
            return Maps.toStringImpl(this);
        }

        public final SortedMap<K, V> headMap(K k) {
            return headMap(k, false);
        }

        public final SortedMap<K, V> subMap(K k, K k2) {
            return subMap(k, true, k2, false);
        }

        public final SortedMap<K, V> tailMap(K k) {
            return tailMap(k, true);
        }
    }

    public static abstract class EntrySet<K, V> extends Sets.ImprovedAbstractSet<Map.Entry<K, V>> {
        public abstract Map<K, V> map();

        public boolean contains(Object obj) {
            Object obj2;
            if (obj instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry) obj;
                Object key = entry.getKey();
                Map map = map();
                Objects.requireNonNull(map);
                try {
                    obj2 = map.get(key);
                } catch (ClassCastException | NullPointerException unused) {
                    obj2 = null;
                }
                if (!R$dimen.equal(obj2, entry.getValue()) || (obj2 == null && !map().containsKey(key))) {
                    return false;
                }
                return true;
            }
            return false;
        }

        public final void clear() {
            map().clear();
        }

        public final boolean isEmpty() {
            return map().isEmpty();
        }

        public boolean remove(Object obj) {
            if (!contains(obj) || !(obj instanceof Map.Entry)) {
                return false;
            }
            return map().keySet().remove(((Map.Entry) obj).getKey());
        }

        public final boolean removeAll(Collection<?> collection) {
            try {
                Objects.requireNonNull(collection);
                return Sets.removeAllImpl(this, collection);
            } catch (UnsupportedOperationException unused) {
                boolean z = false;
                for (Object remove : collection) {
                    z |= remove(remove);
                }
                return z;
            }
        }

        public final boolean retainAll(Collection<?> collection) {
            int i;
            try {
                Objects.requireNonNull(collection);
                return super.retainAll(collection);
            } catch (UnsupportedOperationException unused) {
                int size = collection.size();
                if (size < 3) {
                    CollectPreconditions.checkNonnegative(size, "expectedSize");
                    i = size + 1;
                } else if (size < 1073741824) {
                    i = (int) ((((float) size) / 0.75f) + 1.0f);
                } else {
                    i = Integer.MAX_VALUE;
                }
                HashSet hashSet = new HashSet(i);
                for (Object next : collection) {
                    if (contains(next) && (next instanceof Map.Entry)) {
                        hashSet.add(((Map.Entry) next).getKey());
                    }
                }
                return map().keySet().retainAll(hashSet);
            }
        }

        public final int size() {
            return map().size();
        }
    }

    public static abstract class IteratorBasedAbstractMap<K, V> extends AbstractMap<K, V> {
        public abstract TreeRangeSet$RangesByUpperBound.C24781 entryIterator();

        public final Set<Map.Entry<K, V>> entrySet() {
            return new EntrySet<K, V>() {
                public final Iterator<Map.Entry<K, V>> iterator() {
                    return IteratorBasedAbstractMap.this.entryIterator();
                }

                public final Map<K, V> map() {
                    return IteratorBasedAbstractMap.this;
                }
            };
        }

        public final void clear() {
            Iterators.clear(entryIterator());
        }
    }

    public static class NavigableKeySet<K, V> extends SortedKeySet<K, V> implements NavigableSet<K> {
        public final NavigableSet<K> headSet(K k, boolean z) {
            return ((NavigableMap) this.map).headMap(k, z).navigableKeySet();
        }

        public final NavigableSet<K> subSet(K k, boolean z, K k2, boolean z2) {
            return ((NavigableMap) this.map).subMap(k, z, k2, z2).navigableKeySet();
        }

        public final NavigableSet<K> tailSet(K k, boolean z) {
            return ((NavigableMap) this.map).tailMap(k, z).navigableKeySet();
        }

        public final K ceiling(K k) {
            return ((NavigableMap) this.map).ceilingKey(k);
        }

        public final NavigableSet<K> descendingSet() {
            return ((NavigableMap) this.map).descendingKeySet();
        }

        public final K floor(K k) {
            return ((NavigableMap) this.map).floorKey(k);
        }

        public final K higher(K k) {
            return ((NavigableMap) this.map).higherKey(k);
        }

        public final K lower(K k) {
            return ((NavigableMap) this.map).lowerKey(k);
        }

        public final Map map() {
            return (NavigableMap) this.map;
        }

        public final K pollFirst() {
            return Maps.keyOrNull(((NavigableMap) this.map).pollFirstEntry());
        }

        public final K pollLast() {
            return Maps.keyOrNull(((NavigableMap) this.map).pollLastEntry());
        }

        public final Iterator<K> descendingIterator() {
            return descendingSet().iterator();
        }

        public final SortedSet<K> headSet(K k) {
            return headSet(k, false);
        }

        public final SortedSet<K> subSet(K k, K k2) {
            return subSet(k, true, k2, false);
        }

        public final SortedSet<K> tailSet(K k) {
            return tailSet(k, true);
        }

        public NavigableKeySet(NavigableMap<K, V> navigableMap) {
            super(navigableMap);
        }
    }

    public static class SortedKeySet<K, V> extends KeySet<K, V> implements SortedSet<K> {
        public final Comparator<? super K> comparator() {
            return ((NavigableMap) ((NavigableKeySet) this).map).comparator();
        }

        public final K first() {
            return ((NavigableMap) ((NavigableKeySet) this).map).firstKey();
        }

        public final K last() {
            return ((NavigableMap) ((NavigableKeySet) this).map).lastKey();
        }

        public SortedKeySet(SortedMap<K, V> sortedMap) {
            super(sortedMap);
        }
    }

    public static class Values<K, V> extends AbstractCollection<V> {
        public final Map<K, V> map;

        public final void clear() {
            this.map.clear();
        }

        public final boolean contains(Object obj) {
            return this.map.containsValue(obj);
        }

        public final boolean isEmpty() {
            return this.map.isEmpty();
        }

        public final Iterator<V> iterator() {
            return new TransformedIterator<Map.Entry<Object, Object>, Object>(this.map.entrySet().iterator()) {
                public final Object transform(Object obj) {
                    return ((Map.Entry) obj).getValue();
                }
            };
        }

        public final int size() {
            return this.map.size();
        }

        public Values(Map<K, V> map2) {
            Objects.requireNonNull(map2);
            this.map = map2;
        }

        public final boolean remove(Object obj) {
            try {
                return super.remove(obj);
            } catch (UnsupportedOperationException unused) {
                for (Map.Entry next : this.map.entrySet()) {
                    if (R$dimen.equal(obj, next.getValue())) {
                        this.map.remove(next.getKey());
                        return true;
                    }
                }
                return false;
            }
        }

        public final boolean removeAll(Collection<?> collection) {
            try {
                Objects.requireNonNull(collection);
                return super.removeAll(collection);
            } catch (UnsupportedOperationException unused) {
                HashSet hashSet = new HashSet();
                for (Map.Entry next : this.map.entrySet()) {
                    if (collection.contains(next.getValue())) {
                        hashSet.add(next.getKey());
                    }
                }
                return this.map.keySet().removeAll(hashSet);
            }
        }

        public final boolean retainAll(Collection<?> collection) {
            try {
                Objects.requireNonNull(collection);
                return super.retainAll(collection);
            } catch (UnsupportedOperationException unused) {
                HashSet hashSet = new HashSet();
                for (Map.Entry next : this.map.entrySet()) {
                    if (collection.contains(next.getValue())) {
                        hashSet.add(next.getKey());
                    }
                }
                return this.map.keySet().retainAll(hashSet);
            }
        }
    }

    public static abstract class ViewCachingAbstractMap<K, V> extends AbstractMap<K, V> {
        public transient AbstractMapBasedMultimap.AsMap.AsMapEntries entrySet;
        public transient Values values;

        public final Set<Map.Entry<K, V>> entrySet() {
            AbstractMapBasedMultimap.AsMap.AsMapEntries asMapEntries = this.entrySet;
            if (asMapEntries != null) {
                return asMapEntries;
            }
            AbstractMapBasedMultimap.AsMap.AsMapEntries asMapEntries2 = new AbstractMapBasedMultimap.AsMap.AsMapEntries();
            this.entrySet = asMapEntries2;
            return asMapEntries2;
        }

        public final Collection<V> values() {
            Values values2 = this.values;
            if (values2 != null) {
                return values2;
            }
            Values values3 = new Values(this);
            this.values = values3;
            return values3;
        }
    }

    public static class KeySet<K, V> extends Sets.ImprovedAbstractSet<K> {
        public final Map<K, V> map;

        public KeySet(Map<K, V> map2) {
            Objects.requireNonNull(map2);
            this.map = map2;
        }

        public void clear() {
            map().clear();
        }

        public final boolean contains(Object obj) {
            return map().containsKey(obj);
        }

        public final boolean isEmpty() {
            return map().isEmpty();
        }

        public Iterator<K> iterator() {
            return new TransformedIterator<Map.Entry<Object, Object>, Object>(map().entrySet().iterator()) {
                public final Object transform(Object obj) {
                    return ((Map.Entry) obj).getKey();
                }
            };
        }

        public boolean remove(Object obj) {
            if (!contains(obj)) {
                return false;
            }
            map().remove(obj);
            return true;
        }

        public final int size() {
            return map().size();
        }

        public Map<K, V> map() {
            return this.map;
        }
    }

    public static <K> K keyOrNull(Map.Entry<K, ?> entry) {
        if (entry == null) {
            return null;
        }
        return entry.getKey();
    }

    public static String toStringImpl(Map<?, ?> map) {
        int size = map.size();
        CollectPreconditions.checkNonnegative(size, "size");
        StringBuilder sb = new StringBuilder((int) Math.min(((long) size) * 8, 1073741824));
        sb.append('{');
        boolean z = true;
        for (Map.Entry next : map.entrySet()) {
            if (!z) {
                sb.append(", ");
            }
            z = false;
            sb.append(next.getKey());
            sb.append('=');
            sb.append(next.getValue());
        }
        sb.append('}');
        return sb.toString();
    }
}
