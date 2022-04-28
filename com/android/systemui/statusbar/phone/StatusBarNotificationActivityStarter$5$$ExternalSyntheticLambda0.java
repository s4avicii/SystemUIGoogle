package com.android.systemui.statusbar.phone;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.view.View;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.GhostedViewLaunchAnimatorController;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBarNotificationActivityStarter$5$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ StatusBarNotificationActivityStarter.C15695 f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ View f$2;
    public final /* synthetic */ boolean f$3;

    public /* synthetic */ StatusBarNotificationActivityStarter$5$$ExternalSyntheticLambda0(StatusBarNotificationActivityStarter.C15695 r1, boolean z, View view, boolean z2) {
        this.f$0 = r1;
        this.f$1 = z;
        this.f$2 = view;
        this.f$3 = z2;
    }

    public final void run() {
        Intent intent;
        StatusBarLaunchAnimatorController statusBarLaunchAnimatorController;
        StatusBarNotificationActivityStarter.C15695 r0 = this.f$0;
        boolean z = this.f$1;
        View view = this.f$2;
        boolean z2 = this.f$3;
        Objects.requireNonNull(r0);
        if (z) {
            intent = new Intent("android.settings.NOTIFICATION_HISTORY");
        } else {
            intent = new Intent("android.settings.NOTIFICATION_SETTINGS");
        }
        TaskStackBuilder addNextIntent = TaskStackBuilder.create(StatusBarNotificationActivityStarter.this.mContext).addNextIntent(new Intent("android.settings.NOTIFICATION_SETTINGS"));
        if (z) {
            addNextIntent.addNextIntent(intent);
        }
        GhostedViewLaunchAnimatorController fromView = ActivityLaunchAnimator.Controller.fromView(view, 30);
        if (fromView == null) {
            statusBarLaunchAnimatorController = null;
        } else {
            statusBarLaunchAnimatorController = new StatusBarLaunchAnimatorController(fromView, StatusBarNotificationActivityStarter.this.mStatusBar, true);
        }
        ActivityLaunchAnimator activityLaunchAnimator = StatusBarNotificationActivityStarter.this.mActivityLaunchAnimator;
        String str = intent.getPackage();
        StatusBarNotificationActivityStarter$5$$ExternalSyntheticLambda1 statusBarNotificationActivityStarter$5$$ExternalSyntheticLambda1 = new StatusBarNotificationActivityStarter$5$$ExternalSyntheticLambda1(r0, addNextIntent);
        Objects.requireNonNull(activityLaunchAnimator);
        activityLaunchAnimator.startIntentWithAnimation(statusBarLaunchAnimatorController, z2, str, false, statusBarNotificationActivityStarter$5$$ExternalSyntheticLambda1);
    }
}
