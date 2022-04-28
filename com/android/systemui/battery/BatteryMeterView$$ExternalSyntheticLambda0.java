package com.android.systemui.battery;

import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.policy.BatteryController;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BatteryMeterView$$ExternalSyntheticLambda0 implements BatteryController.EstimateFetchCompletion {
    public final /* synthetic */ BatteryMeterView f$0;

    public /* synthetic */ BatteryMeterView$$ExternalSyntheticLambda0(BatteryMeterView batteryMeterView) {
        this.f$0 = batteryMeterView;
    }

    public final void onBatteryRemainingEstimateRetrieved(String str) {
        BatteryMeterView batteryMeterView = this.f$0;
        int i = BatteryMeterView.$r8$clinit;
        Objects.requireNonNull(batteryMeterView);
        TextView textView = batteryMeterView.mBatteryPercentView;
        if (textView != null) {
            if (str == null || batteryMeterView.mShowPercentMode != 3) {
                batteryMeterView.setPercentTextAtCurrentLevel();
                return;
            }
            textView.setText(str);
            batteryMeterView.setContentDescription(batteryMeterView.getContext().getString(C1777R.string.accessibility_battery_level_with_estimate, new Object[]{Integer.valueOf(batteryMeterView.mLevel), str}));
        }
    }
}
