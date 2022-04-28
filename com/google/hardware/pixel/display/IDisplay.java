package com.google.hardware.pixel.display;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IDisplay extends IInterface {
    public static final String DESCRIPTOR = "com$google$hardware$pixel$display$IDisplay".replace('$', '.');

    public static abstract class Stub extends Binder implements IDisplay {
        public static final /* synthetic */ int $r8$clinit = 0;

        public static class Proxy implements IDisplay {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public final void setLhbmState(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDisplay.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (this.mRemote.transact(9, obtain, obtain2, 0)) {
                        obtain2.readException();
                        return;
                    }
                    throw new RemoteException("Method setLhbmState is unimplemented.");
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final IBinder asBinder() {
                return this.mRemote;
            }
        }
    }

    void setLhbmState(boolean z) throws RemoteException;
}
