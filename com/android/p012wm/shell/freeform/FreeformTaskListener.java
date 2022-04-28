package com.android.p012wm.shell.freeform;

import android.app.ActivityManager;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Rect;
import android.util.Slog;
import android.util.SparseArray;
import android.view.SurfaceControl;
import androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.p012wm.shell.protolog.ShellProtoLogCache;
import com.android.p012wm.shell.protolog.ShellProtoLogGroup;
import com.android.p012wm.shell.protolog.ShellProtoLogImpl;
import java.io.PrintWriter;

/* renamed from: com.android.wm.shell.freeform.FreeformTaskListener */
public final class FreeformTaskListener implements ShellTaskOrganizer.TaskListener {
    public final SyncTransactionQueue mSyncQueue;
    public final SparseArray<State> mTasks = new SparseArray<>();

    /* renamed from: com.android.wm.shell.freeform.FreeformTaskListener$State */
    public static class State {
        public SurfaceControl mLeash;

        public State() {
        }

        public State(int i) {
        }
    }

    public final String toString() {
        return "FreeformTaskListener";
    }

    public final void dump(PrintWriter printWriter, String str) {
        String m = SupportMenuInflater$$ExternalSyntheticOutline0.m4m(str, "  ");
        printWriter.println(str + this);
        printWriter.println(m + this.mTasks.size() + " tasks");
    }

    public final void onTaskAppeared(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl) {
        if (this.mTasks.get(runningTaskInfo.taskId) == null) {
            if (ShellProtoLogCache.WM_SHELL_TASK_ORG_enabled) {
                long j = (long) runningTaskInfo.taskId;
                ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_TASK_ORG, -166960725, 1, (String) null, Long.valueOf(j));
            }
            State state = new State(0);
            state.mLeash = surfaceControl;
            this.mTasks.put(runningTaskInfo.taskId, state);
            this.mSyncQueue.runInSync(new FreeformTaskListener$$ExternalSyntheticLambda0(runningTaskInfo, surfaceControl, runningTaskInfo.configuration.windowConfiguration.getBounds()));
            return;
        }
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Task appeared more than once: #");
        m.append(runningTaskInfo.taskId);
        throw new RuntimeException(m.toString());
    }

    public final void onTaskInfoChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
        State state = this.mTasks.get(runningTaskInfo.taskId);
        if (state != null) {
            if (ShellProtoLogCache.WM_SHELL_TASK_ORG_enabled) {
                ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_TASK_ORG, -272049475, 1, (String) null, Long.valueOf((long) runningTaskInfo.taskId));
            }
            Rect bounds = runningTaskInfo.configuration.windowConfiguration.getBounds();
            this.mSyncQueue.runInSync(new FreeformTaskListener$$ExternalSyntheticLambda1(runningTaskInfo, state.mLeash, bounds));
            return;
        }
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Task info changed before appearing: #");
        m.append(runningTaskInfo.taskId);
        throw new RuntimeException(m.toString());
    }

    public final void onTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (this.mTasks.get(runningTaskInfo.taskId) == null) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Task already vanished: #");
            m.append(runningTaskInfo.taskId);
            Slog.e("FreeformTaskListener", m.toString());
            return;
        }
        if (ShellProtoLogCache.WM_SHELL_TASK_ORG_enabled) {
            ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_TASK_ORG, 1899149317, 1, (String) null, Long.valueOf((long) runningTaskInfo.taskId));
        }
        this.mTasks.remove(runningTaskInfo.taskId);
    }

    public FreeformTaskListener(SyncTransactionQueue syncTransactionQueue) {
        this.mSyncQueue = syncTransactionQueue;
    }
}
