package com.android.systemui.statusbar.phone;

import android.content.Context;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.policy.BatteryController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.systemui.statusbar.phone.LightBarController_Factory  reason: case insensitive filesystem */
public final class C2508LightBarController_Factory implements Factory<LightBarController> {
    public final Provider<BatteryController> batteryControllerProvider;
    public final Provider<Context> ctxProvider;
    public final Provider<DarkIconDispatcher> darkIconDispatcherProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<NavigationModeController> navModeControllerProvider;

    public final Object get() {
        return new LightBarController(this.ctxProvider.get(), this.darkIconDispatcherProvider.get(), this.batteryControllerProvider.get(), this.navModeControllerProvider.get(), this.dumpManagerProvider.get());
    }

    public C2508LightBarController_Factory(Provider<Context> provider, Provider<DarkIconDispatcher> provider2, Provider<BatteryController> provider3, Provider<NavigationModeController> provider4, Provider<DumpManager> provider5) {
        this.ctxProvider = provider;
        this.darkIconDispatcherProvider = provider2;
        this.batteryControllerProvider = provider3;
        this.navModeControllerProvider = provider4;
        this.dumpManagerProvider = provider5;
    }
}
