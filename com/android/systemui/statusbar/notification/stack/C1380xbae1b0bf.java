package com.android.systemui.statusbar.notification.stack;

import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda6;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import java.util.Objects;

/* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1380xbae1b0bf implements DynamicPrivacyController.Listener {
    public final /* synthetic */ NotificationStackScrollLayoutController f$0;

    public /* synthetic */ C1380xbae1b0bf(NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
        this.f$0 = notificationStackScrollLayoutController;
    }

    public final void onDynamicPrivacyChanged() {
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.f$0;
        Objects.requireNonNull(notificationStackScrollLayoutController);
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        Objects.requireNonNull(notificationStackScrollLayout);
        if (notificationStackScrollLayout.mIsExpanded) {
            NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
            Objects.requireNonNull(notificationStackScrollLayout2);
            notificationStackScrollLayout2.mAnimateBottomOnLayout = true;
        }
        notificationStackScrollLayoutController.mView.post(new TaskView$$ExternalSyntheticLambda6(notificationStackScrollLayoutController, 2));
    }
}
