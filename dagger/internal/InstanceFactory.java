package dagger.internal;

import dagger.Lazy;
import java.util.Objects;

public final class InstanceFactory<T> implements Factory<T>, Lazy<T> {
    public static final InstanceFactory<Object> NULL_INSTANCE_FACTORY = new InstanceFactory<>((Object) null);
    public final T instance;

    public static InstanceFactory create(Object obj) {
        Objects.requireNonNull(obj, "instance cannot be null");
        return new InstanceFactory(obj);
    }

    public InstanceFactory(T t) {
        this.instance = t;
    }

    public final T get() {
        return this.instance;
    }
}
