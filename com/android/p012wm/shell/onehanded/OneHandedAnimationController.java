package com.android.p012wm.shell.onehanded;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.view.SurfaceControl;
import android.view.animation.BaseInterpolator;
import android.window.WindowContainerToken;
import androidx.core.view.ViewCompat$$ExternalSyntheticLambda0;
import com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda4;
import com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda5;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/* renamed from: com.android.wm.shell.onehanded.OneHandedAnimationController */
public final class OneHandedAnimationController {
    public final HashMap<WindowContainerToken, OneHandedTransitionAnimator> mAnimatorMap = new HashMap<>();
    public final OneHandedInterpolator mInterpolator;
    public final OneHandedSurfaceTransactionHelper mSurfaceTransactionHelper;

    /* renamed from: com.android.wm.shell.onehanded.OneHandedAnimationController$OneHandedInterpolator */
    public class OneHandedInterpolator extends BaseInterpolator {
        public final float getInterpolation(float f) {
            return (float) ((Math.sin((((double) ((f - 4.0f) / 4.0f)) * 6.283185307179586d) / 4.0d) * Math.pow(2.0d, (double) (-10.0f * f))) + 1.0d);
        }
    }

    /* renamed from: com.android.wm.shell.onehanded.OneHandedAnimationController$OneHandedTransitionAnimator */
    public static abstract class OneHandedTransitionAnimator extends ValueAnimator implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
        public static final /* synthetic */ int $r8$clinit = 0;
        public float mCurrentValue;
        public float mEndValue;
        public final SurfaceControl mLeash;
        public final ArrayList mOneHandedAnimationCallbacks = new ArrayList();
        public float mStartValue;
        public ViewCompat$$ExternalSyntheticLambda0 mSurfaceControlTransactionFactory;
        public OneHandedSurfaceTransactionHelper mSurfaceTransactionHelper;
        public final WindowContainerToken mToken;
        public int mTransitionDirection;

        public abstract void applySurfaceControlTransaction(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, float f);

        public final void onAnimationRepeat(Animator animator) {
        }

        public static OneHandedTransitionAnimator ofYOffset(WindowContainerToken windowContainerToken, SurfaceControl surfaceControl, float f, float f2, Rect rect) {
            return new OneHandedTransitionAnimator(windowContainerToken, surfaceControl, f, f2, rect) {
                public final Rect mTmpRect;

                public final void applySurfaceControlTransaction(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, float f) {
                    float f2 = this.mStartValue;
                    float f3 = (this.mEndValue * f) + ((1.0f - f) * f2) + 0.5f;
                    Rect rect = this.mTmpRect;
                    int i = rect.left;
                    int round = Math.round(f3) + rect.top;
                    Rect rect2 = this.mTmpRect;
                    rect.set(i, round, rect2.right, Math.round(f3) + rect2.bottom);
                    this.mCurrentValue = f3;
                    OneHandedSurfaceTransactionHelper oneHandedSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
                    Rect rect3 = this.mTmpRect;
                    Objects.requireNonNull(oneHandedSurfaceTransactionHelper);
                    transaction.setWindowCrop(surfaceControl, rect3.width(), rect3.height());
                    if (oneHandedSurfaceTransactionHelper.mEnableCornerRadius) {
                        transaction.setCornerRadius(surfaceControl, oneHandedSurfaceTransactionHelper.mCornerRadius);
                    }
                    transaction.setPosition(surfaceControl, 0.0f, f3);
                    transaction.apply();
                }

                {
                    this.mTmpRect = new Rect(r5);
                }
            };
        }

        public final void onAnimationCancel(Animator animator) {
            this.mCurrentValue = this.mEndValue;
            this.mOneHandedAnimationCallbacks.forEach(new WMShell$$ExternalSyntheticLambda5(this, 3));
            this.mOneHandedAnimationCallbacks.clear();
        }

        public final void onAnimationEnd(Animator animator) {
            this.mCurrentValue = this.mEndValue;
            Objects.requireNonNull(this.mSurfaceControlTransactionFactory);
            this.mOneHandedAnimationCallbacks.forEach(new C1874x55a9da47(this, new SurfaceControl.Transaction()));
            this.mOneHandedAnimationCallbacks.clear();
        }

        public final void onAnimationStart(Animator animator) {
            this.mCurrentValue = this.mStartValue;
            this.mOneHandedAnimationCallbacks.forEach(new WMShell$$ExternalSyntheticLambda4(this, 2));
        }

        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            Objects.requireNonNull(this.mSurfaceControlTransactionFactory);
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            this.mOneHandedAnimationCallbacks.forEach(new C1875x55a9da48(this, transaction));
            applySurfaceControlTransaction(this.mLeash, transaction, valueAnimator.getAnimatedFraction());
        }

        public OneHandedTransitionAnimator(WindowContainerToken windowContainerToken, SurfaceControl surfaceControl, float f, float f2) {
            this.mLeash = surfaceControl;
            this.mToken = windowContainerToken;
            this.mStartValue = f;
            this.mEndValue = f2;
            addListener(this);
            addUpdateListener(this);
            this.mSurfaceControlTransactionFactory = ViewCompat$$ExternalSyntheticLambda0.INSTANCE;
            this.mTransitionDirection = 0;
        }
    }

    public final OneHandedTransitionAnimator setupOneHandedTransitionAnimator(OneHandedTransitionAnimator oneHandedTransitionAnimator) {
        OneHandedSurfaceTransactionHelper oneHandedSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
        Objects.requireNonNull(oneHandedTransitionAnimator);
        oneHandedTransitionAnimator.mSurfaceTransactionHelper = oneHandedSurfaceTransactionHelper;
        oneHandedTransitionAnimator.setInterpolator(this.mInterpolator);
        oneHandedTransitionAnimator.setFloatValues(new float[]{0.0f, 1.0f});
        return oneHandedTransitionAnimator;
    }

    public OneHandedAnimationController(Context context) {
        this.mSurfaceTransactionHelper = new OneHandedSurfaceTransactionHelper(context);
        this.mInterpolator = new OneHandedInterpolator();
    }
}
