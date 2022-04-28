package com.google.android.systemui.columbus;

import com.google.android.systemui.columbus.actions.Action;

/* compiled from: ColumbusService.kt */
public final class ColumbusService$actionListener$1 implements Action.Listener {
    public final /* synthetic */ ColumbusService this$0;

    public ColumbusService$actionListener$1(ColumbusService columbusService) {
        this.this$0 = columbusService;
    }

    public final void onActionAvailabilityChanged(Action action) {
        this.this$0.updateSensorListener();
    }
}
