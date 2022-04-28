package com.android.systemui.controls.controller;

import android.content.ComponentName;
import android.content.SharedPreferences;
import android.util.Log;
import com.android.systemui.controls.ControlsServiceInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import kotlin.collections.BrittleContainsOptimizationKt;
import kotlin.collections.CollectionsKt__IteratorsJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: ControlsControllerImpl.kt */
public final class ControlsControllerImpl$listingCallback$1$onServicesUpdated$1 implements Runnable {
    public final /* synthetic */ List<ControlsServiceInfo> $serviceInfos;
    public final /* synthetic */ ControlsControllerImpl this$0;

    public ControlsControllerImpl$listingCallback$1$onServicesUpdated$1(ArrayList arrayList, ControlsControllerImpl controlsControllerImpl) {
        this.$serviceInfos = arrayList;
        this.this$0 = controlsControllerImpl;
    }

    public final void run() {
        List<ControlsServiceInfo> list = this.$serviceInfos;
        ArrayList arrayList = new ArrayList(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(list, 10));
        for (ControlsServiceInfo controlsServiceInfo : list) {
            arrayList.add(controlsServiceInfo.componentName);
        }
        Set<ComponentName> set = CollectionsKt___CollectionsKt.toSet(arrayList);
        ArrayList allStructures = Favorites.getAllStructures();
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(allStructures, 10));
        Iterator it = allStructures.iterator();
        while (it.hasNext()) {
            StructureInfo structureInfo = (StructureInfo) it.next();
            Objects.requireNonNull(structureInfo);
            arrayList2.add(structureInfo.componentName);
        }
        Set set2 = CollectionsKt___CollectionsKt.toSet(arrayList2);
        UserStructure userStructure = this.this$0.userStructure;
        Objects.requireNonNull(userStructure);
        boolean z = false;
        SharedPreferences sharedPreferences = userStructure.userContext.getSharedPreferences("controls_prefs", 0);
        Set<String> stringSet = sharedPreferences.getStringSet("SeedingCompleted", new LinkedHashSet());
        ArrayList arrayList3 = new ArrayList(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(set, 10));
        for (ComponentName packageName : set) {
            arrayList3.add(packageName.getPackageName());
        }
        SharedPreferences.Editor edit = sharedPreferences.edit();
        Set mutableSet = CollectionsKt___CollectionsKt.toMutableSet(stringSet);
        mutableSet.retainAll(BrittleContainsOptimizationKt.convertToSetForSetOperationWith(arrayList3, mutableSet));
        edit.putStringSet("SeedingCompleted", mutableSet).apply();
        Set<ComponentName> mutableSet2 = CollectionsKt___CollectionsKt.toMutableSet(set2);
        mutableSet2.removeAll(BrittleContainsOptimizationKt.convertToSetForSetOperationWith(set, mutableSet2));
        ControlsControllerImpl controlsControllerImpl = this.this$0;
        for (ComponentName componentName : mutableSet2) {
            LinkedHashMap linkedHashMap = new LinkedHashMap(Favorites.favMap);
            linkedHashMap.remove(componentName);
            Favorites.favMap = linkedHashMap;
            controlsControllerImpl.bindingController.onComponentRemoved(componentName);
            z = true;
        }
        ControlsControllerImpl controlsControllerImpl2 = this.this$0;
        Objects.requireNonNull(controlsControllerImpl2);
        AuxiliaryPersistenceWrapper auxiliaryPersistenceWrapper = controlsControllerImpl2.auxiliaryPersistenceWrapper;
        Objects.requireNonNull(auxiliaryPersistenceWrapper);
        if (!auxiliaryPersistenceWrapper.favorites.isEmpty()) {
            Set<ComponentName> mutableSet3 = CollectionsKt___CollectionsKt.toMutableSet(set);
            mutableSet3.removeAll(BrittleContainsOptimizationKt.convertToSetForSetOperationWith(set2, mutableSet3));
            ControlsControllerImpl controlsControllerImpl3 = this.this$0;
            for (ComponentName cachedFavoritesAndRemoveFor : mutableSet3) {
                Objects.requireNonNull(controlsControllerImpl3);
                List<StructureInfo> cachedFavoritesAndRemoveFor2 = controlsControllerImpl3.auxiliaryPersistenceWrapper.getCachedFavoritesAndRemoveFor(cachedFavoritesAndRemoveFor);
                if (!cachedFavoritesAndRemoveFor2.isEmpty()) {
                    for (StructureInfo replaceControls : cachedFavoritesAndRemoveFor2) {
                        Favorites.replaceControls(replaceControls);
                    }
                    z = true;
                }
            }
            Set<ComponentName> mutableSet4 = CollectionsKt___CollectionsKt.toMutableSet(set);
            mutableSet4.retainAll(BrittleContainsOptimizationKt.convertToSetForSetOperationWith(set2, mutableSet4));
            ControlsControllerImpl controlsControllerImpl4 = this.this$0;
            for (ComponentName cachedFavoritesAndRemoveFor3 : mutableSet4) {
                Objects.requireNonNull(controlsControllerImpl4);
                controlsControllerImpl4.auxiliaryPersistenceWrapper.getCachedFavoritesAndRemoveFor(cachedFavoritesAndRemoveFor3);
            }
        }
        if (z) {
            Log.d("ControlsControllerImpl", "Detected change in available services, storing updated favorites");
            this.this$0.persistenceWrapper.storeFavorites(Favorites.getAllStructures());
        }
    }
}
