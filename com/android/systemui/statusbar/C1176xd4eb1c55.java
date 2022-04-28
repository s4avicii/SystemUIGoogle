package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

/* renamed from: com.android.systemui.statusbar.NotificationShadeDepthController$keyguardStateCallback$1$onKeyguardFadingAwayChanged$1$2 */
/* compiled from: NotificationShadeDepthController.kt */
public final class C1176xd4eb1c55 extends AnimatorListenerAdapter {
    public final /* synthetic */ NotificationShadeDepthController this$0;

    public C1176xd4eb1c55(NotificationShadeDepthController notificationShadeDepthController) {
        this.this$0 = notificationShadeDepthController;
    }

    public final void onAnimationEnd(Animator animator) {
        NotificationShadeDepthController notificationShadeDepthController = this.this$0;
        notificationShadeDepthController.keyguardAnimator = null;
        notificationShadeDepthController.scheduleUpdate((View) null);
    }
}
