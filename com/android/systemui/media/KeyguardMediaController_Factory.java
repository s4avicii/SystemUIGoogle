package com.android.systemui.media;

import android.content.Context;
import android.os.Handler;
import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.common.DisplayController;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.TransactionPool;
import com.android.p012wm.shell.transition.Transitions;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class KeyguardMediaController_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider bypassControllerProvider;
    public final Provider configurationControllerProvider;
    public final Provider contextProvider;
    public final Provider mediaFlagsProvider;
    public final Provider mediaHostProvider;
    public final Provider notifLockscreenUserManagerProvider;
    public final Provider statusBarStateControllerProvider;

    public /* synthetic */ KeyguardMediaController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, int i) {
        this.$r8$classId = i;
        this.mediaHostProvider = provider;
        this.bypassControllerProvider = provider2;
        this.statusBarStateControllerProvider = provider3;
        this.notifLockscreenUserManagerProvider = provider4;
        this.contextProvider = provider5;
        this.configurationControllerProvider = provider6;
        this.mediaFlagsProvider = provider7;
    }

    public static KeyguardMediaController_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7) {
        return new KeyguardMediaController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, 0);
    }

    public static KeyguardMediaController_Factory create$1(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7) {
        return new KeyguardMediaController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, 1);
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new KeyguardMediaController((MediaHost) this.mediaHostProvider.get(), (KeyguardBypassController) this.bypassControllerProvider.get(), (SysuiStatusBarStateController) this.statusBarStateControllerProvider.get(), (NotificationLockscreenUserManager) this.notifLockscreenUserManagerProvider.get(), (Context) this.contextProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (MediaFlags) this.mediaFlagsProvider.get());
            default:
                return new Transitions((ShellTaskOrganizer) this.mediaHostProvider.get(), (TransactionPool) this.bypassControllerProvider.get(), (DisplayController) this.statusBarStateControllerProvider.get(), (Context) this.notifLockscreenUserManagerProvider.get(), (ShellExecutor) this.contextProvider.get(), (Handler) this.configurationControllerProvider.get(), (ShellExecutor) this.mediaFlagsProvider.get());
        }
    }
}
