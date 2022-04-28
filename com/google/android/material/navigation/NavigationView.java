package com.google.android.material.navigation;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.customview.view.AbsSavedState;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.mediarouter.R$bool;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.internal.NavigationMenuPresenter;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.internal.ScrimInsetsFrameLayout;
import com.google.android.material.shape.AbsoluteCornerSize;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearancePathProvider;
import java.util.Objects;
import java.util.WeakHashMap;

public class NavigationView extends ScrimInsetsFrameLayout {
    public static final int[] CHECKED_STATE_SET = {16842912};
    public static final int[] DISABLED_STATE_SET = {-16842910};
    public boolean bottomInsetScrimEnabled = true;
    public int drawerLayoutCornerSize = 0;
    public int layoutGravity = 0;
    public final int maxWidth;
    public final NavigationMenu menu;
    public SupportMenuInflater menuInflater;
    public C20562 onGlobalLayoutListener;
    public final NavigationMenuPresenter presenter;
    public final RectF shapeClipBounds = new RectF();
    public Path shapeClipPath;
    public final int[] tmpLocation = new int[2];
    public boolean topInsetScrimEnabled = true;

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
        public Bundle menuState;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.menuState = parcel.readBundle(classLoader);
        }

        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.mSuperState, i);
            parcel.writeBundle(this.menuState);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v6, resolved type: android.graphics.drawable.Drawable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v13, resolved type: android.graphics.drawable.InsetDrawable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v1, resolved type: android.graphics.drawable.InsetDrawable} */
    /* JADX WARNING: type inference failed for: r7v7, types: [android.graphics.drawable.Drawable] */
    /* JADX WARNING: Illegal instructions before constructor call */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x01ad  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x01be  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0224  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x023d  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0242  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x0261  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x02b5  */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x02e5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public NavigationView(android.content.Context r23, android.util.AttributeSet r24) {
        /*
            r22 = this;
            r0 = r22
            r7 = r24
            r8 = 2130969514(0x7f0403aa, float:1.7547712E38)
            r9 = 2132018388(0x7f1404d4, float:1.9675081E38)
            r1 = r23
            android.content.Context r1 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r1, r7, r8, r9)
            r0.<init>(r1, r7, r8)
            com.google.android.material.internal.NavigationMenuPresenter r10 = new com.google.android.material.internal.NavigationMenuPresenter
            r10.<init>()
            r0.presenter = r10
            r11 = 2
            int[] r1 = new int[r11]
            r0.tmpLocation = r1
            r12 = 1
            r0.topInsetScrimEnabled = r12
            r0.bottomInsetScrimEnabled = r12
            r13 = 0
            r0.layoutGravity = r13
            r0.drawerLayoutCornerSize = r13
            android.graphics.RectF r1 = new android.graphics.RectF
            r1.<init>()
            r0.shapeClipBounds = r1
            android.content.Context r14 = r22.getContext()
            com.google.android.material.internal.NavigationMenu r15 = new com.google.android.material.internal.NavigationMenu
            r15.<init>(r14)
            r0.menu = r15
            int[] r3 = com.google.android.material.R$styleable.NavigationView
            int[] r6 = new int[r13]
            r4 = 2130969514(0x7f0403aa, float:1.7547712E38)
            r5 = 2132018388(0x7f1404d4, float:1.9675081E38)
            r1 = r14
            r2 = r24
            androidx.appcompat.widget.TintTypedArray r1 = com.google.android.material.internal.ThemeEnforcement.obtainTintedStyledAttributes(r1, r2, r3, r4, r5, r6)
            boolean r2 = r1.hasValue(r12)
            if (r2 == 0) goto L_0x005b
            android.graphics.drawable.Drawable r2 = r1.getDrawable(r12)
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r3 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            androidx.core.view.ViewCompat.Api16Impl.setBackground(r0, r2)
        L_0x005b:
            r2 = 7
            int r2 = r1.getDimensionPixelSize(r2, r13)
            r0.drawerLayoutCornerSize = r2
            int r2 = r1.getInt(r13, r13)
            r0.layoutGravity = r2
            android.graphics.drawable.Drawable r2 = r22.getBackground()
            if (r2 == 0) goto L_0x0076
            android.graphics.drawable.Drawable r2 = r22.getBackground()
            boolean r2 = r2 instanceof android.graphics.drawable.ColorDrawable
            if (r2 == 0) goto L_0x00a1
        L_0x0076:
            com.google.android.material.shape.ShapeAppearanceModel$Builder r2 = com.google.android.material.shape.ShapeAppearanceModel.builder((android.content.Context) r14, (android.util.AttributeSet) r7, (int) r8, (int) r9)
            com.google.android.material.shape.ShapeAppearanceModel r3 = new com.google.android.material.shape.ShapeAppearanceModel
            r3.<init>(r2)
            android.graphics.drawable.Drawable r2 = r22.getBackground()
            com.google.android.material.shape.MaterialShapeDrawable r4 = new com.google.android.material.shape.MaterialShapeDrawable
            r4.<init>((com.google.android.material.shape.ShapeAppearanceModel) r3)
            boolean r3 = r2 instanceof android.graphics.drawable.ColorDrawable
            if (r3 == 0) goto L_0x0099
            android.graphics.drawable.ColorDrawable r2 = (android.graphics.drawable.ColorDrawable) r2
            int r2 = r2.getColor()
            android.content.res.ColorStateList r2 = android.content.res.ColorStateList.valueOf(r2)
            r4.setFillColor(r2)
        L_0x0099:
            r4.initializeElevationOverlay(r14)
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r2 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            androidx.core.view.ViewCompat.Api16Impl.setBackground(r0, r4)
        L_0x00a1:
            r2 = 8
            boolean r3 = r1.hasValue(r2)
            if (r3 == 0) goto L_0x00b1
            int r2 = r1.getDimensionPixelSize(r2, r13)
            float r2 = (float) r2
            r0.setElevation(r2)
        L_0x00b1:
            boolean r2 = r1.getBoolean(r11, r13)
            r0.setFitsSystemWindows(r2)
            r2 = 3
            int r2 = r1.getDimensionPixelSize(r2, r13)
            r0.maxWidth = r2
            r2 = 29
            boolean r3 = r1.hasValue(r2)
            r4 = 0
            if (r3 == 0) goto L_0x00cd
            android.content.res.ColorStateList r2 = r1.getColorStateList(r2)
            goto L_0x00ce
        L_0x00cd:
            r2 = r4
        L_0x00ce:
            r3 = 32
            boolean r5 = r1.hasValue(r3)
            if (r5 == 0) goto L_0x00db
            int r3 = r1.getResourceId(r3, r13)
            goto L_0x00dc
        L_0x00db:
            r3 = r13
        L_0x00dc:
            r5 = 16842808(0x1010038, float:2.3693715E-38)
            if (r3 != 0) goto L_0x00e7
            if (r2 != 0) goto L_0x00e7
            android.content.res.ColorStateList r2 = r0.createDefaultColorStateList(r5)
        L_0x00e7:
            r6 = 14
            boolean r7 = r1.hasValue(r6)
            if (r7 == 0) goto L_0x00f4
            android.content.res.ColorStateList r5 = r1.getColorStateList(r6)
            goto L_0x00f8
        L_0x00f4:
            android.content.res.ColorStateList r5 = r0.createDefaultColorStateList(r5)
        L_0x00f8:
            r6 = 23
            boolean r7 = r1.hasValue(r6)
            if (r7 == 0) goto L_0x0105
            int r6 = r1.getResourceId(r6, r13)
            goto L_0x0106
        L_0x0105:
            r6 = r13
        L_0x0106:
            r7 = 13
            boolean r8 = r1.hasValue(r7)
            if (r8 == 0) goto L_0x011d
            int r7 = r1.getDimensionPixelSize(r7, r13)
            int r8 = r10.itemIconSize
            if (r8 == r7) goto L_0x011d
            r10.itemIconSize = r7
            r10.hasCustomItemIconSize = r12
            r10.updateMenuView(r13)
        L_0x011d:
            r7 = 24
            boolean r8 = r1.hasValue(r7)
            if (r8 == 0) goto L_0x0129
            android.content.res.ColorStateList r4 = r1.getColorStateList(r7)
        L_0x0129:
            if (r6 != 0) goto L_0x0134
            if (r4 != 0) goto L_0x0134
            r4 = 16842806(0x1010036, float:2.369371E-38)
            android.content.res.ColorStateList r4 = r0.createDefaultColorStateList(r4)
        L_0x0134:
            r7 = 10
            android.graphics.drawable.Drawable r7 = r1.getDrawable(r7)
            if (r7 != 0) goto L_0x01a3
            r8 = 16
            boolean r9 = r1.hasValue(r8)
            r11 = 17
            if (r9 != 0) goto L_0x014f
            boolean r9 = r1.hasValue(r11)
            if (r9 == 0) goto L_0x014d
            goto L_0x014f
        L_0x014d:
            r9 = r13
            goto L_0x0150
        L_0x014f:
            r9 = r12
        L_0x0150:
            if (r9 == 0) goto L_0x01a3
            int r7 = r1.getResourceId(r8, r13)
            int r8 = r1.getResourceId(r11, r13)
            com.google.android.material.shape.MaterialShapeDrawable r9 = new com.google.android.material.shape.MaterialShapeDrawable
            android.content.Context r11 = r22.getContext()
            com.google.android.material.shape.AbsoluteCornerSize r12 = new com.google.android.material.shape.AbsoluteCornerSize
            r24 = r4
            float r4 = (float) r13
            r12.<init>(r4)
            com.google.android.material.shape.ShapeAppearanceModel$Builder r4 = com.google.android.material.shape.ShapeAppearanceModel.builder((android.content.Context) r11, (int) r7, (int) r8, (com.google.android.material.shape.CornerSize) r12)
            com.google.android.material.shape.ShapeAppearanceModel r7 = new com.google.android.material.shape.ShapeAppearanceModel
            r7.<init>(r4)
            r9.<init>((com.google.android.material.shape.ShapeAppearanceModel) r7)
            android.content.Context r4 = r22.getContext()
            r7 = 18
            android.content.res.ColorStateList r4 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r4, (androidx.appcompat.widget.TintTypedArray) r1, (int) r7)
            r9.setFillColor(r4)
            r4 = 21
            int r18 = r1.getDimensionPixelSize(r4, r13)
            r4 = 22
            int r19 = r1.getDimensionPixelSize(r4, r13)
            r4 = 20
            int r20 = r1.getDimensionPixelSize(r4, r13)
            r4 = 19
            int r21 = r1.getDimensionPixelSize(r4, r13)
            android.graphics.drawable.InsetDrawable r7 = new android.graphics.drawable.InsetDrawable
            r16 = r7
            r17 = r9
            r16.<init>(r17, r18, r19, r20, r21)
            goto L_0x01a5
        L_0x01a3:
            r24 = r4
        L_0x01a5:
            r4 = 11
            boolean r8 = r1.hasValue(r4)
            if (r8 == 0) goto L_0x01b6
            int r4 = r1.getDimensionPixelSize(r4, r13)
            r10.itemHorizontalPadding = r4
            r10.updateMenuView(r13)
        L_0x01b6:
            r4 = 25
            boolean r8 = r1.hasValue(r4)
            if (r8 == 0) goto L_0x01c7
            int r4 = r1.getDimensionPixelSize(r4, r13)
            r10.itemVerticalPadding = r4
            r10.updateMenuView(r13)
        L_0x01c7:
            r4 = 6
            int r4 = r1.getDimensionPixelSize(r4, r13)
            r10.dividerInsetStart = r4
            r10.updateMenuView(r13)
            r4 = 5
            int r4 = r1.getDimensionPixelSize(r4, r13)
            r10.dividerInsetEnd = r4
            r10.updateMenuView(r13)
            r4 = 31
            int r4 = r1.getDimensionPixelSize(r4, r13)
            r10.subheaderInsetStart = r4
            r10.updateMenuView(r13)
            r4 = 30
            int r4 = r1.getDimensionPixelSize(r4, r13)
            r10.subheaderInsetStart = r4
            r10.updateMenuView(r13)
            r4 = 33
            boolean r8 = r0.topInsetScrimEnabled
            boolean r4 = r1.getBoolean(r4, r8)
            r0.topInsetScrimEnabled = r4
            r4 = 4
            boolean r8 = r0.bottomInsetScrimEnabled
            boolean r4 = r1.getBoolean(r4, r8)
            r0.bottomInsetScrimEnabled = r4
            r4 = 12
            int r4 = r1.getDimensionPixelSize(r4, r13)
            r8 = 15
            r9 = 1
            int r8 = r1.getInt(r8, r9)
            r10.itemMaxLines = r8
            r10.updateMenuView(r13)
            com.google.android.material.navigation.NavigationView$1 r8 = new com.google.android.material.navigation.NavigationView$1
            r8.<init>()
            r15.mCallback = r8
            r10.f132id = r9
            r10.initForMenu(r14, r15)
            if (r3 == 0) goto L_0x0229
            r10.subheaderTextAppearance = r3
            r10.updateMenuView(r13)
        L_0x0229:
            r10.subheaderColor = r2
            r10.updateMenuView(r13)
            r10.iconTintList = r5
            r10.updateMenuView(r13)
            int r2 = r22.getOverScrollMode()
            r10.overScrollMode = r2
            com.google.android.material.internal.NavigationMenuView r3 = r10.menuView
            if (r3 == 0) goto L_0x0240
            r3.setOverScrollMode(r2)
        L_0x0240:
            if (r6 == 0) goto L_0x0247
            r10.textAppearance = r6
            r10.updateMenuView(r13)
        L_0x0247:
            r2 = r24
            r10.textColor = r2
            r10.updateMenuView(r13)
            r10.itemBackground = r7
            r10.updateMenuView(r13)
            r10.itemIconPadding = r4
            r10.updateMenuView(r13)
            android.content.Context r2 = r15.mContext
            r15.addMenuPresenter(r10, r2)
            com.google.android.material.internal.NavigationMenuView r2 = r10.menuView
            if (r2 != 0) goto L_0x02a8
            android.view.LayoutInflater r2 = r10.layoutInflater
            r3 = 2131624078(0x7f0e008e, float:1.8875326E38)
            android.view.View r2 = r2.inflate(r3, r0, r13)
            com.google.android.material.internal.NavigationMenuView r2 = (com.google.android.material.internal.NavigationMenuView) r2
            r10.menuView = r2
            com.google.android.material.internal.NavigationMenuPresenter$NavigationMenuViewAccessibilityDelegate r3 = new com.google.android.material.internal.NavigationMenuPresenter$NavigationMenuViewAccessibilityDelegate
            com.google.android.material.internal.NavigationMenuView r4 = r10.menuView
            r3.<init>(r4)
            java.util.Objects.requireNonNull(r2)
            r2.mAccessibilityDelegate = r3
            androidx.core.view.ViewCompat.setAccessibilityDelegate(r2, r3)
            com.google.android.material.internal.NavigationMenuPresenter$NavigationMenuAdapter r2 = r10.adapter
            if (r2 != 0) goto L_0x0288
            com.google.android.material.internal.NavigationMenuPresenter$NavigationMenuAdapter r2 = new com.google.android.material.internal.NavigationMenuPresenter$NavigationMenuAdapter
            r2.<init>()
            r10.adapter = r2
        L_0x0288:
            int r2 = r10.overScrollMode
            r3 = -1
            if (r2 == r3) goto L_0x0292
            com.google.android.material.internal.NavigationMenuView r3 = r10.menuView
            r3.setOverScrollMode(r2)
        L_0x0292:
            android.view.LayoutInflater r2 = r10.layoutInflater
            r3 = 2131624075(0x7f0e008b, float:1.887532E38)
            com.google.android.material.internal.NavigationMenuView r4 = r10.menuView
            android.view.View r2 = r2.inflate(r3, r4, r13)
            android.widget.LinearLayout r2 = (android.widget.LinearLayout) r2
            r10.headerLayout = r2
            com.google.android.material.internal.NavigationMenuView r2 = r10.menuView
            com.google.android.material.internal.NavigationMenuPresenter$NavigationMenuAdapter r3 = r10.adapter
            r2.setAdapter(r3)
        L_0x02a8:
            com.google.android.material.internal.NavigationMenuView r2 = r10.menuView
            r0.addView(r2)
            r2 = 26
            boolean r3 = r1.hasValue(r2)
            if (r3 == 0) goto L_0x02dd
            int r2 = r1.getResourceId(r2, r13)
            com.google.android.material.internal.NavigationMenuPresenter$NavigationMenuAdapter r3 = r10.adapter
            if (r3 == 0) goto L_0x02c0
            r4 = 1
            r3.updateSuspended = r4
        L_0x02c0:
            androidx.appcompat.view.SupportMenuInflater r3 = r0.menuInflater
            if (r3 != 0) goto L_0x02cf
            androidx.appcompat.view.SupportMenuInflater r3 = new androidx.appcompat.view.SupportMenuInflater
            android.content.Context r4 = r22.getContext()
            r3.<init>(r4)
            r0.menuInflater = r3
        L_0x02cf:
            androidx.appcompat.view.SupportMenuInflater r3 = r0.menuInflater
            r3.inflate(r2, r15)
            com.google.android.material.internal.NavigationMenuPresenter$NavigationMenuAdapter r2 = r10.adapter
            if (r2 == 0) goto L_0x02da
            r2.updateSuspended = r13
        L_0x02da:
            r10.updateMenuView(r13)
        L_0x02dd:
            r2 = 9
            boolean r3 = r1.hasValue(r2)
            if (r3 == 0) goto L_0x02ff
            int r2 = r1.getResourceId(r2, r13)
            android.view.LayoutInflater r3 = r10.layoutInflater
            android.widget.LinearLayout r4 = r10.headerLayout
            android.view.View r2 = r3.inflate(r2, r4, r13)
            android.widget.LinearLayout r3 = r10.headerLayout
            r3.addView(r2)
            com.google.android.material.internal.NavigationMenuView r2 = r10.menuView
            int r3 = r2.getPaddingBottom()
            r2.setPadding(r13, r13, r13, r3)
        L_0x02ff:
            r1.recycle()
            com.google.android.material.navigation.NavigationView$2 r1 = new com.google.android.material.navigation.NavigationView$2
            r1.<init>()
            r0.onGlobalLayoutListener = r1
            android.view.ViewTreeObserver r1 = r22.getViewTreeObserver()
            com.google.android.material.navigation.NavigationView$2 r0 = r0.onGlobalLayoutListener
            r1.addOnGlobalLayoutListener(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.navigation.NavigationView.<init>(android.content.Context, android.util.AttributeSet):void");
    }

    public final ColorStateList createDefaultColorStateList(int i) {
        TypedValue typedValue = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(i, typedValue, true)) {
            return null;
        }
        ColorStateList colorStateList = AppCompatResources.getColorStateList(getContext(), typedValue.resourceId);
        if (!getContext().getTheme().resolveAttribute(C1777R.attr.colorPrimary, typedValue, true)) {
            return null;
        }
        int i2 = typedValue.data;
        int defaultColor = colorStateList.getDefaultColor();
        int[] iArr = DISABLED_STATE_SET;
        return new ColorStateList(new int[][]{iArr, CHECKED_STATE_SET, FrameLayout.EMPTY_STATE_SET}, new int[]{colorStateList.getColorForState(iArr, defaultColor), i2, defaultColor});
    }

    public final void dispatchDraw(Canvas canvas) {
        if (this.shapeClipPath == null) {
            super.dispatchDraw(canvas);
            return;
        }
        int save = canvas.save();
        canvas.clipPath(this.shapeClipPath);
        super.dispatchDraw(canvas);
        canvas.restoreToCount(save);
    }

    public final void onInsetsChanged(WindowInsetsCompat windowInsetsCompat) {
        int i;
        NavigationMenuPresenter navigationMenuPresenter = this.presenter;
        Objects.requireNonNull(navigationMenuPresenter);
        int systemWindowInsetTop = windowInsetsCompat.getSystemWindowInsetTop();
        if (navigationMenuPresenter.paddingTopDefault != systemWindowInsetTop) {
            navigationMenuPresenter.paddingTopDefault = systemWindowInsetTop;
            if (navigationMenuPresenter.headerLayout.getChildCount() != 0 || !navigationMenuPresenter.isBehindStatusBar) {
                i = 0;
            } else {
                i = navigationMenuPresenter.paddingTopDefault;
            }
            NavigationMenuView navigationMenuView = navigationMenuPresenter.menuView;
            navigationMenuView.setPadding(0, i, 0, navigationMenuView.getPaddingBottom());
        }
        NavigationMenuView navigationMenuView2 = navigationMenuPresenter.menuView;
        navigationMenuView2.setPadding(0, navigationMenuView2.getPaddingTop(), 0, windowInsetsCompat.getSystemWindowInsetBottom());
        ViewCompat.dispatchApplyWindowInsets(navigationMenuPresenter.headerLayout, windowInsetsCompat);
    }

    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        Objects.requireNonNull(savedState);
        super.onRestoreInstanceState(savedState.mSuperState);
        this.menu.restorePresenterStates(savedState.menuState);
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        R$bool.setParentAbsoluteElevation(this);
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this.onGlobalLayoutListener);
    }

    public final void onMeasure(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        if (mode == Integer.MIN_VALUE) {
            i = View.MeasureSpec.makeMeasureSpec(Math.min(View.MeasureSpec.getSize(i), this.maxWidth), 1073741824);
        } else if (mode == 0) {
            i = View.MeasureSpec.makeMeasureSpec(this.maxWidth, 1073741824);
        }
        super.onMeasure(i, i2);
    }

    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        Bundle bundle = new Bundle();
        savedState.menuState = bundle;
        this.menu.savePresenterStates(bundle);
        return savedState;
    }

    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (!(getParent() instanceof DrawerLayout) || this.drawerLayoutCornerSize <= 0 || !(getBackground() instanceof MaterialShapeDrawable)) {
            this.shapeClipPath = null;
            this.shapeClipBounds.setEmpty();
            return;
        }
        MaterialShapeDrawable materialShapeDrawable = (MaterialShapeDrawable) getBackground();
        Objects.requireNonNull(materialShapeDrawable);
        ShapeAppearanceModel shapeAppearanceModel = materialShapeDrawable.drawableState.shapeAppearanceModel;
        Objects.requireNonNull(shapeAppearanceModel);
        ShapeAppearanceModel.Builder builder = new ShapeAppearanceModel.Builder(shapeAppearanceModel);
        int i5 = this.layoutGravity;
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (Gravity.getAbsoluteGravity(i5, ViewCompat.Api17Impl.getLayoutDirection(this)) == 3) {
            int i6 = this.drawerLayoutCornerSize;
            builder.topRightCornerSize = new AbsoluteCornerSize((float) i6);
            builder.bottomRightCornerSize = new AbsoluteCornerSize((float) i6);
        } else {
            int i7 = this.drawerLayoutCornerSize;
            builder.topLeftCornerSize = new AbsoluteCornerSize((float) i7);
            builder.bottomLeftCornerSize = new AbsoluteCornerSize((float) i7);
        }
        materialShapeDrawable.setShapeAppearanceModel(new ShapeAppearanceModel(builder));
        if (this.shapeClipPath == null) {
            this.shapeClipPath = new Path();
        }
        this.shapeClipPath.reset();
        this.shapeClipBounds.set(0.0f, 0.0f, (float) i, (float) i2);
        ShapeAppearancePathProvider shapeAppearancePathProvider = ShapeAppearancePathProvider.Lazy.INSTANCE;
        MaterialShapeDrawable.MaterialShapeDrawableState materialShapeDrawableState = materialShapeDrawable.drawableState;
        ShapeAppearanceModel shapeAppearanceModel2 = materialShapeDrawableState.shapeAppearanceModel;
        float f = materialShapeDrawableState.interpolation;
        RectF rectF = this.shapeClipBounds;
        Path path = this.shapeClipPath;
        Objects.requireNonNull(shapeAppearancePathProvider);
        shapeAppearancePathProvider.calculatePath(shapeAppearanceModel2, f, rectF, (MaterialShapeDrawable.C20781) null, path);
        invalidate();
    }

    public final void setElevation(float f) {
        super.setElevation(f);
        R$bool.setElevation(this, f);
    }

    public final void setOverScrollMode(int i) {
        super.setOverScrollMode(i);
        NavigationMenuPresenter navigationMenuPresenter = this.presenter;
        if (navigationMenuPresenter != null) {
            navigationMenuPresenter.overScrollMode = i;
            NavigationMenuView navigationMenuView = navigationMenuPresenter.menuView;
            if (navigationMenuView != null) {
                navigationMenuView.setOverScrollMode(i);
            }
        }
    }
}
