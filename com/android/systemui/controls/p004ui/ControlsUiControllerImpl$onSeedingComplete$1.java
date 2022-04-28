package com.android.systemui.controls.p004ui;

import android.view.ViewGroup;
import com.android.systemui.controls.controller.StructureInfo;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.systemui.controls.ui.ControlsUiControllerImpl$onSeedingComplete$1 */
/* compiled from: ControlsUiControllerImpl.kt */
public final class ControlsUiControllerImpl$onSeedingComplete$1<T> implements Consumer {
    public final /* synthetic */ ControlsUiControllerImpl this$0;

    public ControlsUiControllerImpl$onSeedingComplete$1(ControlsUiControllerImpl controlsUiControllerImpl) {
        this.this$0 = controlsUiControllerImpl;
    }

    public final void accept(Object obj) {
        Object obj2;
        ViewGroup viewGroup = null;
        if (((Boolean) obj).booleanValue()) {
            ControlsUiControllerImpl controlsUiControllerImpl = this.this$0;
            Objects.requireNonNull(controlsUiControllerImpl);
            Iterator it = controlsUiControllerImpl.controlsController.get().getFavorites().iterator();
            if (!it.hasNext()) {
                obj2 = null;
            } else {
                obj2 = it.next();
                if (it.hasNext()) {
                    StructureInfo structureInfo = (StructureInfo) obj2;
                    Objects.requireNonNull(structureInfo);
                    int size = structureInfo.controls.size();
                    do {
                        Object next = it.next();
                        StructureInfo structureInfo2 = (StructureInfo) next;
                        Objects.requireNonNull(structureInfo2);
                        int size2 = structureInfo2.controls.size();
                        if (size < size2) {
                            obj2 = next;
                            size = size2;
                        }
                    } while (it.hasNext());
                }
            }
            StructureInfo structureInfo3 = (StructureInfo) obj2;
            if (structureInfo3 == null) {
                structureInfo3 = ControlsUiControllerImpl.EMPTY_STRUCTURE;
            }
            controlsUiControllerImpl.selectedStructure = structureInfo3;
            ControlsUiControllerImpl controlsUiControllerImpl2 = this.this$0;
            controlsUiControllerImpl2.updatePreferences(controlsUiControllerImpl2.selectedStructure);
        }
        ControlsUiControllerImpl controlsUiControllerImpl3 = this.this$0;
        ViewGroup viewGroup2 = controlsUiControllerImpl3.parent;
        if (viewGroup2 != null) {
            viewGroup = viewGroup2;
        }
        controlsUiControllerImpl3.reload(viewGroup);
    }
}
