package com.android.systemui.p006qs;

import android.content.Context;
import android.util.AttributeSet;
import com.android.p012wm.shell.C1777R;

/* renamed from: com.android.systemui.qs.SideLabelTileLayout */
/* compiled from: SideLabelTileLayout.kt */
public class SideLabelTileLayout extends TileLayout {
    public final boolean useSidePadding() {
        return false;
    }

    public final boolean updateMaxRows(int i, int i2) {
        int i3 = this.mRows;
        int i4 = this.mMaxAllowedRows;
        this.mRows = i4;
        int i5 = this.mColumns;
        if (i4 > ((i2 + i5) - 1) / i5) {
            this.mRows = ((i2 + i5) - 1) / i5;
        }
        if (i3 != this.mRows) {
            return true;
        }
        return false;
    }

    public boolean updateResources() {
        boolean updateResources = super.updateResources();
        this.mMaxAllowedRows = getContext().getResources().getInteger(C1777R.integer.quick_settings_max_rows);
        return updateResources;
    }

    public SideLabelTileLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
