package com.android.systemui.statusbar;

import android.view.View;

public final class CrossFadeHelper$1 implements Runnable {
    public final /* synthetic */ Runnable val$endRunnable;
    public final /* synthetic */ View val$view;

    public CrossFadeHelper$1(Runnable runnable, View view) {
        this.val$endRunnable = runnable;
        this.val$view = view;
    }

    public final void run() {
        Runnable runnable = this.val$endRunnable;
        if (runnable != null) {
            runnable.run();
        }
        if (this.val$view.getVisibility() != 8) {
            this.val$view.setVisibility(4);
        }
    }
}
