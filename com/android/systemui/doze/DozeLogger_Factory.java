package com.android.systemui.doze;

import android.content.Context;
import android.media.MediaRouter2Manager;
import android.service.quickaccesswallet.QuickAccessWalletClient;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.dagger.WMShellBaseModule$$ExternalSyntheticLambda5;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.WorkLockActivity;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.HeadsUpStatusBarView;
import com.android.systemui.statusbar.notification.collection.coordinator.StackCoordinator;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.phone.PhoneStatusBarView;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

public final class DozeLogger_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider bufferProvider;

    public /* synthetic */ DozeLogger_Factory(Provider provider, int i) {
        this.$r8$classId = i;
        this.bufferProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new DozeLogger((LogBuffer) this.bufferProvider.get());
            case 1:
                MediaRouter2Manager instance = MediaRouter2Manager.getInstance((Context) this.bufferProvider.get());
                Objects.requireNonNull(instance, "Cannot return null from a non-@Nullable @Provides method");
                return instance;
            case 2:
                return new WorkLockActivity((BroadcastDispatcher) this.bufferProvider.get());
            case 3:
                return new StackCoordinator((NotificationIconAreaController) this.bufferProvider.get());
            case 4:
                NotificationGutsManager notificationGutsManager = (NotificationGutsManager) this.bufferProvider.get();
                Objects.requireNonNull(notificationGutsManager, "Cannot return null from a non-@Nullable @Provides method");
                return notificationGutsManager;
            case 5:
                HeadsUpStatusBarView headsUpStatusBarView = (HeadsUpStatusBarView) ((PhoneStatusBarView) this.bufferProvider.get()).findViewById(C1777R.C1779id.heads_up_status_bar_view);
                Objects.requireNonNull(headsUpStatusBarView, "Cannot return null from a non-@Nullable @Provides method");
                return headsUpStatusBarView;
            case FalsingManager.VERSION /*6*/:
                QuickAccessWalletClient create = QuickAccessWalletClient.create((Context) this.bufferProvider.get());
                Objects.requireNonNull(create, "Cannot return null from a non-@Nullable @Provides method");
                return create;
            case 7:
                Optional map = ((Optional) this.bufferProvider.get()).map(WMShellBaseModule$$ExternalSyntheticLambda5.INSTANCE);
                Objects.requireNonNull(map, "Cannot return null from a non-@Nullable @Provides method");
                return map;
            default:
                return Integer.valueOf(((UserTracker) this.bufferProvider.get()).getUserId());
        }
    }
}
