package com.google.android.systemui.gamedashboard;

import android.content.IntentFilter;
import android.graphics.Rect;
import android.view.TouchDelegate;
import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class GameMenuActivity$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ GameMenuActivity f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ View f$2;
    public final /* synthetic */ View f$3;

    public /* synthetic */ GameMenuActivity$$ExternalSyntheticLambda5(GameMenuActivity gameMenuActivity, int i, View view, View view2) {
        this.f$0 = gameMenuActivity;
        this.f$1 = i;
        this.f$2 = view;
        this.f$3 = view2;
    }

    public final void run() {
        GameMenuActivity gameMenuActivity = this.f$0;
        int i = this.f$1;
        View view = this.f$2;
        View view2 = this.f$3;
        IntentFilter intentFilter = GameMenuActivity.DND_FILTER;
        Objects.requireNonNull(gameMenuActivity);
        int dimensionPixelSize = gameMenuActivity.getResources().getDimensionPixelSize(i);
        Rect rect = new Rect();
        view.getHitRect(rect);
        rect.top -= dimensionPixelSize;
        rect.bottom += dimensionPixelSize;
        rect.left -= dimensionPixelSize;
        rect.right += dimensionPixelSize;
        view2.setTouchDelegate(new TouchDelegate(rect, view));
    }
}
