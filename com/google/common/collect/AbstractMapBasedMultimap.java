package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

abstract class AbstractMapBasedMultimap<K, V> extends AbstractMultimap<K, V> implements Serializable {
    private static final long serialVersionUID = 2447537837011683357L;
    public transient Map<K, Collection<V>> map;
    public transient int totalSize;

    public class AsMap extends Maps.ViewCachingAbstractMap<K, Collection<V>> {
        public final transient Map<K, Collection<V>> submap;

        public class AsMapEntries extends Maps.EntrySet<K, Collection<V>> {
            public AsMapEntries() {
            }

            public final boolean contains(Object obj) {
                Set<Map.Entry<K, Collection<V>>> entrySet = AsMap.this.submap.entrySet();
                Objects.requireNonNull(entrySet);
                try {
                    return entrySet.contains(obj);
                } catch (ClassCastException | NullPointerException unused) {
                    return false;
                }
            }

            public final Iterator<Map.Entry<K, Collection<V>>> iterator() {
                return new AsMapIterator();
            }

            public final boolean remove(Object obj) {
                Collection<V> collection;
                if (!contains(obj)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                Objects.requireNonNull(entry);
                AbstractMapBasedMultimap abstractMapBasedMultimap = AbstractMapBasedMultimap.this;
                Object key = entry.getKey();
                Objects.requireNonNull(abstractMapBasedMultimap);
                Map<K, Collection<V>> map = abstractMapBasedMultimap.map;
                Objects.requireNonNull(map);
                try {
                    collection = map.remove(key);
                } catch (ClassCastException | NullPointerException unused) {
                    collection = null;
                }
                Collection collection2 = collection;
                if (collection2 == null) {
                    return true;
                }
                int size = collection2.size();
                collection2.clear();
                abstractMapBasedMultimap.totalSize -= size;
                return true;
            }

            public final Map<K, Collection<V>> map() {
                return AsMap.this;
            }
        }

        public class AsMapIterator implements Iterator<Map.Entry<K, Collection<V>>> {
            public Collection<V> collection;
            public final Iterator<Map.Entry<K, Collection<V>>> delegateIterator;

            public AsMapIterator() {
                this.delegateIterator = AsMap.this.submap.entrySet().iterator();
            }

            public final boolean hasNext() {
                return this.delegateIterator.hasNext();
            }

            public final Object next() {
                Map.Entry next = this.delegateIterator.next();
                this.collection = (Collection) next.getValue();
                AsMap asMap = AsMap.this;
                Objects.requireNonNull(asMap);
                Object key = next.getKey();
                AbstractSetMultimap abstractSetMultimap = (AbstractSetMultimap) AbstractMapBasedMultimap.this;
                Objects.requireNonNull(abstractSetMultimap);
                return new ImmutableEntry(key, new WrappedSet(key, (Set) ((Collection) next.getValue())));
            }

            public final void remove() {
                boolean z;
                if (this.collection != null) {
                    z = true;
                } else {
                    z = false;
                }
                Preconditions.checkState(z, "no calls to next() since the last call to remove()");
                this.delegateIterator.remove();
                AbstractMapBasedMultimap.this.totalSize -= this.collection.size();
                this.collection.clear();
                this.collection = null;
            }
        }

        public AsMap(Map<K, Collection<V>> map) {
            this.submap = map;
        }

        public final void clear() {
            Map<K, Collection<V>> map = this.submap;
            AbstractMapBasedMultimap abstractMapBasedMultimap = AbstractMapBasedMultimap.this;
            if (map == abstractMapBasedMultimap.map) {
                abstractMapBasedMultimap.clear();
            } else {
                Iterators.clear(new AsMapIterator());
            }
        }

        public final boolean containsKey(Object obj) {
            Map<K, Collection<V>> map = this.submap;
            Objects.requireNonNull(map);
            try {
                return map.containsKey(obj);
            } catch (ClassCastException | NullPointerException unused) {
                return false;
            }
        }

        public final boolean equals(Object obj) {
            if (this == obj || this.submap.equals(obj)) {
                return true;
            }
            return false;
        }

        public final Object get(Object obj) {
            Collection<V> collection;
            Map<K, Collection<V>> map = this.submap;
            Objects.requireNonNull(map);
            try {
                collection = map.get(obj);
            } catch (ClassCastException | NullPointerException unused) {
                collection = null;
            }
            Collection collection2 = collection;
            if (collection2 == null) {
                return null;
            }
            AbstractSetMultimap abstractSetMultimap = (AbstractSetMultimap) AbstractMapBasedMultimap.this;
            Objects.requireNonNull(abstractSetMultimap);
            return new WrappedSet(obj, (Set) collection2);
        }

