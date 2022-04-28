package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotifCollectionLogger.kt */
public final class NotifCollectionLogger$logNonExistentNotifDismissed$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotifCollectionLogger$logNonExistentNotifDismissed$2 INSTANCE = new NotifCollectionLogger$logNonExistentNotifDismissed$2();

    public NotifCollectionLogger$logNonExistentNotifDismissed$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("DISMISSED Non Existent ", ((LogMessage) obj).getStr1());
    }
}
