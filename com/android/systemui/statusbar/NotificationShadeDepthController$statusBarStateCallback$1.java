package com.android.systemui.statusbar;

import android.view.View;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import java.util.Objects;

/* compiled from: NotificationShadeDepthController.kt */
public final class NotificationShadeDepthController$statusBarStateCallback$1 implements StatusBarStateController.StateListener {
    public final /* synthetic */ NotificationShadeDepthController this$0;

    public NotificationShadeDepthController$statusBarStateCallback$1(NotificationShadeDepthController notificationShadeDepthController) {
        this.this$0 = notificationShadeDepthController;
    }

    public final void onDozeAmountChanged(float f, float f2) {
        boolean z;
        NotificationShadeDepthController notificationShadeDepthController = this.this$0;
        float blurRadiusOfRatio = notificationShadeDepthController.blurUtils.blurRadiusOfRatio(f2);
        if (notificationShadeDepthController.wakeAndUnlockBlurRadius == blurRadiusOfRatio) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            notificationShadeDepthController.wakeAndUnlockBlurRadius = blurRadiusOfRatio;
            notificationShadeDepthController.scheduleUpdate((View) null);
        }
        this.this$0.scheduleUpdate((View) null);
    }

    public final void onDozingChanged(boolean z) {
        if (z) {
            NotificationShadeDepthController notificationShadeDepthController = this.this$0;
            Objects.requireNonNull(notificationShadeDepthController);
            NotificationShadeDepthController.DepthAnimation depthAnimation = notificationShadeDepthController.shadeAnimation;
            Objects.requireNonNull(depthAnimation);
            SpringAnimation springAnimation = depthAnimation.springAnimation;
            Objects.requireNonNull(springAnimation);
            if (springAnimation.mRunning) {
                depthAnimation.springAnimation.skipToEnd();
            }
            NotificationShadeDepthController notificationShadeDepthController2 = this.this$0;
            Objects.requireNonNull(notificationShadeDepthController2);
            NotificationShadeDepthController.DepthAnimation depthAnimation2 = notificationShadeDepthController2.brightnessMirrorSpring;
            Objects.requireNonNull(depthAnimation2);
            SpringAnimation springAnimation2 = depthAnimation2.springAnimation;
            Objects.requireNonNull(springAnimation2);
            if (springAnimation2.mRunning) {
                depthAnimation2.springAnimation.skipToEnd();
            }
        }
    }

    public final void onStateChanged(int i) {
        NotificationShadeDepthController notificationShadeDepthController = this.this$0;
        Objects.requireNonNull(notificationShadeDepthController);
        float f = notificationShadeDepthController.shadeExpansion;
        NotificationShadeDepthController notificationShadeDepthController2 = this.this$0;
        notificationShadeDepthController.updateShadeAnimationBlur(f, notificationShadeDepthController2.prevTracking, notificationShadeDepthController2.prevShadeVelocity, notificationShadeDepthController2.prevShadeDirection);
        this.this$0.scheduleUpdate((View) null);
    }
}
