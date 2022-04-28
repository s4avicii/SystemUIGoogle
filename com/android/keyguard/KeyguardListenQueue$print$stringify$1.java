package com.android.keyguard;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import java.text.DateFormat;
import java.util.Date;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: KeyguardListenQueue.kt */
final class KeyguardListenQueue$print$stringify$1 extends Lambda implements Function1<KeyguardListenModel, String> {
    public final /* synthetic */ DateFormat $dateFormat;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public KeyguardListenQueue$print$stringify$1(DateFormat dateFormat) {
        super(1);
        this.$dateFormat = dateFormat;
    }

    public final Object invoke(Object obj) {
        KeyguardListenModel keyguardListenModel = (KeyguardListenModel) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("    ");
        m.append(this.$dateFormat.format(new Date(keyguardListenModel.getTimeMillis())));
        m.append(' ');
        m.append(keyguardListenModel);
        return m.toString();
    }
}
