package com.google.android.systemui.elmyra.feedback;

import android.content.Context;
import com.android.p012wm.shell.pip.PipSnapAlgorithm;
import com.android.p012wm.shell.pip.p013tv.TvPipBoundsAlgorithm;
import com.android.p012wm.shell.pip.p013tv.TvPipBoundsState;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.statusbar.notification.collection.coordinator.GutsCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.GutsCoordinatorLogger;
import com.android.systemui.statusbar.notification.collection.render.NotifGutsViewManager;
import com.android.systemui.statusbar.phone.StatusBar;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class OpaHomeButton_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider keyguardViewMediatorProvider;
    public final Provider navModeControllerProvider;
    public final Provider statusBarProvider;

    public /* synthetic */ OpaHomeButton_Factory(Provider provider, Provider provider2, Provider provider3, int i) {
        this.$r8$classId = i;
        this.keyguardViewMediatorProvider = provider;
        this.statusBarProvider = provider2;
        this.navModeControllerProvider = provider3;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new OpaHomeButton((KeyguardViewMediator) this.keyguardViewMediatorProvider.get(), (StatusBar) this.statusBarProvider.get(), (NavigationModeController) this.navModeControllerProvider.get());
            case 1:
                return new GutsCoordinator((NotifGutsViewManager) this.keyguardViewMediatorProvider.get(), (GutsCoordinatorLogger) this.statusBarProvider.get(), (DumpManager) this.navModeControllerProvider.get());
            default:
                return new TvPipBoundsAlgorithm((Context) this.keyguardViewMediatorProvider.get(), (TvPipBoundsState) this.statusBarProvider.get(), (PipSnapAlgorithm) this.navModeControllerProvider.get());
        }
    }
}
