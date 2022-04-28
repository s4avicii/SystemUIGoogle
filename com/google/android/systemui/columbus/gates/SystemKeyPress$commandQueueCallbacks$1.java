package com.google.android.systemui.columbus.gates;

import com.android.systemui.statusbar.CommandQueue;

/* compiled from: SystemKeyPress.kt */
public final class SystemKeyPress$commandQueueCallbacks$1 implements CommandQueue.Callbacks {
    public final /* synthetic */ SystemKeyPress this$0;

    public SystemKeyPress$commandQueueCallbacks$1(SystemKeyPress systemKeyPress) {
        this.this$0 = systemKeyPress;
    }

    public final void handleSystemKey(int i) {
        if (this.this$0.blockingKeys.contains(Integer.valueOf(i))) {
            SystemKeyPress systemKeyPress = this.this$0;
            systemKeyPress.blockForMillis(systemKeyPress.gateDuration);
        }
    }
}
