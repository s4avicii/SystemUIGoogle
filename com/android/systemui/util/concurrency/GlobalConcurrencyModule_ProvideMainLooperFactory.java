package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Factory;
import java.util.Objects;

public final class GlobalConcurrencyModule_ProvideMainLooperFactory implements Factory<Looper> {

    public static final class InstanceHolder {
        public static final GlobalConcurrencyModule_ProvideMainLooperFactory INSTANCE = new GlobalConcurrencyModule_ProvideMainLooperFactory();
    }

    public final Object get() {
        Looper mainLooper = Looper.getMainLooper();
        Objects.requireNonNull(mainLooper, "Cannot return null from a non-@Nullable @Provides method");
        return mainLooper;
    }
}
