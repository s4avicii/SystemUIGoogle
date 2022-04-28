package com.google.android.systemui.elmyra.gates;

import android.content.Context;
import android.os.Handler;

public abstract class TransientGate extends Gate {
    public final long mBlockDuration;
    public boolean mIsBlocking;
    public final C22621 mResetGate = new Runnable() {
        public final void run() {
            TransientGate transientGate = TransientGate.this;
            transientGate.mIsBlocking = false;
            transientGate.notifyListener();
        }
    };
    public final Handler mResetGateHandler;

    public final void block() {
        this.mIsBlocking = true;
        notifyListener();
        this.mResetGateHandler.removeCallbacks(this.mResetGate);
        this.mResetGateHandler.postDelayed(this.mResetGate, this.mBlockDuration);
    }

    public TransientGate(Context context, long j) {
        super(context);
        this.mBlockDuration = j;
        this.mResetGateHandler = new Handler(context.getMainLooper());
    }

    public final boolean isBlocked() {
        return this.mIsBlocking;
    }
}
