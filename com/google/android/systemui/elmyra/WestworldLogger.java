package com.google.android.systemui.elmyra;

import android.app.StatsManager;
import android.content.Context;
import android.util.Log;
import com.android.internal.util.ConcurrentUtils;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.google.android.systemui.elmyra.proto.nano.ChassisProtos$Chassis;
import com.google.android.systemui.elmyra.proto.nano.SnapshotProtos$Snapshot;
import com.google.android.systemui.elmyra.sensors.GestureSensor;
import com.google.android.systemui.elmyra.sensors.config.GestureConfiguration;
import java.util.concurrent.CountDownLatch;

public final class WestworldLogger implements GestureSensor.Listener {
    public ChassisProtos$Chassis mChassis = null;
    public CountDownLatch mCountDownLatch;
    public GestureConfiguration mGestureConfiguration;
    public Object mMutex;
    public SnapshotProtos$Snapshot mSnapshot;
    public SnapshotController mSnapshotController;
    public final WestworldLogger$$ExternalSyntheticLambda0 mWestworldCallback;

    public final void onGestureDetected(GestureSensor.DetectionProperties detectionProperties) {
        SysUiStatsLog.write(174, 3);
    }

    public final void onGestureProgress(float f, int i) {
        SysUiStatsLog.write(176, (int) (f * 100.0f));
        SysUiStatsLog.write(174, i);
    }

    public WestworldLogger(Context context, GestureConfiguration gestureConfiguration, SnapshotController snapshotController) {
        WestworldLogger$$ExternalSyntheticLambda0 westworldLogger$$ExternalSyntheticLambda0 = new WestworldLogger$$ExternalSyntheticLambda0(this);
        this.mWestworldCallback = westworldLogger$$ExternalSyntheticLambda0;
        this.mGestureConfiguration = gestureConfiguration;
        this.mSnapshotController = snapshotController;
        this.mSnapshot = null;
        this.mMutex = new Object();
        StatsManager statsManager = (StatsManager) context.getSystemService("stats");
        if (statsManager == null) {
            Log.d("Elmyra/Logger", "Failed to get StatsManager");
        }
        try {
            statsManager.setPullAtomCallback(150000, (StatsManager.PullAtomMetadata) null, ConcurrentUtils.DIRECT_EXECUTOR, westworldLogger$$ExternalSyntheticLambda0);
        } catch (RuntimeException e) {
            Log.d("Elmyra/Logger", "Failed to register callback with StatsManager");
            e.printStackTrace();
        }
    }
}
