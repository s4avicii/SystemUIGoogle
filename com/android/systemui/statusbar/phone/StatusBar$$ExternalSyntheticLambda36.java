package com.android.systemui.statusbar.phone;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.UserHandle;
import android.view.RemoteAnimationAdapter;
import java.util.Objects;
import kotlin.jvm.functions.Function1;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBar$$ExternalSyntheticLambda36 implements Function1 {
    public final /* synthetic */ StatusBar f$0;
    public final /* synthetic */ Intent f$1;

    public /* synthetic */ StatusBar$$ExternalSyntheticLambda36(StatusBar statusBar, Intent intent) {
        this.f$0 = statusBar;
        this.f$1 = intent;
    }

    public final Object invoke(Object obj) {
        StatusBar statusBar = this.f$0;
        Intent intent = this.f$1;
        long[] jArr = StatusBar.CAMERA_LAUNCH_GESTURE_VIBRATION_TIMINGS;
        Objects.requireNonNull(statusBar);
        return Integer.valueOf(TaskStackBuilder.create(statusBar.mContext).addNextIntent(intent).startActivities(StatusBar.getActivityOptions(statusBar.mDisplayId, (RemoteAnimationAdapter) obj), UserHandle.CURRENT));
    }
}
