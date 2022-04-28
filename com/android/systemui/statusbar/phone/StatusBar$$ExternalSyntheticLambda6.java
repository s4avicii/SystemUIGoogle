package com.android.systemui.statusbar.phone;

import android.util.MathUtils;
import com.android.systemui.statusbar.BackDropView;
import com.android.systemui.statusbar.NotificationShadeDepthController;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBar$$ExternalSyntheticLambda6 implements NotificationShadeDepthController.DepthListener {
    public final /* synthetic */ float f$0;
    public final /* synthetic */ BackDropView f$1;

    public /* synthetic */ StatusBar$$ExternalSyntheticLambda6(float f, BackDropView backDropView) {
        this.f$0 = f;
        this.f$1 = backDropView;
    }

    public final void onWallpaperZoomOutChanged(float f) {
        float f2 = this.f$0;
        BackDropView backDropView = this.f$1;
        long[] jArr = StatusBar.CAMERA_LAUNCH_GESTURE_VIBRATION_TIMINGS;
        float lerp = MathUtils.lerp(f2, 1.0f, f);
        backDropView.setPivotX(((float) backDropView.getWidth()) / 2.0f);
        backDropView.setPivotY(((float) backDropView.getHeight()) / 2.0f);
        backDropView.setScaleX(lerp);
        backDropView.setScaleY(lerp);
    }
}
