package com.android.p012wm.shell.tasksurfacehelper;

import com.android.p012wm.shell.tasksurfacehelper.TaskSurfaceHelperController;

/* renamed from: com.android.wm.shell.tasksurfacehelper.TaskSurfaceHelperController$TaskSurfaceHelperImpl$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1934x3c1cf253 implements Runnable {
    public final /* synthetic */ TaskSurfaceHelperController.TaskSurfaceHelperImpl f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ C1934x3c1cf253(TaskSurfaceHelperController.TaskSurfaceHelperImpl taskSurfaceHelperImpl, int i, int i2) {
        this.f$0 = taskSurfaceHelperImpl;
        this.f$1 = i;
        this.f$2 = i2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r4 = this;
            com.android.wm.shell.tasksurfacehelper.TaskSurfaceHelperController$TaskSurfaceHelperImpl r0 = r4.f$0
            int r1 = r4.f$1
            int r4 = r4.f$2
            java.util.Objects.requireNonNull(r0)
            com.android.wm.shell.tasksurfacehelper.TaskSurfaceHelperController r0 = com.android.p012wm.shell.tasksurfacehelper.TaskSurfaceHelperController.this
            java.util.Objects.requireNonNull(r0)
            com.android.wm.shell.ShellTaskOrganizer r0 = r0.mTaskOrganizer
            java.util.Objects.requireNonNull(r0)
            java.lang.Object r2 = r0.mLock
            monitor-enter(r2)
            android.util.SparseArray<android.window.TaskAppearedInfo> r0 = r0.mTasks     // Catch:{ all -> 0x003c }
            java.lang.Object r0 = r0.get(r1)     // Catch:{ all -> 0x003c }
            android.window.TaskAppearedInfo r0 = (android.window.TaskAppearedInfo) r0     // Catch:{ all -> 0x003c }
            if (r0 == 0) goto L_0x003a
            android.view.SurfaceControl r1 = r0.getLeash()     // Catch:{ all -> 0x003c }
            if (r1 != 0) goto L_0x0027
            goto L_0x003a
        L_0x0027:
            android.view.SurfaceControl$Transaction r1 = new android.view.SurfaceControl$Transaction     // Catch:{ all -> 0x003c }
            r1.<init>()     // Catch:{ all -> 0x003c }
            android.view.SurfaceControl r0 = r0.getLeash()     // Catch:{ all -> 0x003c }
            r3 = 8
            r1.setMetadata(r0, r3, r4)     // Catch:{ all -> 0x003c }
            r1.apply()     // Catch:{ all -> 0x003c }
            monitor-exit(r2)     // Catch:{ all -> 0x003c }
            goto L_0x003b
        L_0x003a:
            monitor-exit(r2)     // Catch:{ all -> 0x003c }
        L_0x003b:
            return
        L_0x003c:
            r4 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x003c }
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.tasksurfacehelper.C1934x3c1cf253.run():void");
    }
}
