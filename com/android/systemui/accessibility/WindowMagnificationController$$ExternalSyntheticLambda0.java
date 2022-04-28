package com.android.systemui.accessibility;

import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WindowMagnificationController$$ExternalSyntheticLambda0 implements View.OnLayoutChangeListener {
    public final /* synthetic */ WindowMagnificationController f$0;

    public /* synthetic */ WindowMagnificationController$$ExternalSyntheticLambda0(WindowMagnificationController windowMagnificationController) {
        this.f$0 = windowMagnificationController;
    }

    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        WindowMagnificationController windowMagnificationController = this.f$0;
        Objects.requireNonNull(windowMagnificationController);
        if (!windowMagnificationController.mHandler.hasCallbacks(windowMagnificationController.mMirrorViewRunnable)) {
            windowMagnificationController.mHandler.post(windowMagnificationController.mMirrorViewRunnable);
        }
    }
}
