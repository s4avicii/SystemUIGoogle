package com.android.systemui.statusbar.lockscreen;

import android.app.PendingIntent;
import android.content.Intent;
import android.view.View;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;

/* compiled from: LockscreenSmartspaceController.kt */
public final class LockscreenSmartspaceController$buildView$1 implements BcSmartspaceDataPlugin.IntentStarter {
    public final /* synthetic */ LockscreenSmartspaceController this$0;

    public LockscreenSmartspaceController$buildView$1(LockscreenSmartspaceController lockscreenSmartspaceController) {
        this.this$0 = lockscreenSmartspaceController;
    }

    public final void startIntent(View view, Intent intent, boolean z) {
        this.this$0.activityStarter.startActivity(intent, true, (ActivityLaunchAnimator.Controller) null, z);
    }

    public final void startPendingIntent(PendingIntent pendingIntent, boolean z) {
        if (z) {
            pendingIntent.send();
        } else {
            this.this$0.activityStarter.startPendingIntentDismissingKeyguard(pendingIntent);
        }
    }
}
