package android.frameworks.stats;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

public class VendorAtom implements Parcelable {
    public static final Parcelable.Creator<VendorAtom> CREATOR = new Parcelable.Creator<VendorAtom>() {
        public final Object createFromParcel(Parcel parcel) {
            VendorAtom vendorAtom = new VendorAtom();
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            if (readInt >= 4) {
                try {
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        vendorAtom.reverseDomainName = parcel.readString();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            vendorAtom.atomId = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                vendorAtom.values = (VendorAtomValue[]) parcel.createTypedArray(VendorAtomValue.CREATOR);
                                if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return vendorAtom;
                } catch (Throwable th) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    throw th;
                }
            } else {
                throw new BadParcelableException("Parcelable too small");
            }
        }

        public final Object[] newArray(int i) {
            return new VendorAtom[i];
        }
    };
    public int atomId = 0;
    public String reverseDomainName;
    public VendorAtomValue[] values;

    public final int describeContents() {
        return describeContents(this.values) | 0;
    }

    public final int getStability() {
        return 1;
    }

    public static int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int i = 0;
            for (Object describeContents : (Object[]) obj) {
                i |= describeContents(describeContents);
            }
            return i;
        } else if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        } else {
            return 0;
        }
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.reverseDomainName);
        parcel.writeInt(this.atomId);
        parcel.writeTypedArray(this.values, 0);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }
}
