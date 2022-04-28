package com.android.p012wm.shell.common.split;

import android.animation.ValueAnimator;
import java.util.Objects;

/* renamed from: com.android.wm.shell.common.split.SplitLayout$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SplitLayout$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ SplitLayout f$0;

    public /* synthetic */ SplitLayout$$ExternalSyntheticLambda0(SplitLayout splitLayout) {
        this.f$0 = splitLayout;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        SplitLayout splitLayout = this.f$0;
        Objects.requireNonNull(splitLayout);
        splitLayout.updateBounds(((Integer) valueAnimator.getAnimatedValue()).intValue());
        splitLayout.mSplitLayoutHandler.onLayoutSizeChanging(splitLayout);
    }
}
