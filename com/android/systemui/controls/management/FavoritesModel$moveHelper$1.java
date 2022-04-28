package com.android.systemui.controls.management;

import android.util.Log;
import com.android.systemui.controls.management.ControlsModel;
import java.util.Objects;

/* compiled from: FavoritesModel.kt */
public final class FavoritesModel$moveHelper$1 implements ControlsModel.MoveHelper {
    public final /* synthetic */ FavoritesModel this$0;

    public final boolean canMoveAfter(int i) {
        return i >= 0 && i < this.this$0.dividerPosition - 1;
    }

    public FavoritesModel$moveHelper$1(FavoritesModel favoritesModel) {
        this.this$0 = favoritesModel;
    }

    public final boolean canMoveBefore(int i) {
        if (i <= 0 || i >= this.this$0.dividerPosition) {
            return false;
        }
        return true;
    }

    public final void moveAfter(int i) {
        if (!canMoveAfter(i)) {
            Log.w("FavoritesModel", "Cannot move position " + i + " after");
            return;
        }
        FavoritesModel favoritesModel = this.this$0;
        Objects.requireNonNull(favoritesModel);
        favoritesModel.onMoveItemInternal(i, i + 1);
    }

    public final void moveBefore(int i) {
        if (!canMoveBefore(i)) {
            Log.w("FavoritesModel", "Cannot move position " + i + " before");
            return;
        }
        FavoritesModel favoritesModel = this.this$0;
        Objects.requireNonNull(favoritesModel);
        favoritesModel.onMoveItemInternal(i, i - 1);
    }
}
