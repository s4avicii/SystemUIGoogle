package com.android.systemui.sensorprivacy;

import com.android.internal.util.FrameworkStatsLog;
import com.android.systemui.plugins.ActivityStarter;

/* compiled from: SensorUseStartedActivity.kt */
public final class SensorUseStartedActivity$onClick$1 implements ActivityStarter.OnDismissAction {
    public final /* synthetic */ SensorUseStartedActivity this$0;

    public SensorUseStartedActivity$onClick$1(SensorUseStartedActivity sensorUseStartedActivity) {
        this.this$0 = sensorUseStartedActivity;
    }

    public final boolean onDismiss() {
        final SensorUseStartedActivity sensorUseStartedActivity = this.this$0;
        sensorUseStartedActivity.bgHandler.postDelayed(new Runnable() {
            public final void run() {
                SensorUseStartedActivity sensorUseStartedActivity = sensorUseStartedActivity;
                int i = SensorUseStartedActivity.$r8$clinit;
                sensorUseStartedActivity.disableSensorPrivacy();
                String str = sensorUseStartedActivity.sensorUsePackageName;
                if (str == null) {
                    str = null;
                }
                FrameworkStatsLog.write(382, 1, str);
            }
        }, 200);
        return false;
    }
}
