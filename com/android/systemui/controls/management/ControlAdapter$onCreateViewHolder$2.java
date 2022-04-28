package com.android.systemui.controls.management;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;

/* compiled from: ControlAdapter.kt */
final class ControlAdapter$onCreateViewHolder$2 extends Lambda implements Function2<String, Boolean, Unit> {
    public final /* synthetic */ ControlAdapter this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ControlAdapter$onCreateViewHolder$2(ControlAdapter controlAdapter) {
        super(2);
        this.this$0 = controlAdapter;
    }

    public final Object invoke(Object obj, Object obj2) {
        String str = (String) obj;
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        ControlsModel controlsModel = this.this$0.model;
        if (controlsModel != null) {
            controlsModel.changeFavoriteStatus(str, booleanValue);
        }
        return Unit.INSTANCE;
    }
}
