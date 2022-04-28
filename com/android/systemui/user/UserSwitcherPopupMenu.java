package com.android.systemui.user;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ShapeDrawable;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.plugins.FalsingManager;

/* compiled from: UserSwitcherPopupMenu.kt */
public final class UserSwitcherPopupMenu extends ListPopupWindow {
    public ListAdapter adapter;
    public final Context context;
    public final FalsingManager falsingManager;
    public final Resources res;

    public UserSwitcherPopupMenu(UserSwitcherActivity userSwitcherActivity, FalsingManager falsingManager2) {
        super(userSwitcherActivity);
        this.context = userSwitcherActivity;
        this.falsingManager = falsingManager2;
        Resources resources = userSwitcherActivity.getResources();
        this.res = resources;
        setBackgroundDrawable(resources.getDrawable(C1777R.C1778drawable.bouncer_user_switcher_popup_bg, userSwitcherActivity.getTheme()));
        setModal(false);
        setOverlapAnchor(true);
    }

    public final void setAdapter(ListAdapter listAdapter) {
        super.setAdapter(listAdapter);
        this.adapter = listAdapter;
    }

    public final void show() {
        super.show();
        ListView listView = getListView();
        int i = 0;
        listView.setVerticalScrollBarEnabled(false);
        listView.setHorizontalScrollBarEnabled(false);
        ShapeDrawable shapeDrawable = new ShapeDrawable();
        shapeDrawable.setAlpha(0);
        listView.setDivider(shapeDrawable);
        listView.setDividerHeight(this.res.getDimensionPixelSize(C1777R.dimen.bouncer_user_switcher_popup_divider_height));
        int dimensionPixelSize = this.res.getDimensionPixelSize(C1777R.dimen.bouncer_user_switcher_popup_header_height);
        listView.addHeaderView(new UserSwitcherPopupMenu$createSpacer$1(dimensionPixelSize, this.context), (Object) null, false);
        listView.addFooterView(new UserSwitcherPopupMenu$createSpacer$1(dimensionPixelSize, this.context), (Object) null, false);
        ListAdapter listAdapter = this.adapter;
        if (listAdapter != null) {
            int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec((int) (((double) this.res.getDisplayMetrics().widthPixels) * 0.25d), Integer.MIN_VALUE);
            int count = listAdapter.getCount();
            int i2 = 0;
            int i3 = 0;
            while (i2 < count) {
                int i4 = i2 + 1;
                View view = listAdapter.getView(i2, (View) null, listView);
                view.measure(makeMeasureSpec, 0);
                i3 = Math.max(view.getMeasuredWidth(), i3);
                i2 = i4;
            }
            i = i3;
        }
        setWidth(i);
        super.show();
    }
}
