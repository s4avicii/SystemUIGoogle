package com.android.systemui.util.concurrency;

import android.os.HandlerThread;
import android.os.Looper;
import dagger.internal.Factory;
import java.util.Objects;

public final class SysUIConcurrencyModule_ProvideLongRunningLooperFactory implements Factory<Looper> {

    public static final class InstanceHolder {
        public static final SysUIConcurrencyModule_ProvideLongRunningLooperFactory INSTANCE = new SysUIConcurrencyModule_ProvideLongRunningLooperFactory();
    }

    public final Object get() {
        HandlerThread handlerThread = new HandlerThread("SysUiLng", 10);
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        Objects.requireNonNull(looper, "Cannot return null from a non-@Nullable @Provides method");
        return looper;
    }
}
