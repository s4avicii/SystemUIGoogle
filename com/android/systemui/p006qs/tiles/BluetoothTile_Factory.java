package com.android.systemui.p006qs.tiles;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Handler;
import android.os.IThermalService;
import android.os.Looper;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.BootCompleteCache;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.policy.BluetoothController;
import com.google.android.systemui.reversecharging.ReverseChargingController;
import dagger.internal.Factory;
import java.util.Optional;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.tiles.BluetoothTile_Factory */
public final class BluetoothTile_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider activityStarterProvider;
    public final Provider backgroundLooperProvider;
    public final Provider bluetoothControllerProvider;
    public final Provider falsingManagerProvider;
    public final Provider hostProvider;
    public final Provider mainHandlerProvider;
    public final Provider metricsLoggerProvider;
    public final Provider qsLoggerProvider;
    public final Provider statusBarStateControllerProvider;

    public /* synthetic */ BluetoothTile_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, int i) {
        this.$r8$classId = i;
        this.hostProvider = provider;
        this.backgroundLooperProvider = provider2;
        this.mainHandlerProvider = provider3;
        this.falsingManagerProvider = provider4;
        this.metricsLoggerProvider = provider5;
        this.statusBarStateControllerProvider = provider6;
        this.activityStarterProvider = provider7;
        this.qsLoggerProvider = provider8;
        this.bluetoothControllerProvider = provider9;
    }

    public static BluetoothTile_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        return new BluetoothTile_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, 0);
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new BluetoothTile((QSHost) this.hostProvider.get(), (Looper) this.backgroundLooperProvider.get(), (Handler) this.mainHandlerProvider.get(), (FalsingManager) this.falsingManagerProvider.get(), (MetricsLogger) this.metricsLoggerProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (QSLogger) this.qsLoggerProvider.get(), (BluetoothController) this.bluetoothControllerProvider.get());
            default:
                return new ReverseChargingController((Context) this.hostProvider.get(), (BroadcastDispatcher) this.backgroundLooperProvider.get(), (Optional) this.mainHandlerProvider.get(), (AlarmManager) this.falsingManagerProvider.get(), (Optional) this.metricsLoggerProvider.get(), (Executor) this.statusBarStateControllerProvider.get(), (Executor) this.activityStarterProvider.get(), (BootCompleteCache) this.qsLoggerProvider.get(), (IThermalService) this.bluetoothControllerProvider.get());
        }
    }
}
