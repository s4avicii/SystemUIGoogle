package com.google.android.systemui.columbus.gates;

import com.google.android.systemui.columbus.gates.Gate;

/* compiled from: CameraVisibility.kt */
public final class CameraVisibility$gateListener$1 implements Gate.Listener {
    public final /* synthetic */ CameraVisibility this$0;

    public CameraVisibility$gateListener$1(CameraVisibility cameraVisibility) {
        this.this$0 = cameraVisibility;
    }

    public final void onGateChanged(Gate gate) {
        CameraVisibility cameraVisibility = this.this$0;
        cameraVisibility.updateHandler.post(new CameraVisibility$gateListener$1$onGateChanged$1(cameraVisibility));
    }
}
