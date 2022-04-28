package com.android.systemui.statusbar.dagger;

import android.content.Context;
import android.os.Handler;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.p012wm.shell.bubbles.Bubbles;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.p006qs.dagger.QSFragmentModule_ProvidesQuickQSPanelFactory;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationViewHierarchyManager;
import com.android.systemui.statusbar.notification.AssistantFeedbackController;
import com.android.systemui.statusbar.notification.DynamicChildBindController;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.collection.legacy.LowPriorityInflationHelper;
import com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy;
import com.android.systemui.statusbar.notification.collection.legacy.VisualStabilityManager;
import com.android.systemui.statusbar.notification.stack.ForegroundServiceSectionController;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

/* renamed from: com.android.systemui.statusbar.dagger.StatusBarDependenciesModule_ProvideNotificationViewHierarchyManagerFactory */
public final class C1210x3f8faa0a implements Factory<NotificationViewHierarchyManager> {
    public final Provider<AssistantFeedbackController> assistantFeedbackControllerProvider;
    public final Provider<Optional<Bubbles>> bubblesOptionalProvider;
    public final Provider<KeyguardBypassController> bypassControllerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DynamicChildBindController> dynamicChildBindControllerProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<ForegroundServiceSectionController> fgsSectionControllerProvider;
    public final Provider<NotificationGroupManagerLegacy> groupManagerProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
    public final Provider<LowPriorityInflationHelper> lowPriorityInflationHelperProvider;
    public final Provider<Handler> mainHandlerProvider;
    public final Provider<NotifPipelineFlags> notifPipelineFlagsProvider;
    public final Provider<NotificationEntryManager> notificationEntryManagerProvider;
    public final Provider<NotificationLockscreenUserManager> notificationLockscreenUserManagerProvider;
    public final Provider<DynamicPrivacyController> privacyControllerProvider;
    public final Provider<StatusBarStateController> statusBarStateControllerProvider;
    public final Provider<VisualStabilityManager> visualStabilityManagerProvider;

    public C1210x3f8faa0a(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, QSFragmentModule_ProvidesQuickQSPanelFactory qSFragmentModule_ProvidesQuickQSPanelFactory, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17) {
        this.contextProvider = provider;
        this.mainHandlerProvider = provider2;
        this.featureFlagsProvider = provider3;
        this.notificationLockscreenUserManagerProvider = provider4;
        this.groupManagerProvider = provider5;
        this.visualStabilityManagerProvider = provider6;
        this.statusBarStateControllerProvider = provider7;
        this.notificationEntryManagerProvider = provider8;
        this.bypassControllerProvider = provider9;
        this.bubblesOptionalProvider = provider10;
        this.privacyControllerProvider = provider11;
        this.fgsSectionControllerProvider = provider12;
        this.dynamicChildBindControllerProvider = qSFragmentModule_ProvidesQuickQSPanelFactory;
        this.lowPriorityInflationHelperProvider = provider13;
        this.assistantFeedbackControllerProvider = provider14;
        this.notifPipelineFlagsProvider = provider15;
        this.keyguardUpdateMonitorProvider = provider16;
        this.keyguardStateControllerProvider = provider17;
    }

    public static C1210x3f8faa0a create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, QSFragmentModule_ProvidesQuickQSPanelFactory qSFragmentModule_ProvidesQuickQSPanelFactory, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17) {
        return new C1210x3f8faa0a(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, qSFragmentModule_ProvidesQuickQSPanelFactory, provider13, provider14, provider15, provider16, provider17);
    }

    public final Object get() {
        KeyguardBypassController keyguardBypassController = this.bypassControllerProvider.get();
        return new NotificationViewHierarchyManager(this.contextProvider.get(), this.mainHandlerProvider.get(), this.featureFlagsProvider.get(), this.notificationLockscreenUserManagerProvider.get(), this.groupManagerProvider.get(), this.visualStabilityManagerProvider.get(), this.statusBarStateControllerProvider.get(), this.notificationEntryManagerProvider.get(), this.bubblesOptionalProvider.get(), this.privacyControllerProvider.get(), this.fgsSectionControllerProvider.get(), this.dynamicChildBindControllerProvider.get(), this.lowPriorityInflationHelperProvider.get(), this.assistantFeedbackControllerProvider.get(), this.notifPipelineFlagsProvider.get(), this.keyguardUpdateMonitorProvider.get(), this.keyguardStateControllerProvider.get());
    }
}
