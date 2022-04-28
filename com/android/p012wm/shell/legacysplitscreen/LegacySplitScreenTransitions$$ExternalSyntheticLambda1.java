package com.android.p012wm.shell.legacysplitscreen;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.view.SurfaceControl;

/* renamed from: com.android.wm.shell.legacysplitscreen.LegacySplitScreenTransitions$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class LegacySplitScreenTransitions$$ExternalSyntheticLambda1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ SurfaceControl.Transaction f$0;
    public final /* synthetic */ SurfaceControl f$1;
    public final /* synthetic */ Rect f$2;
    public final /* synthetic */ Rect f$3;

    public /* synthetic */ LegacySplitScreenTransitions$$ExternalSyntheticLambda1(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, Rect rect, Rect rect2) {
        this.f$0 = transaction;
        this.f$1 = surfaceControl;
        this.f$2 = rect;
        this.f$3 = rect2;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        SurfaceControl.Transaction transaction = this.f$0;
        SurfaceControl surfaceControl = this.f$1;
        Rect rect = this.f$2;
        Rect rect2 = this.f$3;
        float animatedFraction = valueAnimator.getAnimatedFraction();
        float f = 1.0f - animatedFraction;
        transaction.setWindowCrop(surfaceControl, (int) ((((float) rect2.width()) * animatedFraction) + (((float) rect.width()) * f)), (int) ((((float) rect2.height()) * animatedFraction) + (((float) rect.height()) * f)));
        transaction.setPosition(surfaceControl, (((float) rect2.left) * animatedFraction) + (((float) rect.left) * f), (((float) rect2.top) * animatedFraction) + (((float) rect.top) * f));
        transaction.apply();
    }
}
