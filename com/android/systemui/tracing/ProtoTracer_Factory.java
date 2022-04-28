package com.android.systemui.tracing;

import android.content.Context;
import android.hardware.display.AmbientDisplayConfiguration;
import com.android.systemui.dock.DockManager;
import com.android.systemui.doze.DozeDockHandler;
import com.android.systemui.dump.DumpManager;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class ProtoTracer_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider contextProvider;
    public final Provider dumpManagerProvider;

    public /* synthetic */ ProtoTracer_Factory(Provider provider, Provider provider2, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.dumpManagerProvider = provider2;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new ProtoTracer((Context) this.contextProvider.get(), (DumpManager) this.dumpManagerProvider.get());
            default:
                return new DozeDockHandler((AmbientDisplayConfiguration) this.contextProvider.get(), (DockManager) this.dumpManagerProvider.get());
        }
    }
}
