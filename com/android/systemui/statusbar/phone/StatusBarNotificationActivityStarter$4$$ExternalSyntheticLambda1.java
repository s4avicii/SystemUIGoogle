package com.android.systemui.statusbar.phone;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.UserHandle;
import android.view.RemoteAnimationAdapter;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import java.util.Objects;
import kotlin.jvm.functions.Function1;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBarNotificationActivityStarter$4$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ StatusBarNotificationActivityStarter.C15684 f$0;
    public final /* synthetic */ Intent f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ StatusBarNotificationActivityStarter$4$$ExternalSyntheticLambda1(StatusBarNotificationActivityStarter.C15684 r1, Intent intent, int i) {
        this.f$0 = r1;
        this.f$1 = intent;
        this.f$2 = i;
    }

    public final Object invoke(Object obj) {
        StatusBarNotificationActivityStarter.C15684 r0 = this.f$0;
        Intent intent = this.f$1;
        int i = this.f$2;
        Objects.requireNonNull(r0);
        TaskStackBuilder addNextIntentWithParentStack = TaskStackBuilder.create(StatusBarNotificationActivityStarter.this.mContext).addNextIntentWithParentStack(intent);
        StatusBar statusBar = StatusBarNotificationActivityStarter.this.mStatusBar;
        Objects.requireNonNull(statusBar);
        return Integer.valueOf(addNextIntentWithParentStack.startActivities(StatusBar.getActivityOptions(statusBar.mDisplayId, (RemoteAnimationAdapter) obj), new UserHandle(UserHandle.getUserId(i))));
    }
}
