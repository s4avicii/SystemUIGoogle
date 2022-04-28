package com.android.systemui.unfold;

import android.os.Handler;
import android.view.View;
import com.android.keyguard.KeyguardVisibilityHelper$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.statusbar.LightRevealScrim;
import com.android.systemui.statusbar.phone.NotificationPanelViewController;
import com.android.systemui.statusbar.phone.ScreenOffAnimation;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.util.settings.GlobalSettings;
import java.util.ArrayList;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FoldAodAnimationController.kt */
public final class FoldAodAnimationController implements CallbackController<FoldAodAnimationStatus>, ScreenOffAnimation, WakefulnessLifecycle.Observer {
    public boolean alwaysOnEnabled;
    public final GlobalSettings globalSettings;
    public final Handler handler;
    public boolean isAnimationPlaying;
    public boolean isScrimOpaque;
    public Runnable pendingScrimReadyCallback;
    public boolean shouldPlayAnimation;
    public final FoldAodAnimationController$startAnimationRunnable$1 startAnimationRunnable = new FoldAodAnimationController$startAnimationRunnable$1(this);
    public StatusBar statusBar;
    public final ArrayList<FoldAodAnimationStatus> statusListeners = new ArrayList<>();
    public final WakefulnessLifecycle wakefulnessLifecycle;

    /* compiled from: FoldAodAnimationController.kt */
    public interface FoldAodAnimationStatus {
        void onFoldToAodAnimationChanged();
    }

    public final boolean isKeyguardShowDelayed() {
        return false;
    }

    public final boolean overrideNotificationsDozeAmount() {
        return false;
    }

    public final boolean shouldAnimateInKeyguard() {
        return false;
    }

    public final boolean shouldDelayKeyguardShow() {
        return false;
    }

    public final boolean shouldHideScrimOnWakeUp() {
        return false;
    }

    public final void addCallback(Object obj) {
        this.statusListeners.add((FoldAodAnimationStatus) obj);
    }

    public final void initialize(StatusBar statusBar2, LightRevealScrim lightRevealScrim) {
        this.statusBar = statusBar2;
        WakefulnessLifecycle wakefulnessLifecycle2 = this.wakefulnessLifecycle;
        Objects.requireNonNull(wakefulnessLifecycle2);
        wakefulnessLifecycle2.mObservers.add(this);
    }

    public final void onScrimOpaqueChanged(boolean z) {
        this.isScrimOpaque = z;
        if (z) {
            Runnable runnable = this.pendingScrimReadyCallback;
            if (runnable != null) {
                runnable.run();
            }
            this.pendingScrimReadyCallback = null;
        }
    }

    public final void onStartedWakingUp() {
        if (this.isAnimationPlaying) {
            this.handler.removeCallbacks(this.startAnimationRunnable);
            StatusBar statusBar2 = this.statusBar;
            if (statusBar2 == null) {
                statusBar2 = null;
            }
            Objects.requireNonNull(statusBar2);
            NotificationPanelViewController notificationPanelViewController = statusBar2.mNotificationPanelViewController;
            Objects.requireNonNull(notificationPanelViewController);
            notificationPanelViewController.mView.animate().cancel();
            notificationPanelViewController.mView.setAlpha(1.0f);
            notificationPanelViewController.mView.setTranslationX(0.0f);
        }
        setAnimationState(false);
    }

    public final void removeCallback(Object obj) {
        this.statusListeners.remove((FoldAodAnimationStatus) obj);
    }

    public final void setAnimationState(boolean z) {
        this.shouldPlayAnimation = z;
        this.isAnimationPlaying = z;
        for (FoldAodAnimationStatus onFoldToAodAnimationChanged : this.statusListeners) {
            onFoldToAodAnimationChanged.onFoldToAodAnimationChanged();
        }
    }

    public final boolean shouldAnimateAodIcons() {
        return !this.shouldPlayAnimation;
    }

    public final boolean shouldAnimateClockChange() {
        return !this.isAnimationPlaying;
    }

    public final boolean shouldAnimateDozingChange() {
        return !this.shouldPlayAnimation;
    }

    public final boolean startAnimation() {
        if (this.alwaysOnEnabled) {
            WakefulnessLifecycle wakefulnessLifecycle2 = this.wakefulnessLifecycle;
            Objects.requireNonNull(wakefulnessLifecycle2);
            if (wakefulnessLifecycle2.mLastSleepReason == 13 && !Intrinsics.areEqual(this.globalSettings.getString("animator_duration_scale"), "0")) {
                setAnimationState(true);
                StatusBar statusBar2 = this.statusBar;
                if (statusBar2 == null) {
                    statusBar2 = null;
                }
                Objects.requireNonNull(statusBar2);
                NotificationPanelViewController notificationPanelViewController = statusBar2.mNotificationPanelViewController;
                Objects.requireNonNull(notificationPanelViewController);
                notificationPanelViewController.showAodUi();
                notificationPanelViewController.mView.setTranslationX((float) (-notificationPanelViewController.mView.getResources().getDimensionPixelSize(C1777R.dimen.below_clock_padding_start)));
                notificationPanelViewController.mView.setAlpha(0.0f);
                return true;
            }
        }
        setAnimationState(false);
        return false;
    }

    public FoldAodAnimationController(Handler handler2, WakefulnessLifecycle wakefulnessLifecycle2, GlobalSettings globalSettings2) {
        this.handler = handler2;
        this.wakefulnessLifecycle = wakefulnessLifecycle2;
        this.globalSettings = globalSettings2;
    }

    public final void onAlwaysOnChanged(boolean z) {
        this.alwaysOnEnabled = z;
    }

    public final void animateInKeyguard(View view, KeyguardVisibilityHelper$$ExternalSyntheticLambda0 keyguardVisibilityHelper$$ExternalSyntheticLambda0) {
        keyguardVisibilityHelper$$ExternalSyntheticLambda0.run();
    }

    public final boolean isAnimationPlaying() {
        return this.isAnimationPlaying;
    }

    public final boolean isKeyguardHideDelayed() {
        return this.isAnimationPlaying;
    }

    public final boolean shouldDelayDisplayDozeTransition() {
        return this.shouldPlayAnimation;
    }

    public final boolean shouldPlayAnimation() {
        return this.shouldPlayAnimation;
    }

    public final boolean shouldShowAodIconsWhenShade() {
        return this.shouldPlayAnimation;
    }
}
