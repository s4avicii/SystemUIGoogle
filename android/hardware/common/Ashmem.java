package android.hardware.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;

public class Ashmem implements Parcelable {
    public static final Parcelable.Creator<Ashmem> CREATOR = new Parcelable.Creator<Ashmem>() {
        public final Object createFromParcel(Parcel parcel) {
            Ashmem ashmem = new Ashmem();
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            if (readInt >= 4) {
                try {
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        ashmem.f0fd = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            ashmem.size = parcel.readLong();
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
                    return ashmem;
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
            return new Ashmem[i];
        }
    };

    /* renamed from: fd */
    public ParcelFileDescriptor f0fd;
    public long size = 0;

    public final int getStability() {
        return 1;
    }

    public final int describeContents() {
        int i;
        ParcelFileDescriptor parcelFileDescriptor = this.f0fd;
        if (parcelFileDescriptor == null) {
            i = 0;
        } else {
            i = parcelFileDescriptor.describeContents();
        }
        return i | 0;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.f0fd, 0);
        parcel.writeLong(this.size);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }
}
