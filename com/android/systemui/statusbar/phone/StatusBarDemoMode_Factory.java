package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.view.View;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.policy.Clock;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.tuner.TunerService;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class StatusBarDemoMode_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider clockViewProvider;
    public final Provider demoModeControllerProvider;
    public final Provider displayIdProvider;
    public final Provider navigationBarControllerProvider;
    public final Provider operatorNameViewProvider;
    public final Provider phoneStatusBarTransitionsProvider;

    public /* synthetic */ StatusBarDemoMode_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, int i) {
        this.$r8$classId = i;
        this.clockViewProvider = provider;
        this.operatorNameViewProvider = provider2;
        this.demoModeControllerProvider = provider3;
        this.phoneStatusBarTransitionsProvider = provider4;
        this.navigationBarControllerProvider = provider5;
        this.displayIdProvider = provider6;
    }

    public static StatusBarDemoMode_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        return new StatusBarDemoMode_Factory(provider, provider2, provider3, provider4, provider5, provider6, 1);
    }

    public static StatusBarDemoMode_Factory create$1(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        return new StatusBarDemoMode_Factory(provider, provider2, provider3, provider4, provider5, provider6, 0);
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new StatusBarDemoMode((Clock) this.clockViewProvider.get(), (View) this.operatorNameViewProvider.get(), (DemoModeController) this.demoModeControllerProvider.get(), (PhoneStatusBarTransitions) this.phoneStatusBarTransitionsProvider.get(), (NavigationBarController) this.navigationBarControllerProvider.get(), ((Integer) this.displayIdProvider.get()).intValue());
            default:
                return new KeyguardBypassController((Context) this.clockViewProvider.get(), (TunerService) this.operatorNameViewProvider.get(), (StatusBarStateController) this.demoModeControllerProvider.get(), (NotificationLockscreenUserManager) this.phoneStatusBarTransitionsProvider.get(), (KeyguardStateController) this.navigationBarControllerProvider.get(), (DumpManager) this.displayIdProvider.get());
        }
    }
}
