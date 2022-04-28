package com.android.systemui.globalactions;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.annotations.VisibleForTesting;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import java.util.Objects;

public class GlobalActionsGridLayout extends GlobalActionsLayout {
    public final ListGridLayout getListView() {
        return (ListGridLayout) super.getListView();
    }

    public final void addToListView(View view, boolean z) {
        ListGridLayout listView = getListView();
        if (listView != null) {
            ViewGroup parentView = listView.getParentView(listView.mCurrentCount, listView.mReverseSublists, listView.mSwapRowsAndColumns);
            if (listView.mReverseItems) {
                parentView.addView(view, 0);
            } else {
                parentView.addView(view);
            }
            parentView.setVisibility(0);
            listView.mCurrentCount++;
        }
    }

    @VisibleForTesting
    public float getAnimationDistance() {
        return (((float) getListView().getRowCount()) * getContext().getResources().getDimension(C1777R.dimen.global_actions_grid_item_height)) / 2.0f;
    }

    public final void onUpdateList() {
        setupListView();
        super.onUpdateList();
        updateSeparatedItemSize();
    }

    public final void removeAllItems() {
        ViewGroup separatedView = getSeparatedView();
        ListGridLayout listView = getListView();
        if (separatedView != null) {
            separatedView.removeAllViews();
        }
        if (listView != null) {
            for (int i = 0; i < listView.getChildCount(); i++) {
                ViewGroup sublist = listView.getSublist(i);
                if (sublist != null) {
                    sublist.removeAllViews();
                    sublist.setVisibility(8);
                }
            }
            listView.mCurrentCount = 0;
        }
    }

    public final void removeAllListViews() {
        ListGridLayout listView = getListView();
        if (listView != null) {
            for (int i = 0; i < listView.getChildCount(); i++) {
                ViewGroup sublist = listView.getSublist(i);
                if (sublist != null) {
                    sublist.removeAllViews();
                    sublist.setVisibility(8);
                }
            }
            listView.mCurrentCount = 0;
        }
    }

    @VisibleForTesting
    public void setupListView() {
        ListGridLayout listView = getListView();
        GlobalActionsDialogLite.MyAdapter myAdapter = (GlobalActionsDialogLite.MyAdapter) this.mAdapter;
        Objects.requireNonNull(myAdapter);
        int countItems = myAdapter.countItems(false);
        Objects.requireNonNull(listView);
        listView.mExpectedCount = countItems;
        listView.mReverseSublists = shouldReverseSublists();
        listView.mReverseItems = shouldReverseListItems();
        listView.mSwapRowsAndColumns = shouldSwapRowsAndColumns();
    }

    public final boolean shouldReverseListItems() {
        boolean z;
        int currentRotation = getCurrentRotation();
        if (currentRotation == 0 || currentRotation == 3) {
            z = true;
        } else {
            z = false;
        }
        if (getCurrentLayoutDirection() == 1) {
            return !z;
        }
        return z;
    }

    @VisibleForTesting
    public boolean shouldReverseSublists() {
        if (getCurrentRotation() == 3) {
            return true;
        }
        return false;
    }

    @VisibleForTesting
    public boolean shouldSwapRowsAndColumns() {
        if (getCurrentRotation() == 0) {
            return false;
        }
        return true;
    }

    @VisibleForTesting
    public void updateSeparatedItemSize() {
        ViewGroup separatedView = getSeparatedView();
        if (separatedView.getChildCount() != 0) {
            ViewGroup.LayoutParams layoutParams = separatedView.getChildAt(0).getLayoutParams();
            if (separatedView.getChildCount() == 1) {
                layoutParams.width = -1;
                layoutParams.height = -1;
                return;
            }
            layoutParams.width = -2;
            layoutParams.height = -2;
        }
    }

    public GlobalActionsGridLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
