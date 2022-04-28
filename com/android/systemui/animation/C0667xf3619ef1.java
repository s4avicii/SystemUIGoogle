package com.android.systemui.animation;

/* renamed from: com.android.systemui.animation.DialogLaunchAnimator$createActivityLaunchController$1$enableDialogDismiss$1 */
/* compiled from: DialogLaunchAnimator.kt */
public final /* synthetic */ class C0667xf3619ef1 implements Runnable {
    public final /* synthetic */ AnimatedDialog $tmp0;

    public C0667xf3619ef1(AnimatedDialog animatedDialog) {
        this.$tmp0 = animatedDialog;
    }

    public final void run() {
        this.$tmp0.onDialogDismissed();
    }
}
