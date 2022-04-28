package com.google.android.systemui.gamedashboard;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.util.Log;
import android.view.View;
import com.google.android.systemui.gamedashboard.GameDashboardUiEventLogger;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PlayGamesWidget$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ PlayGamesWidget f$0;
    public final /* synthetic */ PendingIntent f$1;

    public /* synthetic */ PlayGamesWidget$$ExternalSyntheticLambda0(PlayGamesWidget playGamesWidget, PendingIntent pendingIntent) {
        this.f$0 = playGamesWidget;
        this.f$1 = pendingIntent;
    }

    public final void onClick(View view) {
        PlayGamesWidget playGamesWidget = this.f$0;
        PendingIntent pendingIntent = this.f$1;
        Objects.requireNonNull(playGamesWidget);
        try {
            playGamesWidget.mUiEventLogger.log(GameDashboardUiEventLogger.GameDashboardEvent.GAME_DASHBOARD_MENU_PLAY_GAMES_WIDGET);
            ((Activity) playGamesWidget.mContext).startIntentSenderForResult(pendingIntent.getIntentSender(), 0, (Intent) null, 0, 0, 0);
        } catch (IntentSender.SendIntentException unused) {
            Log.w("PlayGamesWidget", "Failed to start Play Games PendingIntent.");
        }
    }
}
