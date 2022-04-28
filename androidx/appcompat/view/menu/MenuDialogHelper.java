package androidx.appcompat.view.menu;

import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.menu.ListMenuPresenter;
import androidx.appcompat.view.menu.MenuPresenter;
import java.util.Objects;

public final class MenuDialogHelper implements DialogInterface.OnKeyListener, DialogInterface.OnClickListener, DialogInterface.OnDismissListener, MenuPresenter.Callback {
    public AlertDialog mDialog;
    public MenuBuilder mMenu;
    public ListMenuPresenter mPresenter;

    public final boolean onOpenSubMenu(MenuBuilder menuBuilder) {
        return false;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        MenuBuilder menuBuilder = this.mMenu;
        ListMenuPresenter listMenuPresenter = this.mPresenter;
        Objects.requireNonNull(listMenuPresenter);
        if (listMenuPresenter.mAdapter == null) {
            listMenuPresenter.mAdapter = new ListMenuPresenter.MenuAdapter();
        }
        ListMenuPresenter.MenuAdapter menuAdapter = listMenuPresenter.mAdapter;
        Objects.requireNonNull(menuAdapter);
        MenuItemImpl item = menuAdapter.getItem(i);
        Objects.requireNonNull(menuBuilder);
        menuBuilder.performItemAction(item, (MenuPresenter) null, 0);
    }

    public final void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        AlertDialog alertDialog;
        if ((z || menuBuilder == this.mMenu) && (alertDialog = this.mDialog) != null) {
            alertDialog.dismiss();
        }
    }

    public final void onDismiss(DialogInterface dialogInterface) {
        this.mPresenter.onCloseMenu(this.mMenu, true);
    }

    public final boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        Window window;
        View decorView;
        KeyEvent.DispatcherState keyDispatcherState;
        View decorView2;
        KeyEvent.DispatcherState keyDispatcherState2;
        if (i == 82 || i == 4) {
            if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                Window window2 = this.mDialog.getWindow();
                if (!(window2 == null || (decorView2 = window2.getDecorView()) == null || (keyDispatcherState2 = decorView2.getKeyDispatcherState()) == null)) {
                    keyDispatcherState2.startTracking(keyEvent, this);
                    return true;
                }
            } else if (keyEvent.getAction() == 1 && !keyEvent.isCanceled() && (window = this.mDialog.getWindow()) != null && (decorView = window.getDecorView()) != null && (keyDispatcherState = decorView.getKeyDispatcherState()) != null && keyDispatcherState.isTracking(keyEvent)) {
                this.mMenu.close(true);
                dialogInterface.dismiss();
                return true;
            }
        }
        return this.mMenu.performShortcut(i, keyEvent, 0);
    }

    public MenuDialogHelper(MenuBuilder menuBuilder) {
        this.mMenu = menuBuilder;
    }
}
