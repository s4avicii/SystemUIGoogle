package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.statusbar.notification.collection.render.RenderStageManager$dispatchOnAfterRenderGroups$lambda-5$$inlined$filterIsInstance$1 */
/* compiled from: _Sequences.kt */
public final class C1289x81935fa2 extends Lambda implements Function1<Object, Boolean> {
    public static final C1289x81935fa2 INSTANCE = new C1289x81935fa2();

    public C1289x81935fa2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Boolean.valueOf(obj instanceof GroupEntry);
    }
}
