package com.google.android.systemui.columbus.gates;

import android.content.Context;
import android.os.PowerManager;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import dagger.Lazy;
import java.util.Objects;

/* compiled from: PowerState.kt */
public final class PowerState extends Gate {
    public final PowerManager powerManager;
    public final Lazy<WakefulnessLifecycle> wakefulnessLifecycle;
    public final PowerState$wakefulnessLifecycleObserver$1 wakefulnessLifecycleObserver = new PowerState$wakefulnessLifecycleObserver$1(this);

    public final void onActivate() {
        this.wakefulnessLifecycle.get().addObserver(this.wakefulnessLifecycleObserver);
        updateBlocking();
    }

    public final void onDeactivate() {
        WakefulnessLifecycle wakefulnessLifecycle2 = this.wakefulnessLifecycle.get();
        PowerState$wakefulnessLifecycleObserver$1 powerState$wakefulnessLifecycleObserver$1 = this.wakefulnessLifecycleObserver;
        Objects.requireNonNull(wakefulnessLifecycle2);
        wakefulnessLifecycle2.mObservers.remove(powerState$wakefulnessLifecycleObserver$1);
    }

    public final void updateBlocking() {
        PowerManager powerManager2 = this.powerManager;
        boolean z = false;
        if (powerManager2 != null && !powerManager2.isInteractive()) {
            z = true;
        }
        setBlocking(z);
    }

    public PowerState(Context context, Lazy<WakefulnessLifecycle> lazy) {
        super(context);
        this.wakefulnessLifecycle = lazy;
        this.powerManager = (PowerManager) context.getSystemService(GlobalActionsDialogLite.GLOBAL_ACTION_KEY_POWER);
    }
}
