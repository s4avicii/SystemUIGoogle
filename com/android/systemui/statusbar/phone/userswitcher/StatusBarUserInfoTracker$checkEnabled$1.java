package com.android.systemui.statusbar.phone.userswitcher;

import java.util.Iterator;
import java.util.Objects;

/* compiled from: StatusBarUserInfoTracker.kt */
public final class StatusBarUserInfoTracker$checkEnabled$1 implements Runnable {
    public final /* synthetic */ StatusBarUserInfoTracker this$0;

    public StatusBarUserInfoTracker$checkEnabled$1(StatusBarUserInfoTracker statusBarUserInfoTracker) {
        this.this$0 = statusBarUserInfoTracker;
    }

    public final void run() {
        StatusBarUserInfoTracker statusBarUserInfoTracker = this.this$0;
        Objects.requireNonNull(statusBarUserInfoTracker);
        boolean z = statusBarUserInfoTracker.userSwitcherEnabled;
        StatusBarUserInfoTracker statusBarUserInfoTracker2 = this.this$0;
        statusBarUserInfoTracker2.userSwitcherEnabled = statusBarUserInfoTracker2.userManager.isUserSwitcherEnabled();
        StatusBarUserInfoTracker statusBarUserInfoTracker3 = this.this$0;
        Objects.requireNonNull(statusBarUserInfoTracker3);
        if (z != statusBarUserInfoTracker3.userSwitcherEnabled) {
            final StatusBarUserInfoTracker statusBarUserInfoTracker4 = this.this$0;
            statusBarUserInfoTracker4.mainExecutor.execute(new Runnable() {
                public final void run() {
                    StatusBarUserInfoTracker statusBarUserInfoTracker = statusBarUserInfoTracker4;
                    Objects.requireNonNull(statusBarUserInfoTracker);
                    Iterator it = statusBarUserInfoTracker.listeners.iterator();
                    while (it.hasNext()) {
                        ((CurrentUserChipInfoUpdatedListener) it.next()).onStatusBarUserSwitcherSettingChanged();
                    }
                }
            });
        }
    }
}
