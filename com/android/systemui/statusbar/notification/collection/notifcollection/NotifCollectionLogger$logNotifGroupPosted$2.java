package com.android.systemui.statusbar.notification.collection.notifcollection;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotifCollectionLogger.kt */
public final class NotifCollectionLogger$logNotifGroupPosted$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotifCollectionLogger$logNotifGroupPosted$2 INSTANCE = new NotifCollectionLogger$logNotifGroupPosted$2();

    public NotifCollectionLogger$logNotifGroupPosted$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("POSTED GROUP ");
        m.append(logMessage.getStr1());
        m.append(" (");
        m.append(logMessage.getInt1());
        m.append(" events)");
        return m.toString();
    }
}
