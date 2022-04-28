package com.android.systemui.accessibility.floatingmenu;

import android.animation.ValueAnimator;
import android.view.WindowManager;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AccessibilityFloatingMenuView$$ExternalSyntheticLambda1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ AccessibilityFloatingMenuView f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ AccessibilityFloatingMenuView$$ExternalSyntheticLambda1(AccessibilityFloatingMenuView accessibilityFloatingMenuView, int i, int i2) {
        this.f$0 = accessibilityFloatingMenuView;
        this.f$1 = i;
        this.f$2 = i2;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        AccessibilityFloatingMenuView accessibilityFloatingMenuView = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        Objects.requireNonNull(accessibilityFloatingMenuView);
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        float f = 1.0f - floatValue;
        WindowManager.LayoutParams layoutParams = accessibilityFloatingMenuView.mCurrentLayoutParams;
        float f2 = ((float) i) * floatValue;
        layoutParams.x = (int) (f2 + (((float) layoutParams.x) * f));
        layoutParams.y = (int) ((floatValue * ((float) i2)) + (f * ((float) layoutParams.y)));
        accessibilityFloatingMenuView.mWindowManager.updateViewLayout(accessibilityFloatingMenuView, layoutParams);
    }
}
