package com.google.android.systemui.columbus.actions;

import android.app.SynchronousUserSwitchObserver;
import android.os.RemoteException;

/* compiled from: LaunchApp.kt */
public final class LaunchApp$userSwitchCallback$1 extends SynchronousUserSwitchObserver {
    public final /* synthetic */ LaunchApp this$0;

    public LaunchApp$userSwitchCallback$1(LaunchApp launchApp) {
        this.this$0 = launchApp;
    }

    public final void onUserSwitching(int i) throws RemoteException {
        this.this$0.updateAvailableAppsAndShortcutsAsync();
    }
}
