package com.android.systemui.p006qs;

import android.app.IActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.FgsManagerController_Factory */
public final class FgsManagerController_Factory implements Factory<FgsManagerController> {
    public final Provider<IActivityManager> activityManagerProvider;
    public final Provider<Executor> backgroundExecutorProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DeviceConfigProxy> deviceConfigProxyProvider;
    public final Provider<DialogLaunchAnimator> dialogLaunchAnimatorProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<Executor> mainExecutorProvider;
    public final Provider<PackageManager> packageManagerProvider;
    public final Provider<SystemClock> systemClockProvider;

    public static FgsManagerController_Factory create(Provider<Context> provider, Provider<Executor> provider2, Provider<Executor> provider3, Provider<SystemClock> provider4, Provider<IActivityManager> provider5, Provider<PackageManager> provider6, Provider<DeviceConfigProxy> provider7, Provider<DialogLaunchAnimator> provider8, Provider<DumpManager> provider9) {
        return new FgsManagerController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
    }

    public final Object get() {
        return new FgsManagerController(this.contextProvider.get(), this.mainExecutorProvider.get(), this.backgroundExecutorProvider.get(), this.systemClockProvider.get(), this.activityManagerProvider.get(), this.packageManagerProvider.get(), this.deviceConfigProxyProvider.get(), this.dialogLaunchAnimatorProvider.get(), this.dumpManagerProvider.get());
    }

    public FgsManagerController_Factory(Provider<Context> provider, Provider<Executor> provider2, Provider<Executor> provider3, Provider<SystemClock> provider4, Provider<IActivityManager> provider5, Provider<PackageManager> provider6, Provider<DeviceConfigProxy> provider7, Provider<DialogLaunchAnimator> provider8, Provider<DumpManager> provider9) {
        this.contextProvider = provider;
        this.mainExecutorProvider = provider2;
        this.backgroundExecutorProvider = provider3;
        this.systemClockProvider = provider4;
        this.activityManagerProvider = provider5;
        this.packageManagerProvider = provider6;
        this.deviceConfigProxyProvider = provider7;
        this.dialogLaunchAnimatorProvider = provider8;
        this.dumpManagerProvider = provider9;
    }
}
