package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceTarget;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.settings.CurrentUserTracker;
import java.util.Objects;

/* compiled from: KeyguardMediaViewController.kt */
public final class KeyguardMediaViewController$init$2 extends CurrentUserTracker {
    public final /* synthetic */ KeyguardMediaViewController this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public KeyguardMediaViewController$init$2(KeyguardMediaViewController keyguardMediaViewController, BroadcastDispatcher broadcastDispatcher) {
        super(broadcastDispatcher);
        this.this$0 = keyguardMediaViewController;
    }

    public final void onUserSwitched(int i) {
        KeyguardMediaViewController keyguardMediaViewController = this.this$0;
        Objects.requireNonNull(keyguardMediaViewController);
        keyguardMediaViewController.title = null;
        keyguardMediaViewController.artist = null;
        BcSmartspaceDataPlugin.SmartspaceView smartspaceView = keyguardMediaViewController.smartspaceView;
        if (smartspaceView != null) {
            smartspaceView.setMediaTarget((SmartspaceTarget) null);
        }
    }
}
