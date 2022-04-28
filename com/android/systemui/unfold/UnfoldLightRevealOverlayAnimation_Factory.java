package com.android.systemui.unfold;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.view.IWindowManager;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.SystemWindows;
import com.android.p012wm.shell.pip.PipBoundsState;
import com.android.p012wm.shell.pip.PipMediaController;
import com.android.p012wm.shell.pip.PipUiEventLogger;
import com.android.p012wm.shell.pip.phone.PhonePipMenuController;
import dagger.internal.Factory;
import java.util.Optional;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class UnfoldLightRevealOverlayAnimation_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider backgroundExecutorProvider;
    public final Provider contextProvider;
    public final Provider deviceStateManagerProvider;
    public final Provider displayAreaHelperProvider;
    public final Provider displayManagerProvider;
    public final Provider executorProvider;
    public final Provider unfoldTransitionProgressProvider;
    public final Provider windowManagerInterfaceProvider;

    public /* synthetic */ UnfoldLightRevealOverlayAnimation_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.deviceStateManagerProvider = provider2;
        this.displayManagerProvider = provider3;
        this.unfoldTransitionProgressProvider = provider4;
        this.displayAreaHelperProvider = provider5;
        this.executorProvider = provider6;
        this.backgroundExecutorProvider = provider7;
        this.windowManagerInterfaceProvider = provider8;
    }

    public static UnfoldLightRevealOverlayAnimation_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        return new UnfoldLightRevealOverlayAnimation_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, 0);
    }

    public static UnfoldLightRevealOverlayAnimation_Factory create$1(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        return new UnfoldLightRevealOverlayAnimation_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, 1);
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new UnfoldLightRevealOverlayAnimation((Context) this.contextProvider.get(), (DeviceStateManager) this.deviceStateManagerProvider.get(), (DisplayManager) this.displayManagerProvider.get(), (UnfoldTransitionProgressProvider) this.unfoldTransitionProgressProvider.get(), (Optional) this.displayAreaHelperProvider.get(), (Executor) this.executorProvider.get(), (Executor) this.backgroundExecutorProvider.get(), (IWindowManager) this.windowManagerInterfaceProvider.get());
            default:
                return new PhonePipMenuController((Context) this.contextProvider.get(), (PipBoundsState) this.deviceStateManagerProvider.get(), (PipMediaController) this.displayManagerProvider.get(), (SystemWindows) this.unfoldTransitionProgressProvider.get(), (Optional) this.displayAreaHelperProvider.get(), (PipUiEventLogger) this.executorProvider.get(), (ShellExecutor) this.backgroundExecutorProvider.get(), (Handler) this.windowManagerInterfaceProvider.get());
        }
    }
}
