package com.android.p012wm.shell.recents;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.Context;
import android.os.RemoteException;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.android.p012wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda9;
import com.android.p012wm.shell.common.RemoteCallable;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.SingleInstanceRemoteListener;
import com.android.p012wm.shell.common.TaskStackListenerCallback;
import com.android.p012wm.shell.common.TaskStackListenerImpl;
import com.android.p012wm.shell.pip.phone.PipMotionHelper$$ExternalSyntheticLambda1;
import com.android.p012wm.shell.protolog.ShellProtoLogCache;
import com.android.p012wm.shell.protolog.ShellProtoLogGroup;
import com.android.p012wm.shell.protolog.ShellProtoLogImpl;
import com.android.p012wm.shell.recents.IRecentTasks;
import com.android.p012wm.shell.util.GroupedRecentTaskInfo;
import com.android.p012wm.shell.util.StagedSplitBounds;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/* renamed from: com.android.wm.shell.recents.RecentTasksController */
public final class RecentTasksController implements TaskStackListenerCallback, RemoteCallable<RecentTasksController> {
    public final ArrayList<Runnable> mCallbacks = new ArrayList<>();
    public final Context mContext;
    public final RecentTasksImpl mImpl = new RecentTasksImpl();
    public final ShellExecutor mMainExecutor;
    public final SparseIntArray mSplitTasks = new SparseIntArray();
    public final HashMap mTaskSplitBoundsMap = new HashMap();
    public final TaskStackListenerImpl mTaskStackListener;

    /* renamed from: com.android.wm.shell.recents.RecentTasksController$RecentTasksImpl */
    public class RecentTasksImpl implements RecentTasks {
        public IRecentTasksImpl mIRecentTasks;

        public RecentTasksImpl() {
        }

        public final IRecentTasks createExternalInterface() {
            IRecentTasksImpl iRecentTasksImpl = this.mIRecentTasks;
            if (iRecentTasksImpl != null) {
                Objects.requireNonNull(iRecentTasksImpl);
                iRecentTasksImpl.mController = null;
            }
            IRecentTasksImpl iRecentTasksImpl2 = new IRecentTasksImpl(RecentTasksController.this);
            this.mIRecentTasks = iRecentTasksImpl2;
            return iRecentTasksImpl2;
        }
    }

    /* renamed from: com.android.wm.shell.recents.RecentTasksController$IRecentTasksImpl */
    public static class IRecentTasksImpl extends IRecentTasks.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;
        public RecentTasksController mController;
        public final SingleInstanceRemoteListener<RecentTasksController, IRecentTasksListener> mListener;
        public final C19171 mRecentTasksListener = new Runnable() {
            public final void run() {
                SingleInstanceRemoteListener<RecentTasksController, IRecentTasksListener> singleInstanceRemoteListener = IRecentTasksImpl.this.mListener;
                Objects.requireNonNull(singleInstanceRemoteListener);
                L l = singleInstanceRemoteListener.mListener;
                if (l == null) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call on null listener");
                    return;
                }
                try {
                    ((IRecentTasksListener) l).onRecentTasksChanged();
                } catch (RemoteException e) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call", e);
                }
            }
        };

        public IRecentTasksImpl(RecentTasksController recentTasksController) {
            this.mController = recentTasksController;
            this.mListener = new SingleInstanceRemoteListener<>(recentTasksController, new BubbleController$$ExternalSyntheticLambda9(this, 3), new PipMotionHelper$$ExternalSyntheticLambda1(this, 5));
        }
    }

    public void notifyRecentTasksChanged() {
        if (ShellProtoLogCache.WM_SHELL_RECENT_TASKS_enabled) {
            ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_RECENT_TASKS, -1066960526, 0, (String) null, (Object[]) null);
        }
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            this.mCallbacks.get(i).run();
        }
    }

    public final void removeSplitPair(int i) {
        int i2 = this.mSplitTasks.get(i, -1);
        if (i2 != -1) {
            this.mSplitTasks.delete(i);
            this.mSplitTasks.delete(i2);
            this.mTaskSplitBoundsMap.remove(Integer.valueOf(i));
            this.mTaskSplitBoundsMap.remove(Integer.valueOf(i2));
            notifyRecentTasksChanged();
            if (ShellProtoLogCache.WM_SHELL_RECENT_TASKS_enabled) {
                ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_RECENT_TASKS, 927833074, 5, (String) null, Long.valueOf((long) i), Long.valueOf((long) i2));
            }
        }
    }

    public RecentTasksController(Context context, TaskStackListenerImpl taskStackListenerImpl, ShellExecutor shellExecutor) {
        this.mContext = context;
        this.mTaskStackListener = taskStackListenerImpl;
        this.mMainExecutor = shellExecutor;
    }

    public List<ActivityManager.RecentTaskInfo> getRawRecentTasks(int i, int i2, int i3) {
        return ActivityTaskManager.getInstance().getRecentTasks(i, i2, i3);
    }

    public ArrayList<GroupedRecentTaskInfo> getRecentTasks(int i, int i2, int i3) {
        List<ActivityManager.RecentTaskInfo> rawRecentTasks = getRawRecentTasks(i, i2, i3);
        SparseArray sparseArray = new SparseArray();
        for (int i4 = 0; i4 < rawRecentTasks.size(); i4++) {
            ActivityManager.RecentTaskInfo recentTaskInfo = rawRecentTasks.get(i4);
            sparseArray.put(recentTaskInfo.taskId, recentTaskInfo);
        }
        ArrayList<GroupedRecentTaskInfo> arrayList = new ArrayList<>();
        for (int i5 = 0; i5 < rawRecentTasks.size(); i5++) {
            ActivityManager.RecentTaskInfo recentTaskInfo2 = rawRecentTasks.get(i5);
            if (sparseArray.contains(recentTaskInfo2.taskId)) {
                int i6 = this.mSplitTasks.get(recentTaskInfo2.taskId);
                if (i6 == -1 || !sparseArray.contains(i6)) {
                    arrayList.add(new GroupedRecentTaskInfo(recentTaskInfo2, (ActivityManager.RecentTaskInfo) null, (StagedSplitBounds) null));
                } else {
                    sparseArray.remove(i6);
                    arrayList.add(new GroupedRecentTaskInfo(recentTaskInfo2, (ActivityManager.RecentTaskInfo) sparseArray.get(i6), (StagedSplitBounds) this.mTaskSplitBoundsMap.get(Integer.valueOf(i6))));
                }
            }
        }
        return arrayList;
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final ShellExecutor getRemoteCallExecutor() {
        return this.mMainExecutor;
    }

    public final void onRecentTaskListUpdated() {
        notifyRecentTasksChanged();
    }

    public final void onTaskStackChanged() {
        notifyRecentTasksChanged();
    }
}
