package com.android.p012wm.shell.common;

import android.animation.ValueAnimator;
import android.view.SurfaceControl;
import com.android.p012wm.shell.common.DisplayImeController;
import java.util.Iterator;
import java.util.Objects;

/* renamed from: com.android.wm.shell.common.DisplayImeController$PerDisplay$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DisplayImeController$PerDisplay$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ DisplayImeController.PerDisplay f$0;
    public final /* synthetic */ float f$1;
    public final /* synthetic */ boolean f$2;
    public final /* synthetic */ float f$3;
    public final /* synthetic */ float f$4;

    public /* synthetic */ DisplayImeController$PerDisplay$$ExternalSyntheticLambda0(DisplayImeController.PerDisplay perDisplay, float f, boolean z, float f2, float f3) {
        this.f$0 = perDisplay;
        this.f$1 = f;
        this.f$2 = z;
        this.f$3 = f2;
        this.f$4 = f3;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        float f;
        DisplayImeController.PerDisplay perDisplay = this.f$0;
        float f2 = this.f$1;
        boolean z = this.f$2;
        float f3 = this.f$3;
        float f4 = this.f$4;
        Objects.requireNonNull(perDisplay);
        SurfaceControl.Transaction acquire = DisplayImeController.this.mTransactionPool.acquire();
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        acquire.setPosition(perDisplay.mImeSourceControl.getLeash(), f2, floatValue);
        if (perDisplay.mAnimateAlpha || z) {
            f = (floatValue - f3) / (f4 - f3);
        } else {
            f = 1.0f;
        }
        acquire.setAlpha(perDisplay.mImeSourceControl.getLeash(), f);
        DisplayImeController displayImeController = DisplayImeController.this;
        int i = perDisplay.mDisplayId;
        int i2 = perDisplay.mImeFrame.top + ((int) floatValue);
        Objects.requireNonNull(displayImeController);
        synchronized (displayImeController.mPositionProcessors) {
            Iterator<DisplayImeController.ImePositionProcessor> it = displayImeController.mPositionProcessors.iterator();
            while (it.hasNext()) {
                it.next().onImePositionChanged(i, i2, acquire);
            }
        }
        acquire.apply();
        DisplayImeController.this.mTransactionPool.release(acquire);
    }
}
