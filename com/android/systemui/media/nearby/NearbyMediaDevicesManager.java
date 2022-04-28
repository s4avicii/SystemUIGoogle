package com.android.systemui.media.nearby;

import com.android.systemui.statusbar.CommandQueue;
import java.util.ArrayList;

/* compiled from: NearbyMediaDevicesManager.kt */
public final class NearbyMediaDevicesManager {
    public ArrayList activeCallbacks = new ArrayList();
    public final NearbyMediaDevicesManager$deathRecipient$1 deathRecipient;
    public ArrayList providers = new ArrayList();

    public NearbyMediaDevicesManager(CommandQueue commandQueue) {
        NearbyMediaDevicesManager$commandQueueCallbacks$1 nearbyMediaDevicesManager$commandQueueCallbacks$1 = new NearbyMediaDevicesManager$commandQueueCallbacks$1(this);
        this.deathRecipient = new NearbyMediaDevicesManager$deathRecipient$1(this);
        commandQueue.addCallback((CommandQueue.Callbacks) nearbyMediaDevicesManager$commandQueueCallbacks$1);
    }
}
