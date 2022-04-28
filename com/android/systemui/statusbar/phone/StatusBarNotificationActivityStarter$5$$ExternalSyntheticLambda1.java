package com.android.systemui.statusbar.phone;

import android.app.TaskStackBuilder;
import android.os.UserHandle;
import android.view.RemoteAnimationAdapter;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import java.util.Objects;
import kotlin.jvm.functions.Function1;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBarNotificationActivityStarter$5$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ StatusBarNotificationActivityStarter.C15695 f$0;
    public final /* synthetic */ TaskStackBuilder f$1;

    public /* synthetic */ StatusBarNotificationActivityStarter$5$$ExternalSyntheticLambda1(StatusBarNotificationActivityStarter.C15695 r1, TaskStackBuilder taskStackBuilder) {
        this.f$0 = r1;
        this.f$1 = taskStackBuilder;
    }

    public final Object invoke(Object obj) {
        StatusBarNotificationActivityStarter.C15695 r0 = this.f$0;
        TaskStackBuilder taskStackBuilder = this.f$1;
        Objects.requireNonNull(r0);
        StatusBar statusBar = StatusBarNotificationActivityStarter.this.mStatusBar;
        Objects.requireNonNull(statusBar);
        return Integer.valueOf(taskStackBuilder.startActivities(StatusBar.getActivityOptions(statusBar.mDisplayId, (RemoteAnimationAdapter) obj), UserHandle.CURRENT));
    }
}
