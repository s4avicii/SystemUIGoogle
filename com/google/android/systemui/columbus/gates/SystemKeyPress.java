package com.google.android.systemui.columbus.gates;

import android.content.Context;
import android.os.Handler;
import com.android.systemui.statusbar.CommandQueue;
import java.util.Set;

/* compiled from: SystemKeyPress.kt */
public final class SystemKeyPress extends TransientGate {
    public final Set<Integer> blockingKeys;
    public final CommandQueue commandQueue;
    public final SystemKeyPress$commandQueueCallbacks$1 commandQueueCallbacks = new SystemKeyPress$commandQueueCallbacks$1(this);
    public final long gateDuration;

    public final void onActivate() {
        this.commandQueue.addCallback((CommandQueue.Callbacks) this.commandQueueCallbacks);
    }

    public final void onDeactivate() {
        this.commandQueue.removeCallback((CommandQueue.Callbacks) this.commandQueueCallbacks);
    }

    public SystemKeyPress(Context context, Handler handler, CommandQueue commandQueue2, long j, Set<Integer> set) {
        super(context, handler);
        this.commandQueue = commandQueue2;
        this.gateDuration = j;
        this.blockingKeys = set;
    }
}
