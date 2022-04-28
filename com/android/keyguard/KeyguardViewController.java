package com.android.keyguard;

import android.view.ViewRootImpl;
import com.android.systemui.p006qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda0;

public interface KeyguardViewController {
    void blockPanelExpansionFromCurrentTouch();

    boolean bouncerIsOrWillBeShowing();

    void dismissAndCollapse();

    ViewRootImpl getViewRootImpl();

    void hide(long j, long j2);

    boolean isBouncerShowing();

    boolean isGoingToNotificationShade();

    boolean isShowing();

    boolean isUnlockWithWallpaper();

    void keyguardGoingAway();

    void notifyKeyguardAuthenticated();

    void onCancelClicked();

    void onFinishedGoingToSleep() {
    }

    void onStartedGoingToSleep() {
    }

    void onStartedWakingUp() {
    }

    void reset(boolean z);

    void setKeyguardGoingAwayState(boolean z);

    void setNeedsInput(boolean z);

    void setOccluded(boolean z, boolean z2);

    boolean shouldDisableWindowAnimationsForUnlock();

    boolean shouldSubtleWindowAnimationsForUnlock();

    void show$2();

    void showBouncer(boolean z);

    void startPreHideAnimation(QSTileImpl$$ExternalSyntheticLambda0 qSTileImpl$$ExternalSyntheticLambda0);
}
