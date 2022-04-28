package com.google.android.systemui.gamedashboard;

import android.content.Context;
import android.content.om.OverlayManager;
import android.view.WindowManager;
import com.android.internal.logging.UiEventLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.theme.ThemeOverlayApplier;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class ToastController_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider configurationControllerProvider;
    public final Provider contextProvider;
    public final Provider navigationModeControllerProvider;
    public final Provider uiEventLoggerProvider;
    public final Provider windowManagerProvider;

    public /* synthetic */ ToastController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.configurationControllerProvider = provider2;
        this.windowManagerProvider = provider3;
        this.uiEventLoggerProvider = provider4;
        this.navigationModeControllerProvider = provider5;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new ToastController((Context) this.contextProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (WindowManager) this.windowManagerProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (NavigationModeController) this.navigationModeControllerProvider.get());
            default:
                Context context = (Context) this.contextProvider.get();
                return new ThemeOverlayApplier((OverlayManager) this.uiEventLoggerProvider.get(), (Executor) this.configurationControllerProvider.get(), (Executor) this.windowManagerProvider.get(), context.getString(C1777R.string.launcher_overlayable_package), context.getString(C1777R.string.themepicker_overlayable_package), (DumpManager) this.navigationModeControllerProvider.get());
        }
    }
}
