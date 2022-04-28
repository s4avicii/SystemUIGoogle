package com.android.systemui.navigationbar;

import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NavigationBarController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ NavigationBarController f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ NavigationBarController$$ExternalSyntheticLambda0(NavigationBarController navigationBarController, int i) {
        this.f$0 = navigationBarController;
        this.f$1 = i;
    }

    public final void run() {
        NavigationBarController navigationBarController = this.f$0;
        int i = this.f$1;
        Objects.requireNonNull(navigationBarController);
        if (i != navigationBarController.mNavMode) {
            navigationBarController.updateNavbarForTaskbar();
        }
        for (int i2 = 0; i2 < navigationBarController.mNavigationBars.size(); i2++) {
            NavigationBar valueAt = navigationBarController.mNavigationBars.valueAt(i2);
            if (valueAt != null) {
                valueAt.mNavigationBarView.updateStates();
            }
        }
    }
}
