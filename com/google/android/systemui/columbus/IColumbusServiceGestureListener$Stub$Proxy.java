package com.google.android.systemui.columbus;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

public final class IColumbusServiceGestureListener$Stub$Proxy implements IColumbusServiceGestureListener {
    public IBinder mRemote;

    public IColumbusServiceGestureListener$Stub$Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    public final void onTrigger() throws RemoteException {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("com.google.android.systemui.columbus.IColumbusServiceGestureListener");
            this.mRemote.transact(1, obtain, (Parcel) null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public final IBinder asBinder() {
        return this.mRemote;
    }
}
