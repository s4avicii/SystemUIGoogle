package com.google.android.systemui.assist.uihints;

import androidx.leanback.R$color;
import com.android.systemui.navigationbar.NavigationModeController;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class TouchInsideHandler$$ExternalSyntheticLambda0 implements NavigationModeController.ModeChangedListener {
    public final /* synthetic */ TouchInsideHandler f$0;

    public /* synthetic */ TouchInsideHandler$$ExternalSyntheticLambda0(TouchInsideHandler touchInsideHandler) {
        this.f$0 = touchInsideHandler;
    }

    public final void onNavigationModeChanged(int i) {
        TouchInsideHandler touchInsideHandler = this.f$0;
        Objects.requireNonNull(touchInsideHandler);
        boolean isGesturalMode = R$color.isGesturalMode(i);
        touchInsideHandler.mInGesturalMode = isGesturalMode;
        if (isGesturalMode) {
            touchInsideHandler.mGuardLocked = false;
            touchInsideHandler.mGuarded = false;
        }
    }
}
