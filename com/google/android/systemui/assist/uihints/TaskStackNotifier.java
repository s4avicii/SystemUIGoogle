package com.google.android.systemui.assist.uihints;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.util.Log;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.google.android.systemui.assist.uihints.NgaMessageHandler;
import java.util.Objects;

public final class TaskStackNotifier implements NgaMessageHandler.ConfigInfoListener {
    public PendingIntent mIntent;
    public final C21861 mListener = new TaskStackChangeListener() {
        public final void onTaskCreated(ComponentName componentName) {
            TaskStackNotifier taskStackNotifier = TaskStackNotifier.this;
            Objects.requireNonNull(taskStackNotifier);
            PendingIntent pendingIntent = taskStackNotifier.mIntent;
            if (pendingIntent != null) {
                try {
                    pendingIntent.send();
                } catch (PendingIntent.CanceledException e) {
                    Log.e("TaskStackNotifier", "could not send intent", e);
                }
            }
        }

        public final void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) {
            TaskStackNotifier taskStackNotifier = TaskStackNotifier.this;
            Objects.requireNonNull(taskStackNotifier);
            PendingIntent pendingIntent = taskStackNotifier.mIntent;
            if (pendingIntent != null) {
                try {
                    pendingIntent.send();
                } catch (PendingIntent.CanceledException e) {
                    Log.e("TaskStackNotifier", "could not send intent", e);
                }
            }
        }
    };
    public boolean mListenerRegistered = false;
    public final TaskStackChangeListeners mListeners = TaskStackChangeListeners.INSTANCE;

    public final void onConfigInfo(NgaMessageHandler.ConfigInfo configInfo) {
        PendingIntent pendingIntent = configInfo.onTaskChange;
        this.mIntent = pendingIntent;
        if (pendingIntent != null && !this.mListenerRegistered) {
            this.mListeners.registerTaskStackListener(this.mListener);
            this.mListenerRegistered = true;
        } else if (pendingIntent == null && this.mListenerRegistered) {
            this.mListeners.unregisterTaskStackListener(this.mListener);
            this.mListenerRegistered = false;
        }
    }
}
