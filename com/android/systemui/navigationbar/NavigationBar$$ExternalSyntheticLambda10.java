package com.android.systemui.navigationbar;

import android.graphics.Rect;
import android.graphics.RectF;
import android.view.ViewTreeObserver;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NavigationBar$$ExternalSyntheticLambda10 implements ViewTreeObserver.OnGlobalLayoutListener {
    public final /* synthetic */ NavigationBar f$0;

    public /* synthetic */ NavigationBar$$ExternalSyntheticLambda10(NavigationBar navigationBar) {
        this.f$0 = navigationBar;
    }

    public final void onGlobalLayout() {
        NavigationBar navigationBar = this.f$0;
        Objects.requireNonNull(navigationBar);
        if (navigationBar.mStartingQuickSwitchRotation != -1) {
            RectF computeHomeHandleBounds = navigationBar.mOrientationHandle.computeHomeHandleBounds();
            navigationBar.mOrientationHandle.mapRectFromViewToScreenCoords(computeHomeHandleBounds, true);
            Rect rect = new Rect();
            computeHomeHandleBounds.roundOut(rect);
            NavigationBarView navigationBarView = navigationBar.mNavigationBarView;
            Objects.requireNonNull(navigationBarView);
            navigationBarView.mOrientedHandleSamplingRegion = rect;
            navigationBarView.mRegionSamplingHelper.updateSamplingRect();
        }
    }
}
