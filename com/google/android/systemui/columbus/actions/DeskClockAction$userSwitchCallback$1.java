package com.google.android.systemui.columbus.actions;

import android.app.SynchronousUserSwitchObserver;
import android.os.RemoteException;

/* compiled from: DeskClockAction.kt */
public final class DeskClockAction$userSwitchCallback$1 extends SynchronousUserSwitchObserver {
    public final /* synthetic */ DeskClockAction this$0;

    public DeskClockAction$userSwitchCallback$1(DeskClockAction deskClockAction) {
        this.this$0 = deskClockAction;
    }

    public final void onUserSwitching(int i) throws RemoteException {
        this.this$0.updateBroadcastReceiver();
    }
}
