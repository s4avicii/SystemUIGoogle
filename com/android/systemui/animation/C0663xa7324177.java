package com.android.systemui.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import com.android.systemui.animation.AnimatedDialog;

/* renamed from: com.android.systemui.animation.AnimatedDialog$AnimatedBoundsLayoutListener$onLayoutChange$animator$1$1 */
/* compiled from: DialogLaunchAnimator.kt */
public final class C0663xa7324177 extends AnimatorListenerAdapter {
    public final /* synthetic */ AnimatedDialog.AnimatedBoundsLayoutListener this$0;

    public C0663xa7324177(AnimatedDialog.AnimatedBoundsLayoutListener animatedBoundsLayoutListener) {
        this.this$0 = animatedBoundsLayoutListener;
    }

    public final void onAnimationEnd(Animator animator) {
        this.this$0.currentAnimator = null;
    }
}
