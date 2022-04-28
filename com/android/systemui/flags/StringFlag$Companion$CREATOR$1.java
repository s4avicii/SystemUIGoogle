package com.android.systemui.flags;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: Flag.kt */
public final class StringFlag$Companion$CREATOR$1 implements Parcelable.Creator<StringFlag> {
    public final Object createFromParcel(Parcel parcel) {
        return new StringFlag(parcel);
    }

    public final Object[] newArray(int i) {
        return new StringFlag[i];
    }
}
