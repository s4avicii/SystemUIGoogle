package com.android.systemui.screenshot;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.UserHandle;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.screenshot.ScreenshotController;
import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.function.Supplier;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SaveImageInBackgroundTask$$ExternalSyntheticLambda2 implements Supplier {
    public final /* synthetic */ SaveImageInBackgroundTask f$0;
    public final /* synthetic */ Uri f$1;
    public final /* synthetic */ Context f$2;
    public final /* synthetic */ Resources f$3;

    public /* synthetic */ SaveImageInBackgroundTask$$ExternalSyntheticLambda2(SaveImageInBackgroundTask saveImageInBackgroundTask, Uri uri, Context context, Resources resources) {
        this.f$0 = saveImageInBackgroundTask;
        this.f$1 = uri;
        this.f$2 = context;
        this.f$3 = resources;
    }

    public final Object get() {
        SaveImageInBackgroundTask saveImageInBackgroundTask = this.f$0;
        Uri uri = this.f$1;
        Context context = this.f$2;
        Resources resources = this.f$3;
        Objects.requireNonNull(saveImageInBackgroundTask);
        ScreenshotController.SavedImageData.ActionTransition actionTransition = saveImageInBackgroundTask.mSharedElementTransition.get();
        String format = String.format("Screenshot (%s)", new Object[]{DateFormat.getDateTimeInstance().format(new Date(saveImageInBackgroundTask.mImageTime))});
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("image/png");
        intent.putExtra("android.intent.extra.STREAM", uri);
        intent.setClipData(new ClipData(new ClipDescription("content", new String[]{"text/plain"}), new ClipData.Item(uri)));
        intent.putExtra("android.intent.extra.SUBJECT", format);
        intent.addFlags(1);
        actionTransition.action = new Notification.Action.Builder(Icon.createWithResource(resources, C1777R.C1778drawable.ic_screenshot_share), resources.getString(17041476), PendingIntent.getBroadcastAsUser(context, context.getUserId(), new Intent(context, ActionProxyReceiver.class).putExtra("android:screenshot_action_intent", PendingIntent.getActivityAsUser(context, 0, Intent.createChooser(intent, (CharSequence) null).addFlags(268468224).addFlags(1), 335544320, actionTransition.bundle, UserHandle.CURRENT)).putExtra("android:screenshot_disallow_enter_pip", true).putExtra("android:screenshot_id", saveImageInBackgroundTask.mScreenshotId).putExtra("android:smart_actions_enabled", saveImageInBackgroundTask.mSmartActionsEnabled).setAction("android.intent.action.SEND").addFlags(268435456), 335544320, UserHandle.SYSTEM)).build();
        return actionTransition;
    }
}
