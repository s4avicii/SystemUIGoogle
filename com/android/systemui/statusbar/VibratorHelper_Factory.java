package com.android.systemui.statusbar;

import android.content.Context;
import android.os.Handler;
import android.os.Vibrator;
import com.android.p012wm.shell.pip.PipMediaController;
import com.android.systemui.dreams.SmartSpaceComplication;
import com.android.systemui.screenshot.DeleteScreenshotReceiver;
import com.android.systemui.screenshot.ScreenshotSmartActions;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.systemui.statusbar.phone.KeyguardEnvironmentImpl;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.unfold.UnfoldSharedModule;
import com.android.systemui.unfold.updates.DeviceFoldStateProvider;
import com.android.systemui.unfold.updates.DeviceFoldStateProvider_Factory;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class VibratorHelper_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Object executorProvider;
    public final Provider vibratorProvider;

    public /* synthetic */ VibratorHelper_Factory(Provider provider, Provider provider2, int i) {
        this.$r8$classId = i;
        this.vibratorProvider = provider;
        this.executorProvider = provider2;
    }

    public VibratorHelper_Factory(UnfoldSharedModule unfoldSharedModule, DeviceFoldStateProvider_Factory deviceFoldStateProvider_Factory) {
        this.$r8$classId = 5;
        this.executorProvider = unfoldSharedModule;
        this.vibratorProvider = deviceFoldStateProvider_Factory;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new VibratorHelper((Vibrator) this.vibratorProvider.get(), (Executor) ((Provider) this.executorProvider).get());
            case 1:
                return new SmartSpaceComplication((Context) this.vibratorProvider.get(), (LockscreenSmartspaceController) ((Provider) this.executorProvider).get());
            case 2:
                return new DeleteScreenshotReceiver((ScreenshotSmartActions) this.vibratorProvider.get(), (Executor) ((Provider) this.executorProvider).get());
            case 3:
                return new KeyguardEnvironmentImpl((NotificationLockscreenUserManager) this.vibratorProvider.get(), (DeviceProvisionedController) ((Provider) this.executorProvider).get());
            case 4:
                return new PipMediaController((Context) this.vibratorProvider.get(), (Handler) ((Provider) this.executorProvider).get());
            default:
                DeviceFoldStateProvider deviceFoldStateProvider = (DeviceFoldStateProvider) this.vibratorProvider.get();
                Objects.requireNonNull((UnfoldSharedModule) this.executorProvider);
                return deviceFoldStateProvider;
        }
    }
}
