package com.google.android.systemui.smartspace;

import android.view.View;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.statusbar.NotificationMediaManager;
import java.util.Objects;

/* compiled from: KeyguardMediaViewController.kt */
public final class KeyguardMediaViewController$init$1 implements View.OnAttachStateChangeListener {
    public final /* synthetic */ KeyguardMediaViewController this$0;

    public KeyguardMediaViewController$init$1(KeyguardMediaViewController keyguardMediaViewController) {
        this.this$0 = keyguardMediaViewController;
    }

    public final void onViewAttachedToWindow(View view) {
        KeyguardMediaViewController keyguardMediaViewController = this.this$0;
        Objects.requireNonNull(keyguardMediaViewController);
        keyguardMediaViewController.smartspaceView = (BcSmartspaceDataPlugin.SmartspaceView) view;
        KeyguardMediaViewController keyguardMediaViewController2 = this.this$0;
        keyguardMediaViewController2.mediaManager.addCallback(keyguardMediaViewController2.mediaListener);
    }

    public final void onViewDetachedFromWindow(View view) {
        KeyguardMediaViewController keyguardMediaViewController = this.this$0;
        Objects.requireNonNull(keyguardMediaViewController);
        keyguardMediaViewController.smartspaceView = null;
        KeyguardMediaViewController keyguardMediaViewController2 = this.this$0;
        NotificationMediaManager notificationMediaManager = keyguardMediaViewController2.mediaManager;
        KeyguardMediaViewController$mediaListener$1 keyguardMediaViewController$mediaListener$1 = keyguardMediaViewController2.mediaListener;
        Objects.requireNonNull(notificationMediaManager);
        notificationMediaManager.mMediaListeners.remove(keyguardMediaViewController$mediaListener$1);
    }
}
