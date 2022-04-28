package com.google.android.systemui.smartspace;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import com.android.settingslib.devicestate.DeviceStateRotationLockSettingsManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.statusbar.policy.DeviceStateRotationLockSettingController;
import com.android.systemui.util.wrapper.RotationPolicyWrapper;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class KeyguardSmartspaceController_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider contextProvider;
    public final Provider featureFlagsProvider;
    public final Provider mediaControllerProvider;
    public final Provider zenControllerProvider;

    public /* synthetic */ KeyguardSmartspaceController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.featureFlagsProvider = provider2;
        this.zenControllerProvider = provider3;
        this.mediaControllerProvider = provider4;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new KeyguardSmartspaceController((Context) this.contextProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), (KeyguardZenAlarmViewController) this.zenControllerProvider.get(), (KeyguardMediaViewController) this.mediaControllerProvider.get());
            default:
                return new DeviceStateRotationLockSettingController((RotationPolicyWrapper) this.contextProvider.get(), (DeviceStateManager) this.featureFlagsProvider.get(), (Executor) this.zenControllerProvider.get(), (DeviceStateRotationLockSettingsManager) this.mediaControllerProvider.get());
        }
    }
}
