package com.android.systemui.controls.controller;

import android.content.ComponentName;
import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.controller.ControlsController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import kotlin.collections.CollectionsKt__IteratorsJVMKt;
import kotlin.collections.CollectionsKt__ReversedViewsKt;

/* compiled from: ControlsControllerImpl.kt */
public final class ControlsControllerImpl$loadForComponent$2$error$1 implements Runnable {
    public final /* synthetic */ ComponentName $componentName;
    public final /* synthetic */ Consumer<ControlsController.LoadData> $dataCallback;
    public final /* synthetic */ ControlsControllerImpl this$0;

    public ControlsControllerImpl$loadForComponent$2$error$1(ComponentName componentName, Consumer<ControlsController.LoadData> consumer, ControlsControllerImpl controlsControllerImpl) {
        this.$componentName = componentName;
        this.$dataCallback = consumer;
        this.this$0 = controlsControllerImpl;
    }

    public final void run() {
        Map<ComponentName, ? extends List<StructureInfo>> map = Favorites.favMap;
        List<StructureInfo> structuresForComponent = Favorites.getStructuresForComponent(this.$componentName);
        ControlsControllerImpl controlsControllerImpl = this.this$0;
        ComponentName componentName = this.$componentName;
        ArrayList arrayList = new ArrayList();
        for (StructureInfo structureInfo : structuresForComponent) {
            Objects.requireNonNull(structureInfo);
            List<ControlInfo> list = structureInfo.controls;
            ArrayList arrayList2 = new ArrayList(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(list, 10));
            for (ControlInfo createRemovedStatus : list) {
                arrayList2.add(controlsControllerImpl.createRemovedStatus(componentName, createRemovedStatus, structureInfo.structure, false));
            }
            CollectionsKt__ReversedViewsKt.addAll((Collection) arrayList, (Collection) arrayList2);
        }
        ArrayList arrayList3 = new ArrayList(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(arrayList, 10));
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ControlStatus controlStatus = (ControlStatus) it.next();
            Objects.requireNonNull(controlStatus);
            arrayList3.add(controlStatus.control.getControlId());
        }
        this.$dataCallback.accept(new ControlsControllerKt$createLoadDataObject$1(arrayList, arrayList3, true));
    }
}
