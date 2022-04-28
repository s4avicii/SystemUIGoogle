package com.android.systemui.controls.controller;

import com.android.systemui.controls.controller.ControlsBindingControllerImpl;

/* renamed from: com.android.systemui.controls.controller.ControlsBindingControllerImpl$LoadSubscriber$maybeTerminateAndRun$2 */
/* compiled from: ControlsBindingControllerImpl.kt */
public final class C0737x5cb900b8 implements Runnable {
    public final /* synthetic */ Runnable $postTerminateFn;
    public final /* synthetic */ ControlsBindingControllerImpl.LoadSubscriber this$0;

    public C0737x5cb900b8(ControlsBindingControllerImpl.LoadSubscriber loadSubscriber, ControlsBindingControllerImpl.CallbackRunnable callbackRunnable) {
        this.this$0 = loadSubscriber;
        this.$postTerminateFn = callbackRunnable;
    }

    public final void run() {
        this.this$0.isTerminated.compareAndSet(false, true);
        this.$postTerminateFn.run();
    }
}
