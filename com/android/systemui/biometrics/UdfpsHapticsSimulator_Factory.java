package com.android.systemui.biometrics;

import android.os.Handler;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.unfold.FoldAodAnimationController;
import com.android.systemui.util.settings.GlobalSettings;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

public final class UdfpsHapticsSimulator_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider commandRegistryProvider;
    public final Provider keyguardUpdateMonitorProvider;
    public final Provider vibratorProvider;

    public /* synthetic */ UdfpsHapticsSimulator_Factory(Provider provider, Provider provider2, Provider provider3, int i) {
        this.$r8$classId = i;
        this.commandRegistryProvider = provider;
        this.vibratorProvider = provider2;
        this.keyguardUpdateMonitorProvider = provider3;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get();
                return new UdfpsHapticsSimulator((CommandRegistry) this.commandRegistryProvider.get(), (VibratorHelper) this.vibratorProvider.get());
            case 1:
                return new ScreenOffAnimationController((Optional) this.commandRegistryProvider.get(), (UnlockedScreenOffAnimationController) this.vibratorProvider.get(), (WakefulnessLifecycle) this.keyguardUpdateMonitorProvider.get());
            default:
                return new FoldAodAnimationController((Handler) this.commandRegistryProvider.get(), (WakefulnessLifecycle) this.vibratorProvider.get(), (GlobalSettings) this.keyguardUpdateMonitorProvider.get());
        }
    }
}
