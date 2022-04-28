package com.google.android.material.bottomnavigation;

import android.content.Context;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.navigation.NavigationBarItemView;

public final class BottomNavigationItemView extends NavigationBarItemView {
    public final int getItemDefaultMarginResId() {
        return C1777R.dimen.design_bottom_navigation_margin;
    }

    public final int getItemLayoutResId() {
        return C1777R.layout.design_bottom_navigation_item;
    }

    public BottomNavigationItemView(Context context) {
        super(context);
    }
}
