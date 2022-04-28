package com.android.systemui.screenshot;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.UserHandle;
import android.text.TextUtils;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.screenshot.ScreenshotController;
import java.util.Objects;
import java.util.function.Supplier;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SaveImageInBackgroundTask$$ExternalSyntheticLambda1 implements Supplier {
    public final /* synthetic */ SaveImageInBackgroundTask f$0;
    public final /* synthetic */ Context f$1;
    public final /* synthetic */ Uri f$2;
    public final /* synthetic */ Resources f$3;

    public /* synthetic */ SaveImageInBackgroundTask$$ExternalSyntheticLambda1(SaveImageInBackgroundTask saveImageInBackgroundTask, Context context, Uri uri, Resources resources) {
        this.f$0 = saveImageInBackgroundTask;
        this.f$1 = context;
        this.f$2 = uri;
        this.f$3 = resources;
    }

    public final Object get() {
        SaveImageInBackgroundTask saveImageInBackgroundTask = this.f$0;
        Context context = this.f$1;
        Uri uri = this.f$2;
        Resources resources = this.f$3;
        Objects.requireNonNull(saveImageInBackgroundTask);
        ScreenshotController.SavedImageData.ActionTransition actionTransition = saveImageInBackgroundTask.mSharedElementTransition.get();
        String string = context.getString(C1777R.string.config_screenshotEditor);
        Intent intent = new Intent("android.intent.action.EDIT");
        if (!TextUtils.isEmpty(string)) {
            intent.setComponent(ComponentName.unflattenFromString(string));
        }
        intent.setDataAndType(uri, "image/png");
        intent.addFlags(1);
        intent.addFlags(2);
        intent.addFlags(268468224);
        actionTransition.action = new Notification.Action.Builder(Icon.createWithResource(resources, C1777R.C1778drawable.ic_screenshot_edit), resources.getString(17041431), PendingIntent.getBroadcastAsUser(context, saveImageInBackgroundTask.mContext.getUserId(), new Intent(context, ActionProxyReceiver.class).putExtra("android:screenshot_action_intent", PendingIntent.getActivityAsUser(context, 0, intent, 67108864, actionTransition.bundle, UserHandle.CURRENT)).putExtra("android:screenshot_id", saveImageInBackgroundTask.mScreenshotId).putExtra("android:smart_actions_enabled", saveImageInBackgroundTask.mSmartActionsEnabled).putExtra("android:screenshot_override_transition", true).setAction("android.intent.action.EDIT").addFlags(268435456), 335544320, UserHandle.SYSTEM)).build();
        return actionTransition;
    }
}
