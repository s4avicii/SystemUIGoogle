package com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$Feedback;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$FeedbackBatch;
import com.google.android.apps.miphone.aiai.matchmaker.overview.common.BundleUtils;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.utils.Utils;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

/* renamed from: com.google.android.apps.miphone.aiai.matchmaker.overview.ui.SuggestController */
public final class SuggestController {
    public final BundleUtils bundleUtils = new BundleUtils();
    public final Context uiContext;
    public final ContentSuggestionsServiceWrapperImpl wrapper;

    public SuggestController(Context context, Context context2, SuggestController$$ExternalSyntheticLambda2 suggestController$$ExternalSyntheticLambda2, Executor executor, Executor executor2) {
        this.uiContext = context2;
        this.wrapper = new ContentSuggestionsServiceWrapperImpl(context, suggestController$$ExternalSyntheticLambda2, executor, executor2);
    }

    @VisibleForTesting
    public void reportMetricsToService(String str, FeedbackParcelables$FeedbackBatch feedbackParcelables$FeedbackBatch, @Nullable SuggestListener suggestListener) {
        Objects.requireNonNull(feedbackParcelables$FeedbackBatch);
        List<FeedbackParcelables$Feedback> list = feedbackParcelables$FeedbackBatch.feedback;
        int i = Utils.$r8$clinit;
        Objects.requireNonNull(list);
        if (!list.isEmpty()) {
            this.wrapper.notifyInteractionAsync(str, new SuggestController$$ExternalSyntheticLambda3(this, feedbackParcelables$FeedbackBatch), suggestListener, feedbackParcelables$FeedbackBatch);
        }
    }
}
