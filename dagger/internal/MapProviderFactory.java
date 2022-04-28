package dagger.internal;

import dagger.Lazy;
import dagger.internal.AbstractMapFactory;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import javax.inject.Provider;

public final class MapProviderFactory<K, V> extends AbstractMapFactory<K, V, Provider<V>> implements Lazy<Map<K, Provider<V>>> {

    public static final class Builder<K, V> extends AbstractMapFactory.Builder<K, V, Provider<V>> {
        public final MapProviderFactory<K, V> build() {
            return new MapProviderFactory<>(this.map);
        }

        public final Builder put(Class cls, Provider provider) {
            LinkedHashMap<K, Provider<V>> linkedHashMap = this.map;
            Objects.requireNonNull(provider, "provider");
            linkedHashMap.put(cls, provider);
            return this;
        }

        public Builder(int i) {
            super(i);
        }
    }

    public MapProviderFactory(LinkedHashMap linkedHashMap) {
        super(linkedHashMap);
    }

    public final Object get() {
        return this.contributingMap;
    }
}
