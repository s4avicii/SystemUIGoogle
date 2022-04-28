package com.google.android.systemui.assist.uihints;

import android.animation.ValueAnimator;
import com.google.android.systemui.assist.uihints.GreetingView;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class GreetingView$StaggeredSpan$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ GreetingView.StaggeredSpan f$0;

    public /* synthetic */ GreetingView$StaggeredSpan$$ExternalSyntheticLambda0(GreetingView.StaggeredSpan staggeredSpan) {
        this.f$0 = staggeredSpan;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        GreetingView.StaggeredSpan staggeredSpan = this.f$0;
        Objects.requireNonNull(staggeredSpan);
        staggeredSpan.mAlpha = (int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * GreetingView.this.mMaxAlpha);
    }
}
