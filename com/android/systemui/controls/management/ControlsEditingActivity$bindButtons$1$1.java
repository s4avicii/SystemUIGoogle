package com.android.systemui.controls.management;

import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import androidx.mediarouter.R$bool;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.ControlsControllerImpl$replaceFavoritesForStructure$1;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.controls.p004ui.ControlsActivity;
import java.util.Objects;

/* compiled from: ControlsEditingActivity.kt */
public final class ControlsEditingActivity$bindButtons$1$1 implements View.OnClickListener {
    public final /* synthetic */ ControlsEditingActivity this$0;

    public ControlsEditingActivity$bindButtons$1$1(ControlsEditingActivity controlsEditingActivity) {
        this.this$0 = controlsEditingActivity;
    }

    public final void onClick(View view) {
        ControlsEditingActivity controlsEditingActivity = this.this$0;
        int i = ControlsEditingActivity.$r8$clinit;
        Objects.requireNonNull(controlsEditingActivity);
        ControlsControllerImpl controlsControllerImpl = controlsEditingActivity.controller;
        ComponentName componentName = controlsEditingActivity.component;
        FavoritesModel favoritesModel = null;
        if (componentName == null) {
            componentName = null;
        }
        CharSequence charSequence = controlsEditingActivity.structure;
        if (charSequence == null) {
            charSequence = null;
        }
        FavoritesModel favoritesModel2 = controlsEditingActivity.model;
        if (favoritesModel2 != null) {
            favoritesModel = favoritesModel2;
        }
        StructureInfo structureInfo = new StructureInfo(componentName, charSequence, favoritesModel.getFavorites());
        Objects.requireNonNull(controlsControllerImpl);
        if (controlsControllerImpl.confirmAvailability()) {
            controlsControllerImpl.executor.execute(new ControlsControllerImpl$replaceFavoritesForStructure$1(structureInfo, controlsControllerImpl));
        }
        this.this$0.startActivity(new Intent(this.this$0.getApplicationContext(), ControlsActivity.class), ActivityOptions.makeSceneTransitionAnimation(this.this$0, new Pair[0]).toBundle());
        ControlsEditingActivity controlsEditingActivity2 = this.this$0;
        Objects.requireNonNull(controlsEditingActivity2);
        R$bool.exitAnimation((ViewGroup) controlsEditingActivity2.requireViewById(C1777R.C1779id.controls_management_root), new ControlsEditingActivity$animateExitAndFinish$1(controlsEditingActivity2)).start();
    }
}
