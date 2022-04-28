package com.android.systemui.statusbar.policy;

import com.android.systemui.controls.management.ControlsListingController;
import java.util.function.Consumer;

/* compiled from: DeviceControlsControllerImpl.kt */
public final class DeviceControlsControllerImpl$removeCallback$1<T> implements Consumer {
    public final /* synthetic */ DeviceControlsControllerImpl this$0;

    public DeviceControlsControllerImpl$removeCallback$1(DeviceControlsControllerImpl deviceControlsControllerImpl) {
        this.this$0 = deviceControlsControllerImpl;
    }

    public final void accept(Object obj) {
        ((ControlsListingController) obj).removeCallback(this.this$0.listingCallback);
    }
}
