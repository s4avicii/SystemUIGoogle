package com.android.systemui.controls.management;

import android.content.ComponentName;
import com.android.systemui.controls.controller.ControlsController;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: ControlsProviderSelectorActivity.kt */
public /* synthetic */ class ControlsProviderSelectorActivity$onStart$2 extends FunctionReferenceImpl implements Function1<ComponentName, Integer> {
    public ControlsProviderSelectorActivity$onStart$2(ControlsController controlsController) {
        super(1, controlsController, ControlsController.class, "countFavoritesForComponent", "countFavoritesForComponent(Landroid/content/ComponentName;)I", 0);
    }

    public final Object invoke(Object obj) {
        return Integer.valueOf(((ControlsController) this.receiver).countFavoritesForComponent((ComponentName) obj));
    }
}
