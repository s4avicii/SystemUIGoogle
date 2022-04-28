package com.android.systemui.statusbar.notification.collection.notifcollection;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotifCollectionLogger.kt */
public final class NotifCollectionLogger$logNotifInternalUpdate$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotifCollectionLogger$logNotifInternalUpdate$2 INSTANCE = new NotifCollectionLogger$logNotifInternalUpdate$2();

    public NotifCollectionLogger$logNotifInternalUpdate$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("UPDATED INTERNALLY ");
        m.append(logMessage.getStr1());
        m.append(" BY ");
        m.append(logMessage.getStr2());
        m.append(" BECAUSE ");
        m.append(logMessage.getStr3());
        return m.toString();
    }
}
