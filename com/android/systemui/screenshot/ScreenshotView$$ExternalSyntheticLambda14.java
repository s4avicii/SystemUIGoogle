package com.android.systemui.screenshot;

import android.view.View;
import com.android.systemui.screenshot.ScreenshotController;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ScreenshotView$$ExternalSyntheticLambda14 implements View.OnClickListener {
    public final /* synthetic */ ScreenshotView f$0;
    public final /* synthetic */ ScreenshotController.SavedImageData f$1;

    public /* synthetic */ ScreenshotView$$ExternalSyntheticLambda14(ScreenshotView screenshotView, ScreenshotController.SavedImageData savedImageData) {
        this.f$0 = screenshotView;
        this.f$1 = savedImageData;
    }

    public final void onClick(View view) {
        ScreenshotView screenshotView = this.f$0;
        ScreenshotController.SavedImageData savedImageData = this.f$1;
        int i = ScreenshotView.$r8$clinit;
        Objects.requireNonNull(screenshotView);
        screenshotView.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_PREVIEW_TAPPED, 0, screenshotView.mPackageName);
        screenshotView.startSharedTransition(savedImageData.editTransition.get());
    }
}
