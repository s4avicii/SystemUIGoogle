package com.google.android.systemui.gamedashboard;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.view.View;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.google.android.systemui.gamedashboard.GameDashboardUiEventLogger;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ToastController$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ ToastController f$0;

    public /* synthetic */ ToastController$$ExternalSyntheticLambda0(ToastController toastController) {
        this.f$0 = toastController;
    }

    public final void onClick(View view) {
        ToastController toastController = this.f$0;
        Objects.requireNonNull(toastController);
        toastController.removeViewImmediate();
        Context context = toastController.mContext;
        IntentFilter intentFilter = GameMenuActivity.DND_FILTER;
        Intent intent = new Intent(context, GameMenuActivity.class);
        ActivityOptions makeCustomTaskAnimation = ActivityOptions.makeCustomTaskAnimation(toastController.mContext, C1777R.anim.game_dashboard_fade_in, C1777R.anim.game_dashboard_fade_out, (Handler) null, (ActivityOptions.OnAnimationStartedListener) null, (ActivityOptions.OnAnimationFinishedListener) null);
        ActivityManager.RunningTaskInfo runningTask = ActivityManagerWrapper.sInstance.getRunningTask();
        makeCustomTaskAnimation.setLaunchTaskId(runningTask.taskId);
        toastController.mUiEventLogger.log(GameDashboardUiEventLogger.GameDashboardEvent.GAME_DASHBOARD_LAUNCH, 0, runningTask.baseActivity.getPackageName());
        toastController.mContext.startActivity(intent, makeCustomTaskAnimation.toBundle());
    }
}
