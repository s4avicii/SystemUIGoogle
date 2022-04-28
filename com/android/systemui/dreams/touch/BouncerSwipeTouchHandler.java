package com.android.systemui.dreams.touch;

import android.animation.ValueAnimator;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import com.android.p012wm.shell.animation.FlingAnimationUtils;
import com.android.systemui.dreams.touch.DreamOverlayTouchMonitor;
import com.android.systemui.dreams.touch.DreamTouchHandler;
import com.android.systemui.dreams.touch.dagger.BouncerSwipeModule$$ExternalSyntheticLambda0;
import com.android.systemui.dreams.touch.dagger.BouncerSwipeModule$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import java.util.Objects;

public final class BouncerSwipeTouchHandler implements DreamTouchHandler {
    public final float mBouncerZoneScreenPercentage;
    public Boolean mCapture;
    public float mCurrentExpansion;
    public final FlingAnimationUtils mFlingAnimationUtils;
    public final FlingAnimationUtils mFlingAnimationUtilsClosing;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final C08011 mOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        public boolean mBouncerPresent;
        public boolean mTrack;

        public final boolean onDown(MotionEvent motionEvent) {
            float f;
            boolean z;
            StatusBar statusBar = BouncerSwipeTouchHandler.this.mStatusBar;
            Objects.requireNonNull(statusBar);
            float f2 = (float) statusBar.mDisplayMetrics.heightPixels;
            StatusBar statusBar2 = BouncerSwipeTouchHandler.this.mStatusBar;
            Objects.requireNonNull(statusBar2);
            this.mBouncerPresent = statusBar2.mBouncerShowing;
            float y = motionEvent.getY();
            if (this.mBouncerPresent) {
                f = 0.0f;
            } else {
                f = f2;
            }
            if (Math.abs(y - f) / f2 < BouncerSwipeTouchHandler.this.mBouncerZoneScreenPercentage) {
                z = true;
            } else {
                z = false;
            }
            this.mTrack = z;
            return false;
        }

        public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            boolean z;
            if (!this.mTrack) {
                return false;
            }
            BouncerSwipeTouchHandler bouncerSwipeTouchHandler = BouncerSwipeTouchHandler.this;
            if (bouncerSwipeTouchHandler.mCapture == null) {
                if (Math.abs(f2) > Math.abs(f)) {
                    z = true;
                } else {
                    z = false;
                }
                bouncerSwipeTouchHandler.mCapture = Boolean.valueOf(z);
                if (BouncerSwipeTouchHandler.this.mCapture.booleanValue()) {
                    BouncerSwipeTouchHandler.this.mStatusBarKeyguardViewManager.showBouncer(false);
                }
            }
            if (!BouncerSwipeTouchHandler.this.mCapture.booleanValue()) {
                return false;
            }
            float y = motionEvent.getY() - motionEvent2.getY();
            StatusBar statusBar = BouncerSwipeTouchHandler.this.mStatusBar;
            Objects.requireNonNull(statusBar);
            float abs = Math.abs(y / ((float) statusBar.mDisplayMetrics.heightPixels));
            BouncerSwipeTouchHandler bouncerSwipeTouchHandler2 = BouncerSwipeTouchHandler.this;
            if (!this.mBouncerPresent) {
                abs = 1.0f - abs;
            }
            Objects.requireNonNull(bouncerSwipeTouchHandler2);
            bouncerSwipeTouchHandler2.mCurrentExpansion = abs;
            bouncerSwipeTouchHandler2.mStatusBarKeyguardViewManager.onPanelExpansionChanged(abs, false, true);
            return true;
        }
    };
    public final StatusBar mStatusBar;
    public StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public DreamTouchHandler.TouchSession mTouchSession;
    public ValueAnimatorCreator mValueAnimatorCreator;
    public VelocityTracker mVelocityTracker;
    public VelocityTrackerFactory mVelocityTrackerFactory;

    public interface ValueAnimatorCreator {
        ValueAnimator create(float f, float f2);
    }

    public interface VelocityTrackerFactory {
        VelocityTracker obtain();
    }

    public BouncerSwipeTouchHandler(StatusBarKeyguardViewManager statusBarKeyguardViewManager, StatusBar statusBar, NotificationShadeWindowController notificationShadeWindowController, FlingAnimationUtils flingAnimationUtils, FlingAnimationUtils flingAnimationUtils2, float f) {
        BouncerSwipeModule$$ExternalSyntheticLambda0 bouncerSwipeModule$$ExternalSyntheticLambda0 = BouncerSwipeModule$$ExternalSyntheticLambda0.INSTANCE;
        BouncerSwipeModule$$ExternalSyntheticLambda1 bouncerSwipeModule$$ExternalSyntheticLambda1 = BouncerSwipeModule$$ExternalSyntheticLambda1.INSTANCE;
        this.mStatusBar = statusBar;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mBouncerZoneScreenPercentage = f;
        this.mFlingAnimationUtils = flingAnimationUtils;
        this.mFlingAnimationUtilsClosing = flingAnimationUtils2;
        this.mValueAnimatorCreator = bouncerSwipeModule$$ExternalSyntheticLambda0;
        this.mVelocityTrackerFactory = bouncerSwipeModule$$ExternalSyntheticLambda1;
    }

    public final void onSessionStart(DreamOverlayTouchMonitor.TouchSessionImpl touchSessionImpl) {
        VelocityTracker obtain = this.mVelocityTrackerFactory.obtain();
        this.mVelocityTracker = obtain;
        this.mTouchSession = touchSessionImpl;
        obtain.clear();
        this.mNotificationShadeWindowController.setForcePluginOpen(true, this);
        touchSessionImpl.mGestureListeners.add(this.mOnGestureListener);
        touchSessionImpl.mEventListeners.add(new BouncerSwipeTouchHandler$$ExternalSyntheticLambda0(this));
    }
}
