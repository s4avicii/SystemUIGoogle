package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMapBasedMultimap;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractMultimap<K, V> implements Multimap<K, V> {
    @LazyInit
    public transient AbstractMapBasedMultimap.AsMap asMap;
    @LazyInit
    public transient Entries entries;
    @LazyInit
    public transient AbstractMapBasedMultimap.KeySet keySet;

    public class Entries extends Multimaps$Entries<K, V> {
        public Entries() {
        }

        public final Iterator<Map.Entry<K, V>> iterator() {
            LinkedHashMultimap linkedHashMultimap = (LinkedHashMultimap) AbstractMultimap.this;
            Objects.requireNonNull(linkedHashMultimap);
            return new Iterator<Map.Entry<Object, Object>>() {
                public ValueEntry<Object, Object> nextEntry;
                public ValueEntry<Object, Object> toRemove;

                {
                    ValueEntry<K, V> valueEntry = LinkedHashMultimap.this.multimapHeaderEntry;
                    Objects.requireNonNull(valueEntry);
                    ValueEntry<K, V> valueEntry2 = valueEntry.successorInMultimap;
                    Objects.requireNonNull(valueEntry2);
                    this.nextEntry = valueEntry2;
                }

                public final boolean hasNext() {
                    if (this.nextEntry != LinkedHashMultimap.this.multimapHeaderEntry) {
                        return true;
                    }
                    return false;
                }

                public final void remove() {
                    boolean z;
                    if (this.toRemove != null) {
                        z = true;
                    } else {
                        z = false;
                    }
                    Preconditions.checkState(z, "no calls to next() since the last call to remove()");
                    LinkedHashMultimap linkedHashMultimap = LinkedHashMultimap.this;
                    ValueEntry<Object, Object> valueEntry = this.toRemove;
                    Objects.requireNonNull(valueEntry);
                    K k = valueEntry.key;
                    ValueEntry<Object, Object> valueEntry2 = this.toRemove;
                    Objects.requireNonNull(valueEntry2);
                    V v = valueEntry2.value;
                    Objects.requireNonNull(linkedHashMultimap);
                    Collection collection = (Collection) linkedHashMultimap.asMap().get(k);
                    if (collection != null) {
                        boolean remove = collection.remove(v);
                    }
                    this.toRemove = null;
                }

                public final Object next() {
                    if (hasNext()) {
                        ValueEntry<Object, Object> valueEntry = this.nextEntry;
                        this.toRemove = valueEntry;
                        Objects.requireNonNull(valueEntry);
                        ValueEntry<K, V> valueEntry2 = valueEntry.successorInMultimap;
                        Objects.requireNonNull(valueEntry2);
                        this.nextEntry = valueEntry2;
                        return valueEntry;
                    }
                    throw new NoSuchElementException();
                }
            };
        }
    }

    public abstract AbstractMapBasedMultimap.AsMap asMap();

    public class EntrySet extends AbstractMultimap<K, V>.Entries implements Set<Map.Entry<K, V>> {
        public final boolean equals(Object obj) {
            return Sets.equalsImpl(this, obj);
        }

        public final int hashCode() {
            return Sets.hashCodeImpl(this);
        }

        public EntrySet(AbstractMultimap abstractMultimap) {
            super();
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Multimap) {
            return ((AbstractSetMultimap) this).asMap().equals(((Multimap) obj).asMap());
        }
        return false;
    }

    public final int hashCode() {
        return asMap().hashCode();
    }

    public final String toString() {
        return asMap().toString();
    }
}
