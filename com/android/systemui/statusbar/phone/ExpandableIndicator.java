package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.android.p012wm.shell.C1777R;

public class ExpandableIndicator extends ImageView {
    public boolean mIsDefaultDirection = true;

    public ExpandableIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void onFinishInflate() {
        int i;
        super.onFinishInflate();
        if (this.mIsDefaultDirection) {
            i = C1777R.C1778drawable.ic_volume_expand_animation;
        } else {
            i = C1777R.C1778drawable.ic_volume_collapse_animation;
        }
        setImageResource(i);
        setContentDescription(this.mContext.getString(C1777R.string.accessibility_quick_settings_expand));
    }
}
