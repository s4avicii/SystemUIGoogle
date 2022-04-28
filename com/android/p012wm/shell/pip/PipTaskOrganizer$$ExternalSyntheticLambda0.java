package com.android.p012wm.shell.pip;

import android.animation.ValueAnimator;
import android.util.Log;
import android.view.SurfaceControl;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PipTaskOrganizer$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ PipTaskOrganizer f$0;
    public final /* synthetic */ SurfaceControl f$1;

    public /* synthetic */ PipTaskOrganizer$$ExternalSyntheticLambda0(PipTaskOrganizer pipTaskOrganizer, SurfaceControl surfaceControl) {
        this.f$0 = pipTaskOrganizer;
        this.f$1 = surfaceControl;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        PipTaskOrganizer pipTaskOrganizer = this.f$0;
        SurfaceControl surfaceControl = this.f$1;
        Objects.requireNonNull(pipTaskOrganizer);
        PipTransitionState pipTransitionState = pipTaskOrganizer.mPipTransitionState;
        Objects.requireNonNull(pipTransitionState);
        if (pipTransitionState.mState == 0) {
            Log.d("PipTaskOrganizer", "Task vanished, skip fadeOutAndRemoveOverlay");
            valueAnimator.removeAllListeners();
            valueAnimator.removeAllUpdateListeners();
            valueAnimator.cancel();
            return;
        }
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        Objects.requireNonNull(pipTaskOrganizer.mSurfaceControlTransactionFactory);
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        transaction.setAlpha(surfaceControl, floatValue);
        transaction.apply();
    }
}
