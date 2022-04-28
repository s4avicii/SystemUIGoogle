package com.android.systemui.statusbar.phone.userswitcher;

import android.graphics.drawable.Drawable;
import com.android.systemui.statusbar.policy.UserInfoController;
import java.util.Iterator;

/* compiled from: StatusBarUserInfoTracker.kt */
public final class StatusBarUserInfoTracker$userInfoChangedListener$1 implements UserInfoController.OnUserInfoChangedListener {
    public final /* synthetic */ StatusBarUserInfoTracker this$0;

    public StatusBarUserInfoTracker$userInfoChangedListener$1(StatusBarUserInfoTracker statusBarUserInfoTracker) {
        this.this$0 = statusBarUserInfoTracker;
    }

    public final void onUserInfoChanged(String str, Drawable drawable) {
        StatusBarUserInfoTracker statusBarUserInfoTracker = this.this$0;
        statusBarUserInfoTracker.currentUserAvatar = drawable;
        statusBarUserInfoTracker.currentUserName = str;
        Iterator it = statusBarUserInfoTracker.listeners.iterator();
        while (it.hasNext()) {
            ((CurrentUserChipInfoUpdatedListener) it.next()).onCurrentUserChipInfoUpdated();
        }
    }
}
