package com.android.systemui.flags;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* compiled from: Flag.kt */
public final class IntFlag implements ParcelableFlag<Integer> {
    public static final Parcelable.Creator<IntFlag> CREATOR = new IntFlag$Companion$CREATOR$1();

    /* renamed from: default  reason: not valid java name */
    public final int f166default;

    /* renamed from: id */
    public final int f54id;

    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IntFlag)) {
            return false;
        }
        IntFlag intFlag = (IntFlag) obj;
        int i = this.f54id;
        Objects.requireNonNull(intFlag);
        return i == intFlag.f54id && Integer.valueOf(this.f166default).intValue() == Integer.valueOf(intFlag.f166default).intValue();
    }

    public final int hashCode() {
        return Integer.valueOf(this.f166default).hashCode() + (Integer.hashCode(this.f54id) * 31);
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("IntFlag(id=");
        m.append(this.f54id);
        m.append(", default=");
        m.append(Integer.valueOf(this.f166default).intValue());
        m.append(')');
        return m.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f54id);
        parcel.writeInt(Integer.valueOf(this.f166default).intValue());
    }

    public IntFlag(Parcel parcel) {
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        this.f54id = readInt;
        this.f166default = readInt2;
    }
}
