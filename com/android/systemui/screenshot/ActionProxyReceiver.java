package com.android.systemui.screenshot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.p012wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda5;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.statusbar.phone.StatusBar;
import java.util.Objects;
import java.util.Optional;

public class ActionProxyReceiver extends BroadcastReceiver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityManagerWrapper mActivityManagerWrapper;
    public final ScreenshotSmartActions mScreenshotSmartActions;
    public final StatusBar mStatusBar;

    public final void onReceive(Context context, Intent intent) {
        String str;
        PipTaskOrganizer$$ExternalSyntheticLambda5 pipTaskOrganizer$$ExternalSyntheticLambda5 = new PipTaskOrganizer$$ExternalSyntheticLambda5(this, intent, context, 2);
        StatusBar statusBar = this.mStatusBar;
        if (statusBar != null) {
            statusBar.executeRunnableDismissingKeyguard(pipTaskOrganizer$$ExternalSyntheticLambda5, true, true, true);
        } else {
            pipTaskOrganizer$$ExternalSyntheticLambda5.run();
        }
        if (intent.getBooleanExtra("android:smart_actions_enabled", false)) {
            if ("android.intent.action.EDIT".equals(intent.getAction())) {
                str = "Edit";
            } else {
                str = "Share";
            }
            ScreenshotSmartActions screenshotSmartActions = this.mScreenshotSmartActions;
            String stringExtra = intent.getStringExtra("android:screenshot_id");
            Objects.requireNonNull(screenshotSmartActions);
            ScreenshotSmartActions.notifyScreenshotAction(context, stringExtra, str, false, (Intent) null);
        }
    }

    public ActionProxyReceiver(Optional<StatusBar> optional, ActivityManagerWrapper activityManagerWrapper, ScreenshotSmartActions screenshotSmartActions) {
        this.mStatusBar = optional.orElse((Object) null);
        this.mActivityManagerWrapper = activityManagerWrapper;
        this.mScreenshotSmartActions = screenshotSmartActions;
    }
}
