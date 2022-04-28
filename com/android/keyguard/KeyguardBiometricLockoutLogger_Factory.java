package com.android.keyguard;

import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class KeyguardBiometricLockoutLogger_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider contextProvider;
    public final Provider keyguardUpdateMonitorProvider;
    public final Provider sessionTrackerProvider;
    public final Provider uiEventLoggerProvider;

    public /* synthetic */ KeyguardBiometricLockoutLogger_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.uiEventLoggerProvider = provider2;
        this.keyguardUpdateMonitorProvider = provider3;
        this.sessionTrackerProvider = provider4;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new KeyguardBiometricLockoutLogger((Context) this.contextProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (SessionTracker) this.sessionTrackerProvider.get());
            default:
                return new ControlsBindingControllerImpl((Context) this.contextProvider.get(), (DelayableExecutor) this.uiEventLoggerProvider.get(), DoubleCheck.lazy(this.keyguardUpdateMonitorProvider), (UserTracker) this.sessionTrackerProvider.get());
        }
    }
}
