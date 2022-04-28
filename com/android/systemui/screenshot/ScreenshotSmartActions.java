package com.android.systemui.screenshot;

import android.app.ActivityManager;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.SystemUIFactory;
import com.android.systemui.screenshot.ScreenshotNotificationSmartActionsProvider;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class ScreenshotSmartActions {
    public static void notifyScreenshotOp(String str, ScreenshotNotificationSmartActionsProvider screenshotNotificationSmartActionsProvider, ScreenshotNotificationSmartActionsProvider.ScreenshotOp screenshotOp, ScreenshotNotificationSmartActionsProvider.ScreenshotOpStatus screenshotOpStatus, long j) {
        try {
            screenshotNotificationSmartActionsProvider.notifyOp(str, screenshotOp, screenshotOpStatus, j);
        } catch (Throwable th) {
            Log.e("Screenshot", "Error in notifyScreenshotOp: ", th);
        }
    }

    public static void notifyScreenshotAction(Context context, String str, String str2, boolean z, Intent intent) {
        try {
            SystemUIFactory.mFactory.createScreenshotNotificationSmartActionsProvider(context, AsyncTask.THREAD_POOL_EXECUTOR, new Handler()).notifyAction(str, str2, z, intent);
        } catch (Throwable th) {
            Log.e("Screenshot", "Error in notifyScreenshotAction: ", th);
        }
    }

    @VisibleForTesting
    public List<Notification.Action> getSmartActions(String str, CompletableFuture<List<Notification.Action>> completableFuture, int i, ScreenshotNotificationSmartActionsProvider screenshotNotificationSmartActionsProvider, ScreenshotNotificationSmartActionsProvider.ScreenshotSmartActionType screenshotSmartActionType) {
        ScreenshotNotificationSmartActionsProvider.ScreenshotOpStatus screenshotOpStatus;
        ScreenshotNotificationSmartActionsProvider.ScreenshotOp screenshotOp = ScreenshotNotificationSmartActionsProvider.ScreenshotOp.WAIT_FOR_SMART_ACTIONS;
        long uptimeMillis = SystemClock.uptimeMillis();
        try {
            List<Notification.Action> list = completableFuture.get((long) i, TimeUnit.MILLISECONDS);
            String str2 = str;
            ScreenshotNotificationSmartActionsProvider screenshotNotificationSmartActionsProvider2 = screenshotNotificationSmartActionsProvider;
            ScreenshotNotificationSmartActionsProvider.ScreenshotOp screenshotOp2 = screenshotOp;
            notifyScreenshotOp(str2, screenshotNotificationSmartActionsProvider2, screenshotOp2, ScreenshotNotificationSmartActionsProvider.ScreenshotOpStatus.SUCCESS, SystemClock.uptimeMillis() - uptimeMillis);
            return list;
        } catch (Throwable th) {
            long uptimeMillis2 = SystemClock.uptimeMillis() - uptimeMillis;
            if (th instanceof TimeoutException) {
                screenshotOpStatus = ScreenshotNotificationSmartActionsProvider.ScreenshotOpStatus.TIMEOUT;
            } else {
                screenshotOpStatus = ScreenshotNotificationSmartActionsProvider.ScreenshotOpStatus.ERROR;
            }
            notifyScreenshotOp(str, screenshotNotificationSmartActionsProvider, screenshotOp, screenshotOpStatus, uptimeMillis2);
            return Collections.emptyList();
        }
    }

    @VisibleForTesting
    public CompletableFuture<List<Notification.Action>> getSmartActionsFuture(String str, Uri uri, Bitmap bitmap, ScreenshotNotificationSmartActionsProvider screenshotNotificationSmartActionsProvider, ScreenshotNotificationSmartActionsProvider.ScreenshotSmartActionType screenshotSmartActionType, boolean z, UserHandle userHandle) {
        ComponentName componentName;
        if (!z) {
            return CompletableFuture.completedFuture(Collections.emptyList());
        }
        if (bitmap.getConfig() != Bitmap.Config.HARDWARE) {
            return CompletableFuture.completedFuture(Collections.emptyList());
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        try {
            ActivityManager.RunningTaskInfo runningTask = ActivityManagerWrapper.sInstance.getRunningTask();
            if (runningTask == null || (componentName = runningTask.topActivity) == null) {
                componentName = new ComponentName("", "");
            }
            return screenshotNotificationSmartActionsProvider.getActions(str, uri, bitmap, componentName, screenshotSmartActionType, userHandle);
        } catch (Throwable unused) {
            CompletableFuture<List<Notification.Action>> completedFuture = CompletableFuture.completedFuture(Collections.emptyList());
            String str2 = str;
            ScreenshotNotificationSmartActionsProvider screenshotNotificationSmartActionsProvider2 = screenshotNotificationSmartActionsProvider;
            notifyScreenshotOp(str2, screenshotNotificationSmartActionsProvider2, ScreenshotNotificationSmartActionsProvider.ScreenshotOp.REQUEST_SMART_ACTIONS, ScreenshotNotificationSmartActionsProvider.ScreenshotOpStatus.ERROR, SystemClock.uptimeMillis() - uptimeMillis);
            return completedFuture;
        }
    }
}
