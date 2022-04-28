package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.ValueAnimator;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.Objects;

/* compiled from: NotificationShadeDepthController.kt */
public final class NotificationShadeDepthController$keyguardStateCallback$1 implements KeyguardStateController.Callback {
    public final /* synthetic */ NotificationShadeDepthController this$0;

    public NotificationShadeDepthController$keyguardStateCallback$1(NotificationShadeDepthController notificationShadeDepthController) {
        this.this$0 = notificationShadeDepthController;
    }

    public final void onKeyguardFadingAwayChanged() {
        if (this.this$0.keyguardStateController.isKeyguardFadingAway()) {
            BiometricUnlockController biometricUnlockController = this.this$0.biometricUnlockController;
            Objects.requireNonNull(biometricUnlockController);
            if (biometricUnlockController.mMode == 1) {
                Animator animator = this.this$0.keyguardAnimator;
                if (animator != null) {
                    animator.cancel();
                }
                NotificationShadeDepthController notificationShadeDepthController = this.this$0;
                ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
                NotificationShadeDepthController notificationShadeDepthController2 = this.this$0;
                DozeParameters dozeParameters = notificationShadeDepthController2.dozeParameters;
                Objects.requireNonNull(dozeParameters);
                ofFloat.setDuration(dozeParameters.mAlwaysOnPolicy.wallpaperFadeOutDuration);
                ofFloat.setStartDelay(notificationShadeDepthController2.keyguardStateController.getKeyguardFadingAwayDelay());
                ofFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                ofFloat.addUpdateListener(new C1175xd4eb1c54(notificationShadeDepthController2));
                ofFloat.addListener(new C1176xd4eb1c55(notificationShadeDepthController2));
                ofFloat.start();
                notificationShadeDepthController.keyguardAnimator = ofFloat;
            }
        }
    }

    public final void onKeyguardShowingChanged() {
        if (this.this$0.keyguardStateController.isShowing()) {
            Animator animator = this.this$0.keyguardAnimator;
            if (animator != null) {
                animator.cancel();
            }
            Objects.requireNonNull(this.this$0);
        }
    }
}
