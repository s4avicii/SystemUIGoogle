package com.android.systemui.controls.management;

import android.view.View;
import com.android.p012wm.shell.C1777R;

/* compiled from: ControlAdapter.kt */
public final class DividerHolder extends Holder {
    public final View divider;
    public final View frame;

    public final void bindData(ElementWrapper elementWrapper) {
        int i;
        DividerWrapper dividerWrapper = (DividerWrapper) elementWrapper;
        View view = this.frame;
        int i2 = 0;
        if (dividerWrapper.showNone) {
            i = 0;
        } else {
            i = 8;
        }
        view.setVisibility(i);
        View view2 = this.divider;
        if (!dividerWrapper.showDivider) {
            i2 = 8;
        }
        view2.setVisibility(i2);
    }

    public DividerHolder(View view) {
        super(view);
        this.frame = view.requireViewById(C1777R.C1779id.frame);
        this.divider = view.requireViewById(C1777R.C1779id.divider);
    }
}
