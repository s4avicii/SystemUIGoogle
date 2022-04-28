package com.android.systemui.controls.p004ui;

import android.content.ComponentName;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.globalactions.GlobalActionsPopupMenu;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: com.android.systemui.controls.ui.ControlsUiControllerImpl$createDropDown$2$onClick$1$1 */
/* compiled from: ControlsUiControllerImpl.kt */
public final class ControlsUiControllerImpl$createDropDown$2$onClick$1$1 implements AdapterView.OnItemClickListener {
    public final /* synthetic */ GlobalActionsPopupMenu $this_apply;
    public final /* synthetic */ ControlsUiControllerImpl this$0;

    public ControlsUiControllerImpl$createDropDown$2$onClick$1$1(ControlsUiControllerImpl controlsUiControllerImpl, GlobalActionsPopupMenu globalActionsPopupMenu) {
        this.this$0 = controlsUiControllerImpl;
        this.$this_apply = globalActionsPopupMenu;
    }

    public final void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        boolean z;
        Object itemAtPosition = adapterView.getItemAtPosition(i);
        Objects.requireNonNull(itemAtPosition, "null cannot be cast to non-null type com.android.systemui.controls.ui.SelectionItem");
        SelectionItem selectionItem = (SelectionItem) itemAtPosition;
        ControlsUiControllerImpl controlsUiControllerImpl = this.this$0;
        ComponentName componentName = ControlsUiControllerImpl.EMPTY_COMPONENT;
        Objects.requireNonNull(controlsUiControllerImpl);
        List<StructureInfo> list = controlsUiControllerImpl.allStructures;
        ViewGroup viewGroup = null;
        if (list == null) {
            list = null;
        }
        for (StructureInfo structureInfo : list) {
            Objects.requireNonNull(structureInfo);
            if (!Intrinsics.areEqual(structureInfo.structure, selectionItem.structure) || !Intrinsics.areEqual(structureInfo.componentName, selectionItem.componentName)) {
                z = false;
                continue;
            } else {
                z = true;
                continue;
            }
            if (z) {
                if (!Intrinsics.areEqual(structureInfo, controlsUiControllerImpl.selectedStructure)) {
                    controlsUiControllerImpl.selectedStructure = structureInfo;
                    controlsUiControllerImpl.updatePreferences(structureInfo);
                    ViewGroup viewGroup2 = controlsUiControllerImpl.parent;
                    if (viewGroup2 != null) {
                        viewGroup = viewGroup2;
                    }
                    controlsUiControllerImpl.reload(viewGroup);
                }
                this.$this_apply.dismiss();
                return;
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }
}
