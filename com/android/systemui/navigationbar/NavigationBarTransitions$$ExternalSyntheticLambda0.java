package com.android.systemui.navigationbar;

import android.view.View;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NavigationBarTransitions$$ExternalSyntheticLambda0 implements View.OnLayoutChangeListener {
    public final /* synthetic */ NavigationBarTransitions f$0;

    public /* synthetic */ NavigationBarTransitions$$ExternalSyntheticLambda0(NavigationBarTransitions navigationBarTransitions) {
        this.f$0 = navigationBarTransitions;
    }

    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        NavigationBarTransitions navigationBarTransitions = this.f$0;
        Objects.requireNonNull(navigationBarTransitions);
        NavigationBarView navigationBarView = navigationBarTransitions.mView;
        Objects.requireNonNull(navigationBarView);
        View view2 = navigationBarView.mCurrentView;
        if (view2 != null) {
            navigationBarTransitions.mNavButtons = view2.findViewById(C1777R.C1779id.nav_buttons);
            navigationBarTransitions.applyLightsOut(false, true);
        }
    }
}
