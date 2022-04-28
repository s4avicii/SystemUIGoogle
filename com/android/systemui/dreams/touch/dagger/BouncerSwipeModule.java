package com.android.systemui.dreams.touch.dagger;

import android.content.res.Resources;
import android.util.TypedValue;
import com.android.p012wm.shell.C1777R;

public final class BouncerSwipeModule {
    public static float providesSwipeToBouncerStartRegion(Resources resources) {
        TypedValue typedValue = new TypedValue();
        resources.getValue(C1777R.dimen.dream_overlay_bouncer_start_region_screen_percentage, typedValue, true);
        return typedValue.getFloat();
    }
}
