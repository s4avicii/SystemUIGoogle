package com.android.systemui.navigationbar;

import android.view.MotionEvent;
import android.view.View;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NavigationBar$$ExternalSyntheticLambda7 implements View.OnTouchListener {
    public final /* synthetic */ NavigationBar f$0;

    public /* synthetic */ NavigationBar$$ExternalSyntheticLambda7(NavigationBar navigationBar) {
        this.f$0 = navigationBar;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        return this.f$0.onHomeTouch(view, motionEvent);
    }
}
