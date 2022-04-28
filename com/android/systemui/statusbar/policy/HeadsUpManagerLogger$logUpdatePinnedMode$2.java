package com.android.systemui.statusbar.policy;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: HeadsUpManagerLogger.kt */
final class HeadsUpManagerLogger$logUpdatePinnedMode$2 extends Lambda implements Function1<LogMessage, String> {
    public static final HeadsUpManagerLogger$logUpdatePinnedMode$2 INSTANCE = new HeadsUpManagerLogger$logUpdatePinnedMode$2();

    public HeadsUpManagerLogger$logUpdatePinnedMode$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("has pinned notification changed to ", Boolean.valueOf(((LogMessage) obj).getBool1()));
    }
}
