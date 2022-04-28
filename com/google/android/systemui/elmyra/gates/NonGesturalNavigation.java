package com.google.android.systemui.elmyra.gates;

import android.content.Context;
import androidx.leanback.R$color;
import com.android.systemui.Dependency;
import com.android.systemui.navigationbar.NavigationModeController;
import java.util.Objects;

public final class NonGesturalNavigation extends Gate {
    public boolean mCurrentModeIsGestural;
    public final NavigationModeController mModeController = ((NavigationModeController) Dependency.get(NavigationModeController.class));
    public final C22561 mModeListener = new NavigationModeController.ModeChangedListener() {
        public final void onNavigationModeChanged(int i) {
            NonGesturalNavigation.this.mCurrentModeIsGestural = R$color.isGesturalMode(i);
            NonGesturalNavigation.this.notifyListener();
        }
    };

    public final boolean isBlocked() {
        return !this.mCurrentModeIsGestural;
    }

    public final void onActivate() {
        this.mCurrentModeIsGestural = R$color.isGesturalMode(this.mModeController.addListener(this.mModeListener));
    }

    public final void onDeactivate() {
        NavigationModeController navigationModeController = this.mModeController;
        C22561 r1 = this.mModeListener;
        Objects.requireNonNull(navigationModeController);
        navigationModeController.mListeners.remove(r1);
    }

    public NonGesturalNavigation(Context context) {
        super(context);
    }
}
