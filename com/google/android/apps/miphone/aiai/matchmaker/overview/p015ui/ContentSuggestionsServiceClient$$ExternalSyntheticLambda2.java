package com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$Feedback;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$FeedbackBatch;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$QuickShareInfo;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ScreenshotActionFeedback;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ScreenshotFeedback;
import com.google.android.apps.miphone.aiai.matchmaker.overview.common.BundleUtils;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.utils.Utils;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Supplier;

/* renamed from: com.google.android.apps.miphone.aiai.matchmaker.overview.ui.ContentSuggestionsServiceClient$$ExternalSyntheticLambda2 */
/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class ContentSuggestionsServiceClient$$ExternalSyntheticLambda2 implements Supplier {
    public final /* synthetic */ ContentSuggestionsServiceClient f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ String f$2;
    public final /* synthetic */ boolean f$3;
    public final /* synthetic */ Intent f$4;

    public /* synthetic */ ContentSuggestionsServiceClient$$ExternalSyntheticLambda2(ContentSuggestionsServiceClient contentSuggestionsServiceClient, String str, String str2, boolean z, Intent intent) {
        this.f$0 = contentSuggestionsServiceClient;
        this.f$1 = str;
        this.f$2 = str2;
        this.f$3 = z;
        this.f$4 = intent;
    }

    public final Object get() {
        ContentSuggestionsServiceClient contentSuggestionsServiceClient = this.f$0;
        String str = this.f$1;
        String str2 = this.f$2;
        boolean z = this.f$3;
        Intent intent = this.f$4;
        Objects.requireNonNull(contentSuggestionsServiceClient);
        BundleUtils bundleUtils = contentSuggestionsServiceClient.bundleUtils;
        ArrayList arrayList = new ArrayList();
        FeedbackParcelables$ScreenshotActionFeedback feedbackParcelables$ScreenshotActionFeedback = new FeedbackParcelables$ScreenshotActionFeedback();
        feedbackParcelables$ScreenshotActionFeedback.actionType = str2;
        feedbackParcelables$ScreenshotActionFeedback.isSmartActions = z;
        if (!(!"QUICK_SHARE".equals(str2) || intent == null || intent.getComponent() == null)) {
            FeedbackParcelables$QuickShareInfo feedbackParcelables$QuickShareInfo = new FeedbackParcelables$QuickShareInfo();
            Uri uri = (Uri) intent.getParcelableExtra("android.intent.extra.STREAM");
            if (uri != null) {
                feedbackParcelables$QuickShareInfo.contentUri = uri.toString();
            }
            ComponentName component = intent.getComponent();
            int i = Utils.$r8$clinit;
            Objects.requireNonNull(component);
            feedbackParcelables$QuickShareInfo.targetPackageName = component.getPackageName();
            ComponentName component2 = intent.getComponent();
            Objects.requireNonNull(component2);
            feedbackParcelables$QuickShareInfo.targetClassName = component2.getClassName();
            feedbackParcelables$QuickShareInfo.targetShortcutId = intent.getStringExtra("android.intent.extra.shortcut.ID");
            feedbackParcelables$ScreenshotActionFeedback.quickShareInfo = feedbackParcelables$QuickShareInfo;
        }
        FeedbackParcelables$ScreenshotFeedback feedbackParcelables$ScreenshotFeedback = new FeedbackParcelables$ScreenshotFeedback();
        feedbackParcelables$ScreenshotFeedback.screenshotId = str;
        feedbackParcelables$ScreenshotFeedback.screenshotFeedback = feedbackParcelables$ScreenshotActionFeedback;
        FeedbackParcelables$Feedback feedbackParcelables$Feedback = new FeedbackParcelables$Feedback();
        arrayList.add(feedbackParcelables$Feedback);
        feedbackParcelables$Feedback.feedback = feedbackParcelables$ScreenshotFeedback;
        FeedbackParcelables$FeedbackBatch feedbackParcelables$FeedbackBatch = new FeedbackParcelables$FeedbackBatch();
        feedbackParcelables$FeedbackBatch.screenSessionId = (long) 0;
        feedbackParcelables$FeedbackBatch.overviewSessionId = str;
        int i2 = Utils.$r8$clinit;
        feedbackParcelables$FeedbackBatch.feedback = arrayList;
        Objects.requireNonNull(bundleUtils);
        return BundleUtils.createFeedbackRequest(feedbackParcelables$FeedbackBatch);
    }
}
