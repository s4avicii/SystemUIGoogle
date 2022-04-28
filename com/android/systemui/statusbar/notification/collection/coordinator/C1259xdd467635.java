package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.GroupCountCoordinator$onBeforeFinalizeFilter$$inlined$filterIsInstance$1 */
/* compiled from: _Sequences.kt */
public final class C1259xdd467635 extends Lambda implements Function1<Object, Boolean> {
    public static final C1259xdd467635 INSTANCE = new C1259xdd467635();

    public C1259xdd467635() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Boolean.valueOf(obj instanceof GroupEntry);
    }
}
