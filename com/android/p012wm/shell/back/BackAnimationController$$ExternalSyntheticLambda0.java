package com.android.p012wm.shell.back;

import android.animation.ValueAnimator;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.MathUtils;
import android.view.Choreographer;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.View;
import android.view.animation.PathInterpolator;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.animation.Interpolators;
import com.android.p012wm.shell.startingsurface.SplashScreenExitAnimation;
import java.util.Objects;

/* renamed from: com.android.wm.shell.back.BackAnimationController$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BackAnimationController$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BackAnimationController$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        SurfaceControl surfaceControl;
        switch (this.$r8$classId) {
            case 0:
                BackAnimationController backAnimationController = (BackAnimationController) this.f$0;
                Objects.requireNonNull(backAnimationController);
                if (backAnimationController.mBackNavigationInfo != null) {
                    float animatedFraction = valueAnimator.getAnimatedFraction();
                    float f = (float) backAnimationController.mTouchEventDelta.x;
                    int round = Math.round(f - (f * animatedFraction));
                    float f2 = (float) backAnimationController.mTouchEventDelta.y;
                    int round2 = Math.round(f2 - (animatedFraction * f2));
                    RemoteAnimationTarget departingAnimationTarget = backAnimationController.mBackNavigationInfo.getDepartingAnimationTarget();
                    if (departingAnimationTarget != null) {
                        backAnimationController.mTransaction.setPosition(departingAnimationTarget.leash, (float) round, (float) round2);
                        backAnimationController.mTransaction.apply();
                        return;
                    }
                    return;
                }
                return;
            default:
                SplashScreenExitAnimation splashScreenExitAnimation = (SplashScreenExitAnimation) this.f$0;
                PathInterpolator pathInterpolator = SplashScreenExitAnimation.ICON_INTERPOLATOR;
                Objects.requireNonNull(splashScreenExitAnimation);
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                float interpolation = SplashScreenExitAnimation.ICON_INTERPOLATOR.getInterpolation(MathUtils.constrain(((((float) splashScreenExitAnimation.mAnimationDuration) * floatValue) - ((float) 0)) / ((float) ((long) splashScreenExitAnimation.mIconFadeOutDuration)), 0.0f, 1.0f));
                View iconView = splashScreenExitAnimation.mSplashScreenView.getIconView();
                View brandingView = splashScreenExitAnimation.mSplashScreenView.getBrandingView();
                if (iconView != null) {
                    iconView.setAlpha((1.0f - interpolation) * splashScreenExitAnimation.mIconStartAlpha);
                }
                if (brandingView != null) {
                    brandingView.setAlpha((1.0f - interpolation) * splashScreenExitAnimation.mBrandingStartAlpha);
                }
                float constrain = MathUtils.constrain(((floatValue * ((float) splashScreenExitAnimation.mAnimationDuration)) - ((float) ((long) splashScreenExitAnimation.mAppRevealDelay))) / ((float) ((long) splashScreenExitAnimation.mAppRevealDuration)), 0.0f, 1.0f);
                SplashScreenExitAnimation.RadialVanishAnimation radialVanishAnimation = splashScreenExitAnimation.mRadialVanishAnimation;
                if (!(radialVanishAnimation == null || radialVanishAnimation.mVanishPaint.getShader() == null)) {
                    float interpolation2 = SplashScreenExitAnimation.MASK_RADIUS_INTERPOLATOR.getInterpolation(constrain);
                    float interpolation3 = Interpolators.ALPHA_OUT.getInterpolation(constrain);
                    int i = radialVanishAnimation.mInitRadius;
                    float f3 = (((float) (radialVanishAnimation.mFinishRadius - i)) * interpolation2) + ((float) i);
                    radialVanishAnimation.mVanishMatrix.setScale(f3, f3);
                    Matrix matrix = radialVanishAnimation.mVanishMatrix;
                    Point point = radialVanishAnimation.mCircleCenter;
                    matrix.postTranslate((float) point.x, (float) point.y);
                    radialVanishAnimation.mVanishPaint.getShader().setLocalMatrix(radialVanishAnimation.mVanishMatrix);
                    radialVanishAnimation.mVanishPaint.setAlpha(Math.round(interpolation3 * 255.0f));
                    radialVanishAnimation.postInvalidate();
                }
                SplashScreenExitAnimation.ShiftUpAnimation shiftUpAnimation = splashScreenExitAnimation.mShiftUpAnimation;
                if (shiftUpAnimation != null && (surfaceControl = SplashScreenExitAnimation.this.mFirstWindowSurface) != null && surfaceControl.isValid() && SplashScreenExitAnimation.this.mSplashScreenView.isAttachedToWindow()) {
                    float interpolation4 = SplashScreenExitAnimation.SHIFT_UP_INTERPOLATOR.getInterpolation(constrain);
                    float f4 = shiftUpAnimation.mFromYDelta;
                    float m = MotionController$$ExternalSyntheticOutline0.m7m(shiftUpAnimation.mToYDelta, f4, interpolation4, f4);
                    shiftUpAnimation.mOccludeHoleView.setTranslationY(m);
                    shiftUpAnimation.mTmpTransform.setTranslate(0.0f, m);
                    SurfaceControl.Transaction acquire = SplashScreenExitAnimation.this.mTransactionPool.acquire();
                    acquire.setFrameTimelineVsync(Choreographer.getSfInstance().getVsyncId());
                    Matrix matrix2 = shiftUpAnimation.mTmpTransform;
                    SplashScreenExitAnimation splashScreenExitAnimation2 = SplashScreenExitAnimation.this;
                    Rect rect = splashScreenExitAnimation2.mFirstWindowFrame;
                    matrix2.postTranslate((float) rect.left, (float) (rect.top + splashScreenExitAnimation2.mMainWindowShiftLength));
                    SyncRtSurfaceTransactionApplier.SurfaceParams build = new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(SplashScreenExitAnimation.this.mFirstWindowSurface).withMatrix(shiftUpAnimation.mTmpTransform).withMergeTransaction(acquire).build();
                    shiftUpAnimation.mApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{build});
                    SplashScreenExitAnimation.this.mTransactionPool.release(acquire);
                    return;
                }
                return;
        }
    }
}
