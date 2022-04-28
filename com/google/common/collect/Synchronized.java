package com.google.common.collect;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

public final class Synchronized {

    public static class SynchronizedBiMap<K, V> extends SynchronizedMap<K, V> implements BiMap<K, V> {
        private static final long serialVersionUID = 0;
        public transient Set<V> valueSet;

        public final Map delegate() {
            return (BiMap) ((Map) this.delegate);
        }

        public final Set<V> values() {
            Set<V> set;
            synchronized (this.mutex) {
                if (this.valueSet == null) {
                    this.valueSet = Synchronized.set(((BiMap) ((Map) this.delegate)).values(), this.mutex);
                }
                set = this.valueSet;
            }
            return set;
        }
    }

    public static class SynchronizedCollection<E> extends SynchronizedObject implements Collection<E> {
        private static final long serialVersionUID = 0;

        public SynchronizedCollection() {
            throw null;
        }

        public SynchronizedCollection(Collection collection, Object obj) {
            super(collection, obj);
        }

        public final Object[] toArray() {
            Object[] array;
            synchronized (this.mutex) {
                array = delegate().toArray();
            }
            return array;
        }

        public final boolean add(E e) {
            boolean add;
            synchronized (this.mutex) {
                add = delegate().add(e);
            }
            return add;
        }

        public final boolean addAll(Collection<? extends E> collection) {
            boolean addAll;
            synchronized (this.mutex) {
                addAll = delegate().addAll(collection);
            }
            return addAll;
        }

        public final void clear() {
            synchronized (this.mutex) {
                delegate().clear();
            }
        }

        public final boolean contains(Object obj) {
            boolean contains;
            synchronized (this.mutex) {
                contains = delegate().contains(obj);
            }
            return contains;
        }

        public final boolean containsAll(Collection<?> collection) {
            boolean containsAll;
            synchronized (this.mutex) {
                containsAll = delegate().containsAll(collection);
            }
            return containsAll;
        }

        public Collection<E> delegate() {
            return (Collection) this.delegate;
        }

        public final boolean isEmpty() {
            boolean isEmpty;
            synchronized (this.mutex) {
                isEmpty = delegate().isEmpty();
            }
            return isEmpty;
        }

        public final boolean remove(Object obj) {
            boolean remove;
            synchronized (this.mutex) {
                remove = delegate().remove(obj);
            }
            return remove;
        }

        public final boolean removeAll(Collection<?> collection) {
            boolean removeAll;
            synchronized (this.mutex) {
                removeAll = delegate().removeAll(collection);
            }
            return removeAll;
        }

        public final boolean retainAll(Collection<?> collection) {
            boolean retainAll;
            synchronized (this.mutex) {
                retainAll = delegate().retainAll(collection);
            }
            return retainAll;
        }

        public final int size() {
            int size;
            synchronized (this.mutex) {
                size = delegate().size();
            }
            return size;
        }

        public final Iterator<E> iterator() {
            return delegate().iterator();
        }

        public final <T> T[] toArray(T[] tArr) {
            T[] array;
            synchronized (this.mutex) {
                array = delegate().toArray(tArr);
            }
            return array;
        }
    }

    public static class SynchronizedEntry<K, V> extends SynchronizedObject implements Map.Entry<K, V> {
        private static final long serialVersionUID = 0;

        public final boolean equals(Object obj) {
            boolean equals;
            synchronized (this.mutex) {
                equals = ((Map.Entry) this.delegate).equals(obj);
            }
            return equals;
        }

        public final K getKey() {
            K key;
            synchronized (this.mutex) {
                key = ((Map.Entry) this.delegate).getKey();
            }
            return key;
        }

        public final V getValue() {
            V value;
            synchronized (this.mutex) {
                value = ((Map.Entry) this.delegate).getValue();
            }
            return value;
        }

        public final int hashCode() {
            int hashCode;
            synchronized (this.mutex) {
                hashCode = ((Map.Entry) this.delegate).hashCode();
            }
            return hashCode;
        }

        public final V setValue(V v) {
            V value;
            synchronized (this.mutex) {
                value = ((Map.Entry) this.delegate).setValue(v);
            }
            return value;
        }

        public SynchronizedEntry(Map.Entry<K, V> entry, Object obj) {
            super(entry, obj);
        }
    }

