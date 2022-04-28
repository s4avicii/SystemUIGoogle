package com.android.systemui.flags;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* compiled from: Flag.kt */
public final class LongFlag implements ParcelableFlag<Long> {
    public static final Parcelable.Creator<LongFlag> CREATOR = new LongFlag$Companion$CREATOR$1();

    /* renamed from: default  reason: not valid java name */
    public final long f167default;

    /* renamed from: id */
    public final int f55id;

    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LongFlag)) {
            return false;
        }
        LongFlag longFlag = (LongFlag) obj;
        int i = this.f55id;
        Objects.requireNonNull(longFlag);
        return i == longFlag.f55id && Long.valueOf(this.f167default).longValue() == Long.valueOf(longFlag.f167default).longValue();
    }

    public final int hashCode() {
        return Long.valueOf(this.f167default).hashCode() + (Integer.hashCode(this.f55id) * 31);
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("LongFlag(id=");
        m.append(this.f55id);
        m.append(", default=");
        m.append(Long.valueOf(this.f167default).longValue());
        m.append(')');
        return m.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f55id);
        parcel.writeLong(Long.valueOf(this.f167default).longValue());
    }

    public LongFlag(Parcel parcel) {
        int readInt = parcel.readInt();
        long readLong = parcel.readLong();
        this.f55id = readInt;
        this.f167default = readLong;
    }
}
