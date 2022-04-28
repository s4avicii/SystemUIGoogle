package com.android.systemui.media;

import android.content.Context;
import android.media.session.MediaSessionManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.statusbar.phone.NotificationsQSContainerController;
import com.android.systemui.statusbar.phone.NotificationsQuickSettingsContainer;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class MediaSessionBasedFilter_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider backgroundExecutorProvider;
    public final Provider contextProvider;
    public final Provider foregroundExecutorProvider;
    public final Provider sessionManagerProvider;

    public /* synthetic */ MediaSessionBasedFilter_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.sessionManagerProvider = provider2;
        this.foregroundExecutorProvider = provider3;
        this.backgroundExecutorProvider = provider4;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new MediaSessionBasedFilter((Context) this.contextProvider.get(), (MediaSessionManager) this.sessionManagerProvider.get(), (Executor) this.foregroundExecutorProvider.get(), (Executor) this.backgroundExecutorProvider.get());
            default:
                return new NotificationsQSContainerController((NotificationsQuickSettingsContainer) this.contextProvider.get(), (NavigationModeController) this.sessionManagerProvider.get(), (OverviewProxyService) this.foregroundExecutorProvider.get(), (FeatureFlags) this.backgroundExecutorProvider.get());
        }
    }
}