    public static class SynchronizedMap<K, V> extends SynchronizedObject implements Map<K, V> {
        private static final long serialVersionUID = 0;
        public transient Set<Map.Entry<K, V>> entrySet;
        public transient Set<K> keySet;
        public transient Collection<V> values;

        public final void clear() {
            synchronized (this.mutex) {
                delegate().clear();
            }
        }

        public final boolean containsKey(Object obj) {
            boolean containsKey;
            synchronized (this.mutex) {
                containsKey = delegate().containsKey(obj);
            }
            return containsKey;
        }

        public final boolean containsValue(Object obj) {
            boolean containsValue;
            synchronized (this.mutex) {
                containsValue = delegate().containsValue(obj);
            }
            return containsValue;
        }

        public Map<K, V> delegate() {
            return (Map) this.delegate;
        }

        public final Set<Map.Entry<K, V>> entrySet() {
            Set<Map.Entry<K, V>> set;
            synchronized (this.mutex) {
                if (this.entrySet == null) {
                    this.entrySet = Synchronized.set(delegate().entrySet(), this.mutex);
                }
                set = this.entrySet;
            }
            return set;
        }

        public final boolean equals(Object obj) {
            boolean equals;
            if (obj == this) {
                return true;
            }
            synchronized (this.mutex) {
                equals = delegate().equals(obj);
            }
            return equals;
        }

        public final V get(Object obj) {
            V v;
            synchronized (this.mutex) {
                v = delegate().get(obj);
            }
            return v;
        }

        public final int hashCode() {
            int hashCode;
            synchronized (this.mutex) {
                hashCode = delegate().hashCode();
            }
            return hashCode;
        }

        public final boolean isEmpty() {
            boolean isEmpty;
            synchronized (this.mutex) {
                isEmpty = delegate().isEmpty();
            }
            return isEmpty;
        }

        public Set<K> keySet() {
            Set<K> set;
            synchronized (this.mutex) {
                if (this.keySet == null) {
                    this.keySet = Synchronized.set(delegate().keySet(), this.mutex);
                }
                set = this.keySet;
            }
            return set;
        }

        public final V put(K k, V v) {
            V put;
            synchronized (this.mutex) {
                put = delegate().put(k, v);
            }
            return put;
        }

        public final void putAll(Map<? extends K, ? extends V> map) {
            synchronized (this.mutex) {
                delegate().putAll(map);
            }
        }

        public final V remove(Object obj) {
            V remove;
            synchronized (this.mutex) {
                remove = delegate().remove(obj);
            }
            return remove;
        }

        public final int size() {
            int size;
            synchronized (this.mutex) {
                size = delegate().size();
            }
            return size;
        }

        public Collection<V> values() {
            Collection<V> collection;
            synchronized (this.mutex) {
                if (this.values == null) {
                    this.values = new SynchronizedCollection(delegate().values(), this.mutex);
                }
                collection = this.values;
            }
            return collection;
        }

        public SynchronizedMap(Map<K, V> map, Object obj) {
            super(map, obj);
        }
    }

    public static class SynchronizedNavigableMap<K, V> extends SynchronizedSortedMap<K, V> implements NavigableMap<K, V> {
        private static final long serialVersionUID = 0;
        public transient NavigableSet<K> descendingKeySet;
        public transient NavigableMap<K, V> descendingMap;
        public transient NavigableSet<K> navigableKeySet;

        public final NavigableMap<K, V> headMap(K k, boolean z) {
            SynchronizedNavigableMap synchronizedNavigableMap;
            synchronized (this.mutex) {
                synchronizedNavigableMap = new SynchronizedNavigableMap(delegate().headMap(k, z), this.mutex);
            }
            return synchronizedNavigableMap;
        }

        public final NavigableMap<K, V> subMap(K k, boolean z, K k2, boolean z2) {
            SynchronizedNavigableMap synchronizedNavigableMap;
            synchronized (this.mutex) {
                synchronizedNavigableMap = new SynchronizedNavigableMap(delegate().subMap(k, z, k2, z2), this.mutex);
            }
            return synchronizedNavigableMap;
        }

        public final NavigableMap<K, V> tailMap(K k, boolean z) {
            SynchronizedNavigableMap synchronizedNavigableMap;
            synchronized (this.mutex) {
                synchronizedNavigableMap = new SynchronizedNavigableMap(delegate().tailMap(k, z), this.mutex);
            }
            return synchronizedNavigableMap;
        }

