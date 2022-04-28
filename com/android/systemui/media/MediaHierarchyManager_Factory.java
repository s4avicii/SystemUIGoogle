package com.android.systemui.media;

import android.content.Context;
import com.android.keyguard.KeyguardViewController;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.init.NotificationsController;
import com.android.systemui.statusbar.phone.DozeScrimController;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.NotificationPanelViewController;
import com.android.systemui.statusbar.phone.StatusBarHeadsUpChangeListener;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class MediaHierarchyManager_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider bypassControllerProvider;
    public final Provider configurationControllerProvider;
    public final Provider contextProvider;
    public final Provider dreamOverlayStateControllerProvider;
    public final Provider keyguardStateControllerProvider;
    public final Provider keyguardViewControllerProvider;
    public final Provider mediaCarouselControllerProvider;
    public final Provider notifLockscreenUserManagerProvider;
    public final Provider statusBarStateControllerProvider;
    public final Provider wakefulnessLifecycleProvider;

    public /* synthetic */ MediaHierarchyManager_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.statusBarStateControllerProvider = provider2;
        this.keyguardStateControllerProvider = provider3;
        this.bypassControllerProvider = provider4;
        this.mediaCarouselControllerProvider = provider5;
        this.notifLockscreenUserManagerProvider = provider6;
        this.configurationControllerProvider = provider7;
        this.wakefulnessLifecycleProvider = provider8;
        this.keyguardViewControllerProvider = provider9;
        this.dreamOverlayStateControllerProvider = provider10;
    }

    public static MediaHierarchyManager_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10) {
        return new MediaHierarchyManager_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, 0);
    }

    public static MediaHierarchyManager_Factory create$1(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10) {
        return new MediaHierarchyManager_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, 1);
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new MediaHierarchyManager((Context) this.contextProvider.get(), (SysuiStatusBarStateController) this.statusBarStateControllerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (KeyguardBypassController) this.bypassControllerProvider.get(), (MediaCarouselController) this.mediaCarouselControllerProvider.get(), (NotificationLockscreenUserManager) this.notifLockscreenUserManagerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (WakefulnessLifecycle) this.wakefulnessLifecycleProvider.get(), (KeyguardViewController) this.keyguardViewControllerProvider.get(), (DreamOverlayStateController) this.dreamOverlayStateControllerProvider.get());
            default:
                return new StatusBarHeadsUpChangeListener((NotificationShadeWindowController) this.contextProvider.get(), (StatusBarWindowController) this.statusBarStateControllerProvider.get(), (NotificationPanelViewController) this.keyguardStateControllerProvider.get(), (KeyguardBypassController) this.bypassControllerProvider.get(), (HeadsUpManagerPhone) this.mediaCarouselControllerProvider.get(), (StatusBarStateController) this.notifLockscreenUserManagerProvider.get(), (NotificationRemoteInputManager) this.configurationControllerProvider.get(), (NotificationsController) this.wakefulnessLifecycleProvider.get(), (DozeServiceHost) this.keyguardViewControllerProvider.get(), (DozeScrimController) this.dreamOverlayStateControllerProvider.get());
        }
    }
}
