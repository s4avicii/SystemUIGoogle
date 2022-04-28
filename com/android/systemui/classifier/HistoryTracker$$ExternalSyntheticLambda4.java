package com.android.systemui.classifier;

import com.android.systemui.classifier.HistoryTracker;
import java.util.Objects;
import java.util.function.Function;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class HistoryTracker$$ExternalSyntheticLambda4 implements Function {
    public final /* synthetic */ long f$0;

    public /* synthetic */ HistoryTracker$$ExternalSyntheticLambda4(long j) {
        this.f$0 = j;
    }

    public final Object apply(Object obj) {
        long j = this.f$0;
        HistoryTracker.CombinedResult combinedResult = (HistoryTracker.CombinedResult) obj;
        Objects.requireNonNull(combinedResult);
        return Double.valueOf((Math.pow(HistoryTracker.HISTORY_DECAY, ((double) (10000 - (combinedResult.mExpiryMs - j))) / 100.0d) * (combinedResult.mScore - 0.5d)) + 0.5d);
    }
}
