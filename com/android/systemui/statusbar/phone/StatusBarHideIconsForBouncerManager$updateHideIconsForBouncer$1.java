package com.android.systemui.statusbar.phone;

/* compiled from: StatusBarHideIconsForBouncerManager.kt */
public final class StatusBarHideIconsForBouncerManager$updateHideIconsForBouncer$1 implements Runnable {
    public final /* synthetic */ StatusBarHideIconsForBouncerManager this$0;

    public StatusBarHideIconsForBouncerManager$updateHideIconsForBouncer$1(StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager) {
        this.this$0 = statusBarHideIconsForBouncerManager;
    }

    public final void run() {
        StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager = this.this$0;
        statusBarHideIconsForBouncerManager.wereIconsJustHidden = false;
        statusBarHideIconsForBouncerManager.commandQueue.recomputeDisableFlags(statusBarHideIconsForBouncerManager.displayId, true);
    }
}
