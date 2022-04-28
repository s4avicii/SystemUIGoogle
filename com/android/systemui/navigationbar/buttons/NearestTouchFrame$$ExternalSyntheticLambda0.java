package com.android.systemui.navigationbar.buttons;

import android.view.View;
import java.util.Comparator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NearestTouchFrame$$ExternalSyntheticLambda0 implements Comparator {
    public final /* synthetic */ NearestTouchFrame f$0;

    public /* synthetic */ NearestTouchFrame$$ExternalSyntheticLambda0(NearestTouchFrame nearestTouchFrame) {
        this.f$0 = nearestTouchFrame;
    }

    public final int compare(Object obj, Object obj2) {
        NearestTouchFrame nearestTouchFrame = this.f$0;
        int i = NearestTouchFrame.$r8$clinit;
        Objects.requireNonNull(nearestTouchFrame);
        char c = nearestTouchFrame.mIsVertical;
        ((View) obj).getLocationInWindow(nearestTouchFrame.mTmpInt);
        int[] iArr = nearestTouchFrame.mTmpInt;
        ((View) obj2).getLocationInWindow(iArr);
        return (iArr[c] - nearestTouchFrame.mOffset[c]) - (nearestTouchFrame.mTmpInt[c] - nearestTouchFrame.mOffset[c]);
    }
}
