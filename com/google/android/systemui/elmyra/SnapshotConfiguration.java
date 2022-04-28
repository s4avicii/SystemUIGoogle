package com.google.android.systemui.elmyra;

import android.content.Context;
import com.android.p012wm.shell.C1777R;

public final class SnapshotConfiguration {
    public final int mCompleteGestures;
    public final int mIncompleteGestures;
    public final int mSnapshotDelayAfterGesture;

    public SnapshotConfiguration(Context context) {
        this.mCompleteGestures = context.getResources().getInteger(C1777R.integer.elmyra_snapshot_logger_gesture_size);
        this.mIncompleteGestures = context.getResources().getInteger(C1777R.integer.elmyra_snapshot_logger_incomplete_gesture_size);
        this.mSnapshotDelayAfterGesture = context.getResources().getInteger(C1777R.integer.elmyra_snapshot_gesture_delay_ms);
    }
}
