package com.google.input;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ITouchContextService extends IInterface {
    public static final String DESCRIPTOR = "com$google$input$ITouchContextService".replace('$', '.');

    public static abstract class Stub extends Binder implements ITouchContextService {
        public static final /* synthetic */ int $r8$clinit = 0;

        public static class Proxy implements ITouchContextService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public final void updateContext(ContextPacket contextPacket) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITouchContextService.DESCRIPTOR);
                    obtain.writeTypedObject(contextPacket, 0);
                    if (!this.mRemote.transact(1, obtain, (Parcel) null, 1)) {
                        throw new RemoteException("Method updateContext is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            public final IBinder asBinder() {
                return this.mRemote;
            }
        }
    }

    void updateContext(ContextPacket contextPacket) throws RemoteException;
}
