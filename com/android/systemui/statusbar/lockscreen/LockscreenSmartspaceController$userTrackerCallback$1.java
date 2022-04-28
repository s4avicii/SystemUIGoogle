package com.android.systemui.statusbar.lockscreen;

import com.android.systemui.settings.UserTracker;

/* compiled from: LockscreenSmartspaceController.kt */
public final class LockscreenSmartspaceController$userTrackerCallback$1 implements UserTracker.Callback {
    public final /* synthetic */ LockscreenSmartspaceController this$0;

    public LockscreenSmartspaceController$userTrackerCallback$1(LockscreenSmartspaceController lockscreenSmartspaceController) {
        this.this$0 = lockscreenSmartspaceController;
    }

    public final void onUserChanged(int i) {
        this.this$0.execution.assertIsMainThread();
        this.this$0.reloadSmartspace();
    }
}
