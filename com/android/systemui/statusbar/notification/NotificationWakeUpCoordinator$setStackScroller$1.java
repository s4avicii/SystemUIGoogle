package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: NotificationWakeUpCoordinator.kt */
public final class NotificationWakeUpCoordinator$setStackScroller$1 implements Runnable {
    public final /* synthetic */ NotificationWakeUpCoordinator this$0;

    public NotificationWakeUpCoordinator$setStackScroller$1(NotificationWakeUpCoordinator notificationWakeUpCoordinator) {
        this.this$0 = notificationWakeUpCoordinator;
    }

    public final void run() {
        boolean z;
        NotificationWakeUpCoordinator notificationWakeUpCoordinator = this.this$0;
        Objects.requireNonNull(notificationWakeUpCoordinator);
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationWakeUpCoordinator.mStackScrollerController;
        if (notificationStackScrollLayoutController == null) {
            notificationStackScrollLayoutController = null;
        }
        Objects.requireNonNull(notificationStackScrollLayoutController);
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        Objects.requireNonNull(notificationStackScrollLayout);
        boolean isPulseExpanding = notificationStackScrollLayout.mAmbientState.isPulseExpanding();
        NotificationWakeUpCoordinator notificationWakeUpCoordinator2 = this.this$0;
        if (isPulseExpanding != notificationWakeUpCoordinator2.pulseExpanding) {
            z = true;
        } else {
            z = false;
        }
        notificationWakeUpCoordinator2.pulseExpanding = isPulseExpanding;
        Iterator<NotificationWakeUpCoordinator.WakeUpListener> it = notificationWakeUpCoordinator2.wakeUpListeners.iterator();
        while (it.hasNext()) {
            it.next().onPulseExpansionChanged(z);
        }
    }
}
