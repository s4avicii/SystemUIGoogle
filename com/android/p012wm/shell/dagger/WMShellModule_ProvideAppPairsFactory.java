package com.android.p012wm.shell.dagger;

import android.content.Context;
import com.android.keyguard.KeyguardViewController;
import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.apppairs.AppPairsController;
import com.android.p012wm.shell.common.DisplayController;
import com.android.p012wm.shell.common.DisplayImeController;
import com.android.p012wm.shell.common.DisplayInsetsController;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.WMShellModule_ProvideAppPairsFactory */
public final class WMShellModule_ProvideAppPairsFactory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider displayControllerProvider;
    public final Provider displayImeControllerProvider;
    public final Provider displayInsetsControllerProvider;
    public final Provider mainExecutorProvider;
    public final Provider shellTaskOrganizerProvider;
    public final Provider syncQueueProvider;

    public /* synthetic */ WMShellModule_ProvideAppPairsFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, int i) {
        this.$r8$classId = i;
        this.shellTaskOrganizerProvider = provider;
        this.syncQueueProvider = provider2;
        this.displayControllerProvider = provider3;
        this.mainExecutorProvider = provider4;
        this.displayImeControllerProvider = provider5;
        this.displayInsetsControllerProvider = provider6;
    }

    public static WMShellModule_ProvideAppPairsFactory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        return new WMShellModule_ProvideAppPairsFactory(provider, provider2, provider3, provider4, provider5, provider6, 1);
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new AppPairsController((ShellTaskOrganizer) this.shellTaskOrganizerProvider.get(), (SyncTransactionQueue) this.syncQueueProvider.get(), (DisplayController) this.displayControllerProvider.get(), (ShellExecutor) this.mainExecutorProvider.get(), (DisplayImeController) this.displayImeControllerProvider.get(), (DisplayInsetsController) this.displayInsetsControllerProvider.get());
            default:
                return new KeyguardUnlockAnimationController((Context) this.shellTaskOrganizerProvider.get(), (KeyguardStateController) this.syncQueueProvider.get(), DoubleCheck.lazy(this.displayControllerProvider), (KeyguardViewController) this.mainExecutorProvider.get(), (FeatureFlags) this.displayImeControllerProvider.get(), DoubleCheck.lazy(this.displayInsetsControllerProvider));
        }
    }
}
