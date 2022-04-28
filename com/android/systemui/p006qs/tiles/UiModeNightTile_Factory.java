package com.android.systemui.p006qs.tiles;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.WindowManagerShellWrapper;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.TaskStackListenerImpl;
import com.android.p012wm.shell.pip.PipMediaController;
import com.android.p012wm.shell.pip.PipTaskOrganizer;
import com.android.p012wm.shell.pip.PipTransitionController;
import com.android.p012wm.shell.pip.p013tv.TvPipBoundsAlgorithm;
import com.android.p012wm.shell.pip.p013tv.TvPipBoundsState;
import com.android.p012wm.shell.pip.p013tv.TvPipController;
import com.android.p012wm.shell.pip.p013tv.TvPipMenuController;
import com.android.p012wm.shell.pip.p013tv.TvPipNotificationController;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.LocationController;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.tiles.UiModeNightTile_Factory */
public final class UiModeNightTile_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider activityStarterProvider;
    public final Provider backgroundLooperProvider;
    public final Provider batteryControllerProvider;
    public final Provider configurationControllerProvider;
    public final Provider falsingManagerProvider;
    public final Provider hostProvider;
    public final Provider locationControllerProvider;
    public final Provider mainHandlerProvider;
    public final Provider metricsLoggerProvider;
    public final Provider qsLoggerProvider;
    public final Provider statusBarStateControllerProvider;

    public /* synthetic */ UiModeNightTile_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, int i) {
        this.$r8$classId = i;
        this.hostProvider = provider;
        this.backgroundLooperProvider = provider2;
        this.mainHandlerProvider = provider3;
        this.falsingManagerProvider = provider4;
        this.metricsLoggerProvider = provider5;
        this.statusBarStateControllerProvider = provider6;
        this.activityStarterProvider = provider7;
        this.qsLoggerProvider = provider8;
        this.configurationControllerProvider = provider9;
        this.batteryControllerProvider = provider10;
        this.locationControllerProvider = provider11;
    }

    public static UiModeNightTile_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11) {
        return new UiModeNightTile_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, 0);
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new UiModeNightTile((QSHost) this.hostProvider.get(), (Looper) this.backgroundLooperProvider.get(), (Handler) this.mainHandlerProvider.get(), (FalsingManager) this.falsingManagerProvider.get(), (MetricsLogger) this.metricsLoggerProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (QSLogger) this.qsLoggerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (BatteryController) this.batteryControllerProvider.get(), (LocationController) this.locationControllerProvider.get());
            default:
                Optional of = Optional.of(new TvPipController((Context) this.hostProvider.get(), (TvPipBoundsState) this.backgroundLooperProvider.get(), (TvPipBoundsAlgorithm) this.mainHandlerProvider.get(), (PipTaskOrganizer) this.falsingManagerProvider.get(), (PipTransitionController) this.activityStarterProvider.get(), (TvPipMenuController) this.metricsLoggerProvider.get(), (PipMediaController) this.statusBarStateControllerProvider.get(), (TvPipNotificationController) this.qsLoggerProvider.get(), (TaskStackListenerImpl) this.configurationControllerProvider.get(), (WindowManagerShellWrapper) this.batteryControllerProvider.get(), (ShellExecutor) this.locationControllerProvider.get()).mImpl);
                Objects.requireNonNull(of, "Cannot return null from a non-@Nullable @Provides method");
                return of;
        }
    }
}
