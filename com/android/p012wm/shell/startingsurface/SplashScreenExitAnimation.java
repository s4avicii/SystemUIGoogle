package com.android.p012wm.shell.startingsurface;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Choreographer;
import android.view.SurfaceControl;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.window.SplashScreenView;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda3;
import com.android.p012wm.shell.common.TransactionPool;
import java.util.Objects;

/* renamed from: com.android.wm.shell.startingsurface.SplashScreenExitAnimation */
public final class SplashScreenExitAnimation implements Animator.AnimatorListener {
    public static final PathInterpolator ICON_INTERPOLATOR = new PathInterpolator(0.15f, 0.0f, 1.0f, 1.0f);
    public static final PathInterpolator MASK_RADIUS_INTERPOLATOR = new PathInterpolator(0.0f, 0.0f, 0.4f, 1.0f);
    public static final PathInterpolator SHIFT_UP_INTERPOLATOR = new PathInterpolator(0.0f, 0.0f, 0.0f, 1.0f);
    public final int mAnimationDuration;
    public final int mAppRevealDelay;
    public final int mAppRevealDuration;
    public final float mBrandingStartAlpha;
    public Runnable mFinishCallback;
    public final Rect mFirstWindowFrame;
    public final SurfaceControl mFirstWindowSurface;
    public final int mIconFadeOutDuration;
    public final float mIconStartAlpha;
    public final int mMainWindowShiftLength;
    public RadialVanishAnimation mRadialVanishAnimation;
    public ShiftUpAnimation mShiftUpAnimation;
    public final SplashScreenView mSplashScreenView;
    public final TransactionPool mTransactionPool;

    /* renamed from: com.android.wm.shell.startingsurface.SplashScreenExitAnimation$ShiftUpAnimation */
    public final class ShiftUpAnimation {
        public final SyncRtSurfaceTransactionApplier mApplier;
        public final float mFromYDelta = 0.0f;
        public final View mOccludeHoleView;
        public final Matrix mTmpTransform = new Matrix();
        public final float mToYDelta;

        public ShiftUpAnimation(float f, View view) {
            this.mToYDelta = f;
            this.mOccludeHoleView = view;
            this.mApplier = new SyncRtSurfaceTransactionApplier(view);
        }
    }

    public final void onAnimationRepeat(Animator animator) {
    }

    /* renamed from: com.android.wm.shell.startingsurface.SplashScreenExitAnimation$RadialVanishAnimation */
    public static class RadialVanishAnimation extends View {
        public final Point mCircleCenter = new Point();
        public int mFinishRadius;
        public int mInitRadius;
        public final Matrix mVanishMatrix = new Matrix();
        public final Paint mVanishPaint;
        public final SplashScreenView mView;

        public RadialVanishAnimation(SplashScreenView splashScreenView) {
            super(splashScreenView.getContext());
            Paint paint = new Paint(1);
            this.mVanishPaint = paint;
            this.mView = splashScreenView;
            splashScreenView.addView(this);
            paint.setAlpha(0);
        }

        public final void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawRect(0.0f, 0.0f, (float) this.mView.getWidth(), (float) this.mView.getHeight(), this.mVanishPaint);
        }
    }

    public final void reset() {
        if (this.mSplashScreenView.isAttachedToWindow()) {
            this.mSplashScreenView.setVisibility(8);
            Runnable runnable = this.mFinishCallback;
            if (runnable != null) {
                runnable.run();
                this.mFinishCallback = null;
            }
        }
        ShiftUpAnimation shiftUpAnimation = this.mShiftUpAnimation;
        if (shiftUpAnimation != null) {
            Objects.requireNonNull(shiftUpAnimation);
            SurfaceControl surfaceControl = SplashScreenExitAnimation.this.mFirstWindowSurface;
            if (surfaceControl != null && surfaceControl.isValid()) {
                SurfaceControl.Transaction acquire = SplashScreenExitAnimation.this.mTransactionPool.acquire();
                if (SplashScreenExitAnimation.this.mSplashScreenView.isAttachedToWindow()) {
                    acquire.setFrameTimelineVsync(Choreographer.getSfInstance().getVsyncId());
                    SyncRtSurfaceTransactionApplier.SurfaceParams build = new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(SplashScreenExitAnimation.this.mFirstWindowSurface).withWindowCrop((Rect) null).withMergeTransaction(acquire).build();
                    shiftUpAnimation.mApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{build});
                } else {
                    acquire.setWindowCrop(SplashScreenExitAnimation.this.mFirstWindowSurface, (Rect) null);
                    acquire.apply();
                }
                SplashScreenExitAnimation.this.mTransactionPool.release(acquire);
                Choreographer sfInstance = Choreographer.getSfInstance();
                SurfaceControl surfaceControl2 = SplashScreenExitAnimation.this.mFirstWindowSurface;
                Objects.requireNonNull(surfaceControl2);
                sfInstance.postCallback(4, new TaskView$$ExternalSyntheticLambda3(surfaceControl2, 10), (Object) null);
            }
        }
    }

    public SplashScreenExitAnimation(Context context, SplashScreenView splashScreenView, SurfaceControl surfaceControl, Rect rect, int i, TransactionPool transactionPool, Runnable runnable) {
        Rect rect2 = new Rect();
        this.mFirstWindowFrame = rect2;
        this.mSplashScreenView = splashScreenView;
        this.mFirstWindowSurface = surfaceControl;
        if (rect != null) {
            rect2.set(rect);
        }
        View iconView = splashScreenView.getIconView();
        if (iconView == null || iconView.getLayoutParams().width == 0 || iconView.getLayoutParams().height == 0) {
            this.mIconFadeOutDuration = 0;
            this.mIconStartAlpha = 0.0f;
            this.mBrandingStartAlpha = 0.0f;
            this.mAppRevealDelay = 0;
        } else {
            iconView.setLayerType(2, (Paint) null);
            View brandingView = splashScreenView.getBrandingView();
            if (brandingView != null) {
                this.mBrandingStartAlpha = brandingView.getAlpha();
            } else {
                this.mBrandingStartAlpha = 0.0f;
            }
            this.mIconFadeOutDuration = context.getResources().getInteger(C1777R.integer.starting_window_app_reveal_icon_fade_out_duration);
            this.mAppRevealDelay = context.getResources().getInteger(C1777R.integer.starting_window_app_reveal_anim_delay);
            this.mIconStartAlpha = iconView.getAlpha();
        }
        int integer = context.getResources().getInteger(C1777R.integer.starting_window_app_reveal_anim_duration);
        this.mAppRevealDuration = integer;
        this.mAnimationDuration = Math.max(this.mIconFadeOutDuration, this.mAppRevealDelay + integer);
        this.mMainWindowShiftLength = i;
        this.mFinishCallback = runnable;
        this.mTransactionPool = transactionPool;
    }

    public final void onAnimationCancel(Animator animator) {
        reset();
        InteractionJankMonitor.getInstance().cancel(39);
    }

    public final void onAnimationEnd(Animator animator) {
        reset();
        InteractionJankMonitor.getInstance().end(39);
    }

    public final void onAnimationStart(Animator animator) {
        InteractionJankMonitor.getInstance().begin(this.mSplashScreenView, 39);
    }
}
