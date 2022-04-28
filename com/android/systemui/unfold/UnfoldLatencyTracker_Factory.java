package com.android.systemui.unfold;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import com.android.internal.util.LatencyTracker;
import com.android.systemui.keyguard.ScreenLifecycle;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class UnfoldLatencyTracker_Factory implements Factory<UnfoldLatencyTracker> {
    public final Provider<Context> contextProvider;
    public final Provider<DeviceStateManager> deviceStateManagerProvider;
    public final Provider<LatencyTracker> latencyTrackerProvider;
    public final Provider<ScreenLifecycle> screenLifecycleProvider;
    public final Provider<Executor> uiBgExecutorProvider;

    public final Object get() {
        return new UnfoldLatencyTracker(this.latencyTrackerProvider.get(), this.deviceStateManagerProvider.get(), this.uiBgExecutorProvider.get(), this.contextProvider.get(), this.screenLifecycleProvider.get());
    }

    public UnfoldLatencyTracker_Factory(Provider<LatencyTracker> provider, Provider<DeviceStateManager> provider2, Provider<Executor> provider3, Provider<Context> provider4, Provider<ScreenLifecycle> provider5) {
        this.latencyTrackerProvider = provider;
        this.deviceStateManagerProvider = provider2;
        this.uiBgExecutorProvider = provider3;
        this.contextProvider = provider4;
        this.screenLifecycleProvider = provider5;
    }
}