        public final int hashCode() {
            return this.submap.hashCode();
        }

        public final Set<K> keySet() {
            LinkedHashMultimap linkedHashMultimap = (LinkedHashMultimap) AbstractMapBasedMultimap.this;
            Objects.requireNonNull(linkedHashMultimap);
            KeySet keySet = linkedHashMultimap.keySet;
            if (keySet != null) {
                return keySet;
            }
            KeySet keySet2 = new KeySet(linkedHashMultimap.map);
            linkedHashMultimap.keySet = keySet2;
            return keySet2;
        }

        public final Object remove(Object obj) {
            Collection remove = this.submap.remove(obj);
            if (remove == null) {
                return null;
            }
            LinkedHashMultimap linkedHashMultimap = (LinkedHashMultimap) AbstractMapBasedMultimap.this;
            Objects.requireNonNull(linkedHashMultimap);
            CompactLinkedHashSet compactLinkedHashSet = new CompactLinkedHashSet(linkedHashMultimap.valueSetCapacity);
            compactLinkedHashSet.addAll(remove);
            AbstractMapBasedMultimap.this.totalSize -= remove.size();
            remove.clear();
            return compactLinkedHashSet;
        }

        public final int size() {
            return this.submap.size();
        }

        public final String toString() {
            return this.submap.toString();
        }
    }

    public class KeySet extends Maps.KeySet<K, Collection<V>> {
        public KeySet(Map<K, Collection<V>> map) {
            super(map);
        }

        public final boolean containsAll(Collection<?> collection) {
            return this.map.keySet().containsAll(collection);
        }

        public final boolean equals(Object obj) {
            if (this == obj || this.map.keySet().equals(obj)) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return this.map.keySet().hashCode();
        }

        public final Iterator<K> iterator() {
            final Iterator<Map.Entry<K, V>> it = this.map.entrySet().iterator();
            return new Iterator<K>() {
                public Map.Entry<K, Collection<V>> entry;

                public final boolean hasNext() {
                    return it.hasNext();
                }

                public final K next() {
                    Map.Entry<K, Collection<V>> entry2 = (Map.Entry) it.next();
                    this.entry = entry2;
                    return entry2.getKey();
                }

                public final void remove() {
                    boolean z;
                    if (this.entry != null) {
                        z = true;
                    } else {
                        z = false;
                    }
                    Preconditions.checkState(z, "no calls to next() since the last call to remove()");
                    Collection value = this.entry.getValue();
                    it.remove();
                    AbstractMapBasedMultimap.this.totalSize -= value.size();
                    value.clear();
                    this.entry = null;
                }
            };
        }

        public final boolean remove(Object obj) {
            int i;
            Collection collection = (Collection) this.map.remove(obj);
            if (collection != null) {
                i = collection.size();
                collection.clear();
                AbstractMapBasedMultimap.this.totalSize -= i;
            } else {
                i = 0;
            }
            if (i > 0) {
                return true;
            }
            return false;
        }

        public final void clear() {
            Iterators.clear(iterator());
        }
    }

    public class WrappedCollection extends AbstractCollection<V> {
        public final AbstractMapBasedMultimap<K, V>.WrappedCollection ancestor = null;
        public final Collection<V> ancestorDelegate = null;
        public Collection<V> delegate;
        public final K key;

        public class WrappedIterator implements Iterator<V> {
            public final Iterator<V> delegateIterator;
            public final Collection<V> originalDelegate;

            public WrappedIterator() {
                Iterator<V> it;
                Collection<V> collection = WrappedCollection.this.delegate;
                this.originalDelegate = collection;
                if (collection instanceof List) {
                    it = ((List) collection).listIterator();
                } else {
                    it = collection.iterator();
                }
                this.delegateIterator = it;
            }

            public final boolean hasNext() {
                WrappedCollection.this.refreshIfEmpty();
                if (WrappedCollection.this.delegate == this.originalDelegate) {
                    return this.delegateIterator.hasNext();
                }
                throw new ConcurrentModificationException();
            }

            public final V next() {
                WrappedCollection.this.refreshIfEmpty();
                if (WrappedCollection.this.delegate == this.originalDelegate) {
                    return this.delegateIterator.next();
                }
                throw new ConcurrentModificationException();
            }

            public final void remove() {
                this.delegateIterator.remove();
                WrappedCollection wrappedCollection = WrappedCollection.this;
                AbstractMapBasedMultimap abstractMapBasedMultimap = AbstractMapBasedMultimap.this;
                abstractMapBasedMultimap.totalSize--;
                wrappedCollection.removeIfEmpty();
            }
        }

