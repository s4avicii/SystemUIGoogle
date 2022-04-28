package com.android.p012wm.shell.onehanded;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.onehanded.OneHandedAnimationController;
import com.android.p012wm.shell.onehanded.OneHandedState;
import java.util.Objects;

/* renamed from: com.android.wm.shell.onehanded.OneHandedTutorialHandler */
public final class OneHandedTutorialHandler implements OneHandedTransitionCallback, OneHandedState.OnStateChangedListener, OneHandedAnimationCallback {
    public int mAlphaAnimationDurationMs;
    public ValueAnimator mAlphaAnimator;
    public float mAlphaTransitionStart;
    public final BackgroundWindowManager mBackgroundWindowManager;
    public Context mContext;
    public int mCurrentState;
    public Rect mDisplayBounds;
    public FrameLayout mTargetViewContainer;
    public int mTutorialAreaHeight;
    public final float mTutorialHeightRatio;
    public View mTutorialView;
    public final WindowManager mWindowManager;

    public final void setupAlphaTransition(boolean z) {
        float f;
        int i;
        float f2 = 0.0f;
        if (z) {
            f = 0.0f;
        } else {
            f = 1.0f;
        }
        if (z) {
            f2 = 1.0f;
        }
        if (z) {
            i = this.mAlphaAnimationDurationMs;
        } else {
            i = Math.round((1.0f - this.mTutorialHeightRatio) * ((float) this.mAlphaAnimationDurationMs));
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{f, f2});
        this.mAlphaAnimator = ofFloat;
        ofFloat.setInterpolator(new LinearInterpolator());
        this.mAlphaAnimator.setDuration((long) i);
        this.mAlphaAnimator.addUpdateListener(new OneHandedTutorialHandler$$ExternalSyntheticLambda0(this));
    }

    public final void checkTransitionEnd() {
        ValueAnimator valueAnimator = this.mAlphaAnimator;
        if (valueAnimator == null) {
            return;
        }
        if (valueAnimator.isRunning() || this.mAlphaAnimator.isStarted()) {
            this.mAlphaAnimator.end();
            this.mAlphaAnimator.removeAllUpdateListeners();
            this.mAlphaAnimator = null;
        }
    }

