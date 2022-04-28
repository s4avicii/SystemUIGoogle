package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Parcelable;
import android.transition.Transition;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.widget.DropDownListView;
import androidx.appcompat.widget.MenuItemHoverListener;
import androidx.appcompat.widget.MenuPopupWindow;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import com.android.p012wm.shell.C1777R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.WeakHashMap;

public final class CascadingMenuPopup extends MenuPopup implements View.OnKeyListener, PopupWindow.OnDismissListener {
    public View mAnchorView;
    public final C00442 mAttachStateChangeListener = new View.OnAttachStateChangeListener() {
        public final void onViewAttachedToWindow(View view) {
        }

        public final void onViewDetachedFromWindow(View view) {
            ViewTreeObserver viewTreeObserver = CascadingMenuPopup.this.mTreeObserver;
            if (viewTreeObserver != null) {
                if (!viewTreeObserver.isAlive()) {
                    CascadingMenuPopup.this.mTreeObserver = view.getViewTreeObserver();
                }
                CascadingMenuPopup cascadingMenuPopup = CascadingMenuPopup.this;
                cascadingMenuPopup.mTreeObserver.removeGlobalOnLayoutListener(cascadingMenuPopup.mGlobalLayoutListener);
            }
            view.removeOnAttachStateChangeListener(this);
        }
    };
    public final Context mContext;
    public int mDropDownGravity;
    public boolean mForceShowIcon;
    public final C00431 mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        public final void onGlobalLayout() {
            if (CascadingMenuPopup.this.isShowing() && CascadingMenuPopup.this.mShowingMenus.size() > 0) {
                MenuPopupWindow menuPopupWindow = ((CascadingMenuInfo) CascadingMenuPopup.this.mShowingMenus.get(0)).window;
                Objects.requireNonNull(menuPopupWindow);
                if (!menuPopupWindow.mModal) {
                    View view = CascadingMenuPopup.this.mShownAnchorView;
                    if (view == null || !view.isShown()) {
                        CascadingMenuPopup.this.dismiss();
                        return;
                    }
                    Iterator it = CascadingMenuPopup.this.mShowingMenus.iterator();
                    while (it.hasNext()) {
                        ((CascadingMenuInfo) it.next()).window.show();
                    }
                }
            }
        }
    };
    public boolean mHasXOffset;
    public boolean mHasYOffset;
    public int mLastPosition;
    public final C00453 mMenuItemHoverListener = new MenuItemHoverListener() {
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v11, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: androidx.appcompat.view.menu.CascadingMenuPopup$CascadingMenuInfo} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onItemHoverEnter(final androidx.appcompat.view.menu.MenuBuilder r6, final androidx.appcompat.view.menu.MenuItemImpl r7) {
            /*
                r5 = this;
                androidx.appcompat.view.menu.CascadingMenuPopup r0 = androidx.appcompat.view.menu.CascadingMenuPopup.this
                android.os.Handler r0 = r0.mSubMenuHoverHandler
                r1 = 0
                r0.removeCallbacksAndMessages(r1)
                androidx.appcompat.view.menu.CascadingMenuPopup r0 = androidx.appcompat.view.menu.CascadingMenuPopup.this
                java.util.ArrayList r0 = r0.mShowingMenus
                int r0 = r0.size()
                r2 = 0
            L_0x0011:
                r3 = -1
                if (r2 >= r0) goto L_0x0026
                androidx.appcompat.view.menu.CascadingMenuPopup r4 = androidx.appcompat.view.menu.CascadingMenuPopup.this
                java.util.ArrayList r4 = r4.mShowingMenus
                java.lang.Object r4 = r4.get(r2)
                androidx.appcompat.view.menu.CascadingMenuPopup$CascadingMenuInfo r4 = (androidx.appcompat.view.menu.CascadingMenuPopup.CascadingMenuInfo) r4
                androidx.appcompat.view.menu.MenuBuilder r4 = r4.menu
                if (r6 != r4) goto L_0x0023
                goto L_0x0027
            L_0x0023:
                int r2 = r2 + 1
                goto L_0x0011
            L_0x0026:
                r2 = r3
            L_0x0027:
                if (r2 != r3) goto L_0x002a
                return
            L_0x002a:
                int r2 = r2 + 1
                androidx.appcompat.view.menu.CascadingMenuPopup r0 = androidx.appcompat.view.menu.CascadingMenuPopup.this
                java.util.ArrayList r0 = r0.mShowingMenus
                int r0 = r0.size()
                if (r2 >= r0) goto L_0x0041
                androidx.appcompat.view.menu.CascadingMenuPopup r0 = androidx.appcompat.view.menu.CascadingMenuPopup.this
                java.util.ArrayList r0 = r0.mShowingMenus
                java.lang.Object r0 = r0.get(r2)
                r1 = r0
                androidx.appcompat.view.menu.CascadingMenuPopup$CascadingMenuInfo r1 = (androidx.appcompat.view.menu.CascadingMenuPopup.CascadingMenuInfo) r1
            L_0x0041:
                androidx.appcompat.view.menu.CascadingMenuPopup$3$1 r0 = new androidx.appcompat.view.menu.CascadingMenuPopup$3$1
                r0.<init>(r1, r7, r6)
                long r1 = android.os.SystemClock.uptimeMillis()
                r3 = 200(0xc8, double:9.9E-322)
                long r1 = r1 + r3
                androidx.appcompat.view.menu.CascadingMenuPopup r5 = androidx.appcompat.view.menu.CascadingMenuPopup.this
                android.os.Handler r5 = r5.mSubMenuHoverHandler
                r5.postAtTime(r0, r6, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.view.menu.CascadingMenuPopup.C00453.onItemHoverEnter(androidx.appcompat.view.menu.MenuBuilder, androidx.appcompat.view.menu.MenuItemImpl):void");
        }

        public final void onItemHoverExit(MenuBuilder menuBuilder, MenuItem menuItem) {
            CascadingMenuPopup.this.mSubMenuHoverHandler.removeCallbacksAndMessages(menuBuilder);
        }
    };
    public final int mMenuMaxWidth;
    public PopupWindow.OnDismissListener mOnDismissListener;
    public final boolean mOverflowOnly;
    public final ArrayList mPendingMenus = new ArrayList();
    public final int mPopupStyleAttr;
    public final int mPopupStyleRes;
    public MenuPresenter.Callback mPresenterCallback;
    public int mRawDropDownGravity;
    public boolean mShouldCloseImmediately;
    public boolean mShowTitle;
    public final ArrayList mShowingMenus = new ArrayList();
    public View mShownAnchorView;
    public final Handler mSubMenuHoverHandler;
    public ViewTreeObserver mTreeObserver;
    public int mXOffset;
    public int mYOffset;

    public final boolean flagActionItems() {
        return false;
    }

    public final void onRestoreInstanceState(Parcelable parcelable) {
    }

    public final Parcelable onSaveInstanceState() {
        return null;
    }

    public final void setHorizontalOffset(int i) {
        this.mHasXOffset = true;
        this.mXOffset = i;
    }

    public final void setVerticalOffset(int i) {
        this.mHasYOffset = true;
        this.mYOffset = i;
    }

    public static class CascadingMenuInfo {
        public final MenuBuilder menu;
        public final int position;
        public final MenuPopupWindow window;

        public CascadingMenuInfo(MenuPopupWindow menuPopupWindow, MenuBuilder menuBuilder, int i) {
            this.window = menuPopupWindow;
            this.menu = menuBuilder;
            this.position = i;
        }
    }

    public final void addMenu(MenuBuilder menuBuilder) {
        menuBuilder.addMenuPresenter(this, this.mContext);
        if (isShowing()) {
            showMenu(menuBuilder);
        } else {
            this.mPendingMenus.add(menuBuilder);
        }
    }

    public final void dismiss() {
        int size = this.mShowingMenus.size();
        if (size > 0) {
            CascadingMenuInfo[] cascadingMenuInfoArr = (CascadingMenuInfo[]) this.mShowingMenus.toArray(new CascadingMenuInfo[size]);
            while (true) {
                size--;
                if (size >= 0) {
                    CascadingMenuInfo cascadingMenuInfo = cascadingMenuInfoArr[size];
                    if (cascadingMenuInfo.window.isShowing()) {
                        cascadingMenuInfo.window.dismiss();
                    }
                } else {
                    return;
                }
            }
        }
    }

    public final DropDownListView getListView() {
        if (this.mShowingMenus.isEmpty()) {
            return null;
        }
        ArrayList arrayList = this.mShowingMenus;
        CascadingMenuInfo cascadingMenuInfo = (CascadingMenuInfo) arrayList.get(arrayList.size() - 1);
        Objects.requireNonNull(cascadingMenuInfo);
        MenuPopupWindow menuPopupWindow = cascadingMenuInfo.window;
        Objects.requireNonNull(menuPopupWindow);
        return menuPopupWindow.mDropDownList;
    }

    public final boolean isShowing() {
        if (this.mShowingMenus.size() <= 0 || !((CascadingMenuInfo) this.mShowingMenus.get(0)).window.isShowing()) {
            return false;
        }
        return true;
    }

    public final void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        int i;
        int size = this.mShowingMenus.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                i2 = -1;
                break;
            } else if (menuBuilder == ((CascadingMenuInfo) this.mShowingMenus.get(i2)).menu) {
                break;
            } else {
                i2++;
            }
        }
        if (i2 >= 0) {
            int i3 = i2 + 1;
            if (i3 < this.mShowingMenus.size()) {
                ((CascadingMenuInfo) this.mShowingMenus.get(i3)).menu.close(false);
            }
            CascadingMenuInfo cascadingMenuInfo = (CascadingMenuInfo) this.mShowingMenus.remove(i2);
            cascadingMenuInfo.menu.removeMenuPresenter(this);
            if (this.mShouldCloseImmediately) {
                MenuPopupWindow menuPopupWindow = cascadingMenuInfo.window;
                Objects.requireNonNull(menuPopupWindow);
                menuPopupWindow.mPopup.setExitTransition((Transition) null);
                MenuPopupWindow menuPopupWindow2 = cascadingMenuInfo.window;
                Objects.requireNonNull(menuPopupWindow2);
                menuPopupWindow2.mPopup.setAnimationStyle(0);
            }
            cascadingMenuInfo.window.dismiss();
            int size2 = this.mShowingMenus.size();
            if (size2 > 0) {
                this.mLastPosition = ((CascadingMenuInfo) this.mShowingMenus.get(size2 - 1)).position;
            } else {
                View view = this.mAnchorView;
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                if (ViewCompat.Api17Impl.getLayoutDirection(view) == 1) {
                    i = 0;
                } else {
                    i = 1;
                }
                this.mLastPosition = i;
            }
            if (size2 == 0) {
                dismiss();
                MenuPresenter.Callback callback = this.mPresenterCallback;
                if (callback != null) {
                    callback.onCloseMenu(menuBuilder, true);
                }
                ViewTreeObserver viewTreeObserver = this.mTreeObserver;
                if (viewTreeObserver != null) {
                    if (viewTreeObserver.isAlive()) {
                        this.mTreeObserver.removeGlobalOnLayoutListener(this.mGlobalLayoutListener);
                    }
                    this.mTreeObserver = null;
                }
                this.mShownAnchorView.removeOnAttachStateChangeListener(this.mAttachStateChangeListener);
                this.mOnDismissListener.onDismiss();
            } else if (z) {
                ((CascadingMenuInfo) this.mShowingMenus.get(0)).menu.close(false);
            }
        }
    }

    public final void onDismiss() {
        CascadingMenuInfo cascadingMenuInfo;
        int size = this.mShowingMenus.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                cascadingMenuInfo = null;
                break;
            }
            cascadingMenuInfo = (CascadingMenuInfo) this.mShowingMenus.get(i);
            if (!cascadingMenuInfo.window.isShowing()) {
                break;
            }
            i++;
        }
        if (cascadingMenuInfo != null) {
            cascadingMenuInfo.menu.close(false);
        }
    }

    public final boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        Iterator it = this.mShowingMenus.iterator();
        while (it.hasNext()) {
            CascadingMenuInfo cascadingMenuInfo = (CascadingMenuInfo) it.next();
            if (subMenuBuilder == cascadingMenuInfo.menu) {
                MenuPopupWindow menuPopupWindow = cascadingMenuInfo.window;
                Objects.requireNonNull(menuPopupWindow);
                menuPopupWindow.mDropDownList.requestFocus();
                return true;
            }
        }
        if (!subMenuBuilder.hasVisibleItems()) {
            return false;
        }
        addMenu(subMenuBuilder);
        MenuPresenter.Callback callback = this.mPresenterCallback;
        if (callback != null) {
            callback.onOpenSubMenu(subMenuBuilder);
        }
        return true;
    }

    public final void setAnchorView(View view) {
        if (this.mAnchorView != view) {
            this.mAnchorView = view;
            int i = this.mRawDropDownGravity;
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            this.mDropDownGravity = Gravity.getAbsoluteGravity(i, ViewCompat.Api17Impl.getLayoutDirection(view));
        }
    }

    public final void setGravity(int i) {
        if (this.mRawDropDownGravity != i) {
            this.mRawDropDownGravity = i;
            View view = this.mAnchorView;
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            this.mDropDownGravity = Gravity.getAbsoluteGravity(i, ViewCompat.Api17Impl.getLayoutDirection(view));
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0134, code lost:
        if (((r8.getWidth() + r10[0]) + r4) > r11.right) goto L_0x013f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x013a, code lost:
        if ((r10[0] - r4) < 0) goto L_0x013c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x013f, code lost:
        r11 = 0;
        r8 = 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void showMenu(androidx.appcompat.view.menu.MenuBuilder r17) {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            android.content.Context r2 = r0.mContext
            android.view.LayoutInflater r2 = android.view.LayoutInflater.from(r2)
            androidx.appcompat.view.menu.MenuAdapter r3 = new androidx.appcompat.view.menu.MenuAdapter
            boolean r4 = r0.mOverflowOnly
            r5 = 2131623947(0x7f0e000b, float:1.887506E38)
            r3.<init>(r1, r2, r4, r5)
            boolean r4 = r16.isShowing()
            r5 = 1
            if (r4 != 0) goto L_0x0022
            boolean r4 = r0.mForceShowIcon
            if (r4 == 0) goto L_0x0022
            r3.mForceShowIcon = r5
            goto L_0x002e
        L_0x0022:
            boolean r4 = r16.isShowing()
            if (r4 == 0) goto L_0x002e
            boolean r4 = androidx.appcompat.view.menu.MenuPopup.shouldPreserveIconSpacing(r17)
            r3.mForceShowIcon = r4
        L_0x002e:
            android.content.Context r4 = r0.mContext
            int r6 = r0.mMenuMaxWidth
            int r4 = androidx.appcompat.view.menu.MenuPopup.measureIndividualMenuWidth(r3, r4, r6)
            androidx.appcompat.widget.MenuPopupWindow r6 = new androidx.appcompat.widget.MenuPopupWindow
            android.content.Context r7 = r0.mContext
            int r8 = r0.mPopupStyleAttr
            int r9 = r0.mPopupStyleRes
            r6.<init>(r7, r8, r9)
            androidx.appcompat.view.menu.CascadingMenuPopup$3 r7 = r0.mMenuItemHoverListener
            r6.mHoverListener = r7
            r6.mItemClickListener = r0
            androidx.appcompat.widget.AppCompatPopupWindow r7 = r6.mPopup
            r7.setOnDismissListener(r0)
            android.view.View r7 = r0.mAnchorView
            r6.mDropDownAnchorView = r7
            int r7 = r0.mDropDownGravity
            r6.mDropDownGravity = r7
            r6.mModal = r5
            androidx.appcompat.widget.AppCompatPopupWindow r7 = r6.mPopup
            r7.setFocusable(r5)
            androidx.appcompat.widget.AppCompatPopupWindow r7 = r6.mPopup
            r8 = 2
            r7.setInputMethodMode(r8)
            r6.setAdapter(r3)
            r6.setContentWidth(r4)
            int r3 = r0.mDropDownGravity
            r6.mDropDownGravity = r3
            java.util.ArrayList r3 = r0.mShowingMenus
            int r3 = r3.size()
            r7 = 0
            r9 = 0
            if (r3 <= 0) goto L_0x00ef
            java.util.ArrayList r3 = r0.mShowingMenus
            int r10 = r3.size()
            int r10 = r10 - r5
            java.lang.Object r3 = r3.get(r10)
            androidx.appcompat.view.menu.CascadingMenuPopup$CascadingMenuInfo r3 = (androidx.appcompat.view.menu.CascadingMenuPopup.CascadingMenuInfo) r3
            androidx.appcompat.view.menu.MenuBuilder r10 = r3.menu
            int r11 = r10.size()
            r12 = r7
        L_0x0089:
            if (r12 >= r11) goto L_0x009f
            android.view.MenuItem r13 = r10.getItem(r12)
            boolean r14 = r13.hasSubMenu()
            if (r14 == 0) goto L_0x009c
            android.view.SubMenu r14 = r13.getSubMenu()
            if (r1 != r14) goto L_0x009c
            goto L_0x00a0
        L_0x009c:
            int r12 = r12 + 1
            goto L_0x0089
        L_0x009f:
            r13 = r9
        L_0x00a0:
            if (r13 != 0) goto L_0x00a3
            goto L_0x00ed
        L_0x00a3:
            androidx.appcompat.widget.MenuPopupWindow r10 = r3.window
            java.util.Objects.requireNonNull(r10)
            androidx.appcompat.widget.DropDownListView r10 = r10.mDropDownList
            android.widget.ListAdapter r11 = r10.getAdapter()
            boolean r12 = r11 instanceof android.widget.HeaderViewListAdapter
            if (r12 == 0) goto L_0x00bf
            android.widget.HeaderViewListAdapter r11 = (android.widget.HeaderViewListAdapter) r11
            int r12 = r11.getHeadersCount()
            android.widget.ListAdapter r11 = r11.getWrappedAdapter()
            androidx.appcompat.view.menu.MenuAdapter r11 = (androidx.appcompat.view.menu.MenuAdapter) r11
            goto L_0x00c2
        L_0x00bf:
            androidx.appcompat.view.menu.MenuAdapter r11 = (androidx.appcompat.view.menu.MenuAdapter) r11
            r12 = r7
        L_0x00c2:
            int r14 = r11.getCount()
            r15 = r7
        L_0x00c7:
            r8 = -1
            if (r15 >= r14) goto L_0x00d5
            androidx.appcompat.view.menu.MenuItemImpl r5 = r11.getItem((int) r15)
            if (r13 != r5) goto L_0x00d1
            goto L_0x00d6
        L_0x00d1:
            int r15 = r15 + 1
            r5 = 1
            goto L_0x00c7
        L_0x00d5:
            r15 = r8
        L_0x00d6:
            if (r15 != r8) goto L_0x00d9
            goto L_0x00ed
        L_0x00d9:
            int r15 = r15 + r12
            int r5 = r10.getFirstVisiblePosition()
            int r15 = r15 - r5
            if (r15 < 0) goto L_0x00ed
            int r5 = r10.getChildCount()
            if (r15 < r5) goto L_0x00e8
            goto L_0x00ed
        L_0x00e8:
            android.view.View r5 = r10.getChildAt(r15)
            goto L_0x00f1
        L_0x00ed:
            r5 = r9
            goto L_0x00f1
        L_0x00ef:
            r3 = r9
            r5 = r3
        L_0x00f1:
            if (r5 == 0) goto L_0x016d
            androidx.appcompat.widget.AppCompatPopupWindow r8 = r6.mPopup
            r8.setTouchModal(r7)
            androidx.appcompat.widget.AppCompatPopupWindow r8 = r6.mPopup
            r8.setEnterTransition(r9)
            java.util.ArrayList r8 = r0.mShowingMenus
            int r10 = r8.size()
            r11 = 1
            int r10 = r10 - r11
            java.lang.Object r8 = r8.get(r10)
            androidx.appcompat.view.menu.CascadingMenuPopup$CascadingMenuInfo r8 = (androidx.appcompat.view.menu.CascadingMenuPopup.CascadingMenuInfo) r8
            java.util.Objects.requireNonNull(r8)
            androidx.appcompat.widget.MenuPopupWindow r8 = r8.window
            java.util.Objects.requireNonNull(r8)
            androidx.appcompat.widget.DropDownListView r8 = r8.mDropDownList
            r10 = 2
            int[] r10 = new int[r10]
            r8.getLocationOnScreen(r10)
            android.graphics.Rect r11 = new android.graphics.Rect
            r11.<init>()
            android.view.View r12 = r0.mShownAnchorView
            r12.getWindowVisibleDisplayFrame(r11)
            int r12 = r0.mLastPosition
            r13 = 1
            if (r12 != r13) goto L_0x0137
            r10 = r10[r7]
            int r8 = r8.getWidth()
            int r8 = r8 + r10
            int r8 = r8 + r4
            int r10 = r11.right
            if (r8 <= r10) goto L_0x013c
            goto L_0x013f
        L_0x0137:
            r8 = r10[r7]
            int r8 = r8 - r4
            if (r8 >= 0) goto L_0x013f
        L_0x013c:
            r8 = 1
            r11 = 1
            goto L_0x0141
        L_0x013f:
            r11 = r7
            r8 = 1
        L_0x0141:
            if (r11 != r8) goto L_0x0145
            r8 = 1
            goto L_0x0146
        L_0x0145:
            r8 = r7
        L_0x0146:
            r0.mLastPosition = r11
            r6.mDropDownAnchorView = r5
            int r10 = r0.mDropDownGravity
            r11 = 5
            r10 = r10 & r11
            if (r10 != r11) goto L_0x0158
            if (r8 == 0) goto L_0x0153
            goto L_0x015e
        L_0x0153:
            int r4 = r5.getWidth()
            goto L_0x0160
        L_0x0158:
            if (r8 == 0) goto L_0x0160
            int r4 = r5.getWidth()
        L_0x015e:
            int r4 = r4 + r7
            goto L_0x0162
        L_0x0160:
            int r4 = 0 - r4
        L_0x0162:
            r6.mDropDownHorizontalOffset = r4
            r4 = 1
            r6.mOverlapAnchorSet = r4
            r6.mOverlapAnchor = r4
            r6.setVerticalOffset(r7)
            goto L_0x018b
        L_0x016d:
            boolean r4 = r0.mHasXOffset
            if (r4 == 0) goto L_0x0175
            int r4 = r0.mXOffset
            r6.mDropDownHorizontalOffset = r4
        L_0x0175:
            boolean r4 = r0.mHasYOffset
            if (r4 == 0) goto L_0x017e
            int r4 = r0.mYOffset
            r6.setVerticalOffset(r4)
        L_0x017e:
            android.graphics.Rect r4 = r0.mEpicenterBounds
            if (r4 == 0) goto L_0x0188
            android.graphics.Rect r5 = new android.graphics.Rect
            r5.<init>(r4)
            goto L_0x0189
        L_0x0188:
            r5 = r9
        L_0x0189:
            r6.mEpicenterBounds = r5
        L_0x018b:
            androidx.appcompat.view.menu.CascadingMenuPopup$CascadingMenuInfo r4 = new androidx.appcompat.view.menu.CascadingMenuPopup$CascadingMenuInfo
            int r5 = r0.mLastPosition
            r4.<init>(r6, r1, r5)
            java.util.ArrayList r5 = r0.mShowingMenus
            r5.add(r4)
            r6.show()
            androidx.appcompat.widget.DropDownListView r4 = r6.mDropDownList
            r4.setOnKeyListener(r0)
            if (r3 != 0) goto L_0x01cc
            boolean r0 = r0.mShowTitle
            if (r0 == 0) goto L_0x01cc
            java.util.Objects.requireNonNull(r17)
            java.lang.CharSequence r0 = r1.mHeaderTitle
            if (r0 == 0) goto L_0x01cc
            r0 = 2131623954(0x7f0e0012, float:1.8875074E38)
            android.view.View r0 = r2.inflate(r0, r4, r7)
            android.widget.FrameLayout r0 = (android.widget.FrameLayout) r0
            r2 = 16908310(0x1020016, float:2.387729E-38)
            android.view.View r2 = r0.findViewById(r2)
            android.widget.TextView r2 = (android.widget.TextView) r2
            r0.setEnabled(r7)
            java.lang.CharSequence r1 = r1.mHeaderTitle
            r2.setText(r1)
            r4.addHeaderView(r0, r9, r7)
            r6.show()
        L_0x01cc:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.view.menu.CascadingMenuPopup.showMenu(androidx.appcompat.view.menu.MenuBuilder):void");
    }

    public final void updateMenuView(boolean z) {
        Iterator it = this.mShowingMenus.iterator();
        while (it.hasNext()) {
            CascadingMenuInfo cascadingMenuInfo = (CascadingMenuInfo) it.next();
            Objects.requireNonNull(cascadingMenuInfo);
            MenuPopupWindow menuPopupWindow = cascadingMenuInfo.window;
            Objects.requireNonNull(menuPopupWindow);
            ListAdapter adapter = menuPopupWindow.mDropDownList.getAdapter();
            if (adapter instanceof HeaderViewListAdapter) {
                adapter = ((HeaderViewListAdapter) adapter).getWrappedAdapter();
            }
            ((MenuAdapter) adapter).notifyDataSetChanged();
        }
    }

    public CascadingMenuPopup(Context context, View view, int i, int i2, boolean z) {
        int i3 = 0;
        this.mRawDropDownGravity = 0;
        this.mDropDownGravity = 0;
        this.mContext = context;
        this.mAnchorView = view;
        this.mPopupStyleAttr = i;
        this.mPopupStyleRes = i2;
        this.mOverflowOnly = z;
        this.mForceShowIcon = false;
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        this.mLastPosition = ViewCompat.Api17Impl.getLayoutDirection(view) != 1 ? 1 : i3;
        Resources resources = context.getResources();
        this.mMenuMaxWidth = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(C1777R.dimen.abc_config_prefDialogWidth));
        this.mSubMenuHoverHandler = new Handler();
    }

    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1 || i != 82) {
            return false;
        }
        dismiss();
        return true;
    }

    public final void show() {
        boolean z;
        if (!isShowing()) {
            Iterator it = this.mPendingMenus.iterator();
            while (it.hasNext()) {
                showMenu((MenuBuilder) it.next());
            }
            this.mPendingMenus.clear();
            View view = this.mAnchorView;
            this.mShownAnchorView = view;
            if (view != null) {
                if (this.mTreeObserver == null) {
                    z = true;
                } else {
                    z = false;
                }
                ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
                this.mTreeObserver = viewTreeObserver;
                if (z) {
                    viewTreeObserver.addOnGlobalLayoutListener(this.mGlobalLayoutListener);
                }
                this.mShownAnchorView.addOnAttachStateChangeListener(this.mAttachStateChangeListener);
            }
        }
    }

    public final void setCallback(MenuPresenter.Callback callback) {
        this.mPresenterCallback = callback;
    }

    public final void setForceShowIcon(boolean z) {
        this.mForceShowIcon = z;
    }

    public final void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    public final void setShowTitle(boolean z) {
        this.mShowTitle = z;
    }
}
