package com.android.systemui.accessibility;

import android.view.accessibility.IRemoteMagnificationAnimationCallback;
import com.android.systemui.accessibility.WindowMagnificationAnimationController;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WindowMagnificationConnectionImpl$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ WindowMagnificationConnectionImpl f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ IRemoteMagnificationAnimationCallback f$2;

    public /* synthetic */ WindowMagnificationConnectionImpl$$ExternalSyntheticLambda4(WindowMagnificationConnectionImpl windowMagnificationConnectionImpl, int i, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) {
        this.f$0 = windowMagnificationConnectionImpl;
        this.f$1 = i;
        this.f$2 = iRemoteMagnificationAnimationCallback;
    }

    public final void run() {
        float f;
        WindowMagnificationConnectionImpl windowMagnificationConnectionImpl = this.f$0;
        int i = this.f$1;
        IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback = this.f$2;
        Objects.requireNonNull(windowMagnificationConnectionImpl);
        WindowMagnification windowMagnification = windowMagnificationConnectionImpl.mWindowMagnification;
        Objects.requireNonNull(windowMagnification);
        WindowMagnificationController windowMagnificationController = windowMagnification.mMagnificationControllerSupplier.get(i);
        if (windowMagnificationController != null) {
            WindowMagnificationAnimationController windowMagnificationAnimationController = windowMagnificationController.mAnimationController;
            Objects.requireNonNull(windowMagnificationAnimationController);
            if (windowMagnificationAnimationController.mController != null) {
                windowMagnificationAnimationController.sendAnimationCallback(false);
                if (iRemoteMagnificationAnimationCallback == null) {
                    int i2 = windowMagnificationAnimationController.mState;
                    if (i2 == 3 || i2 == 2) {
                        windowMagnificationAnimationController.mValueAnimator.cancel();
                    }
                    windowMagnificationAnimationController.mController.deleteWindowMagnification$1();
                    windowMagnificationAnimationController.setState(0);
                    return;
                }
                windowMagnificationAnimationController.mAnimationCallback = iRemoteMagnificationAnimationCallback;
                int i3 = windowMagnificationAnimationController.mState;
                if (i3 != 0 && i3 != 2) {
                    windowMagnificationAnimationController.mStartSpec.set(1.0f, Float.NaN, Float.NaN);
                    WindowMagnificationAnimationController.AnimationSpec animationSpec = windowMagnificationAnimationController.mEndSpec;
                    WindowMagnificationController windowMagnificationController2 = windowMagnificationAnimationController.mController;
                    Objects.requireNonNull(windowMagnificationController2);
                    if (windowMagnificationController2.isWindowVisible()) {
                        f = windowMagnificationController2.mScale;
                    } else {
                        f = Float.NaN;
                    }
                    animationSpec.set(f, Float.NaN, Float.NaN);
                    windowMagnificationAnimationController.mValueAnimator.reverse();
                    windowMagnificationAnimationController.setState(2);
                } else if (i3 == 0) {
                    windowMagnificationAnimationController.sendAnimationCallback(true);
                }
            }
        }
    }
}
