package com.android.systemui.statusbar.policy;

import android.content.SharedPreferences;
import android.util.Log;
import com.android.systemui.controls.controller.SeedResponse;
import com.android.systemui.controls.management.ControlsListingController;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptySet;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DeviceControlsControllerImpl.kt */
public final class DeviceControlsControllerImpl$seedFavorites$2<T> implements Consumer {
    public final /* synthetic */ SharedPreferences $prefs;
    public final /* synthetic */ DeviceControlsControllerImpl this$0;

    public DeviceControlsControllerImpl$seedFavorites$2(DeviceControlsControllerImpl deviceControlsControllerImpl, SharedPreferences sharedPreferences) {
        this.this$0 = deviceControlsControllerImpl;
        this.$prefs = sharedPreferences;
    }

    public final void accept(Object obj) {
        SeedResponse seedResponse = (SeedResponse) obj;
        Log.d("DeviceControlsControllerImpl", Intrinsics.stringPlus("Controls seeded: ", seedResponse));
        if (seedResponse.accepted) {
            DeviceControlsControllerImpl deviceControlsControllerImpl = this.this$0;
            SharedPreferences sharedPreferences = this.$prefs;
            String str = seedResponse.packageName;
            Objects.requireNonNull(deviceControlsControllerImpl);
            Set mutableSet = CollectionsKt___CollectionsKt.toMutableSet(sharedPreferences.getStringSet("SeedingCompleted", EmptySet.INSTANCE));
            mutableSet.add(str);
            sharedPreferences.edit().putStringSet("SeedingCompleted", mutableSet).apply();
            DeviceControlsControllerImpl deviceControlsControllerImpl2 = this.this$0;
            Objects.requireNonNull(deviceControlsControllerImpl2);
            if (deviceControlsControllerImpl2.position == null) {
                DeviceControlsControllerImpl deviceControlsControllerImpl3 = this.this$0;
                Objects.requireNonNull(deviceControlsControllerImpl3);
                deviceControlsControllerImpl3.position = 7;
            }
            this.this$0.fireControlsUpdate();
            Optional<ControlsListingController> controlsListingController = this.this$0.controlsComponent.getControlsListingController();
            final DeviceControlsControllerImpl deviceControlsControllerImpl4 = this.this$0;
            controlsListingController.ifPresent(new Consumer() {
                public final void accept(Object obj) {
                    ((ControlsListingController) obj).removeCallback(DeviceControlsControllerImpl.this.listingCallback);
                }
            });
        }
    }
}
