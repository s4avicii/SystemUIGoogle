package com.android.systemui.controls.p004ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/* renamed from: com.android.systemui.controls.ui.ControlViewHolder$startBackgroundAnimation$1$2 */
/* compiled from: ControlViewHolder.kt */
public final class ControlViewHolder$startBackgroundAnimation$1$2 extends AnimatorListenerAdapter {
    public final /* synthetic */ ControlViewHolder this$0;

    public ControlViewHolder$startBackgroundAnimation$1$2(ControlViewHolder controlViewHolder) {
        this.this$0 = controlViewHolder;
    }

    public final void onAnimationEnd(Animator animator) {
        this.this$0.stateAnimator = null;
    }
}
