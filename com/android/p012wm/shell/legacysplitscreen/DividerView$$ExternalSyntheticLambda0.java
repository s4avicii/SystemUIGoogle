package com.android.p012wm.shell.legacysplitscreen;

import android.animation.ValueAnimator;
import android.view.SurfaceControl;
import android.view.animation.PathInterpolator;
import com.android.internal.policy.DividerSnapAlgorithm;
import java.util.Objects;

/* renamed from: com.android.wm.shell.legacysplitscreen.DividerView$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DividerView$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ DividerView f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ DividerSnapAlgorithm.SnapTarget f$2;

    public /* synthetic */ DividerView$$ExternalSyntheticLambda0(DividerView dividerView, boolean z, DividerSnapAlgorithm.SnapTarget snapTarget) {
        this.f$0 = dividerView;
        this.f$1 = z;
        this.f$2 = snapTarget;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        int i;
        DividerView dividerView = this.f$0;
        boolean z = this.f$1;
        DividerSnapAlgorithm.SnapTarget snapTarget = this.f$2;
        PathInterpolator pathInterpolator = DividerView.IME_ADJUST_INTERPOLATOR;
        Objects.requireNonNull(dividerView);
        int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        if (!z || valueAnimator.getAnimatedFraction() != 1.0f) {
            i = snapTarget.taskPosition;
        } else {
            i = Integer.MAX_VALUE;
        }
        dividerView.resizeStackSurfaces(intValue, i, snapTarget, (SurfaceControl.Transaction) null);
    }
}
