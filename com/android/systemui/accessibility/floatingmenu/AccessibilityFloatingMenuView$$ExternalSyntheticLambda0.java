package com.android.systemui.accessibility.floatingmenu;

import android.animation.ValueAnimator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AccessibilityFloatingMenuView$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ AccessibilityFloatingMenuView f$0;

    public /* synthetic */ AccessibilityFloatingMenuView$$ExternalSyntheticLambda0(AccessibilityFloatingMenuView accessibilityFloatingMenuView) {
        this.f$0 = accessibilityFloatingMenuView;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        AccessibilityFloatingMenuView accessibilityFloatingMenuView = this.f$0;
        Objects.requireNonNull(accessibilityFloatingMenuView);
        accessibilityFloatingMenuView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }
}
