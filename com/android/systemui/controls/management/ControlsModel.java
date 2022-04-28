package com.android.systemui.controls.management;

import java.util.ArrayList;
import java.util.List;

/* compiled from: ControlsModel.kt */
public interface ControlsModel {

    /* compiled from: ControlsModel.kt */
    public interface ControlsModelCallback {
        void onFirstChange();
    }

    /* compiled from: ControlsModel.kt */
    public interface MoveHelper {
        boolean canMoveAfter(int i);

        boolean canMoveBefore(int i);

        void moveAfter(int i);

        void moveBefore(int i);
    }

    void changeFavoriteStatus(String str, boolean z);

    List<ElementWrapper> getElements();

    ArrayList getFavorites();

    MoveHelper getMoveHelper();
}
