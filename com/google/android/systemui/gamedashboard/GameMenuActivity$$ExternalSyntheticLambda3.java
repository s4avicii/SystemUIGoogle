package com.google.android.systemui.gamedashboard;

import android.content.IntentFilter;
import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class GameMenuActivity$$ExternalSyntheticLambda3 implements View.OnClickListener {
    public final /* synthetic */ GameMenuActivity f$0;

    public /* synthetic */ GameMenuActivity$$ExternalSyntheticLambda3(GameMenuActivity gameMenuActivity) {
        this.f$0 = gameMenuActivity;
    }

    public final void onClick(View view) {
        GameMenuActivity gameMenuActivity = this.f$0;
        IntentFilter intentFilter = GameMenuActivity.DND_FILTER;
        Objects.requireNonNull(gameMenuActivity);
        gameMenuActivity.onGameModeSelectionChanged(view);
    }
}
