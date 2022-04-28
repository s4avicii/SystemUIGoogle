package com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteAction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserManager;
import android.text.TextUtils;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.EntitiesData;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.SuggestParcelables$Action;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.SuggestParcelables$ActionGroup;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.SuggestParcelables$Entity;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.SuggestParcelables$IntentInfo;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.SuggestParcelables$IntentType;
import com.google.android.apps.miphone.aiai.matchmaker.overview.common.BundleUtils;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.utils.LogUtils;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.utils.Utils;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Executor;

/* renamed from: com.google.android.apps.miphone.aiai.matchmaker.overview.ui.ContentSuggestionsServiceClient */
public final class ContentSuggestionsServiceClient {
    public static final Random random = new Random();
    public final BundleUtils bundleUtils;
    public final Context context;
    public final boolean isAiAiVersionSupported;
    public final ContentSuggestionsServiceWrapperImpl serviceWrapper;
    public final UserManager userManager;

    public ContentSuggestionsServiceClient(Context context2, Executor executor, Handler handler) {
        this.context = context2;
        SuggestController suggestController = new SuggestController(context2, context2, new SuggestController$$ExternalSyntheticLambda2(handler), executor, executor);
        ContentSuggestionsServiceWrapperImpl contentSuggestionsServiceWrapperImpl = suggestController.wrapper;
        boolean z = false;
        SuggestController$$ExternalSyntheticLambda1 suggestController$$ExternalSyntheticLambda1 = new SuggestController$$ExternalSyntheticLambda1(suggestController, 0);
        Objects.requireNonNull(contentSuggestionsServiceWrapperImpl);
        contentSuggestionsServiceWrapperImpl.asyncExecutor.execute(suggestController$$ExternalSyntheticLambda1);
        this.serviceWrapper = suggestController.wrapper;
        try {
            if (context2.getPackageManager().getPackageInfo("com.google.android.as", 0).getLongVersionCode() >= 660780) {
                z = true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.m81e("Error obtaining package info: ", e);
        }
        this.isAiAiVersionSupported = z;
        this.bundleUtils = new BundleUtils();
        this.userManager = (UserManager) context2.getSystemService(UserManager.class);
    }

    public static Notification.Action access$200(Context context2, SuggestParcelables$Entity suggestParcelables$Entity, EntitiesData entitiesData, Uri uri) {
        String str;
        String str2;
        Objects.requireNonNull(suggestParcelables$Entity);
        ArrayList arrayList = suggestParcelables$Entity.actions;
        Icon icon = null;
        if (arrayList == null) {
            return null;
        }
        int i = Utils.$r8$clinit;
        if (arrayList.isEmpty()) {
            return null;
        }
        ArrayList arrayList2 = suggestParcelables$Entity.actions;
        Objects.requireNonNull(arrayList2);
        int i2 = 0;
        SuggestParcelables$ActionGroup suggestParcelables$ActionGroup = (SuggestParcelables$ActionGroup) arrayList2.get(0);
        Objects.requireNonNull(suggestParcelables$ActionGroup);
        SuggestParcelables$Action suggestParcelables$Action = suggestParcelables$ActionGroup.mainAction;
        if (suggestParcelables$Action == null || suggestParcelables$Action.f128id == null) {
            return null;
        }
        if (uri != null && suggestParcelables$Action.hasProxiedIntentInfo) {
            SuggestParcelables$IntentInfo suggestParcelables$IntentInfo = suggestParcelables$Action.proxiedIntentInfo;
            Objects.requireNonNull(suggestParcelables$IntentInfo);
            if (suggestParcelables$IntentInfo.intentType == SuggestParcelables$IntentType.LENS) {
                context2.grantUriPermission("com.google.android.googlequicksearchbox", uri, 1);
            }
        }
        String str3 = suggestParcelables$Action.f128id;
        Objects.requireNonNull(str3);
        Bitmap bitmap = entitiesData.bitmapMap.get(str3);
        String str4 = suggestParcelables$Action.f128id;
        Objects.requireNonNull(str4);
        PendingIntent pendingIntent = entitiesData.pendingIntentMap.get(str4);
        if (pendingIntent == null || bitmap == null) {
            return null;
        }
        String str5 = suggestParcelables$Action.displayName;
        Objects.requireNonNull(str5);
        String str6 = suggestParcelables$Action.fullDisplayName;
        Objects.requireNonNull(str6);
        String str7 = suggestParcelables$Entity.searchQueryHint;
        Objects.requireNonNull(str7);
        String[] strArr = {str5, str6, str7};
        while (true) {
            if (i2 >= 3) {
                str = null;
                break;
            }
            str = strArr[i2];
            if (!TextUtils.isEmpty(str)) {
                break;
            }
            i2++;
        }
        if (str == null) {
            return null;
        }
        RemoteAction remoteAction = new RemoteAction(Icon.createWithBitmap(bitmap), str, str, pendingIntent);
        if (TextUtils.isEmpty(suggestParcelables$Entity.searchQueryHint)) {
            str2 = "Smart Action";
        } else {
            str2 = suggestParcelables$Entity.searchQueryHint;
        }
        Objects.requireNonNull(str2);
        if (remoteAction.shouldShowIcon()) {
            icon = remoteAction.getIcon();
        }
        Bundle bundle = new Bundle();
        bundle.putString("action_type", str2);
        bundle.putFloat("action_score", 1.0f);
        return new Notification.Action.Builder(icon, remoteAction.getTitle(), remoteAction.getActionIntent()).setContextual(true).addExtras(bundle).build();
    }
}
