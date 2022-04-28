package com.android.p012wm.shell.back;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.window.IOnBackInvokedCallback;
import com.android.p012wm.shell.back.BackAnimationController;
import com.android.p012wm.shell.common.ExecutorUtils;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda30;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda31;

/* renamed from: com.android.wm.shell.back.IBackAnimation */
public interface IBackAnimation extends IInterface {

    /* renamed from: com.android.wm.shell.back.IBackAnimation$Stub */
    public static abstract class Stub extends Binder implements IBackAnimation {
        public final IBinder asBinder() {
            return this;
        }

        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.wm.shell.back.IBackAnimation");
            }
            if (i != 1598968902) {
                if (i == 1) {
                    IOnBackInvokedCallback asInterface = IOnBackInvokedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    BackAnimationController.IBackAnimationImpl iBackAnimationImpl = (BackAnimationController.IBackAnimationImpl) this;
                    ExecutorUtils.executeRemoteCallWithTaskPermission(iBackAnimationImpl.mController, "setBackToLauncherCallback", new C1789x62452dae(iBackAnimationImpl, asInterface, 0), false);
                    parcel2.writeNoException();
                } else if (i == 2) {
                    BackAnimationController.IBackAnimationImpl iBackAnimationImpl2 = (BackAnimationController.IBackAnimationImpl) this;
                    ExecutorUtils.executeRemoteCallWithTaskPermission(iBackAnimationImpl2.mController, "clearBackToLauncherCallback", new StatusBar$$ExternalSyntheticLambda31(iBackAnimationImpl2, 4), false);
                    parcel2.writeNoException();
                } else if (i != 3) {
                    return super.onTransact(i, parcel, parcel2, i2);
                } else {
                    BackAnimationController.IBackAnimationImpl iBackAnimationImpl3 = (BackAnimationController.IBackAnimationImpl) this;
                    ExecutorUtils.executeRemoteCallWithTaskPermission(iBackAnimationImpl3.mController, "onBackToLauncherAnimationFinished", new StatusBar$$ExternalSyntheticLambda30(iBackAnimationImpl3, 3), false);
                    parcel2.writeNoException();
                }
                return true;
            }
            parcel2.writeString("com.android.wm.shell.back.IBackAnimation");
            return true;
        }

        public Stub() {
            attachInterface(this, "com.android.wm.shell.back.IBackAnimation");
        }
    }
}
