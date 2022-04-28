package com.google.android.material.navigation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.FrameLayout;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.customview.view.AbsSavedState;
import androidx.mediarouter.R$bool;
import java.util.Objects;

public abstract class NavigationBarView extends FrameLayout {
    public ColorStateList itemRippleColor;
    public final NavigationBarMenu menu;
    public SupportMenuInflater menuInflater;
    public final NavigationBarMenuView menuView;
    public final NavigationBarPresenter presenter;

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
        public Bundle menuPresenterState;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.menuPresenterState = parcel.readBundle(classLoader == null ? getClass().getClassLoader() : classLoader);
        }

        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.mSuperState, i);
            parcel.writeBundle(this.menuPresenterState);
        }
    }

    public abstract NavigationBarMenuView createNavigationBarMenuView(Context context);

    public abstract int getMaxItemCount();

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public NavigationBarView(android.content.Context r17, android.util.AttributeSet r18, int r19, int r20) {
        /*
            r16 = this;
            r0 = r16
            android.content.Context r1 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r17, r18, r19, r20)
            r3 = r18
            r5 = r19
            r0.<init>(r1, r3, r5)
            com.google.android.material.navigation.NavigationBarPresenter r1 = new com.google.android.material.navigation.NavigationBarPresenter
            r1.<init>()
            r0.presenter = r1
            android.content.Context r8 = r16.getContext()
            int[] r4 = com.google.android.material.R$styleable.NavigationBarView
            r9 = 2
            int[] r7 = new int[r9]
            r7 = {10, 9} // fill-array
            r2 = r8
            r6 = r20
            androidx.appcompat.widget.TintTypedArray r2 = com.google.android.material.internal.ThemeEnforcement.obtainTintedStyledAttributes(r2, r3, r4, r5, r6, r7)
            com.google.android.material.navigation.NavigationBarMenu r3 = new com.google.android.material.navigation.NavigationBarMenu
            java.lang.Class r4 = r16.getClass()
            int r5 = r16.getMaxItemCount()
            r3.<init>(r8, r4, r5)
            r0.menu = r3
            com.google.android.material.navigation.NavigationBarMenuView r4 = r0.createNavigationBarMenuView(r8)
            r0.menuView = r4
            r1.menuView = r4
            r5 = 1
            r1.f133id = r5
            java.util.Objects.requireNonNull(r4)
            r4.presenter = r1
            android.content.Context r6 = r3.mContext
            r3.addMenuPresenter(r1, r6)
            android.content.Context r6 = r16.getContext()
            r1.initForMenu(r6, r3)
            r1 = 5
            boolean r3 = r2.hasValue(r1)
            r6 = 0
            if (r3 == 0) goto L_0x0082
            android.content.res.ColorStateList r1 = r2.getColorStateList(r1)
            r4.itemIconTint = r1
            com.google.android.material.navigation.NavigationBarItemView[] r3 = r4.buttons
            if (r3 == 0) goto L_0x00aa
            int r7 = r3.length
            r10 = r6
        L_0x0066:
            if (r10 >= r7) goto L_0x00aa
            r11 = r3[r10]
            java.util.Objects.requireNonNull(r11)
            r11.iconTint = r1
            androidx.appcompat.view.menu.MenuItemImpl r12 = r11.itemData
            if (r12 == 0) goto L_0x007f
            android.graphics.drawable.Drawable r12 = r11.wrappedIconDrawable
            if (r12 == 0) goto L_0x007f
            r12.setTintList(r1)
            android.graphics.drawable.Drawable r11 = r11.wrappedIconDrawable
            r11.invalidateSelf()
        L_0x007f:
            int r10 = r10 + 1
            goto L_0x0066
        L_0x0082:
            android.content.res.ColorStateList r1 = r4.createDefaultColorStateList()
            r4.itemIconTint = r1
            com.google.android.material.navigation.NavigationBarItemView[] r3 = r4.buttons
            if (r3 == 0) goto L_0x00aa
            int r7 = r3.length
            r10 = r6
        L_0x008e:
            if (r10 >= r7) goto L_0x00aa
            r11 = r3[r10]
            java.util.Objects.requireNonNull(r11)
            r11.iconTint = r1
            androidx.appcompat.view.menu.MenuItemImpl r12 = r11.itemData
            if (r12 == 0) goto L_0x00a7
            android.graphics.drawable.Drawable r12 = r11.wrappedIconDrawable
            if (r12 == 0) goto L_0x00a7
            r12.setTintList(r1)
            android.graphics.drawable.Drawable r11 = r11.wrappedIconDrawable
            r11.invalidateSelf()
        L_0x00a7:
            int r10 = r10 + 1
            goto L_0x008e
        L_0x00aa:
            android.content.res.Resources r1 = r16.getResources()
            r3 = 2131166513(0x7f070531, float:1.7947273E38)
            int r1 = r1.getDimensionPixelSize(r3)
            r3 = 4
            int r1 = r2.getDimensionPixelSize(r3, r1)
            r4.itemIconSize = r1
            com.google.android.material.navigation.NavigationBarItemView[] r4 = r4.buttons
            if (r4 == 0) goto L_0x00dd
            int r7 = r4.length
            r10 = r6
        L_0x00c2:
            if (r10 >= r7) goto L_0x00dd
            r11 = r4[r10]
            java.util.Objects.requireNonNull(r11)
            android.widget.ImageView r12 = r11.icon
            android.view.ViewGroup$LayoutParams r12 = r12.getLayoutParams()
            android.widget.FrameLayout$LayoutParams r12 = (android.widget.FrameLayout.LayoutParams) r12
            r12.width = r1
            r12.height = r1
            android.widget.ImageView r11 = r11.icon
            r11.setLayoutParams(r12)
            int r10 = r10 + 1
            goto L_0x00c2
        L_0x00dd:
            r1 = 10
            boolean r4 = r2.hasValue(r1)
            r7 = 1065353216(0x3f800000, float:1.0)
            if (r4 == 0) goto L_0x0128
            int r1 = r2.getResourceId(r1, r6)
            com.google.android.material.navigation.NavigationBarMenuView r4 = r0.menuView
            java.util.Objects.requireNonNull(r4)
            r4.itemTextAppearanceInactive = r1
            com.google.android.material.navigation.NavigationBarItemView[] r10 = r4.buttons
            if (r10 == 0) goto L_0x0128
            int r11 = r10.length
            r12 = r6
        L_0x00f8:
            if (r12 >= r11) goto L_0x0128
            r13 = r10[r12]
            java.util.Objects.requireNonNull(r13)
            android.widget.TextView r14 = r13.smallLabel
            r14.setTextAppearance(r1)
            android.widget.TextView r14 = r13.smallLabel
            float r14 = r14.getTextSize()
            android.widget.TextView r15 = r13.largeLabel
            float r15 = r15.getTextSize()
            float r3 = r14 - r15
            r13.shiftAmount = r3
            float r3 = r15 * r7
            float r3 = r3 / r14
            r13.scaleUpFactor = r3
            float r14 = r14 * r7
            float r14 = r14 / r15
            r13.scaleDownFactor = r14
            android.content.res.ColorStateList r3 = r4.itemTextColorFromUser
            if (r3 == 0) goto L_0x0124
            r13.setTextColor(r3)
        L_0x0124:
            int r12 = r12 + 1
            r3 = 4
            goto L_0x00f8
        L_0x0128:
            r1 = 9
            boolean r3 = r2.hasValue(r1)
            if (r3 == 0) goto L_0x0170
            int r1 = r2.getResourceId(r1, r6)
            com.google.android.material.navigation.NavigationBarMenuView r3 = r0.menuView
            java.util.Objects.requireNonNull(r3)
            r3.itemTextAppearanceActive = r1
            com.google.android.material.navigation.NavigationBarItemView[] r4 = r3.buttons
            if (r4 == 0) goto L_0x0170
            int r10 = r4.length
            r11 = r6
        L_0x0141:
            if (r11 >= r10) goto L_0x0170
            r12 = r4[r11]
            java.util.Objects.requireNonNull(r12)
            android.widget.TextView r13 = r12.largeLabel
            r13.setTextAppearance(r1)
            android.widget.TextView r13 = r12.smallLabel
            float r13 = r13.getTextSize()
            android.widget.TextView r14 = r12.largeLabel
            float r14 = r14.getTextSize()
            float r15 = r13 - r14
            r12.shiftAmount = r15
            float r15 = r14 * r7
            float r15 = r15 / r13
            r12.scaleUpFactor = r15
            float r13 = r13 * r7
            float r13 = r13 / r14
            r12.scaleDownFactor = r13
            android.content.res.ColorStateList r13 = r3.itemTextColorFromUser
            if (r13 == 0) goto L_0x016d
            r12.setTextColor(r13)
        L_0x016d:
            int r11 = r11 + 1
            goto L_0x0141
        L_0x0170:
            r1 = 11
            boolean r3 = r2.hasValue(r1)
            if (r3 == 0) goto L_0x0193
            android.content.res.ColorStateList r1 = r2.getColorStateList(r1)
            com.google.android.material.navigation.NavigationBarMenuView r3 = r0.menuView
            java.util.Objects.requireNonNull(r3)
            r3.itemTextColorFromUser = r1
            com.google.android.material.navigation.NavigationBarItemView[] r3 = r3.buttons
            if (r3 == 0) goto L_0x0193
            int r4 = r3.length
            r7 = r6
        L_0x0189:
            if (r7 >= r4) goto L_0x0193
            r10 = r3[r7]
            r10.setTextColor(r1)
            int r7 = r7 + 1
            goto L_0x0189
        L_0x0193:
            android.graphics.drawable.Drawable r1 = r16.getBackground()
            if (r1 == 0) goto L_0x01a1
            android.graphics.drawable.Drawable r1 = r16.getBackground()
            boolean r1 = r1 instanceof android.graphics.drawable.ColorDrawable
            if (r1 == 0) goto L_0x01c3
        L_0x01a1:
            com.google.android.material.shape.MaterialShapeDrawable r1 = new com.google.android.material.shape.MaterialShapeDrawable
            r1.<init>()
            android.graphics.drawable.Drawable r3 = r16.getBackground()
            boolean r4 = r3 instanceof android.graphics.drawable.ColorDrawable
            if (r4 == 0) goto L_0x01bb
            android.graphics.drawable.ColorDrawable r3 = (android.graphics.drawable.ColorDrawable) r3
            int r3 = r3.getColor()
            android.content.res.ColorStateList r3 = android.content.res.ColorStateList.valueOf(r3)
            r1.setFillColor(r3)
        L_0x01bb:
            r1.initializeElevationOverlay(r8)
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r3 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            androidx.core.view.ViewCompat.Api16Impl.setBackground(r0, r1)
        L_0x01c3:
            r1 = 7
            boolean r3 = r2.hasValue(r1)
            if (r3 == 0) goto L_0x01f6
            int r1 = r2.getDimensionPixelSize(r1, r6)
            com.google.android.material.navigation.NavigationBarMenuView r3 = r0.menuView
            java.util.Objects.requireNonNull(r3)
            r3.itemPaddingTop = r1
            com.google.android.material.navigation.NavigationBarItemView[] r3 = r3.buttons
            if (r3 == 0) goto L_0x01f6
            int r4 = r3.length
            r7 = r6
        L_0x01db:
            if (r7 >= r4) goto L_0x01f6
            r10 = r3[r7]
            java.util.Objects.requireNonNull(r10)
            int r11 = r10.itemPaddingTop
            if (r11 == r1) goto L_0x01f3
            r10.itemPaddingTop = r1
            androidx.appcompat.view.menu.MenuItemImpl r11 = r10.itemData
            if (r11 == 0) goto L_0x01f3
            boolean r11 = r11.isChecked()
            r10.setChecked(r11)
        L_0x01f3:
            int r7 = r7 + 1
            goto L_0x01db
        L_0x01f6:
            r1 = 6
            boolean r3 = r2.hasValue(r1)
            if (r3 == 0) goto L_0x0229
            int r1 = r2.getDimensionPixelSize(r1, r6)
            com.google.android.material.navigation.NavigationBarMenuView r3 = r0.menuView
            java.util.Objects.requireNonNull(r3)
            r3.itemPaddingBottom = r1
            com.google.android.material.navigation.NavigationBarItemView[] r3 = r3.buttons
            if (r3 == 0) goto L_0x0229
            int r4 = r3.length
            r7 = r6
        L_0x020e:
            if (r7 >= r4) goto L_0x0229
            r10 = r3[r7]
            java.util.Objects.requireNonNull(r10)
            int r11 = r10.itemPaddingBottom
            if (r11 == r1) goto L_0x0226
            r10.itemPaddingBottom = r1
            androidx.appcompat.view.menu.MenuItemImpl r11 = r10.itemData
            if (r11 == 0) goto L_0x0226
            boolean r11 = r11.isChecked()
            r10.setChecked(r11)
        L_0x0226:
            int r7 = r7 + 1
            goto L_0x020e
        L_0x0229:
            boolean r1 = r2.hasValue(r5)
            if (r1 == 0) goto L_0x0237
            int r1 = r2.getDimensionPixelSize(r5, r6)
            float r1 = (float) r1
            r0.setElevation(r1)
        L_0x0237:
            android.content.res.ColorStateList r1 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r8, (androidx.appcompat.widget.TintTypedArray) r2, (int) r6)
            android.graphics.drawable.Drawable r3 = r16.getBackground()
            android.graphics.drawable.Drawable r3 = r3.mutate()
            r3.setTintList(r1)
            r1 = 12
            r3 = -1
            android.content.res.TypedArray r4 = r2.mWrapped
            int r1 = r4.getInteger(r1, r3)
            com.google.android.material.navigation.NavigationBarMenuView r3 = r0.menuView
            java.util.Objects.requireNonNull(r3)
            int r3 = r3.labelVisibilityMode
            if (r3 == r1) goto L_0x0264
            com.google.android.material.navigation.NavigationBarMenuView r3 = r0.menuView
            java.util.Objects.requireNonNull(r3)
            r3.labelVisibilityMode = r1
            com.google.android.material.navigation.NavigationBarPresenter r1 = r0.presenter
            r1.updateMenuView(r6)
        L_0x0264:
            r1 = 3
            int r3 = r2.getResourceId(r1, r6)
            r4 = 0
            if (r3 == 0) goto L_0x0294
            com.google.android.material.navigation.NavigationBarMenuView r7 = r0.menuView
            java.util.Objects.requireNonNull(r7)
            r7.itemBackgroundRes = r3
            com.google.android.material.navigation.NavigationBarItemView[] r7 = r7.buttons
            if (r7 == 0) goto L_0x02d5
            int r10 = r7.length
            r11 = r6
        L_0x0279:
            if (r11 >= r10) goto L_0x02d5
            r12 = r7[r11]
            java.util.Objects.requireNonNull(r12)
            if (r3 != 0) goto L_0x0284
            r13 = r4
            goto L_0x028e
        L_0x0284:
            android.content.Context r13 = r12.getContext()
            java.lang.Object r14 = androidx.core.content.ContextCompat.sLock
            android.graphics.drawable.Drawable r13 = r13.getDrawable(r3)
        L_0x028e:
            r12.setItemBackground(r13)
            int r11 = r11 + 1
            goto L_0x0279
        L_0x0294:
            r3 = 8
            android.content.res.ColorStateList r3 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r8, (androidx.appcompat.widget.TintTypedArray) r2, (int) r3)
            android.content.res.ColorStateList r7 = r0.itemRippleColor
            if (r7 != r3) goto L_0x02bd
            if (r3 != 0) goto L_0x02d5
            com.google.android.material.navigation.NavigationBarMenuView r3 = r0.menuView
            java.util.Objects.requireNonNull(r3)
            com.google.android.material.navigation.NavigationBarItemView[] r7 = r3.buttons
            if (r7 == 0) goto L_0x02b3
            int r10 = r7.length
            if (r10 <= 0) goto L_0x02b3
            r3 = r7[r6]
            android.graphics.drawable.Drawable r3 = r3.getBackground()
            goto L_0x02b5
        L_0x02b3:
            android.graphics.drawable.Drawable r3 = r3.itemBackground
        L_0x02b5:
            if (r3 == 0) goto L_0x02d5
            com.google.android.material.navigation.NavigationBarMenuView r3 = r0.menuView
            r3.setItemBackground(r4)
            goto L_0x02d5
        L_0x02bd:
            r0.itemRippleColor = r3
            if (r3 != 0) goto L_0x02c7
            com.google.android.material.navigation.NavigationBarMenuView r3 = r0.menuView
            r3.setItemBackground(r4)
            goto L_0x02d5
        L_0x02c7:
            android.content.res.ColorStateList r3 = com.google.android.material.ripple.RippleUtils.convertToRippleDrawableColor(r3)
            com.google.android.material.navigation.NavigationBarMenuView r7 = r0.menuView
            android.graphics.drawable.RippleDrawable r10 = new android.graphics.drawable.RippleDrawable
            r10.<init>(r3, r4, r4)
            r7.setItemBackground(r10)
        L_0x02d5:
            int r3 = r2.getResourceId(r9, r6)
            if (r3 == 0) goto L_0x03d1
            com.google.android.material.navigation.NavigationBarMenuView r4 = r0.menuView
            java.util.Objects.requireNonNull(r4)
            r4.itemActiveIndicatorEnabled = r5
            com.google.android.material.navigation.NavigationBarItemView[] r4 = r4.buttons
            if (r4 == 0) goto L_0x02fe
            int r7 = r4.length
            r10 = r6
        L_0x02e8:
            if (r10 >= r7) goto L_0x02fe
            r11 = r4[r10]
            java.util.Objects.requireNonNull(r11)
            r11.activeIndicatorEnabled = r5
            android.view.View r12 = r11.activeIndicatorView
            if (r12 == 0) goto L_0x02fb
            r12.setVisibility(r6)
            r11.requestLayout()
        L_0x02fb:
            int r10 = r10 + 1
            goto L_0x02e8
        L_0x02fe:
            int[] r4 = com.google.android.material.R$styleable.NavigationBarActiveIndicator
            android.content.res.TypedArray r3 = r8.obtainStyledAttributes(r3, r4)
            int r4 = r3.getDimensionPixelSize(r5, r6)
            com.google.android.material.navigation.NavigationBarMenuView r7 = r0.menuView
            java.util.Objects.requireNonNull(r7)
            r7.itemActiveIndicatorWidth = r4
            com.google.android.material.navigation.NavigationBarItemView[] r7 = r7.buttons
            if (r7 == 0) goto L_0x0328
            int r10 = r7.length
            r11 = r6
        L_0x0315:
            if (r11 >= r10) goto L_0x0328
            r12 = r7[r11]
            java.util.Objects.requireNonNull(r12)
            r12.activeIndicatorDesiredWidth = r4
            int r13 = r12.getWidth()
            r12.updateActiveIndicatorLayoutParams(r13)
            int r11 = r11 + 1
            goto L_0x0315
        L_0x0328:
            int r4 = r3.getDimensionPixelSize(r6, r6)
            com.google.android.material.navigation.NavigationBarMenuView r7 = r0.menuView
            java.util.Objects.requireNonNull(r7)
            r7.itemActiveIndicatorHeight = r4
            com.google.android.material.navigation.NavigationBarItemView[] r7 = r7.buttons
            if (r7 == 0) goto L_0x034c
            int r10 = r7.length
            r11 = r6
        L_0x0339:
            if (r11 >= r10) goto L_0x034c
            r12 = r7[r11]
            java.util.Objects.requireNonNull(r12)
            r12.activeIndicatorDesiredHeight = r4
            int r13 = r12.getWidth()
            r12.updateActiveIndicatorLayoutParams(r13)
            int r11 = r11 + 1
            goto L_0x0339
        L_0x034c:
            int r1 = r3.getDimensionPixelOffset(r1, r6)
            com.google.android.material.navigation.NavigationBarMenuView r4 = r0.menuView
            java.util.Objects.requireNonNull(r4)
            r4.itemActiveIndicatorMarginHorizontal = r1
            com.google.android.material.navigation.NavigationBarItemView[] r4 = r4.buttons
            if (r4 == 0) goto L_0x0370
            int r7 = r4.length
            r10 = r6
        L_0x035d:
            if (r10 >= r7) goto L_0x0370
            r11 = r4[r10]
            java.util.Objects.requireNonNull(r11)
            r11.activeIndicatorMarginHorizontal = r1
            int r12 = r11.getWidth()
            r11.updateActiveIndicatorLayoutParams(r12)
            int r10 = r10 + 1
            goto L_0x035d
        L_0x0370:
            android.content.res.ColorStateList r1 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r8, (android.content.res.TypedArray) r3, (int) r9)
            com.google.android.material.navigation.NavigationBarMenuView r4 = r0.menuView
            java.util.Objects.requireNonNull(r4)
            r4.itemActiveIndicatorColor = r1
            com.google.android.material.navigation.NavigationBarItemView[] r1 = r4.buttons
            if (r1 == 0) goto L_0x0397
            int r7 = r1.length
            r9 = r6
        L_0x0381:
            if (r9 >= r7) goto L_0x0397
            r10 = r1[r9]
            com.google.android.material.shape.MaterialShapeDrawable r11 = r4.createItemActiveIndicatorDrawable()
            java.util.Objects.requireNonNull(r10)
            android.view.View r10 = r10.activeIndicatorView
            if (r10 != 0) goto L_0x0391
            goto L_0x0394
        L_0x0391:
            r10.setBackgroundDrawable(r11)
        L_0x0394:
            int r9 = r9 + 1
            goto L_0x0381
        L_0x0397:
            r1 = 4
            int r1 = r3.getResourceId(r1, r6)
            com.google.android.material.shape.AbsoluteCornerSize r4 = new com.google.android.material.shape.AbsoluteCornerSize
            float r7 = (float) r6
            r4.<init>(r7)
            com.google.android.material.shape.ShapeAppearanceModel$Builder r1 = com.google.android.material.shape.ShapeAppearanceModel.builder((android.content.Context) r8, (int) r1, (int) r6, (com.google.android.material.shape.CornerSize) r4)
            com.google.android.material.shape.ShapeAppearanceModel r4 = new com.google.android.material.shape.ShapeAppearanceModel
            r4.<init>(r1)
            com.google.android.material.navigation.NavigationBarMenuView r1 = r0.menuView
            java.util.Objects.requireNonNull(r1)
            r1.itemActiveIndicatorShapeAppearance = r4
            com.google.android.material.navigation.NavigationBarItemView[] r4 = r1.buttons
            if (r4 == 0) goto L_0x03ce
            int r7 = r4.length
            r8 = r6
        L_0x03b8:
            if (r8 >= r7) goto L_0x03ce
            r9 = r4[r8]
            com.google.android.material.shape.MaterialShapeDrawable r10 = r1.createItemActiveIndicatorDrawable()
            java.util.Objects.requireNonNull(r9)
            android.view.View r9 = r9.activeIndicatorView
            if (r9 != 0) goto L_0x03c8
            goto L_0x03cb
        L_0x03c8:
            r9.setBackgroundDrawable(r10)
        L_0x03cb:
            int r8 = r8 + 1
            goto L_0x03b8
        L_0x03ce:
            r3.recycle()
        L_0x03d1:
            r1 = 13
            boolean r3 = r2.hasValue(r1)
            if (r3 == 0) goto L_0x0406
            int r1 = r2.getResourceId(r1, r6)
            com.google.android.material.navigation.NavigationBarPresenter r3 = r0.presenter
            java.util.Objects.requireNonNull(r3)
            r3.updateSuspended = r5
            androidx.appcompat.view.SupportMenuInflater r3 = r0.menuInflater
            if (r3 != 0) goto L_0x03f3
            androidx.appcompat.view.SupportMenuInflater r3 = new androidx.appcompat.view.SupportMenuInflater
            android.content.Context r4 = r16.getContext()
            r3.<init>(r4)
            r0.menuInflater = r3
        L_0x03f3:
            androidx.appcompat.view.SupportMenuInflater r3 = r0.menuInflater
            com.google.android.material.navigation.NavigationBarMenu r4 = r0.menu
            r3.inflate(r1, r4)
            com.google.android.material.navigation.NavigationBarPresenter r1 = r0.presenter
            java.util.Objects.requireNonNull(r1)
            r1.updateSuspended = r6
            com.google.android.material.navigation.NavigationBarPresenter r1 = r0.presenter
            r1.updateMenuView(r5)
        L_0x0406:
            r2.recycle()
            com.google.android.material.navigation.NavigationBarMenuView r1 = r0.menuView
            r0.addView(r1)
            com.google.android.material.navigation.NavigationBarMenu r1 = r0.menu
            com.google.android.material.navigation.NavigationBarView$1 r2 = new com.google.android.material.navigation.NavigationBarView$1
            r2.<init>()
            java.util.Objects.requireNonNull(r1)
            r1.mCallback = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.navigation.NavigationBarView.<init>(android.content.Context, android.util.AttributeSet, int, int):void");
    }

    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        Objects.requireNonNull(savedState);
        super.onRestoreInstanceState(savedState.mSuperState);
        this.menu.restorePresenterStates(savedState.menuPresenterState);
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        R$bool.setParentAbsoluteElevation(this);
    }

    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        Bundle bundle = new Bundle();
        savedState.menuPresenterState = bundle;
        this.menu.savePresenterStates(bundle);
        return savedState;
    }

    public final void setElevation(float f) {
        super.setElevation(f);
        R$bool.setElevation(this, f);
    }
}
