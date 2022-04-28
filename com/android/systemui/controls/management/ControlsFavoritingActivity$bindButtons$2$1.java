package com.android.systemui.controls.management;

import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Pair;
import android.view.View;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.ControlsControllerImpl$replaceFavoritesForStructure$1;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.controls.p004ui.ControlsActivity;
import java.util.ArrayList;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsFavoritingActivity.kt */
public final class ControlsFavoritingActivity$bindButtons$2$1 implements View.OnClickListener {
    public final /* synthetic */ ControlsFavoritingActivity this$0;

    public ControlsFavoritingActivity$bindButtons$2$1(ControlsFavoritingActivity controlsFavoritingActivity) {
        this.this$0 = controlsFavoritingActivity;
    }

    public final void onClick(View view) {
        ControlsFavoritingActivity controlsFavoritingActivity = this.this$0;
        if (controlsFavoritingActivity.component != null) {
            for (StructureContainer structureContainer : controlsFavoritingActivity.listOfStructures) {
                Objects.requireNonNull(structureContainer);
                ArrayList favorites = structureContainer.model.getFavorites();
                ControlsControllerImpl controlsControllerImpl = controlsFavoritingActivity.controller;
                ComponentName componentName = controlsFavoritingActivity.component;
                Intrinsics.checkNotNull(componentName);
                StructureInfo structureInfo = new StructureInfo(componentName, structureContainer.structureName, favorites);
                Objects.requireNonNull(controlsControllerImpl);
                if (controlsControllerImpl.confirmAvailability()) {
                    controlsControllerImpl.executor.execute(new ControlsControllerImpl$replaceFavoritesForStructure$1(structureInfo, controlsControllerImpl));
                }
            }
            this.this$0.animateExitAndFinish();
            ControlsFavoritingActivity controlsFavoritingActivity2 = this.this$0;
            Objects.requireNonNull(controlsFavoritingActivity2);
            controlsFavoritingActivity2.startActivity(new Intent(controlsFavoritingActivity2.getApplicationContext(), ControlsActivity.class), ActivityOptions.makeSceneTransitionAnimation(controlsFavoritingActivity2, new Pair[0]).toBundle());
        }
    }
}
