package com.android.systemui.statusbar.notification.collection.coordinator.dagger;

import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinators;

/* compiled from: CoordinatorsModule.kt */
public interface CoordinatorsSubcomponent {

    /* compiled from: CoordinatorsModule.kt */
    public interface Factory {
        CoordinatorsSubcomponent create();
    }

    NotifCoordinators getNotifCoordinators();
}
