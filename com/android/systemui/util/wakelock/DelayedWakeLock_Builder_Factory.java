package com.android.systemui.util.wakelock;

import android.content.Context;
import com.android.systemui.util.wakelock.DelayedWakeLock;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class DelayedWakeLock_Builder_Factory implements Factory<DelayedWakeLock.Builder> {
    public final Provider<Context> contextProvider;

    public final Object get() {
        return new DelayedWakeLock.Builder(this.contextProvider.get());
    }

    public DelayedWakeLock_Builder_Factory(Provider<Context> provider) {
        this.contextProvider = provider;
    }
}
