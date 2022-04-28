package com.android.systemui.controls.controller;

import android.os.IBinder;
import android.service.controls.IControlsActionCallback;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl;

/* compiled from: ControlsBindingControllerImpl.kt */
public final class ControlsBindingControllerImpl$actionCallbackService$1 extends IControlsActionCallback.Stub {
    public final /* synthetic */ ControlsBindingControllerImpl this$0;

    public ControlsBindingControllerImpl$actionCallbackService$1(ControlsBindingControllerImpl controlsBindingControllerImpl) {
        this.this$0 = controlsBindingControllerImpl;
    }

    public final void accept(IBinder iBinder, String str, int i) {
        ControlsBindingControllerImpl controlsBindingControllerImpl = this.this$0;
        controlsBindingControllerImpl.backgroundExecutor.execute(new ControlsBindingControllerImpl.OnActionResponseRunnable(iBinder, str, i));
    }
}
