package com.android.p012wm.shell.apppairs;

import android.app.ActivityManager;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.hidl.base.V1_0.DebugInfo$$ExternalSyntheticOutline0;
import android.view.SurfaceControl;
import android.view.SurfaceSession;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.common.DisplayController;
import com.android.p012wm.shell.common.DisplayImeController;
import com.android.p012wm.shell.common.DisplayInsetsController;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.p012wm.shell.common.split.SplitLayout;
import com.android.p012wm.shell.common.split.SplitWindowManager;
import java.io.PrintWriter;
import java.util.Objects;

/* renamed from: com.android.wm.shell.apppairs.AppPair */
public final class AppPair implements ShellTaskOrganizer.TaskListener, SplitLayout.SplitLayoutHandler {
    public final AppPairsController mController;
    public SurfaceControl mDimLayer1;
    public SurfaceControl mDimLayer2;
    public final DisplayController mDisplayController;
    public final DisplayImeController mDisplayImeController;
    public final DisplayInsetsController mDisplayInsetsController;
    public final C17851 mParentContainerCallbacks = new SplitWindowManager.ParentContainerCallbacks() {
        public final void attachToParentSurface(SurfaceControl.Builder builder) {
            builder.setParent(AppPair.this.mRootTaskLeash);
        }

        public final void onLeashReady(SurfaceControl surfaceControl) {
            AppPair.this.mSyncQueue.runInSync(new AppPair$1$$ExternalSyntheticLambda0(this, surfaceControl));
        }
    };
    public ActivityManager.RunningTaskInfo mRootTaskInfo;
    public SurfaceControl mRootTaskLeash;
    public SplitLayout mSplitLayout;
    public final SurfaceSession mSurfaceSession = new SurfaceSession();
    public final SyncTransactionQueue mSyncQueue;
    public ActivityManager.RunningTaskInfo mTaskInfo1;
    public ActivityManager.RunningTaskInfo mTaskInfo2;
    public SurfaceControl mTaskLeash1;
    public SurfaceControl mTaskLeash2;

    public final int getSplitItemPosition(WindowContainerToken windowContainerToken) {
        if (windowContainerToken == null) {
            return -1;
        }
        if (windowContainerToken.equals(this.mTaskInfo1.getToken())) {
            return 0;
        }
        return windowContainerToken.equals(this.mTaskInfo2.getToken()) ? 1 : -1;
    }

    public final void dump(PrintWriter printWriter, String str) {
        String m = SupportMenuInflater$$ExternalSyntheticOutline0.m4m(str, "  ");
        printWriter.println(str + this);
        if (this.mRootTaskInfo != null) {
            StringBuilder m2 = DebugInfo$$ExternalSyntheticOutline0.m2m(m, "Root taskId=");
            m2.append(this.mRootTaskInfo.taskId);
            m2.append(" winMode=");
            m2.append(this.mRootTaskInfo.getWindowingMode());
            printWriter.println(m2.toString());
        }
        if (this.mTaskInfo1 != null) {
            StringBuilder m3 = DebugInfo$$ExternalSyntheticOutline0.m2m(m, "1 taskId=");
            m3.append(this.mTaskInfo1.taskId);
            m3.append(" winMode=");
            m3.append(this.mTaskInfo1.getWindowingMode());
            printWriter.println(m3.toString());
        }
        if (this.mTaskInfo2 != null) {
            StringBuilder m4 = DebugInfo$$ExternalSyntheticOutline0.m2m(m, "2 taskId=");
            m4.append(this.mTaskInfo2.taskId);
            m4.append(" winMode=");
            m4.append(this.mTaskInfo2.getWindowingMode());
            printWriter.println(m4.toString());
        }
    }

    public final int getRootTaskId() {
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mRootTaskInfo;
        if (runningTaskInfo != null) {
            return runningTaskInfo.taskId;
        }
        return -1;
    }

    public final void onLayoutPositionChanging(SplitLayout splitLayout) {
        this.mSyncQueue.runInSync(new AppPair$$ExternalSyntheticLambda7(this, splitLayout));
    }

    public final void onLayoutSizeChanged(SplitLayout splitLayout) {
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        splitLayout.applyTaskChanges(windowContainerTransaction, this.mTaskInfo1, this.mTaskInfo2);
        this.mSyncQueue.queue(windowContainerTransaction);
        this.mSyncQueue.runInSync(new AppPair$$ExternalSyntheticLambda8(this, splitLayout));
    }

