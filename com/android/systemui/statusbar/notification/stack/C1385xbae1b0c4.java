package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.phone.KeyguardBypassController;
import java.util.Objects;

/* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda5 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1385xbae1b0c4 implements KeyguardBypassController.OnBypassStateChangedListener {
    public final /* synthetic */ NotificationStackScrollLayoutController f$0;

    public /* synthetic */ C1385xbae1b0c4(NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
        this.f$0 = notificationStackScrollLayoutController;
    }

    public final void onBypassStateChanged(boolean z) {
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.f$0;
        Objects.requireNonNull(notificationStackScrollLayoutController);
        NotificationRoundnessManager notificationRoundnessManager = notificationStackScrollLayoutController.mNotificationRoundnessManager;
        Objects.requireNonNull(notificationRoundnessManager);
        notificationRoundnessManager.mRoundForPulsingViews = !z;
    }
}
