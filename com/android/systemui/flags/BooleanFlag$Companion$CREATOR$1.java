package com.android.systemui.flags;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: Flag.kt */
public final class BooleanFlag$Companion$CREATOR$1 implements Parcelable.Creator<BooleanFlag> {
    public final Object createFromParcel(Parcel parcel) {
        return new BooleanFlag(parcel.readInt(), parcel.readBoolean());
    }

    public final Object[] newArray(int i) {
        return new BooleanFlag[i];
    }
}
