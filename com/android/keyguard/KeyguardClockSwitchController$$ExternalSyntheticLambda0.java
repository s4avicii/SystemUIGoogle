package com.android.keyguard;

import com.android.internal.colorextraction.ColorExtractor;
import com.android.systemui.plugins.ClockPlugin;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardClockSwitchController$$ExternalSyntheticLambda0 implements ColorExtractor.OnColorsChangedListener {
    public final /* synthetic */ KeyguardClockSwitchController f$0;

    public /* synthetic */ KeyguardClockSwitchController$$ExternalSyntheticLambda0(KeyguardClockSwitchController keyguardClockSwitchController) {
        this.f$0 = keyguardClockSwitchController;
    }

    public final void onColorsChanged(ColorExtractor colorExtractor, int i) {
        KeyguardClockSwitchController keyguardClockSwitchController = this.f$0;
        if ((i & 2) != 0) {
            KeyguardClockSwitch keyguardClockSwitch = (KeyguardClockSwitch) keyguardClockSwitchController.mView;
            ColorExtractor.GradientColors colors = keyguardClockSwitchController.mColorExtractor.getColors(2);
            Objects.requireNonNull(keyguardClockSwitch);
            keyguardClockSwitch.mSupportsDarkText = colors.supportsDarkText();
            int[] colorPalette = colors.getColorPalette();
            keyguardClockSwitch.mColorPalette = colorPalette;
            ClockPlugin clockPlugin = keyguardClockSwitch.mClockPlugin;
            if (clockPlugin != null) {
                clockPlugin.setColorPalette(keyguardClockSwitch.mSupportsDarkText, colorPalette);
                return;
            }
            return;
        }
        Objects.requireNonNull(keyguardClockSwitchController);
    }
}
