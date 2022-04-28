package com.android.systemui.util.wakelock;

import android.content.Context;
import com.android.systemui.util.wakelock.WakeLock;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class WakeLock_Builder_Factory implements Factory<WakeLock.Builder> {
    public final Provider<Context> contextProvider;

    public final Object get() {
        return new WakeLock.Builder(this.contextProvider.get());
    }

    public WakeLock_Builder_Factory(Provider<Context> provider) {
        this.contextProvider = provider;
    }
}
