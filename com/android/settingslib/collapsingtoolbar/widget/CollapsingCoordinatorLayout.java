package com.android.settingslib.collapsingtoolbar.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.R$id;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.internal.CollapsingTextHelper;
import java.util.Objects;

public class CollapsingCoordinatorLayout extends CoordinatorLayout {
    public AppBarLayout mAppBarLayout;
    public CollapsingToolbarLayout mCollapsingToolbarLayout;
    public boolean mIsMatchParentHeight;
    public CharSequence mToolbarTitle;

    public CollapsingCoordinatorLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CollapsingCoordinatorLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, 0);
        this.mIsMatchParentHeight = false;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$id.CollapsingCoordinatorLayout);
            this.mToolbarTitle = obtainStyledAttributes.getText(0);
            this.mIsMatchParentHeight = obtainStyledAttributes.getBoolean(1, false);
            obtainStyledAttributes.recycle();
        }
        View.inflate(getContext(), C1777R.layout.collapsing_toolbar_content_layout, this);
        this.mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(C1777R.C1779id.collapsing_toolbar);
        this.mAppBarLayout = (AppBarLayout) findViewById(C1777R.C1779id.app_bar);
        CollapsingToolbarLayout collapsingToolbarLayout = this.mCollapsingToolbarLayout;
        if (collapsingToolbarLayout != null) {
            CollapsingTextHelper collapsingTextHelper = collapsingToolbarLayout.collapsingTextHelper;
            Objects.requireNonNull(collapsingTextHelper);
            collapsingTextHelper.lineSpacingMultiplier = 1.1f;
            if (!TextUtils.isEmpty(this.mToolbarTitle)) {
                this.mCollapsingToolbarLayout.setTitle(this.mToolbarTitle);
            }
        }
        AppBarLayout appBarLayout = this.mAppBarLayout;
        if (appBarLayout != null) {
            AppBarLayout.Behavior behavior = new AppBarLayout.Behavior();
            behavior.onDragCallback = new AppBarLayout.Behavior.DragCallback() {
            };
            ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).setBehavior(behavior);
        }
    }

    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        boolean z;
        if (view.getId() == C1777R.C1779id.content_frame && this.mIsMatchParentHeight) {
            layoutParams.height = -1;
        }
        ViewGroup viewGroup = (ViewGroup) findViewById(C1777R.C1779id.content_frame);
        if (viewGroup != null) {
            int id = view.getId();
            if (id == C1777R.C1779id.app_bar || id == C1777R.C1779id.content_frame) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                viewGroup.addView(view, i, layoutParams);
                return;
            }
        }
        super.addView(view, i, layoutParams);
    }
}
