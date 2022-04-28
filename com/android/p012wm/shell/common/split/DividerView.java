package com.android.p012wm.shell.common.split;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Property;
import android.view.GestureDetector;
import android.view.InsetsController;
import android.view.InsetsSource;
import android.view.InsetsState;
import android.view.MotionEvent;
import android.view.SurfaceControlViewHost;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.animation.Interpolators;
import java.util.Objects;

/* renamed from: com.android.wm.shell.common.split.DividerView */
public class DividerView extends FrameLayout implements View.OnTouchListener {
    public static final C18541 DIVIDER_HEIGHT_PROPERTY = new Property<DividerView, Integer>() {
        {
            Class<Integer> cls = Integer.class;
        }

        public final Object get(Object obj) {
            return Integer.valueOf(((DividerView) obj).mDividerBar.getLayoutParams().height);
        }

        public final void set(Object obj, Object obj2) {
            DividerView dividerView = (DividerView) obj;
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) dividerView.mDividerBar.getLayoutParams();
            marginLayoutParams.height = ((Integer) obj2).intValue();
            dividerView.mDividerBar.setLayoutParams(marginLayoutParams);
        }
    };
    public C18552 mAnimatorListener = new AnimatorListenerAdapter() {
        public final void onAnimationCancel(Animator animator) {
            DividerView.this.mSetTouchRegion = true;
        }

        public final void onAnimationEnd(Animator animator) {
            DividerView.this.mSetTouchRegion = true;
        }
    };
    public View mBackground;
    public FrameLayout mDividerBar;
    public final Rect mDividerBounds = new Rect();
    public GestureDetector mDoubleTapDetector;
    public float mExpandedTaskBarHeight;
    public DividerHandleView mHandle;
    public boolean mInteractive;
    public boolean mMoving;
    public boolean mSetTouchRegion = true;
    public SplitLayout mSplitLayout;
    public SplitWindowManager mSplitWindowManager;
    public int mStartPos;
    public final Rect mTempRect = new Rect();
    public int mTouchElevation;
    public final int mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    public VelocityTracker mVelocityTracker;
    public SurfaceControlViewHost mViewHost;

    /* renamed from: com.android.wm.shell.common.split.DividerView$DoubleTapListener */
    public class DoubleTapListener extends GestureDetector.SimpleOnGestureListener {
        public DoubleTapListener() {
        }

        public final boolean onDoubleTap(MotionEvent motionEvent) {
            SplitLayout splitLayout = DividerView.this.mSplitLayout;
            if (splitLayout == null) {
                return true;
            }
            Objects.requireNonNull(splitLayout);
            splitLayout.mSplitLayoutHandler.onDoubleTappedDivider();
            return true;
        }
    }

    public DividerView(Context context) {
        super(context);
    }

    public final void releaseTouching() {
        setSlippery(true);
        this.mHandle.setTouching(false, true);
        this.mHandle.animate().setInterpolator(Interpolators.FAST_OUT_SLOW_IN).setDuration(200).translationZ(0.0f).start();
    }

    public final void onInsetsChanged(InsetsState insetsState, boolean z) {
        this.mTempRect.set(this.mSplitLayout.getDividerBounds());
        InsetsSource source = insetsState.getSource(21);
        if (((float) source.getFrame().height()) >= this.mExpandedTaskBarHeight) {
            Rect rect = this.mTempRect;
            rect.inset(source.calculateVisibleInsets(rect));
        }
        if (!this.mTempRect.equals(this.mDividerBounds)) {
            if (z) {
                ObjectAnimator ofInt = ObjectAnimator.ofInt(this, DIVIDER_HEIGHT_PROPERTY, new int[]{this.mDividerBounds.height(), this.mTempRect.height()});
                ofInt.setInterpolator(InsetsController.RESIZE_INTERPOLATOR);
                ofInt.setDuration(300);
                ofInt.addListener(this.mAnimatorListener);
                ofInt.start();
            } else {
                DIVIDER_HEIGHT_PROPERTY.set(this, Integer.valueOf(this.mTempRect.height()));
                this.mSetTouchRegion = true;
            }
            this.mDividerBounds.set(this.mTempRect);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0049, code lost:
        if (r7 != 3) goto L_0x0121;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onTouch(android.view.View r7, android.view.MotionEvent r8) {
        /*
            r6 = this;
            com.android.wm.shell.common.split.SplitLayout r7 = r6.mSplitLayout
            r0 = 0
            if (r7 == 0) goto L_0x0122
            boolean r7 = r6.mInteractive
            if (r7 != 0) goto L_0x000b
            goto L_0x0122
        L_0x000b:
            android.view.GestureDetector r7 = r6.mDoubleTapDetector
            boolean r7 = r7.onTouchEvent(r8)
            r1 = 1
            if (r7 == 0) goto L_0x0015
            return r1
        L_0x0015:
            float r7 = r8.getRawX()
            float r2 = r8.getRawY()
            r8.setLocation(r7, r2)
            int r7 = r8.getAction()
            r7 = r7 & 255(0xff, float:3.57E-43)
            android.content.res.Resources r2 = r6.getResources()
            android.content.res.Configuration r2 = r2.getConfiguration()
            int r2 = r2.orientation
            r3 = 2
            if (r2 != r3) goto L_0x0035
            r2 = r1
            goto L_0x0036
        L_0x0035:
            r2 = r0
        L_0x0036:
            if (r2 == 0) goto L_0x003d
            float r4 = r8.getX()
            goto L_0x0041
        L_0x003d:
            float r4 = r8.getY()
        L_0x0041:
            int r4 = (int) r4
            if (r7 == 0) goto L_0x00f0
            r5 = 3
            if (r7 == r1) goto L_0x0084
            if (r7 == r3) goto L_0x004d
            if (r7 == r5) goto L_0x0084
            goto L_0x0121
        L_0x004d:
            android.view.VelocityTracker r7 = r6.mVelocityTracker
            r7.addMovement(r8)
            boolean r7 = r6.mMoving
            if (r7 != 0) goto L_0x0066
            int r7 = r6.mStartPos
            int r7 = r4 - r7
            int r7 = java.lang.Math.abs(r7)
            int r8 = r6.mTouchSlop
            if (r7 <= r8) goto L_0x0066
            r6.mStartPos = r4
            r6.mMoving = r1
        L_0x0066:
            boolean r7 = r6.mMoving
            if (r7 == 0) goto L_0x0121
            com.android.wm.shell.common.split.SplitLayout r7 = r6.mSplitLayout
            java.util.Objects.requireNonNull(r7)
            int r7 = r7.mDividePosition
            int r7 = r7 + r4
            int r8 = r6.mStartPos
            int r7 = r7 - r8
            com.android.wm.shell.common.split.SplitLayout r6 = r6.mSplitLayout
            java.util.Objects.requireNonNull(r6)
            r6.updateBounds(r7)
            com.android.wm.shell.common.split.SplitLayout$SplitLayoutHandler r7 = r6.mSplitLayoutHandler
            r7.onLayoutSizeChanging(r6)
            goto L_0x0121
        L_0x0084:
            r6.releaseTouching()
            boolean r7 = r6.mMoving
            if (r7 != 0) goto L_0x008d
            goto L_0x0121
        L_0x008d:
            android.view.VelocityTracker r7 = r6.mVelocityTracker
            r7.addMovement(r8)
            android.view.VelocityTracker r7 = r6.mVelocityTracker
            r8 = 1000(0x3e8, float:1.401E-42)
            r7.computeCurrentVelocity(r8)
            if (r2 == 0) goto L_0x00a2
            android.view.VelocityTracker r7 = r6.mVelocityTracker
            float r7 = r7.getXVelocity()
            goto L_0x00a8
        L_0x00a2:
            android.view.VelocityTracker r7 = r6.mVelocityTracker
            float r7 = r7.getYVelocity()
        L_0x00a8:
            com.android.wm.shell.common.split.SplitLayout r8 = r6.mSplitLayout
            java.util.Objects.requireNonNull(r8)
            int r8 = r8.mDividePosition
            int r8 = r8 + r4
            int r2 = r6.mStartPos
            int r8 = r8 - r2
            com.android.wm.shell.common.split.SplitLayout r2 = r6.mSplitLayout
            java.util.Objects.requireNonNull(r2)
            com.android.internal.policy.DividerSnapAlgorithm r2 = r2.mDividerSnapAlgorithm
            com.android.internal.policy.DividerSnapAlgorithm$SnapTarget r7 = r2.calculateSnapTarget(r8, r7, r0)
            com.android.wm.shell.common.split.SplitLayout r2 = r6.mSplitLayout
            java.util.Objects.requireNonNull(r2)
            int r4 = r7.flag
            if (r4 == r1) goto L_0x00e1
            if (r4 == r3) goto L_0x00d4
            int r3 = r7.position
            com.android.systemui.qs.tiles.CastTile$$ExternalSyntheticLambda1 r4 = new com.android.systemui.qs.tiles.CastTile$$ExternalSyntheticLambda1
            r4.<init>(r2, r7, r5)
            r2.flingDividePosition(r8, r3, r4)
            goto L_0x00ed
        L_0x00d4:
            int r7 = r7.position
            com.android.wm.shell.TaskView$$ExternalSyntheticLambda4 r3 = new com.android.wm.shell.TaskView$$ExternalSyntheticLambda4
            r4 = 11
            r3.<init>(r2, r4)
            r2.flingDividePosition(r8, r7, r3)
            goto L_0x00ed
        L_0x00e1:
            int r7 = r7.position
            com.android.wm.shell.TaskView$$ExternalSyntheticLambda3 r3 = new com.android.wm.shell.TaskView$$ExternalSyntheticLambda3
            r4 = 8
            r3.<init>(r2, r4)
            r2.flingDividePosition(r8, r7, r3)
        L_0x00ed:
            r6.mMoving = r0
            goto L_0x0121
        L_0x00f0:
            android.view.VelocityTracker r7 = android.view.VelocityTracker.obtain()
            r6.mVelocityTracker = r7
            r7.addMovement(r8)
            r6.setSlippery(r0)
            com.android.wm.shell.common.split.DividerHandleView r7 = r6.mHandle
            r7.setTouching(r1, r1)
            com.android.wm.shell.common.split.DividerHandleView r7 = r6.mHandle
            android.view.ViewPropertyAnimator r7 = r7.animate()
            android.view.animation.PathInterpolator r8 = com.android.p012wm.shell.animation.Interpolators.TOUCH_RESPONSE
            android.view.ViewPropertyAnimator r7 = r7.setInterpolator(r8)
            r2 = 150(0x96, double:7.4E-322)
            android.view.ViewPropertyAnimator r7 = r7.setDuration(r2)
            int r8 = r6.mTouchElevation
            float r8 = (float) r8
            android.view.ViewPropertyAnimator r7 = r7.translationZ(r8)
            r7.start()
            r6.mStartPos = r4
            r6.mMoving = r0
        L_0x0121:
            return r1
        L_0x0122:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.common.split.DividerView.onTouch(android.view.View, android.view.MotionEvent):boolean");
    }

    public final void setSlippery(boolean z) {
        boolean z2;
        if (this.mViewHost != null) {
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) getLayoutParams();
            int i = layoutParams.flags;
            if ((i & 536870912) != 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2 != z) {
                if (z) {
                    layoutParams.flags = i | 536870912;
                } else {
                    layoutParams.flags = -536870913 & i;
                }
                this.mViewHost.relayout(layoutParams);
            }
        }
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mDividerBar = (FrameLayout) findViewById(C1777R.C1779id.divider_bar);
        this.mHandle = (DividerHandleView) findViewById(C1777R.C1779id.docked_divider_handle);
        this.mBackground = findViewById(C1777R.C1779id.docked_divider_background);
        this.mExpandedTaskBarHeight = (float) getResources().getDimensionPixelSize(17105563);
        this.mTouchElevation = getResources().getDimensionPixelSize(C1777R.dimen.docked_stack_divider_lift_elevation);
        this.mDoubleTapDetector = new GestureDetector(getContext(), new DoubleTapListener());
        this.mInteractive = true;
        setOnTouchListener(this);
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mSetTouchRegion) {
            this.mTempRect.set(this.mHandle.getLeft(), this.mHandle.getTop(), this.mHandle.getRight(), this.mHandle.getBottom());
            this.mSplitWindowManager.setTouchRegion(this.mTempRect);
            this.mSetTouchRegion = false;
        }
    }

    public DividerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DividerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public DividerView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
