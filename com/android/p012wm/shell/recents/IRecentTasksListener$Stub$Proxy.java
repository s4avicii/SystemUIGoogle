package com.android.p012wm.shell.recents;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: com.android.wm.shell.recents.IRecentTasksListener$Stub$Proxy */
public final class IRecentTasksListener$Stub$Proxy implements IRecentTasksListener {
    public IBinder mRemote;

    public IRecentTasksListener$Stub$Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    public final void onRecentTasksChanged() throws RemoteException {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("com.android.wm.shell.recents.IRecentTasksListener");
            this.mRemote.transact(1, obtain, (Parcel) null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public final IBinder asBinder() {
        return this.mRemote;
    }
}
