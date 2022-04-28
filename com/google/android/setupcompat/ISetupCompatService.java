package com.google.android.setupcompat;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ISetupCompatService extends IInterface {

    public static abstract class Stub extends Binder implements ISetupCompatService {
        public static final /* synthetic */ int $r8$clinit = 0;

        public static class Proxy implements ISetupCompatService {
            public IBinder mRemote;

            public final void logMetric(int i, Bundle bundle) throws RemoteException {
                Bundle bundle2 = Bundle.EMPTY;
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.setupcompat.ISetupCompatService");
                    obtain.writeInt(i);
                    _Parcel.m301$$Nest$smwriteTypedObject(obtain, bundle);
                    _Parcel.m301$$Nest$smwriteTypedObject(obtain, bundle2);
                    this.mRemote.transact(2, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public final void onFocusStatusChanged(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.setupcompat.ISetupCompatService");
                    _Parcel.m301$$Nest$smwriteTypedObject(obtain, bundle);
                    this.mRemote.transact(3, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public final void validateActivity(String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.setupcompat.ISetupCompatService");
                    obtain.writeString(str);
                    _Parcel.m301$$Nest$smwriteTypedObject(obtain, bundle);
                    this.mRemote.transact(1, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public final IBinder asBinder() {
                return this.mRemote;
            }
        }
    }

    public static class _Parcel {
        /* renamed from: -$$Nest$smwriteTypedObject  reason: not valid java name */
        public static void m301$$Nest$smwriteTypedObject(Parcel parcel, Parcelable parcelable) {
            if (parcelable != null) {
                parcel.writeInt(1);
                parcelable.writeToParcel(parcel, 0);
                return;
            }
            parcel.writeInt(0);
        }
    }

    void logMetric(int i, Bundle bundle) throws RemoteException;

    void onFocusStatusChanged(Bundle bundle) throws RemoteException;

    void validateActivity(String str, Bundle bundle) throws RemoteException;
}
