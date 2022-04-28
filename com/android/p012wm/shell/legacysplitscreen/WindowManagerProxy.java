package com.android.p012wm.shell.legacysplitscreen;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.graphics.Rect;
import android.os.RemoteException;
import android.util.Log;
import android.view.SurfaceControl;
import android.window.TaskOrganizer;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.policy.DividerSnapAlgorithm;
import com.android.internal.policy.DockedDividerUtils;
import com.android.internal.util.ArrayUtils;
import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.common.DisplayLayout;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.p012wm.shell.transition.Transitions;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* renamed from: com.android.wm.shell.legacysplitscreen.WindowManagerProxy */
public final class WindowManagerProxy {
    public static final int[] CONTROLLED_ACTIVITY_TYPES = {1, 2, 3, 0};
    public static final int[] CONTROLLED_WINDOWING_MODES = {1, 4, 0};
    public static final int[] HOME_AND_RECENTS = {2, 3};
    @GuardedBy({"mDockedRect"})
    public final Rect mDockedRect = new Rect();
    public final SyncTransactionQueue mSyncTransactionQueue;
    public final TaskOrganizer mTaskOrganizer;
    @GuardedBy({"mDockedRect"})
    public final Rect mTouchableRegion;

    public final boolean getHomeAndRecentsTasks(ArrayList arrayList, WindowContainerToken windowContainerToken) {
        List list;
        if (windowContainerToken == null) {
            list = this.mTaskOrganizer.getRootTasks(0, HOME_AND_RECENTS);
        } else {
            list = this.mTaskOrganizer.getChildTasks(windowContainerToken, HOME_AND_RECENTS);
        }
        int size = list.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) list.get(i);
            arrayList.add(runningTaskInfo);
            if (runningTaskInfo.topActivityType == 2) {
                z = runningTaskInfo.isResizeable;
            }
        }
        return z;
    }

    public static /* synthetic */ boolean $r8$lambda$47w38wy_iigsOkn3kFhRTExSL3k(LegacySplitScreenTaskListener legacySplitScreenTaskListener, ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (runningTaskInfo.token.equals(legacySplitScreenTaskListener.mSecondary.token) || runningTaskInfo.token.equals(legacySplitScreenTaskListener.mPrimary.token)) {
            return true;
        }
        return false;
    }

    public final boolean applyHomeTasksMinimized(LegacySplitDisplayLayout legacySplitDisplayLayout, WindowContainerToken windowContainerToken, WindowContainerTransaction windowContainerTransaction) {
        Rect rect;
        int i;
        int i2;
        ArrayList arrayList = new ArrayList();
        boolean homeAndRecentsTasks = getHomeAndRecentsTasks(arrayList, windowContainerToken);
        if (homeAndRecentsTasks) {
            Objects.requireNonNull(legacySplitDisplayLayout);
            DividerSnapAlgorithm.SnapTarget middleTarget = legacySplitDisplayLayout.getMinimizedSnapAlgorithm(true).getMiddleTarget();
            rect = new Rect();
            int i3 = middleTarget.position;
            int invertDockSide = DockedDividerUtils.invertDockSide(legacySplitDisplayLayout.getPrimarySplitSide());
            DisplayLayout displayLayout = legacySplitDisplayLayout.mDisplayLayout;
            Objects.requireNonNull(displayLayout);
            int i4 = displayLayout.mWidth;
            DisplayLayout displayLayout2 = legacySplitDisplayLayout.mDisplayLayout;
            Objects.requireNonNull(displayLayout2);
            DockedDividerUtils.calculateBoundsForPosition(i3, invertDockSide, rect, i4, displayLayout2.mHeight, legacySplitDisplayLayout.mDividerSize);
        } else {
            boolean z = false;
            rect = new Rect(0, 0, 0, 0);
            int size = arrayList.size() - 1;
            while (true) {
                if (size < 0) {
                    break;
                } else if (((ActivityManager.RunningTaskInfo) arrayList.get(size)).topActivityType == 2) {
                    int i5 = ((ActivityManager.RunningTaskInfo) arrayList.get(size)).configuration.orientation;
                    boolean isLandscape = legacySplitDisplayLayout.mDisplayLayout.isLandscape();
                    if (i5 == 2 || (i5 == 0 && isLandscape)) {
                        z = true;
                    }
                    if (z == isLandscape) {
                        DisplayLayout displayLayout3 = legacySplitDisplayLayout.mDisplayLayout;
                        Objects.requireNonNull(displayLayout3);
                        i = displayLayout3.mWidth;
                    } else {
                        DisplayLayout displayLayout4 = legacySplitDisplayLayout.mDisplayLayout;
                        Objects.requireNonNull(displayLayout4);
                        i = displayLayout4.mHeight;
                    }
                    rect.right = i;
                    if (z == isLandscape) {
                        DisplayLayout displayLayout5 = legacySplitDisplayLayout.mDisplayLayout;
                        Objects.requireNonNull(displayLayout5);
                        i2 = displayLayout5.mHeight;
                    } else {
                        DisplayLayout displayLayout6 = legacySplitDisplayLayout.mDisplayLayout;
                        Objects.requireNonNull(displayLayout6);
                        i2 = displayLayout6.mWidth;
                    }
                    rect.bottom = i2;
                } else {
                    size--;
                }
            }
        }
        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
            if (!homeAndRecentsTasks) {
                if (((ActivityManager.RunningTaskInfo) arrayList.get(size2)).topActivityType == 3) {
                } else {
                    windowContainerTransaction.setWindowingMode(((ActivityManager.RunningTaskInfo) arrayList.get(size2)).token, 1);
                }
            }
            windowContainerTransaction.setBounds(((ActivityManager.RunningTaskInfo) arrayList.get(size2)).token, rect);
        }
        legacySplitDisplayLayout.mTiles.mHomeBounds.set(rect);
        return homeAndRecentsTasks;
    }

    public final boolean buildEnterSplit(WindowContainerTransaction windowContainerTransaction, LegacySplitScreenTaskListener legacySplitScreenTaskListener, LegacySplitDisplayLayout legacySplitDisplayLayout) {
        List rootTasks = this.mTaskOrganizer.getRootTasks(0, (int[]) null);
        if (rootTasks.isEmpty()) {
            return false;
        }
        ActivityManager.RunningTaskInfo runningTaskInfo = null;
        for (int size = rootTasks.size() - 1; size >= 0; size--) {
            ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) rootTasks.get(size);
            if (runningTaskInfo2.supportsMultiWindow || runningTaskInfo2.topActivityType == 2) {
                int windowingMode = runningTaskInfo2.getWindowingMode();
                if (ArrayUtils.contains(CONTROLLED_WINDOWING_MODES, windowingMode) && ArrayUtils.contains(CONTROLLED_ACTIVITY_TYPES, runningTaskInfo2.getActivityType()) && windowingMode != 4) {
                    if (isHomeOrRecentTask(runningTaskInfo2)) {
                        runningTaskInfo = runningTaskInfo2;
                    } else {
                        runningTaskInfo = null;
                    }
                    windowContainerTransaction.reparent(runningTaskInfo2.token, legacySplitScreenTaskListener.mSecondary.token, true);
                }
            }
        }
        windowContainerTransaction.reorder(legacySplitScreenTaskListener.mSecondary.token, true);
        boolean applyHomeTasksMinimized = applyHomeTasksMinimized(legacySplitDisplayLayout, (WindowContainerToken) null, windowContainerTransaction);
        if (runningTaskInfo != null && !Transitions.ENABLE_SHELL_TRANSITIONS) {
            windowContainerTransaction.setBoundsChangeTransaction(runningTaskInfo.token, legacySplitScreenTaskListener.mHomeBounds);
        }
        return applyHomeTasksMinimized;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean queueSyncTransactionIfWaiting(android.window.WindowContainerTransaction r3) {
        /*
            r2 = this;
            com.android.wm.shell.common.SyncTransactionQueue r2 = r2.mSyncTransactionQueue
            java.util.Objects.requireNonNull(r2)
            boolean r0 = r3.isEmpty()
            if (r0 == 0) goto L_0x000c
            goto L_0x0018
        L_0x000c:
            java.util.ArrayList<com.android.wm.shell.common.SyncTransactionQueue$SyncCallback> r0 = r2.mQueue
            monitor-enter(r0)
            java.util.ArrayList<com.android.wm.shell.common.SyncTransactionQueue$SyncCallback> r1 = r2.mQueue     // Catch:{ all -> 0x0033 }
            boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x0033 }
            if (r1 == 0) goto L_0x001a
            monitor-exit(r0)     // Catch:{ all -> 0x0033 }
        L_0x0018:
            r2 = 0
            goto L_0x0032
        L_0x001a:
            com.android.wm.shell.common.SyncTransactionQueue$SyncCallback r1 = new com.android.wm.shell.common.SyncTransactionQueue$SyncCallback     // Catch:{ all -> 0x0033 }
            r1.<init>(r3)     // Catch:{ all -> 0x0033 }
            java.util.ArrayList<com.android.wm.shell.common.SyncTransactionQueue$SyncCallback> r3 = r2.mQueue     // Catch:{ all -> 0x0033 }
            r3.add(r1)     // Catch:{ all -> 0x0033 }
            java.util.ArrayList<com.android.wm.shell.common.SyncTransactionQueue$SyncCallback> r2 = r2.mQueue     // Catch:{ all -> 0x0033 }
            int r2 = r2.size()     // Catch:{ all -> 0x0033 }
            r3 = 1
            if (r2 != r3) goto L_0x0030
            r1.send()     // Catch:{ all -> 0x0033 }
        L_0x0030:
            monitor-exit(r0)     // Catch:{ all -> 0x0033 }
            r2 = r3
        L_0x0032:
            return r2
        L_0x0033:
            r2 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0033 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.legacysplitscreen.WindowManagerProxy.queueSyncTransactionIfWaiting(android.window.WindowContainerTransaction):boolean");
    }

    public WindowManagerProxy(SyncTransactionQueue syncTransactionQueue, TaskOrganizer taskOrganizer) {
        new Rect();
        this.mTouchableRegion = new Rect();
        this.mSyncTransactionQueue = syncTransactionQueue;
        this.mTaskOrganizer = taskOrganizer;
    }

    public static void buildDismissSplit(WindowContainerTransaction windowContainerTransaction, LegacySplitScreenTaskListener legacySplitScreenTaskListener, LegacySplitDisplayLayout legacySplitDisplayLayout, boolean z) {
        int i;
        int i2;
        Objects.requireNonNull(legacySplitScreenTaskListener);
        ShellTaskOrganizer shellTaskOrganizer = legacySplitScreenTaskListener.mTaskOrganizer;
        List childTasks = shellTaskOrganizer.getChildTasks(legacySplitScreenTaskListener.mPrimary.token, (int[]) null);
        List childTasks2 = shellTaskOrganizer.getChildTasks(legacySplitScreenTaskListener.mSecondary.token, (int[]) null);
        List rootTasks = shellTaskOrganizer.getRootTasks(0, HOME_AND_RECENTS);
        rootTasks.removeIf(new WindowManagerProxy$$ExternalSyntheticLambda0(legacySplitScreenTaskListener, 0));
        if (!childTasks.isEmpty() || !childTasks2.isEmpty() || !rootTasks.isEmpty()) {
            if (z) {
                for (int size = childTasks.size() - 1; size >= 0; size--) {
                    windowContainerTransaction.reparent(((ActivityManager.RunningTaskInfo) childTasks.get(size)).token, (WindowContainerToken) null, true);
                }
                boolean z2 = false;
                for (int size2 = childTasks2.size() - 1; size2 >= 0; size2--) {
                    ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) childTasks2.get(size2);
                    windowContainerTransaction.reparent(runningTaskInfo.token, (WindowContainerToken) null, true);
                    if (isHomeOrRecentTask(runningTaskInfo)) {
                        windowContainerTransaction.setBounds(runningTaskInfo.token, (Rect) null);
                        windowContainerTransaction.setWindowingMode(runningTaskInfo.token, 0);
                        if (size2 == 0) {
                            z2 = true;
                        }
                    }
                }
                if (z2 && !Transitions.ENABLE_SHELL_TRANSITIONS) {
                    boolean isLandscape = legacySplitDisplayLayout.mDisplayLayout.isLandscape();
                    if (isLandscape) {
                        i = legacySplitDisplayLayout.mSecondary.left - legacySplitScreenTaskListener.mHomeBounds.left;
                    } else {
                        i = legacySplitDisplayLayout.mSecondary.left;
                    }
                    if (isLandscape) {
                        i2 = legacySplitDisplayLayout.mSecondary.top;
                    } else {
                        i2 = legacySplitDisplayLayout.mSecondary.top - legacySplitScreenTaskListener.mHomeBounds.top;
                    }
                    SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                    transaction.setPosition(legacySplitScreenTaskListener.mSecondarySurface, (float) i, (float) i2);
                    DisplayLayout displayLayout = legacySplitDisplayLayout.mDisplayLayout;
                    Objects.requireNonNull(displayLayout);
                    int i3 = displayLayout.mWidth;
                    DisplayLayout displayLayout2 = legacySplitDisplayLayout.mDisplayLayout;
                    Objects.requireNonNull(displayLayout2);
                    Rect rect = new Rect(0, 0, i3, displayLayout2.mHeight);
                    rect.offset(-i, -i2);
                    transaction.setWindowCrop(legacySplitScreenTaskListener.mSecondarySurface, rect);
                    windowContainerTransaction.setBoundsChangeTransaction(legacySplitScreenTaskListener.mSecondary.token, transaction);
                }
            } else {
                for (int size3 = childTasks2.size() - 1; size3 >= 0; size3--) {
                    if (!isHomeOrRecentTask((ActivityManager.RunningTaskInfo) childTasks2.get(size3))) {
                        windowContainerTransaction.reparent(((ActivityManager.RunningTaskInfo) childTasks2.get(size3)).token, (WindowContainerToken) null, true);
                    }
                }
                for (int size4 = childTasks2.size() - 1; size4 >= 0; size4--) {
                    ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) childTasks2.get(size4);
                    if (isHomeOrRecentTask(runningTaskInfo2)) {
                        windowContainerTransaction.reparent(runningTaskInfo2.token, (WindowContainerToken) null, true);
                        windowContainerTransaction.setBounds(runningTaskInfo2.token, (Rect) null);
                        windowContainerTransaction.setWindowingMode(runningTaskInfo2.token, 0);
                    }
                }
                for (int size5 = childTasks.size() - 1; size5 >= 0; size5--) {
                    windowContainerTransaction.reparent(((ActivityManager.RunningTaskInfo) childTasks.get(size5)).token, (WindowContainerToken) null, true);
                }
            }
            for (int size6 = rootTasks.size() - 1; size6 >= 0; size6--) {
                windowContainerTransaction.setBounds(((ActivityManager.RunningTaskInfo) rootTasks.get(size6)).token, (Rect) null);
                windowContainerTransaction.setWindowingMode(((ActivityManager.RunningTaskInfo) rootTasks.get(size6)).token, 0);
            }
            windowContainerTransaction.setFocusable(legacySplitScreenTaskListener.mPrimary.token, true);
        }
    }

    public static boolean isHomeOrRecentTask(ActivityManager.RunningTaskInfo runningTaskInfo) {
        int activityType = runningTaskInfo.getActivityType();
        if (activityType == 2 || activityType == 3) {
            return true;
        }
        return false;
    }

    public static void setResizing(boolean z) {
        try {
            ActivityTaskManager.getService().setSplitScreenResizing(z);
        } catch (RemoteException e) {
            Log.w("WindowManagerProxy", "Error calling setDockedStackResizing: " + e);
        }
    }
}
