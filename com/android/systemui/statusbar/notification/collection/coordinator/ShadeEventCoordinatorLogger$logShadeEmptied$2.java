package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: ShadeEventCoordinatorLogger.kt */
final class ShadeEventCoordinatorLogger$logShadeEmptied$2 extends Lambda implements Function1<LogMessage, String> {
    public static final ShadeEventCoordinatorLogger$logShadeEmptied$2 INSTANCE = new ShadeEventCoordinatorLogger$logShadeEmptied$2();

    public ShadeEventCoordinatorLogger$logShadeEmptied$2() {
        super(1);
    }

    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        return "Shade emptied";
    }
}
