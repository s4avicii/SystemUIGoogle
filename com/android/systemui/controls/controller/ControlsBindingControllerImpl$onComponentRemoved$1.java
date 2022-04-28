package com.android.systemui.controls.controller;

import android.content.ComponentName;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsBindingControllerImpl.kt */
public final class ControlsBindingControllerImpl$onComponentRemoved$1 implements Runnable {
    public final /* synthetic */ ComponentName $componentName;
    public final /* synthetic */ ControlsBindingControllerImpl this$0;

    public ControlsBindingControllerImpl$onComponentRemoved$1(ControlsBindingControllerImpl controlsBindingControllerImpl, ComponentName componentName) {
        this.this$0 = controlsBindingControllerImpl;
        this.$componentName = componentName;
    }

    public final void run() {
        ControlsBindingControllerImpl controlsBindingControllerImpl = this.this$0;
        ControlsProviderLifecycleManager controlsProviderLifecycleManager = controlsBindingControllerImpl.currentProvider;
        if (controlsProviderLifecycleManager != null) {
            if (Intrinsics.areEqual(controlsProviderLifecycleManager.componentName, this.$componentName)) {
                controlsBindingControllerImpl.unbind();
            }
        }
    }
}
