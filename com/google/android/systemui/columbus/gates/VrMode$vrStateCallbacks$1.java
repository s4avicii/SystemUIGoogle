package com.google.android.systemui.columbus.gates;

import android.service.vr.IVrStateCallbacks;

/* compiled from: VrMode.kt */
public final class VrMode$vrStateCallbacks$1 extends IVrStateCallbacks.Stub {
    public final /* synthetic */ VrMode this$0;

    public VrMode$vrStateCallbacks$1(VrMode vrMode) {
        this.this$0 = vrMode;
    }

    public final void onVrStateChanged(boolean z) {
        VrMode vrMode = this.this$0;
        vrMode.inVrMode = z;
        vrMode.setBlocking(z);
    }
}
