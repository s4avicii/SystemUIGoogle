package com.google.android.material.appbar;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.WindowInsetsCompat;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.R$styleable;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.elevation.ElevationOverlayProvider;
import com.google.android.material.internal.CollapsingTextHelper;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.ArrayList;
import java.util.Objects;
import java.util.WeakHashMap;
import kotlinx.atomicfu.AtomicFU;

public class CollapsingToolbarLayout extends FrameLayout {
    public final CollapsingTextHelper collapsingTextHelper;
    public boolean collapsingTitleEnabled;
    public Drawable contentScrim;
    public int currentOffset;
    public boolean drawCollapsingTitle;
    public View dummyView;
    public int expandedMarginBottom;
    public int expandedMarginEnd;
    public int expandedMarginStart;
    public int expandedMarginTop;
    public int extraMultilineHeight;
    public boolean extraMultilineHeightEnabled;
    public boolean forceApplySystemWindowInsetTop;
    public WindowInsetsCompat lastInsets;
    public OffsetUpdateListener onOffsetChangedListener;
    public boolean refreshToolbar;
    public int scrimAlpha;
    public long scrimAnimationDuration;
    public ValueAnimator scrimAnimator;
    public int scrimVisibleHeightTrigger;
    public boolean scrimsAreShown;
    public Drawable statusBarScrim;
    public int titleCollapseMode;
    public final Rect tmpRect;
    public ViewGroup toolbar;
    public View toolbarDirectChild;
    public int toolbarId;
    public int topInsetApplied;

    public class OffsetUpdateListener implements AppBarLayout.BaseOnOffsetChangedListener {
        public OffsetUpdateListener() {
        }

