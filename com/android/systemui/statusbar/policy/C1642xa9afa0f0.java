package com.android.systemui.statusbar.policy;

import android.app.Notification;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl$inflateSmartReplyViewHolder$smartActionButtons$1$1 */
/* compiled from: SmartReplyStateInflater.kt */
public final class C1642xa9afa0f0 extends Lambda implements Function1<Notification.Action, Boolean> {
    public static final C1642xa9afa0f0 INSTANCE = new C1642xa9afa0f0();

    public C1642xa9afa0f0() {
        super(1);
    }

    public final Object invoke(Object obj) {
        boolean z;
        if (((Notification.Action) obj).actionIntent != null) {
            z = true;
        } else {
            z = false;
        }
        return Boolean.valueOf(z);
    }
}
