package com.android.systemui.keyguard;

import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.unfold.updates.screen.ScreenStatusProvider;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: LifecycleScreenStatusProvider.kt */
public final class LifecycleScreenStatusProvider implements ScreenStatusProvider, ScreenLifecycle.Observer {
    public final ArrayList listeners = new ArrayList();

    public final void addCallback(Object obj) {
        this.listeners.add((ScreenStatusProvider.ScreenListener) obj);
    }

    public final void onScreenTurnedOn() {
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((ScreenStatusProvider.ScreenListener) it.next()).onScreenTurnedOn();
        }
    }

    public final void removeCallback(Object obj) {
        this.listeners.remove((ScreenStatusProvider.ScreenListener) obj);
    }

    public LifecycleScreenStatusProvider(ScreenLifecycle screenLifecycle) {
        screenLifecycle.mObservers.add(this);
    }
}
