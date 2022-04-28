package com.android.p012wm.shell.apppairs;

import android.hidl.base.V1_0.DebugInfo$$ExternalSyntheticOutline0;
import com.android.internal.annotations.VisibleForTesting;
import com.android.p012wm.shell.protolog.ShellProtoLogCache;
import com.android.p012wm.shell.protolog.ShellProtoLogGroup;
import com.android.p012wm.shell.protolog.ShellProtoLogImpl;
import java.util.ArrayList;
import java.util.Objects;

/* renamed from: com.android.wm.shell.apppairs.AppPairsPool */
public final class AppPairsPool {
    @VisibleForTesting
    public final AppPairsController mController;
    public final ArrayList<AppPair> mPool = new ArrayList<>();

    @VisibleForTesting
    public void incrementPool() {
        if (ShellProtoLogCache.WM_SHELL_TASK_ORG_enabled) {
            long size = (long) this.mPool.size();
            ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_TASK_ORG, 1079041527, 1, (String) null, Long.valueOf(size));
        }
        AppPair appPair = new AppPair(this.mController);
        AppPairsController appPairsController = this.mController;
        Objects.requireNonNull(appPairsController);
        appPairsController.mTaskOrganizer.createRootTask(1, appPair);
        this.mPool.add(appPair);
    }

    @VisibleForTesting
    public int poolSize() {
        return this.mPool.size();
    }

    public final void release(AppPair appPair) {
        this.mPool.add(appPair);
        if (ShellProtoLogCache.WM_SHELL_TASK_ORG_enabled) {
            String valueOf = String.valueOf(appPair.getRootTaskId());
            String valueOf2 = String.valueOf(appPair);
            long size = (long) this.mPool.size();
            ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_TASK_ORG, 1891981945, 16, (String) null, valueOf, valueOf2, Long.valueOf(size));
        }
    }

    public final String toString() {
        StringBuilder m = DebugInfo$$ExternalSyntheticOutline0.m2m("AppPairsPool", "#");
        m.append(this.mPool.size());
        return m.toString();
    }

    public AppPairsPool(AppPairsController appPairsController) {
        this.mController = appPairsController;
        incrementPool();
    }
}
