package com.android.p012wm.shell.startingsurface;

import android.window.StartingWindowRemovalInfo;
import com.android.p012wm.shell.startingsurface.StartingSurfaceDrawer;
import java.util.Objects;

/* renamed from: com.android.wm.shell.startingsurface.StartingWindowController$$ExternalSyntheticLambda3 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StartingWindowController$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ StartingWindowController f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ StartingWindowController$$ExternalSyntheticLambda3(StartingWindowController startingWindowController, int i) {
        this.f$0 = startingWindowController;
        this.f$1 = i;
    }

    public final void run() {
        TaskSnapshotWindow taskSnapshotWindow;
        StartingWindowController startingWindowController = this.f$0;
        int i = this.f$1;
        Objects.requireNonNull(startingWindowController);
        StartingSurfaceDrawer startingSurfaceDrawer = startingWindowController.mStartingSurfaceDrawer;
        Objects.requireNonNull(startingSurfaceDrawer);
        StartingSurfaceDrawer.StartingWindowRecord startingWindowRecord = startingSurfaceDrawer.mStartingWindowRecords.get(i);
        if (startingWindowRecord != null && (taskSnapshotWindow = startingWindowRecord.mTaskSnapshotWindow) != null && taskSnapshotWindow.mHasImeSurface) {
            StartingWindowRemovalInfo startingWindowRemovalInfo = startingSurfaceDrawer.mTmpRemovalInfo;
            startingWindowRemovalInfo.taskId = i;
            startingSurfaceDrawer.removeWindowSynced(startingWindowRemovalInfo, true);
        }
    }
}
