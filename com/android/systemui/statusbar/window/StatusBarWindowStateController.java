package com.android.systemui.statusbar.window;

import com.android.systemui.statusbar.CommandQueue;
import java.util.HashSet;

/* compiled from: StatusBarWindowStateController.kt */
public final class StatusBarWindowStateController {
    public final HashSet listeners = new HashSet();
    public final int thisDisplayId;
    public int windowState;

    public StatusBarWindowStateController(int i, CommandQueue commandQueue) {
        this.thisDisplayId = i;
        StatusBarWindowStateController$commandQueueCallback$1 statusBarWindowStateController$commandQueueCallback$1 = new StatusBarWindowStateController$commandQueueCallback$1(this);
        commandQueue.addCallback((CommandQueue.Callbacks) statusBarWindowStateController$commandQueueCallback$1);
    }
}
