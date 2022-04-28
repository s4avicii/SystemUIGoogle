package com.android.systemui.controls.management;

import android.content.pm.ServiceInfo;
import android.util.Log;
import com.android.settingslib.applications.ServiceListing;
import com.android.systemui.controls.management.ControlsListingController;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsListingControllerImpl.kt */
public final class ControlsListingControllerImpl$serviceListingCallback$1 implements ServiceListing.Callback {
    public final /* synthetic */ ControlsListingControllerImpl this$0;

    public ControlsListingControllerImpl$serviceListingCallback$1(ControlsListingControllerImpl controlsListingControllerImpl) {
        this.this$0 = controlsListingControllerImpl;
    }

    public final void onServicesReloaded(ArrayList arrayList) {
        final List<ServiceInfo> list = CollectionsKt___CollectionsKt.toList(arrayList);
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (ServiceInfo componentName : list) {
            linkedHashSet.add(componentName.getComponentName());
        }
        final ControlsListingControllerImpl controlsListingControllerImpl = this.this$0;
        controlsListingControllerImpl.backgroundExecutor.execute(new Runnable() {
            public final void run() {
                if (ControlsListingControllerImpl.this.userChangeInProgress.get() <= 0 && !linkedHashSet.equals(ControlsListingControllerImpl.this.availableComponents)) {
                    Log.d("ControlsListingControllerImpl", Intrinsics.stringPlus("ServiceConfig reloaded, count: ", Integer.valueOf(linkedHashSet.size())));
                    ControlsListingControllerImpl controlsListingControllerImpl = ControlsListingControllerImpl.this;
                    controlsListingControllerImpl.availableComponents = linkedHashSet;
                    controlsListingControllerImpl.availableServices = list;
                    ArrayList currentServices = controlsListingControllerImpl.getCurrentServices();
                    for (ControlsListingController.ControlsListingCallback onServicesUpdated : ControlsListingControllerImpl.this.callbacks) {
                        onServicesUpdated.onServicesUpdated(currentServices);
                    }
                }
            }
        });
    }
}
