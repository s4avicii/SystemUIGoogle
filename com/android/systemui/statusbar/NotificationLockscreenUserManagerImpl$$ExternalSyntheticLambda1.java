package com.android.systemui.statusbar;

import java.util.Objects;
import java.util.function.Supplier;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationLockscreenUserManagerImpl$$ExternalSyntheticLambda1 implements Supplier {
    public final /* synthetic */ NotificationLockscreenUserManagerImpl f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ NotificationLockscreenUserManagerImpl$$ExternalSyntheticLambda1(NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl, int i) {
        this.f$0 = notificationLockscreenUserManagerImpl;
        this.f$1 = i;
    }

    public final Object get() {
        NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = this.f$0;
        int i = this.f$1;
        Objects.requireNonNull(notificationLockscreenUserManagerImpl);
        return Boolean.valueOf(notificationLockscreenUserManagerImpl.mLockPatternUtils.isSeparateProfileChallengeEnabled(i));
    }
}
