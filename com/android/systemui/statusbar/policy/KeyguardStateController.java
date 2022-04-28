package com.android.systemui.statusbar.policy;

public interface KeyguardStateController extends CallbackController<Callback> {

    public interface Callback {
        void onKeyguardDismissAmountChanged() {
        }

        void onKeyguardFadingAwayChanged() {
        }

        void onKeyguardShowingChanged() {
        }

        void onLaunchTransitionFadingAwayChanged() {
        }

        void onUnlockedChanged() {
        }
    }

    long calculateGoingToFullShadeDelay();

    boolean canDismissLockScreen();

    float getDismissAmount();

    long getKeyguardFadingAwayDelay();

    long getKeyguardFadingAwayDuration();

    boolean isBypassFadingAnimation() {
        return false;
    }

    boolean isDismissingFromSwipe();

    boolean isFaceAuthEnabled() {
        return false;
    }

    boolean isFlingingToDismissKeyguard();

    boolean isFlingingToDismissKeyguardDuringSwipeGesture();

    boolean isKeyguardFadingAway();

    boolean isKeyguardGoingAway();

    boolean isKeyguardScreenRotationAllowed();

    boolean isLaunchTransitionFadingAway();

    boolean isMethodSecure();

    boolean isOccluded();

    boolean isShowing();

    boolean isSnappingKeyguardBackAfterSwipe();

    void notifyKeyguardDismissAmountChanged(float f, boolean z) {
    }

    void notifyKeyguardDoneFading() {
    }

    void notifyKeyguardFadingAway(long j, long j2, boolean z) {
    }

    void notifyKeyguardGoingAway() {
    }

    void notifyKeyguardState(boolean z, boolean z2) {
    }

    void notifyPanelFlingEnd();

    void notifyPanelFlingStart(boolean z);

    void setLaunchTransitionFadingAway(boolean z) {
    }

    long getShortenedFadingAwayDuration() {
        if (isBypassFadingAnimation()) {
            return getKeyguardFadingAwayDuration();
        }
        return getKeyguardFadingAwayDuration() / 2;
    }

    boolean isUnlocked() {
        if (!isShowing() || canDismissLockScreen()) {
            return true;
        }
        return false;
    }
}
