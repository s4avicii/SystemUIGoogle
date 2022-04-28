package com.google.android.setupcompat.portal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class NotificationComponent implements Parcelable {
    public static final Parcelable.Creator<NotificationComponent> CREATOR = new Parcelable.Creator<NotificationComponent>() {
        public final Object createFromParcel(Parcel parcel) {
            return new NotificationComponent(parcel);
        }

        public final Object[] newArray(int i) {
            return new NotificationComponent[i];
        }
    };
    public Bundle extraBundle = new Bundle();
    public final int notificationType;

    public final int describeContents() {
        return 0;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.notificationType);
        parcel.writeBundle(this.extraBundle);
    }

    public NotificationComponent(Parcel parcel) {
        int readInt = parcel.readInt();
        this.notificationType = readInt;
        this.extraBundle = parcel.readBundle(Bundle.class.getClassLoader());
    }
}
