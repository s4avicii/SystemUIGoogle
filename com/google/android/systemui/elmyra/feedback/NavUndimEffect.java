package com.google.android.systemui.elmyra.feedback;

import com.android.systemui.Dependency;
import com.android.systemui.navigationbar.NavigationBarController;
import com.google.android.systemui.elmyra.sensors.GestureSensor;

public final class NavUndimEffect implements FeedbackEffect {
    public final NavigationBarController mNavBarController = ((NavigationBarController) Dependency.get(NavigationBarController.class));

    public final void onProgress(float f, int i) {
        this.mNavBarController.touchAutoDim(0);
    }

    public final void onRelease() {
        this.mNavBarController.touchAutoDim(0);
    }

    public final void onResolve(GestureSensor.DetectionProperties detectionProperties) {
        this.mNavBarController.touchAutoDim(0);
    }
}
