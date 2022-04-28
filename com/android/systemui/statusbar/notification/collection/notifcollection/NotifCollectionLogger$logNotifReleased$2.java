package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotifCollectionLogger.kt */
public final class NotifCollectionLogger$logNotifReleased$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotifCollectionLogger$logNotifReleased$2 INSTANCE = new NotifCollectionLogger$logNotifReleased$2();

    public NotifCollectionLogger$logNotifReleased$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("RELEASED ", ((LogMessage) obj).getStr1());
    }
}
