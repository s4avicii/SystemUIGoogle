package com.android.keyguard.clock;

import androidx.lifecycle.Observer;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ClockManager$$ExternalSyntheticLambda0 implements Observer {
    public final /* synthetic */ ClockManager f$0;

    public /* synthetic */ ClockManager$$ExternalSyntheticLambda0(ClockManager clockManager) {
        this.f$0 = clockManager;
    }

    public final void onChanged(Object obj) {
        ClockManager clockManager = this.f$0;
        Integer num = (Integer) obj;
        Objects.requireNonNull(clockManager);
        clockManager.reload();
    }
}
