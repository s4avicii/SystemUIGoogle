package com.android.p012wm.shell.bubbles;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.CornerPathEffect;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.TaskView;
import com.android.p012wm.shell.TaskViewTransitions;
import com.android.p012wm.shell.common.AlphaOptimizedButton;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.TriangleShape;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.BubbleExpandedView */
public class BubbleExpandedView extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mBackgroundColorFloating;
    public Bubble mBubble;
    public BubbleController mController;
    public float mCornerRadius;
    public ShapeDrawable mCurrentPointer;
    public final FrameLayout mExpandedViewContainer;
    public int[] mExpandedViewContainerLocation;
    public boolean mImeVisible;
    public boolean mIsAlphaAnimating;
    public boolean mIsContentVisible;
    public boolean mIsOverflow;
    public ShapeDrawable mLeftPointer;
    public AlphaOptimizedButton mManageButton;
    public boolean mNeedsNewHeight;
    public BubbleOverflowContainerView mOverflowView;
    public PendingIntent mPendingIntent;
    public CornerPathEffect mPointerEffect;
    public int mPointerHeight;
    public float mPointerOverlap;
    public float mPointerRadius;
    public View mPointerView;
    public int mPointerWidth;
    public BubblePositioner mPositioner;
    public ShapeDrawable mRightPointer;
    public BubbleStackView mStackView;
    public int mTaskId;
    public TaskView mTaskView;
    public final C17971 mTaskViewListener;
    public ShapeDrawable mTopPointer;

    public BubbleExpandedView(Context context) {
        this(context, (AttributeSet) null);
    }

    public BubbleExpandedView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public final void applyThemeAttrs() {
        float f;
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(new int[]{16844145, 16844002});
        if (ScreenDecorationsUtils.supportsRoundedCornersOnWindows(this.mContext.getResources())) {
            f = (float) obtainStyledAttributes.getDimensionPixelSize(0, 0);
        } else {
            f = 0.0f;
        }
        this.mCornerRadius = f;
        int color = obtainStyledAttributes.getColor(1, -1);
        this.mBackgroundColorFloating = color;
        this.mExpandedViewContainer.setBackgroundColor(color);
        obtainStyledAttributes.recycle();
        TaskView taskView = this.mTaskView;
        if (taskView != null) {
            taskView.setCornerRadius(this.mCornerRadius);
        }
        updatePointerView();
    }

    public final void cleanUpExpandedState() {
        if (this.mTaskId != -1) {
            try {
                ActivityTaskManager.getService().removeTask(this.mTaskId);
            } catch (RemoteException e) {
                Log.w("Bubbles", e.getMessage());
            }
        }
        TaskView taskView = this.mTaskView;
        if (taskView != null) {
            Objects.requireNonNull(taskView);
            taskView.performRelease();
            removeView(this.mTaskView);
            this.mTaskView = null;
        }
    }

    @VisibleForTesting
    public String getBubbleKey() {
        Bubble bubble = this.mBubble;
        if (bubble != null) {
            Objects.requireNonNull(bubble);
            return bubble.mKey;
        } else if (this.mIsOverflow) {
            return "Overflow";
        } else {
            return null;
        }
    }

    public final void initialize(BubbleController bubbleController, BubbleStackView bubbleStackView, boolean z) {
        this.mController = bubbleController;
        this.mStackView = bubbleStackView;
        this.mIsOverflow = z;
        this.mPositioner = bubbleController.getPositioner();
        if (this.mIsOverflow) {
            BubbleOverflowContainerView bubbleOverflowContainerView = (BubbleOverflowContainerView) LayoutInflater.from(getContext()).inflate(C1777R.layout.bubble_overflow_container, (ViewGroup) null);
            this.mOverflowView = bubbleOverflowContainerView;
            BubbleController bubbleController2 = this.mController;
            Objects.requireNonNull(bubbleOverflowContainerView);
            bubbleOverflowContainerView.mController = bubbleController2;
            this.mExpandedViewContainer.addView(this.mOverflowView, new FrameLayout.LayoutParams(-1, -1));
            this.mExpandedViewContainer.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
            bringChildToFront(this.mOverflowView);
            this.mManageButton.setVisibility(8);
            return;
        }
        Context context = this.mContext;
        BubbleController bubbleController3 = this.mController;
        Objects.requireNonNull(bubbleController3);
        ShellTaskOrganizer shellTaskOrganizer = bubbleController3.mTaskOrganizer;
        BubbleController bubbleController4 = this.mController;
        Objects.requireNonNull(bubbleController4);
        TaskViewTransitions taskViewTransitions = bubbleController4.mTaskViewTransitions;
        BubbleController bubbleController5 = this.mController;
        Objects.requireNonNull(bubbleController5);
        TaskView taskView = new TaskView(context, shellTaskOrganizer, taskViewTransitions, bubbleController5.mSyncQueue);
        this.mTaskView = taskView;
        BubbleController bubbleController6 = this.mController;
        Objects.requireNonNull(bubbleController6);
        ShellExecutor shellExecutor = bubbleController6.mMainExecutor;
        C17971 r5 = this.mTaskViewListener;
        if (taskView.mListener == null) {
            taskView.mListener = r5;
            taskView.mListenerExecutor = shellExecutor;
            this.mExpandedViewContainer.addView(this.mTaskView);
            bringChildToFront(this.mTaskView);
            return;
        }
        throw new IllegalStateException("Trying to set a listener when one has already been set");
    }

    public final void setContentVisibility(boolean z) {
        float f;
        this.mIsContentVisible = z;
        TaskView taskView = this.mTaskView;
        if (taskView != null && !this.mIsAlphaAnimating) {
            float f2 = 1.0f;
            if (z) {
                f = 1.0f;
            } else {
                f = 0.0f;
            }
            taskView.setAlpha(f);
            View view = this.mPointerView;
            if (!z) {
                f2 = 0.0f;
            }
            view.setAlpha(f2);
        }
    }

    public final SurfaceControl.ScreenshotHardwareBuffer snapshotActivitySurface() {
        if (this.mIsOverflow) {
            Picture picture = new Picture();
            BubbleOverflowContainerView bubbleOverflowContainerView = this.mOverflowView;
            bubbleOverflowContainerView.draw(picture.beginRecording(bubbleOverflowContainerView.getWidth(), this.mOverflowView.getHeight()));
            picture.endRecording();
            Bitmap createBitmap = Bitmap.createBitmap(picture);
            return new SurfaceControl.ScreenshotHardwareBuffer(createBitmap.getHardwareBuffer(), createBitmap.getColorSpace(), false);
        }
        TaskView taskView = this.mTaskView;
        if (taskView == null || taskView.getSurfaceControl() == null) {
            return null;
        }
        return SurfaceControl.captureLayers(this.mTaskView.getSurfaceControl(), new Rect(0, 0, this.mTaskView.getWidth(), this.mTaskView.getHeight()), 1.0f);
    }

    public final void updateFontSize() {
        float dimensionPixelSize = (float) this.mContext.getResources().getDimensionPixelSize(17105569);
        AlphaOptimizedButton alphaOptimizedButton = this.mManageButton;
        if (alphaOptimizedButton != null) {
            alphaOptimizedButton.setTextSize(0, dimensionPixelSize);
        }
        BubbleOverflowContainerView bubbleOverflowContainerView = this.mOverflowView;
        if (bubbleOverflowContainerView != null) {
            bubbleOverflowContainerView.updateFontSize();
        }
    }

    public final void updateHeight() {
        float f;
        FrameLayout.LayoutParams layoutParams;
        boolean z;
        if (this.mExpandedViewContainerLocation != null) {
            Bubble bubble = this.mBubble;
            if ((bubble != null && this.mTaskView != null) || this.mIsOverflow) {
                float expandedViewHeight = this.mPositioner.getExpandedViewHeight(bubble);
                int maxExpandedViewHeight = this.mPositioner.getMaxExpandedViewHeight(this.mIsOverflow);
                if (expandedViewHeight == -1.0f) {
                    f = (float) maxExpandedViewHeight;
                } else {
                    f = Math.min(expandedViewHeight, (float) maxExpandedViewHeight);
                }
                if (this.mIsOverflow) {
                    layoutParams = (FrameLayout.LayoutParams) this.mOverflowView.getLayoutParams();
                } else {
                    layoutParams = (FrameLayout.LayoutParams) this.mTaskView.getLayoutParams();
                }
                if (((float) layoutParams.height) != f) {
                    z = true;
                } else {
                    z = false;
                }
                this.mNeedsNewHeight = z;
                if (!this.mImeVisible) {
                    layoutParams.height = (int) f;
                    if (this.mIsOverflow) {
                        this.mOverflowView.setLayoutParams(layoutParams);
                    } else {
                        this.mTaskView.setLayoutParams(layoutParams);
                    }
                    this.mNeedsNewHeight = false;
                }
            }
        }
    }

    public final void updatePointerView() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mPointerView.getLayoutParams();
        ShapeDrawable shapeDrawable = this.mCurrentPointer;
        if (shapeDrawable == this.mLeftPointer || shapeDrawable == this.mRightPointer) {
            layoutParams.width = this.mPointerHeight;
            layoutParams.height = this.mPointerWidth;
        } else {
            layoutParams.width = this.mPointerWidth;
            layoutParams.height = this.mPointerHeight;
        }
        shapeDrawable.setTint(this.mBackgroundColorFloating);
        Paint paint = this.mCurrentPointer.getPaint();
        paint.setColor(this.mBackgroundColorFloating);
        paint.setPathEffect(this.mPointerEffect);
        this.mPointerView.setLayoutParams(layoutParams);
        this.mPointerView.setBackground(this.mCurrentPointer);
    }

    /* renamed from: $r8$lambda$OgjLsfC5rVPmXxC3-ADgwAd5PRI  reason: not valid java name */
    public static /* synthetic */ void m289$r8$lambda$OgjLsfC5rVPmXxC3ADgwAd5PRI(BubbleExpandedView bubbleExpandedView, boolean z, boolean z2, float f, boolean z3) {
        ShapeDrawable shapeDrawable;
        float f2;
        float f3;
        Objects.requireNonNull(bubbleExpandedView);
        if (!z) {
            shapeDrawable = bubbleExpandedView.mTopPointer;
        } else if (z2) {
            shapeDrawable = bubbleExpandedView.mLeftPointer;
        } else {
            shapeDrawable = bubbleExpandedView.mRightPointer;
        }
        bubbleExpandedView.mCurrentPointer = shapeDrawable;
        bubbleExpandedView.updatePointerView();
        if (z) {
            f2 = f - (((float) bubbleExpandedView.mPointerWidth) / 2.0f);
            if (z2) {
                f3 = ((float) (-bubbleExpandedView.mPointerHeight)) + bubbleExpandedView.mPointerOverlap;
            } else {
                f3 = ((float) (bubbleExpandedView.getWidth() - bubbleExpandedView.mPaddingRight)) - bubbleExpandedView.mPointerOverlap;
            }
        } else {
            float f4 = bubbleExpandedView.mPointerOverlap;
            float f5 = f - (((float) bubbleExpandedView.mPointerWidth) / 2.0f);
            f2 = f4;
            f3 = f5;
        }
        if (z3) {
            bubbleExpandedView.mPointerView.animate().translationX(f3).translationY(f2).start();
            return;
        }
        bubbleExpandedView.mPointerView.setTranslationY(f2);
        bubbleExpandedView.mPointerView.setTranslationX(f3);
        bubbleExpandedView.mPointerView.setVisibility(0);
    }

    public BubbleExpandedView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mImeVisible = false;
        this.mNeedsNewHeight = false;
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mManageButton = (AlphaOptimizedButton) LayoutInflater.from(getContext()).inflate(C1777R.layout.bubble_manage_button, this, false);
        updateDimensions();
        View findViewById = findViewById(C1777R.C1779id.pointer_view);
        this.mPointerView = findViewById;
        this.mCurrentPointer = this.mTopPointer;
        findViewById.setVisibility(4);
        setContentVisibility(false);
        this.mExpandedViewContainer.setOutlineProvider(new ViewOutlineProvider() {
            public final void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), BubbleExpandedView.this.mCornerRadius);
            }
        });
        this.mExpandedViewContainer.setClipToOutline(true);
        this.mExpandedViewContainer.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        addView(this.mExpandedViewContainer);
        bringChildToFront(this.mManageButton);
        applyThemeAttrs();
        setClipToPadding(false);
        setOnTouchListener(new BubbleExpandedView$$ExternalSyntheticLambda0(this));
        setLayoutDirection(3);
    }

    public final void updateDimensions() {
        Resources resources = getResources();
        updateFontSize();
        this.mPointerWidth = resources.getDimensionPixelSize(C1777R.dimen.bubble_pointer_width);
        this.mPointerHeight = resources.getDimensionPixelSize(C1777R.dimen.bubble_pointer_height);
        this.mPointerRadius = (float) getResources().getDimensionPixelSize(C1777R.dimen.bubble_pointer_radius);
        this.mPointerEffect = new CornerPathEffect(this.mPointerRadius);
        this.mPointerOverlap = (float) getResources().getDimensionPixelSize(C1777R.dimen.bubble_pointer_overlap);
        float f = (float) this.mPointerWidth;
        float f2 = (float) this.mPointerHeight;
        int i = TriangleShape.$r8$clinit;
        Path path = new Path();
        path.moveTo(0.0f, f2);
        path.lineTo(f, f2);
        path.lineTo(f / 2.0f, 0.0f);
        path.close();
        this.mTopPointer = new ShapeDrawable(new TriangleShape(path, f, f2));
        this.mLeftPointer = new ShapeDrawable(TriangleShape.createHorizontal((float) this.mPointerWidth, (float) this.mPointerHeight, true));
        this.mRightPointer = new ShapeDrawable(TriangleShape.createHorizontal((float) this.mPointerWidth, (float) this.mPointerHeight, false));
        if (this.mPointerView != null) {
            updatePointerView();
        }
        AlphaOptimizedButton alphaOptimizedButton = this.mManageButton;
        if (alphaOptimizedButton != null) {
            int visibility = alphaOptimizedButton.getVisibility();
            removeView(this.mManageButton);
            AlphaOptimizedButton alphaOptimizedButton2 = (AlphaOptimizedButton) LayoutInflater.from(getContext()).inflate(C1777R.layout.bubble_manage_button, this, false);
            this.mManageButton = alphaOptimizedButton2;
            addView(alphaOptimizedButton2);
            this.mManageButton.setVisibility(visibility);
        }
    }

    public BubbleExpandedView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTaskId = -1;
        this.mIsContentVisible = false;
        this.mIsAlphaAnimating = false;
        this.mCornerRadius = 0.0f;
        this.mExpandedViewContainer = new FrameLayout(getContext());
        this.mTaskViewListener = new TaskView.Listener() {
            public boolean mDestroyed = false;
            public boolean mInitialized = false;

            public final void onReleased() {
                this.mDestroyed = true;
            }

            public final void onBackPressedOnTaskRoot(int i) {
                BubbleExpandedView bubbleExpandedView = BubbleExpandedView.this;
                if (bubbleExpandedView.mTaskId == i) {
                    BubbleStackView bubbleStackView = bubbleExpandedView.mStackView;
                    Objects.requireNonNull(bubbleStackView);
                    if (bubbleStackView.mIsExpanded) {
                        BubbleStackView bubbleStackView2 = BubbleExpandedView.this.mStackView;
                        Objects.requireNonNull(bubbleStackView2);
                        if (!bubbleStackView2.mIsExpanded) {
                            return;
                        }
                        if (bubbleStackView2.mShowingManage) {
                            bubbleStackView2.showManageMenu(false);
                            return;
                        }
                        ManageEducationView manageEducationView = bubbleStackView2.mManageEduView;
                        if (manageEducationView == null || manageEducationView.getVisibility() != 0) {
                            bubbleStackView2.mBubbleData.setExpanded(false);
                        } else {
                            bubbleStackView2.mManageEduView.hide();
                        }
                    }
                }
            }

            public final void onInitialized() {
                if (!this.mDestroyed && !this.mInitialized) {
                    ActivityOptions makeCustomAnimation = ActivityOptions.makeCustomAnimation(BubbleExpandedView.this.getContext(), 0, 0);
                    Rect rect = new Rect();
                    BubbleExpandedView.this.mTaskView.getBoundsOnScreen(rect);
                    BubbleExpandedView.this.post(new BubbleExpandedView$1$$ExternalSyntheticLambda1(this, makeCustomAnimation, rect, 0));
                    this.mInitialized = true;
                }
            }

            public final void onTaskCreated(int i) {
                BubbleExpandedView bubbleExpandedView = BubbleExpandedView.this;
                bubbleExpandedView.mTaskId = i;
                bubbleExpandedView.setContentVisibility(true);
            }

            public final void onTaskRemovalStarted() {
                BubbleExpandedView bubbleExpandedView = BubbleExpandedView.this;
                if (bubbleExpandedView.mBubble != null) {
                    bubbleExpandedView.post(new BubbleExpandedView$1$$ExternalSyntheticLambda0(this, 0));
                }
            }

            public final void onTaskVisibilityChanged(boolean z) {
                BubbleExpandedView.this.setContentVisibility(z);
            }
        };
    }
}
