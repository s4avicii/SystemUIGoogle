package com.android.systemui.wmshell;

import com.android.systemui.model.SysUiState;
import com.android.systemui.wmshell.BubblesManager;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubblesManager$5$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ BubblesManager.C17525 f$0;
    public final /* synthetic */ SysUiState f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ BubblesManager$5$$ExternalSyntheticLambda5(BubblesManager.C17525 r1, SysUiState sysUiState, boolean z) {
        this.f$0 = r1;
        this.f$1 = sysUiState;
        this.f$2 = z;
    }

    public final void run() {
        BubblesManager.C17525 r0 = this.f$0;
        SysUiState sysUiState = this.f$1;
        boolean z = this.f$2;
        Objects.requireNonNull(r0);
        sysUiState.setFlag(16384, z);
        sysUiState.commitUpdate(BubblesManager.this.mContext.getDisplayId());
        if (!z) {
            sysUiState.setFlag(8388608, false);
            sysUiState.commitUpdate(BubblesManager.this.mContext.getDisplayId());
        }
    }
}
