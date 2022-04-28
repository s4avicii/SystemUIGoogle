package com.android.systemui.screenshot;

import android.util.DisplayMetrics;
import android.view.WindowManager;

public final class FloatingWindowUtil {
    public static float dpToPx(DisplayMetrics displayMetrics, float f) {
        return (f * ((float) displayMetrics.densityDpi)) / 160.0f;
    }

    public static WindowManager.LayoutParams getFloatingWindowParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 0, 0, 2036, 918816, -3);
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.privateFlags |= 536870912;
        return layoutParams;
    }
}
