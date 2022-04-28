package com.android.p012wm.shell.bubbles;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import android.view.Choreographer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.internal.util.ContrastColorUtil;
import com.android.internal.util.FrameworkStatsLog;
import com.android.keyguard.KeyguardStatusView$$ExternalSyntheticLambda0;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda8;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.TaskView;
import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda5;
import com.android.p012wm.shell.animation.Interpolators;
import com.android.p012wm.shell.animation.PhysicsAnimator;
import com.android.p012wm.shell.bubbles.BadgedImageView;
import com.android.p012wm.shell.bubbles.Bubble;
import com.android.p012wm.shell.bubbles.BubbleLogger;
import com.android.p012wm.shell.bubbles.BubbleOverflowContainerView;
import com.android.p012wm.shell.bubbles.Bubbles;
import com.android.p012wm.shell.bubbles.animation.AnimatableScaleMatrix;
import com.android.p012wm.shell.bubbles.animation.ExpandedAnimationController;
import com.android.p012wm.shell.bubbles.animation.PhysicsAnimationLayout;
import com.android.p012wm.shell.bubbles.animation.StackAnimationController;
import com.android.p012wm.shell.bubbles.animation.StackAnimationController$$ExternalSyntheticLambda2;
import com.android.p012wm.shell.common.DismissCircleView;
import com.android.p012wm.shell.common.ExecutorUtils$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.common.ExecutorUtils$$ExternalSyntheticLambda1;
import com.android.p012wm.shell.common.FloatingContentCoordinator;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.magnetictarget.MagnetizedObject;
import com.android.p012wm.shell.common.magnetictarget.MagnetizedObject$MagneticTarget$updateLocationOnScreen$1;
import com.android.p012wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda3;
import com.android.p012wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda5;
import com.android.systemui.ImageWallpaper$GLEngine$$ExternalSyntheticLambda0;
import com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda3;
import com.android.systemui.media.MediaControlPanel$$ExternalSyntheticLambda3;
import com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda13;
import com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda2;
import com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda10;
import com.android.systemui.wmshell.BubblesManager;
import com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda5;
import com.android.systemui.wmshell.WMShell$7$$ExternalSyntheticLambda0;
import com.android.systemui.wmshell.WMShell$8$$ExternalSyntheticLambda0;
import com.android.wifitrackerlib.WifiEntry$$ExternalSyntheticLambda1;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function1;

/* renamed from: com.android.wm.shell.bubbles.BubbleStackView */
public final class BubbleStackView extends FrameLayout implements ViewTreeObserver.OnComputeInternalInsetsListener {
    public static final C18031 DEFAULT_SURFACE_SYNCHRONIZER = new SurfaceSynchronizer() {
    };
    @VisibleForTesting
    public static final int FLYOUT_HIDE_AFTER = 5000;
    public static final PhysicsAnimator.SpringConfig FLYOUT_IME_ANIMATION_SPRING_CONFIG = new PhysicsAnimator.SpringConfig(200.0f, 0.9f);
    public ExecutorUtils$$ExternalSyntheticLambda0 mAfterFlyoutHidden;
    public final BubbleStackView$$ExternalSyntheticLambda9 mAfterFlyoutTransitionSpring;
    public WifiEntry$$ExternalSyntheticLambda1 mAnimateInFlyout;
    public final KeyguardStatusView$$ExternalSyntheticLambda0 mAnimateTemporarilyInvisibleImmediate;
    public SurfaceControl.ScreenshotHardwareBuffer mAnimatingOutBubbleBuffer;
    public final ValueAnimator mAnimatingOutSurfaceAlphaAnimator;
    public FrameLayout mAnimatingOutSurfaceContainer;
    public boolean mAnimatingOutSurfaceReady;
    public SurfaceView mAnimatingOutSurfaceView;
    public C18166 mBubbleClickListener;
    public PhysicsAnimationLayout mBubbleContainer;
    public final BubbleController mBubbleController;
    public final BubbleData mBubbleData;
    public int mBubbleElevation;
    public BubbleOverflow mBubbleOverflow;
    public int mBubbleSize;
    public BubbleViewProvider mBubbleToExpandAfterFlyoutCollapse;
    public C18177 mBubbleTouchListener;
    public int mBubbleTouchPadding;
    public int mCornerRadius;
    public BubbleStackView$$ExternalSyntheticLambda25 mDelayedAnimation;
    public final ShellExecutor mDelayedAnimationExecutor;
    public final ValueAnimator mDismissBubbleAnimator;
    public DismissView mDismissView;
    public Bubbles.BubbleExpandListener mExpandListener;
    public ExpandedAnimationController mExpandedAnimationController;
    public BubbleViewProvider mExpandedBubble;
    public final ValueAnimator mExpandedViewAlphaAnimator;
    public FrameLayout mExpandedViewContainer;
    public final AnimatableScaleMatrix mExpandedViewContainerMatrix = new AnimatableScaleMatrix();
    public int mExpandedViewPadding;
    public boolean mExpandedViewTemporarilyHidden;
    public BubbleFlyoutView mFlyout;
    public C18188 mFlyoutClickListener;
    public final C18133 mFlyoutCollapseProperty;
    public float mFlyoutDragDeltaX;
    public C18199 mFlyoutTouchListener;
    public final SpringAnimation mFlyoutTransitionSpring;
    public VolumeDialogImpl$$ExternalSyntheticLambda10 mHideFlyout;
    public final C18144 mIndividualBubbleMagnetListener;
    public boolean mIsBubbleSwitchAnimating;
    public boolean mIsDraggingStack;
    public boolean mIsExpanded;
    public boolean mIsExpansionAnimating;
    public boolean mIsGestureInProgress;
    public MagnetizedObject.MagneticTarget mMagneticTarget;
    public MagnetizedObject<?> mMagnetizedObject;
    public ManageEducationView mManageEduView;
    public ViewGroup mManageMenu;
    public View mManageMenuScrim;
    public ImageView mManageSettingsIcon;
    public TextView mManageSettingsText;
    public PhysicsAnimator.SpringConfig mManageSpringConfig;
    public BubbleStackView$$ExternalSyntheticLambda7 mOrientationChangedListener;
    public int mPointerIndexDown;
    public BubblePositioner mPositioner;
    public RelativeStackPosition mRelativeStackPositionBeforeRotation;
    public final PhysicsAnimator.SpringConfig mScaleInSpringConfig = new PhysicsAnimator.SpringConfig(300.0f, 0.9f);
    public final PhysicsAnimator.SpringConfig mScaleOutSpringConfig = new PhysicsAnimator.SpringConfig(900.0f, 1.0f);
    public View mScrim;
    public boolean mShowedUserEducationInTouchListenerActive;
    public boolean mShowingManage;
    public StackAnimationController mStackAnimationController;
    public StackEducationView mStackEduView;
    public final C18155 mStackMagnetListener;
    public boolean mStackOnLeftOrWillBe;
    public StackViewState mStackViewState = new StackViewState();
    public final SurfaceSynchronizer mSurfaceSynchronizer;
    public BubbleStackView$$ExternalSyntheticLambda8 mSystemGestureExcludeUpdater;
    public final List<Rect> mSystemGestureExclusionRects;
    public Rect mTempRect;
    public boolean mTemporarilyInvisible;
    public final PhysicsAnimator.SpringConfig mTranslateSpringConfig = new PhysicsAnimator.SpringConfig(50.0f, 1.0f);
    public Consumer<String> mUnbubbleConversationCallback;
    public View mViewBeingDismissed;
    public boolean mViewUpdatedRequested;
    public C18122 mViewUpdater;

    /* renamed from: com.android.wm.shell.bubbles.BubbleStackView$StackViewState */
    public static class StackViewState {
        public int numberOfBubbles;
        public boolean onLeft;
    }

    /* renamed from: com.android.wm.shell.bubbles.BubbleStackView$SurfaceSynchronizer */
    public interface SurfaceSynchronizer {
    }

