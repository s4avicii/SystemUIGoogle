package com.android.systemui.statusbar.lockscreen;

import com.android.systemui.statusbar.policy.ConfigurationController;

/* compiled from: LockscreenSmartspaceController.kt */
public final class LockscreenSmartspaceController$configChangeListener$1 implements ConfigurationController.ConfigurationListener {
    public final /* synthetic */ LockscreenSmartspaceController this$0;

    public LockscreenSmartspaceController$configChangeListener$1(LockscreenSmartspaceController lockscreenSmartspaceController) {
        this.this$0 = lockscreenSmartspaceController;
    }

    public final void onThemeChanged() {
        this.this$0.execution.assertIsMainThread();
        LockscreenSmartspaceController.access$updateTextColorFromWallpaper(this.this$0);
    }
}
