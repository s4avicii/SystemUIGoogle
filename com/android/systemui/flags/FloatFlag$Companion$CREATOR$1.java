package com.android.systemui.flags;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: Flag.kt */
public final class FloatFlag$Companion$CREATOR$1 implements Parcelable.Creator<FloatFlag> {
    public final Object createFromParcel(Parcel parcel) {
        return new FloatFlag(parcel);
    }

    public final Object[] newArray(int i) {
        return new FloatFlag[i];
    }
}
