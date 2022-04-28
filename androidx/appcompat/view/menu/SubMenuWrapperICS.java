package androidx.appcompat.view.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import androidx.core.internal.view.SupportSubMenu;

public final class SubMenuWrapperICS extends MenuWrapperICS implements SubMenu {
    public final SupportSubMenu mSubMenu;

    public final SubMenu setHeaderIcon(int i) {
        this.mSubMenu.setHeaderIcon(i);
        return this;
    }

    public final SubMenu setHeaderTitle(int i) {
        this.mSubMenu.setHeaderTitle(i);
        return this;
    }

    public final SubMenu setIcon(int i) {
        this.mSubMenu.setIcon(i);
        return this;
    }

    public final void clearHeader() {
        this.mSubMenu.clearHeader();
    }

    public final MenuItem getItem() {
        return getMenuItemWrapper(this.mSubMenu.getItem());
    }

    public final SubMenu setHeaderIcon(Drawable drawable) {
        this.mSubMenu.setHeaderIcon(drawable);
        return this;
    }

    public final SubMenu setHeaderTitle(CharSequence charSequence) {
        this.mSubMenu.setHeaderTitle(charSequence);
        return this;
    }

    public final SubMenu setHeaderView(View view) {
        this.mSubMenu.setHeaderView(view);
        return this;
    }

    public final SubMenu setIcon(Drawable drawable) {
        this.mSubMenu.setIcon(drawable);
        return this;
    }

    public SubMenuWrapperICS(Context context, SupportSubMenu supportSubMenu) {
        super(context, supportSubMenu);
        this.mSubMenu = supportSubMenu;
    }
}
