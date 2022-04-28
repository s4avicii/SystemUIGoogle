package com.android.p012wm.shell.legacysplitscreen;

import android.window.WindowContainerTransaction;
import java.util.Objects;

/* renamed from: com.android.wm.shell.legacysplitscreen.LegacySplitScreenTransitions$$ExternalSyntheticLambda4 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class LegacySplitScreenTransitions$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ LegacySplitScreenTransitions f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ WindowContainerTransaction f$2;

    public /* synthetic */ LegacySplitScreenTransitions$$ExternalSyntheticLambda4(LegacySplitScreenTransitions legacySplitScreenTransitions, boolean z, WindowContainerTransaction windowContainerTransaction) {
        this.f$0 = legacySplitScreenTransitions;
        this.f$1 = z;
        this.f$2 = windowContainerTransaction;
    }

    public final void run() {
        LegacySplitScreenTransitions legacySplitScreenTransitions = this.f$0;
        boolean z = this.f$1;
        WindowContainerTransaction windowContainerTransaction = this.f$2;
        Objects.requireNonNull(legacySplitScreenTransitions);
        legacySplitScreenTransitions.mDismissFromSnap = z;
        legacySplitScreenTransitions.mPendingDismiss = legacySplitScreenTransitions.mTransitions.startTransition(22, windowContainerTransaction, legacySplitScreenTransitions);
    }
}
