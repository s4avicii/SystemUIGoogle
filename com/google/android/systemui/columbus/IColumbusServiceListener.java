package com.google.android.systemui.columbus;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.systemui.columbus.actions.ServiceAction;

public interface IColumbusServiceListener extends IInterface {

    public static abstract class Stub extends Binder implements IColumbusServiceListener {
        public final IBinder asBinder() {
            return this;
        }

        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.google.android.systemui.columbus.IColumbusServiceListener");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.google.android.systemui.columbus.IColumbusServiceListener");
                return true;
            } else if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                IBinder readStrongBinder = parcel.readStrongBinder();
                IBinder readStrongBinder2 = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                ((ServiceAction.ColumbusServiceListener) this).setListener(readStrongBinder, readStrongBinder2);
                return true;
            }
        }

        public static class Proxy implements IColumbusServiceListener {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public final void setListener(IBinder iBinder, IBinder iBinder2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.systemui.columbus.IColumbusServiceListener");
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    this.mRemote.transact(1, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public final IBinder asBinder() {
                return this.mRemote;
            }
        }

        public Stub() {
            attachInterface(this, "com.google.android.systemui.columbus.IColumbusServiceListener");
        }
    }

    void setListener(IBinder iBinder, IBinder iBinder2) throws RemoteException;
}
