package com.android.p012wm.shell.pip;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: com.android.wm.shell.pip.IPipAnimationListener$Stub$Proxy */
public final class IPipAnimationListener$Stub$Proxy implements IPipAnimationListener {
    public IBinder mRemote;

    public IPipAnimationListener$Stub$Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    public final void onPipAnimationStarted() throws RemoteException {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("com.android.wm.shell.pip.IPipAnimationListener");
            this.mRemote.transact(1, obtain, (Parcel) null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public final void onPipCornerRadiusChanged(int i) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("com.android.wm.shell.pip.IPipAnimationListener");
            obtain.writeInt(i);
            this.mRemote.transact(2, obtain, (Parcel) null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public final IBinder asBinder() {
        return this.mRemote;
    }
}
