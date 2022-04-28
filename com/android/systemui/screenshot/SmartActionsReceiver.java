package com.android.systemui.screenshot;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import java.util.Objects;

public class SmartActionsReceiver extends BroadcastReceiver {
    public final ScreenshotSmartActions mScreenshotSmartActions;

    public final void onReceive(Context context, Intent intent) {
        PendingIntent pendingIntent = (PendingIntent) intent.getParcelableExtra("android:screenshot_action_intent");
        String stringExtra = intent.getStringExtra("android:screenshot_action_type");
        try {
            pendingIntent.send(context, 0, (Intent) null, (PendingIntent.OnFinished) null, (Handler) null, (String) null, ActivityOptions.makeBasic().toBundle());
        } catch (PendingIntent.CanceledException e) {
            Log.e("SmartActionsReceiver", "Pending intent canceled", e);
        }
        ScreenshotSmartActions screenshotSmartActions = this.mScreenshotSmartActions;
        String stringExtra2 = intent.getStringExtra("android:screenshot_id");
        Intent intent2 = pendingIntent.getIntent();
        Objects.requireNonNull(screenshotSmartActions);
        ScreenshotSmartActions.notifyScreenshotAction(context, stringExtra2, stringExtra, true, intent2);
    }

    public SmartActionsReceiver(ScreenshotSmartActions screenshotSmartActions) {
        this.mScreenshotSmartActions = screenshotSmartActions;
    }
}
