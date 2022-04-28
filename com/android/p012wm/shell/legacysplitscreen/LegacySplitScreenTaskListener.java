package com.android.p012wm.shell.legacysplitscreen;

import android.app.ActivityManager;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0;
import android.graphics.Point;
import android.graphics.Rect;
import android.hidl.base.V1_0.DebugInfo$$ExternalSyntheticOutline0;
import android.util.SparseArray;
import android.view.SurfaceControl;
import android.view.SurfaceSession;
import androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.p012wm.shell.transition.Transitions;
import java.io.PrintWriter;
import java.util.Objects;

/* renamed from: com.android.wm.shell.legacysplitscreen.LegacySplitScreenTaskListener */
public final class LegacySplitScreenTaskListener implements ShellTaskOrganizer.TaskListener {
    public Rect mHomeBounds = new Rect();
    public final SparseArray<SurfaceControl> mLeashByTaskId = new SparseArray<>();
    public final SparseArray<Point> mPositionByTaskId = new SparseArray<>();
    public ActivityManager.RunningTaskInfo mPrimary;
    public SurfaceControl mPrimaryDim;
    public SurfaceControl mPrimarySurface;
    public ActivityManager.RunningTaskInfo mSecondary;
    public SurfaceControl mSecondaryDim;
    public SurfaceControl mSecondarySurface;
    public final LegacySplitScreenController mSplitScreenController;
    public boolean mSplitScreenSupported = false;
    public final LegacySplitScreenTransitions mSplitTransitions;
    public final SurfaceSession mSurfaceSession = new SurfaceSession();
    public final SyncTransactionQueue mSyncQueue;
    public final ShellTaskOrganizer mTaskOrganizer;

    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0107, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x002f, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onTaskAppeared(android.app.ActivityManager.RunningTaskInfo r12, android.view.SurfaceControl r13) {
        /*
            r11 = this;
            monitor-enter(r11)
            boolean r0 = r12.hasParentTask()     // Catch:{ all -> 0x0030 }
            r1 = 1
            if (r0 == 0) goto L_0x0033
            android.util.SparseArray<android.view.SurfaceControl> r0 = r11.mLeashByTaskId     // Catch:{ all -> 0x0030 }
            int r2 = r12.taskId     // Catch:{ all -> 0x0030 }
            r0.put(r2, r13)     // Catch:{ all -> 0x0030 }
            android.util.SparseArray<android.graphics.Point> r0 = r11.mPositionByTaskId     // Catch:{ all -> 0x0030 }
            int r2 = r12.taskId     // Catch:{ all -> 0x0030 }
            android.graphics.Point r3 = new android.graphics.Point     // Catch:{ all -> 0x0030 }
            android.graphics.Point r4 = r12.positionInParent     // Catch:{ all -> 0x0030 }
            r3.<init>(r4)     // Catch:{ all -> 0x0030 }
            r0.put(r2, r3)     // Catch:{ all -> 0x0030 }
            boolean r0 = com.android.p012wm.shell.transition.Transitions.ENABLE_SHELL_TRANSITIONS     // Catch:{ all -> 0x0030 }
            if (r0 == 0) goto L_0x0022
            goto L_0x002e
        L_0x0022:
            android.graphics.Point r12 = r12.positionInParent     // Catch:{ all -> 0x0030 }
            com.android.wm.shell.common.SyncTransactionQueue r0 = r11.mSyncQueue     // Catch:{ all -> 0x0030 }
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenTaskListener$$ExternalSyntheticLambda0 r2 = new com.android.wm.shell.legacysplitscreen.LegacySplitScreenTaskListener$$ExternalSyntheticLambda0     // Catch:{ all -> 0x0030 }
            r2.<init>(r13, r12, r1)     // Catch:{ all -> 0x0030 }
            r0.runInSync(r2)     // Catch:{ all -> 0x0030 }
        L_0x002e:
            monitor-exit(r11)     // Catch:{ all -> 0x0030 }
            return
        L_0x0030:
            r12 = move-exception
            goto L_0x0108
        L_0x0033:
            int r0 = r12.getWindowingMode()     // Catch:{ all -> 0x0030 }
            r2 = 3
            r3 = 2
            r4 = 4
            r5 = 0
            r6 = 0
            if (r0 != r2) goto L_0x005e
            boolean r0 = com.android.p012wm.shell.protolog.ShellProtoLogCache.WM_SHELL_TASK_ORG_enabled     // Catch:{ all -> 0x0030 }
            if (r0 == 0) goto L_0x0059
            java.lang.String r0 = "LegacySplitScreenTaskListener"
            int r2 = r12.taskId     // Catch:{ all -> 0x0030 }
            long r7 = (long) r2     // Catch:{ all -> 0x0030 }
            com.android.wm.shell.protolog.ShellProtoLogGroup r2 = com.android.p012wm.shell.protolog.ShellProtoLogGroup.WM_SHELL_TASK_ORG     // Catch:{ all -> 0x0030 }
            r9 = -1362429294(0xffffffffaecafa92, float:-9.230407E-11)
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x0030 }
            r3[r6] = r0     // Catch:{ all -> 0x0030 }
            java.lang.Long r0 = java.lang.Long.valueOf(r7)     // Catch:{ all -> 0x0030 }
            r3[r1] = r0     // Catch:{ all -> 0x0030 }
            com.android.p012wm.shell.protolog.ShellProtoLogImpl.m80v(r2, r9, r4, r5, r3)     // Catch:{ all -> 0x0030 }
        L_0x0059:
            r11.mPrimary = r12     // Catch:{ all -> 0x0030 }
            r11.mPrimarySurface = r13     // Catch:{ all -> 0x0030 }
            goto L_0x00a4
        L_0x005e:
            if (r0 != r4) goto L_0x0080
            boolean r0 = com.android.p012wm.shell.protolog.ShellProtoLogCache.WM_SHELL_TASK_ORG_enabled     // Catch:{ all -> 0x0030 }
            if (r0 == 0) goto L_0x007b
            java.lang.String r0 = "LegacySplitScreenTaskListener"
            int r2 = r12.taskId     // Catch:{ all -> 0x0030 }
            long r7 = (long) r2     // Catch:{ all -> 0x0030 }
            com.android.wm.shell.protolog.ShellProtoLogGroup r2 = com.android.p012wm.shell.protolog.ShellProtoLogGroup.WM_SHELL_TASK_ORG     // Catch:{ all -> 0x0030 }
            r9 = 982027396(0x3a888c84, float:0.0010417853)
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x0030 }
            r3[r6] = r0     // Catch:{ all -> 0x0030 }
            java.lang.Long r0 = java.lang.Long.valueOf(r7)     // Catch:{ all -> 0x0030 }
            r3[r1] = r0     // Catch:{ all -> 0x0030 }
            com.android.p012wm.shell.protolog.ShellProtoLogImpl.m80v(r2, r9, r4, r5, r3)     // Catch:{ all -> 0x0030 }
        L_0x007b:
            r11.mSecondary = r12     // Catch:{ all -> 0x0030 }
            r11.mSecondarySurface = r13     // Catch:{ all -> 0x0030 }
            goto L_0x00a4
        L_0x0080:
            boolean r13 = com.android.p012wm.shell.protolog.ShellProtoLogCache.WM_SHELL_TASK_ORG_enabled     // Catch:{ all -> 0x0030 }
            if (r13 == 0) goto L_0x00a4
            java.lang.String r13 = "LegacySplitScreenTaskListener"
            int r12 = r12.taskId     // Catch:{ all -> 0x0030 }
            long r7 = (long) r12     // Catch:{ all -> 0x0030 }
            long r9 = (long) r0     // Catch:{ all -> 0x0030 }
            com.android.wm.shell.protolog.ShellProtoLogGroup r12 = com.android.p012wm.shell.protolog.ShellProtoLogGroup.WM_SHELL_TASK_ORG     // Catch:{ all -> 0x0030 }
            r0 = -298656957(0xffffffffee32db43, float:-1.3838351E28)
            r4 = 20
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x0030 }
            r2[r6] = r13     // Catch:{ all -> 0x0030 }
            java.lang.Long r13 = java.lang.Long.valueOf(r7)     // Catch:{ all -> 0x0030 }
            r2[r1] = r13     // Catch:{ all -> 0x0030 }
            java.lang.Long r13 = java.lang.Long.valueOf(r9)     // Catch:{ all -> 0x0030 }
            r2[r3] = r13     // Catch:{ all -> 0x0030 }
            com.android.p012wm.shell.protolog.ShellProtoLogImpl.m80v(r12, r0, r4, r5, r2)     // Catch:{ all -> 0x0030 }
        L_0x00a4:
            boolean r12 = r11.mSplitScreenSupported     // Catch:{ all -> 0x0030 }
            if (r12 != 0) goto L_0x0106
            android.view.SurfaceControl r12 = r11.mPrimarySurface     // Catch:{ all -> 0x0030 }
            if (r12 == 0) goto L_0x0106
            android.view.SurfaceControl r12 = r11.mSecondarySurface     // Catch:{ all -> 0x0030 }
            if (r12 == 0) goto L_0x0106
            r11.mSplitScreenSupported = r1     // Catch:{ all -> 0x0030 }
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenController r12 = r11.mSplitScreenController     // Catch:{ all -> 0x0030 }
            java.util.Objects.requireNonNull(r12)     // Catch:{ all -> 0x0030 }
            android.window.WindowContainerTransaction r13 = new android.window.WindowContainerTransaction     // Catch:{ all -> 0x0030 }
            r13.<init>()     // Catch:{ all -> 0x0030 }
            com.android.wm.shell.legacysplitscreen.LegacySplitDisplayLayout r0 = r12.mSplitLayout     // Catch:{ all -> 0x0030 }
            com.android.internal.policy.DividerSnapAlgorithm r0 = r0.getSnapAlgorithm()     // Catch:{ all -> 0x0030 }
            com.android.internal.policy.DividerSnapAlgorithm$SnapTarget r0 = r0.getMiddleTarget()     // Catch:{ all -> 0x0030 }
            int r0 = r0.position     // Catch:{ all -> 0x0030 }
            com.android.wm.shell.legacysplitscreen.LegacySplitDisplayLayout r2 = r12.mSplitLayout     // Catch:{ all -> 0x0030 }
            r2.resizeSplits(r0, r13)     // Catch:{ all -> 0x0030 }
            com.android.wm.shell.ShellTaskOrganizer r12 = r12.mTaskOrganizer     // Catch:{ all -> 0x0030 }
            r12.applyTransaction(r13)     // Catch:{ all -> 0x0030 }
            boolean r12 = com.android.p012wm.shell.protolog.ShellProtoLogCache.WM_SHELL_TASK_ORG_enabled     // Catch:{ all -> 0x0030 }
            if (r12 == 0) goto L_0x00e4
            java.lang.String r12 = "LegacySplitScreenTaskListener"
            com.android.wm.shell.protolog.ShellProtoLogGroup r13 = com.android.p012wm.shell.protolog.ShellProtoLogGroup.WM_SHELL_TASK_ORG     // Catch:{ all -> 0x0030 }
            r0 = 473543554(0x1c39b382, float:6.1443374E-22)
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x0030 }
            r1[r6] = r12     // Catch:{ all -> 0x0030 }
            com.android.p012wm.shell.protolog.ShellProtoLogImpl.m80v(r13, r0, r6, r5, r1)     // Catch:{ all -> 0x0030 }
        L_0x00e4:
            android.view.SurfaceControl$Transaction r12 = r11.getTransaction()     // Catch:{ all -> 0x0030 }
            android.view.SurfaceControl r13 = r11.mPrimarySurface     // Catch:{ all -> 0x0030 }
            java.lang.String r0 = "Primary Divider Dim"
            android.view.SurfaceSession r1 = r11.mSurfaceSession     // Catch:{ all -> 0x0030 }
            android.view.SurfaceControl r13 = com.android.p012wm.shell.common.SurfaceUtils.makeDimLayer(r12, r13, r0, r1)     // Catch:{ all -> 0x0030 }
            r11.mPrimaryDim = r13     // Catch:{ all -> 0x0030 }
            android.view.SurfaceControl r13 = r11.mSecondarySurface     // Catch:{ all -> 0x0030 }
            java.lang.String r0 = "Secondary Divider Dim"
            android.view.SurfaceSession r1 = r11.mSurfaceSession     // Catch:{ all -> 0x0030 }
            android.view.SurfaceControl r13 = com.android.p012wm.shell.common.SurfaceUtils.makeDimLayer(r12, r13, r0, r1)     // Catch:{ all -> 0x0030 }
            r11.mSecondaryDim = r13     // Catch:{ all -> 0x0030 }
            r12.apply()     // Catch:{ all -> 0x0030 }
            r11.releaseTransaction(r12)     // Catch:{ all -> 0x0030 }
        L_0x0106:
            monitor-exit(r11)     // Catch:{ all -> 0x0030 }
            return
        L_0x0108:
            monitor-exit(r11)     // Catch:{ all -> 0x0030 }
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.legacysplitscreen.LegacySplitScreenTaskListener.onTaskAppeared(android.app.ActivityManager$RunningTaskInfo, android.view.SurfaceControl):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x006b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onTaskVanished(android.app.ActivityManager.RunningTaskInfo r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            android.util.SparseArray<android.graphics.Point> r0 = r4.mPositionByTaskId     // Catch:{ all -> 0x006c }
            int r1 = r5.taskId     // Catch:{ all -> 0x006c }
            r0.remove(r1)     // Catch:{ all -> 0x006c }
            boolean r0 = r5.hasParentTask()     // Catch:{ all -> 0x006c }
            if (r0 == 0) goto L_0x0017
            android.util.SparseArray<android.view.SurfaceControl> r0 = r4.mLeashByTaskId     // Catch:{ all -> 0x006c }
            int r5 = r5.taskId     // Catch:{ all -> 0x006c }
            r0.remove(r5)     // Catch:{ all -> 0x006c }
            monitor-exit(r4)     // Catch:{ all -> 0x006c }
            return
        L_0x0017:
            android.app.ActivityManager$RunningTaskInfo r0 = r4.mPrimary     // Catch:{ all -> 0x006c }
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x0029
            android.window.WindowContainerToken r3 = r5.token     // Catch:{ all -> 0x006c }
            android.window.WindowContainerToken r0 = r0.token     // Catch:{ all -> 0x006c }
            boolean r0 = r3.equals(r0)     // Catch:{ all -> 0x006c }
            if (r0 == 0) goto L_0x0029
            r0 = r1
            goto L_0x002a
        L_0x0029:
            r0 = r2
        L_0x002a:
            android.app.ActivityManager$RunningTaskInfo r3 = r4.mSecondary     // Catch:{ all -> 0x006c }
            if (r3 == 0) goto L_0x0039
            android.window.WindowContainerToken r5 = r5.token     // Catch:{ all -> 0x006c }
            android.window.WindowContainerToken r3 = r3.token     // Catch:{ all -> 0x006c }
            boolean r5 = r5.equals(r3)     // Catch:{ all -> 0x006c }
            if (r5 == 0) goto L_0x0039
            goto L_0x003a
        L_0x0039:
            r1 = r2
        L_0x003a:
            boolean r5 = r4.mSplitScreenSupported     // Catch:{ all -> 0x006c }
            if (r5 == 0) goto L_0x006a
            if (r0 != 0) goto L_0x0042
            if (r1 == 0) goto L_0x006a
        L_0x0042:
            r4.mSplitScreenSupported = r2     // Catch:{ all -> 0x006c }
            android.view.SurfaceControl$Transaction r5 = r4.getTransaction()     // Catch:{ all -> 0x006c }
            android.view.SurfaceControl r0 = r4.mPrimaryDim     // Catch:{ all -> 0x006c }
            r5.remove(r0)     // Catch:{ all -> 0x006c }
            android.view.SurfaceControl r0 = r4.mSecondaryDim     // Catch:{ all -> 0x006c }
            r5.remove(r0)     // Catch:{ all -> 0x006c }
            android.view.SurfaceControl r0 = r4.mPrimarySurface     // Catch:{ all -> 0x006c }
            r5.remove(r0)     // Catch:{ all -> 0x006c }
            android.view.SurfaceControl r0 = r4.mSecondarySurface     // Catch:{ all -> 0x006c }
            r5.remove(r0)     // Catch:{ all -> 0x006c }
            r5.apply()     // Catch:{ all -> 0x006c }
            r4.releaseTransaction(r5)     // Catch:{ all -> 0x006c }
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenController r5 = r4.mSplitScreenController     // Catch:{ all -> 0x006c }
            java.util.Objects.requireNonNull(r5)     // Catch:{ all -> 0x006c }
            r5.removeDivider()     // Catch:{ all -> 0x006c }
        L_0x006a:
            monitor-exit(r4)     // Catch:{ all -> 0x006c }
            return
        L_0x006c:
            r5 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x006c }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.legacysplitscreen.LegacySplitScreenTaskListener.onTaskVanished(android.app.ActivityManager$RunningTaskInfo):void");
    }

    public final String toString() {
        return "LegacySplitScreenTaskListener";
    }

    public final void attachChildSurfaceToTask(int i, SurfaceControl.Builder builder) {
        if (this.mLeashByTaskId.contains(i)) {
            builder.setParent(this.mLeashByTaskId.get(i));
            return;
        }
        throw new IllegalArgumentException(VendorAtomValue$$ExternalSyntheticOutline0.m0m("There is no surface for taskId=", i));
    }

    public final void dump(PrintWriter printWriter, String str) {
        String m = SupportMenuInflater$$ExternalSyntheticOutline0.m4m(str, "  ");
        printWriter.println(str + this);
        printWriter.println(m + "mSplitScreenSupported=" + this.mSplitScreenSupported);
        if (this.mPrimary != null) {
            StringBuilder m2 = DebugInfo$$ExternalSyntheticOutline0.m2m(m, "mPrimary.taskId=");
            m2.append(this.mPrimary.taskId);
            printWriter.println(m2.toString());
        }
        if (this.mSecondary != null) {
            StringBuilder m3 = DebugInfo$$ExternalSyntheticOutline0.m2m(m, "mSecondary.taskId=");
            m3.append(this.mSecondary.taskId);
            printWriter.println(m3.toString());
        }
    }

    public final SurfaceControl.Transaction getTransaction() {
        return this.mSplitScreenController.mTransactionPool.acquire();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:39:0x008d, code lost:
        if (r1.mHomeStackResizable != false) goto L_0x0091;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x002e, code lost:
        if (r0.mHomeStackResizable != false) goto L_0x0033;
     */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x003a  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0045  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0056  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0059  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x006f A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0070  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void handleTaskInfoChanged(android.app.ActivityManager.RunningTaskInfo r10) {
        /*
            r9 = this;
            boolean r0 = r9.mSplitScreenSupported
            if (r0 != 0) goto L_0x001b
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r0 = "Got handleTaskInfoChanged when not initialized: "
            r9.append(r0)
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            java.lang.String r10 = "LegacySplitScreenTaskListener"
            android.util.Log.e(r10, r9)
            return
        L_0x001b:
            android.app.ActivityManager$RunningTaskInfo r0 = r9.mSecondary
            int r0 = r0.topActivityType
            r1 = 3
            r2 = 2
            r3 = 0
            r4 = 1
            if (r0 == r2) goto L_0x0033
            if (r0 != r1) goto L_0x0031
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenController r0 = r9.mSplitScreenController
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.mHomeStackResizable
            if (r0 == 0) goto L_0x0031
            goto L_0x0033
        L_0x0031:
            r0 = r3
            goto L_0x0034
        L_0x0033:
            r0 = r4
        L_0x0034:
            android.app.ActivityManager$RunningTaskInfo r5 = r9.mPrimary
            int r5 = r5.topActivityType
            if (r5 != 0) goto L_0x003c
            r5 = r4
            goto L_0x003d
        L_0x003c:
            r5 = r3
        L_0x003d:
            android.app.ActivityManager$RunningTaskInfo r6 = r9.mSecondary
            int r6 = r6.topActivityType
            if (r6 != 0) goto L_0x0045
            r6 = r4
            goto L_0x0046
        L_0x0045:
            r6 = r3
        L_0x0046:
            android.window.WindowContainerToken r7 = r10.token
            android.os.IBinder r7 = r7.asBinder()
            android.app.ActivityManager$RunningTaskInfo r8 = r9.mPrimary
            android.window.WindowContainerToken r8 = r8.token
            android.os.IBinder r8 = r8.asBinder()
            if (r7 != r8) goto L_0x0059
            r9.mPrimary = r10
            goto L_0x006b
        L_0x0059:
            android.window.WindowContainerToken r7 = r10.token
            android.os.IBinder r7 = r7.asBinder()
            android.app.ActivityManager$RunningTaskInfo r8 = r9.mSecondary
            android.window.WindowContainerToken r8 = r8.token
            android.os.IBinder r8 = r8.asBinder()
            if (r7 != r8) goto L_0x006b
            r9.mSecondary = r10
        L_0x006b:
            boolean r10 = com.android.p012wm.shell.transition.Transitions.ENABLE_SHELL_TRANSITIONS
            if (r10 == 0) goto L_0x0070
            return
        L_0x0070:
            android.app.ActivityManager$RunningTaskInfo r10 = r9.mPrimary
            int r10 = r10.topActivityType
            if (r10 != 0) goto L_0x0078
            r10 = r4
            goto L_0x0079
        L_0x0078:
            r10 = r3
        L_0x0079:
            android.app.ActivityManager$RunningTaskInfo r7 = r9.mSecondary
            int r7 = r7.topActivityType
            if (r7 != 0) goto L_0x0081
            r8 = r4
            goto L_0x0082
        L_0x0081:
            r8 = r3
        L_0x0082:
            if (r7 == r2) goto L_0x0091
            if (r7 != r1) goto L_0x0090
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenController r1 = r9.mSplitScreenController
            java.util.Objects.requireNonNull(r1)
            boolean r1 = r1.mHomeStackResizable
            if (r1 == 0) goto L_0x0090
            goto L_0x0091
        L_0x0090:
            r4 = r3
        L_0x0091:
            if (r10 != r5) goto L_0x0098
            if (r6 != r8) goto L_0x0098
            if (r0 != r4) goto L_0x0098
            return
        L_0x0098:
            if (r10 != 0) goto L_0x00f4
            if (r8 == 0) goto L_0x009d
            goto L_0x00f4
        L_0x009d:
            if (r4 == 0) goto L_0x00ee
            java.util.ArrayList r10 = new java.util.ArrayList
            r10.<init>()
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenController r0 = r9.mSplitScreenController
            java.util.Objects.requireNonNull(r0)
            com.android.wm.shell.legacysplitscreen.WindowManagerProxy r0 = r0.mWindowManagerProxy
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenController r1 = r9.mSplitScreenController
            java.util.Objects.requireNonNull(r1)
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenTaskListener r1 = r1.mSplits
            if (r1 == 0) goto L_0x00bc
            android.app.ActivityManager$RunningTaskInfo r1 = r1.mSecondary
            if (r1 != 0) goto L_0x00b9
            goto L_0x00bc
        L_0x00b9:
            android.window.WindowContainerToken r1 = r1.token
            goto L_0x00bd
        L_0x00bc:
            r1 = 0
        L_0x00bd:
            r0.getHomeAndRecentsTasks(r10, r1)
            r0 = r3
        L_0x00c1:
            int r1 = r10.size()
            if (r0 >= r1) goto L_0x00e8
            java.lang.Object r1 = r10.get(r0)
            android.app.ActivityManager$RunningTaskInfo r1 = (android.app.ActivityManager.RunningTaskInfo) r1
            android.util.SparseArray<android.view.SurfaceControl> r2 = r9.mLeashByTaskId
            int r4 = r1.taskId
            java.lang.Object r2 = r2.get(r4)
            android.view.SurfaceControl r2 = (android.view.SurfaceControl) r2
            if (r2 == 0) goto L_0x00e5
            android.graphics.Point r1 = r1.positionInParent
            com.android.wm.shell.common.SyncTransactionQueue r4 = r9.mSyncQueue
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenTaskListener$$ExternalSyntheticLambda0 r5 = new com.android.wm.shell.legacysplitscreen.LegacySplitScreenTaskListener$$ExternalSyntheticLambda0
            r5.<init>(r2, r1, r3)
            r4.runInSync(r5)
        L_0x00e5:
            int r0 = r0 + 1
            goto L_0x00c1
        L_0x00e8:
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenController r9 = r9.mSplitScreenController
            r9.ensureMinimizedSplit()
            goto L_0x0150
        L_0x00ee:
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenController r9 = r9.mSplitScreenController
            r9.ensureNormalSplit()
            goto L_0x0150
        L_0x00f4:
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenController r0 = r9.mSplitScreenController
            boolean r0 = r0.isDividerVisible()
            if (r0 == 0) goto L_0x0105
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenController r9 = r9.mSplitScreenController
            java.util.Objects.requireNonNull(r9)
            r9.startDismissSplit(r3, r3)
            goto L_0x0150
        L_0x0105:
            if (r10 != 0) goto L_0x0150
            if (r5 == 0) goto L_0x0150
            if (r6 == 0) goto L_0x0150
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenController r9 = r9.mSplitScreenController
            java.util.Objects.requireNonNull(r9)
            com.android.wm.shell.common.DisplayController r10 = r9.mDisplayController
            android.content.Context r0 = r9.mContext
            int r0 = r0.getDisplayId()
            android.content.Context r10 = r10.getDisplayContext(r0)
            android.content.res.Resources r10 = r10.getResources()
            android.content.res.Configuration r10 = r10.getConfiguration()
            r9.update(r10)
            com.android.wm.shell.legacysplitscreen.WindowManagerProxy r10 = r9.mWindowManagerProxy
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenTaskListener r0 = r9.mSplits
            com.android.wm.shell.legacysplitscreen.LegacySplitDisplayLayout r1 = r9.mRotateSplitLayout
            if (r1 == 0) goto L_0x0130
            goto L_0x0132
        L_0x0130:
            com.android.wm.shell.legacysplitscreen.LegacySplitDisplayLayout r1 = r9.mSplitLayout
        L_0x0132:
            java.util.Objects.requireNonNull(r10)
            android.window.WindowContainerTransaction r2 = new android.window.WindowContainerTransaction
            r2.<init>()
            android.app.ActivityManager$RunningTaskInfo r3 = r0.mSecondary
            android.window.WindowContainerToken r3 = r3.token
            int[] r4 = com.android.p012wm.shell.legacysplitscreen.WindowManagerProxy.CONTROLLED_WINDOWING_MODES
            int[] r5 = com.android.p012wm.shell.legacysplitscreen.WindowManagerProxy.CONTROLLED_ACTIVITY_TYPES
            r2.setLaunchRoot(r3, r4, r5)
            boolean r0 = r10.buildEnterSplit(r2, r0, r1)
            com.android.wm.shell.common.SyncTransactionQueue r10 = r10.mSyncTransactionQueue
            r10.queue(r2)
            r9.mHomeStackResizable = r0
        L_0x0150:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.legacysplitscreen.LegacySplitScreenTaskListener.handleTaskInfoChanged(android.app.ActivityManager$RunningTaskInfo):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x003b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onTaskInfoChanged(android.app.ActivityManager.RunningTaskInfo r6) {
        /*
            r5 = this;
            int r0 = r6.displayId
            if (r0 == 0) goto L_0x0005
            return
        L_0x0005:
            monitor-enter(r5)
            boolean r0 = r6.supportsMultiWindow     // Catch:{ all -> 0x0083 }
            r1 = 0
            if (r0 != 0) goto L_0x003c
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenController r0 = r5.mSplitScreenController     // Catch:{ all -> 0x0083 }
            boolean r0 = r0.isDividerVisible()     // Catch:{ all -> 0x0083 }
            if (r0 == 0) goto L_0x003a
            int r0 = r6.taskId     // Catch:{ all -> 0x0083 }
            android.app.ActivityManager$RunningTaskInfo r2 = r5.mPrimary     // Catch:{ all -> 0x0083 }
            int r2 = r2.taskId     // Catch:{ all -> 0x0083 }
            if (r0 == r2) goto L_0x0030
            int r0 = r6.parentTaskId     // Catch:{ all -> 0x0083 }
            if (r0 != r2) goto L_0x0020
            goto L_0x0030
        L_0x0020:
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenController r0 = r5.mSplitScreenController     // Catch:{ all -> 0x0083 }
            boolean r6 = r6.isFocused     // Catch:{ all -> 0x0083 }
            if (r6 != 0) goto L_0x0028
            r6 = 1
            goto L_0x0029
        L_0x0028:
            r6 = r1
        L_0x0029:
            java.util.Objects.requireNonNull(r0)     // Catch:{ all -> 0x0083 }
            r0.startDismissSplit(r6, r1)     // Catch:{ all -> 0x0083 }
            goto L_0x003a
        L_0x0030:
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenController r0 = r5.mSplitScreenController     // Catch:{ all -> 0x0083 }
            boolean r6 = r6.isFocused     // Catch:{ all -> 0x0083 }
            java.util.Objects.requireNonNull(r0)     // Catch:{ all -> 0x0083 }
            r0.startDismissSplit(r6, r1)     // Catch:{ all -> 0x0083 }
        L_0x003a:
            monitor-exit(r5)     // Catch:{ all -> 0x0083 }
            return
        L_0x003c:
            boolean r0 = r6.hasParentTask()     // Catch:{ all -> 0x0083 }
            if (r0 == 0) goto L_0x0070
            android.graphics.Point r0 = r6.positionInParent     // Catch:{ all -> 0x0083 }
            android.util.SparseArray<android.graphics.Point> r2 = r5.mPositionByTaskId     // Catch:{ all -> 0x0083 }
            int r3 = r6.taskId     // Catch:{ all -> 0x0083 }
            java.lang.Object r2 = r2.get(r3)     // Catch:{ all -> 0x0083 }
            boolean r0 = r0.equals(r2)     // Catch:{ all -> 0x0083 }
            if (r0 == 0) goto L_0x0054
            monitor-exit(r5)     // Catch:{ all -> 0x0083 }
            return
        L_0x0054:
            boolean r0 = com.android.p012wm.shell.transition.Transitions.ENABLE_SHELL_TRANSITIONS     // Catch:{ all -> 0x0083 }
            if (r0 == 0) goto L_0x0059
            goto L_0x0073
        L_0x0059:
            android.util.SparseArray<android.view.SurfaceControl> r0 = r5.mLeashByTaskId     // Catch:{ all -> 0x0083 }
            int r2 = r6.taskId     // Catch:{ all -> 0x0083 }
            java.lang.Object r0 = r0.get(r2)     // Catch:{ all -> 0x0083 }
            android.view.SurfaceControl r0 = (android.view.SurfaceControl) r0     // Catch:{ all -> 0x0083 }
            android.graphics.Point r2 = r6.positionInParent     // Catch:{ all -> 0x0083 }
            com.android.wm.shell.common.SyncTransactionQueue r3 = r5.mSyncQueue     // Catch:{ all -> 0x0083 }
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenTaskListener$$ExternalSyntheticLambda0 r4 = new com.android.wm.shell.legacysplitscreen.LegacySplitScreenTaskListener$$ExternalSyntheticLambda0     // Catch:{ all -> 0x0083 }
            r4.<init>(r0, r2, r1)     // Catch:{ all -> 0x0083 }
            r3.runInSync(r4)     // Catch:{ all -> 0x0083 }
            goto L_0x0073
        L_0x0070:
            r5.handleTaskInfoChanged(r6)     // Catch:{ all -> 0x0083 }
        L_0x0073:
            android.util.SparseArray<android.graphics.Point> r0 = r5.mPositionByTaskId     // Catch:{ all -> 0x0083 }
            int r1 = r6.taskId     // Catch:{ all -> 0x0083 }
            android.graphics.Point r2 = new android.graphics.Point     // Catch:{ all -> 0x0083 }
            android.graphics.Point r6 = r6.positionInParent     // Catch:{ all -> 0x0083 }
            r2.<init>(r6)     // Catch:{ all -> 0x0083 }
            r0.put(r1, r2)     // Catch:{ all -> 0x0083 }
            monitor-exit(r5)     // Catch:{ all -> 0x0083 }
            return
        L_0x0083:
            r6 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0083 }
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.legacysplitscreen.LegacySplitScreenTaskListener.onTaskInfoChanged(android.app.ActivityManager$RunningTaskInfo):void");
    }

    public final void releaseTransaction(SurfaceControl.Transaction transaction) {
        this.mSplitScreenController.mTransactionPool.release(transaction);
    }

    public LegacySplitScreenTaskListener(LegacySplitScreenController legacySplitScreenController, ShellTaskOrganizer shellTaskOrganizer, Transitions transitions, SyncTransactionQueue syncTransactionQueue) {
        this.mSplitScreenController = legacySplitScreenController;
        this.mTaskOrganizer = shellTaskOrganizer;
        LegacySplitScreenTransitions legacySplitScreenTransitions = new LegacySplitScreenTransitions(legacySplitScreenController.mTransactionPool, transitions, legacySplitScreenController, this);
        this.mSplitTransitions = legacySplitScreenTransitions;
        Objects.requireNonNull(transitions);
        transitions.mHandlers.add(legacySplitScreenTransitions);
        this.mSyncQueue = syncTransactionQueue;
    }
}
