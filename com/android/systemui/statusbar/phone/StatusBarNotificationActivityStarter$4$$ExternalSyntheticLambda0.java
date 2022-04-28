package com.android.systemui.statusbar.phone;

import android.content.Intent;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.statusbar.notification.NotificationLaunchAnimatorController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBarNotificationActivityStarter$4$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ StatusBarNotificationActivityStarter.C15684 f$0;
    public final /* synthetic */ ExpandableNotificationRow f$1;
    public final /* synthetic */ boolean f$2;
    public final /* synthetic */ Intent f$3;
    public final /* synthetic */ int f$4;

    public /* synthetic */ StatusBarNotificationActivityStarter$4$$ExternalSyntheticLambda0(StatusBarNotificationActivityStarter.C15684 r1, ExpandableNotificationRow expandableNotificationRow, boolean z, Intent intent, int i) {
        this.f$0 = r1;
        this.f$1 = expandableNotificationRow;
        this.f$2 = z;
        this.f$3 = intent;
        this.f$4 = i;
    }

    public final void run() {
        StatusBarNotificationActivityStarter.C15684 r0 = this.f$0;
        ExpandableNotificationRow expandableNotificationRow = this.f$1;
        boolean z = this.f$2;
        Intent intent = this.f$3;
        int i = this.f$4;
        Objects.requireNonNull(r0);
        NotificationLaunchAnimatorController animatorController = StatusBarNotificationActivityStarter.this.mNotificationAnimationProvider.getAnimatorController(expandableNotificationRow);
        StatusBarNotificationActivityStarter statusBarNotificationActivityStarter = StatusBarNotificationActivityStarter.this;
        StatusBarLaunchAnimatorController statusBarLaunchAnimatorController = new StatusBarLaunchAnimatorController(animatorController, statusBarNotificationActivityStarter.mStatusBar, true);
        ActivityLaunchAnimator activityLaunchAnimator = statusBarNotificationActivityStarter.mActivityLaunchAnimator;
        String str = intent.getPackage();
        StatusBarNotificationActivityStarter$4$$ExternalSyntheticLambda1 statusBarNotificationActivityStarter$4$$ExternalSyntheticLambda1 = new StatusBarNotificationActivityStarter$4$$ExternalSyntheticLambda1(r0, intent, i);
        Objects.requireNonNull(activityLaunchAnimator);
        activityLaunchAnimator.startIntentWithAnimation(statusBarLaunchAnimatorController, z, str, false, statusBarNotificationActivityStarter$4$$ExternalSyntheticLambda1);
    }
}
