package android.support.p000v4.p001os;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.p000v4.p001os.IResultReceiver;

@SuppressLint({"BanParcelableUsage"})
/* renamed from: android.support.v4.os.ResultReceiver */
public class ResultReceiver implements Parcelable {
    public static final Parcelable.Creator<ResultReceiver> CREATOR = new Parcelable.Creator<ResultReceiver>() {
        public final Object createFromParcel(Parcel parcel) {
            return new ResultReceiver(parcel);
        }

        public final Object[] newArray(int i) {
            return new ResultReceiver[i];
        }
    };
    public IResultReceiver mReceiver;

    /* renamed from: android.support.v4.os.ResultReceiver$MyResultReceiver */
    public class MyResultReceiver extends IResultReceiver.Stub {
        public MyResultReceiver() {
        }
    }

    public final int describeContents() {
        return 0;
    }

    public void onReceiveResult(int i, Bundle bundle) {
    }

    public final void writeToParcel(Parcel parcel, int i) {
        synchronized (this) {
            if (this.mReceiver == null) {
                this.mReceiver = new MyResultReceiver();
            }
            parcel.writeStrongBinder(this.mReceiver.asBinder());
        }
    }

    public ResultReceiver(Parcel parcel) {
        IResultReceiver iResultReceiver;
        IBinder readStrongBinder = parcel.readStrongBinder();
        int i = IResultReceiver.Stub.$r8$clinit;
        if (readStrongBinder == null) {
            iResultReceiver = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("android.support.v4.os.IResultReceiver");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IResultReceiver)) {
                iResultReceiver = new IResultReceiver.Stub.Proxy(readStrongBinder);
            } else {
                iResultReceiver = (IResultReceiver) queryLocalInterface;
            }
        }
        this.mReceiver = iResultReceiver;
    }
}
