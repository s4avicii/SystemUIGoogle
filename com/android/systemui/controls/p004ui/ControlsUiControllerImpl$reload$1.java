package com.android.systemui.controls.p004ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

/* renamed from: com.android.systemui.controls.ui.ControlsUiControllerImpl$reload$1 */
/* compiled from: ControlsUiControllerImpl.kt */
public final class ControlsUiControllerImpl$reload$1 extends AnimatorListenerAdapter {
    public final /* synthetic */ ViewGroup $parent;
    public final /* synthetic */ ControlsUiControllerImpl this$0;

    public ControlsUiControllerImpl$reload$1(ControlsUiControllerImpl controlsUiControllerImpl, ViewGroup viewGroup) {
        this.this$0 = controlsUiControllerImpl;
        this.$parent = viewGroup;
    }

    public final void onAnimationEnd(Animator animator) {
        this.this$0.controlViewsById.clear();
        this.this$0.controlsById.clear();
        ControlsUiControllerImpl controlsUiControllerImpl = this.this$0;
        ViewGroup viewGroup = this.$parent;
        Runnable runnable = controlsUiControllerImpl.onDismiss;
        Context context = null;
        if (runnable == null) {
            runnable = null;
        }
        Context context2 = controlsUiControllerImpl.activityContext;
        if (context2 != null) {
            context = context2;
        }
        controlsUiControllerImpl.show(viewGroup, runnable, context);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.$parent, "alpha", new float[]{0.0f, 1.0f});
        ofFloat.setInterpolator(new DecelerateInterpolator(1.0f));
        ofFloat.setDuration(200);
        ofFloat.start();
    }
}
