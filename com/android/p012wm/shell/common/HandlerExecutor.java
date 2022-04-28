package com.android.p012wm.shell.common;

import android.os.Handler;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.SuggestController$$ExternalSyntheticLambda1;

/* renamed from: com.android.wm.shell.common.HandlerExecutor */
public final class HandlerExecutor implements ShellExecutor {
    public final Handler mHandler;

    public final void execute(Runnable runnable) {
        if (this.mHandler.getLooper().isCurrentThread()) {
            runnable.run();
        } else if (!this.mHandler.post(runnable)) {
            throw new RuntimeException(this.mHandler + " is probably exiting");
        }
    }

    public final void executeDelayed(Runnable runnable, long j) {
        if (!this.mHandler.postDelayed(runnable, j)) {
            throw new RuntimeException(this.mHandler + " is probably exiting");
        }
    }

    public final boolean hasCallback(SuggestController$$ExternalSyntheticLambda1 suggestController$$ExternalSyntheticLambda1) {
        return this.mHandler.hasCallbacks(suggestController$$ExternalSyntheticLambda1);
    }

    public final void removeCallbacks(Runnable runnable) {
        this.mHandler.removeCallbacks(runnable);
    }

    public HandlerExecutor(Handler handler) {
        this.mHandler = handler;
    }
}
