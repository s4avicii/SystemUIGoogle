package com.android.p012wm.shell.splitscreen;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationTarget;
import android.window.RemoteTransition;
import com.android.p012wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda2;
import com.android.p012wm.shell.common.ExecutorUtils;
import com.android.p012wm.shell.splitscreen.SplitScreenController;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.C0961QS;
import com.android.systemui.statusbar.policy.LocationControllerImpl$H$$ExternalSyntheticLambda0;

/* renamed from: com.android.wm.shell.splitscreen.ISplitScreen */
public interface ISplitScreen extends IInterface {

    /* renamed from: com.android.wm.shell.splitscreen.ISplitScreen$Stub */
    public static abstract class Stub extends Binder implements ISplitScreen {
        public final IBinder asBinder() {
            return this;
        }

        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            IInterface queryLocalInterface;
            int i3 = i;
            Parcel parcel3 = parcel;
            Parcel parcel4 = parcel2;
            if (i3 >= 1 && i3 <= 16777215) {
                parcel3.enforceInterface("com.android.wm.shell.splitscreen.ISplitScreen");
            }
            if (i3 != 1598968902) {
                ISplitScreenListener iSplitScreenListener = null;
                switch (i3) {
                    case 2:
                        IBinder readStrongBinder = parcel.readStrongBinder();
                        if (readStrongBinder != null) {
                            IInterface queryLocalInterface2 = readStrongBinder.queryLocalInterface("com.android.wm.shell.splitscreen.ISplitScreenListener");
                            if (queryLocalInterface2 == null || !(queryLocalInterface2 instanceof ISplitScreenListener)) {
                                iSplitScreenListener = new ISplitScreenListener$Stub$Proxy(readStrongBinder);
                            } else {
                                iSplitScreenListener = (ISplitScreenListener) queryLocalInterface2;
                            }
                        }
                        parcel.enforceNoDataAvail();
                        SplitScreenController.ISplitScreenImpl iSplitScreenImpl = (SplitScreenController.ISplitScreenImpl) this;
                        ExecutorUtils.executeRemoteCallWithTaskPermission(iSplitScreenImpl.mController, "registerSplitScreenListener", new SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda6(iSplitScreenImpl, iSplitScreenListener), false);
                        break;
                    case 3:
                        IBinder readStrongBinder2 = parcel.readStrongBinder();
                        if (!(readStrongBinder2 == null || (queryLocalInterface = readStrongBinder2.queryLocalInterface("com.android.wm.shell.splitscreen.ISplitScreenListener")) == null || !(queryLocalInterface instanceof ISplitScreenListener))) {
                            ISplitScreenListener iSplitScreenListener2 = (ISplitScreenListener) queryLocalInterface;
                        }
                        parcel.enforceNoDataAvail();
                        SplitScreenController.ISplitScreenImpl iSplitScreenImpl2 = (SplitScreenController.ISplitScreenImpl) this;
                        ExecutorUtils.executeRemoteCallWithTaskPermission(iSplitScreenImpl2.mController, "unregisterSplitScreenListener", new ShellCommandHandlerImpl$$ExternalSyntheticLambda2(iSplitScreenImpl2, 4), false);
                        break;
                    case 4:
                        boolean readBoolean = parcel.readBoolean();
                        parcel.enforceNoDataAvail();
                        ExecutorUtils.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "setSideStageVisibility", new SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda8(readBoolean), false);
                        break;
                    case 5:
                        int readInt = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        ExecutorUtils.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "removeFromSideStage", new ShellCommandHandlerImpl$$ExternalSyntheticLambda0(readInt, 2), false);
                        break;
                    case FalsingManager.VERSION:
                        int readInt2 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        ExecutorUtils.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "exitSplitScreen", new SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda0(readInt2), false);
                        break;
                    case 7:
                        boolean readBoolean2 = parcel.readBoolean();
                        parcel.enforceNoDataAvail();
                        ExecutorUtils.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "exitSplitScreenOnHide", new LocationControllerImpl$H$$ExternalSyntheticLambda0(readBoolean2, 1), false);
                        break;
                    case 8:
                        parcel.enforceNoDataAvail();
                        ExecutorUtils.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startTask", new SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda1(parcel.readInt(), parcel.readInt(), (Bundle) parcel3.readTypedObject(Bundle.CREATOR)), false);
                        break;
                    case 9:
                        parcel.enforceNoDataAvail();
                        ExecutorUtils.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startShortcut", new SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda7(parcel.readString(), parcel.readString(), parcel.readInt(), (Bundle) parcel3.readTypedObject(Bundle.CREATOR), (UserHandle) parcel3.readTypedObject(UserHandle.CREATOR)), false);
                        break;
                    case 10:
                        parcel.enforceNoDataAvail();
                        ExecutorUtils.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startIntent", new SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda4((PendingIntent) parcel3.readTypedObject(PendingIntent.CREATOR), (Intent) parcel3.readTypedObject(Intent.CREATOR), parcel.readInt(), (Bundle) parcel3.readTypedObject(Bundle.CREATOR)), false);
                        break;
                    case QSTileImpl.C1034H.STALE:
                        int readInt3 = parcel.readInt();
                        Parcelable.Creator creator = Bundle.CREATOR;
                        parcel.enforceNoDataAvail();
                        ExecutorUtils.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startTasks", new SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda3(readInt3, (Bundle) parcel3.readTypedObject(creator), parcel.readInt(), (Bundle) parcel3.readTypedObject(creator), parcel.readInt(), parcel.readFloat(), (RemoteTransition) parcel3.readTypedObject(RemoteTransition.CREATOR)), false);
                        break;
                    case KeyguardSliceProvider.ALARM_VISIBILITY_HOURS:
                        int readInt4 = parcel.readInt();
                        Parcelable.Creator creator2 = Bundle.CREATOR;
                        parcel.enforceNoDataAvail();
                        ExecutorUtils.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startTasks", new SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda2(readInt4, (Bundle) parcel3.readTypedObject(creator2), parcel.readInt(), (Bundle) parcel3.readTypedObject(creator2), parcel.readInt(), parcel.readFloat(), (RemoteAnimationAdapter) parcel3.readTypedObject(RemoteAnimationAdapter.CREATOR)), false);
                        break;
                    case C0961QS.VERSION:
                        int readInt5 = parcel.readInt();
                        Parcelable.Creator creator3 = Bundle.CREATOR;
                        parcel.enforceNoDataAvail();
                        ExecutorUtils.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startIntentAndTaskWithLegacyTransition", new SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda5((PendingIntent) parcel3.readTypedObject(PendingIntent.CREATOR), (Intent) parcel3.readTypedObject(Intent.CREATOR), readInt5, (Bundle) parcel3.readTypedObject(creator3), (Bundle) parcel3.readTypedObject(creator3), parcel.readInt(), parcel.readFloat(), (RemoteAnimationAdapter) parcel3.readTypedObject(RemoteAnimationAdapter.CREATOR)), false);
                        break;
                    case 14:
                        parcel.enforceNoDataAvail();
                        Parcelable[][] parcelableArr = {null};
                        ExecutorUtils.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "onGoingToRecentsLegacy", new SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda9(parcelableArr, parcel.readBoolean(), (RemoteAnimationTarget[]) parcel3.createTypedArray(RemoteAnimationTarget.CREATOR)), true);
                        Parcelable[] parcelableArr2 = parcelableArr[0];
                        parcel2.writeNoException();
                        parcel4.writeTypedArray(parcelableArr2, 1);
                        break;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
                return true;
            }
            parcel4.writeString("com.android.wm.shell.splitscreen.ISplitScreen");
            return true;
        }

        public Stub() {
            attachInterface(this, "com.android.wm.shell.splitscreen.ISplitScreen");
        }
    }
}
