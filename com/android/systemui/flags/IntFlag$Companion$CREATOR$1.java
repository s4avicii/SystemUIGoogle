package com.android.systemui.flags;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: Flag.kt */
public final class IntFlag$Companion$CREATOR$1 implements Parcelable.Creator<IntFlag> {
    public final Object createFromParcel(Parcel parcel) {
        return new IntFlag(parcel);
    }

    public final Object[] newArray(int i) {
        return new IntFlag[i];
    }
}
