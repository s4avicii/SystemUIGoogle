package com.android.systemui.p006qs.tiles;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.SensorPrivacyManager;
import android.os.Handler;
import android.os.Looper;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.controls.ControlsMetricsLogger;
import com.android.systemui.controls.CustomIconCache;
import com.android.systemui.controls.p004ui.ControlActionCoordinator;
import com.android.systemui.controls.p004ui.ControlsUiControllerImpl;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.ShadeController;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.tiles.RotationLockTile_Factory */
public final class RotationLockTile_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider activityStarterProvider;
    public final Provider backgroundLooperProvider;
    public final Provider batteryControllerProvider;
    public final Provider falsingManagerProvider;
    public final Provider hostProvider;
    public final Provider mainHandlerProvider;
    public final Provider metricsLoggerProvider;
    public final Provider privacyManagerProvider;
    public final Provider qsLoggerProvider;
    public final Provider rotationLockControllerProvider;
    public final Provider secureSettingsProvider;
    public final Provider statusBarStateControllerProvider;

    public /* synthetic */ RotationLockTile_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, int i) {
        this.$r8$classId = i;
        this.hostProvider = provider;
        this.backgroundLooperProvider = provider2;
        this.mainHandlerProvider = provider3;
        this.falsingManagerProvider = provider4;
        this.metricsLoggerProvider = provider5;
        this.statusBarStateControllerProvider = provider6;
        this.activityStarterProvider = provider7;
        this.qsLoggerProvider = provider8;
        this.rotationLockControllerProvider = provider9;
        this.privacyManagerProvider = provider10;
        this.batteryControllerProvider = provider11;
        this.secureSettingsProvider = provider12;
    }

    public static RotationLockTile_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12) {
        return new RotationLockTile_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, 1);
    }

    public static RotationLockTile_Factory create$1(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12) {
        return new RotationLockTile_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, 0);
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new RotationLockTile((QSHost) this.hostProvider.get(), (Looper) this.backgroundLooperProvider.get(), (Handler) this.mainHandlerProvider.get(), (FalsingManager) this.falsingManagerProvider.get(), (MetricsLogger) this.metricsLoggerProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (QSLogger) this.qsLoggerProvider.get(), (RotationLockController) this.rotationLockControllerProvider.get(), (SensorPrivacyManager) this.privacyManagerProvider.get(), (BatteryController) this.batteryControllerProvider.get(), (SecureSettings) this.secureSettingsProvider.get());
            default:
                return new ControlsUiControllerImpl(DoubleCheck.lazy(this.hostProvider), (Context) this.backgroundLooperProvider.get(), (DelayableExecutor) this.mainHandlerProvider.get(), (DelayableExecutor) this.falsingManagerProvider.get(), DoubleCheck.lazy(this.metricsLoggerProvider), (SharedPreferences) this.statusBarStateControllerProvider.get(), (ControlActionCoordinator) this.activityStarterProvider.get(), (ActivityStarter) this.qsLoggerProvider.get(), (ShadeController) this.rotationLockControllerProvider.get(), (CustomIconCache) this.privacyManagerProvider.get(), (ControlsMetricsLogger) this.batteryControllerProvider.get(), (KeyguardStateController) this.secureSettingsProvider.get());
        }
    }
}
