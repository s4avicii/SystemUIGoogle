package com.android.systemui.controls.controller;

import android.content.ComponentName;
import android.service.controls.Control;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* compiled from: ControlsControllerImpl.kt */
public final class ControlsControllerImpl$refreshStatus$1 implements Runnable {
    public final /* synthetic */ ComponentName $componentName;
    public final /* synthetic */ Control $control;
    public final /* synthetic */ ControlsControllerImpl this$0;

    public ControlsControllerImpl$refreshStatus$1(ComponentName componentName, Control control, ControlsControllerImpl controlsControllerImpl) {
        this.$componentName = componentName;
        this.$control = control;
        this.this$0 = controlsControllerImpl;
    }

    public final void run() {
        Map<ComponentName, ? extends List<StructureInfo>> map = Favorites.favMap;
        if (Favorites.updateControls(this.$componentName, Collections.singletonList(this.$control))) {
            this.this$0.persistenceWrapper.storeFavorites(Favorites.getAllStructures());
        }
    }
}
