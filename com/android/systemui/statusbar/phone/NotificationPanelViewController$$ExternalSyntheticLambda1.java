package com.android.systemui.statusbar.phone;

import android.animation.ValueAnimator;
import android.util.MathUtils;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ NotificationPanelViewController f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ int f$4;
    public final /* synthetic */ boolean f$5;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda1(NotificationPanelViewController notificationPanelViewController, int i, int i2, int i3, int i4, boolean z) {
        this.f$0 = notificationPanelViewController;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = i3;
        this.f$4 = i4;
        this.f$5 = z;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        NotificationPanelViewController notificationPanelViewController = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        int i3 = this.f$3;
        int i4 = this.f$4;
        boolean z = this.f$5;
        Objects.requireNonNull(notificationPanelViewController);
        float animatedFraction = valueAnimator.getAnimatedFraction();
        notificationPanelViewController.applyQSClippingImmediately((int) MathUtils.lerp(i, notificationPanelViewController.mQsClippingAnimationEndBounds.left, animatedFraction), (int) MathUtils.lerp(i2, notificationPanelViewController.mQsClippingAnimationEndBounds.top, animatedFraction), (int) MathUtils.lerp(i3, notificationPanelViewController.mQsClippingAnimationEndBounds.right, animatedFraction), (int) MathUtils.lerp(i4, notificationPanelViewController.mQsClippingAnimationEndBounds.bottom, animatedFraction), z);
    }
}
