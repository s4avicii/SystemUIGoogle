package com.android.systemui.globalactions;

import android.content.Context;
import com.android.systemui.plugins.GlobalActions;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.ExtensionController;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class GlobalActionsComponent_Factory implements Factory<GlobalActionsComponent> {
    public final Provider<CommandQueue> commandQueueProvider;
    public final Provider<Context> contextProvider;
    public final Provider<ExtensionController> extensionControllerProvider;
    public final Provider<GlobalActions> globalActionsProvider;
    public final Provider<StatusBarKeyguardViewManager> statusBarKeyguardViewManagerProvider;

    public final Object get() {
        return new GlobalActionsComponent(this.contextProvider.get(), this.commandQueueProvider.get(), this.extensionControllerProvider.get(), this.globalActionsProvider, this.statusBarKeyguardViewManagerProvider.get());
    }

    public GlobalActionsComponent_Factory(Provider provider, Provider provider2, Provider provider3, GlobalActionsImpl_Factory globalActionsImpl_Factory, Provider provider4) {
        this.contextProvider = provider;
        this.commandQueueProvider = provider2;
        this.extensionControllerProvider = provider3;
        this.globalActionsProvider = globalActionsImpl_Factory;
        this.statusBarKeyguardViewManagerProvider = provider4;
    }
}
