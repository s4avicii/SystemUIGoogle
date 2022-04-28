package com.google.android.systemui.smartspace;

import android.content.ComponentName;
import android.content.Context;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.util.concurrency.DelayableExecutor;

/* compiled from: KeyguardMediaViewController.kt */
public final class KeyguardMediaViewController {
    public CharSequence artist;
    public final BroadcastDispatcher broadcastDispatcher;
    public final Context context;
    public final ComponentName mediaComponent;
    public final KeyguardMediaViewController$mediaListener$1 mediaListener = new KeyguardMediaViewController$mediaListener$1(this);
    public final NotificationMediaManager mediaManager;
    public final BcSmartspaceDataPlugin plugin;
    public BcSmartspaceDataPlugin.SmartspaceView smartspaceView;
    public CharSequence title;
    public final DelayableExecutor uiExecutor;
    public KeyguardMediaViewController$init$2 userTracker;

    @VisibleForTesting
    public static /* synthetic */ void getSmartspaceView$annotations() {
    }

    public KeyguardMediaViewController(Context context2, BcSmartspaceDataPlugin bcSmartspaceDataPlugin, DelayableExecutor delayableExecutor, NotificationMediaManager notificationMediaManager, BroadcastDispatcher broadcastDispatcher2) {
        this.context = context2;
        this.plugin = bcSmartspaceDataPlugin;
        this.uiExecutor = delayableExecutor;
        this.mediaManager = notificationMediaManager;
        this.broadcastDispatcher = broadcastDispatcher2;
        this.mediaComponent = new ComponentName(context2, KeyguardMediaViewController.class);
    }
}
