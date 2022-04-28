package com.android.p012wm.shell.bubbles;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import java.util.Collections;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda8 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda8 implements ViewTreeObserver.OnDrawListener {
    public final /* synthetic */ BubbleStackView f$0;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda8(BubbleStackView bubbleStackView) {
        this.f$0 = bubbleStackView;
    }

    public final void onDraw() {
        BubbleStackView bubbleStackView = this.f$0;
        Objects.requireNonNull(bubbleStackView);
        Rect rect = bubbleStackView.mSystemGestureExclusionRects.get(0);
        if (bubbleStackView.getBubbleCount() > 0) {
            View childAt = bubbleStackView.mBubbleContainer.getChildAt(0);
            rect.set(childAt.getLeft(), childAt.getTop(), childAt.getRight(), childAt.getBottom());
            rect.offset((int) (childAt.getTranslationX() + 0.5f), (int) (childAt.getTranslationY() + 0.5f));
            bubbleStackView.mBubbleContainer.setSystemGestureExclusionRects(bubbleStackView.mSystemGestureExclusionRects);
            return;
        }
        rect.setEmpty();
        bubbleStackView.mBubbleContainer.setSystemGestureExclusionRects(Collections.emptyList());
    }
}
