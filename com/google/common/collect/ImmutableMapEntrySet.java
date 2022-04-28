package com.google.common.collect;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

abstract class ImmutableMapEntrySet<K, V> extends ImmutableSet<Map.Entry<K, V>> {

    public static class EntrySetSerializedForm<K, V> implements Serializable {
        private static final long serialVersionUID = 0;
        public final ImmutableMap<K, V> map;

        public Object readResolve() {
            return this.map.entrySet();
        }

        public EntrySetSerializedForm(ImmutableMap<K, V> immutableMap) {
            this.map = immutableMap;
        }
    }

    public abstract ImmutableSortedMap map();

    public final boolean contains(Object obj) {
        if (obj instanceof Map.Entry) {
            Map.Entry entry = (Map.Entry) obj;
            Object obj2 = map().get(entry.getKey());
            if (obj2 == null || !obj2.equals(entry.getValue())) {
                return false;
            }
            return true;
        }
        return false;
    }

    public Object writeReplace() {
        return new EntrySetSerializedForm(map());
    }

    public final int hashCode() {
        return map().hashCode();
    }

    public final boolean isHashCodeFast() {
        Objects.requireNonNull(map());
        return false;
    }

    public final int size() {
        return map().size();
    }
}
