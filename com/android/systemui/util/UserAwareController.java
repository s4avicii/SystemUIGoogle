package com.android.systemui.util;

import android.os.UserHandle;

/* compiled from: UserAwareController.kt */
public interface UserAwareController {
    void changeUser(UserHandle userHandle) {
    }

    int getCurrentUserId();
}
