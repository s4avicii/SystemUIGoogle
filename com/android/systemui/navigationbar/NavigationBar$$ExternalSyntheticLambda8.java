package com.android.systemui.navigationbar;

import android.view.MotionEvent;
import android.view.View;
import com.android.systemui.statusbar.phone.AutoHideController;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NavigationBar$$ExternalSyntheticLambda8 implements View.OnTouchListener {
    public final /* synthetic */ NavigationBar f$0;

    public /* synthetic */ NavigationBar$$ExternalSyntheticLambda8(NavigationBar navigationBar) {
        this.f$0 = navigationBar;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        NavigationBar navigationBar = this.f$0;
        Objects.requireNonNull(navigationBar);
        AutoHideController autoHideController = navigationBar.mAutoHideController;
        if (autoHideController == null) {
            return false;
        }
        autoHideController.checkUserAutoHide(motionEvent);
        return false;
    }
}
