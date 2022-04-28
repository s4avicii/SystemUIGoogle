package com.google.android.setupdesign.items;

import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.google.android.setupdesign.items.ItemHierarchy;
import java.util.Objects;

public final class ItemAdapter extends BaseAdapter implements ItemHierarchy.Observer {
    public final ItemHierarchy itemHierarchy;
    public final ViewTypes viewTypes = new ViewTypes();

    public static class ViewTypes {
        public int nextPosition = 0;
        public final SparseIntArray positionMap = new SparseIntArray();
    }

    public final long getItemId(int i) {
        return (long) i;
    }

    public final void refreshViewTypes() {
        for (int i = 0; i < getCount(); i++) {
            AbstractItem itemAt = this.itemHierarchy.getItemAt(i);
            ViewTypes viewTypes2 = this.viewTypes;
            int layoutResource = itemAt.getLayoutResource();
            Objects.requireNonNull(viewTypes2);
            if (viewTypes2.positionMap.indexOfKey(layoutResource) < 0) {
                viewTypes2.positionMap.put(layoutResource, viewTypes2.nextPosition);
                viewTypes2.nextPosition++;
            }
            viewTypes2.positionMap.get(layoutResource);
        }
    }

    public final int getCount() {
        return this.itemHierarchy.getCount();
    }

    public final Object getItem(int i) {
        return this.itemHierarchy.getItemAt(i);
    }

    public final int getItemViewType(int i) {
        int layoutResource = this.itemHierarchy.getItemAt(i).getLayoutResource();
        ViewTypes viewTypes2 = this.viewTypes;
        Objects.requireNonNull(viewTypes2);
        return viewTypes2.positionMap.get(layoutResource);
    }

    public final View getView(int i, View view, ViewGroup viewGroup) {
        AbstractItem itemAt = this.itemHierarchy.getItemAt(i);
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(itemAt.getLayoutResource(), viewGroup, false);
        }
        itemAt.onBindView(view);
        return view;
    }

    public final int getViewTypeCount() {
        ViewTypes viewTypes2 = this.viewTypes;
        Objects.requireNonNull(viewTypes2);
        return viewTypes2.positionMap.size();
    }

    public final boolean isEnabled(int i) {
        return this.itemHierarchy.getItemAt(i).isEnabled();
    }

    public ItemAdapter(ItemGroup itemGroup) {
        this.itemHierarchy = itemGroup;
        itemGroup.registerObserver(this);
        refreshViewTypes();
    }

    public final void onItemRangeChanged(ItemHierarchy itemHierarchy2, int i) {
        refreshViewTypes();
        notifyDataSetChanged();
    }

    public final void onItemRangeInserted(ItemHierarchy itemHierarchy2, int i, int i2) {
        refreshViewTypes();
        notifyDataSetChanged();
    }
}
