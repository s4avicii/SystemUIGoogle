package com.android.systemui.statusbar.phone;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.View;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.keyguard.KeyguardVisibilityHelper$$ExternalSyntheticLambda0;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.statusbar.LightRevealScrim;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.notification.AnimatableProperty;
import com.android.systemui.statusbar.notification.PropertyAnimator;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.settings.GlobalSettings;
import dagger.Lazy;
import java.util.HashSet;
import java.util.Objects;

/* compiled from: UnlockedScreenOffAnimationController.kt */
public final class UnlockedScreenOffAnimationController implements WakefulnessLifecycle.Observer, ScreenOffAnimation {
    public float animatorDurationScale = 1.0f;
    public final C1594x7f4c478e animatorDurationScaleObserver;
    public boolean aodUiAnimationPlaying;
    public HashSet<Callback> callbacks = new HashSet<>();
    public final Context context;
    public Boolean decidedToAnimateGoingToSleep;
    public final Lazy<DozeParameters> dozeParameters;
    public final GlobalSettings globalSettings;
    public final Handler handler;
    public final InteractionJankMonitor interactionJankMonitor;
    public final KeyguardStateController keyguardStateController;
    public final Lazy<KeyguardViewMediator> keyguardViewMediatorLazy;
    public boolean lightRevealAnimationPlaying;
    public final ValueAnimator lightRevealAnimator;
    public LightRevealScrim lightRevealScrim;
    public final PowerManager powerManager;
    public boolean shouldAnimateInKeyguard;
    public StatusBar statusBar;
    public final StatusBarStateControllerImpl statusBarStateControllerImpl;
    public final WakefulnessLifecycle wakefulnessLifecycle;

    /* compiled from: UnlockedScreenOffAnimationController.kt */
    public interface Callback {
        void onUnlockedScreenOffProgressUpdate(float f, float f2);
    }

    public final void animateInKeyguard(View view, KeyguardVisibilityHelper$$ExternalSyntheticLambda0 keyguardVisibilityHelper$$ExternalSyntheticLambda0) {
        this.shouldAnimateInKeyguard = false;
        view.setAlpha(0.0f);
        view.setVisibility(0);
        float y = view.getY();
        view.setY(y - (((float) view.getHeight()) * 0.1f));
        AnimatableProperty.C12227 r1 = AnimatableProperty.f80Y;
        ValueAnimator valueAnimator = (ValueAnimator) view.getTag(r1.getAnimatorTag());
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        AnimationProperties animationProperties = new AnimationProperties();
        long j = (long) 500;
        animationProperties.duration = j;
        PropertyAnimator.setProperty(view, r1, y, animationProperties, true);
        view.animate().setDuration(j).setInterpolator(Interpolators.FAST_OUT_SLOW_IN).alpha(1.0f).setListener(new UnlockedScreenOffAnimationController$animateInKeyguard$1(this, keyguardVisibilityHelper$$ExternalSyntheticLambda0, view)).start();
    }

    public final boolean isKeyguardHideDelayed() {
        return false;
    }

    public final void onAlwaysOnChanged(boolean z) {
    }

    public final void onFinishedWakingUp() {
        this.aodUiAnimationPlaying = false;
        if (this.dozeParameters.get().canControlUnlockedScreenOff()) {
            StatusBar statusBar2 = this.statusBar;
            if (statusBar2 == null) {
                statusBar2 = null;
            }
            statusBar2.updateIsKeyguard(true);
        }
    }

    public final void onScrimOpaqueChanged(boolean z) {
    }

    public final void onStartedWakingUp() {
        this.decidedToAnimateGoingToSleep = null;
        this.shouldAnimateInKeyguard = false;
        this.lightRevealAnimator.cancel();
        this.handler.removeCallbacksAndMessages((Object) null);
    }

    public final boolean shouldAnimateClockChange() {
        return true;
    }

    public final boolean shouldAnimateDozingChange() {
        return true;
    }

    public final void initialize(StatusBar statusBar2, LightRevealScrim lightRevealScrim2) {
        this.lightRevealScrim = lightRevealScrim2;
        this.statusBar = statusBar2;
        this.animatorDurationScale = this.globalSettings.getFloat();
        this.globalSettings.registerContentObserver(Settings.Global.getUriFor("animator_duration_scale"), false, this.animatorDurationScaleObserver);
        WakefulnessLifecycle wakefulnessLifecycle2 = this.wakefulnessLifecycle;
        Objects.requireNonNull(wakefulnessLifecycle2);
        wakefulnessLifecycle2.mObservers.add(this);
    }

    public final boolean isAnimationPlaying() {
        if (this.lightRevealAnimationPlaying || this.aodUiAnimationPlaying) {
            return true;
        }
        return false;
    }

