package com.android.systemui.controls.p004ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import java.util.Objects;

/* renamed from: com.android.systemui.controls.ui.ControlViewHolder$animateStatusChange$2$1 */
/* compiled from: ControlViewHolder.kt */
public final class ControlViewHolder$animateStatusChange$2$1 extends AnimatorListenerAdapter {
    public final /* synthetic */ ControlViewHolder this$0;

    public ControlViewHolder$animateStatusChange$2$1(ControlViewHolder controlViewHolder) {
        this.this$0 = controlViewHolder;
    }

    public final void onAnimationEnd(Animator animator) {
        ControlViewHolder controlViewHolder = this.this$0;
        Objects.requireNonNull(controlViewHolder);
        controlViewHolder.status.setAlpha(1.0f);
        this.this$0.statusAnimator = null;
    }
}
