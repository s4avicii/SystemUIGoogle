package com.google.android.systemui.communal.dock.callbacks;

import com.android.systemui.util.condition.Monitor;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class TimeoutToUserZeroCallback$$ExternalSyntheticLambda0 implements Monitor.Callback {
    public final /* synthetic */ TimeoutToUserZeroCallback f$0;

    public /* synthetic */ TimeoutToUserZeroCallback$$ExternalSyntheticLambda0(TimeoutToUserZeroCallback timeoutToUserZeroCallback) {
        this.f$0 = timeoutToUserZeroCallback;
    }

    public final void onConditionsChanged(boolean z) {
        TimeoutToUserZeroCallback timeoutToUserZeroCallback = this.f$0;
        Objects.requireNonNull(timeoutToUserZeroCallback);
        timeoutToUserZeroCallback.mPreconditionsMet = z;
        timeoutToUserZeroCallback.onStateUpdated();
    }
}