    public final void onLayoutSizeChanging(SplitLayout splitLayout) {
        this.mSyncQueue.runInSync(new AppPair$$ExternalSyntheticLambda2(this, splitLayout, 0));
    }

    public final void onSnappedToDismiss(boolean z) {
        ActivityManager.RunningTaskInfo runningTaskInfo;
        if (z) {
            runningTaskInfo = this.mTaskInfo1;
        } else {
            runningTaskInfo = this.mTaskInfo2;
        }
        unpair(runningTaskInfo.token);
    }

    public final void onTaskAppeared(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl) {
        int i;
        int i2;
        ActivityManager.RunningTaskInfo runningTaskInfo2 = this.mRootTaskInfo;
        if (runningTaskInfo2 == null || (i = runningTaskInfo.taskId) == runningTaskInfo2.taskId) {
            this.mRootTaskInfo = runningTaskInfo;
            this.mRootTaskLeash = surfaceControl;
        } else {
            ActivityManager.RunningTaskInfo runningTaskInfo3 = this.mTaskInfo1;
            int i3 = -1;
            if (runningTaskInfo3 != null) {
                i2 = runningTaskInfo3.taskId;
            } else {
                i2 = -1;
            }
            if (i == i2) {
                this.mTaskInfo1 = runningTaskInfo;
                this.mTaskLeash1 = surfaceControl;
                this.mSyncQueue.runInSync(new AppPair$$ExternalSyntheticLambda3(this));
            } else {
                ActivityManager.RunningTaskInfo runningTaskInfo4 = this.mTaskInfo2;
                if (runningTaskInfo4 != null) {
                    i3 = runningTaskInfo4.taskId;
                }
                if (i == i3) {
                    this.mTaskInfo2 = runningTaskInfo;
                    this.mTaskLeash2 = surfaceControl;
                    this.mSyncQueue.runInSync(new AppPair$$ExternalSyntheticLambda4(this));
                } else {
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Unknown task=");
                    m.append(runningTaskInfo.taskId);
                    throw new IllegalStateException(m.toString());
                }
            }
        }
        if (this.mTaskLeash1 != null && this.mTaskLeash2 != null) {
            this.mSplitLayout.init();
            this.mSyncQueue.runInSync(new AppPair$$ExternalSyntheticLambda5(this));
        }
    }

