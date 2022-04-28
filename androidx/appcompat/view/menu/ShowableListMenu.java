package androidx.appcompat.view.menu;

import androidx.appcompat.widget.DropDownListView;

public interface ShowableListMenu {
    void dismiss();

    DropDownListView getListView();

    boolean isShowing();

    void show();
}
