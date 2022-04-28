package com.android.systemui.p006qs.tiles;

import com.android.systemui.controls.management.ControlsListingController;
import java.util.ArrayList;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.DeviceControlsTile$listingCallback$1 */
/* compiled from: DeviceControlsTile.kt */
public final class DeviceControlsTile$listingCallback$1 implements ControlsListingController.ControlsListingCallback {
    public final /* synthetic */ DeviceControlsTile this$0;

    public DeviceControlsTile$listingCallback$1(DeviceControlsTile deviceControlsTile) {
        this.this$0 = deviceControlsTile;
    }

    public final void onServicesUpdated(ArrayList arrayList) {
        if (this.this$0.hasControlsApps.compareAndSet(arrayList.isEmpty(), !arrayList.isEmpty())) {
            DeviceControlsTile deviceControlsTile = this.this$0;
            Objects.requireNonNull(deviceControlsTile);
            deviceControlsTile.refreshState((Object) null);
        }
    }
}
