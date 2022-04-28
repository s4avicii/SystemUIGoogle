package com.android.systemui.controls.p004ui;

import com.android.p012wm.shell.C1777R;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.controls.ui.RenderInfoKt$deviceColorMap$1 */
/* compiled from: RenderInfo.kt */
public final class RenderInfoKt$deviceColorMap$1 extends Lambda implements Function1<Integer, Pair<? extends Integer, ? extends Integer>> {
    public static final RenderInfoKt$deviceColorMap$1 INSTANCE = new RenderInfoKt$deviceColorMap$1();

    public RenderInfoKt$deviceColorMap$1() {
        super(1);
    }

    public final Object invoke(Object obj) {
        ((Number) obj).intValue();
        return new Pair(Integer.valueOf(C1777R.color.control_foreground), Integer.valueOf(C1777R.color.control_enabled_default_background));
    }
}
