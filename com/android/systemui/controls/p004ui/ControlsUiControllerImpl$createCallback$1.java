package com.android.systemui.controls.p004ui;

import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.management.ControlsListingController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IteratorsJVMKt;
import kotlin.jvm.functions.Function1;

/* renamed from: com.android.systemui.controls.ui.ControlsUiControllerImpl$createCallback$1 */
/* compiled from: ControlsUiControllerImpl.kt */
public final class ControlsUiControllerImpl$createCallback$1 implements ControlsListingController.ControlsListingCallback {
    public final /* synthetic */ Function1<List<SelectionItem>, Unit> $onResult;
    public final /* synthetic */ ControlsUiControllerImpl this$0;

    public ControlsUiControllerImpl$createCallback$1(ControlsUiControllerImpl controlsUiControllerImpl, Function1<? super List<SelectionItem>, Unit> function1) {
        this.this$0 = controlsUiControllerImpl;
        this.$onResult = function1;
    }

    public final void onServicesUpdated(ArrayList arrayList) {
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(arrayList, 10));
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) it.next();
            Objects.requireNonNull(controlsServiceInfo);
            arrayList2.add(new SelectionItem(controlsServiceInfo.loadLabel(), "", controlsServiceInfo.loadIcon(), controlsServiceInfo.componentName, controlsServiceInfo.serviceInfo.applicationInfo.uid));
        }
        ControlsUiControllerImpl controlsUiControllerImpl = this.this$0;
        Objects.requireNonNull(controlsUiControllerImpl);
        controlsUiControllerImpl.uiExecutor.execute(new ControlsUiControllerImpl$createCallback$1$onServicesUpdated$1(this.this$0, arrayList2, this.$onResult));
    }
}
