package androidx.arch.core.internal;

import androidx.arch.core.internal.SafeIterableMap;
import java.util.HashMap;

public final class FastSafeIterableMap<K, V> extends SafeIterableMap<K, V> {
    public HashMap<K, SafeIterableMap.Entry<K, V>> mHashMap = new HashMap<>();

    public final SafeIterableMap.Entry<K, V> get(K k) {
        return this.mHashMap.get(k);
    }

    public final V putIfAbsent(K k, V v) {
        SafeIterableMap.Entry entry = get(k);
        if (entry != null) {
            return entry.mValue;
        }
        HashMap<K, SafeIterableMap.Entry<K, V>> hashMap = this.mHashMap;
        SafeIterableMap.Entry<K, V> entry2 = new SafeIterableMap.Entry<>(k, v);
        this.mSize++;
        SafeIterableMap.Entry<K, V> entry3 = this.mEnd;
        if (entry3 == null) {
            this.mStart = entry2;
            this.mEnd = entry2;
        } else {
            entry3.mNext = entry2;
            entry2.mPrevious = entry3;
            this.mEnd = entry2;
        }
        hashMap.put(k, entry2);
        return null;
    }

    public final V remove(K k) {
        V remove = super.remove(k);
        this.mHashMap.remove(k);
        return remove;
    }
}
