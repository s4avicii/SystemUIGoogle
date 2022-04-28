package com.android.systemui.statusbar;

import android.animation.ValueAnimator;
import com.android.systemui.R$array;
import com.android.systemui.statusbar.StatusBarIconView;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBarIconView$$ExternalSyntheticLambda1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ StatusBarIconView f$0;

    public /* synthetic */ StatusBarIconView$$ExternalSyntheticLambda1(StatusBarIconView statusBarIconView) {
        this.f$0 = statusBarIconView;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        StatusBarIconView statusBarIconView = this.f$0;
        StatusBarIconView.C11831 r0 = StatusBarIconView.ICON_APPEAR_AMOUNT;
        Objects.requireNonNull(statusBarIconView);
        statusBarIconView.mCurrentSetColor = R$array.interpolateColors(statusBarIconView.mAnimationStartColor, statusBarIconView.mIconColor, valueAnimator.getAnimatedFraction());
        statusBarIconView.updateIconColor();
    }
}
