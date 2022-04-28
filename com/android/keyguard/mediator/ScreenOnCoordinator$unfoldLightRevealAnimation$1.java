package com.android.keyguard.mediator;

import com.android.systemui.unfold.SysUIUnfoldComponent;
import java.util.function.Function;

/* compiled from: ScreenOnCoordinator.kt */
public /* synthetic */ class ScreenOnCoordinator$unfoldLightRevealAnimation$1 implements Function {
    public static final ScreenOnCoordinator$unfoldLightRevealAnimation$1 INSTANCE = new ScreenOnCoordinator$unfoldLightRevealAnimation$1();

    public final Object apply(Object obj) {
        return ((SysUIUnfoldComponent) obj).getUnfoldLightRevealOverlayAnimation();
    }
}
