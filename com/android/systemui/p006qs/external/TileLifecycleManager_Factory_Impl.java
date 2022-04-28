package com.android.systemui.p006qs.external;

import android.content.Intent;
import android.os.UserHandle;
import com.android.systemui.p006qs.external.TileLifecycleManager;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.external.TileLifecycleManager_Factory_Impl */
public final class TileLifecycleManager_Factory_Impl implements TileLifecycleManager.Factory {
    public final C2507TileLifecycleManager_Factory delegateFactory;

    public final TileLifecycleManager create(Intent intent, UserHandle userHandle) {
        C2507TileLifecycleManager_Factory tileLifecycleManager_Factory = this.delegateFactory;
        Objects.requireNonNull(tileLifecycleManager_Factory);
        return new TileLifecycleManager(tileLifecycleManager_Factory.handlerProvider.get(), tileLifecycleManager_Factory.contextProvider.get(), tileLifecycleManager_Factory.serviceProvider.get(), tileLifecycleManager_Factory.packageManagerAdapterProvider.get(), tileLifecycleManager_Factory.broadcastDispatcherProvider.get(), intent, userHandle);
    }

    public TileLifecycleManager_Factory_Impl(C2507TileLifecycleManager_Factory tileLifecycleManager_Factory) {
        this.delegateFactory = tileLifecycleManager_Factory;
    }
}
