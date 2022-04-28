package com.android.systemui.accessibility;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.util.Log;
import android.view.accessibility.IRemoteMagnificationAnimationCallback;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.accessibility.WindowMagnificationAnimationController;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WindowMagnificationConnectionImpl$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ WindowMagnificationConnectionImpl f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ float f$2;
    public final /* synthetic */ float f$3;
    public final /* synthetic */ float f$4;
    public final /* synthetic */ float f$5;
    public final /* synthetic */ float f$6;
    public final /* synthetic */ IRemoteMagnificationAnimationCallback f$7;

    public /* synthetic */ WindowMagnificationConnectionImpl$$ExternalSyntheticLambda2(WindowMagnificationConnectionImpl windowMagnificationConnectionImpl, int i, float f, float f2, float f3, float f4, float f5, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) {
        this.f$0 = windowMagnificationConnectionImpl;
        this.f$1 = i;
        this.f$2 = f;
        this.f$3 = f2;
        this.f$4 = f3;
        this.f$5 = f4;
        this.f$6 = f5;
        this.f$7 = iRemoteMagnificationAnimationCallback;
    }

    public final void run() {
        float f;
        float f2;
        float f3;
        WindowMagnificationConnectionImpl windowMagnificationConnectionImpl = this.f$0;
        int i = this.f$1;
        float f4 = this.f$2;
        float f5 = this.f$3;
        float f6 = this.f$4;
        float f7 = this.f$5;
        float f8 = this.f$6;
        IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback = this.f$7;
        Objects.requireNonNull(windowMagnificationConnectionImpl);
        WindowMagnification windowMagnification = windowMagnificationConnectionImpl.mWindowMagnification;
        Objects.requireNonNull(windowMagnification);
        WindowMagnificationController windowMagnificationController = windowMagnification.mMagnificationControllerSupplier.get(i);
        if (windowMagnificationController != null) {
            WindowMagnificationAnimationController windowMagnificationAnimationController = windowMagnificationController.mAnimationController;
            Objects.requireNonNull(windowMagnificationAnimationController);
            if (windowMagnificationAnimationController.mController != null) {
                windowMagnificationAnimationController.sendAnimationCallback(false);
                windowMagnificationAnimationController.mMagnificationFrameOffsetRatioX = f7;
                windowMagnificationAnimationController.mMagnificationFrameOffsetRatioY = f8;
                if (iRemoteMagnificationAnimationCallback == null) {
                    int i2 = windowMagnificationAnimationController.mState;
                    if (i2 == 3 || i2 == 2) {
                        windowMagnificationAnimationController.mValueAnimator.cancel();
                    }
                    windowMagnificationAnimationController.mController.enableWindowMagnificationInternal(f4, f5, f6, windowMagnificationAnimationController.mMagnificationFrameOffsetRatioX, windowMagnificationAnimationController.mMagnificationFrameOffsetRatioY);
                    windowMagnificationAnimationController.setState(1);
                    return;
                }
                windowMagnificationAnimationController.mAnimationCallback = iRemoteMagnificationAnimationCallback;
                WindowMagnificationController windowMagnificationController2 = windowMagnificationAnimationController.mController;
                if (windowMagnificationController2 != null) {
                    float f9 = Float.NaN;
                    if (windowMagnificationController2.isWindowVisible()) {
                        f = windowMagnificationController2.mScale;
                    } else {
                        f = Float.NaN;
                    }
                    WindowMagnificationController windowMagnificationController3 = windowMagnificationAnimationController.mController;
                    Objects.requireNonNull(windowMagnificationController3);
                    if (windowMagnificationController3.isWindowVisible()) {
                        f2 = windowMagnificationController3.mMagnificationFrame.exactCenterX();
                    } else {
                        f2 = Float.NaN;
                    }
                    WindowMagnificationController windowMagnificationController4 = windowMagnificationAnimationController.mController;
                    Objects.requireNonNull(windowMagnificationController4);
                    if (windowMagnificationController4.isWindowVisible()) {
                        f9 = windowMagnificationController4.mMagnificationFrame.exactCenterY();
                    }
                    if (windowMagnificationAnimationController.mState == 0) {
                        windowMagnificationAnimationController.mStartSpec.set(1.0f, f5, f6);
                        WindowMagnificationAnimationController.AnimationSpec animationSpec = windowMagnificationAnimationController.mEndSpec;
                        if (Float.isNaN(f4)) {
                            f3 = (float) windowMagnificationAnimationController.mContext.getResources().getInteger(C1777R.integer.magnification_default_scale);
                        } else {
                            f3 = f4;
                        }
                        animationSpec.set(f3, f5, f6);
                    } else {
                        windowMagnificationAnimationController.mStartSpec.set(f, f2, f9);
                        WindowMagnificationAnimationController.AnimationSpec animationSpec2 = windowMagnificationAnimationController.mEndSpec;
                        if (!Float.isNaN(f4)) {
                            f = f4;
                        }
                        if (!Float.isNaN(f5)) {
                            f2 = f5;
                        }
                        if (!Float.isNaN(f6)) {
                            f9 = f6;
                        }
                        animationSpec2.set(f, f2, f9);
                    }
                    if (WindowMagnificationAnimationController.DEBUG) {
                        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("SetupEnableAnimationSpecs : mStartSpec = ");
                        m.append(windowMagnificationAnimationController.mStartSpec);
                        m.append(", endSpec = ");
                        m.append(windowMagnificationAnimationController.mEndSpec);
                        Log.d("WindowMagnificationAnimationController", m.toString());
                    }
                }
                if (windowMagnificationAnimationController.mEndSpec.equals(windowMagnificationAnimationController.mStartSpec)) {
                    int i3 = windowMagnificationAnimationController.mState;
                    if (i3 == 0) {
                        windowMagnificationAnimationController.mController.enableWindowMagnificationInternal(f4, f5, f6, windowMagnificationAnimationController.mMagnificationFrameOffsetRatioX, windowMagnificationAnimationController.mMagnificationFrameOffsetRatioY);
                    } else if (i3 == 3 || i3 == 2) {
                        windowMagnificationAnimationController.mValueAnimator.cancel();
                    }
                    windowMagnificationAnimationController.sendAnimationCallback(true);
                    windowMagnificationAnimationController.setState(1);
                    return;
                }
                int i4 = windowMagnificationAnimationController.mState;
                if (i4 == 2) {
                    windowMagnificationAnimationController.mValueAnimator.reverse();
                } else {
                    if (i4 == 3) {
                        windowMagnificationAnimationController.mValueAnimator.cancel();
                    }
                    windowMagnificationAnimationController.mValueAnimator.start();
                }
                windowMagnificationAnimationController.setState(3);
            }
        }
    }
}
