package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.os.Handler;
import com.android.systemui.dump.DumpManager;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class HotspotControllerImpl_Factory implements Factory<HotspotControllerImpl> {
    public final Provider<Handler> backgroundHandlerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<Handler> mainHandlerProvider;

    public final Object get() {
        return new HotspotControllerImpl(this.contextProvider.get(), this.mainHandlerProvider.get(), this.backgroundHandlerProvider.get(), this.dumpManagerProvider.get());
    }

    public HotspotControllerImpl_Factory(Provider<Context> provider, Provider<Handler> provider2, Provider<Handler> provider3, Provider<DumpManager> provider4) {
        this.contextProvider = provider;
        this.mainHandlerProvider = provider2;
        this.backgroundHandlerProvider = provider3;
        this.dumpManagerProvider = provider4;
    }
}
