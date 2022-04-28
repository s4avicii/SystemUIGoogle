package com.google.android.material.navigationrail;

import android.content.Context;
import android.view.View;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.navigation.NavigationBarItemView;

public final class NavigationRailItemView extends NavigationBarItemView {
    public final int getItemDefaultMarginResId() {
        return C1777R.dimen.mtrl_navigation_rail_icon_margin;
    }

    public final int getItemLayoutResId() {
        return C1777R.layout.mtrl_navigation_rail_item;
    }

    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (View.MeasureSpec.getMode(i2) == 0) {
            setMeasuredDimension(getMeasuredWidthAndState(), View.resolveSizeAndState(Math.max(getMeasuredHeight(), View.MeasureSpec.getSize(i2)), i2, 0));
        }
    }

    public NavigationRailItemView(Context context) {
        super(context);
    }
}
