package com.android.systemui.statusbar.events;

import android.content.Context;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.util.time.SystemClock;

/* compiled from: SystemEventCoordinator.kt */
public final class SystemEventCoordinator {
    public final BatteryController batteryController;
    public final Context context;
    public final PrivacyItemController privacyController;
    public final SystemEventCoordinator$privacyStateListener$1 privacyStateListener = new SystemEventCoordinator$privacyStateListener$1(this);
    public SystemStatusAnimationScheduler scheduler;
    public final SystemClock systemClock;

    public SystemEventCoordinator(SystemClock systemClock2, BatteryController batteryController2, PrivacyItemController privacyItemController, Context context2) {
        this.systemClock = systemClock2;
        this.batteryController = batteryController2;
        this.privacyController = privacyItemController;
        this.context = context2;
    }
}
