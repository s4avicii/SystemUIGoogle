package com.android.keyguard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.view.View;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.plugins.FalsingManager;

public final class KeyguardUserSwitcherPopupMenu extends ListPopupWindow {
    public Context mContext;
    public FalsingManager mFalsingManager;

    public KeyguardUserSwitcherPopupMenu(Context context, FalsingManager falsingManager) {
        super(context);
        this.mContext = context;
        this.mFalsingManager = falsingManager;
        setBackgroundDrawable(context.getResources().getDrawable(C1777R.C1778drawable.bouncer_user_switcher_popup_bg, context.getTheme()));
        setModal(true);
        setOverlapAnchor(true);
    }

    public final void show() {
        super.show();
        ListView listView = getListView();
        listView.setVerticalScrollBarEnabled(false);
        listView.setHorizontalScrollBarEnabled(false);
        ShapeDrawable shapeDrawable = new ShapeDrawable();
        shapeDrawable.setAlpha(0);
        listView.setDivider(shapeDrawable);
        listView.setDividerHeight(this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.bouncer_user_switcher_popup_divider_height));
        final int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.bouncer_user_switcher_popup_header_height);
        listView.addHeaderView(new View(this.mContext) {
            public final void draw(Canvas canvas) {
            }

            public final void onMeasure(int i, int i2) {
                setMeasuredDimension(1, dimensionPixelSize);
            }
        }, (Object) null, false);
        listView.addFooterView(new View(this.mContext) {
            public final void draw(Canvas canvas) {
            }

            public final void onMeasure(int i, int i2) {
                setMeasuredDimension(1, dimensionPixelSize);
            }
        }, (Object) null, false);
        listView.setOnTouchListener(new KeyguardUserSwitcherPopupMenu$$ExternalSyntheticLambda0(this));
        super.show();
    }
}
