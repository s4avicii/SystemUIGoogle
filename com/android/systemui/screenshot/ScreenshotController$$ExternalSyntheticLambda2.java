package com.android.systemui.screenshot;

import com.android.systemui.screenshot.ScreenshotController;
import com.android.systemui.tuner.NavBarTuner$$ExternalSyntheticLambda6;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ScreenshotController$$ExternalSyntheticLambda2 implements ScreenshotController.ActionsReadyListener {
    public final /* synthetic */ ScreenshotController f$0;

    public /* synthetic */ ScreenshotController$$ExternalSyntheticLambda2(ScreenshotController screenshotController) {
        this.f$0 = screenshotController;
    }

    public final void onActionsReady(ScreenshotController.SavedImageData savedImageData) {
        ScreenshotController screenshotController = this.f$0;
        Objects.requireNonNull(screenshotController);
        screenshotController.logSuccessOnActionsReady(savedImageData);
        screenshotController.mScreenshotHandler.resetTimeout();
        if (savedImageData.uri != null) {
            screenshotController.mScreenshotHandler.post(new NavBarTuner$$ExternalSyntheticLambda6(screenshotController, savedImageData, 2));
        }
    }
}
