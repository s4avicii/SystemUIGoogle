package com.android.systemui.controls.p004ui;

import android.view.View;
import android.widget.AdapterView;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.controls.management.ControlsEditingActivity;
import com.android.systemui.controls.management.ControlsFavoritingActivity;
import com.android.systemui.globalactions.GlobalActionsPopupMenu;
import java.util.Objects;

/* renamed from: com.android.systemui.controls.ui.ControlsUiControllerImpl$createMenu$1$onClick$1$1 */
/* compiled from: ControlsUiControllerImpl.kt */
public final class ControlsUiControllerImpl$createMenu$1$onClick$1$1 implements AdapterView.OnItemClickListener {
    public final /* synthetic */ GlobalActionsPopupMenu $this_apply;
    public final /* synthetic */ ControlsUiControllerImpl this$0;

    public ControlsUiControllerImpl$createMenu$1$onClick$1$1(ControlsUiControllerImpl controlsUiControllerImpl, GlobalActionsPopupMenu globalActionsPopupMenu) {
        this.this$0 = controlsUiControllerImpl;
        this.$this_apply = globalActionsPopupMenu;
    }

    public final void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        if (i == 0) {
            ControlsUiControllerImpl controlsUiControllerImpl = this.this$0;
            StructureInfo structureInfo = controlsUiControllerImpl.selectedStructure;
            Objects.requireNonNull(controlsUiControllerImpl);
            controlsUiControllerImpl.startTargetedActivity(structureInfo, ControlsFavoritingActivity.class);
        } else if (i == 1) {
            ControlsUiControllerImpl controlsUiControllerImpl2 = this.this$0;
            StructureInfo structureInfo2 = controlsUiControllerImpl2.selectedStructure;
            Objects.requireNonNull(controlsUiControllerImpl2);
            controlsUiControllerImpl2.startTargetedActivity(structureInfo2, ControlsEditingActivity.class);
        }
        this.$this_apply.dismiss();
    }
}
