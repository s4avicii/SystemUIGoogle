package com.google.android.systemui.elmyra.gates;

import android.content.Context;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.CommandQueue;

public final class SystemKeyPress extends TransientGate {
    public final int[] mBlockingKeys;
    public final CommandQueue mCommandQueue;
    public final C22601 mCommandQueueCallbacks = new CommandQueue.Callbacks() {
        public final void handleSystemKey(int i) {
            int i2 = 0;
            while (true) {
                SystemKeyPress systemKeyPress = SystemKeyPress.this;
                int[] iArr = systemKeyPress.mBlockingKeys;
                if (i2 >= iArr.length) {
                    return;
                }
                if (iArr[i2] == i) {
                    systemKeyPress.block();
                    return;
                }
                i2++;
            }
        }
    };

    public final void onActivate() {
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this.mCommandQueueCallbacks);
    }

    public final void onDeactivate() {
        this.mCommandQueue.removeCallback((CommandQueue.Callbacks) this.mCommandQueueCallbacks);
    }

    public SystemKeyPress(Context context) {
        super(context, (long) context.getResources().getInteger(C1777R.integer.elmyra_system_key_gate_duration));
        this.mBlockingKeys = context.getResources().getIntArray(C1777R.array.elmyra_blocking_system_keys);
        this.mCommandQueue = (CommandQueue) Dependency.get(CommandQueue.class);
    }
}
