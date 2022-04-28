package com.android.p012wm.shell.pip.phone;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.TransitionDrawable;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.FrameLayout;
import androidx.dynamicanimation.animation.DynamicAnimation;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.animation.PhysicsAnimator;
import com.android.p012wm.shell.common.DismissCircleView;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.magnetictarget.MagnetizedObject;
import com.android.p012wm.shell.common.magnetictarget.MagnetizedObject$MagneticTarget$updateLocationOnScreen$1;
import com.android.p012wm.shell.pip.PipBoundsState;
import com.android.p012wm.shell.pip.PipUiEventLogger;
import com.android.p012wm.shell.pip.phone.PipMotionHelper;
import com.android.settingslib.wifi.AccessPoint$$ExternalSyntheticLambda0;
import com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda10;
import java.util.Objects;
import kotlin.jvm.functions.Function1;

/* renamed from: com.android.wm.shell.pip.phone.PipDismissTargetHandler */
public final class PipDismissTargetHandler implements ViewTreeObserver.OnPreDrawListener {
    public final Context mContext;
    public int mDismissAreaHeight;
    public boolean mEnableDismissDragToEdge;
    public float mMagneticFieldRadiusPercent = 1.0f;
    public MagnetizedObject.MagneticTarget mMagneticTarget;
    public PhysicsAnimator<View> mMagneticTargetAnimator;
    public PipMotionHelper.C19123 mMagnetizedPip;
    public final ShellExecutor mMainExecutor;
    public final PipMotionHelper mMotionHelper;
    public final PipUiEventLogger mPipUiEventLogger;
    public int mTargetSize;
    public final PhysicsAnimator.SpringConfig mTargetSpringConfig = new PhysicsAnimator.SpringConfig(200.0f, 0.75f);
    public DismissCircleView mTargetView;
    public FrameLayout mTargetViewContainer;
    public SurfaceControl mTaskLeash;
    public WindowInsets mWindowInsets;
    public final WindowManager mWindowManager;

    public final void createOrUpdateDismissTarget() {
        if (!this.mTargetViewContainer.isAttachedToWindow()) {
            this.mMagneticTargetAnimator.cancel();
            this.mTargetViewContainer.setVisibility(4);
            this.mTargetViewContainer.getViewTreeObserver().removeOnPreDrawListener(this);
            try {
                this.mWindowManager.addView(this.mTargetViewContainer, getDismissTargetLayoutParams());
            } catch (IllegalStateException unused) {
                this.mWindowManager.updateViewLayout(this.mTargetViewContainer, getDismissTargetLayoutParams());
            }
        } else {
            this.mWindowManager.updateViewLayout(this.mTargetViewContainer, getDismissTargetLayoutParams());
        }
    }

