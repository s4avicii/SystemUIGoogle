package com.google.android.systemui.elmyra;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.systemui.elmyra.ElmyraServiceProxy;
import java.util.Objects;

public interface IElmyraService extends IInterface {

    public static abstract class Stub extends Binder implements IElmyraService {
        public static final /* synthetic */ int $r8$clinit = 0;

        public final IBinder asBinder() {
            return this;
        }

        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.google.android.systemui.elmyra.IElmyraService");
            }
            if (i != 1598968902) {
                if (i == 1) {
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    ElmyraServiceProxy.C22361 r5 = (ElmyraServiceProxy.C22361) this;
                    ElmyraServiceProxy elmyraServiceProxy = ElmyraServiceProxy.this;
                    int i3 = ElmyraServiceProxy.$r8$clinit;
                    Objects.requireNonNull(elmyraServiceProxy);
                    elmyraServiceProxy.enforceCallingOrSelfPermission("com.google.android.elmyra.permission.CONFIGURE_ASSIST_GESTURE", "Must have com.google.android.elmyra.permission.CONFIGURE_ASSIST_GESTURE permission");
                    try {
                        int size = ElmyraServiceProxy.this.mElmyraServiceListeners.size();
                        while (true) {
                            size--;
                            if (size < 0) {
                                break;
                            }
                            ElmyraServiceProxy.ElmyraServiceListener elmyraServiceListener = (ElmyraServiceProxy.ElmyraServiceListener) ElmyraServiceProxy.this.mElmyraServiceListeners.get(size);
                            Objects.requireNonNull(elmyraServiceListener);
                            IElmyraServiceListener iElmyraServiceListener = elmyraServiceListener.mListener;
                            if (iElmyraServiceListener == null) {
                                ElmyraServiceProxy.this.mElmyraServiceListeners.remove(size);
                            } else {
                                iElmyraServiceListener.setListener(readStrongBinder, readStrongBinder2);
                            }
                        }
                    } catch (RemoteException e) {
                        Log.e("Elmyra/ElmyraServiceProxy", "Action isn't connected", e);
                    }
                } else if (i == 2) {
                    ElmyraServiceProxy.C22361 r52 = (ElmyraServiceProxy.C22361) this;
                    ElmyraServiceProxy elmyraServiceProxy2 = ElmyraServiceProxy.this;
                    int i4 = ElmyraServiceProxy.$r8$clinit;
                    Objects.requireNonNull(elmyraServiceProxy2);
                    elmyraServiceProxy2.enforceCallingOrSelfPermission("com.google.android.elmyra.permission.CONFIGURE_ASSIST_GESTURE", "Must have com.google.android.elmyra.permission.CONFIGURE_ASSIST_GESTURE permission");
                    try {
                        int size2 = ElmyraServiceProxy.this.mElmyraServiceListeners.size();
                        while (true) {
                            size2--;
                            if (size2 < 0) {
                                break;
                            }
                            ElmyraServiceProxy.ElmyraServiceListener elmyraServiceListener2 = (ElmyraServiceProxy.ElmyraServiceListener) ElmyraServiceProxy.this.mElmyraServiceListeners.get(size2);
                            Objects.requireNonNull(elmyraServiceListener2);
                            IElmyraServiceListener iElmyraServiceListener2 = elmyraServiceListener2.mListener;
                            if (iElmyraServiceListener2 == null) {
                                ElmyraServiceProxy.this.mElmyraServiceListeners.remove(size2);
                            } else {
                                iElmyraServiceListener2.triggerAction();
                            }
                        }
                    } catch (RemoteException e2) {
                        Log.e("Elmyra/ElmyraServiceProxy", "Error launching assistant", e2);
                    }
                } else if (i != 3) {
                    return super.onTransact(i, parcel, parcel2, i2);
                } else {
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    ((ElmyraServiceProxy.C22361) this).registerServiceListener(readStrongBinder3, readStrongBinder4);
                }
                return true;
            }
            parcel2.writeString("com.google.android.systemui.elmyra.IElmyraService");
            return true;
        }

        public static class Proxy implements IElmyraService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public final void registerServiceListener(IBinder iBinder, IBinder iBinder2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.systemui.elmyra.IElmyraService");
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    this.mRemote.transact(3, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public final IBinder asBinder() {
                return this.mRemote;
            }
        }

        public Stub() {
            attachInterface(this, "com.google.android.systemui.elmyra.IElmyraService");
        }
    }

    void registerServiceListener(IBinder iBinder, IBinder iBinder2) throws RemoteException;
}
