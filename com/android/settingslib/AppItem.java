package com.android.settingslib;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseBooleanArray;
import java.util.Objects;

public class AppItem implements Comparable<AppItem>, Parcelable {
    public static final Parcelable.Creator<AppItem> CREATOR = new Parcelable.Creator<AppItem>() {
        public final Object createFromParcel(Parcel parcel) {
            return new AppItem(parcel);
        }

        public final Object[] newArray(int i) {
            return new AppItem[i];
        }
    };
    public final int key;
    public long total;
    public SparseBooleanArray uids;

    public AppItem() {
        this.uids = new SparseBooleanArray();
        this.key = 0;
    }

    public final int describeContents() {
        return 0;
    }

    public final int compareTo(Object obj) {
        AppItem appItem = (AppItem) obj;
        Objects.requireNonNull(appItem);
        int compare = Integer.compare(0, 0);
        if (compare == 0) {
            return Long.compare(appItem.total, this.total);
        }
        return compare;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.key);
        parcel.writeSparseBooleanArray(this.uids);
        parcel.writeLong(this.total);
    }

    public AppItem(Parcel parcel) {
        this.uids = new SparseBooleanArray();
        this.key = parcel.readInt();
        this.uids = parcel.readSparseBooleanArray();
        this.total = parcel.readLong();
    }
}
