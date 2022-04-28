package com.android.systemui.controls.controller;

import android.util.Log;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl;

/* compiled from: ControlsBindingControllerImpl.kt */
public final class ControlsBindingControllerImpl$LoadSubscriber$loadCancel$1 implements Runnable {
    public final /* synthetic */ ControlsBindingControllerImpl.LoadSubscriber this$0;

    public ControlsBindingControllerImpl$LoadSubscriber$loadCancel$1(ControlsBindingControllerImpl.LoadSubscriber loadSubscriber) {
        this.this$0 = loadSubscriber;
    }

    /* JADX WARNING: type inference failed for: r2v2, types: [kotlin.jvm.functions.Function0, kotlin.jvm.internal.Lambda] */
    public final void run() {
        ? r2 = this.this$0._loadCancelInternal;
        if (r2 != 0) {
            Log.d("ControlsBindingControllerImpl", "Canceling loadSubscribtion");
            r2.invoke();
        }
    }
}
