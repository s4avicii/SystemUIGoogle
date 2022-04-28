package com.android.systemui.p006qs;

import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.dagger.FrameworkServicesModule_ProvideFaceManagerFactory;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.KeyguardMediaController;
import com.android.systemui.media.MediaFlags;
import com.android.systemui.media.MediaHost;
import com.android.systemui.p006qs.customize.QSCustomizerController;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.privacy.logging.PrivacyLogger_Factory;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationSectionsFeatureManager;
import com.android.systemui.statusbar.notification.collection.render.MediaContainerController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.stack.NotificationSectionsLogger;
import com.android.systemui.statusbar.notification.stack.NotificationSectionsManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.QuickQSPanelController_Factory */
public final class QuickQSPanelController_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider dumpManagerProvider;
    public final Provider mediaFlagsProvider;
    public final Provider mediaHostProvider;
    public final Provider metricsLoggerProvider;
    public final Provider qsCustomizerControllerProvider;
    public final Provider qsLoggerProvider;
    public final Provider qsTileHostProvider;
    public final Provider uiEventLoggerProvider;
    public final Provider usingCollapsedLandscapeMediaProvider;
    public final Provider usingMediaPlayerProvider;
    public final Provider viewProvider;

    public /* synthetic */ QuickQSPanelController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, int i) {
        this.$r8$classId = i;
        this.viewProvider = provider;
        this.qsTileHostProvider = provider2;
        this.qsCustomizerControllerProvider = provider3;
        this.usingMediaPlayerProvider = provider4;
        this.mediaHostProvider = provider5;
        this.usingCollapsedLandscapeMediaProvider = provider6;
        this.mediaFlagsProvider = provider7;
        this.metricsLoggerProvider = provider8;
        this.uiEventLoggerProvider = provider9;
        this.qsLoggerProvider = provider10;
        this.dumpManagerProvider = provider11;
    }

    public static QuickQSPanelController_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, PrivacyLogger_Factory privacyLogger_Factory, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10) {
        return new QuickQSPanelController_Factory(provider, provider2, provider3, provider4, provider5, privacyLogger_Factory, provider6, provider7, provider8, provider9, provider10, 0);
    }

    public static QuickQSPanelController_Factory create$1(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, FrameworkServicesModule_ProvideFaceManagerFactory frameworkServicesModule_ProvideFaceManagerFactory) {
        return new QuickQSPanelController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, frameworkServicesModule_ProvideFaceManagerFactory, 1);
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new QuickQSPanelController((QuickQSPanel) this.viewProvider.get(), (QSTileHost) this.qsTileHostProvider.get(), (QSCustomizerController) this.qsCustomizerControllerProvider.get(), ((Boolean) this.usingMediaPlayerProvider.get()).booleanValue(), (MediaHost) this.mediaHostProvider.get(), ((Boolean) this.usingCollapsedLandscapeMediaProvider.get()).booleanValue(), (MediaFlags) this.mediaFlagsProvider.get(), (MetricsLogger) this.metricsLoggerProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (QSLogger) this.qsLoggerProvider.get(), (DumpManager) this.dumpManagerProvider.get());
            default:
                return new NotificationSectionsManager((StatusBarStateController) this.viewProvider.get(), (ConfigurationController) this.qsTileHostProvider.get(), (KeyguardMediaController) this.qsCustomizerControllerProvider.get(), (NotificationSectionsFeatureManager) this.usingMediaPlayerProvider.get(), (NotificationSectionsLogger) this.mediaHostProvider.get(), (NotifPipelineFlags) this.usingCollapsedLandscapeMediaProvider.get(), (MediaContainerController) this.mediaFlagsProvider.get(), (SectionHeaderController) this.metricsLoggerProvider.get(), (SectionHeaderController) this.uiEventLoggerProvider.get(), (SectionHeaderController) this.qsLoggerProvider.get(), (SectionHeaderController) this.dumpManagerProvider.get());
        }
    }
}
