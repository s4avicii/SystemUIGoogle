package com.android.p012wm.shell.bubbles;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.BubbleExpandedView$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleExpandedView$$ExternalSyntheticLambda0 implements View.OnTouchListener {
    public final /* synthetic */ BubbleExpandedView f$0;

    public /* synthetic */ BubbleExpandedView$$ExternalSyntheticLambda0(BubbleExpandedView bubbleExpandedView) {
        this.f$0 = bubbleExpandedView;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        BubbleExpandedView bubbleExpandedView = this.f$0;
        int i = BubbleExpandedView.$r8$clinit;
        Objects.requireNonNull(bubbleExpandedView);
        if (bubbleExpandedView.mTaskView == null) {
            return false;
        }
        Rect rect = new Rect();
        bubbleExpandedView.mTaskView.getBoundsOnScreen(rect);
        if (motionEvent.getRawY() < ((float) rect.top) || motionEvent.getRawY() > ((float) rect.bottom)) {
            return false;
        }
        if (motionEvent.getRawX() < ((float) rect.left) || motionEvent.getRawX() > ((float) rect.right)) {
            return true;
        }
        return false;
    }
}
