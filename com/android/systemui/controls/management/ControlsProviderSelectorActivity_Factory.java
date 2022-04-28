package com.android.systemui.controls.management;

import android.content.Context;
import com.android.p012wm.shell.common.DisplayInsetsController;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.TransactionPool;
import com.android.p012wm.shell.dagger.WMShellModule$$ExternalSyntheticLambda0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.p004ui.ControlsUiController;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class ControlsProviderSelectorActivity_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider backExecutorProvider;
    public final Provider broadcastDispatcherProvider;
    public final Provider controlsControllerProvider;
    public final Provider executorProvider;
    public final Provider listingControllerProvider;
    public final Provider uiControllerProvider;

    public /* synthetic */ ControlsProviderSelectorActivity_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, int i) {
        this.$r8$classId = i;
        this.executorProvider = provider;
        this.backExecutorProvider = provider2;
        this.listingControllerProvider = provider3;
        this.controlsControllerProvider = provider4;
        this.broadcastDispatcherProvider = provider5;
        this.uiControllerProvider = provider6;
    }

    public static ControlsProviderSelectorActivity_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        return new ControlsProviderSelectorActivity_Factory(provider, provider2, provider3, provider4, provider5, provider6, 0);
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new ControlsProviderSelectorActivity((Executor) this.executorProvider.get(), (Executor) this.backExecutorProvider.get(), (ControlsListingController) this.listingControllerProvider.get(), (ControlsController) this.controlsControllerProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (ControlsUiController) this.uiControllerProvider.get());
            default:
                Optional map = ((Optional) this.executorProvider.get()).map(new WMShellModule$$ExternalSyntheticLambda0((Context) this.backExecutorProvider.get(), (TransactionPool) this.listingControllerProvider.get(), (DisplayInsetsController) this.broadcastDispatcherProvider.get(), DoubleCheck.lazy(this.controlsControllerProvider), (ShellExecutor) this.uiControllerProvider.get()));
                Objects.requireNonNull(map, "Cannot return null from a non-@Nullable @Provides method");
                return map;
        }
    }
}
