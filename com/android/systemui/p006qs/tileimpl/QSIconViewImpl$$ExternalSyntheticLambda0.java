package com.android.systemui.p006qs.tileimpl;

import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.widget.ImageView;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0;

/* renamed from: com.android.systemui.qs.tileimpl.QSIconViewImpl$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class QSIconViewImpl$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ float f$0;
    public final /* synthetic */ float f$1;
    public final /* synthetic */ float f$2;
    public final /* synthetic */ float f$3;
    public final /* synthetic */ ImageView f$4;

    public /* synthetic */ QSIconViewImpl$$ExternalSyntheticLambda0(float f, float f2, float f3, float f4, ImageView imageView) {
        this.f$0 = f;
        this.f$1 = f2;
        this.f$2 = f3;
        this.f$3 = f4;
        this.f$4 = imageView;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        float f = this.f$0;
        float f2 = this.f$1;
        float f3 = this.f$2;
        float f4 = this.f$3;
        ImageView imageView = this.f$4;
        float animatedFraction = valueAnimator.getAnimatedFraction();
        int m = (int) MotionController$$ExternalSyntheticOutline0.m7m(f4, f3, animatedFraction, f3);
        imageView.setImageTintList(ColorStateList.valueOf(Color.argb((int) MotionController$$ExternalSyntheticOutline0.m7m(f2, f, animatedFraction, f), m, m, m)));
    }
}
