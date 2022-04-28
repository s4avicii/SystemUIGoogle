package com.android.systemui.statusbar.notification.collection.notifcollection;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.JobKt;

/* compiled from: NotifCollectionLogger.kt */
public final class NotifCollectionLogger$logNoNotificationToRemoveWithKey$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotifCollectionLogger$logNoNotificationToRemoveWithKey$2 INSTANCE = new NotifCollectionLogger$logNoNotificationToRemoveWithKey$2();

    public NotifCollectionLogger$logNoNotificationToRemoveWithKey$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("No notification to remove with key ");
        m.append(logMessage.getStr1());
        m.append(" reason=");
        m.append(JobKt.cancellationReasonDebugString(logMessage.getInt1()));
        return m.toString();
    }
}