    public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        internalInsetsInfo.setTouchableInsets(3);
        this.mTempRect.setEmpty();
        Rect rect = this.mTempRect;
        int i = 0;
        if (isStackEduShowing()) {
            rect.set(0, 0, getWidth(), getHeight());
        } else {
            if (this.mIsExpanded) {
                this.mBubbleContainer.getBoundsOnScreen(rect);
                int i2 = rect.bottom;
                BubblePositioner bubblePositioner = this.mPositioner;
                Objects.requireNonNull(bubblePositioner);
                if (bubblePositioner.mImeVisible) {
                    i = bubblePositioner.mImeHeight;
                }
                rect.bottom = i2 - i;
            } else if (getBubbleCount() > 0 || this.mBubbleData.isShowingOverflow()) {
                this.mBubbleContainer.getChildAt(0).getBoundsOnScreen(rect);
                int i3 = rect.top;
                int i4 = this.mBubbleTouchPadding;
                rect.top = i3 - i4;
                rect.left -= i4;
                rect.right += i4;
                rect.bottom += i4;
            }
            if (this.mFlyout.getVisibility() == 0) {
                Rect rect2 = new Rect();
                this.mFlyout.getBoundsOnScreen(rect2);
                rect.union(rect2);
            }
        }
        internalInsetsInfo.touchableRegion.set(this.mTempRect);
    }

    public final void setSelectedBubble(BubbleViewProvider bubbleViewProvider) {
        BubbleViewProvider bubbleViewProvider2;
        if (bubbleViewProvider == null) {
            BubbleData bubbleData = this.mBubbleData;
            Objects.requireNonNull(bubbleData);
            bubbleData.mShowingOverflow = false;
        } else if (this.mExpandedBubble != bubbleViewProvider) {
            if (bubbleViewProvider.getKey().equals("Overflow")) {
                BubbleData bubbleData2 = this.mBubbleData;
                Objects.requireNonNull(bubbleData2);
                bubbleData2.mShowingOverflow = true;
            } else {
                BubbleData bubbleData3 = this.mBubbleData;
                Objects.requireNonNull(bubbleData3);
                bubbleData3.mShowingOverflow = false;
            }
            if (this.mIsExpanded && this.mIsExpansionAnimating) {
                this.mDelayedAnimationExecutor.removeCallbacks(this.mDelayedAnimation);
                this.mIsExpansionAnimating = false;
                this.mIsBubbleSwitchAnimating = false;
                SurfaceView surfaceView = this.mAnimatingOutSurfaceView;
                Function1<Object, ? extends PhysicsAnimator<?>> function1 = PhysicsAnimator.instanceConstructor;
                PhysicsAnimator.Companion.getInstance(surfaceView).cancel();
                PhysicsAnimator.Companion.getInstance(this.mExpandedViewContainerMatrix).cancel();
                this.mExpandedViewContainer.setAnimationMatrix((Matrix) null);
            }
            showManageMenu(false);
            if (!this.mIsExpanded || (bubbleViewProvider2 = this.mExpandedBubble) == null || bubbleViewProvider2.getExpandedView() == null || this.mExpandedViewTemporarilyHidden) {
                showNewlySelectedBubble(bubbleViewProvider);
                return;
            }
            BubbleViewProvider bubbleViewProvider3 = this.mExpandedBubble;
            if (!(bubbleViewProvider3 == null || bubbleViewProvider3.getExpandedView() == null)) {
                BubbleExpandedView expandedView = this.mExpandedBubble.getExpandedView();
                Objects.requireNonNull(expandedView);
                TaskView taskView = expandedView.mTaskView;
                if (taskView != null) {
                    taskView.setZOrderedOnTop(true, true);
                }
            }
            try {
                screenshotAnimatingOutBubbleIntoSurface(new BubbleStackView$$ExternalSyntheticLambda27(this, bubbleViewProvider));
            } catch (Exception e) {
                showNewlySelectedBubble(bubbleViewProvider);
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.android.wm.shell.bubbles.BubbleStackView$RelativeStackPosition */
    public static class RelativeStackPosition {
        public boolean mOnLeft;
        public float mVerticalOffsetPercent;

        public RelativeStackPosition(PointF pointF, RectF rectF) {
            boolean z;
            if (pointF.x < rectF.width() / 2.0f) {
                z = true;
            } else {
                z = false;
            }
            this.mOnLeft = z;
            this.mVerticalOffsetPercent = Math.max(0.0f, Math.min(1.0f, (pointF.y - rectF.top) / rectF.height()));
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    @SuppressLint({"ClickableViewAccessibility"})
    public BubbleStackView(Context context, BubbleController bubbleController, BubbleData bubbleData, SurfaceSynchronizer surfaceSynchronizer, FloatingContentCoordinator floatingContentCoordinator, ShellExecutor shellExecutor) {
        super(context);
        SurfaceSynchronizer surfaceSynchronizer2;
        float f;
        Context context2 = context;
        BubbleData bubbleData2 = bubbleData;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        this.mAnimatingOutSurfaceAlphaAnimator = ofFloat;
        this.mHideFlyout = new VolumeDialogImpl$$ExternalSyntheticLambda10(this, 7);
        this.mBubbleToExpandAfterFlyoutCollapse = null;
        this.mStackOnLeftOrWillBe = true;
        this.mIsGestureInProgress = false;
        this.mTemporarilyInvisible = false;
        this.mIsDraggingStack = false;
        this.mExpandedViewTemporarilyHidden = false;
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        this.mExpandedViewAlphaAnimator = ofFloat2;
        this.mPointerIndexDown = -1;
        this.mViewUpdatedRequested = false;
        this.mIsExpansionAnimating = false;
        this.mIsBubbleSwitchAnimating = false;
        this.mTempRect = new Rect();
        this.mSystemGestureExclusionRects = Collections.singletonList(new Rect());
        this.mViewUpdater = new ViewTreeObserver.OnPreDrawListener() {
            public final boolean onPreDraw() {
                BubbleStackView.this.getViewTreeObserver().removeOnPreDrawListener(BubbleStackView.this.mViewUpdater);
                BubbleStackView.this.updateExpandedView();
                BubbleStackView.this.mViewUpdatedRequested = false;
                return true;
            }
        };
        this.mSystemGestureExcludeUpdater = new BubbleStackView$$ExternalSyntheticLambda8(this);
        C18133 r10 = new FloatPropertyCompat() {
            public final float getValue(Object obj) {
                return BubbleStackView.this.mFlyoutDragDeltaX;
            }

            public final void setValue(Object obj, float f) {
                BubbleStackView.this.setFlyoutStateForDragLength(f);
            }
        };
        this.mFlyoutCollapseProperty = r10;
        SpringAnimation springAnimation = new SpringAnimation(this, r10);
        this.mFlyoutTransitionSpring = springAnimation;
        this.mFlyoutDragDeltaX = 0.0f;
        BubbleStackView$$ExternalSyntheticLambda9 bubbleStackView$$ExternalSyntheticLambda9 = new BubbleStackView$$ExternalSyntheticLambda9(this);
        this.mAfterFlyoutTransitionSpring = bubbleStackView$$ExternalSyntheticLambda9;
        this.mIndividualBubbleMagnetListener = new MagnetizedObject.MagnetListener() {
            public final void onReleasedInTarget() {
                if (BubbleStackView.this.mExpandedAnimationController.getDraggedOutBubble() != null) {
                    ExpandedAnimationController expandedAnimationController = BubbleStackView.this.mExpandedAnimationController;
                    View draggedOutBubble = expandedAnimationController.getDraggedOutBubble();
                    float height = (float) BubbleStackView.this.mDismissView.getHeight();
                    TaskView$$ExternalSyntheticLambda5 taskView$$ExternalSyntheticLambda5 = new TaskView$$ExternalSyntheticLambda5(BubbleStackView.this, 5);
                    if (draggedOutBubble != null) {
                        PhysicsAnimationLayout.PhysicsPropertyAnimator animationForChild = expandedAnimationController.animationForChild(draggedOutBubble);
                        animationForChild.mStiffness = 10000.0f;
                        animationForChild.property(DynamicAnimation.SCALE_X, 0.0f, new Runnable[0]);
                        animationForChild.property(DynamicAnimation.SCALE_Y, 0.0f, new Runnable[0]);
                        animationForChild.translationY(draggedOutBubble.getTranslationY() + height, new Runnable[0]);
                        animationForChild.property(DynamicAnimation.ALPHA, 0.0f, taskView$$ExternalSyntheticLambda5);
                        animationForChild.start(new Runnable[0]);
                        expandedAnimationController.updateBubblePositions();
                    }
                    BubbleStackView.this.mDismissView.hide();
                }
            }

            public final void onStuckToTarget() {
                if (BubbleStackView.this.mExpandedAnimationController.getDraggedOutBubble() != null) {
                    BubbleStackView bubbleStackView = BubbleStackView.this;
                    BubbleStackView.m290$$Nest$manimateDismissBubble(bubbleStackView, bubbleStackView.mExpandedAnimationController.getDraggedOutBubble(), true);
                }
            }

            public final void onUnstuckFromTarget(float f, float f2, boolean z) {
                if (BubbleStackView.this.mExpandedAnimationController.getDraggedOutBubble() != null) {
                    BubbleStackView bubbleStackView = BubbleStackView.this;
                    BubbleStackView.m290$$Nest$manimateDismissBubble(bubbleStackView, bubbleStackView.mExpandedAnimationController.getDraggedOutBubble(), false);
                    if (z) {
                        ExpandedAnimationController expandedAnimationController = BubbleStackView.this.mExpandedAnimationController;
                        expandedAnimationController.snapBubbleBack(expandedAnimationController.getDraggedOutBubble(), f, f2);
                        BubbleStackView.this.mDismissView.hide();
                        return;
                    }
                    ExpandedAnimationController expandedAnimationController2 = BubbleStackView.this.mExpandedAnimationController;
                    Objects.requireNonNull(expandedAnimationController2);
                    expandedAnimationController2.mSpringToTouchOnNextMotionEvent = true;
                }
            }
        };
        this.mStackMagnetListener = new MagnetizedObject.MagnetListener() {
            public final void onReleasedInTarget() {
                BubbleStackView bubbleStackView = BubbleStackView.this;
                StackAnimationController stackAnimationController = bubbleStackView.mStackAnimationController;
                KeyguardUpdateMonitor$$ExternalSyntheticLambda8 keyguardUpdateMonitor$$ExternalSyntheticLambda8 = new KeyguardUpdateMonitor$$ExternalSyntheticLambda8(this, 3);
                Objects.requireNonNull(stackAnimationController);
                stackAnimationController.animationsForChildrenFromIndex(new StackAnimationController$$ExternalSyntheticLambda2(stackAnimationController, (float) bubbleStackView.mDismissView.getHeight())).startAll(new Runnable[]{keyguardUpdateMonitor$$ExternalSyntheticLambda8});
                BubbleStackView.this.mDismissView.hide();
            }

            public final void onStuckToTarget() {
                BubbleStackView bubbleStackView = BubbleStackView.this;
                BubbleStackView.m290$$Nest$manimateDismissBubble(bubbleStackView, bubbleStackView.mBubbleContainer, true);
            }

            public final void onUnstuckFromTarget(float f, float f2, boolean z) {
                BubbleStackView bubbleStackView = BubbleStackView.this;
                BubbleStackView.m290$$Nest$manimateDismissBubble(bubbleStackView, bubbleStackView.mBubbleContainer, false);
                if (z) {
                    StackAnimationController stackAnimationController = BubbleStackView.this.mStackAnimationController;
                    Objects.requireNonNull(stackAnimationController);
                    stackAnimationController.flingStackThenSpringToEdge(stackAnimationController.mStackPosition.x, f, f2);
                    BubbleStackView.this.mDismissView.hide();
                    return;
                }
                StackAnimationController stackAnimationController2 = BubbleStackView.this.mStackAnimationController;
                Objects.requireNonNull(stackAnimationController2);
                stackAnimationController2.mSpringToTouchOnNextMotionEvent = true;
            }
        };
        this.mBubbleClickListener = new View.OnClickListener() {
            public final void onClick(View view) {
                Bubble bubbleWithView;
                BubbleStackView bubbleStackView = BubbleStackView.this;
                bubbleStackView.mIsDraggingStack = false;
                if (!bubbleStackView.mIsExpansionAnimating && !bubbleStackView.mIsBubbleSwitchAnimating && (bubbleWithView = bubbleStackView.mBubbleData.getBubbleWithView(view)) != null) {
                    boolean equals = bubbleWithView.mKey.equals(BubbleStackView.this.mExpandedBubble.getKey());
                    BubbleStackView bubbleStackView2 = BubbleStackView.this;
                    Objects.requireNonNull(bubbleStackView2);
                    if (bubbleStackView2.mIsExpanded) {
                        ExpandedAnimationController expandedAnimationController = BubbleStackView.this.mExpandedAnimationController;
                        Objects.requireNonNull(expandedAnimationController);
                        expandedAnimationController.mBubbleDraggedOutEnough = false;
                        expandedAnimationController.mMagnetizedBubbleDraggingOut = null;
                        expandedAnimationController.updateBubblePositions();
                    }
                    BubbleStackView bubbleStackView3 = BubbleStackView.this;
                    Objects.requireNonNull(bubbleStackView3);
                    if (!bubbleStackView3.mIsExpanded || equals) {
                        if (!BubbleStackView.this.maybeShowStackEdu()) {
                            BubbleStackView bubbleStackView4 = BubbleStackView.this;
                            if (!bubbleStackView4.mShowedUserEducationInTouchListenerActive) {
                                BubbleData bubbleData = bubbleStackView4.mBubbleData;
                                Objects.requireNonNull(bubbleData);
                                bubbleData.setExpanded(!bubbleData.mExpanded);
                            }
                        }
                        BubbleStackView.this.mShowedUserEducationInTouchListenerActive = false;
                        return;
                    }
                    BubbleData bubbleData2 = BubbleStackView.this.mBubbleData;
                    Objects.requireNonNull(bubbleData2);
                    if (bubbleWithView != bubbleData2.mSelectedBubble) {
                        BubbleStackView.this.mBubbleData.setSelectedBubble(bubbleWithView);
                    } else {
                        BubbleStackView.this.setSelectedBubble(bubbleWithView);
                    }
                }
            }
        };
        this.mBubbleTouchListener = new RelativeTouchListener() {
            public final void onDown(View view, MotionEvent motionEvent) {
                BubbleStackView bubbleStackView = BubbleStackView.this;
                if (!bubbleStackView.mIsExpansionAnimating) {
                    bubbleStackView.mShowedUserEducationInTouchListenerActive = false;
                    if (bubbleStackView.maybeShowStackEdu()) {
                        BubbleStackView.this.mShowedUserEducationInTouchListenerActive = true;
                        return;
                    }
                    if (BubbleStackView.this.isStackEduShowing()) {
                        BubbleStackView.this.mStackEduView.hide(false);
                    }
                    BubbleStackView bubbleStackView2 = BubbleStackView.this;
                    if (bubbleStackView2.mShowingManage) {
                        bubbleStackView2.showManageMenu(false);
                    }
                    BubbleData bubbleData = BubbleStackView.this.mBubbleData;
                    Objects.requireNonNull(bubbleData);
                    if (bubbleData.mExpanded) {
                        ManageEducationView manageEducationView = BubbleStackView.this.mManageEduView;
                        if (manageEducationView != null) {
                            manageEducationView.hide();
                        }
                        BubbleStackView bubbleStackView3 = BubbleStackView.this;
                        ExpandedAnimationController expandedAnimationController = bubbleStackView3.mExpandedAnimationController;
                        MagnetizedObject.MagneticTarget magneticTarget = bubbleStackView3.mMagneticTarget;
                        C18144 r0 = bubbleStackView3.mIndividualBubbleMagnetListener;
                        Objects.requireNonNull(expandedAnimationController);
                        expandedAnimationController.mLayout.cancelAnimationsOnView(view);
                        view.setTranslationZ(32767.0f);
                        ExpandedAnimationController.C18251 r4 = new MagnetizedObject<View>(expandedAnimationController, expandedAnimationController.mLayout.getContext(), view, view) {
                            public final /* synthetic */ ExpandedAnimationController this$0;
                            public final /* synthetic */ View val$bubble;

                            public final float getHeight(
/*
Method generation error in method: com.android.wm.shell.bubbles.animation.ExpandedAnimationController.1.getHeight(java.lang.Object):float, dex: classes.dex
                            jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.android.wm.shell.bubbles.animation.ExpandedAnimationController.1.getHeight(java.lang.Object):float, class status: UNLOADED
                            	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:278)
                            	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:116)
                            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:313)
                            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                            	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                            	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                            	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                            	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                            	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:676)
                            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:607)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
                            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
                            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
                            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                            	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                            	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                            	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                            	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                            	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:676)
                            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:607)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:429)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
                            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
                            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
                            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                            	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                            	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                            	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                            	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                            	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                            	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                            	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
                            	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                            	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
                            	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
                            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
                            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
                            
*/

                            public final void getLocationOnScreen(
/*
Method generation error in method: com.android.wm.shell.bubbles.animation.ExpandedAnimationController.1.getLocationOnScreen(java.lang.Object, int[]):void, dex: classes.dex
                            jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.android.wm.shell.bubbles.animation.ExpandedAnimationController.1.getLocationOnScreen(java.lang.Object, int[]):void, class status: UNLOADED
                            	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:278)
                            	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:116)
                            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:313)
                            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                            	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                            	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                            	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                            	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                            	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:676)
                            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:607)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
                            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
                            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
                            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                            	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                            	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                            	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                            	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                            	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:676)
                            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:607)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:429)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
                            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
                            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
                            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                            	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                            	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                            	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                            	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                            	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                            	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                            	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
                            	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                            	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
                            	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
                            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
                            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
                            
*/

                            public final float getWidth(
/*
Method generation error in method: com.android.wm.shell.bubbles.animation.ExpandedAnimationController.1.getWidth(java.lang.Object):float, dex: classes.dex
                            jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.android.wm.shell.bubbles.animation.ExpandedAnimationController.1.getWidth(java.lang.Object):float, class status: UNLOADED
                            	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:278)
                            	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:116)
                            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:313)
                            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                            	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                            	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                            	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                            	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                            	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:676)
                            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:607)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
                            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
                            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
                            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                            	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                            	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                            	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                            	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                            	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:676)
                            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:607)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:429)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
                            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
                            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
                            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                            	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                            	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                            	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                            	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                            	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                            	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                            	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
                            	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                            	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
                            	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
                            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
                            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
                            
*/
                        };
                        expandedAnimationController.mMagnetizedBubbleDraggingOut = r4;
                        r4.associatedTargets.add(magneticTarget);
                        Objects.requireNonNull(magneticTarget);
                        magneticTarget.targetView.post(new MagnetizedObject$MagneticTarget$updateLocationOnScreen$1(magneticTarget));
                        ExpandedAnimationController.C18251 r13 = expandedAnimationController.mMagnetizedBubbleDraggingOut;
                        Objects.requireNonNull(r13);
                        r13.magnetListener = r0;
                        ExpandedAnimationController.C18251 r132 = expandedAnimationController.mMagnetizedBubbleDraggingOut;
                        Objects.requireNonNull(r132);
                        r132.hapticsEnabled = true;
                        ExpandedAnimationController.C18251 r133 = expandedAnimationController.mMagnetizedBubbleDraggingOut;
                        Objects.requireNonNull(r133);
                        r133.flingToTargetMinVelocity = 6000.0f;
                        BubbleStackView.this.hideCurrentInputMethod();
                        BubbleStackView bubbleStackView4 = BubbleStackView.this;
                        ExpandedAnimationController expandedAnimationController2 = bubbleStackView4.mExpandedAnimationController;
                        Objects.requireNonNull(expandedAnimationController2);
                        bubbleStackView4.mMagnetizedObject = expandedAnimationController2.mMagnetizedBubbleDraggingOut;
                    } else {
                        StackAnimationController stackAnimationController = BubbleStackView.this.mStackAnimationController;
                        Objects.requireNonNull(stackAnimationController);
                        DynamicAnimation.C01371 r02 = DynamicAnimation.TRANSLATION_X;
                        stackAnimationController.cancelStackPositionAnimation(r02);
                        DynamicAnimation.C01422 r3 = DynamicAnimation.TRANSLATION_Y;
                        stackAnimationController.cancelStackPositionAnimation(r3);
                        stackAnimationController.mLayout.mEndActionForProperty.remove(r02);
                        stackAnimationController.mLayout.mEndActionForProperty.remove(r3);
                        BubbleStackView bubbleStackView5 = BubbleStackView.this;
                        bubbleStackView5.mBubbleContainer.setActiveController(bubbleStackView5.mStackAnimationController);
                        BubbleStackView.this.hideFlyoutImmediate();
                        Objects.requireNonNull(BubbleStackView.this.mPositioner);
                        BubbleStackView bubbleStackView6 = BubbleStackView.this;
                        StackAnimationController stackAnimationController2 = bubbleStackView6.mStackAnimationController;
                        Objects.requireNonNull(stackAnimationController2);
                        if (stackAnimationController2.mMagnetizedStack == null) {
                            StackAnimationController.C18342 r42 = new MagnetizedObject<StackAnimationController>(stackAnimationController2.mLayout.getContext(), stackAnimationController2, new StackAnimationController.StackPositionProperty(r02), new StackAnimationController.StackPositionProperty(r3)) {
                                public final float getHeight(Object obj) {
                                    StackAnimationController stackAnimationController = (StackAnimationController) obj;
                                    return (float) StackAnimationController.this.mBubbleSize;
                                }

                                public final void getLocationOnScreen(Object obj, int[] iArr) {
                                    StackAnimationController stackAnimationController = (StackAnimationController) obj;
                                    PointF pointF = StackAnimationController.this.mStackPosition;
                                    iArr[0] = (int) pointF.x;
                                    iArr[1] = (int) pointF.y;
                                }

                                public final float getWidth(Object obj) {
                                    StackAnimationController stackAnimationController = (StackAnimationController) obj;
                                    return (float) StackAnimationController.this.mBubbleSize;
                                }
                            };
                            stackAnimationController2.mMagnetizedStack = r42;
                            r42.hapticsEnabled = true;
                            r42.flingToTargetMinVelocity = 4000.0f;
                        }
                        ContentResolver contentResolver = stackAnimationController2.mLayout.getContext().getContentResolver();
                        StackAnimationController.C18342 r32 = stackAnimationController2.mMagnetizedStack;
                        Objects.requireNonNull(r32);
                        float f = Settings.Secure.getFloat(contentResolver, "bubble_dismiss_fling_min_velocity", r32.flingToTargetMinVelocity);
                        StackAnimationController.C18342 r43 = stackAnimationController2.mMagnetizedStack;
                        Objects.requireNonNull(r43);
                        float f2 = Settings.Secure.getFloat(contentResolver, "bubble_dismiss_stick_max_velocity", r43.stickToTargetMaxXVelocity);
                        StackAnimationController.C18342 r5 = stackAnimationController2.mMagnetizedStack;
                        Objects.requireNonNull(r5);
                        float f3 = Settings.Secure.getFloat(contentResolver, "bubble_dismiss_target_width_percent", r5.flingToTargetWidthPercent);
                        StackAnimationController.C18342 r52 = stackAnimationController2.mMagnetizedStack;
                        Objects.requireNonNull(r52);
                        r52.flingToTargetMinVelocity = f;
                        StackAnimationController.C18342 r33 = stackAnimationController2.mMagnetizedStack;
                        Objects.requireNonNull(r33);
                        r33.stickToTargetMaxXVelocity = f2;
                        StackAnimationController.C18342 r34 = stackAnimationController2.mMagnetizedStack;
                        Objects.requireNonNull(r34);
                        r34.flingToTargetWidthPercent = f3;
                        bubbleStackView6.mMagnetizedObject = stackAnimationController2.mMagnetizedStack;
                        MagnetizedObject<?> magnetizedObject = BubbleStackView.this.mMagnetizedObject;
                        Objects.requireNonNull(magnetizedObject);
                        magnetizedObject.associatedTargets.clear();
                        BubbleStackView bubbleStackView7 = BubbleStackView.this;
                        MagnetizedObject<?> magnetizedObject2 = bubbleStackView7.mMagnetizedObject;
                        MagnetizedObject.MagneticTarget magneticTarget2 = bubbleStackView7.mMagneticTarget;
                        Objects.requireNonNull(magnetizedObject2);
                        magnetizedObject2.associatedTargets.add(magneticTarget2);
                        Objects.requireNonNull(magneticTarget2);
                        magneticTarget2.targetView.post(new MagnetizedObject$MagneticTarget$updateLocationOnScreen$1(magneticTarget2));
                        BubbleStackView bubbleStackView8 = BubbleStackView.this;
                        MagnetizedObject<?> magnetizedObject3 = bubbleStackView8.mMagnetizedObject;
                        C18155 r134 = bubbleStackView8.mStackMagnetListener;
                        Objects.requireNonNull(magnetizedObject3);
                        magnetizedObject3.magnetListener = r134;
                        BubbleStackView bubbleStackView9 = BubbleStackView.this;
                        bubbleStackView9.mIsDraggingStack = true;
                        bubbleStackView9.updateTemporarilyInvisibleAnimation(false);
                    }
                    BubbleStackView.m292$$Nest$mpassEventToMagnetizedObject(BubbleStackView.this, motionEvent);
                }
            }

            public final void onMove(View view, MotionEvent motionEvent, float f, float f2, float f3, float f4) {
                BubbleViewProvider bubbleViewProvider;
                BubbleViewProvider bubbleViewProvider2;
                BubbleStackView bubbleStackView = BubbleStackView.this;
                if (!bubbleStackView.mIsExpansionAnimating) {
                    Objects.requireNonNull(bubbleStackView.mPositioner);
                    BubbleStackView bubbleStackView2 = BubbleStackView.this;
                    if (!bubbleStackView2.mShowedUserEducationInTouchListenerActive) {
                        DismissView dismissView = bubbleStackView2.mDismissView;
                        Objects.requireNonNull(dismissView);
                        boolean z = true;
                        if (!dismissView.isShowing) {
                            dismissView.isShowing = true;
                            dismissView.setVisibility(0);
                            Drawable background = dismissView.getBackground();
                            Objects.requireNonNull(background, "null cannot be cast to non-null type android.graphics.drawable.TransitionDrawable");
                            ((TransitionDrawable) background).startTransition(dismissView.DISMISS_SCRIM_FADE_MS);
                            dismissView.animator.cancel();
                            PhysicsAnimator<DismissCircleView> physicsAnimator = dismissView.animator;
                            DynamicAnimation.C01422 r5 = DynamicAnimation.TRANSLATION_Y;
                            PhysicsAnimator.SpringConfig springConfig = dismissView.spring;
                            Objects.requireNonNull(physicsAnimator);
                            physicsAnimator.spring(r5, 0.0f, 0.0f, springConfig);
                            physicsAnimator.start();
                        }
                        BubbleStackView bubbleStackView3 = BubbleStackView.this;
                        if (bubbleStackView3.mIsExpanded && (bubbleViewProvider = bubbleStackView3.mExpandedBubble) != null && view.equals(bubbleViewProvider.getIconView$1())) {
                            BubbleStackView bubbleStackView4 = BubbleStackView.this;
                            Objects.requireNonNull(bubbleStackView4);
                            if (!(bubbleStackView4.mExpandedViewTemporarilyHidden || (bubbleViewProvider2 = bubbleStackView4.mExpandedBubble) == null || bubbleViewProvider2.getExpandedView() == null)) {
                                bubbleStackView4.mExpandedViewTemporarilyHidden = true;
                                AnimatableScaleMatrix animatableScaleMatrix = bubbleStackView4.mExpandedViewContainerMatrix;
                                Function1<Object, ? extends PhysicsAnimator<?>> function1 = PhysicsAnimator.instanceConstructor;
                                PhysicsAnimator instance = PhysicsAnimator.Companion.getInstance(animatableScaleMatrix);
                                instance.spring(AnimatableScaleMatrix.SCALE_X, 449.99997f, 0.0f, bubbleStackView4.mScaleOutSpringConfig);
                                instance.spring(AnimatableScaleMatrix.SCALE_Y, 449.99997f, 0.0f, bubbleStackView4.mScaleOutSpringConfig);
                                instance.updateListeners.add(new BubbleStackView$$ExternalSyntheticLambda10(bubbleStackView4));
                                instance.start();
                                bubbleStackView4.mExpandedViewAlphaAnimator.reverse();
                            }
                        }
                        if (!BubbleStackView.m292$$Nest$mpassEventToMagnetizedObject(BubbleStackView.this, motionEvent)) {
                            BubbleStackView.this.updateBubbleShadows(true);
                            BubbleData bubbleData = BubbleStackView.this.mBubbleData;
                            Objects.requireNonNull(bubbleData);
                            if (!bubbleData.mExpanded) {
                                Objects.requireNonNull(BubbleStackView.this.mPositioner);
                                if (BubbleStackView.this.isStackEduShowing()) {
                                    BubbleStackView.this.mStackEduView.hide(false);
                                }
                                StackAnimationController stackAnimationController = BubbleStackView.this.mStackAnimationController;
                                float f5 = f + f3;
                                float f6 = f2 + f4;
                                Objects.requireNonNull(stackAnimationController);
                                if (stackAnimationController.mSpringToTouchOnNextMotionEvent) {
                                    stackAnimationController.springStack(f5, f6, 12000.0f);
                                    stackAnimationController.mSpringToTouchOnNextMotionEvent = false;
                                    stackAnimationController.mFirstBubbleSpringingToTouch = true;
                                } else if (stackAnimationController.mFirstBubbleSpringingToTouch) {
                                    SpringAnimation springAnimation = (SpringAnimation) stackAnimationController.mStackPositionAnimations.get(DynamicAnimation.TRANSLATION_X);
                                    SpringAnimation springAnimation2 = (SpringAnimation) stackAnimationController.mStackPositionAnimations.get(DynamicAnimation.TRANSLATION_Y);
                                    Objects.requireNonNull(springAnimation);
                                    if (!springAnimation.mRunning) {
                                        Objects.requireNonNull(springAnimation2);
                                        if (!springAnimation2.mRunning) {
                                            stackAnimationController.mFirstBubbleSpringingToTouch = false;
                                        }
                                    }
                                    springAnimation.animateToFinalPosition(f5);
                                    springAnimation2.animateToFinalPosition(f6);
                                }
                                if (!stackAnimationController.mFirstBubbleSpringingToTouch && !stackAnimationController.isStackStuckToTarget()) {
                                    stackAnimationController.mAnimatingToBounds.setEmpty();
                                    stackAnimationController.mPreImeY = -1.4E-45f;
                                    stackAnimationController.moveFirstBubbleWithStackFollowing(DynamicAnimation.TRANSLATION_X, f5);
                                    stackAnimationController.moveFirstBubbleWithStackFollowing(DynamicAnimation.TRANSLATION_Y, f6);
                                    stackAnimationController.mIsMovingFromFlinging = false;
                                    return;
                                }
                                return;
                            }
                            ExpandedAnimationController expandedAnimationController = BubbleStackView.this.mExpandedAnimationController;
                            float f7 = f + f3;
                            float f8 = f2 + f4;
                            Objects.requireNonNull(expandedAnimationController);
                            ExpandedAnimationController.C18251 r10 = expandedAnimationController.mMagnetizedBubbleDraggingOut;
                            if (r10 != null) {
                                if (expandedAnimationController.mSpringToTouchOnNextMotionEvent) {
                                    PhysicsAnimationLayout.PhysicsPropertyAnimator animationForChild = expandedAnimationController.animationForChild((View) r10.underlyingObject);
                                    animationForChild.mPathAnimator = null;
                                    animationForChild.property(DynamicAnimation.TRANSLATION_X, f7, new Runnable[0]);
                                    animationForChild.translationY(f8, new Runnable[0]);
                                    animationForChild.mStiffness = 10000.0f;
                                    animationForChild.start(new Runnable[0]);
                                    expandedAnimationController.mSpringToTouchOnNextMotionEvent = false;
                                    expandedAnimationController.mSpringingBubbleToTouch = true;
                                } else if (expandedAnimationController.mSpringingBubbleToTouch) {
                                    PhysicsAnimationLayout physicsAnimationLayout = expandedAnimationController.mLayout;
                                    DynamicAnimation.C01371 r1 = DynamicAnimation.TRANSLATION_X;
                                    DynamicAnimation.ViewProperty[] viewPropertyArr = {r1, DynamicAnimation.TRANSLATION_Y};
                                    Objects.requireNonNull(physicsAnimationLayout);
                                    if (PhysicsAnimationLayout.arePropertiesAnimatingOnView(view, viewPropertyArr)) {
                                        ExpandedAnimationController.C18251 r102 = expandedAnimationController.mMagnetizedBubbleDraggingOut;
                                        Objects.requireNonNull(r102);
                                        PhysicsAnimationLayout.PhysicsPropertyAnimator animationForChild2 = expandedAnimationController.animationForChild((View) r102.underlyingObject);
                                        animationForChild2.mPathAnimator = null;
                                        animationForChild2.property(r1, f7, new Runnable[0]);
                                        animationForChild2.translationY(f8, new Runnable[0]);
                                        animationForChild2.mStiffness = 10000.0f;
                                        animationForChild2.start(new Runnable[0]);
                                    } else {
                                        expandedAnimationController.mSpringingBubbleToTouch = false;
                                    }
                                }
                                if (!expandedAnimationController.mSpringingBubbleToTouch && !expandedAnimationController.mMagnetizedBubbleDraggingOut.getObjectStuckToTarget()) {
                                    view.setTranslationX(f7);
                                    view.setTranslationY(f8);
                                }
                                float expandedViewYTopAligned = expandedAnimationController.mPositioner.getExpandedViewYTopAligned();
                                float f9 = expandedAnimationController.mBubbleSizePx;
                                if (f8 <= expandedViewYTopAligned + f9 && f8 >= expandedViewYTopAligned - f9) {
                                    z = false;
                                }
                                if (z != expandedAnimationController.mBubbleDraggedOutEnough) {
                                    expandedAnimationController.updateBubblePositions();
                                    expandedAnimationController.mBubbleDraggedOutEnough = z;
                                }
                            }
                        }
                    }
                }
            }

            public final void onUp(View view, MotionEvent motionEvent, float f, float f2, float f3, float f4) {
                boolean z;
                BubbleStackView bubbleStackView = BubbleStackView.this;
                if (!bubbleStackView.mIsExpansionAnimating) {
                    Objects.requireNonNull(bubbleStackView.mPositioner);
                    BubbleStackView bubbleStackView2 = BubbleStackView.this;
                    if (bubbleStackView2.mShowedUserEducationInTouchListenerActive) {
                        bubbleStackView2.mShowedUserEducationInTouchListenerActive = false;
                        return;
                    }
                    if (!BubbleStackView.m292$$Nest$mpassEventToMagnetizedObject(bubbleStackView2, motionEvent)) {
                        BubbleData bubbleData = BubbleStackView.this.mBubbleData;
                        Objects.requireNonNull(bubbleData);
                        if (bubbleData.mExpanded) {
                            BubbleStackView.this.mExpandedAnimationController.snapBubbleBack(view, f3, f4);
                            BubbleStackView bubbleStackView3 = BubbleStackView.this;
                            Objects.requireNonNull(bubbleStackView3);
                            if (bubbleStackView3.mExpandedViewTemporarilyHidden) {
                                bubbleStackView3.mExpandedViewTemporarilyHidden = false;
                                AnimatableScaleMatrix animatableScaleMatrix = bubbleStackView3.mExpandedViewContainerMatrix;
                                Function1<Object, ? extends PhysicsAnimator<?>> function1 = PhysicsAnimator.instanceConstructor;
                                PhysicsAnimator instance = PhysicsAnimator.Companion.getInstance(animatableScaleMatrix);
                                instance.spring(AnimatableScaleMatrix.SCALE_X, 499.99997f, 0.0f, bubbleStackView3.mScaleOutSpringConfig);
                                instance.spring(AnimatableScaleMatrix.SCALE_Y, 499.99997f, 0.0f, bubbleStackView3.mScaleOutSpringConfig);
                                instance.updateListeners.add(new BubbleStackView$$ExternalSyntheticLambda11(bubbleStackView3));
                                instance.start();
                                bubbleStackView3.mExpandedViewAlphaAnimator.start();
                            }
                        } else {
                            BubbleStackView bubbleStackView4 = BubbleStackView.this;
                            boolean z2 = bubbleStackView4.mStackOnLeftOrWillBe;
                            int i = (bubbleStackView4.mStackAnimationController.flingStackThenSpringToEdge(f + f2, f3, f4) > 0.0f ? 1 : (bubbleStackView4.mStackAnimationController.flingStackThenSpringToEdge(f + f2, f3, f4) == 0.0f ? 0 : -1));
                            boolean z3 = true;
                            if (i <= 0) {
                                z = true;
                            } else {
                                z = false;
                            }
                            bubbleStackView4.mStackOnLeftOrWillBe = z;
                            BubbleStackView bubbleStackView5 = BubbleStackView.this;
                            if (z2 == bubbleStackView5.mStackOnLeftOrWillBe) {
                                z3 = false;
                            }
                            bubbleStackView5.updateBadges(z3);
                            BubbleStackView.this.logBubbleEvent((BubbleViewProvider) null, 7);
                        }
                        BubbleStackView.this.mDismissView.hide();
                    }
                    BubbleStackView bubbleStackView6 = BubbleStackView.this;
                    bubbleStackView6.mIsDraggingStack = false;
                    bubbleStackView6.updateTemporarilyInvisibleAnimation(false);
                }
            }
        };
        this.mFlyoutClickListener = new View.OnClickListener() {
            public final void onClick(View view) {
                if (BubbleStackView.this.maybeShowStackEdu()) {
                    BubbleStackView.this.mBubbleToExpandAfterFlyoutCollapse = null;
                } else {
                    BubbleStackView bubbleStackView = BubbleStackView.this;
                    BubbleData bubbleData = bubbleStackView.mBubbleData;
                    Objects.requireNonNull(bubbleData);
                    bubbleStackView.mBubbleToExpandAfterFlyoutCollapse = bubbleData.mSelectedBubble;
                }
                BubbleStackView bubbleStackView2 = BubbleStackView.this;
                bubbleStackView2.mFlyout.removeCallbacks(bubbleStackView2.mHideFlyout);
                BubbleStackView.this.mHideFlyout.run();
            }
        };
        this.mFlyoutTouchListener = new RelativeTouchListener() {
            public final void onDown(View view, MotionEvent motionEvent) {
                BubbleStackView bubbleStackView = BubbleStackView.this;
                bubbleStackView.mFlyout.removeCallbacks(bubbleStackView.mHideFlyout);
            }

            public final void onMove(View view, MotionEvent motionEvent, float f, float f2, float f3, float f4) {
                BubbleStackView.this.setFlyoutStateForDragLength(f3);
            }

            public final void onUp(View view, MotionEvent motionEvent, float f, float f2, float f3, float f4) {
                boolean z;
                boolean z2;
                boolean z3;
                boolean isStackOnLeftSide = BubbleStackView.this.mStackAnimationController.isStackOnLeftSide();
                boolean z4 = true;
                if (!isStackOnLeftSide ? f3 <= 2000.0f : f3 >= -2000.0f) {
                    z = false;
                } else {
                    z = true;
                }
                if (!isStackOnLeftSide ? f2 <= ((float) BubbleStackView.this.mFlyout.getWidth()) * 0.25f : f2 >= ((float) (-BubbleStackView.this.mFlyout.getWidth())) * 0.25f) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                if (!isStackOnLeftSide ? f3 >= 0.0f : f3 <= 0.0f) {
                    z3 = false;
                } else {
                    z3 = true;
                }
                if (!z && (!z2 || z3)) {
                    z4 = false;
                }
                BubbleStackView bubbleStackView = BubbleStackView.this;
                bubbleStackView.mFlyout.removeCallbacks(bubbleStackView.mHideFlyout);
                BubbleStackView.this.animateFlyoutCollapsed(z4, f3);
                BubbleStackView.this.maybeShowStackEdu();
            }
        };
        this.mShowingManage = false;
        this.mShowedUserEducationInTouchListenerActive = false;
        this.mManageSpringConfig = new PhysicsAnimator.SpringConfig(1500.0f, 0.75f);
        this.mAnimateTemporarilyInvisibleImmediate = new KeyguardStatusView$$ExternalSyntheticLambda0(this, 7);
        this.mDelayedAnimationExecutor = shellExecutor;
        this.mBubbleController = bubbleController;
        this.mBubbleData = bubbleData2;
        Resources resources = getResources();
        this.mBubbleSize = resources.getDimensionPixelSize(C1777R.dimen.bubble_size);
        this.mBubbleElevation = resources.getDimensionPixelSize(C1777R.dimen.bubble_elevation);
        this.mBubbleTouchPadding = resources.getDimensionPixelSize(C1777R.dimen.bubble_touch_padding);
        this.mExpandedViewPadding = resources.getDimensionPixelSize(C1777R.dimen.bubble_expanded_view_padding);
        int dimensionPixelSize = resources.getDimensionPixelSize(C1777R.dimen.bubble_elevation);
        this.mPositioner = bubbleController.getPositioner();
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(new int[]{16844145});
        this.mCornerRadius = obtainStyledAttributes.getDimensionPixelSize(0, 0);
        obtainStyledAttributes.recycle();
        BubbleStackView$$ExternalSyntheticLambda16 bubbleStackView$$ExternalSyntheticLambda16 = new BubbleStackView$$ExternalSyntheticLambda16(this, 0);
        this.mStackAnimationController = new StackAnimationController(floatingContentCoordinator, new BubbleStackView$$ExternalSyntheticLambda30(this), bubbleStackView$$ExternalSyntheticLambda16, new BubbleStackView$$ExternalSyntheticLambda18(this, 0), this.mPositioner);
        this.mExpandedAnimationController = new ExpandedAnimationController(this.mPositioner, bubbleStackView$$ExternalSyntheticLambda16, this);
        if (surfaceSynchronizer != null) {
            surfaceSynchronizer2 = surfaceSynchronizer;
        } else {
            surfaceSynchronizer2 = DEFAULT_SURFACE_SYNCHRONIZER;
        }
        this.mSurfaceSynchronizer = surfaceSynchronizer2;
        setLayoutDirection(0);
        PhysicsAnimationLayout physicsAnimationLayout = new PhysicsAnimationLayout(context2);
        this.mBubbleContainer = physicsAnimationLayout;
        physicsAnimationLayout.setActiveController(this.mStackAnimationController);
        float f2 = (float) dimensionPixelSize;
        this.mBubbleContainer.setElevation(f2);
        this.mBubbleContainer.setClipChildren(false);
        addView(this.mBubbleContainer, new FrameLayout.LayoutParams(-1, -1));
        FrameLayout frameLayout = new FrameLayout(context2);
        this.mExpandedViewContainer = frameLayout;
        frameLayout.setElevation(f2);
        this.mExpandedViewContainer.setClipChildren(false);
        addView(this.mExpandedViewContainer);
        FrameLayout frameLayout2 = new FrameLayout(getContext());
        this.mAnimatingOutSurfaceContainer = frameLayout2;
        frameLayout2.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        addView(this.mAnimatingOutSurfaceContainer);
        SurfaceView surfaceView = new SurfaceView(getContext());
        this.mAnimatingOutSurfaceView = surfaceView;
        surfaceView.setUseAlpha();
        this.mAnimatingOutSurfaceView.setZOrderOnTop(true);
        boolean supportsRoundedCornersOnWindows = ScreenDecorationsUtils.supportsRoundedCornersOnWindows(this.mContext.getResources());
        SurfaceView surfaceView2 = this.mAnimatingOutSurfaceView;
        if (supportsRoundedCornersOnWindows) {
            f = (float) this.mCornerRadius;
        } else {
            f = 0.0f;
        }
        surfaceView2.setCornerRadius(f);
        this.mAnimatingOutSurfaceView.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
        this.mAnimatingOutSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            public final void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            }

            public final void surfaceCreated(SurfaceHolder surfaceHolder) {
                BubbleStackView.this.mAnimatingOutSurfaceReady = true;
            }

            public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                BubbleStackView.this.mAnimatingOutSurfaceReady = false;
            }
        });
        this.mAnimatingOutSurfaceContainer.addView(this.mAnimatingOutSurfaceView);
        this.mAnimatingOutSurfaceContainer.setPadding(this.mExpandedViewContainer.getPaddingLeft(), this.mExpandedViewContainer.getPaddingTop(), this.mExpandedViewContainer.getPaddingRight(), this.mExpandedViewContainer.getPaddingBottom());
        setUpManageMenu();
        setUpFlyout();
        SpringForce springForce = new SpringForce();
        springForce.setStiffness(200.0f);
        springForce.setDampingRatio(0.75f);
        springAnimation.mSpring = springForce;
        springAnimation.addEndListener(bubbleStackView$$ExternalSyntheticLambda9);
        setUpDismissView();
        setClipChildren(false);
        setFocusable(true);
        this.mBubbleContainer.bringToFront();
        Objects.requireNonNull(bubbleData);
        BubbleOverflow bubbleOverflow = bubbleData2.mOverflow;
        this.mBubbleOverflow = bubbleOverflow;
        PhysicsAnimationLayout physicsAnimationLayout2 = this.mBubbleContainer;
        BadgedImageView iconView = bubbleOverflow.getIconView$1();
        int childCount = this.mBubbleContainer.getChildCount();
        BubblePositioner bubblePositioner = this.mPositioner;
        Objects.requireNonNull(bubblePositioner);
        int i = bubblePositioner.mBubbleSize;
        BubblePositioner bubblePositioner2 = this.mPositioner;
        Objects.requireNonNull(bubblePositioner2);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i, bubblePositioner2.mBubbleSize);
        Objects.requireNonNull(physicsAnimationLayout2);
        physicsAnimationLayout2.addViewInternal(iconView, childCount, layoutParams, false);
        updateOverflow();
        this.mBubbleOverflow.getIconView$1().setOnClickListener(new PipMenuView$$ExternalSyntheticLambda5(this, 2));
        View view = new View(getContext());
        this.mScrim = view;
        view.setImportantForAccessibility(2);
        this.mScrim.setBackgroundDrawable(new ColorDrawable(getResources().getColor(17170473)));
        addView(this.mScrim);
        this.mScrim.setAlpha(0.0f);
        View view2 = new View(getContext());
        this.mManageMenuScrim = view2;
        view2.setImportantForAccessibility(2);
        this.mManageMenuScrim.setBackgroundDrawable(new ColorDrawable(getResources().getColor(17170473)));
        addView(this.mManageMenuScrim, new FrameLayout.LayoutParams(-1, -1));
        this.mManageMenuScrim.setAlpha(0.0f);
        this.mManageMenuScrim.setVisibility(4);
        this.mOrientationChangedListener = new BubbleStackView$$ExternalSyntheticLambda7(this);
        float dimensionPixelSize2 = (float) getResources().getDimensionPixelSize(C1777R.dimen.dismiss_circle_small);
        ValueAnimator ofFloat3 = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
        this.mDismissBubbleAnimator = ofFloat3;
        ofFloat3.addUpdateListener(new BubbleStackView$$ExternalSyntheticLambda2(this, dimensionPixelSize2 / ((float) getResources().getDimensionPixelSize(C1777R.dimen.dismiss_circle_size))));
        setOnClickListener(new MediaControlPanel$$ExternalSyntheticLambda3(this, 2));
        ViewPropertyAnimator animate = animate();
        PathInterpolator pathInterpolator = Interpolators.PANEL_CLOSE_ACCELERATED;
        animate.setInterpolator(pathInterpolator).setDuration(320);
        ofFloat2.setDuration(150);
        ofFloat2.setInterpolator(pathInterpolator);
        ofFloat2.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationEnd(Animator animator) {
                BubbleViewProvider bubbleViewProvider = BubbleStackView.this.mExpandedBubble;
                if (bubbleViewProvider != null && bubbleViewProvider.getExpandedView() != null) {
                    BubbleStackView bubbleStackView = BubbleStackView.this;
                    if (!bubbleStackView.mExpandedViewTemporarilyHidden) {
                        BubbleExpandedView expandedView = bubbleStackView.mExpandedBubble.getExpandedView();
                        Objects.requireNonNull(expandedView);
                        TaskView taskView = expandedView.mTaskView;
                        if (taskView != null) {
                            taskView.setZOrderedOnTop(false, true);
                        }
                        BubbleExpandedView expandedView2 = BubbleStackView.this.mExpandedBubble.getExpandedView();
                        Objects.requireNonNull(expandedView2);
                        expandedView2.mIsAlphaAnimating = false;
                        expandedView2.setContentVisibility(expandedView2.mIsContentVisible);
                    }
                }
            }

            public final void onAnimationStart(Animator animator) {
                BubbleViewProvider bubbleViewProvider = BubbleStackView.this.mExpandedBubble;
                if (bubbleViewProvider != null && bubbleViewProvider.getExpandedView() != null) {
                    BubbleExpandedView expandedView = BubbleStackView.this.mExpandedBubble.getExpandedView();
                    Objects.requireNonNull(expandedView);
                    TaskView taskView = expandedView.mTaskView;
                    if (taskView != null) {
                        taskView.setZOrderedOnTop(true, true);
                    }
                    BubbleExpandedView expandedView2 = BubbleStackView.this.mExpandedBubble.getExpandedView();
                    Objects.requireNonNull(expandedView2);
                    expandedView2.mIsAlphaAnimating = true;
                }
            }
        });
        ofFloat2.addUpdateListener(new BubbleStackView$$ExternalSyntheticLambda0(this, 0));
        ofFloat.setDuration(150);
        ofFloat.setInterpolator(pathInterpolator);
        ofFloat.addUpdateListener(new BubbleStackView$$ExternalSyntheticLambda1(this));
        ofFloat.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationEnd(Animator animator) {
                BubbleStackView.this.releaseAnimatingOutBubbleBuffer();
            }
        });
    }

    public final void animateFlyoutCollapsed(boolean z, float f) {
        float f2;
        float f3;
        boolean isStackOnLeftSide = this.mStackAnimationController.isStackOnLeftSide();
        SpringAnimation springAnimation = this.mFlyoutTransitionSpring;
        Objects.requireNonNull(springAnimation);
        SpringForce springForce = springAnimation.mSpring;
        if (this.mBubbleToExpandAfterFlyoutCollapse != null) {
            f2 = 1500.0f;
        } else {
            f2 = 200.0f;
        }
        springForce.setStiffness(f2);
        SpringAnimation springAnimation2 = this.mFlyoutTransitionSpring;
        float f4 = this.mFlyoutDragDeltaX;
        Objects.requireNonNull(springAnimation2);
        springAnimation2.mValue = f4;
        springAnimation2.mStartValueIsSet = true;
        springAnimation2.mVelocity = f;
        if (z) {
            int width = this.mFlyout.getWidth();
            if (isStackOnLeftSide) {
                width = -width;
            }
            f3 = (float) width;
        } else {
            f3 = 0.0f;
        }
        springAnimation2.animateToFinalPosition(f3);
    }

    @VisibleForTesting
    public void animateInFlyoutForBubble(Bubble bubble) {
        boolean z;
        BadgedImageView.SuppressionFlag suppressionFlag = BadgedImageView.SuppressionFlag.FLYOUT_VISIBLE;
        Objects.requireNonNull(bubble);
        Bubble.FlyoutMessage flyoutMessage = bubble.mFlyoutMessage;
        BadgedImageView badgedImageView = bubble.mIconView;
        if (flyoutMessage == null || flyoutMessage.message == null || !bubble.showFlyout() || isStackEduShowing() || this.mIsExpanded || this.mIsExpansionAnimating || this.mIsGestureInProgress || this.mBubbleToExpandAfterFlyoutCollapse != null || badgedImageView == null) {
            if (!(badgedImageView == null || this.mFlyout.getVisibility() == 0)) {
                badgedImageView.removeDotSuppressionFlag(suppressionFlag);
            }
            z = false;
        } else {
            z = true;
        }
        if (z) {
            this.mFlyoutDragDeltaX = 0.0f;
            this.mFlyout.removeCallbacks(this.mAnimateInFlyout);
            ExecutorUtils$$ExternalSyntheticLambda0 executorUtils$$ExternalSyntheticLambda0 = this.mAfterFlyoutHidden;
            if (executorUtils$$ExternalSyntheticLambda0 != null) {
                executorUtils$$ExternalSyntheticLambda0.run();
                this.mAfterFlyoutHidden = null;
            }
            this.mAfterFlyoutHidden = new ExecutorUtils$$ExternalSyntheticLambda0(this, bubble, 4);
            BadgedImageView badgedImageView2 = bubble.mIconView;
            Objects.requireNonNull(badgedImageView2);
            if (badgedImageView2.mDotSuppressionFlags.add(suppressionFlag)) {
                badgedImageView2.updateDotVisibility(false);
            }
            post(new ExecutorUtils$$ExternalSyntheticLambda1(this, bubble, 4));
            this.mFlyout.removeCallbacks(this.mHideFlyout);
            this.mFlyout.postDelayed(this.mHideFlyout, 5000);
            logBubbleEvent(bubble, 16);
        }
    }

    public final int getBubbleCount() {
        return this.mBubbleContainer.getChildCount() - 1;
    }

    public final int getBubbleIndex(BubbleViewProvider bubbleViewProvider) {
        if (bubbleViewProvider == null) {
            return 0;
        }
        return this.mBubbleContainer.indexOfChild(bubbleViewProvider.getIconView$1());
    }

    public final StackViewState getState() {
        this.mStackViewState.numberOfBubbles = this.mBubbleContainer.getChildCount();
        StackViewState stackViewState = this.mStackViewState;
        getBubbleIndex(this.mExpandedBubble);
        Objects.requireNonNull(stackViewState);
        StackViewState stackViewState2 = this.mStackViewState;
        stackViewState2.onLeft = this.mStackOnLeftOrWillBe;
        return stackViewState2;
    }

    public final void hideCurrentInputMethod() {
        BubblePositioner bubblePositioner = this.mPositioner;
        Objects.requireNonNull(bubblePositioner);
        bubblePositioner.mImeVisible = false;
        bubblePositioner.mImeHeight = 0;
        BubbleController bubbleController = this.mBubbleController;
        Objects.requireNonNull(bubbleController);
        try {
            bubbleController.mBarService.hideCurrentInputMethodForBubbles();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public final void hideFlyoutImmediate() {
        this.mFlyout.removeCallbacks(this.mAnimateInFlyout);
        ExecutorUtils$$ExternalSyntheticLambda0 executorUtils$$ExternalSyntheticLambda0 = this.mAfterFlyoutHidden;
        if (executorUtils$$ExternalSyntheticLambda0 != null) {
            executorUtils$$ExternalSyntheticLambda0.run();
            this.mAfterFlyoutHidden = null;
        }
        this.mFlyout.removeCallbacks(this.mAnimateInFlyout);
        this.mFlyout.removeCallbacks(this.mHideFlyout);
        BubbleFlyoutView bubbleFlyoutView = this.mFlyout;
        Objects.requireNonNull(bubbleFlyoutView);
        Runnable runnable = bubbleFlyoutView.mOnHide;
        if (runnable != null) {
            runnable.run();
            bubbleFlyoutView.mOnHide = null;
        }
        bubbleFlyoutView.setVisibility(8);
    }

    public final boolean isStackEduShowing() {
        StackEducationView stackEducationView = this.mStackEduView;
        if (stackEducationView == null || stackEducationView.getVisibility() != 0) {
            return false;
        }
        return true;
    }

    public final void logBubbleEvent(BubbleViewProvider bubbleViewProvider, int i) {
        String str;
        if (bubbleViewProvider == null || !(bubbleViewProvider instanceof Bubble)) {
            str = "null";
        } else {
            str = ((Bubble) bubbleViewProvider).mPackageName;
        }
        String str2 = str;
        BubbleData bubbleData = this.mBubbleData;
        int bubbleCount = getBubbleCount();
        int bubbleIndex = getBubbleIndex(bubbleViewProvider);
        StackAnimationController stackAnimationController = this.mStackAnimationController;
        Objects.requireNonNull(stackAnimationController);
        float f = stackAnimationController.mStackPosition.x;
        BubblePositioner bubblePositioner = this.mPositioner;
        Objects.requireNonNull(bubblePositioner);
        BigDecimal bigDecimal = new BigDecimal((double) (f / ((float) bubblePositioner.mPositionRect.width())));
        RoundingMode roundingMode = RoundingMode.CEILING;
        float floatValue = bigDecimal.setScale(4, RoundingMode.HALF_UP).floatValue();
        StackAnimationController stackAnimationController2 = this.mStackAnimationController;
        Objects.requireNonNull(stackAnimationController2);
        float f2 = stackAnimationController2.mStackPosition.y;
        BubblePositioner bubblePositioner2 = this.mPositioner;
        Objects.requireNonNull(bubblePositioner2);
        BigDecimal bigDecimal2 = new BigDecimal((double) (f2 / ((float) bubblePositioner2.mPositionRect.height())));
        RoundingMode roundingMode2 = RoundingMode.CEILING;
        float floatValue2 = bigDecimal2.setScale(4, RoundingMode.HALF_UP).floatValue();
        if (bubbleViewProvider == null) {
            Objects.requireNonNull(bubbleData.mLogger);
            FrameworkStatsLog.write(149, str2, (String) null, 0, 0, bubbleCount, i, floatValue, floatValue2, false, false, false);
            return;
        }
        Objects.requireNonNull(bubbleData);
        if (!bubbleViewProvider.getKey().equals("Overflow")) {
            Bubble bubble = (Bubble) bubbleViewProvider;
            Objects.requireNonNull(bubbleData.mLogger);
            FrameworkStatsLog.write(149, str2, bubble.mChannelId, bubble.mNotificationId, bubbleIndex, bubbleCount, i, floatValue, floatValue2, bubble.showInShade(), false, false);
        } else if (i == 3) {
            BubbleLogger bubbleLogger = bubbleData.mLogger;
            int i2 = bubbleData.mCurrentUserId;
            Objects.requireNonNull(bubbleLogger);
            bubbleLogger.mUiEventLogger.log(BubbleLogger.Event.BUBBLE_OVERFLOW_SELECTED, i2, str2);
        }
    }

    public final void releaseAnimatingOutBubbleBuffer() {
        SurfaceControl.ScreenshotHardwareBuffer screenshotHardwareBuffer = this.mAnimatingOutBubbleBuffer;
        if (screenshotHardwareBuffer != null && !screenshotHardwareBuffer.getHardwareBuffer().isClosed()) {
            this.mAnimatingOutBubbleBuffer.getHardwareBuffer().close();
        }
    }

    public final void requestUpdate() {
        if (!this.mViewUpdatedRequested && !this.mIsExpansionAnimating) {
            this.mViewUpdatedRequested = true;
            getViewTreeObserver().addOnPreDrawListener(this.mViewUpdater);
            invalidate();
        }
    }

    public final void screenshotAnimatingOutBubbleIntoSurface(BubbleStackView$$ExternalSyntheticLambda27 bubbleStackView$$ExternalSyntheticLambda27) {
        BubbleViewProvider bubbleViewProvider;
        int[] iArr;
        if (!this.mIsExpanded || (bubbleViewProvider = this.mExpandedBubble) == null || bubbleViewProvider.getExpandedView() == null) {
            bubbleStackView$$ExternalSyntheticLambda27.accept(Boolean.FALSE);
            return;
        }
        BubbleExpandedView expandedView = this.mExpandedBubble.getExpandedView();
        if (this.mAnimatingOutBubbleBuffer != null) {
            releaseAnimatingOutBubbleBuffer();
        }
        try {
            this.mAnimatingOutBubbleBuffer = expandedView.snapshotActivitySurface();
        } catch (Exception e) {
            Log.wtf("Bubbles", e);
            bubbleStackView$$ExternalSyntheticLambda27.accept(Boolean.FALSE);
        }
        SurfaceControl.ScreenshotHardwareBuffer screenshotHardwareBuffer = this.mAnimatingOutBubbleBuffer;
        if (screenshotHardwareBuffer == null || screenshotHardwareBuffer.getHardwareBuffer() == null) {
            bubbleStackView$$ExternalSyntheticLambda27.accept(Boolean.FALSE);
            return;
        }
        FrameLayout frameLayout = this.mAnimatingOutSurfaceContainer;
        Function1<Object, ? extends PhysicsAnimator<?>> function1 = PhysicsAnimator.instanceConstructor;
        PhysicsAnimator.Companion.getInstance(frameLayout).cancel();
        this.mAnimatingOutSurfaceContainer.setScaleX(1.0f);
        this.mAnimatingOutSurfaceContainer.setScaleY(1.0f);
        this.mAnimatingOutSurfaceContainer.setTranslationX((float) this.mExpandedViewContainer.getPaddingLeft());
        this.mAnimatingOutSurfaceContainer.setTranslationY(0.0f);
        BubbleExpandedView expandedView2 = this.mExpandedBubble.getExpandedView();
        Objects.requireNonNull(expandedView2);
        if (expandedView2.mIsOverflow) {
            iArr = expandedView2.mOverflowView.getLocationOnScreen();
        } else {
            TaskView taskView = expandedView2.mTaskView;
            if (taskView != null) {
                iArr = taskView.getLocationOnScreen();
            } else {
                iArr = new int[]{0, 0};
            }
        }
        this.mAnimatingOutSurfaceContainer.setTranslationY((float) (iArr[1] - this.mAnimatingOutSurfaceView.getLocationOnScreen()[1]));
        this.mAnimatingOutSurfaceView.getLayoutParams().width = this.mAnimatingOutBubbleBuffer.getHardwareBuffer().getWidth();
        this.mAnimatingOutSurfaceView.getLayoutParams().height = this.mAnimatingOutBubbleBuffer.getHardwareBuffer().getHeight();
        this.mAnimatingOutSurfaceView.requestLayout();
        post(new BubbleStackView$$ExternalSyntheticLambda22(this, bubbleStackView$$ExternalSyntheticLambda27, 0));
    }

    public final void setBubbleSuppressed(Bubble bubble, boolean z) {
        if (z) {
            this.mBubbleContainer.removeViewAt(getBubbleIndex(bubble));
            updateExpandedView();
            return;
        }
        Objects.requireNonNull(bubble);
        BadgedImageView badgedImageView = bubble.mIconView;
        if (badgedImageView != null) {
            if (badgedImageView.getParent() != null) {
                Log.e("Bubbles", "Bubble is already added to parent. Can't unsuppress: " + bubble);
                return;
            }
            int indexOf = this.mBubbleData.getBubbles().indexOf(bubble);
            PhysicsAnimationLayout physicsAnimationLayout = this.mBubbleContainer;
            BadgedImageView badgedImageView2 = bubble.mIconView;
            BubblePositioner bubblePositioner = this.mPositioner;
            Objects.requireNonNull(bubblePositioner);
            int i = bubblePositioner.mBubbleSize;
            BubblePositioner bubblePositioner2 = this.mPositioner;
            Objects.requireNonNull(bubblePositioner2);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i, bubblePositioner2.mBubbleSize);
            Objects.requireNonNull(physicsAnimationLayout);
            physicsAnimationLayout.addViewInternal(badgedImageView2, indexOf, layoutParams, false);
            updateBubbleShadows(false);
            requestUpdate();
        }
    }

    public final void setExpanded(boolean z) {
        int i;
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        int i2;
        int i3;
        if (!z) {
            releaseAnimatingOutBubbleBuffer();
        }
        if (z != this.mIsExpanded) {
            hideCurrentInputMethod();
            BubbleController bubbleController = this.mBubbleController;
            Objects.requireNonNull(bubbleController);
            BubblesManager.C17525 r0 = (BubblesManager.C17525) bubbleController.mSysuiProxy;
            Objects.requireNonNull(r0);
            executor2.execute(new BubblesManager$5$$ExternalSyntheticLambda5(r0, sysUiState2, z));
            if (this.mIsExpanded) {
                this.mDelayedAnimationExecutor.removeCallbacks(this.mDelayedAnimation);
                this.mIsExpansionAnimating = false;
                this.mIsBubbleSwitchAnimating = false;
                ManageEducationView manageEducationView = this.mManageEduView;
                if (manageEducationView != null && manageEducationView.getVisibility() == 0) {
                    this.mManageEduView.hide();
                }
                showManageMenu(false);
                this.mIsExpanded = false;
                this.mIsExpansionAnimating = true;
                showScrim(false);
                PhysicsAnimationLayout physicsAnimationLayout = this.mBubbleContainer;
                Objects.requireNonNull(physicsAnimationLayout);
                PhysicsAnimationLayout.PhysicsAnimationController physicsAnimationController = physicsAnimationLayout.mController;
                if (physicsAnimationController != null) {
                    physicsAnimationLayout.cancelAllAnimationsOfProperties((DynamicAnimation.ViewProperty[]) physicsAnimationController.getAnimatedProperties().toArray(new DynamicAnimation.ViewProperty[0]));
                }
                FrameLayout frameLayout = this.mAnimatingOutSurfaceContainer;
                Function1<Object, ? extends PhysicsAnimator<?>> function1 = PhysicsAnimator.instanceConstructor;
                PhysicsAnimator.Companion.getInstance(frameLayout).cancel();
                this.mAnimatingOutSurfaceContainer.setScaleX(0.0f);
                this.mAnimatingOutSurfaceContainer.setScaleY(0.0f);
                ExpandedAnimationController expandedAnimationController = this.mExpandedAnimationController;
                Objects.requireNonNull(expandedAnimationController);
                expandedAnimationController.mPreparingToCollapse = true;
                ExpandedAnimationController expandedAnimationController2 = this.mExpandedAnimationController;
                StackAnimationController stackAnimationController = this.mStackAnimationController;
                Objects.requireNonNull(stackAnimationController);
                Objects.requireNonNull(stackAnimationController.mPositioner);
                PointF pointF = stackAnimationController.mStackPosition;
                boolean isFirstChildXLeftOfCenter = stackAnimationController.mLayout.isFirstChildXLeftOfCenter(pointF.x);
                RectF allowableStackPositionRegion = stackAnimationController.getAllowableStackPositionRegion();
                if (isFirstChildXLeftOfCenter) {
                    f5 = allowableStackPositionRegion.left;
                } else {
                    f5 = allowableStackPositionRegion.right;
                }
                pointF.x = f5;
                PipTaskOrganizer$$ExternalSyntheticLambda3 pipTaskOrganizer$$ExternalSyntheticLambda3 = new PipTaskOrganizer$$ExternalSyntheticLambda3(this, 4);
                Objects.requireNonNull(expandedAnimationController2);
                expandedAnimationController2.mAnimatingExpand = false;
                expandedAnimationController2.mPreparingToCollapse = false;
                expandedAnimationController2.mAnimatingCollapse = true;
                expandedAnimationController2.mAfterCollapse = pipTaskOrganizer$$ExternalSyntheticLambda3;
                expandedAnimationController2.mCollapsePoint = pointF;
                expandedAnimationController2.startOrUpdatePathAnimation(false);
                BubbleViewProvider bubbleViewProvider = this.mExpandedBubble;
                if (bubbleViewProvider == null || !"Overflow".equals(bubbleViewProvider.getKey())) {
                    i2 = this.mBubbleData.getBubbles().indexOf(this.mExpandedBubble);
                } else {
                    i2 = this.mBubbleData.getBubbles().size();
                }
                PointF expandedBubbleXY = this.mPositioner.getExpandedBubbleXY(i2, getState());
                if (this.mPositioner.showBubblesVertically()) {
                    float f6 = (((float) this.mBubbleSize) / 2.0f) + expandedBubbleXY.y;
                    if (this.mStackOnLeftOrWillBe) {
                        BubblePositioner bubblePositioner = this.mPositioner;
                        Objects.requireNonNull(bubblePositioner);
                        i3 = bubblePositioner.mPositionRect.left + this.mBubbleSize + this.mExpandedViewPadding;
                    } else {
                        BubblePositioner bubblePositioner2 = this.mPositioner;
                        Objects.requireNonNull(bubblePositioner2);
                        i3 = (bubblePositioner2.mPositionRect.right - this.mBubbleSize) - this.mExpandedViewPadding;
                    }
                    this.mExpandedViewContainerMatrix.setScale(1.0f, 1.0f, (float) i3, f6);
                } else {
                    AnimatableScaleMatrix animatableScaleMatrix = this.mExpandedViewContainerMatrix;
                    float f7 = expandedBubbleXY.x;
                    float f8 = (float) this.mBubbleSize;
                    animatableScaleMatrix.setScale(1.0f, 1.0f, (f8 / 2.0f) + f7, expandedBubbleXY.y + f8 + ((float) this.mExpandedViewPadding));
                }
                this.mExpandedViewAlphaAnimator.reverse();
                if (this.mExpandedBubble.getExpandedView() != null) {
                    this.mExpandedBubble.getExpandedView().setContentVisibility(false);
                }
                PhysicsAnimator.Companion.getInstance(this.mExpandedViewContainerMatrix).cancel();
                PhysicsAnimator instance = PhysicsAnimator.Companion.getInstance(this.mExpandedViewContainerMatrix);
                instance.spring(AnimatableScaleMatrix.SCALE_X, 449.99997f, 0.0f, this.mScaleOutSpringConfig);
                instance.spring(AnimatableScaleMatrix.SCALE_Y, 449.99997f, 0.0f, this.mScaleOutSpringConfig);
                instance.updateListeners.add(new BubbleStackView$$ExternalSyntheticLambda12(this));
                instance.withEndActions(new WMShell$7$$ExternalSyntheticLambda0(this, 7));
                instance.start();
                logBubbleEvent(this.mExpandedBubble, 4);
            } else {
                this.mDelayedAnimationExecutor.removeCallbacks(this.mDelayedAnimation);
                this.mIsExpansionAnimating = false;
                this.mIsBubbleSwitchAnimating = false;
                boolean showBubblesVertically = this.mPositioner.showBubblesVertically();
                this.mIsExpanded = true;
                if (isStackEduShowing()) {
                    this.mStackEduView.hide(true);
                }
                this.mIsExpansionAnimating = true;
                hideFlyoutImmediate();
                updateExpandedBubble();
                updateExpandedView();
                showScrim(true);
                updateZOrder();
                updateBadges(false);
                this.mBubbleContainer.setActiveController(this.mExpandedAnimationController);
                updateOverflowVisibility();
                updatePointerPosition(false);
                this.mExpandedAnimationController.expandFromStack(new WMShell$8$$ExternalSyntheticLambda0(this, 6));
                BubbleViewProvider bubbleViewProvider2 = this.mExpandedBubble;
                if (bubbleViewProvider2 == null || !"Overflow".equals(bubbleViewProvider2.getKey())) {
                    i = getBubbleIndex(this.mExpandedBubble);
                } else {
                    i = this.mBubbleData.getBubbles().size();
                }
                PointF expandedBubbleXY2 = this.mPositioner.getExpandedBubbleXY(i, getState());
                BubblePositioner bubblePositioner3 = this.mPositioner;
                BubbleViewProvider bubbleViewProvider3 = this.mExpandedBubble;
                if (bubblePositioner3.showBubblesVertically()) {
                    f = expandedBubbleXY2.y;
                } else {
                    f = expandedBubbleXY2.x;
                }
                float expandedViewY = bubblePositioner3.getExpandedViewY(bubbleViewProvider3, f);
                this.mExpandedViewContainer.setTranslationX(0.0f);
                this.mExpandedViewContainer.setTranslationY(expandedViewY);
                this.mExpandedViewContainer.setAlpha(1.0f);
                if (showBubblesVertically) {
                    StackAnimationController stackAnimationController2 = this.mStackAnimationController;
                    Objects.requireNonNull(stackAnimationController2);
                    f2 = stackAnimationController2.mStackPosition.y;
                } else {
                    StackAnimationController stackAnimationController3 = this.mStackAnimationController;
                    Objects.requireNonNull(stackAnimationController3);
                    f2 = stackAnimationController3.mStackPosition.x;
                }
                if (showBubblesVertically) {
                    f3 = expandedBubbleXY2.y;
                } else {
                    f3 = expandedBubbleXY2.x;
                }
                float abs = Math.abs(f3 - f2);
                long j = 0;
                if (getWidth() > 0) {
                    j = (long) (((abs / ((float) getWidth())) * 30.0f) + 210.00002f);
                }
                if (showBubblesVertically) {
                    if (this.mStackOnLeftOrWillBe) {
                        f4 = expandedBubbleXY2.x + ((float) this.mBubbleSize) + ((float) this.mExpandedViewPadding);
                    } else {
                        f4 = expandedBubbleXY2.x - ((float) this.mExpandedViewPadding);
                    }
                    this.mExpandedViewContainerMatrix.setScale(0.9f, 0.9f, f4, (((float) this.mBubbleSize) / 2.0f) + expandedBubbleXY2.y);
                } else {
                    AnimatableScaleMatrix animatableScaleMatrix2 = this.mExpandedViewContainerMatrix;
                    float f9 = expandedBubbleXY2.x;
                    float f10 = (float) this.mBubbleSize;
                    animatableScaleMatrix2.setScale(0.9f, 0.9f, (f10 / 2.0f) + f9, expandedBubbleXY2.y + f10 + ((float) this.mExpandedViewPadding));
                }
                this.mExpandedViewContainer.setAnimationMatrix(this.mExpandedViewContainerMatrix);
                if (this.mExpandedBubble.getExpandedView() != null) {
                    BubbleExpandedView expandedView = this.mExpandedBubble.getExpandedView();
                    Objects.requireNonNull(expandedView);
                    TaskView taskView = expandedView.mTaskView;
                    if (taskView != null) {
                        taskView.setAlpha(0.0f);
                    }
                    expandedView.mPointerView.setAlpha(0.0f);
                    expandedView.setAlpha(0.0f);
                    BubbleExpandedView expandedView2 = this.mExpandedBubble.getExpandedView();
                    Objects.requireNonNull(expandedView2);
                    expandedView2.mIsAlphaAnimating = true;
                }
                BubbleStackView$$ExternalSyntheticLambda25 bubbleStackView$$ExternalSyntheticLambda25 = new BubbleStackView$$ExternalSyntheticLambda25(this, showBubblesVertically, f3);
                this.mDelayedAnimation = bubbleStackView$$ExternalSyntheticLambda25;
                this.mDelayedAnimationExecutor.executeDelayed(bubbleStackView$$ExternalSyntheticLambda25, j);
                logBubbleEvent(this.mExpandedBubble, 3);
                logBubbleEvent(this.mExpandedBubble, 15);
            }
            BubbleViewProvider bubbleViewProvider4 = this.mExpandedBubble;
            boolean z2 = this.mIsExpanded;
            Bubbles.BubbleExpandListener bubbleExpandListener = this.mExpandListener;
            if (bubbleExpandListener != null && bubbleViewProvider4 != null) {
                bubbleExpandListener.onBubbleExpandChanged(z2, bubbleViewProvider4.getKey());
            }
        }
    }

    public final void setFlyoutStateForDragLength(float f) {
        boolean z;
        float f2;
        int i;
        if (this.mFlyout.getWidth() > 0) {
            boolean isStackOnLeftSide = this.mStackAnimationController.isStackOnLeftSide();
            this.mFlyoutDragDeltaX = f;
            if (isStackOnLeftSide) {
                f = -f;
            }
            float width = f / ((float) this.mFlyout.getWidth());
            float f3 = 0.0f;
            this.mFlyout.setCollapsePercent(Math.min(1.0f, Math.max(0.0f, width)));
            int i2 = (width > 0.0f ? 1 : (width == 0.0f ? 0 : -1));
            if (i2 < 0 || width > 1.0f) {
                int i3 = (width > 1.0f ? 1 : (width == 1.0f ? 0 : -1));
                boolean z2 = false;
                int i4 = 1;
                if (i3 > 0) {
                    z = true;
                } else {
                    z = false;
                }
                if ((isStackOnLeftSide && i3 > 0) || (!isStackOnLeftSide && i2 < 0)) {
                    z2 = true;
                }
                if (z) {
                    f2 = width - 1.0f;
                } else {
                    f2 = width * -1.0f;
                }
                if (z2) {
                    i = -1;
                } else {
                    i = 1;
                }
                float f4 = f2 * ((float) i);
                float width2 = (float) this.mFlyout.getWidth();
                if (z) {
                    i4 = 2;
                }
                f3 = (width2 / (8.0f / ((float) i4))) * f4;
            }
            BubbleFlyoutView bubbleFlyoutView = this.mFlyout;
            Objects.requireNonNull(bubbleFlyoutView);
            bubbleFlyoutView.setTranslationX(bubbleFlyoutView.mRestingTranslationX + f3);
        }
    }

    public final void setUpDismissView() {
        DismissView dismissView = this.mDismissView;
        if (dismissView != null) {
            removeView(dismissView);
        }
        this.mDismissView = new DismissView(getContext());
        int dimensionPixelSize = getResources().getDimensionPixelSize(C1777R.dimen.bubble_elevation);
        addView(this.mDismissView);
        this.mDismissView.setElevation((float) dimensionPixelSize);
        int i = Settings.Secure.getInt(getContext().getContentResolver(), "bubble_dismiss_radius", this.mBubbleSize * 2);
        DismissView dismissView2 = this.mDismissView;
        Objects.requireNonNull(dismissView2);
        this.mMagneticTarget = new MagnetizedObject.MagneticTarget(dismissView2.circle, i);
        this.mBubbleContainer.bringToFront();
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public final void setUpFlyout() {
        BubbleFlyoutView bubbleFlyoutView = this.mFlyout;
        if (bubbleFlyoutView != null) {
            removeView(bubbleFlyoutView);
        }
        BubbleFlyoutView bubbleFlyoutView2 = new BubbleFlyoutView(getContext(), this.mPositioner);
        this.mFlyout = bubbleFlyoutView2;
        bubbleFlyoutView2.setVisibility(8);
        this.mFlyout.setOnClickListener(this.mFlyoutClickListener);
        this.mFlyout.setOnTouchListener(this.mFlyoutTouchListener);
        addView(this.mFlyout, new FrameLayout.LayoutParams(-2, -2));
    }

    public final void setUpManageMenu() {
        ViewGroup viewGroup = this.mManageMenu;
        if (viewGroup != null) {
            removeView(viewGroup);
        }
        ViewGroup viewGroup2 = (ViewGroup) LayoutInflater.from(getContext()).inflate(C1777R.layout.bubble_manage_menu, this, false);
        this.mManageMenu = viewGroup2;
        viewGroup2.setVisibility(4);
        PhysicsAnimator.getInstance(this.mManageMenu).defaultSpring = this.mManageSpringConfig;
        this.mManageMenu.setOutlineProvider(new ViewOutlineProvider() {
            public final void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), (float) BubbleStackView.this.mCornerRadius);
            }
        });
        this.mManageMenu.setClipToOutline(true);
        this.mManageMenu.findViewById(C1777R.C1779id.bubble_manage_menu_dismiss_container).setOnClickListener(new BubbleStackView$$ExternalSyntheticLambda4(this, 0));
        this.mManageMenu.findViewById(C1777R.C1779id.bubble_manage_menu_dont_bubble_container).setOnClickListener(new BubbleStackView$$ExternalSyntheticLambda5(this, 0));
        this.mManageMenu.findViewById(C1777R.C1779id.bubble_manage_menu_settings_container).setOnClickListener(new BubbleStackView$$ExternalSyntheticLambda6(this, 0));
        this.mManageSettingsIcon = (ImageView) this.mManageMenu.findViewById(C1777R.C1779id.bubble_manage_menu_settings_icon);
        this.mManageSettingsText = (TextView) this.mManageMenu.findViewById(C1777R.C1779id.bubble_manage_menu_settings_name);
        this.mManageMenu.setLayoutDirection(3);
        addView(this.mManageMenu);
    }

    public final void setupLocalMenu(AccessibilityNodeInfo accessibilityNodeInfo) {
        Resources resources = this.mContext.getResources();
        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(C1777R.C1779id.action_move_top_left, resources.getString(C1777R.string.bubble_accessibility_action_move_top_left)));
        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(C1777R.C1779id.action_move_top_right, resources.getString(C1777R.string.bubble_accessibility_action_move_top_right)));
        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(C1777R.C1779id.action_move_bottom_left, resources.getString(C1777R.string.bubble_accessibility_action_move_bottom_left)));
        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(C1777R.C1779id.action_move_bottom_right, resources.getString(C1777R.string.bubble_accessibility_action_move_bottom_right)));
        accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_DISMISS);
        if (this.mIsExpanded) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_COLLAPSE);
        } else {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_EXPAND);
        }
    }

    @VisibleForTesting
    public void showManageMenu(boolean z) {
        PathInterpolator pathInterpolator;
        float f;
        boolean z2;
        float f2;
        int i;
        Rect rect;
        Bubble bubbleInStackWithKey;
        this.mShowingManage = z;
        BubbleViewProvider bubbleViewProvider = this.mExpandedBubble;
        if (bubbleViewProvider == null || bubbleViewProvider.getExpandedView() == null) {
            this.mManageMenu.setVisibility(4);
            this.mManageMenuScrim.setVisibility(4);
            BubbleController bubbleController = this.mBubbleController;
            Objects.requireNonNull(bubbleController);
            BubblesManager.C17525 r9 = (BubblesManager.C17525) bubbleController.mSysuiProxy;
            Objects.requireNonNull(r9);
            executor2.execute(new ScrimView$$ExternalSyntheticLambda2(r9, sysUiState2, false, 2));
            return;
        }
        if (z) {
            this.mManageMenuScrim.setVisibility(0);
            this.mManageMenuScrim.setTranslationZ(this.mManageMenu.getElevation() - 1.0f);
        }
        BubbleStackView$$ExternalSyntheticLambda23 bubbleStackView$$ExternalSyntheticLambda23 = new BubbleStackView$$ExternalSyntheticLambda23(this, z, 0);
        BubbleController bubbleController2 = this.mBubbleController;
        Objects.requireNonNull(bubbleController2);
        BubblesManager.C17525 r4 = (BubblesManager.C17525) bubbleController2.mSysuiProxy;
        Objects.requireNonNull(r4);
        executor2.execute(new ScrimView$$ExternalSyntheticLambda2(r4, sysUiState2, z, 2));
        ViewPropertyAnimator animate = this.mManageMenuScrim.animate();
        if (z) {
            pathInterpolator = Interpolators.ALPHA_IN;
        } else {
            pathInterpolator = Interpolators.ALPHA_OUT;
        }
        ViewPropertyAnimator interpolator = animate.setInterpolator(pathInterpolator);
        if (z) {
            f = 0.6f;
        } else {
            f = 0.0f;
        }
        interpolator.alpha(f).withEndAction(bubbleStackView$$ExternalSyntheticLambda23).start();
        if (z && (bubbleInStackWithKey = this.mBubbleData.getBubbleInStackWithKey(this.mExpandedBubble.getKey())) != null) {
            this.mManageSettingsIcon.setImageBitmap(bubbleInStackWithKey.mRawBadgeBitmap);
            this.mManageSettingsText.setText(getResources().getString(C1777R.string.bubbles_app_settings, new Object[]{bubbleInStackWithKey.mAppName}));
        }
        BubbleExpandedView expandedView = this.mExpandedBubble.getExpandedView();
        Objects.requireNonNull(expandedView);
        if (expandedView.mTaskView != null) {
            BubbleExpandedView expandedView2 = this.mExpandedBubble.getExpandedView();
            Objects.requireNonNull(expandedView2);
            TaskView taskView = expandedView2.mTaskView;
            if (this.mShowingManage) {
                rect = new Rect(0, 0, getWidth(), getHeight());
            } else {
                rect = null;
            }
            Objects.requireNonNull(taskView);
            taskView.mObscuredTouchRect = rect;
        }
        if (getResources().getConfiguration().getLayoutDirection() == 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        BubbleExpandedView expandedView3 = this.mExpandedBubble.getExpandedView();
        Rect rect2 = this.mTempRect;
        Objects.requireNonNull(expandedView3);
        expandedView3.mManageButton.getBoundsOnScreen(rect2);
        BubbleExpandedView expandedView4 = this.mExpandedBubble.getExpandedView();
        Objects.requireNonNull(expandedView4);
        float marginStart = (float) ((LinearLayout.LayoutParams) expandedView4.mManageButton.getLayoutParams()).getMarginStart();
        if (z2) {
            f2 = (float) this.mTempRect.left;
        } else {
            f2 = ((float) this.mTempRect.right) + marginStart;
            marginStart = (float) this.mManageMenu.getWidth();
        }
        float f3 = f2 - marginStart;
        float height = (float) (this.mTempRect.bottom - this.mManageMenu.getHeight());
        if (z2) {
            i = 1;
        } else {
            i = -1;
        }
        float width = ((float) (this.mManageMenu.getWidth() * i)) / 4.0f;
        if (z) {
            this.mManageMenu.setScaleX(0.5f);
            this.mManageMenu.setScaleY(0.5f);
            this.mManageMenu.setTranslationX(f3 - width);
            ViewGroup viewGroup = this.mManageMenu;
            viewGroup.setTranslationY((((float) viewGroup.getHeight()) / 4.0f) + height);
            this.mManageMenu.setAlpha(0.0f);
            ViewGroup viewGroup2 = this.mManageMenu;
            Function1<Object, ? extends PhysicsAnimator<?>> function1 = PhysicsAnimator.instanceConstructor;
            PhysicsAnimator instance = PhysicsAnimator.Companion.getInstance(viewGroup2);
            instance.spring(DynamicAnimation.ALPHA, 1.0f);
            instance.spring(DynamicAnimation.SCALE_X, 1.0f);
            instance.spring(DynamicAnimation.SCALE_Y, 1.0f);
            instance.spring(DynamicAnimation.TRANSLATION_X, f3);
            instance.spring(DynamicAnimation.TRANSLATION_Y, height);
            instance.withEndActions(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda0(this, 6));
            instance.start();
            this.mManageMenu.setVisibility(0);
            return;
        }
        ViewGroup viewGroup3 = this.mManageMenu;
        Function1<Object, ? extends PhysicsAnimator<?>> function12 = PhysicsAnimator.instanceConstructor;
        PhysicsAnimator instance2 = PhysicsAnimator.Companion.getInstance(viewGroup3);
        instance2.spring(DynamicAnimation.ALPHA, 0.0f);
        instance2.spring(DynamicAnimation.SCALE_X, 0.5f);
        instance2.spring(DynamicAnimation.SCALE_Y, 0.5f);
        instance2.spring(DynamicAnimation.TRANSLATION_X, f3 - width);
        instance2.spring(DynamicAnimation.TRANSLATION_Y, (((float) this.mManageMenu.getHeight()) / 4.0f) + height);
        instance2.withEndActions(new BubbleStackView$$ExternalSyntheticLambda15(this, 0));
        instance2.start();
    }

    public final void showNewlySelectedBubble(BubbleViewProvider bubbleViewProvider) {
        BubbleViewProvider bubbleViewProvider2 = this.mExpandedBubble;
        this.mExpandedBubble = bubbleViewProvider;
        if (this.mIsExpanded) {
            hideCurrentInputMethod();
            this.mExpandedViewContainer.setAlpha(0.0f);
            SurfaceSynchronizer surfaceSynchronizer = this.mSurfaceSynchronizer;
            BubbleStackView$$ExternalSyntheticLambda24 bubbleStackView$$ExternalSyntheticLambda24 = new BubbleStackView$$ExternalSyntheticLambda24(this, bubbleViewProvider2, bubbleViewProvider);
            Objects.requireNonNull((C18031) surfaceSynchronizer);
            Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback(bubbleStackView$$ExternalSyntheticLambda24) {
                public int mFrameWait = 2;
                public final /* synthetic */ Runnable val$callback;

                {
                    this.val$callback = r1;
                }

                public final void doFrame(long j) {
                    int i = this.mFrameWait - 1;
                    this.mFrameWait = i;
                    if (i > 0) {
                        Choreographer.getInstance().postFrameCallback(this);
                    } else {
                        this.val$callback.run();
                    }
                }
            });
        }
    }

    public final void showScrim(boolean z) {
        if (z) {
            this.mScrim.animate().setInterpolator(Interpolators.ALPHA_IN).alpha(0.6f).start();
        } else {
            this.mScrim.animate().alpha(0.0f).setInterpolator(Interpolators.ALPHA_OUT).start();
        }
    }

    public final void updateExpandedBubble() {
        BubbleViewProvider bubbleViewProvider;
        this.mExpandedViewContainer.removeAllViews();
        if (this.mIsExpanded && (bubbleViewProvider = this.mExpandedBubble) != null && bubbleViewProvider.getExpandedView() != null) {
            BubbleExpandedView expandedView = this.mExpandedBubble.getExpandedView();
            expandedView.setContentVisibility(false);
            boolean z = !this.mIsExpansionAnimating;
            expandedView.mIsAlphaAnimating = z;
            if (!z) {
                expandedView.setContentVisibility(expandedView.mIsContentVisible);
            }
            this.mExpandedViewContainerMatrix.setScaleX(0.0f);
            this.mExpandedViewContainerMatrix.setScaleY(0.0f);
            this.mExpandedViewContainerMatrix.setTranslate(0.0f, 0.0f);
            this.mExpandedViewContainer.setVisibility(4);
            this.mExpandedViewContainer.setAlpha(0.0f);
            this.mExpandedViewContainer.addView(expandedView);
            expandedView.mManageButton.setOnClickListener(new BubbleStackView$$ExternalSyntheticLambda3(this, 0));
            if (!this.mIsExpansionAnimating) {
                SurfaceSynchronizer surfaceSynchronizer = this.mSurfaceSynchronizer;
                ScreenDecorations$$ExternalSyntheticLambda3 screenDecorations$$ExternalSyntheticLambda3 = new ScreenDecorations$$ExternalSyntheticLambda3(this, 4);
                Objects.requireNonNull((C18031) surfaceSynchronizer);
                Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback(screenDecorations$$ExternalSyntheticLambda3) {
                    public int mFrameWait = 2;
                    public final /* synthetic */ Runnable val$callback;

                    {
                        this.val$callback = r1;
                    }

                    public final void doFrame(long j) {
                        int i = this.mFrameWait - 1;
                        this.mFrameWait = i;
                        if (i > 0) {
                            Choreographer.getInstance().postFrameCallback(this);
                        } else {
                            this.val$callback.run();
                        }
                    }
                });
            }
        }
    }

    public final void updateExpandedView() {
        boolean z;
        int[] iArr;
        float f;
        boolean z2;
        Drawable drawable;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        float f2;
        float f3;
        int i6;
        int i7;
        int i8;
        BubbleViewProvider bubbleViewProvider = this.mExpandedBubble;
        if (bubbleViewProvider == null || !"Overflow".equals(bubbleViewProvider.getKey())) {
            z = false;
        } else {
            z = true;
        }
        BubblePositioner bubblePositioner = this.mPositioner;
        boolean isStackOnLeftSide = this.mStackAnimationController.isStackOnLeftSide();
        Objects.requireNonNull(bubblePositioner);
        int i9 = bubblePositioner.mPointerHeight - bubblePositioner.mPointerOverlap;
        if (bubblePositioner.mIsLargeScreen) {
            iArr = bubblePositioner.mPaddings;
            if (isStackOnLeftSide) {
                i6 = bubblePositioner.mExpandedViewLargeScreenInset - i9;
            } else {
                i6 = bubblePositioner.mExpandedViewLargeScreenInset;
            }
            iArr[0] = i6;
            iArr[1] = 0;
            if (isStackOnLeftSide) {
                i7 = bubblePositioner.mExpandedViewLargeScreenInset;
            } else {
                i7 = bubblePositioner.mExpandedViewLargeScreenInset - i9;
            }
            iArr[2] = i7;
            if (z) {
                i8 = bubblePositioner.mExpandedViewPadding;
            } else {
                i8 = 0;
            }
            iArr[3] = i8;
        } else {
            Insets insets = bubblePositioner.mInsets;
            int i10 = insets.left;
            int i11 = bubblePositioner.mExpandedViewPadding;
            int i12 = i10 + i11;
            int i13 = insets.right + i11;
            if (z) {
                i4 = bubblePositioner.mOverflowWidth;
            } else {
                i4 = bubblePositioner.mExpandedViewLargeScreenWidth;
            }
            float f4 = (float) i4;
            if (bubblePositioner.showBubblesVertically()) {
                if (!isStackOnLeftSide) {
                    i13 += bubblePositioner.mBubbleSize - i9;
                    float f5 = (float) i12;
                    if (z) {
                        f3 = ((float) (bubblePositioner.mPositionRect.width() - i13)) - f4;
                    } else {
                        f3 = 0.0f;
                    }
                    i12 = (int) (f5 + f3);
                } else {
                    i12 += bubblePositioner.mBubbleSize - i9;
                    float f6 = (float) i13;
                    if (z) {
                        f2 = ((float) (bubblePositioner.mPositionRect.width() - i12)) - f4;
                    } else {
                        f2 = 0.0f;
                    }
                    i13 = (int) (f6 + f2);
                }
            }
            int[] iArr2 = bubblePositioner.mPaddings;
            iArr2[0] = i12;
            if (bubblePositioner.showBubblesVertically()) {
                i5 = 0;
            } else {
                i5 = bubblePositioner.mPointerMargin;
            }
            iArr2[1] = i5;
            int[] iArr3 = bubblePositioner.mPaddings;
            iArr3[2] = i13;
            iArr3[3] = 0;
            iArr = iArr3;
        }
        this.mExpandedViewContainer.setPadding(iArr[0], iArr[1], iArr[2], iArr[3]);
        if (this.mIsExpansionAnimating) {
            FrameLayout frameLayout = this.mExpandedViewContainer;
            if (this.mIsExpanded) {
                i3 = 0;
            } else {
                i3 = 8;
            }
            frameLayout.setVisibility(i3);
        }
        BubbleViewProvider bubbleViewProvider2 = this.mExpandedBubble;
        if (!(bubbleViewProvider2 == null || bubbleViewProvider2.getExpandedView() == null)) {
            PointF expandedBubbleXY = this.mPositioner.getExpandedBubbleXY(getBubbleIndex(this.mExpandedBubble), getState());
            FrameLayout frameLayout2 = this.mExpandedViewContainer;
            BubblePositioner bubblePositioner2 = this.mPositioner;
            BubbleViewProvider bubbleViewProvider3 = this.mExpandedBubble;
            if (bubblePositioner2.showBubblesVertically()) {
                f = expandedBubbleXY.y;
            } else {
                f = expandedBubbleXY.x;
            }
            frameLayout2.setTranslationY(bubblePositioner2.getExpandedViewY(bubbleViewProvider3, f));
            this.mExpandedViewContainer.setTranslationX(0.0f);
            BubbleExpandedView expandedView = this.mExpandedBubble.getExpandedView();
            int[] locationOnScreen = this.mExpandedViewContainer.getLocationOnScreen();
            Objects.requireNonNull(expandedView);
            expandedView.mExpandedViewContainerLocation = locationOnScreen;
            expandedView.updateHeight();
            TaskView taskView = expandedView.mTaskView;
            if (taskView != null && taskView.getVisibility() == 0 && expandedView.mTaskView.isAttachedToWindow()) {
                expandedView.mTaskView.onLocationChanged();
            }
            if (expandedView.mIsOverflow) {
                BubbleOverflowContainerView bubbleOverflowContainerView = expandedView.mOverflowView;
                Objects.requireNonNull(bubbleOverflowContainerView);
                bubbleOverflowContainerView.requestFocus();
                int integer = bubbleOverflowContainerView.getResources().getInteger(C1777R.integer.bubbles_overflow_columns);
                RecyclerView recyclerView = bubbleOverflowContainerView.mRecyclerView;
                bubbleOverflowContainerView.getContext();
                recyclerView.setLayoutManager(new BubbleOverflowContainerView.OverflowGridLayoutManager(integer));
                Context context = bubbleOverflowContainerView.getContext();
                ArrayList arrayList = bubbleOverflowContainerView.mOverflowBubbles;
                BubbleController bubbleController = bubbleOverflowContainerView.mController;
                Objects.requireNonNull(bubbleController);
                BubbleOverflowAdapter bubbleOverflowAdapter = new BubbleOverflowAdapter(context, arrayList, new NavigationBar$$ExternalSyntheticLambda13(bubbleController, 2), bubbleOverflowContainerView.mController.getPositioner());
                bubbleOverflowContainerView.mAdapter = bubbleOverflowAdapter;
                bubbleOverflowContainerView.mRecyclerView.setAdapter(bubbleOverflowAdapter);
                bubbleOverflowContainerView.mOverflowBubbles.clear();
                ArrayList arrayList2 = bubbleOverflowContainerView.mOverflowBubbles;
                BubbleController bubbleController2 = bubbleOverflowContainerView.mController;
                Objects.requireNonNull(bubbleController2);
                arrayList2.addAll(bubbleController2.mBubbleData.getOverflowBubbles());
                bubbleOverflowContainerView.mAdapter.notifyDataSetChanged();
                BubbleController bubbleController3 = bubbleOverflowContainerView.mController;
                BubbleOverflowContainerView.C18021 r4 = bubbleOverflowContainerView.mDataListener;
                Objects.requireNonNull(bubbleController3);
                bubbleController3.mOverflowListener = r4;
                bubbleOverflowContainerView.updateEmptyStateVisibility();
                Resources resources = bubbleOverflowContainerView.getResources();
                if ((resources.getConfiguration().uiMode & 48) == 32) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                ImageView imageView = bubbleOverflowContainerView.mEmptyStateImage;
                if (z2) {
                    drawable = resources.getDrawable(C1777R.C1778drawable.bubble_ic_empty_overflow_dark);
                } else {
                    drawable = resources.getDrawable(C1777R.C1778drawable.bubble_ic_empty_overflow_light);
                }
                imageView.setImageDrawable(drawable);
                View findViewById = bubbleOverflowContainerView.findViewById(C1777R.C1779id.bubble_overflow_container);
                if (z2) {
                    i = resources.getColor(C1777R.color.bubbles_dark);
                } else {
                    i = resources.getColor(C1777R.color.bubbles_light);
                }
                findViewById.setBackgroundColor(i);
                TypedArray obtainStyledAttributes = bubbleOverflowContainerView.getContext().obtainStyledAttributes(new int[]{16844002, 16842808});
                int i14 = -16777216;
                if (z2) {
                    i2 = -16777216;
                } else {
                    i2 = -1;
                }
                int color = obtainStyledAttributes.getColor(0, i2);
                if (z2) {
                    i14 = -1;
                }
                int ensureTextContrast = ContrastColorUtil.ensureTextContrast(obtainStyledAttributes.getColor(1, i14), color, z2);
                obtainStyledAttributes.recycle();
                bubbleOverflowContainerView.setBackgroundColor(color);
                bubbleOverflowContainerView.mEmptyStateTitle.setTextColor(ensureTextContrast);
                bubbleOverflowContainerView.mEmptyStateSubtitle.setTextColor(ensureTextContrast);
            }
            updatePointerPosition(false);
        }
        this.mStackOnLeftOrWillBe = this.mStackAnimationController.isStackOnLeftSide();
    }

    public final void updateOverflow() {
        BubbleOverflow bubbleOverflow = this.mBubbleOverflow;
        Objects.requireNonNull(bubbleOverflow);
        bubbleOverflow.updateResources();
        BubbleExpandedView bubbleExpandedView = bubbleOverflow.expandedView;
        if (bubbleExpandedView != null) {
            bubbleExpandedView.applyThemeAttrs();
        }
        BadgedImageView iconView = bubbleOverflow.getIconView$1();
        if (iconView != null) {
            iconView.mBubbleIcon.setImageResource(C1777R.C1778drawable.bubble_ic_overflow_button);
        }
        bubbleOverflow.updateBtnTheme();
        this.mBubbleContainer.reorderView(this.mBubbleOverflow.getIconView$1(), this.mBubbleContainer.getChildCount() - 1);
        updateOverflowVisibility();
    }

    public final void updateOverflowVisibility() {
        int i;
        BubbleOverflow bubbleOverflow = this.mBubbleOverflow;
        if (this.mIsExpanded || this.mBubbleData.isShowingOverflow()) {
            i = 0;
        } else {
            i = 8;
        }
        Objects.requireNonNull(bubbleOverflow);
        BadgedImageView badgedImageView = bubbleOverflow.overflowBtn;
        if (badgedImageView != null) {
            badgedImageView.setVisibility(i);
        }
    }

    public final void updatePointerPosition(boolean z) {
        int bubbleIndex;
        float f;
        float f2;
        float f3;
        BubbleViewProvider bubbleViewProvider = this.mExpandedBubble;
        if (bubbleViewProvider != null && bubbleViewProvider.getExpandedView() != null && (bubbleIndex = getBubbleIndex(this.mExpandedBubble)) != -1) {
            PointF expandedBubbleXY = this.mPositioner.getExpandedBubbleXY(bubbleIndex, getState());
            if (this.mPositioner.showBubblesVertically()) {
                f = expandedBubbleXY.y;
            } else {
                f = expandedBubbleXY.x;
            }
            BubbleExpandedView expandedView = this.mExpandedBubble.getExpandedView();
            boolean z2 = this.mStackOnLeftOrWillBe;
            Objects.requireNonNull(expandedView);
            boolean showBubblesVertically = expandedView.mPositioner.showBubblesVertically();
            float f4 = 0.0f;
            if (!showBubblesVertically || !z2) {
                f2 = 0.0f;
            } else {
                f2 = ((float) expandedView.mPointerHeight) - expandedView.mPointerOverlap;
            }
            if (!showBubblesVertically || z2) {
                f3 = 0.0f;
            } else {
                f3 = ((float) expandedView.mPointerHeight) - expandedView.mPointerOverlap;
            }
            if (!showBubblesVertically) {
                f4 = ((float) expandedView.mPointerHeight) - expandedView.mPointerOverlap;
            }
            expandedView.setPadding((int) f2, (int) f4, (int) f3, 0);
            float pointerPosition = expandedView.mPositioner.getPointerPosition(f);
            if (expandedView.mPositioner.showBubblesVertically()) {
                pointerPosition -= expandedView.mPositioner.getExpandedViewY(expandedView.mBubble, f);
            }
            expandedView.post(new BubbleExpandedView$$ExternalSyntheticLambda1(expandedView, showBubblesVertically, z2, pointerPosition, z));
        }
    }

    public final void updateTemporarilyInvisibleAnimation(boolean z) {
        boolean z2;
        long j;
        removeCallbacks(this.mAnimateTemporarilyInvisibleImmediate);
        if (!this.mIsDraggingStack) {
            if (!this.mTemporarilyInvisible || this.mFlyout.getVisibility() == 0) {
                z2 = false;
            } else {
                z2 = true;
            }
            KeyguardStatusView$$ExternalSyntheticLambda0 keyguardStatusView$$ExternalSyntheticLambda0 = this.mAnimateTemporarilyInvisibleImmediate;
            if (!z2 || z) {
                j = 0;
            } else {
                j = 1000;
            }
            postDelayed(keyguardStatusView$$ExternalSyntheticLambda0, j);
        }
    }

    public static void $r8$lambda$DjZKjrr94LsLQZplD_wFyam4Xpc(BubbleStackView bubbleStackView, Bubble bubble) {
        Objects.requireNonNull(bubbleStackView);
        bubbleStackView.mAfterFlyoutHidden = null;
        BubbleViewProvider bubbleViewProvider = bubbleStackView.mBubbleToExpandAfterFlyoutCollapse;
        if (bubbleViewProvider != null) {
            bubbleStackView.mBubbleData.setSelectedBubble(bubbleViewProvider);
            bubbleStackView.mBubbleData.setExpanded(true);
            bubbleStackView.mBubbleToExpandAfterFlyoutCollapse = null;
        }
        Objects.requireNonNull(bubble);
        BadgedImageView badgedImageView = bubble.mIconView;
        if (badgedImageView != null) {
            badgedImageView.removeDotSuppressionFlag(BadgedImageView.SuppressionFlag.FLYOUT_VISIBLE);
        }
        bubbleStackView.updateTemporarilyInvisibleAnimation(false);
    }

    public static void $r8$lambda$VRRlSoOy6XdJCiRiE6ME0w93vig(BubbleStackView bubbleStackView) {
        Objects.requireNonNull(bubbleStackView);
        bubbleStackView.showManageMenu(false);
        BubbleData bubbleData = bubbleStackView.mBubbleData;
        Objects.requireNonNull(bubbleData);
        BubbleViewProvider bubbleViewProvider = bubbleData.mSelectedBubble;
        if (bubbleViewProvider != null && bubbleStackView.mBubbleData.hasBubbleInStackWithKey(bubbleViewProvider.getKey())) {
            Bubble bubble = (Bubble) bubbleViewProvider;
            Context context = bubbleStackView.mContext;
            Intent intent = new Intent("android.settings.APP_NOTIFICATION_BUBBLE_SETTINGS");
            intent.putExtra("android.provider.extra.APP_PACKAGE", bubble.mPackageName);
            int i = bubble.mAppUid;
            if (i == -1) {
                PackageManager packageManagerForUser = BubbleController.getPackageManagerForUser(context, bubble.mUser.getIdentifier());
                if (packageManagerForUser != null) {
                    try {
                        i = packageManagerForUser.getApplicationInfo(bubble.mShortcutInfo.getPackage(), 0).uid;
                    } catch (PackageManager.NameNotFoundException e) {
                        Log.e("Bubble", "cannot find uid", e);
                    }
                }
                i = -1;
            }
            if (i != -1) {
                intent.putExtra("app_uid", i);
            }
            intent.addFlags(134217728);
            intent.addFlags(268435456);
            intent.addFlags(536870912);
            bubbleStackView.mBubbleData.setExpanded(false);
            bubbleStackView.mContext.startActivityAsUser(intent, bubble.mUser);
            bubbleStackView.logBubbleEvent(bubbleViewProvider, 9);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x003b, code lost:
        if (r0 != false) goto L_0x003d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003f, code lost:
        if (r4.mExpandedBubble != null) goto L_0x0043;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void $r8$lambda$k7rtryEI4rtW4AORrn7yrpeihz4(com.android.p012wm.shell.bubbles.BubbleStackView r4) {
        /*
            java.util.Objects.requireNonNull(r4)
            boolean r0 = r4.mIsExpanded
            if (r0 == 0) goto L_0x0063
            com.android.wm.shell.bubbles.BubbleViewProvider r0 = r4.mExpandedBubble
            com.android.wm.shell.bubbles.BubbleExpandedView r0 = r0.getExpandedView()
            if (r0 == 0) goto L_0x0063
            boolean r0 = android.app.ActivityManager.isRunningInTestHarness()
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x0018
            goto L_0x0042
        L_0x0018:
            android.content.Context r0 = r4.mContext
            java.lang.String r3 = r0.getPackageName()
            android.content.SharedPreferences r0 = r0.getSharedPreferences(r3, r2)
            java.lang.String r3 = "HasSeenBubblesManageOnboarding"
            boolean r0 = r0.getBoolean(r3, r2)
            if (r0 == 0) goto L_0x003d
            android.content.Context r0 = r4.mContext
            android.content.ContentResolver r0 = r0.getContentResolver()
            java.lang.String r3 = "force_show_bubbles_user_education"
            int r0 = android.provider.Settings.Secure.getInt(r0, r3, r2)
            if (r0 == 0) goto L_0x003a
            r0 = r1
            goto L_0x003b
        L_0x003a:
            r0 = r2
        L_0x003b:
            if (r0 == 0) goto L_0x0042
        L_0x003d:
            com.android.wm.shell.bubbles.BubbleViewProvider r0 = r4.mExpandedBubble
            if (r0 == 0) goto L_0x0042
            goto L_0x0043
        L_0x0042:
            r1 = r2
        L_0x0043:
            if (r1 != 0) goto L_0x0046
            goto L_0x0063
        L_0x0046:
            com.android.wm.shell.bubbles.ManageEducationView r0 = r4.mManageEduView
            if (r0 != 0) goto L_0x0058
            com.android.wm.shell.bubbles.ManageEducationView r0 = new com.android.wm.shell.bubbles.ManageEducationView
            android.content.Context r1 = r4.mContext
            com.android.wm.shell.bubbles.BubblePositioner r2 = r4.mPositioner
            r0.<init>(r1, r2)
            r4.mManageEduView = r0
            r4.addView(r0)
        L_0x0058:
            com.android.wm.shell.bubbles.ManageEducationView r0 = r4.mManageEduView
            com.android.wm.shell.bubbles.BubbleViewProvider r4 = r4.mExpandedBubble
            com.android.wm.shell.bubbles.BubbleExpandedView r4 = r4.getExpandedView()
            r0.show(r4)
        L_0x0063:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.bubbles.BubbleStackView.$r8$lambda$k7rtryEI4rtW4AORrn7yrpeihz4(com.android.wm.shell.bubbles.BubbleStackView):void");
    }

    /* renamed from: -$$Nest$manimateDismissBubble  reason: not valid java name */
    public static void m290$$Nest$manimateDismissBubble(BubbleStackView bubbleStackView, View view, boolean z) {
        Objects.requireNonNull(bubbleStackView);
        bubbleStackView.mViewBeingDismissed = view;
        if (view != null) {
            if (z) {
                bubbleStackView.mDismissBubbleAnimator.removeAllListeners();
                bubbleStackView.mDismissBubbleAnimator.start();
                return;
            }
            bubbleStackView.mDismissBubbleAnimator.removeAllListeners();
            bubbleStackView.mDismissBubbleAnimator.addListener(new AnimatorListenerAdapter() {
                public final void onAnimationCancel(Animator animator) {
                    super.onAnimationCancel(animator);
                    BubbleStackView.m293$$Nest$mresetDismissAnimator(BubbleStackView.this);
                }

                public final void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    BubbleStackView.m293$$Nest$mresetDismissAnimator(BubbleStackView.this);
                }
            });
            bubbleStackView.mDismissBubbleAnimator.reverse();
        }
    }

    /* renamed from: -$$Nest$mdismissMagnetizedObject  reason: not valid java name */
    public static void m291$$Nest$mdismissMagnetizedObject(BubbleStackView bubbleStackView) {
        Objects.requireNonNull(bubbleStackView);
        if (bubbleStackView.mIsExpanded) {
            MagnetizedObject<?> magnetizedObject = bubbleStackView.mMagnetizedObject;
            Objects.requireNonNull(magnetizedObject);
            Bubble bubbleWithView = bubbleStackView.mBubbleData.getBubbleWithView((View) magnetizedObject.underlyingObject);
            if (bubbleWithView != null && bubbleStackView.mBubbleData.hasBubbleInStackWithKey(bubbleWithView.getKey())) {
                bubbleStackView.mBubbleData.dismissBubbleWithKey(bubbleWithView.getKey(), 1);
                return;
            }
            return;
        }
        bubbleStackView.mBubbleData.dismissAll(1);
    }

    /* renamed from: -$$Nest$mpassEventToMagnetizedObject  reason: not valid java name */
    public static boolean m292$$Nest$mpassEventToMagnetizedObject(BubbleStackView bubbleStackView, MotionEvent motionEvent) {
        Objects.requireNonNull(bubbleStackView);
        MagnetizedObject<?> magnetizedObject = bubbleStackView.mMagnetizedObject;
        if (magnetizedObject == null || !magnetizedObject.maybeConsumeMotionEvent(motionEvent)) {
            return false;
        }
        return true;
    }

    /* renamed from: -$$Nest$mresetDismissAnimator  reason: not valid java name */
    public static void m293$$Nest$mresetDismissAnimator(BubbleStackView bubbleStackView) {
        Objects.requireNonNull(bubbleStackView);
        bubbleStackView.mDismissBubbleAnimator.removeAllListeners();
        bubbleStackView.mDismissBubbleAnimator.cancel();
        View view = bubbleStackView.mViewBeingDismissed;
        if (view != null) {
            view.setAlpha(1.0f);
            bubbleStackView.mViewBeingDismissed = null;
        }
        DismissView dismissView = bubbleStackView.mDismissView;
        if (dismissView != null) {
            dismissView.circle.setScaleX(1.0f);
            DismissView dismissView2 = bubbleStackView.mDismissView;
            Objects.requireNonNull(dismissView2);
            dismissView2.circle.setScaleY(1.0f);
        }
    }

    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        boolean z = false;
        if (motionEvent.getAction() != 0 && motionEvent.getActionIndex() != this.mPointerIndexDown) {
            return false;
        }
        if (motionEvent.getAction() == 0) {
            this.mPointerIndexDown = motionEvent.getActionIndex();
        } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            this.mPointerIndexDown = -1;
        }
        boolean dispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        if (!dispatchTouchEvent && !this.mIsExpanded && this.mIsGestureInProgress) {
            this.mBubbleTouchListener.onTouch(this, motionEvent);
            dispatchTouchEvent = true;
        }
        if (!(motionEvent.getAction() == 1 || motionEvent.getAction() == 3)) {
            z = true;
        }
        this.mIsGestureInProgress = z;
        return dispatchTouchEvent;
    }

    public final boolean maybeShowStackEdu() {
        if (!shouldShowStackEdu() || this.mIsExpanded) {
            return false;
        }
        if (this.mStackEduView == null) {
            StackEducationView stackEducationView = new StackEducationView(this.mContext, this.mPositioner, this.mBubbleController);
            this.mStackEduView = stackEducationView;
            addView(stackEducationView);
        }
        this.mBubbleContainer.bringToFront();
        return this.mStackEduView.show(this.mPositioner.getDefaultStartPosition());
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mPositioner.update();
        getViewTreeObserver().addOnComputeInternalInsetsListener(this);
        getViewTreeObserver().addOnDrawListener(this.mSystemGestureExcludeUpdater);
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnPreDrawListener(this.mViewUpdater);
        getViewTreeObserver().removeOnDrawListener(this.mSystemGestureExcludeUpdater);
        getViewTreeObserver().removeOnComputeInternalInsetsListener(this);
        BubbleOverflow bubbleOverflow = this.mBubbleOverflow;
        if (bubbleOverflow != null) {
            BubbleExpandedView bubbleExpandedView = bubbleOverflow.expandedView;
            if (bubbleExpandedView != null) {
                bubbleExpandedView.cleanUpExpandedState();
            }
            bubbleOverflow.expandedView = null;
        }
    }

    public final void onDisplaySizeChanged() {
        boolean z;
        float f;
        updateOverflow();
        setUpManageMenu();
        setUpFlyout();
        setUpDismissView();
        updateUserEdu();
        BubblePositioner bubblePositioner = this.mPositioner;
        Objects.requireNonNull(bubblePositioner);
        this.mBubbleSize = bubblePositioner.mBubbleSize;
        for (Bubble next : this.mBubbleData.getBubbles()) {
            Objects.requireNonNull(next);
            BadgedImageView badgedImageView = next.mIconView;
            if (badgedImageView == null) {
                Log.d("Bubbles", "Display size changed. Icon null: " + next);
            } else {
                int i = this.mBubbleSize;
                badgedImageView.setLayoutParams(new FrameLayout.LayoutParams(i, i));
                BubbleExpandedView bubbleExpandedView = next.mExpandedView;
                if (bubbleExpandedView != null) {
                    bubbleExpandedView.updateDimensions();
                }
            }
        }
        BadgedImageView iconView = this.mBubbleOverflow.getIconView$1();
        int i2 = this.mBubbleSize;
        iconView.setLayoutParams(new FrameLayout.LayoutParams(i2, i2));
        this.mExpandedAnimationController.updateResources();
        StackAnimationController stackAnimationController = this.mStackAnimationController;
        Objects.requireNonNull(stackAnimationController);
        PhysicsAnimationLayout physicsAnimationLayout = stackAnimationController.mLayout;
        if (physicsAnimationLayout != null) {
            stackAnimationController.mBubblePaddingTop = physicsAnimationLayout.getContext().getResources().getDimensionPixelSize(C1777R.dimen.bubble_padding_top);
        }
        DismissView dismissView = this.mDismissView;
        Objects.requireNonNull(dismissView);
        dismissView.updatePadding();
        dismissView.getLayoutParams().height = dismissView.getResources().getDimensionPixelSize(C1777R.dimen.floating_dismiss_gradient_height);
        int dimensionPixelSize = dismissView.getResources().getDimensionPixelSize(C1777R.dimen.dismiss_circle_size);
        dismissView.circle.getLayoutParams().width = dimensionPixelSize;
        dismissView.circle.getLayoutParams().height = dimensionPixelSize;
        dismissView.circle.requestLayout();
        MagnetizedObject.MagneticTarget magneticTarget = this.mMagneticTarget;
        Objects.requireNonNull(magneticTarget);
        magneticTarget.magneticFieldRadiusPx = this.mBubbleSize * 2;
        StackAnimationController stackAnimationController2 = this.mStackAnimationController;
        PointF restingPosition = this.mPositioner.getRestingPosition();
        RectF allowableStackPositionRegion = this.mStackAnimationController.getAllowableStackPositionRegion();
        if (restingPosition.x < allowableStackPositionRegion.width() / 2.0f) {
            z = true;
        } else {
            z = false;
        }
        float max = Math.max(0.0f, Math.min(1.0f, (restingPosition.y - allowableStackPositionRegion.top) / allowableStackPositionRegion.height()));
        Objects.requireNonNull(stackAnimationController2);
        RectF allowableStackPositionRegion2 = stackAnimationController2.getAllowableStackPositionRegion();
        if (z) {
            f = allowableStackPositionRegion2.left;
        } else {
            f = allowableStackPositionRegion2.right;
        }
        stackAnimationController2.setStackPosition(new PointF(f, (allowableStackPositionRegion2.height() * max) + allowableStackPositionRegion2.top));
        if (this.mIsExpanded) {
            updateExpandedView();
        }
    }

    public final void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        setupLocalMenu(accessibilityNodeInfo);
    }

    public final boolean performAccessibilityActionInternal(int i, Bundle bundle) {
        if (super.performAccessibilityActionInternal(i, bundle)) {
            return true;
        }
        RectF allowableStackPositionRegion = this.mStackAnimationController.getAllowableStackPositionRegion();
        if (i == 1048576) {
            this.mBubbleData.dismissAll(6);
            announceForAccessibility(getResources().getString(C1777R.string.accessibility_bubble_dismissed));
            return true;
        } else if (i == 524288) {
            this.mBubbleData.setExpanded(false);
            return true;
        } else if (i == 262144) {
            this.mBubbleData.setExpanded(true);
            return true;
        } else if (i == C1777R.C1779id.action_move_top_left) {
            StackAnimationController stackAnimationController = this.mStackAnimationController;
            float f = allowableStackPositionRegion.left;
            float f2 = allowableStackPositionRegion.top;
            Objects.requireNonNull(stackAnimationController);
            stackAnimationController.springStack(f, f2, 700.0f);
            return true;
        } else if (i == C1777R.C1779id.action_move_top_right) {
            StackAnimationController stackAnimationController2 = this.mStackAnimationController;
            float f3 = allowableStackPositionRegion.right;
            float f4 = allowableStackPositionRegion.top;
            Objects.requireNonNull(stackAnimationController2);
            stackAnimationController2.springStack(f3, f4, 700.0f);
            return true;
        } else if (i == C1777R.C1779id.action_move_bottom_left) {
            StackAnimationController stackAnimationController3 = this.mStackAnimationController;
            float f5 = allowableStackPositionRegion.left;
            float f6 = allowableStackPositionRegion.bottom;
            Objects.requireNonNull(stackAnimationController3);
            stackAnimationController3.springStack(f5, f6, 700.0f);
            return true;
        } else if (i != C1777R.C1779id.action_move_bottom_right) {
            return false;
        } else {
            StackAnimationController stackAnimationController4 = this.mStackAnimationController;
            float f7 = allowableStackPositionRegion.right;
            float f8 = allowableStackPositionRegion.bottom;
            Objects.requireNonNull(stackAnimationController4);
            stackAnimationController4.springStack(f7, f8, 700.0f);
            return true;
        }
    }

    public final boolean shouldShowStackEdu() {
        boolean z;
        if (ActivityManager.isRunningInTestHarness()) {
            return false;
        }
        Context context = this.mContext;
        if (context.getSharedPreferences(context.getPackageName(), 0).getBoolean("HasSeenBubblesOnboarding", false)) {
            if (Settings.Secure.getInt(this.mContext.getContentResolver(), "force_show_bubbles_user_education", 0) != 0) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                return false;
            }
        }
        return true;
    }

    public final void updateBadges(boolean z) {
        int bubbleCount = getBubbleCount();
        for (int i = 0; i < bubbleCount; i++) {
            BadgedImageView badgedImageView = (BadgedImageView) this.mBubbleContainer.getChildAt(i);
            boolean z2 = true;
            if (this.mIsExpanded) {
                if (!this.mPositioner.showBubblesVertically() || this.mStackOnLeftOrWillBe) {
                    z2 = false;
                }
                badgedImageView.showDotAndBadge(z2);
            } else if (z) {
                if (i == 0) {
                    badgedImageView.showDotAndBadge(!this.mStackOnLeftOrWillBe);
                } else {
                    badgedImageView.hideDotAndBadge(!this.mStackOnLeftOrWillBe);
                }
            }
        }
    }

    public final void updateBubbleShadows(boolean z) {
        boolean z2;
        int bubbleCount = getBubbleCount();
        for (int i = 0; i < bubbleCount; i++) {
            BubblePositioner bubblePositioner = this.mPositioner;
            Objects.requireNonNull(bubblePositioner);
            float f = (float) ((bubblePositioner.mMaxBubbles * this.mBubbleElevation) - i);
            BadgedImageView badgedImageView = (BadgedImageView) this.mBubbleContainer.getChildAt(i);
            MagnetizedObject<?> magnetizedObject = this.mMagnetizedObject;
            if (magnetizedObject != null) {
                Objects.requireNonNull(magnetizedObject);
                if (magnetizedObject.underlyingObject.equals(badgedImageView)) {
                    z2 = true;
                    if (!z || z2) {
                        badgedImageView.setZ(f);
                    } else {
                        if (i >= 2) {
                            f = 0.0f;
                        }
                        badgedImageView.setZ(f);
                    }
                }
            }
            z2 = false;
            if (!z) {
            }
            badgedImageView.setZ(f);
        }
    }

    public final void updateUserEdu() {
        if (isStackEduShowing()) {
            removeView(this.mStackEduView);
            StackEducationView stackEducationView = new StackEducationView(this.mContext, this.mPositioner, this.mBubbleController);
            this.mStackEduView = stackEducationView;
            addView(stackEducationView);
            this.mBubbleContainer.bringToFront();
            this.mStackEduView.show(this.mPositioner.getDefaultStartPosition());
        }
        ManageEducationView manageEducationView = this.mManageEduView;
        if (manageEducationView != null && manageEducationView.getVisibility() == 0) {
            removeView(this.mManageEduView);
            ManageEducationView manageEducationView2 = new ManageEducationView(this.mContext, this.mPositioner);
            this.mManageEduView = manageEducationView2;
            addView(manageEducationView2);
            this.mManageEduView.show(this.mExpandedBubble.getExpandedView());
        }
    }

    public final void updateZOrder() {
        float f;
        int bubbleCount = getBubbleCount();
        for (int i = 0; i < bubbleCount; i++) {
            BadgedImageView badgedImageView = (BadgedImageView) this.mBubbleContainer.getChildAt(i);
            if (i < 2) {
                BubblePositioner bubblePositioner = this.mPositioner;
                Objects.requireNonNull(bubblePositioner);
                f = (float) ((bubblePositioner.mMaxBubbles * this.mBubbleElevation) - i);
            } else {
                f = 0.0f;
            }
            badgedImageView.setZ(f);
        }
    }

    @VisibleForTesting
    public BubbleViewProvider getExpandedBubble() {
        return this.mExpandedBubble;
    }
}
