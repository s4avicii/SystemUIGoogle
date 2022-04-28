package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotifCollectionLogger.kt */
public final class NotifCollectionLogger$logDismissAll$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotifCollectionLogger$logDismissAll$2 INSTANCE = new NotifCollectionLogger$logDismissAll$2();

    public NotifCollectionLogger$logDismissAll$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("DISMISS ALL notifications for user ", Integer.valueOf(((LogMessage) obj).getInt1()));
    }
}
