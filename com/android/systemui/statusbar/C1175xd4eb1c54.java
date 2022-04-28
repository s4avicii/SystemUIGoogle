package com.android.systemui.statusbar;

import android.animation.ValueAnimator;
import android.view.View;
import java.util.Objects;

/* renamed from: com.android.systemui.statusbar.NotificationShadeDepthController$keyguardStateCallback$1$onKeyguardFadingAwayChanged$1$1 */
/* compiled from: NotificationShadeDepthController.kt */
public final class C1175xd4eb1c54 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ NotificationShadeDepthController this$0;

    public C1175xd4eb1c54(NotificationShadeDepthController notificationShadeDepthController) {
        this.this$0 = notificationShadeDepthController;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        boolean z;
        NotificationShadeDepthController notificationShadeDepthController = this.this$0;
        BlurUtils blurUtils = notificationShadeDepthController.blurUtils;
        Object animatedValue = valueAnimator.getAnimatedValue();
        Objects.requireNonNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        float blurRadiusOfRatio = blurUtils.blurRadiusOfRatio(((Float) animatedValue).floatValue());
        if (notificationShadeDepthController.wakeAndUnlockBlurRadius == blurRadiusOfRatio) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            notificationShadeDepthController.wakeAndUnlockBlurRadius = blurRadiusOfRatio;
            notificationShadeDepthController.scheduleUpdate((View) null);
        }
    }
}
