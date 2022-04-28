package android.support.p000v4.media.session;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.p000v4.media.session.IMediaControllerCallback;

/* renamed from: android.support.v4.media.session.IMediaSession */
public interface IMediaSession extends IInterface {

    /* renamed from: android.support.v4.media.session.IMediaSession$Stub */
    public static abstract class Stub extends Binder implements IMediaSession {
        public static final /* synthetic */ int $r8$clinit = 0;

        /* renamed from: android.support.v4.media.session.IMediaSession$Stub$Proxy */
        public static class Proxy implements IMediaSession {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public final void registerCallbackListener(IMediaControllerCallback iMediaControllerCallback) throws RemoteException {
                IMediaControllerCallback.Stub stub;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    if (iMediaControllerCallback != null) {
                        stub = (IMediaControllerCallback.Stub) iMediaControllerCallback;
                    } else {
                        stub = null;
                    }
                    obtain.writeStrongBinder(stub);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        int i = Stub.$r8$clinit;
                    }
                    obtain2.readException();
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

    void registerCallbackListener(IMediaControllerCallback iMediaControllerCallback) throws RemoteException;
}
