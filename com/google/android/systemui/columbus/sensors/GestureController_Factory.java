package com.google.android.systemui.columbus.sensors;

import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.statusbar.phone.StatusBar;
import com.google.android.systemui.elmyra.feedback.SquishyNavigationButtons;
import dagger.internal.Factory;
import java.util.Set;
import javax.inject.Provider;

public final class GestureController_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider commandRegistryProvider;
    public final Provider gestureSensorProvider;
    public final Provider softGatesProvider;
    public final Provider uiEventLoggerProvider;

    public /* synthetic */ GestureController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, int i) {
        this.$r8$classId = i;
        this.gestureSensorProvider = provider;
        this.softGatesProvider = provider2;
        this.commandRegistryProvider = provider3;
        this.uiEventLoggerProvider = provider4;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new GestureController((GestureSensor) this.gestureSensorProvider.get(), (Set) this.softGatesProvider.get(), (CommandRegistry) this.commandRegistryProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get());
            default:
                return new SquishyNavigationButtons((Context) this.gestureSensorProvider.get(), (KeyguardViewMediator) this.softGatesProvider.get(), (StatusBar) this.commandRegistryProvider.get(), (NavigationModeController) this.uiEventLoggerProvider.get());
        }
    }
}
