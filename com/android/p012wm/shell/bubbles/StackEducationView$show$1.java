package com.android.p012wm.shell.bubbles;

import android.graphics.PointF;
import android.view.View;
import com.android.p012wm.shell.animation.Interpolators;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.StackEducationView$show$1 */
/* compiled from: StackEducationView.kt */
public final class StackEducationView$show$1 implements Runnable {
    public final /* synthetic */ PointF $stackPosition;
    public final /* synthetic */ StackEducationView this$0;

    public StackEducationView$show$1(StackEducationView stackEducationView, PointF pointF) {
        this.this$0 = stackEducationView;
        this.$stackPosition = pointF;
    }

    public final void run() {
        this.this$0.requestFocus();
        StackEducationView stackEducationView = this.this$0;
        Objects.requireNonNull(stackEducationView);
        View view = (View) stackEducationView.view$delegate.getValue();
        StackEducationView stackEducationView2 = this.this$0;
        PointF pointF = this.$stackPosition;
        if (view.getResources().getConfiguration().getLayoutDirection() == 0) {
            BubblePositioner bubblePositioner = stackEducationView2.positioner;
            Objects.requireNonNull(bubblePositioner);
            view.setPadding(view.getPaddingRight() + bubblePositioner.mBubbleSize, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
        } else {
            int paddingLeft = view.getPaddingLeft();
            int paddingTop = view.getPaddingTop();
            BubblePositioner bubblePositioner2 = stackEducationView2.positioner;
            Objects.requireNonNull(bubblePositioner2);
            view.setPadding(paddingLeft, paddingTop, view.getPaddingLeft() + bubblePositioner2.mBubbleSize, view.getPaddingBottom());
        }
        float f = pointF.y;
        BubblePositioner bubblePositioner3 = stackEducationView2.positioner;
        Objects.requireNonNull(bubblePositioner3);
        view.setTranslationY((f + ((float) (bubblePositioner3.mBubbleSize / 2))) - ((float) (view.getHeight() / 2)));
        this.this$0.animate().setDuration(this.this$0.ANIMATE_DURATION).setInterpolator(Interpolators.FAST_OUT_SLOW_IN).alpha(1.0f);
    }
}
