package com.android.systemui.statusbar.notification.row;

import android.animation.ValueAnimator;
import java.util.Objects;

/* compiled from: ChannelEditorListView.kt */
public final class ChannelRow$playHighlight$1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ ChannelRow this$0;

    public ChannelRow$playHighlight$1(ChannelRow channelRow) {
        this.this$0 = channelRow;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        ChannelRow channelRow = this.this$0;
        Object animatedValue = valueAnimator.getAnimatedValue();
        Objects.requireNonNull(animatedValue, "null cannot be cast to non-null type kotlin.Int");
        channelRow.setBackgroundColor(((Integer) animatedValue).intValue());
    }
}
