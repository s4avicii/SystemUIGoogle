package com.android.systemui.media;

import android.content.Context;
import android.os.Handler;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class MediaDataFilter_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider broadcastDispatcherProvider;
    public final Provider contextProvider;
    public final Provider executorProvider;
    public final Provider lockscreenUserManagerProvider;
    public final Provider systemClockProvider;

    public /* synthetic */ MediaDataFilter_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.broadcastDispatcherProvider = provider2;
        this.lockscreenUserManagerProvider = provider3;
        this.executorProvider = provider4;
        this.systemClockProvider = provider5;
    }

    public static MediaDataFilter_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new MediaDataFilter_Factory(provider, provider2, provider3, provider4, provider5, 0);
    }

    public static MediaDataFilter_Factory create$1(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new MediaDataFilter_Factory(provider, provider2, provider3, provider4, provider5, 1);
    }

    public static MediaDataFilter_Factory create$2(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new MediaDataFilter_Factory(provider, provider2, provider3, provider4, provider5, 2);
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new MediaDataFilter((Context) this.contextProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get(), (Executor) this.executorProvider.get(), (SystemClock) this.systemClockProvider.get());
            case 1:
                return new SensitiveContentCoordinatorImpl((DynamicPrivacyController) this.contextProvider.get(), (NotificationLockscreenUserManager) this.broadcastDispatcherProvider.get(), (KeyguardUpdateMonitor) this.lockscreenUserManagerProvider.get(), (StatusBarStateController) this.executorProvider.get(), (KeyguardStateController) this.systemClockProvider.get());
            default:
                return new ZenModeControllerImpl((Context) this.contextProvider.get(), (Handler) this.broadcastDispatcherProvider.get(), (BroadcastDispatcher) this.lockscreenUserManagerProvider.get(), (DumpManager) this.executorProvider.get(), (GlobalSettings) this.systemClockProvider.get());
        }
    }
}
