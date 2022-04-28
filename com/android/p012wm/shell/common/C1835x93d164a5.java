package com.android.p012wm.shell.common;

import android.os.RemoteException;
import android.util.Slog;
import android.view.IDisplayWindowRotationCallback;
import android.window.WindowContainerTransaction;
import com.android.p012wm.shell.common.DisplayChangeController;
import java.util.Iterator;
import java.util.Objects;

/* renamed from: com.android.wm.shell.common.DisplayChangeController$DisplayWindowRotationControllerImpl$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1835x93d164a5 implements Runnable {
    public final /* synthetic */ DisplayChangeController.DisplayWindowRotationControllerImpl f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ IDisplayWindowRotationCallback f$4;

    public /* synthetic */ C1835x93d164a5(DisplayChangeController.DisplayWindowRotationControllerImpl displayWindowRotationControllerImpl, int i, int i2, int i3, IDisplayWindowRotationCallback iDisplayWindowRotationCallback) {
        this.f$0 = displayWindowRotationControllerImpl;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = i3;
        this.f$4 = iDisplayWindowRotationCallback;
    }

    public final void run() {
        DisplayChangeController.DisplayWindowRotationControllerImpl displayWindowRotationControllerImpl = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        int i3 = this.f$3;
        IDisplayWindowRotationCallback iDisplayWindowRotationCallback = this.f$4;
        Objects.requireNonNull(displayWindowRotationControllerImpl);
        DisplayChangeController displayChangeController = DisplayChangeController.this;
        Objects.requireNonNull(displayChangeController);
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        Iterator<DisplayChangeController.OnDisplayChangingListener> it = displayChangeController.mRotationListener.iterator();
        while (it.hasNext()) {
            it.next().onRotateDisplay(i, i2, i3, windowContainerTransaction);
        }
        try {
            iDisplayWindowRotationCallback.continueRotateDisplay(i3, windowContainerTransaction);
        } catch (RemoteException e) {
            Slog.e("DisplayChangeController", "Failed to continue rotation", e);
        }
    }
}
