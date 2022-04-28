package com.google.android.systemui.assist.uihints;

import android.util.Log;
import com.android.systemui.assist.AssistManager;
import com.google.android.systemui.assist.uihints.TimeoutManager;
import dagger.Lazy;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class TimeoutManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ TimeoutManager f$0;
    public final /* synthetic */ Lazy f$1;

    public /* synthetic */ TimeoutManager$$ExternalSyntheticLambda0(TimeoutManager timeoutManager, Lazy lazy) {
        this.f$0 = timeoutManager;
        this.f$1 = lazy;
    }

    public final void run() {
        TimeoutManager timeoutManager = this.f$0;
        Lazy lazy = this.f$1;
        Objects.requireNonNull(timeoutManager);
        TimeoutManager.TimeoutCallback timeoutCallback = timeoutManager.mTimeoutCallback;
        if (timeoutCallback != null) {
            ((Runnable) ((NgaUiController$$ExternalSyntheticLambda3) timeoutCallback).f$0).run();
            return;
        }
        Log.e("TimeoutManager", "Timeout occurred, but there was no callback provided");
        ((AssistManager) lazy.get()).hideAssist();
    }
}
