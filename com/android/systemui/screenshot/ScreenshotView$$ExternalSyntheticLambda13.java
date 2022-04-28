package com.android.systemui.screenshot;

import android.view.View;
import com.android.systemui.screenshot.ScreenshotView;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ScreenshotView$$ExternalSyntheticLambda13 implements View.OnClickListener {
    public final /* synthetic */ ScreenshotView f$0;

    public /* synthetic */ ScreenshotView$$ExternalSyntheticLambda13(ScreenshotView screenshotView) {
        this.f$0 = screenshotView;
    }

    public final void onClick(View view) {
        ScreenshotView screenshotView = this.f$0;
        int i = ScreenshotView.$r8$clinit;
        Objects.requireNonNull(screenshotView);
        screenshotView.mEditChip.setIsPending(true);
        screenshotView.mShareChip.setIsPending(false);
        OverlayActionChip overlayActionChip = screenshotView.mQuickShareChip;
        if (overlayActionChip != null) {
            overlayActionChip.setIsPending(false);
        }
        screenshotView.mPendingInteraction = ScreenshotView.PendingInteraction.EDIT;
    }
}