    public final boolean shouldDelayDisplayDozeTransition() {
        DozeParameters dozeParameters2 = this.dozeParameters.get();
        Objects.requireNonNull(dozeParameters2);
        if (!dozeParameters2.canControlUnlockedScreenOff() || !dozeParameters2.mUnlockedScreenOffAnimationController.shouldPlayUnlockedScreenOffAnimation()) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003b, code lost:
        if (r0.mNotificationPanelViewController.isFullyCollapsed() == false) goto L_0x003d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean shouldPlayUnlockedScreenOffAnimation() {
        /*
            r3 = this;
            java.lang.Boolean r0 = r3.decidedToAnimateGoingToSleep
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r1)
            r1 = 0
            if (r0 == 0) goto L_0x000c
            return r1
        L_0x000c:
            android.content.Context r0 = r3.context
            android.content.ContentResolver r0 = r0.getContentResolver()
            java.lang.String r2 = "animator_duration_scale"
            java.lang.String r0 = android.provider.Settings.Global.getString(r0, r2)
            java.lang.String r2 = "0"
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r2)
            if (r0 == 0) goto L_0x0021
            return r1
        L_0x0021:
            com.android.systemui.statusbar.StatusBarStateControllerImpl r0 = r3.statusBarStateControllerImpl
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.mState
            if (r0 == 0) goto L_0x002b
            return r1
        L_0x002b:
            com.android.systemui.statusbar.phone.StatusBar r0 = r3.statusBar
            if (r0 == 0) goto L_0x003d
            if (r0 != 0) goto L_0x0032
            r0 = 0
        L_0x0032:
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.phone.NotificationPanelViewController r0 = r0.mNotificationPanelViewController
            boolean r0 = r0.isFullyCollapsed()
            if (r0 != 0) goto L_0x0044
        L_0x003d:
            boolean r0 = r3.isAnimationPlaying()
            if (r0 != 0) goto L_0x0044
            return r1
        L_0x0044:
            com.android.systemui.statusbar.policy.KeyguardStateController r0 = r3.keyguardStateController
            boolean r0 = r0.isKeyguardScreenRotationAllowed()
            if (r0 != 0) goto L_0x0059
            android.content.Context r3 = r3.context
            android.view.Display r3 = r3.getDisplay()
            int r3 = r3.getRotation()
            if (r3 == 0) goto L_0x0059
            return r1
        L_0x0059:
            r3 = 1
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController.shouldPlayUnlockedScreenOffAnimation():boolean");
    }

    public UnlockedScreenOffAnimationController(Context context2, WakefulnessLifecycle wakefulnessLifecycle2, StatusBarStateControllerImpl statusBarStateControllerImpl2, Lazy<KeyguardViewMediator> lazy, KeyguardStateController keyguardStateController2, Lazy<DozeParameters> lazy2, GlobalSettings globalSettings2, InteractionJankMonitor interactionJankMonitor2, PowerManager powerManager2, Handler handler2) {
        this.context = context2;
        this.wakefulnessLifecycle = wakefulnessLifecycle2;
        this.statusBarStateControllerImpl = statusBarStateControllerImpl2;
        this.keyguardViewMediatorLazy = lazy;
        this.keyguardStateController = keyguardStateController2;
        this.dozeParameters = lazy2;
        this.globalSettings = globalSettings2;
        this.interactionJankMonitor = interactionJankMonitor2;
        this.powerManager = powerManager2;
        this.handler = handler2;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
        ofFloat.setDuration(750);
        ofFloat.setInterpolator(Interpolators.LINEAR);
        ofFloat.addUpdateListener(new UnlockedScreenOffAnimationController$lightRevealAnimator$1$1(this));
        ofFloat.addListener(new UnlockedScreenOffAnimationController$lightRevealAnimator$1$2(this));
        this.lightRevealAnimator = ofFloat;
        this.animatorDurationScaleObserver = new C1594x7f4c478e(this);
    }

    public final boolean isKeyguardShowDelayed() {
        return isAnimationPlaying();
    }

    public final boolean overrideNotificationsDozeAmount() {
        if (!shouldPlayUnlockedScreenOffAnimation() || !isAnimationPlaying()) {
            return false;
        }
        return true;
    }

    public final boolean shouldAnimateAodIcons() {
        return shouldPlayUnlockedScreenOffAnimation();
    }

    public final boolean shouldDelayKeyguardShow() {
        return shouldPlayUnlockedScreenOffAnimation();
    }

    public final boolean shouldPlayAnimation() {
        return shouldPlayUnlockedScreenOffAnimation();
    }

    public final boolean shouldShowAodIconsWhenShade() {
        return isAnimationPlaying();
    }

    public final boolean startAnimation() {
        if (shouldPlayUnlockedScreenOffAnimation()) {
            this.decidedToAnimateGoingToSleep = Boolean.TRUE;
            this.shouldAnimateInKeyguard = true;
            this.lightRevealAnimationPlaying = true;
            this.lightRevealAnimator.start();
            this.handler.postDelayed(new UnlockedScreenOffAnimationController$startAnimation$1(this), (long) (((float) 600) * this.animatorDurationScale));
            return true;
        }
        this.decidedToAnimateGoingToSleep = Boolean.FALSE;
        return false;
    }

    public final boolean shouldAnimateInKeyguard() {
        return this.shouldAnimateInKeyguard;
    }

    public final boolean shouldHideScrimOnWakeUp() {
        return this.lightRevealAnimationPlaying;
    }
}
