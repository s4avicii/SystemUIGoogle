package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.core.content.ContextCompat;
import androidx.core.internal.view.SupportMenu;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class MenuBuilder implements SupportMenu {
    public static final int[] sCategoryToOrder = {1, 4, 5, 3, 2, 0};
    public ArrayList<MenuItemImpl> mActionItems;
    public Callback mCallback;
    public final Context mContext;
    public int mDefaultShowAsAction = 0;
    public MenuItemImpl mExpandedItem;
    public boolean mGroupDividerEnabled = false;
    public Drawable mHeaderIcon;
    public CharSequence mHeaderTitle;
    public View mHeaderView;
    public boolean mIsActionItemsStale;
    public boolean mIsClosing = false;
    public boolean mIsVisibleItemsStale;
    public ArrayList<MenuItemImpl> mItems;
    public boolean mItemsChangedWhileDispatchPrevented = false;
    public ArrayList<MenuItemImpl> mNonActionItems;
    public boolean mOverrideVisibleItems;
    public CopyOnWriteArrayList<WeakReference<MenuPresenter>> mPresenters = new CopyOnWriteArrayList<>();
    public boolean mPreventDispatchingItemsChanged = false;
    public boolean mQwertyMode;
    public final Resources mResources;
    public boolean mShortcutsVisible;
    public boolean mStructureChangedWhileDispatchPrevented = false;
    public ArrayList<MenuItemImpl> mTempShortcutItemList = new ArrayList<>();
    public ArrayList<MenuItemImpl> mVisibleItems;

    public interface Callback {
        boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem);

        void onMenuModeChange(MenuBuilder menuBuilder);
    }

    public interface ItemInvoker {
        boolean invokeItem(MenuItemImpl menuItemImpl);
    }

    public final MenuItem add(CharSequence charSequence) {
        return addInternal(0, 0, 0, charSequence);
    }

    public final SubMenu addSubMenu(CharSequence charSequence) {
        return addSubMenu(0, 0, 0, charSequence);
    }

    public final void clearHeader() {
        this.mHeaderIcon = null;
        this.mHeaderTitle = null;
        this.mHeaderView = null;
        onItemsChanged(false);
    }

    public final void close(boolean z) {
        if (!this.mIsClosing) {
            this.mIsClosing = true;
            Iterator<WeakReference<MenuPresenter>> it = this.mPresenters.iterator();
            while (it.hasNext()) {
                WeakReference next = it.next();
                MenuPresenter menuPresenter = (MenuPresenter) next.get();
                if (menuPresenter == null) {
                    this.mPresenters.remove(next);
                } else {
                    menuPresenter.onCloseMenu(this, z);
                }
            }
            this.mIsClosing = false;
        }
    }

    public String getActionViewStatesKey() {
        return "android:menu:actionviewstates";
    }

    public MenuBuilder getRootMenu() {
        return this;
    }

    public final void startDispatchingItemsChanged() {
        this.mPreventDispatchingItemsChanged = false;
        if (this.mItemsChangedWhileDispatchPrevented) {
            this.mItemsChangedWhileDispatchPrevented = false;
            onItemsChanged(this.mStructureChangedWhileDispatchPrevented);
        }
    }

    public final MenuItem add(int i) {
        return addInternal(0, 0, 0, this.mResources.getString(i));
    }

    /* JADX WARNING: type inference failed for: r15v0, types: [android.view.MenuItem[]] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int addIntentOptions(int r8, int r9, int r10, android.content.ComponentName r11, android.content.Intent[] r12, android.content.Intent r13, int r14, android.view.MenuItem[] r15) {
        /*
            r7 = this;
            android.content.Context r0 = r7.mContext
            android.content.pm.PackageManager r0 = r0.getPackageManager()
            r1 = 0
            java.util.List r11 = r0.queryIntentActivityOptions(r11, r12, r13, r1)
            if (r11 == 0) goto L_0x0012
            int r2 = r11.size()
            goto L_0x0013
        L_0x0012:
            r2 = r1
        L_0x0013:
            r14 = r14 & 1
            if (r14 != 0) goto L_0x001a
            r7.removeGroup(r8)
        L_0x001a:
            if (r1 >= r2) goto L_0x005b
            java.lang.Object r14 = r11.get(r1)
            android.content.pm.ResolveInfo r14 = (android.content.pm.ResolveInfo) r14
            android.content.Intent r3 = new android.content.Intent
            int r4 = r14.specificIndex
            if (r4 >= 0) goto L_0x002a
            r4 = r13
            goto L_0x002c
        L_0x002a:
            r4 = r12[r4]
        L_0x002c:
            r3.<init>(r4)
            android.content.ComponentName r4 = new android.content.ComponentName
            android.content.pm.ActivityInfo r5 = r14.activityInfo
            android.content.pm.ApplicationInfo r6 = r5.applicationInfo
            java.lang.String r6 = r6.packageName
            java.lang.String r5 = r5.name
            r4.<init>(r6, r5)
            r3.setComponent(r4)
            java.lang.CharSequence r4 = r14.loadLabel(r0)
            androidx.appcompat.view.menu.MenuItemImpl r4 = r7.addInternal(r8, r9, r10, r4)
            android.graphics.drawable.Drawable r5 = r14.loadIcon(r0)
            r4.setIcon((android.graphics.drawable.Drawable) r5)
            r4.mIntent = r3
            if (r15 == 0) goto L_0x0058
            int r14 = r14.specificIndex
            if (r14 < 0) goto L_0x0058
            r15[r14] = r4
        L_0x0058:
            int r1 = r1 + 1
            goto L_0x001a
        L_0x005b:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.view.menu.MenuBuilder.addIntentOptions(int, int, int, android.content.ComponentName, android.content.Intent[], android.content.Intent, int, android.view.MenuItem[]):int");
    }

    public MenuItemImpl addInternal(int i, int i2, int i3, CharSequence charSequence) {
        int i4;
        int i5 = (-65536 & i3) >> 16;
        if (i5 >= 0) {
            int[] iArr = sCategoryToOrder;
            if (i5 < 6) {
                int i6 = (iArr[i5] << 16) | (65535 & i3);
                MenuItemImpl menuItemImpl = new MenuItemImpl(this, i, i2, i3, i6, charSequence, this.mDefaultShowAsAction);
                ArrayList<MenuItemImpl> arrayList = this.mItems;
                int size = arrayList.size();
                while (true) {
                    size--;
                    if (size < 0) {
                        i4 = 0;
                        break;
                    }
                    MenuItemImpl menuItemImpl2 = arrayList.get(size);
                    Objects.requireNonNull(menuItemImpl2);
                    if (menuItemImpl2.mOrdering <= i6) {
                        i4 = size + 1;
                        break;
                    }
                }
                arrayList.add(i4, menuItemImpl);
                onItemsChanged(true);
                return menuItemImpl;
            }
        }
        throw new IllegalArgumentException("order does not contain a valid category.");
    }

    public final void addMenuPresenter(MenuPresenter menuPresenter, Context context) {
        this.mPresenters.add(new WeakReference(menuPresenter));
        menuPresenter.initForMenu(context, this);
        this.mIsActionItemsStale = true;
    }

    public final SubMenu addSubMenu(int i) {
        return addSubMenu(0, 0, 0, (CharSequence) this.mResources.getString(i));
    }

    public final void clear() {
        MenuItemImpl menuItemImpl = this.mExpandedItem;
        if (menuItemImpl != null) {
            collapseItemActionView(menuItemImpl);
        }
        this.mItems.clear();
        onItemsChanged(true);
    }

    public boolean collapseItemActionView(MenuItemImpl menuItemImpl) {
        boolean z = false;
        if (!this.mPresenters.isEmpty() && this.mExpandedItem == menuItemImpl) {
            stopDispatchingItemsChanged();
            Iterator<WeakReference<MenuPresenter>> it = this.mPresenters.iterator();
            while (it.hasNext()) {
                WeakReference next = it.next();
                MenuPresenter menuPresenter = (MenuPresenter) next.get();
                if (menuPresenter == null) {
                    this.mPresenters.remove(next);
                } else {
                    z = menuPresenter.collapseItemActionView(menuItemImpl);
                    if (z) {
                        break;
                    }
                }
            }
            startDispatchingItemsChanged();
            if (z) {
                this.mExpandedItem = null;
            }
        }
        return z;
    }

    public boolean dispatchMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        Callback callback = this.mCallback;
        if (callback == null || !callback.onMenuItemSelected(menuBuilder, menuItem)) {
            return false;
        }
        return true;
    }

    public boolean expandItemActionView(MenuItemImpl menuItemImpl) {
        boolean z = false;
        if (this.mPresenters.isEmpty()) {
            return false;
        }
        stopDispatchingItemsChanged();
        Iterator<WeakReference<MenuPresenter>> it = this.mPresenters.iterator();
        while (it.hasNext()) {
            WeakReference next = it.next();
            MenuPresenter menuPresenter = (MenuPresenter) next.get();
            if (menuPresenter == null) {
                this.mPresenters.remove(next);
            } else {
                z = menuPresenter.expandItemActionView(menuItemImpl);
                if (z) {
                    break;
                }
            }
        }
        startDispatchingItemsChanged();
        if (z) {
            this.mExpandedItem = menuItemImpl;
        }
        return z;
    }

    public final MenuItemImpl findItemWithShortcutForKey(int i, KeyEvent keyEvent) {
        char c;
        ArrayList<MenuItemImpl> arrayList = this.mTempShortcutItemList;
        arrayList.clear();
        findItemsWithShortcutForKey(arrayList, i, keyEvent);
        if (arrayList.isEmpty()) {
            return null;
        }
        int metaState = keyEvent.getMetaState();
        KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
        keyEvent.getKeyData(keyData);
        int size = arrayList.size();
        if (size == 1) {
            return arrayList.get(0);
        }
        boolean isQwertyMode = isQwertyMode();
        for (int i2 = 0; i2 < size; i2++) {
            MenuItemImpl menuItemImpl = arrayList.get(i2);
            if (isQwertyMode) {
                Objects.requireNonNull(menuItemImpl);
                c = menuItemImpl.mShortcutAlphabeticChar;
            } else {
                Objects.requireNonNull(menuItemImpl);
                c = menuItemImpl.mShortcutNumericChar;
            }
            char[] cArr = keyData.meta;
            if ((c == cArr[0] && (metaState & 2) == 0) || ((c == cArr[2] && (metaState & 2) != 0) || (isQwertyMode && c == 8 && i == 67))) {
                return menuItemImpl;
            }
        }
        return null;
    }

    public final MenuItem getItem(int i) {
        return this.mItems.get(i);
    }

    public final ArrayList<MenuItemImpl> getVisibleItems() {
        if (!this.mIsVisibleItemsStale) {
            return this.mVisibleItems;
        }
        this.mVisibleItems.clear();
        int size = this.mItems.size();
        for (int i = 0; i < size; i++) {
            MenuItemImpl menuItemImpl = this.mItems.get(i);
            if (menuItemImpl.isVisible()) {
                this.mVisibleItems.add(menuItemImpl);
            }
        }
        this.mIsVisibleItemsStale = false;
        this.mIsActionItemsStale = true;
        return this.mVisibleItems;
    }

    public final boolean hasVisibleItems() {
        if (this.mOverrideVisibleItems) {
            return true;
        }
        int size = size();
        for (int i = 0; i < size; i++) {
            if (this.mItems.get(i).isVisible()) {
                return true;
            }
        }
        return false;
    }

    public void onItemsChanged(boolean z) {
        if (!this.mPreventDispatchingItemsChanged) {
            if (z) {
                this.mIsVisibleItemsStale = true;
                this.mIsActionItemsStale = true;
            }
            if (!this.mPresenters.isEmpty()) {
                stopDispatchingItemsChanged();
                Iterator<WeakReference<MenuPresenter>> it = this.mPresenters.iterator();
                while (it.hasNext()) {
                    WeakReference next = it.next();
                    MenuPresenter menuPresenter = (MenuPresenter) next.get();
                    if (menuPresenter == null) {
                        this.mPresenters.remove(next);
                    } else {
                        menuPresenter.updateMenuView(z);
                    }
                }
                startDispatchingItemsChanged();
                return;
            }
            return;
        }
        this.mItemsChangedWhileDispatchPrevented = true;
        if (z) {
            this.mStructureChangedWhileDispatchPrevented = true;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0067  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean performItemAction(android.view.MenuItem r7, androidx.appcompat.view.menu.MenuPresenter r8, int r9) {
        /*
            r6 = this;
            androidx.appcompat.view.menu.MenuItemImpl r7 = (androidx.appcompat.view.menu.MenuItemImpl) r7
            r0 = 0
            if (r7 == 0) goto L_0x00d7
            boolean r1 = r7.isEnabled()
            if (r1 != 0) goto L_0x000d
            goto L_0x00d7
        L_0x000d:
            android.view.MenuItem$OnMenuItemClickListener r1 = r7.mClickListener
            r2 = 1
            if (r1 == 0) goto L_0x0019
            boolean r1 = r1.onMenuItemClick(r7)
            if (r1 == 0) goto L_0x0019
            goto L_0x0045
        L_0x0019:
            androidx.appcompat.view.menu.MenuBuilder r1 = r7.mMenu
            boolean r1 = r1.dispatchMenuItemSelected(r1, r7)
            if (r1 == 0) goto L_0x0022
            goto L_0x0045
        L_0x0022:
            android.content.Intent r1 = r7.mIntent
            if (r1 == 0) goto L_0x003b
            androidx.appcompat.view.menu.MenuBuilder r1 = r7.mMenu     // Catch:{ ActivityNotFoundException -> 0x0033 }
            java.util.Objects.requireNonNull(r1)     // Catch:{ ActivityNotFoundException -> 0x0033 }
            android.content.Context r1 = r1.mContext     // Catch:{ ActivityNotFoundException -> 0x0033 }
            android.content.Intent r3 = r7.mIntent     // Catch:{ ActivityNotFoundException -> 0x0033 }
            r1.startActivity(r3)     // Catch:{ ActivityNotFoundException -> 0x0033 }
            goto L_0x0045
        L_0x0033:
            r1 = move-exception
            java.lang.String r3 = "MenuItemImpl"
            java.lang.String r4 = "Can't find activity to handle intent; ignoring"
            android.util.Log.e(r3, r4, r1)
        L_0x003b:
            androidx.core.view.ActionProvider r1 = r7.mActionProvider
            if (r1 == 0) goto L_0x0047
            boolean r1 = r1.onPerformDefaultAction()
            if (r1 == 0) goto L_0x0047
        L_0x0045:
            r1 = r2
            goto L_0x0048
        L_0x0047:
            r1 = r0
        L_0x0048:
            androidx.core.view.ActionProvider r3 = r7.mActionProvider
            if (r3 == 0) goto L_0x0054
            boolean r4 = r3.hasSubMenu()
            if (r4 == 0) goto L_0x0054
            r4 = r2
            goto L_0x0055
        L_0x0054:
            r4 = r0
        L_0x0055:
            boolean r5 = r7.hasCollapsibleActionView()
            if (r5 == 0) goto L_0x0067
            boolean r7 = r7.expandActionView()
            r1 = r1 | r7
            if (r1 == 0) goto L_0x00d6
            r6.close(r2)
            goto L_0x00d6
        L_0x0067:
            boolean r5 = r7.hasSubMenu()
            if (r5 != 0) goto L_0x0078
            if (r4 == 0) goto L_0x0070
            goto L_0x0078
        L_0x0070:
            r7 = r9 & 1
            if (r7 != 0) goto L_0x00d6
            r6.close(r2)
            goto L_0x00d6
        L_0x0078:
            r9 = r9 & 4
            if (r9 != 0) goto L_0x007f
            r6.close(r0)
        L_0x007f:
            boolean r9 = r7.hasSubMenu()
            if (r9 != 0) goto L_0x0093
            androidx.appcompat.view.menu.SubMenuBuilder r9 = new androidx.appcompat.view.menu.SubMenuBuilder
            android.content.Context r5 = r6.mContext
            r9.<init>(r5, r6, r7)
            r7.mSubMenu = r9
            java.lang.CharSequence r5 = r7.mTitle
            r9.setHeaderTitle((java.lang.CharSequence) r5)
        L_0x0093:
            androidx.appcompat.view.menu.SubMenuBuilder r7 = r7.mSubMenu
            if (r4 == 0) goto L_0x009a
            r3.onPrepareSubMenu(r7)
        L_0x009a:
            java.util.concurrent.CopyOnWriteArrayList<java.lang.ref.WeakReference<androidx.appcompat.view.menu.MenuPresenter>> r9 = r6.mPresenters
            boolean r9 = r9.isEmpty()
            if (r9 == 0) goto L_0x00a3
            goto L_0x00d0
        L_0x00a3:
            if (r8 == 0) goto L_0x00a9
            boolean r0 = r8.onSubMenuSelected(r7)
        L_0x00a9:
            java.util.concurrent.CopyOnWriteArrayList<java.lang.ref.WeakReference<androidx.appcompat.view.menu.MenuPresenter>> r8 = r6.mPresenters
            java.util.Iterator r8 = r8.iterator()
        L_0x00af:
            boolean r9 = r8.hasNext()
            if (r9 == 0) goto L_0x00d0
            java.lang.Object r9 = r8.next()
            java.lang.ref.WeakReference r9 = (java.lang.ref.WeakReference) r9
            java.lang.Object r3 = r9.get()
            androidx.appcompat.view.menu.MenuPresenter r3 = (androidx.appcompat.view.menu.MenuPresenter) r3
            if (r3 != 0) goto L_0x00c9
            java.util.concurrent.CopyOnWriteArrayList<java.lang.ref.WeakReference<androidx.appcompat.view.menu.MenuPresenter>> r3 = r6.mPresenters
            r3.remove(r9)
            goto L_0x00af
        L_0x00c9:
            if (r0 != 0) goto L_0x00af
            boolean r0 = r3.onSubMenuSelected(r7)
            goto L_0x00af
        L_0x00d0:
            r1 = r1 | r0
            if (r1 != 0) goto L_0x00d6
            r6.close(r2)
        L_0x00d6:
            return r1
        L_0x00d7:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.view.menu.MenuBuilder.performItemAction(android.view.MenuItem, androidx.appcompat.view.menu.MenuPresenter, int):boolean");
    }

    public final void removeMenuPresenter(MenuPresenter menuPresenter) {
        Iterator<WeakReference<MenuPresenter>> it = this.mPresenters.iterator();
        while (it.hasNext()) {
            WeakReference next = it.next();
            MenuPresenter menuPresenter2 = (MenuPresenter) next.get();
            if (menuPresenter2 == null || menuPresenter2 == menuPresenter) {
                this.mPresenters.remove(next);
            }
        }
    }

    public final void restoreActionViewStates(Bundle bundle) {
        MenuItem findItem;
        if (bundle != null) {
            SparseArray sparseParcelableArray = bundle.getSparseParcelableArray(getActionViewStatesKey());
            int size = size();
            for (int i = 0; i < size; i++) {
                MenuItem item = getItem(i);
                View actionView = item.getActionView();
                if (!(actionView == null || actionView.getId() == -1)) {
                    actionView.restoreHierarchyState(sparseParcelableArray);
                }
                if (item.hasSubMenu()) {
                    ((SubMenuBuilder) item.getSubMenu()).restoreActionViewStates(bundle);
                }
            }
            int i2 = bundle.getInt("android:menu:expandedactionview");
            if (i2 > 0 && (findItem = findItem(i2)) != null) {
                findItem.expandActionView();
            }
        }
    }

    public final void restorePresenterStates(Bundle bundle) {
        Parcelable parcelable;
        SparseArray sparseParcelableArray = bundle.getSparseParcelableArray("android:menu:presenters");
        if (sparseParcelableArray != null && !this.mPresenters.isEmpty()) {
            Iterator<WeakReference<MenuPresenter>> it = this.mPresenters.iterator();
            while (it.hasNext()) {
                WeakReference next = it.next();
                MenuPresenter menuPresenter = (MenuPresenter) next.get();
                if (menuPresenter == null) {
                    this.mPresenters.remove(next);
                } else {
                    int id = menuPresenter.getId();
                    if (id > 0 && (parcelable = (Parcelable) sparseParcelableArray.get(id)) != null) {
                        menuPresenter.onRestoreInstanceState(parcelable);
                    }
                }
            }
        }
    }

    public final void savePresenterStates(Bundle bundle) {
        Parcelable onSaveInstanceState;
        if (!this.mPresenters.isEmpty()) {
            SparseArray sparseArray = new SparseArray();
            Iterator<WeakReference<MenuPresenter>> it = this.mPresenters.iterator();
            while (it.hasNext()) {
                WeakReference next = it.next();
                MenuPresenter menuPresenter = (MenuPresenter) next.get();
                if (menuPresenter == null) {
                    this.mPresenters.remove(next);
                } else {
                    int id = menuPresenter.getId();
                    if (id > 0 && (onSaveInstanceState = menuPresenter.onSaveInstanceState()) != null) {
                        sparseArray.put(id, onSaveInstanceState);
                    }
                }
            }
            bundle.putSparseParcelableArray("android:menu:presenters", sparseArray);
        }
    }

    public final void setGroupCheckable(int i, boolean z, boolean z2) {
        int size = this.mItems.size();
        for (int i2 = 0; i2 < size; i2++) {
            MenuItemImpl menuItemImpl = this.mItems.get(i2);
            Objects.requireNonNull(menuItemImpl);
            if (menuItemImpl.mGroup == i) {
                menuItemImpl.setExclusiveCheckable(z2);
                menuItemImpl.setCheckable(z);
            }
        }
    }

    public final void setGroupEnabled(int i, boolean z) {
        int size = this.mItems.size();
        for (int i2 = 0; i2 < size; i2++) {
            MenuItemImpl menuItemImpl = this.mItems.get(i2);
            Objects.requireNonNull(menuItemImpl);
            if (menuItemImpl.mGroup == i) {
                menuItemImpl.setEnabled(z);
            }
        }
    }

    public final void setGroupVisible(int i, boolean z) {
        int i2;
        boolean z2;
        int size = this.mItems.size();
        boolean z3 = false;
        for (int i3 = 0; i3 < size; i3++) {
            MenuItemImpl menuItemImpl = this.mItems.get(i3);
            Objects.requireNonNull(menuItemImpl);
            if (menuItemImpl.mGroup == i) {
                int i4 = menuItemImpl.mFlags;
                int i5 = i4 & -9;
                if (z) {
                    i2 = 0;
                } else {
                    i2 = 8;
                }
                int i6 = i5 | i2;
                menuItemImpl.mFlags = i6;
                if (i4 != i6) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (z2) {
                    z3 = true;
                }
            }
        }
        if (z3) {
            onItemsChanged(true);
        }
    }

    public final void setHeaderInternal(int i, CharSequence charSequence, int i2, Drawable drawable, View view) {
        Resources resources = this.mResources;
        if (view != null) {
            this.mHeaderView = view;
            this.mHeaderTitle = null;
            this.mHeaderIcon = null;
        } else {
            if (i > 0) {
                this.mHeaderTitle = resources.getText(i);
            } else if (charSequence != null) {
                this.mHeaderTitle = charSequence;
            }
            if (i2 > 0) {
                Context context = this.mContext;
                Object obj = ContextCompat.sLock;
                this.mHeaderIcon = context.getDrawable(i2);
            } else if (drawable != null) {
                this.mHeaderIcon = drawable;
            }
            this.mHeaderView = null;
        }
        onItemsChanged(false);
    }

    public void setQwertyMode(boolean z) {
        this.mQwertyMode = z;
        onItemsChanged(false);
    }

    public final int size() {
        return this.mItems.size();
    }

    public final void stopDispatchingItemsChanged() {
        if (!this.mPreventDispatchingItemsChanged) {
            this.mPreventDispatchingItemsChanged = true;
            this.mItemsChangedWhileDispatchPrevented = false;
            this.mStructureChangedWhileDispatchPrevented = false;
        }
    }

    public MenuBuilder(Context context) {
        boolean z = false;
        this.mContext = context;
        Resources resources = context.getResources();
        this.mResources = resources;
        this.mItems = new ArrayList<>();
        this.mVisibleItems = new ArrayList<>();
        this.mIsVisibleItemsStale = true;
        this.mActionItems = new ArrayList<>();
        this.mNonActionItems = new ArrayList<>();
        this.mIsActionItemsStale = true;
        if (resources.getConfiguration().keyboard != 1 && ViewConfiguration.get(context).shouldShowMenuShortcutsWhenKeyboardPresent()) {
            z = true;
        }
        this.mShortcutsVisible = z;
    }

    public final MenuItem add(int i, int i2, int i3, CharSequence charSequence) {
        return addInternal(i, i2, i3, charSequence);
    }

    public SubMenu addSubMenu(int i, int i2, int i3, CharSequence charSequence) {
        MenuItemImpl addInternal = addInternal(i, i2, i3, charSequence);
        SubMenuBuilder subMenuBuilder = new SubMenuBuilder(this.mContext, this, addInternal);
        Objects.requireNonNull(addInternal);
        addInternal.mSubMenu = subMenuBuilder;
        subMenuBuilder.setHeaderTitle(addInternal.mTitle);
        return subMenuBuilder;
    }

    public final MenuItem findItem(int i) {
        MenuItem findItem;
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            MenuItemImpl menuItemImpl = this.mItems.get(i2);
            Objects.requireNonNull(menuItemImpl);
            if (menuItemImpl.mId == i) {
                return menuItemImpl;
            }
            if (menuItemImpl.hasSubMenu() && (findItem = menuItemImpl.mSubMenu.findItem(i)) != null) {
                return findItem;
            }
        }
        return null;
    }

    public final void findItemsWithShortcutForKey(ArrayList arrayList, int i, KeyEvent keyEvent) {
        char c;
        int i2;
        boolean z;
        boolean isQwertyMode = isQwertyMode();
        int modifiers = keyEvent.getModifiers();
        KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
        if (keyEvent.getKeyData(keyData) || i == 67) {
            int size = this.mItems.size();
            for (int i3 = 0; i3 < size; i3++) {
                MenuItemImpl menuItemImpl = this.mItems.get(i3);
                if (menuItemImpl.hasSubMenu()) {
                    menuItemImpl.mSubMenu.findItemsWithShortcutForKey(arrayList, i, keyEvent);
                }
                if (isQwertyMode) {
                    c = menuItemImpl.mShortcutAlphabeticChar;
                } else {
                    c = menuItemImpl.mShortcutNumericChar;
                }
                if (isQwertyMode) {
                    i2 = menuItemImpl.mShortcutAlphabeticModifiers;
                } else {
                    i2 = menuItemImpl.mShortcutNumericModifiers;
                }
                if ((modifiers & 69647) == (i2 & 69647)) {
                    z = true;
                } else {
                    z = false;
                }
                if (z && c != 0) {
                    char[] cArr = keyData.meta;
                    if ((c == cArr[0] || c == cArr[2] || (isQwertyMode && c == 8 && i == 67)) && menuItemImpl.isEnabled()) {
                        arrayList.add(menuItemImpl);
                    }
                }
            }
        }
    }

    public final void flagActionItems() {
        boolean z;
        ArrayList<MenuItemImpl> visibleItems = getVisibleItems();
        if (this.mIsActionItemsStale) {
            Iterator<WeakReference<MenuPresenter>> it = this.mPresenters.iterator();
            boolean z2 = false;
            while (it.hasNext()) {
                WeakReference next = it.next();
                MenuPresenter menuPresenter = (MenuPresenter) next.get();
                if (menuPresenter == null) {
                    this.mPresenters.remove(next);
                } else {
                    z2 |= menuPresenter.flagActionItems();
                }
            }
            if (z2) {
                this.mActionItems.clear();
                this.mNonActionItems.clear();
                int size = visibleItems.size();
                for (int i = 0; i < size; i++) {
                    MenuItemImpl menuItemImpl = visibleItems.get(i);
                    Objects.requireNonNull(menuItemImpl);
                    if ((menuItemImpl.mFlags & 32) == 32) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (z) {
                        this.mActionItems.add(menuItemImpl);
                    } else {
                        this.mNonActionItems.add(menuItemImpl);
                    }
                }
            } else {
                this.mActionItems.clear();
                this.mNonActionItems.clear();
                this.mNonActionItems.addAll(getVisibleItems());
            }
            this.mIsActionItemsStale = false;
        }
    }

    public final boolean isShortcutKey(int i, KeyEvent keyEvent) {
        if (findItemWithShortcutForKey(i, keyEvent) != null) {
            return true;
        }
        return false;
    }

    public final boolean performIdentifierAction(int i, int i2) {
        return performItemAction(findItem(i), (MenuPresenter) null, i2);
    }

    public final boolean performShortcut(int i, KeyEvent keyEvent, int i2) {
        boolean z;
        MenuItemImpl findItemWithShortcutForKey = findItemWithShortcutForKey(i, keyEvent);
        if (findItemWithShortcutForKey != null) {
            z = performItemAction(findItemWithShortcutForKey, (MenuPresenter) null, i2);
        } else {
            z = false;
        }
        if ((i2 & 2) != 0) {
            close(true);
        }
        return z;
    }

    public final void removeGroup(int i) {
        int size = size();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i3 >= size) {
                i3 = -1;
                break;
            }
            MenuItemImpl menuItemImpl = this.mItems.get(i3);
            Objects.requireNonNull(menuItemImpl);
            if (menuItemImpl.mGroup == i) {
                break;
            }
            i3++;
        }
        if (i3 >= 0) {
            int size2 = this.mItems.size() - i3;
            while (true) {
                int i4 = i2 + 1;
                if (i2 >= size2) {
                    break;
                }
                MenuItemImpl menuItemImpl2 = this.mItems.get(i3);
                Objects.requireNonNull(menuItemImpl2);
                if (menuItemImpl2.mGroup != i) {
                    break;
                }
                if (i3 >= 0 && i3 < this.mItems.size()) {
                    this.mItems.remove(i3);
                }
                i2 = i4;
            }
            onItemsChanged(true);
        }
    }

    public final void removeItem(int i) {
        int size = size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                i2 = -1;
                break;
            }
            MenuItemImpl menuItemImpl = this.mItems.get(i2);
            Objects.requireNonNull(menuItemImpl);
            if (menuItemImpl.mId == i) {
                break;
            }
            i2++;
        }
        if (i2 >= 0 && i2 < this.mItems.size()) {
            this.mItems.remove(i2);
            onItemsChanged(true);
        }
    }

    public final void saveActionViewStates(Bundle bundle) {
        int size = size();
        SparseArray sparseArray = null;
        for (int i = 0; i < size; i++) {
            MenuItem item = getItem(i);
            View actionView = item.getActionView();
            if (!(actionView == null || actionView.getId() == -1)) {
                if (sparseArray == null) {
                    sparseArray = new SparseArray();
                }
                actionView.saveHierarchyState(sparseArray);
                if (item.isActionViewExpanded()) {
                    bundle.putInt("android:menu:expandedactionview", item.getItemId());
                }
            }
            if (item.hasSubMenu()) {
                ((SubMenuBuilder) item.getSubMenu()).saveActionViewStates(bundle);
            }
        }
        if (sparseArray != null) {
            bundle.putSparseParcelableArray(getActionViewStatesKey(), sparseArray);
        }
    }

    public final MenuItem add(int i, int i2, int i3, int i4) {
        return addInternal(i, i2, i3, this.mResources.getString(i4));
    }

    public final void close() {
        close(true);
    }

    public final SubMenu addSubMenu(int i, int i2, int i3, int i4) {
        return addSubMenu(i, i2, i3, (CharSequence) this.mResources.getString(i4));
    }

    public void setGroupDividerEnabled(boolean z) {
        this.mGroupDividerEnabled = z;
    }

    public boolean isGroupDividerEnabled() {
        return this.mGroupDividerEnabled;
    }

    public boolean isQwertyMode() {
        return this.mQwertyMode;
    }

    public boolean isShortcutsVisible() {
        return this.mShortcutsVisible;
    }
}
