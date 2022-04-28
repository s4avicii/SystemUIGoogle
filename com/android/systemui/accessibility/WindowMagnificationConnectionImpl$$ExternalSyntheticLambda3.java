package com.android.systemui.accessibility;

import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WindowMagnificationConnectionImpl$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ WindowMagnificationConnectionImpl f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ WindowMagnificationConnectionImpl$$ExternalSyntheticLambda3(WindowMagnificationConnectionImpl windowMagnificationConnectionImpl, int i, int i2) {
        this.f$0 = windowMagnificationConnectionImpl;
        this.f$1 = i;
        this.f$2 = i2;
    }

    public final void run() {
        WindowMagnificationConnectionImpl windowMagnificationConnectionImpl = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        Objects.requireNonNull(windowMagnificationConnectionImpl);
        ModeSwitchesController modeSwitchesController = windowMagnificationConnectionImpl.mModeSwitchesController;
        Objects.requireNonNull(modeSwitchesController);
        MagnificationModeSwitch magnificationModeSwitch = modeSwitchesController.mSwitchSupplier.get(i);
        if (magnificationModeSwitch != null) {
            magnificationModeSwitch.showButton(i2, true);
        }
    }
}
