package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.SparseArray;
import com.android.systemui.statusbar.KeyguardAffordanceView;

public class LockIcon extends KeyguardAffordanceView {
    public final SparseArray<Drawable> mDrawableCache = new SparseArray<>();

    public LockIcon(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mDrawableCache.clear();
    }
}
