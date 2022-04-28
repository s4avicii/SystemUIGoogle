package com.android.p012wm.shell.splitscreen;

import android.util.Slog;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.util.FrameworkStatsLog;
import com.android.systemui.plugins.FalsingManager;

/* renamed from: com.android.wm.shell.splitscreen.SplitscreenEventLogger */
public final class SplitscreenEventLogger {
    public int mDragEnterPosition;
    public InstanceId mDragEnterSessionId;
    public final InstanceIdSequence mIdSequence = new InstanceIdSequence(Integer.MAX_VALUE);
    public int mLastMainStagePosition = -1;
    public int mLastMainStageUid = -1;
    public int mLastSideStagePosition = -1;
    public int mLastSideStageUid = -1;
    public float mLastSplitRatio = -1.0f;
    public InstanceId mLoggerSessionId;

    public static int getMainStagePositionFromSplitPosition(int i, boolean z) {
        if (i == -1) {
            return 0;
        }
        return z ? i == 0 ? 1 : 2 : i == 0 ? 3 : 4;
    }

    public static int getSideStagePositionFromSplitPosition(int i, boolean z) {
        if (i == -1) {
            return 0;
        }
        return z ? i == 0 ? 1 : 2 : i == 0 ? 3 : 4;
    }

    public final void logEnter(float f, int i, int i2, int i3, int i4, boolean z) {
        int i5;
        float f2 = f;
        boolean z2 = z;
        this.mLoggerSessionId = this.mIdSequence.newInstanceId();
        int i6 = this.mDragEnterPosition;
        boolean z3 = true;
        if (i6 != -1) {
            if (z2) {
                if (i6 == 0) {
                    i5 = 2;
                } else {
                    i5 = 4;
                }
            } else if (i6 == 0) {
                i5 = 3;
            } else {
                i5 = 5;
            }
            int i7 = i;
        } else {
            int i8 = i;
            i5 = 1;
        }
        updateMainStageState(getMainStagePositionFromSplitPosition(i, z2), i2);
        updateSideStageState(getSideStagePositionFromSplitPosition(i3, z2), i4);
        int i9 = 0;
        if (Float.compare(this.mLastSplitRatio, f) == 0) {
            z3 = false;
        }
        if (z3) {
            this.mLastSplitRatio = f2;
        }
        int i10 = this.mLastMainStagePosition;
        int i11 = this.mLastMainStageUid;
        int i12 = this.mLastSideStagePosition;
        int i13 = this.mLastSideStageUid;
        InstanceId instanceId = this.mDragEnterSessionId;
        if (instanceId != null) {
            i9 = instanceId.getId();
        }
        FrameworkStatsLog.write(388, 1, i5, 0, f, i10, i11, i12, i13, i9, this.mLoggerSessionId.getId());
    }

    public final void logExit(int i, int i2, int i3, int i4, int i5, boolean z) {
        int i6;
        int i7 = i;
        int i8 = i2;
        int i9 = i4;
        boolean z2 = z;
        if (this.mLoggerSessionId != null) {
            if ((i8 == -1 || i9 == -1) && (i3 == 0 || i5 == 0)) {
                switch (i7) {
                    case 1:
                        i6 = 8;
                        break;
                    case 2:
                        i6 = 7;
                        break;
                    case 3:
                        i6 = 5;
                        break;
                    case 4:
                        i6 = 1;
                        break;
                    case 5:
                        i6 = 2;
                        break;
                    case FalsingManager.VERSION:
                        i6 = 6;
                        break;
                    case 7:
                        i6 = 3;
                        break;
                    case 8:
                        i6 = 4;
                        break;
                    default:
                        Slog.e("SplitscreenEventLogger", "Unknown exit reason: " + i7);
                        i6 = 0;
                        break;
                }
                FrameworkStatsLog.write(388, 2, 0, i6, 0.0f, getMainStagePositionFromSplitPosition(i8, z2), i3, getSideStagePositionFromSplitPosition(i9, z2), i5, 0, this.mLoggerSessionId.getId());
                this.mLoggerSessionId = null;
                this.mDragEnterPosition = -1;
                this.mDragEnterSessionId = null;
                this.mLastMainStagePosition = -1;
                this.mLastMainStageUid = -1;
                this.mLastSideStagePosition = -1;
                this.mLastSideStageUid = -1;
                return;
            }
            throw new IllegalArgumentException("Only main or side stage should be set");
        }
    }

    public final boolean updateMainStageState(int i, int i2) {
        boolean z;
        if (this.mLastMainStagePosition == i && this.mLastMainStageUid == i2) {
            z = false;
        } else {
            z = true;
        }
        if (!z) {
            return false;
        }
        this.mLastMainStagePosition = i;
        this.mLastMainStageUid = i2;
        return true;
    }

    public final boolean updateSideStageState(int i, int i2) {
        boolean z;
        if (this.mLastSideStagePosition == i && this.mLastSideStageUid == i2) {
            z = false;
        } else {
            z = true;
        }
        if (!z) {
            return false;
        }
        this.mLastSideStagePosition = i;
        this.mLastSideStageUid = i2;
        return true;
    }
}
