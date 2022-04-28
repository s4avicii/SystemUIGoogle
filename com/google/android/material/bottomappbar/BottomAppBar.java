package com.google.android.material.bottomappbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.customview.view.AbsSavedState;
import androidx.mediarouter.R$bool;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.R$styleable;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.animation.TransformationCallback;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButtonImpl;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;
import java.util.WeakHashMap;

public class BottomAppBar extends Toolbar implements CoordinatorLayout.AttachedBehavior {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Behavior behavior;
    public int bottomInset;
    public int fabAlignmentMode;
    public C19631 fabAnimationListener;
    public boolean fabAttached;
    public final int fabOffsetEndMode;
    public C19642 fabTransformationCallback;
    public boolean hideOnScroll;
    public int leftInset;
    public final MaterialShapeDrawable materialShapeDrawable;
    public boolean menuAnimatingWithFabAlignmentMode;
    public Animator menuAnimator;
    public Integer navigationIconTint;
    public final boolean paddingBottomSystemWindowInsets;
    public final boolean paddingLeftSystemWindowInsets;
    public final boolean paddingRightSystemWindowInsets;
    public int rightInset;

    public static class Behavior extends HideBottomViewOnScrollBehavior<BottomAppBar> {
        public final Rect fabContentRect = new Rect();
        public final C19691 fabLayoutListener = new View.OnLayoutChangeListener() {
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                BottomAppBar bottomAppBar = Behavior.this.viewRef.get();
                if (bottomAppBar == null || !(view instanceof FloatingActionButton)) {
                    view.removeOnLayoutChangeListener(this);
                    return;
                }
                FloatingActionButton floatingActionButton = (FloatingActionButton) view;
                Rect rect = Behavior.this.fabContentRect;
                Objects.requireNonNull(floatingActionButton);
                rect.set(0, 0, floatingActionButton.getMeasuredWidth(), floatingActionButton.getMeasuredHeight());
                floatingActionButton.offsetRectWithShadow(rect);
                int height = Behavior.this.fabContentRect.height();
                float f = (float) height;
                BottomAppBarTopEdgeTreatment topEdgeTreatment = bottomAppBar.getTopEdgeTreatment();
                Objects.requireNonNull(topEdgeTreatment);
                if (f != topEdgeTreatment.fabDiameter) {
                    BottomAppBarTopEdgeTreatment topEdgeTreatment2 = bottomAppBar.getTopEdgeTreatment();
                    Objects.requireNonNull(topEdgeTreatment2);
                    topEdgeTreatment2.fabDiameter = f;
                    bottomAppBar.materialShapeDrawable.invalidateSelf();
                }
                FloatingActionButtonImpl impl = floatingActionButton.getImpl();
                Objects.requireNonNull(impl);
                ShapeAppearanceModel shapeAppearanceModel = impl.shapeAppearance;
                Objects.requireNonNull(shapeAppearanceModel);
                float cornerSize = shapeAppearanceModel.topLeftCornerSize.getCornerSize(new RectF(Behavior.this.fabContentRect));
                BottomAppBarTopEdgeTreatment topEdgeTreatment3 = bottomAppBar.getTopEdgeTreatment();
                Objects.requireNonNull(topEdgeTreatment3);
                if (cornerSize != topEdgeTreatment3.fabCornerSize) {
                    BottomAppBarTopEdgeTreatment topEdgeTreatment4 = bottomAppBar.getTopEdgeTreatment();
                    Objects.requireNonNull(topEdgeTreatment4);
                    topEdgeTreatment4.fabCornerSize = cornerSize;
                    bottomAppBar.materialShapeDrawable.invalidateSelf();
                }
                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
                if (Behavior.this.originalBottomMargin == 0) {
                    layoutParams.bottomMargin = bottomAppBar.bottomInset + (bottomAppBar.getResources().getDimensionPixelOffset(C1777R.dimen.mtrl_bottomappbar_fab_bottom_margin) - ((floatingActionButton.getMeasuredHeight() - height) / 2));
                    layoutParams.leftMargin = bottomAppBar.leftInset;
                    layoutParams.rightMargin = bottomAppBar.rightInset;
                    if (ViewUtils.isLayoutRtl(floatingActionButton)) {
                        layoutParams.leftMargin += bottomAppBar.fabOffsetEndMode;
                    } else {
                        layoutParams.rightMargin += bottomAppBar.fabOffsetEndMode;
                    }
                }
            }
        };
        public int originalBottomMargin;
        public WeakReference<BottomAppBar> viewRef;

