package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.google.android.setupcompat.internal.SetupCompatServiceInvoker$$ExternalSyntheticLambda0;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBarNotificationActivityStarter$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ StatusBarNotificationActivityStarter f$0;
    public final /* synthetic */ NotificationEntry f$1;
    public final /* synthetic */ NotificationEntry f$2;

    public /* synthetic */ StatusBarNotificationActivityStarter$$ExternalSyntheticLambda2(StatusBarNotificationActivityStarter statusBarNotificationActivityStarter, NotificationEntry notificationEntry, NotificationEntry notificationEntry2) {
        this.f$0 = statusBarNotificationActivityStarter;
        this.f$1 = notificationEntry;
        this.f$2 = notificationEntry2;
    }

    public final void run() {
        StatusBarNotificationActivityStarter statusBarNotificationActivityStarter = this.f$0;
        NotificationEntry notificationEntry = this.f$1;
        NotificationEntry notificationEntry2 = this.f$2;
        Objects.requireNonNull(statusBarNotificationActivityStarter);
        SetupCompatServiceInvoker$$ExternalSyntheticLambda0 setupCompatServiceInvoker$$ExternalSyntheticLambda0 = new SetupCompatServiceInvoker$$ExternalSyntheticLambda0(statusBarNotificationActivityStarter, notificationEntry, notificationEntry2, 1);
        if (((StatusBarNotificationPresenter) statusBarNotificationActivityStarter.mPresenter).isCollapsing()) {
            statusBarNotificationActivityStarter.mShadeController.addPostCollapseAction(setupCompatServiceInvoker$$ExternalSyntheticLambda0);
        } else {
            setupCompatServiceInvoker$$ExternalSyntheticLambda0.run();
        }
    }
}
