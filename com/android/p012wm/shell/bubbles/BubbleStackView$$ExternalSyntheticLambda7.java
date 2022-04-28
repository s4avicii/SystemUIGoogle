package com.android.p012wm.shell.bubbles;

import android.graphics.PointF;
import android.graphics.RectF;
import android.view.View;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.BubbleStackView;
import com.android.p012wm.shell.bubbles.animation.PhysicsAnimationLayout;
import com.android.p012wm.shell.bubbles.animation.StackAnimationController;
import com.android.systemui.p006qs.QSAnimator$$ExternalSyntheticLambda0;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda7 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda7 implements View.OnLayoutChangeListener {
    public final /* synthetic */ BubbleStackView f$0;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda7(BubbleStackView bubbleStackView) {
        this.f$0 = bubbleStackView;
    }

    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        float f;
        BubbleStackView bubbleStackView = this.f$0;
        Objects.requireNonNull(bubbleStackView);
        bubbleStackView.mPositioner.update();
        bubbleStackView.onDisplaySizeChanged();
        bubbleStackView.mExpandedAnimationController.updateResources();
        StackAnimationController stackAnimationController = bubbleStackView.mStackAnimationController;
        Objects.requireNonNull(stackAnimationController);
        PhysicsAnimationLayout physicsAnimationLayout = stackAnimationController.mLayout;
        if (physicsAnimationLayout != null) {
            stackAnimationController.mBubblePaddingTop = physicsAnimationLayout.getContext().getResources().getDimensionPixelSize(C1777R.dimen.bubble_padding_top);
        }
        bubbleStackView.mBubbleOverflow.updateResources();
        BubbleStackView.RelativeStackPosition relativeStackPosition = bubbleStackView.mRelativeStackPositionBeforeRotation;
        if (relativeStackPosition != null) {
            StackAnimationController stackAnimationController2 = bubbleStackView.mStackAnimationController;
            Objects.requireNonNull(stackAnimationController2);
            RectF allowableStackPositionRegion = stackAnimationController2.getAllowableStackPositionRegion();
            if (relativeStackPosition.mOnLeft) {
                f = allowableStackPositionRegion.left;
            } else {
                f = allowableStackPositionRegion.right;
            }
            stackAnimationController2.setStackPosition(new PointF(f, (allowableStackPositionRegion.height() * relativeStackPosition.mVerticalOffsetPercent) + allowableStackPositionRegion.top));
            bubbleStackView.mRelativeStackPositionBeforeRotation = null;
        }
        if (bubbleStackView.mIsExpanded) {
            bubbleStackView.mIsExpansionAnimating = true;
            bubbleStackView.hideFlyoutImmediate();
            bubbleStackView.updateExpandedBubble();
            bubbleStackView.updateExpandedView();
            bubbleStackView.updateOverflowVisibility();
            bubbleStackView.updatePointerPosition(false);
            bubbleStackView.mExpandedAnimationController.expandFromStack(new QSAnimator$$ExternalSyntheticLambda0(bubbleStackView, 6));
            BubblePositioner bubblePositioner = bubbleStackView.mPositioner;
            BubbleViewProvider bubbleViewProvider = bubbleStackView.mExpandedBubble;
            float expandedViewY = bubblePositioner.getExpandedViewY(bubbleViewProvider, (float) bubbleStackView.getBubbleIndex(bubbleViewProvider));
            bubbleStackView.mExpandedViewContainer.setTranslationX(0.0f);
            bubbleStackView.mExpandedViewContainer.setTranslationY(expandedViewY);
            bubbleStackView.mExpandedViewContainer.setAlpha(1.0f);
        }
        bubbleStackView.removeOnLayoutChangeListener(bubbleStackView.mOrientationChangedListener);
    }
}
