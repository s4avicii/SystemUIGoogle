package com.google.android.systemui.elmyra.gates;

import android.content.Context;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline1;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;

public final class LockTask extends Gate {
    public boolean mIsBlocked;
    public final C22531 mTaskStackListener = new TaskStackChangeListener() {
        public final void onLockTaskModeChanged(int i) {
            ExifInterface$$ExternalSyntheticOutline1.m14m("Mode: ", i, "Elmyra/LockTask");
            if (i == 0) {
                LockTask.this.mIsBlocked = false;
            } else {
                LockTask.this.mIsBlocked = true;
            }
            LockTask.this.notifyListener();
        }
    };

    public final void onActivate() {
        TaskStackChangeListeners.INSTANCE.registerTaskStackListener(this.mTaskStackListener);
    }

    public final void onDeactivate() {
        TaskStackChangeListeners.INSTANCE.unregisterTaskStackListener(this.mTaskStackListener);
    }

    public LockTask(Context context) {
        super(context);
    }

    public final boolean isBlocked() {
        return this.mIsBlocked;
    }
}
