package com.google.android.systemui.elmyra;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

public final class IElmyraServiceGestureListener$Stub$Proxy implements IElmyraServiceGestureListener {
    public IBinder mRemote;

    public IElmyraServiceGestureListener$Stub$Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    public final void onGestureDetected() throws RemoteException {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("com.google.android.systemui.elmyra.IElmyraServiceGestureListener");
            this.mRemote.transact(2, obtain, (Parcel) null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public final void onGestureProgress(float f, int i) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("com.google.android.systemui.elmyra.IElmyraServiceGestureListener");
            obtain.writeFloat(f);
            obtain.writeInt(i);
            this.mRemote.transact(1, obtain, (Parcel) null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public final IBinder asBinder() {
        return this.mRemote;
    }
}
