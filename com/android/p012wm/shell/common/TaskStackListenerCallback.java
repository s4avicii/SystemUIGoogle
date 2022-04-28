package com.android.p012wm.shell.common;

import android.app.ActivityManager;

/* renamed from: com.android.wm.shell.common.TaskStackListenerCallback */
public interface TaskStackListenerCallback {
    void onActivityDismissingDockedStack() {
    }

    void onActivityForcedResizable(String str, int i, int i2) {
    }

    void onActivityLaunchOnSecondaryDisplayFailed() {
    }

    void onActivityPinned(String str) {
    }

    void onActivityRestartAttempt(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2) {
    }

    void onActivityUnpinned() {
    }

    void onRecentTaskListUpdated() {
    }

    void onTaskCreated() {
    }

    void onTaskMovedToFront(int i) {
    }

    void onTaskStackChanged() {
    }

    void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) {
        onTaskMovedToFront(runningTaskInfo.taskId);
    }

    void onActivityLaunchOnSecondaryDisplayFailed$1() {
        onActivityLaunchOnSecondaryDisplayFailed();
    }
}
