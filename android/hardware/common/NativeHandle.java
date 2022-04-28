package android.hardware.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;

public class NativeHandle implements Parcelable {
    public static final Parcelable.Creator<NativeHandle> CREATOR = new Parcelable.Creator<NativeHandle>() {
        public final Object createFromParcel(Parcel parcel) {
            NativeHandle nativeHandle = new NativeHandle();
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            if (readInt >= 4) {
                try {
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        nativeHandle.fds = (ParcelFileDescriptor[]) parcel.createTypedArray(ParcelFileDescriptor.CREATOR);
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            nativeHandle.ints = parcel.createIntArray();
                            if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return nativeHandle;
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
            return new NativeHandle[i];
        }
    };
    public ParcelFileDescriptor[] fds;
    public int[] ints;

    public final int describeContents() {
        return describeContents(this.fds) | 0;
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
        parcel.writeTypedArray(this.fds, 0);
        parcel.writeIntArray(this.ints);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }
}
