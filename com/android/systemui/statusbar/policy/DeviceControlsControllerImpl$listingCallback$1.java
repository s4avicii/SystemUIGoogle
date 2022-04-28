package com.android.systemui.statusbar.policy;

import android.content.SharedPreferences;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.management.ControlsListingController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptySet;

/* compiled from: DeviceControlsControllerImpl.kt */
public final class DeviceControlsControllerImpl$listingCallback$1 implements ControlsListingController.ControlsListingCallback {
    public final /* synthetic */ DeviceControlsControllerImpl this$0;

    public DeviceControlsControllerImpl$listingCallback$1(DeviceControlsControllerImpl deviceControlsControllerImpl) {
        this.this$0 = deviceControlsControllerImpl;
    }

    public final void onServicesUpdated(ArrayList arrayList) {
        if (!arrayList.isEmpty()) {
            DeviceControlsControllerImpl deviceControlsControllerImpl = this.this$0;
            Objects.requireNonNull(deviceControlsControllerImpl);
            String[] stringArray = deviceControlsControllerImpl.context.getResources().getStringArray(C1777R.array.config_controlsPreferredPackages);
            SharedPreferences sharedPreferences = deviceControlsControllerImpl.userContextProvider.getUserContext().getSharedPreferences("controls_prefs", 0);
            Set<String> stringSet = sharedPreferences.getStringSet("SeedingCompleted", EmptySet.INSTANCE);
            ControlsController controlsController = deviceControlsControllerImpl.controlsComponent.getControlsController().get();
            ArrayList arrayList2 = new ArrayList();
            for (int i = 0; i < Math.min(2, stringArray.length); i++) {
                String str = stringArray[i];
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) it.next();
                    if (str.equals(controlsServiceInfo.componentName.getPackageName()) && !stringSet.contains(str)) {
                        if (controlsController.countFavoritesForComponent(controlsServiceInfo.componentName) > 0) {
                            Set mutableSet = CollectionsKt___CollectionsKt.toMutableSet(sharedPreferences.getStringSet("SeedingCompleted", EmptySet.INSTANCE));
                            mutableSet.add(str);
                            sharedPreferences.edit().putStringSet("SeedingCompleted", mutableSet).apply();
                        } else {
                            arrayList2.add(controlsServiceInfo.componentName);
                        }
                    }
                }
            }
            if (!arrayList2.isEmpty()) {
                controlsController.seedFavoritesForComponents(arrayList2, new DeviceControlsControllerImpl$seedFavorites$2(deviceControlsControllerImpl, sharedPreferences));
            }
        }
    }
}
