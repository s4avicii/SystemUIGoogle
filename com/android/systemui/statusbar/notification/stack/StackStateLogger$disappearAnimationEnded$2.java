package com.android.systemui.statusbar.notification.stack;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: StackStateLogger.kt */
public final class StackStateLogger$disappearAnimationEnded$2 extends Lambda implements Function1<LogMessage, String> {
    public static final StackStateLogger$disappearAnimationEnded$2 INSTANCE = new StackStateLogger$disappearAnimationEnded$2();

    public StackStateLogger$disappearAnimationEnded$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Heads up notification disappear animation ended ");
        m.append(((LogMessage) obj).getStr1());
        m.append(' ');
        return m.toString();
    }
}
