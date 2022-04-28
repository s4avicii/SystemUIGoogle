package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: HeadsUpCoordinatorLogger.kt */
final class HeadsUpCoordinatorLogger$logEvaluatingGroups$2 extends Lambda implements Function1<LogMessage, String> {
    public static final HeadsUpCoordinatorLogger$logEvaluatingGroups$2 INSTANCE = new HeadsUpCoordinatorLogger$logEvaluatingGroups$2();

    public HeadsUpCoordinatorLogger$logEvaluatingGroups$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("evaluating groups for alert transfer: ", Integer.valueOf(((LogMessage) obj).getInt1()));
    }
}
