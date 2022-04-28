package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManager;
import kotlin.Function;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ConversationNotifications.kt */
public final /* synthetic */ class AnimatedImageNotificationManager$bind$3 implements BindEventManager.Listener, FunctionAdapter {
    public final /* synthetic */ AnimatedImageNotificationManager $tmp0;

    public AnimatedImageNotificationManager$bind$3(AnimatedImageNotificationManager animatedImageNotificationManager) {
        this.$tmp0 = animatedImageNotificationManager;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof BindEventManager.Listener) || !(obj instanceof FunctionAdapter)) {
            return false;
        }
        return Intrinsics.areEqual(getFunctionDelegate(), ((FunctionAdapter) obj).getFunctionDelegate());
    }

    public final Function<?> getFunctionDelegate() {
        return new AdaptedFunctionReference(this.$tmp0);
    }

    public final int hashCode() {
        return getFunctionDelegate().hashCode();
    }

    public final void onViewBound(NotificationEntry notificationEntry) {
        this.$tmp0.updateAnimatedImageDrawables(notificationEntry);
    }
}
