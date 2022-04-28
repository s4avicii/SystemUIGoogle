package com.android.systemui.util.leak;

import com.android.systemui.util.concurrency.MessageRouter;
import com.android.wifitrackerlib.WifiEntry$$ExternalSyntheticLambda0;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class GarbageMonitor$$ExternalSyntheticLambda0 implements MessageRouter.SimpleMessageListener {
    public final /* synthetic */ GarbageMonitor f$0;

    public /* synthetic */ GarbageMonitor$$ExternalSyntheticLambda0(GarbageMonitor garbageMonitor) {
        this.f$0 = garbageMonitor;
    }

    public final void onMessage() {
        boolean z;
        GarbageMonitor garbageMonitor = this.f$0;
        Objects.requireNonNull(garbageMonitor);
        if (garbageMonitor.mTrackedGarbage.countOldGarbage() > 5) {
            Runtime.getRuntime().gc();
            z = true;
        } else {
            z = false;
        }
        if (z) {
            garbageMonitor.mDelayableExecutor.executeDelayed(new WifiEntry$$ExternalSyntheticLambda0(garbageMonitor, 4), 100);
        }
        garbageMonitor.mMessageRouter.cancelMessages(1000);
        garbageMonitor.mMessageRouter.sendMessageDelayed(1000, 900000);
    }
}
