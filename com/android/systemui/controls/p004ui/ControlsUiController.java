package com.android.systemui.controls.p004ui;

import android.content.ComponentName;
import android.content.Context;
import android.service.controls.Control;
import android.view.ViewGroup;
import com.android.systemui.controls.controller.StructureInfo;
import java.util.List;

/* renamed from: com.android.systemui.controls.ui.ControlsUiController */
/* compiled from: ControlsUiController.kt */
public interface ControlsUiController {
    StructureInfo getPreferredStructure(List<StructureInfo> list);

    void hide();

    void onActionResponse(ComponentName componentName, String str, int i);

    void onRefreshState(ComponentName componentName, List<Control> list);

    void show(ViewGroup viewGroup, Runnable runnable, Context context);
}
