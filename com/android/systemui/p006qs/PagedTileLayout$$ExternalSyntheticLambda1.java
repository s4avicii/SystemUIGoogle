package com.android.systemui.p006qs;

import android.animation.AnimatorSet;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.PagedTileLayout$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PagedTileLayout$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ PagedTileLayout f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ PagedTileLayout$$ExternalSyntheticLambda1(PagedTileLayout pagedTileLayout, int i) {
        this.f$0 = pagedTileLayout;
        this.f$1 = i;
    }

    public final void run() {
        PagedTileLayout pagedTileLayout = this.f$0;
        int i = this.f$1;
        int i2 = PagedTileLayout.$r8$clinit;
        Objects.requireNonNull(pagedTileLayout);
        pagedTileLayout.setCurrentItem(i, true);
        AnimatorSet animatorSet = pagedTileLayout.mBounceAnimatorSet;
        if (animatorSet != null) {
            animatorSet.start();
        }
        pagedTileLayout.setOffscreenPageLimit(1);
    }
}
