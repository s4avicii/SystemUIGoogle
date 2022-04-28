package com.android.systemui.statusbar.policy;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: HeadsUpManagerLogger.kt */
public final class HeadsUpManagerLogger$logUpdateEntry$2 extends Lambda implements Function1<LogMessage, String> {
    public final /* synthetic */ String $key;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public HeadsUpManagerLogger$logUpdateEntry$2(String str) {
        super(1);
        this.$key = str;
    }

    public final Object invoke(Object obj) {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("update entry ");
        m.append(this.$key);
        m.append(" updatePostTime: ");
        m.append(((LogMessage) obj).getBool1());
        return m.toString();
    }
}
