package com.android.systemui.controls.controller;

import android.os.IBinder;
import android.service.controls.Control;
import android.service.controls.IControlsSubscription;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: ControlsBindingControllerImpl.kt */
public final class ControlsBindingControllerImpl$LoadSubscriber$onNext$1 implements Runnable {

    /* renamed from: $c */
    public final /* synthetic */ Control f44$c;
    public final /* synthetic */ IBinder $token;
    public final /* synthetic */ ControlsBindingControllerImpl.LoadSubscriber this$0;
    public final /* synthetic */ ControlsBindingControllerImpl this$1;

    public ControlsBindingControllerImpl$LoadSubscriber$onNext$1(ControlsBindingControllerImpl.LoadSubscriber loadSubscriber, Control control, ControlsBindingControllerImpl controlsBindingControllerImpl, IBinder iBinder) {
        this.this$0 = loadSubscriber;
        this.f44$c = control;
        this.this$1 = controlsBindingControllerImpl;
        this.$token = iBinder;
    }

    public final void run() {
        if (!this.this$0.isTerminated.get()) {
            ControlsBindingControllerImpl.LoadSubscriber loadSubscriber = this.this$0;
            Objects.requireNonNull(loadSubscriber);
            loadSubscriber.loadedControls.add(this.f44$c);
            ControlsBindingControllerImpl.LoadSubscriber loadSubscriber2 = this.this$0;
            Objects.requireNonNull(loadSubscriber2);
            ControlsBindingControllerImpl.LoadSubscriber loadSubscriber3 = this.this$0;
            Objects.requireNonNull(loadSubscriber3);
            if (((long) loadSubscriber2.loadedControls.size()) >= loadSubscriber3.requestLimit) {
                ControlsBindingControllerImpl.LoadSubscriber loadSubscriber4 = this.this$0;
                ControlsBindingControllerImpl controlsBindingControllerImpl = this.this$1;
                IBinder iBinder = this.$token;
                Objects.requireNonNull(loadSubscriber4);
                ArrayList<Control> arrayList = loadSubscriber4.loadedControls;
                ControlsBindingControllerImpl.LoadSubscriber loadSubscriber5 = this.this$0;
                IControlsSubscription iControlsSubscription = loadSubscriber5.subscription;
                if (iControlsSubscription == null) {
                    iControlsSubscription = null;
                }
                loadSubscriber4.maybeTerminateAndRun(new ControlsBindingControllerImpl.OnCancelAndLoadRunnable(controlsBindingControllerImpl, iBinder, arrayList, iControlsSubscription, loadSubscriber5.callback));
            }
        }
    }
}
