package com.android.settingslib.animation;

import android.content.Context;
import android.view.animation.Interpolator;
import com.android.settingslib.animation.AppearAnimationUtils;

public final class DisappearAnimationUtils extends AppearAnimationUtils {
    public static final C05821 ROW_TRANSLATION_SCALER = new AppearAnimationUtils.RowTranslationScaler() {
    };

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DisappearAnimationUtils(Context context, long j, float f, float f2, Interpolator interpolator) {
        super(context, j, f, f2, interpolator);
        C05821 r0 = ROW_TRANSLATION_SCALER;
        this.mRowTranslationScaler = r0;
        this.mAppearing = false;
    }

    public final long calculateDelay(int i, int i2) {
        return (long) ((((Math.pow((double) i, 0.4d) + 0.4d) * ((double) i2) * 10.0d) + ((double) (i * 60))) * ((double) this.mDelayScale));
    }
}
