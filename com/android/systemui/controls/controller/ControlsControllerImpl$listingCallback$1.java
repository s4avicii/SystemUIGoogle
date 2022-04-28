package com.android.systemui.controls.controller;

import com.android.systemui.controls.management.ControlsListingController;
import java.util.ArrayList;

/* compiled from: ControlsControllerImpl.kt */
public final class ControlsControllerImpl$listingCallback$1 implements ControlsListingController.ControlsListingCallback {
    public final /* synthetic */ ControlsControllerImpl this$0;

    public ControlsControllerImpl$listingCallback$1(ControlsControllerImpl controlsControllerImpl) {
        this.this$0 = controlsControllerImpl;
    }

    public final void onServicesUpdated(ArrayList arrayList) {
        ControlsControllerImpl controlsControllerImpl = this.this$0;
        controlsControllerImpl.executor.execute(new ControlsControllerImpl$listingCallback$1$onServicesUpdated$1(arrayList, controlsControllerImpl));
    }
}
