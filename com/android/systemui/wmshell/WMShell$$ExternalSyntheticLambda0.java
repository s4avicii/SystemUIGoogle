package com.android.systemui.wmshell;

import com.android.p012wm.shell.pip.Pip;
import com.android.systemui.model.SysUiState;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WMShell$$ExternalSyntheticLambda0 implements SysUiState.SysUiStateCallback {
    public final /* synthetic */ WMShell f$0;
    public final /* synthetic */ Pip f$1;

    public /* synthetic */ WMShell$$ExternalSyntheticLambda0(WMShell wMShell, Pip pip) {
        this.f$0 = wMShell;
        this.f$1 = pip;
    }

    public final void onSystemUiStateChanged(int i) {
        boolean z;
        WMShell wMShell = this.f$0;
        Pip pip = this.f$1;
        Objects.requireNonNull(wMShell);
        if ((8440396 & i) == 0) {
            z = true;
        } else {
            z = false;
        }
        pip.onSystemUiStateChanged(z, i);
    }
}
