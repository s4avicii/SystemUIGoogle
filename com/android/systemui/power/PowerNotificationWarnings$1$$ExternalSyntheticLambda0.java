package com.android.systemui.power;

import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.power.PowerNotificationWarnings;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PowerNotificationWarnings$1$$ExternalSyntheticLambda0 implements ActivityStarter.Callback {
    public final /* synthetic */ PowerNotificationWarnings.C09631 f$0;

    public /* synthetic */ PowerNotificationWarnings$1$$ExternalSyntheticLambda0(PowerNotificationWarnings.C09631 r1) {
        this.f$0 = r1;
    }

    public final void onActivityStarted(int i) {
        PowerNotificationWarnings.C09631 r0 = this.f$0;
        Objects.requireNonNull(r0);
        PowerNotificationWarnings.this.mHighTempDialog = null;
    }
}
