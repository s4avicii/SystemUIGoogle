package com.google.android.systemui.gamedashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.android.systemui.theme.ThemeOverlayApplier;

public final class GameDndConfigActivity extends Activity {
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        startActivityForResult(new Intent("com.google.android.settings.games.GAME_SETTINGS").setPackage(ThemeOverlayApplier.SETTINGS_PACKAGE), 0);
        finish();
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        finish();
    }
}
