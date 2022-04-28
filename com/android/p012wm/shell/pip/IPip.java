package com.android.p012wm.shell.pip;

import android.app.PictureInPictureParams;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.SurfaceControl;
import com.android.p012wm.shell.common.ExecutorUtils;
import com.android.p012wm.shell.pip.phone.PipController;
import com.android.p012wm.shell.pip.phone.PipController$IPipImpl$$ExternalSyntheticLambda1;
import com.android.p012wm.shell.pip.phone.PipController$IPipImpl$$ExternalSyntheticLambda2;
import com.android.p012wm.shell.pip.phone.PipController$IPipImpl$$ExternalSyntheticLambda3;
import com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda2;

/* renamed from: com.android.wm.shell.pip.IPip */
public interface IPip extends IInterface {

    /* renamed from: com.android.wm.shell.pip.IPip$Stub */
    public static abstract class Stub extends Binder implements IPip {
        public final IBinder asBinder() {
            return this;
        }

        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            IPipAnimationListener iPipAnimationListener;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.wm.shell.pip.IPip");
            }
            if (i != 1598968902) {
                if (i == 2) {
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Rect[] rectArr = new Rect[1];
                    ExecutorUtils.executeRemoteCallWithTaskPermission(((PipController.IPipImpl) this).mController, "startSwipePipToHome", new PipController$IPipImpl$$ExternalSyntheticLambda2(rectArr, (ComponentName) parcel.readTypedObject(ComponentName.CREATOR), (ActivityInfo) parcel.readTypedObject(ActivityInfo.CREATOR), (PictureInPictureParams) parcel.readTypedObject(PictureInPictureParams.CREATOR), readInt, readInt2), true);
                    Rect rect = rectArr[0];
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(rect, 1);
                } else if (i == 3) {
                    parcel.enforceNoDataAvail();
                    ExecutorUtils.executeRemoteCallWithTaskPermission(((PipController.IPipImpl) this).mController, "stopSwipePipToHome", new PipController$IPipImpl$$ExternalSyntheticLambda3((ComponentName) parcel.readTypedObject(ComponentName.CREATOR), (Rect) parcel.readTypedObject(Rect.CREATOR), (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR)), false);
                } else if (i == 4) {
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    if (readStrongBinder == null) {
                        iPipAnimationListener = null;
                    } else {
                        IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.android.wm.shell.pip.IPipAnimationListener");
                        if (queryLocalInterface == null || !(queryLocalInterface instanceof IPipAnimationListener)) {
                            iPipAnimationListener = new IPipAnimationListener$Stub$Proxy(readStrongBinder);
                        } else {
                            iPipAnimationListener = (IPipAnimationListener) queryLocalInterface;
                        }
                    }
                    parcel.enforceNoDataAvail();
                    PipController.IPipImpl iPipImpl = (PipController.IPipImpl) this;
                    ExecutorUtils.executeRemoteCallWithTaskPermission(iPipImpl.mController, "setPinnedStackAnimationListener", new WifiPickerTracker$$ExternalSyntheticLambda2(iPipImpl, iPipAnimationListener, 2), false);
                } else if (i != 5) {
                    return super.onTransact(i, parcel, parcel2, i2);
                } else {
                    boolean readBoolean = parcel.readBoolean();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ExecutorUtils.executeRemoteCallWithTaskPermission(((PipController.IPipImpl) this).mController, "setShelfHeight", new PipController$IPipImpl$$ExternalSyntheticLambda1(readBoolean, readInt3), false);
                }
                return true;
            }
            parcel2.writeString("com.android.wm.shell.pip.IPip");
            return true;
        }

        public Stub() {
            attachInterface(this, "com.android.wm.shell.pip.IPip");
        }
    }
}