        public Behavior() {
        }

        public final boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int i, int i2) {
            BottomAppBar bottomAppBar = (BottomAppBar) view;
            if (!bottomAppBar.hideOnScroll || !super.onStartNestedScroll(coordinatorLayout, bottomAppBar, view2, view3, i, i2)) {
                return false;
            }
            return true;
        }

        public final boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
            BottomAppBar bottomAppBar = (BottomAppBar) view;
            this.viewRef = new WeakReference<>(bottomAppBar);
            int i2 = BottomAppBar.$r8$clinit;
            View findDependentView = bottomAppBar.findDependentView();
            if (findDependentView != null) {
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                if (!ViewCompat.Api19Impl.isLaidOut(findDependentView)) {
                    CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) findDependentView.getLayoutParams();
                    layoutParams.anchorGravity = 49;
                    this.originalBottomMargin = layoutParams.bottomMargin;
                    if (findDependentView instanceof FloatingActionButton) {
                        FloatingActionButton floatingActionButton = (FloatingActionButton) findDependentView;
                        FloatingActionButtonImpl impl = floatingActionButton.getImpl();
                        Objects.requireNonNull(impl);
                        if (impl.showMotionSpec == null) {
                            MotionSpec createFromResource = MotionSpec.createFromResource(floatingActionButton.getContext(), C1777R.animator.mtrl_fab_show_motion_spec);
                            FloatingActionButtonImpl impl2 = floatingActionButton.getImpl();
                            Objects.requireNonNull(impl2);
                            impl2.showMotionSpec = createFromResource;
                        }
                        FloatingActionButtonImpl impl3 = floatingActionButton.getImpl();
                        Objects.requireNonNull(impl3);
                        if (impl3.hideMotionSpec == null) {
                            MotionSpec createFromResource2 = MotionSpec.createFromResource(floatingActionButton.getContext(), C1777R.animator.mtrl_fab_hide_motion_spec);
                            FloatingActionButtonImpl impl4 = floatingActionButton.getImpl();
                            Objects.requireNonNull(impl4);
                            impl4.hideMotionSpec = createFromResource2;
                        }
                        floatingActionButton.addOnLayoutChangeListener(this.fabLayoutListener);
                        C19631 r1 = bottomAppBar.fabAnimationListener;
                        FloatingActionButtonImpl impl5 = floatingActionButton.getImpl();
                        Objects.requireNonNull(impl5);
                        if (impl5.hideListeners == null) {
                            impl5.hideListeners = new ArrayList<>();
                        }
                        impl5.hideListeners.add(r1);
                        C19689 r12 = new AnimatorListenerAdapter() {
                            public final void onAnimationStart(Animator animator) {
                                FloatingActionButton floatingActionButton;
                                BottomAppBar.this.fabAnimationListener.onAnimationStart(animator);
                                BottomAppBar bottomAppBar = BottomAppBar.this;
                                Objects.requireNonNull(bottomAppBar);
                                View findDependentView = bottomAppBar.findDependentView();
                                if (findDependentView instanceof FloatingActionButton) {
                                    floatingActionButton = (FloatingActionButton) findDependentView;
                                } else {
                                    floatingActionButton = null;
                                }
                                if (floatingActionButton != null) {
                                    floatingActionButton.setTranslationX(BottomAppBar.this.getFabTranslationX$1());
                                }
                            }
                        };
                        FloatingActionButtonImpl impl6 = floatingActionButton.getImpl();
                        Objects.requireNonNull(impl6);
                        if (impl6.showListeners == null) {
                            impl6.showListeners = new ArrayList<>();
                        }
                        impl6.showListeners.add(r12);
                        C19642 r13 = bottomAppBar.fabTransformationCallback;
                        FloatingActionButtonImpl impl7 = floatingActionButton.getImpl();
                        FloatingActionButton.TransformationCallbackWrapper transformationCallbackWrapper = new FloatingActionButton.TransformationCallbackWrapper(r13);
                        Objects.requireNonNull(impl7);
                        if (impl7.transformationCallbacks == null) {
                            impl7.transformationCallbacks = new ArrayList<>();
                        }
                        impl7.transformationCallbacks.add(transformationCallbackWrapper);
                    }
                    bottomAppBar.setCutoutState();
                }
            }
            coordinatorLayout.onLayoutChild(bottomAppBar, i);
            super.onLayoutChild(coordinatorLayout, bottomAppBar, i);
            return false;
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }
    }

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() {
            public final Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel, (ClassLoader) null);
            }

            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public int fabAlignmentMode;
        public boolean fabAttached;

        public SavedState(Toolbar.SavedState savedState) {
            super(savedState);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.fabAlignmentMode = parcel.readInt();
            this.fabAttached = parcel.readInt() != 0;
        }

        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.mSuperState, i);
            parcel.writeInt(this.fabAlignmentMode);
            parcel.writeInt(this.fabAttached ? 1 : 0);
        }
    }

    public BottomAppBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public final int getActionMenuViewTranslationX(ActionMenuView actionMenuView, int i, boolean z) {
        int i2;
        int i3;
        int i4;
        boolean z2;
        if (i != 1 || !z) {
            return 0;
        }
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        if (isLayoutRtl) {
            i2 = getMeasuredWidth();
        } else {
            i2 = 0;
        }
        for (int i5 = 0; i5 < getChildCount(); i5++) {
            View childAt = getChildAt(i5);
            if (!(childAt.getLayoutParams() instanceof Toolbar.LayoutParams) || (((Toolbar.LayoutParams) childAt.getLayoutParams()).gravity & 8388615) != 8388611) {
                z2 = false;
            } else {
                z2 = true;
            }
            if (z2) {
                if (isLayoutRtl) {
                    i2 = Math.min(i2, childAt.getLeft());
                } else {
                    i2 = Math.max(i2, childAt.getRight());
                }
            }
        }
        if (isLayoutRtl) {
            i3 = actionMenuView.getRight();
        } else {
            i3 = actionMenuView.getLeft();
        }
        if (isLayoutRtl) {
            i4 = this.rightInset;
        } else {
            i4 = -this.leftInset;
        }
        return i2 - (i3 + i4);
    }

    public final void setActionMenuViewPosition() {
        ActionMenuView actionMenuView;
        int i = 0;
        while (true) {
            if (i >= getChildCount()) {
                actionMenuView = null;
                break;
            }
            View childAt = getChildAt(i);
            if (childAt instanceof ActionMenuView) {
                actionMenuView = (ActionMenuView) childAt;
                break;
            }
            i++;
        }
        if (actionMenuView != null && this.menuAnimator == null) {
            actionMenuView.setAlpha(1.0f);
            if (!isFabVisibleOrWillBeShown()) {
                translateActionMenuView$1(actionMenuView, 0, false);
            } else {
                translateActionMenuView$1(actionMenuView, this.fabAlignmentMode, this.fabAttached);
            }
        }
    }

    public final void setSubtitle(CharSequence charSequence) {
    }

    public final void setTitle(CharSequence charSequence) {
    }

    public BottomAppBar(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, C1777R.attr.bottomAppBarStyle, 2132018625), attributeSet, C1777R.attr.bottomAppBarStyle);
        MaterialShapeDrawable materialShapeDrawable2 = new MaterialShapeDrawable();
        this.materialShapeDrawable = materialShapeDrawable2;
        this.menuAnimatingWithFabAlignmentMode = false;
        this.fabAttached = true;
        this.fabAnimationListener = new AnimatorListenerAdapter() {
            public final void onAnimationStart(Animator animator) {
                ActionMenuView actionMenuView;
                BottomAppBar bottomAppBar = BottomAppBar.this;
                if (!bottomAppBar.menuAnimatingWithFabAlignmentMode) {
                    int i = bottomAppBar.fabAlignmentMode;
                    boolean z = bottomAppBar.fabAttached;
                    WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    if (!ViewCompat.Api19Impl.isLaidOut(bottomAppBar)) {
                        bottomAppBar.menuAnimatingWithFabAlignmentMode = false;
                        return;
                    }
                    Animator animator2 = bottomAppBar.menuAnimator;
                    if (animator2 != null) {
                        animator2.cancel();
                    }
                    ArrayList arrayList = new ArrayList();
                    if (!bottomAppBar.isFabVisibleOrWillBeShown()) {
                        i = 0;
                        z = false;
                    }
                    int i2 = 0;
                    while (true) {
                        if (i2 >= bottomAppBar.getChildCount()) {
                            actionMenuView = null;
                            break;
                        }
                        View childAt = bottomAppBar.getChildAt(i2);
                        if (childAt instanceof ActionMenuView) {
                            actionMenuView = (ActionMenuView) childAt;
                            break;
                        }
                        i2++;
                    }
                    if (actionMenuView != null) {
                        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(actionMenuView, "alpha", new float[]{1.0f});
                        if (Math.abs(actionMenuView.getTranslationX() - ((float) bottomAppBar.getActionMenuViewTranslationX(actionMenuView, i, z))) > 1.0f) {
                            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(actionMenuView, "alpha", new float[]{0.0f});
                            ofFloat2.addListener(new AnimatorListenerAdapter(actionMenuView, i, z) {
                                public boolean cancelled;
                                public final /* synthetic */ ActionMenuView val$actionMenuView;
                                public final /* synthetic */ boolean val$targetAttached;
                                public final /* synthetic */ int val$targetMode;

                                public final void onAnimationCancel(Animator animator) {
                                    this.cancelled = true;
                                }

                                {
                                    this.val$actionMenuView = r2;
                                    this.val$targetMode = r3;
                                    this.val$targetAttached = r4;
                                }

                                public final void onAnimationEnd(Animator animator) {
                                    if (!this.cancelled) {
                                        Objects.requireNonNull(BottomAppBar.this);
                                        Objects.requireNonNull(BottomAppBar.this);
                                        BottomAppBar.this.translateActionMenuView$1(this.val$actionMenuView, this.val$targetMode, this.val$targetAttached);
                                    }
                                }
                            });
                            AnimatorSet animatorSet = new AnimatorSet();
                            animatorSet.setDuration(150);
                            animatorSet.playSequentially(new Animator[]{ofFloat2, ofFloat});
                            arrayList.add(animatorSet);
                        } else if (actionMenuView.getAlpha() < 1.0f) {
                            arrayList.add(ofFloat);
                        }
                    }
                    AnimatorSet animatorSet2 = new AnimatorSet();
                    animatorSet2.playTogether(arrayList);
                    bottomAppBar.menuAnimator = animatorSet2;
                    animatorSet2.addListener(new AnimatorListenerAdapter() {
                        public final void onAnimationEnd(Animator animator) {
                            BottomAppBar bottomAppBar = BottomAppBar.this;
                            int i = BottomAppBar.$r8$clinit;
                            Objects.requireNonNull(bottomAppBar);
                            BottomAppBar bottomAppBar2 = BottomAppBar.this;
                            bottomAppBar2.menuAnimatingWithFabAlignmentMode = false;
                            bottomAppBar2.menuAnimator = null;
                        }

                        public final void onAnimationStart(Animator animator) {
                            BottomAppBar bottomAppBar = BottomAppBar.this;
                            int i = BottomAppBar.$r8$clinit;
                            Objects.requireNonNull(bottomAppBar);
                        }
                    });
                    bottomAppBar.menuAnimator.start();
                }
            }
        };
        this.fabTransformationCallback = new TransformationCallback<FloatingActionButton>() {
        };
        Context context2 = getContext();
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R$styleable.BottomAppBar, C1777R.attr.bottomAppBarStyle, 2132018625, new int[0]);
        ColorStateList colorStateList = MaterialResources.getColorStateList(context2, obtainStyledAttributes, 0);
        if (obtainStyledAttributes.hasValue(8)) {
            this.navigationIconTint = Integer.valueOf(obtainStyledAttributes.getColor(8, -1));
            Drawable navigationIcon = getNavigationIcon();
            if (navigationIcon != null) {
                setNavigationIcon(navigationIcon);
            }
        }
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(1, 0);
        this.fabAlignmentMode = obtainStyledAttributes.getInt(2, 0);
        obtainStyledAttributes.getInt(3, 0);
        this.hideOnScroll = obtainStyledAttributes.getBoolean(7, false);
        this.paddingBottomSystemWindowInsets = obtainStyledAttributes.getBoolean(9, false);
        this.paddingLeftSystemWindowInsets = obtainStyledAttributes.getBoolean(10, false);
        this.paddingRightSystemWindowInsets = obtainStyledAttributes.getBoolean(11, false);
        obtainStyledAttributes.recycle();
        this.fabOffsetEndMode = getResources().getDimensionPixelOffset(C1777R.dimen.mtrl_bottomappbar_fabOffsetEndMode);
        BottomAppBarTopEdgeTreatment bottomAppBarTopEdgeTreatment = new BottomAppBarTopEdgeTreatment((float) obtainStyledAttributes.getDimensionPixelOffset(4, 0), (float) obtainStyledAttributes.getDimensionPixelOffset(5, 0), (float) obtainStyledAttributes.getDimensionPixelOffset(6, 0));
        ShapeAppearanceModel.Builder builder = new ShapeAppearanceModel.Builder();
        builder.topEdge = bottomAppBarTopEdgeTreatment;
        materialShapeDrawable2.setShapeAppearanceModel(new ShapeAppearanceModel(builder));
        materialShapeDrawable2.setShadowCompatibilityMode();
        materialShapeDrawable2.setPaintStyle(Paint.Style.FILL);
        materialShapeDrawable2.initializeElevationOverlay(context2);
        setElevation((float) dimensionPixelSize);
        materialShapeDrawable2.setTintList(colorStateList);
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.setBackground(this, materialShapeDrawable2);
        C19653 r13 = new ViewUtils.OnApplyWindowInsetsListener() {
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat, ViewUtils.RelativePadding relativePadding) {
                boolean z;
                BottomAppBar bottomAppBar = BottomAppBar.this;
                if (bottomAppBar.paddingBottomSystemWindowInsets) {
                    bottomAppBar.bottomInset = windowInsetsCompat.getSystemWindowInsetBottom();
                }
                BottomAppBar bottomAppBar2 = BottomAppBar.this;
                boolean z2 = true;
                boolean z3 = false;
                if (bottomAppBar2.paddingLeftSystemWindowInsets) {
                    if (bottomAppBar2.leftInset != windowInsetsCompat.getSystemWindowInsetLeft()) {
                        z = true;
                    } else {
                        z = false;
                    }
                    BottomAppBar.this.leftInset = windowInsetsCompat.getSystemWindowInsetLeft();
                } else {
                    z = false;
                }
                BottomAppBar bottomAppBar3 = BottomAppBar.this;
                if (bottomAppBar3.paddingRightSystemWindowInsets) {
                    if (bottomAppBar3.rightInset == windowInsetsCompat.getSystemWindowInsetRight()) {
                        z2 = false;
                    }
                    BottomAppBar.this.rightInset = windowInsetsCompat.getSystemWindowInsetRight();
                    z3 = z2;
                }
                if (z || z3) {
                    BottomAppBar bottomAppBar4 = BottomAppBar.this;
                    Objects.requireNonNull(bottomAppBar4);
                    Animator animator = bottomAppBar4.menuAnimator;
                    if (animator != null) {
                        animator.cancel();
                    }
                    BottomAppBar.this.setCutoutState();
                    BottomAppBar.this.setActionMenuViewPosition();
                }
                return windowInsetsCompat;
            }
        };
        TypedArray obtainStyledAttributes2 = getContext().obtainStyledAttributes(attributeSet, R$styleable.Insets, C1777R.attr.bottomAppBarStyle, 2132018625);
        boolean z = obtainStyledAttributes2.getBoolean(0, false);
        boolean z2 = obtainStyledAttributes2.getBoolean(1, false);
        boolean z3 = obtainStyledAttributes2.getBoolean(2, false);
        obtainStyledAttributes2.recycle();
        ViewUtils.doOnApplyWindowInsets(this, new ViewUtils.OnApplyWindowInsetsListener(z, z2, z3, r13) {
            public final /* synthetic */ OnApplyWindowInsetsListener val$listener;
            public final /* synthetic */ boolean val$paddingBottomSystemWindowInsets;
            public final /* synthetic */ boolean val$paddingLeftSystemWindowInsets;
            public final /* synthetic */ boolean val$paddingRightSystemWindowInsets;

            {
                this.val$paddingBottomSystemWindowInsets = r1;
                this.val$paddingLeftSystemWindowInsets = r2;
                this.val$paddingRightSystemWindowInsets = r3;
                this.val$listener = r4;
            }

            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat, RelativePadding relativePadding) {
                if (this.val$paddingBottomSystemWindowInsets) {
                    relativePadding.bottom = windowInsetsCompat.getSystemWindowInsetBottom() + relativePadding.bottom;
                }
                boolean isLayoutRtl = ViewUtils.isLayoutRtl(view);
                if (this.val$paddingLeftSystemWindowInsets) {
                    if (isLayoutRtl) {
                        relativePadding.end = windowInsetsCompat.getSystemWindowInsetLeft() + relativePadding.end;
                    } else {
                        relativePadding.start = windowInsetsCompat.getSystemWindowInsetLeft() + relativePadding.start;
                    }
                }
                if (this.val$paddingRightSystemWindowInsets) {
                    if (isLayoutRtl) {
                        relativePadding.start = windowInsetsCompat.getSystemWindowInsetRight() + relativePadding.start;
                    } else {
                        relativePadding.end = windowInsetsCompat.getSystemWindowInsetRight() + relativePadding.end;
                    }
                }
                int i = relativePadding.start;
                int i2 = relativePadding.top;
                int i3 = relativePadding.end;
                int i4 = relativePadding.bottom;
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api17Impl.setPaddingRelative(view, i, i2, i3, i4);
                OnApplyWindowInsetsListener onApplyWindowInsetsListener = this.val$listener;
                if (onApplyWindowInsetsListener != null) {
                    return onApplyWindowInsetsListener.onApplyWindowInsets(view, windowInsetsCompat, relativePadding);
                }
                return windowInsetsCompat;
            }
        });
    }

    public final CoordinatorLayout.Behavior getBehavior() {
        if (this.behavior == null) {
            this.behavior = new Behavior();
        }
        return this.behavior;
    }

    public final float getFabTranslationX$1() {
        int i;
        int i2 = this.fabAlignmentMode;
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        int i3 = 1;
        if (i2 != 1) {
            return 0.0f;
        }
        if (isLayoutRtl) {
            i = this.leftInset;
        } else {
            i = this.rightInset;
        }
        int measuredWidth = (getMeasuredWidth() / 2) - (this.fabOffsetEndMode + i);
        if (isLayoutRtl) {
            i3 = -1;
        }
        return (float) (measuredWidth * i3);
    }

    public final BottomAppBarTopEdgeTreatment getTopEdgeTreatment() {
        MaterialShapeDrawable materialShapeDrawable2 = this.materialShapeDrawable;
        Objects.requireNonNull(materialShapeDrawable2);
        ShapeAppearanceModel shapeAppearanceModel = materialShapeDrawable2.drawableState.shapeAppearanceModel;
        Objects.requireNonNull(shapeAppearanceModel);
        return (BottomAppBarTopEdgeTreatment) shapeAppearanceModel.topEdge;
    }

    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        Objects.requireNonNull(savedState);
        super.onRestoreInstanceState(savedState.mSuperState);
        this.fabAlignmentMode = savedState.fabAlignmentMode;
        this.fabAttached = savedState.fabAttached;
    }

    public final void setElevation(float f) {
        this.materialShapeDrawable.setElevation(f);
        MaterialShapeDrawable materialShapeDrawable2 = this.materialShapeDrawable;
        Objects.requireNonNull(materialShapeDrawable2);
        int shadowOffsetY = materialShapeDrawable2.drawableState.shadowCompatRadius - this.materialShapeDrawable.getShadowOffsetY();
        if (this.behavior == null) {
            this.behavior = new Behavior();
        }
        Behavior behavior2 = this.behavior;
        Objects.requireNonNull(behavior2);
        behavior2.additionalHiddenOffsetY = shadowOffsetY;
        if (behavior2.currentState == 1) {
            setTranslationY((float) (behavior2.height + shadowOffsetY));
        }
    }

    public final void setNavigationIcon(Drawable drawable) {
        if (!(drawable == null || this.navigationIconTint == null)) {
            drawable = drawable.mutate();
            drawable.setTint(this.navigationIconTint.intValue());
        }
        super.setNavigationIcon(drawable);
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x003e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.view.View findDependentView() {
        /*
            r3 = this;
            android.view.ViewParent r0 = r3.getParent()
            boolean r0 = r0 instanceof androidx.coordinatorlayout.widget.CoordinatorLayout
            r1 = 0
            if (r0 != 0) goto L_0x000a
            return r1
        L_0x000a:
            android.view.ViewParent r0 = r3.getParent()
            androidx.coordinatorlayout.widget.CoordinatorLayout r0 = (androidx.coordinatorlayout.widget.CoordinatorLayout) r0
            java.util.Objects.requireNonNull(r0)
            androidx.coordinatorlayout.widget.DirectedAcyclicGraph r0 = r0.mChildDag
            java.util.Objects.requireNonNull(r0)
            java.lang.Object r0 = r0.mGraph
            androidx.collection.SimpleArrayMap r0 = (androidx.collection.SimpleArrayMap) r0
            java.util.Objects.requireNonNull(r0)
            java.lang.Object r3 = r0.getOrDefault(r3, r1)
            java.util.ArrayList r3 = (java.util.ArrayList) r3
            if (r3 != 0) goto L_0x0029
            r0 = r1
            goto L_0x002e
        L_0x0029:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>(r3)
        L_0x002e:
            if (r0 != 0) goto L_0x0034
            java.util.List r0 = java.util.Collections.emptyList()
        L_0x0034:
            java.util.Iterator r3 = r0.iterator()
        L_0x0038:
            boolean r0 = r3.hasNext()
            if (r0 == 0) goto L_0x004d
            java.lang.Object r0 = r3.next()
            android.view.View r0 = (android.view.View) r0
            boolean r2 = r0 instanceof com.google.android.material.floatingactionbutton.FloatingActionButton
            if (r2 != 0) goto L_0x004c
            boolean r2 = r0 instanceof com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
            if (r2 == 0) goto L_0x0038
        L_0x004c:
            return r0
        L_0x004d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.bottomappbar.BottomAppBar.findDependentView():android.view.View");
    }

    public final boolean isFabVisibleOrWillBeShown() {
        FloatingActionButton floatingActionButton;
        boolean z;
        View findDependentView = findDependentView();
        if (findDependentView instanceof FloatingActionButton) {
            floatingActionButton = (FloatingActionButton) findDependentView;
        } else {
            floatingActionButton = null;
        }
        if (floatingActionButton != null) {
            FloatingActionButtonImpl impl = floatingActionButton.getImpl();
            Objects.requireNonNull(impl);
            if (impl.view.getVisibility() == 0 ? impl.animState == 1 : impl.animState != 2) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        R$bool.setParentAbsoluteElevation(this, this.materialShapeDrawable);
        if (getParent() instanceof ViewGroup) {
            ((ViewGroup) getParent()).setClipChildren(false);
        }
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            Animator animator = this.menuAnimator;
            if (animator != null) {
                animator.cancel();
            }
            setCutoutState();
        }
        setActionMenuViewPosition();
    }

    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState((Toolbar.SavedState) super.onSaveInstanceState());
        savedState.fabAlignmentMode = this.fabAlignmentMode;
        savedState.fabAttached = this.fabAttached;
        return savedState;
    }

    public final void setCutoutState() {
        float f;
        BottomAppBarTopEdgeTreatment topEdgeTreatment = getTopEdgeTreatment();
        float fabTranslationX$1 = getFabTranslationX$1();
        Objects.requireNonNull(topEdgeTreatment);
        topEdgeTreatment.horizontalOffset = fabTranslationX$1;
        View findDependentView = findDependentView();
        MaterialShapeDrawable materialShapeDrawable2 = this.materialShapeDrawable;
        if (!this.fabAttached || !isFabVisibleOrWillBeShown()) {
            f = 0.0f;
        } else {
            f = 1.0f;
        }
        materialShapeDrawable2.setInterpolation(f);
        if (findDependentView != null) {
            BottomAppBarTopEdgeTreatment topEdgeTreatment2 = getTopEdgeTreatment();
            Objects.requireNonNull(topEdgeTreatment2);
            findDependentView.setTranslationY(-topEdgeTreatment2.cradleVerticalOffset);
            findDependentView.setTranslationX(getFabTranslationX$1());
        }
    }

    public final void translateActionMenuView$1(ActionMenuView actionMenuView, int i, boolean z) {
        actionMenuView.setTranslationX((float) getActionMenuViewTranslationX(actionMenuView, i, z));
    }
}
