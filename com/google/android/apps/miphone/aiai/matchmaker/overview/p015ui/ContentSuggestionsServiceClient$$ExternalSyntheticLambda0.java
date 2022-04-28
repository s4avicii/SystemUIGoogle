package com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui;

import android.app.Notification;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.ContentParcelables$Contents;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.EntitiesData;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.InteractionContextParcelables$InteractionContext;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.SuggestParcelables$Entities;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.SuggestParcelables$Entity;
import com.google.android.apps.miphone.aiai.matchmaker.overview.common.BundleUtils;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.ContentSuggestionsServiceClient;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.ContentSuggestionsServiceWrapper;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.utils.LogUtils;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.utils.Utils;
import com.google.android.systemui.screenshot.ScreenshotNotificationSmartActionsProviderGoogle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import kotlinx.coroutines.internal.LockFreeLinkedListKt;

/* renamed from: com.google.android.apps.miphone.aiai.matchmaker.overview.ui.ContentSuggestionsServiceClient$$ExternalSyntheticLambda0 */
/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class ContentSuggestionsServiceClient$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ ContentSuggestionsServiceClient f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Bundle f$2;
    public final /* synthetic */ String f$3;
    public final /* synthetic */ String f$4;
    public final /* synthetic */ long f$5;
    public final /* synthetic */ InteractionContextParcelables$InteractionContext f$6;
    public final /* synthetic */ UserHandle f$7;
    public final /* synthetic */ Uri f$8;
    public final /* synthetic */ ContentSuggestionsServiceWrapper.BundleCallback f$9;

    public /* synthetic */ ContentSuggestionsServiceClient$$ExternalSyntheticLambda0(ContentSuggestionsServiceClient contentSuggestionsServiceClient, int i, Bundle bundle, String str, String str2, long j, InteractionContextParcelables$InteractionContext interactionContextParcelables$InteractionContext, UserHandle userHandle, Uri uri, ScreenshotNotificationSmartActionsProviderGoogle.C23051 r11) {
        this.f$0 = contentSuggestionsServiceClient;
        this.f$1 = i;
        this.f$2 = bundle;
        this.f$3 = str;
        this.f$4 = str2;
        this.f$5 = j;
        this.f$6 = interactionContextParcelables$InteractionContext;
        this.f$7 = userHandle;
        this.f$8 = uri;
        this.f$9 = r11;
    }

    public final void run() {
        ContentSuggestionsServiceClient contentSuggestionsServiceClient = this.f$0;
        int i = this.f$1;
        Bundle bundle = this.f$2;
        String str = this.f$3;
        String str2 = this.f$4;
        long j = this.f$5;
        InteractionContextParcelables$InteractionContext interactionContextParcelables$InteractionContext = this.f$6;
        UserHandle userHandle = this.f$7;
        Uri uri = this.f$8;
        ContentSuggestionsServiceWrapper.BundleCallback bundleCallback = this.f$9;
        Objects.requireNonNull(contentSuggestionsServiceClient);
        ContentSuggestionsServiceWrapperImpl contentSuggestionsServiceWrapperImpl = contentSuggestionsServiceClient.serviceWrapper;
        Objects.requireNonNull(contentSuggestionsServiceWrapperImpl);
        bundle.putLong("CAPTURE_TIME_MS", System.currentTimeMillis());
        try {
            contentSuggestionsServiceWrapperImpl.contentSuggestionsManager.provideContextImage(i, bundle);
        } catch (Throwable th) {
            LogUtils.m81e("Failed to provideContextImage", th);
        }
        BundleUtils bundleUtils = contentSuggestionsServiceClient.bundleUtils;
        Bundle bundle2 = new Bundle();
        LockFreeLinkedListKt lockFreeLinkedListKt = new LockFreeLinkedListKt();
        Objects.requireNonNull(bundleUtils);
        String str3 = str2;
        int i2 = i;
        long j2 = j;
        Uri uri2 = uri;
        UserHandle userHandle2 = userHandle;
        Bundle createSelectionsRequest = BundleUtils.createSelectionsRequest(str, str3, i2, j2, interactionContextParcelables$InteractionContext, bundle2, lockFreeLinkedListKt);
        createSelectionsRequest.putBoolean("IsManagedProfile", contentSuggestionsServiceClient.userManager.isManagedProfile(userHandle2.getIdentifier()));
        createSelectionsRequest.putParcelable("UserHandle", userHandle2);
        String str4 = str;
        ContentSuggestionsServiceClient.C19471 r11 = r0;
        ContentSuggestionsServiceClient.C19471 r0 = new ContentSuggestionsServiceWrapper.BundleCallback(str4, str3, i2, j2, interactionContextParcelables$InteractionContext, uri2, bundleCallback) {
            public final /* synthetic */ ContentSuggestionsServiceWrapper.BundleCallback val$bundleCallback;
            public final /* synthetic */ long val$captureTimestampMs;
            public final /* synthetic */ String val$className;
            public final /* synthetic */ InteractionContextParcelables$InteractionContext val$interactionContext;
            public final /* synthetic */ String val$packageName;
            public final /* synthetic */ Uri val$screenshotUri;
            public final /* synthetic */ int val$taskId;

            {
                this.val$packageName = r2;
                this.val$className = r3;
                this.val$taskId = r4;
                this.val$captureTimestampMs = r5;
                this.val$interactionContext = r7;
                this.val$screenshotUri = r8;
                this.val$bundleCallback = r9;
            }

            public final void onResult(Bundle bundle) {
                ContentParcelables$Contents contentParcelables$Contents;
                try {
                    Objects.requireNonNull(ContentSuggestionsServiceClient.this.bundleUtils);
                    Bundle bundle2 = bundle.getBundle("Contents");
                    if (bundle2 == null) {
                        contentParcelables$Contents = new ContentParcelables$Contents();
                    } else {
                        contentParcelables$Contents = new ContentParcelables$Contents(bundle2);
                    }
                    BundleUtils bundleUtils = ContentSuggestionsServiceClient.this.bundleUtils;
                    String str = this.val$packageName;
                    String str2 = this.val$className;
                    int i = this.val$taskId;
                    long j = this.val$captureTimestampMs;
                    Bundle bundle3 = new Bundle();
                    InteractionContextParcelables$InteractionContext interactionContextParcelables$InteractionContext = this.val$interactionContext;
                    Objects.requireNonNull(bundleUtils);
                    ContentSuggestionsServiceClient.this.serviceWrapper.classifyContentSelections(BundleUtils.createClassificationsRequest(str, str2, i, j, bundle3, interactionContextParcelables$InteractionContext, contentParcelables$Contents), new ContentSuggestionsServiceWrapper.BundleCallback() {
                        public final void onResult(Bundle bundle) {
                            try {
                                Objects.requireNonNull(ContentSuggestionsServiceClient.this.bundleUtils);
                                bundle.setClassLoader(EntitiesData.class.getClassLoader());
                                EntitiesData entitiesData = (EntitiesData) bundle.getParcelable("EntitiesData");
                                Objects.requireNonNull(entitiesData);
                                SuggestParcelables$Entities suggestParcelables$Entities = entitiesData.entities;
                                if (suggestParcelables$Entities == null) {
                                    suggestParcelables$Entities = new SuggestParcelables$Entities();
                                }
                                ArrayList arrayList = new ArrayList();
                                ArrayList arrayList2 = suggestParcelables$Entities.entities;
                                if (arrayList2 != null) {
                                    int i = Utils.$r8$clinit;
                                    Iterator it = arrayList2.iterator();
                                    while (it.hasNext()) {
                                        C19471 r3 = C19471.this;
                                        Notification.Action access$200 = ContentSuggestionsServiceClient.access$200(ContentSuggestionsServiceClient.this.context, (SuggestParcelables$Entity) it.next(), entitiesData, r3.val$screenshotUri);
                                        if (access$200 != null) {
                                            arrayList.add(access$200);
                                        }
                                    }
                                }
                                C19471 r6 = C19471.this;
                                ContentSuggestionsServiceWrapper.BundleCallback bundleCallback = r6.val$bundleCallback;
                                Objects.requireNonNull(ContentSuggestionsServiceClient.this.bundleUtils);
                                Bundle bundle2 = new Bundle();
                                bundle2.putParcelableArrayList("ScreenshotNotificationActions", arrayList);
                                bundleCallback.onResult(bundle2);
                            } catch (Throwable th) {
                                LogUtils.m81e("Failed to handle classification result while generating smart actions for screenshot notification", th);
                                C19471.this.val$bundleCallback.onResult(Bundle.EMPTY);
                            }
                        }
                    });
                } catch (Throwable th) {
                    LogUtils.m81e("Failed to handle selections response while generating smart actions for screenshot notification", th);
                    this.val$bundleCallback.onResult(Bundle.EMPTY);
                }
            }
        };
        contentSuggestionsServiceClient.serviceWrapper.suggestContentSelections(i, createSelectionsRequest, r11);
    }
}
