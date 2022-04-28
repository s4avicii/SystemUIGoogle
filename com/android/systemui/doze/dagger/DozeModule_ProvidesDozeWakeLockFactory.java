package com.android.systemui.doze.dagger;

import android.os.Handler;
import com.android.systemui.util.wakelock.DelayedWakeLock;
import com.android.systemui.util.wakelock.WakeLock;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class DozeModule_ProvidesDozeWakeLockFactory implements Factory<WakeLock> {
    public final Provider<DelayedWakeLock.Builder> delayedWakeLockBuilderProvider;
    public final Provider<Handler> handlerProvider;

    public final Object get() {
        DelayedWakeLock.Builder builder = this.delayedWakeLockBuilderProvider.get();
        Handler handler = this.handlerProvider.get();
        Objects.requireNonNull(builder);
        builder.mHandler = handler;
        builder.mTag = "Doze";
        return new DelayedWakeLock(handler, WakeLock.createPartial(builder.mContext, "Doze"));
    }

    public DozeModule_ProvidesDozeWakeLockFactory(Provider<DelayedWakeLock.Builder> provider, Provider<Handler> provider2) {
        this.delayedWakeLockBuilderProvider = provider;
        this.handlerProvider = provider2;
    }
}
