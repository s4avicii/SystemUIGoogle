package com.android.systemui.screenshot;

import android.view.KeyEvent;
import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ScreenshotController$$ExternalSyntheticLambda1 implements View.OnKeyListener {
    public final /* synthetic */ ScreenshotController f$0;

    public /* synthetic */ ScreenshotController$$ExternalSyntheticLambda1(ScreenshotController screenshotController) {
        this.f$0 = screenshotController;
    }

    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        ScreenshotController screenshotController = this.f$0;
        if (i == 4) {
            screenshotController.dismissScreenshot();
            return true;
        }
        Objects.requireNonNull(screenshotController);
        return false;
    }
}
