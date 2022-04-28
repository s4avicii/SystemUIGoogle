package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.widget.DropDownListView;
import androidx.appcompat.widget.MenuPopupWindow;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

public final class StandardMenuPopup extends MenuPopup implements PopupWindow.OnDismissListener, View.OnKeyListener {
    public final MenuAdapter mAdapter;
    public View mAnchorView;
    public final C00502 mAttachStateChangeListener = new View.OnAttachStateChangeListener() {
        public final void onViewAttachedToWindow(View view) {
        }

        public final void onViewDetachedFromWindow(View view) {
            ViewTreeObserver viewTreeObserver = StandardMenuPopup.this.mTreeObserver;
            if (viewTreeObserver != null) {
                if (!viewTreeObserver.isAlive()) {
                    StandardMenuPopup.this.mTreeObserver = view.getViewTreeObserver();
                }
                StandardMenuPopup standardMenuPopup = StandardMenuPopup.this;
                standardMenuPopup.mTreeObserver.removeGlobalOnLayoutListener(standardMenuPopup.mGlobalLayoutListener);
            }
            view.removeOnAttachStateChangeListener(this);
        }
    };
    public int mContentWidth;
    public final Context mContext;
    public int mDropDownGravity = 0;
    public final C00491 mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        public final void onGlobalLayout() {
            if (StandardMenuPopup.this.isShowing()) {
                MenuPopupWindow menuPopupWindow = StandardMenuPopup.this.mPopup;
                Objects.requireNonNull(menuPopupWindow);
                if (!menuPopupWindow.mModal) {
                    View view = StandardMenuPopup.this.mShownAnchorView;
                    if (view == null || !view.isShown()) {
                        StandardMenuPopup.this.dismiss();
                    } else {
                        StandardMenuPopup.this.mPopup.show();
                    }
                }
            }
        }
    };
    public boolean mHasContentWidth;
    public final MenuBuilder mMenu;
    public PopupWindow.OnDismissListener mOnDismissListener;
    public final boolean mOverflowOnly;
    public final MenuPopupWindow mPopup;
    public final int mPopupMaxWidth;
    public final int mPopupStyleAttr;
    public final int mPopupStyleRes;
    public MenuPresenter.Callback mPresenterCallback;
    public boolean mShowTitle;
    public View mShownAnchorView;
    public ViewTreeObserver mTreeObserver;
    public boolean mWasDismissed;

    public final void addMenu(MenuBuilder menuBuilder) {
    }

    public final boolean flagActionItems() {
        return false;
    }

    public final void onDismiss() {
        this.mWasDismissed = true;
        MenuBuilder menuBuilder = this.mMenu;
        Objects.requireNonNull(menuBuilder);
        menuBuilder.close(true);
        ViewTreeObserver viewTreeObserver = this.mTreeObserver;
        if (viewTreeObserver != null) {
            if (!viewTreeObserver.isAlive()) {
                this.mTreeObserver = this.mShownAnchorView.getViewTreeObserver();
            }
            this.mTreeObserver.removeGlobalOnLayoutListener(this.mGlobalLayoutListener);
            this.mTreeObserver = null;
        }
        this.mShownAnchorView.removeOnAttachStateChangeListener(this.mAttachStateChangeListener);
        PopupWindow.OnDismissListener onDismissListener = this.mOnDismissListener;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    public final void onRestoreInstanceState(Parcelable parcelable) {
    }

    public final Parcelable onSaveInstanceState() {
        return null;
    }

    public final void updateMenuView(boolean z) {
        this.mHasContentWidth = false;
        MenuAdapter menuAdapter = this.mAdapter;
        if (menuAdapter != null) {
            menuAdapter.notifyDataSetChanged();
        }
    }

    public final DropDownListView getListView() {
        MenuPopupWindow menuPopupWindow = this.mPopup;
        Objects.requireNonNull(menuPopupWindow);
        return menuPopupWindow.mDropDownList;
    }

    public final boolean isShowing() {
        if (this.mWasDismissed || !this.mPopup.isShowing()) {
            return false;
        }
        return true;
    }

    public final void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        if (menuBuilder == this.mMenu) {
            dismiss();
            MenuPresenter.Callback callback = this.mPresenterCallback;
            if (callback != null) {
                callback.onCloseMenu(menuBuilder, z);
            }
        }
    }

    public final void setForceShowIcon(boolean z) {
        MenuAdapter menuAdapter = this.mAdapter;
        Objects.requireNonNull(menuAdapter);
        menuAdapter.mForceShowIcon = z;
    }

    public final void setHorizontalOffset(int i) {
        MenuPopupWindow menuPopupWindow = this.mPopup;
        Objects.requireNonNull(menuPopupWindow);
        menuPopupWindow.mDropDownHorizontalOffset = i;
    }

    public final void setVerticalOffset(int i) {
        this.mPopup.setVerticalOffset(i);
    }

    public StandardMenuPopup(Context context, MenuBuilder menuBuilder, View view, int i, int i2, boolean z) {
        this.mContext = context;
        this.mMenu = menuBuilder;
        this.mOverflowOnly = z;
        this.mAdapter = new MenuAdapter(menuBuilder, LayoutInflater.from(context), z, C1777R.layout.abc_popup_menu_item_layout);
        this.mPopupStyleAttr = i;
        this.mPopupStyleRes = i2;
        Resources resources = context.getResources();
        this.mPopupMaxWidth = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(C1777R.dimen.abc_config_prefDialogWidth));
        this.mAnchorView = view;
        this.mPopup = new MenuPopupWindow(context, i, i2);
        menuBuilder.addMenuPresenter(this, context);
    }

    public final void dismiss() {
        if (isShowing()) {
            this.mPopup.dismiss();
        }
    }

    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1 || i != 82) {
            return false;
        }
        dismiss();
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0077  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onSubMenuSelected(androidx.appcompat.view.menu.SubMenuBuilder r10) {
        /*
            r9 = this;
            boolean r0 = r10.hasVisibleItems()
            r1 = 0
            if (r0 == 0) goto L_0x007f
            androidx.appcompat.view.menu.MenuPopupHelper r0 = new androidx.appcompat.view.menu.MenuPopupHelper
            android.content.Context r3 = r9.mContext
            android.view.View r5 = r9.mShownAnchorView
            boolean r6 = r9.mOverflowOnly
            int r7 = r9.mPopupStyleAttr
            int r8 = r9.mPopupStyleRes
            r2 = r0
            r4 = r10
            r2.<init>(r3, r4, r5, r6, r7, r8)
            androidx.appcompat.view.menu.MenuPresenter$Callback r2 = r9.mPresenterCallback
            r0.mPresenterCallback = r2
            androidx.appcompat.view.menu.MenuPopup r3 = r0.mPopup
            if (r3 == 0) goto L_0x0023
            r3.setCallback(r2)
        L_0x0023:
            boolean r2 = androidx.appcompat.view.menu.MenuPopup.shouldPreserveIconSpacing(r10)
            r0.mForceShowIcon = r2
            androidx.appcompat.view.menu.MenuPopup r3 = r0.mPopup
            if (r3 == 0) goto L_0x0030
            r3.setForceShowIcon(r2)
        L_0x0030:
            android.widget.PopupWindow$OnDismissListener r2 = r9.mOnDismissListener
            r0.mOnDismissListener = r2
            r2 = 0
            r9.mOnDismissListener = r2
            androidx.appcompat.view.menu.MenuBuilder r2 = r9.mMenu
            r2.close(r1)
            androidx.appcompat.widget.MenuPopupWindow r2 = r9.mPopup
            java.util.Objects.requireNonNull(r2)
            int r2 = r2.mDropDownHorizontalOffset
            androidx.appcompat.widget.MenuPopupWindow r3 = r9.mPopup
            int r3 = r3.getVerticalOffset()
            int r4 = r9.mDropDownGravity
            android.view.View r5 = r9.mAnchorView
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r6 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            int r5 = androidx.core.view.ViewCompat.Api17Impl.getLayoutDirection(r5)
            int r4 = android.view.Gravity.getAbsoluteGravity(r4, r5)
            r4 = r4 & 7
            r5 = 5
            if (r4 != r5) goto L_0x0063
            android.view.View r4 = r9.mAnchorView
            int r4 = r4.getWidth()
            int r2 = r2 + r4
        L_0x0063:
            boolean r4 = r0.isShowing()
            r5 = 1
            if (r4 == 0) goto L_0x006b
            goto L_0x0074
        L_0x006b:
            android.view.View r4 = r0.mAnchorView
            if (r4 != 0) goto L_0x0071
            r0 = r1
            goto L_0x0075
        L_0x0071:
            r0.showPopup(r2, r3, r5, r5)
        L_0x0074:
            r0 = r5
        L_0x0075:
            if (r0 == 0) goto L_0x007f
            androidx.appcompat.view.menu.MenuPresenter$Callback r9 = r9.mPresenterCallback
            if (r9 == 0) goto L_0x007e
            r9.onOpenSubMenu(r10)
        L_0x007e:
            return r5
        L_0x007f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.view.menu.StandardMenuPopup.onSubMenuSelected(androidx.appcompat.view.menu.SubMenuBuilder):boolean");
    }

    public final void show() {
        View view;
        boolean z;
        Rect rect;
        boolean z2 = true;
        if (!isShowing()) {
            if (this.mWasDismissed || (view = this.mAnchorView) == null) {
                z2 = false;
            } else {
                this.mShownAnchorView = view;
                MenuPopupWindow menuPopupWindow = this.mPopup;
                Objects.requireNonNull(menuPopupWindow);
                menuPopupWindow.mPopup.setOnDismissListener(this);
                MenuPopupWindow menuPopupWindow2 = this.mPopup;
                Objects.requireNonNull(menuPopupWindow2);
                menuPopupWindow2.mItemClickListener = this;
                MenuPopupWindow menuPopupWindow3 = this.mPopup;
                Objects.requireNonNull(menuPopupWindow3);
                menuPopupWindow3.mModal = true;
                menuPopupWindow3.mPopup.setFocusable(true);
                View view2 = this.mShownAnchorView;
                if (this.mTreeObserver == null) {
                    z = true;
                } else {
                    z = false;
                }
                ViewTreeObserver viewTreeObserver = view2.getViewTreeObserver();
                this.mTreeObserver = viewTreeObserver;
                if (z) {
                    viewTreeObserver.addOnGlobalLayoutListener(this.mGlobalLayoutListener);
                }
                view2.addOnAttachStateChangeListener(this.mAttachStateChangeListener);
                MenuPopupWindow menuPopupWindow4 = this.mPopup;
                Objects.requireNonNull(menuPopupWindow4);
                menuPopupWindow4.mDropDownAnchorView = view2;
                MenuPopupWindow menuPopupWindow5 = this.mPopup;
                int i = this.mDropDownGravity;
                Objects.requireNonNull(menuPopupWindow5);
                menuPopupWindow5.mDropDownGravity = i;
                if (!this.mHasContentWidth) {
                    this.mContentWidth = MenuPopup.measureIndividualMenuWidth(this.mAdapter, this.mContext, this.mPopupMaxWidth);
                    this.mHasContentWidth = true;
                }
                this.mPopup.setContentWidth(this.mContentWidth);
                MenuPopupWindow menuPopupWindow6 = this.mPopup;
                Objects.requireNonNull(menuPopupWindow6);
                menuPopupWindow6.mPopup.setInputMethodMode(2);
                MenuPopupWindow menuPopupWindow7 = this.mPopup;
                Rect rect2 = this.mEpicenterBounds;
                if (rect2 != null) {
                    Objects.requireNonNull(menuPopupWindow7);
                    rect = new Rect(rect2);
                } else {
                    rect = null;
                }
                menuPopupWindow7.mEpicenterBounds = rect;
                this.mPopup.show();
                MenuPopupWindow menuPopupWindow8 = this.mPopup;
                Objects.requireNonNull(menuPopupWindow8);
                DropDownListView dropDownListView = menuPopupWindow8.mDropDownList;
                dropDownListView.setOnKeyListener(this);
                if (this.mShowTitle) {
                    MenuBuilder menuBuilder = this.mMenu;
                    Objects.requireNonNull(menuBuilder);
                    if (menuBuilder.mHeaderTitle != null) {
                        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(this.mContext).inflate(C1777R.layout.abc_popup_menu_header_item_layout, dropDownListView, false);
                        TextView textView = (TextView) frameLayout.findViewById(16908310);
                        if (textView != null) {
                            MenuBuilder menuBuilder2 = this.mMenu;
                            Objects.requireNonNull(menuBuilder2);
                            textView.setText(menuBuilder2.mHeaderTitle);
                        }
                        frameLayout.setEnabled(false);
                        dropDownListView.addHeaderView(frameLayout, (Object) null, false);
                    }
                }
                this.mPopup.setAdapter(this.mAdapter);
                this.mPopup.show();
            }
        }
        if (!z2) {
            throw new IllegalStateException("StandardMenuPopup cannot be used without an anchor");
        }
    }

    public final void setAnchorView(View view) {
        this.mAnchorView = view;
    }

    public final void setCallback(MenuPresenter.Callback callback) {
        this.mPresenterCallback = callback;
    }

    public final void setGravity(int i) {
        this.mDropDownGravity = i;
    }

    public final void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    public final void setShowTitle(boolean z) {
        this.mShowTitle = z;
    }
}
