package com.android.p012wm.shell.startingsurface;

import java.util.Objects;

/* renamed from: com.android.wm.shell.startingsurface.StartingWindowController$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StartingWindowController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ StartingWindowController f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ StartingWindowController$$ExternalSyntheticLambda2(StartingWindowController startingWindowController, int i) {
        this.f$0 = startingWindowController;
        this.f$1 = i;
    }

    public final void run() {
        StartingWindowController startingWindowController = this.f$0;
        int i = this.f$1;
        Objects.requireNonNull(startingWindowController);
        StartingSurfaceDrawer startingSurfaceDrawer = startingWindowController.mStartingSurfaceDrawer;
        Objects.requireNonNull(startingSurfaceDrawer);
        startingSurfaceDrawer.onAppSplashScreenViewRemoved(i, true);
    }
}
