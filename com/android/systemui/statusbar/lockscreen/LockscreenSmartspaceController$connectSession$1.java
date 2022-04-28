package com.android.systemui.statusbar.lockscreen;

import android.app.smartspace.SmartspaceSession;
import android.app.smartspace.SmartspaceTargetEvent;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;

/* compiled from: LockscreenSmartspaceController.kt */
public final class LockscreenSmartspaceController$connectSession$1 implements BcSmartspaceDataPlugin.SmartspaceEventNotifier {
    public final /* synthetic */ LockscreenSmartspaceController this$0;

    public LockscreenSmartspaceController$connectSession$1(LockscreenSmartspaceController lockscreenSmartspaceController) {
        this.this$0 = lockscreenSmartspaceController;
    }

    public final void notifySmartspaceEvent(SmartspaceTargetEvent smartspaceTargetEvent) {
        SmartspaceSession smartspaceSession = this.this$0.session;
        if (smartspaceSession != null) {
            smartspaceSession.notifySmartspaceEvent(smartspaceTargetEvent);
        }
    }
}
