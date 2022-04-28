package com.android.systemui.controls.controller;

import android.content.ComponentName;
import android.util.Log;
import com.android.systemui.controls.controller.ControlsBindingController;
import java.util.List;
import java.util.function.Consumer;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsControllerImpl.kt */
public final class ControlsControllerImpl$startSeeding$1 implements ControlsBindingController.LoadCallback {
    public final /* synthetic */ Consumer<SeedResponse> $callback;
    public final /* synthetic */ ComponentName $componentName;
    public final /* synthetic */ boolean $didAnyFail;
    public final /* synthetic */ List<ComponentName> $remaining;
    public final /* synthetic */ ControlsControllerImpl this$0;

    public final void accept(Object obj) {
        ControlsControllerImpl controlsControllerImpl = this.this$0;
        controlsControllerImpl.executor.execute(new ControlsControllerImpl$startSeeding$1$accept$1((List) obj, controlsControllerImpl, this.$callback, this.$componentName, this.$remaining, this.$didAnyFail));
    }

    public ControlsControllerImpl$startSeeding$1(ControlsControllerImpl controlsControllerImpl, Consumer<SeedResponse> consumer, ComponentName componentName, List<ComponentName> list, boolean z) {
        this.this$0 = controlsControllerImpl;
        this.$callback = consumer;
        this.$componentName = componentName;
        this.$remaining = list;
        this.$didAnyFail = z;
    }

    public final void error(String str) {
        Log.e("ControlsControllerImpl", Intrinsics.stringPlus("Unable to seed favorites: ", str));
        ControlsControllerImpl controlsControllerImpl = this.this$0;
        controlsControllerImpl.executor.execute(new ControlsControllerImpl$startSeeding$1$error$1(this.$callback, this.$componentName, controlsControllerImpl, this.$remaining));
    }
}
