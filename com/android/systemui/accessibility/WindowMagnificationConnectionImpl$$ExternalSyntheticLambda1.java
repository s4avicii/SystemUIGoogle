package com.android.systemui.accessibility;

import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WindowMagnificationConnectionImpl$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ WindowMagnificationConnectionImpl f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ float f$2;
    public final /* synthetic */ float f$3;

    public /* synthetic */ WindowMagnificationConnectionImpl$$ExternalSyntheticLambda1(WindowMagnificationConnectionImpl windowMagnificationConnectionImpl, int i, float f, float f2) {
        this.f$0 = windowMagnificationConnectionImpl;
        this.f$1 = i;
        this.f$2 = f;
        this.f$3 = f2;
    }

    public final void run() {
        WindowMagnificationConnectionImpl windowMagnificationConnectionImpl = this.f$0;
        int i = this.f$1;
        float f = this.f$2;
        float f2 = this.f$3;
        Objects.requireNonNull(windowMagnificationConnectionImpl);
        WindowMagnification windowMagnification = windowMagnificationConnectionImpl.mWindowMagnification;
        Objects.requireNonNull(windowMagnification);
        WindowMagnificationController windowMagnificationController = windowMagnification.mMagnificationControllerSupplier.get(i);
        if (windowMagnificationController != null) {
            windowMagnificationController.moveWindowMagnifier(f, f2);
        }
    }
}
