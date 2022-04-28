package com.android.systemui.flags;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Flag.kt */
public final class DoubleFlag implements ParcelableFlag<Double> {
    public static final Parcelable.Creator<DoubleFlag> CREATOR = new DoubleFlag$Companion$CREATOR$1();

    /* renamed from: default  reason: not valid java name */
    public final double f164default;

    /* renamed from: id */
    public final int f52id;

    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DoubleFlag)) {
            return false;
        }
        DoubleFlag doubleFlag = (DoubleFlag) obj;
        int i = this.f52id;
        Objects.requireNonNull(doubleFlag);
        return i == doubleFlag.f52id && Intrinsics.areEqual(Double.valueOf(this.f164default), Double.valueOf(doubleFlag.f164default));
    }

    public final int hashCode() {
        return Double.valueOf(this.f164default).hashCode() + (Integer.hashCode(this.f52id) * 31);
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("DoubleFlag(id=");
        m.append(this.f52id);
        m.append(", default=");
        m.append(Double.valueOf(this.f164default).doubleValue());
        m.append(')');
        return m.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f52id);
        parcel.writeDouble(Double.valueOf(this.f164default).doubleValue());
    }

    public DoubleFlag(Parcel parcel) {
        int readInt = parcel.readInt();
        double readDouble = parcel.readDouble();
        this.f52id = readInt;
        this.f164default = readDouble;
    }
}
