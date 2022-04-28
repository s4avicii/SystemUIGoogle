package com.android.systemui.flags;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* compiled from: Flag.kt */
public final class BooleanFlag implements ParcelableFlag<Boolean> {
    public static final Parcelable.Creator<BooleanFlag> CREATOR = new BooleanFlag$Companion$CREATOR$1();

    /* renamed from: default  reason: not valid java name */
    public final boolean f163default;

    /* renamed from: id */
    public final int f51id;

    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BooleanFlag)) {
            return false;
        }
        BooleanFlag booleanFlag = (BooleanFlag) obj;
        int i = this.f51id;
        Objects.requireNonNull(booleanFlag);
        return i == booleanFlag.f51id && getDefault().booleanValue() == booleanFlag.getDefault().booleanValue();
    }

    public final Boolean getDefault() {
        return Boolean.valueOf(this.f163default);
    }

    public final int hashCode() {
        return getDefault().hashCode() + (Integer.hashCode(this.f51id) * 31);
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("BooleanFlag(id=");
        m.append(this.f51id);
        m.append(", default=");
        m.append(getDefault().booleanValue());
        m.append(')');
        return m.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f51id);
        parcel.writeBoolean(getDefault().booleanValue());
    }

    public BooleanFlag(int i, boolean z) {
        this.f51id = i;
        this.f163default = z;
    }
}
