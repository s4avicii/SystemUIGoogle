package com.android.systemui.controls.controller;

import android.content.ComponentName;
import android.service.controls.Control;
import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.controller.ControlsController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import kotlin.collections.BrittleContainsOptimizationKt;
import kotlin.collections.CollectionsKt__IteratorsJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: ControlsControllerImpl.kt */
public final class ControlsControllerImpl$loadForComponent$2$accept$1 implements Runnable {
    public final /* synthetic */ ComponentName $componentName;
    public final /* synthetic */ List<Control> $controls;
    public final /* synthetic */ Consumer<ControlsController.LoadData> $dataCallback;
    public final /* synthetic */ ControlsControllerImpl this$0;

    public ControlsControllerImpl$loadForComponent$2$accept$1(ComponentName componentName, List<Control> list, ControlsControllerImpl controlsControllerImpl, Consumer<ControlsController.LoadData> consumer) {
        this.$componentName = componentName;
        this.$controls = list;
        this.this$0 = controlsControllerImpl;
        this.$dataCallback = consumer;
    }

    public final void run() {
        Set set;
        LinkedHashSet linkedHashSet;
        Map<ComponentName, ? extends List<StructureInfo>> map = Favorites.favMap;
        ArrayList controlsForComponent = Favorites.getControlsForComponent(this.$componentName);
        ArrayList arrayList = new ArrayList(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(controlsForComponent, 10));
        Iterator it = controlsForComponent.iterator();
        while (it.hasNext()) {
            ControlInfo controlInfo = (ControlInfo) it.next();
            Objects.requireNonNull(controlInfo);
            arrayList.add(controlInfo.controlId);
        }
        Map<ComponentName, ? extends List<StructureInfo>> map2 = Favorites.favMap;
        if (Favorites.updateControls(this.$componentName, this.$controls)) {
            this.this$0.persistenceWrapper.storeFavorites(Favorites.getAllStructures());
        }
        ControlsControllerImpl controlsControllerImpl = this.this$0;
        Set set2 = CollectionsKt___CollectionsKt.toSet(arrayList);
        List<Control> list = this.$controls;
        Objects.requireNonNull(controlsControllerImpl);
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(list, 10));
        for (Control controlId : list) {
            arrayList2.add(controlId.getControlId());
        }
        Collection convertToSetForSetOperationWith = BrittleContainsOptimizationKt.convertToSetForSetOperationWith(arrayList2, set2);
        if (convertToSetForSetOperationWith.isEmpty()) {
            set = CollectionsKt___CollectionsKt.toSet(set2);
        } else {
            if (convertToSetForSetOperationWith instanceof Set) {
                linkedHashSet = new LinkedHashSet();
                for (Object next : set2) {
                    if (!convertToSetForSetOperationWith.contains(next)) {
                        linkedHashSet.add(next);
                    }
                }
            } else {
                linkedHashSet = new LinkedHashSet(set2);
                linkedHashSet.removeAll(convertToSetForSetOperationWith);
            }
            set = linkedHashSet;
        }
        List<Control> list2 = this.$controls;
        ComponentName componentName = this.$componentName;
        ArrayList arrayList3 = new ArrayList(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(list2, 10));
        for (Control control : list2) {
            arrayList3.add(new ControlStatus(control, componentName, arrayList.contains(control.getControlId()), false));
        }
        ArrayList arrayList4 = new ArrayList();
        Map<ComponentName, ? extends List<StructureInfo>> map3 = Favorites.favMap;
        List<StructureInfo> structuresForComponent = Favorites.getStructuresForComponent(this.$componentName);
        ControlsControllerImpl controlsControllerImpl2 = this.this$0;
        ComponentName componentName2 = this.$componentName;
        for (StructureInfo structureInfo : structuresForComponent) {
            Objects.requireNonNull(structureInfo);
            for (ControlInfo controlInfo2 : structureInfo.controls) {
                Objects.requireNonNull(controlInfo2);
                if (set.contains(controlInfo2.controlId)) {
                    arrayList4.add(controlsControllerImpl2.createRemovedStatus(componentName2, controlInfo2, structureInfo.structure, true));
                }
            }
        }
        this.$dataCallback.accept(new ControlsControllerKt$createLoadDataObject$1(CollectionsKt___CollectionsKt.plus((List) arrayList4, (List) arrayList3), arrayList, false));
    }
}
