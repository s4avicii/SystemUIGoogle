package com.android.systemui.controls.management;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Objects;

/* compiled from: FavoritesModel.kt */
public final class FavoritesModel$itemTouchHelperCallback$1 extends ItemTouchHelper.SimpleCallback {
    public final /* synthetic */ FavoritesModel this$0;

    public final void onSwiped() {
    }

    public FavoritesModel$itemTouchHelperCallback$1(FavoritesModel favoritesModel) {
        this.this$0 = favoritesModel;
    }

    public final boolean onMove(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
        FavoritesModel favoritesModel = this.this$0;
        int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
        int bindingAdapterPosition2 = viewHolder2.getBindingAdapterPosition();
        Objects.requireNonNull(favoritesModel);
        favoritesModel.onMoveItemInternal(bindingAdapterPosition, bindingAdapterPosition2);
        return true;
    }

    public final boolean canDropOver(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
        if (viewHolder2.getBindingAdapterPosition() < this.this$0.dividerPosition) {
            return true;
        }
        return false;
    }

    public final int getMovementFlags(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder.getBindingAdapterPosition() < this.this$0.dividerPosition) {
            return ItemTouchHelper.Callback.makeMovementFlags(15, 0);
        }
        return ItemTouchHelper.Callback.makeMovementFlags(0, 0);
    }
}
