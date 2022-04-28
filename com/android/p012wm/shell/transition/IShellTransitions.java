package com.android.p012wm.shell.transition;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.window.RemoteTransition;
import android.window.TransitionFilter;
import com.android.p012wm.shell.common.ExecutorUtils;
import com.android.p012wm.shell.transition.Transitions;

/* renamed from: com.android.wm.shell.transition.IShellTransitions */
public interface IShellTransitions extends IInterface {

    /* renamed from: com.android.wm.shell.transition.IShellTransitions$Stub */
    public static abstract class Stub extends Binder implements IShellTransitions {
        public final IBinder asBinder() {
            return this;
        }

        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.wm.shell.transition.IShellTransitions");
            }
            if (i != 1598968902) {
                if (i == 2) {
                    parcel.enforceNoDataAvail();
                    ExecutorUtils.executeRemoteCallWithTaskPermission(((Transitions.IShellTransitionsImpl) this).mTransitions, "registerRemote", new Transitions$IShellTransitionsImpl$$ExternalSyntheticLambda1((TransitionFilter) parcel.readTypedObject(TransitionFilter.CREATOR), (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR)), false);
                } else if (i != 3) {
                    return super.onTransact(i, parcel, parcel2, i2);
                } else {
                    parcel.enforceNoDataAvail();
                    ExecutorUtils.executeRemoteCallWithTaskPermission(((Transitions.IShellTransitionsImpl) this).mTransitions, "unregisterRemote", new Transitions$IShellTransitionsImpl$$ExternalSyntheticLambda0((RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR), 0), false);
                }
                return true;
            }
            parcel2.writeString("com.android.wm.shell.transition.IShellTransitions");
            return true;
        }

        public Stub() {
            attachInterface(this, "com.android.wm.shell.transition.IShellTransitions");
        }
    }
}
