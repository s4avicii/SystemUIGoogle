package com.google.common.collect;

import androidx.recyclerview.R$dimen;
import java.util.Map;

public abstract class AbstractMapEntry<K, V> implements Map.Entry<K, V> {
    public abstract K getKey();

    public abstract V getValue();

    public boolean equals(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if (!R$dimen.equal(getKey(), entry.getKey()) || !R$dimen.equal(getValue(), entry.getValue())) {
            return false;
        }
        return true;
    }

    public V setValue(V v) {
        throw new UnsupportedOperationException();
    }

    public final String toString() {
        return getKey() + "=" + getValue();
    }

    public int hashCode() {
        int i;
        Object key = getKey();
        Object value = getValue();
        int i2 = 0;
        if (key == null) {
            i = 0;
        } else {
            i = key.hashCode();
        }
        if (value != null) {
            i2 = value.hashCode();
        }
        return i ^ i2;
    }
}
