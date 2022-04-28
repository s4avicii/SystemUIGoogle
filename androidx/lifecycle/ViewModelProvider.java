package androidx.lifecycle;

import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ViewModelProvider.kt */
public class ViewModelProvider {
    public final Factory factory;
    public final ViewModelStore store;

    /* compiled from: ViewModelProvider.kt */
    public interface Factory {
        ViewModel create();
    }

    /* compiled from: ViewModelProvider.kt */
    public static abstract class KeyedFactory extends OnRequeryFactory implements Factory {
        public abstract ViewModel create$1();

        public final ViewModel create() {
            throw new UnsupportedOperationException("create(String, Class<?>) must be called on implementations of KeyedFactory");
        }
    }

    /* compiled from: ViewModelProvider.kt */
    public static class OnRequeryFactory {
    }

    public final <T extends ViewModel> T get(Class<T> cls) {
        String canonicalName = cls.getCanonicalName();
        if (canonicalName != null) {
            return get(Intrinsics.stringPlus("androidx.lifecycle.ViewModelProvider.DefaultKey:", canonicalName), cls);
        }
        throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
    }

    public ViewModelProvider(ViewModelStore viewModelStore, Factory factory2) {
        this.store = viewModelStore;
        this.factory = factory2;
    }

    public final <T extends ViewModel> T get(String str, Class<T> cls) {
        T t;
        ViewModelStore viewModelStore = this.store;
        Objects.requireNonNull(viewModelStore);
        T t2 = (ViewModel) viewModelStore.mMap.get(str);
        if (cls.isInstance(t2)) {
            Factory factory2 = this.factory;
            if (factory2 instanceof OnRequeryFactory) {
                OnRequeryFactory onRequeryFactory = (OnRequeryFactory) factory2;
            }
            Objects.requireNonNull(t2, "null cannot be cast to non-null type T of androidx.lifecycle.ViewModelProvider.get");
            return t2;
        }
        Factory factory3 = this.factory;
        if (factory3 instanceof KeyedFactory) {
            t = ((KeyedFactory) factory3).create$1();
        } else {
            t = factory3.create();
        }
        ViewModelStore viewModelStore2 = this.store;
        Objects.requireNonNull(viewModelStore2);
        ViewModel put = viewModelStore2.mMap.put(str, t);
        if (put != null) {
            put.onCleared();
        }
        return t;
    }
}