    public final WindowManager.LayoutParams getDismissTargetLayoutParams() {
        Point point = new Point();
        this.mWindowManager.getDefaultDisplay().getRealSize(point);
        int i = this.mDismissAreaHeight;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, i, 0, point.y - i, 2024, 280, -3);
        layoutParams.setTitle("pip-dismiss-overlay");
        layoutParams.privateFlags |= 16;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.setFitInsetsTypes(0);
        return layoutParams;
    }

    public final void hideDismissTargetMaybe() {
        if (this.mEnableDismissDragToEdge) {
            PhysicsAnimator<View> physicsAnimator = this.mMagneticTargetAnimator;
            PhysicsAnimator.SpringConfig springConfig = this.mTargetSpringConfig;
            Objects.requireNonNull(physicsAnimator);
            physicsAnimator.spring(DynamicAnimation.TRANSLATION_Y, (float) this.mTargetViewContainer.getHeight(), 0.0f, springConfig);
            physicsAnimator.withEndActions(new AccessPoint$$ExternalSyntheticLambda0(this, 11));
            physicsAnimator.start();
            ((TransitionDrawable) this.mTargetViewContainer.getBackground()).reverseTransition(200);
        }
    }

    public final void init() {
        Resources resources = this.mContext.getResources();
        this.mEnableDismissDragToEdge = resources.getBoolean(C1777R.bool.config_pipEnableDismissDragToEdge);
        this.mDismissAreaHeight = resources.getDimensionPixelSize(C1777R.dimen.floating_dismiss_gradient_height);
        FrameLayout frameLayout = this.mTargetViewContainer;
        if (frameLayout != null && frameLayout.isAttachedToWindow()) {
            this.mWindowManager.removeViewImmediate(this.mTargetViewContainer);
        }
        this.mTargetView = new DismissCircleView(this.mContext);
        FrameLayout frameLayout2 = new FrameLayout(this.mContext);
        this.mTargetViewContainer = frameLayout2;
        frameLayout2.setBackgroundDrawable(this.mContext.getDrawable(C1777R.C1778drawable.floating_dismiss_gradient_transition));
        this.mTargetViewContainer.setClipChildren(false);
        this.mTargetViewContainer.addView(this.mTargetView);
        this.mTargetViewContainer.setOnApplyWindowInsetsListener(new PipDismissTargetHandler$$ExternalSyntheticLambda0(this));
        PipMotionHelper pipMotionHelper = this.mMotionHelper;
        Objects.requireNonNull(pipMotionHelper);
        if (pipMotionHelper.mMagnetizedPip == null) {
            Context context = pipMotionHelper.mContext;
            PipBoundsState pipBoundsState = pipMotionHelper.mPipBoundsState;
            Objects.requireNonNull(pipBoundsState);
            PipBoundsState.MotionBoundsState motionBoundsState = pipBoundsState.mMotionBoundsState;
            Objects.requireNonNull(motionBoundsState);
            pipMotionHelper.mMagnetizedPip = new MagnetizedObject<Rect>(context, motionBoundsState.mBoundsInMotion) {
                public final float getHeight(Object obj) {
                    return (float) ((Rect) obj).height();
                }

                public final void getLocationOnScreen(Object obj, int[] iArr) {
                    Rect rect = (Rect) obj;
                    iArr[0] = rect.left;
                    iArr[1] = rect.top;
                }

                public final float getWidth(Object obj) {
                    return (float) ((Rect) obj).width();
                }
            };
        }
        PipMotionHelper.C19123 r0 = pipMotionHelper.mMagnetizedPip;
        this.mMagnetizedPip = r0;
        Objects.requireNonNull(r0);
        r0.associatedTargets.clear();
        PipMotionHelper.C19123 r02 = this.mMagnetizedPip;
        DismissCircleView dismissCircleView = this.mTargetView;
        Objects.requireNonNull(r02);
        MagnetizedObject.MagneticTarget magneticTarget = new MagnetizedObject.MagneticTarget(dismissCircleView, 0);
        r02.associatedTargets.add(magneticTarget);
        dismissCircleView.post(new MagnetizedObject$MagneticTarget$updateLocationOnScreen$1(magneticTarget));
        this.mMagneticTarget = magneticTarget;
        updateMagneticTargetSize();
        PipMotionHelper.C19123 r03 = this.mMagnetizedPip;
        PipDismissTargetHandler$$ExternalSyntheticLambda1 pipDismissTargetHandler$$ExternalSyntheticLambda1 = new PipDismissTargetHandler$$ExternalSyntheticLambda1(this);
        Objects.requireNonNull(r03);
        r03.animateStuckToTarget = pipDismissTargetHandler$$ExternalSyntheticLambda1;
        PipMotionHelper.C19123 r04 = this.mMagnetizedPip;
        C19051 r1 = new MagnetizedObject.MagnetListener() {
            public final void onReleasedInTarget() {
                PipDismissTargetHandler pipDismissTargetHandler = PipDismissTargetHandler.this;
                if (pipDismissTargetHandler.mEnableDismissDragToEdge) {
                    pipDismissTargetHandler.mMainExecutor.executeDelayed(new VolumeDialogImpl$$ExternalSyntheticLambda10(this, 8), 0);
                }
            }

            public final void onStuckToTarget() {
                PipDismissTargetHandler pipDismissTargetHandler = PipDismissTargetHandler.this;
                if (pipDismissTargetHandler.mEnableDismissDragToEdge) {
                    pipDismissTargetHandler.showDismissTargetMaybe();
                }
            }

            public final void onUnstuckFromTarget(float f, float f2, boolean z) {
                if (z) {
                    PipMotionHelper pipMotionHelper = PipDismissTargetHandler.this.mMotionHelper;
                    Objects.requireNonNull(pipMotionHelper);
                    pipMotionHelper.movetoTarget(f, f2, (Runnable) null, false);
                    PipDismissTargetHandler.this.hideDismissTargetMaybe();
                    return;
                }
                PipMotionHelper pipMotionHelper2 = PipDismissTargetHandler.this.mMotionHelper;
                Objects.requireNonNull(pipMotionHelper2);
                pipMotionHelper2.mSpringingToTouch = true;
            }
        };
        Objects.requireNonNull(r04);
        r04.magnetListener = r1;
        DismissCircleView dismissCircleView2 = this.mTargetView;
        Function1<Object, ? extends PhysicsAnimator<?>> function1 = PhysicsAnimator.instanceConstructor;
        this.mMagneticTargetAnimator = PhysicsAnimator.Companion.getInstance(dismissCircleView2);
    }

    public final boolean onPreDraw() {
        this.mTargetViewContainer.getViewTreeObserver().removeOnPreDrawListener(this);
        if (this.mTaskLeash == null) {
            return true;
        }
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        transaction.setRelativeLayer(this.mTargetViewContainer.getViewRootImpl().getSurfaceControl(), this.mTaskLeash, -1);
        transaction.apply();
        return true;
    }

    public final void showDismissTargetMaybe() {
        if (this.mEnableDismissDragToEdge) {
            createOrUpdateDismissTarget();
            if (this.mTargetViewContainer.getVisibility() != 0) {
                this.mTargetView.setTranslationY((float) this.mTargetViewContainer.getHeight());
                this.mTargetViewContainer.setVisibility(0);
                this.mTargetViewContainer.getViewTreeObserver().addOnPreDrawListener(this);
                this.mMagneticTargetAnimator.cancel();
                PhysicsAnimator<View> physicsAnimator = this.mMagneticTargetAnimator;
                DynamicAnimation.C01422 r1 = DynamicAnimation.TRANSLATION_Y;
                PhysicsAnimator.SpringConfig springConfig = this.mTargetSpringConfig;
                Objects.requireNonNull(physicsAnimator);
                physicsAnimator.spring(r1, 0.0f, 0.0f, springConfig);
                physicsAnimator.start();
                ((TransitionDrawable) this.mTargetViewContainer.getBackground()).startTransition(200);
            }
        }
    }

    public final void updateMagneticTargetSize() {
        if (this.mTargetView != null) {
            Resources resources = this.mContext.getResources();
            this.mTargetSize = resources.getDimensionPixelSize(C1777R.dimen.dismiss_circle_size);
            this.mDismissAreaHeight = resources.getDimensionPixelSize(C1777R.dimen.floating_dismiss_gradient_height);
            Insets insetsIgnoringVisibility = this.mWindowManager.getCurrentWindowMetrics().getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars());
            int i = this.mTargetSize;
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i, i);
            layoutParams.gravity = 81;
            layoutParams.bottomMargin = this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.floating_dismiss_bottom_margin) + insetsIgnoringVisibility.bottom;
            this.mTargetView.setLayoutParams(layoutParams);
            float f = this.mMagneticFieldRadiusPercent;
            this.mMagneticFieldRadiusPercent = f;
            MagnetizedObject.MagneticTarget magneticTarget = this.mMagneticTarget;
            Objects.requireNonNull(magneticTarget);
            magneticTarget.magneticFieldRadiusPx = (int) (f * ((float) this.mTargetSize) * 1.25f);
        }
    }

    public PipDismissTargetHandler(Context context, PipUiEventLogger pipUiEventLogger, PipMotionHelper pipMotionHelper, ShellExecutor shellExecutor) {
        this.mContext = context;
        this.mPipUiEventLogger = pipUiEventLogger;
        this.mMotionHelper = pipMotionHelper;
        this.mMainExecutor = shellExecutor;
        this.mWindowManager = (WindowManager) context.getSystemService("window");
    }
}
