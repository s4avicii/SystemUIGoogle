package com.android.systemui.statusbar.notification.row;

import android.view.View;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationGutsManager$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ NotificationGutsManager f$0;
    public final /* synthetic */ View f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ NotificationMenuRowPlugin.MenuItem f$4;

    public /* synthetic */ NotificationGutsManager$$ExternalSyntheticLambda6(NotificationGutsManager notificationGutsManager, View view, int i, int i2, NotificationMenuRowPlugin.MenuItem menuItem) {
        this.f$0 = notificationGutsManager;
        this.f$1 = view;
        this.f$2 = i;
        this.f$3 = i2;
        this.f$4 = menuItem;
    }

    public final void run() {
        NotificationGutsManager notificationGutsManager = this.f$0;
        View view = this.f$1;
        int i = this.f$2;
        int i2 = this.f$3;
        NotificationMenuRowPlugin.MenuItem menuItem = this.f$4;
        Objects.requireNonNull(notificationGutsManager);
        notificationGutsManager.mMainHandler.post(new NotificationGutsManager$$ExternalSyntheticLambda5(notificationGutsManager, view, i, i2, menuItem));
    }
}
