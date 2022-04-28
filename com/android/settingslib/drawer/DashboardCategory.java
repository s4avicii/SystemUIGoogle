package com.android.settingslib.drawer;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class DashboardCategory implements Parcelable {
    public static final Parcelable.Creator<DashboardCategory> CREATOR = new Parcelable.Creator<DashboardCategory>() {
        public final Object createFromParcel(Parcel parcel) {
            return new DashboardCategory(parcel);
        }

        public final Object[] newArray(int i) {
            return new DashboardCategory[i];
        }
    };
    public final String key;
    public ArrayList mTiles = new ArrayList();

    public final int describeContents() {
        return 0;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.key);
        int size = this.mTiles.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            ((Tile) this.mTiles.get(i2)).writeToParcel(parcel, i);
        }
    }

    public DashboardCategory(Parcel parcel) {
        this.key = parcel.readString();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            this.mTiles.add(Tile.CREATOR.createFromParcel(parcel));
        }
    }
}
