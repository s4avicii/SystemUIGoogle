package com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui;

import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$Feedback;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$FeedbackBatch;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ScreenshotFeedback;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ScreenshotOp;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ScreenshotOpFeedback;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ScreenshotOpStatus;
import com.google.android.apps.miphone.aiai.matchmaker.overview.common.BundleUtils;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.utils.Utils;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Supplier;

/* renamed from: com.google.android.apps.miphone.aiai.matchmaker.overview.ui.ContentSuggestionsServiceClient$$ExternalSyntheticLambda1 */
/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class ContentSuggestionsServiceClient$$ExternalSyntheticLambda1 implements Supplier {
    public final /* synthetic */ ContentSuggestionsServiceClient f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ FeedbackParcelables$ScreenshotOp f$2;
    public final /* synthetic */ FeedbackParcelables$ScreenshotOpStatus f$3;
    public final /* synthetic */ long f$4;

    public /* synthetic */ ContentSuggestionsServiceClient$$ExternalSyntheticLambda1(ContentSuggestionsServiceClient contentSuggestionsServiceClient, String str, FeedbackParcelables$ScreenshotOp feedbackParcelables$ScreenshotOp, FeedbackParcelables$ScreenshotOpStatus feedbackParcelables$ScreenshotOpStatus, long j) {
        this.f$0 = contentSuggestionsServiceClient;
        this.f$1 = str;
        this.f$2 = feedbackParcelables$ScreenshotOp;
        this.f$3 = feedbackParcelables$ScreenshotOpStatus;
        this.f$4 = j;
    }

    public final Object get() {
        ContentSuggestionsServiceClient contentSuggestionsServiceClient = this.f$0;
        String str = this.f$1;
        FeedbackParcelables$ScreenshotOp feedbackParcelables$ScreenshotOp = this.f$2;
        FeedbackParcelables$ScreenshotOpStatus feedbackParcelables$ScreenshotOpStatus = this.f$3;
        long j = this.f$4;
        Objects.requireNonNull(contentSuggestionsServiceClient);
        BundleUtils bundleUtils = contentSuggestionsServiceClient.bundleUtils;
        ArrayList arrayList = new ArrayList();
        FeedbackParcelables$ScreenshotOpFeedback feedbackParcelables$ScreenshotOpFeedback = new FeedbackParcelables$ScreenshotOpFeedback();
        feedbackParcelables$ScreenshotOpFeedback.durationMs = j;
        feedbackParcelables$ScreenshotOpFeedback.f127op = feedbackParcelables$ScreenshotOp;
        feedbackParcelables$ScreenshotOpFeedback.status = feedbackParcelables$ScreenshotOpStatus;
        FeedbackParcelables$ScreenshotFeedback feedbackParcelables$ScreenshotFeedback = new FeedbackParcelables$ScreenshotFeedback();
        feedbackParcelables$ScreenshotFeedback.screenshotId = str;
        feedbackParcelables$ScreenshotFeedback.screenshotFeedback = feedbackParcelables$ScreenshotOpFeedback;
        FeedbackParcelables$Feedback feedbackParcelables$Feedback = new FeedbackParcelables$Feedback();
        arrayList.add(feedbackParcelables$Feedback);
        feedbackParcelables$Feedback.feedback = feedbackParcelables$ScreenshotFeedback;
        FeedbackParcelables$FeedbackBatch feedbackParcelables$FeedbackBatch = new FeedbackParcelables$FeedbackBatch();
        feedbackParcelables$FeedbackBatch.screenSessionId = (long) 0;
        feedbackParcelables$FeedbackBatch.overviewSessionId = str;
        int i = Utils.$r8$clinit;
        feedbackParcelables$FeedbackBatch.feedback = arrayList;
        Objects.requireNonNull(bundleUtils);
        return BundleUtils.createFeedbackRequest(feedbackParcelables$FeedbackBatch);
    }
}
