package com.android.systemui.shared.system.smartspace;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController$setLauncherUnlockController$1;

public abstract class ISysuiUnlockAnimationController$Stub extends Binder implements IInterface {
    public final IBinder asBinder() {
        return this;
    }

    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        ILauncherUnlockAnimationController iLauncherUnlockAnimationController;
        IBinder asBinder;
        if (i >= 1 && i <= 16777215) {
            parcel.enforceInterface("com.android.systemui.shared.system.smartspace.ISysuiUnlockAnimationController");
        }
        if (i != 1598968902) {
            if (i == 1) {
                IBinder readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder == null) {
                    iLauncherUnlockAnimationController = null;
                } else {
                    IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController");
                    if (queryLocalInterface == null || !(queryLocalInterface instanceof ILauncherUnlockAnimationController)) {
                        iLauncherUnlockAnimationController = new ILauncherUnlockAnimationController$Stub$Proxy(readStrongBinder);
                    } else {
                        iLauncherUnlockAnimationController = (ILauncherUnlockAnimationController) queryLocalInterface;
                    }
                }
                parcel.enforceNoDataAvail();
                KeyguardUnlockAnimationController keyguardUnlockAnimationController = (KeyguardUnlockAnimationController) this;
                keyguardUnlockAnimationController.launcherUnlockController = iLauncherUnlockAnimationController;
                if (!(iLauncherUnlockAnimationController == null || (asBinder = iLauncherUnlockAnimationController.asBinder()) == null)) {
                    asBinder.linkToDeath(new KeyguardUnlockAnimationController$setLauncherUnlockController$1(keyguardUnlockAnimationController), 0);
                }
            } else if (i != 2) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel.enforceNoDataAvail();
                ((KeyguardUnlockAnimationController) this).launcherSmartspaceState = (SmartspaceState) parcel.readTypedObject(SmartspaceState.CREATOR);
            }
            return true;
        }
        parcel2.writeString("com.android.systemui.shared.system.smartspace.ISysuiUnlockAnimationController");
        return true;
    }

    public ISysuiUnlockAnimationController$Stub() {
        attachInterface(this, "com.android.systemui.shared.system.smartspace.ISysuiUnlockAnimationController");
    }
}
