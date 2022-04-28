package com.google.android.systemui.statusbar;

import kotlin.Pair;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$registerCallbacksWhenEnabled$3 */
/* compiled from: NotificationVoiceReplyManagerService.kt */
final class C2340x1ef5d61f extends Lambda implements Function2<Pair<? extends Integer, ? extends Integer>, Pair<? extends Integer, ? extends Integer>, Boolean> {
    public static final C2340x1ef5d61f INSTANCE = new C2340x1ef5d61f();

    public C2340x1ef5d61f() {
        super(2);
    }

    public final Object invoke(Object obj, Object obj2) {
        boolean z;
        boolean z2;
        int intValue = ((Number) ((Pair) obj).component2()).intValue();
        int intValue2 = ((Number) ((Pair) obj2).component2()).intValue();
        boolean z3 = true;
        if (intValue != 0) {
            z = true;
        } else {
            z = false;
        }
        if (intValue2 != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z != z2) {
            z3 = false;
        }
        return Boolean.valueOf(z3);
    }
}
