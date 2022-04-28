package com.google.android.systemui.gamedashboard;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.systemui.gamedashboard.GameDashboardUiEventLogger;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class GameMenuActivity$$ExternalSyntheticLambda0 implements DialogInterface.OnClickListener {
    public final /* synthetic */ GameMenuActivity f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ String f$2;

    public /* synthetic */ GameMenuActivity$$ExternalSyntheticLambda0(GameMenuActivity gameMenuActivity, int i, String str) {
        this.f$0 = gameMenuActivity;
        this.f$1 = i;
        this.f$2 = str;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        GameMenuActivity gameMenuActivity = this.f$0;
        int i2 = this.f$1;
        String str = this.f$2;
        IntentFilter intentFilter = GameMenuActivity.DND_FILTER;
        Objects.requireNonNull(gameMenuActivity);
        gameMenuActivity.toggleGameModeRadioButtons(i2);
        gameMenuActivity.mGameManager.setGameMode(str, i2);
        gameMenuActivity.mUiEventLogger.log(GameDashboardUiEventLogger.GameDashboardEvent.GAME_DASHBOARD_RESTART_NOW);
        try {
            EntryPointController entryPointController = gameMenuActivity.mController;
            Objects.requireNonNull(entryPointController);
            String str2 = entryPointController.mGamePackageName;
            IActivityManager service = ActivityManager.getService();
            EntryPointController entryPointController2 = gameMenuActivity.mController;
            Objects.requireNonNull(entryPointController2);
            service.forceStopPackage(entryPointController2.mGamePackageName, -2);
            gameMenuActivity.mActivityStarter.startActivity(gameMenuActivity.getPackageManager().getLaunchIntentForPackage(str2), true);
        } catch (RemoteException e) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Fail to restart the game: ");
            m.append(e.getMessage());
            Log.e("GameMenuActivity", m.toString());
        }
        gameMenuActivity.finish();
    }
}
