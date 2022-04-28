package com.android.systemui.statusbar.notification;

import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Collection;

/* compiled from: ConversationNotifications.kt */
public final class AnimatedImageNotificationManager$bind$2 implements StatusBarStateController.StateListener {
    public final /* synthetic */ AnimatedImageNotificationManager this$0;

    public AnimatedImageNotificationManager$bind$2(AnimatedImageNotificationManager animatedImageNotificationManager) {
        this.this$0 = animatedImageNotificationManager;
    }

    public final void onExpandedChanged(boolean z) {
        AnimatedImageNotificationManager animatedImageNotificationManager = this.this$0;
        animatedImageNotificationManager.isStatusBarExpanded = z;
        Collection<NotificationEntry> allNotifs = animatedImageNotificationManager.notifCollection.getAllNotifs();
        AnimatedImageNotificationManager animatedImageNotificationManager2 = this.this$0;
        for (NotificationEntry updateAnimatedImageDrawables : allNotifs) {
            animatedImageNotificationManager2.updateAnimatedImageDrawables(updateAnimatedImageDrawables);
        }
    }
}
