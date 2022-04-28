package com.google.android.systemui.screenshot;

import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.Log;
import com.android.systemui.screenshot.ScreenshotNotificationSmartActionsProvider;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$FeedbackBatch;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ScreenshotOp;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ScreenshotOpStatus;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.InteractionContextParcelables$InteractionContext;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.SuggestParcelables$InteractionType;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.ContentSuggestionsServiceClient;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.ContentSuggestionsServiceClient$$ExternalSyntheticLambda0;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.ContentSuggestionsServiceClient$$ExternalSyntheticLambda1;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.ContentSuggestionsServiceClient$$ExternalSyntheticLambda2;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.ContentSuggestionsServiceWrapper;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.ContentSuggestionsServiceWrapperImpl;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.SuggestListener;
import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public final class ScreenshotNotificationSmartActionsProviderGoogle extends ScreenshotNotificationSmartActionsProvider {
    public static final ImmutableMap<ScreenshotNotificationSmartActionsProvider.ScreenshotSmartActionType, SuggestParcelables$InteractionType> SCREENSHOT_INTERACTION_TYPE_MAP;
    public static final ImmutableMap<ScreenshotNotificationSmartActionsProvider.ScreenshotOp, FeedbackParcelables$ScreenshotOp> SCREENSHOT_OP_MAP;
    public static final ImmutableMap<ScreenshotNotificationSmartActionsProvider.ScreenshotOpStatus, FeedbackParcelables$ScreenshotOpStatus> SCREENSHOT_OP_STATUS_MAP;
    public final ContentSuggestionsServiceClient mClient;

    static {
        ImmutableMap.Builder builder = new ImmutableMap.Builder(4);
        builder.put(ScreenshotNotificationSmartActionsProvider.ScreenshotOp.RETRIEVE_SMART_ACTIONS, FeedbackParcelables$ScreenshotOp.RETRIEVE_SMART_ACTIONS);
        builder.put(ScreenshotNotificationSmartActionsProvider.ScreenshotOp.REQUEST_SMART_ACTIONS, FeedbackParcelables$ScreenshotOp.REQUEST_SMART_ACTIONS);
        builder.put(ScreenshotNotificationSmartActionsProvider.ScreenshotOp.WAIT_FOR_SMART_ACTIONS, FeedbackParcelables$ScreenshotOp.WAIT_FOR_SMART_ACTIONS);
        SCREENSHOT_OP_MAP = builder.buildOrThrow();
        ImmutableMap.Builder builder2 = new ImmutableMap.Builder(4);
        builder2.put(ScreenshotNotificationSmartActionsProvider.ScreenshotOpStatus.SUCCESS, FeedbackParcelables$ScreenshotOpStatus.SUCCESS);
        builder2.put(ScreenshotNotificationSmartActionsProvider.ScreenshotOpStatus.ERROR, FeedbackParcelables$ScreenshotOpStatus.ERROR);
        builder2.put(ScreenshotNotificationSmartActionsProvider.ScreenshotOpStatus.TIMEOUT, FeedbackParcelables$ScreenshotOpStatus.TIMEOUT);
        SCREENSHOT_OP_STATUS_MAP = builder2.buildOrThrow();
        ImmutableMap.Builder builder3 = new ImmutableMap.Builder(4);
        builder3.put(ScreenshotNotificationSmartActionsProvider.ScreenshotSmartActionType.REGULAR_SMART_ACTIONS, SuggestParcelables$InteractionType.SCREENSHOT_NOTIFICATION);
        builder3.put(ScreenshotNotificationSmartActionsProvider.ScreenshotSmartActionType.QUICK_SHARE_ACTION, SuggestParcelables$InteractionType.QUICK_SHARE);
        SCREENSHOT_INTERACTION_TYPE_MAP = builder3.buildOrThrow();
    }

    public void completeFuture(Bundle bundle, CompletableFuture<List<Notification.Action>> completableFuture) {
        if (bundle.containsKey("ScreenshotNotificationActions")) {
            completableFuture.complete(bundle.getParcelableArrayList("ScreenshotNotificationActions"));
        } else {
            completableFuture.complete(Collections.emptyList());
        }
    }

    public final CompletableFuture<List<Notification.Action>> getActions(String str, Uri uri, Bitmap bitmap, ComponentName componentName, ScreenshotNotificationSmartActionsProvider.ScreenshotSmartActionType screenshotSmartActionType, UserHandle userHandle) {
        CompletableFuture<List<Notification.Action>> completableFuture = new CompletableFuture<>();
        if (bitmap.getConfig() != Bitmap.Config.HARDWARE) {
            Log.e("ScreenshotActionsGoogle", String.format("Bitmap expected: Hardware, Bitmap found: %s. Returning empty list.", new Object[]{bitmap.getConfig()}));
            return CompletableFuture.completedFuture(Collections.emptyList());
        }
        final long uptimeMillis = SystemClock.uptimeMillis();
        Log.d("ScreenshotActionsGoogle", "Calling AiAi to obtain screenshot notification smart actions.");
        ContentSuggestionsServiceClient contentSuggestionsServiceClient = this.mClient;
        String packageName = componentName.getPackageName();
        String className = componentName.getClassName();
        SuggestParcelables$InteractionType orDefault = SCREENSHOT_INTERACTION_TYPE_MAP.getOrDefault(screenshotSmartActionType, SuggestParcelables$InteractionType.SCREENSHOT_NOTIFICATION);
        final CompletableFuture<List<Notification.Action>> completableFuture2 = completableFuture;
        final String str2 = str;
        C23051 r0 = new ContentSuggestionsServiceWrapper.BundleCallback() {
            public final void onResult(Bundle bundle) {
                ScreenshotNotificationSmartActionsProviderGoogle.this.completeFuture(bundle, completableFuture2);
                long uptimeMillis = SystemClock.uptimeMillis() - uptimeMillis;
                Log.d("ScreenshotActionsGoogle", String.format("Total time taken to get smart actions: %d ms", new Object[]{Long.valueOf(uptimeMillis)}));
                ScreenshotNotificationSmartActionsProviderGoogle.this.notifyOp(str2, ScreenshotNotificationSmartActionsProvider.ScreenshotOp.RETRIEVE_SMART_ACTIONS, ScreenshotNotificationSmartActionsProvider.ScreenshotOpStatus.SUCCESS, uptimeMillis);
            }
        };
        Objects.requireNonNull(contentSuggestionsServiceClient);
        if (!contentSuggestionsServiceClient.isAiAiVersionSupported) {
            r0.onResult(Bundle.EMPTY);
        } else {
            int nextInt = ContentSuggestionsServiceClient.random.nextInt();
            long currentTimeMillis = System.currentTimeMillis();
            Objects.requireNonNull(contentSuggestionsServiceClient.bundleUtils);
            Bundle bundle = new Bundle();
            bundle.putInt("CONTEXT_IMAGE_BUNDLE_VERSION_KEY", 1);
            bundle.putBoolean("CONTEXT_IMAGE_PRIMARY_TASK_KEY", true);
            bundle.putString("CONTEXT_IMAGE_PACKAGE_NAME_KEY", packageName);
            bundle.putString("CONTEXT_IMAGE_ACTIVITY_NAME_KEY", className);
            bundle.putLong("CONTEXT_IMAGE_CAPTURE_TIME_MS_KEY", currentTimeMillis);
            bundle.putParcelable("CONTEXT_IMAGE_BITMAP_URI_KEY", uri);
            bundle.putParcelable("android.contentsuggestions.extra.BITMAP", bitmap);
            InteractionContextParcelables$InteractionContext interactionContextParcelables$InteractionContext = new InteractionContextParcelables$InteractionContext();
            interactionContextParcelables$InteractionContext.interactionType = orDefault;
            interactionContextParcelables$InteractionContext.disallowCopyPaste = false;
            interactionContextParcelables$InteractionContext.versionCode = 1;
            interactionContextParcelables$InteractionContext.isPrimaryTask = true;
            ContentSuggestionsServiceWrapperImpl contentSuggestionsServiceWrapperImpl = contentSuggestionsServiceClient.serviceWrapper;
            ContentSuggestionsServiceClient$$ExternalSyntheticLambda0 contentSuggestionsServiceClient$$ExternalSyntheticLambda0 = new ContentSuggestionsServiceClient$$ExternalSyntheticLambda0(contentSuggestionsServiceClient, nextInt, bundle, packageName, className, currentTimeMillis, interactionContextParcelables$InteractionContext, userHandle, uri, r0);
            Objects.requireNonNull(contentSuggestionsServiceWrapperImpl);
            contentSuggestionsServiceWrapperImpl.asyncExecutor.execute(contentSuggestionsServiceClient$$ExternalSyntheticLambda0);
        }
        return completableFuture;
    }

    public final void notifyAction(String str, String str2, boolean z, Intent intent) {
        ContentSuggestionsServiceClient contentSuggestionsServiceClient = this.mClient;
        Objects.requireNonNull(contentSuggestionsServiceClient);
        contentSuggestionsServiceClient.serviceWrapper.notifyInteractionAsync(str, new ContentSuggestionsServiceClient$$ExternalSyntheticLambda2(contentSuggestionsServiceClient, str, str2, z, intent), (SuggestListener) null, (FeedbackParcelables$FeedbackBatch) null);
    }

    public final void notifyOp(String str, ScreenshotNotificationSmartActionsProvider.ScreenshotOp screenshotOp, ScreenshotNotificationSmartActionsProvider.ScreenshotOpStatus screenshotOpStatus, long j) {
        ContentSuggestionsServiceClient contentSuggestionsServiceClient = this.mClient;
        Objects.requireNonNull(contentSuggestionsServiceClient);
        contentSuggestionsServiceClient.serviceWrapper.notifyInteractionAsync(str, new ContentSuggestionsServiceClient$$ExternalSyntheticLambda1(contentSuggestionsServiceClient, str, SCREENSHOT_OP_MAP.getOrDefault(screenshotOp, FeedbackParcelables$ScreenshotOp.OP_UNKNOWN), SCREENSHOT_OP_STATUS_MAP.getOrDefault(screenshotOpStatus, FeedbackParcelables$ScreenshotOpStatus.OP_STATUS_UNKNOWN), j), (SuggestListener) null, (FeedbackParcelables$FeedbackBatch) null);
    }

    public ScreenshotNotificationSmartActionsProviderGoogle(Context context, Executor executor, Handler handler) {
        this.mClient = new ContentSuggestionsServiceClient(context, executor, handler);
    }
}
