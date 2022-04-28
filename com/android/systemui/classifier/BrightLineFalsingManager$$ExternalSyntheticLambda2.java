package com.android.systemui.classifier;

import com.android.systemui.classifier.FalsingClassifier;
import java.util.Objects;
import java.util.function.Function;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BrightLineFalsingManager$$ExternalSyntheticLambda2 implements Function {
    public final /* synthetic */ BrightLineFalsingManager f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ boolean[] f$2;

    public /* synthetic */ BrightLineFalsingManager$$ExternalSyntheticLambda2(BrightLineFalsingManager brightLineFalsingManager, int i, boolean[] zArr) {
        this.f$0 = brightLineFalsingManager;
        this.f$1 = i;
        this.f$2 = zArr;
    }

    public final Object apply(Object obj) {
        BrightLineFalsingManager brightLineFalsingManager = this.f$0;
        int i = this.f$1;
        boolean[] zArr = this.f$2;
        FalsingClassifier falsingClassifier = (FalsingClassifier) obj;
        Objects.requireNonNull(brightLineFalsingManager);
        brightLineFalsingManager.mHistoryTracker.falseBelief();
        brightLineFalsingManager.mHistoryTracker.falseConfidence();
        Objects.requireNonNull(falsingClassifier);
        FalsingClassifier.Result calculateFalsingResult = falsingClassifier.calculateFalsingResult(i);
        boolean z = zArr[0];
        Objects.requireNonNull(calculateFalsingResult);
        zArr[0] = z | calculateFalsingResult.mFalsed;
        return calculateFalsingResult;
    }
}
