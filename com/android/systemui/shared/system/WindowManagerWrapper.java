package com.android.systemui.shared.system;

import android.view.InsetsController;
import android.view.animation.Interpolator;

public final class WindowManagerWrapper {
    public static final WindowManagerWrapper sInstance = new WindowManagerWrapper();

    static {
        Interpolator interpolator = InsetsController.RESIZE_INTERPOLATOR;
    }
}
