package com.android.systemui.classifier;

import com.android.systemui.classifier.HistoryTracker;
import java.util.Objects;
import java.util.function.Function;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class HistoryTracker$$ExternalSyntheticLambda3 implements Function {
    public final /* synthetic */ double f$0;

    public /* synthetic */ HistoryTracker$$ExternalSyntheticLambda3(double d) {
        this.f$0 = d;
    }

    public final Object apply(Object obj) {
        double d = this.f$0;
        HistoryTracker.CombinedResult combinedResult = (HistoryTracker.CombinedResult) obj;
        Objects.requireNonNull(combinedResult);
        return Double.valueOf(Math.pow(combinedResult.mScore - d, 2.0d));
    }
}
