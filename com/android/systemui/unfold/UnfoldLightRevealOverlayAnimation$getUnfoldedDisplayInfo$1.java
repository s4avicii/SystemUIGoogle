package com.android.systemui.unfold;

import android.view.Display;
import android.view.DisplayInfo;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: UnfoldLightRevealOverlayAnimation.kt */
final class UnfoldLightRevealOverlayAnimation$getUnfoldedDisplayInfo$1 extends Lambda implements Function1<Display, DisplayInfo> {
    public static final UnfoldLightRevealOverlayAnimation$getUnfoldedDisplayInfo$1 INSTANCE = new UnfoldLightRevealOverlayAnimation$getUnfoldedDisplayInfo$1();

    public UnfoldLightRevealOverlayAnimation$getUnfoldedDisplayInfo$1() {
        super(1);
    }

    public final Object invoke(Object obj) {
        DisplayInfo displayInfo = new DisplayInfo();
        ((Display) obj).getDisplayInfo(displayInfo);
        return displayInfo;
    }
}
