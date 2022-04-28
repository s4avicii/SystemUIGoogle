package com.android.systemui.navigationbar.buttons;

import android.graphics.Rect;
import android.view.View;
import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NearestTouchFrame$$ExternalSyntheticLambda1 implements Predicate {
    public final /* synthetic */ NearestTouchFrame f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ NearestTouchFrame$$ExternalSyntheticLambda1(NearestTouchFrame nearestTouchFrame, int i, int i2) {
        this.f$0 = nearestTouchFrame;
        this.f$1 = i;
        this.f$2 = i2;
    }

    public final boolean test(Object obj) {
        NearestTouchFrame nearestTouchFrame = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        int i3 = NearestTouchFrame.$r8$clinit;
        Objects.requireNonNull(nearestTouchFrame);
        return ((Rect) nearestTouchFrame.mTouchableRegions.get((View) obj)).contains(i, i2);
    }
}
