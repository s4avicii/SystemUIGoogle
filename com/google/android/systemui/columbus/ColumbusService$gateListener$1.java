package com.google.android.systemui.columbus;

import com.google.android.systemui.columbus.gates.Gate;

/* compiled from: ColumbusService.kt */
public final class ColumbusService$gateListener$1 implements Gate.Listener {
    public final /* synthetic */ ColumbusService this$0;

    public ColumbusService$gateListener$1(ColumbusService columbusService) {
        this.this$0 = columbusService;
    }

    public final void onGateChanged(Gate gate) {
        this.this$0.updateSensorListener();
    }
}
