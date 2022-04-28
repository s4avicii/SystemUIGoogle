package com.google.android.material.navigationrail;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.WindowInsetsCompat;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.navigation.NavigationBarMenuView;
import com.google.android.material.navigation.NavigationBarView;
import java.util.Objects;
import java.util.WeakHashMap;

public class NavigationRailView extends NavigationBarView {
    public View headerView;
    public final int topMargin;

    public final int getMaxItemCount() {
        return 7;
    }

    public final NavigationBarMenuView createNavigationBarMenuView(Context context) {
        return new NavigationRailMenuView(context);
    }

    public NavigationRailView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, C1777R.attr.navigationRailStyle, 2132018690);
        int dimensionPixelSize = getResources().getDimensionPixelSize(C1777R.dimen.mtrl_navigation_rail_margin);
        this.topMargin = dimensionPixelSize;
        TintTypedArray obtainTintedStyledAttributes = ThemeEnforcement.obtainTintedStyledAttributes(getContext(), attributeSet, R$styleable.NavigationRailView, C1777R.attr.navigationRailStyle, 2132018690, new int[0]);
        int resourceId = obtainTintedStyledAttributes.getResourceId(0, 0);
        if (resourceId != 0) {
            View inflate = LayoutInflater.from(getContext()).inflate(resourceId, this, false);
            View view = this.headerView;
            if (view != null) {
                removeView(view);
                this.headerView = null;
            }
            this.headerView = inflate;
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
            layoutParams.gravity = 49;
            layoutParams.topMargin = dimensionPixelSize;
            addView(inflate, 0, layoutParams);
        }
        int i = obtainTintedStyledAttributes.getInt(2, 49);
        NavigationRailMenuView navigationRailMenuView = (NavigationRailMenuView) this.menuView;
        Objects.requireNonNull(navigationRailMenuView);
        FrameLayout.LayoutParams layoutParams2 = navigationRailMenuView.layoutParams;
        if (layoutParams2.gravity != i) {
            layoutParams2.gravity = i;
            navigationRailMenuView.setLayoutParams(layoutParams2);
        }
        if (obtainTintedStyledAttributes.hasValue(1)) {
            int dimensionPixelSize2 = obtainTintedStyledAttributes.getDimensionPixelSize(1, -1);
            NavigationRailMenuView navigationRailMenuView2 = (NavigationRailMenuView) this.menuView;
            Objects.requireNonNull(navigationRailMenuView2);
            if (navigationRailMenuView2.itemMinimumHeight != dimensionPixelSize2) {
                navigationRailMenuView2.itemMinimumHeight = dimensionPixelSize2;
                navigationRailMenuView2.requestLayout();
            }
        }
        obtainTintedStyledAttributes.recycle();
        ViewUtils.doOnApplyWindowInsets(this, new ViewUtils.OnApplyWindowInsetsListener() {
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat, ViewUtils.RelativePadding relativePadding) {
                relativePadding.top = windowInsetsCompat.getSystemWindowInsetTop() + relativePadding.top;
                relativePadding.bottom = windowInsetsCompat.getSystemWindowInsetBottom() + relativePadding.bottom;
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                boolean z = true;
                if (ViewCompat.Api17Impl.getLayoutDirection(view) != 1) {
                    z = false;
                }
                int systemWindowInsetLeft = windowInsetsCompat.getSystemWindowInsetLeft();
                int systemWindowInsetRight = windowInsetsCompat.getSystemWindowInsetRight();
                int i = relativePadding.start;
                if (z) {
                    systemWindowInsetLeft = systemWindowInsetRight;
                }
                int i2 = i + systemWindowInsetLeft;
                relativePadding.start = i2;
                ViewCompat.Api17Impl.setPaddingRelative(view, i2, relativePadding.top, relativePadding.end, relativePadding.bottom);
                return windowInsetsCompat;
            }
        });
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean z2;
        super.onLayout(z, i, i2, i3, i4);
        NavigationRailMenuView navigationRailMenuView = (NavigationRailMenuView) this.menuView;
        View view = this.headerView;
        boolean z3 = true;
        int i5 = 0;
        if (view == null || view.getVisibility() == 8) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (z2) {
            int bottom = this.headerView.getBottom() + this.topMargin;
            int top = navigationRailMenuView.getTop();
            if (top < bottom) {
                i5 = bottom - top;
            }
        } else {
            Objects.requireNonNull(navigationRailMenuView);
            if ((navigationRailMenuView.layoutParams.gravity & 112) != 48) {
                z3 = false;
            }
            if (z3) {
                i5 = this.topMargin;
            }
        }
        if (i5 > 0) {
            navigationRailMenuView.layout(navigationRailMenuView.getLeft(), navigationRailMenuView.getTop() + i5, navigationRailMenuView.getRight(), navigationRailMenuView.getBottom() + i5);
        }
    }

    public final void onMeasure(int i, int i2) {
        boolean z;
        int suggestedMinimumWidth = getSuggestedMinimumWidth();
        if (View.MeasureSpec.getMode(i) != 1073741824 && suggestedMinimumWidth > 0) {
            int paddingLeft = getPaddingLeft();
            i = View.MeasureSpec.makeMeasureSpec(Math.min(View.MeasureSpec.getSize(i), getPaddingRight() + paddingLeft + suggestedMinimumWidth), 1073741824);
        }
        super.onMeasure(i, i2);
        View view = this.headerView;
        if (view == null || view.getVisibility() == 8) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            measureChild((NavigationRailMenuView) this.menuView, i, View.MeasureSpec.makeMeasureSpec((getMeasuredHeight() - this.headerView.getMeasuredHeight()) - this.topMargin, Integer.MIN_VALUE));
        }
    }
}
