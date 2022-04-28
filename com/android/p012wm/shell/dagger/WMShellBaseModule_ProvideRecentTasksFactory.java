package com.android.p012wm.shell.dagger;

import android.content.Context;
import android.content.om.OverlayManager;
import android.os.Handler;
import android.os.Looper;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.shared.plugins.PluginManager;
import com.android.systemui.statusbar.notification.NotificationEntryManagerLogger;
import com.android.systemui.statusbar.phone.NotificationListenerWithPlugins;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.WMShellBaseModule_ProvideRecentTasksFactory */
public final class WMShellBaseModule_ProvideRecentTasksFactory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider recentTasksControllerProvider;

    public /* synthetic */ WMShellBaseModule_ProvideRecentTasksFactory(Provider provider, int i) {
        this.$r8$classId = i;
        this.recentTasksControllerProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                Optional map = ((Optional) this.recentTasksControllerProvider.get()).map(WMShellBaseModule$$ExternalSyntheticLambda1.INSTANCE);
                Objects.requireNonNull(map, "Cannot return null from a non-@Nullable @Provides method");
                return map;
            case 1:
                OverlayManager overlayManager = (OverlayManager) ((Context) this.recentTasksControllerProvider.get()).getSystemService(OverlayManager.class);
                Objects.requireNonNull(overlayManager, "Cannot return null from a non-@Nullable @Provides method");
                return overlayManager;
            case 2:
                return new NotificationEntryManagerLogger((LogBuffer) this.recentTasksControllerProvider.get());
            case 3:
                return new NotificationListenerWithPlugins((PluginManager) this.recentTasksControllerProvider.get());
            case 4:
                return new Handler((Looper) this.recentTasksControllerProvider.get());
            default:
                DeviceProvisionedControllerImpl deviceProvisionedControllerImpl = (DeviceProvisionedControllerImpl) this.recentTasksControllerProvider.get();
                deviceProvisionedControllerImpl.init();
                return deviceProvisionedControllerImpl;
        }
    }
}
