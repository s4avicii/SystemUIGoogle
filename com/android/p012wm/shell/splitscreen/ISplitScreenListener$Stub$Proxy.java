package com.android.p012wm.shell.splitscreen;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: com.android.wm.shell.splitscreen.ISplitScreenListener$Stub$Proxy */
public final class ISplitScreenListener$Stub$Proxy implements ISplitScreenListener {
    public IBinder mRemote;

    public ISplitScreenListener$Stub$Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    public final void onStagePositionChanged(int i, int i2) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("com.android.wm.shell.splitscreen.ISplitScreenListener");
            obtain.writeInt(i);
            obtain.writeInt(i2);
            this.mRemote.transact(1, obtain, (Parcel) null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public final void onTaskStageChanged(int i, int i2, boolean z) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("com.android.wm.shell.splitscreen.ISplitScreenListener");
            obtain.writeInt(i);
            obtain.writeInt(i2);
            obtain.writeBoolean(z);
            this.mRemote.transact(2, obtain, (Parcel) null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public final IBinder asBinder() {
        return this.mRemote;
    }
}
