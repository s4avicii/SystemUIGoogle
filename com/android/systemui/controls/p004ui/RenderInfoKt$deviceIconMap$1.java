package com.android.systemui.controls.p004ui;

import com.android.p012wm.shell.C1777R;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.controls.ui.RenderInfoKt$deviceIconMap$1 */
/* compiled from: RenderInfo.kt */
public final class RenderInfoKt$deviceIconMap$1 extends Lambda implements Function1<Integer, Integer> {
    public static final RenderInfoKt$deviceIconMap$1 INSTANCE = new RenderInfoKt$deviceIconMap$1();

    public RenderInfoKt$deviceIconMap$1() {
        super(1);
    }

    public final Object invoke(Object obj) {
        ((Number) obj).intValue();
        return Integer.valueOf(C1777R.C1778drawable.ic_device_unknown);
    }
}
