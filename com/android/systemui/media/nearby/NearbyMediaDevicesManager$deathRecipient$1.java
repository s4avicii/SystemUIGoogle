package com.android.systemui.media.nearby;

import android.media.INearbyMediaDevicesProvider;
import android.os.IBinder;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NearbyMediaDevicesManager.kt */
public final class NearbyMediaDevicesManager$deathRecipient$1 implements IBinder.DeathRecipient {
    public final /* synthetic */ NearbyMediaDevicesManager this$0;

    public final void binderDied() {
    }

    public NearbyMediaDevicesManager$deathRecipient$1(NearbyMediaDevicesManager nearbyMediaDevicesManager) {
        this.this$0 = nearbyMediaDevicesManager;
    }

    public final void binderDied(IBinder iBinder) {
        NearbyMediaDevicesManager nearbyMediaDevicesManager = this.this$0;
        Objects.requireNonNull(nearbyMediaDevicesManager);
        synchronized (nearbyMediaDevicesManager.providers) {
            int size = nearbyMediaDevicesManager.providers.size() - 1;
            if (size >= 0) {
                while (true) {
                    int i = size - 1;
                    if (Intrinsics.areEqual(((INearbyMediaDevicesProvider) nearbyMediaDevicesManager.providers.get(size)).asBinder(), iBinder)) {
                        nearbyMediaDevicesManager.providers.remove(size);
                        break;
                    } else if (i < 0) {
                        break;
                    } else {
                        size = i;
                    }
                }
            }
        }
    }
}
