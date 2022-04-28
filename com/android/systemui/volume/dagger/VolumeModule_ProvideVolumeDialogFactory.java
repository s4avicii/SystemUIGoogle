package com.android.systemui.volume.dagger;

import android.content.Context;
import com.android.systemui.media.dialog.MediaOutputDialogFactory;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.VolumeDialog;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.statusbar.policy.AccessibilityManagerWrapper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.volume.VolumeDialogImpl;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class VolumeModule_ProvideVolumeDialogFactory implements Factory<VolumeDialog> {
    public final Provider<AccessibilityManagerWrapper> accessibilityManagerWrapperProvider;
    public final Provider<ActivityStarter> activityStarterProvider;
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DeviceProvisionedController> deviceProvisionedControllerProvider;
    public final Provider<MediaOutputDialogFactory> mediaOutputDialogFactoryProvider;
    public final Provider<VolumeDialogController> volumeDialogControllerProvider;

    public static VolumeModule_ProvideVolumeDialogFactory create(Provider<Context> provider, Provider<VolumeDialogController> provider2, Provider<AccessibilityManagerWrapper> provider3, Provider<DeviceProvisionedController> provider4, Provider<ConfigurationController> provider5, Provider<MediaOutputDialogFactory> provider6, Provider<ActivityStarter> provider7) {
        return new VolumeModule_ProvideVolumeDialogFactory(provider, provider2, provider3, provider4, provider5, provider6, provider7);
    }

    public final Object get() {
        VolumeDialogImpl volumeDialogImpl = new VolumeDialogImpl(this.contextProvider.get(), this.volumeDialogControllerProvider.get(), this.accessibilityManagerWrapperProvider.get(), this.deviceProvisionedControllerProvider.get(), this.configurationControllerProvider.get(), this.mediaOutputDialogFactoryProvider.get(), this.activityStarterProvider.get());
        volumeDialogImpl.mHandler.obtainMessage(5, 1, 0).sendToTarget();
        if (!volumeDialogImpl.mAutomute) {
            volumeDialogImpl.mAutomute = true;
            volumeDialogImpl.mHandler.sendEmptyMessage(4);
        }
        if (volumeDialogImpl.mSilentMode) {
            volumeDialogImpl.mSilentMode = false;
            volumeDialogImpl.mHandler.sendEmptyMessage(4);
        }
        return volumeDialogImpl;
    }

    public VolumeModule_ProvideVolumeDialogFactory(Provider<Context> provider, Provider<VolumeDialogController> provider2, Provider<AccessibilityManagerWrapper> provider3, Provider<DeviceProvisionedController> provider4, Provider<ConfigurationController> provider5, Provider<MediaOutputDialogFactory> provider6, Provider<ActivityStarter> provider7) {
        this.contextProvider = provider;
        this.volumeDialogControllerProvider = provider2;
        this.accessibilityManagerWrapperProvider = provider3;
        this.deviceProvisionedControllerProvider = provider4;
        this.configurationControllerProvider = provider5;
        this.mediaOutputDialogFactoryProvider = provider6;
        this.activityStarterProvider = provider7;
    }
}
