package com.android.systemui.controls.controller;

import android.content.ComponentName;
import com.android.systemui.controls.controller.ControlsBindingController;
import com.android.systemui.controls.controller.ControlsController;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: ControlsControllerImpl.kt */
public final class ControlsControllerImpl$loadForComponent$2 implements ControlsBindingController.LoadCallback {
    public final /* synthetic */ ComponentName $componentName;
    public final /* synthetic */ Consumer<ControlsController.LoadData> $dataCallback;
    public final /* synthetic */ ControlsControllerImpl this$0;

    public ControlsControllerImpl$loadForComponent$2(ControlsControllerImpl controlsControllerImpl, ComponentName componentName, Consumer<ControlsController.LoadData> consumer) {
        this.this$0 = controlsControllerImpl;
        this.$componentName = componentName;
        this.$dataCallback = consumer;
    }

    public final void accept(Object obj) {
        ControlsControllerImpl controlsControllerImpl = this.this$0;
        controlsControllerImpl.executor.execute(new ControlsControllerImpl$loadForComponent$2$accept$1(this.$componentName, (List) obj, controlsControllerImpl, this.$dataCallback));
    }

    public final void error(String str) {
        ControlsControllerImpl controlsControllerImpl = this.this$0;
        controlsControllerImpl.executor.execute(new ControlsControllerImpl$loadForComponent$2$error$1(this.$componentName, this.$dataCallback, controlsControllerImpl));
    }
}
