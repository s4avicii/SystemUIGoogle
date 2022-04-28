package com.google.common.collect;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract class ForwardingMap<K, V> extends ForwardingObject implements Map<K, V> {
    public abstract Map<K, V> delegate();

    public final boolean equals(Object obj) {
        if (obj == this || delegate().equals(obj)) {
            return true;
        }
        return false;
    }

    public final void clear() {
        delegate().clear();
    }

    public final boolean containsKey(Object obj) {
        return delegate().containsKey(obj);
    }

    public final boolean containsValue(Object obj) {
        return delegate().containsValue(obj);
    }

    public Set<Map.Entry<K, V>> entrySet() {
        return delegate().entrySet();
    }

    public final V get(Object obj) {
        return delegate().get(obj);
    }

    public final int hashCode() {
        return delegate().hashCode();
    }

    public final boolean isEmpty() {
        return delegate().isEmpty();
    }

    public Set<K> keySet() {
        return delegate().keySet();
    }

    @CanIgnoreReturnValue
    public final V put(K k, V v) {
        return delegate().put(k, v);
    }

    public final void putAll(Map<? extends K, ? extends V> map) {
        delegate().putAll(map);
    }

    @CanIgnoreReturnValue
    public final V remove(Object obj) {
        return delegate().remove(obj);
    }

    public final int size() {
        return delegate().size();
    }

    public Collection<V> values() {
        return delegate().values();
    }
}