    public final void onTaskInfoChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
        int i;
        if (!runningTaskInfo.supportsMultiWindow) {
            AppPairsController appPairsController = this.mController;
            int i2 = this.mRootTaskInfo.taskId;
            Objects.requireNonNull(appPairsController);
            appPairsController.unpair(i2, true);
        } else if (runningTaskInfo.taskId == getRootTaskId()) {
            if (this.mRootTaskInfo.isVisible != runningTaskInfo.isVisible) {
                this.mSyncQueue.runInSync(new AppPair$$ExternalSyntheticLambda6(this, runningTaskInfo));
            }
            this.mRootTaskInfo = runningTaskInfo;
            SplitLayout splitLayout = this.mSplitLayout;
            if (splitLayout != null && splitLayout.updateConfiguration(runningTaskInfo.configuration)) {
                onLayoutSizeChanged(this.mSplitLayout);
            }
        } else {
            int i3 = runningTaskInfo.taskId;
            ActivityManager.RunningTaskInfo runningTaskInfo2 = this.mTaskInfo1;
            int i4 = -1;
            if (runningTaskInfo2 != null) {
                i = runningTaskInfo2.taskId;
            } else {
                i = -1;
            }
            if (i3 == i) {
                this.mTaskInfo1 = runningTaskInfo;
                return;
            }
            ActivityManager.RunningTaskInfo runningTaskInfo3 = this.mTaskInfo2;
            if (runningTaskInfo3 != null) {
                i4 = runningTaskInfo3.taskId;
            }
            if (i3 == i4) {
                this.mTaskInfo2 = runningTaskInfo;
                return;
            }
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Unknown task=");
            m.append(runningTaskInfo.taskId);
            throw new IllegalStateException(m.toString());
        }
    }

    public final void onTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) {
        int i;
        if (runningTaskInfo.taskId == getRootTaskId()) {
            this.mController.unpair(this.mRootTaskInfo.taskId, false);
            return;
        }
        int i2 = runningTaskInfo.taskId;
        ActivityManager.RunningTaskInfo runningTaskInfo2 = this.mTaskInfo1;
        int i3 = -1;
        if (runningTaskInfo2 != null) {
            i = runningTaskInfo2.taskId;
        } else {
            i = -1;
        }
        if (i2 == i) {
            AppPairsController appPairsController = this.mController;
            int i4 = this.mRootTaskInfo.taskId;
            Objects.requireNonNull(appPairsController);
            appPairsController.unpair(i4, true);
            this.mSyncQueue.runInSync(new AppPair$$ExternalSyntheticLambda0(this, 0));
            return;
        }
        ActivityManager.RunningTaskInfo runningTaskInfo3 = this.mTaskInfo2;
        if (runningTaskInfo3 != null) {
            i3 = runningTaskInfo3.taskId;
        }
        if (i2 == i3) {
            AppPairsController appPairsController2 = this.mController;
            int i5 = this.mRootTaskInfo.taskId;
            Objects.requireNonNull(appPairsController2);
            appPairsController2.unpair(i5, true);
            this.mSyncQueue.runInSync(new AppPair$$ExternalSyntheticLambda1(this, 0));
        }
    }

    public final void setLayoutOffsetTarget(int i, SplitLayout splitLayout) {
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        splitLayout.applyLayoutOffsetTarget(windowContainerTransaction, i, this.mTaskInfo1, this.mTaskInfo2);
        AppPairsController appPairsController = this.mController;
        Objects.requireNonNull(appPairsController);
        appPairsController.mTaskOrganizer.applyTransaction(windowContainerTransaction);
    }

    public final String toString() {
        StringBuilder m = DebugInfo$$ExternalSyntheticOutline0.m2m("AppPair", "#");
        m.append(getRootTaskId());
        return m.toString();
    }

    public final void unpair(WindowContainerToken windowContainerToken) {
        boolean z;
        WindowContainerToken windowContainerToken2 = this.mTaskInfo1.token;
        WindowContainerToken windowContainerToken3 = this.mTaskInfo2.token;
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        boolean z2 = true;
        WindowContainerTransaction reorder = windowContainerTransaction.setHidden(this.mRootTaskInfo.token, true).reorder(this.mRootTaskInfo.token, false);
        if (windowContainerToken2 == windowContainerToken) {
            z = true;
        } else {
            z = false;
        }
        WindowContainerTransaction reparent = reorder.reparent(windowContainerToken2, (WindowContainerToken) null, z);
        if (windowContainerToken3 != windowContainerToken) {
            z2 = false;
        }
        reparent.reparent(windowContainerToken3, (WindowContainerToken) null, z2).setWindowingMode(windowContainerToken2, 0).setWindowingMode(windowContainerToken3, 0);
        AppPairsController appPairsController = this.mController;
        Objects.requireNonNull(appPairsController);
        appPairsController.mTaskOrganizer.applyTransaction(windowContainerTransaction);
        this.mTaskInfo1 = null;
        this.mTaskInfo2 = null;
        this.mSplitLayout.release();
        this.mSplitLayout = null;
    }

    public AppPair(AppPairsController appPairsController) {
        this.mController = appPairsController;
        Objects.requireNonNull(appPairsController);
        this.mSyncQueue = appPairsController.mSyncQueue;
        this.mDisplayController = appPairsController.mDisplayController;
        this.mDisplayImeController = appPairsController.mDisplayImeController;
        this.mDisplayInsetsController = appPairsController.mDisplayInsetsController;
    }

    public final void attachChildSurfaceToTask(int i, SurfaceControl.Builder builder) {
        int i2;
        if (getRootTaskId() == i) {
            builder.setParent(this.mRootTaskLeash);
            return;
        }
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo1;
        int i3 = -1;
        if (runningTaskInfo != null) {
            i2 = runningTaskInfo.taskId;
        } else {
            i2 = -1;
        }
        if (i2 == i) {
            builder.setParent(this.mTaskLeash1);
            return;
        }
        ActivityManager.RunningTaskInfo runningTaskInfo2 = this.mTaskInfo2;
        if (runningTaskInfo2 != null) {
            i3 = runningTaskInfo2.taskId;
        }
        if (i3 == i) {
            builder.setParent(this.mTaskLeash2);
            return;
        }
        throw new IllegalArgumentException(VendorAtomValue$$ExternalSyntheticOutline0.m0m("There is no surface for taskId=", i));
    }
}
