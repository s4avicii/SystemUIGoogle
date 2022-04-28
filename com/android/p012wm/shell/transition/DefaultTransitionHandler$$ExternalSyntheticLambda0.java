package com.android.p012wm.shell.transition;

import android.animation.ValueAnimator;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.SurfaceControl;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/* renamed from: com.android.wm.shell.transition.DefaultTransitionHandler$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DefaultTransitionHandler$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ ValueAnimator f$0;
    public final /* synthetic */ SurfaceControl.Transaction f$1;
    public final /* synthetic */ SurfaceControl f$2;
    public final /* synthetic */ Animation f$3;
    public final /* synthetic */ Transformation f$4;
    public final /* synthetic */ float[] f$5;
    public final /* synthetic */ Point f$6;
    public final /* synthetic */ float f$7;
    public final /* synthetic */ Rect f$8;

    public /* synthetic */ DefaultTransitionHandler$$ExternalSyntheticLambda0(ValueAnimator valueAnimator, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, Animation animation, Transformation transformation, float[] fArr, Point point, float f, Rect rect) {
        this.f$0 = valueAnimator;
        this.f$1 = transaction;
        this.f$2 = surfaceControl;
        this.f$3 = animation;
        this.f$4 = transformation;
        this.f$5 = fArr;
        this.f$6 = point;
        this.f$7 = f;
        this.f$8 = rect;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        ValueAnimator valueAnimator2 = this.f$0;
        DefaultTransitionHandler.applyTransformation(Math.min(valueAnimator2.getDuration(), valueAnimator2.getCurrentPlayTime()), this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6, this.f$7, this.f$8);
    }
}
