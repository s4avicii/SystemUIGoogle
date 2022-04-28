package com.android.systemui.statusbar;

import android.provider.Settings;
import java.util.Objects;
import java.util.function.Supplier;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationLockscreenUserManagerImpl$$ExternalSyntheticLambda0 implements Supplier {
    public final /* synthetic */ NotificationLockscreenUserManagerImpl f$0;

    public /* synthetic */ NotificationLockscreenUserManagerImpl$$ExternalSyntheticLambda0(NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl) {
        this.f$0 = notificationLockscreenUserManagerImpl;
    }

    public final Object get() {
        NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = this.f$0;
        Objects.requireNonNull(notificationLockscreenUserManagerImpl);
        boolean z = true;
        if (Settings.Secure.getInt(notificationLockscreenUserManagerImpl.mContext.getContentResolver(), "lock_screen_show_silent_notifications", 1) != 0) {
            z = false;
        }
        return Boolean.valueOf(z);
    }
}