        public final Map.Entry<K, V> ceilingEntry(K k) {
            Map.Entry<K, V> access$700;
            synchronized (this.mutex) {
                access$700 = Synchronized.access$700(delegate().ceilingEntry(k), this.mutex);
            }
            return access$700;
        }

        public final K ceilingKey(K k) {
            K ceilingKey;
            synchronized (this.mutex) {
                ceilingKey = delegate().ceilingKey(k);
            }
            return ceilingKey;
        }

        public final NavigableSet<K> descendingKeySet() {
            synchronized (this.mutex) {
                NavigableSet<K> navigableSet = this.descendingKeySet;
                if (navigableSet != null) {
                    return navigableSet;
                }
                SynchronizedNavigableSet synchronizedNavigableSet = new SynchronizedNavigableSet(delegate().descendingKeySet(), this.mutex);
                this.descendingKeySet = synchronizedNavigableSet;
                return synchronizedNavigableSet;
            }
        }

        public final NavigableMap<K, V> descendingMap() {
            synchronized (this.mutex) {
                NavigableMap<K, V> navigableMap = this.descendingMap;
                if (navigableMap != null) {
                    return navigableMap;
                }
                SynchronizedNavigableMap synchronizedNavigableMap = new SynchronizedNavigableMap(delegate().descendingMap(), this.mutex);
                this.descendingMap = synchronizedNavigableMap;
                return synchronizedNavigableMap;
            }
        }

        public final Map.Entry<K, V> firstEntry() {
            Map.Entry<K, V> access$700;
            synchronized (this.mutex) {
                access$700 = Synchronized.access$700(delegate().firstEntry(), this.mutex);
            }
            return access$700;
        }

        public final Map.Entry<K, V> floorEntry(K k) {
            Map.Entry<K, V> access$700;
            synchronized (this.mutex) {
                access$700 = Synchronized.access$700(delegate().floorEntry(k), this.mutex);
            }
            return access$700;
        }

        public final K floorKey(K k) {
            K floorKey;
            synchronized (this.mutex) {
                floorKey = delegate().floorKey(k);
            }
            return floorKey;
        }

        public final Map.Entry<K, V> higherEntry(K k) {
            Map.Entry<K, V> access$700;
            synchronized (this.mutex) {
                access$700 = Synchronized.access$700(delegate().higherEntry(k), this.mutex);
            }
            return access$700;
        }

        public final K higherKey(K k) {
            K higherKey;
            synchronized (this.mutex) {
                higherKey = delegate().higherKey(k);
            }
            return higherKey;
        }

        public final Map.Entry<K, V> lastEntry() {
            Map.Entry<K, V> access$700;
            synchronized (this.mutex) {
                access$700 = Synchronized.access$700(delegate().lastEntry(), this.mutex);
            }
            return access$700;
        }

        public final Map.Entry<K, V> lowerEntry(K k) {
            Map.Entry<K, V> access$700;
            synchronized (this.mutex) {
                access$700 = Synchronized.access$700(delegate().lowerEntry(k), this.mutex);
            }
            return access$700;
        }

        public final K lowerKey(K k) {
            K lowerKey;
            synchronized (this.mutex) {
                lowerKey = delegate().lowerKey(k);
            }
            return lowerKey;
        }

        public final NavigableSet<K> navigableKeySet() {
            synchronized (this.mutex) {
                NavigableSet<K> navigableSet = this.navigableKeySet;
                if (navigableSet != null) {
                    return navigableSet;
                }
                SynchronizedNavigableSet synchronizedNavigableSet = new SynchronizedNavigableSet(delegate().navigableKeySet(), this.mutex);
                this.navigableKeySet = synchronizedNavigableSet;
                return synchronizedNavigableSet;
            }
        }

        public final Map.Entry<K, V> pollFirstEntry() {
            Map.Entry<K, V> access$700;
            synchronized (this.mutex) {
                access$700 = Synchronized.access$700(delegate().pollFirstEntry(), this.mutex);
            }
            return access$700;
        }

        public final Map.Entry<K, V> pollLastEntry() {
            Map.Entry<K, V> access$700;
            synchronized (this.mutex) {
                access$700 = Synchronized.access$700(delegate().pollLastEntry(), this.mutex);
            }
            return access$700;
        }

        public final NavigableMap<K, V> delegate() {
            return (NavigableMap) super.delegate();
        }

