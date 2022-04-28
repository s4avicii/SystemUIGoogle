package com.android.systemui.screenshot;

import android.view.InputEvent;
import android.view.MotionEvent;
import com.android.systemui.screenshot.ScreenshotController;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ScreenshotView$$ExternalSyntheticLambda15 implements InputChannelCompat$InputEventListener {
    public final /* synthetic */ ScreenshotView f$0;

    public /* synthetic */ ScreenshotView$$ExternalSyntheticLambda15(ScreenshotView screenshotView) {
        this.f$0 = screenshotView;
    }

    public final void onInputEvent(InputEvent inputEvent) {
        ScreenshotView screenshotView = this.f$0;
        int i = ScreenshotView.$r8$clinit;
        Objects.requireNonNull(screenshotView);
        if (inputEvent instanceof MotionEvent) {
            MotionEvent motionEvent = (MotionEvent) inputEvent;
            if (motionEvent.getActionMasked() == 0 && !screenshotView.getTouchRegion(false).contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY())) {
                ScreenshotController.C10753 r3 = (ScreenshotController.C10753) screenshotView.mCallbacks;
                Objects.requireNonNull(r3);
                ScreenshotController.this.setWindowFocusable(false);
            }
        }
    }
}
