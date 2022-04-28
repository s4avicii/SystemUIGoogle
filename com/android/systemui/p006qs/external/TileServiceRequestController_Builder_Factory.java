package com.android.systemui.p006qs.external;

import com.android.systemui.p006qs.external.TileServiceRequestController;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.external.TileServiceRequestController_Builder_Factory */
public final class TileServiceRequestController_Builder_Factory implements Factory<TileServiceRequestController.Builder> {
    public final Provider<CommandQueue> commandQueueProvider;
    public final Provider<CommandRegistry> commandRegistryProvider;

    public final Object get() {
        return new TileServiceRequestController.Builder(this.commandQueueProvider.get(), this.commandRegistryProvider.get());
    }

    public TileServiceRequestController_Builder_Factory(Provider<CommandQueue> provider, Provider<CommandRegistry> provider2) {
        this.commandQueueProvider = provider;
        this.commandRegistryProvider = provider2;
    }
}
