package com.android.systemui.dreams.touch;

import android.animation.ValueAnimator;
import android.util.Log;
import android.view.InputEvent;
import android.view.MotionEvent;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.android.p012wm.shell.animation.FlingAnimationUtils;
import com.android.systemui.dreams.touch.DreamOverlayTouchMonitor;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.statusbar.StatusBarIconView$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.StatusBar;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BouncerSwipeTouchHandler$$ExternalSyntheticLambda0 implements InputChannelCompat$InputEventListener {
    public final /* synthetic */ BouncerSwipeTouchHandler f$0;

    public /* synthetic */ BouncerSwipeTouchHandler$$ExternalSyntheticLambda0(BouncerSwipeTouchHandler bouncerSwipeTouchHandler) {
        this.f$0 = bouncerSwipeTouchHandler;
    }

    public final void onInputEvent(InputEvent inputEvent) {
        boolean z;
        ValueAnimator valueAnimator;
        InputEvent inputEvent2 = inputEvent;
        BouncerSwipeTouchHandler bouncerSwipeTouchHandler = this.f$0;
        Objects.requireNonNull(bouncerSwipeTouchHandler);
        if (!(inputEvent2 instanceof MotionEvent)) {
            Log.e("BouncerSwipeTouchHandler", "non MotionEvent received:" + inputEvent2);
            return;
        }
        MotionEvent motionEvent = (MotionEvent) inputEvent2;
        if (motionEvent.getAction() != 1) {
            bouncerSwipeTouchHandler.mVelocityTracker.addMovement(motionEvent);
            return;
        }
        Boolean bool = bouncerSwipeTouchHandler.mCapture;
        if (bool != null && bool.booleanValue()) {
            bouncerSwipeTouchHandler.mVelocityTracker.computeCurrentVelocity(1000);
            float yVelocity = bouncerSwipeTouchHandler.mVelocityTracker.getYVelocity();
            float abs = Math.abs((float) Math.hypot((double) bouncerSwipeTouchHandler.mVelocityTracker.getXVelocity(), (double) yVelocity));
            FlingAnimationUtils flingAnimationUtils = bouncerSwipeTouchHandler.mFlingAnimationUtils;
            Objects.requireNonNull(flingAnimationUtils);
            int i = (abs > flingAnimationUtils.mMinVelocityPxPerSecond ? 1 : (abs == flingAnimationUtils.mMinVelocityPxPerSecond ? 0 : -1));
            float f = 0.0f;
            if (i >= 0 ? yVelocity <= 0.0f : bouncerSwipeTouchHandler.mCurrentExpansion <= 0.5f) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                f = 1.0f;
            }
            StatusBar statusBar = bouncerSwipeTouchHandler.mStatusBar;
            Objects.requireNonNull(statusBar);
            float f2 = (float) statusBar.mDisplayMetrics.heightPixels;
            float f3 = bouncerSwipeTouchHandler.mCurrentExpansion;
            float f4 = f2 * f3;
            float f5 = f2 * f;
            ValueAnimator create = bouncerSwipeTouchHandler.mValueAnimatorCreator.create(f3, f);
            create.addUpdateListener(new StatusBarIconView$$ExternalSyntheticLambda0(bouncerSwipeTouchHandler, 1));
            int i2 = (f > 1.0f ? 1 : (f == 1.0f ? 0 : -1));
            if (i2 == 0) {
                bouncerSwipeTouchHandler.mFlingAnimationUtils.apply(create, f4, f5, yVelocity, f2);
                valueAnimator = create;
            } else {
                valueAnimator = create;
                bouncerSwipeTouchHandler.mFlingAnimationUtilsClosing.apply(create, bouncerSwipeTouchHandler.mCurrentExpansion, f4, f5, f2);
            }
            valueAnimator.start();
            if (i2 == 0) {
                bouncerSwipeTouchHandler.mStatusBarKeyguardViewManager.reset(false);
            }
            DreamOverlayTouchMonitor.TouchSessionImpl touchSessionImpl = (DreamOverlayTouchMonitor.TouchSessionImpl) bouncerSwipeTouchHandler.mTouchSession;
            Objects.requireNonNull(touchSessionImpl);
            DreamOverlayTouchMonitor dreamOverlayTouchMonitor = touchSessionImpl.mTouchMonitor;
            Objects.requireNonNull(dreamOverlayTouchMonitor);
            CallbackToFutureAdapter.getFuture(new DreamOverlayTouchMonitor$$ExternalSyntheticLambda0(dreamOverlayTouchMonitor, touchSessionImpl));
        }
    }
}
