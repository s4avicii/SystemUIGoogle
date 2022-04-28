package com.android.p012wm.shell.onehanded;

import android.content.Context;
import android.content.res.Resources;
import com.android.p012wm.shell.C1777R;

/* renamed from: com.android.wm.shell.onehanded.OneHandedSurfaceTransactionHelper */
public final class OneHandedSurfaceTransactionHelper {
    public final float mCornerRadius;
    public final float mCornerRadiusAdjustment;
    public final boolean mEnableCornerRadius;

    public OneHandedSurfaceTransactionHelper(Context context) {
        Resources resources = context.getResources();
        float dimension = resources.getDimension(17105513);
        this.mCornerRadiusAdjustment = dimension;
        this.mCornerRadius = resources.getDimension(17105512) - dimension;
        this.mEnableCornerRadius = resources.getBoolean(C1777R.bool.config_one_handed_enable_round_corner);
    }
}
