package androidx.appcompat.view.menu;

import android.content.Context;
import android.view.LayoutInflater;
import androidx.appcompat.view.menu.MenuPresenter;
import com.android.p012wm.shell.C1777R;

public abstract class BaseMenuPresenter implements MenuPresenter {
    public MenuPresenter.Callback mCallback;
    public Context mContext;
    public int mId;
    public int mItemLayoutRes = C1777R.layout.abc_action_menu_item_layout;
    public MenuBuilder mMenu;
    public int mMenuLayoutRes = C1777R.layout.abc_action_menu_layout;
    public MenuView mMenuView;
    public Context mSystemContext;
    public LayoutInflater mSystemInflater;

    public final boolean collapseItemActionView(MenuItemImpl menuItemImpl) {
        return false;
    }

    public final boolean expandItemActionView(MenuItemImpl menuItemImpl) {
        return false;
    }

    public BaseMenuPresenter(Context context) {
        this.mSystemContext = context;
        this.mSystemInflater = LayoutInflater.from(context);
    }

    public final void setCallback(MenuPresenter.Callback callback) {
        this.mCallback = callback;
    }

    public final int getId() {
        return this.mId;
    }
}
