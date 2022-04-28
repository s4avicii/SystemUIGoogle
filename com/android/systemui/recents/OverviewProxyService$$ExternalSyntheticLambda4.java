package com.android.systemui.recents;

import android.net.Uri;
import android.provider.Settings;
import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreen;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.NotificationPanelViewController;
import com.google.android.systemui.elmyra.gates.KeyguardDeferredSetup;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OverviewProxyService$$ExternalSyntheticLambda4 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ OverviewProxyService$$ExternalSyntheticLambda4(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void accept(Object obj) {
        boolean z = true;
        switch (this.$r8$classId) {
            case 0:
                OverviewProxyService overviewProxyService = (OverviewProxyService) this.f$0;
                Objects.requireNonNull(overviewProxyService);
                ((LegacySplitScreen) obj).registerBoundsChangeListener(overviewProxyService.mSplitScreenBoundsChangeListener);
                return;
            case 1:
                NotificationPanelViewController.C147914 r6 = (NotificationPanelViewController.C147914) this.f$0;
                Boolean bool = (Boolean) obj;
                Objects.requireNonNull(r6);
                if (NotificationPanelViewController.this.mQs.getHeader().isShown()) {
                    NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                    Objects.requireNonNull(notificationPanelViewController);
                    notificationPanelViewController.mAnimateNextNotificationBounds = true;
                    notificationPanelViewController.mNotificationBoundsAnimationDuration = 360;
                    notificationPanelViewController.mNotificationBoundsAnimationDelay = 0;
                    NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationPanelViewController.this.mNotificationStackScrollLayoutController;
                    Objects.requireNonNull(notificationStackScrollLayoutController);
                    NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
                    Objects.requireNonNull(notificationStackScrollLayout);
                    notificationStackScrollLayout.mAnimateNextTopPaddingChange = true;
                    return;
                }
                return;
            default:
                KeyguardDeferredSetup keyguardDeferredSetup = (KeyguardDeferredSetup) this.f$0;
                Uri uri = (Uri) obj;
                Objects.requireNonNull(keyguardDeferredSetup);
                if (Settings.Secure.getIntForUser(keyguardDeferredSetup.mContext.getContentResolver(), "assist_gesture_setup_complete", 0, -2) == 0) {
                    z = false;
                }
                if (keyguardDeferredSetup.mDeferredSetupComplete != z) {
                    keyguardDeferredSetup.mDeferredSetupComplete = z;
                    keyguardDeferredSetup.notifyListener();
                    return;
                }
                return;
        }
    }
}
