package com.google.android.systemui.columbus.gates;

import android.content.Context;
import android.os.Vibrator;
import com.android.keyguard.KeyguardClockSwitch;
import com.android.keyguard.KeyguardStatusView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.p006qs.QuickStatusBarHeader;
import com.android.systemui.statusbar.QsFrameTranslateImpl;
import com.android.systemui.statusbar.phone.StatusBar;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class VrMode_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider contextProvider;

    public /* synthetic */ VrMode_Factory(Provider provider, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new VrMode((Context) this.contextProvider.get());
            case 1:
                return getKeyguardClockSwitch((KeyguardStatusView) this.contextProvider.get());
            case 2:
                return (Vibrator) ((Context) this.contextProvider.get()).getSystemService(Vibrator.class);
            case 3:
                return ((LogBufferFactory) this.contextProvider.get()).create("QSFragmentDisableFlagsLog", 10, 10, false);
            case 4:
                BatteryMeterView batteryMeterView = (BatteryMeterView) ((QuickStatusBarHeader) this.contextProvider.get()).findViewById(C1777R.C1779id.batteryRemainingIcon);
                Objects.requireNonNull(batteryMeterView, "Cannot return null from a non-@Nullable @Provides method");
                return batteryMeterView;
            default:
                StatusBar statusBar = (StatusBar) this.contextProvider.get();
                return new QsFrameTranslateImpl();
        }
    }

    public static KeyguardClockSwitch getKeyguardClockSwitch(KeyguardStatusView keyguardStatusView) {
        KeyguardClockSwitch keyguardClockSwitch = (KeyguardClockSwitch) keyguardStatusView.findViewById(C1777R.C1779id.keyguard_clock_container);
        Objects.requireNonNull(keyguardClockSwitch, "Cannot return null from a non-@Nullable @Provides method");
        return keyguardClockSwitch;
    }
}
