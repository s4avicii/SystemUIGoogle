package com.google.android.systemui.reversecharging;

import android.content.Context;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.policy.BatteryController;
import com.google.android.systemui.statusbar.KeyguardIndicationControllerGoogle;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class ReverseChargingViewController_Factory implements Factory<ReverseChargingViewController> {
    public final Provider<BatteryController> batteryControllerProvider;
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<Context> contextProvider;
    public final Provider<KeyguardIndicationControllerGoogle> keyguardIndicationControllerProvider;
    public final Provider<Executor> mainExecutorProvider;
    public final Provider<StatusBarIconController> statusBarIconControllerProvider;
    public final Provider<StatusBar> statusBarLazyProvider;

    public final Object get() {
        return new ReverseChargingViewController(this.contextProvider.get(), this.batteryControllerProvider.get(), DoubleCheck.lazy(this.statusBarLazyProvider), this.statusBarIconControllerProvider.get(), this.broadcastDispatcherProvider.get(), this.mainExecutorProvider.get(), this.keyguardIndicationControllerProvider.get());
    }

    public ReverseChargingViewController_Factory(Provider<Context> provider, Provider<BatteryController> provider2, Provider<StatusBar> provider3, Provider<StatusBarIconController> provider4, Provider<BroadcastDispatcher> provider5, Provider<Executor> provider6, Provider<KeyguardIndicationControllerGoogle> provider7) {
        this.contextProvider = provider;
        this.batteryControllerProvider = provider2;
        this.statusBarLazyProvider = provider3;
        this.statusBarIconControllerProvider = provider4;
        this.broadcastDispatcherProvider = provider5;
        this.mainExecutorProvider = provider6;
        this.keyguardIndicationControllerProvider = provider7;
    }
}
