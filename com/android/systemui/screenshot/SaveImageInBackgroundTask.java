package com.android.systemui.screenshot;

import android.app.ActivityTaskManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.window.WindowContext;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.android.internal.annotations.VisibleForTesting;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.SystemUIFactory;
import com.android.systemui.screenshot.ImageExporter;
import com.android.systemui.screenshot.ScreenshotController;
import com.android.systemui.screenshot.ScreenshotNotificationSmartActionsProvider;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda22;
import java.text.DateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public final class SaveImageInBackgroundTask extends AsyncTask<Void, Void, Void> {
    public final Context mContext;
    public final ScreenshotController.SavedImageData mImageData;
    public final ImageExporter mImageExporter;
    public long mImageTime;
    public final ScreenshotController.SaveImageInBackgroundData mParams;
    public final ScreenshotController.QuickShareData mQuickShareData;
    public final Random mRandom = new Random();
    public String mScreenshotId;
    public final ScreenshotSmartActions mScreenshotSmartActions;
    public final Supplier<ScreenshotController.SavedImageData.ActionTransition> mSharedElementTransition;
    public final boolean mSmartActionsEnabled;
    public final ScreenshotNotificationSmartActionsProvider mSmartActionsProvider;

    @VisibleForTesting
    private Notification.Action createQuickShareAction(Context context, Notification.Action action, Uri uri) {
        if (action == null) {
            return null;
        }
        Intent intent = action.actionIntent.getIntent();
        intent.setType("image/png");
        intent.putExtra("android.intent.extra.STREAM", uri);
        intent.putExtra("android.intent.extra.SUBJECT", String.format("Screenshot (%s)", new Object[]{DateFormat.getDateTimeInstance().format(new Date(this.mImageTime))}));
        intent.setClipData(new ClipData(new ClipDescription("content", new String[]{"image/png"}), new ClipData.Item(uri)));
        intent.addFlags(1);
        PendingIntent activity = PendingIntent.getActivity(context, 0, intent, 335544320);
        Bundle extras = action.getExtras();
        String string = extras.getString("action_type", "Smart Action");
        Intent addFlags = new Intent(context, SmartActionsReceiver.class).putExtra("android:screenshot_action_intent", activity).addFlags(268435456);
        String str = this.mScreenshotId;
        addFlags.putExtra("android:screenshot_action_type", string).putExtra("android:screenshot_id", str).putExtra("android:smart_actions_enabled", this.mSmartActionsEnabled);
        return new Notification.Action.Builder(action.getIcon(), action.title, PendingIntent.getBroadcast(context, this.mRandom.nextInt(), addFlags, 335544320)).setContextual(true).addExtras(extras).build();
    }

    public final ArrayList buildSmartActions(List list, Context context) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Notification.Action action = (Notification.Action) it.next();
            Bundle extras = action.getExtras();
            String string = extras.getString("action_type", "Smart Action");
            Intent addFlags = new Intent(context, SmartActionsReceiver.class).putExtra("android:screenshot_action_intent", action.actionIntent).addFlags(268435456);
            String str = this.mScreenshotId;
            addFlags.putExtra("android:screenshot_action_type", string).putExtra("android:screenshot_id", str).putExtra("android:smart_actions_enabled", this.mSmartActionsEnabled);
            arrayList.add(new Notification.Action.Builder(action.getIcon(), action.title, PendingIntent.getBroadcast(context, this.mRandom.nextInt(), addFlags, 335544320)).setContextual(true).addExtras(extras).build());
        }
        return arrayList;
    }

    @VisibleForTesting
    public Notification.Action createDeleteAction(Context context, Resources resources, Uri uri) {
        return new Notification.Action.Builder(Icon.createWithResource(resources, C1777R.C1778drawable.ic_screenshot_delete), resources.getString(17040129), PendingIntent.getBroadcast(context, this.mContext.getUserId(), new Intent(context, DeleteScreenshotReceiver.class).putExtra("android:screenshot_uri_id", uri.toString()).putExtra("android:screenshot_id", this.mScreenshotId).putExtra("android:smart_actions_enabled", this.mSmartActionsEnabled).addFlags(268435456), 1409286144)).build();
    }

    @VisibleForTesting
    public Supplier<ScreenshotController.SavedImageData.ActionTransition> createEditAction(Context context, Resources resources, Uri uri) {
        return new SaveImageInBackgroundTask$$ExternalSyntheticLambda1(this, context, uri, resources);
    }

    @VisibleForTesting
    public Supplier<ScreenshotController.SavedImageData.ActionTransition> createShareAction(Context context, Resources resources, Uri uri) {
        return new SaveImageInBackgroundTask$$ExternalSyntheticLambda2(this, uri, context, resources);
    }

    public final Object doInBackground(Object[] objArr) {
        int i;
        Void[] voidArr = (Void[]) objArr;
        if (!isCancelled()) {
            UUID randomUUID = UUID.randomUUID();
            Context context = this.mContext;
            UserManager userManager = UserManager.get(context);
            try {
                i = ActivityTaskManager.getService().getLastResumedActivityUserId();
            } catch (RemoteException unused) {
                i = context.getUserId();
            }
            UserHandle userHandle = userManager.getUserInfo(i).getUserHandle();
            Thread.currentThread().setPriority(10);
            Bitmap bitmap = this.mParams.image;
            this.mScreenshotId = String.format("Screenshot_%s", new Object[]{randomUUID});
            try {
                if (this.mSmartActionsEnabled && this.mParams.mQuickShareActionsReadyListener != null) {
                    queryQuickShareAction(bitmap, userHandle);
                }
                ImageExporter imageExporter = this.mImageExporter;
                SaveImageInBackgroundTask$$ExternalSyntheticLambda0 saveImageInBackgroundTask$$ExternalSyntheticLambda0 = SaveImageInBackgroundTask$$ExternalSyntheticLambda0.INSTANCE;
                Objects.requireNonNull(imageExporter);
                Bitmap bitmap2 = bitmap;
                ImageExporter.Result result = (ImageExporter.Result) CallbackToFutureAdapter.getFuture(new ImageExporter$$ExternalSyntheticLambda1(saveImageInBackgroundTask$$ExternalSyntheticLambda0, new ImageExporter.Task(imageExporter.mResolver, randomUUID, bitmap2, ZonedDateTime.now(), imageExporter.mCompressFormat, imageExporter.mQuality))).get();
                Uri uri = result.uri;
                this.mImageTime = result.timestamp;
                ScreenshotSmartActions screenshotSmartActions = this.mScreenshotSmartActions;
                String str = this.mScreenshotId;
                ScreenshotNotificationSmartActionsProvider screenshotNotificationSmartActionsProvider = this.mSmartActionsProvider;
                ScreenshotNotificationSmartActionsProvider.ScreenshotSmartActionType screenshotSmartActionType = ScreenshotNotificationSmartActionsProvider.ScreenshotSmartActionType.REGULAR_SMART_ACTIONS;
                CompletableFuture<List<Notification.Action>> smartActionsFuture = screenshotSmartActions.getSmartActionsFuture(str, uri, bitmap, screenshotNotificationSmartActionsProvider, screenshotSmartActionType, this.mSmartActionsEnabled, userHandle);
                ArrayList arrayList = new ArrayList();
                if (this.mSmartActionsEnabled) {
                    arrayList.addAll(buildSmartActions(this.mScreenshotSmartActions.getSmartActions(this.mScreenshotId, smartActionsFuture, DeviceConfig.getInt("systemui", "screenshot_notification_smart_actions_timeout_ms", 1000), this.mSmartActionsProvider, screenshotSmartActionType), this.mContext));
                }
                ScreenshotController.SavedImageData savedImageData = this.mImageData;
                savedImageData.uri = uri;
                savedImageData.smartActions = arrayList;
                Context context2 = this.mContext;
                savedImageData.shareTransition = createShareAction(context2, context2.getResources(), uri);
                ScreenshotController.SavedImageData savedImageData2 = this.mImageData;
                Context context3 = this.mContext;
                savedImageData2.editTransition = createEditAction(context3, context3.getResources(), uri);
                ScreenshotController.SavedImageData savedImageData3 = this.mImageData;
                Context context4 = this.mContext;
                createDeleteAction(context4, context4.getResources(), uri);
                Objects.requireNonNull(savedImageData3);
                this.mImageData.quickShareAction = createQuickShareAction(this.mContext, this.mQuickShareData.quickShareAction, uri);
                this.mParams.mActionsReadyListener.onActionsReady(this.mImageData);
                this.mParams.finisher.accept(this.mImageData.uri);
                this.mParams.image = null;
            } catch (Exception unused2) {
                ScreenshotController.SaveImageInBackgroundData saveImageInBackgroundData = this.mParams;
                Objects.requireNonNull(saveImageInBackgroundData);
                saveImageInBackgroundData.image = null;
                ScreenshotController.SavedImageData savedImageData4 = this.mImageData;
                Objects.requireNonNull(savedImageData4);
                savedImageData4.uri = null;
                savedImageData4.shareTransition = null;
                savedImageData4.editTransition = null;
                savedImageData4.smartActions = null;
                savedImageData4.quickShareAction = null;
                ScreenshotController.QuickShareData quickShareData = this.mQuickShareData;
                Objects.requireNonNull(quickShareData);
                quickShareData.quickShareAction = null;
                this.mParams.mActionsReadyListener.onActionsReady(this.mImageData);
                this.mParams.finisher.accept((Object) null);
            }
        }
        return null;
    }

    public final void onCancelled(Object obj) {
        Void voidR = (Void) obj;
        ScreenshotController.SavedImageData savedImageData = this.mImageData;
        Objects.requireNonNull(savedImageData);
        savedImageData.uri = null;
        savedImageData.shareTransition = null;
        savedImageData.editTransition = null;
        savedImageData.smartActions = null;
        savedImageData.quickShareAction = null;
        ScreenshotController.QuickShareData quickShareData = this.mQuickShareData;
        Objects.requireNonNull(quickShareData);
        quickShareData.quickShareAction = null;
        this.mParams.mActionsReadyListener.onActionsReady(this.mImageData);
        this.mParams.finisher.accept((Object) null);
        ScreenshotController.SaveImageInBackgroundData saveImageInBackgroundData = this.mParams;
        Objects.requireNonNull(saveImageInBackgroundData);
        saveImageInBackgroundData.image = null;
    }

    public final void queryQuickShareAction(Bitmap bitmap, UserHandle userHandle) {
        ScreenshotSmartActions screenshotSmartActions = this.mScreenshotSmartActions;
        String str = this.mScreenshotId;
        ScreenshotNotificationSmartActionsProvider screenshotNotificationSmartActionsProvider = this.mSmartActionsProvider;
        ScreenshotNotificationSmartActionsProvider.ScreenshotSmartActionType screenshotSmartActionType = ScreenshotNotificationSmartActionsProvider.ScreenshotSmartActionType.QUICK_SHARE_ACTION;
        List<Notification.Action> smartActions = this.mScreenshotSmartActions.getSmartActions(this.mScreenshotId, screenshotSmartActions.getSmartActionsFuture(str, (Uri) null, bitmap, screenshotNotificationSmartActionsProvider, screenshotSmartActionType, this.mSmartActionsEnabled, userHandle), DeviceConfig.getInt("systemui", "screenshot_notification_quick_share_actions_timeout_ms", 500), this.mSmartActionsProvider, screenshotSmartActionType);
        if (!smartActions.isEmpty()) {
            this.mQuickShareData.quickShareAction = smartActions.get(0);
            ScreenshotController.QuickShareActionReadyListener quickShareActionReadyListener = this.mParams.mQuickShareActionsReadyListener;
            ScreenshotController.QuickShareData quickShareData = this.mQuickShareData;
            ScreenshotController$$ExternalSyntheticLambda4 screenshotController$$ExternalSyntheticLambda4 = (ScreenshotController$$ExternalSyntheticLambda4) quickShareActionReadyListener;
            Objects.requireNonNull(screenshotController$$ExternalSyntheticLambda4);
            ScreenshotController screenshotController = (ScreenshotController) screenshotController$$ExternalSyntheticLambda4.f$0;
            Objects.requireNonNull(screenshotController);
            if (quickShareData.quickShareAction != null) {
                screenshotController.mScreenshotHandler.post(new StatusBar$$ExternalSyntheticLambda22(screenshotController, quickShareData, 1));
            }
        }
    }

    public SaveImageInBackgroundTask(WindowContext windowContext, ImageExporter imageExporter, ScreenshotSmartActions screenshotSmartActions, ScreenshotController.SaveImageInBackgroundData saveImageInBackgroundData, ScreenshotController$$ExternalSyntheticLambda8 screenshotController$$ExternalSyntheticLambda8) {
        this.mContext = windowContext;
        this.mScreenshotSmartActions = screenshotSmartActions;
        this.mImageData = new ScreenshotController.SavedImageData();
        this.mQuickShareData = new ScreenshotController.QuickShareData();
        this.mSharedElementTransition = screenshotController$$ExternalSyntheticLambda8;
        this.mImageExporter = imageExporter;
        this.mParams = saveImageInBackgroundData;
        boolean z = DeviceConfig.getBoolean("systemui", "enable_screenshot_notification_smart_actions", true);
        this.mSmartActionsEnabled = z;
        if (z) {
            this.mSmartActionsProvider = SystemUIFactory.mFactory.createScreenshotNotificationSmartActionsProvider(windowContext, AsyncTask.THREAD_POOL_EXECUTOR, new Handler());
        } else {
            this.mSmartActionsProvider = new ScreenshotNotificationSmartActionsProvider();
        }
    }

    static {
        Class<SaveImageInBackgroundTask> cls = SaveImageInBackgroundTask.class;
    }
}
