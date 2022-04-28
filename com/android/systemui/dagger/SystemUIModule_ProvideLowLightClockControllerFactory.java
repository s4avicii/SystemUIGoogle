package com.android.systemui.dagger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.accessibility.AccessibilityManager;
import com.android.systemui.lowlightclock.LowLightClockController;
import com.android.systemui.statusbar.notification.collection.render.MediaContainerController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

public final class SystemUIModule_ProvideLowLightClockControllerFactory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider optionalControllerProvider;

    public /* synthetic */ SystemUIModule_ProvideLowLightClockControllerFactory(Provider provider, int i) {
        this.$r8$classId = i;
        this.optionalControllerProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                Optional optional = (Optional) this.optionalControllerProvider.get();
                if (!optional.isPresent() || !((LowLightClockController) optional.get()).isLowLightClockEnabled()) {
                    optional = Optional.empty();
                }
                Objects.requireNonNull(optional, "Cannot return null from a non-@Nullable @Provides method");
                return optional;
            case 1:
                AccessibilityManager accessibilityManager = (AccessibilityManager) ((Context) this.optionalControllerProvider.get()).getSystemService(AccessibilityManager.class);
                Objects.requireNonNull(accessibilityManager, "Cannot return null from a non-@Nullable @Provides method");
                return accessibilityManager;
            case 2:
                return new MediaContainerController((LayoutInflater) this.optionalControllerProvider.get());
            default:
                DeviceProvisionedControllerImpl deviceProvisionedControllerImpl = (DeviceProvisionedControllerImpl) this.optionalControllerProvider.get();
                deviceProvisionedControllerImpl.init();
                return deviceProvisionedControllerImpl;
        }
    }
}
