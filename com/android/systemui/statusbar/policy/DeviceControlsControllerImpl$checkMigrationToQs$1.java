package com.android.systemui.statusbar.policy;

import com.android.systemui.controls.controller.ControlsController;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: DeviceControlsControllerImpl.kt */
public final class DeviceControlsControllerImpl$checkMigrationToQs$1<T> implements Consumer {
    public final /* synthetic */ DeviceControlsControllerImpl this$0;

    public DeviceControlsControllerImpl$checkMigrationToQs$1(DeviceControlsControllerImpl deviceControlsControllerImpl) {
        this.this$0 = deviceControlsControllerImpl;
    }

    public final void accept(Object obj) {
        if (!((ControlsController) obj).getFavorites().isEmpty()) {
            DeviceControlsControllerImpl deviceControlsControllerImpl = this.this$0;
            Objects.requireNonNull(deviceControlsControllerImpl);
            deviceControlsControllerImpl.position = 3;
            this.this$0.fireControlsUpdate();
        }
    }
}
