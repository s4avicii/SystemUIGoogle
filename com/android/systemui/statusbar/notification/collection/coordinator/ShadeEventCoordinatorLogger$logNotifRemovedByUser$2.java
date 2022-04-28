package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: ShadeEventCoordinatorLogger.kt */
final class ShadeEventCoordinatorLogger$logNotifRemovedByUser$2 extends Lambda implements Function1<LogMessage, String> {
    public static final ShadeEventCoordinatorLogger$logNotifRemovedByUser$2 INSTANCE = new ShadeEventCoordinatorLogger$logNotifRemovedByUser$2();

    public ShadeEventCoordinatorLogger$logNotifRemovedByUser$2() {
        super(1);
    }

    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        return "Notification removed by user";
    }
}
