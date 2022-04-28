package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logRemoteExceptionOnClearAllNotifications$2 */
/* compiled from: NotifCollectionLogger.kt */
public final class C1288x8e3a9df8 extends Lambda implements Function1<LogMessage, String> {
    public static final C1288x8e3a9df8 INSTANCE = new C1288x8e3a9df8();

    public C1288x8e3a9df8() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("RemoteException while attempting to clear all notifications:\n", ((LogMessage) obj).getStr1());
    }
}
