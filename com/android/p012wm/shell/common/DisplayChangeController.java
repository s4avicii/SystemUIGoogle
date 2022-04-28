package com.android.p012wm.shell.common;

import android.os.RemoteException;
import android.view.IDisplayWindowRotationCallback;
import android.view.IDisplayWindowRotationController;
import android.view.IWindowManager;
import android.window.WindowContainerTransaction;
import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: com.android.wm.shell.common.DisplayChangeController */
public final class DisplayChangeController {
    public final DisplayWindowRotationControllerImpl mControllerImpl;
    public final ShellExecutor mMainExecutor;
    public final CopyOnWriteArrayList<OnDisplayChangingListener> mRotationListener = new CopyOnWriteArrayList<>();

    /* renamed from: com.android.wm.shell.common.DisplayChangeController$DisplayWindowRotationControllerImpl */
    public class DisplayWindowRotationControllerImpl extends IDisplayWindowRotationController.Stub {
        public DisplayWindowRotationControllerImpl() {
        }

        public final void onRotateDisplay(int i, int i2, int i3, IDisplayWindowRotationCallback iDisplayWindowRotationCallback) {
            DisplayChangeController.this.mMainExecutor.execute(new C1835x93d164a5(this, i, i2, i3, iDisplayWindowRotationCallback));
        }
    }

    /* renamed from: com.android.wm.shell.common.DisplayChangeController$OnDisplayChangingListener */
    public interface OnDisplayChangingListener {
        void onRotateDisplay(int i, int i2, int i3, WindowContainerTransaction windowContainerTransaction);
    }

    public DisplayChangeController(IWindowManager iWindowManager, ShellExecutor shellExecutor) {
        this.mMainExecutor = shellExecutor;
        DisplayWindowRotationControllerImpl displayWindowRotationControllerImpl = new DisplayWindowRotationControllerImpl();
        this.mControllerImpl = displayWindowRotationControllerImpl;
        try {
            iWindowManager.setDisplayWindowRotationController(displayWindowRotationControllerImpl);
        } catch (RemoteException unused) {
            throw new RuntimeException("Unable to register rotation controller");
        }
    }
}
