package com.android.p012wm.shell.startingsurface;

import android.os.Bundle;
import android.os.RemoteCallback;
import com.android.systemui.biometrics.AuthController$$ExternalSyntheticLambda0;
import java.util.Objects;

/* renamed from: com.android.wm.shell.startingsurface.StartingSurfaceDrawer$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StartingSurfaceDrawer$$ExternalSyntheticLambda0 implements RemoteCallback.OnResultListener {
    public final /* synthetic */ StartingSurfaceDrawer f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ StartingSurfaceDrawer$$ExternalSyntheticLambda0(StartingSurfaceDrawer startingSurfaceDrawer, int i) {
        this.f$0 = startingSurfaceDrawer;
        this.f$1 = i;
    }

    public final void onResult(Bundle bundle) {
        StartingSurfaceDrawer startingSurfaceDrawer = this.f$0;
        int i = this.f$1;
        Objects.requireNonNull(startingSurfaceDrawer);
        startingSurfaceDrawer.mSplashScreenExecutor.execute(new AuthController$$ExternalSyntheticLambda0(startingSurfaceDrawer, i, 1));
    }
}
