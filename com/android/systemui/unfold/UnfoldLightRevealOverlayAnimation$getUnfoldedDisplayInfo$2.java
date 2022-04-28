package com.android.systemui.unfold;

import android.view.DisplayInfo;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: UnfoldLightRevealOverlayAnimation.kt */
final class UnfoldLightRevealOverlayAnimation$getUnfoldedDisplayInfo$2 extends Lambda implements Function1<DisplayInfo, Boolean> {
    public static final UnfoldLightRevealOverlayAnimation$getUnfoldedDisplayInfo$2 INSTANCE = new UnfoldLightRevealOverlayAnimation$getUnfoldedDisplayInfo$2();

    public UnfoldLightRevealOverlayAnimation$getUnfoldedDisplayInfo$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        int i = ((DisplayInfo) obj).type;
        boolean z = true;
        if (i != 1) {
            z = false;
        }
        return Boolean.valueOf(z);
    }
}
