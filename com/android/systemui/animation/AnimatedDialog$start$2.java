package com.android.systemui.animation;

import android.view.View;
import android.view.ViewGroup;

/* compiled from: DialogLaunchAnimator.kt */
public final class AnimatedDialog$start$2 implements View.OnLayoutChangeListener {
    public final /* synthetic */ ViewGroup $dialogContentWithBackground;
    public final /* synthetic */ AnimatedDialog this$0;

    public AnimatedDialog$start$2(ViewGroup viewGroup, AnimatedDialog animatedDialog) {
        this.$dialogContentWithBackground = viewGroup;
        this.this$0 = animatedDialog;
    }

    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this.$dialogContentWithBackground.removeOnLayoutChangeListener(this);
        AnimatedDialog animatedDialog = this.this$0;
        animatedDialog.isOriginalDialogViewLaidOut = true;
        AnimatedDialog.access$maybeStartLaunchAnimation(animatedDialog);
    }
}
