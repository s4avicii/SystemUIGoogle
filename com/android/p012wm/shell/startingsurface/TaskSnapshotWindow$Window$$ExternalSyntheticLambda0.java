package com.android.p012wm.shell.startingsurface;

import android.os.RemoteException;
import android.util.MergedConfiguration;
import android.view.SurfaceControl;
import com.android.p012wm.shell.startingsurface.TaskSnapshotWindow;
import java.util.Objects;

/* renamed from: com.android.wm.shell.startingsurface.TaskSnapshotWindow$Window$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class TaskSnapshotWindow$Window$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ TaskSnapshotWindow.Window f$0;
    public final /* synthetic */ MergedConfiguration f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ TaskSnapshotWindow$Window$$ExternalSyntheticLambda0(TaskSnapshotWindow.Window window, MergedConfiguration mergedConfiguration, boolean z) {
        this.f$0 = window;
        this.f$1 = mergedConfiguration;
        this.f$2 = z;
    }

    public final void run() {
        TaskSnapshotWindow.Window window = this.f$0;
        MergedConfiguration mergedConfiguration = this.f$1;
        boolean z = this.f$2;
        if (mergedConfiguration != null && window.mOuter.mOrientationOnCreation != mergedConfiguration.getMergedConfiguration().orientation) {
            TaskSnapshotWindow taskSnapshotWindow = window.mOuter;
            Objects.requireNonNull(taskSnapshotWindow);
            taskSnapshotWindow.mSplashScreenExecutor.executeDelayed(taskSnapshotWindow.mClearWindowHandler, 0);
        } else if (z) {
            TaskSnapshotWindow taskSnapshotWindow2 = window.mOuter;
            if (taskSnapshotWindow2.mHasDrawn) {
                try {
                    taskSnapshotWindow2.mSession.finishDrawing(taskSnapshotWindow2.mWindow, (SurfaceControl.Transaction) null);
                } catch (RemoteException unused) {
                    taskSnapshotWindow2.mSplashScreenExecutor.executeDelayed(taskSnapshotWindow2.mClearWindowHandler, 0);
                }
            }
        } else {
            Objects.requireNonNull(window);
        }
    }
}
