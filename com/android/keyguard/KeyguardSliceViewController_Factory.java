package com.android.keyguard;

import android.content.Context;
import com.android.p012wm.shell.common.DisplayInsetsController;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.fullscreen.FullscreenUnfoldController;
import com.android.p012wm.shell.unfold.ShellUnfoldProgressProvider;
import com.android.p012wm.shell.unfold.UnfoldBackgroundController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyboard.KeyboardUI_Factory;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.tuner.TunerService;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

public final class KeyguardSliceViewController_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider activityStarterProvider;
    public final Provider configurationControllerProvider;
    public final Provider dumpManagerProvider;
    public final Provider keyguardSliceViewProvider;
    public final Provider tunerServiceProvider;

    public /* synthetic */ KeyguardSliceViewController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, int i) {
        this.$r8$classId = i;
        this.keyguardSliceViewProvider = provider;
        this.activityStarterProvider = provider2;
        this.configurationControllerProvider = provider3;
        this.tunerServiceProvider = provider4;
        this.dumpManagerProvider = provider5;
    }

    public static KeyguardSliceViewController_Factory create(KeyboardUI_Factory keyboardUI_Factory, Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        return new KeyguardSliceViewController_Factory(keyboardUI_Factory, provider, provider2, provider3, provider4, 0);
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new KeyguardSliceViewController((KeyguardSliceView) this.keyguardSliceViewProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (TunerService) this.tunerServiceProvider.get(), (DumpManager) this.dumpManagerProvider.get());
            default:
                Lazy lazy = DoubleCheck.lazy(this.configurationControllerProvider);
                return new FullscreenUnfoldController((Context) this.keyguardSliceViewProvider.get(), (ShellExecutor) this.dumpManagerProvider.get(), (UnfoldBackgroundController) lazy.get(), (ShellUnfoldProgressProvider) ((Optional) this.activityStarterProvider.get()).get(), (DisplayInsetsController) this.tunerServiceProvider.get());
        }
    }
}
