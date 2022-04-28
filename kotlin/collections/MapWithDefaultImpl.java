package kotlin.collections;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import kotlin.jvm.functions.Function1;

/* compiled from: MapWithDefault.kt */
public final class MapWithDefaultImpl<K, V> implements MapWithDefault<K, V> {

    /* renamed from: default  reason: not valid java name */
    public final Function1<K, V> f172default;
    public final Map<K, V> map;

    public final void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public final V put(K k, V v) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public final void putAll(Map<? extends K, ? extends V> map2) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public final V remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public final boolean containsKey(Object obj) {
        return this.map.containsKey(obj);
    }

    public final boolean containsValue(Object obj) {
        return this.map.containsValue(obj);
    }

    public final Set<Map.Entry<K, V>> entrySet() {
        return this.map.entrySet();
    }

    public final boolean equals(Object obj) {
        return this.map.equals(obj);
    }

    public final V get(Object obj) {
        return this.map.get(obj);
    }

    public final V getOrImplicitDefault(K k) {
        Map<K, V> map2 = this.map;
        V v = map2.get(k);
        if (v != null || map2.containsKey(k)) {
            return v;
        }
        return this.f172default.invoke(k);
    }

    public final int hashCode() {
        return this.map.hashCode();
    }

    public final boolean isEmpty() {
        return this.map.isEmpty();
    }

    public final Set<K> keySet() {
        return this.map.keySet();
    }

    public final int size() {
        return this.map.size();
    }

    public final String toString() {
        return this.map.toString();
    }

    public final Collection<V> values() {
        return this.map.values();
    }

    public MapWithDefaultImpl(Map<K, ? extends V> map2, Function1<? super K, ? extends V> function1) {
        this.map = map2;
        this.f172default = function1;
    }

    public final Map<K, V> getMap() {
        return this.map;
    }
}
