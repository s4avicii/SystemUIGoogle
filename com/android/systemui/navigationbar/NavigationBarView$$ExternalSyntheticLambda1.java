package com.android.systemui.navigationbar;

import android.graphics.Rect;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NavigationBarView$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ NavigationBarView f$0;
    public final /* synthetic */ Rect f$1;

    public /* synthetic */ NavigationBarView$$ExternalSyntheticLambda1(NavigationBarView navigationBarView, Rect rect) {
        this.f$0 = navigationBarView;
        this.f$1 = rect;
    }

    public final void run() {
        NavigationBarView navigationBarView = this.f$0;
        Rect rect = this.f$1;
        int i = NavigationBarView.$r8$clinit;
        Objects.requireNonNull(navigationBarView);
        EdgeBackGestureHandler edgeBackGestureHandler = navigationBarView.mEdgeBackGestureHandler;
        Objects.requireNonNull(edgeBackGestureHandler);
        edgeBackGestureHandler.mPipExcludedBounds.set(rect);
    }
}
