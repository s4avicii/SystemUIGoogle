package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.ActionMenuPresenter;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import java.util.Objects;

public class ActionMenuView extends LinearLayoutCompat implements MenuBuilder.ItemInvoker, MenuView {
    public MenuPresenter.Callback mActionMenuPresenterCallback;
    public boolean mFormatItems;
    public int mFormatItemsWidth;
    public int mGeneratedItemPadding;
    public MenuBuilder mMenu;
    public MenuBuilder.Callback mMenuBuilderCallback;
    public int mMinCellSize;
    public OnMenuItemClickListener mOnMenuItemClickListener;
    public Context mPopupContext;
    public int mPopupTheme;
    public ActionMenuPresenter mPresenter;
    public boolean mReserveOverflow;

    public interface ActionMenuChildView {
        boolean needsDividerAfter();

        boolean needsDividerBefore();
    }

    public static class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        public final void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        }

        public final boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            return false;
        }
    }

    public static class LayoutParams extends LinearLayoutCompat.LayoutParams {
        @ViewDebug.ExportedProperty
        public int cellsUsed;
        @ViewDebug.ExportedProperty
        public boolean expandable;
        public boolean expanded;
        @ViewDebug.ExportedProperty
        public int extraPixels;
        @ViewDebug.ExportedProperty
        public boolean isOverflowButton;
        @ViewDebug.ExportedProperty
        public boolean preventEdgeOffset;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.LayoutParams) layoutParams);
            this.isOverflowButton = layoutParams.isOverflowButton;
        }

        public LayoutParams() {
            super(-2);
            this.isOverflowButton = false;
        }
    }

    public class MenuBuilderCallback implements MenuBuilder.Callback {
        public MenuBuilderCallback() {
        }

        public final boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            OnMenuItemClickListener onMenuItemClickListener = ActionMenuView.this.mOnMenuItemClickListener;
            if (onMenuItemClickListener == null) {
                return false;
            }
            Toolbar.C00851 r0 = (Toolbar.C00851) onMenuItemClickListener;
            Objects.requireNonNull(r0);
            Objects.requireNonNull(Toolbar.this);
            return false;
        }

        public final void onMenuModeChange(MenuBuilder menuBuilder) {
            MenuBuilder.Callback callback = ActionMenuView.this.mMenuBuilderCallback;
            if (callback != null) {
                callback.onMenuModeChange(menuBuilder);
            }
        }
    }

    public interface OnMenuItemClickListener {
    }

    public ActionMenuView(Context context) {
        this(context, (AttributeSet) null);
    }

    public final boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return false;
    }

    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        LayoutParams layoutParams = new LayoutParams();
        layoutParams.gravity = 16;
        return layoutParams;
    }

    public final boolean hasSupportDividerBeforeChildAt(int i) {
        boolean z = false;
        if (i == 0) {
            return false;
        }
        View childAt = getChildAt(i - 1);
        View childAt2 = getChildAt(i);
        if (i < getChildCount() && (childAt instanceof ActionMenuChildView)) {
            z = false | ((ActionMenuChildView) childAt).needsDividerAfter();
        }
        if (i <= 0 || !(childAt2 instanceof ActionMenuChildView)) {
            return z;
        }
        return z | ((ActionMenuChildView) childAt2).needsDividerBefore();
    }

    public ActionMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mBaselineAligned = false;
        float f = context.getResources().getDisplayMetrics().density;
        this.mMinCellSize = (int) (56.0f * f);
        this.mGeneratedItemPadding = (int) (f * 4.0f);
        this.mPopupContext = context;
        this.mPopupTheme = 0;
    }

    public final MenuBuilder getMenu() {
        if (this.mMenu == null) {
            Context context = getContext();
            MenuBuilder menuBuilder = new MenuBuilder(context);
            this.mMenu = menuBuilder;
            menuBuilder.mCallback = new MenuBuilderCallback();
            ActionMenuPresenter actionMenuPresenter = new ActionMenuPresenter(context);
            this.mPresenter = actionMenuPresenter;
            actionMenuPresenter.mReserveOverflow = true;
            actionMenuPresenter.mReserveOverflowSet = true;
            MenuPresenter.Callback callback = this.mActionMenuPresenterCallback;
            if (callback == null) {
                callback = new ActionMenuPresenterCallback();
            }
            Objects.requireNonNull(actionMenuPresenter);
            actionMenuPresenter.mCallback = callback;
            this.mMenu.addMenuPresenter(this.mPresenter, this.mPopupContext);
            ActionMenuPresenter actionMenuPresenter2 = this.mPresenter;
            Objects.requireNonNull(actionMenuPresenter2);
            actionMenuPresenter2.mMenuView = this;
            this.mMenu = actionMenuPresenter2.mMenu;
        }
        return this.mMenu;
    }

    public final boolean invokeItem(MenuItemImpl menuItemImpl) {
        MenuBuilder menuBuilder = this.mMenu;
        Objects.requireNonNull(menuBuilder);
        return menuBuilder.performItemAction(menuItemImpl, (MenuPresenter) null, 0);
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        if (!this.mFormatItems) {
            super.onLayout(z, i, i2, i3, i4);
            return;
        }
        int childCount = getChildCount();
        int i8 = (i4 - i2) / 2;
        int i9 = this.mDividerWidth;
        int i10 = i3 - i;
        int paddingRight = (i10 - getPaddingRight()) - getPaddingLeft();
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        int i11 = 0;
        int i12 = 0;
        for (int i13 = 0; i13 < childCount; i13++) {
            View childAt = getChildAt(i13);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isOverflowButton) {
                    int measuredWidth = childAt.getMeasuredWidth();
                    if (hasSupportDividerBeforeChildAt(i13)) {
                        measuredWidth += i9;
                    }
                    int measuredHeight = childAt.getMeasuredHeight();
                    if (isLayoutRtl) {
                        i6 = getPaddingLeft() + layoutParams.leftMargin;
                        i7 = i6 + measuredWidth;
                    } else {
                        i7 = (getWidth() - getPaddingRight()) - layoutParams.rightMargin;
                        i6 = i7 - measuredWidth;
                    }
                    int i14 = i8 - (measuredHeight / 2);
                    childAt.layout(i6, i14, i7, measuredHeight + i14);
                    paddingRight -= measuredWidth;
                    i11 = 1;
                } else {
                    paddingRight -= (childAt.getMeasuredWidth() + layoutParams.leftMargin) + layoutParams.rightMargin;
                    hasSupportDividerBeforeChildAt(i13);
                    i12++;
                }
            }
        }
        if (childCount == 1 && i11 == 0) {
            View childAt2 = getChildAt(0);
            int measuredWidth2 = childAt2.getMeasuredWidth();
            int measuredHeight2 = childAt2.getMeasuredHeight();
            int i15 = (i10 / 2) - (measuredWidth2 / 2);
            int i16 = i8 - (measuredHeight2 / 2);
            childAt2.layout(i15, i16, measuredWidth2 + i15, measuredHeight2 + i16);
            return;
        }
        int i17 = i12 - (i11 ^ 1);
        if (i17 > 0) {
            i5 = paddingRight / i17;
        } else {
            i5 = 0;
        }
        int max = Math.max(0, i5);
        if (isLayoutRtl) {
            int width = getWidth() - getPaddingRight();
            for (int i18 = 0; i18 < childCount; i18++) {
                View childAt3 = getChildAt(i18);
                LayoutParams layoutParams2 = (LayoutParams) childAt3.getLayoutParams();
                if (childAt3.getVisibility() != 8 && !layoutParams2.isOverflowButton) {
                    int i19 = width - layoutParams2.rightMargin;
                    int measuredWidth3 = childAt3.getMeasuredWidth();
                    int measuredHeight3 = childAt3.getMeasuredHeight();
                    int i20 = i8 - (measuredHeight3 / 2);
                    childAt3.layout(i19 - measuredWidth3, i20, i19, measuredHeight3 + i20);
                    width = i19 - ((measuredWidth3 + layoutParams2.leftMargin) + max);
                }
            }
            return;
        }
        int paddingLeft = getPaddingLeft();
        for (int i21 = 0; i21 < childCount; i21++) {
            View childAt4 = getChildAt(i21);
            LayoutParams layoutParams3 = (LayoutParams) childAt4.getLayoutParams();
            if (childAt4.getVisibility() != 8 && !layoutParams3.isOverflowButton) {
                int i22 = paddingLeft + layoutParams3.leftMargin;
                int measuredWidth4 = childAt4.getMeasuredWidth();
                int measuredHeight4 = childAt4.getMeasuredHeight();
                int i23 = i8 - (measuredHeight4 / 2);
                childAt4.layout(i22, i23, i22 + measuredWidth4, measuredHeight4 + i23);
                paddingLeft = measuredWidth4 + layoutParams3.rightMargin + max + i22;
            }
        }
    }

    public final void onMeasure(int i, int i2) {
        boolean z;
        boolean z2;
        boolean z3;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        boolean z4;
        boolean z5;
        int i10;
        ActionMenuItemView actionMenuItemView;
        boolean z6;
        int i11;
        boolean z7;
        MenuBuilder menuBuilder;
        boolean z8 = this.mFormatItems;
        if (View.MeasureSpec.getMode(i) == 1073741824) {
            z = true;
        } else {
            z = false;
        }
        this.mFormatItems = z;
        if (z8 != z) {
            this.mFormatItemsWidth = 0;
        }
        int size = View.MeasureSpec.getSize(i);
        if (!(!this.mFormatItems || (menuBuilder = this.mMenu) == null || size == this.mFormatItemsWidth)) {
            this.mFormatItemsWidth = size;
            menuBuilder.onItemsChanged(true);
        }
        int childCount = getChildCount();
        if (!this.mFormatItems || childCount <= 0) {
            int i12 = i2;
            for (int i13 = 0; i13 < childCount; i13++) {
                LayoutParams layoutParams = (LayoutParams) getChildAt(i13).getLayoutParams();
                layoutParams.rightMargin = 0;
                layoutParams.leftMargin = 0;
            }
            super.onMeasure(i, i2);
            return;
        }
        int mode = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i);
        int size3 = View.MeasureSpec.getSize(i2);
        int paddingRight = getPaddingRight() + getPaddingLeft();
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i2, paddingBottom, -2);
        int i14 = size2 - paddingRight;
        int i15 = this.mMinCellSize;
        int i16 = i14 / i15;
        int i17 = i14 % i15;
        if (i16 == 0) {
            setMeasuredDimension(i14, 0);
            return;
        }
        int i18 = (i17 / i16) + i15;
        int childCount2 = getChildCount();
        int i19 = 0;
        int i20 = 0;
        int i21 = 0;
        int i22 = 0;
        boolean z9 = false;
        int i23 = 0;
        long j = 0;
        while (i22 < childCount2) {
            View childAt = getChildAt(i22);
            int i24 = size3;
            if (childAt.getVisibility() == 8) {
                i6 = mode;
                i7 = i14;
                i8 = paddingBottom;
            } else {
                boolean z10 = childAt instanceof ActionMenuItemView;
                int i25 = i20 + 1;
                if (z10) {
                    int i26 = this.mGeneratedItemPadding;
                    i9 = i25;
                    z4 = false;
                    childAt.setPadding(i26, 0, i26, 0);
                } else {
                    i9 = i25;
                    z4 = false;
                }
                LayoutParams layoutParams2 = (LayoutParams) childAt.getLayoutParams();
                layoutParams2.expanded = z4;
                layoutParams2.extraPixels = z4 ? 1 : 0;
                layoutParams2.cellsUsed = z4;
                layoutParams2.expandable = z4;
                layoutParams2.leftMargin = z4;
                layoutParams2.rightMargin = z4;
                if (!z10 || !((ActionMenuItemView) childAt).hasText()) {
                    z5 = false;
                } else {
                    z5 = true;
                }
                layoutParams2.preventEdgeOffset = z5;
                if (layoutParams2.isOverflowButton) {
                    i10 = 1;
                } else {
                    i10 = i16;
                }
                i7 = i14;
                LayoutParams layoutParams3 = (LayoutParams) childAt.getLayoutParams();
                i6 = mode;
                i8 = paddingBottom;
                int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(childMeasureSpec) - paddingBottom, View.MeasureSpec.getMode(childMeasureSpec));
                if (z10) {
                    actionMenuItemView = (ActionMenuItemView) childAt;
                } else {
                    actionMenuItemView = null;
                }
                if (actionMenuItemView == null || !actionMenuItemView.hasText()) {
                    z6 = false;
                } else {
                    z6 = true;
                }
                if (i10 <= 0 || (z6 && i10 < 2)) {
                    i11 = 0;
                } else {
                    childAt.measure(View.MeasureSpec.makeMeasureSpec(i10 * i18, Integer.MIN_VALUE), makeMeasureSpec);
                    int measuredWidth = childAt.getMeasuredWidth();
                    i11 = measuredWidth / i18;
                    if (measuredWidth % i18 != 0) {
                        i11++;
                    }
                    if (z6 && i11 < 2) {
                        i11 = 2;
                    }
                }
                if (layoutParams3.isOverflowButton || !z6) {
                    z7 = false;
                } else {
                    z7 = true;
                }
                layoutParams3.expandable = z7;
                layoutParams3.cellsUsed = i11;
                childAt.measure(View.MeasureSpec.makeMeasureSpec(i18 * i11, 1073741824), makeMeasureSpec);
                i21 = Math.max(i21, i11);
                if (layoutParams2.expandable) {
                    i23++;
                }
                if (layoutParams2.isOverflowButton) {
                    z9 = true;
                }
                i16 -= i11;
                i19 = Math.max(i19, childAt.getMeasuredHeight());
                if (i11 == 1) {
                    j |= (long) (1 << i22);
                }
                i20 = i9;
            }
            i22++;
            size3 = i24;
            paddingBottom = i8;
            i14 = i7;
            mode = i6;
        }
        int i27 = mode;
        int i28 = i14;
        int i29 = size3;
        if (!z9 || i20 != 2) {
            z2 = false;
        } else {
            z2 = true;
        }
        boolean z11 = false;
        while (i23 > 0 && i16 > 0) {
            int i30 = Integer.MAX_VALUE;
            int i31 = 0;
            long j2 = 0;
            for (int i32 = 0; i32 < childCount2; i32++) {
                LayoutParams layoutParams4 = (LayoutParams) getChildAt(i32).getLayoutParams();
                if (layoutParams4.expandable) {
                    int i33 = layoutParams4.cellsUsed;
                    if (i33 < i30) {
                        j2 = 1 << i32;
                        i30 = i33;
                        i31 = 1;
                    } else if (i33 == i30) {
                        i31++;
                        j2 |= 1 << i32;
                    }
                }
            }
            j |= j2;
            if (i31 > i16) {
                break;
            }
            int i34 = i30 + 1;
            int i35 = 0;
            while (i35 < childCount2) {
                View childAt2 = getChildAt(i35);
                LayoutParams layoutParams5 = (LayoutParams) childAt2.getLayoutParams();
                int i36 = childMeasureSpec;
                int i37 = childCount2;
                long j3 = (long) (1 << i35);
                if ((j2 & j3) != 0) {
                    if (z2 && layoutParams5.preventEdgeOffset && i16 == 1) {
                        int i38 = this.mGeneratedItemPadding;
                        childAt2.setPadding(i38 + i18, 0, i38, 0);
                    }
                    layoutParams5.cellsUsed++;
                    layoutParams5.expanded = true;
                    i16--;
                } else if (layoutParams5.cellsUsed == i34) {
                    j |= j3;
                }
                i35++;
                childMeasureSpec = i36;
                childCount2 = i37;
            }
            z11 = true;
        }
        int i39 = childMeasureSpec;
        int i40 = childCount2;
        if (z9 || i20 != 1) {
            z3 = false;
        } else {
            z3 = true;
        }
        if (i16 <= 0 || j == 0 || (i16 >= i20 - 1 && !z3 && i21 <= 1)) {
            i3 = i40;
        } else {
            float bitCount = (float) Long.bitCount(j);
            if (!z3) {
                if ((j & 1) != 0 && !((LayoutParams) getChildAt(0).getLayoutParams()).preventEdgeOffset) {
                    bitCount -= 0.5f;
                }
                int i41 = i40 - 1;
                if ((j & ((long) (1 << i41))) != 0 && !((LayoutParams) getChildAt(i41).getLayoutParams()).preventEdgeOffset) {
                    bitCount -= 0.5f;
                }
            }
            if (bitCount > 0.0f) {
                i5 = (int) (((float) (i16 * i18)) / bitCount);
            } else {
                i5 = 0;
            }
            boolean z12 = z11;
            i3 = i40;
            for (int i42 = 0; i42 < i3; i42++) {
                if ((j & ((long) (1 << i42))) != 0) {
                    View childAt3 = getChildAt(i42);
                    LayoutParams layoutParams6 = (LayoutParams) childAt3.getLayoutParams();
                    if (childAt3 instanceof ActionMenuItemView) {
                        layoutParams6.extraPixels = i5;
                        layoutParams6.expanded = true;
                        if (i42 == 0 && !layoutParams6.preventEdgeOffset) {
                            layoutParams6.leftMargin = (-i5) / 2;
                        }
                        z12 = true;
                    } else {
                        if (layoutParams6.isOverflowButton) {
                            layoutParams6.extraPixels = i5;
                            layoutParams6.expanded = true;
                            layoutParams6.rightMargin = (-i5) / 2;
                            z12 = true;
                        } else {
                            if (i42 != 0) {
                                layoutParams6.leftMargin = i5 / 2;
                            }
                            if (i42 != i3 - 1) {
                                layoutParams6.rightMargin = i5 / 2;
                            }
                        }
                    }
                }
            }
            z11 = z12;
        }
        if (z11) {
            for (int i43 = 0; i43 < i3; i43++) {
                View childAt4 = getChildAt(i43);
                LayoutParams layoutParams7 = (LayoutParams) childAt4.getLayoutParams();
                if (layoutParams7.expanded) {
                    childAt4.measure(View.MeasureSpec.makeMeasureSpec((layoutParams7.cellsUsed * i18) + layoutParams7.extraPixels, 1073741824), i39);
                }
            }
        }
        if (i27 != 1073741824) {
            i4 = i19;
        } else {
            i4 = i29;
        }
        setMeasuredDimension(i28, i4);
    }

    /* renamed from: generateDefaultLayoutParams  reason: collision with other method in class */
    public final LinearLayoutCompat.LayoutParams m142generateDefaultLayoutParams() {
        LayoutParams layoutParams = new LayoutParams();
        layoutParams.gravity = 16;
        return layoutParams;
    }

    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.updateMenuView(false);
            if (this.mPresenter.isOverflowMenuShowing()) {
                this.mPresenter.hideOverflowMenu();
                this.mPresenter.showOverflowMenu();
            }
        }
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.hideOverflowMenu();
            ActionMenuPresenter.ActionButtonSubmenu actionButtonSubmenu = actionMenuPresenter.mActionButtonPopup;
            if (actionButtonSubmenu != null && actionButtonSubmenu.isShowing()) {
                actionButtonSubmenu.mPopup.dismiss();
            }
        }
    }

    /* renamed from: generateLayoutParams  reason: collision with other method in class */
    public final LinearLayoutCompat.LayoutParams m143generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public static LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        LayoutParams layoutParams2;
        if (layoutParams != null) {
            if (layoutParams instanceof LayoutParams) {
                layoutParams2 = new LayoutParams((LayoutParams) layoutParams);
            } else {
                layoutParams2 = new LayoutParams(layoutParams);
            }
            if (layoutParams2.gravity <= 0) {
                layoutParams2.gravity = 16;
            }
            return layoutParams2;
        }
        LayoutParams layoutParams3 = new LayoutParams();
        layoutParams3.gravity = 16;
        return layoutParams3;
    }

    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public final void initialize(MenuBuilder menuBuilder) {
        this.mMenu = menuBuilder;
    }
}