        public final Set<K> keySet() {
            return navigableKeySet();
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

        public SynchronizedNavigableMap(NavigableMap<K, V> navigableMap, Object obj) {
            super(navigableMap, obj);
        }
    }

    public static class SynchronizedNavigableSet<E> extends SynchronizedSortedSet<E> implements NavigableSet<E> {
        private static final long serialVersionUID = 0;
        public transient NavigableSet<E> descendingSet;

        public final NavigableSet<E> headSet(E e, boolean z) {
            SynchronizedNavigableSet synchronizedNavigableSet;
            synchronized (this.mutex) {
                synchronizedNavigableSet = new SynchronizedNavigableSet(delegate().headSet(e, z), this.mutex);
            }
            return synchronizedNavigableSet;
        }

        public final NavigableSet<E> subSet(E e, boolean z, E e2, boolean z2) {
            SynchronizedNavigableSet synchronizedNavigableSet;
            synchronized (this.mutex) {
                synchronizedNavigableSet = new SynchronizedNavigableSet(delegate().subSet(e, z, e2, z2), this.mutex);
            }
            return synchronizedNavigableSet;
        }

        public final NavigableSet<E> tailSet(E e, boolean z) {
            SynchronizedNavigableSet synchronizedNavigableSet;
            synchronized (this.mutex) {
                synchronizedNavigableSet = new SynchronizedNavigableSet(delegate().tailSet(e, z), this.mutex);
            }
            return synchronizedNavigableSet;
        }

        public final E ceiling(E e) {
            E ceiling;
            synchronized (this.mutex) {
                ceiling = delegate().ceiling(e);
            }
            return ceiling;
        }

        public final NavigableSet<E> descendingSet() {
            synchronized (this.mutex) {
                NavigableSet<E> navigableSet = this.descendingSet;
                if (navigableSet != null) {
                    return navigableSet;
                }
                SynchronizedNavigableSet synchronizedNavigableSet = new SynchronizedNavigableSet(delegate().descendingSet(), this.mutex);
                this.descendingSet = synchronizedNavigableSet;
                return synchronizedNavigableSet;
            }
        }

        public final E floor(E e) {
            E floor;
            synchronized (this.mutex) {
                floor = delegate().floor(e);
            }
            return floor;
        }

        public final E higher(E e) {
            E higher;
            synchronized (this.mutex) {
                higher = delegate().higher(e);
            }
            return higher;
        }

        public final E lower(E e) {
            E lower;
            synchronized (this.mutex) {
                lower = delegate().lower(e);
            }
            return lower;
        }

        public final E pollFirst() {
            E pollFirst;
            synchronized (this.mutex) {
                pollFirst = delegate().pollFirst();
            }
            return pollFirst;
        }

        public final E pollLast() {
            E pollLast;
            synchronized (this.mutex) {
                pollLast = delegate().pollLast();
            }
            return pollLast;
        }

        public final Iterator<E> descendingIterator() {
            return delegate().descendingIterator();
        }

        public final NavigableSet<E> delegate() {
            return (NavigableSet) super.delegate();
        }

        public final SortedSet<E> headSet(E e) {
            return headSet(e, false);
        }

        public final SortedSet<E> subSet(E e, E e2) {
            return subSet(e, true, e2, false);
        }

        public final SortedSet<E> tailSet(E e) {
            return tailSet(e, true);
        }

        public SynchronizedNavigableSet(NavigableSet<E> navigableSet, Object obj) {
            super(navigableSet, obj);
        }
    }

    public static class SynchronizedObject implements Serializable {
        private static final long serialVersionUID = 0;
        public final Object delegate;
        public final Object mutex;

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            synchronized (this.mutex) {
                objectOutputStream.defaultWriteObject();
            }
        }

        public final String toString() {
            String obj;
            synchronized (this.mutex) {
                obj = this.delegate.toString();
            }
            return obj;
        }

        public SynchronizedObject(Object obj, Object obj2) {
            Objects.requireNonNull(obj);
            this.delegate = obj;
            this.mutex = obj2 == null ? this : obj2;
        }
    }

    public static class SynchronizedSet<E> extends SynchronizedCollection<E> implements Set<E> {
        private static final long serialVersionUID = 0;

        public Set<E> delegate() {
            return (Set) ((Collection) this.delegate);
        }

        public final boolean equals(Object obj) {
            boolean equals;
            if (obj == this) {
                return true;
            }
            synchronized (this.mutex) {
                equals = delegate().equals(obj);
            }
            return equals;
        }

