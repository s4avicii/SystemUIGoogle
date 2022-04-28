package com.android.systemui.accessibility.floatingmenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.MathUtils;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewPropertyAnimator;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;
import com.android.internal.accessibility.dialog.AccessibilityTarget;
import com.android.internal.annotations.VisibleForTesting;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda6;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda1;
import com.android.p012wm.shell.common.ExecutorUtils$$ExternalSyntheticLambda1;
import com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class AccessibilityFloatingMenuView extends FrameLayout implements RecyclerView.OnItemTouchListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AccessibilityTargetAdapter mAdapter;
    public int mAlignment;
    @VisibleForTesting
    public final WindowManager.LayoutParams mCurrentLayoutParams;
    public int mDisplayHeight;
    public final Rect mDisplayInsetsRect = new Rect();
    public int mDisplayWidth;
    public int mDownX;
    public int mDownY;
    @VisibleForTesting
    public final ValueAnimator mDragAnimator;
    public final ValueAnimator mFadeOutAnimator;
    public float mFadeOutValue;
    public int mIconHeight;
    public int mIconWidth;
    public final Rect mImeInsetsRect = new Rect();
    public int mInset;
    public boolean mIsDownInEnlargedTouchArea;
    public boolean mIsDragging = false;
    public boolean mIsFadeEffectEnabled;
    public boolean mIsShowing;
    public final Configuration mLastConfiguration;
    public final RecyclerView mListView;
    public int mMargin;
    public Optional<OnDragEndListener> mOnDragEndListener = Optional.empty();
    public int mPadding;
    public final Position mPosition;
    public float mRadius;
    public int mRadiusType;
    public int mRelativeToPointerDownX;
    public int mRelativeToPointerDownY;
    @VisibleForTesting
    public int mShapeType = 0;
    public int mSizeType = 0;
    public float mSquareScaledTouchSlop;
    public final ArrayList mTargets;
    public int mTemporaryShapeType;
    public final Handler mUiHandler;
    public final WindowManager mWindowManager;

    public interface OnDragEndListener {
        void onDragEnd(Position position);
    }

    public final void onRequestDisallowInterceptTouchEvent(boolean z) {
    }

    public final void onTouchEvent(MotionEvent motionEvent) {
    }

    @VisibleForTesting
    public void fadeIn() {
        if (this.mIsFadeEffectEnabled) {
            this.mFadeOutAnimator.cancel();
            this.mUiHandler.removeCallbacksAndMessages((Object) null);
            this.mUiHandler.post(new KeyguardUpdateMonitor$$ExternalSyntheticLambda6(this, 4));
        }
    }

    @VisibleForTesting
    public void fadeOut() {
        if (this.mIsFadeEffectEnabled) {
            this.mUiHandler.postDelayed(new ScrimView$$ExternalSyntheticLambda0(this, 3), 3000);
        }
    }

    @VisibleForTesting
    public Rect getAvailableBounds() {
        return new Rect(0, 0, this.mDisplayWidth - (((this.mPadding * 2) + this.mIconWidth) + (getMarginStartEndWith(this.mLastConfiguration) * 2)), this.mDisplayHeight - getWindowHeight());
    }

    public final int getInterval() {
        Position position = this.mPosition;
        Objects.requireNonNull(position);
        int i = this.mDisplayHeight - this.mImeInsetsRect.bottom;
        int windowHeight = getWindowHeight() + ((int) (position.mPercentageY * ((float) (this.mDisplayHeight - getWindowHeight()))));
        if (windowHeight > i) {
            return windowHeight - i;
        }
        return 0;
    }

    public final int getMarginStartEndWith(Configuration configuration) {
        if (configuration == null || configuration.orientation != 1) {
            return 0;
        }
        return this.mMargin;
    }

    public final int getMaxWindowX() {
        return (this.mDisplayWidth - getMarginStartEndWith(this.mLastConfiguration)) - ((this.mPadding * 2) + this.mIconWidth);
    }

    public final int getWindowHeight() {
        int i = this.mDisplayHeight;
        int i2 = this.mMargin;
        return Math.min(i, Math.min(i - (i2 * 2), (this.mTargets.size() * (this.mPadding + this.mIconHeight)) + this.mPadding) + (i2 * 2));
    }

    @VisibleForTesting
    public boolean hasExceededMaxLayoutHeight() {
        if ((this.mTargets.size() * (this.mPadding + this.mIconHeight)) + this.mPadding > this.mDisplayHeight - (this.mMargin * 2)) {
            return true;
        }
        return false;
    }

    public final void setRadius(float f, int i) {
        ((GradientDrawable) ((InstantInsetLayerDrawable) this.mListView.getBackground()).getDrawable(0)).setCornerRadii(i == 0 ? new float[]{f, f, 0.0f, 0.0f, 0.0f, 0.0f, f, f} : i == 2 ? new float[]{0.0f, 0.0f, f, f, f, f, 0.0f, 0.0f} : new float[]{f, f, f, f, f, f, f, f});
    }

    public final void setSystemGestureExclusion() {
        post(new ExecutorUtils$$ExternalSyntheticLambda1(this, new Rect(0, 0, (this.mPadding * 2) + this.mIconWidth + (getMarginStartEndWith(this.mLastConfiguration) * 2), getWindowHeight()), 1));
    }

    @VisibleForTesting
    public void snapToLocation(int i, int i2) {
        this.mDragAnimator.cancel();
        this.mDragAnimator.removeAllUpdateListeners();
        this.mDragAnimator.addUpdateListener(new AccessibilityFloatingMenuView$$ExternalSyntheticLambda1(this, i, i2));
        this.mDragAnimator.start();
    }

    public final void updateInsetWith(int i, int i2) {
        boolean z;
        int i3;
        int i4;
        int i5;
        if ((i & 48) == 32) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            i3 = this.mInset;
        } else {
            i3 = 0;
        }
        if (i2 == 0) {
            i4 = i3;
        } else {
            i4 = 0;
        }
        if (i2 == 1) {
            i5 = i3;
        } else {
            i5 = 0;
        }
        InstantInsetLayerDrawable instantInsetLayerDrawable = (InstantInsetLayerDrawable) this.mListView.getBackground();
        if (instantInsetLayerDrawable.getLayerInsetLeft(0) != i4 || instantInsetLayerDrawable.getLayerInsetRight(0) != i5) {
            instantInsetLayerDrawable.setLayerInset(0, i4, 0, i5, 0);
        }
    }

    public final void updateOpacityWith(boolean z, float f) {
        this.mIsFadeEffectEnabled = z;
        this.mFadeOutValue = f;
        this.mFadeOutAnimator.cancel();
        float f2 = 1.0f;
        this.mFadeOutAnimator.setFloatValues(new float[]{1.0f, this.mFadeOutValue});
        if (this.mIsFadeEffectEnabled) {
            f2 = this.mFadeOutValue;
        }
        setAlpha(f2);
    }

    @VisibleForTesting
    public AccessibilityFloatingMenuView(Context context, Position position, RecyclerView recyclerView) {
        super(context);
        int i;
        int i2;
        int i3;
        ArrayList arrayList = new ArrayList();
        this.mTargets = arrayList;
        this.mListView = recyclerView;
        this.mWindowManager = (WindowManager) context.getSystemService(WindowManager.class);
        Configuration configuration = new Configuration(getResources().getConfiguration());
        this.mLastConfiguration = configuration;
        AccessibilityTargetAdapter accessibilityTargetAdapter = new AccessibilityTargetAdapter(arrayList);
        this.mAdapter = accessibilityTargetAdapter;
        Looper myLooper = Looper.myLooper();
        Objects.requireNonNull(myLooper, "looper must not be null");
        this.mUiHandler = new Handler(myLooper);
        this.mPosition = position;
        Objects.requireNonNull(position);
        if (position.mPercentageX < 0.5f) {
            i = 0;
        } else {
            i = 1;
        }
        this.mAlignment = i;
        if (i == 1) {
            i2 = 0;
        } else {
            i2 = 2;
        }
        this.mRadiusType = i2;
        updateDimensions();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, 2024, 520, -3);
        layoutParams.receiveInsetsIgnoringZOrder = true;
        layoutParams.privateFlags |= 2097152;
        layoutParams.windowAnimations = 16973827;
        layoutParams.gravity = 8388659;
        if (this.mAlignment == 1) {
            i3 = getMaxWindowX();
        } else {
            i3 = -getMarginStartEndWith(configuration);
        }
        layoutParams.x = i3;
        layoutParams.y = Math.max(0, ((int) (position.mPercentageY * ((float) (this.mDisplayHeight - getWindowHeight())))) - getInterval());
        layoutParams.accessibilityTitle = getResources().getString(17039577);
        this.mCurrentLayoutParams = layoutParams;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{1.0f, this.mFadeOutValue});
        this.mFadeOutAnimator = ofFloat;
        ofFloat.setDuration(1000);
        ofFloat.addUpdateListener(new AccessibilityFloatingMenuView$$ExternalSyntheticLambda0(this));
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        this.mDragAnimator = ofFloat2;
        ofFloat2.setDuration(150);
        ofFloat2.setInterpolator(new OvershootInterpolator());
        ofFloat2.addListener(new AnimatorListenerAdapter() {
            public static final /* synthetic */ int $r8$clinit = 0;

            public final void onAnimationEnd(Animator animator) {
                float f;
                int i;
                AccessibilityFloatingMenuView accessibilityFloatingMenuView = AccessibilityFloatingMenuView.this;
                Position position = accessibilityFloatingMenuView.mPosition;
                if (((double) (((float) accessibilityFloatingMenuView.mCurrentLayoutParams.x) / ((float) accessibilityFloatingMenuView.getMaxWindowX()))) < 0.5d) {
                    f = 0.0f;
                } else {
                    f = 1.0f;
                }
                AccessibilityFloatingMenuView accessibilityFloatingMenuView2 = AccessibilityFloatingMenuView.this;
                Objects.requireNonNull(accessibilityFloatingMenuView2);
                Objects.requireNonNull(position);
                position.mPercentageX = f;
                position.mPercentageY = ((float) accessibilityFloatingMenuView2.mCurrentLayoutParams.y) / ((float) (accessibilityFloatingMenuView2.mDisplayHeight - accessibilityFloatingMenuView2.getWindowHeight()));
                AccessibilityFloatingMenuView accessibilityFloatingMenuView3 = AccessibilityFloatingMenuView.this;
                Position position2 = accessibilityFloatingMenuView3.mPosition;
                Objects.requireNonNull(position2);
                int i2 = 0;
                if (position2.mPercentageX < 0.5f) {
                    i = 0;
                } else {
                    i = 1;
                }
                accessibilityFloatingMenuView3.mAlignment = i;
                AccessibilityFloatingMenuView accessibilityFloatingMenuView4 = AccessibilityFloatingMenuView.this;
                accessibilityFloatingMenuView4.updateLocationWith(accessibilityFloatingMenuView4.mPosition);
                AccessibilityFloatingMenuView accessibilityFloatingMenuView5 = AccessibilityFloatingMenuView.this;
                accessibilityFloatingMenuView5.updateInsetWith(accessibilityFloatingMenuView5.getResources().getConfiguration().uiMode, AccessibilityFloatingMenuView.this.mAlignment);
                AccessibilityFloatingMenuView accessibilityFloatingMenuView6 = AccessibilityFloatingMenuView.this;
                if (accessibilityFloatingMenuView6.mAlignment != 1) {
                    i2 = 2;
                }
                accessibilityFloatingMenuView6.mRadiusType = i2;
                accessibilityFloatingMenuView6.updateRadiusWith(accessibilityFloatingMenuView6.mSizeType, i2, accessibilityFloatingMenuView6.mTargets.size());
                AccessibilityFloatingMenuView.this.fadeOut();
                AccessibilityFloatingMenuView.this.mOnDragEndListener.ifPresent(new ShellCommandHandlerImpl$$ExternalSyntheticLambda1(this, 1));
            }
        });
        Drawable drawable = getContext().getDrawable(C1777R.C1778drawable.accessibility_floating_menu_background);
        getContext();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(1);
        recyclerView.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        recyclerView.setBackground(new InstantInsetLayerDrawable(new Drawable[]{drawable}));
        recyclerView.setAdapter(accessibilityTargetAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.mOnItemTouchListeners.add(this);
        recyclerView.animate().setInterpolator(new OvershootInterpolator());
        C06622 r13 = new RecyclerViewAccessibilityDelegate(recyclerView) {
            public final AccessibilityDelegateCompat getItemDelegate() {
                return new ItemDelegateCompat(this, AccessibilityFloatingMenuView.this);
            }
        };
        recyclerView.mAccessibilityDelegate = r13;
        ViewCompat.setAccessibilityDelegate(recyclerView, r13);
        updateListViewWith(configuration);
        addView(recyclerView);
        updateStrokeWith(getResources().getConfiguration().uiMode, this.mAlignment);
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mLastConfiguration.setTo(configuration);
        if ((configuration.diff(this.mLastConfiguration) & 4) != 0) {
            this.mCurrentLayoutParams.accessibilityTitle = getResources().getString(17039577);
        }
        updateDimensions();
        updateListViewWith(configuration);
        updateItemViewDimensionsWith(this.mSizeType);
        AccessibilityTargetAdapter accessibilityTargetAdapter = this.mAdapter;
        int i = this.mPadding;
        Objects.requireNonNull(accessibilityTargetAdapter);
        accessibilityTargetAdapter.mItemPadding = i;
        AccessibilityTargetAdapter accessibilityTargetAdapter2 = this.mAdapter;
        int i2 = this.mIconWidth;
        Objects.requireNonNull(accessibilityTargetAdapter2);
        accessibilityTargetAdapter2.mIconWidthHeight = i2;
        this.mAdapter.notifyDataSetChanged();
        int i3 = 0;
        ((GradientDrawable) ((InstantInsetLayerDrawable) this.mListView.getBackground()).getDrawable(0)).setColor(getResources().getColor(C1777R.color.accessibility_floating_menu_background));
        updateStrokeWith(configuration.uiMode, this.mAlignment);
        updateLocationWith(this.mPosition);
        updateRadiusWith(this.mSizeType, this.mRadiusType, this.mTargets.size());
        boolean hasExceededMaxLayoutHeight = hasExceededMaxLayoutHeight();
        RecyclerView recyclerView = this.mListView;
        if (!hasExceededMaxLayoutHeight) {
            i3 = 2;
        }
        recyclerView.setOverScrollMode(i3);
        setSystemGestureExclusion();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x003c, code lost:
        if (r12 != false) goto L_0x003e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0018, code lost:
        if (r12 != 3) goto L_0x00fb;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onInterceptTouchEvent$1(android.view.MotionEvent r12) {
        /*
            r11 = this;
            float r0 = r12.getRawX()
            int r0 = (int) r0
            float r1 = r12.getRawY()
            int r1 = (int) r1
            int r12 = r12.getAction()
            r2 = 0
            if (r12 == 0) goto L_0x00de
            r3 = 2
            r4 = 1
            if (r12 == r4) goto L_0x00a7
            if (r12 == r3) goto L_0x001c
            r0 = 3
            if (r12 == r0) goto L_0x00a7
            goto L_0x00fb
        L_0x001c:
            boolean r12 = r11.mIsDragging
            if (r12 != 0) goto L_0x003e
            int r12 = r11.mDownX
            int r3 = r11.mDownY
            int r12 = r0 - r12
            float r12 = (float) r12
            float r12 = android.util.MathUtils.sq(r12)
            int r3 = r1 - r3
            float r3 = (float) r3
            float r3 = android.util.MathUtils.sq(r3)
            float r3 = r3 + r12
            float r12 = r11.mSquareScaledTouchSlop
            int r12 = (r3 > r12 ? 1 : (r3 == r12 ? 0 : -1))
            if (r12 <= 0) goto L_0x003b
            r12 = r4
            goto L_0x003c
        L_0x003b:
            r12 = r2
        L_0x003c:
            if (r12 == 0) goto L_0x00fb
        L_0x003e:
            boolean r12 = r11.mIsDragging
            if (r12 != 0) goto L_0x0067
            r11.mIsDragging = r4
            float r12 = r11.mRadius
            r11.setRadius(r12, r4)
            androidx.recyclerview.widget.RecyclerView r12 = r11.mListView
            android.graphics.drawable.Drawable r12 = r12.getBackground()
            r5 = r12
            com.android.systemui.accessibility.floatingmenu.InstantInsetLayerDrawable r5 = (com.android.systemui.accessibility.floatingmenu.InstantInsetLayerDrawable) r5
            int r12 = r5.getLayerInsetLeft(r2)
            if (r12 != 0) goto L_0x005f
            int r12 = r5.getLayerInsetRight(r2)
            if (r12 != 0) goto L_0x005f
            goto L_0x0067
        L_0x005f:
            r6 = 0
            r8 = 0
            r10 = 0
            r9 = 0
            r7 = r9
            r5.setLayerInset(r6, r7, r8, r9, r10)
        L_0x0067:
            int r12 = r11.mAlignment
            int r3 = r11.mDownX
            if (r12 != r4) goto L_0x006f
            if (r0 > r3) goto L_0x0075
        L_0x006f:
            if (r12 != 0) goto L_0x0074
            if (r3 <= r0) goto L_0x0074
            goto L_0x0075
        L_0x0074:
            r4 = r2
        L_0x0075:
            r11.mTemporaryShapeType = r4
            int r12 = r11.mRelativeToPointerDownX
            int r0 = r0 + r12
            int r12 = r11.mRelativeToPointerDownY
            int r1 = r1 + r12
            android.view.WindowManager$LayoutParams r12 = r11.mCurrentLayoutParams
            android.content.res.Configuration r3 = r11.mLastConfiguration
            int r3 = r11.getMarginStartEndWith(r3)
            int r3 = -r3
            int r4 = r11.getMaxWindowX()
            int r0 = android.util.MathUtils.constrain(r0, r3, r4)
            r12.x = r0
            android.view.WindowManager$LayoutParams r12 = r11.mCurrentLayoutParams
            int r0 = r11.mDisplayHeight
            int r3 = r11.getWindowHeight()
            int r0 = r0 - r3
            int r0 = android.util.MathUtils.constrain(r1, r2, r0)
            r12.y = r0
            android.view.WindowManager r12 = r11.mWindowManager
            android.view.WindowManager$LayoutParams r0 = r11.mCurrentLayoutParams
            r12.updateViewLayout(r11, r0)
            goto L_0x00fb
        L_0x00a7:
            boolean r12 = r11.mIsDragging
            if (r12 == 0) goto L_0x00cd
            r11.mIsDragging = r2
            android.content.res.Configuration r12 = r11.mLastConfiguration
            int r12 = r11.getMarginStartEndWith(r12)
            int r12 = -r12
            int r0 = r11.getMaxWindowX()
            android.view.WindowManager$LayoutParams r1 = r11.mCurrentLayoutParams
            int r2 = r1.x
            int r5 = r12 + r0
            int r5 = r5 / r3
            if (r2 <= r5) goto L_0x00c2
            r12 = r0
        L_0x00c2:
            int r0 = r1.y
            r11.snapToLocation(r12, r0)
            int r12 = r11.mTemporaryShapeType
            r11.setShapeType(r12)
            return r4
        L_0x00cd:
            int r12 = r11.mShapeType
            if (r12 != 0) goto L_0x00d3
            r12 = r4
            goto L_0x00d4
        L_0x00d3:
            r12 = r2
        L_0x00d4:
            if (r12 != 0) goto L_0x00da
            r11.setShapeType(r2)
            return r4
        L_0x00da:
            r11.fadeOut()
            goto L_0x00fb
        L_0x00de:
            r11.fadeIn()
            r11.mDownX = r0
            r11.mDownY = r1
            android.view.WindowManager$LayoutParams r12 = r11.mCurrentLayoutParams
            int r3 = r12.x
            int r3 = r3 - r0
            r11.mRelativeToPointerDownX = r3
            int r12 = r12.y
            int r12 = r12 - r1
            r11.mRelativeToPointerDownY = r12
            androidx.recyclerview.widget.RecyclerView r11 = r11.mListView
            android.view.ViewPropertyAnimator r11 = r11.animate()
            r12 = 0
            r11.translationX(r12)
        L_0x00fb:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView.onInterceptTouchEvent$1(android.view.MotionEvent):boolean");
    }

    public final void onTargetsChanged(List<AccessibilityTarget> list) {
        int i;
        fadeIn();
        this.mTargets.clear();
        this.mTargets.addAll(list);
        this.mAdapter.notifyDataSetChanged();
        updateRadiusWith(this.mSizeType, this.mRadiusType, this.mTargets.size());
        boolean hasExceededMaxLayoutHeight = hasExceededMaxLayoutHeight();
        RecyclerView recyclerView = this.mListView;
        if (hasExceededMaxLayoutHeight) {
            i = 0;
        } else {
            i = 2;
        }
        recyclerView.setOverScrollMode(i);
        setSystemGestureExclusion();
        fadeOut();
    }

    public final void setShapeType(int i) {
        AccessibilityFloatingMenuView$$ExternalSyntheticLambda3 accessibilityFloatingMenuView$$ExternalSyntheticLambda3;
        fadeIn();
        this.mShapeType = i;
        int i2 = this.mAlignment;
        float f = ((float) ((this.mPadding * 2) + this.mIconWidth)) / 2.0f;
        if (i == 0) {
            f = 0.0f;
        }
        ViewPropertyAnimator animate = this.mListView.animate();
        if (i2 != 1) {
            f = -f;
        }
        animate.translationX(f);
        if (i == 0) {
            accessibilityFloatingMenuView$$ExternalSyntheticLambda3 = null;
        } else {
            accessibilityFloatingMenuView$$ExternalSyntheticLambda3 = new AccessibilityFloatingMenuView$$ExternalSyntheticLambda3(this);
        }
        setOnTouchListener(accessibilityFloatingMenuView$$ExternalSyntheticLambda3);
        fadeOut();
    }

    public final void setSizeType(int i) {
        int i2;
        fadeIn();
        this.mSizeType = i;
        updateItemViewDimensionsWith(i);
        AccessibilityTargetAdapter accessibilityTargetAdapter = this.mAdapter;
        int i3 = this.mPadding;
        Objects.requireNonNull(accessibilityTargetAdapter);
        accessibilityTargetAdapter.mItemPadding = i3;
        AccessibilityTargetAdapter accessibilityTargetAdapter2 = this.mAdapter;
        int i4 = this.mIconWidth;
        Objects.requireNonNull(accessibilityTargetAdapter2);
        accessibilityTargetAdapter2.mIconWidthHeight = i4;
        this.mAdapter.notifyDataSetChanged();
        updateRadiusWith(i, this.mRadiusType, this.mTargets.size());
        updateLocationWith(this.mPosition);
        boolean hasExceededMaxLayoutHeight = hasExceededMaxLayoutHeight();
        RecyclerView recyclerView = this.mListView;
        if (hasExceededMaxLayoutHeight) {
            i2 = 0;
        } else {
            i2 = 2;
        }
        recyclerView.setOverScrollMode(i2);
        int i5 = this.mShapeType;
        int i6 = this.mAlignment;
        float f = ((float) ((this.mPadding * 2) + this.mIconWidth)) / 2.0f;
        if (i5 == 0) {
            f = 0.0f;
        }
        ViewPropertyAnimator animate = this.mListView.animate();
        if (i6 != 1) {
            f = -f;
        }
        animate.translationX(f);
        setSystemGestureExclusion();
        fadeOut();
    }

    public final void updateDimensions() {
        Resources resources = getResources();
        updateDisplaySizeWith(this.mWindowManager.getCurrentWindowMetrics());
        this.mMargin = resources.getDimensionPixelSize(C1777R.dimen.accessibility_floating_menu_margin);
        this.mInset = resources.getDimensionPixelSize(C1777R.dimen.accessibility_floating_menu_stroke_inset);
        this.mSquareScaledTouchSlop = MathUtils.sq((float) ViewConfiguration.get(getContext()).getScaledTouchSlop());
        updateItemViewDimensionsWith(this.mSizeType);
    }

    public final void updateDisplaySizeWith(WindowMetrics windowMetrics) {
        Rect bounds = windowMetrics.getBounds();
        Insets insetsIgnoringVisibility = windowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
        this.mDisplayInsetsRect.set(insetsIgnoringVisibility.toRect());
        bounds.inset(insetsIgnoringVisibility);
        this.mDisplayWidth = bounds.width();
        this.mDisplayHeight = bounds.height();
    }

    public final void updateItemViewDimensionsWith(int i) {
        int i2;
        int i3;
        Resources resources = getResources();
        if (i == 0) {
            i2 = C1777R.dimen.accessibility_floating_menu_small_padding;
        } else {
            i2 = C1777R.dimen.accessibility_floating_menu_large_padding;
        }
        this.mPadding = resources.getDimensionPixelSize(i2);
        if (i == 0) {
            i3 = C1777R.dimen.accessibility_floating_menu_small_width_height;
        } else {
            i3 = C1777R.dimen.accessibility_floating_menu_large_width_height;
        }
        int dimensionPixelSize = resources.getDimensionPixelSize(i3);
        this.mIconWidth = dimensionPixelSize;
        this.mIconHeight = dimensionPixelSize;
    }

    public final void updateListViewWith(Configuration configuration) {
        int marginStartEndWith = getMarginStartEndWith(configuration);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mListView.getLayoutParams();
        int i = this.mMargin;
        layoutParams.setMargins(marginStartEndWith, i, marginStartEndWith, i);
        this.mListView.setLayoutParams(layoutParams);
        this.mListView.setElevation((float) getResources().getDimensionPixelSize(C1777R.dimen.accessibility_floating_menu_elevation));
    }

    public final void updateLocationWith(Position position) {
        boolean z;
        int i;
        Objects.requireNonNull(position);
        if (position.mPercentageX < 0.5f) {
            z = false;
        } else {
            z = true;
        }
        WindowManager.LayoutParams layoutParams = this.mCurrentLayoutParams;
        if (z) {
            i = getMaxWindowX();
        } else {
            i = -getMarginStartEndWith(this.mLastConfiguration);
        }
        layoutParams.x = i;
        this.mCurrentLayoutParams.y = Math.max(0, ((int) (position.mPercentageY * ((float) (this.mDisplayHeight - getWindowHeight())))) - getInterval());
        this.mWindowManager.updateViewLayout(this, this.mCurrentLayoutParams);
    }

    public final void updateRadiusWith(int i, int i2, int i3) {
        int i4;
        Resources resources = getResources();
        if (i == 0) {
            if (i3 > 1) {
                i4 = C1777R.dimen.accessibility_floating_menu_small_multiple_radius;
            } else {
                i4 = C1777R.dimen.accessibility_floating_menu_small_single_radius;
            }
        } else if (i3 > 1) {
            i4 = C1777R.dimen.accessibility_floating_menu_large_multiple_radius;
        } else {
            i4 = C1777R.dimen.accessibility_floating_menu_large_single_radius;
        }
        float dimensionPixelSize = (float) resources.getDimensionPixelSize(i4);
        this.mRadius = dimensionPixelSize;
        setRadius(dimensionPixelSize, i2);
    }

    public final void updateStrokeWith(int i, int i2) {
        boolean z;
        updateInsetWith(i, i2);
        if ((i & 48) == 32) {
            z = true;
        } else {
            z = false;
        }
        Resources resources = getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(C1777R.dimen.accessibility_floating_menu_stroke_width);
        if (!z) {
            dimensionPixelSize = 0;
        }
        ((GradientDrawable) ((InstantInsetLayerDrawable) this.mListView.getBackground()).getDrawable(0)).setStroke(dimensionPixelSize, resources.getColor(C1777R.color.accessibility_floating_menu_stroke_dark));
    }
}
