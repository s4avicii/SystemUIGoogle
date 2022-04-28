package com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui;

import android.os.Bundle;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$FeedbackBatch;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.utils.LogUtils;
import java.util.Objects;
import java.util.function.Supplier;

/* renamed from: com.google.android.apps.miphone.aiai.matchmaker.overview.ui.ContentSuggestionsServiceWrapperImpl$$ExternalSyntheticLambda2 */
/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class ContentSuggestionsServiceWrapperImpl$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ ContentSuggestionsServiceWrapperImpl f$0;
    public final /* synthetic */ Supplier f$1;
    public final /* synthetic */ String f$2;
    public final /* synthetic */ SuggestListener f$3;
    public final /* synthetic */ FeedbackParcelables$FeedbackBatch f$4;

    public /* synthetic */ ContentSuggestionsServiceWrapperImpl$$ExternalSyntheticLambda2(ContentSuggestionsServiceWrapperImpl contentSuggestionsServiceWrapperImpl, Supplier supplier, String str, SuggestListener suggestListener, FeedbackParcelables$FeedbackBatch feedbackParcelables$FeedbackBatch) {
        this.f$0 = contentSuggestionsServiceWrapperImpl;
        this.f$1 = supplier;
        this.f$2 = str;
        this.f$3 = suggestListener;
        this.f$4 = feedbackParcelables$FeedbackBatch;
    }

    public final void run() {
        ContentSuggestionsServiceWrapperImpl contentSuggestionsServiceWrapperImpl = this.f$0;
        Supplier supplier = this.f$1;
        String str = this.f$2;
        SuggestListener suggestListener = this.f$3;
        FeedbackParcelables$FeedbackBatch feedbackParcelables$FeedbackBatch = this.f$4;
        Objects.requireNonNull(contentSuggestionsServiceWrapperImpl);
        try {
            contentSuggestionsServiceWrapperImpl.contentSuggestionsManager.notifyInteraction(str, (Bundle) supplier.get());
            if (suggestListener != null && feedbackParcelables$FeedbackBatch != null) {
                suggestListener.onFeedbackBatchSent();
            }
        } catch (Throwable th) {
            LogUtils.m81e("Failed to notifyInteraction", th);
        }
    }
}
