package com.android.systemui.statusbar.policy;

import java.util.Objects;
import java.util.concurrent.Executor;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SmartReplyConstants$$ExternalSyntheticLambda0 implements Executor {
    public final /* synthetic */ SmartReplyConstants f$0;

    public /* synthetic */ SmartReplyConstants$$ExternalSyntheticLambda0(SmartReplyConstants smartReplyConstants) {
        this.f$0 = smartReplyConstants;
    }

    public final void execute(Runnable runnable) {
        SmartReplyConstants smartReplyConstants = this.f$0;
        Objects.requireNonNull(smartReplyConstants);
        smartReplyConstants.mHandler.post(runnable);
    }
}
