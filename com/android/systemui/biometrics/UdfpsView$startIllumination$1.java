package com.android.systemui.biometrics;

import android.view.Surface;
import com.android.systemui.biometrics.UdfpsSurfaceView;

/* compiled from: UdfpsView.kt */
public final /* synthetic */ class UdfpsView$startIllumination$1 implements UdfpsSurfaceView.GhbmIlluminationListener {
    public final /* synthetic */ UdfpsView $tmp0;

    public UdfpsView$startIllumination$1(UdfpsView udfpsView) {
        this.$tmp0 = udfpsView;
    }

    public final void enableGhbm(Surface surface, Runnable runnable) {
        UdfpsView udfpsView = this.$tmp0;
        int i = UdfpsView.$r8$clinit;
        udfpsView.doIlluminate(surface, runnable);
    }
}
