package com.android.p012wm.shell.startingsurface;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: com.android.wm.shell.startingsurface.IStartingWindowListener$Stub$Proxy */
public final class IStartingWindowListener$Stub$Proxy implements IStartingWindowListener {
    public IBinder mRemote;

    public IStartingWindowListener$Stub$Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    public final void onTaskLaunching(int i, int i2, int i3) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("com.android.wm.shell.startingsurface.IStartingWindowListener");
            obtain.writeInt(i);
            obtain.writeInt(i2);
            obtain.writeInt(i3);
            this.mRemote.transact(1, obtain, (Parcel) null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public final IBinder asBinder() {
        return this.mRemote;
    }
}
