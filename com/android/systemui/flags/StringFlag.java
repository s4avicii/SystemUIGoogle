package com.android.systemui.flags;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Flag.kt */
public final class StringFlag implements ParcelableFlag<String> {
    public static final Parcelable.Creator<StringFlag> CREATOR = new StringFlag$Companion$CREATOR$1();

    /* renamed from: default  reason: not valid java name */
    public final String f168default;

    /* renamed from: id */
    public final int f57id;

    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StringFlag)) {
            return false;
        }
        StringFlag stringFlag = (StringFlag) obj;
        int i = this.f57id;
        Objects.requireNonNull(stringFlag);
        return i == stringFlag.f57id && Intrinsics.areEqual(this.f168default, stringFlag.f168default);
    }

    public final int hashCode() {
        return this.f168default.hashCode() + (Integer.hashCode(this.f57id) * 31);
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("StringFlag(id=");
        m.append(this.f57id);
        m.append(", default=");
        m.append(this.f168default);
        m.append(')');
        return m.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f57id);
        parcel.writeString(this.f168default);
    }

    public StringFlag(Parcel parcel) {
        int readInt = parcel.readInt();
        String readString = parcel.readString();
        readString = readString == null ? "" : readString;
        this.f57id = readInt;
        this.f168default = readString;
    }
}
