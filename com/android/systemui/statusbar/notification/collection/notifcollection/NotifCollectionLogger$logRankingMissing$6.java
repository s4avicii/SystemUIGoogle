package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotifCollectionLogger.kt */
public final class NotifCollectionLogger$logRankingMissing$6 extends Lambda implements Function1<LogMessage, String> {
    public static final NotifCollectionLogger$logRankingMissing$6 INSTANCE = new NotifCollectionLogger$logRankingMissing$6();

    public NotifCollectionLogger$logRankingMissing$6() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("  ", ((LogMessage) obj).getStr1());
    }
}
