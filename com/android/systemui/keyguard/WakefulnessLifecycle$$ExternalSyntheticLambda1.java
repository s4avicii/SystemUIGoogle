package com.android.systemui.keyguard;

import com.android.systemui.keyguard.WakefulnessLifecycle;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WakefulnessLifecycle$$ExternalSyntheticLambda1 implements Consumer {
    public static final /* synthetic */ WakefulnessLifecycle$$ExternalSyntheticLambda1 INSTANCE = new WakefulnessLifecycle$$ExternalSyntheticLambda1();

    public final void accept(Object obj) {
        ((WakefulnessLifecycle.Observer) obj).onFinishedWakingUp();
    }
}
