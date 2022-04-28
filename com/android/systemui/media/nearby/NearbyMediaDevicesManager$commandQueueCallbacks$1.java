package com.android.systemui.media.nearby;

import android.media.INearbyMediaDevicesProvider;
import android.media.INearbyMediaDevicesUpdateCallback;
import com.android.systemui.statusbar.CommandQueue;
import java.util.Iterator;

/* compiled from: NearbyMediaDevicesManager.kt */
public final class NearbyMediaDevicesManager$commandQueueCallbacks$1 implements CommandQueue.Callbacks {
    public final /* synthetic */ NearbyMediaDevicesManager this$0;

    public NearbyMediaDevicesManager$commandQueueCallbacks$1(NearbyMediaDevicesManager nearbyMediaDevicesManager) {
        this.this$0 = nearbyMediaDevicesManager;
    }

    public final void registerNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) {
        if (!this.this$0.providers.contains(iNearbyMediaDevicesProvider)) {
            Iterator it = this.this$0.activeCallbacks.iterator();
            while (it.hasNext()) {
                iNearbyMediaDevicesProvider.registerNearbyDevicesCallback((INearbyMediaDevicesUpdateCallback) it.next());
            }
            this.this$0.providers.add(iNearbyMediaDevicesProvider);
            iNearbyMediaDevicesProvider.asBinder().linkToDeath(this.this$0.deathRecipient, 0);
        }
    }

    public final void unregisterNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) {
        this.this$0.providers.remove(iNearbyMediaDevicesProvider);
    }
}
