package com.android.p012wm.shell.recents;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.p012wm.shell.ShellTaskOrganizer$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.common.ExecutorUtils;
import com.android.p012wm.shell.recents.RecentTasksController;
import com.android.p012wm.shell.util.GroupedRecentTaskInfo;

/* renamed from: com.android.wm.shell.recents.IRecentTasks */
public interface IRecentTasks extends IInterface {

    /* renamed from: com.android.wm.shell.recents.IRecentTasks$Stub */
    public static abstract class Stub extends Binder implements IRecentTasks {
        public final IBinder asBinder() {
            return this;
        }

        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            IInterface queryLocalInterface;
            GroupedRecentTaskInfo[] groupedRecentTaskInfoArr;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.wm.shell.recents.IRecentTasks");
            }
            if (i != 1598968902) {
                IRecentTasksListener iRecentTasksListener = null;
                if (i == 2) {
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    if (readStrongBinder != null) {
                        IInterface queryLocalInterface2 = readStrongBinder.queryLocalInterface("com.android.wm.shell.recents.IRecentTasksListener");
                        if (queryLocalInterface2 == null || !(queryLocalInterface2 instanceof IRecentTasksListener)) {
                            iRecentTasksListener = new IRecentTasksListener$Stub$Proxy(readStrongBinder);
                        } else {
                            iRecentTasksListener = (IRecentTasksListener) queryLocalInterface2;
                        }
                    }
                    parcel.enforceNoDataAvail();
                    RecentTasksController.IRecentTasksImpl iRecentTasksImpl = (RecentTasksController.IRecentTasksImpl) this;
                    ExecutorUtils.executeRemoteCallWithTaskPermission(iRecentTasksImpl.mController, "registerRecentTasksListener", new RecentTasksController$IRecentTasksImpl$$ExternalSyntheticLambda0(iRecentTasksImpl, iRecentTasksListener), false);
                } else if (i == 3) {
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    if (!(readStrongBinder2 == null || (queryLocalInterface = readStrongBinder2.queryLocalInterface("com.android.wm.shell.recents.IRecentTasksListener")) == null || !(queryLocalInterface instanceof IRecentTasksListener))) {
                        IRecentTasksListener iRecentTasksListener2 = (IRecentTasksListener) queryLocalInterface;
                    }
                    parcel.enforceNoDataAvail();
                    RecentTasksController.IRecentTasksImpl iRecentTasksImpl2 = (RecentTasksController.IRecentTasksImpl) this;
                    ExecutorUtils.executeRemoteCallWithTaskPermission(iRecentTasksImpl2.mController, "unregisterRecentTasksListener", new ShellTaskOrganizer$$ExternalSyntheticLambda0(iRecentTasksImpl2, 5), false);
                } else if (i != 4) {
                    return super.onTransact(i, parcel, parcel2, i2);
                } else {
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    RecentTasksController recentTasksController = ((RecentTasksController.IRecentTasksImpl) this).mController;
                    if (recentTasksController == null) {
                        groupedRecentTaskInfoArr = new GroupedRecentTaskInfo[0];
                    } else {
                        GroupedRecentTaskInfo[][] groupedRecentTaskInfoArr2 = {null};
                        ExecutorUtils.executeRemoteCallWithTaskPermission(recentTasksController, "getRecentTasks", new RecentTasksController$IRecentTasksImpl$$ExternalSyntheticLambda1(groupedRecentTaskInfoArr2, readInt, readInt2, readInt3), true);
                        groupedRecentTaskInfoArr = groupedRecentTaskInfoArr2[0];
                    }
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(groupedRecentTaskInfoArr, 1);
                }
                return true;
            }
            parcel2.writeString("com.android.wm.shell.recents.IRecentTasks");
            return true;
        }

        public Stub() {
            attachInterface(this, "com.android.wm.shell.recents.IRecentTasks");
        }
    }
}
