package com.android.systemui;

import android.app.PendingIntent;
import com.android.systemui.statusbar.phone.StatusBar;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ActivityStarterDelegate$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ PendingIntent f$0;
    public final /* synthetic */ Runnable f$1;

    public /* synthetic */ ActivityStarterDelegate$$ExternalSyntheticLambda2(PendingIntent pendingIntent, Runnable runnable) {
        this.f$0 = pendingIntent;
        this.f$1 = runnable;
    }

    public final void accept(Object obj) {
        ((StatusBar) obj).startPendingIntentDismissingKeyguard(this.f$0, this.f$1);
    }
}
