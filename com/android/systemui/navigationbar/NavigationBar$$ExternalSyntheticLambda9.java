package com.android.systemui.navigationbar;

import android.view.MotionEvent;
import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NavigationBar$$ExternalSyntheticLambda9 implements View.OnTouchListener {
    public final /* synthetic */ NavigationBar f$0;

    public /* synthetic */ NavigationBar$$ExternalSyntheticLambda9(NavigationBar navigationBar) {
        this.f$0 = navigationBar;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        NavigationBar navigationBar = this.f$0;
        Objects.requireNonNull(navigationBar);
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            navigationBar.mCommandQueue.preloadRecentApps();
            return false;
        } else if (action == 3) {
            navigationBar.mCommandQueue.cancelPreloadRecentApps();
            return false;
        } else if (action != 1 || view.isPressed()) {
            return false;
        } else {
            navigationBar.mCommandQueue.cancelPreloadRecentApps();
            return false;
        }
    }
}
