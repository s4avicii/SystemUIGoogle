package com.android.systemui.globalactions;

import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.CoreStartable;
import com.android.systemui.p006qs.QSPanel$$ExternalSyntheticLambda1;
import com.android.systemui.plugins.GlobalActions;
import com.android.systemui.shared.plugins.PluginManager;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.ExtensionController;
import com.android.systemui.statusbar.policy.ExtensionControllerImpl;
import java.util.Objects;
import javax.inject.Provider;

public class GlobalActionsComponent extends CoreStartable implements CommandQueue.Callbacks, GlobalActions.GlobalActionsManager {
    public IStatusBarService mBarService;
    public final CommandQueue mCommandQueue;
    public ExtensionControllerImpl.ExtensionImpl mExtension;
    public final ExtensionController mExtensionController;
    public final Provider<GlobalActions> mGlobalActionsProvider;
    public GlobalActions mPlugin;
    public StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;

    public final void handleShowGlobalActionsMenu() {
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.mStatusBarKeyguardViewManager;
        Objects.requireNonNull(statusBarKeyguardViewManager);
        statusBarKeyguardViewManager.mGlobalActionsVisible = true;
        statusBarKeyguardViewManager.updateStates();
        ExtensionControllerImpl.ExtensionImpl extensionImpl = this.mExtension;
        Objects.requireNonNull(extensionImpl);
        ((GlobalActions) extensionImpl.mItem).showGlobalActions(this);
    }

    public final void handleShowShutdownUi(boolean z, String str) {
        ExtensionControllerImpl.ExtensionImpl extensionImpl = this.mExtension;
        Objects.requireNonNull(extensionImpl);
        ((GlobalActions) extensionImpl.mItem).showShutdownUi(z, str);
    }

    public final void onGlobalActionsHidden() {
        try {
            StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.mStatusBarKeyguardViewManager;
            Objects.requireNonNull(statusBarKeyguardViewManager);
            statusBarKeyguardViewManager.mGlobalActionsVisible = false;
            statusBarKeyguardViewManager.updateStates();
            this.mBarService.onGlobalActionsHidden();
        } catch (RemoteException unused) {
        }
    }

    public final void onGlobalActionsShown() {
        try {
            this.mBarService.onGlobalActionsShown();
        } catch (RemoteException unused) {
        }
    }

    public final void reboot(boolean z) {
        try {
            this.mBarService.reboot(z);
        } catch (RemoteException unused) {
        }
    }

    public final void shutdown() {
        try {
            this.mBarService.shutdown();
        } catch (RemoteException unused) {
        }
    }

    public final void start() {
        Class<GlobalActions> cls = GlobalActions.class;
        this.mBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        ExtensionControllerImpl.ExtensionBuilder newExtension = this.mExtensionController.newExtension();
        Objects.requireNonNull(newExtension);
        String action = PluginManager.Helper.getAction(cls);
        ExtensionControllerImpl.ExtensionImpl<T> extensionImpl = newExtension.mExtension;
        Objects.requireNonNull(extensionImpl);
        extensionImpl.mProducers.add(new ExtensionControllerImpl.ExtensionImpl.PluginItem(action, cls, (ExtensionController.PluginConverter) null));
        Provider<GlobalActions> provider = this.mGlobalActionsProvider;
        Objects.requireNonNull(provider);
        newExtension.withDefault(new GlobalActionsComponent$$ExternalSyntheticLambda0(provider));
        newExtension.mExtension.mCallbacks.add(new QSPanel$$ExternalSyntheticLambda1(this, 2));
        ExtensionControllerImpl.ExtensionImpl build = newExtension.build();
        this.mExtension = build;
        Objects.requireNonNull(build);
        this.mPlugin = (GlobalActions) build.mItem;
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
    }

    public GlobalActionsComponent(Context context, CommandQueue commandQueue, ExtensionController extensionController, Provider<GlobalActions> provider, StatusBarKeyguardViewManager statusBarKeyguardViewManager) {
        super(context);
        this.mCommandQueue = commandQueue;
        this.mExtensionController = extensionController;
        this.mGlobalActionsProvider = provider;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
    }
}
