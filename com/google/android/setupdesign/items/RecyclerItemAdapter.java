package com.google.android.setupdesign.items;

import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupdesign.R$styleable;
import com.google.android.setupdesign.items.ItemHierarchy;
import java.util.Objects;

public final class RecyclerItemAdapter extends RecyclerView.Adapter<ItemViewHolder> implements ItemHierarchy.Observer {
    public final boolean applyPartnerHeavyThemeResource;
    public final ItemHierarchy itemHierarchy;
    public final boolean useFullDynamicColor;

    public static class PatchedLayerDrawable extends LayerDrawable {
        public final boolean getPadding(Rect rect) {
            if (!super.getPadding(rect) || (rect.left == 0 && rect.top == 0 && rect.right == 0 && rect.bottom == 0)) {
                return false;
            }
            return true;
        }

        public PatchedLayerDrawable(Drawable[] drawableArr) {
            super(drawableArr);
        }
    }

    public final int getItemCount() {
        return this.itemHierarchy.getCount();
    }

    public final long getItemId(int i) {
        AbstractItem itemAt = this.itemHierarchy.getItemAt(i);
        if (!(itemAt instanceof AbstractItem)) {
            return -1;
        }
        Objects.requireNonNull(itemAt);
        int i2 = itemAt.f137id;
        if (i2 > 0) {
            return (long) i2;
        }
        return -1;
    }

    public final int getItemViewType(int i) {
        return this.itemHierarchy.getItemAt(i).getLayoutResource();
    }

    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        AbstractItem itemAt = this.itemHierarchy.getItemAt(i);
        boolean isEnabled = itemAt.isEnabled();
        itemViewHolder.isEnabled = isEnabled;
        itemViewHolder.itemView.setClickable(isEnabled);
        itemViewHolder.itemView.setEnabled(isEnabled);
        itemViewHolder.itemView.setFocusable(isEnabled);
        itemViewHolder.item = itemAt;
        itemAt.onBindView(itemViewHolder.itemView);
    }

    public final void onItemRangeChanged(ItemHierarchy itemHierarchy2, int i) {
        RecyclerView.AdapterDataObservable adapterDataObservable = this.mObservable;
        Objects.requireNonNull(adapterDataObservable);
        adapterDataObservable.notifyItemRangeChanged(i, 1, (Object) null);
    }

    public RecyclerItemAdapter(ItemHierarchy itemHierarchy2, boolean z, boolean z2) {
        this.applyPartnerHeavyThemeResource = z;
        this.useFullDynamicColor = z2;
        this.itemHierarchy = itemHierarchy2;
        itemHierarchy2.registerObserver(this);
    }

    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        Drawable drawable;
        View inflate = LayoutInflater.from(recyclerView.getContext()).inflate(i, recyclerView, false);
        final ItemViewHolder itemViewHolder = new ItemViewHolder(inflate);
        if (!"noBackground".equals(inflate.getTag())) {
            TypedArray obtainStyledAttributes = recyclerView.getContext().obtainStyledAttributes(R$styleable.SudRecyclerItemAdapter);
            Drawable drawable2 = obtainStyledAttributes.getDrawable(1);
            if (drawable2 == null) {
                drawable2 = obtainStyledAttributes.getDrawable(2);
                drawable = null;
            } else {
                drawable = inflate.getBackground();
                if (drawable == null) {
                    if (!this.applyPartnerHeavyThemeResource || this.useFullDynamicColor) {
                        drawable = obtainStyledAttributes.getDrawable(0);
                    } else {
                        drawable = new ColorDrawable(PartnerConfigHelper.get(inflate.getContext()).getColor(inflate.getContext(), PartnerConfig.CONFIG_LAYOUT_BACKGROUND_COLOR));
                    }
                }
            }
            if (drawable2 == null || drawable == null) {
                Log.e("RecyclerItemAdapter", "Cannot resolve required attributes. selectableItemBackground=" + drawable2 + " background=" + drawable);
            } else {
                inflate.setBackgroundDrawable(new PatchedLayerDrawable(new Drawable[]{drawable, drawable2}));
            }
            obtainStyledAttributes.recycle();
        }
        inflate.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                Objects.requireNonNull(itemViewHolder);
                Objects.requireNonNull(RecyclerItemAdapter.this);
            }
        });
        return itemViewHolder;
    }

    public final void onItemRangeInserted(ItemHierarchy itemHierarchy2, int i, int i2) {
        notifyItemRangeInserted(i, i2);
    }
}
