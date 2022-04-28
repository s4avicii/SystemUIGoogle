package com.google.android.systemui.gamedashboard;

import android.os.Handler;
import android.view.View;
import com.android.internal.util.ScreenshotHelper;
import com.google.android.systemui.gamedashboard.GameDashboardUiEventLogger;
import java.util.Objects;
import java.util.Optional;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ShortcutBarView$$ExternalSyntheticLambda2 implements View.OnClickListener {
    public final /* synthetic */ ShortcutBarView f$0;
    public final /* synthetic */ Optional f$1;
    public final /* synthetic */ ShortcutBarController f$2;
    public final /* synthetic */ ScreenshotHelper f$3;
    public final /* synthetic */ Handler f$4;

    public /* synthetic */ ShortcutBarView$$ExternalSyntheticLambda2(ShortcutBarView shortcutBarView, Optional optional, ShortcutBarController shortcutBarController, ScreenshotHelper screenshotHelper, Handler handler) {
        this.f$0 = shortcutBarView;
        this.f$1 = optional;
        this.f$2 = shortcutBarController;
        this.f$3 = screenshotHelper;
        this.f$4 = handler;
    }

    public final void onClick(View view) {
        ShortcutBarView shortcutBarView = this.f$0;
        Optional optional = this.f$1;
        ShortcutBarController shortcutBarController = this.f$2;
        ScreenshotHelper screenshotHelper = this.f$3;
        Handler handler = this.f$4;
        int i = ShortcutBarView.SHORTCUT_BAR_BACKGROUND_COLOR;
        Objects.requireNonNull(shortcutBarView);
        shortcutBarView.mUiEventLogger.log(GameDashboardUiEventLogger.GameDashboardEvent.GAME_DASHBOARD_SCREENSHOT);
        optional.ifPresent(new ShortcutBarView$$ExternalSyntheticLambda5(shortcutBarView, shortcutBarController, screenshotHelper, handler));
    }
}
