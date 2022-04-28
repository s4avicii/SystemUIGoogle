package com.android.keyguard;

import android.content.Context;
import android.os.Handler;
import com.android.keyguard.AdminSecondaryLockScreenController;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class AdminSecondaryLockScreenController_Factory_Factory implements Factory<AdminSecondaryLockScreenController.Factory> {
    public final Provider<Context> contextProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<KeyguardSecurityContainer> parentProvider;
    public final Provider<KeyguardUpdateMonitor> updateMonitorProvider;

    public final Object get() {
        return new AdminSecondaryLockScreenController.Factory(this.contextProvider.get(), this.parentProvider.get(), this.updateMonitorProvider.get(), this.handlerProvider.get());
    }

    public AdminSecondaryLockScreenController_Factory_Factory(Provider<Context> provider, Provider<KeyguardSecurityContainer> provider2, Provider<KeyguardUpdateMonitor> provider3, Provider<Handler> provider4) {
        this.contextProvider = provider;
        this.parentProvider = provider2;
        this.updateMonitorProvider = provider3;
        this.handlerProvider = provider4;
    }
}
