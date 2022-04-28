package com.android.systemui.statusbar.phone;

import com.android.internal.colorextraction.ColorExtractor;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBar$$ExternalSyntheticLambda3 implements ColorExtractor.OnColorsChangedListener {
    public final /* synthetic */ StatusBar f$0;

    public /* synthetic */ StatusBar$$ExternalSyntheticLambda3(StatusBar statusBar) {
        this.f$0 = statusBar;
    }

    public final void onColorsChanged(ColorExtractor colorExtractor, int i) {
        StatusBar statusBar = this.f$0;
        long[] jArr = StatusBar.CAMERA_LAUNCH_GESTURE_VIBRATION_TIMINGS;
        Objects.requireNonNull(statusBar);
        statusBar.updateTheme();
    }
}