        public final void onOffsetChanged(int i) {
            int i2;
            CollapsingToolbarLayout collapsingToolbarLayout = CollapsingToolbarLayout.this;
            collapsingToolbarLayout.currentOffset = i;
            WindowInsetsCompat windowInsetsCompat = collapsingToolbarLayout.lastInsets;
            if (windowInsetsCompat != null) {
                i2 = windowInsetsCompat.getSystemWindowInsetTop();
            } else {
                i2 = 0;
            }
            int childCount = CollapsingToolbarLayout.this.getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = CollapsingToolbarLayout.this.getChildAt(i3);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                ViewOffsetHelper viewOffsetHelper = CollapsingToolbarLayout.getViewOffsetHelper(childAt);
                int i4 = layoutParams.collapseMode;
                if (i4 == 1) {
                    CollapsingToolbarLayout collapsingToolbarLayout2 = CollapsingToolbarLayout.this;
                    Objects.requireNonNull(collapsingToolbarLayout2);
                    viewOffsetHelper.setTopAndBottomOffset(AtomicFU.clamp(-i, 0, ((collapsingToolbarLayout2.getHeight() - CollapsingToolbarLayout.getViewOffsetHelper(childAt).layoutTop) - childAt.getHeight()) - ((LayoutParams) childAt.getLayoutParams()).bottomMargin));
                } else if (i4 == 2) {
                    viewOffsetHelper.setTopAndBottomOffset(Math.round(((float) (-i)) * layoutParams.parallaxMult));
                }
            }
            CollapsingToolbarLayout.this.updateScrimVisibility();
            CollapsingToolbarLayout collapsingToolbarLayout3 = CollapsingToolbarLayout.this;
            if (collapsingToolbarLayout3.statusBarScrim != null && i2 > 0) {
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api16Impl.postInvalidateOnAnimation(collapsingToolbarLayout3);
            }
            int height = CollapsingToolbarLayout.this.getHeight();
            CollapsingToolbarLayout collapsingToolbarLayout4 = CollapsingToolbarLayout.this;
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            int minimumHeight = (height - ViewCompat.Api16Impl.getMinimumHeight(collapsingToolbarLayout4)) - i2;
            int scrimVisibleHeightTrigger = height - CollapsingToolbarLayout.this.getScrimVisibleHeightTrigger();
            CollapsingTextHelper collapsingTextHelper = CollapsingToolbarLayout.this.collapsingTextHelper;
            float f = (float) minimumHeight;
            float min = Math.min(1.0f, ((float) scrimVisibleHeightTrigger) / f);
            Objects.requireNonNull(collapsingTextHelper);
            collapsingTextHelper.fadeModeStartFraction = min;
            collapsingTextHelper.fadeModeThresholdFraction = MotionController$$ExternalSyntheticOutline0.m7m(1.0f, min, 0.5f, min);
            CollapsingToolbarLayout collapsingToolbarLayout5 = CollapsingToolbarLayout.this;
            CollapsingTextHelper collapsingTextHelper2 = collapsingToolbarLayout5.collapsingTextHelper;
            Objects.requireNonNull(collapsingTextHelper2);
            collapsingTextHelper2.currentOffsetY = collapsingToolbarLayout5.currentOffset + minimumHeight;
            CollapsingToolbarLayout.this.collapsingTextHelper.setExpansionFraction(((float) Math.abs(i)) / f);
        }
    }

    public CollapsingToolbarLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    public final void updateTextBounds(int i, int i2, int i3, int i4, boolean z) {
        View view;
        boolean z2;
        boolean z3;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        boolean z4;
        int i10;
        int i11;
        boolean z5 = z;
        if (this.collapsingTitleEnabled && (view = this.dummyView) != null) {
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            boolean z6 = false;
            if (!ViewCompat.Api19Impl.isAttachedToWindow(view) || this.dummyView.getVisibility() != 0) {
                z2 = false;
            } else {
                z2 = true;
            }
            this.drawCollapsingTitle = z2;
            if (z2 || z5) {
                if (ViewCompat.Api17Impl.getLayoutDirection(this) == 1) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                View view2 = this.toolbarDirectChild;
                if (view2 == null) {
                    view2 = this.toolbar;
                }
                int height = ((getHeight() - getViewOffsetHelper(view2).layoutTop) - view2.getHeight()) - ((LayoutParams) view2.getLayoutParams()).bottomMargin;
                DescendantOffsetUtils.getDescendantRect(this, this.dummyView, this.tmpRect);
                ViewGroup viewGroup = this.toolbar;
                if (viewGroup instanceof Toolbar) {
                    Toolbar toolbar2 = (Toolbar) viewGroup;
                    Objects.requireNonNull(toolbar2);
                    i7 = toolbar2.mTitleMarginStart;
                    i6 = toolbar2.mTitleMarginEnd;
                    i5 = toolbar2.mTitleMarginTop;
                    i8 = toolbar2.mTitleMarginBottom;
                } else if (viewGroup instanceof android.widget.Toolbar) {
                    android.widget.Toolbar toolbar3 = (android.widget.Toolbar) viewGroup;
                    i7 = toolbar3.getTitleMarginStart();
                    i6 = toolbar3.getTitleMarginEnd();
                    i5 = toolbar3.getTitleMarginTop();
                    i8 = toolbar3.getTitleMarginBottom();
                } else {
                    i8 = 0;
                    i7 = 0;
                    i6 = 0;
                    i5 = 0;
                }
                CollapsingTextHelper collapsingTextHelper2 = this.collapsingTextHelper;
                Rect rect = this.tmpRect;
                int i12 = rect.left;
                if (z3) {
                    i9 = i6;
                } else {
                    i9 = i7;
                }
                int i13 = i12 + i9;
                int i14 = rect.top + height + i5;
                int i15 = rect.right;
                if (!z3) {
                    i7 = i6;
                }
                int i16 = i15 - i7;
                int i17 = (rect.bottom + height) - i8;
                Objects.requireNonNull(collapsingTextHelper2);
                Rect rect2 = collapsingTextHelper2.collapsedBounds;
                if (rect2.left == i13 && rect2.top == i14 && rect2.right == i16 && rect2.bottom == i17) {
                    z4 = true;
                } else {
                    z4 = false;
                }
                if (!z4) {
                    rect2.set(i13, i14, i16, i17);
                    collapsingTextHelper2.boundsChanged = true;
                    collapsingTextHelper2.onBoundsChanged();
                }
                CollapsingTextHelper collapsingTextHelper3 = this.collapsingTextHelper;
                if (z3) {
                    i10 = this.expandedMarginEnd;
                } else {
                    i10 = this.expandedMarginStart;
                }
                int i18 = this.tmpRect.top + this.expandedMarginTop;
                int i19 = i3 - i;
                if (z3) {
                    i11 = this.expandedMarginStart;
                } else {
                    i11 = this.expandedMarginEnd;
                }
                int i20 = i19 - i11;
                int i21 = (i4 - i2) - this.expandedMarginBottom;
                Objects.requireNonNull(collapsingTextHelper3);
                Rect rect3 = collapsingTextHelper3.expandedBounds;
                if (rect3.left == i10 && rect3.top == i18 && rect3.right == i20 && rect3.bottom == i21) {
                    z6 = true;
                }
                if (!z6) {
                    rect3.set(i10, i18, i20, i21);
                    collapsingTextHelper3.boundsChanged = true;
                    collapsingTextHelper3.onBoundsChanged();
                }
                this.collapsingTextHelper.recalculate(z5);
            }
        }
    }

    public CollapsingToolbarLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C1777R.attr.collapsingToolbarLayoutStyle);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0018, code lost:
        r3 = true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean drawChild(android.graphics.Canvas r7, android.view.View r8, long r9) {
        /*
            r6 = this;
            android.graphics.drawable.Drawable r0 = r6.contentScrim
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x004d
            int r3 = r6.scrimAlpha
            if (r3 <= 0) goto L_0x004d
            android.view.View r3 = r6.toolbarDirectChild
            if (r3 == 0) goto L_0x0014
            if (r3 != r6) goto L_0x0011
            goto L_0x0014
        L_0x0011:
            if (r8 != r3) goto L_0x001a
            goto L_0x0018
        L_0x0014:
            android.view.ViewGroup r3 = r6.toolbar
            if (r8 != r3) goto L_0x001a
        L_0x0018:
            r3 = r2
            goto L_0x001b
        L_0x001a:
            r3 = r1
        L_0x001b:
            if (r3 == 0) goto L_0x004d
            int r3 = r6.getWidth()
            int r4 = r6.getHeight()
            int r5 = r6.titleCollapseMode
            if (r5 != r2) goto L_0x002b
            r5 = r2
            goto L_0x002c
        L_0x002b:
            r5 = r1
        L_0x002c:
            if (r5 == 0) goto L_0x0038
            if (r8 == 0) goto L_0x0038
            boolean r5 = r6.collapsingTitleEnabled
            if (r5 == 0) goto L_0x0038
            int r4 = r8.getBottom()
        L_0x0038:
            r0.setBounds(r1, r1, r3, r4)
            android.graphics.drawable.Drawable r0 = r6.contentScrim
            android.graphics.drawable.Drawable r0 = r0.mutate()
            int r3 = r6.scrimAlpha
            r0.setAlpha(r3)
            android.graphics.drawable.Drawable r0 = r6.contentScrim
            r0.draw(r7)
            r0 = r2
            goto L_0x004e
        L_0x004d:
            r0 = r1
        L_0x004e:
            boolean r6 = super.drawChild(r7, r8, r9)
            if (r6 != 0) goto L_0x0056
            if (r0 == 0) goto L_0x0057
        L_0x0056:
            r1 = r2
        L_0x0057:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.appbar.CollapsingToolbarLayout.drawChild(android.graphics.Canvas, android.view.View, long):boolean");
    }

    /* JADX WARNING: type inference failed for: r5v0, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void ensureToolbar() {
        /*
            r7 = this;
            boolean r0 = r7.refreshToolbar
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            r0 = 0
            r7.toolbar = r0
            r7.toolbarDirectChild = r0
            int r1 = r7.toolbarId
            r2 = -1
            if (r1 == r2) goto L_0x002f
            android.view.View r1 = r7.findViewById(r1)
            android.view.ViewGroup r1 = (android.view.ViewGroup) r1
            r7.toolbar = r1
            if (r1 == 0) goto L_0x002f
            android.view.ViewParent r3 = r1.getParent()
        L_0x001d:
            if (r3 == r7) goto L_0x002d
            if (r3 == 0) goto L_0x002d
            boolean r4 = r3 instanceof android.view.View
            if (r4 == 0) goto L_0x0028
            r1 = r3
            android.view.View r1 = (android.view.View) r1
        L_0x0028:
            android.view.ViewParent r3 = r3.getParent()
            goto L_0x001d
        L_0x002d:
            r7.toolbarDirectChild = r1
        L_0x002f:
            android.view.ViewGroup r1 = r7.toolbar
            r3 = 0
            if (r1 != 0) goto L_0x0056
            int r1 = r7.getChildCount()
            r4 = r3
        L_0x0039:
            if (r4 >= r1) goto L_0x0054
            android.view.View r5 = r7.getChildAt(r4)
            boolean r6 = r5 instanceof androidx.appcompat.widget.Toolbar
            if (r6 != 0) goto L_0x004a
            boolean r6 = r5 instanceof android.widget.Toolbar
            if (r6 == 0) goto L_0x0048
            goto L_0x004a
        L_0x0048:
            r6 = r3
            goto L_0x004b
        L_0x004a:
            r6 = 1
        L_0x004b:
            if (r6 == 0) goto L_0x0051
            r0 = r5
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            goto L_0x0054
        L_0x0051:
            int r4 = r4 + 1
            goto L_0x0039
        L_0x0054:
            r7.toolbar = r0
        L_0x0056:
            boolean r0 = r7.collapsingTitleEnabled
            if (r0 != 0) goto L_0x006d
            android.view.View r0 = r7.dummyView
            if (r0 == 0) goto L_0x006d
            android.view.ViewParent r0 = r0.getParent()
            boolean r1 = r0 instanceof android.view.ViewGroup
            if (r1 == 0) goto L_0x006d
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            android.view.View r1 = r7.dummyView
            r0.removeView(r1)
        L_0x006d:
            boolean r0 = r7.collapsingTitleEnabled
            if (r0 == 0) goto L_0x0093
            android.view.ViewGroup r0 = r7.toolbar
            if (r0 == 0) goto L_0x0093
            android.view.View r0 = r7.dummyView
            if (r0 != 0) goto L_0x0084
            android.view.View r0 = new android.view.View
            android.content.Context r1 = r7.getContext()
            r0.<init>(r1)
            r7.dummyView = r0
        L_0x0084:
            android.view.View r0 = r7.dummyView
            android.view.ViewParent r0 = r0.getParent()
            if (r0 != 0) goto L_0x0093
            android.view.ViewGroup r0 = r7.toolbar
            android.view.View r1 = r7.dummyView
            r0.addView(r1, r2, r2)
        L_0x0093:
            r7.refreshToolbar = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.appbar.CollapsingToolbarLayout.ensureToolbar():void");
    }

    /* renamed from: generateDefaultLayoutParams  reason: collision with other method in class */
    public final FrameLayout.LayoutParams m300generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    public final FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public final int getScrimVisibleHeightTrigger() {
        int i;
        int i2 = this.scrimVisibleHeightTrigger;
        if (i2 >= 0) {
            return i2 + this.topInsetApplied + this.extraMultilineHeight;
        }
        WindowInsetsCompat windowInsetsCompat = this.lastInsets;
        if (windowInsetsCompat != null) {
            i = windowInsetsCompat.getSystemWindowInsetTop();
        } else {
            i = 0;
        }
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        int minimumHeight = ViewCompat.Api16Impl.getMinimumHeight(this);
        if (minimumHeight > 0) {
            return Math.min((minimumHeight * 2) + i, getHeight());
        }
        return getHeight() / 3;
    }

    public final void setContentScrim(Drawable drawable) {
        Drawable drawable2 = this.contentScrim;
        if (drawable2 != drawable) {
            Drawable drawable3 = null;
            if (drawable2 != null) {
                drawable2.setCallback((Drawable.Callback) null);
            }
            if (drawable != null) {
                drawable3 = drawable.mutate();
            }
            this.contentScrim = drawable3;
            if (drawable3 != null) {
                int width = getWidth();
                int height = getHeight();
                ViewGroup viewGroup = this.toolbar;
                boolean z = true;
                if (this.titleCollapseMode != 1) {
                    z = false;
                }
                if (z && viewGroup != null && this.collapsingTitleEnabled) {
                    height = viewGroup.getBottom();
                }
                drawable3.setBounds(0, 0, width, height);
                this.contentScrim.setCallback(this);
                this.contentScrim.setAlpha(this.scrimAlpha);
            }
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
        }
    }

    public final void setTitle(CharSequence charSequence) {
        CollapsingTextHelper collapsingTextHelper2 = this.collapsingTextHelper;
        Objects.requireNonNull(collapsingTextHelper2);
        CharSequence charSequence2 = null;
        if (charSequence == null || !TextUtils.equals(collapsingTextHelper2.text, charSequence)) {
            collapsingTextHelper2.text = charSequence;
            collapsingTextHelper2.textToDraw = null;
            Bitmap bitmap = collapsingTextHelper2.expandedTitleTexture;
            if (bitmap != null) {
                bitmap.recycle();
                collapsingTextHelper2.expandedTitleTexture = null;
            }
            collapsingTextHelper2.recalculate(false);
        }
        if (this.collapsingTitleEnabled) {
            CollapsingTextHelper collapsingTextHelper3 = this.collapsingTextHelper;
            Objects.requireNonNull(collapsingTextHelper3);
            charSequence2 = collapsingTextHelper3.text;
        }
        setContentDescription(charSequence2);
    }

    public final void updateScrimVisibility() {
        boolean z;
        boolean z2;
        ViewGroup viewGroup;
        TimeInterpolator timeInterpolator;
        if (this.contentScrim != null || this.statusBarScrim != null) {
            int i = 0;
            if (getHeight() + this.currentOffset < getScrimVisibleHeightTrigger()) {
                z = true;
            } else {
                z = false;
            }
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (!ViewCompat.Api19Impl.isLaidOut(this) || isInEditMode()) {
                z2 = false;
            } else {
                z2 = true;
            }
            if (this.scrimsAreShown != z) {
                int i2 = 255;
                if (z2) {
                    if (!z) {
                        i2 = 0;
                    }
                    ensureToolbar();
                    ValueAnimator valueAnimator = this.scrimAnimator;
                    if (valueAnimator == null) {
                        ValueAnimator valueAnimator2 = new ValueAnimator();
                        this.scrimAnimator = valueAnimator2;
                        if (i2 > this.scrimAlpha) {
                            timeInterpolator = AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR;
                        } else {
                            timeInterpolator = AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR;
                        }
                        valueAnimator2.setInterpolator(timeInterpolator);
                        this.scrimAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                ViewGroup viewGroup;
                                CollapsingToolbarLayout collapsingToolbarLayout = CollapsingToolbarLayout.this;
                                int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                                Objects.requireNonNull(collapsingToolbarLayout);
                                if (intValue != collapsingToolbarLayout.scrimAlpha) {
                                    if (!(collapsingToolbarLayout.contentScrim == null || (viewGroup = collapsingToolbarLayout.toolbar) == null)) {
                                        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                                        ViewCompat.Api16Impl.postInvalidateOnAnimation(viewGroup);
                                    }
                                    collapsingToolbarLayout.scrimAlpha = intValue;
                                    WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                                    ViewCompat.Api16Impl.postInvalidateOnAnimation(collapsingToolbarLayout);
                                }
                            }
                        });
                    } else if (valueAnimator.isRunning()) {
                        this.scrimAnimator.cancel();
                    }
                    this.scrimAnimator.setDuration(this.scrimAnimationDuration);
                    this.scrimAnimator.setIntValues(new int[]{this.scrimAlpha, i2});
                    this.scrimAnimator.start();
                } else {
                    if (z) {
                        i = 255;
                    }
                    if (i != this.scrimAlpha) {
                        if (!(this.contentScrim == null || (viewGroup = this.toolbar) == null)) {
                            ViewCompat.Api16Impl.postInvalidateOnAnimation(viewGroup);
                        }
                        this.scrimAlpha = i;
                        ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
                    }
                }
                this.scrimsAreShown = z;
            }
        }
    }

    public final void updateTitleFromToolbarIfNeeded() {
        CharSequence charSequence;
        if (this.toolbar != null && this.collapsingTitleEnabled) {
            CollapsingTextHelper collapsingTextHelper2 = this.collapsingTextHelper;
            Objects.requireNonNull(collapsingTextHelper2);
            if (TextUtils.isEmpty(collapsingTextHelper2.text)) {
                ViewGroup viewGroup = this.toolbar;
                if (viewGroup instanceof Toolbar) {
                    Toolbar toolbar2 = (Toolbar) viewGroup;
                    Objects.requireNonNull(toolbar2);
                    charSequence = toolbar2.mTitleText;
                } else if (viewGroup instanceof android.widget.Toolbar) {
                    charSequence = ((android.widget.Toolbar) viewGroup).getTitle();
                } else {
                    charSequence = null;
                }
                setTitle(charSequence);
            }
        }
    }

    public CollapsingToolbarLayout(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, 2132018386), attributeSet, i);
        int i2;
        ColorStateList colorStateList;
        boolean z = true;
        this.refreshToolbar = true;
        this.tmpRect = new Rect();
        this.scrimVisibleHeightTrigger = -1;
        this.topInsetApplied = 0;
        this.extraMultilineHeight = 0;
        Context context2 = getContext();
        CollapsingTextHelper collapsingTextHelper2 = new CollapsingTextHelper(this);
        this.collapsingTextHelper = collapsingTextHelper2;
        collapsingTextHelper2.textSizeInterpolator = AnimationUtils.DECELERATE_INTERPOLATOR;
        collapsingTextHelper2.recalculate(false);
        collapsingTextHelper2.isRtlTextDirectionHeuristicsEnabled = false;
        ElevationOverlayProvider elevationOverlayProvider = new ElevationOverlayProvider(context2);
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R$styleable.CollapsingToolbarLayout, i, 2132018386, new int[0]);
        int i3 = obtainStyledAttributes.getInt(4, 8388691);
        if (collapsingTextHelper2.expandedTextGravity != i3) {
            collapsingTextHelper2.expandedTextGravity = i3;
            collapsingTextHelper2.recalculate(false);
        }
        int i4 = obtainStyledAttributes.getInt(0, 8388627);
        if (collapsingTextHelper2.collapsedTextGravity != i4) {
            collapsingTextHelper2.collapsedTextGravity = i4;
            collapsingTextHelper2.recalculate(false);
        }
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(5, 0);
        this.expandedMarginBottom = dimensionPixelSize;
        this.expandedMarginEnd = dimensionPixelSize;
        this.expandedMarginTop = dimensionPixelSize;
        this.expandedMarginStart = dimensionPixelSize;
        if (obtainStyledAttributes.hasValue(8)) {
            this.expandedMarginStart = obtainStyledAttributes.getDimensionPixelSize(8, 0);
        }
        if (obtainStyledAttributes.hasValue(7)) {
            this.expandedMarginEnd = obtainStyledAttributes.getDimensionPixelSize(7, 0);
        }
        if (obtainStyledAttributes.hasValue(9)) {
            this.expandedMarginTop = obtainStyledAttributes.getDimensionPixelSize(9, 0);
        }
        if (obtainStyledAttributes.hasValue(6)) {
            this.expandedMarginBottom = obtainStyledAttributes.getDimensionPixelSize(6, 0);
        }
        this.collapsingTitleEnabled = obtainStyledAttributes.getBoolean(20, true);
        setTitle(obtainStyledAttributes.getText(18));
        collapsingTextHelper2.setExpandedTextAppearance(2132017892);
        collapsingTextHelper2.setCollapsedTextAppearance(2132017844);
        if (obtainStyledAttributes.hasValue(10)) {
            collapsingTextHelper2.setExpandedTextAppearance(obtainStyledAttributes.getResourceId(10, 0));
        }
        if (obtainStyledAttributes.hasValue(1)) {
            collapsingTextHelper2.setCollapsedTextAppearance(obtainStyledAttributes.getResourceId(1, 0));
        }
        if (obtainStyledAttributes.hasValue(11) && collapsingTextHelper2.expandedTextColor != (colorStateList = MaterialResources.getColorStateList(context2, obtainStyledAttributes, 11))) {
            collapsingTextHelper2.expandedTextColor = colorStateList;
            collapsingTextHelper2.recalculate(false);
        }
        if (obtainStyledAttributes.hasValue(2)) {
            collapsingTextHelper2.setCollapsedTextColor(MaterialResources.getColorStateList(context2, obtainStyledAttributes, 2));
        }
        this.scrimVisibleHeightTrigger = obtainStyledAttributes.getDimensionPixelSize(16, -1);
        Drawable drawable = null;
        if (obtainStyledAttributes.hasValue(14) && (i2 = obtainStyledAttributes.getInt(14, 1)) != collapsingTextHelper2.maxLines) {
            collapsingTextHelper2.maxLines = i2;
            Bitmap bitmap = collapsingTextHelper2.expandedTitleTexture;
            if (bitmap != null) {
                bitmap.recycle();
                collapsingTextHelper2.expandedTitleTexture = null;
            }
            collapsingTextHelper2.recalculate(false);
        }
        if (obtainStyledAttributes.hasValue(21)) {
            collapsingTextHelper2.positionInterpolator = android.view.animation.AnimationUtils.loadInterpolator(context2, obtainStyledAttributes.getResourceId(21, 0));
            collapsingTextHelper2.recalculate(false);
        }
        this.scrimAnimationDuration = (long) obtainStyledAttributes.getInt(15, 600);
        setContentScrim(obtainStyledAttributes.getDrawable(3));
        Drawable drawable2 = obtainStyledAttributes.getDrawable(17);
        Drawable drawable3 = this.statusBarScrim;
        if (drawable3 != drawable2) {
            if (drawable3 != null) {
                drawable3.setCallback((Drawable.Callback) null);
            }
            drawable = drawable2 != null ? drawable2.mutate() : drawable;
            this.statusBarScrim = drawable;
            if (drawable != null) {
                if (drawable.isStateful()) {
                    this.statusBarScrim.setState(getDrawableState());
                }
                Drawable drawable4 = this.statusBarScrim;
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                drawable4.setLayoutDirection(ViewCompat.Api17Impl.getLayoutDirection(this));
                this.statusBarScrim.setVisible(getVisibility() == 0, false);
                this.statusBarScrim.setCallback(this);
                this.statusBarScrim.setAlpha(this.scrimAlpha);
            }
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
        }
        int i5 = obtainStyledAttributes.getInt(19, 0);
        this.titleCollapseMode = i5;
        boolean z2 = i5 == 1;
        collapsingTextHelper2.fadeModeEnabled = z2;
        ViewParent parent = getParent();
        if (parent instanceof AppBarLayout) {
            AppBarLayout appBarLayout = (AppBarLayout) parent;
            if (this.titleCollapseMode != 1 ? false : z) {
                Objects.requireNonNull(appBarLayout);
                appBarLayout.liftOnScroll = false;
            }
        }
        if (z2 && this.contentScrim == null) {
            setContentScrim(new ColorDrawable(elevationOverlayProvider.compositeOverlayIfNeeded(elevationOverlayProvider.colorSurface, getResources().getDimension(C1777R.dimen.design_appbar_elevation))));
        }
        this.toolbarId = obtainStyledAttributes.getResourceId(22, -1);
        this.forceApplySystemWindowInsetTop = obtainStyledAttributes.getBoolean(13, false);
        this.extraMultilineHeightEnabled = obtainStyledAttributes.getBoolean(12, false);
        obtainStyledAttributes.recycle();
        setWillNotDraw(false);
        C19571 r12 = new OnApplyWindowInsetsListener() {
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                WindowInsetsCompat windowInsetsCompat2;
                CollapsingToolbarLayout collapsingToolbarLayout = CollapsingToolbarLayout.this;
                Objects.requireNonNull(collapsingToolbarLayout);
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                if (ViewCompat.Api16Impl.getFitsSystemWindows(collapsingToolbarLayout)) {
                    windowInsetsCompat2 = windowInsetsCompat;
                } else {
                    windowInsetsCompat2 = null;
                }
                if (!Objects.equals(collapsingToolbarLayout.lastInsets, windowInsetsCompat2)) {
                    collapsingToolbarLayout.lastInsets = windowInsetsCompat2;
                    collapsingToolbarLayout.requestLayout();
                }
                return windowInsetsCompat.mImpl.consumeSystemWindowInsets();
            }
        };
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(this, r12);
    }

    public static ViewOffsetHelper getViewOffsetHelper(View view) {
        ViewOffsetHelper viewOffsetHelper = (ViewOffsetHelper) view.getTag(C1777R.C1779id.view_offset_helper);
        if (viewOffsetHelper != null) {
            return viewOffsetHelper;
        }
        ViewOffsetHelper viewOffsetHelper2 = new ViewOffsetHelper(view);
        view.setTag(C1777R.C1779id.view_offset_helper, viewOffsetHelper2);
        return viewOffsetHelper2;
    }

    public final void draw(Canvas canvas) {
        int i;
        Drawable drawable;
        super.draw(canvas);
        ensureToolbar();
        if (this.toolbar == null && (drawable = this.contentScrim) != null && this.scrimAlpha > 0) {
            drawable.mutate().setAlpha(this.scrimAlpha);
            this.contentScrim.draw(canvas);
        }
        if (this.collapsingTitleEnabled && this.drawCollapsingTitle) {
            if (!(this.toolbar == null || this.contentScrim == null || this.scrimAlpha <= 0)) {
                boolean z = true;
                if (this.titleCollapseMode != 1) {
                    z = false;
                }
                if (z) {
                    CollapsingTextHelper collapsingTextHelper2 = this.collapsingTextHelper;
                    Objects.requireNonNull(collapsingTextHelper2);
                    float f = collapsingTextHelper2.expandedFraction;
                    CollapsingTextHelper collapsingTextHelper3 = this.collapsingTextHelper;
                    Objects.requireNonNull(collapsingTextHelper3);
                    if (f < collapsingTextHelper3.fadeModeThresholdFraction) {
                        int save = canvas.save();
                        canvas.clipRect(this.contentScrim.getBounds(), Region.Op.DIFFERENCE);
                        this.collapsingTextHelper.draw(canvas);
                        canvas.restoreToCount(save);
                    }
                }
            }
            this.collapsingTextHelper.draw(canvas);
        }
        if (this.statusBarScrim != null && this.scrimAlpha > 0) {
            WindowInsetsCompat windowInsetsCompat = this.lastInsets;
            if (windowInsetsCompat != null) {
                i = windowInsetsCompat.getSystemWindowInsetTop();
            } else {
                i = 0;
            }
            if (i > 0) {
                this.statusBarScrim.setBounds(0, -this.currentOffset, getWidth(), i - this.currentOffset);
                this.statusBarScrim.mutate().setAlpha(this.scrimAlpha);
                this.statusBarScrim.draw(canvas);
            }
        }
    }

    public final void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.statusBarScrim;
        boolean z = false;
        if (drawable != null && drawable.isStateful()) {
            z = false | drawable.setState(drawableState);
        }
        Drawable drawable2 = this.contentScrim;
        if (drawable2 != null && drawable2.isStateful()) {
            z |= drawable2.setState(drawableState);
        }
        CollapsingTextHelper collapsingTextHelper2 = this.collapsingTextHelper;
        if (collapsingTextHelper2 != null) {
            z |= collapsingTextHelper2.setState(drawableState);
        }
        if (z) {
            invalidate();
        }
    }

    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = getParent();
        if (parent instanceof AppBarLayout) {
            AppBarLayout appBarLayout = (AppBarLayout) parent;
            boolean z = true;
            if (this.titleCollapseMode != 1) {
                z = false;
            }
            if (z) {
                Objects.requireNonNull(appBarLayout);
                appBarLayout.liftOnScroll = false;
            }
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            setFitsSystemWindows(ViewCompat.Api16Impl.getFitsSystemWindows(appBarLayout));
            if (this.onOffsetChangedListener == null) {
                this.onOffsetChangedListener = new OffsetUpdateListener();
            }
            OffsetUpdateListener offsetUpdateListener = this.onOffsetChangedListener;
            Objects.requireNonNull(appBarLayout);
            if (appBarLayout.listeners == null) {
                appBarLayout.listeners = new ArrayList();
            }
            if (offsetUpdateListener != null && !appBarLayout.listeners.contains(offsetUpdateListener)) {
                appBarLayout.listeners.add(offsetUpdateListener);
            }
            ViewCompat.Api20Impl.requestApplyInsets(this);
        }
    }

    public final void onDetachedFromWindow() {
        ViewParent parent = getParent();
        OffsetUpdateListener offsetUpdateListener = this.onOffsetChangedListener;
        if (offsetUpdateListener != null && (parent instanceof AppBarLayout)) {
            AppBarLayout appBarLayout = (AppBarLayout) parent;
            Objects.requireNonNull(appBarLayout);
            ArrayList arrayList = appBarLayout.listeners;
            if (arrayList != null) {
                arrayList.remove(offsetUpdateListener);
            }
        }
        super.onDetachedFromWindow();
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        WindowInsetsCompat windowInsetsCompat = this.lastInsets;
        if (windowInsetsCompat != null) {
            int systemWindowInsetTop = windowInsetsCompat.getSystemWindowInsetTop();
            int childCount = getChildCount();
            for (int i5 = 0; i5 < childCount; i5++) {
                View childAt = getChildAt(i5);
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                if (!ViewCompat.Api16Impl.getFitsSystemWindows(childAt) && childAt.getTop() < systemWindowInsetTop) {
                    childAt.offsetTopAndBottom(systemWindowInsetTop);
                }
            }
        }
        int childCount2 = getChildCount();
        for (int i6 = 0; i6 < childCount2; i6++) {
            ViewOffsetHelper viewOffsetHelper = getViewOffsetHelper(getChildAt(i6));
            viewOffsetHelper.layoutTop = viewOffsetHelper.view.getTop();
            viewOffsetHelper.layoutLeft = viewOffsetHelper.view.getLeft();
        }
        updateTextBounds(i, i2, i3, i4, false);
        updateTitleFromToolbarIfNeeded();
        updateScrimVisibility();
        int childCount3 = getChildCount();
        for (int i7 = 0; i7 < childCount3; i7++) {
            getViewOffsetHelper(getChildAt(i7)).applyOffsets();
        }
    }

    public final void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int i5;
        ensureToolbar();
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i2);
        WindowInsetsCompat windowInsetsCompat = this.lastInsets;
        if (windowInsetsCompat != null) {
            i3 = windowInsetsCompat.getSystemWindowInsetTop();
        } else {
            i3 = 0;
        }
        if ((mode == 0 || this.forceApplySystemWindowInsetTop) && i3 > 0) {
            this.topInsetApplied = i3;
            super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(getMeasuredHeight() + i3, 1073741824));
        }
        if (this.extraMultilineHeightEnabled) {
            CollapsingTextHelper collapsingTextHelper2 = this.collapsingTextHelper;
            Objects.requireNonNull(collapsingTextHelper2);
            if (collapsingTextHelper2.maxLines > 1) {
                updateTitleFromToolbarIfNeeded();
                updateTextBounds(0, 0, getMeasuredWidth(), getMeasuredHeight(), true);
                CollapsingTextHelper collapsingTextHelper3 = this.collapsingTextHelper;
                Objects.requireNonNull(collapsingTextHelper3);
                int i6 = collapsingTextHelper3.expandedLineCount;
                if (i6 > 1) {
                    CollapsingTextHelper collapsingTextHelper4 = this.collapsingTextHelper;
                    Objects.requireNonNull(collapsingTextHelper4);
                    TextPaint textPaint = collapsingTextHelper4.tmpPaint;
                    textPaint.setTextSize(collapsingTextHelper4.expandedTextSize);
                    textPaint.setTypeface(collapsingTextHelper4.expandedTypeface);
                    textPaint.setLetterSpacing(collapsingTextHelper4.expandedLetterSpacing);
                    int i7 = i6 - 1;
                    this.extraMultilineHeight = i7 * Math.round(collapsingTextHelper4.tmpPaint.descent() + (-collapsingTextHelper4.tmpPaint.ascent()));
                    super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(getMeasuredHeight() + this.extraMultilineHeight, 1073741824));
                }
            }
        }
        ViewGroup viewGroup = this.toolbar;
        if (viewGroup != null) {
            View view = this.toolbarDirectChild;
            if (view == null || view == this) {
                ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                    i4 = viewGroup.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
                } else {
                    i4 = viewGroup.getMeasuredHeight();
                }
                setMinimumHeight(i4);
                return;
            }
            ViewGroup.LayoutParams layoutParams2 = view.getLayoutParams();
            if (layoutParams2 instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) layoutParams2;
                i5 = view.getMeasuredHeight() + marginLayoutParams2.topMargin + marginLayoutParams2.bottomMargin;
            } else {
                i5 = view.getMeasuredHeight();
            }
            setMinimumHeight(i5);
        }
    }

    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        Drawable drawable = this.contentScrim;
        if (drawable != null) {
            ViewGroup viewGroup = this.toolbar;
            boolean z = true;
            if (this.titleCollapseMode != 1) {
                z = false;
            }
            if (z && viewGroup != null && this.collapsingTitleEnabled) {
                i2 = viewGroup.getBottom();
            }
            drawable.setBounds(0, 0, i, i2);
        }
    }

    public final void setVisibility(int i) {
        boolean z;
        super.setVisibility(i);
        if (i == 0) {
            z = true;
        } else {
            z = false;
        }
        Drawable drawable = this.statusBarScrim;
        if (!(drawable == null || drawable.isVisible() == z)) {
            this.statusBarScrim.setVisible(z, false);
        }
        Drawable drawable2 = this.contentScrim;
        if (drawable2 != null && drawable2.isVisible() != z) {
            this.contentScrim.setVisible(z, false);
        }
    }

    public final boolean verifyDrawable(Drawable drawable) {
        if (super.verifyDrawable(drawable) || drawable == this.contentScrim || drawable == this.statusBarScrim) {
            return true;
        }
        return false;
    }

    public static class LayoutParams extends FrameLayout.LayoutParams {
        public int collapseMode = 0;
        public float parallaxMult = 0.5f;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.CollapsingToolbarLayout_Layout);
            this.collapseMode = obtainStyledAttributes.getInt(0, 0);
            this.parallaxMult = obtainStyledAttributes.getFloat(1, 0.5f);
            obtainStyledAttributes.recycle();
        }

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }
}
