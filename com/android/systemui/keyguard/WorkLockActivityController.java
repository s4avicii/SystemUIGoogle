package com.android.systemui.keyguard;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.IActivityTaskManager;
import android.app.ProfilerInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline1;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import java.util.Objects;

public final class WorkLockActivityController {
    public final Context mContext;
    public final IActivityTaskManager mIatm;
    public final C08631 mLockListener;

    @VisibleForTesting
    public WorkLockActivityController(Context context, TaskStackChangeListeners taskStackChangeListeners, IActivityTaskManager iActivityTaskManager) {
        C08631 r0 = new TaskStackChangeListener() {
            public final void onTaskProfileLocked(int i, int i2) {
                ActivityManager.TaskDescription taskDescription;
                int i3 = i;
                WorkLockActivityController workLockActivityController = WorkLockActivityController.this;
                Objects.requireNonNull(workLockActivityController);
                try {
                    taskDescription = workLockActivityController.mIatm.getTaskDescription(i3);
                } catch (RemoteException unused) {
                    GridLayoutManager$$ExternalSyntheticOutline1.m20m("Failed to get description for task=", i3, "WorkLockActivityController");
                    taskDescription = null;
                }
                Intent addFlags = new Intent("android.app.action.CONFIRM_DEVICE_CREDENTIAL_WITH_USER").setComponent(new ComponentName(workLockActivityController.mContext, WorkLockActivity.class)).putExtra("android.intent.extra.USER_ID", i2).putExtra("com.android.systemui.keyguard.extra.TASK_DESCRIPTION", taskDescription).addFlags(67239936);
                ActivityOptions makeBasic = ActivityOptions.makeBasic();
                makeBasic.setLaunchTaskId(i3);
                makeBasic.setTaskOverlay(true, false);
                Bundle bundle = makeBasic.toBundle();
                int i4 = -96;
                try {
                    i4 = workLockActivityController.mIatm.startActivityAsUser(workLockActivityController.mContext.getIApplicationThread(), workLockActivityController.mContext.getBasePackageName(), workLockActivityController.mContext.getAttributionTag(), addFlags, addFlags.resolveTypeIfNeeded(workLockActivityController.mContext.getContentResolver()), (IBinder) null, (String) null, 0, 268435456, (ProfilerInfo) null, bundle, -2);
                } catch (RemoteException | Exception unused2) {
                }
                if (!ActivityManager.isStartResultSuccessful(i4)) {
                    try {
                        workLockActivityController.mIatm.removeTask(i3);
                    } catch (RemoteException unused3) {
                        GridLayoutManager$$ExternalSyntheticOutline1.m20m("Failed to get description for task=", i3, "WorkLockActivityController");
                    }
                }
            }
        };
        this.mLockListener = r0;
        this.mContext = context;
        this.mIatm = iActivityTaskManager;
        taskStackChangeListeners.registerTaskStackListener(r0);
    }
}
