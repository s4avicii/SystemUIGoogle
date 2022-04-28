package com.google.android.systemui.communal.dock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.flags.BooleanFlag;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda1;
import com.android.systemui.util.condition.Condition;
import com.android.systemui.util.condition.Monitor;
import com.android.systemui.util.condition.Monitor$$ExternalSyntheticLambda0;
import dagger.Lazy;
import java.util.Objects;

public class DockEventSimulator extends CoreStartable {
    public final DockingCondition mCondition;
    public final Lazy<Monitor> mDockMonitorLazy;
    public final FeatureFlags mFeatureFlags;
    public final MotionLayout$$ExternalSyntheticOutline0 mFeatureListener = new MotionLayout$$ExternalSyntheticOutline0();

    public static class DockingCondition extends Condition {
        public final Context mContext;
        public final C22001 mReceiver = new BroadcastReceiver() {
            public final void onReceive(Context context, Intent intent) {
                DockingCondition.this.processIntent(intent);
            }
        };

        public final boolean isOverridingCondition() {
            return true;
        }

        public final void processIntent(Intent intent) {
            if (intent != null) {
                boolean z = true;
                intent.getIntExtra("status", 1);
                int intExtra = intent.getIntExtra("plugged", 0);
                intent.getIntExtra("level", 0);
                intent.getIntExtra("health", 1);
                intent.getBooleanExtra("present", true);
                int intExtra2 = intent.getIntExtra("max_charging_current", -1);
                int intExtra3 = intent.getIntExtra("max_charging_voltage", -1);
                if (intExtra3 <= 0) {
                    intExtra3 = 5000000;
                }
                if (intExtra2 > 0) {
                    int i = intExtra2 / 1000;
                    int i2 = intExtra3 / 1000;
                }
                if (!(intExtra == 1 || intExtra == 2 || intExtra == 4 || intExtra == 8)) {
                    z = false;
                }
                updateCondition(z);
            }
        }

        public final void start() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
            processIntent(this.mContext.registerReceiver(this.mReceiver, intentFilter));
        }

        public final void stop() {
            this.mContext.unregisterReceiver(this.mReceiver);
        }

        public DockingCondition(Context context) {
            this.mContext = context;
        }
    }

    public final void start() {
        FeatureFlags featureFlags = this.mFeatureFlags;
        BooleanFlag booleanFlag = Flags.SIMULATE_DOCK_THROUGH_CHARGING;
        featureFlags.addListener();
        if (this.mFeatureFlags.isEnabled(booleanFlag)) {
            Monitor monitor = this.mDockMonitorLazy.get();
            DockingCondition dockingCondition = this.mCondition;
            Objects.requireNonNull(monitor);
            monitor.mExecutor.execute(new Monitor$$ExternalSyntheticLambda0(monitor, dockingCondition, 0));
            return;
        }
        Monitor monitor2 = this.mDockMonitorLazy.get();
        DockingCondition dockingCondition2 = this.mCondition;
        Objects.requireNonNull(monitor2);
        monitor2.mExecutor.execute(new ScrimView$$ExternalSyntheticLambda1(monitor2, dockingCondition2, 3));
    }

    public DockEventSimulator(Context context, FeatureFlags featureFlags, Lazy<Monitor> lazy, DockingCondition dockingCondition) {
        super(context);
        this.mCondition = dockingCondition;
        this.mFeatureFlags = featureFlags;
        this.mDockMonitorLazy = lazy;
    }
}
