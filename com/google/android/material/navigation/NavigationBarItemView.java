package com.google.android.material.navigation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.badge.BadgeDrawable;
import java.util.Objects;
import java.util.WeakHashMap;

public abstract class NavigationBarItemView extends FrameLayout implements MenuView.ItemView {
    public static final ActiveIndicatorTransform ACTIVE_INDICATOR_LABELED_TRANSFORM = new ActiveIndicatorTransform();
    public static final ActiveIndicatorUnlabeledTransform ACTIVE_INDICATOR_UNLABELED_TRANSFORM = new ActiveIndicatorUnlabeledTransform();
    public static final int[] CHECKED_STATE_SET = {16842912};
    public ValueAnimator activeIndicatorAnimator;
    public int activeIndicatorDesiredHeight = 0;
    public int activeIndicatorDesiredWidth = 0;
    public boolean activeIndicatorEnabled = false;
    public int activeIndicatorMarginHorizontal = 0;
    public float activeIndicatorProgress = 0.0f;
    public boolean activeIndicatorResizeable = false;
    public ActiveIndicatorTransform activeIndicatorTransform = ACTIVE_INDICATOR_LABELED_TRANSFORM;
    public final View activeIndicatorView;
    public BadgeDrawable badgeDrawable;
    public final ImageView icon;
    public final FrameLayout iconContainer;
    public ColorStateList iconTint;
    public boolean initialized = false;
    public boolean isShifting;
    public MenuItemImpl itemData;
    public int itemPaddingBottom;
    public int itemPaddingTop;
    public final ViewGroup labelGroup;
    public int labelVisibilityMode;
    public final TextView largeLabel;
    public Drawable originalIconDrawable;
    public float scaleDownFactor;
    public float scaleUpFactor;
    public float shiftAmount;
    public final TextView smallLabel;
    public Drawable wrappedIconDrawable;

    public static class ActiveIndicatorTransform {
        public float calculateScaleY(float f, float f2) {
            return 1.0f;
        }
    }

    public static class ActiveIndicatorUnlabeledTransform extends ActiveIndicatorTransform {
        public final float calculateScaleY(float f, float f2) {
            LinearInterpolator linearInterpolator = AnimationUtils.LINEAR_INTERPOLATOR;
            return (f * 0.6f) + 0.4f;
        }
    }

    public int getItemDefaultMarginResId() {
        return C1777R.dimen.mtrl_navigation_bar_item_default_margin;
    }

    public abstract int getItemLayoutResId();

    public final View getIconOrContainer() {
        FrameLayout frameLayout = this.iconContainer;
        if (frameLayout != null) {
            return frameLayout;
        }
        return this.icon;
    }

    public final int getSuggestedMinimumHeight() {
        int i;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.labelGroup.getLayoutParams();
        BadgeDrawable badgeDrawable2 = this.badgeDrawable;
        if (badgeDrawable2 != null) {
            i = badgeDrawable2.getMinimumHeight() / 2;
        } else {
            i = 0;
        }
        int max = Math.max(i, ((FrameLayout.LayoutParams) getIconOrContainer().getLayoutParams()).topMargin);
        return this.labelGroup.getMeasuredHeight() + this.icon.getMeasuredWidth() + max + i + layoutParams.topMargin + layoutParams.bottomMargin;
    }

