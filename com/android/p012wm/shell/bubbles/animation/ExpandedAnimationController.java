package com.android.p012wm.shell.bubbles.animation;

import android.graphics.PointF;
import android.view.View;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda6;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda3;
import com.android.p012wm.shell.animation.PhysicsAnimator;
import com.android.p012wm.shell.bubbles.BubblePositioner;
import com.android.p012wm.shell.bubbles.BubbleStackView;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda16;
import com.android.p012wm.shell.bubbles.animation.PhysicsAnimationLayout;
import com.android.p012wm.shell.common.ExecutorUtils$$ExternalSyntheticLambda1;
import com.android.p012wm.shell.onehanded.OneHandedDisplayAreaOrganizer$$ExternalSyntheticLambda0;
import com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda0;
import com.google.android.collect.Sets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import kotlin.jvm.functions.Function1;

/* renamed from: com.android.wm.shell.bubbles.animation.ExpandedAnimationController */
public final class ExpandedAnimationController extends PhysicsAnimationLayout.PhysicsAnimationController {
    public Runnable mAfterCollapse;
    public Runnable mAfterExpand;
    public final PhysicsAnimator.SpringConfig mAnimateOutSpringConfig = new PhysicsAnimator.SpringConfig(1000.0f, 1.0f);
    public boolean mAnimatingCollapse = false;
    public boolean mAnimatingExpand = false;
    public boolean mBubbleDraggedOutEnough = false;
    public float mBubbleSizePx;
    public BubbleStackView mBubbleStackView;
    public PointF mCollapsePoint;
    public Runnable mLeadBubbleEndAction;
    public C18251 mMagnetizedBubbleDraggingOut;
    public Runnable mOnBubbleAnimatedOutAction;
    public BubblePositioner mPositioner;
    public boolean mPreparingToCollapse = false;
    public boolean mSpringToTouchOnNextMotionEvent = false;
    public boolean mSpringingBubbleToTouch = false;
    public float mStackOffsetPx;

    public final void expandFromStack(Runnable runnable) {
        this.mPreparingToCollapse = false;
        this.mAnimatingCollapse = false;
        this.mAnimatingExpand = true;
        this.mAfterExpand = runnable;
        this.mLeadBubbleEndAction = null;
        startOrUpdatePathAnimation(true);
    }

    public final HashSet getAnimatedProperties() {
        return Sets.newHashSet(new DynamicAnimation.ViewProperty[]{DynamicAnimation.TRANSLATION_X, DynamicAnimation.TRANSLATION_Y, DynamicAnimation.SCALE_X, DynamicAnimation.SCALE_Y, DynamicAnimation.ALPHA});
    }

    public final int getNextAnimationInChain(DynamicAnimation.ViewProperty viewProperty, int i) {
        return -1;
    }

    public final float getOffsetForChainedPropertyAnimation(DynamicAnimation.ViewProperty viewProperty, int i) {
        return 0.0f;
    }

    public final View getDraggedOutBubble() {
        C18251 r0 = this.mMagnetizedBubbleDraggingOut;
        if (r0 == null) {
            return null;
        }
        return (View) r0.underlyingObject;
    }

    public final SpringForce getSpringForce() {
        SpringForce springForce = new SpringForce();
        springForce.setDampingRatio(0.65f);
        springForce.setStiffness(200.0f);
        return springForce;
    }

    public final void onChildAdded(View view, int i) {
        float f;
        boolean z = true;
        if (this.mAnimatingExpand) {
            startOrUpdatePathAnimation(true);
        } else if (this.mAnimatingCollapse) {
            startOrUpdatePathAnimation(false);
        } else {
            BubblePositioner bubblePositioner = this.mPositioner;
            PointF pointF = this.mCollapsePoint;
            if (pointF == null) {
                pointF = bubblePositioner.getRestingPosition();
            } else {
                Objects.requireNonNull(bubblePositioner);
            }
            if ((bubblePositioner.mBubbleSize / 2) + ((int) pointF.x) >= bubblePositioner.mScreenRect.width() / 2) {
                z = false;
            }
            PointF expandedBubbleXY = this.mPositioner.getExpandedBubbleXY(i, this.mBubbleStackView.getState());
            if (this.mPositioner.showBubblesVertically()) {
                view.setTranslationY(expandedBubbleXY.y);
            } else {
                view.setTranslationX(expandedBubbleXY.x);
            }
            if (!this.mPreparingToCollapse) {
                if (this.mPositioner.showBubblesVertically()) {
                    if (z) {
                        f = expandedBubbleXY.x - (this.mBubbleSizePx * 4.0f);
                    } else {
                        f = expandedBubbleXY.x + (this.mBubbleSizePx * 4.0f);
                    }
                    PhysicsAnimationLayout.PhysicsPropertyAnimator animationForChild = animationForChild(view);
                    HashMap hashMap = animationForChild.mInitialPropertyValues;
                    DynamicAnimation.C01371 r4 = DynamicAnimation.TRANSLATION_X;
                    hashMap.put(r4, Float.valueOf(f));
                    animationForChild.mPathAnimator = null;
                    animationForChild.property(r4, expandedBubbleXY.y, new Runnable[0]);
                    animationForChild.start(new Runnable[0]);
                } else {
                    float f2 = expandedBubbleXY.y - (this.mBubbleSizePx * 4.0f);
                    PhysicsAnimationLayout.PhysicsPropertyAnimator animationForChild2 = animationForChild(view);
                    animationForChild2.mInitialPropertyValues.put(DynamicAnimation.TRANSLATION_Y, Float.valueOf(f2));
                    animationForChild2.translationY(expandedBubbleXY.y, new Runnable[0]);
                    animationForChild2.start(new Runnable[0]);
                }
                updateBubblePositions();
            }
        }
    }

