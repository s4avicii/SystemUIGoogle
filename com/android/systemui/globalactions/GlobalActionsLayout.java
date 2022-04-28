package com.android.systemui.globalactions;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.preference.R$id;
import com.android.internal.annotations.VisibleForTesting;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.HardwareBgDrawable;
import com.android.systemui.MultiListLayout;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import java.util.Locale;
import java.util.Objects;

public abstract class GlobalActionsLayout extends MultiListLayout {
    public boolean mBackgroundsSet;

    public abstract boolean shouldReverseListItems();

    public void addToListView(View view, boolean z) {
        if (z) {
            getListView().addView(view, 0);
        } else {
            getListView().addView(view);
        }
    }

    public HardwareBgDrawable getBackgroundDrawable(int i) {
        HardwareBgDrawable hardwareBgDrawable = new HardwareBgDrawable(getContext());
        hardwareBgDrawable.setTint(i);
        return hardwareBgDrawable;
    }

    @VisibleForTesting
    public int getCurrentRotation() {
        return R$id.getRotation(this.mContext);
    }

    @VisibleForTesting
    public int getCurrentLayoutDirection() {
        return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault());
    }

    public ViewGroup getListView() {
        return (ViewGroup) findViewById(16908298);
    }

    public final ViewGroup getSeparatedView() {
        return (ViewGroup) findViewById(C1777R.C1779id.separated_button);
    }

    public void onMeasure(int i, int i2) {
        HardwareBgDrawable backgroundDrawable;
        super.onMeasure(i, i2);
        if (getListView() != null && !this.mBackgroundsSet) {
            ViewGroup listView = getListView();
            HardwareBgDrawable backgroundDrawable2 = getBackgroundDrawable(getResources().getColor(C1777R.color.global_actions_grid_background, (Resources.Theme) null));
            if (backgroundDrawable2 != null) {
                listView.setBackground(backgroundDrawable2);
            }
            if (!(getSeparatedView() == null || (backgroundDrawable = getBackgroundDrawable(getResources().getColor(C1777R.color.global_actions_separated_background, (Resources.Theme) null))) == null)) {
                getSeparatedView().setBackground(backgroundDrawable);
            }
            this.mBackgroundsSet = true;
        }
    }

    public void onUpdateList() {
        super.onUpdateList();
        getSeparatedView();
        ViewGroup listView = getListView();
        for (int i = 0; i < ((GlobalActionsDialogLite.MyAdapter) this.mAdapter).getCount(); i++) {
            GlobalActionsDialogLite.MyAdapter myAdapter = (GlobalActionsDialogLite.MyAdapter) this.mAdapter;
            Objects.requireNonNull(myAdapter);
            myAdapter.getItem(i).shouldBeSeparated();
            addToListView(((GlobalActionsDialogLite.MyAdapter) this.mAdapter).getView(i, (View) null, listView), shouldReverseListItems());
        }
    }

    public GlobalActionsLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
