package com.android.systemui.controls.p004ui;

import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* renamed from: com.android.systemui.controls.ui.ControlsUiControllerImpl$createCallback$1$onServicesUpdated$1 */
/* compiled from: ControlsUiControllerImpl.kt */
public final class ControlsUiControllerImpl$createCallback$1$onServicesUpdated$1 implements Runnable {
    public final /* synthetic */ List<SelectionItem> $lastItems;
    public final /* synthetic */ Function1<List<SelectionItem>, Unit> $onResult;
    public final /* synthetic */ ControlsUiControllerImpl this$0;

    public ControlsUiControllerImpl$createCallback$1$onServicesUpdated$1(ControlsUiControllerImpl controlsUiControllerImpl, ArrayList arrayList, Function1 function1) {
        this.this$0 = controlsUiControllerImpl;
        this.$lastItems = arrayList;
        this.$onResult = function1;
    }

    public final void run() {
        ViewGroup viewGroup = this.this$0.parent;
        if (viewGroup == null) {
            viewGroup = null;
        }
        viewGroup.removeAllViews();
        if (this.$lastItems.size() > 0) {
            this.$onResult.invoke(this.$lastItems);
        }
    }
}
