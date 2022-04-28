package com.android.systemui.p006qs;

import android.view.animation.Interpolator;

/* renamed from: com.android.systemui.qs.PagedTileLayout$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PagedTileLayout$$ExternalSyntheticLambda0 implements Interpolator {
    public static final /* synthetic */ PagedTileLayout$$ExternalSyntheticLambda0 INSTANCE = new PagedTileLayout$$ExternalSyntheticLambda0();

    public final float getInterpolation(float f) {
        int i = PagedTileLayout.$r8$clinit;
        float f2 = f - 1.0f;
        return (f2 * f2 * f2) + 1.0f;
    }
}
