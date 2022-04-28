package com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui;

import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$FeedbackBatch;
import com.google.android.apps.miphone.aiai.matchmaker.overview.common.BundleUtils;
import java.util.Objects;
import java.util.function.Supplier;

/* renamed from: com.google.android.apps.miphone.aiai.matchmaker.overview.ui.SuggestController$$ExternalSyntheticLambda3 */
/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class SuggestController$$ExternalSyntheticLambda3 implements Supplier {
    public final /* synthetic */ SuggestController f$0;
    public final /* synthetic */ FeedbackParcelables$FeedbackBatch f$1;

    public /* synthetic */ SuggestController$$ExternalSyntheticLambda3(SuggestController suggestController, FeedbackParcelables$FeedbackBatch feedbackParcelables$FeedbackBatch) {
        this.f$0 = suggestController;
        this.f$1 = feedbackParcelables$FeedbackBatch;
    }

    public final Object get() {
        SuggestController suggestController = this.f$0;
        FeedbackParcelables$FeedbackBatch feedbackParcelables$FeedbackBatch = this.f$1;
        Objects.requireNonNull(suggestController);
        Objects.requireNonNull(suggestController.bundleUtils);
        return BundleUtils.createFeedbackRequest(feedbackParcelables$FeedbackBatch);
    }
}
