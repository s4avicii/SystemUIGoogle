package com.android.systemui.unfold.updates;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import android.os.Handler;
import com.android.p012wm.shell.dagger.WMShellBaseModule_ProvideRecentTasksFactory;
import com.android.systemui.unfold.updates.hinge.HingeAngleProvider;
import com.android.systemui.unfold.updates.screen.ScreenStatusProvider;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class DeviceFoldStateProvider_Factory implements Factory<DeviceFoldStateProvider> {
    public final Provider<Context> contextProvider;
    public final Provider<DeviceStateManager> deviceStateManagerProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<HingeAngleProvider> hingeAngleProvider;
    public final Provider<Executor> mainExecutorProvider;
    public final Provider<ScreenStatusProvider> screenStatusProvider;

    public static DeviceFoldStateProvider_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, WMShellBaseModule_ProvideRecentTasksFactory wMShellBaseModule_ProvideRecentTasksFactory) {
        return new DeviceFoldStateProvider_Factory(provider, provider2, provider3, provider4, provider5, wMShellBaseModule_ProvideRecentTasksFactory);
    }

    public final Object get() {
        return new DeviceFoldStateProvider(this.contextProvider.get(), this.hingeAngleProvider.get(), this.screenStatusProvider.get(), this.deviceStateManagerProvider.get(), this.mainExecutorProvider.get(), this.handlerProvider.get());
    }

    public DeviceFoldStateProvider_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, WMShellBaseModule_ProvideRecentTasksFactory wMShellBaseModule_ProvideRecentTasksFactory) {
        this.contextProvider = provider;
        this.hingeAngleProvider = provider2;
        this.screenStatusProvider = provider3;
        this.deviceStateManagerProvider = provider4;
        this.mainExecutorProvider = provider5;
        this.handlerProvider = wMShellBaseModule_ProvideRecentTasksFactory;
    }
}
