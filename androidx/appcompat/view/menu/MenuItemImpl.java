package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewDebug;
import android.widget.LinearLayout;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.internal.view.SupportMenuItem;
import androidx.core.view.ActionProvider;
import java.util.Objects;

public final class MenuItemImpl implements SupportMenuItem {
    public ActionProvider mActionProvider;
    public View mActionView;
    public final int mCategoryOrder;
    public MenuItem.OnMenuItemClickListener mClickListener;
    public CharSequence mContentDescription;
    public int mFlags = 16;
    public final int mGroup;
    public boolean mHasIconTint = false;
    public boolean mHasIconTintMode = false;
    public Drawable mIconDrawable;
    public int mIconResId = 0;
    public ColorStateList mIconTintList = null;
    public PorterDuff.Mode mIconTintMode = null;
    public final int mId;
    public Intent mIntent;
    public boolean mIsActionViewExpanded = false;
    public MenuBuilder mMenu;
    public boolean mNeedToApplyIconTint = false;
    public MenuItem.OnActionExpandListener mOnActionExpandListener;
    public final int mOrdering;
    public char mShortcutAlphabeticChar;
    public int mShortcutAlphabeticModifiers = 4096;
    public char mShortcutNumericChar;
    public int mShortcutNumericModifiers = 4096;
    public int mShowAsAction;
    public SubMenuBuilder mSubMenu;
    public CharSequence mTitle;
    public CharSequence mTitleCondensed;
    public CharSequence mTooltipText;

    public static void appendModifier(StringBuilder sb, int i, int i2, String str) {
        if ((i & i2) == i2) {
            sb.append(str);
        }
    }

    public final ContextMenu.ContextMenuInfo getMenuInfo() {
        return null;
    }

    public final MenuItem setActionView(View view) {
        int i;
        this.mActionView = view;
        this.mActionProvider = null;
        if (view != null && view.getId() == -1 && (i = this.mId) > 0) {
            view.setId(i);
        }
        MenuBuilder menuBuilder = this.mMenu;
        Objects.requireNonNull(menuBuilder);
        menuBuilder.mIsActionItemsStale = true;
        menuBuilder.onItemsChanged(true);
        return this;
    }

