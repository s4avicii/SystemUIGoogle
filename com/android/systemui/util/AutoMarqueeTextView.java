package com.android.systemui.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

public class AutoMarqueeTextView extends SafeMarqueeTextView {
    public boolean mAggregatedVisible = false;

    public AutoMarqueeTextView(Context context) {
        super(context);
    }

    public AutoMarqueeTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        setSelected(true);
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setSelected(false);
    }

    public final void onFinishInflate() {
        onVisibilityAggregated(isVisibleToUser());
    }

    public final void onVisibilityAggregated(boolean z) {
        super.onVisibilityAggregated(z);
        if (z != this.mAggregatedVisible) {
            this.mAggregatedVisible = z;
            if (z) {
                setEllipsize(TextUtils.TruncateAt.MARQUEE);
            } else {
                setEllipsize(TextUtils.TruncateAt.END);
            }
        }
    }

    public AutoMarqueeTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public AutoMarqueeTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
