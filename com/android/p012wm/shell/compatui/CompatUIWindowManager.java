package com.android.p012wm.shell.compatui;

import android.app.TaskInfo;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.window.TaskAppearedInfo;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.FrameworkStatsLog;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.common.DisplayLayout;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.p012wm.shell.compatui.CompatUIController;
import java.util.Objects;

/* renamed from: com.android.wm.shell.compatui.CompatUIWindowManager */
public final class CompatUIWindowManager extends CompatUIWindowManagerAbstract {
    public final CompatUIController.CompatUICallback mCallback;
    @VisibleForTesting
    public int mCameraCompatControlState;
    @VisibleForTesting
    public CompatUIHintsState mCompatUIHintsState;
    @VisibleForTesting
    public boolean mHasSizeCompat;
    @VisibleForTesting
    public CompatUILayout mLayout;

    /* renamed from: com.android.wm.shell.compatui.CompatUIWindowManager$CompatUIHintsState */
    public static class CompatUIHintsState {
        @VisibleForTesting
        public boolean mHasShownCameraCompatHint;
        @VisibleForTesting
        public boolean mHasShownSizeCompatHint;
    }

    public CompatUIWindowManager(Context context, TaskInfo taskInfo, SyncTransactionQueue syncTransactionQueue, CompatUIController.CompatUICallback compatUICallback, ShellTaskOrganizer.TaskListener taskListener, DisplayLayout displayLayout, CompatUIHintsState compatUIHintsState) {
        super(context, taskInfo, syncTransactionQueue, taskListener, displayLayout);
        this.mCallback = compatUICallback;
        this.mHasSizeCompat = taskInfo.topActivityInSizeCompat;
        this.mCameraCompatControlState = taskInfo.cameraCompatControlState;
        this.mCompatUIHintsState = compatUIHintsState;
    }

    public final int getZOrder() {
        return 2147483646;
    }

    public final void removeLayout() {
        this.mLayout = null;
    }

    public final boolean eligibleToShowLayout() {
        if (this.mHasSizeCompat || shouldShowCameraControl()) {
            return true;
        }
        return false;
    }

    @VisibleForTesting
    public CompatUILayout inflateLayout() {
        return (CompatUILayout) LayoutInflater.from(this.mContext).inflate(C1777R.layout.compat_ui_layout, (ViewGroup) null);
    }

    public final boolean shouldShowCameraControl() {
        int i = this.mCameraCompatControlState;
        if (i == 0 || i == 3) {
            return false;
        }
        return true;
    }

    public final boolean updateCompatInfo(TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener, boolean z) {
        boolean z2 = this.mHasSizeCompat;
        int i = this.mCameraCompatControlState;
        this.mHasSizeCompat = taskInfo.topActivityInSizeCompat;
        this.mCameraCompatControlState = taskInfo.cameraCompatControlState;
        if (!super.updateCompatInfo(taskInfo, taskListener, z)) {
            return false;
        }
        if (z2 == this.mHasSizeCompat && i == this.mCameraCompatControlState) {
            return true;
        }
        updateVisibilityOfViews();
        return true;
    }

    @VisibleForTesting
    public void updateSurfacePosition() {
        int i;
        int i2;
        if (this.mLayout != null) {
            Rect bounds = this.mTaskConfig.windowConfiguration.getBounds();
            Rect rect = new Rect(this.mStableBounds);
            rect.intersect(this.mTaskConfig.windowConfiguration.getBounds());
            if (this.mContext.getResources().getConfiguration().getLayoutDirection() == 1) {
                i2 = rect.left;
                i = bounds.left;
            } else {
                i2 = rect.right - bounds.left;
                i = this.mLayout.getMeasuredWidth();
            }
            int i3 = i2 - i;
            int measuredHeight = (rect.bottom - bounds.top) - this.mLayout.getMeasuredHeight();
            if (this.mLeash != null) {
                this.mSyncQueue.runInSync(new CompatUIWindowManagerAbstract$$ExternalSyntheticLambda1(this, i3, measuredHeight));
            }
        }
    }

    public final void updateVisibilityOfViews() {
        CompatUILayout compatUILayout = this.mLayout;
        if (compatUILayout != null) {
            boolean z = this.mHasSizeCompat;
            compatUILayout.setViewVisibility(C1777R.C1779id.size_compat_restart_button, z);
            if (!z) {
                compatUILayout.setViewVisibility(C1777R.C1779id.size_compat_hint, false);
            }
            if (this.mHasSizeCompat && !this.mCompatUIHintsState.mHasShownSizeCompatHint) {
                CompatUILayout compatUILayout2 = this.mLayout;
                Objects.requireNonNull(compatUILayout2);
                compatUILayout2.setViewVisibility(C1777R.C1779id.size_compat_hint, true);
                this.mCompatUIHintsState.mHasShownSizeCompatHint = true;
            }
            CompatUILayout compatUILayout3 = this.mLayout;
            boolean shouldShowCameraControl = shouldShowCameraControl();
            Objects.requireNonNull(compatUILayout3);
            compatUILayout3.setViewVisibility(C1777R.C1779id.camera_compat_control, shouldShowCameraControl);
            if (!shouldShowCameraControl) {
                compatUILayout3.setViewVisibility(C1777R.C1779id.camera_compat_hint, false);
            }
            if (shouldShowCameraControl() && !this.mCompatUIHintsState.mHasShownCameraCompatHint) {
                CompatUILayout compatUILayout4 = this.mLayout;
                Objects.requireNonNull(compatUILayout4);
                compatUILayout4.setViewVisibility(C1777R.C1779id.camera_compat_hint, true);
                this.mCompatUIHintsState.mHasShownCameraCompatHint = true;
            }
            if (shouldShowCameraControl()) {
                this.mLayout.updateCameraTreatmentButton(this.mCameraCompatControlState);
            }
        }
    }

    public final View createLayout() {
        TaskAppearedInfo taskAppearedInfo;
        ActivityInfo activityInfo;
        CompatUILayout inflateLayout = inflateLayout();
        this.mLayout = inflateLayout;
        Objects.requireNonNull(inflateLayout);
        inflateLayout.mWindowManager = this;
        updateVisibilityOfViews();
        if (this.mHasSizeCompat) {
            CompatUIController.CompatUICallback compatUICallback = this.mCallback;
            int i = this.mTaskId;
            ShellTaskOrganizer shellTaskOrganizer = (ShellTaskOrganizer) compatUICallback;
            Objects.requireNonNull(shellTaskOrganizer);
            synchronized (shellTaskOrganizer.mLock) {
                taskAppearedInfo = shellTaskOrganizer.mTasks.get(i);
            }
            if (!(taskAppearedInfo == null || (activityInfo = taskAppearedInfo.getTaskInfo().topActivityInfo) == null)) {
                FrameworkStatsLog.write(387, activityInfo.applicationInfo.uid, 1);
            }
        }
        return this.mLayout;
    }

    public final View getLayout() {
        return this.mLayout;
    }
}
