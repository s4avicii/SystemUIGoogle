package com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui;

import android.app.contentsuggestions.ClassificationsRequest;
import android.app.contentsuggestions.ContentSuggestionsManager;
import android.app.contentsuggestions.SelectionsRequest;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$FeedbackBatch;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.ContentSuggestionsServiceClient;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.ContentSuggestionsServiceWrapper;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.utils.LogUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

/* renamed from: com.google.android.apps.miphone.aiai.matchmaker.overview.ui.ContentSuggestionsServiceWrapperImpl */
public final class ContentSuggestionsServiceWrapperImpl extends ContentSuggestionsServiceWrapper {
    public final ContentSuggestionsManager contentSuggestionsManager;
    public final Map<Object, ContentSuggestionsServiceWrapper.BundleCallback> pendingCallbacks = Collections.synchronizedMap(new WeakHashMap());

    /* renamed from: com.google.android.apps.miphone.aiai.matchmaker.overview.ui.ContentSuggestionsServiceWrapperImpl$BundleCallbackWrapper */
    public static class BundleCallbackWrapper implements ContentSuggestionsServiceWrapper.BundleCallback {
        public final Object key;
        public final WeakReference<ContentSuggestionsServiceWrapperImpl> parentRef;

        public final void onResult(Bundle bundle) {
            ContentSuggestionsServiceWrapper.BundleCallback remove;
            ContentSuggestionsServiceWrapperImpl contentSuggestionsServiceWrapperImpl = this.parentRef.get();
            if (contentSuggestionsServiceWrapperImpl != null && (remove = contentSuggestionsServiceWrapperImpl.pendingCallbacks.remove(this.key)) != null) {
                remove.onResult(bundle);
            }
        }

        public BundleCallbackWrapper(ContentSuggestionsServiceWrapper.BundleCallback bundleCallback, ContentSuggestionsServiceWrapperImpl contentSuggestionsServiceWrapperImpl) {
            Object obj = new Object();
            this.key = obj;
            contentSuggestionsServiceWrapperImpl.pendingCallbacks.put(obj, bundleCallback);
            this.parentRef = new WeakReference<>(contentSuggestionsServiceWrapperImpl);
        }
    }

    public final void classifyContentSelections(Bundle bundle, ContentSuggestionsServiceClient.C19471.C19481 r4) {
        try {
            this.contentSuggestionsManager.classifyContentSelections(new ClassificationsRequest.Builder(new ArrayList()).setExtras(bundle).build(), this.callbackExecutor, new ContentSuggestionsServiceWrapperImpl$$ExternalSyntheticLambda0(new BundleCallbackWrapper(r4, this)));
        } catch (Throwable th) {
            LogUtils.m81e("Failed to classifyContentSelections", th);
        }
    }

    public final void notifyInteractionAsync(String str, Supplier<Bundle> supplier, @Nullable SuggestListener suggestListener, @Nullable FeedbackParcelables$FeedbackBatch feedbackParcelables$FeedbackBatch) {
        this.loggingExecutor.execute(new ContentSuggestionsServiceWrapperImpl$$ExternalSyntheticLambda2(this, supplier, str, suggestListener, feedbackParcelables$FeedbackBatch));
    }

    public final void suggestContentSelections(int i, Bundle bundle, ContentSuggestionsServiceWrapper.BundleCallback bundleCallback) {
        SelectionsRequest build = new SelectionsRequest.Builder(i).setExtras(bundle).build();
        try {
            this.contentSuggestionsManager.suggestContentSelections(build, this.callbackExecutor, new ContentSuggestionsServiceWrapperImpl$$ExternalSyntheticLambda1(new BundleCallbackWrapper(bundleCallback, this)));
        } catch (Throwable th) {
            LogUtils.m81e("Failed to suggestContentSelections", th);
        }
    }

    public ContentSuggestionsServiceWrapperImpl(Context context, SuggestController$$ExternalSyntheticLambda2 suggestController$$ExternalSyntheticLambda2, Executor executor, Executor executor2) {
        super(suggestController$$ExternalSyntheticLambda2, executor, executor2);
        this.contentSuggestionsManager = (ContentSuggestionsManager) context.getSystemService(ContentSuggestionsManager.class);
    }
}
