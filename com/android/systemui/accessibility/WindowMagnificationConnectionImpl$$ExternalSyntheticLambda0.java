package com.android.systemui.accessibility;

import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WindowMagnificationConnectionImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ WindowMagnificationConnectionImpl f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ float f$2;

    public /* synthetic */ WindowMagnificationConnectionImpl$$ExternalSyntheticLambda0(WindowMagnificationConnectionImpl windowMagnificationConnectionImpl, int i, float f) {
        this.f$0 = windowMagnificationConnectionImpl;
        this.f$1 = i;
        this.f$2 = f;
    }

    public final void run() {
        WindowMagnificationConnectionImpl windowMagnificationConnectionImpl = this.f$0;
        int i = this.f$1;
        float f = this.f$2;
        Objects.requireNonNull(windowMagnificationConnectionImpl);
        WindowMagnification windowMagnification = windowMagnificationConnectionImpl.mWindowMagnification;
        Objects.requireNonNull(windowMagnification);
        WindowMagnificationController windowMagnificationController = windowMagnification.mMagnificationControllerSupplier.get(i);
        if (windowMagnificationController != null) {
            WindowMagnificationAnimationController windowMagnificationAnimationController = windowMagnificationController.mAnimationController;
            Objects.requireNonNull(windowMagnificationAnimationController);
            if (!windowMagnificationAnimationController.mValueAnimator.isRunning() && windowMagnificationController.isWindowVisible() && windowMagnificationController.mScale != f) {
                windowMagnificationController.enableWindowMagnificationInternal(f, Float.NaN, Float.NaN, Float.NaN, Float.NaN);
                windowMagnificationController.mHandler.removeCallbacks(windowMagnificationController.mUpdateStateDescriptionRunnable);
                windowMagnificationController.mHandler.postDelayed(windowMagnificationController.mUpdateStateDescriptionRunnable, 100);
            }
        }
    }
}
