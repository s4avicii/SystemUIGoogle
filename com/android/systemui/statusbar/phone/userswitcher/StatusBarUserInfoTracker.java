package com.android.systemui.statusbar.phone.userswitcher;

import android.graphics.drawable.Drawable;
import android.os.UserManager;
import com.android.keyguard.KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.statusbar.policy.UserInfoController;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.Executor;

/* compiled from: StatusBarUserInfoTracker.kt */
public final class StatusBarUserInfoTracker implements CallbackController<CurrentUserChipInfoUpdatedListener>, Dumpable {
    public final Executor backgroundExecutor;
    public Drawable currentUserAvatar;
    public String currentUserName;
    public final ArrayList listeners = new ArrayList();
    public boolean listening;
    public final Executor mainExecutor;
    public final StatusBarUserInfoTracker$userInfoChangedListener$1 userInfoChangedListener = new StatusBarUserInfoTracker$userInfoChangedListener$1(this);
    public final UserInfoController userInfoController;
    public final UserManager userManager;
    public boolean userSwitcherEnabled;

    public final void addCallback(CurrentUserChipInfoUpdatedListener currentUserChipInfoUpdatedListener) {
        if (this.listeners.isEmpty()) {
            this.listening = true;
            this.userInfoController.addCallback(this.userInfoChangedListener);
        }
        if (!this.listeners.contains(currentUserChipInfoUpdatedListener)) {
            this.listeners.add(currentUserChipInfoUpdatedListener);
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0.m25m(this.userSwitcherEnabled, "  userSwitcherEnabled=", printWriter);
        KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0.m25m(this.listening, "  listening=", printWriter);
    }

    public final void removeCallback(Object obj) {
        this.listeners.remove((CurrentUserChipInfoUpdatedListener) obj);
        if (this.listeners.isEmpty()) {
            this.listening = false;
            this.userInfoController.removeCallback(this.userInfoChangedListener);
        }
    }

    public StatusBarUserInfoTracker(UserInfoController userInfoController2, UserManager userManager2, DumpManager dumpManager, Executor executor, Executor executor2) {
        this.userInfoController = userInfoController2;
        this.userManager = userManager2;
        this.mainExecutor = executor;
        this.backgroundExecutor = executor2;
        dumpManager.registerDumpable("StatusBarUserInfoTracker", this);
    }
}
