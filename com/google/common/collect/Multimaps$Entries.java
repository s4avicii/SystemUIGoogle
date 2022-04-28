package com.google.common.collect;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public abstract class Multimaps$Entries<K, V> extends AbstractCollection<Map.Entry<K, V>> {
    public final void clear() {
        ((LinkedHashMultimap) AbstractMultimap.this).clear();
    }

    public final boolean contains(Object obj) {
        if (obj instanceof Map.Entry) {
            Map.Entry entry = (Map.Entry) obj;
            AbstractMultimap abstractMultimap = AbstractMultimap.this;
            Object key = entry.getKey();
            Object value = entry.getValue();
            Objects.requireNonNull(abstractMultimap);
            Collection collection = (Collection) abstractMultimap.asMap().get(key);
            if (collection == null || !collection.contains(value)) {
                return false;
            }
            return true;
        }
        return false;
    }

    public final boolean remove(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        AbstractMultimap abstractMultimap = AbstractMultimap.this;
        Object key = entry.getKey();
        Object value = entry.getValue();
        Objects.requireNonNull(abstractMultimap);
        Collection collection = (Collection) abstractMultimap.asMap().get(key);
        if (collection == null || !collection.remove(value)) {
            return false;
        }
        return true;
    }

    public final int size() {
        AbstractMapBasedMultimap abstractMapBasedMultimap = (AbstractMapBasedMultimap) AbstractMultimap.this;
        Objects.requireNonNull(abstractMapBasedMultimap);
        return abstractMapBasedMultimap.totalSize;
    }
}
