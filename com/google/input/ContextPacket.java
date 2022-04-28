package com.google.input;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

public class ContextPacket implements Parcelable {
    public static final Parcelable.Creator<ContextPacket> CREATOR = new Parcelable.Creator<ContextPacket>() {
        public final Object createFromParcel(Parcel parcel) {
            ContextPacket contextPacket = new ContextPacket();
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            if (readInt >= 4) {
                try {
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        contextPacket.orientation = parcel.readByte();
                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return contextPacket;
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
            return new ContextPacket[i];
        }
    };
    public byte orientation;

    public final int describeContents() {
        return 0;
    }

    public final int getStability() {
        return 1;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByte(this.orientation);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }
}
