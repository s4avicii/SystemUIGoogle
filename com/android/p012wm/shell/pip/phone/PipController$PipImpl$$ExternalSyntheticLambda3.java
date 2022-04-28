package com.android.p012wm.shell.pip.phone;

import com.android.p012wm.shell.pip.phone.PipController;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.phone.PipController$PipImpl$$ExternalSyntheticLambda3 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PipController$PipImpl$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ PipController.PipImpl f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ PipController$PipImpl$$ExternalSyntheticLambda3(PipController.PipImpl pipImpl, boolean z, int i) {
        this.f$0 = pipImpl;
        this.f$1 = z;
    }

    public final void run() {
        PipController.PipImpl pipImpl = this.f$0;
        boolean z = this.f$1;
        Objects.requireNonNull(pipImpl);
        PipController pipController = PipController.this;
        Objects.requireNonNull(pipController);
        PipTouchHandler pipTouchHandler = pipController.mTouchHandler;
        Objects.requireNonNull(pipTouchHandler);
        PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler.mPipResizeGestureHandler;
        Objects.requireNonNull(pipResizeGestureHandler);
        pipResizeGestureHandler.mIsSysUiStateValid = z;
    }
}