    public final int getSuggestedMinimumWidth() {
        int i;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.labelGroup.getLayoutParams();
        int measuredWidth = this.labelGroup.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
        BadgeDrawable badgeDrawable2 = this.badgeDrawable;
        if (badgeDrawable2 == null) {
            i = 0;
        } else {
            int minimumWidth = badgeDrawable2.getMinimumWidth();
            BadgeDrawable badgeDrawable3 = this.badgeDrawable;
            Objects.requireNonNull(badgeDrawable3);
            i = minimumWidth - badgeDrawable3.savedState.horizontalOffsetWithoutText;
        }
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) getIconOrContainer().getLayoutParams();
        int max = Math.max(i, layoutParams2.leftMargin);
        return Math.max(Math.max(i, layoutParams2.rightMargin) + this.icon.getMeasuredWidth() + max, measuredWidth);
    }

    public final void initialize(MenuItemImpl menuItemImpl) {
        CharSequence charSequence;
        int i;
        this.itemData = menuItemImpl;
        Objects.requireNonNull(menuItemImpl);
        refreshDrawableState();
        setChecked(menuItemImpl.isChecked());
        setEnabled(menuItemImpl.isEnabled());
        Drawable icon2 = menuItemImpl.getIcon();
        if (icon2 != this.originalIconDrawable) {
            this.originalIconDrawable = icon2;
            if (icon2 != null) {
                Drawable.ConstantState constantState = icon2.getConstantState();
                if (constantState != null) {
                    icon2 = constantState.newDrawable();
                }
                icon2 = icon2.mutate();
                this.wrappedIconDrawable = icon2;
                ColorStateList colorStateList = this.iconTint;
                if (colorStateList != null) {
                    icon2.setTintList(colorStateList);
                }
            }
            this.icon.setImageDrawable(icon2);
        }
        CharSequence charSequence2 = menuItemImpl.mTitle;
        this.smallLabel.setText(charSequence2);
        this.largeLabel.setText(charSequence2);
        MenuItemImpl menuItemImpl2 = this.itemData;
        if (menuItemImpl2 == null || TextUtils.isEmpty(menuItemImpl2.mContentDescription)) {
            setContentDescription(charSequence2);
        }
        MenuItemImpl menuItemImpl3 = this.itemData;
        if (menuItemImpl3 != null && !TextUtils.isEmpty(menuItemImpl3.mTooltipText)) {
            MenuItemImpl menuItemImpl4 = this.itemData;
            Objects.requireNonNull(menuItemImpl4);
            charSequence2 = menuItemImpl4.mTooltipText;
        }
        setTooltipText(charSequence2);
        setId(menuItemImpl.mId);
        if (!TextUtils.isEmpty(menuItemImpl.mContentDescription)) {
            setContentDescription(menuItemImpl.mContentDescription);
        }
        if (!TextUtils.isEmpty(menuItemImpl.mTooltipText)) {
            charSequence = menuItemImpl.mTooltipText;
        } else {
            charSequence = menuItemImpl.mTitle;
        }
        setTooltipText(charSequence);
        if (menuItemImpl.isVisible()) {
            i = 0;
        } else {
            i = 8;
        }
        setVisibility(i);
        this.initialized = true;
    }

    public final int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
        MenuItemImpl menuItemImpl = this.itemData;
        if (menuItemImpl != null && menuItemImpl.isCheckable() && this.itemData.isChecked()) {
            View.mergeDrawableStates(onCreateDrawableState, CHECKED_STATE_SET);
        }
        return onCreateDrawableState;
    }

    public final void setActiveIndicatorProgress(float f, float f2) {
        float f3;
        float f4;
        View view = this.activeIndicatorView;
        if (view != null) {
            ActiveIndicatorTransform activeIndicatorTransform2 = this.activeIndicatorTransform;
            Objects.requireNonNull(activeIndicatorTransform2);
            LinearInterpolator linearInterpolator = AnimationUtils.LINEAR_INTERPOLATOR;
            view.setScaleX((0.6f * f) + 0.4f);
            view.setScaleY(activeIndicatorTransform2.calculateScaleY(f, f2));
            int i = (f2 > 0.0f ? 1 : (f2 == 0.0f ? 0 : -1));
            if (i == 0) {
                f3 = 0.8f;
            } else {
                f3 = 0.0f;
            }
            if (i == 0) {
                f4 = 1.0f;
            } else {
                f4 = 0.2f;
            }
            view.setAlpha(AnimationUtils.lerp(0.0f, 1.0f, f3, f4, f));
        }
        this.activeIndicatorProgress = f;
    }

    public final void setBadge(BadgeDrawable badgeDrawable2) {
        boolean z;
        this.badgeDrawable = badgeDrawable2;
        ImageView imageView = this.icon;
        if (imageView != null) {
            if (badgeDrawable2 != null) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                setClipChildren(false);
                setClipToPadding(false);
                BadgeDrawable badgeDrawable3 = this.badgeDrawable;
                Rect rect = new Rect();
                imageView.getDrawingRect(rect);
                badgeDrawable3.setBounds(rect);
                badgeDrawable3.updateBadgeCoordinates(imageView, (FrameLayout) null);
                if (badgeDrawable3.getCustomBadgeParent() != null) {
                    badgeDrawable3.getCustomBadgeParent().setForeground(badgeDrawable3);
                } else {
                    imageView.getOverlay().add(badgeDrawable3);
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x00ad  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0139  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setChecked(boolean r10) {
        /*
            r9 = this;
            android.widget.TextView r0 = r9.largeLabel
            int r1 = r0.getWidth()
            r2 = 2
            int r1 = r1 / r2
            float r1 = (float) r1
            r0.setPivotX(r1)
            android.widget.TextView r0 = r9.largeLabel
            int r1 = r0.getBaseline()
            float r1 = (float) r1
            r0.setPivotY(r1)
            android.widget.TextView r0 = r9.smallLabel
            int r1 = r0.getWidth()
            int r1 = r1 / r2
            float r1 = (float) r1
            r0.setPivotX(r1)
            android.widget.TextView r0 = r9.smallLabel
            int r1 = r0.getBaseline()
            float r1 = (float) r1
            r0.setPivotY(r1)
            r0 = 1065353216(0x3f800000, float:1.0)
            if (r10 == 0) goto L_0x0031
            r1 = r0
            goto L_0x0032
        L_0x0031:
            r1 = 0
        L_0x0032:
            boolean r3 = r9.activeIndicatorEnabled
            r4 = 1
            r5 = 0
            if (r3 == 0) goto L_0x00a0
            boolean r3 = r9.initialized
            if (r3 == 0) goto L_0x00a0
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r3 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            boolean r3 = androidx.core.view.ViewCompat.Api19Impl.isAttachedToWindow(r9)
            if (r3 != 0) goto L_0x0045
            goto L_0x00a0
        L_0x0045:
            android.animation.ValueAnimator r3 = r9.activeIndicatorAnimator
            if (r3 == 0) goto L_0x004f
            r3.cancel()
            r3 = 0
            r9.activeIndicatorAnimator = r3
        L_0x004f:
            float[] r3 = new float[r2]
            float r6 = r9.activeIndicatorProgress
            r3[r5] = r6
            r3[r4] = r1
            android.animation.ValueAnimator r3 = android.animation.ValueAnimator.ofFloat(r3)
            r9.activeIndicatorAnimator = r3
            com.google.android.material.navigation.NavigationBarItemView$3 r6 = new com.google.android.material.navigation.NavigationBarItemView$3
            r6.<init>(r1)
            r3.addUpdateListener(r6)
            android.animation.ValueAnimator r1 = r9.activeIndicatorAnimator
            android.content.Context r3 = r9.getContext()
            androidx.interpolator.view.animation.FastOutSlowInInterpolator r6 = com.google.android.material.animation.AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR
            android.animation.TimeInterpolator r3 = com.google.android.material.motion.MotionUtils.resolveThemeInterpolator(r3, r6)
            r1.setInterpolator(r3)
            android.animation.ValueAnimator r1 = r9.activeIndicatorAnimator
            android.content.Context r3 = r9.getContext()
            android.content.res.Resources r6 = r9.getResources()
            r7 = 2131492994(0x7f0c0082, float:1.8609456E38)
            int r6 = r6.getInteger(r7)
            r7 = 2130969488(0x7f040390, float:1.754766E38)
            android.util.TypedValue r3 = com.google.android.material.resources.MaterialAttributes.resolve(r3, r7)
            if (r3 == 0) goto L_0x0096
            int r7 = r3.type
            r8 = 16
            if (r7 != r8) goto L_0x0096
            int r6 = r3.data
        L_0x0096:
            long r6 = (long) r6
            r1.setDuration(r6)
            android.animation.ValueAnimator r1 = r9.activeIndicatorAnimator
            r1.start()
            goto L_0x00a3
        L_0x00a0:
            r9.setActiveIndicatorProgress(r1, r1)
        L_0x00a3:
            int r1 = r9.labelVisibilityMode
            r3 = -1
            r6 = 17
            r7 = 49
            r8 = 4
            if (r1 == r3) goto L_0x0139
            if (r1 == 0) goto L_0x0108
            if (r1 == r4) goto L_0x00cc
            if (r1 == r2) goto L_0x00b5
            goto L_0x01a7
        L_0x00b5:
            android.view.View r0 = r9.getIconOrContainer()
            int r1 = r9.itemPaddingTop
            setViewTopMarginAndGravity(r0, r1, r6)
            android.widget.TextView r0 = r9.largeLabel
            r1 = 8
            r0.setVisibility(r1)
            android.widget.TextView r0 = r9.smallLabel
            r0.setVisibility(r1)
            goto L_0x01a7
        L_0x00cc:
            android.view.ViewGroup r1 = r9.labelGroup
            int r2 = r9.itemPaddingBottom
            updateViewPaddingBottom(r1, r2)
            if (r10 == 0) goto L_0x00f1
            android.view.View r1 = r9.getIconOrContainer()
            int r2 = r9.itemPaddingTop
            float r2 = (float) r2
            float r3 = r9.shiftAmount
            float r2 = r2 + r3
            int r2 = (int) r2
            setViewTopMarginAndGravity(r1, r2, r7)
            android.widget.TextView r1 = r9.largeLabel
            setViewScaleValues(r1, r0, r0, r5)
            android.widget.TextView r0 = r9.smallLabel
            float r1 = r9.scaleUpFactor
            setViewScaleValues(r0, r1, r1, r8)
            goto L_0x01a7
        L_0x00f1:
            android.view.View r1 = r9.getIconOrContainer()
            int r2 = r9.itemPaddingTop
            setViewTopMarginAndGravity(r1, r2, r7)
            android.widget.TextView r1 = r9.largeLabel
            float r2 = r9.scaleDownFactor
            setViewScaleValues(r1, r2, r2, r8)
            android.widget.TextView r1 = r9.smallLabel
            setViewScaleValues(r1, r0, r0, r5)
            goto L_0x01a7
        L_0x0108:
            if (r10 == 0) goto L_0x0120
            android.view.View r0 = r9.getIconOrContainer()
            int r1 = r9.itemPaddingTop
            setViewTopMarginAndGravity(r0, r1, r7)
            android.view.ViewGroup r0 = r9.labelGroup
            int r1 = r9.itemPaddingBottom
            updateViewPaddingBottom(r0, r1)
            android.widget.TextView r0 = r9.largeLabel
            r0.setVisibility(r5)
            goto L_0x0133
        L_0x0120:
            android.view.View r0 = r9.getIconOrContainer()
            int r1 = r9.itemPaddingTop
            setViewTopMarginAndGravity(r0, r1, r6)
            android.view.ViewGroup r0 = r9.labelGroup
            updateViewPaddingBottom(r0, r5)
            android.widget.TextView r0 = r9.largeLabel
            r0.setVisibility(r8)
        L_0x0133:
            android.widget.TextView r0 = r9.smallLabel
            r0.setVisibility(r8)
            goto L_0x01a7
        L_0x0139:
            boolean r1 = r9.isShifting
            if (r1 == 0) goto L_0x016e
            if (r10 == 0) goto L_0x0155
            android.view.View r0 = r9.getIconOrContainer()
            int r1 = r9.itemPaddingTop
            setViewTopMarginAndGravity(r0, r1, r7)
            android.view.ViewGroup r0 = r9.labelGroup
            int r1 = r9.itemPaddingBottom
            updateViewPaddingBottom(r0, r1)
            android.widget.TextView r0 = r9.largeLabel
            r0.setVisibility(r5)
            goto L_0x0168
        L_0x0155:
            android.view.View r0 = r9.getIconOrContainer()
            int r1 = r9.itemPaddingTop
            setViewTopMarginAndGravity(r0, r1, r6)
            android.view.ViewGroup r0 = r9.labelGroup
            updateViewPaddingBottom(r0, r5)
            android.widget.TextView r0 = r9.largeLabel
            r0.setVisibility(r8)
        L_0x0168:
            android.widget.TextView r0 = r9.smallLabel
            r0.setVisibility(r8)
            goto L_0x01a7
        L_0x016e:
            android.view.ViewGroup r1 = r9.labelGroup
            int r2 = r9.itemPaddingBottom
            updateViewPaddingBottom(r1, r2)
            if (r10 == 0) goto L_0x0192
            android.view.View r1 = r9.getIconOrContainer()
            int r2 = r9.itemPaddingTop
            float r2 = (float) r2
            float r3 = r9.shiftAmount
            float r2 = r2 + r3
            int r2 = (int) r2
            setViewTopMarginAndGravity(r1, r2, r7)
            android.widget.TextView r1 = r9.largeLabel
            setViewScaleValues(r1, r0, r0, r5)
            android.widget.TextView r0 = r9.smallLabel
            float r1 = r9.scaleUpFactor
            setViewScaleValues(r0, r1, r1, r8)
            goto L_0x01a7
        L_0x0192:
            android.view.View r1 = r9.getIconOrContainer()
            int r2 = r9.itemPaddingTop
            setViewTopMarginAndGravity(r1, r2, r7)
            android.widget.TextView r1 = r9.largeLabel
            float r2 = r9.scaleDownFactor
            setViewScaleValues(r1, r2, r2, r8)
            android.widget.TextView r1 = r9.smallLabel
            setViewScaleValues(r1, r0, r0, r5)
        L_0x01a7:
            r9.refreshDrawableState()
            r9.setSelected(r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.navigation.NavigationBarItemView.setChecked(boolean):void");
    }

    public final void setItemBackground(Drawable drawable) {
        if (!(drawable == null || drawable.getConstantState() == null)) {
            drawable = drawable.getConstantState().newDrawable().mutate();
        }
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.setBackground(this, drawable);
    }

    public final void setLabelVisibilityMode(int i) {
        boolean z;
        if (this.labelVisibilityMode != i) {
            this.labelVisibilityMode = i;
            if (!this.activeIndicatorResizeable || i != 2) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                this.activeIndicatorTransform = ACTIVE_INDICATOR_UNLABELED_TRANSFORM;
            } else {
                this.activeIndicatorTransform = ACTIVE_INDICATOR_LABELED_TRANSFORM;
            }
            updateActiveIndicatorLayoutParams(getWidth());
            MenuItemImpl menuItemImpl = this.itemData;
            if (menuItemImpl != null) {
                setChecked(menuItemImpl.isChecked());
            }
        }
    }

    public final void setTextColor(ColorStateList colorStateList) {
        if (colorStateList != null) {
            this.smallLabel.setTextColor(colorStateList);
            this.largeLabel.setTextColor(colorStateList);
        }
    }

    public final void updateActiveIndicatorLayoutParams(int i) {
        boolean z;
        int i2;
        if (this.activeIndicatorView != null) {
            int min = Math.min(this.activeIndicatorDesiredWidth, i - (this.activeIndicatorMarginHorizontal * 2));
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.activeIndicatorView.getLayoutParams();
            if (!this.activeIndicatorResizeable || this.labelVisibilityMode != 2) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                i2 = min;
            } else {
                i2 = this.activeIndicatorDesiredHeight;
            }
            layoutParams.height = i2;
            layoutParams.width = min;
            this.activeIndicatorView.setLayoutParams(layoutParams);
        }
    }

    public NavigationBarItemView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(getItemLayoutResId(), this, true);
        this.iconContainer = (FrameLayout) findViewById(C1777R.C1779id.navigation_bar_item_icon_container);
        this.activeIndicatorView = findViewById(C1777R.C1779id.navigation_bar_item_active_indicator_view);
        ImageView imageView = (ImageView) findViewById(C1777R.C1779id.navigation_bar_item_icon_view);
        this.icon = imageView;
        ViewGroup viewGroup = (ViewGroup) findViewById(C1777R.C1779id.navigation_bar_item_labels_group);
        this.labelGroup = viewGroup;
        TextView textView = (TextView) findViewById(C1777R.C1779id.navigation_bar_item_small_label_view);
        this.smallLabel = textView;
        TextView textView2 = (TextView) findViewById(C1777R.C1779id.navigation_bar_item_large_label_view);
        this.largeLabel = textView2;
        setBackgroundResource(C1777R.C1778drawable.mtrl_navigation_bar_item_background);
        this.itemPaddingTop = getResources().getDimensionPixelSize(getItemDefaultMarginResId());
        this.itemPaddingBottom = viewGroup.getPaddingBottom();
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.setImportantForAccessibility(textView, 2);
        ViewCompat.Api16Impl.setImportantForAccessibility(textView2, 2);
        setFocusable(true);
        float textSize = textView.getTextSize();
        float textSize2 = textView2.getTextSize();
        this.shiftAmount = textSize - textSize2;
        this.scaleUpFactor = (textSize2 * 1.0f) / textSize;
        this.scaleDownFactor = (textSize * 1.0f) / textSize2;
        if (imageView != null) {
            imageView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    boolean z;
                    if (NavigationBarItemView.this.icon.getVisibility() == 0) {
                        NavigationBarItemView navigationBarItemView = NavigationBarItemView.this;
                        ImageView imageView = navigationBarItemView.icon;
                        BadgeDrawable badgeDrawable = navigationBarItemView.badgeDrawable;
                        if (badgeDrawable != null) {
                            z = true;
                        } else {
                            z = false;
                        }
                        if (z) {
                            Rect rect = new Rect();
                            imageView.getDrawingRect(rect);
                            badgeDrawable.setBounds(rect);
                            badgeDrawable.updateBadgeCoordinates(imageView, (FrameLayout) null);
                        }
                    }
                }
            });
        }
    }

    public static void setViewScaleValues(TextView textView, float f, float f2, int i) {
        textView.setScaleX(f);
        textView.setScaleY(f2);
        textView.setVisibility(i);
    }

    public static void setViewTopMarginAndGravity(View view, int i, int i2) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        layoutParams.topMargin = i;
        layoutParams.bottomMargin = i;
        layoutParams.gravity = i2;
        view.setLayoutParams(layoutParams);
    }

    public static void updateViewPaddingBottom(ViewGroup viewGroup, int i) {
        viewGroup.setPadding(viewGroup.getPaddingLeft(), viewGroup.getPaddingTop(), viewGroup.getPaddingRight(), i);
    }

    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        Context context;
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        BadgeDrawable badgeDrawable2 = this.badgeDrawable;
        if (badgeDrawable2 != null && badgeDrawable2.isVisible()) {
            MenuItemImpl menuItemImpl = this.itemData;
            Objects.requireNonNull(menuItemImpl);
            CharSequence charSequence = menuItemImpl.mTitle;
            MenuItemImpl menuItemImpl2 = this.itemData;
            Objects.requireNonNull(menuItemImpl2);
            if (!TextUtils.isEmpty(menuItemImpl2.mContentDescription)) {
                MenuItemImpl menuItemImpl3 = this.itemData;
                Objects.requireNonNull(menuItemImpl3);
                charSequence = menuItemImpl3.mContentDescription;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(charSequence);
            sb.append(", ");
            BadgeDrawable badgeDrawable3 = this.badgeDrawable;
            Objects.requireNonNull(badgeDrawable3);
            String str = null;
            if (badgeDrawable3.isVisible()) {
                if (!badgeDrawable3.hasNumber()) {
                    str = badgeDrawable3.savedState.contentDescriptionNumberless;
                } else if (badgeDrawable3.savedState.contentDescriptionQuantityStrings > 0 && (context = badgeDrawable3.contextRef.get()) != null) {
                    int number = badgeDrawable3.getNumber();
                    int i = badgeDrawable3.maxBadgeNumber;
                    if (number <= i) {
                        str = context.getResources().getQuantityString(badgeDrawable3.savedState.contentDescriptionQuantityStrings, badgeDrawable3.getNumber(), new Object[]{Integer.valueOf(badgeDrawable3.getNumber())});
                    } else {
                        str = context.getString(badgeDrawable3.savedState.contentDescriptionExceedsMaxBadgeNumberRes, new Object[]{Integer.valueOf(i)});
                    }
                }
            }
            sb.append(str);
            accessibilityNodeInfo.setContentDescription(sb.toString());
        }
        ViewGroup viewGroup = (ViewGroup) getParent();
        int indexOfChild = viewGroup.indexOfChild(this);
        int i2 = 0;
        for (int i3 = 0; i3 < indexOfChild; i3++) {
            View childAt = viewGroup.getChildAt(i3);
            if ((childAt instanceof NavigationBarItemView) && childAt.getVisibility() == 0) {
                i2++;
            }
        }
        accessibilityNodeInfo.setCollectionItemInfo((AccessibilityNodeInfo.CollectionItemInfo) AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(0, 1, i2, 1, isSelected()).mInfo);
        if (isSelected()) {
            accessibilityNodeInfo.setClickable(false);
            accessibilityNodeInfo.removeAction((AccessibilityNodeInfo.AccessibilityAction) AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK.mAction);
        }
        accessibilityNodeInfo.getExtras().putCharSequence("AccessibilityNodeInfo.roleDescription", getResources().getString(C1777R.string.item_view_role_description));
    }

    public final void onSizeChanged(final int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        post(new Runnable() {
            public final void run() {
                NavigationBarItemView.this.updateActiveIndicatorLayoutParams(i);
            }
        });
    }

    public final void setEnabled(boolean z) {
        super.setEnabled(z);
        this.smallLabel.setEnabled(z);
        this.largeLabel.setEnabled(z);
        this.icon.setEnabled(z);
        if (z) {
            PointerIcon systemIcon = PointerIcon.getSystemIcon(getContext(), 1002);
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api24Impl.setPointerIcon(this, systemIcon);
            return;
        }
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api24Impl.setPointerIcon(this, (PointerIcon) null);
    }

    public final MenuItemImpl getItemData() {
        return this.itemData;
    }
}
