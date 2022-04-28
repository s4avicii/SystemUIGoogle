package com.android.systemui.statusbar.phone;

import android.animation.ValueAnimator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ NotificationPanelViewController f$0;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda0(NotificationPanelViewController notificationPanelViewController) {
        this.f$0 = notificationPanelViewController;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        NotificationPanelViewController notificationPanelViewController = this.f$0;
        Objects.requireNonNull(notificationPanelViewController);
        notificationPanelViewController.mBottomAreaShadeAlpha = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        notificationPanelViewController.updateKeyguardBottomAreaAlpha();
    }
}
