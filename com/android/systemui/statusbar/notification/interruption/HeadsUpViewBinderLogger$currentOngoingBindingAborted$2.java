package com.android.systemui.statusbar.notification.interruption;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: HeadsUpViewBinderLogger.kt */
final class HeadsUpViewBinderLogger$currentOngoingBindingAborted$2 extends Lambda implements Function1<LogMessage, String> {
    public static final HeadsUpViewBinderLogger$currentOngoingBindingAborted$2 INSTANCE = new HeadsUpViewBinderLogger$currentOngoingBindingAborted$2();

    public HeadsUpViewBinderLogger$currentOngoingBindingAborted$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("aborted potential ongoing heads up entry binding ");
        m.append(((LogMessage) obj).getStr1());
        m.append(' ');
        return m.toString();
    }
}
