package com.android.systemui;

import android.content.Intent;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.statusbar.phone.StatusBar;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ActivityStarterDelegate$$ExternalSyntheticLambda6 implements Consumer {
    public final /* synthetic */ Intent f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ ActivityLaunchAnimator.Controller f$2;

    public /* synthetic */ ActivityStarterDelegate$$ExternalSyntheticLambda6(Intent intent, int i, ActivityLaunchAnimator.Controller controller) {
        this.f$0 = intent;
        this.f$1 = i;
        this.f$2 = controller;
    }

    public final void accept(Object obj) {
        ((StatusBar) obj).postStartActivityDismissingKeyguard(this.f$0, this.f$1, this.f$2);
    }
}
