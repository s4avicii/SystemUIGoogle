package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.provider.DebugModeFilterProvider;

/* compiled from: DebugModeCoordinator.kt */
public final class DebugModeCoordinator implements Coordinator {
    public final DebugModeFilterProvider debugModeFilterProvider;
    public final DebugModeCoordinator$preGroupFilter$1 preGroupFilter = new DebugModeCoordinator$preGroupFilter$1(this);

    public final void attach(NotifPipeline notifPipeline) {
        notifPipeline.addPreGroupFilter(this.preGroupFilter);
        this.debugModeFilterProvider.registerInvalidationListener(new DebugModeCoordinator$attach$1(this.preGroupFilter));
    }

    public DebugModeCoordinator(DebugModeFilterProvider debugModeFilterProvider2) {
        this.debugModeFilterProvider = debugModeFilterProvider2;
    }
}