    public final void onChildReordered() {
        if (!this.mPreparingToCollapse) {
            if (this.mAnimatingCollapse) {
                startOrUpdatePathAnimation(false);
            } else {
                updateBubblePositions();
            }
        }
    }

    public final void snapBubbleBack(View view, float f, float f2) {
        PhysicsAnimationLayout physicsAnimationLayout = this.mLayout;
        if (physicsAnimationLayout != null) {
            int indexOfChild = physicsAnimationLayout.indexOfChild(view);
            PointF expandedBubbleXY = this.mPositioner.getExpandedBubbleXY(indexOfChild, this.mBubbleStackView.getState());
            PhysicsAnimationLayout.PhysicsPropertyAnimator animationForChild = animationForChild(this.mLayout.getChildAt(indexOfChild));
            float f3 = expandedBubbleXY.x;
            float f4 = expandedBubbleXY.y;
            animationForChild.mPositionEndActions = new Runnable[0];
            animationForChild.mPathAnimator = null;
            DynamicAnimation.C01371 r6 = DynamicAnimation.TRANSLATION_X;
            animationForChild.property(r6, f3, new Runnable[0]);
            animationForChild.translationY(f4, new Runnable[0]);
            animationForChild.mPositionStartVelocities.put(r6, Float.valueOf(f));
            animationForChild.mPositionStartVelocities.put(DynamicAnimation.TRANSLATION_Y, Float.valueOf(f2));
            animationForChild.start(new TaskView$$ExternalSyntheticLambda3(view, 7));
            this.mMagnetizedBubbleDraggingOut = null;
            updateBubblePositions();
        }
    }

    public final void startOrUpdatePathAnimation(boolean z) {
        Runnable runnable;
        if (z) {
            runnable = new ScrimView$$ExternalSyntheticLambda0(this, 5);
        } else {
            runnable = new KeyguardUpdateMonitor$$ExternalSyntheticLambda6(this, 9);
        }
        animationsForChildrenFromIndex(new ExpandedAnimationController$$ExternalSyntheticLambda0(this, z)).startAll(new Runnable[]{runnable});
    }

    public final void updateBubblePositions() {
        if (!this.mAnimatingExpand && !this.mAnimatingCollapse) {
            int i = 0;
            while (i < this.mLayout.getChildCount()) {
                View childAt = this.mLayout.getChildAt(i);
                if (!childAt.equals(getDraggedOutBubble())) {
                    PointF expandedBubbleXY = this.mPositioner.getExpandedBubbleXY(i, this.mBubbleStackView.getState());
                    PhysicsAnimationLayout.PhysicsPropertyAnimator animationForChild = animationForChild(childAt);
                    animationForChild.mPathAnimator = null;
                    animationForChild.property(DynamicAnimation.TRANSLATION_X, expandedBubbleXY.x, new Runnable[0]);
                    animationForChild.translationY(expandedBubbleXY.y, new Runnable[0]);
                    animationForChild.start(new Runnable[0]);
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    public final void updateResources() {
        PhysicsAnimationLayout physicsAnimationLayout = this.mLayout;
        if (physicsAnimationLayout != null) {
            this.mStackOffsetPx = (float) physicsAnimationLayout.getContext().getResources().getDimensionPixelSize(C1777R.dimen.bubble_stack_offset);
            BubblePositioner bubblePositioner = this.mPositioner;
            Objects.requireNonNull(bubblePositioner);
            this.mBubbleSizePx = (float) bubblePositioner.mBubbleSize;
        }
    }

    public ExpandedAnimationController(BubblePositioner bubblePositioner, BubbleStackView$$ExternalSyntheticLambda16 bubbleStackView$$ExternalSyntheticLambda16, BubbleStackView bubbleStackView) {
        this.mPositioner = bubblePositioner;
        updateResources();
        this.mOnBubbleAnimatedOutAction = bubbleStackView$$ExternalSyntheticLambda16;
        this.mCollapsePoint = this.mPositioner.getDefaultStartPosition();
        this.mBubbleStackView = bubbleStackView;
    }

    public final void onActiveControllerForLayout(PhysicsAnimationLayout physicsAnimationLayout) {
        updateResources();
        this.mLayout.setVisibility(0);
        animationsForChildrenFromIndex(OneHandedDisplayAreaOrganizer$$ExternalSyntheticLambda0.INSTANCE$1).startAll(new Runnable[0]);
    }

    public final void onChildRemoved(View view, ExecutorUtils$$ExternalSyntheticLambda1 executorUtils$$ExternalSyntheticLambda1) {
        if (view.equals(getDraggedOutBubble())) {
            this.mMagnetizedBubbleDraggingOut = null;
            executorUtils$$ExternalSyntheticLambda1.run();
            this.mOnBubbleAnimatedOutAction.run();
        } else {
            Function1<Object, ? extends PhysicsAnimator<?>> function1 = PhysicsAnimator.instanceConstructor;
            PhysicsAnimator instance = PhysicsAnimator.Companion.getInstance(view);
            instance.spring(DynamicAnimation.ALPHA, 0.0f);
            instance.spring(DynamicAnimation.SCALE_X, 0.0f, 0.0f, this.mAnimateOutSpringConfig);
            instance.spring(DynamicAnimation.SCALE_Y, 0.0f, 0.0f, this.mAnimateOutSpringConfig);
            instance.withEndActions(executorUtils$$ExternalSyntheticLambda1, this.mOnBubbleAnimatedOutAction);
            instance.start();
        }
        updateBubblePositions();
    }
}
