package com.android.p012wm.shell.startingsurface;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.p012wm.shell.common.ExecutorUtils;
import com.android.p012wm.shell.startingsurface.StartingWindowController;

/* renamed from: com.android.wm.shell.startingsurface.IStartingWindow */
public interface IStartingWindow extends IInterface {

    /* renamed from: com.android.wm.shell.startingsurface.IStartingWindow$Stub */
    public static abstract class Stub extends Binder implements IStartingWindow {
        public final IBinder asBinder() {
            return this;
        }

        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            IStartingWindowListener iStartingWindowListener;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.wm.shell.startingsurface.IStartingWindow");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.wm.shell.startingsurface.IStartingWindow");
                return true;
            } else if (i != 44) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                IBinder readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder == null) {
                    iStartingWindowListener = null;
                } else {
                    IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.android.wm.shell.startingsurface.IStartingWindowListener");
                    if (queryLocalInterface == null || !(queryLocalInterface instanceof IStartingWindowListener)) {
                        iStartingWindowListener = new IStartingWindowListener$Stub$Proxy(readStrongBinder);
                    } else {
                        iStartingWindowListener = (IStartingWindowListener) queryLocalInterface;
                    }
                }
                parcel.enforceNoDataAvail();
                StartingWindowController.IStartingWindowImpl iStartingWindowImpl = (StartingWindowController.IStartingWindowImpl) this;
                ExecutorUtils.executeRemoteCallWithTaskPermission(iStartingWindowImpl.mController, "setStartingWindowListener", new C1933x795f7bd1(iStartingWindowImpl, iStartingWindowListener), false);
                return true;
            }
        }

        public Stub() {
            attachInterface(this, "com.android.wm.shell.startingsurface.IStartingWindow");
        }
    }
}
