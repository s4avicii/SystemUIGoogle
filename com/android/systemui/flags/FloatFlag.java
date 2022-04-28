package com.android.systemui.flags;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Flag.kt */
public final class FloatFlag implements ParcelableFlag<Float> {
    public static final Parcelable.Creator<FloatFlag> CREATOR = new FloatFlag$Companion$CREATOR$1();

    /* renamed from: default  reason: not valid java name */
    public final float f165default;

    /* renamed from: id */
    public final int f53id;

    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FloatFlag)) {
            return false;
        }
        FloatFlag floatFlag = (FloatFlag) obj;
        int i = this.f53id;
        Objects.requireNonNull(floatFlag);
        return i == floatFlag.f53id && Intrinsics.areEqual(Float.valueOf(this.f165default), Float.valueOf(floatFlag.f165default));
    }

    public final int hashCode() {
        return Float.valueOf(this.f165default).hashCode() + (Integer.hashCode(this.f53id) * 31);
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("FloatFlag(id=");
        m.append(this.f53id);
        m.append(", default=");
        m.append(Float.valueOf(this.f165default).floatValue());
        m.append(')');
        return m.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f53id);
        parcel.writeFloat(Float.valueOf(this.f165default).floatValue());
    }

    public FloatFlag(Parcel parcel) {
        int readInt = parcel.readInt();
        float readFloat = parcel.readFloat();
        this.f53id = readInt;
        this.f165default = readFloat;
    }
}
