package com.android.systemui.statusbar.notification;

import android.util.FloatProperty;

/* compiled from: NotificationWakeUpCoordinator.kt */
public final class NotificationWakeUpCoordinator$mNotificationVisibility$1 extends FloatProperty<NotificationWakeUpCoordinator> {
    public NotificationWakeUpCoordinator$mNotificationVisibility$1() {
        super("notificationVisibility");
    }

    public final Object get(Object obj) {
        return Float.valueOf(((NotificationWakeUpCoordinator) obj).mLinearVisibilityAmount);
    }

    public final void setValue(Object obj, float f) {
        ((NotificationWakeUpCoordinator) obj).setVisibilityAmount(f);
    }
}
