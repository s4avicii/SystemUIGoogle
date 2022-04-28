package com.google.protobuf;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public final class LazyField extends LazyFieldLite {

    public static class LazyEntry<K> implements Map.Entry<K, Object> {
        public Map.Entry<K, LazyField> entry;

        public final K getKey() {
            return this.entry.getKey();
        }

        public final Object getValue() {
            LazyField value = this.entry.getValue();
            if (value == null) {
                return null;
            }
            return value.getValue((MessageLite) null);
        }

        public final Object setValue(Object obj) {
            if (obj instanceof MessageLite) {
                LazyField value = this.entry.getValue();
                Objects.requireNonNull(value);
                MessageLite messageLite = value.value;
                value.memoizedBytes = null;
                value.value = (MessageLite) obj;
                return messageLite;
            }
            throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
        }

        public LazyEntry(Map.Entry entry2) {
            this.entry = entry2;
        }
    }

    public static class LazyIterator<K> implements Iterator<Map.Entry<K, Object>> {
        public Iterator<Map.Entry<K, Object>> iterator;

        public final boolean hasNext() {
            return this.iterator.hasNext();
        }

        public final Object next() {
            Map.Entry next = this.iterator.next();
            if (next.getValue() instanceof LazyField) {
                return new LazyEntry(next);
            }
            return next;
        }

        public final void remove() {
            this.iterator.remove();
        }

        public LazyIterator(Iterator<Map.Entry<K, Object>> it) {
            this.iterator = it;
        }
    }

    public final boolean equals(Object obj) {
        return getValue((MessageLite) null).equals(obj);
    }

    public final int hashCode() {
        return getValue((MessageLite) null).hashCode();
    }

    public final String toString() {
        return getValue((MessageLite) null).toString();
    }
}
