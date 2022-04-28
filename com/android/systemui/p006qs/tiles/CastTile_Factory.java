package com.android.systemui.p006qs.tiles;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.MediaCarouselController;
import com.android.systemui.media.MediaControlPanel_Factory;
import com.android.systemui.media.MediaDataManager;
import com.android.systemui.media.MediaFlags;
import com.android.systemui.media.MediaHostStatesManager;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import com.android.systemui.statusbar.policy.CastController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.HotspotController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.tiles.CastTile_Factory */
public final class CastTile_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider activityStarterProvider;
    public final Provider backgroundLooperProvider;
    public final Provider castControllerProvider;
    public final Provider dialogLaunchAnimatorProvider;
    public final Provider falsingManagerProvider;
    public final Provider hostProvider;
    public final Provider hotspotControllerProvider;
    public final Provider keyguardStateControllerProvider;
    public final Provider mainHandlerProvider;
    public final Provider metricsLoggerProvider;
    public final Provider networkControllerProvider;
    public final Provider qsLoggerProvider;
    public final Provider statusBarStateControllerProvider;

    public /* synthetic */ CastTile_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, int i) {
        this.$r8$classId = i;
        this.hostProvider = provider;
        this.backgroundLooperProvider = provider2;
        this.mainHandlerProvider = provider3;
        this.falsingManagerProvider = provider4;
        this.metricsLoggerProvider = provider5;
        this.statusBarStateControllerProvider = provider6;
        this.activityStarterProvider = provider7;
        this.qsLoggerProvider = provider8;
        this.castControllerProvider = provider9;
        this.keyguardStateControllerProvider = provider10;
        this.networkControllerProvider = provider11;
        this.hotspotControllerProvider = provider12;
        this.dialogLaunchAnimatorProvider = provider13;
    }

    public static CastTile_Factory create(Provider provider, MediaControlPanel_Factory mediaControlPanel_Factory, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12) {
        return new CastTile_Factory(provider, mediaControlPanel_Factory, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, 1);
    }

    public static CastTile_Factory create$1(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13) {
        return new CastTile_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, 0);
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new CastTile((QSHost) this.hostProvider.get(), (Looper) this.backgroundLooperProvider.get(), (Handler) this.mainHandlerProvider.get(), (FalsingManager) this.falsingManagerProvider.get(), (MetricsLogger) this.metricsLoggerProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (QSLogger) this.qsLoggerProvider.get(), (CastController) this.castControllerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (NetworkController) this.networkControllerProvider.get(), (HotspotController) this.hotspotControllerProvider.get(), (DialogLaunchAnimator) this.dialogLaunchAnimatorProvider.get());
            default:
                return new MediaCarouselController((Context) this.hostProvider.get(), this.backgroundLooperProvider, (VisualStabilityProvider) this.mainHandlerProvider.get(), (MediaHostStatesManager) this.falsingManagerProvider.get(), (ActivityStarter) this.metricsLoggerProvider.get(), (SystemClock) this.statusBarStateControllerProvider.get(), (DelayableExecutor) this.activityStarterProvider.get(), (MediaDataManager) this.qsLoggerProvider.get(), (ConfigurationController) this.castControllerProvider.get(), (FalsingCollector) this.keyguardStateControllerProvider.get(), (FalsingManager) this.networkControllerProvider.get(), (DumpManager) this.hotspotControllerProvider.get(), (MediaFlags) this.dialogLaunchAnimatorProvider.get());
        }
    }
}
