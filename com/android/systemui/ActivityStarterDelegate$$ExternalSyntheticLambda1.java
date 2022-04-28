package com.android.systemui;

import android.app.PendingIntent;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.statusbar.phone.StatusBar;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ActivityStarterDelegate$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ PendingIntent f$0;
    public final /* synthetic */ ActivityLaunchAnimator.Controller f$1;

    public /* synthetic */ ActivityStarterDelegate$$ExternalSyntheticLambda1(PendingIntent pendingIntent, ActivityLaunchAnimator.Controller controller) {
        this.f$0 = pendingIntent;
        this.f$1 = controller;
    }

    public final void accept(Object obj) {
        ((StatusBar) obj).postStartActivityDismissingKeyguard(this.f$0, this.f$1);
    }
}
