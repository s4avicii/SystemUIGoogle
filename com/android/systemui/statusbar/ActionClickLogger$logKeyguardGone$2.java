package com.android.systemui.statusbar;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: ActionClickLogger.kt */
public final class ActionClickLogger$logKeyguardGone$2 extends Lambda implements Function1<LogMessage, String> {
    public static final ActionClickLogger$logKeyguardGone$2 INSTANCE = new ActionClickLogger$logKeyguardGone$2();

    public ActionClickLogger$logKeyguardGone$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("  [Action click] Keyguard dismissed, calling default handler for intent ", ((LogMessage) obj).getStr1());
    }
}
