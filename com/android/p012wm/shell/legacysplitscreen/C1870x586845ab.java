package com.android.p012wm.shell.legacysplitscreen;

import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreenController;
import com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda4;
import java.util.Objects;

/* renamed from: com.android.wm.shell.legacysplitscreen.LegacySplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1870x586845ab implements Runnable {
    public final /* synthetic */ LegacySplitScreenController.SplitScreenImpl f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ C1870x586845ab(LegacySplitScreenController.SplitScreenImpl splitScreenImpl, boolean z) {
        this.f$0 = splitScreenImpl;
        this.f$1 = z;
    }

    public final void run() {
        LegacySplitScreenController.SplitScreenImpl splitScreenImpl = this.f$0;
        boolean z = this.f$1;
        Objects.requireNonNull(splitScreenImpl);
        LegacySplitScreenController legacySplitScreenController = LegacySplitScreenController.this;
        Objects.requireNonNull(legacySplitScreenController);
        legacySplitScreenController.mMainExecutor.execute(new OverviewProxyService$1$$ExternalSyntheticLambda4(legacySplitScreenController, z, 1));
    }
}