        public WrappedCollection(Object obj, Collection collection) {
            this.key = obj;
            this.delegate = collection;
        }

        public final void addToMap() {
            AbstractMapBasedMultimap<K, V>.WrappedCollection wrappedCollection = this.ancestor;
            if (wrappedCollection != null) {
                wrappedCollection.addToMap();
            } else {
                AbstractMapBasedMultimap.this.map.put(this.key, this.delegate);
            }
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            refreshIfEmpty();
            return this.delegate.equals(obj);
        }

        public final void refreshIfEmpty() {
            Collection<V> collection;
            AbstractMapBasedMultimap<K, V>.WrappedCollection wrappedCollection = this.ancestor;
            if (wrappedCollection != null) {
                wrappedCollection.refreshIfEmpty();
                AbstractMapBasedMultimap<K, V>.WrappedCollection wrappedCollection2 = this.ancestor;
                Objects.requireNonNull(wrappedCollection2);
                if (wrappedCollection2.delegate != this.ancestorDelegate) {
                    throw new ConcurrentModificationException();
                }
            } else if (this.delegate.isEmpty() && (collection = AbstractMapBasedMultimap.this.map.get(this.key)) != null) {
                this.delegate = collection;
            }
        }

        public final void removeIfEmpty() {
            AbstractMapBasedMultimap<K, V>.WrappedCollection wrappedCollection = this.ancestor;
            if (wrappedCollection != null) {
                wrappedCollection.removeIfEmpty();
            } else if (this.delegate.isEmpty()) {
                AbstractMapBasedMultimap.this.map.remove(this.key);
            }
        }

        public final boolean add(V v) {
            refreshIfEmpty();
            boolean isEmpty = this.delegate.isEmpty();
            boolean add = this.delegate.add(v);
            if (add) {
                AbstractMapBasedMultimap.this.totalSize++;
                if (isEmpty) {
                    addToMap();
                }
            }
            return add;
        }

        public final boolean addAll(Collection<? extends V> collection) {
            if (collection.isEmpty()) {
                return false;
            }
            int size = size();
            boolean addAll = this.delegate.addAll(collection);
            if (addAll) {
                int size2 = this.delegate.size();
                AbstractMapBasedMultimap.this.totalSize += size2 - size;
                if (size == 0) {
                    addToMap();
                }
            }
            return addAll;
        }

        public final void clear() {
            int size = size();
            if (size != 0) {
                this.delegate.clear();
                AbstractMapBasedMultimap.this.totalSize -= size;
                removeIfEmpty();
            }
        }

        public final boolean contains(Object obj) {
            refreshIfEmpty();
            return this.delegate.contains(obj);
        }

        public final boolean containsAll(Collection<?> collection) {
            refreshIfEmpty();
            return this.delegate.containsAll(collection);
        }

        public final int hashCode() {
            refreshIfEmpty();
            return this.delegate.hashCode();
        }

        public final Iterator<V> iterator() {
            refreshIfEmpty();
            return new WrappedIterator();
        }

        public final boolean remove(Object obj) {
            refreshIfEmpty();
            boolean remove = this.delegate.remove(obj);
            if (remove) {
                AbstractMapBasedMultimap abstractMapBasedMultimap = AbstractMapBasedMultimap.this;
                abstractMapBasedMultimap.totalSize--;
                removeIfEmpty();
            }
            return remove;
        }

        public final boolean retainAll(Collection<?> collection) {
            Objects.requireNonNull(collection);
            int size = size();
            boolean retainAll = this.delegate.retainAll(collection);
            if (retainAll) {
                int size2 = this.delegate.size();
                AbstractMapBasedMultimap.this.totalSize += size2 - size;
                removeIfEmpty();
            }
            return retainAll;
        }

        public final int size() {
            refreshIfEmpty();
            return this.delegate.size();
        }

        public final String toString() {
            refreshIfEmpty();
            return this.delegate.toString();
        }
    }

    public class WrappedSet extends AbstractMapBasedMultimap<K, V>.WrappedCollection implements Set<V> {
        public WrappedSet(K k, Set<V> set) {
            super(k, set);
        }

        public final boolean removeAll(Collection<?> collection) {
            if (collection.isEmpty()) {
                return false;
            }
            int size = size();
            boolean removeAllImpl = Sets.removeAllImpl((Set) this.delegate, collection);
            if (removeAllImpl) {
                int size2 = this.delegate.size();
                AbstractMapBasedMultimap.this.totalSize += size2 - size;
                removeIfEmpty();
            }
            return removeAllImpl;
        }
    }

    public abstract void clear();
}
