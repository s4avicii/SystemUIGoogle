package com.android.systemui.controls.controller;

import android.service.controls.IControlsSubscriber;
import android.util.Log;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsProviderLifecycleManager.kt */
public final class ControlsProviderLifecycleManager$maybeBindAndLoad$1 implements Runnable {
    public final /* synthetic */ IControlsSubscriber.Stub $subscriber;
    public final /* synthetic */ ControlsProviderLifecycleManager this$0;

    public ControlsProviderLifecycleManager$maybeBindAndLoad$1(ControlsProviderLifecycleManager controlsProviderLifecycleManager, ControlsBindingControllerImpl.LoadSubscriber loadSubscriber) {
        this.this$0 = controlsProviderLifecycleManager;
        this.$subscriber = loadSubscriber;
    }

    public final void run() {
        ControlsProviderLifecycleManager controlsProviderLifecycleManager = this.this$0;
        Log.d(controlsProviderLifecycleManager.TAG, Intrinsics.stringPlus("Timeout waiting onLoad for ", controlsProviderLifecycleManager.componentName));
        IControlsSubscriber.Stub stub = this.$subscriber;
        ControlsProviderLifecycleManager controlsProviderLifecycleManager2 = this.this$0;
        Objects.requireNonNull(controlsProviderLifecycleManager2);
        stub.onError(controlsProviderLifecycleManager2.token, "Timeout waiting onLoad");
        ControlsProviderLifecycleManager controlsProviderLifecycleManager3 = this.this$0;
        Objects.requireNonNull(controlsProviderLifecycleManager3);
        Runnable runnable = controlsProviderLifecycleManager3.onLoadCanceller;
        if (runnable != null) {
            runnable.run();
        }
        controlsProviderLifecycleManager3.onLoadCanceller = null;
        controlsProviderLifecycleManager3.executor.execute(new ControlsProviderLifecycleManager$bindService$1(controlsProviderLifecycleManager3, false));
    }
}
