package com.google.android.systemui.gamedashboard;

import android.app.ActivityManager;
import android.content.IntentFilter;
import android.view.View;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.google.android.systemui.gamedashboard.GameDashboardUiEventLogger;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class GameMenuActivity$$ExternalSyntheticLambda4 implements View.OnClickListener {
    public final /* synthetic */ GameMenuActivity f$0;
    public final /* synthetic */ GameDashboardButton f$1;

    public /* synthetic */ GameMenuActivity$$ExternalSyntheticLambda4(GameMenuActivity gameMenuActivity, GameDashboardButton gameDashboardButton) {
        this.f$0 = gameMenuActivity;
        this.f$1 = gameDashboardButton;
    }

    public final void onClick(View view) {
        GameDashboardUiEventLogger.GameDashboardEvent gameDashboardEvent;
        int i;
        int i2;
        GameMenuActivity gameMenuActivity = this.f$0;
        GameDashboardButton gameDashboardButton = this.f$1;
        IntentFilter intentFilter = GameMenuActivity.DND_FILTER;
        Objects.requireNonNull(gameMenuActivity);
        Objects.requireNonNull(gameDashboardButton);
        boolean z = !gameDashboardButton.mToggled;
        ShortcutBarController shortcutBarController = gameMenuActivity.mShortcutBarController;
        Objects.requireNonNull(shortcutBarController);
        if (z) {
            shortcutBarController.mToast.showShortcutText(C1777R.string.game_dashboard_fps_counter_on);
        }
        ShortcutBarView shortcutBarView = shortcutBarController.mView;
        Objects.requireNonNull(shortcutBarView);
        GameDashboardUiEventLogger gameDashboardUiEventLogger = shortcutBarView.mUiEventLogger;
        if (z) {
            gameDashboardEvent = GameDashboardUiEventLogger.GameDashboardEvent.GAME_DASHBOARD_MENU_ENABLE_FPS;
        } else {
            gameDashboardEvent = GameDashboardUiEventLogger.GameDashboardEvent.GAME_DASHBOARD_MENU_DISABLE_FPS;
        }
        gameDashboardUiEventLogger.log(gameDashboardEvent);
        shortcutBarView.mIsFpsVisible = z;
        TextView textView = shortcutBarView.mFpsView;
        if (z) {
            i = 0;
        } else {
            i = 8;
        }
        textView.setVisibility(i);
        if (shortcutBarView.mRevealButton.getVisibility() == 0 && shortcutBarView.mIsAttached) {
            shortcutBarView.mRevealButton.bounce(z);
        }
        shortcutBarController.onButtonVisibilityChange(z);
        if (z) {
            ShortcutBarController shortcutBarController2 = gameMenuActivity.mShortcutBarController;
            EntryPointController entryPointController = gameMenuActivity.mController;
            Objects.requireNonNull(entryPointController);
            ActivityManager.RunningTaskInfo runningTaskInfo = entryPointController.mGameTaskInfo;
            if (runningTaskInfo == null) {
                i2 = -1;
            } else {
                i2 = runningTaskInfo.taskId;
            }
            shortcutBarController2.registerFps(i2);
            return;
        }
        ShortcutBarController shortcutBarController3 = gameMenuActivity.mShortcutBarController;
        Objects.requireNonNull(shortcutBarController3);
        FpsController fpsController = shortcutBarController3.mFpsController;
        Objects.requireNonNull(fpsController);
        fpsController.mWindowManager.unregisterTaskFpsCallback(fpsController.mListener);
        if (fpsController.mCallback != null) {
            fpsController.mCallback = null;
        }
    }
}
