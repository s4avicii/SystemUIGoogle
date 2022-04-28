package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: GutsCoordinatorLogger.kt */
final class GutsCoordinatorLogger$logGutsClosed$2 extends Lambda implements Function1<LogMessage, String> {
    public static final GutsCoordinatorLogger$logGutsClosed$2 INSTANCE = new GutsCoordinatorLogger$logGutsClosed$2();

    public GutsCoordinatorLogger$logGutsClosed$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("Guts closed for class ", ((LogMessage) obj).getStr1());
    }
}
