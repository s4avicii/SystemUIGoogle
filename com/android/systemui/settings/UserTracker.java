package com.android.systemui.settings;

import android.content.pm.UserInfo;
import android.os.UserHandle;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: UserTracker.kt */
public interface UserTracker extends UserContentResolverProvider, UserContextProvider {

    /* compiled from: UserTracker.kt */
    public interface Callback {
        void onProfilesChanged() {
        }

        void onUserChanged(int i) {
        }
    }

    void addCallback(Callback callback, Executor executor);

    UserHandle getUserHandle();

    int getUserId();

    UserInfo getUserInfo();

    List<UserInfo> getUserProfiles();

    void removeCallback(Callback callback);
}
