package com.android.systemui.statusbar.phone;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.RemoteAnimationAdapter;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBarNotificationActivityStarter$$ExternalSyntheticLambda0 implements ActivityLaunchAnimator.PendingIntentStarter {
    public final /* synthetic */ StatusBarNotificationActivityStarter f$0;
    public final /* synthetic */ ExpandableNotificationRow f$1;
    public final /* synthetic */ PendingIntent f$2;
    public final /* synthetic */ Intent f$3;

    public /* synthetic */ StatusBarNotificationActivityStarter$$ExternalSyntheticLambda0(StatusBarNotificationActivityStarter statusBarNotificationActivityStarter, ExpandableNotificationRow expandableNotificationRow, PendingIntent pendingIntent, Intent intent) {
        this.f$0 = statusBarNotificationActivityStarter;
        this.f$1 = expandableNotificationRow;
        this.f$2 = pendingIntent;
        this.f$3 = intent;
    }

    public final int startPendingIntent(RemoteAnimationAdapter remoteAnimationAdapter) {
        Bundle bundle;
        int i;
        StatusBarNotificationActivityStarter statusBarNotificationActivityStarter = this.f$0;
        ExpandableNotificationRow expandableNotificationRow = this.f$1;
        PendingIntent pendingIntent = this.f$2;
        Intent intent = this.f$3;
        Objects.requireNonNull(statusBarNotificationActivityStarter);
        Objects.requireNonNull(expandableNotificationRow);
        long j = expandableNotificationRow.mLastActionUpTime;
        expandableNotificationRow.mLastActionUpTime = 0;
        if (j > 0) {
            StatusBar statusBar = statusBarNotificationActivityStarter.mStatusBar;
            Objects.requireNonNull(statusBar);
            int i2 = statusBar.mDisplayId;
            boolean isShowing = statusBarNotificationActivityStarter.mKeyguardStateController.isShowing();
            ActivityOptions defaultActivityOptions = StatusBar.getDefaultActivityOptions(remoteAnimationAdapter);
            if (isShowing) {
                i = 3;
            } else {
                i = 2;
            }
            defaultActivityOptions.setSourceInfo(i, j);
            defaultActivityOptions.setLaunchDisplayId(i2);
            defaultActivityOptions.setCallerDisplayId(i2);
            bundle = defaultActivityOptions.toBundle();
        } else {
            StatusBar statusBar2 = statusBarNotificationActivityStarter.mStatusBar;
            Objects.requireNonNull(statusBar2);
            bundle = StatusBar.getActivityOptions(statusBar2.mDisplayId, remoteAnimationAdapter);
        }
        return pendingIntent.sendAndReturnResult(statusBarNotificationActivityStarter.mContext, 0, intent, (PendingIntent.OnFinished) null, (Handler) null, (String) null, bundle);
    }
}
