package com.google.android.systemui.dreamliner;

import com.google.android.systemui.dreamliner.DockObserver;
import java.util.Objects;

/* renamed from: com.google.android.systemui.dreamliner.DockObserver$DreamlinerBroadcastReceiver$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C2232x9eff5e89 implements Runnable {
    public final /* synthetic */ DockObserver.DreamlinerBroadcastReceiver f$0;

    public /* synthetic */ C2232x9eff5e89(DockObserver.DreamlinerBroadcastReceiver dreamlinerBroadcastReceiver) {
        this.f$0 = dreamlinerBroadcastReceiver;
    }

    public final void run() {
        DockObserver.DreamlinerBroadcastReceiver dreamlinerBroadcastReceiver = this.f$0;
        int i = DockObserver.DreamlinerBroadcastReceiver.$r8$clinit;
        Objects.requireNonNull(dreamlinerBroadcastReceiver);
        DockObserver.this.notifyDreamlinerLatestFanLevel();
    }
}
