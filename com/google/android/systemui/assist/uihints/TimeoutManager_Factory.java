package com.google.android.systemui.assist.uihints;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import com.android.internal.statusbar.IStatusBarService;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.dagger.SectionHeaderControllerSubcomponent;
import com.android.systemui.statusbar.phone.PhoneStatusBarView;
import com.android.systemui.statusbar.policy.RemoteInputUriController;
import com.google.android.systemui.columbus.sensors.config.LowSensitivitySettingAdjustment;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.Collections;
import java.util.Objects;
import javax.inject.Provider;

public final class TimeoutManager_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider assistManagerProvider;

    public /* synthetic */ TimeoutManager_Factory(Provider provider, int i) {
        this.$r8$classId = i;
        this.assistManagerProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new TimeoutManager(DoubleCheck.lazy(this.assistManagerProvider));
            case 1:
                DeviceStateManager deviceStateManager = (DeviceStateManager) ((Context) this.assistManagerProvider.get()).getSystemService(DeviceStateManager.class);
                Objects.requireNonNull(deviceStateManager, "Cannot return null from a non-@Nullable @Provides method");
                return deviceStateManager;
            case 2:
                NodeController nodeController = ((SectionHeaderControllerSubcomponent) this.assistManagerProvider.get()).getNodeController();
                Objects.requireNonNull(nodeController, "Cannot return null from a non-@Nullable @Provides method");
                return nodeController;
            case 3:
                BatteryMeterView batteryMeterView = (BatteryMeterView) ((PhoneStatusBarView) this.assistManagerProvider.get()).findViewById(C1777R.C1779id.battery);
                Objects.requireNonNull(batteryMeterView, "Cannot return null from a non-@Nullable @Provides method");
                return batteryMeterView;
            case 4:
                return new RemoteInputUriController((IStatusBarService) this.assistManagerProvider.get());
            default:
                return Collections.singletonList((LowSensitivitySettingAdjustment) this.assistManagerProvider.get());
        }
    }
}
