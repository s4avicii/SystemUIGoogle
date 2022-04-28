package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: SensitiveContentCoordinator.kt */
public final class SensitiveContentCoordinatorImpl$onBeforeRenderList$1 extends Lambda implements Function1<NotificationEntry, Boolean> {
    public static final SensitiveContentCoordinatorImpl$onBeforeRenderList$1 INSTANCE = new SensitiveContentCoordinatorImpl$onBeforeRenderList$1();

    public SensitiveContentCoordinatorImpl$onBeforeRenderList$1() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Boolean.valueOf(((NotificationEntry) obj).rowExists());
    }
}
