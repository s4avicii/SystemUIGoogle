package com.android.systemui.recents;

import android.content.Context;
import com.android.systemui.flags.Flags;
import com.android.systemui.media.taptotransfer.MediaTttCommandLineHelper;
import com.android.systemui.media.taptotransfer.MediaTttFlags;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.ForegroundServiceDismissalFeatureController;
import com.android.systemui.statusbar.notification.collection.coordinator.HideNotifsForOtherUsersCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.SharedCoordinatorLogger;
import com.android.systemui.util.DeviceConfigProxy;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

public final class ScreenPinningRequest_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider contextProvider;
    public final Provider statusBarOptionalLazyProvider;

    public /* synthetic */ ScreenPinningRequest_Factory(Provider provider, Provider provider2, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.statusBarOptionalLazyProvider = provider2;
    }

    public final Object get() {
        Optional optional;
        switch (this.$r8$classId) {
            case 0:
                return new ScreenPinningRequest((Context) this.contextProvider.get(), DoubleCheck.lazy(this.statusBarOptionalLazyProvider));
            case 1:
                MediaTttFlags mediaTttFlags = (MediaTttFlags) this.contextProvider.get();
                Lazy lazy = DoubleCheck.lazy(this.statusBarOptionalLazyProvider);
                Objects.requireNonNull(mediaTttFlags);
                if (!mediaTttFlags.featureFlags.isEnabled(Flags.MEDIA_TAP_TO_TRANSFER)) {
                    optional = Optional.empty();
                } else {
                    optional = Optional.of((MediaTttCommandLineHelper) lazy.get());
                }
                Objects.requireNonNull(optional, "Cannot return null from a non-@Nullable @Provides method");
                return optional;
            case 2:
                Context context = (Context) this.statusBarOptionalLazyProvider.get();
                return new ForegroundServiceDismissalFeatureController((DeviceConfigProxy) this.contextProvider.get());
            default:
                return new HideNotifsForOtherUsersCoordinator((NotificationLockscreenUserManager) this.contextProvider.get(), (SharedCoordinatorLogger) this.statusBarOptionalLazyProvider.get());
        }
    }
}
