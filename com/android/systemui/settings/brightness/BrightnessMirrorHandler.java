package com.android.systemui.settings.brightness;

import com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda4;
import com.android.systemui.statusbar.policy.BrightnessMirrorController;
import java.util.Objects;

/* compiled from: BrightnessMirrorHandler.kt */
public final class BrightnessMirrorHandler {
    public final MirroredBrightnessController brightnessController;
    public final BrightnessMirrorHandler$brightnessMirrorListener$1 brightnessMirrorListener = new BrightnessMirrorHandler$brightnessMirrorListener$1(this);
    public BrightnessMirrorController mirrorController;

    public final void updateBrightnessMirror() {
        BrightnessMirrorController brightnessMirrorController = this.mirrorController;
        if (brightnessMirrorController != null) {
            BrightnessController brightnessController2 = (BrightnessController) this.brightnessController;
            Objects.requireNonNull(brightnessController2);
            BrightnessSliderController brightnessSliderController = (BrightnessSliderController) brightnessController2.mControl;
            Objects.requireNonNull(brightnessSliderController);
            brightnessSliderController.mMirrorController = brightnessMirrorController;
            BrightnessSliderController brightnessSliderController2 = brightnessMirrorController.mToggleSliderController;
            brightnessSliderController.mMirror = brightnessSliderController2;
            if (brightnessSliderController2 != null) {
                BrightnessSliderView brightnessSliderView = (BrightnessSliderView) brightnessSliderController.mView;
                Objects.requireNonNull(brightnessSliderView);
                brightnessSliderController2.setMax(brightnessSliderView.mSlider.getMax());
                ToggleSlider toggleSlider = brightnessSliderController.mMirror;
                BrightnessSliderView brightnessSliderView2 = (BrightnessSliderView) brightnessSliderController.mView;
                Objects.requireNonNull(brightnessSliderView2);
                ((BrightnessSliderController) toggleSlider).setValue(brightnessSliderView2.mSlider.getProgress());
                BrightnessSliderView brightnessSliderView3 = (BrightnessSliderView) brightnessSliderController.mView;
                ScreenshotController$$ExternalSyntheticLambda4 screenshotController$$ExternalSyntheticLambda4 = new ScreenshotController$$ExternalSyntheticLambda4(brightnessSliderController);
                Objects.requireNonNull(brightnessSliderView3);
                brightnessSliderView3.mListener = screenshotController$$ExternalSyntheticLambda4;
                return;
            }
            BrightnessSliderView brightnessSliderView4 = (BrightnessSliderView) brightnessSliderController.mView;
            Objects.requireNonNull(brightnessSliderView4);
            brightnessSliderView4.mListener = null;
        }
    }

    public BrightnessMirrorHandler(BrightnessController brightnessController2) {
        this.brightnessController = brightnessController2;
    }
}
