package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.view.IWindowManager;
import com.android.p012wm.shell.common.DisplayController;
import com.android.p012wm.shell.common.DisplayInsetsController;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.systemui.statusbar.CommandQueue;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class RemoteInputQuickSettingsDisabler_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider commandQueueProvider;
    public final Provider configControllerProvider;
    public final Provider contextProvider;

    public /* synthetic */ RemoteInputQuickSettingsDisabler_Factory(Provider provider, Provider provider2, Provider provider3, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.commandQueueProvider = provider2;
        this.configControllerProvider = provider3;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new RemoteInputQuickSettingsDisabler((Context) this.contextProvider.get(), (CommandQueue) this.commandQueueProvider.get(), (ConfigurationController) this.configControllerProvider.get());
            default:
                return new DisplayInsetsController((IWindowManager) this.contextProvider.get(), (DisplayController) this.commandQueueProvider.get(), (ShellExecutor) this.configControllerProvider.get());
        }
    }
}
