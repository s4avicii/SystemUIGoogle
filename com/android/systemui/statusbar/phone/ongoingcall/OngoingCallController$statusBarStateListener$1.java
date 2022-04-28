package com.android.systemui.statusbar.phone.ongoingcall;

import com.android.systemui.plugins.statusbar.StatusBarStateController;

/* compiled from: OngoingCallController.kt */
public final class OngoingCallController$statusBarStateListener$1 implements StatusBarStateController.StateListener {
    public final /* synthetic */ OngoingCallController this$0;

    public OngoingCallController$statusBarStateListener$1(OngoingCallController ongoingCallController) {
        this.this$0 = ongoingCallController;
    }

    public final void onFullscreenStateChanged(boolean z) {
        OngoingCallController ongoingCallController = this.this$0;
        ongoingCallController.isFullscreen = z;
        ongoingCallController.updateChipClickListener();
        this.this$0.updateGestureListening();
    }
}
