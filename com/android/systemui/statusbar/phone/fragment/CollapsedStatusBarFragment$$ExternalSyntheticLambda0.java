package com.android.systemui.statusbar.phone.fragment;

import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class CollapsedStatusBarFragment$$ExternalSyntheticLambda0 implements View.OnLayoutChangeListener {
    public final /* synthetic */ CollapsedStatusBarFragment f$0;

    public /* synthetic */ CollapsedStatusBarFragment$$ExternalSyntheticLambda0(CollapsedStatusBarFragment collapsedStatusBarFragment) {
        this.f$0 = collapsedStatusBarFragment;
    }

    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        CollapsedStatusBarFragment collapsedStatusBarFragment = this.f$0;
        if (i == i5 && i3 == i7) {
            Objects.requireNonNull(collapsedStatusBarFragment);
        } else {
            collapsedStatusBarFragment.updateStatusBarLocation(i, i3);
        }
    }
}
