package com.android.systemui.controls.controller;

import com.android.systemui.controls.p004ui.ControlsUiControllerImpl$onSeedingComplete$1;
import java.util.function.Consumer;

/* compiled from: ControlsControllerImpl.kt */
public final class ControlsControllerImpl$addSeedingFavoritesCallback$1 implements Runnable {
    public final /* synthetic */ Consumer<Boolean> $callback;
    public final /* synthetic */ ControlsControllerImpl this$0;

    public ControlsControllerImpl$addSeedingFavoritesCallback$1(ControlsControllerImpl controlsControllerImpl, ControlsUiControllerImpl$onSeedingComplete$1 controlsUiControllerImpl$onSeedingComplete$1) {
        this.this$0 = controlsControllerImpl;
        this.$callback = controlsUiControllerImpl$onSeedingComplete$1;
    }

    public final void run() {
        ControlsControllerImpl controlsControllerImpl = this.this$0;
        if (controlsControllerImpl.seedingInProgress) {
            controlsControllerImpl.seedingCallbacks.add(this.$callback);
        } else {
            this.$callback.accept(Boolean.FALSE);
        }
    }
}
