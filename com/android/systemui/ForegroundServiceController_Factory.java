package com.android.systemui;

import android.content.Context;
import android.os.Handler;
import android.provider.Settings;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.p007tv.notifications.TvNotificationPanel;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.sensors.ProximityCheck;
import com.android.systemui.util.sensors.ProximitySensor;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class ForegroundServiceController_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider appOpsControllerProvider;
    public final Provider mainHandlerProvider;

    public /* synthetic */ ForegroundServiceController_Factory(Provider provider, Provider provider2, int i) {
        this.$r8$classId = i;
        this.appOpsControllerProvider = provider;
        this.mainHandlerProvider = provider2;
    }

    public final Object get() {
        boolean z;
        switch (this.$r8$classId) {
            case 0:
                return new ForegroundServiceController((AppOpsController) this.appOpsControllerProvider.get(), (Handler) this.mainHandlerProvider.get());
            case 1:
                return new TvNotificationPanel((Context) this.appOpsControllerProvider.get(), (CommandQueue) this.mainHandlerProvider.get());
            case 2:
                return new ProximityCheck((ProximitySensor) this.appOpsControllerProvider.get(), (DelayableExecutor) this.mainHandlerProvider.get());
            default:
                if (Settings.Secure.getString(((Context) this.appOpsControllerProvider.get()).getContentResolver(), (String) this.mainHandlerProvider.get()) != null) {
                    z = true;
                } else {
                    z = false;
                }
                return Boolean.valueOf(z);
        }
    }
}
