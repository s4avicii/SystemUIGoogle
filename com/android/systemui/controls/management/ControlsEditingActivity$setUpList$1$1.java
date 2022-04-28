package com.android.systemui.controls.management;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: ControlsEditingActivity.kt */
public final class ControlsEditingActivity$setUpList$1$1 extends GridLayoutManager {
    public ControlsEditingActivity$setUpList$1$1() {
        super(2);
    }

    public final int getRowCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        int rowCountForAccessibility = super.getRowCountForAccessibility(recycler, state);
        if (rowCountForAccessibility > 0) {
            return rowCountForAccessibility - 1;
        }
        return rowCountForAccessibility;
    }
}
