package com.google.android.systemui.statusbar;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

public final class INotificationVoiceReplyServiceCallbacks$Stub$Proxy implements INotificationVoiceReplyServiceCallbacks {
    public IBinder mRemote;

    public INotificationVoiceReplyServiceCallbacks$Stub$Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    public final void onNotifAvailableForQuickPhraseChanged(boolean z) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("com.google.android.systemui.statusbar.INotificationVoiceReplyServiceCallbacks");
            obtain.writeBoolean(z);
            this.mRemote.transact(3, obtain, (Parcel) null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public final void onNotifAvailableForReplyChanged(boolean z) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("com.google.android.systemui.statusbar.INotificationVoiceReplyServiceCallbacks");
            obtain.writeBoolean(z);
            this.mRemote.transact(1, obtain, (Parcel) null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public final void onVoiceReplyHandled(int i, int i2) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("com.google.android.systemui.statusbar.INotificationVoiceReplyServiceCallbacks");
            obtain.writeInt(i);
            obtain.writeInt(i2);
            this.mRemote.transact(2, obtain, (Parcel) null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public final IBinder asBinder() {
        return this.mRemote;
    }
}
