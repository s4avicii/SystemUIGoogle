package com.android.systemui.media;

import android.content.Context;
import android.os.Handler;
import com.android.systemui.ForegroundServiceController;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.statusbar.notification.collection.coordinator.AppOpsCoordinator;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.google.android.systemui.columbus.gates.PowerState;
import com.google.android.systemui.columbus.gates.ScreenTouch;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class MediaViewController_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider configurationControllerProvider;
    public final Provider contextProvider;
    public final Provider mediaHostStatesManagerProvider;

    public /* synthetic */ MediaViewController_Factory(Provider provider, Provider provider2, Provider provider3, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.configurationControllerProvider = provider2;
        this.mediaHostStatesManagerProvider = provider3;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new MediaViewController((Context) this.contextProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (MediaHostStatesManager) this.mediaHostStatesManagerProvider.get());
            case 1:
                return new AppOpsCoordinator((ForegroundServiceController) this.contextProvider.get(), (AppOpsController) this.configurationControllerProvider.get(), (DelayableExecutor) this.mediaHostStatesManagerProvider.get());
            default:
                return new ScreenTouch((Context) this.contextProvider.get(), (PowerState) this.configurationControllerProvider.get(), (Handler) this.mediaHostStatesManagerProvider.get());
        }
    }
}
