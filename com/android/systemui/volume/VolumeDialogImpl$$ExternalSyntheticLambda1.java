package com.android.systemui.volume;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class VolumeDialogImpl$$ExternalSyntheticLambda1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ VolumeDialogImpl f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ VolumeDialogImpl$$ExternalSyntheticLambda1(VolumeDialogImpl volumeDialogImpl, int i, int i2) {
        this.f$0 = volumeDialogImpl;
        this.f$1 = i;
        this.f$2 = i2;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        VolumeDialogImpl volumeDialogImpl = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        Objects.requireNonNull(volumeDialogImpl);
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        int intValue = ((Integer) ArgbEvaluator.getInstance().evaluate(floatValue, Integer.valueOf(i), Integer.valueOf(i2))).intValue();
        int intValue2 = ((Integer) ArgbEvaluator.getInstance().evaluate(floatValue, Integer.valueOf(i2), Integer.valueOf(i))).intValue();
        volumeDialogImpl.mRingerDrawerIconAnimatingDeselected.setColorFilter(intValue);
        volumeDialogImpl.mRingerDrawerIconAnimatingSelected.setColorFilter(intValue2);
    }
}