        public final int hashCode() {
            int hashCode;
            synchronized (this.mutex) {
                hashCode = delegate().hashCode();
            }
            return hashCode;
        }

        public SynchronizedSet(Set<E> set, Object obj) {
            super(set, obj);
        }
    }

    public static class SynchronizedSortedMap<K, V> extends SynchronizedMap<K, V> implements SortedMap<K, V> {
        private static final long serialVersionUID = 0;

        public SortedMap<K, V> delegate() {
            return (SortedMap) ((Map) this.delegate);
        }

        public final Comparator<? super K> comparator() {
            Comparator<? super K> comparator;
            synchronized (this.mutex) {
                comparator = delegate().comparator();
            }
            return comparator;
        }

        public final K firstKey() {
            K firstKey;
            synchronized (this.mutex) {
                firstKey = delegate().firstKey();
            }
            return firstKey;
        }

        public SortedMap<K, V> headMap(K k) {
            SynchronizedSortedMap synchronizedSortedMap;
            synchronized (this.mutex) {
                synchronizedSortedMap = new SynchronizedSortedMap(delegate().headMap(k), this.mutex);
            }
            return synchronizedSortedMap;
        }

        public final K lastKey() {
            K lastKey;
            synchronized (this.mutex) {
                lastKey = delegate().lastKey();
            }
            return lastKey;
        }

        public SortedMap<K, V> subMap(K k, K k2) {
            SynchronizedSortedMap synchronizedSortedMap;
            synchronized (this.mutex) {
                synchronizedSortedMap = new SynchronizedSortedMap(delegate().subMap(k, k2), this.mutex);
            }
            return synchronizedSortedMap;
        }

        public SortedMap<K, V> tailMap(K k) {
            SynchronizedSortedMap synchronizedSortedMap;
            synchronized (this.mutex) {
                synchronizedSortedMap = new SynchronizedSortedMap(delegate().tailMap(k), this.mutex);
            }
            return synchronizedSortedMap;
        }

        public SynchronizedSortedMap(SortedMap<K, V> sortedMap, Object obj) {
            super(sortedMap, obj);
        }
    }

    public static class SynchronizedSortedSet<E> extends SynchronizedSet<E> implements SortedSet<E> {
        private static final long serialVersionUID = 0;

        public final Comparator<? super E> comparator() {
            Comparator<? super E> comparator;
            synchronized (this.mutex) {
                comparator = delegate().comparator();
            }
            return comparator;
        }

        public final E first() {
            E first;
            synchronized (this.mutex) {
                first = delegate().first();
            }
            return first;
        }

        public SortedSet<E> headSet(E e) {
            SynchronizedSortedSet synchronizedSortedSet;
            synchronized (this.mutex) {
                synchronizedSortedSet = new SynchronizedSortedSet(delegate().headSet(e), this.mutex);
            }
            return synchronizedSortedSet;
        }

        public final E last() {
            E last;
            synchronized (this.mutex) {
                last = delegate().last();
            }
            return last;
        }

        public SortedSet<E> subSet(E e, E e2) {
            SynchronizedSortedSet synchronizedSortedSet;
            synchronized (this.mutex) {
                synchronizedSortedSet = new SynchronizedSortedSet(delegate().subSet(e, e2), this.mutex);
            }
            return synchronizedSortedSet;
        }

        public SortedSet<E> tailSet(E e) {
            SynchronizedSortedSet synchronizedSortedSet;
            synchronized (this.mutex) {
                synchronizedSortedSet = new SynchronizedSortedSet(delegate().tailSet(e), this.mutex);
            }
            return synchronizedSortedSet;
        }

        public SortedSet<E> delegate() {
            return (SortedSet) super.delegate();
        }

        public SynchronizedSortedSet(SortedSet<E> sortedSet, Object obj) {
            super(sortedSet, obj);
        }
    }

    public static Map.Entry access$700(Map.Entry entry, Object obj) {
        if (entry == null) {
            return null;
        }
        return new SynchronizedEntry(entry, obj);
    }

    public static <K, V> Map<K, V> map(Map<K, V> map, Object obj) {
        return new SynchronizedMap(map, obj);
    }

    public static <E> Set<E> set(Set<E> set, Object obj) {
        return new SynchronizedSet(set, obj);
    }
}
