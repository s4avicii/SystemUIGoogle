package com.android.systemui.statusbar.notification.stack;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: StackStateLogger.kt */
final class StackStateLogger$logHUNViewAppearingWithAddEvent$2 extends Lambda implements Function1<LogMessage, String> {
    public static final StackStateLogger$logHUNViewAppearingWithAddEvent$2 INSTANCE = new StackStateLogger$logHUNViewAppearingWithAddEvent$2();

    public StackStateLogger$logHUNViewAppearingWithAddEvent$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Heads up view disappearing ");
        m.append(((LogMessage) obj).getStr1());
        m.append(" for ANIMATION_TYPE_ADD");
        return m.toString();
    }
}
