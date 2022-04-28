package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;

/* compiled from: ConversationNotifications.kt */
public final class AnimatedImageNotificationManager$bind$1 implements OnHeadsUpChangedListener {
    public final /* synthetic */ AnimatedImageNotificationManager this$0;

    public AnimatedImageNotificationManager$bind$1(AnimatedImageNotificationManager animatedImageNotificationManager) {
        this.this$0 = animatedImageNotificationManager;
    }

    public final void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z) {
        this.this$0.updateAnimatedImageDrawables(notificationEntry);
    }
}
