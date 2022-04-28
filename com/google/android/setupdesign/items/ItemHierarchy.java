package com.google.android.setupdesign.items;

public interface ItemHierarchy {

    public interface Observer {
        void onItemRangeChanged(ItemHierarchy itemHierarchy, int i);

        void onItemRangeInserted(ItemHierarchy itemHierarchy, int i, int i2);
    }

    int getCount();

    AbstractItem getItemAt(int i);

    void registerObserver(Observer observer);
}
