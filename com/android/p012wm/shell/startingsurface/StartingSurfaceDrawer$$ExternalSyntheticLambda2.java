package com.android.p012wm.shell.startingsurface;

import android.window.StartingWindowRemovalInfo;
import java.util.Objects;

/* renamed from: com.android.wm.shell.startingsurface.StartingSurfaceDrawer$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StartingSurfaceDrawer$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ StartingSurfaceDrawer f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ StartingSurfaceDrawer$$ExternalSyntheticLambda2(StartingSurfaceDrawer startingSurfaceDrawer, int i) {
        this.f$0 = startingSurfaceDrawer;
        this.f$1 = i;
    }

    public final void run() {
        StartingSurfaceDrawer startingSurfaceDrawer = this.f$0;
        int i = this.f$1;
        Objects.requireNonNull(startingSurfaceDrawer);
        StartingWindowRemovalInfo startingWindowRemovalInfo = startingSurfaceDrawer.mTmpRemovalInfo;
        startingWindowRemovalInfo.taskId = i;
        startingSurfaceDrawer.removeWindowSynced(startingWindowRemovalInfo, true);
    }
}
