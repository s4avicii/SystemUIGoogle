package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.content.res.Resources;
import com.android.p012wm.shell.C1777R;

public final class HybridGroupManager {
    public final Context mContext;
    public int mOverflowNumberColor;
    public int mOverflowNumberPadding;
    public float mOverflowNumberSize;

    public HybridGroupManager(Context context) {
        this.mContext = context;
        Resources resources = context.getResources();
        this.mOverflowNumberSize = (float) resources.getDimensionPixelSize(C1777R.dimen.group_overflow_number_size);
        this.mOverflowNumberPadding = resources.getDimensionPixelSize(C1777R.dimen.group_overflow_number_padding);
    }
}
