package com.android.systemui.p006qs;

import com.android.internal.colorextraction.ColorExtractor;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.QuickStatusBarHeaderController$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class QuickStatusBarHeaderController$$ExternalSyntheticLambda0 implements ColorExtractor.OnColorsChangedListener {
    public final /* synthetic */ QuickStatusBarHeaderController f$0;

    public /* synthetic */ QuickStatusBarHeaderController$$ExternalSyntheticLambda0(QuickStatusBarHeaderController quickStatusBarHeaderController) {
        this.f$0 = quickStatusBarHeaderController;
    }

    public final void onColorsChanged(ColorExtractor colorExtractor, int i) {
        ColorExtractor.GradientColors gradientColors;
        QuickStatusBarHeaderController quickStatusBarHeaderController = this.f$0;
        Objects.requireNonNull(quickStatusBarHeaderController);
        SysuiColorExtractor sysuiColorExtractor = quickStatusBarHeaderController.mColorExtractor;
        Objects.requireNonNull(sysuiColorExtractor);
        if (sysuiColorExtractor.mHasMediaArtwork) {
            gradientColors = sysuiColorExtractor.mBackdropColors;
        } else {
            gradientColors = sysuiColorExtractor.mNeutralColorsLock;
        }
        quickStatusBarHeaderController.mClockView.onColorsChanged(gradientColors.supportsDarkText());
    }
}