    public final WindowManager.LayoutParams getTutorialTargetLayoutParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(this.mDisplayBounds.width(), this.mTutorialAreaHeight, 0, 0, 2024, 264, -3);
        layoutParams.gravity = 51;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.privateFlags |= 16;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTitle("one-handed-tutorial-overlay");
        return layoutParams;
    }

    @VisibleForTesting
    public boolean isAttached() {
        FrameLayout frameLayout = this.mTargetViewContainer;
        if (frameLayout == null || !frameLayout.isAttachedToWindow()) {
            return false;
        }
        return true;
    }

    public final void onOneHandedAnimationCancel(OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimator) {
        ValueAnimator valueAnimator = this.mAlphaAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }

    public final void onStartFinished(Rect rect) {
        BackgroundWindowManager backgroundWindowManager;
        FrameLayout frameLayout = this.mTargetViewContainer;
        if (frameLayout != null && (backgroundWindowManager = this.mBackgroundWindowManager) != null) {
            frameLayout.setBackgroundColor(backgroundWindowManager.getThemeColorForBackground());
        }
    }

    public final void onStateChanged(int i) {
        this.mCurrentState = i;
        BackgroundWindowManager backgroundWindowManager = this.mBackgroundWindowManager;
        Objects.requireNonNull(backgroundWindowManager);
        backgroundWindowManager.mCurrentState = i;
        if (i != 0) {
            if (i == 1) {
                createViewAndAttachToWindow(this.mContext);
                updateThemeColor();
                setupAlphaTransition(true);
                return;
            } else if (i == 2) {
                checkTransitionEnd();
                setupAlphaTransition(false);
                return;
            } else if (i != 3) {
                return;
            }
        }
        checkTransitionEnd();
        removeTutorialFromWindowManager();
    }

    @VisibleForTesting
    public void removeBackgroundSurface() {
        BackgroundWindowManager backgroundWindowManager = this.mBackgroundWindowManager;
        Objects.requireNonNull(backgroundWindowManager);
        if (backgroundWindowManager.mBackgroundView != null) {
            backgroundWindowManager.mBackgroundView = null;
        }
        SurfaceControlViewHost surfaceControlViewHost = backgroundWindowManager.mViewHost;
        if (surfaceControlViewHost != null) {
            surfaceControlViewHost.release();
            backgroundWindowManager.mViewHost = null;
        }
        if (backgroundWindowManager.mLeash != null) {
            Objects.requireNonNull(backgroundWindowManager.mTransactionFactory);
            new SurfaceControl.Transaction().remove(backgroundWindowManager.mLeash).apply();
            backgroundWindowManager.mLeash = null;
        }
    }

    public final void updateThemeColor() {
        if (this.mTutorialView != null) {
            TypedArray obtainStyledAttributes = new ContextThemeWrapper(this.mTutorialView.getContext(), 16974563).obtainStyledAttributes(new int[]{16842806, 16842808});
            int color = obtainStyledAttributes.getColor(0, 0);
            int color2 = obtainStyledAttributes.getColor(1, 0);
            obtainStyledAttributes.recycle();
            ((ImageView) this.mTutorialView.findViewById(C1777R.C1779id.one_handed_tutorial_image)).setImageTintList(ColorStateList.valueOf(color));
            ((TextView) this.mTutorialView.findViewById(C1777R.C1779id.one_handed_tutorial_title)).setTextColor(color);
            ((TextView) this.mTutorialView.findViewById(C1777R.C1779id.one_handed_tutorial_description)).setTextColor(color2);
        }
    }

    public OneHandedTutorialHandler(Context context, OneHandedSettingsUtil oneHandedSettingsUtil, WindowManager windowManager, BackgroundWindowManager backgroundWindowManager) {
        this.mContext = context;
        this.mWindowManager = windowManager;
        this.mBackgroundWindowManager = backgroundWindowManager;
        this.mTutorialHeightRatio = context.getResources().getFraction(C1777R.fraction.config_one_handed_offset, 1, 1);
        this.mAlphaAnimationDurationMs = context.getResources().getInteger(C1777R.integer.config_one_handed_translate_animation_duration);
    }

    @VisibleForTesting
    public void createViewAndAttachToWindow(Context context) {
        float f;
        if (!isAttached()) {
            this.mTutorialView = LayoutInflater.from(context).inflate(C1777R.layout.one_handed_tutorial, (ViewGroup) null);
            FrameLayout frameLayout = new FrameLayout(context);
            this.mTargetViewContainer = frameLayout;
            frameLayout.setClipChildren(false);
            FrameLayout frameLayout2 = this.mTargetViewContainer;
            if (this.mCurrentState == 2) {
                f = 1.0f;
            } else {
                f = 0.0f;
            }
            frameLayout2.setAlpha(f);
            this.mTargetViewContainer.addView(this.mTutorialView);
            this.mTargetViewContainer.setLayerType(2, (Paint) null);
            try {
                this.mWindowManager.addView(this.mTargetViewContainer, getTutorialTargetLayoutParams());
                this.mBackgroundWindowManager.showBackgroundLayer();
            } catch (IllegalStateException unused) {
                this.mWindowManager.updateViewLayout(this.mTargetViewContainer, getTutorialTargetLayoutParams());
            }
        }
    }

    public final void onAnimationUpdate(float f) {
        if (isAttached()) {
            if (f < this.mAlphaTransitionStart) {
                checkTransitionEnd();
                return;
            }
            ValueAnimator valueAnimator = this.mAlphaAnimator;
            if (valueAnimator != null && !valueAnimator.isStarted() && !this.mAlphaAnimator.isRunning()) {
                this.mAlphaAnimator.start();
            }
        }
    }

    @VisibleForTesting
    public void removeTutorialFromWindowManager() {
        if (isAttached()) {
            this.mTargetViewContainer.setLayerType(0, (Paint) null);
            this.mWindowManager.removeViewImmediate(this.mTargetViewContainer);
            this.mTargetViewContainer = null;
        }
    }

    public final void onStopFinished(Rect rect) {
        removeBackgroundSurface();
    }
}