    public final MenuItem setAlphabeticShortcut(char c) {
        if (this.mShortcutAlphabeticChar == c) {
            return this;
        }
        this.mShortcutAlphabeticChar = Character.toLowerCase(c);
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public final MenuItem setIcon(Drawable drawable) {
        this.mIconResId = 0;
        this.mIconDrawable = drawable;
        this.mNeedToApplyIconTint = true;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public final MenuItem setNumericShortcut(char c) {
        if (this.mShortcutNumericChar == c) {
            return this;
        }
        this.mShortcutNumericChar = c;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public final MenuItem setShortcut(char c, char c2) {
        this.mShortcutNumericChar = c;
        this.mShortcutAlphabeticChar = Character.toLowerCase(c2);
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public final MenuItem setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        this.mMenu.onItemsChanged(false);
        SubMenuBuilder subMenuBuilder = this.mSubMenu;
        if (subMenuBuilder != null) {
            subMenuBuilder.setHeaderTitle(charSequence);
        }
        return this;
    }

    public final Drawable applyIconTintIfNecessary(Drawable drawable) {
        if (drawable != null && this.mNeedToApplyIconTint && (this.mHasIconTint || this.mHasIconTintMode)) {
            drawable = drawable.mutate();
            if (this.mHasIconTint) {
                drawable.setTintList(this.mIconTintList);
            }
            if (this.mHasIconTintMode) {
                drawable.setTintMode(this.mIconTintMode);
            }
            this.mNeedToApplyIconTint = false;
        }
        return drawable;
    }

    public final boolean collapseActionView() {
        if ((this.mShowAsAction & 8) == 0) {
            return false;
        }
        if (this.mActionView == null) {
            return true;
        }
        MenuItem.OnActionExpandListener onActionExpandListener = this.mOnActionExpandListener;
        if (onActionExpandListener == null || onActionExpandListener.onMenuItemActionCollapse(this)) {
            return this.mMenu.collapseItemActionView(this);
        }
        return false;
    }

    public final android.view.ActionProvider getActionProvider() {
        throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.getActionProvider()");
    }

    public final View getActionView() {
        View view = this.mActionView;
        if (view != null) {
            return view;
        }
        ActionProvider actionProvider = this.mActionProvider;
        if (actionProvider == null) {
            return null;
        }
        View onCreateActionView = actionProvider.onCreateActionView(this);
        this.mActionView = onCreateActionView;
        return onCreateActionView;
    }

    public final Drawable getIcon() {
        Drawable drawable = this.mIconDrawable;
        if (drawable != null) {
            return applyIconTintIfNecessary(drawable);
        }
        if (this.mIconResId == 0) {
            return null;
        }
        MenuBuilder menuBuilder = this.mMenu;
        Objects.requireNonNull(menuBuilder);
        Drawable drawable2 = AppCompatResources.getDrawable(menuBuilder.mContext, this.mIconResId);
        this.mIconResId = 0;
        this.mIconDrawable = drawable2;
        return applyIconTintIfNecessary(drawable2);
    }

    public final CharSequence getTitleCondensed() {
        CharSequence charSequence = this.mTitleCondensed;
        if (charSequence != null) {
            return charSequence;
        }
        return this.mTitle;
    }

    public final boolean hasCollapsibleActionView() {
        ActionProvider actionProvider;
        if ((this.mShowAsAction & 8) == 0) {
            return false;
        }
        if (this.mActionView == null && (actionProvider = this.mActionProvider) != null) {
            this.mActionView = actionProvider.onCreateActionView(this);
        }
        if (this.mActionView != null) {
            return true;
        }
        return false;
    }

    public final boolean hasSubMenu() {
        if (this.mSubMenu != null) {
            return true;
        }
        return false;
    }

    public final boolean isCheckable() {
        if ((this.mFlags & 1) == 1) {
            return true;
        }
        return false;
    }

    public final boolean isChecked() {
        if ((this.mFlags & 2) == 2) {
            return true;
        }
        return false;
    }

    public final boolean isEnabled() {
        if ((this.mFlags & 16) != 0) {
            return true;
        }
        return false;
    }

    public final boolean isVisible() {
        ActionProvider actionProvider = this.mActionProvider;
        if (actionProvider == null || !actionProvider.overridesItemVisibility()) {
            if ((this.mFlags & 8) == 0) {
                return true;
            }
            return false;
        } else if ((this.mFlags & 8) != 0 || !this.mActionProvider.isVisible()) {
            return false;
        } else {
            return true;
        }
    }

    public final boolean requiresActionButton() {
        if ((this.mShowAsAction & 2) == 2) {
            return true;
        }
        return false;
    }

    public final MenuItem setActionProvider(android.view.ActionProvider actionProvider) {
        throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.setActionProvider()");
    }

    public final MenuItem setCheckable(boolean z) {
        int i = this.mFlags;
        boolean z2 = z | (i & true);
        this.mFlags = z2 ? 1 : 0;
        if (i != z2) {
            this.mMenu.onItemsChanged(false);
        }
        return this;
    }

    public final MenuItem setChecked(boolean z) {
        boolean z2;
        int i;
        int i2 = this.mFlags;
        int i3 = 2;
        if ((i2 & 4) != 0) {
            MenuBuilder menuBuilder = this.mMenu;
            Objects.requireNonNull(menuBuilder);
            int i4 = this.mGroup;
            int size = menuBuilder.mItems.size();
            menuBuilder.stopDispatchingItemsChanged();
            for (int i5 = 0; i5 < size; i5++) {
                MenuItemImpl menuItemImpl = menuBuilder.mItems.get(i5);
                Objects.requireNonNull(menuItemImpl);
                if (menuItemImpl.mGroup == i4) {
                    boolean z3 = true;
                    if ((menuItemImpl.mFlags & 4) != 0) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (z2 && menuItemImpl.isCheckable()) {
                        if (menuItemImpl != this) {
                            z3 = false;
                        }
                        int i6 = menuItemImpl.mFlags;
                        int i7 = i6 & -3;
                        if (z3) {
                            i = 2;
                        } else {
                            i = 0;
                        }
                        int i8 = i | i7;
                        menuItemImpl.mFlags = i8;
                        if (i6 != i8) {
                            menuItemImpl.mMenu.onItemsChanged(false);
                        }
                    }
                }
            }
            menuBuilder.startDispatchingItemsChanged();
        } else {
            int i9 = i2 & -3;
            if (!z) {
                i3 = 0;
            }
            int i10 = i3 | i9;
            this.mFlags = i10;
            if (i2 != i10) {
                this.mMenu.onItemsChanged(false);
            }
        }
        return this;
    }

    public final SupportMenuItem setContentDescription(CharSequence charSequence) {
        this.mContentDescription = charSequence;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public final MenuItem setEnabled(boolean z) {
        if (z) {
            this.mFlags |= 16;
        } else {
            this.mFlags &= -17;
        }
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public final void setExclusiveCheckable(boolean z) {
        int i;
        int i2 = this.mFlags & -5;
        if (z) {
            i = 4;
        } else {
            i = 0;
        }
        this.mFlags = i | i2;
    }

    public final MenuItem setIconTintList(ColorStateList colorStateList) {
        this.mIconTintList = colorStateList;
        this.mHasIconTint = true;
        this.mNeedToApplyIconTint = true;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public final MenuItem setIconTintMode(PorterDuff.Mode mode) {
        this.mIconTintMode = mode;
        this.mHasIconTintMode = true;
        this.mNeedToApplyIconTint = true;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public final void setIsActionButton(boolean z) {
        if (z) {
            this.mFlags |= 32;
        } else {
            this.mFlags &= -33;
        }
    }

    public final void setShowAsAction(int i) {
        int i2 = i & 3;
        if (i2 == 0 || i2 == 1 || i2 == 2) {
            this.mShowAsAction = i;
            MenuBuilder menuBuilder = this.mMenu;
            Objects.requireNonNull(menuBuilder);
            menuBuilder.mIsActionItemsStale = true;
            menuBuilder.onItemsChanged(true);
            return;
        }
        throw new IllegalArgumentException("SHOW_AS_ACTION_ALWAYS, SHOW_AS_ACTION_IF_ROOM, and SHOW_AS_ACTION_NEVER are mutually exclusive.");
    }

    public final SupportMenuItem setSupportActionProvider(ActionProvider actionProvider) {
        ActionProvider actionProvider2 = this.mActionProvider;
        if (actionProvider2 != null) {
            actionProvider2.mVisibilityListener = null;
            actionProvider2.mSubUiVisibilityListener = null;
        }
        this.mActionView = null;
        this.mActionProvider = actionProvider;
        this.mMenu.onItemsChanged(true);
        ActionProvider actionProvider3 = this.mActionProvider;
        if (actionProvider3 != null) {
            actionProvider3.setVisibilityListener(new ActionProvider.VisibilityListener() {
            });
        }
        return this;
    }

    public final MenuItem setTitleCondensed(CharSequence charSequence) {
        this.mTitleCondensed = charSequence;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public final SupportMenuItem setTooltipText(CharSequence charSequence) {
        this.mTooltipText = charSequence;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public final MenuItem setVisible(boolean z) {
        int i;
        int i2 = this.mFlags;
        int i3 = i2 & -9;
        boolean z2 = false;
        if (z) {
            i = 0;
        } else {
            i = 8;
        }
        int i4 = i | i3;
        this.mFlags = i4;
        if (i2 != i4) {
            z2 = true;
        }
        if (z2) {
            MenuBuilder menuBuilder = this.mMenu;
            Objects.requireNonNull(menuBuilder);
            menuBuilder.mIsVisibleItemsStale = true;
            menuBuilder.onItemsChanged(true);
        }
        return this;
    }

    public final String toString() {
        CharSequence charSequence = this.mTitle;
        if (charSequence != null) {
            return charSequence.toString();
        }
        return null;
    }

    public MenuItemImpl(MenuBuilder menuBuilder, int i, int i2, int i3, int i4, CharSequence charSequence, int i5) {
        this.mMenu = menuBuilder;
        this.mId = i2;
        this.mGroup = i;
        this.mCategoryOrder = i3;
        this.mOrdering = i4;
        this.mTitle = charSequence;
        this.mShowAsAction = i5;
    }

    public final boolean expandActionView() {
        if (!hasCollapsibleActionView()) {
            return false;
        }
        MenuItem.OnActionExpandListener onActionExpandListener = this.mOnActionExpandListener;
        if (onActionExpandListener == null || onActionExpandListener.onMenuItemActionExpand(this)) {
            return this.mMenu.expandItemActionView(this);
        }
        return false;
    }

    public final boolean requiresOverflow() {
        boolean z;
        if (!requiresActionButton()) {
            if ((this.mShowAsAction & 1) == 1) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                return true;
            }
        }
        return false;
    }

    public final MenuItem setAlphabeticShortcut(char c, int i) {
        if (this.mShortcutAlphabeticChar == c && this.mShortcutAlphabeticModifiers == i) {
            return this;
        }
        this.mShortcutAlphabeticChar = Character.toLowerCase(c);
        this.mShortcutAlphabeticModifiers = KeyEvent.normalizeMetaState(i);
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public final MenuItem setNumericShortcut(char c, int i) {
        if (this.mShortcutNumericChar == c && this.mShortcutNumericModifiers == i) {
            return this;
        }
        this.mShortcutNumericChar = c;
        this.mShortcutNumericModifiers = KeyEvent.normalizeMetaState(i);
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public final MenuItem setShortcut(char c, char c2, int i, int i2) {
        this.mShortcutNumericChar = c;
        this.mShortcutNumericModifiers = KeyEvent.normalizeMetaState(i);
        this.mShortcutAlphabeticChar = Character.toLowerCase(c2);
        this.mShortcutAlphabeticModifiers = KeyEvent.normalizeMetaState(i2);
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public final MenuItem setIcon(int i) {
        this.mIconDrawable = null;
        this.mIconResId = i;
        this.mNeedToApplyIconTint = true;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public final MenuItem setTitle(int i) {
        MenuBuilder menuBuilder = this.mMenu;
        Objects.requireNonNull(menuBuilder);
        setTitle((CharSequence) menuBuilder.mContext.getString(i));
        return this;
    }

    public final MenuItem setActionView(int i) {
        int i2;
        MenuBuilder menuBuilder = this.mMenu;
        Objects.requireNonNull(menuBuilder);
        Context context = menuBuilder.mContext;
        View inflate = LayoutInflater.from(context).inflate(i, new LinearLayout(context), false);
        this.mActionView = inflate;
        this.mActionProvider = null;
        if (inflate != null && inflate.getId() == -1 && (i2 = this.mId) > 0) {
            inflate.setId(i2);
        }
        MenuBuilder menuBuilder2 = this.mMenu;
        Objects.requireNonNull(menuBuilder2);
        menuBuilder2.mIsActionItemsStale = true;
        menuBuilder2.onItemsChanged(true);
        return this;
    }

    public final MenuItem setIntent(Intent intent) {
        this.mIntent = intent;
        return this;
    }

    public final MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        this.mOnActionExpandListener = onActionExpandListener;
        return this;
    }

    public final MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        this.mClickListener = onMenuItemClickListener;
        return this;
    }

    public final MenuItem setShowAsActionFlags(int i) {
        setShowAsAction(i);
        return this;
    }

    public final int getAlphabeticModifiers() {
        return this.mShortcutAlphabeticModifiers;
    }

    public final char getAlphabeticShortcut() {
        return this.mShortcutAlphabeticChar;
    }

    public final CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    public final int getGroupId() {
        return this.mGroup;
    }

    public final ColorStateList getIconTintList() {
        return this.mIconTintList;
    }

    public final PorterDuff.Mode getIconTintMode() {
        return this.mIconTintMode;
    }

    public final Intent getIntent() {
        return this.mIntent;
    }

    @ViewDebug.CapturedViewProperty
    public final int getItemId() {
        return this.mId;
    }

    public final int getNumericModifiers() {
        return this.mShortcutNumericModifiers;
    }

    public final char getNumericShortcut() {
        return this.mShortcutNumericChar;
    }

    public final int getOrder() {
        return this.mCategoryOrder;
    }

    public final SubMenu getSubMenu() {
        return this.mSubMenu;
    }

    public final ActionProvider getSupportActionProvider() {
        return this.mActionProvider;
    }

    @ViewDebug.CapturedViewProperty
    public final CharSequence getTitle() {
        return this.mTitle;
    }

    public final CharSequence getTooltipText() {
        return this.mTooltipText;
    }

    public final boolean isActionViewExpanded() {
        return this.mIsActionViewExpanded;
    }
}
