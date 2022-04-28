package com.android.systemui.statusbar.window;

import com.android.systemui.statusbar.CommandQueue;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: StatusBarWindowStateController.kt */
public final class StatusBarWindowStateController$commandQueueCallback$1 implements CommandQueue.Callbacks {
    public final /* synthetic */ StatusBarWindowStateController this$0;

    public StatusBarWindowStateController$commandQueueCallback$1(StatusBarWindowStateController statusBarWindowStateController) {
        this.this$0 = statusBarWindowStateController;
    }

    public final void setWindowState(int i, int i2, int i3) {
        StatusBarWindowStateController statusBarWindowStateController = this.this$0;
        Objects.requireNonNull(statusBarWindowStateController);
        if (i == statusBarWindowStateController.thisDisplayId && i2 == 1 && statusBarWindowStateController.windowState != i3) {
            statusBarWindowStateController.windowState = i3;
            Iterator it = statusBarWindowStateController.listeners.iterator();
            while (it.hasNext()) {
                ((StatusBarWindowStateListener) it.next()).onStatusBarWindowStateChanged(i3);
            }
        }
    }
}
