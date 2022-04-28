package com.android.p012wm.shell.pip.phone;

import android.window.WindowContainerTransaction;
import com.android.internal.util.function.TriConsumer;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.phone.PipController$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PipController$$ExternalSyntheticLambda0 implements TriConsumer {
    public final /* synthetic */ PipController f$0;

    public /* synthetic */ PipController$$ExternalSyntheticLambda0(PipController pipController) {
        this.f$0 = pipController;
    }

    public final void accept(Object obj, Object obj2, Object obj3) {
        PipController pipController = this.f$0;
        Objects.requireNonNull(pipController);
        PipTouchHandler pipTouchHandler = pipController.mTouchHandler;
        boolean booleanValue = ((Boolean) obj).booleanValue();
        int intValue = ((Integer) obj2).intValue();
        Objects.requireNonNull(pipTouchHandler);
        pipTouchHandler.mIsShelfShowing = booleanValue;
        pipTouchHandler.mShelfHeight = intValue;
        if (((Boolean) obj3).booleanValue()) {
            pipController.updateMovementBounds(pipController.mPipBoundsState.getBounds(), false, false, true, (WindowContainerTransaction) null);
        }
    }
}
