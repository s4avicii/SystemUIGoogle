package com.android.systemui.classifier;

import java.util.function.BinaryOperator;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class HistoryTracker$$ExternalSyntheticLambda0 implements BinaryOperator {
    public static final /* synthetic */ HistoryTracker$$ExternalSyntheticLambda0 INSTANCE = new HistoryTracker$$ExternalSyntheticLambda0();

    public final Object apply(Object obj, Object obj2) {
        return Double.valueOf(Double.sum(((Double) obj).doubleValue(), ((Double) obj2).doubleValue()));
    }
}
