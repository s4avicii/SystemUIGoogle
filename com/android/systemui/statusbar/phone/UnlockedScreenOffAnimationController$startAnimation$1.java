package com.android.systemui.statusbar.phone;

import java.util.Objects;

/* compiled from: UnlockedScreenOffAnimationController.kt */
public final class UnlockedScreenOffAnimationController$startAnimation$1 implements Runnable {
    public final /* synthetic */ UnlockedScreenOffAnimationController this$0;

    public UnlockedScreenOffAnimationController$startAnimation$1(UnlockedScreenOffAnimationController unlockedScreenOffAnimationController) {
        this.this$0 = unlockedScreenOffAnimationController;
    }

    public final void run() {
        if (!this.this$0.powerManager.isInteractive()) {
            UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = this.this$0;
            unlockedScreenOffAnimationController.aodUiAnimationPlaying = true;
            StatusBar statusBar = unlockedScreenOffAnimationController.statusBar;
            if (statusBar == null) {
                statusBar = null;
            }
            Objects.requireNonNull(statusBar);
            statusBar.mNotificationPanelViewController.showAodUi();
        }
    }
}
