package com.android.systemui.controls.p004ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/* renamed from: com.android.systemui.controls.ui.ToggleRangeBehavior$updateRange$1$2 */
/* compiled from: ToggleRangeBehavior.kt */
public final class ToggleRangeBehavior$updateRange$1$2 extends AnimatorListenerAdapter {
    public final /* synthetic */ ToggleRangeBehavior this$0;

    public ToggleRangeBehavior$updateRange$1$2(ToggleRangeBehavior toggleRangeBehavior) {
        this.this$0 = toggleRangeBehavior;
    }

    public final void onAnimationEnd(Animator animator) {
        this.this$0.rangeAnimator = null;
    }
}
