package com.android.systemui.statusbar.policy;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: HeadsUpManagerLogger.kt */
public final class HeadsUpManagerLogger$logReleaseAllImmediately$2 extends Lambda implements Function1<LogMessage, String> {
    public static final HeadsUpManagerLogger$logReleaseAllImmediately$2 INSTANCE = new HeadsUpManagerLogger$logReleaseAllImmediately$2();

    public HeadsUpManagerLogger$logReleaseAllImmediately$2() {
        super(1);
    }

    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        return "release all immediately";
    }
}
